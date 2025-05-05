package net.ilexiconn.nationsgui.forge.server.command;

import java.io.IOException;
import net.ilexiconn.nationsgui.forge.server.util.CommandUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.SaveHandler;

public class POCommand extends CommandBase {

   public String func_71517_b() {
      return "po";
   }

   public String func_71518_a(ICommandSender sender) {
      return "/po <parachute> <player>";
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      if(args.length != 2) {
         throw new CommandException(this.func_71518_a(sender), new Object[0]);
      } else {
         boolean parachute = CommandBase.func_110662_c(sender, args[0]);
         String username = args[1];
         if(!(sender.func_130014_f_().func_72860_G() instanceof SaveHandler)) {
            throw new CommandException("Using deprecated save system", new Object[0]);
         } else {
            SaveHandler saveHandler = (SaveHandler)sender.func_130014_f_().func_72860_G();
            NBTTagCompound playerData = saveHandler.func_75764_a(username);
            if(playerData == null) {
               throw new CommandException("Player not found", new Object[0]);
            } else {
               playerData.func_74757_a("usingParachute", parachute);

               try {
                  CommandUtils.writePlayerData(saveHandler, username, playerData);
               } catch (IOException var8) {
                  throw new CommandException("Unable to write player data", new Object[0]);
               }
            }
         }
      }
   }

   public int compareTo(Object o) {
      return 0;
   }
}
