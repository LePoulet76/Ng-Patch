package net.ilexiconn.nationsgui.forge.client.gui.radio;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.client.gui.ToggleGUI$ISliderCallback;
import net.ilexiconn.nationsgui.forge.client.gui.radio.RadioModeratorGUI;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SetRedstonePacket;

class RadioModeratorGUI$1 implements ToggleGUI$ISliderCallback {

   // $FF: synthetic field
   final RadioModeratorGUI this$0;


   RadioModeratorGUI$1(RadioModeratorGUI this$0) {
      this.this$0 = this$0;
   }

   public void call(boolean active) {
      RadioModeratorGUI.access$000(this.this$0).getBlockEntity().needsRedstone = active;
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new SetRedstonePacket(RadioModeratorGUI.access$000(this.this$0).getBlockEntity())));
   }
}
