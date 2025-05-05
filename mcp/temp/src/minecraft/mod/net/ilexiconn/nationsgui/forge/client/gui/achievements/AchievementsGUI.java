package net.ilexiconn.nationsgui.forge.client.gui.achievements;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.CloseButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBar;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AchievementsDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IncrementObjectivePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class AchievementsGUI extends GuiScreen {

   public static final ResourceLocation TEXTURE = new ResourceLocation("nationsglory", "textures/gui/achievements.png");
   private RenderItem itemRenderer = new RenderItem();
   private ArrayList<HashMap<String, String>> achievements = new ArrayList();
   private int guiLeft = 0;
   private int guiTop = 0;
   private int xSize = 182;
   private int ySize = 195;
   private GuiButton refreshButton;
   private GuiScrollBar scrollBar;
   public static boolean loaded = false;
   public static boolean badgesChecked = false;
   public static boolean achievementDone = false;


   public AchievementsGUI() {
      loaded = false;
      if(!achievementDone) {
         achievementDone = true;
         PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IncrementObjectivePacket("player_open_achievements", 1)));
      }

   }

   public void func_73866_w_() {
      super.func_73866_w_();
      loaded = false;
      this.guiLeft = this.field_73880_f / 2 - this.xSize / 2;
      this.guiTop = this.field_73881_g / 2 - this.ySize / 2;
      this.field_73887_h.add(new CloseButtonGUI(0, this.guiLeft + 161, this.guiTop + 13));
      this.scrollBar = new GuiScrollBar((float)(this.guiLeft + 162), (float)(this.guiTop + 46), 140);
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new AchievementsDataPacket()));
   }

   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
      this.guiLeft = this.field_73880_f / 2 - this.xSize / 2;
      this.guiTop = this.field_73881_g / 2 - this.ySize / 2;
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ClientEventHandler.STYLE.bindTexture("achievements");
      ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
      this.drawScaledString(I18n.func_135053_a("gui.achievements.title"), this.guiLeft + 30, this.guiTop + 11, 16777215, 1.8F, false);
      if(loaded && ClientData.getAchievements() != null) {
         this.achievements = ClientData.getAchievements();
         ClientEventHandler.STYLE.bindTexture("achievements");
         GUIUtils.startGLScissor(this.guiLeft + 10, this.guiTop + 45, 147, 142);

         for(int i = 0; i < this.achievements.size(); ++i) {
            int offsetX = this.guiLeft + 10;
            Float offsetY = Float.valueOf((float)(this.guiTop + 45 + i * 61) + this.getSlide());
            ClientEventHandler.STYLE.bindTexture("achievements");
            ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)offsetY.intValue(), 0, 201, 147, 58, 512.0F, 512.0F, false);
            if(NationsGUI.BADGES_RESOURCES.containsKey(((HashMap)this.achievements.get(i)).get("badge"))) {
               Minecraft.func_71410_x().func_110434_K().func_110577_a((ResourceLocation)NationsGUI.BADGES_RESOURCES.get(((HashMap)this.achievements.get(i)).get("badge")));
               ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 9), (float)(offsetY.intValue() + 9), 0, 0, 18, 18, 18.0F, 18.0F, false);
            }

            this.drawScaledString((String)((HashMap)this.achievements.get(i)).get("name"), offsetX + 33, offsetY.intValue() + 10, 16777215, 1.1F, false);
            int descLineNumber = 0;
            String[] progress = ((String)((HashMap)this.achievements.get(i)).get("description")).split("\n");
            int maxBarSize = progress.length;

            for(int barSize = 0; barSize < maxBarSize; ++barSize) {
               String descLine = progress[barSize];
               this.drawScaledString(descLine, offsetX + 33, offsetY.intValue() + 23 + 10 * descLineNumber, 11842740, 1.0F, false);
               ++descLineNumber;
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            int var12 = Integer.parseInt((String)((HashMap)this.achievements.get(i)).get("progress"));
            short var13 = 147;
            Double var14 = Double.valueOf((double)var13 * ((double)var12 * 1.0D / 100.0D));
            ClientEventHandler.STYLE.bindTexture("achievements");
            ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)(offsetY.intValue() + 52), 0, 195, var14.intValue(), 6, 512.0F, 512.0F, false);
            if(mouseX > offsetX && mouseX < offsetX + 147 && mouseY > offsetY.intValue() + 52 && mouseY < offsetY.intValue() + 52 + 6) {
               this.drawHoveringText(Collections.singletonList("\u00a7o" + var12 + "%"), mouseX, mouseY, this.field_73886_k);
            }
         }

         GUIUtils.endGLScissor();
      }

      this.scrollBar.draw(mouseX, mouseY);
      super.func_73863_a(mouseX, mouseY, partialTicks);
   }

   private float getSlide() {
      return this.achievements.size() > 2?(float)(-(this.achievements.size() - 2) * 58) * this.scrollBar.getSliderValue():0.0F;
   }

   public void drawScaledString(String text, int x, int y, int color, float scale, boolean centered) {
      GL11.glPushMatrix();
      GL11.glScalef(scale, scale, scale);
      float newX = (float)x;
      if(centered) {
         newX = (float)x - (float)this.field_73886_k.func_78256_a(text) * scale / 2.0F;
      }

      this.field_73886_k.func_85187_a(text, (int)(newX / scale), (int)((float)(y + 1) / scale), (color & 16579836) >> 2 | color & -16777216, false);
      this.field_73886_k.func_85187_a(text, (int)(newX / scale), (int)((float)y / scale), color, false);
      GL11.glPopMatrix();
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

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      super.func_73864_a(mouseX, mouseY, mouseButton);
   }

   protected void func_73875_a(GuiButton button) {
      if(button.field_73741_f == 0) {
         this.field_73882_e.func_71373_a((GuiScreen)null);
         this.field_73882_e.func_71381_h();
         this.field_73882_e.field_71416_A.func_82461_f();
      }

   }

   public boolean func_73868_f() {
      return false;
   }

}
