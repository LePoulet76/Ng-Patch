package net.ilexiconn.nationsgui.forge.client.gui.auth.type;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.SnackbarGUI;
import net.ilexiconn.nationsgui.forge.client.gui.auth.AuthGUI;
import net.ilexiconn.nationsgui.forge.client.gui.auth.component.PasswordFieldGUI;
import net.ilexiconn.nationsgui.forge.client.gui.auth.type.IAuthType;
import net.ilexiconn.nationsgui.forge.client.gui.main.MainGUI;
import net.ilexiconn.nationsgui.forge.server.permission.PermissionCache;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.stats.StatList;
import net.minecraft.util.StatCollector;

@SideOnly(Side.CLIENT)
public class RegisterType implements IAuthType {

   public PasswordFieldGUI password;
   public PasswordFieldGUI passwordConfirm;
   public GuiButton buttonRegister;


   public void init(int x, int y, AuthGUI gui, List<GuiButton> buttonList) {
      buttonList.add(new GuiButton(0, x + 13, y + 108, 100, 20, StatCollector.func_74838_a("nationsgui.auth.leave")));
      buttonList.add(this.buttonRegister = new GuiButton(1, x + 119, y + 108, 100, 20, StatCollector.func_74838_a("nationsgui.auth.register")));
      this.password = new PasswordFieldGUI(x + 13, y + 50, 206, 16);
      this.passwordConfirm = new PasswordFieldGUI(x + 13, y + 80, 206, 16);
      this.password.func_73796_b(true);
   }

   public void render(int x, int y, int mouseX, int mouseY, AuthGUI gui) {
      Minecraft.func_71410_x().field_71466_p.func_78276_b(StatCollector.func_74838_a("nationsgui.auth.password"), x + 13, y + 40, 4210752);
      Minecraft.func_71410_x().field_71466_p.func_78276_b(StatCollector.func_74838_a("nationsgui.auth.password_confirm"), x + 13, y + 70, 4210752);
      this.password.func_73795_f();
      this.passwordConfirm.func_73795_f();
      this.buttonRegister.field_73742_g = !this.password.func_73781_b().isEmpty() && !this.passwordConfirm.func_73781_b().isEmpty();
   }

   public void update(int x, int y, AuthGUI gui) {
      this.password.func_73780_a();
      this.passwordConfirm.func_73780_a();
   }

   public void mouseClicked(int x, int y, int mouseX, int mouseY, int button, AuthGUI gui) {
      this.password.func_73793_a(mouseX, mouseY, button);
      this.passwordConfirm.func_73793_a(mouseX, mouseY, button);
   }

   public void actionPerformed(GuiButton button, AuthGUI gui) {
      if(button.field_73741_f == 0) {
         Minecraft.func_71410_x().field_71413_E.func_77450_a(StatList.field_75947_j, 1);
         Minecraft.func_71410_x().field_71441_e.func_72882_A();
         Minecraft.func_71410_x().func_71403_a((WorldClient)null);
         Minecraft.func_71410_x().func_71373_a(new MainGUI());
         PermissionCache.INSTANCE.clearCache();
      } else if(button.field_73741_f == 1) {
         if(this.password.func_73781_b().equals(this.passwordConfirm.func_73781_b())) {
            Minecraft.func_71410_x().field_71439_g.func_71165_d("/register " + this.password.func_73781_b() + " " + this.passwordConfirm.func_73781_b());
         } else {
            ClientProxy.SNACKBAR_LIST.add(new SnackbarGUI(StatCollector.func_74838_a("nationsgui.auth.no_match")));
         }
      }

   }

   public void onKeyPressed(char character, int key) {
      if(key == 28 && this.passwordConfirm.func_73806_l() && !this.password.func_73781_b().isEmpty() && !this.passwordConfirm.func_73781_b().isEmpty()) {
         if(this.password.func_73781_b().equals(this.passwordConfirm.func_73781_b())) {
            Minecraft.func_71410_x().field_71439_g.func_71165_d("/register " + this.password.func_73781_b() + " " + this.passwordConfirm.func_73781_b());
         } else {
            ClientProxy.SNACKBAR_LIST.add(new SnackbarGUI(StatCollector.func_74838_a("nationsgui.auth.no_match")));
         }
      } else if(key == 15 && this.password.func_73806_l()) {
         this.password.func_73796_b(false);
         this.passwordConfirm.func_73796_b(true);
      } else if(!this.password.func_73802_a(character, key)) {
         this.passwordConfirm.func_73802_a(character, key);
      }

   }

   public void handleMouseInput() {}
}
