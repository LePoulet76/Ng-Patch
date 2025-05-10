package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGui_OLD;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.Mail;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionMainJoinPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.MarkMailDeletedPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.NewMailPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.RequestMailPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class MailGUI extends TabbedGUI
{
    private Mail openMail;
    private boolean writingMail;
    private GuiButton backButton;
    private GuiButton deleteButton;
    private GuiButton replyButton;
    private GuiButton writeButton;
    private GuiButton sendButton;
    private GuiButton joinButton;
    private GuiTextField receiverText;
    private GuiTextField titleText;
    private GuiTextField contentText;
    private GuiScrollBar scrollBar;
    private String receiverName;
    public static boolean loaded = false;

    public MailGUI(EntityPlayer player)
    {
        super(player);
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.backButton = new TexturedButtonGUI(0, this.guiLeft + 10, this.guiTop + 196, 19, 19, "mail_open", 201, 72, "");
        this.deleteButton = new TexturedButtonGUI(1, this.guiLeft + 132 + 21, this.guiTop + 52, 19, 19, "mail_open", 182, 72, "");
        this.replyButton = new TexturedButtonGUI(2, this.guiLeft + 83 + 15, this.guiTop + 196, 74, 19, "mail_open", 182, 15, I18n.getString("gui.mail.reply"));
        this.writeButton = new TexturedButtonGUI(3, this.guiLeft + 98 + 21, this.guiTop + 52, 31, 19, "mail", 182, 15, "");
        this.sendButton = new TexturedButtonGUI(4, this.guiLeft + 98, this.guiTop + 197, 74, 19, "mail_send", 182, 15, I18n.getString("gui.mail.send"));
        this.joinButton = new TexturedButtonGUI(5, this.guiLeft + 83 + 15, this.guiTop + 196, 74, 19, "mail_open", 182, 15, I18n.getString("gui.mail.join"));
        this.receiverText = new GuiTextField(this.fontRenderer, this.guiLeft + 34, this.guiTop + 56, 133, 9);
        this.receiverText.setEnableBackgroundDrawing(false);
        this.receiverText.setMaxStringLength(16);
        this.titleText = new GuiTextField(this.fontRenderer, this.guiLeft + 15, this.guiTop + 80, 152, 9);
        this.titleText.setEnableBackgroundDrawing(false);
        this.titleText.setMaxStringLength(32);
        this.contentText = new TextAreaGUI(this.guiLeft + 11, this.guiTop + 100, 152);
        this.contentText.setMaxStringLength(225);
        this.scrollBar = new GuiScrollBar((float)(this.guiLeft + 162), (float)(this.guiTop + 75), 140);

        if (this.openMail != null)
        {
            this.openMail(this.openMail);
        }
        else
        {
            this.closeMail();
        }

        this.setWritingMail(this.writingMail);
        loaded = false;
        ClientData.getMail().clear();
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new RequestMailPacket()));
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.receiverText.updateCursorCounter();
        this.titleText.updateCursorCounter();
        this.contentText.updateCursorCounter();
        this.sendButton.enabled = this.receiverName != null && this.titleText.getText().length() > 2 && this.contentText.getText().length() > 2;
    }

    public void drawScreen(int mouseX, int mouseY)
    {
        if (this.openMail != null)
        {
            ClientEventHandler.STYLE.bindTexture("mail_open");
            this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
            this.mc.getTextureManager().bindTexture(AbstractClientPlayer.getLocationSkin(this.openMail.sender));
            drawScaledCustomSizeModalRect(this.guiLeft + 13, this.guiTop + 52, 8.0F, 8.0F, 8, 8, 16, 16, 64.0F, 64.0F);
            drawScaledCustomSizeModalRect(this.guiLeft + 13, this.guiTop + 52, 40.0F, 8.0F, 8, 8, 16, 16, 64.0F, 64.0F);
            this.fontRenderer.drawString(this.openMail.sender, this.guiLeft + 34, this.guiTop + 56, 16777215, true);
            this.fontRenderer.drawString(this.openMail.title, this.guiLeft + 16, this.guiTop + 80, 16777215, true);
            Gui.drawRect(this.guiLeft + 15, this.guiTop + 93, this.guiLeft + 152 + 15, this.guiTop + 94, -12303292);
            this.fontRenderer.drawSplitString(this.openMail.content, this.guiLeft + 16, this.guiTop + 99, 152, 11842740);
        }
        else if (this.writingMail)
        {
            ClientEventHandler.STYLE.bindTexture("mail_send");
            this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
            this.receiverText.drawTextBox();
            this.titleText.drawTextBox();
            this.contentText.drawTextBox();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            ResourceLocation i = AbstractClientPlayer.locationStevePng;

            if (this.receiverName != null)
            {
                i = AbstractClientPlayer.getLocationSkull(this.receiverName);
                AbstractClientPlayer.getDownloadImageSkin(i, this.receiverName);
            }

            this.mc.getTextureManager().bindTexture(i);
            drawScaledCustomSizeModalRect(this.guiLeft + 13, this.guiTop + 52, 8.0F, 8.0F, 8, 8, 16, 16, 64.0F, 64.0F);
            drawScaledCustomSizeModalRect(this.guiLeft + 13, this.guiTop + 52, 40.0F, 8.0F, 8, 8, 16, 16, 64.0F, 64.0F);
        }
        else
        {
            ClientEventHandler.STYLE.bindTexture("mail");
            this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
            this.drawCenteredString(this.fontRenderer, I18n.getString("gui.mail.inbox"), this.guiLeft + 52 + 11, this.guiTop + 59, 16777215);

            if (loaded)
            {
                GUIUtils.startGLScissor(this.guiLeft + 10, this.guiTop + 75, 145, 140);

                for (int var8 = 0; var8 < ClientData.getMail().size(); ++var8)
                {
                    Mail mail = (Mail)ClientData.getMail().get(var8);
                    byte x = 17;
                    int y = (int)((float)(84 + var8 * 35) + this.getSlide());
                    Gui.drawRect(this.guiLeft + x - 1, this.guiTop + y - 1, this.guiLeft + x + 16 + 1, this.guiTop + y + 16 + 1, -4605511);
                    Gui.drawRect(this.guiLeft + x - 1, this.guiTop + y + 25, this.guiLeft + x + 134, this.guiTop + y + 26, -12303292);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    ResourceLocation skin = AbstractClientPlayer.getLocationSkull(mail.sender);
                    AbstractClientPlayer.getDownloadImageSkin(skin, mail.sender);
                    this.mc.getTextureManager().bindTexture(skin);
                    drawScaledCustomSizeModalRect(this.guiLeft + x, this.guiTop + y, 8.0F, 8.0F, 8, 8, 16, 16, 64.0F, 64.0F);
                    drawScaledCustomSizeModalRect(this.guiLeft + x, this.guiTop + y, 40.0F, 8.0F, 8, 8, 16, 16, 64.0F, 64.0F);
                    this.fontRenderer.drawString(mail.title, this.guiLeft + x + 20, this.guiTop + y - 1, 16777215, true);
                    this.fontRenderer.drawString(mail.sender, this.guiLeft + x + 20, this.guiTop + y + 10, 8355711, true);
                    GL11.glColor3f(1.0F, 1.0F, 1.0F);

                    if (mouseX >= this.guiLeft + x - 6 && mouseX <= this.guiLeft + x + 139 && mouseY >= this.guiTop + y - 9 && mouseY <= this.guiTop + y + 24)
                    {
                        Gui.drawRect(this.guiLeft + x - 6, this.guiTop + y - 9, this.guiLeft + x + 139, this.guiTop + y + 25, 553648127);
                    }
                }

                GUIUtils.endGLScissor();
            }
            else
            {
                this.fontRenderer.drawString(I18n.getString("gui.mail.loading"), this.guiLeft + 12 + 71 - this.fontRenderer.getStringWidth(I18n.getString("gui.mail.loading")) / 2, this.guiTop + 85, 6645612, false);
                GL11.glColor3f(1.0F, 1.0F, 1.0F);
            }

            this.scrollBar.draw(mouseX, mouseY);
        }
    }

    public void drawTooltip(int mouseX, int mouseY) {}

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char typedChar, int keyCode)
    {
        if (this.writingMail)
        {
            if (this.receiverText.textboxKeyTyped(typedChar, keyCode))
            {
                this.receiverName = this.receiverText.getText();
            }
            else if (!this.titleText.textboxKeyTyped(typedChar, keyCode) && !this.contentText.textboxKeyTyped(typedChar, keyCode))
            {
                super.keyTyped(typedChar, keyCode);
            }
        }
        else
        {
            super.keyTyped(typedChar, keyCode);
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (this.openMail == null && !this.writingMail)
        {
            if (mouseButton == 0)
            {
                for (int i = 0; i < ClientData.getMail().size(); ++i)
                {
                    Mail mail = (Mail)ClientData.getMail().get(i);
                    byte x = 17;
                    int y = (int)((float)(84 + i * 35) + this.getSlide());

                    if (mouseX >= this.guiLeft + x - 6 && mouseX <= this.guiLeft + x + 139 && mouseY >= this.guiTop + y - 9 && mouseY <= this.guiTop + y + 24)
                    {
                        this.openMail(mail);
                        this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                    }
                }
            }
        }
        else if (this.writingMail)
        {
            this.receiverText.mouseClicked(mouseX, mouseY, mouseButton);
            this.titleText.mouseClicked(mouseX, mouseY, mouseButton);
            this.contentText.mouseClicked(mouseX, mouseY, mouseButton);
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private void openMail(Mail mail)
    {
        this.openMail = mail;
        this.buttonList.clear();
        this.buttonList.add(this.backButton);

        if (mail.title.contains("[Invitation]"))
        {
            this.buttonList.add(this.joinButton);
        }
        else
        {
            this.buttonList.add(this.replyButton);
        }
    }

    private void closeMail()
    {
        this.openMail = null;
        this.buttonList.clear();
        this.buttonList.add(this.deleteButton);
        this.buttonList.add(this.writeButton);
    }

    private void setWritingMail(boolean writingMail)
    {
        this.writingMail = writingMail;
        this.receiverName = null;
        this.receiverText.setText("");
        this.titleText.setText("");
        this.contentText.setText("");
        this.sendButton.enabled = false;
        this.buttonList.clear();

        if (this.writingMail)
        {
            this.buttonList.add(this.backButton);
            this.buttonList.add(this.sendButton);
            this.receiverText.setFocused(true);
        }
        else
        {
            this.buttonList.add(this.writeButton);
            this.buttonList.add(this.deleteButton);
            this.receiverText.setFocused(false);
        }
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton button)
    {
        if (button == this.backButton)
        {
            if (this.writingMail)
            {
                this.setWritingMail(false);
            }
            else
            {
                this.closeMail();
            }
        }
        else if (button == this.deleteButton)
        {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new MarkMailDeletedPacket()));
            ClientData.getMail().clear();
            Minecraft.getMinecraft().displayGuiScreen(this);
        }
        else if (button == this.writeButton)
        {
            this.setWritingMail(true);
        }
        else if (button == this.sendButton)
        {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new NewMailPacket(this.receiverName, this.titleText.getText(), this.contentText.getText())));
            this.setWritingMail(false);
        }
        else
        {
            Mail mail;

            if (button == this.replyButton)
            {
                mail = this.openMail;
                this.closeMail();
                this.setWritingMail(true);
                this.receiverText.setText(mail.sender);
                this.receiverName = mail.sender;
            }
            else if (button == this.joinButton)
            {
                mail = this.openMail;

                if (mail != null)
                {
                    PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionMainJoinPacket(mail.title.split(" ")[1])));
                    Minecraft.getMinecraft().displayGuiScreen(new FactionGui_OLD(mail.title.split(" ")[1]));
                }
            }
        }
    }

    public static void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight)
    {
        float f = 1.0F / tileWidth;
        float f1 = 1.0F / tileHeight;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(7);
        tessellator.addVertexWithUV((double)x, (double)(y + height), 0.0D, (double)(u * f), (double)((v + (float)vHeight) * f1));
        tessellator.addVertexWithUV((double)(x + width), (double)(y + height), 0.0D, (double)((u + (float)uWidth) * f), (double)((v + (float)vHeight) * f1));
        tessellator.addVertexWithUV((double)(x + width), (double)y, 0.0D, (double)((u + (float)uWidth) * f), (double)(v * f1));
        tessellator.addVertexWithUV((double)x, (double)y, 0.0D, (double)(u * f), (double)(v * f1));
        tessellator.draw();
    }

    private float getSlide()
    {
        return ClientData.getMail().size() > 4 ? (float)(-(ClientData.getMail().size() - 4) * 35) * this.scrollBar.getSliderValue() : 0.0F;
    }
}
