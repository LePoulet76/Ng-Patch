package net.ilexiconn.nationsgui.forge.server.command;

import java.util.List;
import net.ilexiconn.nationsgui.forge.server.command.ScreenPlayerCommand$LinkRequester;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public class ScreenPlayerCommand extends CommandBase {

   public String func_71517_b() {
      return "screen";
   }

   public String func_71518_a(ICommandSender icommandsender) {
      return "\u00a7c/screen <player>";
   }

   public void func_71515_b(ICommandSender icommandsender, String[] args) {
      if(icommandsender instanceof EntityPlayer) {
         EntityPlayer sender = (EntityPlayer)icommandsender;
         EntityPlayer player = sender.func_130014_f_().func_72924_a(args[0]);
         if(args.length > 0 && player != null) {
            Thread t = new Thread(new ScreenPlayerCommand$LinkRequester(sender, player));
            t.setDaemon(true);
            t.start();
         }
      }

   }

   public List func_71516_a(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
      return par2ArrayOfStr.length == 1?func_71530_a(par2ArrayOfStr, MinecraftServer.func_71276_C().func_71213_z()):null;
   }

   public int compareTo(Object o) {
      return 0;
   }
}
