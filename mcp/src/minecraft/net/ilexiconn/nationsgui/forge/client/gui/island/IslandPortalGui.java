package net.ilexiconn.nationsgui.forge.client.gui.island;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandPortalPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class IslandPortalGui extends GuiScreen
{
    public int posX;
    public int posY;
    public int posZ;
    protected int xSize = 319;
    protected int ySize = 128;
    public int guiLeft;
    public int guiTop;
    public boolean isIsland = false;
    private GuiButton cancelButton;
    private GuiButton confirmButton;
    private GuiTextField codeInput;

    public IslandPortalGui(int posX, int posY, int posZ)
    {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        if (!this.isIsland)
        {
            this.codeInput.updateCursorCounter();
        }
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.codeInput = new GuiTextField(this.fontRenderer, this.guiLeft + 56, this.guiTop + 68, 247, 10);
        this.codeInput.setEnableBackgroundDrawing(false);
        this.codeInput.setMaxStringLength(20);

        if (ClientProxy.serverType.equalsIgnoreCase("build"))
        {
            this.isIsland = true;
            this.codeInput.setText(System.currentTimeMillis() + "");
        }

        this.cancelButton = new GuiButton(0, this.guiLeft + 53, this.guiTop + 95, 118, 20, I18n.getString("island.portal.cancel"));
        this.confirmButton = new GuiButton(1, this.guiLeft + 183, this.guiTop + 95, 118, 20, this.isIsland ? I18n.getString("island.portal.copy") : I18n.getString("island.portal.confirm"));
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        this.drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        ClientEventHandler.STYLE.bindTexture("faction_modal");
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
        super.drawScreen(mouseX, mouseY, par3);

        if (mouseX >= this.guiLeft + 304 && mouseX <= this.guiLeft + 304 + 9 && mouseY >= this.guiTop - 6 && mouseY <= this.guiTop - 6 + 10)
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 304), (float)(this.guiTop - 6), 0, 143, 9, 10, 512.0F, 512.0F, false);
        }
        else
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 304), (float)(this.guiTop - 6), 0, 133, 9, 10, 512.0F, 512.0F, false);
        }

        this.drawScaledString(I18n.getString("island.portal.title"), this.guiLeft + 53, this.guiTop + 16, 1644825, 1.3F, false, false);

        if (ClientProxy.serverType.equalsIgnoreCase("ng"))
        {
            this.drawScaledString(I18n.getString("island.portal.ng.description_1"), this.guiLeft + 53, this.guiTop + 25, 1644825, 1.0F, false, false);
            this.drawScaledString(I18n.getString("island.portal.ng.description_2"), this.guiLeft + 53, this.guiTop + 35, 1644825, 1.0F, false, false);
            this.drawScaledString(I18n.getString("island.portal.ng.description_3"), this.guiLeft + 53, this.guiTop + 45, 1644825, 1.0F, false, false);
        }
        else
        {
            this.drawScaledString(I18n.getString("island.portal.build.description_1"), this.guiLeft + 53, this.guiTop + 30, 1644825, 1.0F, false, false);
            this.drawScaledString(I18n.getString("island.portal.build.description_2"), this.guiLeft + 53, this.guiTop + 40, 1644825, 1.0F, false, false);
        }

        ClientEventHandler.STYLE.bindTexture("faction_modal");
        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 53), (float)(this.guiTop + 62), 0, 158, 249, 20, 512.0F, 512.0F, false);
        this.codeInput.drawTextBox();
        this.cancelButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
        this.confirmButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char typedChar, int keyCode)
    {
        if (!this.isIsland)
        {
            this.codeInput.textboxKeyTyped(typedChar, keyCode);
        }

        super.keyTyped(typedChar, keyCode);
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseX > this.guiLeft + 53 && mouseX < this.guiLeft + 53 + 118 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 20)
        {
            this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
        }

        if (!this.codeInput.getText().isEmpty() && mouseX > this.guiLeft + 183 && mouseX < this.guiLeft + 183 + 118 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 20)
        {
            this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandPortalPacket(this.codeInput.getText(), this.posX, this.posY, this.posZ)));

            if (this.isIsland)
            {
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Clipboard clipboard = toolkit.getSystemClipboard();
                StringSelection strSel = new StringSelection(this.codeInput.getText());
                clipboard.setContents(strSel, (ClipboardOwner)null);
            }

            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
        }

        if (!this.isIsland)
        {
            this.codeInput.mouseClicked(mouseX, mouseY, mouseButton);
        }

        if (mouseButton == 0 && mouseX > this.guiLeft + 304 && mouseX < this.guiLeft + 304 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10)
        {
            this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void drawScaledString(String text, int x, int y, int color, float scale, boolean centered, boolean shadow)
    {
        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, scale);
        float newX = (float)x;

        if (centered)
        {
            newX = (float)x - (float)this.fontRenderer.getStringWidth(text) * scale / 2.0F;
        }

        if (shadow)
        {
            this.fontRenderer.drawString(text, (int)(newX / scale), (int)((float)(y + 1) / scale), (color & 16579836) >> 2 | color & -16777216, false);
        }

        this.fontRenderer.drawString(text, (int)(newX / scale), (int)((float)y / scale), color, false);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
