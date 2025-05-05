package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.MinimapRenderer;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.faction.CreateGui$1;
import net.ilexiconn.nationsgui.forge.client.gui.faction.CreateGui$2;
import net.ilexiconn.nationsgui.forge.client.gui.faction.CreateGui$3;
import net.ilexiconn.nationsgui.forge.client.gui.faction.CreateGui$4;
import net.ilexiconn.nationsgui.forge.client.gui.faction.CreateGui$5;
import net.ilexiconn.nationsgui.forge.client.gui.faction.LeaveConfirmGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.SearchGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUIClientHooks;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionCreateCheckCountryDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionCreateDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionCreatePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.MinimapRequestPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class CreateGui extends GuiScreen {

   public static final List<GuiScreenTab> TABS = new ArrayList();
   protected int xSize = 319;
   protected int ySize = 250;
   private int guiLeft;
   private int guiTop;
   private boolean regenZone = false;
   private boolean announce = false;
   private RenderItem itemRenderer = new RenderItem();
   public static boolean loaded = false;
   private GuiScrollBarFaction scrollBar;
   public MinimapRenderer minimapRenderer = new MinimapRenderer(8, 8);
   boolean expanded = false;
   public static boolean playerHasCountry = false;
   public static ArrayList<HashMap<String, String>> countries = new ArrayList();
   public static HashMap<String, String> selectedCountry = new HashMap();
   private HashMap<String, String> hoveredCountry = new HashMap();
   public static boolean mapLoaded;
   public static String mapLoadedFor;
   private GuiButton createButton;
   private GuiButton redirectButton;
   private GuiButton leaveButton;


   public CreateGui() {
      loaded = false;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionCreateDataPacket()));
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionCreateCheckCountryDataPacket()));
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - this.ySize) / 2;
      this.scrollBar = new GuiScrollBarFaction((float)(this.guiLeft + 296), (float)(this.guiTop + 69), 150);
      this.createButton = new GuiButton(0, this.guiLeft + 186, this.guiTop + 201, 122, 20, I18n.func_135053_a("faction.create.create"));
      this.redirectButton = new GuiButton(1, this.guiLeft + 97, this.guiTop + 130, 160, 20, I18n.func_135053_a("faction.create.redirect"));
      this.leaveButton = new GuiButton(2, this.guiLeft + 97, this.guiTop + 140, 160, 20, I18n.func_135053_a("faction.create.leave"));
   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      this.func_73873_v_();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ClientEventHandler.STYLE.bindTexture("faction_create");
      ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
      if(countries != null && countries.size() > 0) {
         this.createButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
         if((NationsGUIClientHooks.whitelistedStaff == null || !NationsGUIClientHooks.whitelistedStaff.contains(Minecraft.func_71410_x().field_71439_g.getDisplayName())) && System.currentTimeMillis() - ClientProxy.clientConfig.currentServerTime.longValue() < 10800000L) {
            this.createButton.field_73742_g = false;
            if(mouseX >= this.guiLeft + 186 && mouseX <= this.guiLeft + 186 + 122 && mouseY >= this.guiTop + 201 && mouseY <= this.guiTop + 201 + 20) {
               this.drawHoveringText(Arrays.asList(new String[]{I18n.func_135053_a("faction.create.cooldown")}), mouseX, mouseY, this.field_73886_k);
            }
         }
      }

      ClientEventHandler.STYLE.bindTexture("faction_search");

      int i;
      for(i = 0; i < TABS.size(); ++i) {
         GuiScreenTab offsetX = (GuiScreenTab)TABS.get(i);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         int offsetY = i % 5;
         int y = i / 5;
         if(this.getClass() == offsetX.getClassReferent()) {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23), (float)(this.guiTop + 20 + i * 31), 23, 251, 29, 30, 512.0F, 512.0F, false);
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23 + 4), (float)(this.guiTop + 20 + i * 31 + 5), offsetY * 20, 298 + y * 20, 20, 20, 512.0F, 512.0F, false);
         } else {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23), (float)(this.guiTop + 20 + i * 31), 0, 251, 23, 30, 512.0F, 512.0F, false);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23 + 4), (float)(this.guiTop + 20 + i * 31 + 5), offsetY * 20, 298 + y * 20, 20, 20, 512.0F, 512.0F, false);
            GL11.glDisable(3042);
         }
      }

      if(mouseX >= this.guiLeft + 304 && mouseX <= this.guiLeft + 304 + 9 && mouseY >= this.guiTop - 6 && mouseY <= this.guiTop - 6 + 10) {
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 304), (float)(this.guiTop - 6), 138, 261, 9, 10, 512.0F, 512.0F, false);
      } else {
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 304), (float)(this.guiTop - 6), 138, 251, 9, 10, 512.0F, 512.0F, false);
      }

      this.drawScaledString(I18n.func_135053_a("faction.create.title"), this.guiLeft + 50, this.guiTop + 20, 1644825, 1.4F, false, false);
      this.drawScaledString(I18n.func_135053_a("faction.create.map"), this.guiLeft + 51, this.guiTop + 83, 1644825, 0.8F, false, false);
      this.drawScaledString(I18n.func_135053_a("faction.create.location"), this.guiLeft + 187, this.guiTop + 83, 1644825, 0.8F, false, false);
      ClientEventHandler.STYLE.bindTexture("faction_create");
      if(playerHasCountry) {
         Gui.func_73734_a(this.guiLeft + 45, this.guiTop + 30, this.guiLeft + 45 + 266, this.guiTop + 30 + 209, -1513240);
         this.drawScaledString(I18n.func_135053_a("faction.create.hascountry_1"), this.guiLeft + 177, this.guiTop + 100, 328965, 1.1F, true, false);
         this.drawScaledString(I18n.func_135053_a("faction.create.hascountry_2"), this.guiLeft + 177, this.guiTop + 110, 328965, 1.1F, true, false);
         this.leaveButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
      } else if(countries != null && countries.size() > 0) {
         if(selectedCountry != null && !selectedCountry.isEmpty()) {
            this.drawScaledString((String)selectedCountry.get("name"), this.guiLeft + 56, this.guiTop + 52, 16777215, 1.0F, false, false);
            this.drawScaledString((String)selectedCountry.get("x"), this.guiLeft + 213, this.guiTop + 98, 16777215, 1.0F, true, false);
            this.drawScaledString((String)selectedCountry.get("z"), this.guiLeft + 276, this.guiTop + 98, 16777215, 1.0F, true, false);
            ClientEventHandler.STYLE.bindTexture("faction_create");
            if(this.announce) {
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 186), (float)(this.guiTop + 155), 159, 250, 10, 10, 512.0F, 512.0F, false);
            } else {
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 186), (float)(this.guiTop + 155), 169, 250, 10, 10, 512.0F, 512.0F, false);
            }

            this.drawScaledString(I18n.func_135053_a("faction.create.announce"), this.guiLeft + 200, this.guiTop + 157, 1644825, 0.9F, false, false);
            ClientEventHandler.STYLE.bindTexture("faction_create");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 300), (float)(this.guiTop + 155), 148, 250, 10, 11, 512.0F, 512.0F, false);
            if(!mapLoaded || mapLoadedFor != null && !mapLoadedFor.equals(selectedCountry.get("name"))) {
               PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new MinimapRequestPacket(Integer.parseInt((String)selectedCountry.get("x")), Integer.parseInt((String)selectedCountry.get("z")), 8, 8)));
               mapLoaded = true;
               mapLoadedFor = (String)selectedCountry.get("name");
            }

            GL11.glDisable(2929);
            this.minimapRenderer.renderMap(this.guiLeft + 51, this.guiTop + 92, mouseX, mouseY, true);
            GL11.glEnable(2929);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            ClientEventHandler.STYLE.bindTexture("faction_create");
            GL11.glEnable(3042);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(3008);
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 50), (float)(this.guiTop + 91), 0, 344, 129, 129, 512.0F, 512.0F, false);
            GL11.glDisable(3042);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(3008);
         }

         if(this.expanded && countries.size() > 0) {
            ClientEventHandler.STYLE.bindTexture("faction_create");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 50), (float)(this.guiTop + 64), 241, 344, 253, 160, 512.0F, 512.0F, false);
            this.hoveredCountry = new HashMap();
            GUIUtils.startGLScissor(this.guiLeft + 51, this.guiTop + 65, 253, 160);
            if(countries.size() > 0) {
               for(i = 0; i < countries.size(); ++i) {
                  int var8 = this.guiLeft + 51;
                  Float var9 = Float.valueOf((float)(this.guiTop + 65 + i * 20) + this.getSlide());
                  ClientEventHandler.STYLE.bindTexture("faction_create");
                  ModernGui.drawModalRectWithCustomSizedTexture((float)var8, (float)var9.intValue(), 242, 345, 245, 20, 512.0F, 512.0F, false);
                  this.drawScaledString((String)((HashMap)countries.get(i)).get("name"), var8 + 5, var9.intValue() + 5, 16777215, 1.0F, false, false);
                  if(mouseX >= var8 && mouseX <= var8 + 245 && (float)mouseY >= var9.floatValue() && (float)mouseY <= var9.floatValue() + 20.0F) {
                     this.hoveredCountry = (HashMap)countries.get(i);
                  }
               }
            }

            GUIUtils.endGLScissor();
            this.scrollBar.draw(mouseX, mouseY);
         }
      } else {
         Gui.func_73734_a(this.guiLeft + 45, this.guiTop + 30, this.guiLeft + 45 + 266, this.guiTop + 30 + 209, -1513240);
         this.drawScaledString(I18n.func_135053_a("faction.create.unavailable"), this.guiLeft + 177, this.guiTop + 100, 328965, 1.1F, true, false);
         this.redirectButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
      }

      for(i = 0; i < SearchGui.TABS.size(); ++i) {
         if(mouseX >= this.guiLeft - 23 && mouseX <= this.guiLeft - 23 + 29 && mouseY >= this.guiTop + 20 + i * 31 && mouseY <= this.guiTop + 20 + 30 + i * 31) {
            this.drawHoveringText(Arrays.asList(new String[]{I18n.func_135053_a("faction.tab.search." + i)}), mouseX, mouseY, this.field_73886_k);
         }
      }

      GL11.glEnable(2896);
      RenderHelper.func_74519_b();
   }

   private float getSlide() {
      return countries.size() > 8?(float)(-(countries.size() - 8) * 20) * this.scrollBar.getSliderValue():0.0F;
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0) {
         for(int e = 0; e < TABS.size(); ++e) {
            GuiScreenTab type = (GuiScreenTab)TABS.get(e);
            if(mouseX >= this.guiLeft - 20 && mouseX <= this.guiLeft + 3 && mouseY >= this.guiTop + 20 + e * 31 && mouseY <= this.guiTop + 50 + e * 31) {
               this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
               if(this.getClass() != type.getClassReferent()) {
                  try {
                     type.call();
                  } catch (Exception var8) {
                     var8.printStackTrace();
                  }
               }
            }
         }

         if(mouseX >= this.guiLeft + 284 && mouseX <= this.guiLeft + 302 && mouseY >= this.guiTop + 46 && mouseY <= this.guiTop + 64) {
            if(!this.expanded) {
               this.expanded = true;
            } else {
               this.expanded = false;
            }
         }

         if(mouseX > this.guiLeft + 304 && mouseX < this.guiLeft + 304 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            this.field_73882_e.func_71373_a((GuiScreen)null);
         }

         if(this.hoveredCountry != null && !this.hoveredCountry.isEmpty()) {
            selectedCountry = this.hoveredCountry;
            this.hoveredCountry = new HashMap();
            this.expanded = false;
         }

         if(mouseX >= this.guiLeft + 186 && mouseX <= this.guiLeft + 186 + 10 && mouseY >= this.guiTop + 155 && mouseY <= this.guiTop + 155 + 10) {
            this.announce = !this.announce;
         }

         if(countries != null && countries.size() == 0 && mouseX >= this.guiLeft + 97 && mouseX <= this.guiLeft + 97 + 160 && mouseY >= this.guiTop + 130 && mouseY <= this.guiTop + 130 + 20) {
            this.field_73882_e.func_71373_a(new SearchGui());
         }

         if((NationsGUIClientHooks.whitelistedStaff != null && NationsGUIClientHooks.whitelistedStaff.contains(Minecraft.func_71410_x().field_71439_g.getDisplayName()) || System.currentTimeMillis() - ClientProxy.clientConfig.currentServerTime.longValue() > 10800000L) && this.createButton.field_73742_g && selectedCountry != null && !selectedCountry.isEmpty() && mouseX >= this.guiLeft + 186 && mouseX <= this.guiLeft + 186 + 122 && mouseY >= this.guiTop + 201 && mouseY <= this.guiTop + 201 + 20) {
            if(System.currentTimeMillis() < 1648382400000L) {
               List var9 = Arrays.asList(new String[]{"ArchipelCrozet", "TerreSiple", "TerreSpaatz", "TerreMill", "TerreGrant", "TerreVega", "TerreThor", "TerreLow", "TerrePowell", "TerreAdelie", "TerreBurke", "TerreSnow", "TerreBooth", "TerreSmith", "IleBouvet", "TerreRoss", "TerreSigny", "TerreLiard", "TerreMasson"});
               if(var9.contains(selectedCountry.get("name"))) {
                  Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
                  return;
               }
            }

            ClientProxy.clientConfig.currentServerTime = Long.valueOf(System.currentTimeMillis());
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionCreatePacket((String)selectedCountry.get("name"), this.announce, this.regenZone)));
            Minecraft.func_71410_x().func_71373_a((GuiScreen)null);

            try {
               ClientProxy.saveConfig();
            } catch (IOException var7) {
               var7.printStackTrace();
            }
         }

         if(playerHasCountry && mouseX >= this.guiLeft + 97 && mouseX <= this.guiLeft + 97 + 160 && mouseY >= this.guiTop + 140 && mouseY <= this.guiTop + 140 + 20) {
            Minecraft.func_71410_x().func_71373_a(new LeaveConfirmGui(this));
            playerHasCountry = false;
         }
      }

      super.func_73864_a(mouseX, mouseY, mouseButton);
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
      TABS.add(new CreateGui$1());
      TABS.add(new CreateGui$2());
      TABS.add(new CreateGui$3());
      TABS.add(new CreateGui$4());
      TABS.add(new CreateGui$5());
   }
}
