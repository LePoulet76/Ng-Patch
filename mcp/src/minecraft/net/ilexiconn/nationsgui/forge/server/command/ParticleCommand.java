package net.ilexiconn.nationsgui.forge.server.command;

import com.google.common.collect.Lists;
import com.google.common.primitives.Doubles;
import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.Collections;
import java.util.List;
import net.ilexiconn.nationsgui.forge.server.command.utils.EnumParticleTypes;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PacketSpawnParticle;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ParticleCommand extends CommandBase
{
    public String getCommandName()
    {
        return "particle";
    }

    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    public String getCommandUsage(ICommandSender sender)
    {
        return "/particle <name> <x> <y> <z> <xd> <yd> <zd> [count]";
    }

    public void processCommand(ICommandSender sender, String[] args)
    {
        if (args.length < 7)
        {
            throw new WrongUsageException("/particle <name> <x> <y> <z> <xd> <yd> <zd> [count]", new Object[0]);
        }
        else
        {
            EnumParticleTypes enumparticletypes = EnumParticleTypes.getParticleFromId(args[0]);

            if (enumparticletypes == null)
            {
                throw new CommandException("Unknown effect name (" + args[0] + ")", new Object[0]);
            }
            else
            {
                String s = args[0];
                ChunkCoordinates cc = sender.getPlayerCoordinates();
                double x = (double)((float)parseDouble((double)cc.posX, args[1], true));
                double y = (double)((float)parseDouble((double)cc.posY, args[2], true));
                double z = (double)((float)parseDouble((double)cc.posZ, args[3], true));
                double xd = (double)((float)parseDouble(args[4]));
                double yd = (double)((float)parseDouble(args[5]));
                double zd = (double)((float)parseDouble(args[6]));
                int i = 0;

                if (args.length > 7)
                {
                    i = (i = parseInt(sender, args[7])) != 0 ? i : 0;
                }

                World world = sender.getEntityWorld();

                if (world instanceof WorldServer)
                {
                    if (i != 0)
                    {
                        for (int j = 0; j < i; ++j)
                        {
                            PacketDispatcher.sendPacketToAllAround(x, y, z, 128.0D, world.provider.dimensionId, PacketRegistry.INSTANCE.generatePacket(new PacketSpawnParticle(s, x, y, z, xd, yd, zd)));
                        }
                    }
                    else
                    {
                        PacketDispatcher.sendPacketToAllAround(x, y, z, 128.0D, world.provider.dimensionId, PacketRegistry.INSTANCE.generatePacket(new PacketSpawnParticle(s, x, y, z, xd, yd, zd)));
                    }

                    sender.sendChatToPlayer(ChatMessageComponent.createFromText("Playing effect " + s + " for " + Integer.valueOf(Math.max(i, 1)) + " times"));
                }
            }
        }
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List addTabCompletionOptions(ICommandSender sender, String[] args)
    {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, EnumParticleTypes.getParticleNames()) : (args.length > 1 && args.length <= 4 ? getTabCompletionCoordinate(args, 1, sender.getPlayerCoordinates()) : (args.length == 10 ? getListOfStringsMatchingLastWord(args, new String[] {"normal", "force"}): (args.length == 11 ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : Collections.emptyList())));
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    public boolean isUsernameIndex(String[] args, int index)
    {
        return index == 10;
    }

    public int compareTo(Object arg0)
    {
        return 0;
    }

    public static double parseDouble(double base, String input, boolean centerBlock) throws NumberInvalidException
    {
        return parseDouble(base, input, -30000000, 30000000, centerBlock);
    }

    public static double parseDouble(double base, String input, int min, int max, boolean centerBlock) throws NumberInvalidException
    {
        boolean flag = input.startsWith("~");

        if (flag && Double.isNaN(base))
        {
            throw new NumberInvalidException("\'" + Double.valueOf(base) + "\' is not a valid number", new Object[0]);
        }
        else
        {
            double d0 = flag ? base : 0.0D;

            if (!flag || input.length() > 1)
            {
                boolean flag1 = input.contains(".");

                if (flag)
                {
                    input = input.substring(1);
                }

                d0 += parseDouble(input);

                if (!flag1 && !flag && centerBlock)
                {
                    d0 += 0.5D;
                }
            }

            if (min != 0 || max != 0)
            {
                if (d0 < (double)min)
                {
                    throw new NumberInvalidException("The number you have entered (" + Double.valueOf(d0) + ") is too small, it must be at least " + Integer.valueOf(min), new Object[0]);
                }

                if (d0 > (double)max)
                {
                    throw new NumberInvalidException("The number you have entered (" + Double.valueOf(d0) + ") is too big, it must be at most " + Integer.valueOf(max), new Object[0]);
                }
            }

            return d0;
        }
    }

    public static double parseDouble(String input) throws NumberInvalidException
    {
        try
        {
            double var3 = Double.parseDouble(input);

            if (!Doubles.isFinite(var3))
            {
                throw new NumberInvalidException("\'" + Double.valueOf(input) + "\' is not a valid number", new Object[0]);
            }
            else
            {
                return var3;
            }
        }
        catch (NumberFormatException var31)
        {
            throw new NumberInvalidException("\'" + Double.valueOf(input) + "\' is not a valid number", new Object[0]);
        }
    }

    public static List<String> getTabCompletionCoordinate(String[] inputArgs, int index, ChunkCoordinates pos)
    {
        if (pos == null)
        {
            return Lists.newArrayList(new String[] {"~"});
        }
        else
        {
            int i = inputArgs.length - 1;
            String s;

            if (i == index)
            {
                s = Integer.toString(pos.posX);
            }
            else if (i == index + 1)
            {
                s = Integer.toString(pos.posY);
            }
            else
            {
                if (i != index + 2)
                {
                    return Collections.emptyList();
                }

                s = Integer.toString(pos.posZ);
            }

            return Lists.newArrayList(new String[] {s});
        }
    }
}
