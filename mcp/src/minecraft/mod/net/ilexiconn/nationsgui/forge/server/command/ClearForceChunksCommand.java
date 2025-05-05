package net.ilexiconn.nationsgui.forge.server.command;

import com.google.common.collect.UnmodifiableIterator;
import java.util.Map.Entry;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;

public class ClearForceChunksCommand extends CommandBase
{
    public String getCommandName()
    {
        return "clearforcechunks";
    }

    public String getCommandUsage(ICommandSender icommandsender)
    {
        return null;
    }

    public void processCommand(ICommandSender icommandsender, String[] args)
    {
        if (icommandsender.getEntityWorld() != null)
        {
            System.out.println("Nb forced chunks: " + icommandsender.getEntityWorld().getPersistentChunks().size());

            if (args.length > 0)
            {
                UnmodifiableIterator var3 = icommandsender.getEntityWorld().getPersistentChunks().entries().iterator();

                while (var3.hasNext())
                {
                    Entry entry = (Entry)var3.next();
                    ChunkCoordIntPair chunkCoordIntPair = (ChunkCoordIntPair)entry.getKey();
                    ForgeChunkManager.unforceChunk((Ticket)entry.getValue(), chunkCoordIntPair);
                }
            }
        }
    }

    public int compareTo(Object o)
    {
        return 0;
    }
}
