package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.TexturedCenteredButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseSaveSettingsDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

public class EnterpriseSettingsGUI extends TabbedEnterpriseGUI
{
    private GuiScrollBarFaction scrollBarLogs;
    private GuiTextField inputDescription;
    private boolean checkboxOpen;
    private DynamicTexture flagTexture;
    private GuiButton disbandButton;
    private boolean saved = false;
    private boolean servicesEdit = false;
    private ArrayList<GuiTextField> linesTextField = new ArrayList();

    public EnterpriseSettingsGUI()
    {
        this.checkboxOpen = ((Boolean)EnterpriseGui.enterpriseInfos.get("isOpen")).booleanValue();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.inputDescription = new GuiTextField(this.fontRenderer, this.guiLeft + 133, this.guiTop + 95, 246, 10);
        this.inputDescription.setEnableBackgroundDrawing(false);
        this.inputDescription.setMaxStringLength(250);
        this.inputDescription.setText((String)EnterpriseGui.enterpriseInfos.get("description"));
        this.inputDescription.setCursorPositionZero();
        List services = Arrays.asList(((String)EnterpriseGui.enterpriseInfos.get("services")).split("##"));

        for (int i = 0; i < 18; ++i)
        {
            GuiTextField lineTextField = new GuiTextField(this.fontRenderer, this.guiLeft + 131, this.guiTop + 37 + i * 10, 246, 10);
            lineTextField.setEnableBackgroundDrawing(false);
            lineTextField.setMaxStringLength(41);

            if (i < services.size())
            {
                lineTextField.setText((String)services.get(i));
            }

            lineTextField.setCursorPositionZero();
            this.linesTextField.add(lineTextField);
        }

        this.disbandButton = new TexturedCenteredButtonGUI(1, this.guiLeft + 10, this.guiTop + 220, 100, 20, "faction_btn", 0, 0, I18n.getString("enterprise.settings.close_button"));
        this.scrollBarLogs = new GuiScrollBarFaction((float)(this.guiLeft + 377), (float)(this.guiTop + 149), 63);

        if (!EnterpriseGui.enterpriseInfos.get("playerRole").equals("leader"))
        {
            this.disbandButton.enabled = false;
        }
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        if (!this.servicesEdit)
        {
            this.inputDescription.updateCursorCounter();
        }
        else
        {
            Iterator var1 = this.linesTextField.iterator();

            while (var1.hasNext())
            {
                GuiTextField lineTextField = (GuiTextField)var1.next();
                lineTextField.updateCursorCounter();
            }
        }
    }

    public void drawScreen(int mouseX, int mouseY)
    {
        ClientEventHandler.STYLE.bindTexture("enterprise_settings");
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);

        if (EnterpriseGui.enterpriseInfos.get("playerRole").equals("leader"))
        {
            this.disbandButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
        }

        String tooltipToDraw = "";
        this.drawScaledString(I18n.getString("faction.settings.title"), this.guiLeft + 131, this.guiTop + 16, 1644825, 1.4F, false, false);

