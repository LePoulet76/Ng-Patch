package net.ilexiconn.nationsgui.forge.server.voices.commands;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.VoiceMutePlayerPacket;
import net.ilexiconn.nationsgui.forge.server.util.Translation;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;

public class VoicePlayerMuteCommand extends CommandBase
{
    public String getCommandName()
    {
        return "voc";
    }

    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "/voc <username>";
    }

    public void processCommand(ICommandSender sender, String[] args)
    {
        if (args.length == 1 && sender instanceof EntityPlayerMP)
        {
            if (!MinecraftServer.getServer().getConfigurationManager().isPlayerOpped(args[0]))
            {
                if (getPlayer(sender, args[0]) != null)
                {
                    PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new VoiceMutePlayerPacket(getPlayer(sender, args[0]).entityId, args[0])), (Player)sender);
                }
                else
                {
                    PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new VoiceMutePlayerPacket(-1, args[0])), (Player)sender);
                }
            }
            else
            {
                sender.sendChatToPlayer(ChatMessageComponent.createFromText(Translation.get("\u00a7cLe joueur que vous tentez de mute est op\u00e9rateur.")));
            }
        }
        else
        {
            sender.sendChatToPlayer(ChatMessageComponent.createFromText(this.getCommandUsage(sender)));
        }
    }

    public int compareTo(Object arg0)
    {
        return 0;
    }

    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 0;
    }
}
