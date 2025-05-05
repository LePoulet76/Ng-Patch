package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseElectricPriceGui;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.TabbedEnterpriseGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseElectricDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class EnterpriseElectricGUI extends TabbedEnterpriseGUI {

   public static boolean loaded = false;
   private GuiScrollBarFaction scrollBar;
   public static HashMap<String, Object> enterpriseElectricInfos;
   private GuiButton priceButton;
   public static final ResourceLocation STATS_TEXTURE = new ResourceLocation("nationsgui", "tmp/stats_electric");


   public void func_73866_w_() {
      super.func_73866_w_();
      loaded = false;
      this.scrollBar = new GuiScrollBarFaction((float)(this.guiLeft + 249), (float)(this.guiTop + 145), 85);
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterpriseElectricDataPacket((String)EnterpriseGui.enterpriseInfos.get("name"))));
   }

   public void drawScreen(int mouseX, int mouseY) {
      String tooltipToDraw = "";
      ClientEventHandler.STYLE.bindTexture("enterprise_electric");
      ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
      if(loaded) {
         if(this.priceButton == null) {
            this.priceButton = new GuiButton(0, this.guiLeft + 10, this.guiTop + 165, 100, 20, I18n.func_135053_a("enterprise.electric.btnPrice").replace("<price>", String.format("%.0f", new Object[]{(Double)enterpriseElectricInfos.get("price")})));
         } else {
            this.priceButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
         }

         this.drawScaledString(I18n.func_135053_a("enterprise.electric.title"), this.guiLeft + 131, this.guiTop + 15, 1644825, 1.4F, false, false);
         Minecraft.func_71410_x().func_110434_K().func_110577_a(STATS_TEXTURE);
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 134), (float)(this.guiTop + 38), 0, 0, 248, 95, 248.0F, 95.0F, true);
         ClientEventHandler.STYLE.bindTexture("enterprise_electric");
         this.drawScaledString(I18n.func_135053_a("enterprise.electric.label.available"), this.guiLeft + 288, this.guiTop + 148, 11842740, 1.0F, false, false);
         this.drawScaledString(String.format("%.2f", new Object[]{(Double)enterpriseElectricInfos.get("available")}) + " MW", this.guiLeft + 266, this.guiTop + 168, 16777215, 1.0F, false, false);
         this.drawScaledString(I18n.func_135053_a("enterprise.electric.label.total"), this.guiLeft + 288, this.guiTop + 199, 11842740, 1.0F, false, false);
         this.drawScaledString(String.format("%.2f", new Object[]{(Double)enterpriseElectricInfos.get("total")}) + " MW", this.guiLeft + 266, this.guiTop + 219, 16777215, 1.0F, false, false);
         GUIUtils.startGLScissor(this.guiLeft + 135, this.guiTop + 141, 114, 92);

         for(int i = 0; i < ((ArrayList)enterpriseElectricInfos.get("countries")).size(); ++i) {
            int offsetX = this.guiLeft + 135;
            Float offsetY = Float.valueOf((float)(this.guiTop + 141 + i * 20) + this.getSlide());
            ClientEventHandler.STYLE.bindTexture("enterprise_electric");
            ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)offsetY.intValue(), 135, 141, 114, 94, 512.0F, 512.0F, false);
            String countryName = ((String)((ArrayList)enterpriseElectricInfos.get("countries")).get(i)).split("##")[0];
            String value = "\u00a7a" + String.format("%.1f", new Object[]{Double.valueOf(Double.parseDouble(((String)((ArrayList)enterpriseElectricInfos.get("countries")).get(i)).split("##")[1]))});
            this.drawScaledString(countryName, offsetX + 2, offsetY.intValue() + 5, 16777215, 1.0F, false, false);
            this.drawScaledString(value, offsetX + 112 - this.field_73886_k.func_78256_a(value), offsetY.intValue() + 5, 16777215, 1.0F, false, false);
         }

         GUIUtils.endGLScissor();
         this.scrollBar.draw(mouseX, mouseY);
         if(!tooltipToDraw.isEmpty()) {
            this.drawTooltip(tooltipToDraw, mouseX, mouseY);
         }
      }

   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      super.func_73864_a(mouseX, mouseY, mouseButton);
      if(mouseButton == 0 && this.priceButton != null && this.priceButton.field_73742_g && EnterpriseGui.hasPermission("electric") && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 165 && mouseY <= this.guiTop + 165 + 20) {
         Minecraft.func_71410_x().func_71373_a(new EnterpriseElectricPriceGui(this));
      }

   }

   private float getSlide() {
      return ((ArrayList)enterpriseElectricInfos.get("countries")).size() > 5?(float)(-(((ArrayList)enterpriseElectricInfos.get("countries")).size() - 5) * 20) * this.scrollBar.getSliderValue():0.0F;
   }

   public void drawTooltip(String text, int mouseX, int mouseY) {
      int var10000 = mouseX - this.guiLeft;
      var10000 = mouseY - this.guiTop;
      this.drawHoveringText(Arrays.asList(new String[]{text}), mouseX, mouseY, this.field_73886_k);
   }

   private void drawPriceChart(int x, int y, int width, int height) {
      ScaledResolution scaledresolution = new ScaledResolution(this.field_73882_e.field_71474_y, this.field_73882_e.field_71443_c, this.field_73882_e.field_71440_d);
      x *= scaledresolution.func_78325_e();
      y *= scaledresolution.func_78325_e();
      width *= scaledresolution.func_78325_e();
      height *= scaledresolution.func_78325_e();
      ModernGui.drawModalRectWithCustomSizedTexture((float)x, (float)y, 0, 0, width, height, (float)width, (float)height, true);
   }

}
