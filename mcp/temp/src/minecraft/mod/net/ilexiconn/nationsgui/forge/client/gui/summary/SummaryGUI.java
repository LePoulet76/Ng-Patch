package net.ilexiconn.nationsgui.forge.client.gui.summary;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.net.URI;
import java.net.URISyntaxException;
import net.ilexiconn.nationsgui.forge.client.gui.CloseButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.GuiBrowser;
import net.ilexiconn.nationsgui.forge.client.gui.achievements.AchievementsGUI;
import net.ilexiconn.nationsgui.forge.client.gui.shop.ShopGUI;
import net.ilexiconn.nationsgui.forge.client.gui.summary.SummaryGUI$1;
import net.ilexiconn.nationsgui.forge.client.gui.summary.SummaryGUI$2;
import net.ilexiconn.nationsgui.forge.client.gui.summary.SummaryGUI$3;
import net.ilexiconn.nationsgui.forge.client.gui.summary.SummaryGUI$IButtonCallback;
import net.ilexiconn.nationsgui.forge.client.gui.summary.component.SummaryButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.summary.component.SummaryButtonGUI$Type;
import net.ilexiconn.nationsgui.forge.server.packet.PacketCallbacks;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AssistanceListingPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.RemoteOpenCatalogPacket;
import net.ilexiconn.nationsgui.forge.server.permission.PermissionCache;
import net.ilexiconn.nationsgui.forge.server.permission.PermissionType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class SummaryGUI extends GuiScreen {

   public static ResourceLocation TEXTURE = new ResourceLocation("nationsgui", "textures/gui/ingame.png");
   public SummaryGUI$IButtonCallback callback;
   public int currentTick;


   public void func_73866_w_() {
      int x = this.field_73880_f / 2 - 75;
      int y = this.field_73881_g / 2 - 96;
      this.callback = null;
      this.currentTick = 0;
      PermissionCache.INSTANCE.clearCache();
      PacketCallbacks.MONEY.send(new String[0]);
      this.field_73887_h.add(new CloseButtonGUI(0, x + 130, y + 14));
      GuiButton buttonShop = new GuiButton(1, x + 16, y + 46, 120, 20, StatCollector.func_74838_a("nationsgui.summary.shop"));
      buttonShop.field_73742_g = false;
      this.field_73887_h.add(buttonShop);
      PermissionCache.INSTANCE.checkPermission(PermissionType.SHOP, new SummaryGUI$1(this, buttonShop), new String[0]);
      this.field_73887_h.add(new GuiButton(2, x + 16, y + 74, 120, 20, StatCollector.func_74838_a("nationsgui.summary.achievements")));
      GuiButton buttonHelp = new GuiButton(4, x + 16, y + 103, 120, 20, StatCollector.func_74838_a("nationsgui.summary.help"));
      this.field_73887_h.add(buttonHelp);
      this.field_73887_h.add(new GuiButton(5, x + 16, y + 131, 57, 20, StatCollector.func_74838_a("nationsgui.summary.spawn")));
      this.field_73887_h.add(new GuiButton(10, x + 16 + 57 + 7, y + 131, 57, 20, StatCollector.func_74838_a("nationsgui.summary.wiki")));
      this.field_73887_h.add(new SummaryButtonGUI(6, x + 16, y + 162, SummaryButtonGUI$Type.SETTINGS));
      this.field_73887_h.add(new SummaryButtonGUI(7, x + 49, y + 162, SummaryButtonGUI$Type.DISCORD));
      this.field_73887_h.add(new SummaryButtonGUI(8, x + 82, y + 162, SummaryButtonGUI$Type.TEAMSPEAK));
      this.field_73887_h.add(new SummaryButtonGUI(9, x + 115, y + 162, SummaryButtonGUI$Type.LEAVE));
   }

   public void func_73876_c() {
      if(this.callback != null) {
         if(this.currentTick >= this.callback.getSeconds() * 20) {
            this.callback.call(this.field_73882_e);
            this.callback.getButton().field_73742_g = true;
            this.callback = null;
            this.currentTick = 0;
         }

         ++this.currentTick;
      } else {
         this.currentTick = 0;
      }

   }

   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
      int x = this.field_73880_f / 2 - 75;
      int y = this.field_73881_g / 2 - 96;
      this.func_73873_v_();
      this.field_73882_e.func_110434_K().func_110577_a(TEXTURE);
      this.func_73729_b(x + 13, y + 1, 0, 75, 34, 34);
      GuiInventory.func_110423_a(x + 31, y + 72, 32, (float)((-mouseX + x + 32) / 4), (float)((-mouseY + y + 32) / 4), this.field_73882_e.field_71439_g);
      this.field_73882_e.func_110434_K().func_110577_a(TEXTURE);
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, 0.0F, 400.0F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.func_73729_b(x, y - 7, 0, 0, 151, 53);

      for(int i = 0; i < 8; ++i) {
         this.func_73729_b(x + 6, y + 46 + 18 * i, 6, 53, 141, 18);
      }

      this.func_73729_b(x + 6, y + 188, 6, 71, 141, 4);
      this.field_73882_e.field_71466_p.func_78276_b(Minecraft.func_71410_x().field_71439_g.field_71092_bJ, x + 56, y + 9, 16777215);
      this.field_73882_e.field_71466_p.func_78276_b(EnumChatFormatting.GRAY.toString() + (int)ShopGUI.CURRENT_MONEY + "$", x + 56, y + 20, -1);
      if(this.currentTick != 0 && this.callback != null) {
         this.field_73882_e.field_71466_p.func_78276_b(EnumChatFormatting.GRAY.toString() + ((this.callback.getSeconds() * 20 - this.currentTick) / 20 + 1) + "s", x + 150, this.callback.getButton().field_73743_d + 6, 16777215);
      }

      super.func_73863_a(mouseX, mouseY, partialTicks);
      GL11.glPopMatrix();
   }

   protected void func_73875_a(GuiButton button) {
      if(button.field_73741_f == 0) {
         this.field_73882_e.func_71373_a((GuiScreen)null);
         this.field_73882_e.func_71381_h();
         this.field_73882_e.field_71416_A.func_82461_f();
      } else if(button.field_73741_f == 1) {
         PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new RemoteOpenCatalogPacket()));
      } else if(button.field_73741_f == 2) {
         try {
            this.field_73882_e.func_71373_a(new AchievementsGUI());
         } catch (Exception var5) {
            ;
         }
      } else if(button.field_73741_f == 4) {
         PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new AssistanceListingPacket()));
      } else if(button.field_73741_f == 5) {
         this.field_73882_e.field_71439_g.func_71165_d("/spawn");
         this.setCallback(new SummaryGUI$2(this, button));
      } else if(button.field_73741_f == 6) {
         this.field_73882_e.func_71373_a(new GuiOptions(this, this.field_73882_e.field_71474_y));
      } else if(button.field_73741_f == 7) {
         try {
            this.func_73896_a(new URI("https://discordapp.com/invite/GWBZeCf"));
         } catch (URISyntaxException var4) {
            var4.printStackTrace();
         }
      } else if(button.field_73741_f == 8) {
         try {
            this.func_73896_a(new URI("ts3server://ts.nationsglory.fr"));
         } catch (URISyntaxException var3) {
            var3.printStackTrace();
         }
      } else if(button.field_73741_f == 9) {
         this.setCallback(new SummaryGUI$3(this, button));
      } else if(button.field_73741_f == 10) {
         Minecraft.func_71410_x().func_71373_a(new GuiBrowser("https://wiki.nationsglory.fr/fr/"));
      }

   }

   public void setCallback(SummaryGUI$IButtonCallback callback) {
      if(this.callback != null) {
         this.callback.getButton().field_73742_g = true;
      }

      this.callback = callback;
      this.callback.getButton().field_73742_g = false;
      this.currentTick = 0;
   }

   public void func_73896_a(URI _uri) {
      try {
         Class t = Class.forName("java.awt.Desktop");
         Object theDesktop = t.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
         t.getMethod("browse", new Class[]{URI.class}).invoke(theDesktop, new Object[]{_uri});
      } catch (Throwable var4) {
         var4.printStackTrace();
      }

   }

}
