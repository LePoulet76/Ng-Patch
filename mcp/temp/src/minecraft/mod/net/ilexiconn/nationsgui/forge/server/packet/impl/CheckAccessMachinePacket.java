package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nationsglory.client.gui.RandomGUI;
import fr.nationsglory.client.gui.TraderGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class CheckAccessMachinePacket implements IPacket, IClientPacket {

   private String enterpriseName;
   private boolean accessGranted;


   public CheckAccessMachinePacket(String enterpriseName) {
      this.enterpriseName = enterpriseName;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.accessGranted = data.readBoolean();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.enterpriseName);
   }

   @SideOnly(Side.CLIENT)
   public void handleClientPacket(EntityPlayer player) {
      if(this.accessGranted) {
         RandomGUI.loaded = true;
         TraderGUI.loaded = true;
      } else {
         Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
      }

   }
}
