package mods.railcraft.client.util;

import java.util.Collection;
import java.util.function.Consumer;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;

public class GuiUtil {

  public static void newButtonRowAuto(Consumer<AbstractWidget> addButton, int xStart, int xSize,
      Collection<? extends Button> buttons) {
    int buttonWidth = buttons.stream().mapToInt(Button::getWidth).sum();
    int remaining = xSize - buttonWidth;
    int spacing = remaining / (buttons.size() + 1);
    int pointer = 0;
    for (var button : buttons) {
      pointer += spacing;
      button.setX(xStart + pointer);
      pointer += button.getWidth();
      addButton.accept(button);
    }
  }

  public static void newButtonRowBookended(Consumer<AbstractWidget> buttonList, int xStart,
      int xEnd, Collection<? extends Button> buttons) {
    int buttonWidth = buttons.stream().mapToInt(Button::getWidth).sum();
    int remaining = (xEnd - xStart) - buttonWidth;
    int spacing = remaining / (buttons.size() + 1);
    int pointer = 0;
    for (var button : buttons) {
      pointer += spacing;
      button.setX(xStart + pointer);
      pointer += button.getWidth();
      buttonList.accept(button);
    }
  }

  public static void newButtonRow(Consumer<AbstractWidget> buttonList, int xStart, int spacing,
      Collection<? extends Button> buttons) {
    int pointer = 0;
    for (var button : buttons) {
      button.setX(xStart + pointer);
      pointer += button.getWidth() + spacing;
      buttonList.accept(button);
    }
  }
}
