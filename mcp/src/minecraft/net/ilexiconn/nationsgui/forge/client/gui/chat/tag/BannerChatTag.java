/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.chat.tag;

import java.util.HashMap;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.chat.tag.AbstractChatTag;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.impl.GetGroupAndPrimePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class BannerChatTag
extends AbstractChatTag {
    private static final ResourceLocation TEXTURE_LOCATION_FR = new ResourceLocation("nationsgui", "textures/gui/chat_banners.png");
    private static final ResourceLocation TEXTURE_LOCATION_EN = new ResourceLocation("nationsgui", "textures/gui/chat_banners_en.png");
    private static final HashMap<String, Banner> BANNERS = new HashMap();
    private final Banner banner;

    public BannerChatTag(Map<String, String> parameters) throws Exception {
        super(parameters);
        String playerName;
        String keySuffix = "";
        if (ClientProxy.currentServerName.equalsIgnoreCase("ruby")) {
            keySuffix = "_en";
        }
        String bannerId = parameters.get("id").toLowerCase() + keySuffix;
        if (parameters.containsKey("playerName") && !bannerId.contains("_prime") && GetGroupAndPrimePacket.NGPRIME_PLAYERS.contains(playerName = parameters.get("playerName").replaceAll("[\u00a7&].", ""))) {
            bannerId = bannerId + "_prime";
        }
        this.banner = BANNERS.containsKey(bannerId) || BANNERS.containsKey(bannerId.replaceAll("_prime", "")) ? (BANNERS.containsKey(bannerId) ? BANNERS.get(bannerId) : BANNERS.get(bannerId.replaceAll("_prime", ""))) : (parameters.containsKey("type") && parameters.get("type").equals("rank") ? BANNERS.get("rp") : null);
    }

    @Override
    public void render(int mouseX, int mouseY) {
        if (this.banner != null) {
            Minecraft.func_71410_x().func_110434_K().func_110577_a(!ClientProxy.currentServerName.equalsIgnoreCase("ruby") ? TEXTURE_LOCATION_FR : TEXTURE_LOCATION_EN);
            GL11.glPushMatrix();
            GL11.glScalef((float)this.getScale(), (float)this.getScale(), (float)0.0f);
            ModernGui.drawScaledCustomSizeModalRect(0.0f, -2.0f, this.banner.u, this.banner.v, this.banner.width, this.banner.height, this.banner.width, this.banner.height, 768.0f, 768.0f, false);
            GL11.glEnable((int)3042);
            GL11.glPopMatrix();
        }
    }

    @Override
    public void onClick(int mouseX, int mouseY) {
    }

    public float getScale() {
        if (this.banner == null) {
            return 0.0f;
        }
        return 10.0f / (float)this.banner.height;
    }

    @Override
    public int getWidth() {
        if (this.banner == null) {
            return 0;
        }
        return (int)((float)this.banner.width * this.getScale());
    }

    static {
        BANNERS.put("joueur", new Banner(0, 0, 70, 22));
        BANNERS.put("joueur_prime", new Banner(0, 22, 92, 22));
        BANNERS.put("ngprime", new Banner(0, 22, 92, 22));
        BANNERS.put("heros", new Banner(0, 44, 62, 22));
        BANNERS.put("heros_prime", new Banner(0, 66, 84, 22));
        BANNERS.put("legende", new Banner(0, 88, 80, 22));
        BANNERS.put("legende_prime", new Banner(0, 110, 102, 22));
        BANNERS.put("premium", new Banner(0, 132, 82, 22));
        BANNERS.put("premium_prime", new Banner(0, 154, 104, 22));
        BANNERS.put("affiliate", new Banner(0, 176, 70, 22));
        BANNERS.put("affiliate_prime", new Banner(0, 704, 92, 22));
        BANNERS.put("staff", new Banner(0, 198, 62, 22));
        BANNERS.put("staff_prime", new Banner(0, 682, 84, 22));
        BANNERS.put("moderateur_test", new Banner(0, 220, 74, 22));
        BANNERS.put("moderateur_test_prime", new Banner(0, 616, 96, 22));
        BANNERS.put("moderateur", new Banner(0, 242, 58, 22));
        BANNERS.put("moderateur_prime", new Banner(0, 638, 80, 22));
        BANNERS.put("moderateur_plus", new Banner(0, 264, 72, 22));
        BANNERS.put("moderateur_plus_prime", new Banner(0, 660, 94, 22));
        BANNERS.put("supermodo", new Banner(0, 286, 74, 22));
        BANNERS.put("supermodo_prime", new Banner(107, 594, 96, 22));
        BANNERS.put("guide", new Banner(0, 308, 60, 22));
        BANNERS.put("guide_prime", new Banner(107, 616, 82, 22));
        BANNERS.put("builder", new Banner(0, 330, 78, 22));
        BANNERS.put("builder_prime", new Banner(107, 638, 100, 22));
        BANNERS.put("admin", new Banner(0, 352, 68, 22));
        BANNERS.put("admin_prime", new Banner(107, 660, 90, 22));
        BANNERS.put("comm", new Banner(267, 408, 58, 22));
        BANNERS.put("comm_prime", new Banner(107, 682, 80, 22));
        BANNERS.put("radio", new Banner(267, 386, 58, 22));
        BANNERS.put("radio_prime", new Banner(0, 726, 80, 22));
        BANNERS.put("dev", new Banner(0, 374, 46, 22));
        BANNERS.put("respdesign", new Banner(0, 418, 83, 22));
        BANNERS.put("respadmin", new Banner(0, 440, 78, 22));
        BANNERS.put("respstaff", new Banner(0, 440, 78, 22));
        BANNERS.put("respgameplay", new Banner(0, 484, 98, 22));
        BANNERS.put("co-fonda", new Banner(0, 528, 82, 22));
        BANNERS.put("fondateur", new Banner(0, 550, 60, 22));
        BANNERS.put("rp", new Banner(0, 572, 38, 22));
        BANNERS.put("rp_prime", new Banner(0, 594, 60, 22));
        BANNERS.put("info", new Banner(147, 130, 56, 22));
        BANNERS.put("onu", new Banner(147, 394, 52, 22));
        BANNERS.put("event", new Banner(147, 350, 68, 22));
        BANNERS.put("atm", new Banner(147, 460, 54, 22));
        BANNERS.put("loto", new Banner(147, 438, 58, 22));
        BANNERS.put("mine", new Banner(147, 416, 64, 22));
        BANNERS.put("trade", new Banner(147, 372, 96, 22));
        BANNERS.put("announce", new Banner(147, 218, 86, 22));
        BANNERS.put("assault", new Banner(147, 284, 72, 22));
        BANNERS.put("shop", new Banner(147, 240, 92, 22));
        BANNERS.put("minus", new Banner(333, 274, 16, 16));
        BANNERS.put("plus", new Banner(359, 274, 16, 16));
        BANNERS.put("journaliste", new Banner(399, 0, 94, 22));
        BANNERS.put("journalist", new Banner(399, 0, 94, 22));
        BANNERS.put("avocat", new Banner(399, 22, 84, 22));
        BANNERS.put("player_en", new Banner(0, 0, 70, 22));
        BANNERS.put("player_prime_en", new Banner(0, 22, 92, 22));
        BANNERS.put("ngprime_en", new Banner(0, 22, 92, 22));
        BANNERS.put("heros_en", new Banner(0, 44, 54, 22));
        BANNERS.put("heros_prime_en", new Banner(0, 66, 76, 22));
        BANNERS.put("legende_en", new Banner(0, 88, 72, 22));
        BANNERS.put("legende_prime_en", new Banner(0, 110, 94, 22));
        BANNERS.put("premium_en", new Banner(0, 132, 82, 22));
        BANNERS.put("premium_prime_en", new Banner(0, 154, 104, 22));
        BANNERS.put("affiliate_en", new Banner(0, 176, 86, 22));
        BANNERS.put("affiliate_en_prime", new Banner(0, 704, 108, 22));
        BANNERS.put("staff_en", new Banner(0, 198, 62, 22));
        BANNERS.put("staff_en_prime", new Banner(0, 682, 84, 22));
        BANNERS.put("mod_test_en", new Banner(0, 220, 66, 22));
        BANNERS.put("mod_test_en_prime", new Banner(0, 616, 88, 22));
        BANNERS.put("mod_en", new Banner(0, 242, 50, 22));
        BANNERS.put("mod_en_prime", new Banner(0, 638, 72, 22));
        BANNERS.put("mod_plus_en", new Banner(0, 264, 64, 22));
        BANNERS.put("mod_plus_en_prime", new Banner(0, 660, 86, 22));
        BANNERS.put("supermod_en", new Banner(0, 286, 66, 22));
        BANNERS.put("supermod_en_prime", new Banner(107, 594, 88, 22));
        BANNERS.put("guide_en", new Banner(0, 308, 68, 22));
        BANNERS.put("guide_en_prime", new Banner(107, 616, 90, 22));
        BANNERS.put("builder_en", new Banner(0, 330, 78, 22));
        BANNERS.put("builder_en_prime", new Banner(107, 638, 100, 22));
        BANNERS.put("admin_en", new Banner(0, 352, 68, 22));
        BANNERS.put("admin_en_prime", new Banner(107, 660, 90, 22));
        BANNERS.put("dev_en", new Banner(0, 374, 46, 22));
        BANNERS.put("commmanager_en", new Banner(0, 396, 80, 22));
        BANNERS.put("adminmanager_en", new Banner(0, 440, 78, 22));
        BANNERS.put("staffmanager_en", new Banner(0, 440, 78, 22));
        BANNERS.put("gameplaymanager_en", new Banner(0, 484, 100, 22));
        BANNERS.put("co-founder_en", new Banner(0, 528, 98, 22));
        BANNERS.put("founder_en", new Banner(0, 550, 76, 22));
        BANNERS.put("rp_en", new Banner(0, 572, 38, 22));
        BANNERS.put("rp_prime_en", new Banner(0, 594, 60, 22));
        BANNERS.put("info_en", new Banner(147, 130, 56, 22));
        BANNERS.put("un_en", new Banner(147, 394, 46, 22));
        BANNERS.put("event_en", new Banner(147, 350, 68, 22));
        BANNERS.put("atm_en", new Banner(147, 460, 54, 22));
        BANNERS.put("loto_en", new Banner(147, 438, 66, 22));
        BANNERS.put("mine_en", new Banner(147, 416, 64, 22));
        BANNERS.put("trade_en", new Banner(147, 452, 64, 22));
        BANNERS.put("announce_en", new Banner(147, 218, 62, 22));
        BANNERS.put("assault_en", new Banner(147, 284, 78, 22));
        BANNERS.put("shop_en", new Banner(147, 240, 66, 22));
        BANNERS.put("minus_en", new Banner(333, 274, 16, 16));
        BANNERS.put("plus_en", new Banner(359, 274, 16, 16));
        BANNERS.put("journalist_en", new Banner(0, 160, 83, 16));
        BANNERS.put("lawyer_en", new Banner(0, 240, 73, 16));
    }

    public static class Banner {
        private final int u;
        private final int v;
        private final int width;
        private final int height;

        public Banner(int u, int v, int width, int height) {
            this.u = u;
            this.v = v;
            this.width = width;
            this.height = height;
        }
    }
}

