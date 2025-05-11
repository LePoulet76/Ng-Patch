/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.ReflectionHelper
 *  net.minecraft.command.CommandBase
 *  net.minecraft.command.ICommandSender
 *  net.minecraft.util.ChatMessageComponent
 */
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
import java.util.List;
import java.util.Map;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatMessageComponent;

public class CommandDebug
extends CommandBase {
    static File debugDir;

    public CommandDebug() {
        debugDir = new File(".", "debug");
        if (!debugDir.exists()) {
            debugDir.mkdir();
        }
    }

    public String func_71517_b() {
        return "staticdebug";
    }

    public String func_71518_a(ICommandSender sender) {
        return "staticdebug <class> <fieldname>";
    }

    public void func_71515_b(ICommandSender sender, String[] args) {
        if (args.length > 1) {
            try {
                Class<?> clazz = Class.forName(args[0]);
                Field f = ReflectionHelper.findField(clazz, (String[])new String[]{args[1]});
                boolean flag = f.isAccessible();
                f.setAccessible(true);
                File debugFile = new File(debugDir, sender.func_70005_c_() + "-" + new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss").format(new Date()).toString().replace(' ', '-') + ".txt");
                if (!debugFile.exists()) {
                    debugFile.createNewFile();
                }
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(debugFile)));
                writer.write("Field name : " + f.getName());
                writer.newLine();
                writer.write("Field Type : " + f.getType());
                writer.newLine();
                if (f.get(null) instanceof Map) {
                    Map m = (Map)f.get(null);
                    writer.write("Map Size : " + m.size());
                    for (Map.Entry o : m.entrySet()) {
                        if (!(o instanceof Map.Entry)) continue;
                        Map.Entry e = o;
                        writer.write(e.getKey() + " -> " + e.getValue());
                        writer.newLine();
                    }
                } else if (f.get(null) instanceof List) {
                    List l = (List)f.get(null);
                    writer.write("Map Size : " + l.size());
                    for (Object o : l) {
                        writer.write(o.toString());
                        writer.newLine();
                    }
                } else {
                    writer.write(f.get(null).toString());
                    writer.newLine();
                }
                writer.flush();
                writer.close();
                f.setAccessible(flag);
                this.send(sender, "Debug saved to : " + debugFile.getAbsolutePath());
            }
            catch (IOException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | SecurityException e) {
                e.printStackTrace();
            }
        } else {
            this.send(sender, this.func_71518_a(sender));
        }
    }

    private void send(ICommandSender sender, String string) {
        sender.func_70006_a(ChatMessageComponent.func_111066_d((String)string));
    }

    public int compareTo(Object o) {
        return 0;
    }
}

