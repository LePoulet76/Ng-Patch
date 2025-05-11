/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;

public class NewMailPacket
implements IPacket {
    public String receiver;
    public String title;
    public String content;

    public NewMailPacket(String receiver, String title, String content) {
        this.receiver = receiver;
        this.title = title;
        this.content = content;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.receiver = data.readUTF();
        this.title = data.readUTF();
        this.content = data.readUTF();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.receiver);
        data.writeUTF(this.title);
        data.writeUTF(this.content);
    }
}

