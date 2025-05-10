package net.ilexiconn.nationsgui.forge.server.command;

import net.ilexiconn.nationsgui.forge.server.command.ViewCommand$Manager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;

public class ViewCommand extends CommandBase
{
    private static ViewCommand$Manager manager;

    public ViewCommand()
    {
        manager = new ViewCommand$Manager();
    }

    public static ViewCommand$Manager getManager()
    {
        return manager;
    }

    public String getCommandName()
    {
        return "view";
    }

    public String getCommandUsage(ICommandSender sender)
    {
        return "\u00a7c/view <username>";
    }

    public void processCommand(ICommandSender sender, String[] args)
    {
        if (sender instanceof EntityPlayerMP)
        {
            EntityPlayerMP viewer = (EntityPlayerMP)sender;

            if (args.length > 0)
            {
                String sTarget = args[0];
                EntityPlayerMP target;

                if ((target = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(sTarget)) != null)
                {
                    if (target.isEntityAlive())
                    {
                        if (viewer.getEntityWorld().provider.dimensionId == target.getEntityWorld().provider.dimensionId)
                        {
                            getManager().startView(viewer, target);
                        }
                        else
                        {
                            viewer.sendChatToPlayer(ChatMessageComponent.createFromText("\u00a7cLe joueur n\'est pas dans la m\u00eame dimension"));
                        }
                    }
                    else
                    {
                        viewer.sendChatToPlayer(ChatMessageComponent.createFromText("\u00a7cLe joueur est mort"));
                    }
                }
                else
                {
                    viewer.sendChatToPlayer(ChatMessageComponent.createFromText("\u00a7cLe joueur n\'existe pas"));
                }
            }
            else if (getManager().isViewer(viewer))
            {
                getManager().stopView(viewer);
            }
            else
            {
                viewer.sendChatToPlayer(ChatMessageComponent.createFromText(this.getCommandUsage(sender)));
            }
        }
    }

    public int compareTo(Object o)
    {
        return 0;
    }
}
