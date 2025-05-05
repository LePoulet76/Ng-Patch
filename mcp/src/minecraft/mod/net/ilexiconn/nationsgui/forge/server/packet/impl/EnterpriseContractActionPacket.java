package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.entity.player.EntityPlayer;

public class EnterpriseContractActionPacket implements IPacket, IClientPacket
{
    public String enterpriseName;
    public int contractId;
    public String action;
    private Integer loanRefundAmount;

    public EnterpriseContractActionPacket(String enterpriseName, Integer contractId, String action, Integer loanRefundAmount)
    {
        this.enterpriseName = enterpriseName;
        this.contractId = contractId.intValue();
        this.action = action;
        this.loanRefundAmount = loanRefundAmount;
    }

    public void fromBytes(ByteArrayDataInput data) {}

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.enterpriseName);
        data.writeInt(this.contractId);
        data.writeUTF(this.action);
        data.writeInt(this.loanRefundAmount.intValue());
    }

    public void handleClientPacket(EntityPlayer player)
    {
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterpriseContractDataPacket((String)EnterpriseGui.enterpriseInfos.get("name"))));
    }
}
