/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  net.minecraft.command.CommandBase
 *  net.minecraft.command.ICommandSender
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.nbt.CompressedStreamTools
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.nbt.NBTTagString
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.ChatMessageComponent
 *  net.minecraft.world.WorldServer
 */
package net.ilexiconn.nationsgui.forge.server.command;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import net.ilexiconn.nationsgui.forge.server.ServerProxy;
import net.ilexiconn.nationsgui.forge.server.entity.data.NGPlayerData;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PacketEmote;
import net.ilexiconn.nationsgui.forge.server.util.Translation;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.WorldServer;

public class CommandEmote
extends CommandBase {
    public String func_71517_b() {
        return "emote";
    }

    public String func_71518_a(ICommandSender p_71518_1_) {
        return "<emote>";
    }

    public void func_71515_b(ICommandSender sender, String[] args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("give") && args.length > 2) {
                if (CommandBase.func_82359_c((ICommandSender)sender, (String)args[1]) != null) {
                    if (ServerProxy.emotesName.contains(args[2])) {
                        if (NGPlayerData.get((EntityPlayer)CommandBase.func_82359_c((ICommandSender)sender, (String)args[1])).addEmote(args[2])) {
                            sender.func_70006_a(ChatMessageComponent.func_111066_d((String)Translation.get("\u00a74[\u00a7cEmotes\u00a74] \u00a77Vous avez ajout\u00e9 l'emote '<emote>' \u00e0 <player>.").replaceAll("<emote>", args[2]).replaceAll("<player>", args[1])));
                        } else {
                            sender.func_70006_a(ChatMessageComponent.func_111066_d((String)Translation.get("\u00a74[\u00a7cEmotes\u00a74] \u00a77Ce joueur a d\u00e9ja cette emote.")));
                        }
                    } else if (args[2].equals("*")) {
                        for (String emoteName : ServerProxy.emotesName) {
                            NGPlayerData.get((EntityPlayer)CommandBase.func_82359_c((ICommandSender)sender, (String)args[1])).addEmote(emoteName);
                        }
                    } else {
                        sender.func_70006_a(ChatMessageComponent.func_111066_d((String)Translation.get("\u00a74[\u00a7cEmotes\u00a74] \u00a77Cette emote n'existe pas")));
                    }
                } else {
                    File playerFile = new File(".", "/world/players/" + args[0] + ".dat");
                    if (!playerFile.exists()) {
                        sender.func_70006_a(ChatMessageComponent.func_111066_d((String)Translation.get("Le fichier du joueur n'existe pas")));
                        return;
                    }
                    try {
                        NBTTagCompound comp = CompressedStreamTools.func_74796_a((InputStream)new FileInputStream(playerFile));
                        if (comp.func_74764_b("NGPlayerData")) {
                            NBTTagCompound emotes = comp.func_74775_l("NGPlayerData");
                            if (emotes.func_74764_b("Emotes") && emotes.func_74761_m("Emotes").func_74745_c() > 0) {
                                NBTTagList list = emotes.func_74761_m("Emotes");
                                NBTTagList list1 = (NBTTagList)list.func_74737_b();
                                list1.func_74742_a((NBTBase)new NBTTagString("Emote", args[2]));
                                emotes.func_74782_a(list.func_74740_e(), (NBTBase)list1);
                            } else {
                                NBTTagList list = new NBTTagList("Emotes");
                                list.func_74742_a((NBTBase)new NBTTagString("Emote", args[2]));
                                emotes.func_74782_a(list.func_74740_e(), (NBTBase)list);
                            }
                            comp.func_74782_a("NGPlayerData", (NBTBase)emotes);
                        }
                        CompressedStreamTools.func_74799_a((NBTTagCompound)comp, (OutputStream)new FileOutputStream(playerFile));
                        return;
                    }
                    catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            } else if (args[0].equalsIgnoreCase("get") && args.length > 1) {
                if (CommandBase.func_82359_c((ICommandSender)sender, (String)args[1]) != null) {
                    List<String> emotes = NGPlayerData.get((EntityPlayer)CommandBase.func_82359_c((ICommandSender)sender, (String)args[1])).emotes;
                    sender.func_70006_a(ChatMessageComponent.func_111066_d((String)Translation.get("\u00a74[\u00a7cEmotes\u00a74] \u00a77Liste des emotes de ce joueur :")));
                    for (String emote : emotes) {
                        sender.func_70006_a(ChatMessageComponent.func_111066_d((String)("\u00a74[\u00a7cEmotes\u00a74] \u00a77- " + emote)));
                    }
                } else {
                    sender.func_70006_a(ChatMessageComponent.func_111066_d((String)Translation.get("\u00a74[\u00a7cEmotes\u00a74] \u00a77Ce joueur n'est pas connect\u00e9.")));
                }
            } else if (args[0].equalsIgnoreCase("remove") && args.length > 2) {
                if (CommandBase.func_82359_c((ICommandSender)sender, (String)args[1]) != null) {
                    if (ServerProxy.emotesName.contains(args[2])) {
                        if (NGPlayerData.get((EntityPlayer)CommandBase.func_82359_c((ICommandSender)sender, (String)args[1])).removeEmote(args[2])) {
                            sender.func_70006_a(ChatMessageComponent.func_111066_d((String)Translation.get("\u00a74[\u00a7cEmotes\u00a74] \u00a77Vous avez retir\u00e9 l'emote '<emote>' de <player>.").replaceAll("<emote>", args[2]).replaceAll("<player>", args[1])));
                        } else {
                            sender.func_70006_a(ChatMessageComponent.func_111066_d((String)Translation.get("\u00a74[\u00a7cEmotes\u00a74] \u00a77Ce joueur n'a pas cette emote.")));
                        }
                    } else {
                        sender.func_70006_a(ChatMessageComponent.func_111066_d((String)Translation.get("\u00a74[\u00a7cEmotes\u00a74] \u00a77Cette emote n'existe pas")));
                    }
                } else {
                    File playerFile = new File(".", "/world/players/" + args[0] + ".dat");
                    if (!playerFile.exists()) {
                        sender.func_70006_a(ChatMessageComponent.func_111066_d((String)Translation.get("Le fichier du joueur n'existe pas")));
                        return;
                    }
                    try {
                        NBTTagCompound comp = CompressedStreamTools.func_74796_a((InputStream)new FileInputStream(playerFile));
                        if (comp.func_74764_b("NGPlayerData")) {
                            NBTTagCompound emotes = comp.func_74775_l("NGPlayerData");
                            if (emotes.func_74764_b("Emotes") && emotes.func_74761_m("Emotes").func_74745_c() > 0) {
                                NBTTagList list = emotes.func_74761_m("Emotes");
                                NBTTagList list1 = new NBTTagList(list.func_74740_e());
                                for (int i = 0; i < list.func_74745_c(); ++i) {
                                    NBTTagString tag = (NBTTagString)list.func_74743_b(i);
                                    if (tag.field_74751_a.equalsIgnoreCase(args[2])) continue;
                                    list1.func_74742_a((NBTBase)tag);
                                }
                                emotes.func_74782_a(list.func_74740_e(), (NBTBase)list1);
                            }
                            comp.func_74782_a("NGPlayerData", (NBTBase)emotes);
                        }
                        CompressedStreamTools.func_74799_a((NBTTagCompound)comp, (OutputStream)new FileOutputStream(playerFile));
                        return;
                    }
                    catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            } else if (args[0].equalsIgnoreCase("play") && args.length > 1 && sender instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer)sender;
                for (Object e : player.field_70170_p.func_72872_a(player.getClass(), AxisAlignedBB.func_72332_a().func_72299_a(player.field_70165_t - 50.0, player.field_70163_u - 50.0, player.field_70161_v - 50.0, player.field_70165_t + 50.0, player.field_70163_u + 50.0, player.field_70161_v + 50.0))) {
                    if (!(e instanceof Player)) continue;
                    PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new PacketEmote(args[1], player.field_71092_bJ)), (Player)((Player)e));
                }
            } else if (args[0].equalsIgnoreCase("playallworlds") && args.length > 1) {
                for (WorldServer server : MinecraftServer.func_71276_C().field_71305_c) {
                    for (EntityPlayer player : server.field_73010_i) {
                        for (Object e : player.field_70170_p.func_72872_a(player.getClass(), AxisAlignedBB.func_72332_a().func_72299_a(player.field_70165_t - 50.0, player.field_70163_u - 50.0, player.field_70161_v - 50.0, player.field_70165_t + 50.0, player.field_70163_u + 50.0, player.field_70161_v + 50.0))) {
                            if (!(e instanceof Player)) continue;
                            PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new PacketEmote(args[1], player.field_71092_bJ)), (Player)((Player)e));
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("playall") && args.length > 1 && sender instanceof EntityPlayer) {
                for (EntityPlayer player : ((EntityPlayer)sender).field_70170_p.field_73010_i) {
                    for (Object e : player.field_70170_p.func_72872_a(player.getClass(), AxisAlignedBB.func_72332_a().func_72299_a(player.field_70165_t - 50.0, player.field_70163_u - 50.0, player.field_70161_v - 50.0, player.field_70165_t + 50.0, player.field_70163_u + 50.0, player.field_70161_v + 50.0))) {
                        if (!(e instanceof Player)) continue;
                        PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new PacketEmote(args[1], player.field_71092_bJ)), (Player)((Player)e));
                    }
                }
            } else {
                sender.func_70006_a(ChatMessageComponent.func_111066_d((String)"\u00a74[\u00a7cEmotes\u00a74] \u00a77/emote <give/get/remove/list/play/playallworlds/playall> [name] [emote]"));
            }
        } else {
            sender.func_70006_a(ChatMessageComponent.func_111066_d((String)"\u00a74[\u00a7cEmotes\u00a74] \u00a77/emote <give/get/remove/list/play/playallworlds/playall> [name] [emote]"));
        }
    }

    public int compareTo(Object o) {
        return 0;
    }
}

