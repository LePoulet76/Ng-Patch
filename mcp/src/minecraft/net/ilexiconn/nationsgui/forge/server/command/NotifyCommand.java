/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.Player
 *  net.minecraft.command.CommandBase
 *  net.minecraft.command.ICommandSender
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.tileentity.TileEntityCommandBlock
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.ChatMessageComponent
 */
package net.ilexiconn.nationsgui.forge.server.command;

import cpw.mods.fml.common.network.Player;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.notifications.NotificationManager;
import net.ilexiconn.nationsgui.forge.server.util.CommandUtils;
import net.ilexiconn.nationsgui.forge.server.util.Translation;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatMessageComponent;

public class NotifyCommand
extends CommandBase {
    public String func_71517_b() {
        return "notify";
    }

    public String func_71518_a(ICommandSender icommandsender) {
        return null;
    }

    public void func_71515_b(ICommandSender icommandsender, String[] astring) {
        if (astring.length == 1) {
            if (astring[0].equalsIgnoreCase("colors")) {
                icommandsender.func_70006_a(ChatMessageComponent.func_111066_d((String)Translation.get("\u00a7eCouleur disponibles:")));
                for (NotificationManager.NColor color : NotificationManager.NColor.values()) {
                    icommandsender.func_70006_a(ChatMessageComponent.func_111066_d((String)("\u00a77 - " + color.name())));
                }
            } else if (astring[0].equalsIgnoreCase("icons")) {
                icommandsender.func_70006_a(ChatMessageComponent.func_111066_d((String)Translation.get("\u00a7eIcones disponibles:")));
                for (NotificationManager.NIcon icon : NotificationManager.NIcon.values()) {
                    icommandsender.func_70006_a(ChatMessageComponent.func_111066_d((String)("\u00a77 - " + icon.name())));
                }
            }
            return;
        }
        if (astring.length >= 4) {
            StringBuilder content = new StringBuilder();
            for (int i = 4; i < astring.length; ++i) {
                content.append(astring[i]);
                content.append(" ");
            }
            NotificationManager.NColor color = this.getColorFromString(astring[1]);
            NotificationManager.NIcon icon = this.getIconFromString(astring[2]);
            String title = astring[3].replaceAll("_", " ");
            if (color != null && icon != null) {
                if (astring.length >= 10) {
                    String okMessage = astring[4].replaceAll("_", " ");
                    String okIdAction = astring[5];
                    String okargsAction = astring[6].replaceAll("_", " ");
                    String denyMessage = astring[7].replaceAll("_", " ");
                    String denyIdAction = astring[8];
                    String denyargsAction = astring[9].replaceAll("_", " ");
                    content = new StringBuilder();
                    for (int i = 10; i < astring.length; ++i) {
                        content.append(astring[i]);
                        content.append(" ");
                    }
                    if (astring[0].startsWith("@")) {
                        int radius;
                        if (astring[0].startsWith("@a")) {
                            if (NationsGUI.isRadiusSet(astring[0])) {
                                radius = NationsGUI.getRadius(astring[0]);
                                if (icommandsender instanceof TileEntityCommandBlock) {
                                    TileEntityCommandBlock tile = (TileEntityCommandBlock)icommandsender;
                                    for (Object o : tile.field_70331_k.func_72872_a(EntityPlayer.class, AxisAlignedBB.func_72330_a((double)(tile.field_70329_l - radius), (double)(tile.field_70330_m - radius), (double)(tile.field_70327_n - radius), (double)(tile.field_70329_l + radius), (double)(tile.field_70330_m + radius), (double)(tile.field_70327_n + radius)))) {
                                        this.applyNotificationAction(icommandsender, ((EntityPlayer)o).field_71092_bJ, title, content, color, icon, okIdAction, okMessage, denyIdAction, denyMessage, denyargsAction, okargsAction);
                                    }
                                } else if (icommandsender instanceof EntityPlayer) {
                                    EntityPlayer player = (EntityPlayer)icommandsender;
                                    for (Object o : ((EntityPlayer)icommandsender).field_70170_p.func_72872_a(EntityPlayer.class, AxisAlignedBB.func_72330_a((double)(player.field_70165_t - (double)radius), (double)(player.field_70163_u - (double)radius), (double)(player.field_70161_v - (double)radius), (double)(player.field_70165_t + (double)radius), (double)(player.field_70163_u + (double)radius), (double)(player.field_70161_v + (double)radius)))) {
                                        this.applyNotificationAction(icommandsender, ((EntityPlayer)o).field_71092_bJ, title, content, color, icon, okIdAction, okMessage, denyIdAction, denyMessage, denyargsAction, okargsAction);
                                    }
                                }
                            } else {
                                for (EntityPlayer player : CommandUtils.getAllPlayers()) {
                                    this.applyNotificationAction(icommandsender, player.field_71092_bJ, title, content, color, icon, okIdAction, okMessage, denyIdAction, denyMessage, denyargsAction, okargsAction);
                                }
                            }
                        } else if (astring[0].startsWith("@p")) {
                            radius = 5;
                            if (icommandsender instanceof TileEntityCommandBlock) {
                                TileEntityCommandBlock tile = (TileEntityCommandBlock)icommandsender;
                                int i = 0;
                                for (Object o : tile.field_70331_k.func_72872_a(EntityPlayer.class, AxisAlignedBB.func_72330_a((double)(tile.field_70329_l - radius), (double)(tile.field_70330_m - radius), (double)(tile.field_70327_n - radius), (double)(tile.field_70329_l + radius), (double)(tile.field_70330_m + radius), (double)(tile.field_70327_n + radius)))) {
                                    if (i > 0) continue;
                                    this.applyNotificationAction(icommandsender, ((EntityPlayer)o).field_71092_bJ, title, content, color, icon, okIdAction, okMessage, denyIdAction, denyMessage, denyargsAction, okargsAction);
                                    ++i;
                                }
                            } else if (icommandsender instanceof EntityPlayer) {
                                EntityPlayer player = (EntityPlayer)icommandsender;
                                int i = 0;
                                for (Object o : ((EntityPlayer)icommandsender).field_70170_p.func_72872_a(EntityPlayer.class, AxisAlignedBB.func_72330_a((double)(player.field_70165_t - (double)radius), (double)(player.field_70163_u - (double)radius), (double)(player.field_70161_v - (double)radius), (double)(player.field_70165_t + (double)radius), (double)(player.field_70163_u + (double)radius), (double)(player.field_70161_v + (double)radius)))) {
                                    if (i > 0) continue;
                                    this.applyNotificationAction(icommandsender, ((EntityPlayer)o).field_71092_bJ, title, content, color, icon, okIdAction, okMessage, denyIdAction, denyMessage, denyargsAction, okargsAction);
                                    ++i;
                                }
                            }
                        }
                    } else if (CommandUtils.isPlayerOnline(astring[0])) {
                        this.applyNotificationAction(icommandsender, astring[0], title, content, color, icon, okIdAction, okMessage, denyIdAction, denyMessage, denyargsAction, okargsAction);
                    } else {
                        icommandsender.func_70006_a(ChatMessageComponent.func_111066_d((String)Translation.get("\u00a7cLe joueur n'est pas connect\u00e9")));
                    }
                } else if (astring[0].startsWith("@")) {
                    if (astring[0].startsWith("@a")) {
                        if (NationsGUI.isRadiusSet(astring[0])) {
                            int radius = NationsGUI.getRadius(astring[0]);
                            if (icommandsender instanceof TileEntityCommandBlock) {
                                TileEntityCommandBlock tile = (TileEntityCommandBlock)icommandsender;
                                for (Object o : tile.field_70331_k.func_72872_a(EntityPlayer.class, AxisAlignedBB.func_72330_a((double)(tile.field_70329_l - radius), (double)(tile.field_70330_m - radius), (double)(tile.field_70327_n - radius), (double)(tile.field_70329_l + radius), (double)(tile.field_70330_m + radius), (double)(tile.field_70327_n + radius)))) {
                                    this.applyNotificationSimple(icommandsender, ((EntityPlayer)o).field_71092_bJ, title, content, color, icon);
                                }
                            } else if (icommandsender instanceof EntityPlayer) {
                                EntityPlayer player = (EntityPlayer)icommandsender;
                                for (Object o : ((EntityPlayer)icommandsender).field_70170_p.func_72872_a(EntityPlayer.class, AxisAlignedBB.func_72330_a((double)(player.field_70165_t - (double)radius), (double)(player.field_70163_u - (double)radius), (double)(player.field_70161_v - (double)radius), (double)(player.field_70165_t + (double)radius), (double)(player.field_70163_u + (double)radius), (double)(player.field_70161_v + (double)radius)))) {
                                    this.applyNotificationSimple(icommandsender, ((EntityPlayer)o).field_71092_bJ, title, content, color, icon);
                                }
                            }
                        } else {
                            for (EntityPlayer player : CommandUtils.getAllPlayers()) {
                                this.applyNotificationSimple(icommandsender, player.field_71092_bJ, title, content, color, icon);
                            }
                        }
                    } else if (astring[0].startsWith("@p")) {
                        int radius = 5;
                        if (icommandsender instanceof TileEntityCommandBlock) {
                            TileEntityCommandBlock tile = (TileEntityCommandBlock)icommandsender;
                            int i = 0;
                            for (Object o : tile.field_70331_k.func_72872_a(EntityPlayer.class, AxisAlignedBB.func_72330_a((double)(tile.field_70329_l - radius), (double)(tile.field_70330_m - radius), (double)(tile.field_70327_n - radius), (double)(tile.field_70329_l + radius), (double)(tile.field_70330_m + radius), (double)(tile.field_70327_n + radius)))) {
                                if (i > 0) continue;
                                this.applyNotificationSimple(icommandsender, ((EntityPlayer)o).field_71092_bJ, title, content, color, icon);
                                ++i;
                            }
                        } else if (icommandsender instanceof EntityPlayer) {
                            EntityPlayer player = (EntityPlayer)icommandsender;
                            int i = 0;
                            for (Object o : ((EntityPlayer)icommandsender).field_70170_p.func_72872_a(EntityPlayer.class, AxisAlignedBB.func_72330_a((double)(player.field_70165_t - (double)radius), (double)(player.field_70163_u - (double)radius), (double)(player.field_70161_v - (double)radius), (double)(player.field_70165_t + (double)radius), (double)(player.field_70163_u + (double)radius), (double)(player.field_70161_v + (double)radius)))) {
                                if (i > 0) continue;
                                this.applyNotificationSimple(icommandsender, ((EntityPlayer)o).field_71092_bJ, title, content, color, icon);
                                ++i;
                            }
                        }
                    }
                } else if (CommandUtils.isPlayerOnline(astring[0])) {
                    this.applyNotificationSimple(icommandsender, astring[0], title, content, color, icon);
                } else {
                    icommandsender.func_70006_a(ChatMessageComponent.func_111066_d((String)Translation.get("\u00a7cLe joueur n'est pas connect\u00e9")));
                }
                icommandsender.func_70006_a(ChatMessageComponent.func_111066_d((String)Translation.get("\u00a7aNotification envoy\u00e9e")));
                return;
            }
        }
        icommandsender.func_70006_a(ChatMessageComponent.func_111066_d((String)"\u00a7cUsage: /notify <player> <color> <icon> <title> <titleActionYes?> <idActionYes?> <argsActionYes?> <titleActionNo?> <idActionNo?> <argsActionNo?> <content>"));
    }

    private NotificationManager.NColor getColorFromString(String arg) {
        try {
            return NotificationManager.NColor.valueOf(arg.toUpperCase());
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }

    private NotificationManager.NIcon getIconFromString(String arg) {
        try {
            return NotificationManager.NIcon.valueOf(arg.toUpperCase());
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }

    private void applyNotificationSimple(ICommandSender icommandsender, String target, String title, StringBuilder content, NotificationManager.NColor color, NotificationManager.NIcon icon) {
        NotificationManager.sendNotificationToPlayer((Player)NotifyCommand.func_82359_c((ICommandSender)icommandsender, (String)target), title, content.toString(), color, icon, 10000L);
    }

    private void applyNotificationAction(ICommandSender icommandsender, String target, String title, StringBuilder content, NotificationManager.NColor color, NotificationManager.NIcon icon, String okIdAction, String okMessage, String denyIdAction, String denyMessage, String denyargsAction, String okargsAction) {
        if (okIdAction.equals("link") && !okargsAction.contains("https://static.nationsglory.fr") && !okargsAction.contains("https://glor.cc")) {
            icommandsender.func_70006_a(ChatMessageComponent.func_111066_d((String)Translation.get("\u00a7cMerci d'utiliser uniquement des liens provenant du panel admin")));
            return;
        }
        if (denyIdAction.equals("link") && !denyargsAction.contains("https://static.nationsglory.fr") && !denyargsAction.contains("https://glor.cc")) {
            icommandsender.func_70006_a(ChatMessageComponent.func_111066_d((String)Translation.get("\u00a7cMerci d'utiliser uniquement des liens provenant du panel admin")));
            return;
        }
        NotificationManager.sendNotificationToPlayer((Player)NotifyCommand.func_82359_c((ICommandSender)icommandsender, (String)target), title, content.toString(), color, icon, 10000L, okIdAction, okMessage, denyIdAction, denyMessage, denyargsAction, okargsAction);
    }

    public int compareTo(Object o) {
        return 0;
    }
}

