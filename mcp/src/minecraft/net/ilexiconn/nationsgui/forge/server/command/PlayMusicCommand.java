package net.ilexiconn.nationsgui.forge.server.command;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.util.List;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.MusicPacket;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class PlayMusicCommand extends CommandBase
{
    public String getCommandName()
    {
        return "playmusic";
    }

    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "/playmusic <player> <filename>";
    }

    public void processCommand(ICommandSender icommandsender, String[] astring)
    {
        MusicPacket musicPacket = new MusicPacket();
        musicPacket.filename = astring[1];
        PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(musicPacket), (Player)getPlayer(icommandsender, astring[0]));
    }

    public int compareTo(Object o)
    {
        return 0;
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        return null;
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2)
    {
        return par2 == 0;
    }
}
