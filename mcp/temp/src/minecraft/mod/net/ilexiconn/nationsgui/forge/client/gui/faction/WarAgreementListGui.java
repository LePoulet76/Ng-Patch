package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.faction.WarAgreementCreateGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.WarGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionAgreementListPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionEnemyAgreementCancelPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionEnemyAgreementUpdateStatusPacket;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class WarAgreementListGui extends GuiScreen {

   public static ArrayList<HashMap<String, Object>> agreements = new ArrayList();
   public static boolean canCreateAgreement = false;
   public static String playerFactionId = "";
   public HashMap<String, Object> hoveredAgreement = null;
   public HashMap<String, Object> selectedAgreement = null;
   protected int xSize = 226;
   protected int ySize = 248;
   private int guiLeft;
   private int guiTop;
   private RenderItem itemRenderer = new RenderItem();
   public static boolean loaded = false;
   private GuiScrollBarFaction scrollBar;
   boolean statusExpanded = false;
   private String selectedStatus = "";
   private String hoveredStatus = "";
   private String hoveredAction = "";
   private EntityPlayer player;
   public static int warId;
   private String faction1;
   private String faction2;
   private List<String> availableStatus = Arrays.asList(new String[]{"all", "waiting", "active", "refused", "expired"});


   public WarAgreementListGui(EntityPlayer player, int warId, String faction1, String faction2) {
      this.player = player;
      warId = warId;
      this.faction1 = faction1;
      this.faction2 = faction2;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - this.ySize) / 2;
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionAgreementListPacket(warId)));
      this.scrollBar = new GuiScrollBarFaction((float)(this.guiLeft + 211), (float)(this.guiTop + 42), 173);
      this.selectedStatus = (String)this.availableStatus.get(0);
   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      this.hoveredStatus = "";
      this.hoveredAgreement = null;
      this.hoveredAction = "";
      this.func_73873_v_();
      List tooltipToDraw = null;
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ClientEventHandler.STYLE.bindTexture("faction_war_agreement_list");
      ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
      GL11.glPushMatrix();
      GL11.glTranslatef((float)(this.guiLeft + 12), (float)(this.guiTop + 190), 0.0F);
      GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
      GL11.glTranslatef((float)(-(this.guiLeft + 14)), (float)(-(this.guiTop + 190)), 0.0F);
      this.drawScaledString(I18n.func_135053_a("faction.enemy.agreement.title"), this.guiLeft + 12, this.guiTop + 190, 16777215, 1.5F, false, false);
      GL11.glPopMatrix();
      ClientEventHandler.STYLE.bindTexture("faction_war_agreement_list");
      if(mouseX >= this.guiLeft + 214 && mouseX <= this.guiLeft + 214 + 9 && mouseY >= this.guiTop - 8 && mouseY <= this.guiTop - 8 + 10) {
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 214), (float)(this.guiTop - 8), 46, 248, 9, 10, 512.0F, 512.0F, false);
      } else {
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 214), (float)(this.guiTop - 8), 46, 258, 9, 10, 512.0F, 512.0F, false);
      }

      if(loaded) {
         if(!this.selectedStatus.isEmpty()) {
            this.drawScaledString(I18n.func_135053_a("faction.enemy.agreement.status." + this.selectedStatus), this.guiLeft + 49, this.guiTop + 23, 16777215, 1.0F, false, false);
         }

         int i;
         int index;
         if(this.selectedAgreement == null) {
            GUIUtils.startGLScissor(this.guiLeft + 47, this.guiTop + 40, 164, 177);
            i = 0;
            Iterator dateFormatDay = agreements.iterator();

            while(dateFormatDay.hasNext()) {
               HashMap infoX = (HashMap)dateFormatDay.next();
               if(this.selectedStatus.equals("all") || ((String)infoX.get("status")).startsWith(this.selectedStatus)) {
                  index = this.guiLeft + 47;
                  Float offsetY = Float.valueOf((float)(this.guiTop + 40 + i) + this.getSlideAgreements());
                  ClientEventHandler.STYLE.bindTexture("faction_war_agreement_list");
                  ModernGui.drawModalRectWithCustomSizedTexture((float)index, offsetY.floatValue(), 47, 40, 164, 35, 512.0F, 512.0F, false);
                  ModernGui.drawModalRectWithCustomSizedTexture((float)(index + 4), offsetY.floatValue() + 4.0F, 37, 275, 24, 17, 512.0F, 512.0F, false);
                  ModernGui.drawModalRectWithCustomSizedTexture((float)(index + 10), offsetY.floatValue() + 7.0F, infoX.get("type").equals("no_missile")?100:(infoX.get("type").equals("peace")?112:124), 251, 12, 12, 512.0F, 512.0F, false);
                  ModernGui.drawModalRectWithCustomSizedTexture((float)(index + 4), offsetY.floatValue() + 21.0F, 64, infoX.get("status").equals("refused")?275:(infoX.get("status").equals("waiting")?284:(infoX.get("status").equals("active")?293:302)), 24, 8, 512.0F, 512.0F, false);
                  this.drawScaledString(I18n.func_135053_a("faction.enemy.agreement.type." + infoX.get("type")), index + 35, offsetY.intValue() + 8, 16777215, 1.0F, false, false);
                  Date date = new Date(((Double)infoX.get("creationTime")).longValue());
                  SimpleDateFormat dateFormatDay1 = new SimpleDateFormat("dd/MM");
                  this.drawScaledString("\u00a77" + I18n.func_135053_a("faction.enemy.agreement.send_date") + " " + dateFormatDay1.format(date), index + 35, offsetY.intValue() + 18, 16777215, 1.0F, false, false);
                  ClientEventHandler.STYLE.bindTexture("faction_war_agreement_list");
                  ModernGui.drawModalRectWithCustomSizedTexture((float)(index + 128), offsetY.floatValue() + 9.0F, 0, 272, 32, 15, 512.0F, 512.0F, false);
                  if(!this.statusExpanded && mouseX >= index + 128 && mouseX <= index + 128 + 32 && (float)mouseY >= offsetY.floatValue() + 9.0F && (float)mouseY <= offsetY.floatValue() + 9.0F + 15.0F) {
                     ModernGui.drawModalRectWithCustomSizedTexture((float)(index + 128), offsetY.floatValue() + 9.0F, 0, 287, 32, 15, 512.0F, 512.0F, false);
                     this.hoveredAgreement = infoX;
                  }

                  this.drawScaledString(I18n.func_135053_a("faction.enemy.agreement.see"), index + 145, offsetY.intValue() + 13, 0, 1.0F, true, false);
                  i += 35;
               }
            }

            GUIUtils.endGLScissor();
            if(!this.statusExpanded) {
               this.scrollBar.draw(mouseX, mouseY);
            }

            if(canCreateAgreement) {
               ClientEventHandler.STYLE.bindTexture("faction_war_agreement_list");
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 46), (float)(this.guiTop + 220), 0, 427, 170, 15, 512.0F, 512.0F, false);
               if(mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 170 && mouseY >= this.guiTop + 220 && mouseY <= this.guiTop + 220 + 15) {
                  ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 46), (float)(this.guiTop + 220), 0, 442, 170, 15, 512.0F, 512.0F, false);
                  this.hoveredAction = "create";
               }

               this.drawScaledString(I18n.func_135053_a("faction.enemy.agreement.create"), this.guiLeft + 131, this.guiTop + 224, 16777215, 1.0F, true, false);
            }
         } else {
            ModernGui.drawNGBlackSquare(this.guiLeft + 46, this.guiTop + 39, 170, 196);
            ClientEventHandler.STYLE.bindTexture("faction_war_agreement_list");
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            ClientEventHandler.STYLE.bindTexture("faction_war_agreement_list");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 47), (float)(this.guiTop + 40), 47, 40, 164, 35, 512.0F, 512.0F, false);
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 51), (float)(this.guiTop + 44), 37, 275, 24, 17, 512.0F, 512.0F, false);
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 57), (float)(this.guiTop + 47), this.selectedAgreement.get("type").equals("no_missile")?100:(this.selectedAgreement.get("type").equals("peace")?112:124), 251, 12, 12, 512.0F, 512.0F, false);
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 51), (float)(this.guiTop + 61), 64, this.selectedAgreement.get("status").equals("refused")?275:(this.selectedAgreement.get("status").equals("waiting")?284:(this.selectedAgreement.get("status").equals("active")?293:302)), 24, 8, 512.0F, 512.0F, false);
            this.drawScaledString(I18n.func_135053_a("faction.enemy.agreement.type." + this.selectedAgreement.get("type")), this.guiLeft + 82, this.guiTop + 48, 16777215, 1.0F, false, false);
            Date var13 = new Date(((Double)this.selectedAgreement.get("creationTime")).longValue());
            SimpleDateFormat var14 = new SimpleDateFormat("dd/MM");
            this.drawScaledString("\u00a77" + I18n.func_135053_a("faction.enemy.agreement.send_date") + " " + var14.format(var13), this.guiLeft + 82, this.guiTop + 58, 16777215, 1.0F, false, false);
            ClientEventHandler.STYLE.bindTexture("faction_war_agreement_list");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 175), (float)(this.guiTop + 49), 0, 272, 32, 15, 512.0F, 512.0F, false);
            if(!this.statusExpanded && mouseX >= this.guiLeft + 175 && mouseX <= this.guiLeft + 175 + 32 && mouseY >= this.guiTop + 49 && mouseY <= this.guiTop + 49 + 15) {
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 175), (float)(this.guiTop + 49), 0, 287, 32, 15, 512.0F, 512.0F, false);
               this.hoveredAgreement = this.selectedAgreement;
            }

            this.drawScaledString(I18n.func_135053_a("faction.enemy.agreement.back"), this.guiLeft + 192, this.guiTop + 53, 0, 1.0F, true, false);
            ClientEventHandler.STYLE.bindTexture("faction_war_agreement_list");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 54), (float)(this.guiTop + 84), 95, 251, 3, 3, 512.0F, 512.0F, false);
            this.drawScaledString("\u00a77" + I18n.func_135053_a("faction.enemy.agreement.status") + " \u00a77> " + I18n.func_135053_a("faction.enemy.agreement.status.short." + this.selectedAgreement.get("status")), this.guiLeft + 64, this.guiTop + 82, 16777215, 1.0F, false, false);
            ClientEventHandler.STYLE.bindTexture("faction_war_agreement_list");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 51), (float)(this.guiTop + 96), 61, 251, 9, 11, 512.0F, 512.0F, false);
            if(this.selectedAgreement.get("status").equals("active") && this.selectedAgreement.containsKey("signatureTime")) {
               Date var15 = new Date(((Double)this.selectedAgreement.get("value")).longValue() * 86400000L + ((Double)this.selectedAgreement.get("signatureTime")).longValue());
               SimpleDateFormat var17 = new SimpleDateFormat("dd/MM/yyy HH:mm");
               this.drawScaledString("\u00a77" + I18n.func_135053_a("faction.enemy.agreement.end") + " \u00a77> \u00a7e" + var17.format(var15), this.guiLeft + 64, this.guiTop + 99, 16777215, 1.0F, false, false);
            } else {
               this.drawScaledString("\u00a77" + I18n.func_135053_a("faction.enemy.agreement.duration") + " \u00a77> \u00a7e" + ((Double)this.selectedAgreement.get("value")).intValue() + " " + I18n.func_135053_a("faction.enemy.rewards.valueType.dayLong"), this.guiLeft + 64, this.guiTop + 99, 16777215, 1.0F, false, false);
            }

            ClientEventHandler.STYLE.bindTexture("faction_war_agreement_list");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 51), (float)(this.guiTop + 114), 61, 263, 10, 10, 512.0F, 512.0F, false);
            this.drawScaledString("\u00a77" + I18n.func_135053_a("faction.enemy.agreement.conditions"), this.guiLeft + 64, this.guiTop + 116, 16777215, 1.0F, false, false);
            int var16 = this.guiLeft + 64 + this.field_73886_k.func_78256_a(I18n.func_135053_a("faction.enemy.agreement.conditions")) + 3;
            ClientEventHandler.STYLE.bindTexture("faction_war_agreement_list");
            ModernGui.drawModalRectWithCustomSizedTexture((float)var16, (float)(this.guiTop + 114), 71, 251, 10, 11, 512.0F, 512.0F, false);
            if(mouseX >= var16 && mouseX <= var16 + 10 && mouseY >= this.guiTop + 114 && mouseY <= this.guiTop + 114 + 11) {
               tooltipToDraw = Arrays.asList(I18n.func_135053_a("faction.enemy.agreement.conditions.tooltip").split("##"));
            }

            if(this.selectedAgreement.get("conditions") != null && !((String)this.selectedAgreement.get("conditions")).isEmpty()) {
               index = 0;
               String[] var18 = ((String)this.selectedAgreement.get("conditions")).split(",");
               int var19 = var18.length;

               for(int var20 = 0; var20 < var19; ++var20) {
                  String condition = var18[var20];
                  ClientProxy.loadCountryFlag(condition.split("#")[2]);
                  if(ClientProxy.flagsTexture.containsKey(condition.split("#")[2])) {
                     this.drawScaledString("\u00a77>", this.guiLeft + 53, this.guiTop + 131 + 12 * index, 16777215, 1.0F, false, false);
                     GL11.glBindTexture(3553, ((DynamicTexture)ClientProxy.flagsTexture.get(condition.split("#")[2])).func_110552_b());
                     ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 61), (float)(this.guiTop + 129 + 12 * index), 0.0F, 0.0F, 156, 78, 18, 10, 156.0F, 78.0F, false);
                     this.drawScaledString(condition.split("#")[1] + " " + I18n.func_135053_a("faction.enemy.agreement.conditions." + condition.split("#")[0]), this.guiLeft + 83, this.guiTop + 131 + 12 * index, 16777215, 1.0F, false, false);
                  }

                  ++index;
               }
            } else {
               this.drawScaledString("\u00a7c\u2716 " + I18n.func_135053_a("faction.enemy.none"), this.guiLeft + 53, this.guiTop + 129, 16777215, 1.0F, false, false);
            }

            if(canCreateAgreement && this.selectedAgreement.get("status").equals("waiting") && (playerFactionId.equals(this.selectedAgreement.get("factionATT")) || playerFactionId.equals(this.selectedAgreement.get("factionDEF"))) && !this.selectedAgreement.get("factionSender").equals(playerFactionId)) {
               ClientEventHandler.STYLE.bindTexture("faction_war_agreement_list");
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 52), (float)(this.guiTop + 217), 360, 208, 73, 15, 512.0F, 512.0F, false);
               if(mouseX >= this.guiLeft + 52 && mouseX <= this.guiLeft + 52 + 73 && mouseY >= this.guiTop + 217 && mouseY <= this.guiTop + 217 + 15) {
                  ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 52), (float)(this.guiTop + 217), 360, 223, 73, 15, 512.0F, 512.0F, false);
                  this.hoveredAction = "accept";
               }

               this.drawScaledString(I18n.func_135053_a("faction.enemy.agreement.accept"), this.guiLeft + 88, this.guiTop + 221, 16777215, 1.0F, true, false);
               ClientEventHandler.STYLE.bindTexture("faction_war_agreement_list");
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 137), (float)(this.guiTop + 217), 439, 208, 73, 15, 512.0F, 512.0F, false);
               if(mouseX >= this.guiLeft + 137 && mouseX <= this.guiLeft + 137 + 73 && mouseY >= this.guiTop + 217 && mouseY <= this.guiTop + 217 + 15) {
                  ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 137), (float)(this.guiTop + 217), 439, 223, 73, 15, 512.0F, 512.0F, false);
                  this.hoveredAction = "refuse";
               }

               this.drawScaledString(I18n.func_135053_a("faction.enemy.agreement.refuse"), this.guiLeft + 173, this.guiTop + 221, 16777215, 1.0F, true, false);
            } else if(canCreateAgreement && this.selectedAgreement.get("status").equals("waiting") && (playerFactionId.equals(this.selectedAgreement.get("factionATT")) || playerFactionId.equals(this.selectedAgreement.get("factionDEF"))) && this.selectedAgreement.get("factionSender").equals(playerFactionId)) {
               ClientEventHandler.STYLE.bindTexture("faction_war_agreement_list");
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 52), (float)(this.guiTop + 217), 0, 457, 158, 15, 512.0F, 512.0F, false);
               if(mouseX >= this.guiLeft + 52 && mouseX <= this.guiLeft + 52 + 158 && mouseY >= this.guiTop + 217 && mouseY <= this.guiTop + 217 + 15) {
                  ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 52), (float)(this.guiTop + 217), 0, 472, 158, 15, 512.0F, 512.0F, false);
                  this.hoveredAction = "cancel";
               }

               this.drawScaledString(I18n.func_135053_a("faction.enemy.agreement.cancel"), this.guiLeft + 131, this.guiTop + 221, 16777215, 1.0F, true, false);
            }
         }

         if(this.statusExpanded) {
            for(i = 0; i < this.availableStatus.size(); ++i) {
               ClientEventHandler.STYLE.bindTexture("faction_war_agreement_list");
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 46), (float)(this.guiTop + 37 + i * 19), 0, 312, 170, 19, 512.0F, 512.0F, false);
               this.drawScaledString(I18n.func_135053_a("faction.enemy.agreement.status." + (String)this.availableStatus.get(i)), this.guiLeft + 49, this.guiTop + 37 + i * 19 + 5, 16777215, 1.0F, false, false);
               if(mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 170 && mouseY >= this.guiTop + 37 + i * 19 && mouseY <= this.guiTop + 37 + i * 19 + 19) {
                  this.hoveredStatus = (String)this.availableStatus.get(i);
               }
            }
         }
      }

      if(tooltipToDraw != null && !tooltipToDraw.isEmpty()) {
         this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
      }

      GL11.glEnable(2896);
      RenderHelper.func_74519_b();
   }

   private float getSlideAgreements() {
      int agreementCount = 0;
      Iterator var2 = agreements.iterator();

      while(var2.hasNext()) {
         HashMap agreement = (HashMap)var2.next();
         if(this.selectedStatus.equals("all") || ((String)agreement.get("status")).startsWith(this.selectedStatus)) {
            ++agreementCount;
         }
      }

      return agreementCount > 5?(float)(-(agreementCount - 5) * 35) * this.scrollBar.getSliderValue():0.0F;
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0) {
         if(mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 170 && mouseY >= this.guiTop + 17 && mouseY <= this.guiTop + 17 + 20) {
            this.statusExpanded = !this.statusExpanded;
         }

         if(mouseX > this.guiLeft + 214 && mouseX < this.guiLeft + 214 + 9 && mouseY > this.guiTop - 8 && mouseY < this.guiTop - 8 + 10) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            this.field_73882_e.func_71373_a(new WarGUI());
         }

         if(!this.hoveredStatus.isEmpty()) {
            this.selectedAgreement = null;
            this.selectedStatus = this.hoveredStatus;
            this.hoveredStatus = "";
            this.statusExpanded = false;
            this.scrollBar.reset();
         }

         if(this.hoveredAgreement != null) {
            if(this.selectedAgreement != this.hoveredAgreement) {
               this.selectedAgreement = this.hoveredAgreement;
            } else {
               this.selectedAgreement = null;
            }

            this.hoveredAgreement = null;
         }

         if(canCreateAgreement && this.hoveredAction.equals("create")) {
            this.field_73882_e.func_71373_a(new WarAgreementCreateGui(this.player, warId, this.faction1, this.faction2));
         }

         if(canCreateAgreement && this.hoveredAction.equals("cancel") && this.selectedAgreement != null) {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionEnemyAgreementCancelPacket(Integer.valueOf(((Double)this.selectedAgreement.get("id")).intValue()))));
            this.selectedStatus = (String)this.availableStatus.get(0);
            this.scrollBar.reset();
            this.selectedAgreement = null;
         }

         if(canCreateAgreement && this.selectedAgreement != null && (this.hoveredAction.equals("accept") || this.hoveredAction.equals("refuse"))) {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionEnemyAgreementUpdateStatusPacket(Integer.valueOf(((Double)this.selectedAgreement.get("id")).intValue()), this.hoveredAction.equals("accept")?"active":"refused")));
            this.selectedStatus = (String)this.availableStatus.get(0);
            this.scrollBar.reset();
            this.selectedAgreement = null;
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
