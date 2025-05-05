package net.ilexiconn.nationsgui.forge.server.command;

import joptsimple.internal.Strings;
import net.ilexiconn.nationsgui.forge.server.ServerProxy;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.network.packet.Packet201PlayerInfo;
import net.minecraft.server.MinecraftServer;

public class CommandDisconnectPhone extends CommandBase {

   public String func_71517_b() {
      return "disconnectphone";
   }

   public String func_71518_a(ICommandSender icommandsender) {
      return null;
   }

   public void func_71515_b(ICommandSender icommandsender, String[] astring) {
      String name = Strings.join(astring, " ");
      ServerProxy.mobilePlayers.remove(name);
      MinecraftServer.func_71196_a(MinecraftServer.func_71276_C()).func_72384_a(new Packet201PlayerInfo(name, false, -42));
   }

   public int compareTo(Object o) {
      return 0;
   }
}
