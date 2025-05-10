package net.ilexiconn.nationsgui.forge.client.gui.assistance;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AssistanceListingPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AssistanceUpdateAdminState;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class StaffButton extends AbstractAssistanceComponent
{
    private final int posX;
    private final int posY;
    private boolean active = false;

    public StaffButton(int posX, int posY)
    {
        this.posX = posX;
        this.posY = posY;

        if (ClientProxy.playersInAdminMode.containsKey(Minecraft.getMinecraft().thePlayer.username))
        {
            this.active = ((Boolean)ClientProxy.playersInAdminMode.get(Minecraft.getMinecraft().thePlayer.username)).booleanValue();
        }
    }

    public void draw(int mouseX, int mouseY, float partialTicks)
    {
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        this.drawTexturedModalRect(this.posX, this.posY, 384, 21, 27, 14);
        this.drawTexturedModalRect(this.posX + (this.active ? -2 : 14), this.posY, 381, 0, 15, 17);
    }

    public void onClick(int mouseX, int mouseY, int clickType)
    {
        if (mouseX >= this.posX && mouseX <= this.posX + 27 && mouseY >= this.posY && mouseY <= this.posY + 14)
        {
            this.active = !this.active;
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new AssistanceUpdateAdminState(this.active)));
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new AssistanceListingPacket()));
        }
    }

    public void update() {}

    public void keyTyped(char c, int key) {}

    public boolean isActive()
    {
        return this.active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }
}
