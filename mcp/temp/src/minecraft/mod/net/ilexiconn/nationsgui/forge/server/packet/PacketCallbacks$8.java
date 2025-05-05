package net.ilexiconn.nationsgui.forge.server.packet;

import com.google.common.io.ByteArrayDataInput;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.SnackbarGUI;
import net.ilexiconn.nationsgui.forge.client.gui.radio.RequestGUI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;

enum PacketCallbacks$8 {

   PacketCallbacks$8(String var1, int var2) {}

   public void handleCallback(EntityPlayer player, ByteArrayDataInput data) {
      boolean success = data.readBoolean();
      String message = data.readUTF();
      RequestGUI.handleReturn(success);
      ClientProxy.SNACKBAR_LIST.add(new SnackbarGUI(StatCollector.func_74838_a("nationsgui.radio." + message)));
   }
}
