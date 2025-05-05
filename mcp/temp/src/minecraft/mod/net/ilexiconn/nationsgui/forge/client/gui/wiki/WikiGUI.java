package net.ilexiconn.nationsgui.forge.client.gui.wiki;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.Desktop.Action;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class WikiGUI extends GuiScreen {

   public static final ResourceLocation SCROLLBAR_CURSOR = new ResourceLocation("nationsgui", "textures/gui/multi/scrollbar_cursor.png");
   public static String selectedMainCategory = "";
   public static String selectedCategory = "";
   public static String defaultCategory = "";
   public static String hoveredCategory = "";
   public static String hoveredMainCategory = "";
   public static String hoveredAction = "";
   public static int selectedMainCategoryFullHeight = 0;
   private RenderItem itemRenderer = new RenderItem();
   private GuiTextField inputSearch;
   private GuiScrollBarGeneric scrollBar;
   public static HashMap<String, Integer> categoryScrollOffset = new HashMap();
   private GuiScreen guiFrom;


   public WikiGUI(GuiScreen guiFrom) {
      this.guiFrom = guiFrom;
      selectedMainCategoryFullHeight = 0;
   }

   public void func_73876_c() {
      super.func_73876_c();
      this.inputSearch.func_73780_a();
   }

   protected void func_73869_a(char typedChar, int keyCode) {
      this.inputSearch.func_73802_a(typedChar, keyCode);
      if(keyCode == 1) {
         this.field_73882_e.func_71373_a(this.guiFrom);
         if(this.guiFrom == null) {
            this.field_73882_e.func_71381_h();
         }
      }

   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.scrollBar = new GuiScrollBarGeneric(3780.0F, 440.0F, 1500, SCROLLBAR_CURSOR, 24, 179);
      int windowHeight = this.field_73880_f * 9 / 16;
      this.inputSearch = new GuiTextField(this.field_73886_k, (int)(1650.0F / (3840.0F / (float)this.field_73880_f)), (int)(880.0F / (2160.0F / (float)windowHeight)), (int)(530.0F / (3840.0F / (float)this.field_73880_f)), 20);
      this.inputSearch.func_73786_a(false);
      this.inputSearch.func_73804_f(40);
   }

   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
      ArrayList toolTipLines = new ArrayList();
      hoveredCategory = "";
      hoveredMainCategory = "";
      hoveredAction = "";
      GL11.glDisable(2884);
      GL11.glPushMatrix();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      int windowWidth = this.field_73880_f;
      int windowHeight = this.field_73880_f * 9 / 16;
      int screenWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
      int screenHeight = screenWidth * 9 / 16;
      int mouseXScaled = (int)((float)mouseX * (3840.0F / (float)this.field_73880_f));
      int mouseYScaled = (int)((float)mouseY * (2160.0F / (float)windowHeight));
      Gui.func_73734_a(0, 0, (int)(950.0F * ((float)this.field_73880_f / 3840.0F)), this.field_73881_g, -13749417);
      Gui.func_73734_a((int)(950.0F * ((float)this.field_73880_f / 3840.0F)), 0, this.field_73880_f, this.field_73881_g, -15197637);
      GL11.glScaled((double)((float)this.field_73880_f / 3840.0F), (double)((float)windowHeight / 2160.0F), 1.0D);
      ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("wiki.title"), 82.0F, 50.0F, 15659004, 4.0F, "left", false, "georamaSemiBold", 50);
      ModernGui.drawSectionStringCustomFont(I18n.func_135053_a("wiki.description"), 82.0F, 185.0F, 15659004, 4.0F, "left", false, "georamaRegular", 18, 40, 200);
      ClientProxy.loadResource("textures/gui/wiki/search.png");
      ModernGui.drawModalRectWithCustomSizedTexture(80.0F, 300.0F, 0, 0, 794, 92, 794.0F, 92.0F, true);
      Gui.func_73734_a(56, 450, 894, 452, -12170642);
      short initialCategoriesOffset = 520;
      int offsetCategories = 0;
      Iterator offsetContent = ((ArrayList)ClientProxy.wiki.clone()).iterator();

      Iterator var15;
      HashMap mainCategory;
      while(offsetContent.hasNext()) {
         HashMap currentSliderValue = (HashMap)offsetContent.next();
         if(mouseXScaled >= 200 && mouseXScaled <= 800 && mouseYScaled >= initialCategoriesOffset + offsetCategories && mouseYScaled <= initialCategoriesOffset + offsetCategories + 60) {
            hoveredMainCategory = (String)currentSliderValue.get("name");
         }

         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         ClientProxy.loadResource("textures/gui/wiki/icons/" + currentSliderValue.get("name") + (selectedMainCategory.equals(currentSliderValue.get("name"))?"_purple":(hoveredMainCategory.equals(currentSliderValue.get("name"))?"_white":"_gray")) + ".png");
         ModernGui.drawModalRectWithCustomSizedTexture(120.0F, (float)(initialCategoriesOffset + offsetCategories), 0, 0, 60, 63, 60.0F, 63.0F, true);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("wiki.text." + currentSliderValue.get("name") + ".title"), 200.0F, (float)(initialCategoriesOffset + offsetCategories), selectedMainCategory.equals(currentSliderValue.get("name"))?7239406:(hoveredMainCategory.equals(currentSliderValue.get("name"))?16777215:12895428), 4.0F, "left", false, "georamaSemiBold", 30);
         if(currentSliderValue.containsKey("children") && (selectedMainCategory.isEmpty() || selectedMainCategory.equals(currentSliderValue.get("name")))) {
            offsetCategories += 10;

            for(var15 = ((ArrayList)((ArrayList)currentSliderValue.get("children")).clone()).iterator(); var15.hasNext(); ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("wiki.text." + mainCategory.get("name") + ".title"), 230.0F, (float)(initialCategoriesOffset + 10 + offsetCategories), selectedCategory.equals((String)mainCategory.get("name"))?16777215:(hoveredCategory.equals((String)mainCategory.get("name"))?7239406:12895428), 3.0F, "left", false, selectedCategory.equals((String)mainCategory.get("name"))?"georamaBold":"georamaRegular", 30)) {
               mainCategory = (HashMap)var15.next();
               if(selectedCategory.isEmpty()) {
                  selectedCategory = (String)mainCategory.get("name");
                  defaultCategory = (String)mainCategory.get("name");
                  selectedMainCategory = (String)currentSliderValue.get("name");
               }

               offsetCategories += 70;
               if(mouseXScaled >= 230 && mouseXScaled <= 800 && mouseYScaled >= initialCategoriesOffset + 10 + offsetCategories && mouseYScaled <= initialCategoriesOffset + 10 + offsetCategories + 40) {
                  hoveredCategory = (String)mainCategory.get("name");
               }
            }

            Gui.func_73734_a(108, initialCategoriesOffset + offsetCategories + 70 + 30, 848, initialCategoriesOffset + offsetCategories + 70 + 30 + 2, -15197637);
            offsetCategories += 140;
         } else {
            offsetCategories += 100;
         }
      }

      if(!selectedMainCategory.isEmpty()) {
         int var25 = 0;
         float var26 = this.scrollBar.getSliderValue() * (float)selectedMainCategoryFullHeight;
         var15 = ((ArrayList)ClientProxy.wiki.clone()).iterator();

         while(var15.hasNext()) {
            mainCategory = (HashMap)var15.next();
            if(((String)mainCategory.get("name")).equals(selectedMainCategory) && mainCategory.containsKey("children")) {
               String tempSelectedCategory = "";
               GUIUtils.startGLScissor((int)(950.0F * ((float)this.field_73880_f / 3840.0F)), (int)(0.0F * ((float)this.field_73881_g / 2160.0F)), (int)(2890.0F * ((float)this.field_73880_f / 3840.0F)), (int)(2160.0F * ((float)this.field_73881_g - 0.0F * ((float)windowHeight / 2160.0F) / 2160.0F)));
               Float offsetY = Float.valueOf((float)(0 + var25) + this.getSlide());
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               ClientProxy.loadResource("textures/gui/wiki/images/" + mainCategory.get("name") + "/top.png");
               ModernGui.drawModalRectWithCustomSizedTexture(950.0F, offsetY.floatValue(), 0, 0, 2890, 396, 2890.0F, 396.0F, false);
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("wiki.text." + mainCategory.get("name") + ".title").toUpperCase(), 1110.0F, (float)(offsetY.intValue() + 110), 16777215, 4.0F, "left", false, "georamaSemiBold", 35);
               if(mainCategory.containsKey("tags")) {
                  int tagIndex = 0;

                  for(Iterator childCategory = ((List)mainCategory.get("tags")).iterator(); childCategory.hasNext(); ++tagIndex) {
                     String tag = (String)childCategory.next();
                     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                     ClientProxy.loadResource("textures/gui/wiki/tag.png");
                     ModernGui.drawModalRectWithCustomSizedTexture((float)(1110 + tagIndex * 205), (float)(offsetY.intValue() + 190), 0, 0, 186, 44, 186.0F, 44.0F, false);
                     ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("wiki.tag." + tag).toUpperCase(), (float)(1110 + tagIndex * 205 + 93), (float)(offsetY.intValue() + 198), 16777215, 2.0F, "center", false, "georamaMedium", 30);
                  }
               }

               ModernGui.drawSectionStringCustomFont(I18n.func_135053_a("wiki.text." + mainCategory.get("name") + ".description"), 1110.0F, (float)(offsetY.intValue() + 260), 12895428, 2.0F, "left", false, "georamaMedium", 33, 20, 600);
               var25 += 396;

               HashMap var28;
               for(Iterator var27 = ((ArrayList)((ArrayList)mainCategory.get("children")).clone()).iterator(); var27.hasNext(); var25 = (int)((long)var25 + ((Long)var28.get("height")).longValue())) {
                  var28 = (HashMap)var27.next();
                  offsetY = Float.valueOf((float)(0 + var25) + this.getSlide());
                  if(!categoryScrollOffset.containsKey(mainCategory.get("name") + "#" + var28.get("name"))) {
                     categoryScrollOffset.put(mainCategory.get("name") + "#" + var28.get("name"), Integer.valueOf(var25));
                  }

                  if(tempSelectedCategory.isEmpty() && offsetY.floatValue() > (float)(-(((Long)var28.get("height")).longValue() / 2L)) && offsetY.floatValue() < 1800.0F || (long)selectedMainCategoryFullHeight == (long)var25 + ((Long)var28.get("height")).longValue() && this.scrollBar.getSliderValue() == 1.0F) {
                     tempSelectedCategory = (String)var28.get("name");
                  }

                  Gui.func_73734_a(950, offsetY.intValue(), 3840, offsetY.intValue() + ((Long)var28.get("height")).intValue(), var28.containsKey("color")?(int)Long.parseLong(((String)var28.get("color")).replace("0x", ""), 16):-15197637);
                  GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                  Object imgObj;
                  JSONObject img;
                  Iterator var29;
                  if(var28.containsKey("texts")) {
                     var29 = ((JSONArray)var28.get("texts")).iterator();

                     while(var29.hasNext()) {
                        imgObj = var29.next();
                        if(imgObj instanceof JSONObject) {
                           img = (JSONObject)imgObj;
                           String textTrad;
                           if(((String)img.get("name")).equals("title")) {
                              textTrad = I18n.func_135053_a("wiki.text." + var28.get("name") + ".title");
                              if(textTrad.equals("wiki.text." + var28.get("name") + ".title")) {
                                 textTrad = (String)var28.get("name");
                              }

                              ModernGui.drawScaledStringCustomFont(textTrad, (float)(950 + ((Long)img.get("posX")).intValue()), (float)(offsetY.intValue() + ((Long)img.get("posY")).intValue()), 16777215, 4.0F, (String)img.get("align"), false, img.containsKey("font")?(String)img.get("font"):"georamaSemiBold", 32);
                           } else {
                              textTrad = I18n.func_135053_a("wiki.text." + var28.get("name") + "." + img.get("name"));
                              if(textTrad.equals("wiki.text." + var28.get("name") + "." + img.get("name"))) {
                                 textTrad = (String)img.get("name");
                              }

                              ModernGui.drawSectionStringCustomFont(textTrad, (float)(950 + ((Long)img.get("posX")).intValue()), (float)(offsetY.intValue() + ((Long)img.get("posY")).intValue()), img.containsKey("color")?(int)Long.parseLong(((String)img.get("color")).replace("0x", ""), 16):-3881788, img.containsKey("scale")?((Double)img.get("scale")).floatValue():3.0F, (String)img.get("align"), false, img.containsKey("font")?(String)img.get("font"):"georamaRegular", img.containsKey("size")?((Long)img.get("size")).intValue():30, 60, img.containsKey("width")?((Long)img.get("width")).intValue():500);
                           }
                        }
                     }
                  }

                  if(var28.containsKey("images")) {
                     var29 = ((JSONArray)var28.get("images")).iterator();

                     while(var29.hasNext()) {
                        imgObj = var29.next();
                        if(imgObj instanceof JSONObject) {
                           img = (JSONObject)imgObj;
                           GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                           if(img.containsKey("name")) {
                              ClientProxy.loadResource("textures/gui/wiki/images/" + mainCategory.get("name") + "/" + img.get("name") + ".png");
                           } else if(img.containsKey("url")) {
                              ModernGui.bindRemoteTexture((String)img.get("url"));
                           }

                           ModernGui.drawModalRectWithCustomSizedTexture((float)(950 + ((Long)img.get("posX")).intValue()), (float)(offsetY.intValue() + ((Long)img.get("posY")).intValue()), 0, 0, ((Long)img.get("width")).intValue(), ((Long)img.get("height")).intValue(), (float)((Long)img.get("width")).intValue(), (float)((Long)img.get("height")).intValue(), false);
                        }
                     }
                  }
               }

               selectedCategory = !tempSelectedCategory.isEmpty()?tempSelectedCategory:defaultCategory;
               if(selectedMainCategoryFullHeight == 0) {
                  selectedMainCategoryFullHeight = var25;
               }

               GUIUtils.endGLScissor();
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               ClientProxy.loadResource("textures/gui/wiki/scrollbar.png");
               ModernGui.drawModalRectWithCustomSizedTexture(3780.0F, 440.0F, 0, 0, 24, 1500, 24.0F, 1500.0F, false);
               this.scrollBar.draw(mouseXScaled, mouseYScaled);
            }
         }
      }

      if(mouseXScaled >= 3770 && mouseXScaled <= 3802 && mouseYScaled >= 50 && mouseYScaled <= 82) {
         hoveredAction = "close";
      }

      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ClientProxy.loadResource("textures/gui/generic/cross_" + (hoveredAction.equals("close")?"white":"gray") + ".png");
      ModernGui.drawModalRectWithCustomSizedTexture(3770.0F, 50.0F, 0, 0, 32, 32, 32.0F, 32.0F, true);
      if(!toolTipLines.isEmpty()) {
         this.drawHoveringText(toolTipLines, mouseXScaled, mouseYScaled, this.field_73886_k);
      }

      GL11.glPopMatrix();
   }

   public void openURL(String url) {
      Desktop desktop = Desktop.getDesktop();
      if(desktop.isSupported(Action.BROWSE)) {
         try {
            desktop.browse(new URI(url));
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }

   }

   private float getSlide() {
      float test = (float)this.field_73881_g * (2160.0F / (float)this.field_73881_g);
      return (float)selectedMainCategoryFullHeight > test?-((float)(selectedMainCategoryFullHeight + 100) - test) * this.scrollBar.getSliderValue():0.0F;
   }

   public void scrollToCategory(String category) {
      if(categoryScrollOffset.containsKey(selectedMainCategory + "#" + category)) {
         int scrollOffset = ((Integer)categoryScrollOffset.get(selectedMainCategory + "#" + category)).intValue();
         int totalScrollHeight = selectedMainCategoryFullHeight + 100 - (int)((float)this.field_73881_g * (2160.0F / (float)this.field_73881_g));
         float scrollValue = Math.min(1.0F, (float)scrollOffset * 1.0F / (float)Math.max(1, totalScrollHeight));
         this.scrollBar.setSliderValue(scrollValue);
      }

   }

   protected void func_73864_a(int par1, int par2, int par3) {
      int windowWidth = this.field_73880_f;
      int windowHeight = this.field_73880_f * 9 / 16;
      int var10000 = (int)((float)par1 * (3840.0F / (float)this.field_73880_f));
      var10000 = (int)((float)par2 * (2160.0F / (float)windowHeight));
      if(!hoveredMainCategory.isEmpty()) {
         selectedMainCategory = hoveredMainCategory;
         selectedMainCategoryFullHeight = 0;
         selectedCategory = "";
         this.scrollBar.setSliderValue(0.0F);
      } else if(!hoveredCategory.isEmpty()) {
         selectedCategory = hoveredCategory;
         this.scrollToCategory(hoveredCategory);
      } else if(!hoveredAction.isEmpty() && hoveredAction.equals("close")) {
         Minecraft.func_71410_x().func_71373_a(this.guiFrom);
      }

      this.inputSearch.func_73793_a(par1, par2, par3);
      super.func_73864_a(par1, par2, par3);
   }

   protected void drawHoveringText(List par1List, int par2, int par3, FontRenderer font) {
      if(!par1List.isEmpty()) {
         GL11.glDisable('\u803a');
         RenderHelper.func_74518_a();
         GL11.glDisable(2896);
         GL11.glDisable(2929);
         int k = 0;
         Iterator iterator = par1List.iterator();

         int j1;
         while(iterator.hasNext()) {
            String i1 = (String)iterator.next();
            j1 = font.func_78256_a(i1);
            if(j1 > k) {
               k = j1;
            }
         }

         int var15 = par2 + 12;
         j1 = par3 - 12;
         int k1 = 8;
         if(par1List.size() > 1) {
            k1 += 2 + (par1List.size() - 1) * 10;
         }

         if(var15 + k > this.field_73880_f) {
            var15 -= 28 + k;
         }

         if(j1 + k1 + 6 > this.field_73881_g) {
            j1 = this.field_73881_g - k1 - 6;
         }

         this.field_73735_i = 300.0F;
         this.itemRenderer.field_77023_b = 300.0F;
         int l1 = -267386864;
         this.func_73733_a(var15 - 3, j1 - 4, var15 + k + 3, j1 - 3, l1, l1);
         this.func_73733_a(var15 - 3, j1 + k1 + 3, var15 + k + 3, j1 + k1 + 4, l1, l1);
         this.func_73733_a(var15 - 3, j1 - 3, var15 + k + 3, j1 + k1 + 3, l1, l1);
         this.func_73733_a(var15 - 4, j1 - 3, var15 - 3, j1 + k1 + 3, l1, l1);
         this.func_73733_a(var15 + k + 3, j1 - 3, var15 + k + 4, j1 + k1 + 3, l1, l1);
         int i2 = 1347420415;
         int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
         this.func_73733_a(var15 - 3, j1 - 3 + 1, var15 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
         this.func_73733_a(var15 + k + 2, j1 - 3 + 1, var15 + k + 3, j1 + k1 + 3 - 1, i2, j2);
         this.func_73733_a(var15 - 3, j1 - 3, var15 + k + 3, j1 - 3 + 1, i2, i2);
         this.func_73733_a(var15 - 3, j1 + k1 + 2, var15 + k + 3, j1 + k1 + 3, j2, j2);

         for(int k2 = 0; k2 < par1List.size(); ++k2) {
            String s1 = (String)par1List.get(k2);
            font.func_78261_a(s1, var15, j1, -1);
            if(k2 == 0) {
               j1 += 2;
            }

            j1 += 10;
         }

         this.field_73735_i = 0.0F;
         this.itemRenderer.field_77023_b = 0.0F;
         GL11.glDisable(2896);
         GL11.glDisable(2929);
         GL11.glEnable('\u803a');
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      }

   }

}
