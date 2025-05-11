/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.gui.GuiButton
 */
package net.ilexiconn.nationsgui.forge.client.gui.auth.type;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.gui.auth.AuthGUI;
import net.minecraft.client.gui.GuiButton;

@SideOnly(value=Side.CLIENT)
public interface IAuthType {
    public void init(int var1, int var2, AuthGUI var3, List<GuiButton> var4);

    public void render(int var1, int var2, int var3, int var4, AuthGUI var5);

    public void update(int var1, int var2, AuthGUI var3);

    public void mouseClicked(int var1, int var2, int var3, int var4, int var5, AuthGUI var6);

    public void actionPerformed(GuiButton var1, AuthGUI var2);

    public void onKeyPressed(char var1, int var2);

    public void handleMouseInput();
}

