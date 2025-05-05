package net.ilexiconn.nationsgui.forge.server.command;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.JoinWaitingPacket;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;

public class JoinWaitingCommand extends CommandBase {

   public String func_71517_b() {
      return "joinwaiting";
   }

   public String func_71518_a(ICommandSender icommandsender) {
      return "/joinwaiting <Server> <IP> <Port>";
   }

   public void func_71515_b(ICommandSender icommandsender, String[] astring) {
      if(astring.length == 3) {
         EntityPlayerMP target = func_82359_c(icommandsender, icommandsender.func_70005_c_());
         PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new JoinWaitingPacket(astring[0], astring[1], Integer.parseInt(astring[2]))), (Player)target);
      }

   }

   public int compareTo(Object o) {
      return 0;
   }

   public boolean func_82358_a(String[] par1ArrayOfStr, int par2) {
      return par2 == 0;
   }
}
