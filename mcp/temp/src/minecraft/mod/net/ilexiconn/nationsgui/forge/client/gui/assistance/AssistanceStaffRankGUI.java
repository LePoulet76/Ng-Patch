package net.ilexiconn.nationsgui.forge.client.gui.assistance;

import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiScroller;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiScrollerElement;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AbstractAssistanceComponent;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class AssistanceStaffRankGUI extends AbstractAssistanceComponent implements GuiScrollerElement {

   private final String pseudo;
   private final String score;
   private int width;


   public AssistanceStaffRankGUI(String pseudo, int score) {
      this.pseudo = pseudo;
      this.score = "(" + score + ")";
   }

   public void draw(int mouseX, int mouseY, float partialTicks) {
      float scoreWidth = (float)Minecraft.func_71410_x().field_71466_p.func_78256_a(this.score);
      float maxPseudoWidth = (float)this.width - scoreWidth - 16.0F - 2.0F;
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
      this.func_73731_b(Minecraft.func_71410_x().field_71466_p, this.score, (int)((float)this.width - scoreWidth), 4, 16777215);
   }

   public void onClick(int mouseX, int mouseY, int clickType) {}

   public void update() {}

   public void keyTyped(char c, int key) {}

   public void init(GuiScroller scroller) {
      this.width = scroller.getWorkWidth();
   }

   public int getHeight() {
      return 15;
   }
}
