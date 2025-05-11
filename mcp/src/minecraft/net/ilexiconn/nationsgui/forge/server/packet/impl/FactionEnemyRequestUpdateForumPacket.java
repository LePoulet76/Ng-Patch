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

public class FactionEnemyRequestUpdateForumPacket
implements IPacket {
    private String forumLink;
    private Integer requestID;

    public FactionEnemyRequestUpdateForumPacket(Integer requestID, String forumLink) {
        this.requestID = requestID;
        this.forumLink = forumLink;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeInt(this.requestID.intValue());
        data.writeUTF(this.forumLink);
    }
}

