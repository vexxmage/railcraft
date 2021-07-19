package mods.railcraft.world.entity;

import java.util.EnumSet;
import javax.annotation.Nullable;
import mods.railcraft.api.charge.CapabilitiesCharge;
import mods.railcraft.api.charge.IBatteryCart;
import mods.railcraft.battery.CartBattery;
import mods.railcraft.sounds.RailcraftSoundEvents;
import mods.railcraft.util.inventory.InvTools;
import mods.railcraft.util.inventory.wrappers.InventoryMapper;
import mods.railcraft.world.inventory.ElectricLocomotiveMenu;
import mods.railcraft.world.item.ItemTicket;
import mods.railcraft.world.item.RailcraftItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

/**
 * @author CovertJaguar <http://www.railcraft.info/>
 */
public class ElectricLocomotiveEntity extends LocomotiveEntity implements ISidedInventory {

  private static final int CHARGE_USE_PER_TICK = 20;
  private static final int FUEL_PER_REQUEST = 1;
  private static final int CHARGE_USE_PER_REQUEST = CHARGE_USE_PER_TICK * FUEL_PER_REQUEST;
  public static final float MAX_CHARGE = 5000.0F;
  private static final int SLOT_TICKET = 0;
  private static final int[] SLOTS = InvTools.buildSlotArray(0, 1);
  private final IInventory invTicket = new InventoryMapper(this, SLOT_TICKET, 2).ignoreItemChecks();
  private final LazyOptional<IBatteryCart> cartBattery =
      LazyOptional.of(() -> new CartBattery(IBatteryCart.Type.USER, MAX_CHARGE));

  public ElectricLocomotiveEntity(EntityType<?> type, World world) {
    super(type, world);
  }

  public ElectricLocomotiveEntity(double x, double y, double z, World world) {
    super(RailcraftEntityTypes.ELECTRIC_LOCOMOTIVE.get(), x, y, z, world);
  }

  {
    setAllowedModes(EnumSet.of(Mode.RUNNING, Mode.SHUTDOWN));
  }

  @Override
  protected int getDefaultPrimaryColor() {
    return DyeColor.YELLOW.getColorValue();
  }

  @Override
  protected int getDefaultSecondaryColor() {
    return DyeColor.BLACK.getColorValue();
  }

  @Override
  public SoundEvent getWhistle() {
    return RailcraftSoundEvents.ELECTRIC_WHISTLE.get();
  }

  @Override
  protected int getIdleFuelUse() {
    return 0;
  }

  @Override
  public int getMoreGoJuice() {
    return cartBattery
        .filter(cart -> cart.getCharge() > CHARGE_USE_PER_REQUEST)
        .map(cart -> {
          cart.removeCharge(CHARGE_USE_PER_REQUEST);
          return FUEL_PER_REQUEST;
        })
        .orElse(0);
  }

  @Override
  public Item getItem() {
    return RailcraftItems.ELECTRIC_LOCOMOTIVE.get();
  }

  @Override
  public float getOptimalDistance(AbstractMinecartEntity cart) {
    return 0.92f;
  }

  @Override
  public void tick() {
    super.tick();
    if (this.level.isClientSide())
      return;
    cartBattery.ifPresent(cart -> cart.tick(this));
  }

  @Override
  protected void moveAlongTrack(BlockPos pos, BlockState state) {
    super.moveAlongTrack(pos, state);
    if (this.level.isClientSide())
      return;
    cartBattery.ifPresent(cart -> cart.tickOnTrack(this, pos));
  }

  @Override
  protected IInventory getTicketInventory() {
    return invTicket;
  }

  @Override
  public int getContainerSize() {
    return 2;
  }

  @Override
  public int[] getSlotsForFace(Direction side) {
    return SLOTS;
  }

  @Override
  public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction side) {
    return canPlaceItem(slot, stack);
  }

  @Override
  public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction side) {
    return slot == SLOT_TICKET;
  }

  @Override
  public boolean canPlaceItem(int slot, @Nullable ItemStack stack) {
    switch (slot) {
      case SLOT_TICKET:
        return ItemTicket.FILTER.test(stack);
      default:
        return false;
    }
  }

  public IBatteryCart getBatteryCart() {
    return this.getCapability(CapabilitiesCharge.CART_BATTERY)
        .orElseThrow(IllegalStateException::new);
  }

  @Override
  public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
    if (capability == CapabilitiesCharge.CART_BATTERY)
      return cartBattery.cast();
    return super.getCapability(capability, facing);
  }

  @Override
  public void addAdditionalSaveData(CompoundNBT data) {
    super.addAdditionalSaveData(data);
    cartBattery.ifPresent(cart -> cart.writeToNBT(data));
  }

  @Override
  public void readAdditionalSaveData(CompoundNBT data) {
    super.readAdditionalSaveData(data);
    cartBattery.ifPresent(cart -> cart.readFromNBT(data));
  }

  @Override
  public boolean doesCartMatchFilter(ItemStack stack) {
    return stack.getItem() == this.getItem();
  }

  @Override
  public ItemStack getContents() {
    return ItemStack.EMPTY;
  }

  @Override
  protected Container createMenu(int id, PlayerInventory playerInventory) {
    return new ElectricLocomotiveMenu(id, playerInventory, this);
  }
}