package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionTeleportPacket;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

class FactionSelectGui$FactionButton extends Gui
{
    private String factionName;
    private final int posX;
    private final int posY;
    private int width;
    private int height;

    final FactionSelectGui this$0;

    public FactionSelectGui$FactionButton(FactionSelectGui var1, String factionName, int posX, int posY)
    {
        this.this$0 = var1;
        this.width = 108;
        this.height = 12;
        this.factionName = factionName;
        this.posX = posX;
        this.posY = posY;
    }

    public void draw(int mouseX, int mouseY)
    {
        if (mouseX >= this.posX && mouseY >= this.posY && mouseX < this.posX + this.width && mouseY < this.posY + this.height)
        {
            drawRect(this.posX, this.posY, this.posX + this.width, this.posY + this.height, -13948117);
            FactionSelectGui.access$000(this.this$0).getTextureManager().bindTexture(AbstractFirstConnectionGui.BACKGROUND);
            GL11.glColor3f(1.0F, 1.0F, 1.0F);

            if (FactionSelectGui.access$100(this.this$0))
            {
                this.drawTexturedModalRect(this.posX + this.width - 10, this.posY + this.height / 2 - 3, 35, 45, 6, 7);
            }
            else
            {
                this.drawTexturedModalRect(this.posX + this.width - 10, this.posY + this.height / 2 - 2, 26, 45, 5, 5);
            }
        }

        int var10002 = this.posX + 5;
        FactionSelectGui.access$200(this.this$0).fontRenderer.drawString(this.factionName, var10002, this.posY + this.height / 2 - 4, -1);
    }

    public void click(int mouseX, int mouseY)
    {
        if (mouseX >= this.posX && mouseY >= this.posY && mouseX < this.posX + this.width && mouseY < this.posY + this.height)
        {
            FactionTeleportPacket factionTeleportPacket = new FactionTeleportPacket();
            factionTeleportPacket.createFaction = FactionSelectGui.access$100(this.this$0);
            FactionSelectGui.access$300(this.this$0).displayGuiScreen((GuiScreen)null);

            if (FactionSelectGui.access$100(this.this$0))
            {
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(factionTeleportPacket));
                FactionSelectGui.access$400(this.this$0).thePlayer.sendChatMessage("/f create " + this.factionName);
                FactionSelectGui.access$500(this.this$0).thePlayer.sendChatMessage("/f claim");
            }
            else
            {
                FactionSelectGui.access$600(this.this$0).thePlayer.sendChatMessage("/f join " + this.factionName);
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(factionTeleportPacket));
            }
        }
    }
}
