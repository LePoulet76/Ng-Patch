package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.SkillsGui;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseLeaderConfirmGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGui_OLD;
import net.ilexiconn.nationsgui.forge.client.gui.faction.LeaderConfirmGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.StatsGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionProfilActionPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionProfilDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

@SideOnly(Side.CLIENT)
public class ProfilGui extends GuiScreen {

   public static final List<GuiScreenTab> TABS = new ArrayList();
   protected int xSize = 319;
   protected int ySize = 249;
   private int guiLeft;
   private int guiTop;
   private RenderItem itemRenderer = new RenderItem();
   public static boolean loaded = false;
   private String targetName;
   private String enterpriseName;
   private EntityOtherPlayerMP playerEntity = null;
   private GuiScrollBarFaction scrollBar;
   public static HashMap<String, Object> playerInfos;
   public String hoveredAction = "";
   private DynamicTexture flagTexture;
   public static Long lastPromotePlayer = Long.valueOf(0L);


   public ProfilGui(String targetName, String enterpriseName) {
      this.targetName = targetName;
      this.enterpriseName = enterpriseName;
      loaded = false;
      playerInfos = null;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionProfilDataPacket(this.targetName, this.enterpriseName)));
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - this.ySize) / 2;
      this.scrollBar = new GuiScrollBarFaction((float)(this.guiLeft + 168), (float)(this.guiTop + 143), 80);
      this.playerEntity = null;
   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      if(this.playerEntity == null && loaded && !this.targetName.isEmpty() || loaded && !this.targetName.isEmpty() && this.playerEntity != null && !this.playerEntity.getDisplayName().equals(this.targetName)) {
         this.playerEntity = new EntityOtherPlayerMP(this.field_73882_e.field_71441_e, this.targetName);
      }

      if(loaded && this.flagTexture == null && playerInfos.get("factionFlag") != null && !((String)playerInfos.get("factionFlag")).isEmpty()) {
         BufferedImage date = decodeToImage((String)playerInfos.get("factionFlag"));
         this.flagTexture = new DynamicTexture(date);
      }

      this.func_73873_v_();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ClientEventHandler.STYLE.bindTexture("faction_profil");
      ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
      super.func_73863_a(mouseX, mouseY, par3);
      if(mouseX >= this.guiLeft + 304 && mouseX <= this.guiLeft + 304 + 9 && mouseY >= this.guiTop - 6 && mouseY <= this.guiTop - 6 + 10) {
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 304), (float)(this.guiTop - 6), 138, 259, 9, 10, 512.0F, 512.0F, false);
      } else {
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 304), (float)(this.guiTop - 6), 138, 249, 9, 10, 512.0F, 512.0F, false);
      }

      if(loaded) {
         this.drawScaledString(I18n.func_135053_a("faction.profil.title") + " " + this.targetName, this.guiLeft + 51, this.guiTop + 17, 1644825, 1.4F, false, false);
         ClientEventHandler.STYLE.bindTexture("faction_profil");
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 51), (float)(this.guiTop + 30), 0, 322, 251, 68, 512.0F, 512.0F, false);
         GL11.glEnable(3042);
         GL11.glDisable(2929);
         GL11.glDepthMask(false);
         GL11.glBlendFunc(770, 771);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glDisable(3008);
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 51), (float)(this.guiTop + 30), 0, 391, 251, 68, 512.0F, 512.0F, false);
         GL11.glDisable(3042);
         GL11.glEnable(2929);
         GL11.glDepthMask(true);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glEnable(3008);
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 145), (float)(this.guiTop + 40), 323, 5, 150, 50, 512.0F, 512.0F, false);
         if(this.playerEntity != null) {
            GUIUtils.startGLScissor(this.guiLeft + 51, this.guiTop + 31, 93, 68);
            GuiInventory.func_110423_a(this.guiLeft + 97, this.guiTop + 190, 80, 0.0F, 0.0F, this.playerEntity);
            GUIUtils.endGLScissor();
         }

         if(this.flagTexture != null) {
            GL11.glBindTexture(3553, this.flagTexture.func_110552_b());
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 267), (float)(this.guiTop + 35), 0.0F, 0.0F, 156, 78, 30, 16, 156.0F, 78.0F, false);
            ClientEventHandler.STYLE.bindTexture("faction_profil");
            GL11.glEnable(3042);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(3008);
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 267), (float)(this.guiTop + 35), 197, 249, 30, 16, 512.0F, 512.0F, false);
            GL11.glDisable(3042);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(3008);
         }

         if(NationsGUI.BADGES_RESOURCES.containsKey("badges_" + ((String)playerInfos.get("group")).toLowerCase())) {
            Minecraft.func_71410_x().func_110434_K().func_110577_a((ResourceLocation)NationsGUI.BADGES_RESOURCES.get("badges_" + ((String)playerInfos.get("group")).toLowerCase()));
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 152), (float)(this.guiTop + 45), 0.0F, 0.0F, 18, 18, 10, 10, 18.0F, 18.0F, false);
         }

         this.drawScaledString(this.getRankColor((String)playerInfos.get("group")) + playerInfos.get("group") + "", this.guiLeft + 166, this.guiTop + 46, 16777215, 1.1F, false, false);
         String var15 = "";
         if(!((String)playerInfos.get("login")).isEmpty()) {
            long powerString = System.currentTimeMillis() - Long.parseLong(playerInfos.get("login") + "");
            long actions = powerString / 86400000L;
            long offsetX = 0L;
            long actionHumanName = 0L;
            long seconds = 0L;
            if(actions == 0L) {
               offsetX = powerString / 3600000L;
               if(offsetX == 0L) {
                  actionHumanName = powerString / 60000L;
                  if(actionHumanName == 0L) {
                     seconds = powerString / 1000L;
                     var15 = var15 + " " + seconds + " " + I18n.func_135053_a("faction.common.seconds");
                  } else {
                     var15 = var15 + " " + actionHumanName + " " + I18n.func_135053_a("faction.common.minutes");
                  }
               } else {
                  var15 = var15 + " " + offsetX + " " + I18n.func_135053_a("faction.common.hours");
               }
            } else {
               var15 = var15 + " " + actions + " " + I18n.func_135053_a("faction.common.days");
            }
         } else {
            var15 = "-";
         }

         ClientEventHandler.STYLE.bindTexture("faction_profil");
         if(((String)playerInfos.get("is_unique_account")).equals("true")) {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 285), (float)(this.guiTop + 82), 228, 249, 14, 14, 512.0F, 512.0F, false);
            if(mouseX >= this.guiLeft + 285 && mouseX <= this.guiLeft + 285 + 14 && mouseY >= this.guiTop + 82 && mouseY <= this.guiTop + 82 + 14) {
               ArrayList var16 = new ArrayList();
               var16.add(I18n.func_135053_a("faction.profil.unique.account_1"));
               var16.add(I18n.func_135053_a("faction.profil.unique.account_2"));
               if(((String)playerInfos.get("bourse_already_given")).equals("false")) {
                  var16.add(I18n.func_135053_a("faction.profil.unique.account_3"));
                  var16.add(I18n.func_135053_a("faction.profil.unique.account_4"));
                  var16.add(I18n.func_135053_a("faction.profil.unique.account_5"));
               }

               this.drawHoveringText(var16, mouseX, mouseY, this.field_73886_k);
            }
         }

         this.drawScaledString(I18n.func_135053_a("faction.profil.date_1"), this.guiLeft + 150, this.guiTop + 67, 16777215, 1.0F, false, false);
         this.drawScaledString(I18n.func_135053_a("faction.profil.date_2") + var15 + " " + I18n.func_135053_a("faction.profil.date_3"), this.guiLeft + 150, this.guiTop + 77, 16777215, 1.0F, false, false);
         String var17 = "Power : " + playerInfos.get("power") + "/" + playerInfos.get("power_max");
         Float powerStringLength = Float.valueOf((float)this.field_73886_k.func_78256_a(var17) * 0.8F);
         this.drawScaledString(var17, this.guiLeft + 91, this.guiTop + 111, 11842740, 0.8F, true, false);
         this.drawScaledString(I18n.func_135053_a("faction.profil.salary") + " : " + playerInfos.get("salary") + "$", this.guiLeft + 174, this.guiTop + 111, 11842740, 0.8F, true, false);
         this.drawScaledString(I18n.func_135053_a("faction.profil.tax") + " : " + playerInfos.get("tax") + "$", this.guiLeft + 267, this.guiTop + 111, 11842740, 0.8F, true, false);
         if((float)mouseX >= (float)(this.guiLeft + 91) - powerStringLength.floatValue() / 2.0F && (float)mouseX <= (float)(this.guiLeft + 91) - powerStringLength.floatValue() / 2.0F + powerStringLength.floatValue() && mouseY >= this.guiTop + 109 && mouseY <= this.guiTop + 109 + 8) {
            this.drawHoveringText(Arrays.asList(new String[]{"\u00a7a+" + playerInfos.get("power_bonus") + " powerboost", "\u00a76+" + playerInfos.get("power_hour") + "/" + I18n.func_135053_a("faction.common.hour") + ", " + playerInfos.get("power_death") + "/" + I18n.func_135053_a("faction.common.death")}), mouseX, mouseY, this.field_73886_k);
         }

         ArrayList var18 = (ArrayList)playerInfos.get("actions");
         this.hoveredAction = "";
         if(var18.size() > 0) {
            GUIUtils.startGLScissor(this.guiLeft + 51, this.guiTop + 139, 117, 88);

            for(int j = 0; j < var18.size(); ++j) {
               int var19 = this.guiLeft + 51;
               Float offsetY = Float.valueOf((float)(this.guiTop + 139 + j * 20) + this.getSlide());
               ClientEventHandler.STYLE.bindTexture("faction_profil");
               if(mouseX >= var19 && mouseX <= var19 + 117 && (float)mouseY >= offsetY.floatValue() && (float)mouseY <= offsetY.floatValue() + 20.0F) {
                  this.hoveredAction = (String)var18.get(j);
                  ModernGui.drawModalRectWithCustomSizedTexture((float)var19, (float)offsetY.intValue(), 323, 56, 117, 20, 512.0F, 512.0F, false);
               } else {
                  ModernGui.drawModalRectWithCustomSizedTexture((float)var19, (float)offsetY.intValue(), 51, 139, 117, 20, 512.0F, 512.0F, false);
               }

               String var20 = "";
               if(((String)var18.get(j)).contains("promote_")) {
                  var20 = I18n.func_135053_a("faction.profil.actions.promote") + " " + I18n.func_135053_a("faction.profil.actions.rank." + ((String)var18.get(j)).split("_")[1]);
               } else if(((String)var18.get(j)).contains("demote_")) {
                  var20 = I18n.func_135053_a("faction.profil.actions.demote") + " " + I18n.func_135053_a("faction.profil.actions.rank." + ((String)var18.get(j)).split("_")[1]);
               } else {
                  var20 = I18n.func_135053_a("faction.profil.actions." + (String)var18.get(j));
               }

               this.drawScaledString(var20, var19 + 6, offsetY.intValue() + 6, 11842740, 0.85F, false, false);
            }

            GUIUtils.endGLScissor();
         }

         this.drawScaledString(I18n.func_135053_a("faction.profil.boutons.skills"), this.guiLeft + 184, this.guiTop + 145, 11842740, 0.85F, false, false);
         this.drawScaledString(I18n.func_135053_a("faction.profil.boutons.stats"), this.guiLeft + 184, this.guiTop + 168, 11842740, 0.85F, false, false);
         if(mouseX > this.guiLeft + 50 && mouseX < this.guiLeft + 50 + 125 && mouseY > this.guiTop + 138 && mouseY < this.guiTop + 138 + 90) {
            this.scrollBar.draw(mouseX, mouseY);
         }
      }

      GL11.glEnable(2896);
      RenderHelper.func_74519_b();
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

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0) {
         if(mouseX > this.guiLeft + 304 && mouseX < this.guiLeft + 304 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            if(this.enterpriseName != null && !this.enterpriseName.isEmpty()) {
               Minecraft.func_71410_x().func_71373_a(new EnterpriseGui(this.enterpriseName));
            } else if(FactionGui_OLD.factionInfos != null && FactionGui_OLD.factionInfos.get("name") != null) {
               Minecraft.func_71410_x().func_71373_a(new FactionGui_OLD((String)FactionGui_OLD.factionInfos.get("name")));
            } else {
               Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
            }
         } else if(mouseX > this.guiLeft + 51 && mouseX < this.guiLeft + 51 + 117 && mouseY > this.guiTop + 139 && mouseY < this.guiTop + 139 + 88) {
            if(!this.hoveredAction.isEmpty()) {
               if(this.hoveredAction.contains("promote")) {
                  if(System.currentTimeMillis() - lastPromotePlayer.longValue() < 2000L) {
                     return;
                  }

                  lastPromotePlayer = Long.valueOf(System.currentTimeMillis());
               }

               if(this.hoveredAction.equals("promote_leader")) {
                  Minecraft.func_71410_x().func_71373_a(new LeaderConfirmGui(this, this.targetName));
               } else if(this.hoveredAction.equals("enterprise_leader")) {
                  Minecraft.func_71410_x().func_71373_a(new EnterpriseLeaderConfirmGui(this, this.targetName, this.enterpriseName));
               } else {
                  PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionProfilActionPacket(this.targetName, this.enterpriseName, this.hoveredAction)));
               }

               this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
               this.hoveredAction = "";
            }
         } else if(mouseX > this.guiLeft + 179 && mouseX < this.guiLeft + 179 + 124 && mouseY > this.guiTop + 138 && mouseY < this.guiTop + 138 + 20) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            Minecraft.func_71410_x().func_71373_a(new SkillsGui(this.targetName));
         } else if(mouseX > this.guiLeft + 179 && mouseX < this.guiLeft + 179 + 124 && mouseY > this.guiTop + 161 && mouseY < this.guiTop + 161 + 20) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            Minecraft.func_71410_x().func_71373_a(new StatsGUI(this.targetName));
         }
      }

      super.func_73864_a(mouseX, mouseY, mouseButton);
   }

   private float getSlide() {
      return ((ArrayList)playerInfos.get("actions")).size() > 4?(float)(-(((ArrayList)playerInfos.get("actions")).size() - 4) * 20) * this.scrollBar.getSliderValue():0.0F;
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

   public static BufferedImage decodeToImage(String imageString) {
      BufferedImage image = null;

      try {
         BASE64Decoder e = new BASE64Decoder();
         byte[] imageByte = e.decodeBuffer(imageString);
         ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
         image = ImageIO.read(bis);
         bis.close();
      } catch (Exception var5) {
         var5.printStackTrace();
      }

      return image;
   }

   public String getRankColor(String rank) {
      String res = "\u00a7f";
      String var3 = rank.toLowerCase();
      byte var4 = -1;
      switch(var3.hashCode()) {
      case -2016291104:
         if(var3.equals("moderateur")) {
            var4 = 3;
         }
         break;
      case -1996099632:
         if(var3.equals("fondateur")) {
            var4 = 9;
         }
         break;
      case -1973319297:
         if(var3.equals("respadmin")) {
            var4 = 8;
         }
         break;
      case -332106840:
         if(var3.equals("supermodo")) {
            var4 = 6;
         }
         break;
      case -318452137:
         if(var3.equals("premium")) {
            var4 = 2;
         }
         break;
      case 55934456:
         if(var3.equals("legende")) {
            var4 = 1;
         }
         break;
      case 92668751:
         if(var3.equals("admin")) {
            var4 = 7;
         }
         break;
      case 99168185:
         if(var3.equals("heros")) {
            var4 = 0;
         }
         break;
      case 1670429593:
         if(var3.equals("moderateur_plus")) {
            var4 = 4;
         }
         break;
      case 1670541969:
         if(var3.equals("moderateur_test")) {
            var4 = 5;
         }
         break;
      case 1854118753:
         if(var3.equals("co-fonda")) {
            var4 = 10;
         }
      }

      switch(var4) {
      case 0:
         res = "\u00a77";
         break;
      case 1:
         res = "\u00a73";
         break;
      case 2:
         res = "\u00a76";
         break;
      case 3:
      case 4:
      case 5:
         res = "\u00a7a";
         break;
      case 6:
         res = "\u00a79";
         break;
      case 7:
         res = "\u00a7c";
         break;
      case 8:
         res = "\u00a74";
         break;
      case 9:
      case 10:
         res = "\u00a7b";
      }

      return res;
   }

}
