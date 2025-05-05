package net.ilexiconn.nationsgui.forge.server.command;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PingPacket;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class PingCommand extends CommandBase {

   public String func_71517_b() {
      return "nping";
   }

   public String func_71518_a(ICommandSender icommandsender) {
      return null;
   }

   public void func_71515_b(ICommandSender icommandsender, String[] astring) {
      PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new PingPacket(icommandsender.func_70005_c_())), (Player)func_82359_c(icommandsender, astring[0]));
   }

   public int compareTo(Object o) {
      return 0;
   }
}
