package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nationsglory.client.gui.PumpjackGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class PumpjackInfosMachinePacket implements IPacket, IClientPacket {

   private int posX;
   private int posZ;
   private String zone;
   private Double petrolPercent;
   private boolean canOpen;


   public PumpjackInfosMachinePacket(int posX, int posZ) {
      this.posX = posX;
      this.posZ = posZ;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.zone = data.readUTF();
      this.petrolPercent = Double.valueOf(data.readDouble());
      this.canOpen = data.readBoolean();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeInt(this.posX);
      data.writeInt(this.posZ);
   }

   @SideOnly(Side.CLIENT)
   public void handleClientPacket(EntityPlayer player) {
      if(this.canOpen) {
         PumpjackGUI.zoneName = this.zone;
         PumpjackGUI.petrolPercent = this.petrolPercent.doubleValue();
         PumpjackGUI.loaded = true;
      } else {
         Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
      }

   }
}
