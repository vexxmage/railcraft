package mods.railcraft.world.level.block.entity;

import java.util.Optional;

import javax.annotation.Nullable;

import mods.railcraft.world.item.crafting.CokeOvenMenu;
import mods.railcraft.world.item.crafting.CokeOvenRecipe;
import mods.railcraft.world.item.crafting.RailcraftRecipeTypes;
import mods.railcraft.world.level.block.RailcraftBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class CokeOvenBlockEntity extends LockableTileEntity
    implements ISidedInventory, IRecipeHolder, ITickableTileEntity, IFluidTank {

  private static final ITextComponent MENU_TITLE =
      new TranslationTextComponent("container.coke_oven");
  private static final int[] SLOTS_FOR_UP_AND_SIDES = new int[]{0};
  private static final int[] SLOTS_FOR_DOWN = new int[]{1};
  private static final int FLUID_STORAGE_MAX = 10000;

  //FIRST LAYER
  private static final BlockPattern FIRST_Z_LAYER =
      BlockPatternBuilder.start()
      .aisle(
        "CCC",
        "C C",
        "CCC"
      )
      .where('C',
        CachedBlockInfo.hasState(BlockStateMatcher.forBlock(RailcraftBlocks.FIRESTONE.get())))
      .build();

  private static final BlockPattern SECOND_Z_LAYER =
      BlockPatternBuilder.start()
      .aisle(
        "CCC",
        "C~C",
        "CCC"
      )
      .where('C',
        CachedBlockInfo.hasState(BlockStateMatcher.forBlock(RailcraftBlocks.FIRESTONE.get())))
      .where('~',
        CachedBlockInfo.hasState(BlockStateMatcher.forBlock(Blocks.AIR)))
      .build();

  private static final BlockPattern THIRD_Z_LAYER =
      BlockPatternBuilder.start()
      .aisle(
        "CCC",
        "CCC",
        "CCC"
      )
      .where('C',
        CachedBlockInfo.hasState(BlockStateMatcher.forBlock(RailcraftBlocks.FIRESTONE.get())))
      .build();

  // internal inventory, 3 total. 0 IN, 1 OUT
  protected NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);
  protected FluidStack fluid = FluidStack.EMPTY;

  private int recipieRequiredTime = 0;
  private int currentTick = 0;
  // 0 - required time, 1 - current tick, 2 - fluid amount
  protected final IIntArray dataAccess = new IIntArray() {
    public int get(int key) {
      switch (key) {
        case 0:
          return CokeOvenBlockEntity.this.recipieRequiredTime;
        case 1:
          return CokeOvenBlockEntity.this.currentTick;
        case 2:
          return CokeOvenBlockEntity.this.fluid.getAmount();
        default:
          return 0;
      }
    }

    public void set(int key, int value) {
      switch (key) {
        case 0:
          // unsettable
          break;
        case 1:
          // unsettable
          break;
        case 2:
          // unsettable
          break;
        default:
          break;
      }

    }

    public int getCount() {
      return 3;
    }
  };

  public CokeOvenBlockEntity() {
    super(RailcraftBlockEntityTypes.COKE_OVEN.get());
  }

  public CokeOvenBlockEntity(TileEntityType<?> type) {
    super(type);
  }

  /**
   * Can we run? Returns true if it can.
   */
  public boolean canRun() {
    if (this.recipieRequiredTime > 0 || this.recipieRequiredTime == -1) {
      return true;
    }
    return false;
  }

  @Override
  public void load(BlockState blockState, CompoundNBT data) {
    super.load(blockState, data);
    this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
    ItemStackHelper.loadAllItems(data, this.items);
    this.recipieRequiredTime = data.getInt("RecipieRequiredTime");
    this.currentTick = data.getInt("CurrentTick");
    this.fluid = FluidStack.loadFluidStackFromNBT(data);
  }

  @Override
  public CompoundNBT save(CompoundNBT data) {
    super.save(data);
    ItemStackHelper.saveAllItems(data, this.items);
    data.putInt("RecipieRequiredTime", this.recipieRequiredTime);
    data.putInt("CurrentTick", this.currentTick);
    this.fluid.writeToNBT(data);
    return data;
  }

  @Override
  public int getContainerSize() {
    return this.items.size();
  }

  @Override
  public boolean isEmpty() {
    for (ItemStack itemstack : this.items) {
      if (!itemstack.isEmpty()) {
        return false;
      }
    }
    return true;
  }

  @Override
  public ItemStack getItem(int index) {
    return this.items.get(index);
  }

  @Override
  public ItemStack removeItem(int index, int count) {
    return ItemStackHelper.removeItem(this.items, index, count);
  }

  @Override
  public ItemStack removeItemNoUpdate(int index) {
    return ItemStackHelper.takeItem(this.items, index);
  }

  @Override
  public void setItem(int slotID, ItemStack itemStack) {
    ItemStack itemstack = this.items.get(slotID);
    // not empty, same item, and tag matches
    boolean isValid = !itemStack.isEmpty() && itemStack.sameItem(itemstack)
        && ItemStack.tagMatches(itemStack, itemstack);
    this.items.set(slotID, itemStack);
    if (itemStack.getCount() > this.getMaxStackSize()) {
      itemStack.setCount(this.getMaxStackSize());
    }
    // slot 0 is input
    if (slotID == 0 && !isValid) {
      this.recipieRequiredTime = 0; //this.getTotalCookTime();
      this.currentTick = 0;
      this.setChanged();
    }
  }

  @Override
  public boolean stillValid(PlayerEntity playerEntity) {
    if (this.level.getBlockEntity(this.worldPosition) != this) {
      return false;
    } else {
      return playerEntity.distanceToSqr(
          (double)this.worldPosition.getX() + 0.5D,
          (double)this.worldPosition.getY() + 0.5D,
          (double)this.worldPosition.getZ() + 0.5D) <= 64.0D;
    }
  }

  @Override
  public boolean canPlaceItem(int index, ItemStack itemStack) {
    if (index == 1) { // 1 is OUTPUT, 0 is INPUT
      return false;
    }
    return true;
  }

  @Override
  public void clearContent() {
    this.items.clear();
  }

  @Override
  public void setRecipeUsed(IRecipe<?> recipe) {
    // unused
  }

  @Override
  @Nullable
  public IRecipe<?> getRecipeUsed() {
    // unused
    return null;
  }

  @Override
  public FluidStack getFluid() {
    return this.fluid;
  }

  @Override
  public int getFluidAmount() {
    return this.fluid.getAmount();
  }

  @Override
  public int getCapacity() {
    return FLUID_STORAGE_MAX;
  }

  @Override
  public boolean isFluidValid(FluidStack stack) {
    return false;
  }

  @Override
  public int fill(FluidStack resource, FluidAction action) {
    return 0;
  }

  @Override
  public FluidStack drain(int maxDrain, FluidAction action) {
    int drained = maxDrain;
    if (fluid.getAmount() < drained) {
      drained = fluid.getAmount();
    }
    FluidStack stack = new FluidStack(fluid, drained);
    if (action.execute() && drained > 0) {
      fluid.shrink(drained);
    }
    return stack;
  }

  @Override
  public FluidStack drain(FluidStack resource, FluidAction action) {
    if (resource.isEmpty() || !resource.isFluidEqual(fluid)) {
      return FluidStack.EMPTY;
    }
    return drain(resource.getAmount(), action);
  }

  // TODO particles
  @Override
  public void tick() {
    if (this.level.isClientSide()) {
      return;
    }
    // TODO neighbour checking for multiblock.

    ItemStack itemstack = this.items.get(0);
    if (itemstack.isEmpty()) {
      this.recipieRequiredTime = 0;
      return;
    }

    Optional<CokeOvenRecipe> irecipe = this.level.getRecipeManager()
        .getRecipeFor(RailcraftRecipeTypes.COKEING, this, this.level);

    if (!irecipe.isPresent()) {
      this.recipieRequiredTime = 0;
      return;
    }
    CokeOvenRecipe cokeRecipe = irecipe.get();
    if (this.canWork(cokeRecipe)) {
      this.recipieRequiredTime = 0;
      return;
    }
    if (this.currentTick >= this.recipieRequiredTime) {
      this.currentTick = 0;
      this.recipieRequiredTime = this.getTickCost();
      this.bake(cokeRecipe);
    }
    this.currentTick++;
  }

  protected boolean scanNeighbours() {
    if (FIRST_Z_LAYER.find(this.level, this.worldPosition) == null) {
      return false;
    }

    return false;
  }

  protected boolean canWork(CokeOvenRecipe recipe) {
    ItemStack resultItemStack = recipe.getResultItem();
    FluidStack resultFluidStack = recipe.getResultFluid();
    if (resultItemStack.isEmpty()) {
      return false;
    }
    ItemStack resultSlot = this.items.get(1);
    // empty? we can proceede to coke
    if (resultSlot.isEmpty()) {
      return true;
    }
    if (!resultSlot.sameItem(resultItemStack) && !this.fluid.isFluidEqual(resultFluidStack)) {
      return false;
    }
    // respect stacks (and fluid count.)
    if ((resultSlot.getCount() + resultItemStack.getCount() <= this.getMaxStackSize())
        && resultSlot.getCount() + resultItemStack.getCount() <= resultSlot.getMaxStackSize()
        && this.fluid.getAmount() + resultFluidStack.getAmount() <= FLUID_STORAGE_MAX) {
      return true;
    }
    return (resultSlot.getCount() + resultItemStack.getCount() <= resultItemStack.getMaxStackSize())
        && (this.fluid.getAmount() + resultFluidStack.getAmount() <= FLUID_STORAGE_MAX);
  }

  protected int getTickCost() {
    return this.level.getRecipeManager()
      .getRecipeFor(RailcraftRecipeTypes.COKEING, this, this.level)
      .map(CokeOvenRecipe::getTickCost).orElse(2400); // 2min base for coal coke
  }

  private void bake(CokeOvenRecipe cokeOvenRecipe) {
    if (!this.canWork(cokeOvenRecipe)) {
      return;
    }
    ItemStack resultItemStack = cokeOvenRecipe.getResultItem();
    FluidStack resultFluidStack = cokeOvenRecipe.getResultFluid();

    ItemStack resultStack = this.items.get(1);

    if (resultStack.isEmpty()) {
      this.items.set(1, resultItemStack.copy());
    } else if (resultStack.sameItem(resultItemStack)) {
      resultStack.grow(resultItemStack.getCount());
    }

    if (this.fluid.isEmpty()) {
      this.fluid = resultFluidStack.copy();
    } else if (this.fluid.isFluidEqual(resultFluidStack)) {
      this.fluid.grow(resultFluidStack.getAmount());
    }


    if (!this.level.isClientSide) {
      this.setRecipeUsed(cokeOvenRecipe);
    }

    ItemStack inputStack = this.items.get(0);
    inputStack.shrink(1);
  }

  @Override
  public int[] getSlotsForFace(Direction direction) {
    if (direction == Direction.DOWN) {
      return SLOTS_FOR_DOWN;
    }
    return SLOTS_FOR_UP_AND_SIDES;
  }

  @Override
  public boolean canPlaceItemThroughFace(
      int storageIndex, ItemStack itemStack, Direction direction) {
    return this.canPlaceItem(storageIndex, itemStack);
  }

  @Override
  public boolean canTakeItemThroughFace(int slotIndex, ItemStack itemStack, Direction direction) {
    if (direction == Direction.DOWN && slotIndex == 0) { // only output, index 1
      return false;
    }
    return true;
  }

  @Override
  protected ITextComponent getDefaultName() {
    return MENU_TITLE;
  }

  @Override
  protected Container createMenu(int containerProvider, PlayerInventory playerInventory) {
    return new CokeOvenMenu(containerProvider, playerInventory, this, this.dataAccess);
  }

}
