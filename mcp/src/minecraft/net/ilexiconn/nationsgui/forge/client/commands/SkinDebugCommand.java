package net.ilexiconn.nationsgui.forge.client.commands;

import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.commands.SkinDebugCommand$1;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractSkin;
import net.ilexiconn.nationsgui.forge.client.util.Transform;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatMessageComponent;

public class SkinDebugCommand extends CommandBase
{
    public String getCommandName()
    {
        return "skindebug";
    }

    public String getCommandUsage(ICommandSender icommandsender)
    {
        return null;
    }

    public void processCommand(ICommandSender icommandsender, String[] astring)
    {
        if (astring.length >= 1 && astring[0].equals("reload"))
        {
            icommandsender.sendChatToPlayer(ChatMessageComponent.createFromText("Reloading skins..."));
            Thread skin1 = new Thread(new SkinDebugCommand$1(this, icommandsender));
            skin1.start();
        }
        else
        {
            AbstractSkin skin = ClientProxy.SKIN_MANAGER.getSkinFromID(astring[0]);

            if (skin == null)
            {
                icommandsender.sendChatToPlayer(ChatMessageComponent.createFromText("Skin not found"));
            }
            else if (astring.length >= 2 && astring[1].equals("reload"))
            {
                skin.reload();
                icommandsender.sendChatToPlayer(ChatMessageComponent.createFromText("Skin reloaded"));
            }
            else if (astring.length >= 3)
            {
                Transform transform = skin.getTransform(astring[1]);
                String var5 = astring[2];
                byte var6 = -1;

                switch (var5.hashCode())
                {
                    case -1548407259:
                        if (var5.equals("offsetX"))
                        {
                            var6 = 1;
                        }

                        break;

                    case -1548407258:
                        if (var5.equals("offsetY"))
                        {
                            var6 = 2;
                        }

                        break;

                    case -1548407257:
                        if (var5.equals("offsetZ"))
                        {
                            var6 = 3;
                        }

                        break;

                    case 3237038:
                        if (var5.equals("info"))
                        {
                            var6 = 7;
                        }

                        break;

                    case 109250890:
                        if (var5.equals("scale"))
                        {
                            var6 = 0;
                        }

                        break;

                    case 1384173149:
                        if (var5.equals("rotateX"))
                        {
                            var6 = 4;
                        }

                        break;

                    case 1384173150:
                        if (var5.equals("rotateY"))
                        {
                            var6 = 5;
                        }

                        break;

                    case 1384173151:
                        if (var5.equals("rotateZ"))
                        {
                            var6 = 6;
                        }
                }

                switch (var6)
                {
                    case 0:
                        transform.setScale(Double.parseDouble(astring[3]));
                        break;

                    case 1:
                        transform.setOffsetX(Double.parseDouble(astring[3]));
                        break;

                    case 2:
                        transform.setOffsetY(Double.parseDouble(astring[3]));
                        break;

                    case 3:
                        transform.setOffsetZ(Double.parseDouble(astring[3]));
                        break;

                    case 4:
                        transform.setRotateX(Double.parseDouble(astring[3]));
                        break;

                    case 5:
                        transform.setRotateY(Double.parseDouble(astring[3]));
                        break;

                    case 6:
                        transform.setRotateZ(Double.parseDouble(astring[3]));
                        break;

                    case 7:
                        icommandsender.sendChatToPlayer(ChatMessageComponent.createFromText("S : " + transform.getScale()));
                        icommandsender.sendChatToPlayer(ChatMessageComponent.createFromText("oX : " + transform.getOffsetX()));
                        icommandsender.sendChatToPlayer(ChatMessageComponent.createFromText("oY : " + transform.getOffsetY()));
                        icommandsender.sendChatToPlayer(ChatMessageComponent.createFromText("oZ : " + transform.getOffsetZ()));
                        icommandsender.sendChatToPlayer(ChatMessageComponent.createFromText("rX : " + transform.getRotateX()));
                        icommandsender.sendChatToPlayer(ChatMessageComponent.createFromText("rY : " + transform.getRotateY()));
                        icommandsender.sendChatToPlayer(ChatMessageComponent.createFromText("rZ : " + transform.getRotateZ()));
                        break;

                    default:
                        icommandsender.sendChatToPlayer(ChatMessageComponent.createFromText("Parameter invalid"));
                }
            }
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
