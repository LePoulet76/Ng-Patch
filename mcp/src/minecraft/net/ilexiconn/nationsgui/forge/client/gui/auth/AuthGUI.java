package net.ilexiconn.nationsgui.forge.client.gui.auth;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.CloseButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.FirstConnectionGui;
import net.ilexiconn.nationsgui.forge.client.gui.auth.type.AuthTypes;
import net.ilexiconn.nationsgui.forge.client.gui.auth.type.IAuthType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class AuthGUI extends GuiScreen
{
    public static final ResourceLocation TEXTURE = new ResourceLocation("nationsgui", "textures/gui/auth.png");
    private int ordinal;
    private IAuthType type;

    public AuthGUI(AuthTypes type)
    {
        this.ordinal = type.ordinal();
        this.type = type.getType();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        int x = this.width / 2 - 116;
        int y = this.height / 2 - 67;
        Keyboard.enableRepeatEvents(true);
        this.type.init(x, y, this, this.buttonList);
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        int x = this.width / 2 - 116;
        int y = this.height / 2 - 67;
        this.drawDefaultBackground();
        this.mc.getTextureManager().bindTexture(TEXTURE);
        this.drawTexturedModalRect(x, y, 0, 0, 233, 37);
        int v;

        for (v = 0; v < 4; ++v)
        {
            this.drawTexturedModalRect(x, y + 37 + v * 24, 0, 37, 233, 24);
        }

        this.drawTexturedModalRect(x, y + 133, 0, 61, 233, 5);
        v = 66 + this.ordinal * 36;

        if (this.mc.gameSettings.language.startsWith("fr_"))
        {
            v += 18;
        }

        this.drawTexturedModalRect(x + 11, y + 11, 0, v, 150, 18);
        this.type.render(x, y, mouseX, mouseY, this);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        int x = this.width / 2 - 116;
        int y = this.height / 2 - 67;
        this.type.update(x, y, this);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char character, int key)
    {
        if (key == 1)
        {
            Iterator var3 = this.buttonList.iterator();

            while (var3.hasNext())
            {
                Object button = var3.next();

                if (button instanceof CloseButtonGUI)
                {
                    return;
                }
            }

            this.buttonList.add(new CloseButtonGUI(2, 5, 5));
        }
        else
        {
            this.type.onKeyPressed(character, key);
        }
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput()
    {
        this.type.handleMouseInput();
        super.handleMouseInput();
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 2)
        {
            if (this.type.equals(AuthTypes.REGISTER.getType()) && ClientProxy.openFirstConnectionGuiAfterAuthMe)
            {
                Minecraft.getMinecraft().displayGuiScreen(new FirstConnectionGui(ClientProxy.currentServerName));
                ClientProxy.openFirstConnectionGuiAfterAuthMe = false;
            }
            else
            {
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
            }
        }
        else
        {
            this.type.actionPerformed(button, this);
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int button)
    {
        int x = this.width / 2 - 116;
        int y = this.height / 2 - 67;
        this.type.mouseClicked(x, y, mouseX, mouseY, button, this);
        super.mouseClicked(mouseX, mouseY, button);
    }

    public AuthTypes getType()
    {
        return AuthTypes.values()[this.ordinal];
    }
}
