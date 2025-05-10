package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.Arrays;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseCasinoDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class EnterpriseCasinoGUI extends TabbedEnterpriseGUI
{
    public static boolean loaded = false;
    public static HashMap<String, Object> enterpriseCasinoInfos;
    public static final ResourceLocation STATS_TEXTURE = new ResourceLocation("nationsgui", "tmp/stats_casino");

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        loaded = false;
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterpriseCasinoDataPacket((String)EnterpriseGui.enterpriseInfos.get("name"))));
    }

    public void drawScreen(int mouseX, int mouseY)
    {
        String tooltipToDraw = "";
        ClientEventHandler.STYLE.bindTexture("enterprise_casino");
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);

        if (loaded)
        {
            this.drawScaledString(I18n.getString("enterprise.casino.title"), this.guiLeft + 131, this.guiTop + 15, 1644825, 1.4F, false, false);
            Minecraft.getMinecraft().getTextureManager().bindTexture(STATS_TEXTURE);
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 134), (float)(this.guiTop + 38), 0, 0, 248, 95, 248.0F, 95.0F, true);
            ClientEventHandler.STYLE.bindTexture("enterprise_casino");
            this.drawScaledString(I18n.getString("enterprise.casino.label.benef_moy"), this.guiLeft + 160, this.guiTop + 148, 11842740, 1.0F, false, false);
            this.drawScaledString(String.format("%.2f", new Object[] {(Double)enterpriseCasinoInfos.get("benefMoy")}) + "\u00a7a$", this.guiLeft + 138, this.guiTop + 168, 16777215, 1.0F, false, false);
            this.drawScaledString(I18n.getString("enterprise.casino.label.win_percent"), this.guiLeft + 288, this.guiTop + 148, 11842740, 1.0F, false, false);
            this.drawScaledString(String.format("%.0f", new Object[] {(Double)enterpriseCasinoInfos.get("winPercent")}) + "%", this.guiLeft + 266, this.guiTop + 168, 16777215, 1.0F, false, false);
            this.drawScaledString(I18n.getString("enterprise.casino.label.total_play"), this.guiLeft + 160, this.guiTop + 199, 11842740, 1.0F, false, false);
            this.drawScaledString(String.format("%.2f", new Object[] {(Double)enterpriseCasinoInfos.get("totalPlay")}) + "\u00a7a$", this.guiLeft + 138, this.guiTop + 219, 16777215, 1.0F, false, false);
            this.drawScaledString(I18n.getString("enterprise.casino.label.total_reward"), this.guiLeft + 288, this.guiTop + 199, 11842740, 1.0F, false, false);
            this.drawScaledString(String.format("%.2f", new Object[] {(Double)enterpriseCasinoInfos.get("totalWin")}) + "\u00a7a$", this.guiLeft + 266, this.guiTop + 219, 16777215, 1.0F, false, false);

            if (!tooltipToDraw.isEmpty())
            {
                this.drawTooltip(tooltipToDraw, mouseX, mouseY);
            }
        }
    }

    public void drawTooltip(String text, int mouseX, int mouseY)
    {
        int var10000 = mouseX - this.guiLeft;
        var10000 = mouseY - this.guiTop;
        this.drawHoveringText(Arrays.asList(new String[] {text}), mouseX, mouseY, this.fontRenderer);
    }

    private void drawPriceChart(int x, int y, int width, int height)
    {
        ScaledResolution scaledresolution = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        x *= scaledresolution.getScaleFactor();
        y *= scaledresolution.getScaleFactor();
        width *= scaledresolution.getScaleFactor();
        height *= scaledresolution.getScaleFactor();
        ModernGui.drawModalRectWithCustomSizedTexture((float)x, (float)y, 0, 0, width, height, (float)width, (float)height, true);
    }
}
