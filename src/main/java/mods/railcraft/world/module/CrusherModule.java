package mods.railcraft.world.module;

import static mods.railcraft.data.recipes.builders.CrusherRecipeBuilder.DEFAULT_PROCESSING_TIME;

import java.util.Optional;
import mods.railcraft.util.MachineEnergyStorage;
import mods.railcraft.util.container.ContainerCopy;
import mods.railcraft.util.container.ContainerMapper;
import mods.railcraft.util.container.FilteredInvWrapper;
import mods.railcraft.world.item.crafting.CrusherRecipe;
import mods.railcraft.world.item.crafting.RailcraftRecipeTypes;
import mods.railcraft.world.level.block.entity.CrusherBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;

public class CrusherModule extends CrafterModule<CrusherBlockEntity> {

    public static final int SLOT_INPUT = 0;
    public static final int SLOT_OUTPUT = 9;

    private final ContainerMapper inputContainer;
    private final ContainerMapper outputContainer;

    private Optional<CrusherRecipe> currentRecipe;
    private int currentSlot;

    private final MachineEnergyStorage energyStorage;

    private LazyOptional<IItemHandler> inputHandler, outputHandler;
    private LazyOptional<IEnergyStorage> energyHandler;
    private static final int COST_PER_TICK = 80;
    private static final int COST_PER_STEP = COST_PER_TICK * PROGRESS_STEP;

    public CrusherModule(CrusherBlockEntity provider) {
        super(provider, 18);
        energyStorage = new MachineEnergyStorage(80000, 5000);

        inputContainer = ContainerMapper.make(this, SLOT_INPUT, 9);
        outputContainer = ContainerMapper.make(this, SLOT_OUTPUT, 9).ignoreItemChecks();
        currentRecipe = Optional.empty();

        inputHandler = LazyOptional.of(() -> new FilteredInvWrapper(inputContainer, true, false));
        outputHandler = LazyOptional.of(() -> new FilteredInvWrapper(outputContainer, false, true));
        energyHandler = LazyOptional.of(() -> energyStorage);
    }

    @Override
    public void serverTick() {
        super.serverTick();
        if(!lacksRequirements()) {
            energyStorage.consumeEnergyInternally(COST_PER_TICK);
        }
    }

    public MachineEnergyStorage getEnergyStorage() {
        return energyStorage;
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        if (index < 9 && super.canPlaceItem(index, stack)) {
            return getRecipe(stack).isPresent();
        }
        return false;
    }

    @Override
    protected int calculateDuration() {
        return currentRecipe.map(CrusherRecipe::getTickCost).orElse(DEFAULT_PROCESSING_TIME);
    }

    @Override
    protected boolean lacksRequirements() {
        return currentRecipe.isEmpty();
    }

    @Override
    protected boolean doProcessStep() {
        return energyStorage.getEnergyStored() > COST_PER_STEP;
    }

    @Override
    protected void setupCrafting() {
        if (isRecipeValid()) {
            return;
        }

        currentRecipe = Optional.empty();
        for (int i = 0; i < inputContainer.getContainerSize(); i++) {
            var itemStack = inputContainer.getItem(i);
            if (!itemStack.isEmpty()) {
                currentSlot = i;
                currentRecipe = getRecipe(itemStack);
                break;
            }
        }
    }

    @Override
    protected boolean craftAndPush() {
        final var recipe = currentRecipe.orElseThrow(NullPointerException::new);
        var tempInv = new ContainerCopy(outputContainer);
        var outputs = recipe.pollOutputs(provider.getLevel().getRandom());
        var hasSpace = outputs.stream()
            .map(tempInv::addStack)
            .allMatch(ItemStack::isEmpty);

        if (hasSpace) {
            outputs.forEach(outputContainer::addStack);
            inputContainer.removeOneItem(recipe.getIngredients().get(0));
            provider.getLevel().playSound(null, provider.blockPos(),
                SoundEvents.IRON_GOLEM_DEATH, SoundSource.BLOCKS, 1.0f,
                provider.getLevel().getRandom().nextFloat() * 0.25f + 0.7f);
        }
        return hasSpace;
    }

    private boolean isRecipeValid() {
        return currentRecipe
            .map(r -> r.getIngredients().get(0))
            .map(r -> r.test(inputContainer.getItem(currentSlot)))
            .orElse(false);
    }

    private Optional<CrusherRecipe> getRecipe(ItemStack itemStack) {
        return provider.getLevel().getRecipeManager()
            .getRecipeFor(RailcraftRecipeTypes.CRUSHING.get(),
                new SimpleContainer(itemStack), provider.getLevel());
    }

    @Override
    public void setChanged() {
        super.setChanged();
        currentRecipe = Optional.empty();
    }

    public LazyOptional<IItemHandler> getInputHandler() {
        return inputHandler;
    }

    public LazyOptional<IItemHandler> getOutputHandler() {
        return outputHandler;
    }

    public LazyOptional<IEnergyStorage> getEnergyHandler() {
        return energyHandler;
    }

    public void invalidateCaps() {
        inputHandler.invalidate();
        outputHandler.invalidate();
        energyHandler.invalidate();
    }

    @Override
    public CompoundTag serializeNBT() {
        var tag = super.serializeNBT();
        tag.put("energy", energyStorage.serializeNBT());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        super.deserializeNBT(tag);
        energyStorage.deserializeNBT(tag.get("energy"));
    }
}
