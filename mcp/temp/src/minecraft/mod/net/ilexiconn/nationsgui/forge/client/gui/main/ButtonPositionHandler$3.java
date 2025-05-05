package net.ilexiconn.nationsgui.forge.client.gui.main;

import net.ilexiconn.nationsgui.forge.client.gui.main.ButtonPositionHandler$4;
import net.ilexiconn.nationsgui.forge.client.gui.main.ButtonPositionHandler$State;
import net.ilexiconn.nationsgui.forge.client.gui.main.component.MenuButtonGUI;

enum ButtonPositionHandler$3 {

   ButtonPositionHandler$3(String var1, int var2) {}

   public void handleTransform(MenuButtonGUI button, ButtonPositionHandler$State state) {
      switch(ButtonPositionHandler$4.$SwitchMap$net$ilexiconn$nationsgui$forge$client$gui$main$ButtonPositionHandler$State[state.ordinal()]) {
      case 1:
         this.setTargetPosition(button, button.positionXOrig);
         break;
      case 2:
         this.setTargetPosition(button, button.positionXOrig - 10.0F);
      }

   }
}
