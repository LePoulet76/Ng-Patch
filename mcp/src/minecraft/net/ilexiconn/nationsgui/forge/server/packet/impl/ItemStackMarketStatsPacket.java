/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.reflect.TypeToken
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.client.renderer.texture.TextureObject
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.ResourceLocation
 *  org.knowm.xchart.BitmapEncoder
 *  org.knowm.xchart.BitmapEncoder$BitmapFormat
 *  org.knowm.xchart.CategoryChart
 *  org.knowm.xchart.CategoryChartBuilder
 *  org.knowm.xchart.XYChart
 *  org.knowm.xchart.XYChartBuilder
 *  org.knowm.xchart.XYSeries$XYSeriesRenderStyle
 *  org.knowm.xchart.internal.chartpart.Chart
 *  org.knowm.xchart.style.CategoryStyler
 *  org.knowm.xchart.style.Styler$LegendPosition
 *  org.knowm.xchart.style.XYStyler
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.data.MarketSale;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.MarketGui;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.SellItemGUI;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.StatsGui;
import net.ilexiconn.nationsgui.forge.client.render.texture.GraphTexture;
import net.ilexiconn.nationsgui.forge.server.json.DateDeserializer;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
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
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.CategoryStyler;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.XYStyler;
import org.lwjgl.opengl.GL11;

public class ItemStackMarketStatsPacket
implements IPacket,
IClientPacket {
    private String targetUuid;
    private Map<Date, Integer> priceHistory;
    private Map<Date, Integer> quantityHistory;

    public ItemStackMarketStatsPacket() {
    }

    public ItemStackMarketStatsPacket(String targetUuid) {
        this.targetUuid = targetUuid;
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void handleClientPacket(EntityPlayer player) {
        XYChart chart = ((XYChartBuilder)((XYChartBuilder)((XYChartBuilder)new XYChartBuilder().width(884)).height(225)).title(I18n.func_135053_a((String)"hdv.CHART1_TEXTURE.title"))).build();
        ((XYStyler)chart.getStyler()).setLegendPosition(Styler.LegendPosition.InsideNE);
        ((XYStyler)chart.getStyler()).setAxisTitlesVisible(false);
        ((XYStyler)chart.getStyler()).setChartBackgroundColor(new Color(45, 45, 45));
        ((XYStyler)chart.getStyler()).setPlotBackgroundColor(new Color(45, 45, 45));
        ((XYStyler)chart.getStyler()).setAxisTickLabelsColor(new Color(255, 255, 255));
        ((XYStyler)chart.getStyler()).setAxisTickMarksColor(new Color(255, 255, 255));
        ((XYStyler)chart.getStyler()).setLegendVisible(false);
        ((XYStyler)chart.getStyler()).setDatePattern("d/M");
        ((XYStyler)chart.getStyler()).setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Area);
        ((XYStyler)chart.getStyler()).setDecimalPattern("#");
        chart.addSeries("a", new ArrayList<Date>(this.priceHistory.keySet()), new ArrayList<Integer>(this.priceHistory.values()));
        this.loadChartTexture((Chart)chart, StatsGui.CHART1_TEXTURE);
        CategoryChart chart2 = ((CategoryChartBuilder)((CategoryChartBuilder)((CategoryChartBuilder)new CategoryChartBuilder().width(884)).height(225)).title(I18n.func_135053_a((String)"hdv.CHART2_TEXTURE.title"))).build();
        ((CategoryStyler)chart2.getStyler()).setLegendPosition(Styler.LegendPosition.InsideNE);
        ((CategoryStyler)chart2.getStyler()).setLegendVisible(false);
        ((CategoryStyler)chart2.getStyler()).setChartBackgroundColor(new Color(45, 45, 45));
        ((CategoryStyler)chart2.getStyler()).setPlotBackgroundColor(new Color(45, 45, 45));
        ((CategoryStyler)chart2.getStyler()).setAxisTickLabelsColor(new Color(255, 255, 255));
        ((CategoryStyler)chart2.getStyler()).setAxisTickMarksColor(new Color(255, 255, 255));
        ((CategoryStyler)chart2.getStyler()).setAxisTitlesVisible(false);
        ((CategoryStyler)chart2.getStyler()).setDatePattern("d/M");
        ((CategoryStyler)chart2.getStyler()).setDecimalPattern("#");
        chart2.addSeries("a", new ArrayList<Date>(this.quantityHistory.keySet()), new ArrayList<Integer>(this.quantityHistory.values()));
        this.loadChartTexture((Chart)chart2, StatsGui.CHART2_TEXTURE);
        GuiScreen guiScreen = Minecraft.func_71410_x().field_71462_r;
        if (guiScreen instanceof MarketGui) {
            MarketGui marketGui = (MarketGui)guiScreen;
            MarketSale marketSale = marketGui.getSelected();
            if (marketSale != null) {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new StatsGui(marketGui, marketSale.getItemStack()));
            }
        } else if (guiScreen instanceof SellItemGUI) {
            SellItemGUI sellItemGUI = (SellItemGUI)guiScreen;
            Minecraft.func_71410_x().func_71373_a((GuiScreen)new StatsGui(guiScreen, sellItemGUI.getItemStack()));
        }
    }

    @SideOnly(value=Side.CLIENT)
    private void loadChartTexture(Chart chart, ResourceLocation resourceLocation) {
        TextureManager textureManager = Minecraft.func_71410_x().func_110434_K();
        TextureObject textureObject = textureManager.func_110581_b(resourceLocation);
        if (textureObject != null) {
            GL11.glDeleteTextures((int)textureObject.func_110552_b());
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            BitmapEncoder.saveBitmap((Chart)chart, (OutputStream)outputStream, (BitmapEncoder.BitmapFormat)BitmapEncoder.BitmapFormat.PNG);
            textureManager.func_110579_a(resourceLocation, (TextureObject)new GraphTexture(new ByteArrayInputStream(outputStream.toByteArray())));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, (Object)new DateDeserializer());
        Gson gson = gsonBuilder.create();
        Type type = new TypeToken<LinkedHashMap<Date, Integer>>(){}.getType();
        this.priceHistory = (Map)gson.fromJson(data.readUTF(), type);
        this.quantityHistory = (Map)gson.fromJson(data.readUTF(), type);
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.targetUuid);
    }
}

