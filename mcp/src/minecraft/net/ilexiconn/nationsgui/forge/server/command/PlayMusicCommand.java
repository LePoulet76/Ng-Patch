/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  net.minecraft.command.CommandBase
 *  net.minecraft.command.ICommandSender
 *  net.minecraft.network.packet.Packet
 */
package net.ilexiconn.nationsgui.forge.server.command;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.util.List;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.MusicPacket;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.network.packet.Packet;

public class PlayMusicCommand
extends CommandBase {
    public String func_71517_b() {
        return "playmusic";
    }

    public String func_71518_a(ICommandSender icommandsender) {
        return "/playmusic <player> <filename>";
    }

    public void func_71515_b(ICommandSender icommandsender, String[] astring) {
        MusicPacket musicPacket = new MusicPacket();
        musicPacket.filename = astring[1];
        PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(musicPacket), (Player)((Player)PlayMusicCommand.func_82359_c((ICommandSender)icommandsender, (String)astring[0])));
    }

    public int compareTo(Object o) {
        return 0;
    }

    public List func_71516_a(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return null;
    }

    public boolean func_82358_a(String[] par1ArrayOfStr, int par2) {
        return par2 == 0;
    }
}

