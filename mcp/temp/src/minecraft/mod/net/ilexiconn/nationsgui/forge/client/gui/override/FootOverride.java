package net.ilexiconn.nationsgui.forge.client.gui.override;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.override.BuildOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.ElementOverride;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

public class FootOverride extends Gui implements ElementOverride {

   private HashMap<String, DynamicTexture> flagTextures = new HashMap();


   public ElementType getType() {
      return ElementType.HOTBAR;
   }

   public ElementType[] getSubTypes() {
      return new ElementType[0];
   }

   public void renderOverride(Minecraft client, ScaledResolution resolution, float partialTicks) {
      if(!ClientData.currentFoot.isEmpty()) {
         Double doubleY = Double.valueOf((double)resolution.func_78328_b() * 0.4D);
         int y = doubleY.intValue();
         int x = resolution.func_78326_a() - 140;
         func_73734_a(x, y, x + 140, y + 16, -1157627904);
         this.drawSmallString(client.field_71466_p, "Football", x + 75 - client.field_71466_p.func_78256_a("Football") / 2, y + 4, 16777215);
         ClientEventHandler.STYLE.bindTexture("hud2");
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.func_73729_b(x + 75 - client.field_71466_p.func_78256_a("Football") / 2 - 20, y + 2, 183, 35, 12, 12);
         this.func_73729_b(x + 75 + client.field_71466_p.func_78256_a("Football") / 2 + 4, y + 2, 183, 35, 12, 12);
         func_73734_a(x, y + 16, x + 140, y + 16 + 45, 1996488704);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.drawSmallString(client.field_71466_p, (String)ClientData.currentFoot.get("team1"), x + 3, y + 22, 16777215);
         this.drawSmallString(client.field_71466_p, "\u00a7cVS", x + 75 - client.field_71466_p.func_78256_a("VS") / 2, y + 35, 16777215);
         this.drawSmallString(client.field_71466_p, (String)ClientData.currentFoot.get("team2"), x + 3, y + 47, 16777215);
         func_73734_a(x, y + 61, x + 140, y + 61 + 14, -1157627904);
         this.drawSmallString(client.field_71466_p, I18n.func_135053_a("assault.time"), x + 3, y + 65, 16777215);
         this.drawSmallString(client.field_71466_p, "\u00a77" + BuildOverride.chronoTimeToStr(Long.valueOf(Long.parseLong((String)ClientData.currentFoot.get("remainingTime")) * 1000L), false), x + 115 - 3, y + 65, 16777215);
         byte scoreY = 79;
         func_73734_a(x, y + scoreY, x + 140, y + scoreY + 16, -1157627904);
         this.drawSmallString(client.field_71466_p, I18n.func_135053_a("assault.score.title"), x + 75 - client.field_71466_p.func_78256_a(I18n.func_135053_a("assault.score.title")) / 2, y + scoreY + 4, 16777215);
         func_73734_a(x, y + scoreY + 16, x + 140, y + scoreY + 16 + 35, 1996488704);
         this.drawSmallString(client.field_71466_p, (String)ClientData.currentFoot.get("team1"), x + 3, y + scoreY + 22, 16777215);
         this.drawSmallString(client.field_71466_p, "\u00a77" + (String)ClientData.currentFoot.get("score1"), x + 140 - client.field_71466_p.func_78256_a((String)ClientData.currentFoot.get("score1")) - 3, y + scoreY + 22, 16777215);
         this.drawSmallString(client.field_71466_p, (String)ClientData.currentFoot.get("team2"), x + 3, y + scoreY + 37, 16777215);
         this.drawSmallString(client.field_71466_p, "\u00a77" + (String)ClientData.currentFoot.get("score2"), x + 140 - client.field_71466_p.func_78256_a((String)ClientData.currentFoot.get("score2")) - 3, y + scoreY + 37, 16777215);
      }

   }

   private void drawSmallString(FontRenderer fontRenderer, String string, int posX, int posY, int color) {
      GL11.glPushMatrix();
      GL11.glTranslatef((float)posX, (float)posY, 0.0F);
      GL11.glScalef(0.95F, 0.95F, 0.95F);
      this.func_73731_b(fontRenderer, string, 0, 0, 16777215);
      GL11.glPopMatrix();
   }

   private void drawVerySmallString(FontRenderer fontRenderer, String string, int posX, int posY, int color) {
      GL11.glPushMatrix();
      GL11.glTranslatef((float)posX, (float)posY, 0.0F);
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      this.func_73731_b(fontRenderer, string, 0, 0, 16777215);
      GL11.glPopMatrix();
   }

   public void drawScaledString(FontRenderer fontRenderer, String text, int x, int y, int color, float scale, boolean centered, boolean shadow) {
      GL11.glPushMatrix();
      GL11.glScalef(scale, scale, scale);
      float newX = (float)x;
      if(centered) {
         newX = (float)x - (float)fontRenderer.func_78256_a(text) * scale / 2.0F;
      }

      if(shadow) {
         fontRenderer.func_85187_a(text, (int)(newX / scale), (int)((float)(y + 1) / scale), (color & 16579836) >> 2 | color & -16777216, false);
      }

      fontRenderer.func_85187_a(text, (int)(newX / scale), (int)((float)y / scale), color, false);
      GL11.glPopMatrix();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
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
