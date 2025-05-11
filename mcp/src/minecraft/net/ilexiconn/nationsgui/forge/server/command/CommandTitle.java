/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  net.minecraft.command.CommandBase
 *  net.minecraft.command.ICommandSender
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.tileentity.TileEntityCommandBlock
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.ChatMessageComponent
 */
package net.ilexiconn.nationsgui.forge.server.command;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TitlePacket;
import net.ilexiconn.nationsgui.forge.server.util.CommandUtils;
import net.ilexiconn.nationsgui.forge.server.util.Title;
import net.ilexiconn.nationsgui.forge.server.util.Translation;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatMessageComponent;

public class CommandTitle
extends CommandBase {
    private String title = "";
    private String subtitle = "";
    private int time = 5;

    public String func_71517_b() {
        return "title";
    }

    public String func_71518_a(ICommandSender sender) {
        return "\u00a7cUsage: /title <@a[r=x]|@p|pseudo> [time] <title> $$$ <subtitle>";
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void func_71515_b(ICommandSender sender, String[] args) {
        if (args.length < 2) {
            this.send(sender, this.func_71518_a(sender));
            return;
        }
        if (args[0].startsWith("@")) {
            if (args[0].startsWith("@a")) {
                if (this.isRadiusSet(args[0])) {
                    if (this.isTime(args[1])) {
                        if (args.length < 2) {
                            this.send(sender, this.func_71518_a(sender));
                            return;
                        }
                        this.time = this.getTime(args[1]);
                        this.parseTitleAndSub(sender, args, 1);
                    } else {
                        this.parseTitleAndSub(sender, args, 0);
                    }
                    int radius = this.getRadius(args[0]);
                    if (sender instanceof TileEntityCommandBlock) {
                        TileEntityCommandBlock tile = (TileEntityCommandBlock)sender;
                        for (Object o : tile.field_70331_k.func_72872_a(EntityPlayer.class, AxisAlignedBB.func_72330_a((double)(tile.field_70329_l - radius), (double)(tile.field_70330_m - radius), (double)(tile.field_70327_n - radius), (double)(tile.field_70329_l + radius), (double)(tile.field_70330_m + radius), (double)(tile.field_70327_n + radius)))) {
                            PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new TitlePacket(new Title(this.title.replace('&', '\u00a7'), this.subtitle.replace('&', '\u00a7'), this.time * 20))), (Player)((Player)o));
                        }
                    } else if (sender instanceof EntityPlayer) {
                        EntityPlayer player = (EntityPlayer)sender;
                        for (Object o : ((EntityPlayer)sender).field_70170_p.func_72872_a(EntityPlayer.class, AxisAlignedBB.func_72330_a((double)(player.field_70165_t - (double)radius), (double)(player.field_70163_u - (double)radius), (double)(player.field_70161_v - (double)radius), (double)(player.field_70165_t + (double)radius), (double)(player.field_70163_u + (double)radius), (double)(player.field_70161_v + (double)radius)))) {
                            PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new TitlePacket(new Title(this.title.replace('&', '\u00a7'), this.subtitle.replace('&', '\u00a7'), this.time * 20))), (Player)((Player)o));
                        }
                    }
                    sender.func_70006_a(ChatMessageComponent.func_111066_d((String)Translation.get("\u00a7cLe title a \u00e9t\u00e9 envoy\u00e9 \u00e0 \u00a74tout le monde \u00a7cdans un rayon de <radius> blocks.").replaceAll("<radius>", radius + "")));
                    this.resetTitle();
                    return;
                }
                if (this.isTime(args[1])) {
                    if (args.length < 2) {
                        this.send(sender, this.func_71518_a(sender));
                        return;
                    }
                    this.time = this.getTime(args[1]);
                    this.parseTitleAndSub(sender, args, 1);
                } else {
                    this.parseTitleAndSub(sender, args, 0);
                }
                sender.func_70006_a(ChatMessageComponent.func_111066_d((String)Translation.get("\u00a7aLe title a \u00e9t\u00e9 envoy\u00e9 \u00e0 \u00a72tout le monde\u00a7c.")));
                for (EntityPlayer player : CommandUtils.getAllPlayers()) {
                    PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new TitlePacket(new Title(this.title.replace('&', '\u00a7'), this.subtitle.replace('&', '\u00a7'), this.time * 20))), (Player)((Player)player));
                }
                this.resetTitle();
                return;
            }
            if (args[0].startsWith("@p")) {
                if (this.isTime(args[1])) {
                    if (args.length < 2) {
                        this.send(sender, this.func_71518_a(sender));
                        return;
                    }
                    this.time = this.getTime(args[1]);
                    this.parseTitleAndSub(sender, args, 1);
                } else {
                    this.parseTitleAndSub(sender, args, 0);
                }
                int radius = 5;
                if (sender instanceof TileEntityCommandBlock) {
                    TileEntityCommandBlock tile = (TileEntityCommandBlock)sender;
                    int i = 0;
                    for (Object o : tile.field_70331_k.func_72872_a(EntityPlayer.class, AxisAlignedBB.func_72330_a((double)(tile.field_70329_l - radius), (double)(tile.field_70330_m - radius), (double)(tile.field_70327_n - radius), (double)(tile.field_70329_l + radius), (double)(tile.field_70330_m + radius), (double)(tile.field_70327_n + radius)))) {
                        if (i > 0) continue;
                        PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new TitlePacket(new Title(this.title.replace('&', '\u00a7'), this.subtitle.replace('&', '\u00a7'), this.time * 20))), (Player)((Player)o));
                        ++i;
                    }
                } else if (sender instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer)sender;
                    int i = 0;
                    for (Object o : ((EntityPlayer)sender).field_70170_p.func_72872_a(EntityPlayer.class, AxisAlignedBB.func_72330_a((double)(player.field_70165_t - (double)radius), (double)(player.field_70163_u - (double)radius), (double)(player.field_70161_v - (double)radius), (double)(player.field_70165_t + (double)radius), (double)(player.field_70163_u + (double)radius), (double)(player.field_70161_v + (double)radius)))) {
                        if (i > 0) continue;
                        PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new TitlePacket(new Title(this.title.replace('&', '\u00a7'), this.subtitle.replace('&', '\u00a7'), this.time * 20))), (Player)((Player)o));
                        ++i;
                    }
                }
                sender.func_70006_a(ChatMessageComponent.func_111066_d((String)Translation.get("\u00a7cLe title a \u00e9t\u00e9 envoy\u00e9 au \u00a74joueur le plus proche.")));
                this.resetTitle();
                return;
            }
            this.send(sender, this.func_71518_a(sender));
            return;
        }
        if (CommandUtils.isPlayerOnline(args[0])) {
            EntityPlayer target = CommandUtils.getPlayer(args[0]);
            if (this.isTime(args[1])) {
                if (args.length < 2) {
                    this.send(sender, this.func_71518_a(sender));
                    return;
                }
                this.time = this.getTime(args[1]);
                this.parseTitleAndSub(sender, args, 1);
            } else {
                this.parseTitleAndSub(sender, args, 0);
            }
            sender.func_70006_a(ChatMessageComponent.func_111066_d((String)Translation.get("\u00a7aLe title a \u00e9t\u00e9 envoy\u00e9 \u00e0 \u00a72<player>\u00a7a.").replaceAll("<player>", target.getDisplayName())));
            PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new TitlePacket(new Title(this.title.replace('&', '\u00a7'), this.subtitle.replace('&', '\u00a7'), this.time * 20))), (Player)((Player)target));
            this.resetTitle();
            return;
        } else {
            this.send(sender, this.func_71518_a(sender));
            return;
        }
    }

    private void resetTitle() {
        this.title = "";
        this.subtitle = "";
    }

    private int getRadius(String string) {
        String nb;
        Pattern pt = Pattern.compile("@a\\[r=(\\d+)\\]");
        Matcher mt = pt.matcher(string);
        if (mt.find() && this.isInt(nb = mt.group(1))) {
            return Integer.parseInt(nb);
        }
        return 0;
    }

    private boolean isRadiusSet(String string) {
        return string.matches("@a\\[r=(\\d+)\\]");
    }

    private void parseTitleAndSub(ICommandSender sender, String[] args, int removed) {
        String[] newArgs;
        ArrayList<String> list = new ArrayList<String>(Arrays.asList(args));
        for (int i = 0; i <= removed; ++i) {
            System.out.println(i);
            list.remove(0);
        }
        String line = "";
        for (String s : list) {
            line = line + s + " ";
        }
        if (line.contains("$$$") && (newArgs = line.split("\\$\\$\\$")).length == 2) {
            this.title = newArgs[0];
            this.subtitle = newArgs[1];
        } else if (!line.contains("$$$")) {
            this.title = line;
        } else {
            this.send(sender, this.func_71518_a(sender));
            return;
        }
    }

    private boolean isInt(String string) {
        try {
            Integer.parseInt(string);
            return true;
        }
        catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

    private boolean isTime(String string) {
        return string.matches("t=(\\d+)");
    }

    private int getTime(String string) {
        String nb;
        Pattern pt = Pattern.compile("t=(\\d+)");
        Matcher mt = pt.matcher(string);
        if (mt.find() && this.isInt(nb = mt.group(1))) {
            return Integer.parseInt(nb) > 10 ? 10 : Integer.parseInt(nb);
        }
        return 3;
    }

    private void send(ICommandSender sender, String mess) {
        sender.func_70006_a(ChatMessageComponent.func_111066_d((String)mess));
    }

    public int compareTo(Object arg0) {
        return 0;
    }
}

