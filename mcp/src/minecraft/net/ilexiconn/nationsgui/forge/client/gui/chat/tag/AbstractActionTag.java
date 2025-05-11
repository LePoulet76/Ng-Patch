/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 */
package net.ilexiconn.nationsgui.forge.client.gui.chat.tag;

import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.gui.chat.tag.AbstractChatTag;
import net.ilexiconn.nationsgui.forge.server.notifications.NotificationManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public abstract class AbstractActionTag
extends AbstractChatTag {
    private NBTTagCompound tagCompound = null;

    public AbstractActionTag(Map<String, String> parameters) throws Exception {
        super(parameters);
        String id = parameters.get("action");
        if (id == null) {
            return;
        }
        this.tagCompound = new NBTTagCompound();
        this.tagCompound.func_74778_a("id", id);
        NBTTagCompound data = new NBTTagCompound();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            data.func_74778_a(entry.getKey(), entry.getValue());
        }
        this.tagCompound.func_74782_a("data", (NBTBase)data);
    }

    protected void doAction() {
        if (this.tagCompound != null) {
            NotificationManager.executeAction((EntityPlayer)Minecraft.func_71410_x().field_71439_g, this.tagCompound.func_74779_i("id"), this.tagCompound.func_74775_l("data"));
        }
    }
}

