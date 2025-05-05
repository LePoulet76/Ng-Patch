package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.faction.WarRequestGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionEnemyCreatePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionGetImagePacket;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class EnemyGui extends GuiScreen {

   private String attFaction;
   private String defFaction;
   protected int xSize = 217;
   protected int ySize = 210;
   private int guiLeft;
   private int guiTop;
   private RenderItem itemRenderer = new RenderItem();
   public static boolean loaded = false;
   private GuiScrollBarFaction scrollBar;
   boolean reasonExpanded = false;
   private String selectedReason = "";
   private String hoveredReason = "";
   private List<String> availableReasons = Arrays.asList(new String[]{"kill_wilderness", "provocation", "territorial_expansion", "treason", "intrusion", "scam", "tpkill", "independence", "colony_refusal", "colony_protection", "buffer_country", "under_power", "reprisal", "500mmr", "follow_war", "empire", "war_revenge", "bombing_asssistance", "treaty", "colony_steal"});
   private List<String> reasonsAuto = Arrays.asList(new String[]{"kill_wilderness", "tpkill", "independence", "colony_refusal", "colony_protection", "under_power", "reprisal", "500mmr", "empire", "war_revenge", "colony_steal"});


   public EnemyGui(String attFaction, String defFaction) {
      this.attFaction = attFaction;
      this.defFaction = defFaction;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - this.ySize) / 2;
      this.scrollBar = new GuiScrollBarFaction((float)(this.guiLeft + 202), (float)(this.guiTop + 117), 71);
      this.selectedReason = "kill_wilderness";
   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      this.hoveredReason = "";
      this.func_73873_v_();
      Object tooltipToDraw = null;
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ClientEventHandler.STYLE.bindTexture("faction_enemy");
      ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
      GL11.glPushMatrix();
      GL11.glTranslatef((float)(this.guiLeft + 12), (float)(this.guiTop + 190), 0.0F);
      GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
      GL11.glTranslatef((float)(-(this.guiLeft + 14)), (float)(-(this.guiTop + 190)), 0.0F);
      this.drawScaledString(I18n.func_135053_a("faction.enemy.title"), this.guiLeft + 12, this.guiTop + 190, 16777215, 1.5F, false, false);
      GL11.glPopMatrix();
      ClientEventHandler.STYLE.bindTexture("faction_enemy");
      if(mouseX >= this.guiLeft + 206 && mouseX <= this.guiLeft + 206 + 9 && mouseY >= this.guiTop - 6 && mouseY <= this.guiTop - 6 + 10) {
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 206), (float)(this.guiTop - 6), 63, 223, 9, 10, 512.0F, 512.0F, false);
      } else {
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 206), (float)(this.guiTop - 6), 63, 213, 9, 10, 512.0F, 512.0F, false);
      }

      BufferedImage i;
      if(!ClientProxy.flagsTexture.containsKey(this.attFaction)) {
         if(!ClientProxy.base64FlagsByFactionName.containsKey(this.attFaction)) {
            ClientProxy.base64FlagsByFactionName.put(this.attFaction, "");
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionGetImagePacket(this.attFaction)));
         } else if(ClientProxy.base64FlagsByFactionName.containsKey(this.attFaction) && !((String)ClientProxy.base64FlagsByFactionName.get(this.attFaction)).isEmpty()) {
            i = ClientProxy.decodeToImage((String)ClientProxy.base64FlagsByFactionName.get(this.attFaction));
            ClientProxy.flagsTexture.put(this.attFaction, new DynamicTexture(i));
         }
      }

      if(!ClientProxy.flagsTexture.containsKey(this.defFaction)) {
         if(!ClientProxy.base64FlagsByFactionName.containsKey(this.defFaction)) {
            ClientProxy.base64FlagsByFactionName.put(this.defFaction, "");
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionGetImagePacket(this.defFaction)));
         } else if(ClientProxy.base64FlagsByFactionName.containsKey(this.defFaction) && !((String)ClientProxy.base64FlagsByFactionName.get(this.defFaction)).isEmpty()) {
            i = ClientProxy.decodeToImage((String)ClientProxy.base64FlagsByFactionName.get(this.defFaction));
            ClientProxy.flagsTexture.put(this.defFaction, new DynamicTexture(i));
         }
      }

      if(ClientProxy.flagsTexture.containsKey(this.attFaction)) {
         GL11.glBindTexture(3553, ((DynamicTexture)ClientProxy.flagsTexture.get(this.attFaction)).func_110552_b());
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 63), (float)(this.guiTop + 34), 0.0F, 0.0F, 156, 78, 27, 15, 156.0F, 78.0F, false);
      }

      if(ClientProxy.flagsTexture.containsKey(this.defFaction)) {
         GL11.glBindTexture(3553, ((DynamicTexture)ClientProxy.flagsTexture.get(this.defFaction)).func_110552_b());
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 162), (float)(this.guiTop + 34), 0.0F, 0.0F, 156, 78, 27, 15, 156.0F, 78.0F, false);
      }

      if(this.attFaction.contains("Empire")) {
         this.drawScaledString("Empire", this.guiLeft + 76, this.guiTop + 53, 16777215, 1.0F, true, false);
         this.drawScaledString(this.attFaction.replace("Empire", ""), this.guiLeft + 76, this.guiTop + 63, 16777215, 1.0F, true, false);
      } else {
         this.drawScaledString(this.attFaction, this.guiLeft + 76, this.guiTop + 53, 16777215, 1.0F, true, false);
      }

      if(this.defFaction.contains("Empire")) {
         this.drawScaledString("\u00a7cEmpire", this.guiLeft + 175, this.guiTop + 53, 16777215, 1.0F, true, false);
         this.drawScaledString("\u00a7c" + this.defFaction.replace("Empire", ""), this.guiLeft + 175, this.guiTop + 63, 16777215, 1.0F, true, false);
      } else {
         this.drawScaledString("\u00a7c" + this.defFaction, this.guiLeft + 175, this.guiTop + 53, 16777215, 1.0F, true, false);
      }

      this.drawScaledString(I18n.func_135053_a("faction.enemy.reason"), this.guiLeft + 45, this.guiTop + 86, 0, 1.0F, false, false);
      if(!this.selectedReason.isEmpty()) {
         String var12 = I18n.func_135053_a("faction.enemy.reason." + this.selectedReason);
         if(var12.length() > 25) {
            var12 = var12.substring(0, 25) + "..";
         }

         this.drawScaledString(var12, this.guiLeft + 48, this.guiTop + 102, 16777215, 1.0F, false, false);
      }

      if(!this.selectedReason.isEmpty()) {
         String[] var13 = I18n.func_135053_a("faction.enemy.reason.desc." + this.selectedReason).split(" ");
         String offsetX = "";
         int offsetY = 0;
         String[] var8 = var13;
         int var9 = var13.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            String descWord = var8[var10];
            if((double)this.field_73886_k.func_78256_a(offsetX + descWord) * 0.9D <= 152.0D) {
               if(!offsetX.equals("")) {
                  offsetX = offsetX + " ";
               }

               offsetX = offsetX + descWord;
            } else {
               this.drawScaledString(offsetX, this.guiLeft + 49, this.guiTop + 120 + offsetY * 10, 16777215, 0.9F, false, false);
               ++offsetY;
               offsetX = descWord;
            }
         }

         this.drawScaledString(offsetX, this.guiLeft + 49, this.guiTop + 120 + offsetY * 10, 16777215, 0.9F, false, false);
         if(this.reasonsAuto.contains(this.selectedReason)) {
            this.drawScaledString(I18n.func_135053_a("faction.enemy.reason.auto"), this.guiLeft + 49, this.guiTop + 170, 16777215, 0.9F, false, false);
         } else {
            this.drawScaledString(I18n.func_135053_a("faction.enemy.reason.forum"), this.guiLeft + 49, this.guiTop + 170, 16777215, 0.9F, false, false);
         }
      }

      if(!this.reasonExpanded && mouseX >= this.guiLeft + 45 && mouseX <= this.guiLeft + 45 + 162 && mouseY >= this.guiTop + 182 && mouseY <= this.guiTop + 182 + 15) {
         ClientEventHandler.STYLE.bindTexture("faction_enemy");
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 45), (float)(this.guiTop + 182), 0, 249, 162, 15, 512.0F, 512.0F, false);
      }

      this.drawScaledString(I18n.func_135053_a("faction.enemy.send_request"), this.guiLeft + 126, this.guiTop + 185, 16777215, 1.0F, true, false);
      if(this.reasonExpanded) {
         ClientEventHandler.STYLE.bindTexture("faction_enemy");
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 45), (float)(this.guiTop + 114), 0, 285, 162, 77, 512.0F, 512.0F, false);
         GUIUtils.startGLScissor(this.guiLeft + 46, this.guiTop + 115, 156, 75);

         for(int var14 = 0; var14 < this.availableReasons.size(); ++var14) {
            int var15 = this.guiLeft + 46;
            Float var16 = Float.valueOf((float)(this.guiTop + 115 + var14 * 19) + this.getSlideReasons());
            ClientEventHandler.STYLE.bindTexture("faction_enemy");
            ModernGui.drawModalRectWithCustomSizedTexture((float)var15, (float)var16.intValue(), 1, 286, 156, 19, 512.0F, 512.0F, false);
            this.drawScaledString(I18n.func_135053_a("faction.enemy.reason." + (String)this.availableReasons.get(var14)), this.guiLeft + 48, var16.intValue() + 5, 16777215, 1.0F, false, false);
            if(mouseX >= var15 && mouseX <= var15 + 156 && (float)mouseY >= var16.floatValue() && (float)mouseY <= var16.floatValue() + 19.0F) {
               this.hoveredReason = (String)this.availableReasons.get(var14);
            }
         }

         GUIUtils.endGLScissor();
         this.scrollBar.draw(mouseX, mouseY);
      }

      GL11.glEnable(2896);
      RenderHelper.func_74519_b();
   }

   private float getSlideReasons() {
      return this.availableReasons.size() > 4?(float)(-(this.availableReasons.size() - 4) * 19) * this.scrollBar.getSliderValue():0.0F;
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0) {
         if(mouseX >= this.guiLeft + 45 && mouseX <= this.guiLeft + 45 + 162 && mouseY >= this.guiTop + 95 && mouseY <= this.guiTop + 95 + 20) {
            this.reasonExpanded = !this.reasonExpanded;
         }

         if(mouseX > this.guiLeft + 206 && mouseX < this.guiLeft + 206 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            this.field_73882_e.func_71373_a((GuiScreen)null);
         }

         if(this.hoveredReason != null && !this.hoveredReason.isEmpty()) {
            this.selectedReason = this.hoveredReason;
            this.hoveredReason = "";
            this.reasonExpanded = false;
         }

         if(!this.reasonExpanded && !this.selectedReason.isEmpty() && mouseX > this.guiLeft + 45 && mouseX < this.guiLeft + 45 + 162 && mouseY > this.guiTop + 182 && mouseY < this.guiTop + 182 + 15) {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionEnemyCreatePacket(this.attFaction, this.defFaction, this.selectedReason)));
            this.field_73882_e.func_71373_a(new WarRequestGUI(-1, (GuiScreen)null));
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

}
