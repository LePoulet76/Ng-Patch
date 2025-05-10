package net.ilexiconn.nationsgui.forge.client.gui;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import net.ilexiconn.nationsgui.forge.client.gui.FirstConnectionChoiceGui$Button;
import net.ilexiconn.nationsgui.forge.client.gui.FirstConnectionChoiceGui$Data;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class FirstConnectionChoiceGui extends AbstractFirstConnectionGui
{
    private FirstConnectionChoiceGui$Data data;

    public FirstConnectionChoiceGui(String serverName)
    {
        try
        {
            this.data = (FirstConnectionChoiceGui$Data)(new Gson()).fromJson(new InputStreamReader((new URL("http://api.nationsglory.fr/factions_list.php?server=" + serverName)).openStream(), Charsets.UTF_8), FirstConnectionChoiceGui$Data.class);
        }
        catch (IOException var3)
        {
            var3.printStackTrace();
        }
    }

    public boolean canBeOpen()
    {
        return !FirstConnectionChoiceGui$Data.access$000(this.data).isEmpty() || !FirstConnectionChoiceGui$Data.access$100(this.data).isEmpty();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.buttonList.add(new FirstConnectionChoiceGui$Button(this, 0, this.width / 2 - 75, this.height / 2 - 30, "Rejoindre un pays existant"));
        FirstConnectionChoiceGui$Button button = new FirstConnectionChoiceGui$Button(this, 1, this.width / 2 - 75, this.height / 2 - 10, "Cr\u00e9er mon propre pays");
        button.enabled = !FirstConnectionChoiceGui$Data.access$000(this.data).isEmpty();
        this.buttonList.add(button);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        super.drawScreen(par1, par2, par3);
        String text = "Il est temps de faire ton choix :";
        this.mc.fontRenderer.drawString(text, this.width / 2 - 90, this.height / 2 - 40, -1);
        this.mc.getTextureManager().bindTexture(BACKGROUND);
        GL11.glPushMatrix();
        float scale = 0.375F;
        GL11.glTranslatef((float)(this.width / 2) - 112.0F * scale / 2.0F, (float)(this.height / 2 + 64) - 144.0F * scale - 2.0F, 0.0F);
        GL11.glScalef(scale, scale, 1.0F);
        this.drawTexturedModalRect(0, 0, 40, 112, 216, 144);
        GL11.glPopMatrix();
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        super.actionPerformed(par1GuiButton);

        if (par1GuiButton.id == 0 || par1GuiButton.id == 1)
        {
            this.mc.displayGuiScreen(new FactionSelectGui(par1GuiButton.id == 1, this.data));
        }
    }

    static Minecraft access$200(FirstConnectionChoiceGui x0)
    {
        return x0.mc;
    }

    static Minecraft access$300(FirstConnectionChoiceGui x0)
    {
        return x0.mc;
    }
}
