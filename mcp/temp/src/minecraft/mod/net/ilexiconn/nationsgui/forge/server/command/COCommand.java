package net.ilexiconn.nationsgui.forge.server.command;

import java.io.IOException;
import java.util.Arrays;
import net.ilexiconn.nationsgui.forge.server.util.CommandUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.SaveHandler;

public class COCommand extends CommandBase {

   public String func_71517_b() {
      return "co";
   }

   public String func_71518_a(ICommandSender sender) {
      return "/co <player>";
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      if(args.length != 1) {
         throw new CommandException(this.func_71518_a(sender), new Object[0]);
      } else {
         String username = args[0];
         boolean totalCleared = false;
         StringBuilder logBuilder = (new StringBuilder()).append("Clearing inventory of player: ").append(username);
         int var12;
         if(Arrays.asList(MinecraftServer.func_71276_C().func_71213_z()).contains(username)) {
            EntityPlayerMP saveHandler = CommandBase.func_82359_c(sender, username);
            ItemStack[] playerData = saveHandler.field_71071_by.field_70462_a;
            int e = playerData.length;

            int i;
            for(i = 0; i < e; ++i) {
               ItemStack item = playerData[i];
               if(item != null) {
                  logBuilder.append("\n").append("(x").append(item.field_77994_a).append(")").append(item.field_77993_c).append(":").append(item.func_77960_j());
               }
            }

            var12 = saveHandler.field_71071_by.func_82347_b(-1, -1);
            if(saveHandler.field_71069_bz instanceof ContainerPlayer) {
               ContainerPlayer var14 = (ContainerPlayer)saveHandler.field_71069_bz;
               InventoryCrafting var16 = var14.field_75181_e;

               for(i = 0; i < var16.func_70302_i_(); ++i) {
                  if(var16.func_70301_a(i) != null) {
                     ++var12;
                  }

                  var16.func_70299_a(i, (ItemStack)null);
               }

               if(var14.field_75179_f.func_70301_a(0) != null) {
                  ++var12;
                  var14.field_75179_f.func_70299_a(0, (ItemStack)null);
               }
            }

            saveHandler.field_71069_bz.func_75142_b();
            if(!saveHandler.field_71075_bZ.field_75098_d) {
               saveHandler.func_71113_k();
            }
         } else {
            if(!(sender.func_130014_f_().func_72860_G() instanceof SaveHandler)) {
               throw new CommandException("Using deprecated save system", new Object[0]);
            }

            SaveHandler var13 = (SaveHandler)sender.func_130014_f_().func_72860_G();
            NBTTagCompound var15 = var13.func_75764_a(username);
            if(var15 == null) {
               throw new CommandException("Player not found", new Object[0]);
            }

            var15.func_82580_o("Inventory");
            var15.func_74782_a("Inventory", new NBTTagList("Inventory"));
            var12 = -1;

            try {
               CommandUtils.writePlayerData(var13, username, var15);
            } catch (IOException var11) {
               throw new CommandException("Unable to write player data", new Object[0]);
            }
         }

         MinecraftServer.func_71276_C().func_71244_g(logBuilder.toString());
         CommandBase.func_71522_a(sender, "commands.clear.success", new Object[]{username, Integer.valueOf(var12)});
      }
   }

   public int compareTo(Object o) {
      return this.func_71525_a((ICommand)o);
   }
}
