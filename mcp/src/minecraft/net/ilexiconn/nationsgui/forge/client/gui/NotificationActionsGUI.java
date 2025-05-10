package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.Notification;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.override.GenericOverride;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.NotificationActionPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class NotificationActionsGUI extends GuiScreen
{
    public static int GUI_SCALE = 3;
    public int xSize = 332;
    public int ySize = 109;
    public int guiLeft;
    public int guiTop;
    public String hoveredAction;
    public List<String> tooltipToDraw = new ArrayList();
    private Notification notification;

    public NotificationActionsGUI(Notification notification)
    {
        this.notification = notification;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = this.height - 170;
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0 && !this.hoveredAction.isEmpty())
        {
            this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);

            if (this.hoveredAction.equals("cancel") && this.notification.getActions() != null && this.notification.getActions().hasKey("deny"))
            {
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new NotificationActionPacket(this.notification.getActions().getCompoundTag("deny").getString("id"), this.notification.getActions().getCompoundTag("deny").getString("args"))));
            }
            else if (this.hoveredAction.equals("validate") && this.notification.getActions() != null && this.notification.getActions().hasKey("allow"))
            {
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new NotificationActionPacket(this.notification.getActions().getCompoundTag("allow").getString("id"), this.notification.getActions().getCompoundTag("allow").getString("args"))));
            }

            this.notification.setActionDone(true);
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.hoveredAction = "";
        ModernGui.bindTextureOverlayMain();
        ModernGui.drawScaledCustomSizeModalRect((float)this.guiLeft, (float)this.guiTop, (float)(221 * GUI_SCALE), (float)(686 * GUI_SCALE), 830 * GUI_SCALE, 275 * GUI_SCALE, this.xSize, this.ySize, (float)(1920 * GUI_SCALE), (float)(1033 * GUI_SCALE), false);
        ClientEventHandler.STYLE.bindTexture("overlay_icons");
        ModernGui.drawScaledCustomSizeModalRect((float)this.guiLeft + 0.5F, (float)this.guiTop + 3.5F, (float)(126 * this.notification.getColor().ordinal() * GenericOverride.GUI_SCALE), (float)(13 * GenericOverride.GUI_SCALE), 126 * GenericOverride.GUI_SCALE, 123 * GenericOverride.GUI_SCALE, 50, 49, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
        ClientEventHandler.STYLE.bindTexture("overlay_icons");
        ModernGui.drawScaledCustomSizeModalRect((float)this.guiLeft + 0.5F, (float)this.guiTop + 3.5F, (float)(126 * (this.notification.getIcon().ordinal() % 15) * GenericOverride.GUI_SCALE), (float)((381 + this.notification.getIcon().ordinal() / 15 * 123) * GenericOverride.GUI_SCALE), 126 * GenericOverride.GUI_SCALE, 123 * GenericOverride.GUI_SCALE, 50, 49, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
        ModernGui.drawScaledStringCustomFont(this.notification.getTitle().toUpperCase(), (float)(this.guiLeft + 57), (float)(this.guiTop + 20), -1314054, 0.9F, "left", true, "minecraftDungeons", 23);
        ModernGui.drawSectionStringCustomFont(this.notification.getContent(), (float)(this.guiLeft + 57), (float)(this.guiTop + 45), 15463162, 0.5F, "left", false, "georamaSemiBold", 30, 9, 500);
        ModernGui.bindTextureOverlayMain();

        if (mouseX > this.guiLeft + this.xSize - 160 && mouseX < this.guiLeft + this.xSize - 160 + 74 && mouseY > this.guiTop + this.ySize - 20 && mouseY < this.guiTop + this.ySize - 20 + 14)
        {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + this.xSize - 160), (float)(this.guiTop + this.ySize - 20), (float)(1734 * GenericOverride.GUI_SCALE), (float)(74 * GenericOverride.GUI_SCALE), 186 * GenericOverride.GUI_SCALE, 37 * GenericOverride.GUI_SCALE, 74, 14, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
            this.hoveredAction = "cancel";
            ModernGui.drawSectionStringCustomFont(this.notification.getActions().getCompoundTag("deny").getString("translatedTitle"), (float)(this.guiLeft + this.xSize - 160 + 37), (float)(this.guiTop + this.ySize - 20 + 3), 7239406, 0.5F, "center", false, "georamaSemiBold", 30, 9, 500);
        }
        else
        {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + this.xSize - 160), (float)(this.guiTop + this.ySize - 20), (float)(1734 * GenericOverride.GUI_SCALE), (float)(37 * GenericOverride.GUI_SCALE), 186 * GenericOverride.GUI_SCALE, 37 * GenericOverride.GUI_SCALE, 74, 14, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
            ModernGui.drawSectionStringCustomFont(this.notification.getActions().getCompoundTag("deny").getString("translatedTitle"), (float)(this.guiLeft + this.xSize - 160 + 37), (float)(this.guiTop + this.ySize - 20 + 3), 15463162, 0.5F, "center", false, "georamaSemiBold", 30, 9, 500);
        }

        ModernGui.bindTextureOverlayMain();

        if (mouseX > this.guiLeft + this.xSize - 80 && mouseX < this.guiLeft + this.xSize - 80 + 74 && mouseY > this.guiTop + this.ySize - 20 && mouseY < this.guiTop + this.ySize - 20 + 14)
        {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + this.xSize - 80), (float)(this.guiTop + this.ySize - 20), (float)(1734 * GenericOverride.GUI_SCALE), (float)(74 * GenericOverride.GUI_SCALE), 186 * GenericOverride.GUI_SCALE, 37 * GenericOverride.GUI_SCALE, 74, 14, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
            this.hoveredAction = "validate";
            ModernGui.drawSectionStringCustomFont(this.notification.getActions().getCompoundTag("allow").getString("translatedTitle"), (float)(this.guiLeft + this.xSize - 80 + 37), (float)(this.guiTop + this.ySize - 20 + 3), 7239406, 0.5F, "center", false, "georamaSemiBold", 30, 9, 500);
        }
        else
        {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + this.xSize - 80), (float)(this.guiTop + this.ySize - 20), (float)(1734 * GenericOverride.GUI_SCALE), (float)(0 * GenericOverride.GUI_SCALE), 186 * GenericOverride.GUI_SCALE, 37 * GenericOverride.GUI_SCALE, 74, 14, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
            ModernGui.drawSectionStringCustomFont(this.notification.getActions().getCompoundTag("allow").getString("translatedTitle"), (float)(this.guiLeft + this.xSize - 80 + 37), (float)(this.guiTop + this.ySize - 20 + 3), 15463162, 0.5F, "center", false, "georamaSemiBold", 30, 9, 500);
        }

        super.drawScreen(mouseX, mouseY, par3);
    }
}
