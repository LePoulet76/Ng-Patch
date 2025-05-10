package net.ilexiconn.nationsgui.forge.client.gui.auth.type;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.gui.auth.AuthGUI;
import net.minecraft.client.gui.GuiButton;

@SideOnly(Side.CLIENT)
public interface IAuthType
{
    void init(int var1, int var2, AuthGUI var3, List<GuiButton> var4);

    void render(int var1, int var2, int var3, int var4, AuthGUI var5);

    void update(int var1, int var2, AuthGUI var3);

    void mouseClicked(int var1, int var2, int var3, int var4, int var5, AuthGUI var6);

    void actionPerformed(GuiButton var1, AuthGUI var2);

    void onKeyPressed(char var1, int var2);

    void handleMouseInput();
}
