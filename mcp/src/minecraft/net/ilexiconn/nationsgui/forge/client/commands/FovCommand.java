/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.command.CommandBase
 *  net.minecraft.command.ICommandSender
 *  net.minecraft.util.ChatMessageComponent
 */
package net.ilexiconn.nationsgui.forge.client.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatMessageComponent;

public class FovCommand
extends CommandBase {
    public String func_71517_b() {
        return "fov";
    }

    public String func_71518_a(ICommandSender icommandsender) {
        return null;
    }

    public void func_71515_b(ICommandSender icommandsender, String[] astring) {
        block5: {
            if (astring.length >= 1) {
                try {
                    int fov = Integer.parseInt(astring[0]);
                    if (fov >= 0 && fov <= 150) {
                        float normalizedFov;
                        Minecraft.func_71410_x().field_71474_y.field_74334_X = normalizedFov = (float)(fov - 0) / 150.0f;
                        icommandsender.func_70006_a(ChatMessageComponent.func_111066_d((String)("Field of view set to " + fov)));
                        break block5;
                    }
                    icommandsender.func_70006_a(ChatMessageComponent.func_111066_d((String)"Field of view must be between 0 and 150"));
                }
                catch (NumberFormatException e) {
                    icommandsender.func_70006_a(ChatMessageComponent.func_111066_d((String)"Invalid number format. Please enter a valid FOV value."));
                }
            } else {
                icommandsender.func_70006_a(ChatMessageComponent.func_111066_d((String)"Usage: /fov <value>"));
            }
        }
    }

    public int compareTo(Object o) {
        return 0;
    }

    public boolean func_71519_b(ICommandSender par1ICommandSender) {
        return true;
    }
}

