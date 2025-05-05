package net.ilexiconn.nationsgui.forge.server.command;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import fr.nationsglory.itemmanager.CommonProxy;
import java.util.List;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FirstConnectionPacket;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;

public class WelcomeCommand extends CommandBase {

   public String func_71517_b() {
      return "welcome";
   }

   public String func_71518_a(ICommandSender icommandsender) {
      return "/welcome <pseudo>";
   }

   public void func_71515_b(ICommandSender icommandsender, String[] astring) {
      if(astring.length > 0) {
         FirstConnectionPacket firstConnectionPacket = new FirstConnectionPacket();
         firstConnectionPacket.serverName = CommonProxy.localConfig.getServerName();
         firstConnectionPacket.forceOpen = true;
         firstConnectionPacket.serverType = CommonProxy.localConfig.getServerType();
         PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(firstConnectionPacket), (Player)func_82359_c(icommandsender, astring[0]));
      } else {
         throw new WrongUsageException("/welcome <pseudo>", new Object[0]);
      }
   }

   public int compareTo(Object o) {
      return 0;
   }

   public int func_82362_a() {
      return 4;
   }

   public boolean func_82358_a(String[] par1ArrayOfStr, int par2) {
      return par2 == 0;
   }

   public List func_71516_a(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
      return par2ArrayOfStr.length == 1?func_71530_a(par2ArrayOfStr, MinecraftServer.func_71276_C().func_71213_z()):null;
   }
}
