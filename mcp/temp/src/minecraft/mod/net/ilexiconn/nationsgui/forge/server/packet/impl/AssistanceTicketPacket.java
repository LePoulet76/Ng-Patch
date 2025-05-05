package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AssistanceTicketButton;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AssistanceTicketGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

public class AssistanceTicketPacket implements IPacket, IClientPacket {

   private NBTTagCompound tagCompound = null;
   private int id;


   public AssistanceTicketPacket(int id) {
      this.id = id;
   }

   public AssistanceTicketPacket() {}

   @SideOnly(Side.CLIENT)
   public void handleClientPacket(EntityPlayer player) {
      AssistanceTicketButton.locked = false;
      Minecraft.func_71410_x().func_71373_a(new AssistanceTicketGUI(this.tagCompound));
   }

   public void fromBytes(ByteArrayDataInput data) {
      try {
         this.tagCompound = CompressedStreamTools.func_74794_a(data);
      } catch (IOException var3) {
         var3.printStackTrace();
      }

   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeInt(this.id);
   }
}
