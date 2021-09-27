package mods.railcraft.world.level.block.entity.signal;

import mods.railcraft.api.signals.DualLamp;
import mods.railcraft.api.signals.DualSignalReceiver;
import mods.railcraft.api.signals.IReceiverProvider;
import mods.railcraft.api.signals.SignalAspect;
import mods.railcraft.api.signals.SignalController;
import mods.railcraft.world.level.block.entity.RailcraftBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class DualDistantSignalBlockEntity extends AbstractSignalBlockEntity implements IReceiverProvider, IDualSignal {

  private final DualSignalReceiver receiver = new DualSignalReceiver("nothing", this);

  public DualDistantSignalBlockEntity() {
    super(RailcraftBlockEntityTypes.DUAL_DISTANT_SIGNAL.get());
  }

  @Override
  public int getLightValue() {
    return Math.max(getSignalAspect(DualLamp.TOP).getBlockLight(),
        getSignalAspect(DualLamp.BOTTOM).getBlockLight());
  }

  @Override
  public void tick() {
    super.tick();
    if (this.level.isClientSide()) {
      receiver.tickClient();
      return;
    }

    receiver.tickServer();
    int numPairs = receiver.getNumPairs();
    boolean changed = false;
    switch (numPairs) {
      case 0:
        changed = receiver.setAspect(DualLamp.TOP, SignalAspect.BLINK_RED);
      case 1:
        changed |= receiver.setAspect(DualLamp.BOTTOM, SignalAspect.BLINK_RED);
    }
    if (changed) {
      syncToClient();
    }
  }

  @Override
  public void onControllerAspectChange(SignalController con, SignalAspect aspect) {
    syncToClient();
  }

  @Override
  public CompoundNBT save(CompoundNBT data) {
    super.save(data);

    receiver.writeToNBT(data);
    return data;
  }

  @Override
  public void load(BlockState state, CompoundNBT data) {
    super.load(state, data);

    receiver.readFromNBT(data);

  }

  @Override
  public void writeSyncData(PacketBuffer data) {
    super.writeSyncData(data);
    receiver.writeSyncData(data);
  }

  @Override
  public void readSyncData(PacketBuffer data) {
    super.readSyncData(data);
    receiver.readSyncData(data);
  }

  @Override
  public DualSignalReceiver getReceiver() {
    return receiver;
  }

  @Override
  public SignalAspect getSignalAspect(DualLamp lamp) {
    return receiver.getAspect(lamp);
  }

  @Override
  public SignalAspect getSignalAspect() {
    return receiver.getAspect(DualLamp.TOP);
  }
}
