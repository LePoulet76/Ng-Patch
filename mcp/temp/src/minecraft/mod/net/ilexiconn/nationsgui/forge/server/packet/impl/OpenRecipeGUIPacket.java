package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.client.gui.RecipeListGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.controllers.RecipeCarpentry;
import noppes.npcs.controllers.RecipeController;

public class OpenRecipeGUIPacket implements IPacket, IServerPacket, IClientPacket {

   @SideOnly(Side.CLIENT)
   public void handleClientPacket(EntityPlayer player) {
      Minecraft.func_71410_x().func_71373_a(new RecipeListGUI(Minecraft.func_71410_x().field_71462_r));
   }

   public void fromBytes(ByteArrayDataInput data) {}

   public void toBytes(ByteArrayDataOutput data) {}

   public void handleServerPacket(EntityPlayer player) {
      try {
         RecipeController e = RecipeController.instance;
         NBTTagList list = new NBTTagList();
         Iterator var6 = e.globalRecipes.values().iterator();

         while(var6.hasNext()) {
            RecipeCarpentry compound = (RecipeCarpentry)var6.next();
            list.func_74742_a(compound.writeNBT());
         }

         NBTTagCompound compound1 = new NBTTagCompound();
         compound1.func_74782_a("recipes", list);
         NoppesUtilServer.sendData(player, EnumPacketType.SyncRecipes, new Object[]{compound1});
      } catch (Exception var61) {
         var61.printStackTrace();
      }

      PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new OpenRecipeGUIPacket()), (Player)player);
   }
}
