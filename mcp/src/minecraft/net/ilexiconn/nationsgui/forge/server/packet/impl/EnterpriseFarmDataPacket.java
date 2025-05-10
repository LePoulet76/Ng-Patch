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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseFarmGUI;
import net.ilexiconn.nationsgui.forge.client.render.texture.GraphTexture;
import net.ilexiconn.nationsgui.forge.server.json.DateDeserializer;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseFarmDataPacket$1;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseFarmDataPacket$2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureObject;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.XYStyler;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.lwjgl.opengl.GL11;

public class EnterpriseFarmDataPacket implements IPacket, IClientPacket
{
    private Map<String, Map<String, Double>> histories = new LinkedHashMap();
    public HashMap<String, Object> infos = new HashMap();
    public String enterpriseName;

    public EnterpriseFarmDataPacket(String targetName)
    {
        this.enterpriseName = targetName;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.infos = (HashMap)(new Gson()).fromJson(data.readUTF(), (new EnterpriseFarmDataPacket$1(this)).getType());
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
        Gson gson = gsonBuilder.create();
        Type type = (new EnterpriseFarmDataPacket$2(this)).getType();
        this.histories = (Map)gson.fromJson(data.readUTF(), type);
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.enterpriseName);
    }

    @SideOnly(Side.CLIENT)
    public void handleClientPacket(EntityPlayer player)
    {
        XYChart chart = ((XYChartBuilder)((XYChartBuilder)((XYChartBuilder)(new XYChartBuilder()).width(536)).height(226)).title(I18n.getString("enterprise.STATS_TEXTURE.farm.title"))).build();
        ((XYStyler)chart.getStyler()).setLegendPosition(LegendPosition.OutsideE);
        ((XYStyler)chart.getStyler()).setAxisTitlesVisible(false);
        ((XYStyler)chart.getStyler()).setChartBackgroundColor(new Color(232, 232, 232));
        ((XYStyler)chart.getStyler()).setLegendVisible(true);
        ((XYStyler)chart.getStyler()).setDatePattern("d/M");
        ((XYStyler)chart.getStyler()).setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);
        ((XYStyler)chart.getStyler()).setDecimalPattern("#");
        Iterator var3 = this.histories.entrySet().iterator();

        while (var3.hasNext())
        {
            Entry entry = (Entry)var3.next();
            chart.addSeries(I18n.getString("cereal." + (String)entry.getKey()), new ArrayList(((Map)entry.getValue()).keySet()), new ArrayList(((Map)entry.getValue()).values()));
        }

        this.loadChartTexture(chart, EnterpriseFarmGUI.STATS_TEXTURE);
        EnterpriseFarmGUI.enterpriseFarmInfos = this.infos;
        EnterpriseFarmGUI.loaded = true;
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
}
