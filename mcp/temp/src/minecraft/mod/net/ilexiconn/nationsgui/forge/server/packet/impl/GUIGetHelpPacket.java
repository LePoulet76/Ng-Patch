package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.SliderHelpGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class GUIGetHelpPacket implements IPacket, IClientPacket {

   private String GUIClass;
   private String GUIHelpDialog;
   private boolean forceOpenGUI;


   public GUIGetHelpPacket(String GUIClass) {
      this.GUIClass = GUIClass;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.GUIClass = data.readUTF();
      this.GUIHelpDialog = data.readUTF();
      this.forceOpenGUI = data.readBoolean();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.GUIClass);
   }

   @SideOnly(Side.CLIENT)
   public void handleClientPacket(EntityPlayer player) {
      ClientData.GUIWithHelp.put(this.GUIClass, this.GUIHelpDialog);
      if(this.forceOpenGUI && !ClientProxy.clientConfig.openedHelp.contains(this.GUIHelpDialog)) {
         Minecraft.func_71410_x().func_71373_a(new SliderHelpGui(this.GUIHelpDialog, Minecraft.func_71410_x().field_71462_r));
         ClientProxy.clientConfig.openedHelp.add(this.GUIHelpDialog);

         try {
            ClientProxy.saveConfig();
         } catch (Exception var3) {
            var3.printStackTrace();
         }
      }

   }
}
