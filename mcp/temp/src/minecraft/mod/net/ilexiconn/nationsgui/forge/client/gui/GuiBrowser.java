package net.ilexiconn.nationsgui.forge.client.gui;

import fr.nationsglory.ngbrowser.client.gui.IBrowserGuiScreen;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.override.GenericOverride;
import net.minecraft.client.gui.GuiScreen;

public class GuiBrowser extends GuiScreen implements IBrowserGuiScreen {

   private fr.nationsglory.ngbrowser.client.gui.GuiBrowser browser;
   private final String url;
   private String hoveredAction = "";


   public GuiBrowser(String url) {
      this.url = url;
   }

   public void func_73866_w_() {
      if(this.browser == null) {
         this.browser = new fr.nationsglory.ngbrowser.client.gui.GuiBrowser(this.url, 0, 0, 0, 0);
      }

      this.browser.setSize(this.field_73880_f, this.field_73881_g);
      this.browser.setVolume(1.0F);
   }

   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
      this.hoveredAction = "";
      this.browser.draw();
      ClientEventHandler.STYLE.bindTexture("overlay_main");
      boolean hoveringClose = mouseX >= this.field_73880_f - 25 && mouseX < this.field_73880_f - 25 + 14 && mouseY >= 10 && mouseY < 24;
      ModernGui.drawScaledCustomSizeModalRect((float)(this.field_73880_f - 25), 10.0F, (float)(1658 * GenericOverride.GUI_SCALE), (float)((hoveringClose?215:153) * GenericOverride.GUI_SCALE), 52 * GenericOverride.GUI_SCALE, 52 * GenericOverride.GUI_SCALE, 14, 14, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), false);
      if(hoveringClose) {
         this.hoveredAction = "close";
      }

   }

   public void func_73876_c() {
      this.browser.update();
   }

   public void func_73867_d() {
      super.func_73867_d();
      this.browser.handleMouseInput();
   }

   public void func_73860_n() {
      super.func_73860_n();
      this.browser.handleKeyboardInput();
   }

   public void func_73874_b() {
      this.browser.close();
      super.func_73874_b();
   }

   public fr.nationsglory.ngbrowser.client.gui.GuiBrowser getBrowser() {
      return this.browser;
   }

   public void closeRequestedByBrowser() {
      this.field_73882_e.func_71373_a((GuiScreen)null);
   }

   public boolean func_73868_f() {
      return true;
   }

   public void func_73864_a(int x, int y, int button) {
      if(button == 0 && this.hoveredAction.equals("close")) {
         this.field_73882_e.func_71373_a((GuiScreen)null);
      }

      super.func_73864_a(x, y, button);
   }
}
