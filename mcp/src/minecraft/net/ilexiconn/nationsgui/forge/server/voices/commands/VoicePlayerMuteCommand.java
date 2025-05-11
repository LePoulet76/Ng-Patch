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
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.util.ChatMessageComponent
 */
package net.ilexiconn.nationsgui.forge.server.voices.commands;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.VoiceMutePlayerPacket;
import net.ilexiconn.nationsgui.forge.server.util.Translation;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;

public class VoicePlayerMuteCommand
extends CommandBase {
    public String func_71517_b() {
        return "voc";
    }

    public String func_71518_a(ICommandSender icommandsender) {
        return "/voc <username>";
    }

    public void func_71515_b(ICommandSender sender, String[] args) {
        if (args.length == 1 && sender instanceof EntityPlayerMP) {
            if (!MinecraftServer.func_71276_C().func_71203_ab().func_72353_e(args[0])) {
                if (VoicePlayerMuteCommand.func_82359_c((ICommandSender)sender, (String)args[0]) != null) {
                    PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new VoiceMutePlayerPacket(VoicePlayerMuteCommand.func_82359_c((ICommandSender)sender, (String)args[0]).field_70157_k, args[0])), (Player)((Player)sender));
                } else {
                    PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new VoiceMutePlayerPacket(-1, args[0])), (Player)((Player)sender));
                }
            } else {
                sender.func_70006_a(ChatMessageComponent.func_111066_d((String)Translation.get("\u00a7cLe joueur que vous tentez de mute est op\u00e9rateur.")));
            }
        } else {
            sender.func_70006_a(ChatMessageComponent.func_111066_d((String)this.func_71518_a(sender)));
        }
    }

    public int compareTo(Object arg0) {
        return 0;
    }

    public int func_82362_a() {
        return 0;
    }
}

