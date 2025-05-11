/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package net.ilexiconn.nationsgui.forge.client;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import net.ilexiconn.nationsgui.forge.client.Ping;
import net.minecraft.client.Minecraft;

public class PingThread
extends Thread {
    public static int ping = 0;

    @Override
    public void run() {
        try {
            InetSocketAddress address = (InetSocketAddress)Minecraft.func_71410_x().field_71439_g.field_71174_a.func_72548_f().func_74430_c();
            InetAddress inetAddress = address.getAddress();
            Ping p = new Ping(inetAddress.getHostAddress());
            ping = (int)p.run();
            Thread.sleep(5000L);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

