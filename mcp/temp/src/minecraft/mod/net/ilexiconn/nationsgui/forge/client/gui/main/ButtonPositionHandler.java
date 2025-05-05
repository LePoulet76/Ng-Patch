package net.ilexiconn.nationsgui.forge.client.gui.main;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.client.gui.main.ButtonPositionHandler$1;
import net.ilexiconn.nationsgui.forge.client.gui.main.ButtonPositionHandler$State;
import net.ilexiconn.nationsgui.forge.client.gui.main.component.MenuButtonGUI;

@SideOnly(Side.CLIENT)
public enum ButtonPositionHandler {

   NONE("NONE", 0),
   LEFT("LEFT", 1),
   RIGHT("RIGHT", 2);
   private static Map<MenuButtonGUI, Float> positionMap = new HashMap();
   // $FF: synthetic field
   private static final ButtonPositionHandler[] $VALUES = new ButtonPositionHandler[]{NONE, LEFT, RIGHT};


   private ButtonPositionHandler(String var1, int var2) {}

   protected void setTargetPosition(MenuButtonGUI button, float targetPosition) {
      positionMap.put(button, Float.valueOf(targetPosition));
   }

   public static void updatePositions() {
      ArrayList remove = new ArrayList();
      Iterator var1 = positionMap.entrySet().iterator();

      while(var1.hasNext()) {
         Entry button = (Entry)var1.next();
         ((MenuButtonGUI)button.getKey()).positionX = interpolate(((MenuButtonGUI)button.getKey()).positionX, ((Float)button.getValue()).floatValue(), 0.35F);
         if(((MenuButtonGUI)button.getKey()).positionX == ((Float)button.getValue()).floatValue()) {
            remove.add(button.getKey());
         }
      }

      var1 = remove.iterator();

      while(var1.hasNext()) {
         MenuButtonGUI button1 = (MenuButtonGUI)var1.next();
         positionMap.remove(button1);
      }

   }

   private static float interpolate(float prev, float current, float partialTicks) {
      return prev + partialTicks * (current - prev);
   }

   public abstract void handleTransform(MenuButtonGUI var1, ButtonPositionHandler$State var2);

   // $FF: synthetic method
   ButtonPositionHandler(String x0, int x1, ButtonPositionHandler$1 x2) {
      this(x0, x1);
   }

}
