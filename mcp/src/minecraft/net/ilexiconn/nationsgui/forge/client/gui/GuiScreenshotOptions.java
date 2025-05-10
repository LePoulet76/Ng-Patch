package net.ilexiconn.nationsgui.forge.client.gui;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import net.ilexiconn.nationsgui.forge.server.config.NBTConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumOS;
import net.minecraft.util.Util;
import org.lwjgl.Sys;

public class GuiScreenshotOptions extends GuiScreen
{
    private GuiScreen previous;
    private GuiButton datedButton;

    public GuiScreenshotOptions(GuiScreen previous)
    {
        this.previous = previous;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(231, this.width / 2 - 157, this.height / 6 - 12, 150, 20, I18n.getString("options.screenshots.openScreenshot")));
        this.buttonList.add(this.datedButton = new GuiButton(541, this.width / 2 + 4, this.height / 6 - 12, 150, 20, I18n.getString("options.screenshots.datedScreenshot")));
        this.buttonList.add(new GuiButton(100, this.width / 2 - 100, this.height / 6 + 38, I18n.getString("gui.done")));
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, I18n.getString("options.screenshots"), this.width / 2, 15, 16777215);
        this.datedButton.displayString = I18n.getString("options.screenshots.datedScreenshot") + " " + (NBTConfig.CONFIG.getCompound().getBoolean("DatedScreenshot") ? I18n.getString("options.screenshots.datedScreenshot.dated") : I18n.getString("options.screenshots.datedScreenshot.notdated"));
        super.drawScreen(par1, par2, par3);
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton button)
    {
        super.actionPerformed(button);

        switch (button.id)
        {
            case 100:
                this.mc.displayGuiScreen(this.previous);
                break;

            case 231:
                File file = new File(Minecraft.getMinecraft().mcDataDir, "screenshots");
                String string = file.getAbsolutePath();

                if (Util.getOSType() == EnumOS.MACOS)
                {
                    try
                    {
                        this.mc.getLogAgent().logInfo(string);
                        Runtime.getRuntime().exec(new String[] {"/usr/bin/open", string});
                        return;
                    }
                    catch (IOException var9)
                    {
                        var9.printStackTrace();
                    }
                }
                else if (Util.getOSType() == EnumOS.WINDOWS)
                {
                    String bl = String.format("cmd.exe /C start \"Open file\" \"%s\"", new Object[] {string});

                    try
                    {
                        Runtime.getRuntime().exec(bl);
                        return;
                    }
                    catch (IOException var8)
                    {
                        var8.printStackTrace();
                    }
                }

                boolean bl1 = false;

                try
                {
                    Class throwable = Class.forName("java.awt.Desktop");
                    Object object = throwable.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
                    throwable.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {file.toURI()});
                }
                catch (Throwable var7)
                {
                    var7.printStackTrace();
                    bl1 = true;
                }

                if (bl1)
                {
                    this.mc.getLogAgent().logInfo("Opening via system class!");
                    Sys.openURL("file://" + string);
                }

                break;

            case 541:
                NBTConfig.CONFIG.getCompound().setBoolean("DatedScreenshot", !NBTConfig.CONFIG.getCompound().getBoolean("DatedScreenshot"));
        }
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        NBTConfig.CONFIG.save();
    }
}
