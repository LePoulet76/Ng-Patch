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

public class ChallengeAcceptPacket
implements IPacket {
    private String playerAtt;
    private String playerDef;
    private String kit;
    private int bet;

    public ChallengeAcceptPacket(String playerAtt, String playerDef, String kit, int bet) {
        this.playerAtt = playerAtt;
        this.playerDef = playerDef;
        this.kit = kit;
        this.bet = bet;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.playerAtt);
        data.writeUTF(this.playerDef);
        data.writeUTF(this.kit);
        data.writeInt(this.bet);
    }
}

