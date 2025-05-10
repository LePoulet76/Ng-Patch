package net.ilexiconn.nationsgui.forge.client.gui.auth.type;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
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
public class LoginType implements IAuthType
{
    public PasswordFieldGUI password;
    public GuiButton buttonLogin;

    public void init(int x, int y, AuthGUI gui, List<GuiButton> buttonList)
    {
        buttonList.add(new GuiButton(0, x + 13, y + 108, 100, 20, StatCollector.translateToLocal("nationsgui.auth.leave")));
        buttonList.add(this.buttonLogin = new GuiButton(1, x + 119, y + 108, 100, 20, StatCollector.translateToLocal("nationsgui.auth.login")));
        this.password = new PasswordFieldGUI(x + 13, y + 60, 206, 16);
        this.password.setFocused(true);
    }

    public void render(int x, int y, int mouseX, int mouseY, AuthGUI gui)
    {
        Minecraft.getMinecraft().fontRenderer.drawString(StatCollector.translateToLocal("nationsgui.auth.password"), x + 13, y + 50, 4210752);
        this.password.drawTextBox();
        this.buttonLogin.enabled = !this.password.getText().isEmpty();
    }

    public void update(int x, int y, AuthGUI gui)
    {
        this.password.updateCursorCounter();
    }

    public void mouseClicked(int x, int y, int mouseX, int mouseY, int button, AuthGUI gui)
    {
        this.password.mouseClicked(mouseX, mouseY, button);
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
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/login " + this.password.getText());
        }
    }

    public void onKeyPressed(char character, int key)
    {
        if (key == 28 && !this.password.getText().isEmpty())
        {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/login " + this.password.getText());
        }
        else
        {
            this.password.textboxKeyTyped(character, key);
        }
    }

    public void handleMouseInput() {}
}
