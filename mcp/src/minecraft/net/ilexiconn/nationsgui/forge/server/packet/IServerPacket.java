/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.server.MinecraftServer
 */
package net.ilexiconn.nationsgui.forge.server.packet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public interface IServerPacket {
    public static final MinecraftServer sv = MinecraftServer.func_71276_C();

    public void handleServerPacket(EntityPlayer var1);
}

