package net.ilexiconn.nationsgui.forge.client.gui.chat.tag;

import java.util.Map;
import net.minecraft.client.gui.Gui;

public abstract class AbstractChatTag extends Gui {

   protected AbstractChatTag(Map<String, String> parameters) throws Exception {}

   public abstract void render(int var1, int var2);

   public abstract void onClick(int var1, int var2);

   public int getLineHeight() {
      return 1;
   }

   public abstract int getWidth();
}
