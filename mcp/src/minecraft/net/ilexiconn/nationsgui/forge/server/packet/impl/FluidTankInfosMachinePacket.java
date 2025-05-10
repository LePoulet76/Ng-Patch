package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nationsglory.client.gui.FluidTankGUI;
import java.util.ArrayList;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FluidTankInfosMachinePacket$1;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class FluidTankInfosMachinePacket implements IPacket, IClientPacket
{
    private ArrayList<String> enterprises;
    private boolean isInPetrolEnterprise;
    private String petrolEnterpriseFlag;
    private boolean canOpen;

    public void fromBytes(ByteArrayDataInput data)
    {
        this.enterprises = (ArrayList)(new Gson()).fromJson(data.readUTF(), (new FluidTankInfosMachinePacket$1(this)).getType());
        this.isInPetrolEnterprise = data.readBoolean();
        this.petrolEnterpriseFlag = data.readUTF();
        this.canOpen = data.readBoolean();
    }

    public void toBytes(ByteArrayDataOutput data) {}

    @SideOnly(Side.CLIENT)
    public void handleClientPacket(EntityPlayer player)
    {
        if (this.canOpen)
        {
            FluidTankGUI.enterprises = this.enterprises;
            FluidTankGUI.isInPetrolEnterprise = this.isInPetrolEnterprise;
            FluidTankGUI.petrolEnterpriseFlag = this.petrolEnterpriseFlag;
            FluidTankGUI.loaded = true;
        }
        else
        {
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
        }
    }
}
