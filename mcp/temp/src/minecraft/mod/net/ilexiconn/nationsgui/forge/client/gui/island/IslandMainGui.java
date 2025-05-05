package net.ilexiconn.nationsgui.forge.client.gui.island;

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
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandCreateGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandChangeFlySpeedPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandChangeGamemodePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandClearInventoryPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandMainDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandVotePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

@SideOnly(Side.CLIENT)
public class IslandMainGui extends GuiScreen {

   public static final List<GuiScreenTab> TABS = new ArrayList();
   protected int xSize = 289;
   protected int ySize = 248;
   private int guiLeft;
   private int guiTop;
   private RenderItem itemRenderer = new RenderItem();
   public static boolean loaded = false;
   public boolean changedIsGamemode = false;
   public boolean newIsGamemode = false;
   public boolean changedFlySpeed = false;
   public Double newFlySpeed = Double.valueOf(0.0D);
   private GuiScrollBarFaction scrollBarMembers;
   private GuiScrollBarFaction scrollBarVisitors;
   public static HashMap<String, Object> islandInfos;
   private DynamicTexture imageTexture;
   public static boolean isPremium = false;
   public static boolean isOp = false;
   public static String serverNumber = "0";
   List<String> biomes = new ArrayList();
   public static HashMap<String, Boolean> membersPerms = new HashMap();
   public static HashMap<String, Boolean> visitorsPerms = new HashMap();
   public static HashMap<String, Boolean> islandFlags = new HashMap();


