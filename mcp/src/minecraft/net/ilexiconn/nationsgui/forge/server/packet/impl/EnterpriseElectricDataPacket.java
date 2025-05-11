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
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.client.renderer.texture.TextureObject
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.ResourceLocation
 *  org.knowm.xchart.BitmapEncoder
 *  org.knowm.xchart.BitmapEncoder$BitmapFormat
 *  org.knowm.xchart.XYChart
 *  org.knowm.xchart.XYChartBuilder
 *  org.knowm.xchart.XYSeries$XYSeriesRenderStyle
 *  org.knowm.xchart.internal.chartpart.Chart
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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseElectricGUI;
import net.ilexiconn.nationsgui.forge.client.render.texture.GraphTexture;
import net.ilexiconn.nationsgui.forge.server.json.DateDeserializer;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureObject;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.XYStyler;
import org.lwjgl.opengl.GL11;

public class EnterpriseElectricDataPacket
implements IPacket,
IClientPacket {
    private Map<String, Double> history_generated = new LinkedHashMap<String, Double>();
    private Map<String, Double> history_collected = new LinkedHashMap<String, Double>();
    public HashMap<String, Object> infos = new HashMap();
    public String enterpriseName;

    public EnterpriseElectricDataPacket(String targetName) {
        this.enterpriseName = targetName;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.infos = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, Object>>(){}.getType());
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, (Object)new DateDeserializer());
        Gson gson = gsonBuilder.create();
        Type type = new TypeToken<LinkedHashMap<Date, Double>>(){}.getType();
        this.history_generated = (Map)gson.fromJson(data.readUTF(), type);
        this.history_collected = (Map)gson.fromJson(data.readUTF(), type);
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.enterpriseName);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void handleClientPacket(EntityPlayer player) {
        XYChart chart = ((XYChartBuilder)((XYChartBuilder)((XYChartBuilder)new XYChartBuilder().width(496)).height(190)).title(I18n.func_135053_a((String)"enterprise.STATS_TEXTURE.electric.title"))).build();
        ((XYStyler)chart.getStyler()).setLegendPosition(Styler.LegendPosition.InsideNE);
        ((XYStyler)chart.getStyler()).setAxisTitlesVisible(false);
        ((XYStyler)chart.getStyler()).setChartBackgroundColor(new Color(232, 232, 232));
        ((XYStyler)chart.getStyler()).setLegendVisible(false);
        ((XYStyler)chart.getStyler()).setDatePattern("d/M");
        ((XYStyler)chart.getStyler()).setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Area);
        ((XYStyler)chart.getStyler()).setDecimalPattern("#");
        if (!this.history_generated.isEmpty()) {
            chart.addSeries("a", new ArrayList<String>(this.history_generated.keySet()), new ArrayList<Double>(this.history_generated.values()));
        }
        if (!this.history_collected.isEmpty()) {
            chart.addSeries("b", new ArrayList<String>(this.history_collected.keySet()), new ArrayList<Double>(this.history_collected.values()));
        }
        this.loadChartTexture((Chart)chart, EnterpriseElectricGUI.STATS_TEXTURE);
        EnterpriseElectricGUI.enterpriseElectricInfos = this.infos;
        EnterpriseElectricGUI.loaded = true;
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
}

