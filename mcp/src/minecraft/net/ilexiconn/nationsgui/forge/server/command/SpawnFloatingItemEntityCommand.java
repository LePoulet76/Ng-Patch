/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.command.CommandBase
 *  net.minecraft.command.ICommandSender
 *  net.minecraft.util.ChatMessageComponent
 */
package net.ilexiconn.nationsgui.forge.server.command;

import java.util.List;
import net.ilexiconn.nationsgui.forge.server.ServerUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatMessageComponent;

public class SpawnFloatingItemEntityCommand
extends CommandBase {
    public String func_71517_b() {
        return "spawnfloatingitem";
    }

    public String func_71518_a(ICommandSender icommandsender) {
        return "/spawnfloatingitem <id> <meta>";
    }

    public void func_71515_b(ICommandSender icommandsender, String[] astring) {
        if (astring.length > 1) {
            ServerUtils.spawnFloatingItemAt(icommandsender.func_82114_b().field_71574_a, icommandsender.func_82114_b().field_71572_b, icommandsender.func_82114_b().field_71573_c, icommandsender.func_130014_f_().func_72912_H().func_76065_j(), Integer.parseInt(astring[0]), Integer.parseInt(astring[1]), 4.0f, 0.0f, 0.0f, 0.0f, false);
        } else {
            icommandsender.func_70006_a(ChatMessageComponent.func_111066_d((String)"\u00a7cUsage: /spawnfloatingitem <id> <meta>"));
        }
    }

    public int compareTo(Object o) {
        return 0;
    }

    public List func_71516_a(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return null;
    }

    public boolean func_82358_a(String[] par1ArrayOfStr, int par2) {
        return par2 == 0;
    }
}

