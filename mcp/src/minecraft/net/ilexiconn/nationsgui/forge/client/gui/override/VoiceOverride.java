/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.override;

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
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;

public class VoiceOverride
extends Gui
implements ElementOverride {
    @Override
    public RenderGameOverlayEvent.ElementType getType() {
        return RenderGameOverlayEvent.ElementType.HOTBAR;
    }

    @Override
    public RenderGameOverlayEvent.ElementType[] getSubTypes() {
        return null;
    }

    @Override
    public void renderOverride(Minecraft client, ScaledResolution resolution, float partialTicks) {
        float size = 0.5f;
        GL11.glPushMatrix();
        if (KeyManager.getInstance().isKeyMuted()) {
            ClientEventHandler.STYLE.bindTexture("voice_off");
            GL11.glScalef((float)size, (float)size, (float)size);
            ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(8.0f, 8.0f, 0, 0, 32, 32, 32.0f, 32.0f, false);
            GL11.glScalef((float)1.0f, (float)1.0f, (float)1.0f);
        } else if (VoiceChat.getProxyInstance().isRecorderActive()) {
            ClientEventHandler.STYLE.bindTexture("voice_vol");
            GL11.glScalef((float)size, (float)size, (float)size);
            switch ((int)(Minecraft.func_71386_F() % 1000L / 250L)) {
                case 0: {
                    ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(8.0f, 8.0f, 0, 0, 19, 32, 128.0f, 32.0f, false);
                    break;
                }
                case 1: {
                    ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(8.0f, 8.0f, 22, 0, 27, 32, 128.0f, 32.0f, false);
                    break;
                }
                case 2: {
                    ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(8.0f, 8.0f, 53, 0, 31, 32, 128.0f, 32.0f, false);
                    break;
                }
                case 3: {
                    ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(8.0f, 8.0f, 89, 0, 37, 32, 128.0f, 32.0f, false);
                }
            }
            GL11.glScalef((float)1.0f, (float)1.0f, (float)1.0f);
        }
        GL11.glPopMatrix();
        int y = 97;
        int i = 0;
        for (EntityPlayer ent : Minecraft.func_71410_x().field_71441_e.field_73010_i) {
            if (!(ent instanceof AbstractClientPlayer) || !ClientEventHandler.isPlayerTalk(ent.func_70005_c_()) || i >= 3) continue;
            ++i;
            GL11.glPushMatrix();
            double size1 = 0.8;
            GL11.glScaled((double)size1, (double)size1, (double)size1);
            ClientEventHandler.STYLE.bindTexture("voice_card");
            int x = 5;
            int width = 134;
            int height = 30;
            ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(x, y, 0, 0, 43, 30, 256.0f, 256.0f, false);
            int nameWidth = Minecraft.func_71410_x().field_71466_p.func_78256_a(ent.func_70005_c_());
            Gui.func_73734_a((int)(x + 43), (int)(y + 3), (int)(x + 43 + nameWidth + 6), (int)(y + 3 + 22), (int)-14803651);
            this.func_73731_b(client.field_71466_p, ent.func_70005_c_(), x + 46, y + 10, 0xFFFFFF);
            ClientEventHandler.STYLE.bindTexture("voice_card");
            ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(x + 43 + nameWidth + 6, y, 130, 0, 5, 30, 256.0f, 256.0f, false);
            client.field_71446_o.func_110577_a(((AbstractClientPlayer)ent).func_110306_p());
            GL11.glPushMatrix();
            GL11.glTranslated((double)((double)x + 9.0), (double)((double)y + 9.0), (double)0.0);
            GL11.glScaled((double)1.5, (double)1.5, (double)1.5);
            ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(0.0f, 0.0f, 8, 8, 8, 8, 64.0f, 64.0f, false);
            GL11.glPopMatrix();
            y += 34;
            GL11.glPopMatrix();
        }
        if (i > 3) {
            this.func_73732_a(client.field_71466_p, "+" + (i - 3) + " personne(s)", 5, y += 34, 0xFFFFFF);
        }
    }
}

