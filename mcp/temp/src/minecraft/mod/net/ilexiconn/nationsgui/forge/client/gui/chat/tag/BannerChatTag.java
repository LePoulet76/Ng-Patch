package net.ilexiconn.nationsgui.forge.client.gui.chat.tag;

import java.util.HashMap;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.chat.tag.AbstractChatTag;
import net.ilexiconn.nationsgui.forge.client.gui.chat.tag.BannerChatTag$Banner;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.impl.GetGroupAndPrimePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class BannerChatTag extends AbstractChatTag {

   private static final ResourceLocation TEXTURE_LOCATION_FR = new ResourceLocation("nationsgui", "textures/gui/chat_banners.png");
   private static final ResourceLocation TEXTURE_LOCATION_EN = new ResourceLocation("nationsgui", "textures/gui/chat_banners_en.png");
   private static final HashMap<String, BannerChatTag$Banner> BANNERS = new HashMap();
   private final BannerChatTag$Banner banner;


   public BannerChatTag(Map<String, String> parameters) throws Exception {
      super(parameters);
      String keySuffix = "";
      if(ClientProxy.currentServerName.equalsIgnoreCase("ruby")) {
         keySuffix = "_en";
      }

      String bannerId = ((String)parameters.get("id")).toLowerCase() + keySuffix;
      if(parameters.containsKey("playerName") && !bannerId.contains("_prime")) {
         String playerName = ((String)parameters.get("playerName")).replaceAll("[\u00a7&].", "");
         if(GetGroupAndPrimePacket.NGPRIME_PLAYERS.contains(playerName)) {
            bannerId = bannerId + "_prime";
         }
      }

      if(!BANNERS.containsKey(bannerId) && !BANNERS.containsKey(bannerId.replaceAll("_prime", ""))) {
         if(parameters.containsKey("type") && ((String)parameters.get("type")).equals("rank")) {
            this.banner = (BannerChatTag$Banner)BANNERS.get("rp");
         } else {
            this.banner = null;
         }
      } else {
         this.banner = BANNERS.containsKey(bannerId)?(BannerChatTag$Banner)BANNERS.get(bannerId):(BannerChatTag$Banner)BANNERS.get(bannerId.replaceAll("_prime", ""));
      }

   }

   public void render(int mouseX, int mouseY) {
      if(this.banner != null) {
         Minecraft.func_71410_x().func_110434_K().func_110577_a(!ClientProxy.currentServerName.equalsIgnoreCase("ruby")?TEXTURE_LOCATION_FR:TEXTURE_LOCATION_EN);
         GL11.glPushMatrix();
         GL11.glScalef(this.getScale(), this.getScale(), 0.0F);
         ModernGui.drawScaledCustomSizeModalRect(0.0F, -2.0F, (float)BannerChatTag$Banner.access$000(this.banner), (float)BannerChatTag$Banner.access$100(this.banner), BannerChatTag$Banner.access$200(this.banner), BannerChatTag$Banner.access$300(this.banner), BannerChatTag$Banner.access$200(this.banner), BannerChatTag$Banner.access$300(this.banner), 768.0F, 768.0F, false);
         GL11.glEnable(3042);
         GL11.glPopMatrix();
      }

   }

   public void onClick(int mouseX, int mouseY) {}

   public float getScale() {
      return this.banner == null?0.0F:10.0F / (float)BannerChatTag$Banner.access$300(this.banner);
   }

   public int getWidth() {
      return this.banner == null?0:(int)((float)BannerChatTag$Banner.access$200(this.banner) * this.getScale());
   }

   static {
      BANNERS.put("joueur", new BannerChatTag$Banner(0, 0, 70, 22));
      BANNERS.put("joueur_prime", new BannerChatTag$Banner(0, 22, 92, 22));
      BANNERS.put("ngprime", new BannerChatTag$Banner(0, 22, 92, 22));
      BANNERS.put("heros", new BannerChatTag$Banner(0, 44, 62, 22));
      BANNERS.put("heros_prime", new BannerChatTag$Banner(0, 66, 84, 22));
      BANNERS.put("legende", new BannerChatTag$Banner(0, 88, 80, 22));
      BANNERS.put("legende_prime", new BannerChatTag$Banner(0, 110, 102, 22));
      BANNERS.put("premium", new BannerChatTag$Banner(0, 132, 82, 22));
      BANNERS.put("premium_prime", new BannerChatTag$Banner(0, 154, 104, 22));
      BANNERS.put("affiliate", new BannerChatTag$Banner(0, 176, 70, 22));
      BANNERS.put("affiliate_prime", new BannerChatTag$Banner(0, 704, 92, 22));
      BANNERS.put("staff", new BannerChatTag$Banner(0, 198, 62, 22));
      BANNERS.put("staff_prime", new BannerChatTag$Banner(0, 682, 84, 22));
      BANNERS.put("moderateur_test", new BannerChatTag$Banner(0, 220, 74, 22));
      BANNERS.put("moderateur_test_prime", new BannerChatTag$Banner(0, 616, 96, 22));
      BANNERS.put("moderateur", new BannerChatTag$Banner(0, 242, 58, 22));
      BANNERS.put("moderateur_prime", new BannerChatTag$Banner(0, 638, 80, 22));
      BANNERS.put("moderateur_plus", new BannerChatTag$Banner(0, 264, 72, 22));
      BANNERS.put("moderateur_plus_prime", new BannerChatTag$Banner(0, 660, 94, 22));
      BANNERS.put("supermodo", new BannerChatTag$Banner(0, 286, 74, 22));
      BANNERS.put("supermodo_prime", new BannerChatTag$Banner(107, 594, 96, 22));
      BANNERS.put("guide", new BannerChatTag$Banner(0, 308, 60, 22));
      BANNERS.put("guide_prime", new BannerChatTag$Banner(107, 616, 82, 22));
      BANNERS.put("builder", new BannerChatTag$Banner(0, 330, 78, 22));
      BANNERS.put("builder_prime", new BannerChatTag$Banner(107, 638, 100, 22));
      BANNERS.put("admin", new BannerChatTag$Banner(0, 352, 68, 22));
      BANNERS.put("admin_prime", new BannerChatTag$Banner(107, 660, 90, 22));
      BANNERS.put("comm", new BannerChatTag$Banner(267, 408, 58, 22));
      BANNERS.put("comm_prime", new BannerChatTag$Banner(107, 682, 80, 22));
      BANNERS.put("radio", new BannerChatTag$Banner(267, 386, 58, 22));
      BANNERS.put("radio_prime", new BannerChatTag$Banner(0, 726, 80, 22));
      BANNERS.put("dev", new BannerChatTag$Banner(0, 374, 46, 22));
      BANNERS.put("respdesign", new BannerChatTag$Banner(0, 418, 83, 22));
      BANNERS.put("respadmin", new BannerChatTag$Banner(0, 440, 78, 22));
      BANNERS.put("respstaff", new BannerChatTag$Banner(0, 440, 78, 22));
      BANNERS.put("respgameplay", new BannerChatTag$Banner(0, 484, 98, 22));
      BANNERS.put("co-fonda", new BannerChatTag$Banner(0, 528, 82, 22));
      BANNERS.put("fondateur", new BannerChatTag$Banner(0, 550, 60, 22));
      BANNERS.put("rp", new BannerChatTag$Banner(0, 572, 38, 22));
      BANNERS.put("rp_prime", new BannerChatTag$Banner(0, 594, 60, 22));
      BANNERS.put("info", new BannerChatTag$Banner(147, 130, 56, 22));
      BANNERS.put("onu", new BannerChatTag$Banner(147, 394, 52, 22));
      BANNERS.put("event", new BannerChatTag$Banner(147, 350, 68, 22));
      BANNERS.put("atm", new BannerChatTag$Banner(147, 460, 54, 22));
      BANNERS.put("loto", new BannerChatTag$Banner(147, 438, 58, 22));
      BANNERS.put("mine", new BannerChatTag$Banner(147, 416, 64, 22));
      BANNERS.put("trade", new BannerChatTag$Banner(147, 372, 96, 22));
      BANNERS.put("announce", new BannerChatTag$Banner(147, 218, 86, 22));
      BANNERS.put("assault", new BannerChatTag$Banner(147, 284, 72, 22));
      BANNERS.put("shop", new BannerChatTag$Banner(147, 240, 92, 22));
      BANNERS.put("minus", new BannerChatTag$Banner(333, 274, 16, 16));
      BANNERS.put("plus", new BannerChatTag$Banner(359, 274, 16, 16));
      BANNERS.put("journaliste", new BannerChatTag$Banner(399, 0, 94, 22));
      BANNERS.put("journalist", new BannerChatTag$Banner(399, 0, 94, 22));
      BANNERS.put("avocat", new BannerChatTag$Banner(399, 22, 84, 22));
      BANNERS.put("player_en", new BannerChatTag$Banner(0, 0, 70, 22));
      BANNERS.put("player_prime_en", new BannerChatTag$Banner(0, 22, 92, 22));
      BANNERS.put("ngprime_en", new BannerChatTag$Banner(0, 22, 92, 22));
      BANNERS.put("heros_en", new BannerChatTag$Banner(0, 44, 54, 22));
      BANNERS.put("heros_prime_en", new BannerChatTag$Banner(0, 66, 76, 22));
      BANNERS.put("legende_en", new BannerChatTag$Banner(0, 88, 72, 22));
      BANNERS.put("legende_prime_en", new BannerChatTag$Banner(0, 110, 94, 22));
      BANNERS.put("premium_en", new BannerChatTag$Banner(0, 132, 82, 22));
      BANNERS.put("premium_prime_en", new BannerChatTag$Banner(0, 154, 104, 22));
      BANNERS.put("affiliate_en", new BannerChatTag$Banner(0, 176, 86, 22));
      BANNERS.put("affiliate_en_prime", new BannerChatTag$Banner(0, 704, 108, 22));
      BANNERS.put("staff_en", new BannerChatTag$Banner(0, 198, 62, 22));
      BANNERS.put("staff_en_prime", new BannerChatTag$Banner(0, 682, 84, 22));
      BANNERS.put("mod_test_en", new BannerChatTag$Banner(0, 220, 66, 22));
      BANNERS.put("mod_test_en_prime", new BannerChatTag$Banner(0, 616, 88, 22));
      BANNERS.put("mod_en", new BannerChatTag$Banner(0, 242, 50, 22));
      BANNERS.put("mod_en_prime", new BannerChatTag$Banner(0, 638, 72, 22));
      BANNERS.put("mod_plus_en", new BannerChatTag$Banner(0, 264, 64, 22));
      BANNERS.put("mod_plus_en_prime", new BannerChatTag$Banner(0, 660, 86, 22));
      BANNERS.put("supermod_en", new BannerChatTag$Banner(0, 286, 66, 22));
      BANNERS.put("supermod_en_prime", new BannerChatTag$Banner(107, 594, 88, 22));
      BANNERS.put("guide_en", new BannerChatTag$Banner(0, 308, 68, 22));
      BANNERS.put("guide_en_prime", new BannerChatTag$Banner(107, 616, 90, 22));
      BANNERS.put("builder_en", new BannerChatTag$Banner(0, 330, 78, 22));
      BANNERS.put("builder_en_prime", new BannerChatTag$Banner(107, 638, 100, 22));
      BANNERS.put("admin_en", new BannerChatTag$Banner(0, 352, 68, 22));
      BANNERS.put("admin_en_prime", new BannerChatTag$Banner(107, 660, 90, 22));
      BANNERS.put("dev_en", new BannerChatTag$Banner(0, 374, 46, 22));
      BANNERS.put("commmanager_en", new BannerChatTag$Banner(0, 396, 80, 22));
      BANNERS.put("adminmanager_en", new BannerChatTag$Banner(0, 440, 78, 22));
      BANNERS.put("staffmanager_en", new BannerChatTag$Banner(0, 440, 78, 22));
      BANNERS.put("gameplaymanager_en", new BannerChatTag$Banner(0, 484, 100, 22));
      BANNERS.put("co-founder_en", new BannerChatTag$Banner(0, 528, 98, 22));
      BANNERS.put("founder_en", new BannerChatTag$Banner(0, 550, 76, 22));
      BANNERS.put("rp_en", new BannerChatTag$Banner(0, 572, 38, 22));
      BANNERS.put("rp_prime_en", new BannerChatTag$Banner(0, 594, 60, 22));
      BANNERS.put("info_en", new BannerChatTag$Banner(147, 130, 56, 22));
      BANNERS.put("un_en", new BannerChatTag$Banner(147, 394, 46, 22));
      BANNERS.put("event_en", new BannerChatTag$Banner(147, 350, 68, 22));
      BANNERS.put("atm_en", new BannerChatTag$Banner(147, 460, 54, 22));
      BANNERS.put("loto_en", new BannerChatTag$Banner(147, 438, 66, 22));
      BANNERS.put("mine_en", new BannerChatTag$Banner(147, 416, 64, 22));
      BANNERS.put("trade_en", new BannerChatTag$Banner(147, 452, 64, 22));
      BANNERS.put("announce_en", new BannerChatTag$Banner(147, 218, 62, 22));
      BANNERS.put("assault_en", new BannerChatTag$Banner(147, 284, 78, 22));
      BANNERS.put("shop_en", new BannerChatTag$Banner(147, 240, 66, 22));
      BANNERS.put("minus_en", new BannerChatTag$Banner(333, 274, 16, 16));
      BANNERS.put("plus_en", new BannerChatTag$Banner(359, 274, 16, 16));
      BANNERS.put("journalist_en", new BannerChatTag$Banner(0, 160, 83, 16));
      BANNERS.put("lawyer_en", new BannerChatTag$Banner(0, 240, 73, 16));
   }
}
