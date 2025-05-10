package net.ilexiconn.nationsgui.forge.server.command;

import cpw.mods.fml.relauncher.ReflectionHelper;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatMessageComponent;

public class CommandDebug extends CommandBase
{
    static File debugDir;

    public CommandDebug()
    {
        debugDir = new File(".", "debug");

        if (!debugDir.exists())
        {
            debugDir.mkdir();
        }
    }

    public String getCommandName()
    {
        return "staticdebug";
    }

    public String getCommandUsage(ICommandSender sender)
    {
        return "staticdebug <class> <fieldname>";
    }

    public void processCommand(ICommandSender sender, String[] args)
    {
        if (args.length > 1)
        {
            try
            {
                Class e = Class.forName(args[0]);
                Field f = ReflectionHelper.findField(e, new String[] {args[1]});
                boolean flag = f.isAccessible();
                f.setAccessible(true);
                File debugFile = new File(debugDir, sender.getCommandSenderName() + "-" + (new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss")).format(new Date()).toString().replace(' ', '-') + ".txt");

                if (!debugFile.exists())
                {
                    debugFile.createNewFile();
                }

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(debugFile)));
                writer.write("Field name : " + f.getName());
                writer.newLine();
                writer.write("Field Type : " + f.getType());
                writer.newLine();
                Iterator var9;
                Object o;

                if (f.get((Object)null) instanceof Map)
                {
                    Map l = (Map)f.get((Object)null);
                    writer.write("Map Size : " + l.size());
                    var9 = l.entrySet().iterator();

                    while (var9.hasNext())
                    {
                        o = var9.next();

                        if (o instanceof Entry)
                        {
                            Entry e1 = (Entry)o;
                            writer.write(e1.getKey() + " -> " + e1.getValue());
                            writer.newLine();
                        }
                    }
                }
                else if (f.get((Object)null) instanceof List)
                {
                    List l1 = (List)f.get((Object)null);
                    writer.write("Map Size : " + l1.size());
                    var9 = l1.iterator();

                    while (var9.hasNext())
                    {
                        o = var9.next();
                        writer.write(o.toString());
                        writer.newLine();
                    }
                }
                else
                {
                    writer.write(f.get((Object)null).toString());
                    writer.newLine();
                }

                writer.flush();
                writer.close();
                f.setAccessible(flag);
                this.send(sender, "Debug saved to : " + debugFile.getAbsolutePath());
            }
            catch (IOException var12)
            {
                var12.printStackTrace();
            }
        }
        else
        {
            this.send(sender, this.getCommandUsage(sender));
        }
    }

    private void send(ICommandSender sender, String string)
    {
        sender.sendChatToPlayer(ChatMessageComponent.createFromText(string));
    }

    public int compareTo(Object o)
    {
        return 0;
    }
}
