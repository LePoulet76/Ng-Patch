package net.ilexiconn.nationsgui.forge.client.gui.chat.tag;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.client.gui.chat.tag.AbstractChatTag;
import net.ilexiconn.nationsgui.forge.server.notifications.NotificationManager;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;

public abstract class AbstractActionTag extends AbstractChatTag {

   private NBTTagCompound tagCompound = null;


   public AbstractActionTag(Map<String, String> parameters) throws Exception {
      super(parameters);
      String id = (String)parameters.get("action");
      if(id != null) {
         this.tagCompound = new NBTTagCompound();
         this.tagCompound.func_74778_a("id", id);
         NBTTagCompound data = new NBTTagCompound();
         Iterator var4 = parameters.entrySet().iterator();

         while(var4.hasNext()) {
            Entry entry = (Entry)var4.next();
            data.func_74778_a((String)entry.getKey(), (String)entry.getValue());
         }

         this.tagCompound.func_74782_a("data", data);
      }
   }

   protected void doAction() {
      if(this.tagCompound != null) {
         NotificationManager.executeAction(Minecraft.func_71410_x().field_71439_g, this.tagCompound.func_74779_i("id"), this.tagCompound.func_74775_l("data"));
      }

   }
}
