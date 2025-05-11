/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  net.minecraft.command.CommandBase
 *  net.minecraft.command.ICommandSender
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.network.packet.Packet
 */
package net.ilexiconn.nationsgui.forge.server.command;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.ChangeServerPacket;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet;

public class ChangeServerCommand
extends CommandBase {
    public String func_71517_b() {
        return "changeserver";
    }

    public String func_71518_a(ICommandSender icommandsender) {
        return "/changeserver <Player> <IP> <Port>";
    }

    public void func_71515_b(ICommandSender icommandsender, String[] astring) {
        if (astring.length == 3) {
            EntityPlayerMP target = ChangeServerCommand.func_82359_c((ICommandSender)icommandsender, (String)astring[0]);
            PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new ChangeServerPacket(astring[1], Integer.parseInt(astring[2]))), (Player)((Player)target));
        }
    }

    public int compareTo(Object o) {
        return 0;
    }

    public boolean func_82358_a(String[] par1ArrayOfStr, int par2) {
        return par2 == 0;
    }
}

