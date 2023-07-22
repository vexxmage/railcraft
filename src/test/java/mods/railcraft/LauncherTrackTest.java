package mods.railcraft;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(Railcraft.ID)
@PrefixGameTestTemplate(false)
public class LauncherTrackTest {

  @GameTest(template = "launcher_track_active")
  public static void launcherTrackActive(GameTestHelper helper) {
    helper.pressButton(0, 3, 1);
    helper.succeedWhenEntityPresent(EntityType.MINECART, 22, 2, 1);
  }

  @GameTest(template = "launcher_track_passive")
  public static void launcherTrackPassive(GameTestHelper helper) {
    helper.pressButton(0, 3, 1);
    helper.succeedWhenEntityPresent(EntityType.MINECART, 5, 2, 1);
  }
}
