package net.ilexiconn.nationsgui.forge.client.gui.assistance;

import java.text.SimpleDateFormat;
import java.util.Date;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiScroller;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiScrollerElement;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiTextMultiLines;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AbstractAssistanceComponent;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class AssistanceMessage extends AbstractAssistanceComponent implements GuiScrollerElement {

   private String pseudo;
   private String message;
   private String date;
   private int width;
   private GuiTextMultiLines multiLines;


   public AssistanceMessage(String pseudo, String message, Date date) {
      this.pseudo = pseudo;
      this.message = message;
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
      this.date = simpleDateFormat.format(date);
   }

   public void draw(int mouseX, int mouseY, float partialTicks) {
      float dateWidth = (float)Minecraft.func_71410_x().field_71466_p.func_78256_a(this.date) * 0.75F;
      float maxPseudoWidth = (float)this.width - dateWidth - 16.0F - 2.0F;
      float pseudoRatio = Math.min(1.0F, maxPseudoWidth / (float)Minecraft.func_71410_x().field_71466_p.func_78256_a(this.pseudo));
      ResourceLocation resourceLocation = AbstractClientPlayer.func_110311_f(this.pseudo);
      AbstractClientPlayer.func_110304_a(resourceLocation, this.pseudo);
      Minecraft.func_71410_x().func_110434_K().func_110577_a(resourceLocation);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      GUIUtils.drawScaledCustomSizeModalRect(0, 0, 8.0F, 8.0F, 8, 8, 12, 12, 64.0F, 64.0F);
      GUIUtils.drawScaledCustomSizeModalRect(0, 0, 40.0F, 8.0F, 8, 8, 12, 12, 64.0F, 64.0F);
      GL11.glPushMatrix();
      GL11.glTranslatef(16.0F, 7.0F - 9.0F * pseudoRatio / 2.0F, 0.0F);
      GL11.glScalef(pseudoRatio, pseudoRatio, pseudoRatio);
      this.func_73731_b(Minecraft.func_71410_x().field_71466_p, this.pseudo, 0, 0, 16777215);
      GL11.glPopMatrix();
      GL11.glPushMatrix();
      GL11.glTranslatef((float)this.width - dateWidth, 4.0F, 0.0F);
      GL11.glScalef(0.75F, 0.75F, 0.75F);
      this.func_73731_b(Minecraft.func_71410_x().field_71466_p, this.date, 0, 0, 16777215);
      GL11.glPopMatrix();
      this.multiLines.draw(mouseX, mouseY, partialTicks);
   }

   public void onClick(int mouseX, int mouseY, int clickType) {}

   public void update() {}

   public void keyTyped(char c, int key) {}

   public void init(GuiScroller scroller) {
      this.width = scroller.getWorkWidth();
      this.multiLines = new GuiTextMultiLines(this.message, this.width - 6, false, 1.0F);
      this.multiLines.setPosition(0, 20);
   }

   public int getHeight() {
      return 20 + this.multiLines.getHeight() + 4;
   }
}
