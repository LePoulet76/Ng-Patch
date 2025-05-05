package net.ilexiconn.nationsgui.forge.server.packet;

import com.google.common.io.ByteArrayDataInput;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.SnackbarGUI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;

enum PacketCallbacks$6 {

   PacketCallbacks$6(String var1, int var2) {}

   public void handleCallback(EntityPlayer player, ByteArrayDataInput data) {
      String message = String.format(StatCollector.func_74838_a("nationsgui.help.closed"), new Object[]{data.readUTF()});
      String extra = data.readUTF();
      if(!extra.isEmpty()) {
         message = message + ": " + extra;
      }

      ClientProxy.SNACKBAR_LIST.add(new SnackbarGUI(message, false));
   }
}
