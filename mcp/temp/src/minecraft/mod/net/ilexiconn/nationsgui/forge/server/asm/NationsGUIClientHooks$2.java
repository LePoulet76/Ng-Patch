package net.ilexiconn.nationsgui.forge.server.asm;

import java.awt.image.BufferedImage;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUIClientHooks;
import net.ilexiconn.nationsgui.forge.server.notifications.INotificationActionHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

final class NationsGUIClientHooks$2 implements INotificationActionHandler {

   public void handleAction(EntityPlayer entityPlayer, NBTTagCompound data) {
      NationsGUIClientHooks.uploadToForum((BufferedImage)NationsGUIClientHooks.screenMap.remove(data.func_74779_i("screenId")));
   }
}
