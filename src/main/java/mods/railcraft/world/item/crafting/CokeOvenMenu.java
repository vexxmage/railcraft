package mods.railcraft.world.item.crafting;

import mods.railcraft.world.inventory.RailcraftMenuTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.FurnaceResultSlot;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.world.World;

public class CokeOvenMenu extends Container {

  private final World level;
  private final IIntArray data;
  private final IInventory cokeOvenInventory;

  public CokeOvenMenu(int containerID, PlayerInventory playerInventory) {
    this(containerID, playerInventory, new Inventory(2), new IntArray(3));
  }

  public CokeOvenMenu(int containerID, PlayerInventory playerInventory,
      IInventory cokeOvenStorageEntity, IIntArray dataAccess) {
    super(RailcraftMenuTypes.COKE_OVEN.get(), containerID);
    checkContainerSize(cokeOvenStorageEntity, 2);
    checkContainerDataCount(dataAccess, 3);
    this.cokeOvenInventory = cokeOvenStorageEntity;
    this.data = dataAccess;
    this.level = playerInventory.player.level;

    // our inventory
    this.addSlot(new Slot(cokeOvenStorageEntity, 0, 56, 17));
    this.addSlot(new FurnaceResultSlot(playerInventory.player, cokeOvenStorageEntity, 1, 116, 35));

    // generic player inventory
    for (int i = 0; i < 3; ++i) {
      for (int j = 0; j < 9; ++j) {
        this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
      }
    }

    for (int k = 0; k < 9; ++k) {
      this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
    }

    this.addDataSlots(dataAccess);
  }


  @Override
  public boolean stillValid(PlayerEntity player) {
    return this.cokeOvenInventory.stillValid(player);
  }

  @Override
  public ItemStack quickMoveStack(PlayerEntity playerEntity, int slotID) {
    Slot slot = this.slots.get(slotID);
    if (slot == null || !slot.hasItem()) {
      return ItemStack.EMPTY;
    }

    ItemStack itemstack = slot.getItem();
    ItemStack itemstack1 = itemstack.copy();
    if (slotID == 1) {
      if (!this.moveItemStackTo(itemstack1, 2, 38, true)) {
        return ItemStack.EMPTY;
      }

      slot.onQuickCraft(itemstack1, itemstack);
      slot.onTake(playerEntity, itemstack1);
    } else if (slotID != 1 && slotID != 0) {
      if (this.canSmelt(itemstack1)) {
        if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
          return ItemStack.EMPTY;
        }
      } else if (slotID >= 2 && slotID < 29) {
        if (!this.moveItemStackTo(itemstack1, 29, 38, false)) {
          return ItemStack.EMPTY;
        }
      } else if (slotID >= 29 && slotID < 38 && !this.moveItemStackTo(itemstack1, 2, 29, false)) {
        return ItemStack.EMPTY;
      }
    } else if (!this.moveItemStackTo(itemstack1, 2, 38, false)) {
      return ItemStack.EMPTY;
    }

    if (itemstack1.isEmpty()) {
      slot.set(ItemStack.EMPTY);
    } else {
      slot.setChanged();
    }

    if (itemstack1.getCount() == itemstack.getCount()) {
      return ItemStack.EMPTY;
    }

    slot.onTake(playerEntity, itemstack1);

    return itemstack;
  }

  protected boolean canSmelt(ItemStack itemStack) {
    return this.level.getRecipeManager()
      .getRecipeFor(RailcraftRecipeTypes.COKEING, new Inventory(itemStack), this.level).isPresent();
  }

  /**
   * Get the burn progress in float percent.
   */
  public float getBurnProgress() {
    int reqTime = this.data.get(0);
    int currentTick = this.data.get(1);
    return Math.max(Math.min(currentTick / reqTime, 1), 0);
  }

  public int tankFillProgress() {
    int tankUsed = this.data.get(2);
    return Math.max(Math.min(tankUsed / 10000, 1), 0);
  }
}
