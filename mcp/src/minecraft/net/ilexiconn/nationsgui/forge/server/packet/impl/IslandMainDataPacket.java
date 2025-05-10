package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandMainGui;
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandPermsGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandMainDataPacket$1;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandMainDataPacket$10;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandMainDataPacket$2;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandMainDataPacket$3;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandMainDataPacket$4;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandMainDataPacket$5;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandMainDataPacket$6;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandMainDataPacket$7;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandMainDataPacket$8;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandMainDataPacket$9;
import net.minecraft.entity.player.EntityPlayer;

public class IslandMainDataPacket implements IPacket, IClientPacket
{
    public HashMap<String, Object> islandInfos = new HashMap();
    public boolean isPremium;
    public boolean isOp;
    public String serverNumber;

    public void fromBytes(ByteArrayDataInput data)
    {
        this.islandInfos = (HashMap)(new Gson()).fromJson(data.readUTF(), (new IslandMainDataPacket$1(this)).getType());
        this.isPremium = data.readBoolean();
        this.isOp = data.readBoolean();
        this.serverNumber = data.readUTF();
    }

    public void toBytes(ByteArrayDataOutput data) {}

    @SideOnly(Side.CLIENT)
    public void handleClientPacket(EntityPlayer player)
    {
        IslandMainGui.islandInfos = this.islandInfos;
        IslandMainGui.isPremium = this.isPremium;
        IslandMainGui.isOp = this.isOp;
        IslandMainGui.serverNumber = this.serverNumber;
        IslandMainGui.membersPerms = (HashMap)(new Gson()).fromJson((String)this.islandInfos.get("memberPermissions"), (new IslandMainDataPacket$2(this)).getType());
        IslandMainGui.visitorsPerms = (HashMap)(new Gson()).fromJson((String)this.islandInfos.get("visitorPermissions"), (new IslandMainDataPacket$3(this)).getType());
        IslandMainGui.islandFlags = (HashMap)(new Gson()).fromJson((String)this.islandInfos.get("islandFlags"), (new IslandMainDataPacket$4(this)).getType());
        IslandPermsGui.editedPerms = new HashMap();
        IslandMainGui.TABS.clear();
        IslandMainGui.TABS.add(new IslandMainDataPacket$5(this));

        if (this.islandInfos.get("isCreator").equals("true") || this.isOp)
        {
            IslandMainGui.TABS.add(new IslandMainDataPacket$6(this));
            IslandMainGui.TABS.add(new IslandMainDataPacket$7(this));
            IslandMainGui.TABS.add(new IslandMainDataPacket$8(this));
        }

        if (this.islandInfos.get("isCreator").equals("true") || ((ArrayList)this.islandInfos.get("playerPermissions")).contains("tab_properties") || this.isOp)
        {
            IslandMainGui.TABS.add(new IslandMainDataPacket$9(this));
        }

        if (this.islandInfos.get("isCreator").equals("true") || this.isOp)
        {
            IslandMainGui.TABS.add(new IslandMainDataPacket$10(this));
        }

        IslandMainGui.loaded = true;
    }
}
