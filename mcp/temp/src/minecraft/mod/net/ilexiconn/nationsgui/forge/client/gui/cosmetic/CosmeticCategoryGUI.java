package net.ilexiconn.nationsgui.forge.client.gui.cosmetic;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.emotes.ClientEmotesHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.CommonCosmeticGUI;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.CosmeticCategoryGUI$1;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.CosmeticCategoryGUI$2;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.CosmeticGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.CosmeticCategoryDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.CosmeticResetGroupPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.CosmeticSetActivePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.HatExportPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class CosmeticCategoryGUI extends CommonCosmeticGUI {

   public static String selectedSkinId;
   public int entityMoovingCallCount = 0;
   public static HashMap<String, LinkedHashMap<String, ArrayList<HashMap<String, String>>>> categoriesData = new HashMap();
   public static boolean loaded = false;
   public static LinkedHashMap<String, Integer> groupIDSlotByRarityX = new CosmeticCategoryGUI$1();
   public static LinkedHashMap<String, Integer> skinIDSlotByRarityX = new CosmeticCategoryGUI$2();
   private String selectedGroupId;
   public long ticks = 0L;
   public static HashMap<String, String> cachedSelectedSkin;
   private EntityOtherPlayerMP playerEntity = null;
   private GuiScrollBarGeneric scrollBarLeftNavCategories;
   private GuiScrollBarGeneric scrollBarGroupIds;
   public static List<String> activeEmotes = new ArrayList();
   public static List<String> activeBadges = new ArrayList();
   public String overridePreviewSkin;
   public static float targetPlayerRotation = -1.0F;
   public static float manualOffsetPlayerRotation = 0.0F;
   public static float lastPlayerRotation = -1.0F;
   public static long lastPlayerAnimation = -1L;
   public static long nextRandomAnimationTime = -1L;
   public static String nextRandomAnimation = null;
   public static List<String> randomAnimations = Arrays.asList(new String[]{"cheer", "clap", "facepalm", "stand", "wave"});


   public CosmeticCategoryGUI(String categoryTarget, String playerTarget) {
      this.categoryTarget = categoryTarget;
      this.playerTarget = playerTarget;
      categoriesData.clear();
      selectedSkinId = null;
      loaded = false;
      manualOffsetPlayerRotation = 0.0F;
      nextRandomAnimationTime = System.currentTimeMillis() + (long)(new Random()).nextInt(20000) + 10000L;
      nextRandomAnimation = (String)randomAnimations.get((new Random()).nextInt(randomAnimations.size()));
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new CosmeticCategoryDataPacket(this.categoryTarget, this.playerTarget)));
      this.playerEntity = null;
      this.scrollBarLeftNavCategories = new GuiScrollBarGeneric(0.0F, 0.0F, 0, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 0, 0);
      this.scrollBarGroupIds = new GuiScrollBarGeneric((float)(this.guiLeft + 448), (float)(this.guiTop + 158), 57, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
   }

   private float getSlideCategories() {
      return CosmeticGUI.CATEGORIES_ORDER.size() > 4?(float)(-(CosmeticGUI.CATEGORIES_ORDER.size() - 4) * 39) * this.scrollBarLeftNavCategories.getSliderValue():0.0F;
   }

   private float getSlideGroupIds() {
      return ((LinkedHashMap)categoriesData.get(this.categoryTarget)).size() > 16?-((float)Math.ceil((double)((float)(((LinkedHashMap)categoriesData.get(this.categoryTarget)).size() - 16) / 8.0F))) * 32.0F * this.scrollBarGroupIds.getSliderValue():0.0F;
   }

   public void renderPlayer(int par0, int par1, int par2, float mouseX, float mouseY, EntityLivingBase par5EntityLivingBase) {
      GUIUtils.startGLScissor(this.guiLeft + 45, this.guiTop + 10, 160, 210);
      String rotation = this.categoryTarget;
      byte f2 = -1;
      switch(rotation.hashCode()) {
      case -1299337733:
         if(rotation.equals("emotes")) {
            f2 = 3;
         }
         break;
      case -1191220642:
         if(rotation.equals("chestplates")) {
            f2 = 2;
         }
         break;
      case 3195192:
         if(rotation.equals("hats")) {
            f2 = 0;
         }
         break;
      case 94429184:
         if(rotation.equals("capes")) {
            f2 = 4;
         }
         break;
      case 226093540:
         if(rotation.equals("buddies")) {
            f2 = 1;
         }
      }

      switch(f2) {
      case 0:
      case 1:
         targetPlayerRotation = -20.0F;
         break;
      case 2:
      case 3:
         targetPlayerRotation = 20.0F;
         break;
      case 4:
         targetPlayerRotation = 135.0F;
         break;
      default:
         targetPlayerRotation = -20.0F;
      }

      targetPlayerRotation += manualOffsetPlayerRotation;
      float rotation1 = lastPlayerRotation != -1.0F?lastPlayerRotation:targetPlayerRotation;
      if(rotation1 != targetPlayerRotation) {
         if(rotation1 > targetPlayerRotation) {
            rotation1 = Math.max(targetPlayerRotation, rotation1 - (float)(System.currentTimeMillis() - lastPlayerAnimation) * 0.3F);
         } else {
            rotation1 = Math.min(targetPlayerRotation, rotation1 + (float)(System.currentTimeMillis() - lastPlayerAnimation) * 0.3F);
         }
      }

      lastPlayerAnimation = System.currentTimeMillis();
      lastPlayerRotation = rotation1;
      GL11.glEnable(2903);
      GL11.glPushMatrix();
      GL11.glTranslatef((float)par0, (float)par1, 50.0F);
      GL11.glScalef((float)(-par2), (float)par2, (float)par2);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      float f21 = par5EntityLivingBase.field_70761_aq;
      float f3 = par5EntityLivingBase.field_70177_z;
      float f4 = par5EntityLivingBase.field_70125_A;
      float f5 = par5EntityLivingBase.field_70758_at;
      float f6 = par5EntityLivingBase.field_70759_as;
      GL11.glRotatef(rotation1, 0.0F, 1.0F, 0.0F);
      RenderHelper.func_74519_b();
      GL11.glRotatef(-rotation1, 0.0F, 1.0F, 0.0F);
      par5EntityLivingBase.field_70761_aq = rotation1;
      par5EntityLivingBase.field_70177_z = rotation1;
      par5EntityLivingBase.field_70125_A = 0.0F;
      par5EntityLivingBase.field_70759_as = par5EntityLivingBase.field_70177_z;
      par5EntityLivingBase.field_70758_at = par5EntityLivingBase.field_70177_z;
      GL11.glTranslatef(0.0F, par5EntityLivingBase.field_70129_M, 0.0F);
      RenderManager.field_78727_a.field_78735_i = 180.0F;
      RenderManager.field_78727_a.func_78719_a(par5EntityLivingBase, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
      par5EntityLivingBase.field_70761_aq = f21;
      par5EntityLivingBase.field_70177_z = f3;
      par5EntityLivingBase.field_70125_A = f4;
      par5EntityLivingBase.field_70758_at = f5;
      par5EntityLivingBase.field_70759_as = f6;
      GL11.glPopMatrix();
      RenderHelper.func_74518_a();
      GL11.glDisable('\u803a');
      OpenGlHelper.func_77473_a(OpenGlHelper.field_77476_b);
      GL11.glDisable(3553);
      OpenGlHelper.func_77473_a(OpenGlHelper.field_77478_a);
      if(nextRandomAnimationTime < System.currentTimeMillis()) {
         nextRandomAnimationTime = System.currentTimeMillis() + (long)(new Random()).nextInt(20000) + 10000L;
         if(!this.categoryTarget.equals("capes")) {
            ClientEmotesHandler.playEmote(nextRandomAnimation, true);
         }

         nextRandomAnimation = (String)randomAnimations.get((new Random()).nextInt(randomAnimations.size()));
      }

      GUIUtils.endGLScissor();
      ClientEventHandler.STYLE.bindTexture("cosmetic");
      boolean isHovered = mouseX >= (float)(this.guiLeft + 65) && mouseX <= (float)(this.guiLeft + 65 + 32) && mouseY >= (float)(this.guiTop + 151) && mouseY <= (float)(this.guiTop + 151 + 20);
      ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 65), (float)(this.guiTop + 151), (float)(534 * GUI_SCALE), (float)((isHovered?209:184) * GUI_SCALE), 32 * GUI_SCALE, 23 * GUI_SCALE, 32, 20, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
      if(isHovered) {
         this.hoveredAction = "rotatePlus";
      }

      ClientEventHandler.STYLE.bindTexture("cosmetic");
      isHovered = mouseX >= (float)(this.guiLeft + 151) && mouseX <= (float)(this.guiLeft + 151 + 32) && mouseY >= (float)(this.guiTop + 151) && mouseY <= (float)(this.guiTop + 151 + 20);
      ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 151), (float)(this.guiTop + 151), (float)(534 * GUI_SCALE), (float)((isHovered?159:134) * GUI_SCALE), 32 * GUI_SCALE, 23 * GUI_SCALE, 32, 20, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
      if(isHovered) {
         this.hoveredAction = "rotateMinus";
      }

   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      this.func_73873_v_();
      this.hoveredAction = "";
      this.tooltipToDraw.clear();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ++this.ticks;
      ClientEventHandler.STYLE.bindTexture("cosmetic");
      ModernGui.drawScaledCustomSizeModalRect((float)this.guiLeft, (float)this.guiTop, (float)(0 * GUI_SCALE), (float)(321 * GUI_SCALE), this.xSize * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize, this.ySize, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
      GUIUtils.startGLScissor(this.guiLeft + 12, this.guiTop + 0, 30, 235);
      int index = 0;
      ClientEventHandler.STYLE.bindTexture("cosmetic");

      Iterator skinsOfSelectedGroupID;
      int skinTitle;
      for(skinsOfSelectedGroupID = CosmeticGUI.CATEGORIES_ORDER.iterator(); skinsOfSelectedGroupID.hasNext(); ++index) {
         String skinDataToDisplay = (String)skinsOfSelectedGroupID.next();
         skinTitle = this.guiLeft + 12;
         Float offsetXLabels = Float.valueOf((float)(this.guiTop + 69 + index * 39) + this.getSlideCategories());
         ModernGui.drawScaledCustomSizeModalRect((float)skinTitle, (float)offsetXLabels.intValue(), (float)(991 * GUI_SCALE), (float)((this.categoryTarget.equals(skinDataToDisplay)?7:45) * GUI_SCALE), 30 * GUI_SCALE, 30 * GUI_SCALE, 30, 30, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
         if(mouseX >= skinTitle && mouseX <= skinTitle + 30 && (float)mouseY >= offsetXLabels.floatValue() && (float)mouseY <= offsetXLabels.floatValue() + 30.0F) {
            ModernGui.drawScaledCustomSizeModalRect((float)skinTitle, (float)offsetXLabels.intValue(), (float)(951 * GUI_SCALE), (float)(index * 30 * GUI_SCALE), 30 * GUI_SCALE, 30 * GUI_SCALE, 30, 30, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            this.hoveredAction = "tab#" + skinDataToDisplay;
         } else {
            ModernGui.drawScaledCustomSizeModalRect((float)skinTitle, (float)offsetXLabels.intValue(), (float)((this.categoryTarget.equals(skinDataToDisplay)?951:920) * GUI_SCALE), (float)(index * 30 * GUI_SCALE), 30 * GUI_SCALE, 30 * GUI_SCALE, 30, 30, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
         }
      }

      GUIUtils.endGLScissor();
      ModernGui.drawScaledCustomSizeModalRect((float)this.guiLeft, (float)this.guiTop, (float)(0 * GUI_SCALE), (float)(245 * GUI_SCALE), 55 * GUI_SCALE, 66 * GUI_SCALE, 55, 66, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
      ClientEventHandler.STYLE.bindTexture("cosmetic");
      if(mouseX >= this.guiLeft + 13 && (float)mouseX <= (float)(this.guiLeft + 21) + bold28.getStringWidth(I18n.func_135053_a("cosmetic.label.return")) && mouseY >= this.guiTop + 30 && mouseY <= this.guiTop + 30 + 9) {
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 13), (float)(this.guiTop + 30), (float)(597 * GUI_SCALE), (float)(222 * GUI_SCALE), 6 * GUI_SCALE, 9 * GUI_SCALE, 6, 9, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("cosmetic.label.return"), (float)(this.guiLeft + 21), (float)(this.guiTop + 31), COLOR_LIGHT_GRAY, 0.5F, "left", false, "georamaBold", 28);
         this.hoveredAction = "return";
      } else {
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 13), (float)(this.guiTop + 30), (float)(597 * GUI_SCALE), (float)(211 * GUI_SCALE), 6 * GUI_SCALE, 9 * GUI_SCALE, 6, 9, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("cosmetic.label.return"), (float)(this.guiLeft + 21), (float)(this.guiTop + 31), COLOR_LIGHT_BLUE, 0.5F, "left", false, "georamaBold", 28);
      }

      if(mouseX >= this.guiLeft && mouseX <= this.guiLeft + 55 && mouseY >= this.guiTop && mouseY <= this.guiTop + this.ySize) {
         this.scrollBarLeftNavCategories.draw(mouseX, mouseY);
      }

      ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("cosmetic.category." + this.categoryTarget), (float)(this.guiLeft + 12), (float)(this.guiTop + 11), 12895428, 1.0F, "left", false, "georamaExtraBold", 28);
      if(!this.displayModal) {
         if(!this.categoryTarget.equals("hats") && !this.categoryTarget.equals("hands") && !this.categoryTarget.equals("capes") && !this.categoryTarget.equals("chestplates") && !this.categoryTarget.equals("emotes") && !this.categoryTarget.equals("buddies")) {
            if(this.categoryTarget.equals("vehicles")) {
               if(selectedSkinId != null && ClientProxy.SKIN_MANAGER.getSkinFromID(selectedSkinId) != null) {
                  ClientProxy.SKIN_MANAGER.getSkinFromID(selectedSkinId).renderInGUI(this.guiLeft + 50, this.guiTop + 60, 6.0F, par3);
               }
            } else if(this.categoryTarget.equals("items")) {
               if(selectedSkinId != null && ClientProxy.SKIN_MANAGER.getSkinFromID(selectedSkinId) != null) {
                  ClientProxy.SKIN_MANAGER.getSkinFromID(selectedSkinId).renderInGUI(this.guiLeft + 50, this.guiTop + 60, 6.0F, par3);
               }
            } else if(!this.categoryTarget.equals("armors") && !this.categoryTarget.equals("items")) {
               if(this.categoryTarget.equals("hands")) {
                  if(selectedSkinId != null && ClientProxy.SKIN_MANAGER.getSkinFromID(selectedSkinId) != null) {
                     ClientProxy.SKIN_MANAGER.getSkinFromID(selectedSkinId).renderInGUI(this.guiLeft + 45, this.guiTop + 40, 6.0F, par3);
                  }
               } else if(this.categoryTarget.equals("badges") && selectedSkinId != null && ClientProxy.SKIN_MANAGER.getSkinFromID(selectedSkinId) != null) {
                  ClientProxy.SKIN_MANAGER.getSkinFromID(selectedSkinId).renderInGUI(this.guiLeft + 75, this.guiTop + 75, 4.0F, par3);
               }
            } else if(selectedSkinId != null && ClientProxy.SKIN_MANAGER.getSkinFromID(selectedSkinId) != null) {
               ClientProxy.SKIN_MANAGER.getSkinFromID(selectedSkinId).renderInGUI(this.guiLeft + 35, this.guiTop + 35, 7.0F, par3);
            }
         } else {
            this.renderPlayer(this.guiLeft + 125, this.guiTop + 185, 65, (float)mouseX, (float)mouseY, Minecraft.func_71410_x().field_71439_g);
         }

         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      }

      if(loaded) {
         String var22;
         if(this.selectedGroupId == null && categoriesData.containsKey(this.categoryTarget) && ((LinkedHashMap)categoriesData.get(this.categoryTarget)).keySet().size() > 0) {
            skinsOfSelectedGroupID = ((LinkedHashMap)categoriesData.get(this.categoryTarget)).entrySet().iterator();

            while(skinsOfSelectedGroupID.hasNext()) {
               Entry var20 = (Entry)skinsOfSelectedGroupID.next();
               var22 = (String)var20.getKey();
               Iterator var23 = ((ArrayList)var20.getValue()).iterator();

               while(var23.hasNext()) {
                  HashMap rarityToUse = (HashMap)var23.next();
                  if(((String)rarityToUse.get("active")).equals("1")) {
                     this.selectedGroupId = var22;
                  }
               }
            }

            if(this.selectedGroupId == null) {
               this.selectedGroupId = (String)((LinkedHashMap)categoriesData.get(this.categoryTarget)).keySet().toArray()[0];
            }
         }

         if(categoriesData.containsKey(this.categoryTarget) && ((LinkedHashMap)categoriesData.get(this.categoryTarget)).size() > 0 && this.selectedGroupId != null && ((LinkedHashMap)categoriesData.get(this.categoryTarget)).get(this.selectedGroupId) != null) {
            ArrayList var19 = (ArrayList)((LinkedHashMap)categoriesData.get(this.categoryTarget)).get(this.selectedGroupId);
            HashMap var21 = cachedSelectedSkin != null?cachedSelectedSkin:(HashMap)var19.get(0);
            String var26;
            if(((String)var21.get("skin_name")).equals(selectedSkinId)) {
               boolean var24;
               if(((String)var21.get("owned")).equals("1")) {
                  if(!((String)var21.get("by_default")).equals("1")) {
                     ClientEventHandler.STYLE.bindTexture("cosmetic");
                     skinTitle = this.categoryTarget.equals("hats") && !((String)var21.get("rarity")).equals("limited")?79:94;
                     var24 = mouseX >= this.guiLeft + skinTitle && mouseX <= this.guiLeft + skinTitle + 59 && mouseY >= this.guiTop + 197 && mouseY <= this.guiTop + 197 + 11;
                     ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + skinTitle), (float)(this.guiTop + 197), (float)(283 * GUI_SCALE), (float)((((String)var21.get("active")).equals("1")?(var24?680:695):(var24?680:710)) * GUI_SCALE), 59 * GUI_SCALE, 11 * GUI_SCALE, 59, 11, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                     var26 = I18n.func_135053_a("cosmetic.label." + (((String)var21.get("active")).equals("1")?"unequip":"equip")).toUpperCase();
                     ModernGui.drawScaledStringCustomFont(var26, (float)(this.guiLeft + skinTitle + 30 - 4), (float)this.guiTop + 200.0F, !var24 && ((String)var21.get("active")).equals("1")?COLOR_LIGHT_GRAY:COLOR_DARK_BLUE, 0.5F, "center", false, "georamaSemiBold", 24);
                     ClientEventHandler.STYLE.bindTexture("cosmetic");
                     ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + skinTitle + 30 + 1) + semiBold24.getStringWidth(var26) * 0.5F / 2.0F, (float)this.guiTop + 199.0F, (float)(595 * GUI_SCALE), (float)((((String)var21.get("active")).equals("1")?(var24?278:269):287) * GUI_SCALE), 7 * GUI_SCALE, 7 * GUI_SCALE, 7, 7, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                     if(var24) {
                        this.hoveredAction = "skinID#" + (String)var21.get("skin_name") + "#" + (((String)var21.get("active")).equals("1")?"unequip":"equip");
                     }

                     if(this.categoryTarget.equals("hats") && !((String)var21.get("rarity")).equals("limited")) {
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 142), (float)(this.guiTop + 197), (float)(709 * GUI_SCALE), (float)(174 * GUI_SCALE), 18 * GUI_SCALE, 11 * GUI_SCALE, 18, 11, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                        if(mouseX >= this.guiLeft + 142 && mouseX <= this.guiLeft + 142 + 59 && mouseY >= this.guiTop + 197 && mouseY <= this.guiTop + 197 + 11) {
                           ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 142), (float)(this.guiTop + 197), (float)(729 * GUI_SCALE), (float)(174 * GUI_SCALE), 18 * GUI_SCALE, 11 * GUI_SCALE, 18, 11, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                           this.hoveredAction = "exportHat";
                        }
                     }
                  } else if(((String)var21.get("by_default")).equals("1")) {
                     ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("cosmetic.label.by_default").toUpperCase(), (float)(this.guiLeft + 124), (float)(this.guiTop + 199), COLOR_LIGHT_BLUE, 0.5F, "center", false, "georamaSemiBold", 27);
                  }
               } else if(!((String)var21.get("price")).equals("0") && (((String)var21.get("unlock_type")).equals("null") || ((String)var21.get("unlock_type")).equals("ngprime") && ((Boolean)CosmeticGUI.data.get("ngprime")).booleanValue()) && (var21.get("availability_start") == null || Long.parseLong((String)var21.get("availability_start")) <= System.currentTimeMillis()) && (var21.get("availability_end") == null || Long.parseLong((String)var21.get("availability_end")) >= System.currentTimeMillis())) {
                  if(!((String)var21.get("price")).equals("0")) {
                     skinTitle = Integer.parseInt((String)var21.get("price"));
                     var24 = CosmeticGUI.data.get("player_points") != null && ((Double)CosmeticGUI.data.get("player_points")).doubleValue() >= (double)skinTitle;
                     boolean var28 = mouseX >= this.guiLeft + 121 && mouseX <= this.guiLeft + 121 + 46 && mouseY >= this.guiTop + 197 && mouseY <= this.guiTop + 197 + 12;
                     ModernGui.drawScaledStringCustomFont((String)var21.get("price"), (float)(this.guiLeft + 104), (float)(this.guiTop + 199), var24?COLOR_WHITE:COLOR_LIGHT_BLUE, 0.75F, "right", false, "georamaSemiBold", 24);
                     ClientEventHandler.STYLE.bindTexture("cosmetic");
                     ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 107), (float)(this.guiTop + 199), (float)(594 * GUI_SCALE), (float)((var24?179:199) * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                     ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 121), (float)(this.guiTop + 197), (float)(483 * GUI_SCALE), (float)((var24 && !var28?134:117) * GUI_SCALE), 46 * GUI_SCALE, 12 * GUI_SCALE, 46, 12, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                     ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("cosmetic.label.get").toUpperCase(), (float)(this.guiLeft + 144), (float)(this.guiTop + 200), var24 && !var28?COLOR_WHITE:COLOR_DARK_BLUE, 0.5F, "center", false, "georamaSemiBold", 24);
                     if(var24 && var28) {
                        this.itemToBuyHover.putAll(var21);
                        this.hoveredAction = "open_modal";
                     } else if(var28) {
                        this.tooltipToDraw.add(I18n.func_135053_a("cosmetic.label.not_enough_points"));
                     }
                  }
               } else {
                  ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("cosmetic.label.not_buyable").toUpperCase(), (float)(this.guiLeft + 124 - 5), (float)(this.guiTop + 199), COLOR_LIGHT_BLUE, 0.5F, "center", false, "georamaSemiBold", 27);
                  ClientEventHandler.STYLE.bindTexture("cosmetic");
                  ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 122) + semiBold27.getStringWidth(I18n.func_135053_a("cosmetic.label.not_buyable").toUpperCase()) * 0.5F / 2.0F, (float)this.guiTop + 198.2F, (float)(594 * GUI_SCALE), (float)(236 * GUI_SCALE), 9 * GUI_SCALE, 8 * GUI_SCALE, 9, 8, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                  if((float)mouseX >= (float)(this.guiLeft + 122) + semiBold27.getStringWidth(I18n.func_135053_a("cosmetic.label.not_buyable").toUpperCase()) * 0.5F / 2.0F && (float)mouseX <= (float)(this.guiLeft + 122) + semiBold27.getStringWidth(I18n.func_135053_a("cosmetic.label.not_buyable").toUpperCase()) * 0.5F / 2.0F + 9.0F && (float)mouseY >= (float)this.guiTop + 198.2F && (float)mouseY <= (float)this.guiTop + 198.2F + 8.0F) {
                     this.tooltipToDraw.add(I18n.func_135053_a("cosmetic.unlock_type." + (var21.get("unlock_type") != null && (!((String)var21.get("unlock_type")).equals("ngprime") || !((Boolean)CosmeticGUI.data.get("ngprime")).booleanValue())?(String)var21.get("unlock_type"):"date")));
                  }
               }
            }

            var22 = var21.containsKey("name_" + System.getProperty("java.lang"))?((String)var21.get("name_" + System.getProperty("java.lang"))).toUpperCase():((String)var21.get("skin_name")).toUpperCase();
            ModernGui.drawScaledStringCustomFont(var22, (float)(this.guiLeft + 206), (float)(this.guiTop + 43), CosmeticGUI.COLOR_LIGHT_GRAY, 0.75F, "left", false, "georamaSemiBold", 30);
            byte var25 = 0;
            ClientEventHandler.STYLE.bindTexture("cosmetic");
            var26 = (String)var21.get("rarity");
            if(cachedSelectedSkin != null) {
               var26 = (String)cachedSelectedSkin.get("rarity");
            }

            ModernGui.glColorHex(((Integer)CosmeticGUI.rarityColors.get(var26)).intValue(), 1.0F);
            ModernGui.drawRoundedRectangle((float)(this.guiLeft + 206 + 5 + (int)((double)CosmeticGUI.semiBold30.getStringWidth(var22) * 0.75D)), (float)(this.guiTop + 43), 0.0F, (float)((var26.equals("limited") && ((String)var21.get("owned")).equals("1")?70:35) + (((String)var21.get("unlock_type")).equals("ngprime")?5:0)), 9.0F);
            int var27 = var25 + (int)((double)CosmeticGUI.semiBold30.getStringWidth(var22) * 0.75D) + (var26.equals("limited") && ((String)var21.get("owned")).equals("1")?70:35) + 3;
            GL11.glColor3f(1.0F, 1.0F, 1.0F);
            String label = I18n.func_135053_a("cosmetic.rarity." + var26);
            if(var26.equals("limited") && ((String)var21.get("owned")).equals("1")) {
               if(!((String)var21.get("limited_copies")).equals("0")) {
                  label = I18n.func_135053_a("cosmetic.rarity.limited") + " N\u00b0" + (String)var21.get("limited_copy_number") + "/" + (String)var21.get("limited_copies");
               } else {
                  label = I18n.func_135053_a("cosmetic.rarity.limited") + " N\u00b0" + (String)var21.get("limited_copy_number");
               }
            }

            if(((String)var21.get("unlock_type")).equals("ngprime")) {
               ClientEventHandler.STYLE.bindTexture("cosmetic");
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 206 + 5 + (var26.equals("limited") && ((String)var21.get("owned")).equals("1")?35:17) + (int)((double)CosmeticGUI.semiBold30.getStringWidth(var22) * 0.75D)) - CosmeticGUI.semiBold27.getStringWidth(label) / 2.0F / 2.0F - 2.0F, (float)(this.guiTop + 45), (float)(655 * GUI_SCALE), (float)(187 * GUI_SCALE), 7 * GUI_SCALE, 5 * GUI_SCALE, 7, 5, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            }

            ModernGui.drawScaledStringCustomFont(label, (float)(this.guiLeft + 206 + 6 + (var26.equals("limited") && ((String)var21.get("owned")).equals("1")?35:17) + (int)((double)CosmeticGUI.semiBold30.getStringWidth(var22) * 0.75D) + (((String)var21.get("unlock_type")).equals("ngprime")?7:0)), (float)this.guiTop + 44.0F, COLOR_DARK_BLUE, 0.5F, "center", false, "georamaSemiBold", 27);
            if(var21.containsKey("is_new") && ((String)var21.get("is_new")).equals("1")) {
               ModernGui.glColorHex(-1, 1.0F);
               ModernGui.drawRoundedRectangle((float)(this.guiLeft + 206 + 5 + var27), (float)(this.guiTop + 43), 0.0F, 26.0F, 9.0F);
               ModernGui.drawScaledStringCustomFont("NEW !", (float)(this.guiLeft + 206 + 5 + var27 + 13), (float)(this.guiTop + 44), COLOR_DARK_BLUE, 0.5F, "center", false, "georamaSemiBold", 27);
               var27 += 29;
            }

            int offsetX;
            int entry;
            String groupID;
            if(var21.containsKey("only_servers") && var21.get("only_servers") != null) {
               String[] groupIdsOffsetY = ((String)var21.get("only_servers")).split(",");
               offsetX = groupIdsOffsetY.length;

               for(entry = 0; entry < offsetX; ++entry) {
                  groupID = groupIdsOffsetY[entry];
                  if(CommonCosmeticGUI.SERVERS_COLOR.containsKey(groupID)) {
                     ModernGui.glColorHex(((Integer)CommonCosmeticGUI.SERVERS_COLOR.get(groupID)).intValue(), 1.0F);
                     ModernGui.drawRoundedRectangle((float)(this.guiLeft + 206 + 5 + var27), (float)(this.guiTop + 43), 0.0F, 40.0F, 9.0F);
                     ModernGui.glColorHex(-14277557, 1.0F);
                     ModernGui.drawRoundedRectangle((float)(this.guiLeft + 206) + 5.6F + (float)var27, (float)this.guiTop + 43.5F, 0.0F, 38.9F, 7.9F);
                     ClientEventHandler.STYLE.bindTexture("cosmetic");
                     GL11.glColor3f(1.0F, 1.0F, 1.0F);
                     ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 206) + 7.5F + (float)var27, (float)(this.guiTop + 44), (float)(685 * GUI_SCALE), (float)((156 + CommonCosmeticGUI.SERVERS_ORDER.indexOf(groupID) * 8) * GUI_SCALE), 7 * GUI_SCALE, 7 * GUI_SCALE, 7, 7, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                     ModernGui.drawScaledStringCustomFont(groupID.toUpperCase(), (float)(this.guiLeft + 206 + 5 + var27 + 24), (float)this.guiTop + 44.4F, ((Integer)CommonCosmeticGUI.SERVERS_COLOR.get(groupID)).intValue(), 0.5F, "center", false, "georamaSemiBold", 27);
                     var27 += 43;
                  }
               }
            }

            if(var21.get("event_icon") != null && !((String)var21.get("event_icon")).isEmpty() && eventIcons.containsKey(var21.get("event_icon"))) {
               ClientEventHandler.STYLE.bindTexture("cosmetic");
               boolean var29 = mouseX >= this.guiLeft + 206 + 5 + var27 && mouseX <= this.guiLeft + 206 + 5 + var27 + 14 && (float)mouseY >= (float)this.guiTop + 40.5F && (float)mouseY <= (float)this.guiTop + 40.5F + 14.0F;
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 206 + 5 + var27), (float)this.guiTop + 40.3F, (float)((var29?631:617) * GUI_SCALE), (float)(((Integer)eventIcons.get(var21.get("event_icon"))).intValue() * GUI_SCALE), 14 * GUI_SCALE, 14 * GUI_SCALE, 14, 14, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
               if(var29) {
                  this.tooltipToDraw.add(I18n.func_135053_a("cosmetic.event." + (String)var21.get("event_icon")));
               }
            }

            ModernGui.drawSectionStringCustomFont(var21.containsKey("description_" + System.getProperty("java.lang"))?((String)var21.get("description_" + System.getProperty("java.lang"))).replaceAll("<player>", Minecraft.func_71410_x().field_71439_g.getDisplayName()):"no description set", (float)(this.guiLeft + 206), (float)(this.guiTop + 60), CosmeticGUI.COLOR_LIGHT_GRAY, 0.5F, "left", false, "georamaMedium", 25, 9, 450);
            if(!this.categoryTarget.equals("capes") && !this.categoryTarget.equals("emotes") && !this.categoryTarget.equals("badges") && !this.categoryTarget.equals("buddies")) {
               index = 0;

               for(Iterator var31 = var19.iterator(); var31.hasNext(); ++index) {
                  HashMap var32 = (HashMap)var31.next();
                  entry = this.guiLeft + 207 + index % 8 * 30;
                  int var36 = this.guiTop + 90 + index / 8 * 29;
                  ClientEventHandler.STYLE.bindTexture("cosmetic");
                  if(selectedSkinId == null && ((String)var32.get("active")).equals("1")) {
                     selectedSkinId = (String)var32.get("skin_name");
                  }

                  if(selectedSkinId != null && selectedSkinId.equals(var32.get("skin_name")) && (cachedSelectedSkin == null || !((String)cachedSelectedSkin.get("skin_name")).equals(var32.get("skin_name")))) {
                     cachedSelectedSkin = var32;
                     if(cachedSelectedSkin.get("preview_sound") != null) {
                        ClientProxy.playClientMusic((String)cachedSelectedSkin.get("preview_sound"), 1.5F);
                     } else {
                        ClientProxy.stopClientMusic();
                     }
                  }

                  if(!this.isBehindModal(entry, var36)) {
                     if(((String)var32.get("owned")).equals("1")) {
                        ModernGui.drawScaledCustomSizeModalRect((float)entry, (float)var36, (float)(((Integer)skinIDSlotByRarityX.get(var32.get("rarity"))).intValue() * GUI_SCALE), (float)(322 * GUI_SCALE), 25 * GUI_SCALE, 25 * GUI_SCALE, 25, 25, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                        if(ClientProxy.SKIN_MANAGER.getSkinFromID((String)var32.get("skin_name")) != null) {
                           ClientProxy.SKIN_MANAGER.getSkinFromID((String)var32.get("skin_name")).renderInGUI(entry, var36, 1.0F, par3);
                        }

                        GL11.glColor3f(1.0F, 1.0F, 1.0F);
                        if(mouseX >= entry && mouseX <= entry + 25 && mouseY >= var36 && mouseY <= var36 + 25) {
                           this.hoveredAction = "skinID#" + (String)var32.get("skin_name") + "#select";
                        }
                     } else {
                        ModernGui.drawScaledCustomSizeModalRect((float)entry, (float)var36, (float)(483 * GUI_SCALE), (float)(322 * GUI_SCALE), 25 * GUI_SCALE, 25 * GUI_SCALE, 25, 25, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
                        if(ClientProxy.SKIN_MANAGER.getSkinFromID((String)var32.get("skin_name")) != null) {
                           ClientProxy.SKIN_MANAGER.getSkinFromID((String)var32.get("skin_name")).renderInGUI(entry, var36, 1.0F, par3);
                        }

                        GL11.glColor3f(1.0F, 1.0F, 1.0F);
                        ClientEventHandler.STYLE.bindTexture("cosmetic");
                        GL11.glPushMatrix();
                        GL11.glTranslatef(0.0F, 0.0F, 500.0F);
                        ModernGui.drawScaledCustomSizeModalRect((float)entry, (float)var36, (float)(513 * GUI_SCALE), (float)(322 * GUI_SCALE), 25 * GUI_SCALE, 25 * GUI_SCALE, 25, 25, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
                        GL11.glPopMatrix();
                        if(mouseX >= entry && mouseX <= entry + 25 && mouseY >= var36 && mouseY <= var36 + 25) {
                           this.hoveredAction = "skinID#" + (String)var32.get("skin_name") + "#select";
                        }
                     }
                  }

                  if(selectedSkinId != null && selectedSkinId.equals(var32.get("skin_name"))) {
                     ClientEventHandler.STYLE.bindTexture("cosmetic");
                     ModernGui.drawScaledCustomSizeModalRect((float)entry, (float)var36, (float)(543 * GUI_SCALE), (float)(322 * GUI_SCALE), 25 * GUI_SCALE, 25 * GUI_SCALE, 25, 25, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
                  }
               }
            } else {
               int var30;
               if(this.categoryTarget.equals("emotes")) {
                  ClientEventHandler.STYLE.bindTexture("cosmetic");
                  ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 206), (float)(this.guiTop + 85), (float)(219 * GUI_SCALE), (float)(257 * GUI_SCALE), 244 * GUI_SCALE, 43 * GUI_SCALE, 244, 43, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);

                  for(var30 = 0; var30 < 6; ++var30) {
                     offsetX = this.guiLeft + 214 + var30 * 24;
                     ClientEventHandler.STYLE.bindTexture("cosmetic");
                     ModernGui.drawScaledCustomSizeModalRect((float)offsetX, (float)(this.guiTop + 90), (float)(483 * GUI_SCALE), (float)(421 * GUI_SCALE), 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
                     if(activeEmotes.size() > var30) {
                        if(ClientProxy.SKIN_MANAGER.getSkinFromID((String)activeEmotes.get(var30)) != null) {
                           ClientProxy.SKIN_MANAGER.getSkinFromID((String)activeEmotes.get(var30)).renderInGUI(offsetX - 3, this.guiTop + 89, 1.0F, par3);
                        }

                        if(mouseX >= offsetX + 2 && mouseX <= offsetX + 18 && mouseY >= this.guiTop + 90 + 2 && mouseY <= this.guiTop + 90 + 18) {
                           this.hoveredAction = "groupID#" + (String)activeEmotes.get(var30);
                        }
                     } else {
                        ModernGui.drawScaledStringCustomFont(var30 + 1 + "", (float)offsetX + 10.5F, (float)this.guiTop + 96.5F, CosmeticGUI.COLOR_DARK_BLUE, 0.75F, "center", false, "georamaBold", 26);
                     }
                  }
               } else if(this.categoryTarget.equals("badges")) {
                  ClientEventHandler.STYLE.bindTexture("cosmetic");
                  ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 206), (float)(this.guiTop + 85), (float)(219 * GUI_SCALE), (float)(257 * GUI_SCALE), 244 * GUI_SCALE, 43 * GUI_SCALE, 244, 43, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);

                  for(var30 = 0; var30 < 2; ++var30) {
                     offsetX = this.guiLeft + 214 + var30 * 24;
                     ClientEventHandler.STYLE.bindTexture("cosmetic");
                     ModernGui.drawScaledCustomSizeModalRect((float)offsetX, (float)(this.guiTop + 90), (float)(483 * GUI_SCALE), (float)(421 * GUI_SCALE), 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
                     if(activeBadges.size() > var30) {
                        if(ClientProxy.SKIN_MANAGER.getSkinFromID((String)activeBadges.get(var30)) != null) {
                           ClientProxy.SKIN_MANAGER.getSkinFromID((String)activeBadges.get(var30)).renderInGUI(offsetX + 3, this.guiTop + 93, 0.6F, par3);
                        }

                        if(mouseX >= offsetX + 2 && mouseX <= offsetX + 18 && mouseY >= this.guiTop + 90 + 2 && mouseY <= this.guiTop + 90 + 18) {
                           this.hoveredAction = "groupID#" + (String)activeBadges.get(var30);
                        }
                     } else {
                        ModernGui.drawScaledStringCustomFont(var30 + 1 + "", (float)offsetX + 10.5F, (float)(this.guiTop + 97), CosmeticGUI.COLOR_DARK_BLUE, 0.75F, "center", false, "georamaBold", 26);
                     }
                  }
               }
            }

            if(selectedSkinId == null) {
               cachedSelectedSkin = (HashMap)((ArrayList)((LinkedHashMap)categoriesData.get(this.categoryTarget)).get(this.selectedGroupId)).get(0);
               selectedSkinId = (String)cachedSelectedSkin.get("skin_name");
               if(cachedSelectedSkin.get("preview_sound") != null) {
                  ClientProxy.playClientMusic((String)cachedSelectedSkin.get("preview_sound"), 1.5F);
               } else {
                  ClientProxy.stopClientMusic();
               }
            }

            index = 0;
            short var33 = 158;
            if(!this.categoryTarget.equals("hats") && !this.categoryTarget.equals("chestplates") && !this.categoryTarget.equals("emotes") && !this.categoryTarget.equals("badges")) {
               if(this.categoryTarget.equals("capes") || this.categoryTarget.equals("buddies")) {
                  var33 = 94;
               }
            } else {
               var33 = 126;
            }

            GUIUtils.startGLScissor(this.guiLeft + 206, this.guiTop + var33 - 2, 235, 59 + (158 - var33));

            for(Iterator var34 = ((LinkedHashMap)categoriesData.get(this.categoryTarget)).entrySet().iterator(); var34.hasNext(); ++index) {
               Entry var35 = (Entry)var34.next();
               groupID = (String)var35.getKey();
               HashMap skinData = (HashMap)((ArrayList)var35.getValue()).get(0);
               int offsetX1 = this.guiLeft + 206 + index % 8 * 30;
               Float offsetY = Float.valueOf((float)(this.guiTop + var33 + index / 8 * 32) + this.getSlideGroupIds());
               ClientEventHandler.STYLE.bindTexture("cosmetic");
               boolean isGroupIdActive = false;
               if(this.categoryTarget.equals("emotes")) {
                  isGroupIdActive = activeEmotes.contains(groupID);
               } else if(this.categoryTarget.equals("badges")) {
                  isGroupIdActive = activeBadges.contains(groupID);
               } else {
                  isGroupIdActive = this.selectedGroupId.equals(groupID);
               }

               GL11.glColor3f(1.0F, 1.0F, 1.0F);
               GL11.glEnable(3042);
               ModernGui.drawScaledCustomSizeModalRect((float)offsetX1, (float)offsetY.intValue(), (float)(((Integer)groupIDSlotByRarityX.get(skinData.get("rarity"))).intValue() * GUI_SCALE), (float)((isGroupIdActive?385:356) * GUI_SCALE), 25 * GUI_SCALE, 25 * GUI_SCALE, 25, 25, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
               if(!this.isBehindModal(offsetX1, offsetY.intValue())) {
                  if(ClientProxy.SKIN_MANAGER.getSkinFromID((String)skinData.get("skin_name")) != null) {
                     ClientProxy.SKIN_MANAGER.getSkinFromID((String)skinData.get("skin_name")).renderInGUI(offsetX1, offsetY.intValue(), 1.0F, par3);
                  }

                  GL11.glColor3f(1.0F, 1.0F, 1.0F);
               }

               if(((String)skinData.get("owned")).equals("0")) {
                  ClientEventHandler.STYLE.bindTexture("cosmetic");
                  GL11.glPushMatrix();
                  GL11.glTranslatef(0.0F, 0.0F, 200.0F);
                  ModernGui.drawScaledCustomSizeModalRect((float)offsetX1, (float)offsetY.intValue(), (float)(513 * GUI_SCALE), (float)(356 * GUI_SCALE), 25 * GUI_SCALE, 25 * GUI_SCALE, 25, 25, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
                  GL11.glPopMatrix();
               }

               if(skinData.containsKey("is_new") && ((String)skinData.get("is_new")).equals("1")) {
                  ClientEventHandler.STYLE.bindTexture("cosmetic");
                  GL11.glPushMatrix();
                  GL11.glTranslatef(0.0F, 0.0F, 200.0F);
                  ModernGui.drawScaledCustomSizeModalRect((float)(offsetX1 + 15), (float)(offsetY.intValue() - 1), (float)(709 * GUI_SCALE), (float)(189 * GUI_SCALE), 12 * GUI_SCALE, 4 * GUI_SCALE, 12, 4, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
                  GL11.glPopMatrix();
               }

               if(mouseY >= this.guiTop + var33 && mouseY <= this.guiTop + var33 + 57 + (158 - var33) && mouseX >= offsetX1 && mouseX <= offsetX1 + 25 && (float)mouseY >= offsetY.floatValue() && (float)mouseY <= offsetY.floatValue() + 25.0F) {
                  this.hoveredAction = "groupID#" + groupID;
               }
            }

            GUIUtils.endGLScissor();
            this.scrollBarGroupIds.draw(mouseX, mouseY);
         }

         this.overridePreviewSkin = selectedSkinId;
      }

      super.func_73863_a(mouseX, mouseY, par3);
      if(!this.tooltipToDraw.isEmpty()) {
         this.drawHoveringText(this.tooltipToDraw, mouseX, mouseY, this.field_73886_k);
      }

      GL11.glEnable(2896);
      RenderHelper.func_74519_b();
   }

   public void func_73874_b() {
      ClientProxy.stopClientMusic();
      super.func_73874_b();
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(this.displayModal) {
         super.func_73864_a(mouseX, mouseY, mouseButton);
      }

      if(mouseButton == 0) {
         if(this.hoveredAction.equals("return")) {
            Minecraft.func_71410_x().func_71373_a(new CosmeticGUI(this.playerTarget));
         } else if(this.hoveredAction.contains("tab#")) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            this.categoryTarget = this.hoveredAction.replaceAll("tab#", "");
            if(!categoriesData.containsKey(this.categoryTarget)) {
               loaded = false;
               PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new CosmeticCategoryDataPacket(this.categoryTarget, this.playerTarget)));
            }

            this.selectedGroupId = null;
            selectedSkinId = null;
            manualOffsetPlayerRotation = 0.0F;
         } else if(this.hoveredAction.contains("groupID#")) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            this.selectedGroupId = this.hoveredAction.replaceAll("groupID#", "");
            selectedSkinId = (String)((HashMap)((ArrayList)((LinkedHashMap)categoriesData.get(this.categoryTarget)).get(this.selectedGroupId)).get(0)).get("skin_name");
            cachedSelectedSkin = null;
            if(this.categoryTarget.equals("emotes")) {
               ClientEmotesHandler.playEmote(selectedSkinId.replaceAll("emotes_", ""), true);
            }
         } else if(this.hoveredAction.contains("skinID#")) {
            if(this.hoveredAction.split("#")[2].equals("select")) {
               this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
               selectedSkinId = this.hoveredAction.split("#")[1];
               cachedSelectedSkin = null;
            } else if(this.hoveredAction.split("#")[2].equals("equip")) {
               ClientProxy.playClientMusic("https://static.nationsglory.fr/N342363533.mp3", 1.0F);
               boolean activeEmotesChanged = false;
               boolean activeBadgesChanged = false;
               String badges;
               Iterator var7;
               String badge;
               if(this.categoryTarget.equals("emotes")) {
                  if(activeEmotes.contains(this.selectedGroupId)) {
                     activeEmotes.remove(this.selectedGroupId);
                     activeEmotesChanged = true;
                  } else if(activeEmotes.size() < 6 && ((String)((HashMap)((ArrayList)((LinkedHashMap)categoriesData.get(this.categoryTarget)).get(this.selectedGroupId)).get(0)).get("owned")).equals("1")) {
                     activeEmotes.add(this.selectedGroupId);
                     activeEmotesChanged = true;
                  }

                  if(activeEmotesChanged) {
                     badges = "";

                     for(var7 = activeEmotes.iterator(); var7.hasNext(); badges = badges + badge + "#") {
                        badge = (String)var7.next();
                     }

                     badges = badges.replaceAll("#$", "");
                     PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new CosmeticSetActivePacket(this.playerTarget, badges, this.categoryTarget)));
                  }
               } else if(this.categoryTarget.equals("badges")) {
                  if(activeBadges.contains(this.selectedGroupId)) {
                     activeBadges.remove(this.selectedGroupId);
                     activeBadgesChanged = true;
                  } else if(activeBadges.size() < 2 && ((String)((HashMap)((ArrayList)((LinkedHashMap)categoriesData.get(this.categoryTarget)).get(this.selectedGroupId)).get(0)).get("owned")).equals("1")) {
                     activeBadges.add(this.selectedGroupId);
                     activeBadgesChanged = true;
                  }

                  if(activeBadgesChanged) {
                     badges = "";

                     for(var7 = activeBadges.iterator(); var7.hasNext(); badges = badges + badge + "#") {
                        badge = (String)var7.next();
                     }

                     badges = badges.replaceAll("#$", "");
                     PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new CosmeticSetActivePacket(this.playerTarget, badges, this.categoryTarget)));
                  }
               } else {
                  PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new CosmeticSetActivePacket(this.playerTarget, this.hoveredAction.split("#")[1], this.categoryTarget)));
               }
            } else if(this.hoveredAction.split("#")[2].equals("unequip")) {
               ClientProxy.playClientMusic("https://static.nationsglory.fr/N342364y3y.mp3", 1.0F);
               PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new CosmeticResetGroupPacket(this.playerTarget, this.selectedGroupId, this.categoryTarget)));
            }
         } else if(this.hoveredAction.equals("rotatePlus")) {
            manualOffsetPlayerRotation += 45.0F;
         } else if(this.hoveredAction.equals("rotateMinus")) {
            manualOffsetPlayerRotation -= 45.0F;
         } else if(this.hoveredAction.equals("exportHat")) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new HatExportPacket(selectedSkinId)));
         }
      }

      super.func_73864_a(mouseX, mouseY, mouseButton);
   }

}
