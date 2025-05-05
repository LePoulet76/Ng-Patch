package net.ilexiconn.nationsgui.forge.server.command;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatMessageComponent;

public class CommandTitle extends CommandBase
{
    private String title = "";
    private String subtitle = "";
    private int time = 5;

    public String getCommandName()
    {
        return "title";
    }

    public String getCommandUsage(ICommandSender sender)
    {
        return "\u00a7cUsage: /title <@a[r=x]|@p|pseudo> [time] <title> $$$ <subtitle>";
    }

    public void processCommand(ICommandSender sender, String[] args)
    {
        if (args.length < 2)
        {
            this.send(sender, this.getCommandUsage(sender));
        }
        else
        {
            if (args[0].startsWith("@"))
            {
                TileEntityCommandBlock player;
                EntityPlayer var11;

                if (args[0].startsWith("@a"))
                {
                    if (this.isRadiusSet(args[0]))
                    {
                        if (this.isTime(args[1]))
                        {
                            if (args.length < 2)
                            {
                                this.send(sender, this.getCommandUsage(sender));
                                return;
                            }

                            this.time = this.getTime(args[1]);
                            this.parseTitleAndSub(sender, args, 1);
                        }
                        else
                        {
                            this.parseTitleAndSub(sender, args, 0);
                        }

                        int target = this.getRadius(args[0]);
                        Iterator i;
                        Object o;

                        if (sender instanceof TileEntityCommandBlock)
                        {
                            player = (TileEntityCommandBlock)sender;
                            i = player.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox((double)(player.xCoord - target), (double)(player.yCoord - target), (double)(player.zCoord - target), (double)(player.xCoord + target), (double)(player.yCoord + target), (double)(player.zCoord + target))).iterator();

                            while (i.hasNext())
                            {
                                o = i.next();
                                PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new TitlePacket(new Title(this.title.replace('&', '\u00a7'), this.subtitle.replace('&', '\u00a7'), (float)(this.time * 20)))), (Player)o);
                            }
                        }
                        else if (sender instanceof EntityPlayer)
                        {
                            var11 = (EntityPlayer)sender;
                            i = ((EntityPlayer)sender).worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(var11.posX - (double)target, var11.posY - (double)target, var11.posZ - (double)target, var11.posX + (double)target, var11.posY + (double)target, var11.posZ + (double)target)).iterator();

                            while (i.hasNext())
                            {
                                o = i.next();
                                PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new TitlePacket(new Title(this.title.replace('&', '\u00a7'), this.subtitle.replace('&', '\u00a7'), (float)(this.time * 20)))), (Player)o);
                            }
                        }

                        sender.sendChatToPlayer(ChatMessageComponent.createFromText(Translation.get("\u00a7cLe title a \u00e9t\u00e9 envoy\u00e9 \u00e0 \u00a74tout le monde \u00a7cdans un rayon de <radius> blocks.").replaceAll("<radius>", target + "")));
                        this.resetTitle();
                    }
                    else
                    {
                        if (this.isTime(args[1]))
                        {
                            if (args.length < 2)
                            {
                                this.send(sender, this.getCommandUsage(sender));
                                return;
                            }

                            this.time = this.getTime(args[1]);
                            this.parseTitleAndSub(sender, args, 1);
                        }
                        else
                        {
                            this.parseTitleAndSub(sender, args, 0);
                        }

                        sender.sendChatToPlayer(ChatMessageComponent.createFromText(Translation.get("\u00a7aLe title a \u00e9t\u00e9 envoy\u00e9 \u00e0 \u00a72tout le monde\u00a7c.")));
                        Iterator var8 = CommandUtils.getAllPlayers().iterator();

                        while (var8.hasNext())
                        {
                            var11 = (EntityPlayer)var8.next();
                            PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new TitlePacket(new Title(this.title.replace('&', '\u00a7'), this.subtitle.replace('&', '\u00a7'), (float)(this.time * 20)))), (Player)var11);
                        }

                        this.resetTitle();
                    }
                }
                else
                {
                    if (!args[0].startsWith("@p"))
                    {
                        this.send(sender, this.getCommandUsage(sender));
                        return;
                    }

                    if (this.isTime(args[1]))
                    {
                        if (args.length < 2)
                        {
                            this.send(sender, this.getCommandUsage(sender));
                            return;
                        }

                        this.time = this.getTime(args[1]);
                        this.parseTitleAndSub(sender, args, 1);
                    }
                    else
                    {
                        this.parseTitleAndSub(sender, args, 0);
                    }

                    byte var9 = 5;
                    Object o1;
                    int var12;
                    Iterator var13;

                    if (sender instanceof TileEntityCommandBlock)
                    {
                        player = (TileEntityCommandBlock)sender;
                        var12 = 0;
                        var13 = player.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox((double)(player.xCoord - var9), (double)(player.yCoord - var9), (double)(player.zCoord - var9), (double)(player.xCoord + var9), (double)(player.yCoord + var9), (double)(player.zCoord + var9))).iterator();

                        while (var13.hasNext())
                        {
                            o1 = var13.next();

                            if (var12 <= 0)
                            {
                                PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new TitlePacket(new Title(this.title.replace('&', '\u00a7'), this.subtitle.replace('&', '\u00a7'), (float)(this.time * 20)))), (Player)o1);
                                ++var12;
                            }
                        }
                    }
                    else if (sender instanceof EntityPlayer)
                    {
                        var11 = (EntityPlayer)sender;
                        var12 = 0;
                        var13 = ((EntityPlayer)sender).worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(var11.posX - (double)var9, var11.posY - (double)var9, var11.posZ - (double)var9, var11.posX + (double)var9, var11.posY + (double)var9, var11.posZ + (double)var9)).iterator();

                        while (var13.hasNext())
                        {
                            o1 = var13.next();

                            if (var12 <= 0)
                            {
                                PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new TitlePacket(new Title(this.title.replace('&', '\u00a7'), this.subtitle.replace('&', '\u00a7'), (float)(this.time * 20)))), (Player)o1);
                                ++var12;
                            }
                        }
                    }

                    sender.sendChatToPlayer(ChatMessageComponent.createFromText(Translation.get("\u00a7cLe title a \u00e9t\u00e9 envoy\u00e9 au \u00a74joueur le plus proche.")));
                    this.resetTitle();
                }
            }
            else
            {
                if (!CommandUtils.isPlayerOnline(args[0]))
                {
                    this.send(sender, this.getCommandUsage(sender));
                    return;
                }

                EntityPlayer var10 = CommandUtils.getPlayer(args[0]);

                if (this.isTime(args[1]))
                {
                    if (args.length < 2)
                    {
                        this.send(sender, this.getCommandUsage(sender));
                        return;
                    }

                    this.time = this.getTime(args[1]);
                    this.parseTitleAndSub(sender, args, 1);
                }
                else
                {
                    this.parseTitleAndSub(sender, args, 0);
                }

                sender.sendChatToPlayer(ChatMessageComponent.createFromText(Translation.get("\u00a7aLe title a \u00e9t\u00e9 envoy\u00e9 \u00e0 \u00a72<player>\u00a7a.").replaceAll("<player>", var10.getDisplayName())));
                PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new TitlePacket(new Title(this.title.replace('&', '\u00a7'), this.subtitle.replace('&', '\u00a7'), (float)(this.time * 20)))), (Player)var10);
                this.resetTitle();
            }
        }
    }

    private void resetTitle()
    {
        this.title = "";
        this.subtitle = "";
    }

    private int getRadius(String string)
    {
        Pattern pt = Pattern.compile("@a\\[r=(\\d+)\\]");
        Matcher mt = pt.matcher(string);

        if (mt.find())
        {
            String nb = mt.group(1);

            if (this.isInt(nb))
            {
                return Integer.parseInt(nb);
            }
        }

        return 0;
    }

    private boolean isRadiusSet(String string)
    {
        return string.matches("@a\\[r=(\\d+)\\]");
    }

    private void parseTitleAndSub(ICommandSender sender, String[] args, int removed)
    {
        ArrayList list = new ArrayList(Arrays.asList(args));

        for (int line = 0; line <= removed; ++line)
        {
            System.out.println(line);
            list.remove(0);
        }

        String var8 = "";
        String s;

        for (Iterator newArgs = list.iterator(); newArgs.hasNext(); var8 = var8 + s + " ")
        {
            s = (String)newArgs.next();
        }

        String[] var9;

        if (var8.contains("$$$") && (var9 = var8.split("\\$\\$\\$")).length == 2)
        {
            this.title = var9[0];
            this.subtitle = var9[1];
        }
        else
        {
            if (var8.contains("$$$"))
            {
                this.send(sender, this.getCommandUsage(sender));
                return;
            }

            this.title = var8;
        }
    }

    private boolean isInt(String string)
    {
        try
        {
            Integer.parseInt(string);
            return true;
        }
        catch (NumberFormatException var3)
        {
            return false;
        }
    }

    private boolean isTime(String string)
    {
        return string.matches("t=(\\d+)");
    }

    private int getTime(String string)
    {
        Pattern pt = Pattern.compile("t=(\\d+)");
        Matcher mt = pt.matcher(string);

        if (mt.find())
        {
            String nb = mt.group(1);

            if (this.isInt(nb))
            {
                return Integer.parseInt(nb) > 10 ? 10 : Integer.parseInt(nb);
            }
        }

        return 3;
    }

    private void send(ICommandSender sender, String mess)
    {
        sender.sendChatToPlayer(ChatMessageComponent.createFromText(mess));
    }

    public int compareTo(Object arg0)
    {
        return 0;
    }
}
