package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGui_OLD;
import net.ilexiconn.nationsgui.forge.client.gui.faction.SearchGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.SearchSellCountryGui$1;
import net.ilexiconn.nationsgui.forge.client.gui.faction.SearchSellCountryGui$2;
import net.ilexiconn.nationsgui.forge.client.gui.faction.SearchSellCountryGui$3;
import net.ilexiconn.nationsgui.forge.client.gui.faction.SearchSellCountryGui$4;
import net.ilexiconn.nationsgui.forge.client.gui.faction.SearchSellCountryGui$5;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionSearchSellCountryDataPacket;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class SearchSellCountryGui extends GuiScreen {

   public static final List<GuiScreenTab> TABS = new ArrayList();
   protected int xSize = 319;
   protected int ySize = 250;
   private int guiLeft;
   private int guiTop;
   public static boolean loaded = false;
   private RenderItem itemRenderer = new RenderItem();
   private GuiScrollBarFaction scrollBar;
   public static ArrayList<HashMap<String, String>> countries = new ArrayList();
   private HashMap<String, String> hoveredCountry = new HashMap();
   private boolean open_filter = false;
   private String searchText = "";
   private GuiTextField countrySearch;


   public SearchSellCountryGui() {
      loaded = false;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionSearchSellCountryDataPacket()));
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - this.ySize) / 2;
      this.scrollBar = new GuiScrollBarFaction((float)(this.guiLeft + 296), (float)(this.guiTop + 64), 150);
      this.countrySearch = new GuiTextField(this.field_73886_k, this.guiLeft + 208, this.guiTop + 28, 93, 10);
      this.countrySearch.func_73786_a(false);
      this.countrySearch.func_73804_f(40);
   }

   public void func_73876_c() {
      this.countrySearch.func_73780_a();
   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      this.func_73873_v_();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ClientEventHandler.STYLE.bindTexture("faction_search");
      ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
      this.countrySearch.func_73795_f();
      ClientEventHandler.STYLE.bindTexture("faction_search");

      int filterOffsetY;
      int offsetX;
      for(filterOffsetY = 0; filterOffsetY < TABS.size(); ++filterOffsetY) {
         GuiScreenTab i = (GuiScreenTab)TABS.get(filterOffsetY);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         offsetX = filterOffsetY % 5;
         int offsetY = filterOffsetY / 5;
         if(this.getClass() == i.getClassReferent()) {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23), (float)(this.guiTop + 20 + filterOffsetY * 31), 23, 251, 29, 30, 512.0F, 512.0F, false);
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23 + 4), (float)(this.guiTop + 20 + filterOffsetY * 31 + 5), offsetX * 20, 298 + offsetY * 20, 20, 20, 512.0F, 512.0F, false);
         } else {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23), (float)(this.guiTop + 20 + filterOffsetY * 31), 0, 251, 23, 30, 512.0F, 512.0F, false);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23 + 4), (float)(this.guiTop + 20 + filterOffsetY * 31 + 5), offsetX * 20, 298 + offsetY * 20, 20, 20, 512.0F, 512.0F, false);
            GL11.glDisable(3042);
         }
      }

      if(mouseX >= this.guiLeft + 304 && mouseX <= this.guiLeft + 304 + 9 && mouseY >= this.guiTop - 6 && mouseY <= this.guiTop - 6 + 10) {
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 304), (float)(this.guiTop - 6), 138, 261, 9, 10, 512.0F, 512.0F, false);
      } else {
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 304), (float)(this.guiTop - 6), 138, 251, 9, 10, 512.0F, 512.0F, false);
      }

      this.drawScaledString(I18n.func_135053_a("faction.search_sell.title"), this.guiLeft + 50, this.guiTop + 20, 1644825, 1.0F, false, false);
      this.drawScaledString(I18n.func_135053_a("faction.search.country"), this.guiLeft + 51 + 5, this.guiTop + 50, 1644825, 0.9F, false, false);
      this.drawScaledString(I18n.func_135053_a("faction.search.level"), this.guiLeft + 51 + 80, this.guiTop + 50, 1644825, 0.9F, false, false);
      this.drawScaledString(I18n.func_135053_a("faction.search_sell.nb_members"), this.guiLeft + 51 + 130, this.guiTop + 50, 1644825, 0.9F, false, false);
      this.drawScaledString(I18n.func_135053_a("faction.search_sell.price"), this.guiLeft + 51 + 195, this.guiTop + 50, 1644825, 0.9F, false, false);
      if(loaded && countries.size() > 0) {
         this.hoveredCountry = new HashMap();
         filterOffsetY = 0;
         GUIUtils.startGLScissor(this.guiLeft + 51, this.guiTop + 60, 245, 158);

         for(int var8 = 0; var8 < countries.size(); ++var8) {
            if(countries.get(var8) != null) {
               if(!this.searchText.isEmpty() && (this.searchText.isEmpty() || !((String)((HashMap)countries.get(var8)).get("name")).toLowerCase().contains(this.searchText.toLowerCase()))) {
                  ++filterOffsetY;
               } else {
                  offsetX = this.guiLeft + 51;
                  Float var9 = Float.valueOf((float)(this.guiTop + 60 + (var8 - filterOffsetY) * 20) + this.getSlide());
                  ClientEventHandler.STYLE.bindTexture("faction_search");
                  ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)var9.intValue(), 51, 60, 245, 20, 512.0F, 512.0F, false);
                  this.drawScaledString(((String)((HashMap)countries.get(var8)).get("name")).length() < 14?(String)((HashMap)countries.get(var8)).get("name"):((String)((HashMap)countries.get(var8)).get("name")).substring(0, 13) + "..", offsetX + 5, var9.intValue() + 5, 16777215, 1.0F, false, false);
                  this.drawScaledString((String)((HashMap)countries.get(var8)).get("level"), offsetX + 80, var9.intValue() + 5, 16777215, 1.0F, false, false);
                  this.drawScaledString((String)((HashMap)countries.get(var8)).get("nb_members"), offsetX + 130, var9.intValue() + 5, 16777215, 1.0F, false, false);
                  this.drawScaledString((String)((HashMap)countries.get(var8)).get("price") + "$", offsetX + 195, var9.intValue() + 5, 16777215, 1.0F, false, false);
                  if(mouseX > this.guiLeft + 51 && mouseX < this.guiLeft + 51 + 245 && mouseY > this.guiTop + 60 && mouseY < this.guiTop + 60 + 158 && mouseX > offsetX && mouseX < offsetX + 245 && (float)mouseY > var9.floatValue() && (float)mouseY < var9.floatValue() + 20.0F) {
                     this.hoveredCountry = (HashMap)countries.get(var8);
                  }
               }
            }
         }

         GUIUtils.endGLScissor();
         this.scrollBar.draw(mouseX, mouseY);
      }

      super.func_73863_a(mouseX, mouseY, par3);
      GL11.glEnable(2896);
      RenderHelper.func_74519_b();
   }

   private float getSlide() {
      return countries.size() > 8?(float)(-(countries.size() - 8) * 20) * this.scrollBar.getSliderValue():0.0F;
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      int i;
      if(mouseButton == 0) {
         for(i = 0; i < TABS.size(); ++i) {
            GuiScreenTab type = (GuiScreenTab)TABS.get(i);
            if(mouseX >= this.guiLeft - 20 && mouseX <= this.guiLeft + 3 && mouseY >= this.guiTop + 20 + i * 31 && mouseY <= this.guiTop + 50 + i * 31) {
               this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
               if(this.getClass() != type.getClassReferent()) {
                  try {
                     type.call();
                  } catch (Exception var7) {
                     var7.printStackTrace();
                  }
               }
            }
         }

         if(mouseX > this.guiLeft + 304 && mouseX < this.guiLeft + 304 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            this.field_73882_e.func_71373_a((GuiScreen)null);
         }

         if(this.hoveredCountry != null && !this.hoveredCountry.isEmpty()) {
            FactionGui_OLD.loaded = false;
            this.field_73882_e.func_71373_a(new FactionGui_OLD((String)this.hoveredCountry.get("name")));
         }
      }

      this.countrySearch.func_73793_a(mouseX, mouseY, mouseButton);

      for(i = 0; i < SearchGui.TABS.size(); ++i) {
         if(mouseX >= this.guiLeft - 23 && mouseX <= this.guiLeft - 23 + 29 && mouseY >= this.guiTop + 20 + i * 31 && mouseY <= this.guiTop + 20 + 30 + i * 31) {
            this.drawHoveringText(Arrays.asList(new String[]{I18n.func_135053_a("faction.tab.search." + i)}), mouseX, mouseY, this.field_73886_k);
         }
      }

      super.func_73864_a(mouseX, mouseY, mouseButton);
   }

   protected void func_73869_a(char typedChar, int keyCode) {
      if(this.countrySearch.func_73802_a(typedChar, keyCode)) {
         this.searchText = this.countrySearch.func_73781_b();
      }

      super.func_73869_a(typedChar, keyCode);
   }

   public void drawScaledString(String text, int x, int y, int color, float scale, boolean centered, boolean shadow) {
      GL11.glPushMatrix();
      GL11.glScalef(scale, scale, scale);
      float newX = (float)x;
      if(centered) {
         newX = (float)x - (float)this.field_73886_k.func_78256_a(text) * scale / 2.0F;
      }

      if(shadow) {
         this.field_73886_k.func_85187_a(text, (int)(newX / scale), (int)((float)(y + 1) / scale), (color & 16579836) >> 2 | color & -16777216, false);
      }

      this.field_73886_k.func_85187_a(text, (int)(newX / scale), (int)((float)y / scale), color, false);
      GL11.glPopMatrix();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public boolean func_73868_f() {
      return false;
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

   static {
      TABS.add(new SearchSellCountryGui$1());
      TABS.add(new SearchSellCountryGui$2());
      TABS.add(new SearchSellCountryGui$3());
      TABS.add(new SearchSellCountryGui$4());
      TABS.add(new SearchSellCountryGui$5());
   }
}
