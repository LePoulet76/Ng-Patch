/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.primitives.Doubles
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.command.CommandBase
 *  net.minecraft.command.CommandException
 *  net.minecraft.command.ICommandSender
 *  net.minecraft.command.NumberInvalidException
 *  net.minecraft.command.WrongUsageException
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.util.ChatMessageComponent
 *  net.minecraft.util.ChunkCoordinates
 *  net.minecraft.world.World
 *  net.minecraft.world.WorldServer
 */
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
import net.minecraft.network.packet.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ParticleCommand
extends CommandBase {
    public String func_71517_b() {
        return "particle";
    }

    public int func_82362_a() {
        return 2;
    }

    public String func_71518_a(ICommandSender sender) {
        return "/particle <name> <x> <y> <z> <xd> <yd> <zd> [count]";
    }

    public void func_71515_b(ICommandSender sender, String[] args) {
        World world;
        if (args.length < 7) {
            throw new WrongUsageException("/particle <name> <x> <y> <z> <xd> <yd> <zd> [count]", new Object[0]);
        }
        EnumParticleTypes enumparticletypes = EnumParticleTypes.getParticleFromId(args[0]);
        if (enumparticletypes == null) {
            throw new CommandException("Unknown effect name (" + args[0] + ")", new Object[0]);
        }
        String s = args[0];
        ChunkCoordinates cc = sender.func_82114_b();
        double x = (float)ParticleCommand.parseDouble(cc.field_71574_a, args[1], true);
        double y = (float)ParticleCommand.parseDouble(cc.field_71572_b, args[2], true);
        double z = (float)ParticleCommand.parseDouble(cc.field_71573_c, args[3], true);
        double xd = (float)ParticleCommand.parseDouble(args[4]);
        double yd = (float)ParticleCommand.parseDouble(args[5]);
        double zd = (float)ParticleCommand.parseDouble(args[6]);
        int i = 0;
        if (args.length > 7) {
            i = ParticleCommand.func_71526_a((ICommandSender)sender, (String)args[7]);
            int n = i = i != 0 ? i : 0;
        }
        if ((world = sender.func_130014_f_()) instanceof WorldServer) {
            if (i != 0) {
                for (int j = 0; j < i; ++j) {
                    PacketDispatcher.sendPacketToAllAround((double)x, (double)y, (double)z, (double)128.0, (int)world.field_73011_w.field_76574_g, (Packet)PacketRegistry.INSTANCE.generatePacket(new PacketSpawnParticle(s, x, y, z, xd, yd, zd)));
                }
            } else {
                PacketDispatcher.sendPacketToAllAround((double)x, (double)y, (double)z, (double)128.0, (int)world.field_73011_w.field_76574_g, (Packet)PacketRegistry.INSTANCE.generatePacket(new PacketSpawnParticle(s, x, y, z, xd, yd, zd)));
            }
            sender.func_70006_a(ChatMessageComponent.func_111066_d((String)("Playing effect " + s + " for " + Integer.valueOf(Math.max(i, 1)) + " times")));
        }
    }

    public List func_71516_a(ICommandSender sender, String[] args) {
        return args.length == 1 ? ParticleCommand.func_71530_a((String[])args, (String[])EnumParticleTypes.getParticleNames()) : (args.length > 1 && args.length <= 4 ? ParticleCommand.getTabCompletionCoordinate(args, 1, sender.func_82114_b()) : (args.length == 10 ? ParticleCommand.func_71530_a((String[])args, (String[])new String[]{"normal", "force"}) : (args.length == 11 ? ParticleCommand.func_71530_a((String[])args, (String[])MinecraftServer.func_71276_C().func_71213_z()) : Collections.emptyList())));
    }

    public boolean func_82358_a(String[] args, int index) {
        return index == 10;
    }

    public int compareTo(Object arg0) {
        return 0;
    }

    public static double parseDouble(double base, String input, boolean centerBlock) throws NumberInvalidException {
        return ParticleCommand.parseDouble(base, input, -30000000, 30000000, centerBlock);
    }

    public static double parseDouble(double base, String input, int min, int max, boolean centerBlock) throws NumberInvalidException {
        double d0;
        boolean flag = input.startsWith("~");
        if (flag && Double.isNaN(base)) {
            throw new NumberInvalidException("'" + Double.valueOf(base) + "' is not a valid number", new Object[0]);
        }
        double d = d0 = flag ? base : 0.0;
        if (!flag || input.length() > 1) {
            boolean flag1 = input.contains(".");
            if (flag) {
                input = input.substring(1);
            }
            d0 += ParticleCommand.parseDouble(input);
            if (!flag1 && !flag && centerBlock) {
                d0 += 0.5;
            }
        }
        if (min != 0 || max != 0) {
            if (d0 < (double)min) {
                throw new NumberInvalidException("The number you have entered (" + Double.valueOf(d0) + ") is too small, it must be at least " + Integer.valueOf(min), new Object[0]);
            }
            if (d0 > (double)max) {
                throw new NumberInvalidException("The number you have entered (" + Double.valueOf(d0) + ") is too big, it must be at most " + Integer.valueOf(max), new Object[0]);
            }
        }
        return d0;
    }

    public static double parseDouble(String input) throws NumberInvalidException {
        try {
            double d0 = Double.parseDouble(input);
            if (!Doubles.isFinite((double)d0)) {
                throw new NumberInvalidException("'" + Double.valueOf(input) + "' is not a valid number", new Object[0]);
            }
            return d0;
        }
        catch (NumberFormatException var3) {
            throw new NumberInvalidException("'" + Double.valueOf(input) + "' is not a valid number", new Object[0]);
        }
    }

    public static List<String> getTabCompletionCoordinate(String[] inputArgs, int index, ChunkCoordinates pos) {
        String s;
        if (pos == null) {
            return Lists.newArrayList((Object[])new String[]{"~"});
        }
        int i = inputArgs.length - 1;
        if (i == index) {
            s = Integer.toString(pos.field_71574_a);
        } else if (i == index + 1) {
            s = Integer.toString(pos.field_71572_b);
        } else {
            if (i != index + 2) {
                return Collections.emptyList();
            }
            s = Integer.toString(pos.field_71573_c);
        }
        return Lists.newArrayList((Object[])new String[]{s});
    }
}

