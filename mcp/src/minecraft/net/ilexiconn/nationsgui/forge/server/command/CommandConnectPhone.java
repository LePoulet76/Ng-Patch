/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  joptsimple.internal.Strings
 *  net.minecraft.command.CommandBase
 *  net.minecraft.command.ICommandSender
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.network.packet.Packet201PlayerInfo
 *  net.minecraft.server.MinecraftServer
 */
package net.ilexiconn.nationsgui.forge.server.command;

import joptsimple.internal.Strings;
import net.ilexiconn.nationsgui.forge.server.ServerProxy;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet201PlayerInfo;
import net.minecraft.server.MinecraftServer;

public class CommandConnectPhone
extends CommandBase {
    public String func_71517_b() {
        return "connectphone";
    }

    public String func_71518_a(ICommandSender icommandsender) {
        return null;
    }

    public void func_71515_b(ICommandSender icommandsender, String[] astring) {
        String name = Strings.join((String[])astring, (String)" ");
        ServerProxy.mobilePlayers.add(name);
        MinecraftServer.func_71196_a((MinecraftServer)MinecraftServer.func_71276_C()).func_72384_a((Packet)new Packet201PlayerInfo(name, true, -42));
    }

    public int compareTo(Object o) {
        return 0;
    }
}

