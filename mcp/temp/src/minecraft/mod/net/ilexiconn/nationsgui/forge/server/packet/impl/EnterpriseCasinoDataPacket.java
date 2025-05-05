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
import java.util.LinkedHashMap;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseCasinoGUI;
import net.ilexiconn.nationsgui.forge.client.render.texture.GraphTexture;
import net.ilexiconn.nationsgui.forge.server.json.DateDeserializer;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseCasinoDataPacket$1;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseCasinoDataPacket$2;
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

public class EnterpriseCasinoDataPacket implements IPacket, IClientPacket {

   private Map<String, Double> history = new LinkedHashMap();
   public HashMap<String, Object> casinoInfos = new HashMap();
   public String enterpriseName;


   public EnterpriseCasinoDataPacket(String targetName) {
      this.enterpriseName = targetName;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.casinoInfos = (HashMap)(new Gson()).fromJson(data.readUTF(), (new EnterpriseCasinoDataPacket$1(this)).getType());
      GsonBuilder gsonBuilder = new GsonBuilder();
      gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
      Gson gson = gsonBuilder.create();
      Type type = (new EnterpriseCasinoDataPacket$2(this)).getType();
      this.history = (Map)gson.fromJson(data.readUTF(), type);
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.enterpriseName);
   }

   @SideOnly(Side.CLIENT)
   public void handleClientPacket(EntityPlayer player) {
      XYChart chart = ((XYChartBuilder)((XYChartBuilder)((XYChartBuilder)(new XYChartBuilder()).width(496)).height(190)).title(I18n.func_135053_a("enterprise.STATS_TEXTURE.casino.title"))).build();
      ((XYStyler)chart.getStyler()).setLegendPosition(LegendPosition.InsideNE);
      ((XYStyler)chart.getStyler()).setAxisTitlesVisible(false);
      ((XYStyler)chart.getStyler()).setChartBackgroundColor(new Color(232, 232, 232));
      ((XYStyler)chart.getStyler()).setLegendVisible(false);
      ((XYStyler)chart.getStyler()).setDatePattern("d/M");
      ((XYStyler)chart.getStyler()).setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Area);
      ((XYStyler)chart.getStyler()).setDecimalPattern("#");
      if(!this.history.isEmpty()) {
         chart.addSeries("a", new ArrayList(this.history.keySet()), new ArrayList(this.history.values()));
      }

      this.loadChartTexture(chart, EnterpriseCasinoGUI.STATS_TEXTURE);
      EnterpriseCasinoGUI.enterpriseCasinoInfos = this.casinoInfos;
      EnterpriseCasinoGUI.loaded = true;
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