   public IslandMainGui() {
      loaded = false;
      this.biomes.addAll(Arrays.asList(new String[]{"plaine", "marais", "desert", "neige", "jungle"}));
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandMainDataPacket()));
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - this.ySize) / 2;
      this.scrollBarMembers = new GuiScrollBarFaction((float)(this.guiLeft + 156), (float)(this.guiTop + 109), 84);
      this.scrollBarVisitors = new GuiScrollBarFaction((float)(this.guiLeft + 271), (float)(this.guiTop + 109), 84);
   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      this.func_73873_v_();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ClientEventHandler.STYLE.bindTexture("island_main");
      ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
      Object tooltipToDraw = new ArrayList();
      if(loaded && islandInfos.size() > 0) {
         ClientEventHandler.STYLE.bindTexture("island_main");

         for(int titleOffsetY = 0; titleOffsetY < TABS.size(); ++titleOffsetY) {
            GuiScreenTab descriptionWords = (GuiScreenTab)TABS.get(titleOffsetY);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            int line = getTabIndex((GuiScreenTab)TABS.get(titleOffsetY));
            if(this.getClass() == descriptionWords.getClassReferent()) {
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23), (float)(this.guiTop + 47 + titleOffsetY * 31), 23, 249, 29, 30, 512.0F, 512.0F, false);
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23 + 4), (float)(this.guiTop + 47 + titleOffsetY * 31 + 5), line * 20, 331, 20, 20, 512.0F, 512.0F, false);
            } else {
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23), (float)(this.guiTop + 47 + titleOffsetY * 31), 0, 249, 23, 30, 512.0F, 512.0F, false);
               GL11.glBlendFunc(770, 771);
               GL11.glEnable(3042);
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23 + 4), (float)(this.guiTop + 47 + titleOffsetY * 31 + 5), line * 20, 331, 20, 20, 512.0F, 512.0F, false);
               GL11.glDisable(3042);
               if(mouseX >= this.guiLeft - 23 && mouseX <= this.guiLeft - 23 + 29 && mouseY >= this.guiTop + 47 + titleOffsetY * 31 && mouseY <= this.guiTop + 47 + 30 + titleOffsetY * 31) {
                  tooltipToDraw = Arrays.asList(new String[]{I18n.func_135053_a("island.tab." + line)});
               }
            }
         }
      } else if(loaded && islandInfos.size() == 0) {
         Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
      }

      ClientEventHandler.STYLE.bindTexture("island_main");
      if(mouseX >= this.guiLeft + 278 && mouseX <= this.guiLeft + 278 + 9 && mouseY >= this.guiTop - 8 && mouseY <= this.guiTop - 8 + 10) {
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 278), (float)(this.guiTop - 8), 139, 259, 9, 10, 512.0F, 512.0F, false);
      } else {
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 278), (float)(this.guiTop - 8), 139, 249, 9, 10, 512.0F, 512.0F, false);
      }

      if(loaded && islandInfos.size() > 0) {
         if(this.imageTexture == null && !((String)islandInfos.get("image")).isEmpty()) {
            BufferedImage var13 = decodeToImage((String)islandInfos.get("image"));
            this.imageTexture = new DynamicTexture(var13);
         }

         GL11.glPushMatrix();
         Double var14 = Double.valueOf((double)(this.guiTop + 45) + (double)this.field_73886_k.func_78256_a((String)islandInfos.get("name")) * 1.5D);
         GL11.glTranslatef((float)(this.guiLeft + 14), (float)var14.intValue(), 0.0F);
         GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
         GL11.glTranslatef((float)(-(this.guiLeft + 14)), (float)(-var14.intValue()), 0.0F);
         this.drawScaledString((String)islandInfos.get("name"), this.guiLeft + 14, var14.intValue(), 16777215, 1.5F, false, false);
         GL11.glPopMatrix();
         ClientEventHandler.STYLE.bindTexture("island_main");
         if(loaded) {
            if(this.imageTexture != null) {
               GL11.glBindTexture(3553, this.imageTexture.func_110552_b());
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 58), (float)(this.guiTop + 27), 0.0F, 0.0F, 102, 102, 30, 30, 102.0F, 102.0F, false);
               ClientEventHandler.STYLE.bindTexture("island_main");
               GL11.glEnable(3042);
               GL11.glDisable(2929);
               GL11.glDepthMask(false);
               GL11.glBlendFunc(770, 771);
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               GL11.glDisable(3008);
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 57), (float)(this.guiTop + 26), 292, 41, 32, 32, 512.0F, 512.0F, false);
               GL11.glDisable(3042);
               GL11.glEnable(2929);
               GL11.glDepthMask(true);
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               GL11.glEnable(3008);
            }

            ClientEventHandler.STYLE.bindTexture("island_main");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 83), (float)(this.guiTop + 52), 292, 75, 10, 11, 512.0F, 512.0F, false);
            if(mouseX > this.guiLeft + 83 && mouseX < this.guiLeft + 83 + 10 && mouseY > this.guiTop + 52 && mouseY < this.guiTop + 52 + 11) {
               tooltipToDraw = Arrays.asList(new String[]{I18n.func_135053_a("island.list.tooltip.creation") + " " + (String)islandInfos.get("creationDate")});
            }

            this.drawScaledString((String)islandInfos.get("name"), this.guiLeft + 95, this.guiTop + 27, 11842740, 1.0F, false, false);
            String[] var15 = ((String)islandInfos.get("description")).replaceAll("\u00a7[0-9a-z]{1}", "").split(" ");
            String var16 = "";
            int lineNumber = 0;
            String[] biomeNumber = var15;
            int speed = var15.length;

            int offsetX;
            for(offsetX = 0; offsetX < speed; ++offsetX) {
               String offsetY = biomeNumber[offsetX];
               if(this.field_73886_k.func_78256_a(var16 + offsetY) <= 128) {
                  if(!var16.equals("")) {
                     var16 = var16 + " ";
                  }

                  var16 = var16 + offsetY;
               } else {
                  if(lineNumber == 0) {
                     var16 = "\u00a7o\"" + var16;
                  }

                  this.drawScaledString(var16, this.guiLeft + 158, this.guiTop + 44 + lineNumber * 10, 16777215, 1.0F, true, true);
                  ++lineNumber;
                  var16 = offsetY;
               }
            }

            if(lineNumber == 0) {
               var16 = "\u00a7o\"" + var16;
            }

            this.drawScaledString(var16 + "\"", this.guiLeft + 158, this.guiTop + 44 + lineNumber * 10, 16777215, 1.0F, true, true);
            int var17 = this.biomes.indexOf((String)islandInfos.get("biome"));
            ClientEventHandler.STYLE.bindTexture("island_main");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 236), (float)(this.guiTop + 24), 0 + var17 * 29, 284, 28, 38, 512.0F, 512.0F, false);
            this.drawScaledString(!((String)islandInfos.get("visit")).equals("0") && !((String)islandInfos.get("visit")).equals("1")?islandInfos.get("visit") + " " + I18n.func_135053_a("island.main.label.visits"):(String)islandInfos.get("visit") + " " + I18n.func_135053_a("island.main.label.visit"), this.guiLeft + 104, this.guiTop + 75, 16777215, 1.0F, true, false);
            if(((Boolean)islandInfos.get("currentPlayerVotedUp")).booleanValue() || mouseX >= this.guiLeft + 163 && mouseX <= this.guiLeft + 163 + 55 && mouseY >= this.guiTop + 70 && mouseY <= this.guiTop + 70 + 18) {
               ClientEventHandler.STYLE.bindTexture("island_main");
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 163), (float)(this.guiTop + 70), 291, 4, 55, 18, 512.0F, 512.0F, false);
            }

            this.drawScaledString((String)islandInfos.get("voteUp"), this.guiLeft + 191, this.guiTop + 75, 16777215, 1.0F, false, false);
            if(((Boolean)islandInfos.get("currentPlayerVotedDown")).booleanValue() || mouseX >= this.guiLeft + 221 && mouseX <= this.guiLeft + 221 + 55 && mouseY >= this.guiTop + 70 && mouseY <= this.guiTop + 70 + 18) {
               ClientEventHandler.STYLE.bindTexture("island_main");
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 221), (float)(this.guiTop + 70), 291, 22, 55, 18, 512.0F, 512.0F, false);
            }

            this.drawScaledString((String)islandInfos.get("voteDown"), this.guiLeft + 244, this.guiTop + 75, 16777215, 1.0F, false, false);
            this.drawScaledString(I18n.func_135053_a("island.main.members"), this.guiLeft + 48, this.guiTop + 97, 0, 1.0F, false, false);
            GUIUtils.startGLScissor(this.guiLeft + 49, this.guiTop + 107, 107, 88);

            Float var19;
            for(speed = 0; speed < ((ArrayList)islandInfos.get("members")).size(); ++speed) {
               offsetX = this.guiLeft + 49;
               var19 = Float.valueOf((float)(this.guiTop + 107 + speed * 21) + this.getSlideMembers());
               ClientEventHandler.STYLE.bindTexture("island_main");
               ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)var19.intValue(), 49, 107, 107, 21, 512.0F, 512.0F, false);
               this.drawScaledString((String)((ArrayList)islandInfos.get("members")).get(speed), offsetX + 4, var19.intValue() + 6, 16777215, 1.0F, false, false);
            }

            GUIUtils.endGLScissor();
            if(mouseX > this.guiLeft + 48 && mouseX < this.guiLeft + 48 + 113 && mouseY > this.guiTop + 106 && mouseY < this.guiTop + 106 + 90) {
               this.scrollBarMembers.draw(mouseX, mouseY);
            }

            this.drawScaledString(I18n.func_135053_a("island.main.visitors"), this.guiLeft + 164, this.guiTop + 97, 0, 1.0F, false, false);
            GUIUtils.startGLScissor(this.guiLeft + 164, this.guiTop + 107, 107, 88);

            for(speed = 0; speed < ((ArrayList)islandInfos.get("visitors")).size(); ++speed) {
               offsetX = this.guiLeft + 164;
               var19 = Float.valueOf((float)(this.guiTop + 107 + speed * 21) + this.getSlideVisitors());
               ClientEventHandler.STYLE.bindTexture("island_main");
               ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)var19.intValue(), 49, 107, 107, 21, 512.0F, 512.0F, false);
               this.drawScaledString((String)((ArrayList)islandInfos.get("visitors")).get(speed), offsetX + 4, var19.intValue() + 6, 16777215, 1.0F, false, false);
            }

            GUIUtils.endGLScissor();
            if(mouseX > this.guiLeft + 163 && mouseX < this.guiLeft + 163 + 113 && mouseY > this.guiTop + 106 && mouseY < this.guiTop + 106 + 90) {
               this.scrollBarVisitors.draw(mouseX, mouseY);
            }

            ClientEventHandler.STYLE.bindTexture("island_list");
            if(!this.canPlayerCreateIsland() || mouseX >= this.guiLeft + 170 && mouseX <= this.guiLeft + 170 + 103 && mouseY >= this.guiTop + 203 && mouseY <= this.guiTop + 203 + 27) {
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 170), (float)(this.guiTop + 203), 153, 319, 103, 27, 512.0F, 512.0F, false);
            }

            if(((Boolean)islandInfos.get("playerIsCreator")).booleanValue()) {
               this.drawScaledString(I18n.func_135053_a("island.main.create.button.own"), this.guiLeft + 228, this.guiTop + 213, 16777215, 1.0F, true, false);
            } else {
               this.drawScaledString(I18n.func_135053_a("island.main.create.button.other"), this.guiLeft + 228, this.guiTop + 213, 16777215, 1.0F, true, false);
            }

            if(((Boolean)islandInfos.get("playerIsCreator")).booleanValue()) {
               this.drawScaledString(I18n.func_135053_a("island.main.create.text.own"), this.guiLeft + 54, this.guiTop + 207, 16777215, 1.0F, false, false);
               this.drawScaledString(I18n.func_135053_a("island.main.create.text.own_2"), this.guiLeft + 54, this.guiTop + 217, 16777215, 1.0F, false, false);
            } else {
               this.drawScaledString(I18n.func_135053_a("island.main.create.text.other_1"), this.guiLeft + 54, this.guiTop + 207, 16777215, 1.0F, false, false);
               this.drawScaledString(I18n.func_135053_a("island.main.create.text.other_2"), this.guiLeft + 54, this.guiTop + 217, 16777215, 1.0F, false, false);
            }

            if(!this.canPlayerCreateIsland() && mouseX >= this.guiLeft + 170 && mouseX <= this.guiLeft + 170 + 103 && mouseY >= this.guiTop + 203 && mouseY <= this.guiTop + 203 + 27) {
               if(isPremium) {
                  this.drawHoveringText(Arrays.asList(new String[]{I18n.func_135053_a("island.create.disable.premium")}), mouseX, mouseY, this.field_73886_k);
               } else {
                  this.drawHoveringText(Arrays.asList(new String[]{I18n.func_135053_a("island.create.disable.non_premium_1"), I18n.func_135053_a("island.create.disable.non_premium_2"), I18n.func_135053_a("island.create.disable.non_premium_3")}), mouseX, mouseY, this.field_73886_k);
               }
            }

            ClientEventHandler.STYLE.bindTexture("island_main");
            if(((ArrayList)islandInfos.get("playerPermissions")).contains("gamemode") && (mouseX < this.guiLeft + 291 || mouseX > this.guiLeft + 291 + 60 || mouseY < this.guiTop + 4 || mouseY > this.guiTop + 4 + 15)) {
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 291), (float)(this.guiTop + 4), 352, 117, 60, 15, 512.0F, 512.0F, false);
            } else {
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 291), (float)(this.guiTop + 4), 352, 132, 60, 15, 512.0F, 512.0F, false);
               if(!((ArrayList)islandInfos.get("playerPermissions")).contains("gamemode") && mouseX >= this.guiLeft + 291 && mouseX <= this.guiLeft + 291 + 60 && mouseY >= this.guiTop + 4 && mouseY <= this.guiTop + 4 + 15) {
                  tooltipToDraw = Arrays.asList(new String[]{I18n.func_135053_a("island.global.permission_required")});
               }
            }

            if((!((Boolean)islandInfos.get("isCreative")).booleanValue() || this.changedIsGamemode) && (!this.changedIsGamemode || !this.newIsGamemode)) {
               this.drawScaledString(I18n.func_135053_a("island.main.creative"), this.guiLeft + 321, this.guiTop + 8, 0, 1.0F, true, false);
            } else {
               this.drawScaledString(I18n.func_135053_a("island.main.survival"), this.guiLeft + 321, this.guiTop + 8, 0, 1.0F, true, false);
            }

            ClientEventHandler.STYLE.bindTexture("island_main");
            if((((Boolean)islandInfos.get("isCreative")).booleanValue() || this.changedIsGamemode) && (!this.changedIsGamemode || this.newIsGamemode) && (mouseX < this.guiLeft + 291 || mouseX > this.guiLeft + 291 + 60 || mouseY < this.guiTop + 23 || mouseY > this.guiTop + 23 + 15)) {
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 291), (float)(this.guiTop + 23), 352, 117, 60, 15, 512.0F, 512.0F, false);
            } else {
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 291), (float)(this.guiTop + 23), 352, 132, 60, 15, 512.0F, 512.0F, false);
               if((!((Boolean)islandInfos.get("isCreative")).booleanValue() && !this.changedIsGamemode || this.changedIsGamemode && !this.newIsGamemode) && mouseX >= this.guiLeft + 291 && mouseX <= this.guiLeft + 291 + 60 && mouseY >= this.guiTop + 23 && mouseY <= this.guiTop + 23 + 15) {
                  tooltipToDraw = Arrays.asList(new String[]{I18n.func_135053_a("island.main.flyspeed.blocked")});
               }
            }

            Double var18 = this.changedFlySpeed?this.newFlySpeed:(Double)islandInfos.get("flySpeed");
            var18 = Double.valueOf(var18.doubleValue() * 10.0D);
            this.drawScaledString(I18n.func_135053_a("island.main.flyspeed") + " " + var18.intValue(), this.guiLeft + 321, this.guiTop + 27, 0, 1.0F, true, false);
            ClientEventHandler.STYLE.bindTexture("island_main");
            if(mouseX >= this.guiLeft + 291 && mouseX <= this.guiLeft + 291 + 60 && mouseY >= this.guiTop + 42 && mouseY <= this.guiTop + 42 + 15) {
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 291), (float)(this.guiTop + 42), 352, 132, 60, 15, 512.0F, 512.0F, false);
            } else {
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 291), (float)(this.guiTop + 42), 352, 117, 60, 15, 512.0F, 512.0F, false);
            }

            this.drawScaledString(I18n.func_135053_a("island.main.clearinventory"), this.guiLeft + 321, this.guiTop + 46, 0, 1.0F, true, false);
            if(!((List)tooltipToDraw).isEmpty()) {
               this.drawHoveringText((List)tooltipToDraw, mouseX, mouseY, this.field_73886_k);
            }
         }
      }

      super.func_73863_a(mouseX, mouseY, par3);
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
         if(loaded && islandInfos.size() > 0) {
            for(int i = 0; i < TABS.size(); ++i) {
               GuiScreenTab type = (GuiScreenTab)TABS.get(i);
               if(mouseX >= this.guiLeft - 20 && mouseX <= this.guiLeft + 3 && mouseY >= this.guiTop + 47 + i * 31 && mouseY <= this.guiTop + 47 + 30 + i * 31) {
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
         }

         if(mouseX > this.guiLeft + 278 && mouseX < this.guiLeft + 278 + 9 && mouseY > this.guiTop - 8 && mouseY < this.guiTop - 8 + 10) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
         } else if(loaded && islandInfos.size() > 0 && mouseX >= this.guiLeft + 163 && mouseX <= this.guiLeft + 163 + 55 && mouseY >= this.guiTop + 70 && mouseY <= this.guiTop + 70 + 18) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandVotePacket((String)islandInfos.get("id"), "up")));
         } else if(loaded && islandInfos.size() > 0 && mouseX >= this.guiLeft + 221 && mouseX <= this.guiLeft + 221 + 55 && mouseY >= this.guiTop + 70 && mouseY <= this.guiTop + 70 + 18) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandVotePacket((String)islandInfos.get("id"), "down")));
         } else if(loaded && islandInfos.size() > 0 && this.canPlayerCreateIsland() && mouseX >= this.guiLeft + 170 && mouseX <= this.guiLeft + 170 + 103 && mouseY >= this.guiTop + 203 && mouseY <= this.guiTop + 203 + 27) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            Minecraft.func_71410_x().func_71373_a(new IslandCreateGui(isPremium, serverNumber));
         } else if(loaded && islandInfos.size() > 0 && ((ArrayList)islandInfos.get("playerPermissions")).contains("gamemode") && mouseX >= this.guiLeft + 291 && mouseX <= this.guiLeft + 291 + 60 && mouseY >= this.guiTop + 4 && mouseY <= this.guiTop + 4 + 15) {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandChangeGamemodePacket()));
            if(!this.changedIsGamemode) {
               this.newIsGamemode = !((Boolean)islandInfos.get("isCreative")).booleanValue();
            } else {
               this.newIsGamemode = !this.newIsGamemode;
            }

            this.changedIsGamemode = true;
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
         } else if(loaded && islandInfos.size() > 0 && (((Boolean)islandInfos.get("isCreative")).booleanValue() && !this.changedIsGamemode || this.changedIsGamemode && this.newIsGamemode) && mouseX >= this.guiLeft + 291 && mouseX <= this.guiLeft + 291 + 60 && mouseY >= this.guiTop + 4 && mouseY <= this.guiTop + 23 + 15) {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandChangeFlySpeedPacket()));
            if(!this.changedFlySpeed) {
               this.newFlySpeed = Double.valueOf(((Double)islandInfos.get("flySpeed")).doubleValue() + 0.1D <= 0.5D?((Double)islandInfos.get("flySpeed")).doubleValue() + 0.1D:0.1D);
            } else {
               this.newFlySpeed = Double.valueOf(this.newFlySpeed.doubleValue() + 0.1D <= 0.5D?this.newFlySpeed.doubleValue() + 0.1D:0.1D);
            }

            this.changedFlySpeed = true;
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
         } else if(loaded && islandInfos.size() > 0 && mouseX >= this.guiLeft + 291 && mouseX <= this.guiLeft + 291 + 60 && mouseY >= this.guiTop + 4 && mouseY <= this.guiTop + 42 + 15) {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandClearInventoryPacket()));
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
         }
      }

      super.func_73864_a(mouseX, mouseY, mouseButton);
   }

   private float getSlideMembers() {
      return ((ArrayList)islandInfos.get("members")).size() > 4?(float)(-(((ArrayList)islandInfos.get("members")).size() - 4) * 20) * this.scrollBarMembers.getSliderValue():0.0F;
   }

   private float getSlideVisitors() {
      return ((ArrayList)islandInfos.get("visitors")).size() > 4?(float)(-(((ArrayList)islandInfos.get("visitors")).size() - 4) * 20) * this.scrollBarVisitors.getSliderValue():0.0F;
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

   public static int getTabIndex(GuiScreenTab inventoryTab) {
      String classReferent = inventoryTab.getClassReferent().toString();
      return classReferent.contains("MainGui")?0:(classReferent.contains("MembersGui")?1:(classReferent.contains("PermsGui")?2:(classReferent.contains("SettingsGui")?3:(classReferent.contains("PropertiesGui")?4:(classReferent.contains("BackupGui")?5:0)))));
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

   public boolean canPlayerCreateIsland() {
      return (isPremium || ((Double)islandInfos.get("currentPlayerIslandCount")).doubleValue() < 1.0D) && (!isPremium || ((Double)islandInfos.get("currentPlayerIslandCount")).doubleValue() < 5.0D);
   }

}
