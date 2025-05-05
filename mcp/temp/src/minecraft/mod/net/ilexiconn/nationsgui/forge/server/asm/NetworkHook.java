package net.ilexiconn.nationsgui.forge.server.asm;

import fr.nationsglory.itemmanager.CommonProxy;
import fr.nationsglory.itemmanager.ItemManager;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PlayerListTextPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SpawnTypePacket;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet2ClientProtocol;
import net.minecraft.server.MinecraftServer;

public class NetworkHook {

   public static void onProtocolPacket(Packet2ClientProtocol packet2ClientProtocol, NetHandler netHandler) {
      MinecraftServer minecraftServer = MinecraftServer.func_71276_C();
      NetLoginHandler netLoginHandler = (NetLoginHandler)netHandler;
      CommonProxy var10000 = ItemManager.proxy;
      if(CommonProxy.localConfig.isMultipleRespawn()) {
         netLoginHandler.field_72538_b.func_74429_a(PacketRegistry.INSTANCE.generatePacket(new SpawnTypePacket()));
      }

      PlayerListTextPacket playerListTextPacket = new PlayerListTextPacket();
      CommonProxy var10001 = ItemManager.proxy;
      playerListTextPacket.up = CommonProxy.localConfig.getPlayerListTopText();
      var10001 = ItemManager.proxy;
      playerListTextPacket.bottom = CommonProxy.localConfig.getPlayerListBottomText();
      netLoginHandler.field_72538_b.func_74429_a(PacketRegistry.INSTANCE.generatePacket(playerListTextPacket));
      netHandler.func_72500_a(packet2ClientProtocol);
   }
}
