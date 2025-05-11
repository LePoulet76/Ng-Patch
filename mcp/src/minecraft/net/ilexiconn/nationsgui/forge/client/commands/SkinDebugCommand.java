/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.command.CommandBase
 *  net.minecraft.command.ICommandSender
 *  net.minecraft.util.ChatMessageComponent
 */
package net.ilexiconn.nationsgui.forge.client.commands;

import java.io.IOException;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractSkin;
import net.ilexiconn.nationsgui.forge.client.util.Transform;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatMessageComponent;
import org.json.simple.parser.ParseException;

public class SkinDebugCommand
extends CommandBase {
    public String func_71517_b() {
        return "skindebug";
    }

    public String func_71518_a(ICommandSender icommandsender) {
        return null;
    }

    public void func_71515_b(final ICommandSender icommandsender, String[] astring) {
        if (astring.length >= 1 && astring[0].equals("reload")) {
            icommandsender.func_70006_a(ChatMessageComponent.func_111066_d((String)"Reloading skins..."));
            Thread thread = new Thread(new Runnable(){

                @Override
                public void run() {
                    try {
                        ClientProxy.SKIN_MANAGER.loadSkins();
                        icommandsender.func_70006_a(ChatMessageComponent.func_111066_d((String)"Skins reloaded"));
                    }
                    catch (IOException | ParseException e) {
                        e.printStackTrace();
                        icommandsender.func_70006_a(ChatMessageComponent.func_111066_d((String)"Skins reload failed"));
                    }
                }
            });
            thread.start();
            return;
        }
        AbstractSkin skin = ClientProxy.SKIN_MANAGER.getSkinFromID(astring[0]);
        if (skin == null) {
            icommandsender.func_70006_a(ChatMessageComponent.func_111066_d((String)"Skin not found"));
            return;
        }
        if (astring.length >= 2 && astring[1].equals("reload")) {
            skin.reload();
            icommandsender.func_70006_a(ChatMessageComponent.func_111066_d((String)"Skin reloaded"));
            return;
        }
        if (astring.length < 3) {
            return;
        }
        Transform transform = skin.getTransform(astring[1]);
        switch (astring[2]) {
            case "scale": {
                transform.setScale(Double.parseDouble(astring[3]));
                break;
            }
            case "offsetX": {
                transform.setOffsetX(Double.parseDouble(astring[3]));
                break;
            }
            case "offsetY": {
                transform.setOffsetY(Double.parseDouble(astring[3]));
                break;
            }
            case "offsetZ": {
                transform.setOffsetZ(Double.parseDouble(astring[3]));
                break;
            }
            case "rotateX": {
                transform.setRotateX(Double.parseDouble(astring[3]));
                break;
            }
            case "rotateY": {
                transform.setRotateY(Double.parseDouble(astring[3]));
                break;
            }
            case "rotateZ": {
                transform.setRotateZ(Double.parseDouble(astring[3]));
                break;
            }
            case "info": {
                icommandsender.func_70006_a(ChatMessageComponent.func_111066_d((String)("S : " + transform.getScale())));
                icommandsender.func_70006_a(ChatMessageComponent.func_111066_d((String)("oX : " + transform.getOffsetX())));
                icommandsender.func_70006_a(ChatMessageComponent.func_111066_d((String)("oY : " + transform.getOffsetY())));
                icommandsender.func_70006_a(ChatMessageComponent.func_111066_d((String)("oZ : " + transform.getOffsetZ())));
                icommandsender.func_70006_a(ChatMessageComponent.func_111066_d((String)("rX : " + transform.getRotateX())));
                icommandsender.func_70006_a(ChatMessageComponent.func_111066_d((String)("rY : " + transform.getRotateY())));
                icommandsender.func_70006_a(ChatMessageComponent.func_111066_d((String)("rZ : " + transform.getRotateZ())));
                break;
            }
            default: {
                icommandsender.func_70006_a(ChatMessageComponent.func_111066_d((String)"Parameter invalid"));
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

