package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseFarmDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class EnterpriseFarmGUI extends TabbedEnterpriseGUI
{
    public static boolean loaded = false;
    private GuiScrollBarFaction scrollBar;
    public static HashMap<String, Object> enterpriseFarmInfos;
    private GuiButton marketButton;
    public static final ResourceLocation STATS_TEXTURE = new ResourceLocation("nationsgui", "tmp/stats_farm");

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        loaded = false;
        this.scrollBar = new GuiScrollBarFaction((float)(this.guiLeft + 249), (float)(this.guiTop + 165), 65);
        this.marketButton = new GuiButton(0, this.guiLeft + 10, this.guiTop + 165, 100, 20, I18n.getString("enterprise.farm.btnMarket"));
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterpriseFarmDataPacket((String)EnterpriseGui.enterpriseInfos.get("name"))));
    }

    public void drawScreen(int mouseX, int mouseY)
    {
        String tooltipToDraw = "";
        ClientEventHandler.STYLE.bindTexture("enterprise_farm");
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
        this.marketButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);

        if (loaded)
        {
            this.drawScaledString(I18n.getString("enterprise.farm.title"), this.guiLeft + 131, this.guiTop + 15, 1644825, 1.4F, false, false);
            Minecraft.getMinecraft().getTextureManager().bindTexture(STATS_TEXTURE);
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 124), (float)(this.guiTop + 30), 0, 0, 268, 113, 268.0F, 115.0F, true);
            ClientEventHandler.STYLE.bindTexture("enterprise_farm");
            this.drawScaledString(I18n.getString("enterprise.farm.label.today"), this.guiLeft + 286, this.guiTop + 149, 11842740, 1.0F, false, false);
            this.drawScaledString(String.format("%.0f", new Object[] {(Double)enterpriseFarmInfos.get("today")}) + " kg", this.guiLeft + 266, this.guiTop + 168, 16777215, 1.0F, false, false);
            this.drawScaledString(I18n.getString("enterprise.farm.label.total"), this.guiLeft + 286, this.guiTop + 199, 11842740, 1.0F, false, false);
            this.drawScaledString(String.format("%.0f", new Object[] {(Double)enterpriseFarmInfos.get("total")}) + " kg", this.guiLeft + 266, this.guiTop + 219, 16777215, 1.0F, false, false);
            this.drawScaledString(I18n.getString("enterprise.farm.label.history"), this.guiLeft + 158, this.guiTop + 149, 11842740, 1.0F, false, false);
            GUIUtils.startGLScissor(this.guiLeft + 135, this.guiTop + 162, 114, 71);

            for (int i = 0; i < ((ArrayList)enterpriseFarmInfos.get("collected_top")).size(); ++i)
            {
                int offsetX = this.guiLeft + 135;
                Float offsetY = Float.valueOf((float)(this.guiTop + 162 + i * 20) + this.getSlide());
                ClientEventHandler.STYLE.bindTexture("enterprise_farm");
                ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)offsetY.intValue(), 135, 162, 114, 20, 512.0F, 512.0F, false);
                String cerealName = I18n.getString("cereal." + ((String)((ArrayList)enterpriseFarmInfos.get("collected_top")).get(i)).split("##")[0]);
                String value = "\u00a7a" + String.format("%.0f", new Object[] {Double.valueOf(Double.parseDouble(((String)((ArrayList)enterpriseFarmInfos.get("collected_top")).get(i)).split("##")[1]))});
                this.drawScaledString(cerealName, offsetX + 2, offsetY.intValue() + 5, 16777215, 1.0F, false, false);
                this.drawScaledString(value, offsetX + 112 - this.fontRenderer.getStringWidth(value), offsetY.intValue() + 5, 16777215, 1.0F, false, false);
            }

            GUIUtils.endGLScissor();
            this.scrollBar.draw(mouseX, mouseY);

            if (!tooltipToDraw.isEmpty())
            {
                this.drawTooltip(tooltipToDraw, mouseX, mouseY);
            }
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (mouseButton == 0 && this.marketButton != null && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 165 && mouseY <= this.guiTop + 165 + 20)
        {
            Minecraft.getMinecraft().displayGuiScreen(new EnterpriseGlobalMarketGui());
        }
    }

    private float getSlide()
    {
        return ((ArrayList)enterpriseFarmInfos.get("collected_top")).size() > 3 ? (float)(-(((ArrayList)enterpriseFarmInfos.get("collected_top")).size() - 3) * 20) * this.scrollBar.getSliderValue() : 0.0F;
    }

    public void drawTooltip(String text, int mouseX, int mouseY)
    {
        int var10000 = mouseX - this.guiLeft;
        var10000 = mouseY - this.guiTop;
        this.drawHoveringText(Arrays.asList(new String[] {text}), mouseX, mouseY, this.fontRenderer);
    }
}
