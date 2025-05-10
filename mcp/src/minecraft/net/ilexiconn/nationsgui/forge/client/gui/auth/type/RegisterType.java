package net.ilexiconn.nationsgui.forge.client.gui.auth.type;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.SnackbarGUI;
import net.ilexiconn.nationsgui.forge.client.gui.auth.AuthGUI;
import net.ilexiconn.nationsgui.forge.client.gui.auth.component.PasswordFieldGUI;
import net.ilexiconn.nationsgui.forge.client.gui.main.MainGUI;
import net.ilexiconn.nationsgui.forge.server.permission.PermissionCache;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.stats.StatList;
import net.minecraft.util.StatCollector;

@SideOnly(Side.CLIENT)
public class RegisterType implements IAuthType
{
    public PasswordFieldGUI password;
    public PasswordFieldGUI passwordConfirm;
    public GuiButton buttonRegister;

    public void init(int x, int y, AuthGUI gui, List<GuiButton> buttonList)
    {
        buttonList.add(new GuiButton(0, x + 13, y + 108, 100, 20, StatCollector.translateToLocal("nationsgui.auth.leave")));
        buttonList.add(this.buttonRegister = new GuiButton(1, x + 119, y + 108, 100, 20, StatCollector.translateToLocal("nationsgui.auth.register")));
        this.password = new PasswordFieldGUI(x + 13, y + 50, 206, 16);
        this.passwordConfirm = new PasswordFieldGUI(x + 13, y + 80, 206, 16);
        this.password.setFocused(true);
    }

    public void render(int x, int y, int mouseX, int mouseY, AuthGUI gui)
    {
        Minecraft.getMinecraft().fontRenderer.drawString(StatCollector.translateToLocal("nationsgui.auth.password"), x + 13, y + 40, 4210752);
        Minecraft.getMinecraft().fontRenderer.drawString(StatCollector.translateToLocal("nationsgui.auth.password_confirm"), x + 13, y + 70, 4210752);
        this.password.drawTextBox();
        this.passwordConfirm.drawTextBox();
        this.buttonRegister.enabled = !this.password.getText().isEmpty() && !this.passwordConfirm.getText().isEmpty();
    }

    public void update(int x, int y, AuthGUI gui)
    {
        this.password.updateCursorCounter();
        this.passwordConfirm.updateCursorCounter();
    }

    public void mouseClicked(int x, int y, int mouseX, int mouseY, int button, AuthGUI gui)
    {
        this.password.mouseClicked(mouseX, mouseY, button);
        this.passwordConfirm.mouseClicked(mouseX, mouseY, button);
    }

    public void actionPerformed(GuiButton button, AuthGUI gui)
    {
        if (button.id == 0)
        {
            Minecraft.getMinecraft().statFileWriter.readStat(StatList.leaveGameStat, 1);
            Minecraft.getMinecraft().theWorld.sendQuittingDisconnectingPacket();
            Minecraft.getMinecraft().loadWorld((WorldClient)null);
            Minecraft.getMinecraft().displayGuiScreen(new MainGUI());
            PermissionCache.INSTANCE.clearCache();
        }
        else if (button.id == 1)
        {
            if (this.password.getText().equals(this.passwordConfirm.getText()))
            {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/register " + this.password.getText() + " " + this.passwordConfirm.getText());
            }
            else
            {
                ClientProxy.SNACKBAR_LIST.add(new SnackbarGUI(StatCollector.translateToLocal("nationsgui.auth.no_match")));
            }
        }
    }

    public void onKeyPressed(char character, int key)
    {
        if (key == 28 && this.passwordConfirm.isFocused() && !this.password.getText().isEmpty() && !this.passwordConfirm.getText().isEmpty())
        {
            if (this.password.getText().equals(this.passwordConfirm.getText()))
            {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/register " + this.password.getText() + " " + this.passwordConfirm.getText());
            }
            else
            {
                ClientProxy.SNACKBAR_LIST.add(new SnackbarGUI(StatCollector.translateToLocal("nationsgui.auth.no_match")));
            }
        }
        else if (key == 15 && this.password.isFocused())
        {
            this.password.setFocused(false);
            this.passwordConfirm.setFocused(true);
        }
        else if (!this.password.textboxKeyTyped(character, key))
        {
            this.passwordConfirm.textboxKeyTyped(character, key);
        }
    }

    public void handleMouseInput() {}
}
