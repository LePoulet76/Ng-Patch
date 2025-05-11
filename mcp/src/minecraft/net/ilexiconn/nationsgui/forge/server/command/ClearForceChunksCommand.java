/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.command.CommandBase
 *  net.minecraft.command.ICommandSender
 *  net.minecraft.world.ChunkCoordIntPair
 *  net.minecraftforge.common.ForgeChunkManager
 *  net.minecraftforge.common.ForgeChunkManager$Ticket
 */
package net.ilexiconn.nationsgui.forge.server.command;

import java.util.Map;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraftforge.common.ForgeChunkManager;

public class ClearForceChunksCommand
extends CommandBase {
    public String func_71517_b() {
        return "clearforcechunks";
    }

    public String func_71518_a(ICommandSender icommandsender) {
        return null;
    }

    public void func_71515_b(ICommandSender icommandsender, String[] args) {
        if (icommandsender.func_130014_f_() != null) {
            System.out.println("Nb forced chunks: " + icommandsender.func_130014_f_().getPersistentChunks().size());
            if (args.length > 0) {
                for (Map.Entry entry : icommandsender.func_130014_f_().getPersistentChunks().entries()) {
                    ChunkCoordIntPair chunkCoordIntPair = (ChunkCoordIntPair)entry.getKey();
                    ForgeChunkManager.unforceChunk((ForgeChunkManager.Ticket)((ForgeChunkManager.Ticket)entry.getValue()), (ChunkCoordIntPair)chunkCoordIntPair);
                }
            }
        }
    }

    public int compareTo(Object o) {
        return 0;
    }
}

