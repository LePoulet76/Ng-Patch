package net.ilexiconn.nationsgui.forge.client.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatMessageComponent;

public class FovCommand extends CommandBase
{
    public String getCommandName()
    {
        return "fov";
    }

    public String getCommandUsage(ICommandSender icommandsender)
    {
        return null;
    }

    public void processCommand(ICommandSender icommandsender, String[] astring)
    {
        if (astring.length >= 1)
        {
            try
            {
                int e = Integer.parseInt(astring[0]);

                if (e >= 0 && e <= 150)
                {
                    float normalizedFov = (float)(e - 0) / 150.0F;
                    Minecraft.getMinecraft().gameSettings.fovSetting = normalizedFov;
                    icommandsender.sendChatToPlayer(ChatMessageComponent.createFromText("Field of view set to " + e));
                }
                else
                {
                    icommandsender.sendChatToPlayer(ChatMessageComponent.createFromText("Field of view must be between 0 and 150"));
                }
            }
            catch (NumberFormatException var5)
            {
                icommandsender.sendChatToPlayer(ChatMessageComponent.createFromText("Invalid number format. Please enter a valid FOV value."));
            }
        }
        else
        {
            icommandsender.sendChatToPlayer(ChatMessageComponent.createFromText("Usage: /fov <value>"));
        }
    }

    public int compareTo(Object o)
    {
        return 0;
    }

    /**
     * Returns true if the given command sender is allowed to use this command.
     */
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
    {
        return true;
    }
}
