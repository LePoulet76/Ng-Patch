/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  com.google.gson.Gson
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.gui.MailGUI;
import net.ilexiconn.nationsgui.forge.server.Mail;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;

public class RequestMailPacket
implements IPacket,
IClientPacket {
    public List mails = new ArrayList();

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.mails = (List)new Gson().fromJson(data.readUTF(), List.class);
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(new Gson().toJson((Object)this.mails));
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        ClientData.getMail().clear();
        for (Object object : this.mails) {
            String str = (String)object;
            String[] sp = str.split(":", 3);
            String subject = sp.length > 2 ? sp[1] : I18n.func_135053_a((String)"gui.mail.nosubject");
            Mail mail = new Mail(sp[0], subject, sp[sp.length - 1].trim());
            ClientData.addMail(mail);
        }
        MailGUI.loaded = true;
    }
}

