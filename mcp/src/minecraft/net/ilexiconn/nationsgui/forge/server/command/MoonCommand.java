/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  net.minecraft.command.CommandBase
 *  net.minecraft.command.ICommandSender
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.util.ChatMessageComponent
 *  org.bukkit.Bukkit
 */
package net.ilexiconn.nationsgui.forge.server.command;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.MoonOpenGuiPacket;
import net.ilexiconn.nationsgui.forge.server.util.Translation;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;
import org.bukkit.Bukkit;

public class MoonCommand
extends CommandBase {
    private static Class cl = Bukkit.getPluginManager().getPlugin("NationsGUI") != null ? Bukkit.getPluginManager().getPlugin("NationsGUI").getClass() : null;

    public String func_71517_b() {
        return "moon";
    }

    public String func_71518_a(ICommandSender sender) {
        return null;
    }

    public void func_71515_b(ICommandSender sender, String[] args) {
        if (cl == null && Bukkit.getPluginManager().getPlugin("NationsGUI") != null) {
            cl = Bukkit.getPluginManager().getPlugin("NationsGUI").getClass();
        }
        if (args.length == 2 && MinecraftServer.func_71276_C().func_71203_ab().func_72353_e(sender.func_70005_c_())) {
            if (args[0].equalsIgnoreCase("setGoal") && this.isDouble(args[1])) {
                double goal = Double.parseDouble(args[1]);
                try {
                    cl.getDeclaredMethod("setMoonGoal", Double.TYPE).invoke(null, goal);
                    sender.func_70006_a(ChatMessageComponent.func_111066_d((String)Translation.get("\u00a7aLe nouveau goal est de \u00a72" + goal)));
                }
                catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else if (args[0].equalsIgnoreCase("setActive") && (args[1].equals("1") || args[1].equals("0"))) {
                try {
                    cl.getDeclaredMethod("setMoonActive", Boolean.TYPE).invoke(null, args[1].equals("1"));
                    sender.func_70006_a(ChatMessageComponent.func_111066_d((String)Translation.get("\u00a7aLe projet lunaire est maintenant " + (args[1].equals("1") ? "\u00a72activ\u00e9" : "\u00a7cd\u00e9sactiv\u00e9"))));
                }
                catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            return;
        }
        if (this.isMoonActived()) {
            PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new MoonOpenGuiPacket(this.isGoalAchieved(), this.getGoal(), this.getActualMoney(), this.getDonators())), (Player)((Player)sender));
        } else {
            sender.func_70006_a(ChatMessageComponent.func_111066_d((String)Translation.get("\u00a7cIl n'y a pas de projet lunaire en cours !")));
        }
    }

    private List<String> getDonators() {
        try {
            return (List)cl.getDeclaredMethod("getDonators", new Class[0]).invoke(null, new Object[0]);
        }
        catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isGoalAchieved() {
        try {
            return (Boolean)cl.getDeclaredMethod("isGoalAchieved", new Class[0]).invoke(null, new Object[0]);
        }
        catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }
    }

    public double getGoal() {
        try {
            return (Double)cl.getDeclaredMethod("getGoal", new Class[0]).invoke(null, new Object[0]);
        }
        catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    public double getActualMoney() {
        try {
            return (Double)cl.getDeclaredMethod("getActualMoney", new Class[0]).invoke(null, new Object[0]);
        }
        catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    public boolean isMoonActived() {
        try {
            return (Boolean)cl.getDeclaredMethod("isMoonActived", new Class[0]).invoke(null, new Object[0]);
        }
        catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isDouble(String string) {
        try {
            Double.parseDouble(string);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public int compareTo(Object arg0) {
        return 0;
    }
}

