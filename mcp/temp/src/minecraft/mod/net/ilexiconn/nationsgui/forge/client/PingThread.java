package net.ilexiconn.nationsgui.forge.client;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import net.ilexiconn.nationsgui.forge.client.Ping;
import net.minecraft.client.Minecraft;

public class PingThread extends Thread {

   public static int ping = 0;


   public void run() {
      try {
         InetSocketAddress e = (InetSocketAddress)Minecraft.func_71410_x().field_71439_g.field_71174_a.func_72548_f().func_74430_c();
         InetAddress inetAddress = e.getAddress();
         Ping p = new Ping(inetAddress.getHostAddress());
         ping = (int)p.run();
         Thread.sleep(5000L);
      } catch (InterruptedException var4) {
         var4.printStackTrace();
      }

   }

}
