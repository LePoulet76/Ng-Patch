package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IncrementObjectivePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SkillDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SkillLeaderBoardDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class SkillsGui extends GuiScreen {

   protected int xSize = 463;
   protected int ySize = 232;
   private int guiLeft;
   private int guiTop;
   public String playerTarget;
   public static boolean loaded = false;
   public static HashMap<String, HashMap<String, Object>> skillsData = null;
   public static HashMap<String, List<String>> skillsLeaderBoard = null;
   public static HashMap<String, String> skillsTopInterServ = null;
   private RenderItem itemRenderer = new RenderItem();
   public String hoveredAction = "";
   public String hoveredSkill = "";
   public static String selectedSkill = "";
   public int hoveredLevel = -1;
   private GuiScrollBarGeneric scrollBar;
   private EntityOtherPlayerMP topPlayerEntity = null;
   public static HashMap<String, Integer> skillIconPositionY = new HashMap();
   public static HashMap<String, ResourceLocation> cacheHeadPlayer = new HashMap();
   public static String displayMode = "personal";
   public static boolean achievementDone = false;


   public SkillsGui(String playerTarget) {
      this.playerTarget = playerTarget;
      loaded = false;
      displayMode = "personal";
      if(skillIconPositionY.isEmpty()) {
         skillIconPositionY.put("farmer", Integer.valueOf(240));
         skillIconPositionY.put("lumberjack", Integer.valueOf(271));
         skillIconPositionY.put("builder", Integer.valueOf(303));
         skillIconPositionY.put("hunter", Integer.valueOf(333));
         skillIconPositionY.put("engineer", Integer.valueOf(365));
         skillIconPositionY.put("miner", Integer.valueOf(396));
      }

      selectedSkill = "";
      if(!achievementDone) {
         achievementDone = true;
         PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IncrementObjectivePacket("player_open_skills", 1)));
      }

   }

   public void func_73866_w_() {
      super.func_73866_w_();
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new SkillDataPacket(this.playerTarget)));
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - this.ySize) / 2;
      this.scrollBar = new GuiScrollBarGeneric((float)(this.guiLeft + 437), (float)(this.guiTop + 56), 154, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_grey.png"), 3, 26);
   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      Object tooltipToDraw = new ArrayList();
      this.hoveredLevel = -1;
      this.hoveredSkill = "";
      this.hoveredAction = "";
      this.func_73873_v_();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ClientEventHandler.STYLE.bindTexture("skills");
      ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
      if(mouseX >= this.guiLeft + 433 && mouseX <= this.guiLeft + 433 + 18 && mouseY >= this.guiTop + 8 && mouseY <= this.guiTop + 8 + 18) {
         ClientEventHandler.STYLE.bindTexture("skills");
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 433), (float)(this.guiTop + 8), 129, 280, 18, 18, 512.0F, 512.0F, false);
      }

      ModernGui.drawScaledStringCustomFont(Minecraft.func_71410_x().field_71439_g.field_71092_bJ.equalsIgnoreCase(this.playerTarget)?I18n.func_135053_a("skills.title"):I18n.func_135053_a("skills.title_of").replace("#player#", this.playerTarget.toUpperCase()), (float)(this.guiLeft + 12), (float)(this.guiTop + 11), 12895428, 1.0F, "left", false, "georamaExtraBold", 28);
      if(loaded && skillsData.size() > 0) {
         int skillOffsetY = 0;

         for(Iterator it = skillsData.entrySet().iterator(); it.hasNext(); ++skillOffsetY) {
            Entry playerPosition = (Entry)it.next();
            String i = (String)playerPosition.getKey();
            ClientEventHandler.STYLE.bindTexture("skills");
            if(selectedSkill.equals(i)) {
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 12), (float)(this.guiTop + 41 + 31 * skillOffsetY), 214, 485, 104, 27, 512.0F, 512.0F, false);
            } else if(mouseX >= this.guiLeft + 12 && mouseX <= this.guiLeft + 12 + 104 && mouseY >= this.guiTop + 41 + 31 * skillOffsetY && mouseY <= this.guiTop + 41 + 31 * skillOffsetY + 27) {
               this.hoveredSkill = i;
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 12), (float)(this.guiTop + 41 + 31 * skillOffsetY), 107, 485, 104, 27, 512.0F, 512.0F, false);
            } else {
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 12), (float)(this.guiTop + 41 + 31 * skillOffsetY), 0, 485, 104, 27, 512.0F, 512.0F, false);
            }

            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 17), (float)(this.guiTop + 48 + 31 * skillOffsetY), i.equals(selectedSkill)?423:443, ((Integer)skillIconPositionY.get(i)).intValue(), 15, 15, 512.0F, 512.0F, false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("skills.skill." + i), (float)(this.guiLeft + 38), (float)(this.guiTop + 50 + 31 * skillOffsetY), i.equals(selectedSkill)?16777215:12895428, 1.0F, "left", false, "georamaSemiBold", 20);
            ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[]{(Double)((HashMap)playerPosition.getValue()).get("actualLevelNumber")}), (float)(this.guiLeft + 20 + 104 - 15), (float)(this.guiTop + 51 + 31 * skillOffsetY), i.equals(selectedSkill)?16777215:7239406, 1.0F, "right", false, "georamaSemiBold", 17);
         }

         Double playerScore;
         int var24;
         if(displayMode.equals("personal")) {
            if(!selectedSkill.isEmpty()) {
               HashMap var22 = (HashMap)skillsData.get(selectedSkill);
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("skills.skill." + selectedSkill).toUpperCase(), (float)(this.guiLeft + 134), (float)(this.guiTop + 51), 7239406, 1.0F, "left", false, "georamaBold", 28);

               for(var24 = 0; var24 <= 4; ++var24) {
                  ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("skills.skill." + selectedSkill + ".desc." + (var24 + 1)), (float)(this.guiLeft + 134), (float)(this.guiTop + 78 + var24 * 12), 16777215, 1.0F, "left", false, "georamaRegular", 20);
               }

               ClientEventHandler.STYLE.bindTexture("skills");
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 134), (float)(this.guiTop + 145), 443, ((Integer)skillIconPositionY.get(selectedSkill)).intValue(), 15, 15, 512.0F, 512.0F, false);
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("skills.how_to"), (float)(this.guiLeft + 153), (float)(this.guiTop + 148), 12895428, 1.0F, "left", false, "georamaRegular", 18);
               if(mouseX >= this.guiLeft + 134 && mouseX <= this.guiLeft + 134 + 15 + this.field_73886_k.func_78256_a(I18n.func_135053_a("skills.how_to")) && mouseY >= this.guiTop + 145 && mouseY <= this.guiTop + 145 + 15) {
                  tooltipToDraw = Arrays.asList(I18n.func_135053_a("skills.skill." + selectedSkill + ".tooltip.how_to").split("##"));
               }

               ClientEventHandler.STYLE.bindTexture("skills");
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 340), (float)(this.guiTop + 50), 104, 320, 101, 111, 512.0F, 512.0F, false);
               ClientEventHandler.STYLE.bindTexture("skill_" + selectedSkill);
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 336), (float)(this.guiTop - 7), 0.0F, 0.0F, 331, 400, 109, 132, 331.0F, 400.0F, false);
               Double var26 = (Double)var22.get("position");
               ModernGui.drawScaledStringCustomFont(var26.doubleValue() != 0.0D?String.format("%.0f", new Object[]{var26}) + "e":"NC", (float)(this.guiLeft + 390), (float)(this.guiTop + 129), 16777215, 1.0F, "center", false, "georamaSemiBold", 23);
               ClientEventHandler.STYLE.bindTexture("skills");
               if(mouseX >= this.guiLeft + 355 && mouseX <= this.guiLeft + 355 + 71 && mouseY >= this.guiTop + 142 && mouseY <= this.guiTop + 142 + 15) {
                  ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 355), (float)(this.guiTop + 142), 149, 297, 71, 15, 512.0F, 512.0F, false);
                  ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("skills.leaderboard"), (float)(this.guiLeft + 391), (float)(this.guiTop + 146), 7239406, 1.0F, "center", false, "georamaSemiBold", 18);
                  this.hoveredAction = "switch_leaderboard";
               } else {
                  ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 355), (float)(this.guiTop + 142), 149, 280, 71, 15, 512.0F, 512.0F, false);
                  ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("skills.leaderboard"), (float)(this.guiLeft + 391), (float)(this.guiTop + 146), 16777215, 1.0F, "center", false, "georamaSemiBold", 18);
               }

               ClientEventHandler.STYLE.bindTexture("skills");
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 134), (float)(this.guiTop + 170), 0, 433, 307, 46, 512.0F, 512.0F, false);
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("skills.level").toUpperCase(), (float)(this.guiLeft + 141), (float)(this.guiTop + 175), 16777215, 1.0F, "left", false, "georamaSemiBold", 20);
               Double offsetY = (Double)var22.get("score");
               Double playerName = (Double)var22.get("actualLevelNumber");
               playerScore = (Double)var22.get("actualLevel");
               Double e = (Double)var22.get("nextLevel");
               ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[]{offsetY}) + "/" + String.format("%.0f", new Object[]{e}), (float)(this.guiLeft + 436), (float)(this.guiTop + 175), 16777215, 1.0F, "right", false, "georamaRegular", 18);
               List levels = (List)var22.get("levels");
               int levelDotInterval = 276 / levels.size();
               Double scoreProgress = Double.valueOf(Math.min(1.0D, (offsetY.doubleValue() - playerScore.doubleValue()) / (e.doubleValue() - playerScore.doubleValue())));
               Double progressWidth = Double.valueOf(Math.min(276.0D, playerName.doubleValue() * (double)levelDotInterval + (double)levelDotInterval * scoreProgress.doubleValue()));
               ClientEventHandler.STYLE.bindTexture("skills");
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 149), (float)(this.guiTop + 200), 166, 234, 276, 4, 512.0F, 512.0F, false);
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 149), (float)(this.guiTop + 200), 215, 420, progressWidth.intValue(), 4, 512.0F, 512.0F, false);
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 149 - 7), (float)(this.guiTop + 194), 129, 252, 15, 15, 512.0F, 512.0F, false);
               ModernGui.drawScaledStringCustomFont("0", (float)(this.guiLeft + 150), (float)(this.guiTop + 196), 16777215, 1.0F, "center", false, "georamaBold", 22);

               for(int i1 = 1; i1 <= levels.size(); ++i1) {
                  ClientEventHandler.STYLE.bindTexture("skills");
                  if(offsetY.doubleValue() >= ((Double)levels.get(i1 - 1)).doubleValue()) {
                     if(mouseX >= this.guiLeft + 149 - 7 + levelDotInterval * i1 && mouseX <= this.guiLeft + 149 - 7 + levelDotInterval * i1 + 15 && mouseY >= this.guiTop + 194 && mouseY <= this.guiTop + 194 + 15) {
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 149 - 7 + levelDotInterval * i1), (float)(this.guiTop + 194), 149, 252, 15, 15, 512.0F, 512.0F, false);
                        ModernGui.drawScaledStringCustomFont(i1 + "", (float)(this.guiLeft + 150 + levelDotInterval * i1), (float)(this.guiTop + 196), 7239406, 1.0F, "center", false, "georamaBold", 22);
                        tooltipToDraw = Arrays.asList(I18n.func_135053_a("skills.skill." + selectedSkill + ".tooltip.level." + i1).split("##"));
                     } else {
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 149 - 7 + levelDotInterval * i1), (float)(this.guiTop + 194), 129, 252, 15, 15, 512.0F, 512.0F, false);
                        ModernGui.drawScaledStringCustomFont(i1 + "", (float)(this.guiLeft + 150 + levelDotInterval * i1), (float)(this.guiTop + 196), 16777215, 1.0F, "center", false, "georamaBold", 22);
                     }
                  } else if(mouseX >= this.guiLeft + 149 - 7 + levelDotInterval * i1 && mouseX <= this.guiLeft + 149 - 7 + levelDotInterval * i1 + 15 && mouseY >= this.guiTop + 194 && mouseY <= this.guiTop + 194 + 15) {
                     ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 149 - 7 + levelDotInterval * i1), (float)(this.guiTop + 194), 149, 234, 15, 15, 512.0F, 512.0F, false);
                     tooltipToDraw = Arrays.asList(I18n.func_135053_a("skills.skill." + selectedSkill + ".tooltip.level." + i1).split("##"));
                  } else {
                     ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 149 - 7 + levelDotInterval * i1), (float)(this.guiTop + 194), 129, 234, 15, 15, 512.0F, 512.0F, false);
                  }
               }
            }
         } else {
            ClientEventHandler.STYLE.bindTexture("skills");
            if(mouseX >= this.guiLeft + 375 && mouseX <= this.guiLeft + 375 + 52 && mouseY >= this.guiTop + 9 && mouseY <= this.guiTop + 9 + 16) {
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 375), (float)(this.guiTop + 9), 168, 262, 52, 16, 512.0F, 512.0F, false);
               this.hoveredAction = "switch_personal";
            } else {
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 375), (float)(this.guiTop + 9), 168, 245, 52, 16, 512.0F, 512.0F, false);
            }

            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("skills.return"), (float)(this.guiLeft + 401), (float)(this.guiTop + 13), 7239406, 1.0F, "center", false, "georamaRegular", 18);
            ClientEventHandler.STYLE.bindTexture("skills");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 130), (float)(this.guiTop + 50), 0, 234, 127, 35, 512.0F, 512.0F, false);
            if(!((String)skillsTopInterServ.get(selectedSkill)).isEmpty()) {
               if(this.topPlayerEntity == null || !((String)skillsTopInterServ.get(selectedSkill)).split("#")[0].equalsIgnoreCase(this.topPlayerEntity.field_71092_bJ)) {
                  try {
                     this.topPlayerEntity = new EntityOtherPlayerMP(this.field_73882_e.field_71441_e, ((String)skillsTopInterServ.get(selectedSkill)).split("#")[0]);
                  } catch (Exception var21) {
                     this.topPlayerEntity = null;
                  }
               }

               if(this.topPlayerEntity != null) {
                  GUIUtils.startGLScissor(this.guiLeft + 130, this.guiTop + 35, 50, 50);
                  GuiInventory.func_110423_a(this.guiLeft + 155, this.guiTop + 117, 42, 0.0F, 0.0F, this.topPlayerEntity);
                  GUIUtils.endGLScissor();
               }
            }

            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("skills.best") + " " + I18n.func_135053_a("skills.skill." + selectedSkill).toUpperCase() + " NG", (float)(this.guiLeft + 180), (float)(this.guiTop + 54), 0, 1.0F, "left", false, "georamaRegular", 13);
            ModernGui.drawScaledStringCustomFont(!((String)skillsTopInterServ.get(selectedSkill)).isEmpty()?this.topPlayerEntity.field_71092_bJ:"???", (float)(this.guiLeft + 180), (float)(this.guiTop + 61), 16777215, 1.0F, "left", false, "georamaSemiBold", 22);
            ModernGui.drawScaledStringCustomFont(!((String)skillsTopInterServ.get(selectedSkill)).isEmpty()?((String)skillsTopInterServ.get(selectedSkill)).split("#")[1]:"0", (float)(this.guiLeft + 180), (float)(this.guiTop + 74), 16777215, 1.0F, "left", false, "georamaRegular", 18);
            ClientEventHandler.STYLE.bindTexture("skills");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 130), (float)(this.guiTop + 91), 0, 271, 127, 35, 512.0F, 512.0F, false);
            if(!cacheHeadPlayer.containsKey(this.playerTarget)) {
               try {
                  ResourceLocation var23 = AbstractClientPlayer.field_110314_b;
                  var23 = AbstractClientPlayer.func_110311_f(this.playerTarget);
                  AbstractClientPlayer.func_110304_a(var23, this.playerTarget);
                  cacheHeadPlayer.put(this.playerTarget, var23);
               } catch (Exception var20) {
                  System.out.println(var20.getMessage());
               }
            } else {
               Minecraft.func_71410_x().field_71446_o.func_110577_a((ResourceLocation)cacheHeadPlayer.get(this.playerTarget));
               this.field_73882_e.func_110434_K().func_110577_a((ResourceLocation)cacheHeadPlayer.get(this.playerTarget));
               GUIUtils.drawScaledCustomSizeModalRect(this.guiLeft + 166, this.guiTop + 122, 8.0F, 16.0F, 8, -8, -26, -26, 64.0F, 64.0F);
            }

            ModernGui.drawScaledStringCustomFont(this.playerTarget.toUpperCase(), (float)(this.guiLeft + 180), (float)(this.guiTop + 95), 0, 1.0F, "left", false, "georamaRegular", 13);
            ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[]{(Double)((HashMap)skillsData.get(selectedSkill)).get("score")}), (float)(this.guiLeft + 180), (float)(this.guiTop + 102), 0, 1.0F, "left", false, "georamaSemiBold", 22);
            Double var25 = (Double)((HashMap)skillsData.get(selectedSkill)).get("position");
            ModernGui.drawScaledStringCustomFont(var25.doubleValue() != 0.0D?String.format("%.0f", new Object[]{var25}) + "e":"NC", (float)(this.guiLeft + 180), (float)(this.guiTop + 115), 0, 1.0F, "left", false, "georamaRegular", 18);
            ClientEventHandler.STYLE.bindTexture("skills");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 130), (float)(this.guiTop + 135), 384, 430, 127, 82, 512.0F, 512.0F, false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("skills.top_3"), (float)(this.guiLeft + 138), (float)(this.guiTop + 141), 12895428, 1.0F, "left", false, "georamaRegular", 13);

            String var29;
            ResourceLocation var31;
            for(var24 = 0; var24 < 3; ++var24) {
               ClientEventHandler.STYLE.bindTexture("skills");
               if(mouseX >= this.guiLeft + 137 && mouseX <= this.guiLeft + 137 + 15 && mouseY >= this.guiTop + 153 + var24 * 21 && mouseY <= this.guiTop + 153 + var24 * 21 + 15) {
                  ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 137), (float)(this.guiTop + 153 + var24 * 21), 149, 252, 15, 15, 512.0F, 512.0F, false);
                  ModernGui.drawScaledStringCustomFont(var24 + 1 + "", (float)(this.guiLeft + 145), (float)(this.guiTop + 155 + var24 * 21), 7239406, 1.0F, "center", false, "georamaBold", 22);
                  tooltipToDraw = Arrays.asList(I18n.func_135053_a("skills.rewards.server." + (var24 + 1)).split("##"));
               } else {
                  ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 137), (float)(this.guiTop + 153 + var24 * 21), 129, 252, 15, 15, 512.0F, 512.0F, false);
                  ModernGui.drawScaledStringCustomFont(var24 + 1 + "", (float)(this.guiLeft + 145), (float)(this.guiTop + 155 + var24 * 21), 16777215, 1.0F, "center", false, "georamaBold", 22);
               }

               String var27 = ((List)skillsLeaderBoard.get(selectedSkill)).size() > var24?((String)((List)skillsLeaderBoard.get(selectedSkill)).get(var24)).split("#")[0]:"-";
               var29 = ((List)skillsLeaderBoard.get(selectedSkill)).size() > var24?((String)((List)skillsLeaderBoard.get(selectedSkill)).get(var24)).split("#")[1]:"0";
               playerScore = Double.valueOf(Double.parseDouble(var29) / 1000.0D);
               if(!cacheHeadPlayer.containsKey(var27)) {
                  try {
                     var31 = AbstractClientPlayer.field_110314_b;
                     var31 = AbstractClientPlayer.func_110311_f(var27);
                     AbstractClientPlayer.func_110304_a(var31, var27);
                     cacheHeadPlayer.put(var27, var31);
                  } catch (Exception var19) {
                     System.out.println(var19.getMessage());
                  }
               } else {
                  Minecraft.func_71410_x().field_71446_o.func_110577_a((ResourceLocation)cacheHeadPlayer.get(var27));
                  this.field_73882_e.func_110434_K().func_110577_a((ResourceLocation)cacheHeadPlayer.get(var27));
                  GUIUtils.drawScaledCustomSizeModalRect(this.guiLeft + 170, this.guiTop + 166 + var24 * 21, 8.0F, 16.0F, 8, -8, -12, -12, 64.0F, 64.0F);
               }

               ModernGui.drawScaledStringCustomFont(var27, (float)(this.guiLeft + 175), (float)(this.guiTop + 157 + var24 * 21), 12895428, 1.0F, "left", false, "georamaSemiBold", 16);
               ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[]{playerScore}) + "k", (float)(this.guiLeft + 250), (float)(this.guiTop + 157 + var24 * 21), 12895428, 1.0F, "right", false, "georamaRegular", 16);
            }

            ClientEventHandler.STYLE.bindTexture("skills");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 265), (float)(this.guiTop + 48), 224, 245, 181, 169, 512.0F, 512.0F, false);
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 437), (float)(this.guiTop + 56), 407, 245, 3, 154, 512.0F, 512.0F, false);
            this.scrollBar.draw(mouseX, mouseY);
            if(((List)skillsLeaderBoard.get(selectedSkill)).size() > 3) {
               GUIUtils.startGLScissor(this.guiLeft + 265, this.guiTop + 48, 172, 169);

               for(var24 = 3; var24 < ((List)skillsLeaderBoard.get(selectedSkill)).size(); ++var24) {
                  Float var28 = Float.valueOf((float)(this.guiTop + 53 + (var24 - 3) * 18) + this.getSlide());
                  ModernGui.drawScaledStringCustomFont(var24 + 1 + "", (float)(this.guiLeft + 276), (float)(var28.intValue() + 4), 16777215, 1.0F, "center", false, "georamaSemiBold", 18);
                  ClientEventHandler.STYLE.bindTexture("skills");
                  var29 = ((String)((List)skillsLeaderBoard.get(selectedSkill)).get(var24)).split("#")[0];
                  String var30 = ((String)((List)skillsLeaderBoard.get(selectedSkill)).get(var24)).split("#")[1];
                  if(!cacheHeadPlayer.containsKey(var29)) {
                     try {
                        var31 = AbstractClientPlayer.field_110314_b;
                        var31 = AbstractClientPlayer.func_110311_f(var29);
                        AbstractClientPlayer.func_110304_a(var31, var29);
                        cacheHeadPlayer.put(var29, var31);
                     } catch (Exception var18) {
                        System.out.println(var18.getMessage());
                     }
                  } else {
                     Minecraft.func_71410_x().field_71446_o.func_110577_a((ResourceLocation)cacheHeadPlayer.get(var29));
                     this.field_73882_e.func_110434_K().func_110577_a((ResourceLocation)cacheHeadPlayer.get(var29));
                     GUIUtils.drawScaledCustomSizeModalRect(this.guiLeft + 298, var28.intValue() + 14, 8.0F, 16.0F, 8, -8, -12, -12, 64.0F, 64.0F);
                  }

                  ModernGui.drawScaledStringCustomFont(var29, (float)(this.guiLeft + 305), (float)(var28.intValue() + 5), 12895428, 1.0F, "left", false, "georamaSemiBold", 16);
                  ModernGui.drawScaledStringCustomFont(var30, (float)(this.guiLeft + 422), (float)(var28.intValue() + 5), 12895428, 1.0F, "right", false, "georamaRegular", 16);
               }

               GUIUtils.endGLScissor();
            }
         }
      }

      if(!((List)tooltipToDraw).isEmpty()) {
         this.drawHoveringText((List)tooltipToDraw, mouseX, mouseY, this.field_73886_k);
      }

      super.func_73863_a(mouseX, mouseY, par3);
      GL11.glEnable(2896);
      RenderHelper.func_74519_b();
   }

   private float getSlide() {
      return ((List)skillsLeaderBoard.get(selectedSkill)).size() - 3 > 9?(float)(-(((List)skillsLeaderBoard.get(selectedSkill)).size() - 3 - 9) * 18) * this.scrollBar.getSliderValue():0.0F;
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0) {
         if(mouseX > this.guiLeft + 433 && mouseX < this.guiLeft + 433 + 18 && mouseY > this.guiTop + 8 && mouseY < this.guiTop + 8 + 18) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
         }

         if(!this.hoveredSkill.isEmpty()) {
            selectedSkill = this.hoveredSkill;
            this.hoveredSkill = "";
         }

         if(this.hoveredAction.equals("switch_leaderboard")) {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new SkillLeaderBoardDataPacket()));
         }

         if(this.hoveredAction.equals("switch_personal")) {
            displayMode = "personal";
         }
      }

      super.func_73864_a(mouseX, mouseY, mouseButton);
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
