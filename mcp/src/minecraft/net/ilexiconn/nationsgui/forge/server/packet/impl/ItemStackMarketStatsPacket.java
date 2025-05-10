package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.data.MarketSale;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.MarketGui;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.SellItemGUI;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.StatsGui;
import net.ilexiconn.nationsgui.forge.client.render.texture.GraphTexture;
import net.ilexiconn.nationsgui.forge.server.json.DateDeserializer;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.ItemStackMarketStatsPacket$1;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureObject;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.CategoryStyler;
import org.knowm.xchart.style.XYStyler;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.lwjgl.opengl.GL11;

public class ItemStackMarketStatsPacket implements IPacket, IClientPacket
{
    private String targetUuid;
    private Map<Date, Integer> priceHistory;
    private Map<Date, Integer> quantityHistory;

    public ItemStackMarketStatsPacket() {}

    public ItemStackMarketStatsPacket(String targetUuid)
    {
        this.targetUuid = targetUuid;
    }

    @SideOnly(Side.CLIENT)
    public void handleClientPacket(EntityPlayer player)
    {
        XYChart chart = ((XYChartBuilder)((XYChartBuilder)((XYChartBuilder)(new XYChartBuilder()).width(884)).height(225)).title(I18n.getString("hdv.CHART1_TEXTURE.title"))).build();
        ((XYStyler)chart.getStyler()).setLegendPosition(LegendPosition.InsideNE);
        ((XYStyler)chart.getStyler()).setAxisTitlesVisible(false);
        ((XYStyler)chart.getStyler()).setChartBackgroundColor(new Color(45, 45, 45));
        ((XYStyler)chart.getStyler()).setPlotBackgroundColor(new Color(45, 45, 45));
        ((XYStyler)chart.getStyler()).setAxisTickLabelsColor(new Color(255, 255, 255));
        ((XYStyler)chart.getStyler()).setAxisTickMarksColor(new Color(255, 255, 255));
        ((XYStyler)chart.getStyler()).setLegendVisible(false);
        ((XYStyler)chart.getStyler()).setDatePattern("d/M");
        ((XYStyler)chart.getStyler()).setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Area);
        ((XYStyler)chart.getStyler()).setDecimalPattern("#");
        chart.addSeries("a", new ArrayList(this.priceHistory.keySet()), new ArrayList(this.priceHistory.values()));
        this.loadChartTexture(chart, StatsGui.CHART1_TEXTURE);
        CategoryChart chart2 = ((CategoryChartBuilder)((CategoryChartBuilder)((CategoryChartBuilder)(new CategoryChartBuilder()).width(884)).height(225)).title(I18n.getString("hdv.CHART2_TEXTURE.title"))).build();
        ((CategoryStyler)chart2.getStyler()).setLegendPosition(LegendPosition.InsideNE);
        ((CategoryStyler)chart2.getStyler()).setLegendVisible(false);
        ((CategoryStyler)chart2.getStyler()).setChartBackgroundColor(new Color(45, 45, 45));
        ((CategoryStyler)chart2.getStyler()).setPlotBackgroundColor(new Color(45, 45, 45));
        ((CategoryStyler)chart2.getStyler()).setAxisTickLabelsColor(new Color(255, 255, 255));
        ((CategoryStyler)chart2.getStyler()).setAxisTickMarksColor(new Color(255, 255, 255));
        ((CategoryStyler)chart2.getStyler()).setAxisTitlesVisible(false);
        ((CategoryStyler)chart2.getStyler()).setDatePattern("d/M");
        ((CategoryStyler)chart2.getStyler()).setDecimalPattern("#");
        chart2.addSeries("a", new ArrayList(this.quantityHistory.keySet()), new ArrayList(this.quantityHistory.values()));
        this.loadChartTexture(chart2, StatsGui.CHART2_TEXTURE);
        GuiScreen guiScreen = Minecraft.getMinecraft().currentScreen;

        if (guiScreen instanceof MarketGui)
        {
            MarketGui sellItemGUI = (MarketGui)guiScreen;
            MarketSale marketSale = sellItemGUI.getSelected();

            if (marketSale != null)
            {
                Minecraft.getMinecraft().displayGuiScreen(new StatsGui(sellItemGUI, marketSale.getItemStack()));
            }
        }
        else if (guiScreen instanceof SellItemGUI)
        {
            SellItemGUI sellItemGUI1 = (SellItemGUI)guiScreen;
            Minecraft.getMinecraft().displayGuiScreen(new StatsGui(guiScreen, sellItemGUI1.getItemStack()));
        }
    }

    @SideOnly(Side.CLIENT)
    private void loadChartTexture(Chart chart, ResourceLocation resourceLocation)
    {
        TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
        TextureObject textureObject = textureManager.getTexture(resourceLocation);

        if (textureObject != null)
        {
            GL11.glDeleteTextures(textureObject.getGlTextureId());
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try
        {
            BitmapEncoder.saveBitmap(chart, outputStream, BitmapFormat.PNG);
            textureManager.loadTexture(resourceLocation, new GraphTexture(new ByteArrayInputStream(outputStream.toByteArray())));
        }
        catch (IOException var8)
        {
            var8.printStackTrace();
        }

        try
        {
            outputStream.close();
        }
        catch (IOException var7)
        {
            var7.printStackTrace();
        }
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
        Gson gson = gsonBuilder.create();
        Type type = (new ItemStackMarketStatsPacket$1(this)).getType();
        this.priceHistory = (Map)gson.fromJson(data.readUTF(), type);
        this.quantityHistory = (Map)gson.fromJson(data.readUTF(), type);
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.targetUuid);
    }
}
