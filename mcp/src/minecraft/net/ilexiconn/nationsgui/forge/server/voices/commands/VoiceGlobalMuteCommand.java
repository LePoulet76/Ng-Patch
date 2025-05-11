/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.command.CommandBase
 *  net.minecraft.command.CommandException
 *  net.minecraft.command.ICommandSender
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.util.ChatMessageComponent
 */
package net.ilexiconn.nationsgui.forge.server.voices.commands;

import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.server.command.utils.DateUtil;
import net.ilexiconn.nationsgui.forge.server.util.Translation;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceChat;
import net.ilexiconn.nationsgui.forge.server.voices.networking.DataManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;

public class VoiceGlobalMuteCommand
extends CommandBase {
    public int func_82362_a() {
        return 2;
    }

    public List func_71516_a(ICommandSender par1iCommandSender, String[] par2ArrayOfStr) {
        return par2ArrayOfStr.length == 1 ? VoiceGlobalMuteCommand.func_71530_a((String[])par2ArrayOfStr, (String[])MinecraftServer.func_71276_C().func_71213_z()) : null;
    }

    public boolean func_82358_a(String[] par1ArrayOfStr, int par2) {
        return par2 == 0;
    }

    public String func_71517_b() {
        return "vmute";
    }

    public String func_71518_a(ICommandSender icommandsender) {
        return "Usage: /vmute <player> [datediff]";
    }

    public void func_71515_b(ICommandSender sender, String[] args) {
        if (args.length > 0 && args.length < 3 && args[0].length() > 0) {
            EntityPlayerMP player = VoiceGlobalMuteCommand.func_82359_c((ICommandSender)sender, (String)args[0]);
            long time = Long.MAX_VALUE;
            if (args.length == 2) {
                try {
                    time = DateUtil.parseDateDiff(args[1], true);
                }
                catch (Exception e) {
                    sender.func_70006_a(ChatMessageComponent.func_111066_d((String)this.func_71518_a(sender)));
                    return;
                }
            }
            if (player != null) {
                DataManager dataManager = VoiceChat.getServerInstance().getServerNetwork().getDataManager();
                Map<String, Long> mutedPlayers = dataManager.mutedPlayers;
                if (mutedPlayers.containsKey(player.field_71092_bJ)) {
                    mutedPlayers.remove(player.field_71092_bJ);
                    sender.func_70006_a(ChatMessageComponent.func_111066_d((String)Translation.get("\u00a7aLe joueur \u00a72<player> \u00a7apeut de nouveau utiliser le chat vocal.").replaceAll("<player>", player.getDisplayName())));
                    player.func_71035_c(Translation.get("\u00a7aVous pouvez de nouveau utiliser le chat vocal."));
                } else {
                    mutedPlayers.put(player.field_71092_bJ, time);
                    if (time == Long.MAX_VALUE) {
                        player.func_71035_c(Translation.get("\u00a7cVous avez \u00e9t\u00e9 mute du chat vocal, vous ne pouvez plus l'utiliser."));
                        sender.func_70006_a(ChatMessageComponent.func_111066_d((String)Translation.get("\u00a7cLe joueur \u00a74<player> \u00a7cne peut plus parler en vocal.").replaceAll("<player>", player.getDisplayName())));
                    } else {
                        String s = DateUtil.formatDateDiff(time);
                        player.func_71035_c(Translation.get("\u00a7cVous avez \u00e9t\u00e9 mute du chat vocal, vous ne pouvez plus l'utiliser pendant \u00a74<time>").replaceAll("<time>", s));
                        sender.func_70006_a(ChatMessageComponent.func_111066_d((String)Translation.get("\u00a7cLe joueur \u00a74<player> \u00a7cne peut plus parler en vocal pendant \u00a74<time>").replaceAll("<player>", player.getDisplayName()).replaceAll("<time>", s)));
                    }
                }
                dataManager.save();
            } else {
                player.func_71035_c(Translation.get("\u00a7cLe joueur est introuvable"));
            }
        } else {
            throw new CommandException(this.func_71518_a(sender), new Object[0]);
        }
    }

    public int compareTo(Object o) {
        return 0;
    }
}

