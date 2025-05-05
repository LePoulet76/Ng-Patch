package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nationsglory.client.gui.SiloGUI;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SiloInfosMachinePacket$1;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class SiloInfosMachinePacket implements IPacket, IClientPacket {

   private HashMap<String, Double> cerealsPrice;
   private boolean isInFarmingEnterprise;
   private String farmingEnterpriseFlag;
   private boolean canOpen;
   private int posX;
   private int posY;
   private int posZ;


   public SiloInfosMachinePacket(int posX, int posY, int posZ) {
      this.posX = posX;
      this.posY = posY;
      this.posZ = posZ;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.cerealsPrice = (HashMap)(new Gson()).fromJson(data.readUTF(), (new SiloInfosMachinePacket$1(this)).getType());
      this.isInFarmingEnterprise = data.readBoolean();
      this.farmingEnterpriseFlag = data.readUTF();
      this.canOpen = data.readBoolean();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeInt(this.posX);
      data.writeInt(this.posY);
      data.writeInt(this.posZ);
   }

   @SideOnly(Side.CLIENT)
   public void handleClientPacket(EntityPlayer player) {
      if(this.canOpen) {
         SiloGUI.cerealsPrice = this.cerealsPrice;
         SiloGUI.isInFarmingEnterprise = this.isInFarmingEnterprise;
         SiloGUI.farmingEnterpriseFlag = this.farmingEnterpriseFlag;
         SiloGUI.loaded = true;
      } else {
         Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
      }

   }
}
