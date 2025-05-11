/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  com.google.common.io.ByteStreams
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.StatCollector
 */
package net.ilexiconn.nationsgui.forge.server.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.SnackbarGUI;
import net.ilexiconn.nationsgui.forge.client.gui.radio.RequestGUI;
import net.ilexiconn.nationsgui.forge.client.gui.shop.ShopGUI;
import net.ilexiconn.nationsgui.forge.server.permission.PermissionCache;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.StatCollector;

public enum PacketCallbacks {
    PERMISSION{

        @Override
        public void handleCallback(EntityPlayer player, ByteArrayDataInput data) {
            PermissionCache.INSTANCE.addPermission(data.readUTF(), data.readBoolean());
        }
    }
    ,
    MONEY{

        @Override
        public void handleCallback(EntityPlayer player, ByteArrayDataInput data) {
            ShopGUI.CURRENT_MONEY = data.readDouble();
        }
    }
    ,
    TICKETS{

        @Override
        public void handleCallback(EntityPlayer player, ByteArrayDataInput data) {
        }
    }
    ,
    TELEPORT{

        @Override
        public void handleCallback(EntityPlayer player, ByteArrayDataInput data) {
        }
    }
    ,
    REMOVE_TICKET{

        @Override
        public void handleCallback(EntityPlayer player, ByteArrayDataInput data) {
        }
    }
    ,
    TICKET_RESOLVED{

        @Override
        public void handleCallback(EntityPlayer player, ByteArrayDataInput data) {
            String message = String.format(StatCollector.func_74838_a((String)"nationsgui.help.closed"), data.readUTF());
            String extra = data.readUTF();
            if (!extra.isEmpty()) {
                message = message + ": " + extra;
            }
            ClientProxy.SNACKBAR_LIST.add(new SnackbarGUI(message, false));
        }
    }
    ,
    NEW_TICKET{

        @Override
        public void handleCallback(EntityPlayer player, ByteArrayDataInput data) {
        }
    }
    ,
    REQUEST_SONG{

        @Override
        public void handleCallback(EntityPlayer player, ByteArrayDataInput data) {
            boolean success = data.readBoolean();
            String message = data.readUTF();
            RequestGUI.handleReturn(success);
            ClientProxy.SNACKBAR_LIST.add(new SnackbarGUI(StatCollector.func_74838_a((String)("nationsgui.radio." + message))));
        }
    };


    public abstract void handleCallback(EntityPlayer var1, ByteArrayDataInput var2);

    public void send(String ... extra) {
        ByteArrayDataOutput data = ByteStreams.newDataOutput();
        data.writeInt(this.ordinal() - 50);
        data.writeInt(extra.length);
        for (String s : extra) {
            data.writeUTF(s);
        }
        PacketDispatcher.sendPacketToServer((Packet)PacketDispatcher.getPacket((String)"nationsgui", (byte[])data.toByteArray()));
    }
}

