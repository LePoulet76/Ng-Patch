/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.assistance;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AbstractAssistanceComponent;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AssistanceListingPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AssistanceUpdateAdminState;
import net.minecraft.client.Minecraft;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;

public class StaffButton
extends AbstractAssistanceComponent {
    private final int posX;
    private final int posY;
    private boolean active = false;

    public StaffButton(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        if (ClientProxy.playersInAdminMode.containsKey(Minecraft.func_71410_x().field_71439_g.field_71092_bJ)) {
            this.active = ClientProxy.playersInAdminMode.get(Minecraft.func_71410_x().field_71439_g.field_71092_bJ);
        }
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        this.func_73729_b(this.posX, this.posY, 384, 21, 27, 14);
        this.func_73729_b(this.posX + (this.active ? -2 : 14), this.posY, 381, 0, 15, 17);
    }

    @Override
    public void onClick(int mouseX, int mouseY, int clickType) {
        if (mouseX >= this.posX && mouseX <= this.posX + 27 && mouseY >= this.posY && mouseY <= this.posY + 14) {
            this.active = !this.active;
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new AssistanceUpdateAdminState(this.active)));
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new AssistanceListingPacket()));
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void keyTyped(char c, int key) {
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

