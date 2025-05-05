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
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandMainGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandSavePermsPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

@SideOnly(Side.CLIENT)
public class IslandPermsGui extends GuiScreen {

   protected int xSize = 260;
   protected int ySize = 248;
   private int guiLeft;
   private int guiTop;
   private RenderItem itemRenderer = new RenderItem();
   private GuiScrollBarFaction scrollBar;
   public boolean helpOpened = false;
   public int helpSectionOffsetX = 0;
   public String hoveredPerm = "";
   public String hoveredRole = "";
   public boolean hoveredStatus = false;
   public static HashMap<String, HashMap<String, Boolean>> editedPerms = new HashMap();


   public void func_73866_w_() {
      super.func_73866_w_();
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - this.ySize) / 2;
      this.scrollBar = new GuiScrollBarFaction((float)(this.guiLeft + 243), (float)(this.guiTop + 37), 164);
   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      this.func_73873_v_();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ClientEventHandler.STYLE.bindTexture("island_perms");
      Object tooltipToDraw = new ArrayList();
      if(!this.helpOpened) {
         this.helpSectionOffsetX = Math.max(this.helpSectionOffsetX - 1, 0);
      } else {
         this.helpSectionOffsetX = Math.min(this.helpSectionOffsetX + 1, 107);
      }

      ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 260 + this.helpSectionOffsetX), (float)(this.guiTop + 171), 0, 271, 23, 45, 512.0F, 512.0F, false);
      GL11.glPushMatrix();
      GL11.glTranslatef((float)(this.guiLeft + 268 + this.helpSectionOffsetX), (float)(this.guiTop + 209), 0.0F);
      GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
      GL11.glTranslatef((float)(-(this.guiLeft + 268 + this.helpSectionOffsetX)), (float)(-(this.guiTop + 209)), 0.0F);
      this.drawScaledString(I18n.func_135053_a("island.list.help.title"), this.guiLeft + 268 + this.helpSectionOffsetX, this.guiTop + 209, 0, 1.0F, false, false);
      GL11.glPopMatrix();
      ClientEventHandler.STYLE.bindTexture("island_perms");
      ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 260 - 107 + this.helpSectionOffsetX), (float)(this.guiTop + 8), 405, 0, 107, 232, 512.0F, 512.0F, false);
      this.drawScaledString(I18n.func_135053_a("island.perms.help.title"), this.guiLeft + 312 - 107 + this.helpSectionOffsetX, this.guiTop + 20, 0, 1.3F, true, false);
      this.drawScaledString(I18n.func_135053_a("island.perms.help.text_1"), this.guiLeft + 312 - 107 + this.helpSectionOffsetX, this.guiTop + 50, 0, 1.0F, true, false);
      this.drawScaledString(I18n.func_135053_a("island.perms.help.text_2"), this.guiLeft + 312 - 107 + this.helpSectionOffsetX, this.guiTop + 60, 0, 1.0F, true, false);
      this.drawScaledString(I18n.func_135053_a("island.perms.help.text_3"), this.guiLeft + 312 - 107 + this.helpSectionOffsetX, this.guiTop + 70, 0, 1.0F, true, false);
      this.drawScaledString(I18n.func_135053_a("island.perms.help.text_4"), this.guiLeft + 312 - 107 + this.helpSectionOffsetX, this.guiTop + 80, 0, 1.0F, true, false);
      this.drawScaledString(I18n.func_135053_a("island.perms.help.text_5"), this.guiLeft + 312 - 107 + this.helpSectionOffsetX, this.guiTop + 95, 0, 1.0F, true, false);
      this.drawScaledString(I18n.func_135053_a("island.perms.help.text_6"), this.guiLeft + 312 - 107 + this.helpSectionOffsetX, this.guiTop + 105, 0, 1.0F, true, false);
      this.drawScaledString(I18n.func_135053_a("island.perms.help.text_7"), this.guiLeft + 312 - 107 + this.helpSectionOffsetX, this.guiTop + 115, 0, 1.0F, true, false);
      this.drawScaledString(I18n.func_135053_a("island.perms.help.text_8"), this.guiLeft + 312 - 107 + this.helpSectionOffsetX, this.guiTop + 125, 0, 1.0F, true, false);
      this.drawScaledString(I18n.func_135053_a("island.perms.help.text_9"), this.guiLeft + 312 - 107 + this.helpSectionOffsetX, this.guiTop + 135, 0, 1.0F, true, false);
      ClientEventHandler.STYLE.bindTexture("island_perms");
      ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
      ClientEventHandler.STYLE.bindTexture("island_main");

      for(int titleOffsetY = 0; titleOffsetY < IslandMainGui.TABS.size(); ++titleOffsetY) {
         GuiScreenTab l = (GuiScreenTab)IslandMainGui.TABS.get(titleOffsetY);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         int perm = IslandMainGui.getTabIndex((GuiScreenTab)IslandMainGui.TABS.get(titleOffsetY));
         if(this.getClass() == l.getClassReferent()) {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23), (float)(this.guiTop + 47 + titleOffsetY * 31), 23, 249, 29, 30, 512.0F, 512.0F, false);
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23 + 4), (float)(this.guiTop + 47 + titleOffsetY * 31 + 5), perm * 20, 331, 20, 20, 512.0F, 512.0F, false);
         } else {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23), (float)(this.guiTop + 47 + titleOffsetY * 31), 0, 249, 23, 30, 512.0F, 512.0F, false);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23 + 4), (float)(this.guiTop + 47 + titleOffsetY * 31 + 5), perm * 20, 331, 20, 20, 512.0F, 512.0F, false);
            GL11.glDisable(3042);
            if(mouseX >= this.guiLeft - 23 && mouseX <= this.guiLeft - 23 + 29 && mouseY >= this.guiTop + 47 + titleOffsetY * 31 && mouseY <= this.guiTop + 47 + 30 + titleOffsetY * 31) {
               tooltipToDraw = Arrays.asList(new String[]{I18n.func_135053_a("island.tab." + perm)});
            }
         }
      }

      ClientEventHandler.STYLE.bindTexture("island_perms");
      if(mouseX >= this.guiLeft + 248 && mouseX <= this.guiLeft + 248 + 9 && mouseY >= this.guiTop - 8 && mouseY <= this.guiTop - 8 + 10) {
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 248), (float)(this.guiTop - 8), 139, 259, 9, 10, 512.0F, 512.0F, false);
      } else {
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 248), (float)(this.guiTop - 8), 139, 249, 9, 10, 512.0F, 512.0F, false);
      }

      GL11.glPushMatrix();
      Double var12 = Double.valueOf((double)(this.guiTop + 45) + (double)this.field_73886_k.func_78256_a((String)IslandMainGui.islandInfos.get("name")) * 1.5D);
      GL11.glTranslatef((float)(this.guiLeft + 14), (float)var12.intValue(), 0.0F);
      GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
      GL11.glTranslatef((float)(-(this.guiLeft + 14)), (float)(-var12.intValue()), 0.0F);
      this.drawScaledString((String)IslandMainGui.islandInfos.get("name"), this.guiLeft + 14, var12.intValue(), 16777215, 1.5F, false, false);
      GL11.glPopMatrix();
      this.drawScaledString(I18n.func_135053_a("island.perms.role.member").toUpperCase(), this.guiLeft + 159, this.guiTop + 25, 0, 1.0F, true, false);
      this.drawScaledString(I18n.func_135053_a("island.perms.role.visitor").toUpperCase(), this.guiLeft + 215, this.guiTop + 25, 0, 1.0F, true, false);
      this.hoveredPerm = "";
      this.hoveredRole = "";
      GUIUtils.startGLScissor(this.guiLeft + 49, this.guiTop + 35, 194, 168);

      for(int var13 = 0; var13 < ((ArrayList)IslandMainGui.islandInfos.get("permissions")).size(); ++var13) {
         String var14 = (String)((ArrayList)IslandMainGui.islandInfos.get("permissions")).get(var13);
         int offsetX = this.guiLeft + 49;
         Float offsetY = Float.valueOf((float)(this.guiTop + 35 + var13 * 23) + this.getSlide());
         boolean trueForMembers = false;
         boolean trueForVisitors = false;
         ClientEventHandler.STYLE.bindTexture("island_perms");
         ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)offsetY.intValue(), 49, 35, 194, 23, 512.0F, 512.0F, false);
         this.drawScaledString(I18n.func_135053_a("island.perms.label." + var14), offsetX + 4, offsetY.intValue() + 6, 16777215, 1.0F, false, false);
         ClientEventHandler.STYLE.bindTexture("island_perms");
         ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 67), (float)(offsetY.intValue() + 6), 119, 249, 10, 11, 512.0F, 512.0F, false);
         if(mouseX >= offsetX + 67 && mouseX <= offsetX + 67 + 10 && mouseY >= offsetY.intValue() + 6 && mouseY <= offsetY.intValue() + 6 + 11) {
            tooltipToDraw = Arrays.asList(new String[]{I18n.func_135053_a("island.perms.tooltip." + var14)});
         }

         ClientEventHandler.STYLE.bindTexture("island_perms");
         if((!editedPerms.containsKey("member") || !((HashMap)editedPerms.get("member")).containsKey(var14) || !((Boolean)((HashMap)editedPerms.get("member")).get(var14)).booleanValue()) && (editedPerms.containsKey("member") && ((HashMap)editedPerms.get("member")).containsKey(var14) || !((Boolean)IslandMainGui.membersPerms.get(var14)).booleanValue())) {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 102), (float)(offsetY.intValue() + 3), 180, 251, 14, 15, 512.0F, 512.0F, false);
         } else {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 102), (float)(offsetY.intValue() + 3), 164, 251, 15, 15, 512.0F, 512.0F, false);
            trueForMembers = true;
         }

         if((!editedPerms.containsKey("visitor") || !((HashMap)editedPerms.get("visitor")).containsKey(var14) || !((Boolean)((HashMap)editedPerms.get("visitor")).get(var14)).booleanValue()) && (editedPerms.containsKey("visitor") && ((HashMap)editedPerms.get("visitor")).containsKey(var14) || !((Boolean)IslandMainGui.visitorsPerms.get(var14)).booleanValue())) {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 158), (float)(offsetY.intValue() + 3), 180, 251, 14, 15, 512.0F, 512.0F, false);
         } else {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 158), (float)(offsetY.intValue() + 3), 164, 251, 15, 15, 512.0F, 512.0F, false);
            trueForVisitors = true;
         }

         if(mouseX >= offsetX + 102 && mouseX <= offsetX + 102 + 15 && (float)mouseY >= offsetY.floatValue() + 3.0F && (float)mouseY <= offsetY.floatValue() + 3.0F + 15.0F) {
            this.hoveredRole = "member";
            this.hoveredPerm = var14;
            this.hoveredStatus = trueForMembers;
         } else if(mouseX >= offsetX + 158 && mouseX <= offsetX + 158 + 15 && (float)mouseY >= offsetY.floatValue() + 3.0F && (float)mouseY <= offsetY.floatValue() + 3.0F + 15.0F) {
            this.hoveredRole = "visitor";
            this.hoveredPerm = var14;
            this.hoveredStatus = trueForVisitors;
         }
      }

      GUIUtils.endGLScissor();
      this.scrollBar.draw(mouseX, mouseY);
      ClientEventHandler.STYLE.bindTexture("island_perms");
      if(editedPerms.isEmpty() || mouseX >= this.guiLeft + 135 && mouseX <= this.guiLeft + 135 + 113 && mouseY >= this.guiTop + 214 && mouseY <= this.guiTop + 214 + 18) {
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 135), (float)(this.guiTop + 214), 0, 249, 113, 18, 512.0F, 512.0F, false);
      }

      this.drawScaledString(I18n.func_135053_a("island.global.save"), this.guiLeft + 191, this.guiTop + 219, 16777215, 1.0F, true, false);
      if(!((List)tooltipToDraw).isEmpty()) {
         this.drawHoveringText((List)tooltipToDraw, mouseX, mouseY, this.field_73886_k);
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
         for(int editedPermForRole = 0; editedPermForRole < IslandMainGui.TABS.size(); ++editedPermForRole) {
            GuiScreenTab type = (GuiScreenTab)IslandMainGui.TABS.get(editedPermForRole);
            if(mouseX >= this.guiLeft - 20 && mouseX <= this.guiLeft + 3 && mouseY >= this.guiTop + 47 + editedPermForRole * 31 && mouseY <= this.guiTop + 47 + 30 + editedPermForRole * 31) {
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

         if(mouseX > this.guiLeft + 248 && mouseX < this.guiLeft + 248 + 9 && mouseY > this.guiTop - 8 && mouseY < this.guiTop - 8 + 10) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
         } else if(!this.helpOpened && mouseX > this.guiLeft + 260 && mouseX < this.guiLeft + 260 + 23 && mouseY > this.guiTop + 171 && mouseY < this.guiTop + 171 + 45) {
            this.helpOpened = true;
         } else if(this.helpOpened && mouseX > this.guiLeft + 260 + 107 && mouseX < this.guiLeft + 260 + 107 + 23 && mouseY > this.guiTop + 171 && mouseY < this.guiTop + 171 + 45) {
            this.helpOpened = false;
         } else if(!this.hoveredRole.isEmpty() && !this.hoveredPerm.isEmpty()) {
            HashMap var8 = new HashMap();
            if(editedPerms.containsKey(this.hoveredRole)) {
               var8 = (HashMap)editedPerms.get(this.hoveredRole);
            }

            var8.put(this.hoveredPerm, Boolean.valueOf(!this.hoveredStatus));
            editedPerms.put(this.hoveredRole, var8);
         } else if(mouseX >= this.guiLeft + 135 && mouseX <= this.guiLeft + 135 + 113 && mouseY >= this.guiTop + 214 && mouseY <= this.guiTop + 214 + 18 && !editedPerms.isEmpty()) {
            this.field_73882_e.field_71416_A.func_77366_a("random.successful_hit", 1.0F, 1.0F);
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandSavePermsPacket((String)IslandMainGui.islandInfos.get("id"), editedPerms)));
         }
      }

      super.func_73864_a(mouseX, mouseY, mouseButton);
   }

   private float getSlide() {
      return ((ArrayList)IslandMainGui.islandInfos.get("permissions")).size() > 7?(float)(-(((ArrayList)IslandMainGui.islandInfos.get("permissions")).size() - 7) * 23) * this.scrollBar.getSliderValue():0.0F;
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

}
