package net.ilexiconn.nationsgui.forge.server.command;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.WorldServer;

public class CommandEmote extends CommandBase
{
    public String getCommandName()
    {
        return "emote";
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return "<emote>";
    }

    public void processCommand(ICommandSender sender, String[] args)
    {
        if (args.length > 0)
        {
            Iterator player;
            File var15;
            NBTTagCompound var19;
            NBTTagCompound var23;
            NBTTagList var26;
            NBTTagList var27;

            if (args[0].equalsIgnoreCase("give") && args.length > 2)
            {
                if (CommandBase.getPlayer(sender, args[1]) != null)
                {
                    if (ServerProxy.emotesName.contains(args[2]))
                    {
                        if (NGPlayerData.get(CommandBase.getPlayer(sender, args[1])).addEmote(args[2]))
                        {
                            sender.sendChatToPlayer(ChatMessageComponent.createFromText(Translation.get("\u00a74[\u00a7cEmotes\u00a74] \u00a77Vous avez ajout\u00e9 l\'emote \'<emote>\' \u00e0 <player>.").replaceAll("<emote>", args[2]).replaceAll("<player>", args[1])));
                        }
                        else
                        {
                            sender.sendChatToPlayer(ChatMessageComponent.createFromText(Translation.get("\u00a74[\u00a7cEmotes\u00a74] \u00a77Ce joueur a d\u00e9ja cette emote.")));
                        }
                    }
                    else if (args[2].equals("*"))
                    {
                        player = ServerProxy.emotesName.iterator();

                        while (player.hasNext())
                        {
                            String var21 = (String)player.next();
                            NGPlayerData.get(CommandBase.getPlayer(sender, args[1])).addEmote(var21);
                        }
                    }
                    else
                    {
                        sender.sendChatToPlayer(ChatMessageComponent.createFromText(Translation.get("\u00a74[\u00a7cEmotes\u00a74] \u00a77Cette emote n\'existe pas")));
                    }
                }
                else
                {
                    var15 = new File(".", "/world/players/" + args[0] + ".dat");

                    if (!var15.exists())
                    {
                        sender.sendChatToPlayer(ChatMessageComponent.createFromText(Translation.get("Le fichier du joueur n\'existe pas")));
                        return;
                    }

                    try
                    {
                        var19 = CompressedStreamTools.readCompressed(new FileInputStream(var15));

                        if (var19.hasKey("NGPlayerData"))
                        {
                            var23 = var19.getCompoundTag("NGPlayerData");

                            if (var23.hasKey("Emotes") && var23.getTagList("Emotes").tagCount() > 0)
                            {
                                var26 = var23.getTagList("Emotes");
                                var27 = (NBTTagList)var26.copy();
                                var27.appendTag(new NBTTagString("Emote", args[2]));
                                var23.setTag(var26.getName(), var27);
                            }
                            else
                            {
                                var26 = new NBTTagList("Emotes");
                                var26.appendTag(new NBTTagString("Emote", args[2]));
                                var23.setTag(var26.getName(), var26);
                            }

                            var19.setTag("NGPlayerData", var23);
                        }

                        CompressedStreamTools.writeCompressed(var19, new FileOutputStream(var15));
                        return;
                    }
                    catch (IOException var12)
                    {
                        var12.printStackTrace();
                    }
                }
            }
            else
            {
                Iterator var18;

                if (args[0].equalsIgnoreCase("get") && args.length > 1)
                {
                    if (CommandBase.getPlayer(sender, args[1]) != null)
                    {
                        List var16 = NGPlayerData.get(CommandBase.getPlayer(sender, args[1])).emotes;
                        sender.sendChatToPlayer(ChatMessageComponent.createFromText(Translation.get("\u00a74[\u00a7cEmotes\u00a74] \u00a77Liste des emotes de ce joueur :")));
                        var18 = var16.iterator();

                        while (var18.hasNext())
                        {
                            String var25 = (String)var18.next();
                            sender.sendChatToPlayer(ChatMessageComponent.createFromText("\u00a74[\u00a7cEmotes\u00a74] \u00a77- " + var25));
                        }
                    }
                    else
                    {
                        sender.sendChatToPlayer(ChatMessageComponent.createFromText(Translation.get("\u00a74[\u00a7cEmotes\u00a74] \u00a77Ce joueur n\'est pas connect\u00e9.")));
                    }
                }
                else if (args[0].equalsIgnoreCase("remove") && args.length > 2)
                {
                    if (CommandBase.getPlayer(sender, args[1]) != null)
                    {
                        if (ServerProxy.emotesName.contains(args[2]))
                        {
                            if (NGPlayerData.get(CommandBase.getPlayer(sender, args[1])).removeEmote(args[2]))
                            {
                                sender.sendChatToPlayer(ChatMessageComponent.createFromText(Translation.get("\u00a74[\u00a7cEmotes\u00a74] \u00a77Vous avez retir\u00e9 l\'emote \'<emote>\' de <player>.").replaceAll("<emote>", args[2]).replaceAll("<player>", args[1])));
                            }
                            else
                            {
                                sender.sendChatToPlayer(ChatMessageComponent.createFromText(Translation.get("\u00a74[\u00a7cEmotes\u00a74] \u00a77Ce joueur n\'a pas cette emote.")));
                            }
                        }
                        else
                        {
                            sender.sendChatToPlayer(ChatMessageComponent.createFromText(Translation.get("\u00a74[\u00a7cEmotes\u00a74] \u00a77Cette emote n\'existe pas")));
                        }
                    }
                    else
                    {
                        var15 = new File(".", "/world/players/" + args[0] + ".dat");

                        if (!var15.exists())
                        {
                            sender.sendChatToPlayer(ChatMessageComponent.createFromText(Translation.get("Le fichier du joueur n\'existe pas")));
                            return;
                        }

                        try
                        {
                            var19 = CompressedStreamTools.readCompressed(new FileInputStream(var15));

                            if (var19.hasKey("NGPlayerData"))
                            {
                                var23 = var19.getCompoundTag("NGPlayerData");

                                if (var23.hasKey("Emotes") && var23.getTagList("Emotes").tagCount() > 0)
                                {
                                    var26 = var23.getTagList("Emotes");
                                    var27 = new NBTTagList(var26.getName());

                                    for (int var28 = 0; var28 < var26.tagCount(); ++var28)
                                    {
                                        NBTTagString var29 = (NBTTagString)var26.tagAt(var28);

                                        if (!var29.data.equalsIgnoreCase(args[2]))
                                        {
                                            var27.appendTag(var29);
                                        }
                                    }

                                    var23.setTag(var26.getName(), var27);
                                }

                                var19.setTag("NGPlayerData", var23);
                            }

                            CompressedStreamTools.writeCompressed(var19, new FileOutputStream(var15));
                            return;
                        }
                        catch (IOException var11)
                        {
                            var11.printStackTrace();
                        }
                    }
                }
                else if (args[0].equalsIgnoreCase("play") && args.length > 1 && sender instanceof EntityPlayer)
                {
                    EntityPlayer var14 = (EntityPlayer)sender;
                    var18 = var14.worldObj.getEntitiesWithinAABB(var14.getClass(), AxisAlignedBB.getAABBPool().getAABB(var14.posX - 50.0D, var14.posY - 50.0D, var14.posZ - 50.0D, var14.posX + 50.0D, var14.posY + 50.0D, var14.posZ + 50.0D)).iterator();

                    while (var18.hasNext())
                    {
                        Object var22 = var18.next();

                        if (var22 instanceof Player)
                        {
                            PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new PacketEmote(args[1], var14.username)), (Player)var22);
                        }
                    }
                }
                else if (args[0].equalsIgnoreCase("playallworlds") && args.length > 1)
                {
                    WorldServer[] var13 = MinecraftServer.getServer().worldServers;
                    int var17 = var13.length;

                    for (int var20 = 0; var20 < var17; ++var20)
                    {
                        WorldServer var24 = var13[var20];
                        Iterator list1 = var24.playerEntities.iterator();

                        while (list1.hasNext())
                        {
                            EntityPlayer player2 = (EntityPlayer)list1.next();
                            Iterator tag = player2.worldObj.getEntitiesWithinAABB(player2.getClass(), AxisAlignedBB.getAABBPool().getAABB(player2.posX - 50.0D, player2.posY - 50.0D, player2.posZ - 50.0D, player2.posX + 50.0D, player2.posY + 50.0D, player2.posZ + 50.0D)).iterator();

                            while (tag.hasNext())
                            {
                                Object e2 = tag.next();

                                if (e2 instanceof Player)
                                {
                                    PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new PacketEmote(args[1], player2.username)), (Player)e2);
                                }
                            }
                        }
                    }
                }
                else if (args[0].equalsIgnoreCase("playall") && args.length > 1 && sender instanceof EntityPlayer)
                {
                    player = ((EntityPlayer)sender).worldObj.playerEntities.iterator();

                    while (player.hasNext())
                    {
                        EntityPlayer player1 = (EntityPlayer)player.next();
                        Iterator e = player1.worldObj.getEntitiesWithinAABB(player1.getClass(), AxisAlignedBB.getAABBPool().getAABB(player1.posX - 50.0D, player1.posY - 50.0D, player1.posZ - 50.0D, player1.posX + 50.0D, player1.posY + 50.0D, player1.posZ + 50.0D)).iterator();

                        while (e.hasNext())
                        {
                            Object e1 = e.next();

                            if (e1 instanceof Player)
                            {
                                PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new PacketEmote(args[1], player1.username)), (Player)e1);
                            }
                        }
                    }
                }
                else
                {
                    sender.sendChatToPlayer(ChatMessageComponent.createFromText("\u00a74[\u00a7cEmotes\u00a74] \u00a77/emote <give/get/remove/list/play/playallworlds/playall> [name] [emote]"));
                }
            }
        }
        else
        {
            sender.sendChatToPlayer(ChatMessageComponent.createFromText("\u00a74[\u00a7cEmotes\u00a74] \u00a77/emote <give/get/remove/list/play/playallworlds/playall> [name] [emote]"));
        }
    }

    public int compareTo(Object o)
    {
        return 0;
    }
}
