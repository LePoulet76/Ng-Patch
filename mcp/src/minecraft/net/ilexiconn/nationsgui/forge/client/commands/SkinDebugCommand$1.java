package net.ilexiconn.nationsgui.forge.client.commands;

import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatMessageComponent;
import org.json.simple.parser.ParseException;

class SkinDebugCommand$1 implements Runnable
{
    final ICommandSender val$icommandsender;

    final SkinDebugCommand this$0;

    SkinDebugCommand$1(SkinDebugCommand this$0, ICommandSender var2)
    {
        this.this$0 = this$0;
        this.val$icommandsender = var2;
    }

    public void run()
    {
        try
        {
            ClientProxy.SKIN_MANAGER.loadSkins();
            this.val$icommandsender.sendChatToPlayer(ChatMessageComponent.createFromText("Skins reloaded"));
        }
        catch (ParseException var2)
        {
            var2.printStackTrace();
            this.val$icommandsender.sendChatToPlayer(ChatMessageComponent.createFromText("Skins reload failed"));
        }
    }
}
