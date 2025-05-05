package net.ilexiconn.nationsgui.forge.server.asm;

import net.ilexiconn.nationsgui.forge.server.asm.NationsGUIClientHooks;
import net.ilexiconn.nationsgui.forge.server.notifications.INotificationActionHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

final class NationsGUIClientHooks$3 implements INotificationActionHandler {

   public void handleAction(EntityPlayer entityPlayer, NBTTagCompound data) {
      NationsGUIClientHooks.screenMap.remove(data.func_74779_i("screenId"));
   }
}
