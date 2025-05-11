/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.stats.StatList
 *  net.minecraft.util.StatCollector
 */
package net.ilexiconn.nationsgui.forge.client.gui.auth.type;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.gui.auth.AuthGUI;
import net.ilexiconn.nationsgui.forge.client.gui.auth.component.PasswordFieldGUI;
import net.ilexiconn.nationsgui.forge.client.gui.auth.type.IAuthType;
import net.ilexiconn.nationsgui.forge.client.gui.main.MainGUI;
import net.ilexiconn.nationsgui.forge.server.permission.PermissionCache;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.stats.StatList;
import net.minecraft.util.StatCollector;

@SideOnly(value=Side.CLIENT)
public class LoginType
implements IAuthType {
    public PasswordFieldGUI password;
    public GuiButton buttonLogin;

    @Override
    public void init(int x, int y, AuthGUI gui, List<GuiButton> buttonList) {
        buttonList.add(new GuiButton(0, x + 13, y + 108, 100, 20, StatCollector.func_74838_a((String)"nationsgui.auth.leave")));
        this.buttonLogin = new GuiButton(1, x + 119, y + 108, 100, 20, StatCollector.func_74838_a((String)"nationsgui.auth.login"));
        buttonList.add(this.buttonLogin);
        this.password = new PasswordFieldGUI(x + 13, y + 60, 206, 16);
        this.password.func_73796_b(true);
    }

    @Override
    public void render(int x, int y, int mouseX, int mouseY, AuthGUI gui) {
        Minecraft.func_71410_x().field_71466_p.func_78276_b(StatCollector.func_74838_a((String)"nationsgui.auth.password"), x + 13, y + 50, 0x404040);
        this.password.func_73795_f();
        this.buttonLogin.field_73742_g = !this.password.func_73781_b().isEmpty();
    }

    @Override
    public void update(int x, int y, AuthGUI gui) {
        this.password.func_73780_a();
    }

    @Override
    public void mouseClicked(int x, int y, int mouseX, int mouseY, int button, AuthGUI gui) {
        this.password.func_73793_a(mouseX, mouseY, button);
    }

    @Override
    public void actionPerformed(GuiButton button, AuthGUI gui) {
        if (button.field_73741_f == 0) {
            Minecraft.func_71410_x().field_71413_E.func_77450_a(StatList.field_75947_j, 1);
            Minecraft.func_71410_x().field_71441_e.func_72882_A();
            Minecraft.func_71410_x().func_71403_a(null);
            Minecraft.func_71410_x().func_71373_a((GuiScreen)new MainGUI());
            PermissionCache.INSTANCE.clearCache();
        } else if (button.field_73741_f == 1) {
            Minecraft.func_71410_x().field_71439_g.func_71165_d("/login " + this.password.func_73781_b());
        }
    }

    @Override
    public void onKeyPressed(char character, int key) {
        if (key == 28 && !this.password.func_73781_b().isEmpty()) {
            Minecraft.func_71410_x().field_71439_g.func_71165_d("/login " + this.password.func_73781_b());
        } else {
            this.password.func_73802_a(character, key);
        }
    }

    @Override
    public void handleMouseInput() {
    }
}