        if (!this.servicesEdit)
        {
            if (this.flagTexture == null && EnterpriseGui.enterpriseInfos.get("flagImage") != null && !((String)EnterpriseGui.enterpriseInfos.get("flagImage")).isEmpty())
            {
                BufferedImage logs = decodeToImage((String)EnterpriseGui.enterpriseInfos.get("flagImage"));
                this.flagTexture = new DynamicTexture(logs);
            }

            if (this.flagTexture != null)
            {
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.flagTexture.getGlTextureId());
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 156), (float)(this.guiTop + 40), 0.0F, 0.0F, 150, 150, 32, 32, 150.0F, 150.0F, false);
            }

            this.drawScaledString(I18n.getString("enterprise.settings.edit_flag"), this.guiLeft + 292, this.guiTop + 52, 16777215, 1.0F, true, false);
            this.drawScaledString(I18n.getString("faction.settings.description"), this.guiLeft + 131, this.guiTop + 80, 1644825, 0.9F, false, false);
            this.inputDescription.drawTextBox();
            ClientEventHandler.STYLE.bindTexture("enterprise_settings");

            if (this.checkboxOpen)
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 130), (float)(this.guiTop + 113), 159, 250, 10, 10, 512.0F, 512.0F, false);
            }
            else
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 130), (float)(this.guiTop + 113), 169, 250, 10, 10, 512.0F, 512.0F, false);
            }

            this.drawScaledString(I18n.getString("enterprise.settings.open_text"), this.guiLeft + 145, this.guiTop + 114, 1644825, 1.0F, false, false);
            this.drawScaledString(I18n.getString("faction.settings.logs"), this.guiLeft + 131, this.guiTop + 136, 1644825, 0.9F, false, false);
            ArrayList var8 = (ArrayList)EnterpriseGui.enterpriseInfos.get("logs");
            GUIUtils.startGLScissor(this.guiLeft + 131, this.guiTop + 146, 246, 70);

            for (int lineTextField = 0; lineTextField < var8.size(); ++lineTextField)
            {
                int offsetX = this.guiLeft + 131;
                Float offsetY = Float.valueOf((float)(this.guiTop + 146 + lineTextField * 20) + this.getSlide());
                ClientEventHandler.STYLE.bindTexture("enterprise_settings");
                ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)offsetY.intValue(), 131, 146, 246, 18, 512.0F, 512.0F, false);
                this.drawScaledString(((String)var8.get(lineTextField)).split("##")[2] + " " + I18n.getString("enterprise.settings.logs." + ((String)var8.get(lineTextField)).split("##")[1]).replace("#target#", ((String)var8.get(lineTextField)).split("##")[3]), offsetX + 6, offsetY.intValue() + 6, 11842740, 0.85F, false, false);
                ClientEventHandler.STYLE.bindTexture("enterprise_settings");
                ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 232), (float)(offsetY.intValue() + 3), 148, 250, 10, 11, 512.0F, 512.0F, false);

                if (mouseX > offsetX + 232 && mouseX < offsetX + 232 + 10 && (float)mouseY > offsetY.floatValue() + 3.0F && (float)mouseY < offsetY.floatValue() + 3.0F + 11.0F)
                {
                    tooltipToDraw = ((String)var8.get(lineTextField)).split("##")[0];
                }
            }

            GUIUtils.endGLScissor();

            if (mouseX > this.guiLeft + 130 && mouseX < this.guiLeft + 130 + 254 && mouseY > this.guiTop + 145 && mouseY < this.guiTop + 145 + 72)
            {
                this.scrollBarLogs.draw(mouseX, mouseY);
            }
        }
        else
        {
            ClientEventHandler.STYLE.bindTexture("enterprise_settings");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 130), (float)(this.guiTop + 36), 223, 250, 254, 181, 512.0F, 512.0F, false);
            Iterator var9 = this.linesTextField.iterator();

            while (var9.hasNext())
            {
                GuiTextField var10 = (GuiTextField)var9.next();
                var10.drawTextBox();
            }
        }

        this.drawScaledString(I18n.getString("enterprise.settings.services_button"), this.guiLeft + 184, this.guiTop + 225, 16777215, 1.0F, true, false);
        this.drawScaledString(I18n.getString("faction.settings.save_button"), this.guiLeft + 334, this.guiTop + 225, 16777215, 1.0F, true, false);

        if (!tooltipToDraw.isEmpty())
        {
            this.drawTooltip(tooltipToDraw, mouseX, mouseY);
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            if (!this.servicesEdit && mouseX >= this.guiLeft + 217 && mouseX <= this.guiLeft + 217 + 150 && mouseY >= this.guiTop + 47 && mouseY <= this.guiTop + 47 + 18)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen(new EnterpriseFlagGui((String)EnterpriseGui.enterpriseInfos.get("name")));
            }

            if (!this.saved && mouseX >= this.guiLeft + 284 && mouseX <= this.guiLeft + 284 + 100 && mouseY >= this.guiTop + 221 && mouseY <= this.guiTop + 221 + 15)
            {
                String services = "";
                GuiTextField lineTextField1;

                for (Iterator lineTextField = this.linesTextField.iterator(); lineTextField.hasNext(); services = services + "##" + lineTextField1.getText())
                {
                    lineTextField1 = (GuiTextField)lineTextField.next();
                }

                services = services.replaceAll("^##", "");
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterpriseSaveSettingsDataPacket((String)EnterpriseGui.enterpriseInfos.get("name"), this.inputDescription.getText(), this.checkboxOpen, services)));
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                this.saved = true;
            }

            if (mouseX >= this.guiLeft + 130 && mouseX <= this.guiLeft + 130 + 100 && mouseY >= this.guiTop + 221 && mouseY <= this.guiTop + 221 + 15)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                this.servicesEdit = !this.servicesEdit;
            }

            if (EnterpriseGui.enterpriseInfos.get("playerRole").equals("leader") && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 220 && mouseY <= this.guiTop + 220 + 20)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen(new EnterpriseDisbandConfirmGui(this));
            }

            if (mouseX >= this.guiLeft + 130 && mouseX <= this.guiLeft + 130 + 10 && mouseY >= this.guiTop + 113 && mouseY <= this.guiTop + 113 + 10)
            {
                this.checkboxOpen = !this.checkboxOpen;
                EnterpriseGui.enterpriseInfos.put("isOpen", Boolean.valueOf(this.checkboxOpen));
            }
        }

        if (!this.servicesEdit)
        {
            this.inputDescription.mouseClicked(mouseX, mouseY, mouseButton);
        }
        else
        {
            Iterator services1 = this.linesTextField.iterator();

            while (services1.hasNext())
            {
                GuiTextField lineTextField2 = (GuiTextField)services1.next();
                lineTextField2.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private float getSlide()
    {
        return ((ArrayList)EnterpriseGui.enterpriseInfos.get("logs")).size() > 3 ? (float)(-(((ArrayList)EnterpriseGui.enterpriseInfos.get("logs")).size() - 3) * 18) * this.scrollBarLogs.getSliderValue() : 0.0F;
    }

    public void drawTooltip(String time, int mouseX, int mouseY)
    {
        String date = "\u00a77";
        long diff = System.currentTimeMillis() - Long.parseLong(time);
        long days = diff / 86400000L;
        long hours = 0L;
        long minutes = 0L;
        long seconds = 0L;

        if (days == 0L)
        {
            hours = diff / 3600000L;

            if (hours == 0L)
            {
                minutes = diff / 60000L;

                if (minutes == 0L)
                {
                    seconds = diff / 1000L;
                    date = date + " " + seconds + " " + I18n.getString("faction.common.seconds");
                }
                else
                {
                    date = date + " " + minutes + " " + I18n.getString("faction.common.minutes");
                }
            }
            else
            {
                date = date + " " + hours + " " + I18n.getString("faction.common.hours");
            }
        }
        else
        {
            date = date + " " + days + " " + I18n.getString("faction.common.days");
        }

        this.drawHoveringText(Arrays.asList(new String[] {date}), mouseX, mouseY, this.fontRenderer);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char typedChar, int keyCode)
    {
        if (!this.servicesEdit)
        {
            this.inputDescription.textboxKeyTyped(typedChar, keyCode);
        }
        else
        {
            Iterator var3 = this.linesTextField.iterator();

            while (var3.hasNext())
            {
                GuiTextField lineTextField = (GuiTextField)var3.next();
                lineTextField.textboxKeyTyped(typedChar, keyCode);
            }
        }

        super.keyTyped(typedChar, keyCode);
    }

    public static BufferedImage decodeToImage(String imageString)
    {
        BufferedImage image = null;

        try
        {
            BASE64Decoder e = new BASE64Decoder();
            byte[] imageByte = e.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        }
        catch (Exception var5)
        {
            var5.printStackTrace();
        }

        return image;
    }
}
