package net.ilexiconn.nationsgui.forge.client.gui.override;

import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.override.ElementOverride;
import net.ilexiconn.nationsgui.forge.client.voices.keybindings.KeyManager;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceChat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import org.lwjgl.opengl.GL11;

public class VoiceOverride extends Gui implements ElementOverride {

   public ElementType getType() {
      return ElementType.HOTBAR;
   }

   public ElementType[] getSubTypes() {
      return null;
   }

   public void renderOverride(Minecraft client, ScaledResolution resolution, float partialTicks) {
      float size = 0.5F;
      GL11.glPushMatrix();
      if(KeyManager.getInstance().isKeyMuted()) {
         ClientEventHandler.STYLE.bindTexture("voice_off");
         GL11.glScalef(size, size, size);
         ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(8.0F, 8.0F, 0, 0, 32, 32, 32.0F, 32.0F, false);
         GL11.glScalef(1.0F, 1.0F, 1.0F);
      } else if(VoiceChat.getProxyInstance().isRecorderActive()) {
         ClientEventHandler.STYLE.bindTexture("voice_vol");
         GL11.glScalef(size, size, size);
         switch((int)(Minecraft.func_71386_F() % 1000L / 250L)) {
         case 0:
            ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(8.0F, 8.0F, 0, 0, 19, 32, 128.0F, 32.0F, false);
            break;
         case 1:
            ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(8.0F, 8.0F, 22, 0, 27, 32, 128.0F, 32.0F, false);
            break;
         case 2:
            ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(8.0F, 8.0F, 53, 0, 31, 32, 128.0F, 32.0F, false);
            break;
         case 3:
            ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(8.0F, 8.0F, 89, 0, 37, 32, 128.0F, 32.0F, false);
         }

         GL11.glScalef(1.0F, 1.0F, 1.0F);
      }

      GL11.glPopMatrix();
      int y = 97;
      int i = 0;
      Iterator var7 = Minecraft.func_71410_x().field_71441_e.field_73010_i.iterator();

      while(var7.hasNext()) {
         EntityPlayer ent = (EntityPlayer)var7.next();
         if(ent instanceof AbstractClientPlayer && ClientEventHandler.isPlayerTalk(ent.func_70005_c_()) && i < 3) {
            ++i;
            GL11.glPushMatrix();
            double size1 = 0.8D;
            GL11.glScaled(size1, size1, size1);
            ClientEventHandler.STYLE.bindTexture("voice_card");
            byte x = 5;
            boolean width = true;
            boolean height = true;
            ModernGui.drawModalRectWithCustomSizedTextureWithTransparency((float)x, (float)y, 0, 0, 43, 30, 256.0F, 256.0F, false);
            int nameWidth = Minecraft.func_71410_x().field_71466_p.func_78256_a(ent.func_70005_c_());
            Gui.func_73734_a(x + 43, y + 3, x + 43 + nameWidth + 6, y + 3 + 22, -14803651);
            this.func_73731_b(client.field_71466_p, ent.func_70005_c_(), x + 46, y + 10, 16777215);
            ClientEventHandler.STYLE.bindTexture("voice_card");
            ModernGui.drawModalRectWithCustomSizedTextureWithTransparency((float)(x + 43 + nameWidth + 6), (float)y, 130, 0, 5, 30, 256.0F, 256.0F, false);
            client.field_71446_o.func_110577_a(((AbstractClientPlayer)ent).func_110306_p());
            GL11.glPushMatrix();
            GL11.glTranslated((double)x + 9.0D, (double)y + 9.0D, 0.0D);
            GL11.glScaled(1.5D, 1.5D, 1.5D);
            ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(0.0F, 0.0F, 8, 8, 8, 8, 64.0F, 64.0F, false);
            GL11.glPopMatrix();
            y += 34;
            GL11.glPopMatrix();
         }
      }

      if(i > 3) {
         y += 34;
         this.func_73732_a(client.field_71466_p, "+" + (i - 3) + " personne(s)", 5, y, 16777215);
      }

   }
}
