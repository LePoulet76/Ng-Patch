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
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGlobalMarketGui;
import net.ilexiconn.nationsgui.forge.client.render.texture.GraphTexture;
import net.ilexiconn.nationsgui.forge.server.json.DateDeserializer;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseGlobalMarketDataPacket$1;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseGlobalMarketDataPacket$2;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseGlobalMarketDataPacket$3;
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

public class EnterpriseGlobalMarketDataPacket implements IPacket, IClientPacket {

   private Map<String, Map<String, Double>> prices_history = new LinkedHashMap();
   private Map<String, Map<String, Double>> sales_history = new LinkedHashMap();
   private Map<String, Map<String, Double>> stocks_history = new LinkedHashMap();
   public HashMap<String, Integer> cerealsPrice = new HashMap();
   public HashMap<String, String> cerealsPriceChange = new HashMap();


   public void fromBytes(ByteArrayDataInput data) {
      this.cerealsPrice = (HashMap)(new Gson()).fromJson(data.readUTF(), (new EnterpriseGlobalMarketDataPacket$1(this)).getType());
      this.cerealsPriceChange = (HashMap)(new Gson()).fromJson(data.readUTF(), (new EnterpriseGlobalMarketDataPacket$2(this)).getType());
      GsonBuilder gsonBuilder = new GsonBuilder();
      gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
      Gson gson = gsonBuilder.create();
      Type type = (new EnterpriseGlobalMarketDataPacket$3(this)).getType();
      this.prices_history = (Map)gson.fromJson(data.readUTF(), type);
      this.sales_history = (Map)gson.fromJson(data.readUTF(), type);
      this.stocks_history = (Map)gson.fromJson(data.readUTF(), type);
   }

   public void toBytes(ByteArrayDataOutput data) {}

   @SideOnly(Side.CLIENT)
   public void handleClientPacket(EntityPlayer player) {
      XYChart chart = ((XYChartBuilder)((XYChartBuilder)((XYChartBuilder)(new XYChartBuilder()).width(676)).height(266)).title(I18n.func_135053_a("globalmarket.chart.prices"))).build();
      ((XYStyler)chart.getStyler()).setLegendPosition(LegendPosition.OutsideE);
      ((XYStyler)chart.getStyler()).setAxisTitlesVisible(false);
      ((XYStyler)chart.getStyler()).setChartBackgroundColor(new Color(255, 255, 255));
      ((XYStyler)chart.getStyler()).setLegendVisible(true);
      ((XYStyler)chart.getStyler()).setDatePattern("d/M");
      ((XYStyler)chart.getStyler()).setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);
      ((XYStyler)chart.getStyler()).setDecimalPattern("#");
      Iterator var3 = this.prices_history.entrySet().iterator();

      Entry entry;
      while(var3.hasNext()) {
         entry = (Entry)var3.next();
         chart.addSeries(I18n.func_135053_a("cereal." + (String)entry.getKey()), new ArrayList(((Map)entry.getValue()).keySet()), new ArrayList(((Map)entry.getValue()).values()));
      }

      this.loadChartTexture(chart, EnterpriseGlobalMarketGui.PRICES_TEXTURE);
      chart = ((XYChartBuilder)((XYChartBuilder)((XYChartBuilder)(new XYChartBuilder()).width(676)).height(266)).title(I18n.func_135053_a("globalmarket.chart.sales"))).build();
      ((XYStyler)chart.getStyler()).setLegendPosition(LegendPosition.OutsideE);
      ((XYStyler)chart.getStyler()).setAxisTitlesVisible(false);
      ((XYStyler)chart.getStyler()).setChartBackgroundColor(new Color(255, 255, 255));
      ((XYStyler)chart.getStyler()).setLegendVisible(true);
      ((XYStyler)chart.getStyler()).setDatePattern("d/M");
      ((XYStyler)chart.getStyler()).setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);
      ((XYStyler)chart.getStyler()).setDecimalPattern("#");
      var3 = this.sales_history.entrySet().iterator();

      while(var3.hasNext()) {
         entry = (Entry)var3.next();
         chart.addSeries(I18n.func_135053_a("cereal." + (String)entry.getKey()), new ArrayList(((Map)entry.getValue()).keySet()), new ArrayList(((Map)entry.getValue()).values()));
      }

      this.loadChartTexture(chart, EnterpriseGlobalMarketGui.SALES_TEXTURE);
      chart = ((XYChartBuilder)((XYChartBuilder)((XYChartBuilder)(new XYChartBuilder()).width(676)).height(266)).title(I18n.func_135053_a("globalmarket.chart.stocks"))).build();
      ((XYStyler)chart.getStyler()).setLegendPosition(LegendPosition.OutsideE);
      ((XYStyler)chart.getStyler()).setAxisTitlesVisible(false);
      ((XYStyler)chart.getStyler()).setChartBackgroundColor(new Color(255, 255, 255));
      ((XYStyler)chart.getStyler()).setLegendVisible(true);
      ((XYStyler)chart.getStyler()).setDatePattern("d/M");
      ((XYStyler)chart.getStyler()).setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);
      ((XYStyler)chart.getStyler()).setDecimalPattern("#");
      var3 = this.stocks_history.entrySet().iterator();

      while(var3.hasNext()) {
         entry = (Entry)var3.next();
         chart.addSeries(I18n.func_135053_a("cereal." + (String)entry.getKey()), new ArrayList(((Map)entry.getValue()).keySet()), new ArrayList(((Map)entry.getValue()).values()));
      }

      this.loadChartTexture(chart, EnterpriseGlobalMarketGui.STOCKS_TEXTURE);
      EnterpriseGlobalMarketGui.cerealsPrice = this.cerealsPrice;
      EnterpriseGlobalMarketGui.cerealsPriceChange = this.cerealsPriceChange;
      EnterpriseGlobalMarketGui.loaded = true;
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
