package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBar;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ProfilGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionBadgeDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class BadgesGUI extends GuiScreen {

   protected int xSize = 237;
   protected int ySize = 222;
   private int guiLeft;
   private int guiTop;
   public static boolean loaded = false;
   private RenderItem itemRenderer = new RenderItem();
   public static NBTTagCompound CLIENT_BADGES = null;
   public static List<String> CLIENT_ANIMS = new ArrayList();
   public static String CLICKED_BADGE = null;
   private String targetName;
   private String[] availableBadges;
   private GuiScrollBar scrollBar;
   private String hoveredBadge = "";


   public BadgesGUI(String targetName) {
      this.targetName = targetName;
      loaded = false;
      CLICKED_BADGE = null;
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionBadgeDataPacket(this.targetName)));
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - this.ySize) / 2;
      this.scrollBar = new GuiScrollBar((float)(this.guiLeft + 214), (float)(this.guiTop + 47), 106);
   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ClientEventHandler.STYLE.bindTexture("faction_badge");
      ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
      if(mouseX >= this.guiLeft + 223 && mouseX <= this.guiLeft + 223 + 9 && mouseY >= this.guiTop - 6 && mouseY <= this.guiTop - 6 + 10) {
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 223), (float)(this.guiTop - 6), 0, 236, 9, 10, 512.0F, 512.0F, false);
      } else {
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 223), (float)(this.guiTop - 6), 0, 226, 9, 10, 512.0F, 512.0F, false);
      }

      this.drawScaledString(I18n.func_135053_a("faction.badges.title") + " " + this.targetName, this.guiLeft + 51, this.guiTop + 17, 1644825, 1.4F, false, false);
      if(loaded) {
         this.availableBadges = CLIENT_BADGES.func_74779_i("AvailableBadges").split(",");
         this.hoveredBadge = "";
         if(CLICKED_BADGE != null && NationsGUI.BADGES_RESOURCES.containsKey(CLICKED_BADGE)) {
            Minecraft.func_71410_x().func_110434_K().func_110577_a((ResourceLocation)NationsGUI.BADGES_RESOURCES.get(CLICKED_BADGE));
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 60), (float)(this.guiTop + 176), 0, 0, 18, 18, 18.0F, 18.0F, false);
            if(NationsGUI.BADGES_NAMES.containsKey(CLICKED_BADGE)) {
               this.drawScaledString((String)NationsGUI.BADGES_NAMES.get(CLICKED_BADGE), this.guiLeft + 60 + 25, this.guiTop + 182, 16777215, 1.2F, false, true);
            } else {
               this.drawScaledString(CLICKED_BADGE, this.guiLeft + 60 + 25, this.guiTop + 182, 16777215, 1.2F, false, true);
            }
         }

         if(CLIENT_BADGES != null && CLIENT_BADGES.func_74764_b("AvailableBadges")) {
            GUIUtils.startGLScissor(this.guiLeft + 46, this.guiTop + 45, 164, 110);
            int i = 0;
            String[] tooltipLines = this.availableBadges;
            int var6 = tooltipLines.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               String availableBadge = tooltipLines[var7];
               int j = i % 9;
               int k = i / 9;
               if(this.availableBadges != null && !availableBadge.isEmpty() && NationsGUI.BADGES_RESOURCES.containsKey(availableBadge)) {
                  Minecraft.func_71410_x().func_110434_K().func_110577_a((ResourceLocation)NationsGUI.BADGES_RESOURCES.get(availableBadge));
                  int offsetX = this.guiLeft + 47 + j * 18;
                  Float offsetY = Float.valueOf((float)(this.guiTop + 46 + k * 18) + this.getSlide());
                  ModernGui.drawScaledCustomSizeModalRect((float)(offsetX + 2), (float)(offsetY.intValue() + 2), 0.0F, 0.0F, 18, 18, 14, 14, 18.0F, 18.0F, false);
                  if(mouseX >= offsetX && mouseX <= offsetX + 18 && (float)mouseY >= offsetY.floatValue() && (float)mouseY <= offsetY.floatValue() + 18.0F) {
                     if(NationsGUI.BADGES_TOOLTIPS.containsKey(availableBadge)) {
                        this.hoveredBadge = availableBadge;
                     }

                     if(Mouse.isButtonDown(0) && (CLICKED_BADGE == null || CLICKED_BADGE != null && !CLICKED_BADGE.equals(availableBadge))) {
                        Minecraft.func_71410_x().field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
                        CLICKED_BADGE = availableBadge;
                     }
                  }

                  if(CLICKED_BADGE != null && CLICKED_BADGE.equals(availableBadge)) {
                     ClientEventHandler.STYLE.bindTexture("faction_badge");
                     GL11.glEnable(3042);
                     GL11.glBlendFunc(770, 771);
                     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                     this.field_73735_i = 300.0F;
                     int offsetXSelect = this.guiLeft + 38 + j * 18;
                     Double offsetYSelect = Double.valueOf((double)this.guiTop + 43.5D + (double)(k * 18));
                     this.func_73729_b(offsetXSelect, offsetYSelect.intValue(), 18, 226, 24, 24);
                     this.field_73735_i = 0.0F;
                     GL11.glDisable(3042);
                  }

                  ++i;
               }
            }

            GUIUtils.endGLScissor();
            this.scrollBar.draw(mouseX, mouseY);
            if(!this.hoveredBadge.isEmpty()) {
               List var15 = (List)NationsGUI.BADGES_TOOLTIPS.get(this.hoveredBadge);
               this.drawHoveringText(var15, mouseX, mouseY, this.field_73886_k);
               this.hoveredBadge = "";
            }
         }
      }

   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0 && mouseX > this.guiLeft + 223 && mouseX < this.guiLeft + 223 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10) {
         this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
         Minecraft.func_71410_x().func_71373_a(new ProfilGui(this.targetName, ""));
      }

   }

   private float getSlide() {
      return this.availableBadges.length > 54?(float)(-(this.availableBadges.length - 54) * 18) * this.scrollBar.getSliderValue():0.0F;
   }

   public void drawHoveringText(List<String> text, int mouseX, int mouseY, FontRenderer fontRenderer) {
      if(!text.isEmpty()) {
         GL11.glDisable('\u803a');
         GL11.glDisable(2896);
         GL11.glDisable(2929);
         int width = 0;

         int offsetY;
         for(Iterator posX = text.iterator(); posX.hasNext(); width = Math.max(width, offsetY)) {
            String posY = (String)posX.next();
            offsetY = fontRenderer.func_78256_a(posY);
         }

         int var14 = mouseX + 12;
         int var15 = mouseY - 12;
         offsetY = 8;
         if(text.size() > 1) {
            offsetY += 2 + (text.size() - 1) * 10;
         }

         if(var14 + width > this.field_73880_f) {
            var14 -= 28 + width;
         }

         if(var15 + offsetY + 6 > this.field_73881_g) {
            var15 = this.field_73881_g - offsetY - 6;
         }

         this.field_73735_i = 300.0F;
         this.itemRenderer.field_77023_b = 300.0F;
         int color1 = -267386864;
         this.func_73733_a(var14 - 3, var15 - 4, var14 + width + 3, var15 - 3, color1, color1);
         this.func_73733_a(var14 - 3, var15 + offsetY + 3, var14 + width + 3, var15 + offsetY + 4, color1, color1);
         this.func_73733_a(var14 - 3, var15 - 3, var14 + width + 3, var15 + offsetY + 3, color1, color1);
         this.func_73733_a(var14 - 4, var15 - 3, var14 - 3, var15 + offsetY + 3, color1, color1);
         this.func_73733_a(var14 + width + 3, var15 - 3, var14 + width + 4, var15 + offsetY + 3, color1, color1);
         int color2 = 1347420415;
         int color3 = (color2 & 16711422) >> 1 | color2 & -16777216;
         this.func_73733_a(var14 - 3, var15 - 3 + 1, var14 - 3 + 1, var15 + offsetY + 3 - 1, color2, color3);
         this.func_73733_a(var14 + width + 2, var15 - 3 + 1, var14 + width + 3, var15 + offsetY + 3 - 1, color2, color3);
         this.func_73733_a(var14 - 3, var15 - 3, var14 + width + 3, var15 - 3 + 1, color2, color2);
         this.func_73733_a(var14 - 3, var15 + offsetY + 2, var14 + width + 3, var15 + offsetY + 3, color3, color3);

         for(int i = 0; i < text.size(); ++i) {
            String line = (String)text.get(i);
            if(i == 0) {
               fontRenderer.func_78261_a(line, var14, var15, -1);
               var15 += 2;
            } else {
               fontRenderer.func_78261_a(EnumChatFormatting.GOLD + line, var14 + width - fontRenderer.func_78256_a(line), var15, 16777215);
            }

            var15 += 10;
         }

         this.field_73735_i = 0.0F;
         this.itemRenderer.field_77023_b = 0.0F;
         GL11.glDisable(2896);
         GL11.glDisable(2929);
         GL11.glEnable('\u803a');
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      }

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

}
