package net.ilexiconn.nationsgui.forge.server.command;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.util.List;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SnackbarPacket;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import org.apache.commons.lang3.ArrayUtils;

public class SnackbarCommand extends CommandBase {

   public String func_71517_b() {
      return "snackbar";
   }

   public String func_71518_a(ICommandSender sender) {
      return "/snackbar <username/all> <message>";
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      if(args.length >= 2) {
         if(args[0].equals("all")) {
            PacketDispatcher.sendPacketToAllPlayers(PacketRegistry.INSTANCE.generatePacket(new SnackbarPacket(this.getMessage(args), new String[0])));
         } else {
            EntityPlayer player = sender.func_130014_f_().func_72924_a(args[0]);
            if(player == null) {
               throw new CommandException("That\'s not a valid username!", new Object[0]);
            }

            PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new SnackbarPacket(this.getMessage(args), new String[0])), (Player)player);
         }

      } else {
         throw new CommandException(this.func_71518_a(sender), new Object[0]);
      }
   }

   public String getMessage(String[] args) {
      String result = "";

      for(int i = 1; i < args.length; ++i) {
         result = result + args[i].replace('&', '\u00a7') + " ";
      }

      return result;
   }

   public int compareTo(Object o) {
      return this.func_71525_a((ICommand)o);
   }

   public List<String> func_71516_a(ICommandSender sender, String[] args) {
      return args.length == 1?CommandBase.func_71530_a(args, this.getAllUsernames()):null;
   }

   public String[] getAllUsernames() {
      String[] usernames = MinecraftServer.func_71276_C().func_71213_z();
      usernames = (String[])((String[])ArrayUtils.add(usernames, 0, "all"));
      return usernames;
   }
}
