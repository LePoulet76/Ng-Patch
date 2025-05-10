package net.ilexiconn.nationsgui.forge.server.command;

import java.util.ArrayList;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.Item;

public class SearchIdsCommand extends CommandBase
{
    public String getCommandName()
    {
        return "searchids";
    }

    public String getCommandUsage(ICommandSender icommandsender)
    {
        return null;
    }

    public void processCommand(ICommandSender icommandsender, String[] args)
    {
        ArrayList ids = new ArrayList();
        Item[] i = Item.itemsList;
        int var5 = i.length;

        for (int var6 = 0; var6 < var5; ++var6)
        {
            Item item = i[var6];

            if (item != null)
            {
                ids.add(Integer.valueOf(item.itemID));
            }
        }

        for (int var8 = 1; var8 <= 4096; ++var8)
        {
            if (!ids.contains(Integer.valueOf(var8)))
            {
                System.out.println(var8);
            }
        }
    }

    public int compareTo(Object o)
    {
        return 0;
    }
}
