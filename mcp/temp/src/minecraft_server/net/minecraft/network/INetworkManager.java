package net.minecraft.network;

import java.net.SocketAddress;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;

public interface INetworkManager {

   void func_74425_a(NetHandler var1);

   void func_74429_a(Packet var1);

   void func_74427_a();

   void func_74428_b();

   SocketAddress func_74430_c();

   void func_74423_d();

   int func_74426_e();

   void func_74424_a(String var1, Object ... var2);
}
