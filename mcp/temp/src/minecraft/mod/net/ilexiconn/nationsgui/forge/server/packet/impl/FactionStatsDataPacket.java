package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.BasicStroke;
import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionStatsGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionStatsGUI$TAB;
import net.ilexiconn.nationsgui.forge.client.render.texture.GraphTexture;
import net.ilexiconn.nationsgui.forge.server.json.DateDeserializer;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionStatsDataPacket$1;
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

public class FactionStatsDataPacket implements IPacket, IClientPacket {

   private String targetFaction;
   private Map<String, Double> historyNotations = new LinkedHashMap();
   private Map<String, Double> historyPopulation = new LinkedHashMap();
   private Map<String, Double> historyTerritory = new LinkedHashMap();
   private Map<String, Double> historyWars = new LinkedHashMap();
   private Map<String, Double> historyEconomy = new LinkedHashMap();


   public FactionStatsDataPacket(String targetFaction) {
      this.targetFaction = targetFaction;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.targetFaction = data.readUTF();
      GsonBuilder gsonBuilder = new GsonBuilder();
      gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
      Gson gson = gsonBuilder.create();
      Type type = (new FactionStatsDataPacket$1(this)).getType();
      this.historyNotations = (Map)gson.fromJson(data.readUTF(), type);
      this.historyPopulation = (Map)gson.fromJson(data.readUTF(), type);
      this.historyTerritory = (Map)gson.fromJson(data.readUTF(), type);
      this.historyWars = (Map)gson.fromJson(data.readUTF(), type);
      this.historyEconomy = (Map)gson.fromJson(data.readUTF(), type);
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.targetFaction);
   }

   @SideOnly(Side.CLIENT)
   public void handleClientPacket(EntityPlayer player) {
      XYChart chart = this.getDefaultChart();
      chart.addSeries(I18n.func_135053_a("faction.stats.chart.label.rating"), new ArrayList(this.historyNotations.keySet()), new ArrayList(this.historyNotations.values()));
      this.loadChartTexture(chart, (ResourceLocation)FactionStatsGUI.TEXTURES.get(FactionStatsGUI$TAB.NOTATIONS));
      chart = this.getDefaultChart();
      chart.addSeries(I18n.func_135053_a("faction.stats.chart.label.members"), new ArrayList(this.historyPopulation.keySet()), new ArrayList(this.historyPopulation.values()));
      this.loadChartTexture(chart, (ResourceLocation)FactionStatsGUI.TEXTURES.get(FactionStatsGUI$TAB.POPULATION));
      chart = this.getDefaultChart();
      chart.addSeries(I18n.func_135053_a("faction.stats.chart.label.claims"), new ArrayList(this.historyTerritory.keySet()), new ArrayList(this.historyTerritory.values()));
      this.loadChartTexture(chart, (ResourceLocation)FactionStatsGUI.TEXTURES.get(FactionStatsGUI$TAB.TERRITORY));
      chart = this.getDefaultChart();
      chart.addSeries(I18n.func_135053_a("faction.stats.chart.label.mmr"), new ArrayList(this.historyWars.keySet()), new ArrayList(this.historyWars.values()));
      this.loadChartTexture(chart, (ResourceLocation)FactionStatsGUI.TEXTURES.get(FactionStatsGUI$TAB.WARS));
      chart = this.getDefaultChart();
      chart.addSeries(I18n.func_135053_a("faction.stats.chart.label.pib"), new ArrayList(this.historyEconomy.keySet()), new ArrayList(this.historyEconomy.values()));
      this.loadChartTexture(chart, (ResourceLocation)FactionStatsGUI.TEXTURES.get(FactionStatsGUI$TAB.ECONOMY));
      FactionStatsGUI.loaded = true;
   }

   public XYChart getDefaultChart() {
      XYChart chart = ((XYChartBuilder)((XYChartBuilder)((XYChartBuilder)(new XYChartBuilder()).width(411 * FactionGUI.GUI_SCALE)).height(125 * FactionGUI.GUI_SCALE)).title("")).build();
      ((XYStyler)chart.getStyler()).setLegendPosition(LegendPosition.OutsideE);
      ((XYStyler)chart.getStyler()).setAxisTitlesVisible(false);
      ((XYStyler)chart.getStyler()).setChartBackgroundColor(new Color(34, 24, 57));
      ((XYStyler)chart.getStyler()).setChartFontColor(new Color(235, 242, 250));
      ((XYStyler)chart.getStyler()).setLegendBackgroundColor(new Color(34, 24, 57));
      ((XYStyler)chart.getStyler()).setLegendBorderColor(new Color(34, 24, 57));
      ((XYStyler)chart.getStyler()).setPlotBackgroundColor(new Color(34, 24, 57));
      ((XYStyler)chart.getStyler()).setPlotGridLinesColor(new Color(34, 24, 57));
      ((XYStyler)chart.getStyler()).setPlotBorderColor(new Color(34, 24, 57));
      ((XYStyler)chart.getStyler()).setAxisTickLabelsColor(new Color(235, 242, 250));
      ((XYStyler)chart.getStyler()).setAxisTickMarksColor(new Color(235, 242, 250));
      ((XYStyler)chart.getStyler()).setLegendVisible(true);
      ((XYStyler)chart.getStyler()).setDatePattern("d/M");
      ((XYStyler)chart.getStyler()).setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);
      ((XYStyler)chart.getStyler()).setSeriesColors((Color[])((Color[])Arrays.asList(new Color[]{new Color(110, 118, 238)}).toArray()));
      ((XYStyler)chart.getStyler()).setSeriesLines((BasicStroke[])((BasicStroke[])Arrays.asList(new BasicStroke[]{new BasicStroke(4.0F)}).toArray()));
      ((XYStyler)chart.getStyler()).setDecimalPattern("#");
      return chart;
   }

   @SideOnly(Side.CLIENT)
   private void loadChartTexture(Chart chart, ResourceLocation resourceLocation) {
      TextureManager textureManager = Minecraft.func_71410_x().func_110434_K();
      TextureObject textureObject = textureManager.func_110581_b(resourceLocation);
      if(textureObject != null) {
         GL11.glDeleteTextures(textureObject.func_110552_b());
      }

      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

      try {
         BitmapEncoder.saveBitmap(chart, outputStream, BitmapFormat.PNG);
         textureManager.func_110579_a(resourceLocation, new GraphTexture(new ByteArrayInputStream(outputStream.toByteArray())));
      } catch (IOException var8) {
         var8.printStackTrace();
      }

      try {
         outputStream.close();
      } catch (IOException var7) {
         var7.printStackTrace();
      }

   }
}
