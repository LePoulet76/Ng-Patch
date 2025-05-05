package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AbstractAssistanceGUI;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AssistancePlayerGUI;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AssistanceStaffGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class AssistanceListingPacket implements IPacket, IClientPacket {

   private NBTTagCompound tagCompound;
   public static NBTTagList ticketList = null;
   private boolean canBeAdmin = false;


   @SideOnly(Side.CLIENT)
   public void handleClientPacket(EntityPlayer player) {
      AbstractAssistanceGUI.canBeAdmin = this.canBeAdmin;
      if(this.tagCompound.func_74764_b("ranks")) {
         mc.func_71373_a(new AssistanceStaffGUI(this.tagCompound));
      } else {
         mc.func_71373_a(new AssistancePlayerGUI());
      }

   }

   public void fromBytes(ByteArrayDataInput data) {
      try {
         this.tagCompound = CompressedStreamTools.func_74794_a(data);
         ticketList = this.tagCompound.func_74761_m("TicketList");
         this.canBeAdmin = this.tagCompound.func_74767_n("canBeAdmin");
      } catch (IOException var3) {
         var3.printStackTrace();
      }

   }

   public void toBytes(ByteArrayDataOutput data) {}

}
