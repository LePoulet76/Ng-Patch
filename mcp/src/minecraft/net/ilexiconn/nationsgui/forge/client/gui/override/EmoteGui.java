/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.override;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.emotes.ClientEmotesHandler;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.EmotesGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.SkinType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class EmoteGui
extends GuiScreen {
    static ResourceLocation menu = new ResourceLocation("nationsgui", "emotes/gui/emote_wheel_base.png");
    Map<Integer, Integer[]> locs = new HashMap<Integer, Integer[]>();
    private int backX;
    private int backY;
    private int menuX;
    private int menuY;
    private List<String> currentEmotes;

    public void func_73866_w_() {
        this.backX = 185;
        this.backY = 172;
        this.menuX = (this.field_73880_f - this.backX) / 2;
        this.menuY = (this.field_73881_g - this.backY) / 2;
        this.locs.put(0, new Integer[]{31, 0, 61, 32});
        this.locs.put(1, new Integer[]{94, 0, 124, 32});
        this.locs.put(2, new Integer[]{0, 53, 30, 85});
        this.locs.put(3, new Integer[]{125, 53, 155, 85});
        this.locs.put(4, new Integer[]{31, 107, 61, 139});
        this.locs.put(5, new Integer[]{94, 107, 124, 139});
        this.currentEmotes = new ArrayList<String>();
        List<AbstractSkin> emoteSkins = ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(Minecraft.func_71410_x().field_71439_g.field_71092_bJ, SkinType.EMOTES);
        for (AbstractSkin skin : emoteSkins) {
            this.currentEmotes.add(skin.getId());
        }
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        GL11.glPushMatrix();
        String emote = "";
        this.bindTexture(menu);
        ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(this.menuX, this.menuY, 0, 0, 185, 172, 256.0f, 256.0f, false);
        for (Map.Entry<Integer, Integer[]> e : this.locs.entrySet()) {
            int diameter;
            int radius;
            int pointY;
            if (this.currentEmotes.size() <= e.getKey()) continue;
            Integer[] coords = e.getValue();
            int textureX = coords[0];
            int textureY = coords[1];
            int pointX = coords[2];
            if (this.isInsideCircle(mouseX, mouseY, this.menuX + pointX, this.menuY + (pointY = coords[3].intValue()), radius = (diameter = 58) / 2)) {
                this.bindTexture(menu);
                ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(this.menuX + textureX, this.menuY + textureY, 0, 190, 61, 66, 256.0f, 256.0f, false);
                String string = emote = this.currentEmotes.get(e.getKey()) != null && !this.currentEmotes.get(e.getKey()).isEmpty() ? this.currentEmotes.get(e.getKey()).replaceAll("emotes_", "") : "";
            }
            if (this.currentEmotes.get(e.getKey()) != null && !this.currentEmotes.get(e.getKey()).isEmpty()) {
                if (ClientProxy.SKIN_MANAGER.getSkinFromID(this.currentEmotes.get(e.getKey())) == null) continue;
                ClientProxy.SKIN_MANAGER.getSkinFromID(this.currentEmotes.get(e.getKey())).renderInGUI(this.menuX + pointX - 13, this.menuY + pointY - 12, 1.0f, par3);
                continue;
            }
            this.bindTexture(new ResourceLocation("nationsgui", "emotes/icons/null.png"));
            ModernGui.drawModalRectWithCustomSizedTexture(this.menuX + pointX - 8, this.menuY + pointY - 8, 0, 0, 16, 16, 16.0f, 16.0f, false);
        }
        this.field_73886_k.func_78276_b(emote, (this.field_73880_f - this.field_73886_k.func_78256_a(emote)) / 2, (this.field_73881_g - this.field_73886_k.field_78288_b) / 2, 0xFFFFFF);
        GL11.glPopMatrix();
        super.func_73863_a(mouseX, mouseY, par3);
    }

    protected void func_73864_a(int mouseX, int mouseY, int par3) {
        super.func_73864_a(mouseX, mouseY, par3);
        for (Map.Entry<Integer, Integer[]> e : this.locs.entrySet()) {
            int diameter;
            int radius;
            int pointY;
            int id = e.getKey();
            Integer[] coords = e.getValue();
            int pointX = coords[2];
            if (!this.isInsideCircle(mouseX, mouseY, this.menuX + pointX, this.menuY + (pointY = coords[3].intValue()), radius = (diameter = 58) / 2)) continue;
            String anim = "";
            int index = 0;
            for (AbstractSkin skin : SkinType.EMOTES.getSkins()) {
                if (!ClientProxy.SKIN_MANAGER.playerHasSkin(Minecraft.func_71410_x().field_71439_g.field_71092_bJ, skin)) continue;
                if (index == id) {
                    anim = skin.getId();
                    break;
                }
                ++index;
            }
            ClientEmotesHandler.playEmote(anim.replaceAll("emotes_", ""), false);
            Minecraft.func_71410_x().func_71373_a(null);
        }
    }

    private void bindTexture(ResourceLocation res) {
        Minecraft.func_71410_x().field_71446_o.func_110577_a(res);
    }

    private boolean isInsideCircle(int mouseX, int mouseY, int pointX, int pointY, int radius) {
        return (mouseX - pointX) * (mouseX - pointX) + (mouseY - pointY) * (mouseY - pointY) <= radius * radius;
    }

    private boolean iconExist(ResourceLocation rl) {
        return EmotesGUI.class.getResourceAsStream("/assets/" + rl.func_110624_b() + "/" + rl.func_110623_a()) != null;
    }
}

