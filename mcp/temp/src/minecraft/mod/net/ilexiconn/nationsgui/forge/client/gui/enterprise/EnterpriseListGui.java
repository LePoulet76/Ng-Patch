package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

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
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseCreateGui;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseListDataPacket;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class EnterpriseListGui extends GuiScreen {

   protected int xSize = 319;
   protected int ySize = 250;
   private int guiLeft;
   private int guiTop;
   public static boolean loaded = false;
   private RenderItem itemRenderer = new RenderItem();
   private GuiScrollBarFaction scrollBarEnterprises;
   private GuiScrollBarFaction scrollBarTypes;
   public static ArrayList<HashMap<String, String>> enterprises = new ArrayList();
   private HashMap<String, String> hoveredEnterprise = new HashMap();
   private String hoveredType;
   private String selectedType = "all";
   private boolean typesExpanded;
   private boolean open_filter = false;
   private String searchText = "";
   private GuiTextField enterpriseSearch;


   public EnterpriseListGui() {
      loaded = false;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      enterprises.clear();
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterpriseListDataPacket()));
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - this.ySize) / 2;
      this.scrollBarEnterprises = new GuiScrollBarFaction((float)(this.guiLeft + 296), (float)(this.guiTop + 64), 150);
      this.scrollBarTypes = new GuiScrollBarFaction((float)(this.guiLeft + 155), (float)(this.guiTop + 45), 90);
      this.enterpriseSearch = new GuiTextField(this.field_73886_k, this.guiLeft + 208, this.guiTop + 28, 93, 10);
      this.enterpriseSearch.func_73786_a(false);
      this.enterpriseSearch.func_73804_f(40);
   }

   public void func_73876_c() {
      this.enterpriseSearch.func_73780_a();
   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      this.func_73873_v_();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ClientEventHandler.STYLE.bindTexture("enterprise_list");
      ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
      ClientEventHandler.STYLE.bindTexture("enterprise_create");

      int i;
      int offsetY;
      for(i = 0; i < EnterpriseCreateGui.TABS.size(); ++i) {
         GuiScreenTab offsetX = (GuiScreenTab)EnterpriseCreateGui.TABS.get(i);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         offsetY = i % 5;
         int offsetY1 = i / 5;
         if(this.getClass() == offsetX.getClassReferent()) {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23), (float)(this.guiTop + 20 + i * 31), 23, 251, 29, 30, 512.0F, 512.0F, false);
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23 + 4), (float)(this.guiTop + 20 + i * 31 + 5), offsetY * 20, 298 + offsetY1 * 20, 20, 20, 512.0F, 512.0F, false);
         } else {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23), (float)(this.guiTop + 20 + i * 31), 0, 251, 23, 30, 512.0F, 512.0F, false);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23 + 4), (float)(this.guiTop + 20 + i * 31 + 5), offsetY * 20, 298 + offsetY1 * 20, 20, 20, 512.0F, 512.0F, false);
            GL11.glDisable(3042);
         }
      }

      this.enterpriseSearch.func_73795_f();
      ClientEventHandler.STYLE.bindTexture("enterprise_list");
      if(mouseX >= this.guiLeft + 304 && mouseX <= this.guiLeft + 304 + 9 && mouseY >= this.guiTop - 6 && mouseY <= this.guiTop - 6 + 10) {
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 304), (float)(this.guiTop - 6), 138, 261, 9, 10, 512.0F, 512.0F, false);
      } else {
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 304), (float)(this.guiTop - 6), 138, 251, 9, 10, 512.0F, 512.0F, false);
      }

      if(!this.selectedType.equalsIgnoreCase("all")) {
         ClientEventHandler.STYLE.bindTexture("enterprise_main");
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 53), (float)(this.guiTop + 23), EnterpriseGui.getTypeOffsetX(this.selectedType), 442, 16, 16, 512.0F, 512.0F, false);
         this.drawScaledString(I18n.func_135053_a("enterprise.type." + this.selectedType), this.guiLeft + 53 + 16 + 2, this.guiTop + 27, 16777215, 1.0F, false, false);
         ClientEventHandler.STYLE.bindTexture("enterprise_list");
      } else {
         this.drawScaledString(I18n.func_135053_a("enterprise.type.all"), this.guiLeft + 53, this.guiTop + 27, 16777215, 1.0F, false, false);
      }

      this.drawScaledString(I18n.func_135053_a("enterprise.list.name"), this.guiLeft + 51 + 5, this.guiTop + 50, 1644825, 0.9F, false, false);
      this.drawScaledString(I18n.func_135053_a("enterprise.list.type"), this.guiLeft + 51 + 100, this.guiTop + 50, 1644825, 0.9F, false, false);
      this.drawScaledString(I18n.func_135053_a("enterprise.list.satisfaction"), this.guiLeft + 51 + 155, this.guiTop + 50, 1644825, 0.9F, false, false);
      this.drawScaledString(I18n.func_135053_a("enterprise.list.online_players"), this.guiLeft + 51 + 210, this.guiTop + 50, 1644825, 0.9F, false, false);
      this.drawScaledString(I18n.func_135053_a("enterprise.list.open_filter"), this.guiLeft + 64, this.guiTop + 227, 1644825, 0.9F, false, false);
      ClientEventHandler.STYLE.bindTexture("enterprise_list");
      if(this.open_filter) {
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 50), (float)(this.guiTop + 225), 159, 250, 10, 10, 512.0F, 512.0F, false);
      } else {
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 50), (float)(this.guiTop + 225), 169, 250, 10, 10, 512.0F, 512.0F, false);
      }

      int var8;
      if(loaded && enterprises.size() > 0) {
         this.hoveredEnterprise = new HashMap();
         i = 0;
         GUIUtils.startGLScissor(this.guiLeft + 51, this.guiTop + 60, 245, 158);

         for(var8 = 0; var8 < enterprises.size(); ++var8) {
            if((!this.open_filter || this.open_filter && ((String)((HashMap)enterprises.get(var8)).get("open")).equals("true")) && (this.searchText.isEmpty() || !this.searchText.isEmpty() && ((String)((HashMap)enterprises.get(var8)).get("name")).toLowerCase().contains(this.searchText.toLowerCase())) && (this.selectedType.equals("all") || ((String)((HashMap)enterprises.get(var8)).get("type")).equals(this.selectedType))) {
               offsetY = this.guiLeft + 51;
               Float var10 = Float.valueOf((float)(this.guiTop + 60 + (var8 - i) * 20) + this.getSlideEnterprises());
               ClientEventHandler.STYLE.bindTexture("enterprise_list");
               ModernGui.drawModalRectWithCustomSizedTexture((float)offsetY, (float)var10.intValue(), 51, 60, 245, 20, 512.0F, 512.0F, false);
               this.drawScaledString((String)((HashMap)enterprises.get(var8)).get("name"), offsetY + 5, var10.intValue() + 5, 16777215, 1.0F, false, false);
               ClientEventHandler.STYLE.bindTexture("enterprise_main");
               ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetY + 100), (float)(var10.intValue() + 1), EnterpriseGui.getTypeOffsetX((String)((HashMap)enterprises.get(var8)).get("type")), 442, 16, 16, 512.0F, 512.0F, false);
               ClientEventHandler.STYLE.bindTexture("enterprise_list");
               this.drawScaledString((String)((HashMap)enterprises.get(var8)).get("satisfaction"), offsetY + 155, var10.intValue() + 5, 16777215, 1.0F, false, false);
               this.drawScaledString((String)((HashMap)enterprises.get(var8)).get("online_players") + "/" + (String)((HashMap)enterprises.get(var8)).get("max_players"), offsetY + 210, var10.intValue() + 5, 16777215, 1.0F, false, false);
               if(!this.typesExpanded && mouseX > this.guiLeft + 51 && mouseX < this.guiLeft + 51 + 245 && mouseY > this.guiTop + 60 && mouseY < this.guiTop + 60 + 158 && mouseX > offsetY && mouseX < offsetY + 245 && (float)mouseY > var10.floatValue() && (float)mouseY < var10.floatValue() + 20.0F) {
                  this.hoveredEnterprise = (HashMap)enterprises.get(var8);
               }
            } else {
               ++i;
            }
         }

         GUIUtils.endGLScissor();
         if(!this.typesExpanded) {
            this.scrollBarEnterprises.draw(mouseX, mouseY);
         }
      }

      this.hoveredType = null;
      if(this.typesExpanded) {
         ClientEventHandler.STYLE.bindTexture("enterprise_list");
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 50), (float)(this.guiTop + 40), 180, 250, 110, 99, 512.0F, 512.0F, false);
         GUIUtils.startGLScissor(this.guiLeft + 51, this.guiTop + 41, 104, 97);

         for(i = 0; i < EnterpriseGui.availableTypes.size(); ++i) {
            var8 = this.guiLeft + 51;
            Float var9 = Float.valueOf((float)(this.guiTop + 41 + i * 20) + this.getSlideTypes());
            ClientEventHandler.STYLE.bindTexture("enterprise_list");
            ModernGui.drawModalRectWithCustomSizedTexture((float)var8, (float)var9.intValue(), 181, 251, 104, 20, 512.0F, 512.0F, false);
            if(!((String)EnterpriseGui.availableTypes.get(i)).equalsIgnoreCase("all")) {
               ClientEventHandler.STYLE.bindTexture("enterprise_main");
               ModernGui.drawModalRectWithCustomSizedTexture((float)(var8 + 2), (float)(var9.intValue() + 1), EnterpriseGui.getTypeOffsetX((String)EnterpriseGui.availableTypes.get(i)), 442, 16, 16, 512.0F, 512.0F, false);
               this.drawScaledString(I18n.func_135053_a("enterprise.type." + (String)EnterpriseGui.availableTypes.get(i)), var8 + 2 + 16 + 2, var9.intValue() + 5, 16777215, 1.0F, false, false);
               ClientEventHandler.STYLE.bindTexture("enterprise_list");
            } else {
               this.drawScaledString(I18n.func_135053_a("enterprise.type.all"), var8 + 2, var9.intValue() + 5, 16777215, 1.0F, false, false);
            }

            if(mouseX > var8 && mouseX < var8 + 104 && (float)mouseY > var9.floatValue() && (float)mouseY < var9.floatValue() + 20.0F) {
               this.hoveredType = (String)EnterpriseGui.availableTypes.get(i);
            }
         }

         GUIUtils.endGLScissor();
         this.scrollBarTypes.draw(mouseX, mouseY);
      }

      super.func_73863_a(mouseX, mouseY, par3);

      for(i = 0; i < EnterpriseCreateGui.TABS.size(); ++i) {
         if(mouseX >= this.guiLeft - 23 && mouseX <= this.guiLeft - 23 + 29 && mouseY >= this.guiTop + 20 + i * 31 && mouseY <= this.guiTop + 20 + 30 + i * 31) {
            this.drawHoveringText(Arrays.asList(new String[]{I18n.func_135053_a("enterprise.tab.search." + i)}), mouseX, mouseY, this.field_73886_k);
         }
      }

      GL11.glEnable(2896);
      RenderHelper.func_74519_b();
   }

   private float getSlideEnterprises() {
      int counter = 0;

      for(int i = 0; i < enterprises.size(); ++i) {
         if((!this.open_filter || this.open_filter && ((String)((HashMap)enterprises.get(i)).get("open")).equals("true")) && (this.searchText.isEmpty() || !this.searchText.isEmpty() && ((String)((HashMap)enterprises.get(i)).get("name")).toLowerCase().contains(this.searchText.toLowerCase()))) {
            ++counter;
         }
      }

      return counter > 8?(float)(-(counter - 8) * 20) * this.scrollBarEnterprises.getSliderValue():0.0F;
   }

   private float getSlideTypes() {
      return EnterpriseGui.availableTypes.size() > 5?(float)(-(EnterpriseGui.availableTypes.size() - 5) * 20) * this.scrollBarTypes.getSliderValue():0.0F;
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0) {
         for(int i = 0; i < EnterpriseCreateGui.TABS.size(); ++i) {
            GuiScreenTab type = (GuiScreenTab)EnterpriseCreateGui.TABS.get(i);
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

         if(this.hoveredEnterprise != null && !this.hoveredEnterprise.isEmpty()) {
            this.field_73882_e.func_71373_a(new EnterpriseGui((String)this.hoveredEnterprise.get("name")));
         }

         if(mouseX >= this.guiLeft + 50 && mouseX <= this.guiLeft + 50 + 10 && mouseY >= this.guiTop + 225 && mouseY <= this.guiTop + 225 + 10) {
            this.open_filter = !this.open_filter;
         }

         if(mouseX >= this.guiLeft + 50 && mouseX <= this.guiLeft + 50 + 110 && mouseY >= this.guiTop + 21 && mouseY <= this.guiTop + 21 + 20) {
            this.typesExpanded = !this.typesExpanded;
         }

         if(this.hoveredType != null && !this.hoveredType.isEmpty()) {
            this.selectedType = this.hoveredType;
            this.hoveredType = null;
            this.typesExpanded = false;
            this.scrollBarEnterprises.setSliderValue(0.0F);
         }
      }

      this.enterpriseSearch.func_73793_a(mouseX, mouseY, mouseButton);
      super.func_73864_a(mouseX, mouseY, mouseButton);
   }

   protected void func_73869_a(char typedChar, int keyCode) {
      if(this.enterpriseSearch.func_73802_a(typedChar, keyCode)) {
         this.searchText = this.enterpriseSearch.func_73781_b();
         this.scrollBarEnterprises.setSliderValue(0.0F);
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

}
