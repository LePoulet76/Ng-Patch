/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.ITickHandler
 *  cpw.mods.fml.common.TickType
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  cpw.mods.fml.common.registry.TickRegistry
 *  cpw.mods.fml.relauncher.Side
 *  net.minecraft.command.CommandBase
 *  net.minecraft.command.ICommandSender
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.util.ChatMessageComponent
 *  net.minecraft.world.EnumGameType
 */
package net.ilexiconn.nationsgui.forge.server.command;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SetViewPacket;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.EnumGameType;

public class ViewCommand
extends CommandBase {
    private static Manager manager;

    public ViewCommand() {
        manager = new Manager();
    }

    public static Manager getManager() {
        return manager;
    }

    public String func_71517_b() {
        return "view";
    }

    public String func_71518_a(ICommandSender sender) {
        return "\u00a7c/view <username>";
    }

    public void func_71515_b(ICommandSender sender, String[] args) {
        if (sender instanceof EntityPlayerMP) {
            EntityPlayerMP viewer = (EntityPlayerMP)sender;
            if (args.length > 0) {
                String sTarget = args[0];
                EntityPlayerMP target = MinecraftServer.func_71276_C().func_71203_ab().func_72361_f(sTarget);
                if (target != null) {
                    if (target.func_70089_S()) {
                        if (viewer.func_130014_f_().field_73011_w.field_76574_g == target.func_130014_f_().field_73011_w.field_76574_g) {
                            ViewCommand.getManager().startView(viewer, target);
                        } else {
                            viewer.func_70006_a(ChatMessageComponent.func_111066_d((String)"\u00a7cLe joueur n'est pas dans la m\u00eame dimension"));
                        }
                    } else {
                        viewer.func_70006_a(ChatMessageComponent.func_111066_d((String)"\u00a7cLe joueur est mort"));
                    }
                } else {
                    viewer.func_70006_a(ChatMessageComponent.func_111066_d((String)"\u00a7cLe joueur n'existe pas"));
                }
            } else if (ViewCommand.getManager().isViewer(viewer)) {
                ViewCommand.getManager().stopView(viewer);
            } else {
                viewer.func_70006_a(ChatMessageComponent.func_111066_d((String)this.func_71518_a(sender)));
            }
        }
    }

    public int compareTo(Object o) {
        return 0;
    }

    public static class Manager
    implements ITickHandler {
        public Map<String, String> currentViews = new HashMap<String, String>();

        public Manager() {
            System.out.println("Register");
            TickRegistry.registerTickHandler((ITickHandler)this, (Side)Side.SERVER);
        }

        public boolean isViewer(EntityPlayerMP viewer) {
            return this.currentViews.containsKey(viewer.func_110124_au().toString());
        }

        public void startView(EntityPlayerMP viewer, EntityPlayerMP target) {
            this.currentViews.put(viewer.func_110124_au().toString(), target.func_110124_au().toString());
            viewer.func_70080_a(target.field_70165_t, target.field_70163_u, target.field_70161_v, target.field_70177_z, target.field_70125_A);
            viewer.field_71075_bZ.field_75100_b = true;
            viewer.func_71033_a(EnumGameType.CREATIVE);
            PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new SetViewPacket((EntityPlayer)viewer, (EntityPlayer)target)), (Player)((Player)viewer));
        }

        public void stopView(EntityPlayerMP viewer) {
            this.currentViews.remove(viewer.func_110124_au().toString());
            PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new SetViewPacket((EntityPlayer)viewer, (EntityPlayer)viewer)), (Player)((Player)viewer));
        }

        public void tickStart(EnumSet<TickType> type, Object ... tickData) {
        }

        public void tickEnd(EnumSet<TickType> type, Object ... tickData) {
            for (String player : this.currentViews.keySet()) {
                for (EntityPlayerMP viewer : MinecraftServer.func_71276_C().func_71203_ab().field_72404_b) {
                    if (!viewer.func_110124_au().toString().equalsIgnoreCase(player)) continue;
                    for (EntityPlayerMP target : MinecraftServer.func_71276_C().func_71203_ab().field_72404_b) {
                        if (!target.func_110124_au().toString().equals(this.currentViews.get(player))) continue;
                        if (!viewer.func_70093_af() && target.func_70089_S() && viewer.func_130014_f_().field_73011_w.field_76574_g == target.func_130014_f_().field_73011_w.field_76574_g) {
                            viewer.func_70080_a(target.field_70165_t, target.field_70163_u + 15.0, target.field_70161_v, target.field_70177_z, target.field_70125_A);
                            continue;
                        }
                        this.stopView(viewer);
                    }
                }
            }
        }

        public EnumSet<TickType> ticks() {
            return EnumSet.of(TickType.SERVER);
        }

        public String getLabel() {
            return "SpectateViewTickHandler";
        }
    }
}

