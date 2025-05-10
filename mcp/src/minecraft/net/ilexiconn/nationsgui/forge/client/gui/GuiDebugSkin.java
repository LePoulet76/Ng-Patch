package net.ilexiconn.nationsgui.forge.client.gui;

import net.ilexiconn.nationsgui.forge.client.gui.GuiDebugSkin$DebugTextField;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.BadgeSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.CapeSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.SkinType;
import net.ilexiconn.nationsgui.forge.client.util.Transform;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.opengl.GL11;

public class GuiDebugSkin extends GuiScreen
{
    public static final int COLLUMNS = 30;
    private static final int TILE_SIZE = 25;
    private static final int TILE_OFFSET = 2;
    private GuiTextField fieldX;
    private GuiTextField fieldY;
    private GuiTextField fieldZ;
    private GuiTextField rotateX;
    private GuiTextField rotateY;
    private GuiTextField rotateZ;
    private GuiTextField scale;
    private Transform transform;
    private String selectedSkin;

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.fieldX = new GuiDebugSkin$DebugTextField(this.fontRenderer, 25, this.height - 25, 50, 15, "oX");
        this.fieldX.setText("0.0");
        this.fieldY = new GuiDebugSkin$DebugTextField(this.fontRenderer, 85, this.height - 25, 50, 15, "oY");
        this.fieldY.setText("0.0");
        this.fieldZ = new GuiDebugSkin$DebugTextField(this.fontRenderer, 145, this.height - 25, 50, 15, "oZ");
        this.fieldZ.setText("0.0");
        this.rotateX = new GuiDebugSkin$DebugTextField(this.fontRenderer, 205, this.height - 25, 50, 15, "rX");
        this.rotateX.setText("0.0");
        this.rotateY = new GuiDebugSkin$DebugTextField(this.fontRenderer, 265, this.height - 25, 50, 15, "rY");
        this.rotateY.setText("0.0");
        this.rotateZ = new GuiDebugSkin$DebugTextField(this.fontRenderer, 325, this.height - 25, 50, 15, "rZ");
        this.rotateZ.setText("0.0");
        this.scale = new GuiDebugSkin$DebugTextField(this.fontRenderer, 385, this.height - 25, 50, 15, "scale");
        this.scale.setText("1.0");
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        int i = 0;
        SkinType[] var5 = SkinType.values();
        int var6 = var5.length;

        for (int var7 = 0; var7 < var6; ++var7)
        {
            SkinType value = var5[var7];
            AbstractSkin[] var9 = value.getSkins();
            int var10 = var9.length;

            for (int var11 = 0; var11 < var10; ++var11)
            {
                AbstractSkin skin = var9[var11];

                if (!(skin instanceof BadgeSkin) && !(skin instanceof CapeSkin))
                {
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    boolean active = skin.getTransform("gui") == this.transform;
                    int rX = i % 30 * 27;
                    int rY = 25 + i / 30 * 27;
                    GL11.glPushMatrix();
                    GL11.glTranslatef(0.0F, 0.0F, -50.0F);
                    drawRect(rX, rY, rX + 25, rY + 25, active ? -65451 : 1358888960);
                    GL11.glPopMatrix();
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    skin.renderInGUI(i % 30 * 27, 25 + i / 30 * 27, 1.0F, par3);
                    ++i;
                }
            }
        }

        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 0.0F, 500.0F);
        this.fieldX.drawTextBox();
        this.fieldY.drawTextBox();
        this.fieldZ.drawTextBox();
        this.rotateX.drawTextBox();
        this.rotateY.drawTextBox();
        this.rotateZ.drawTextBox();
        this.scale.drawTextBox();
        GL11.glPopMatrix();

        if (this.selectedSkin != null)
        {
            ModernGui.drawScaledString(this.selectedSkin, 445, this.height - 25, 16777215, 1.0F, false, true);
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        super.keyTyped(par1, par2);
        this.fieldX.textboxKeyTyped(par1, par2);
        this.fieldY.textboxKeyTyped(par1, par2);
        this.fieldZ.textboxKeyTyped(par1, par2);
        this.rotateX.textboxKeyTyped(par1, par2);
        this.rotateY.textboxKeyTyped(par1, par2);
        this.rotateZ.textboxKeyTyped(par1, par2);
        this.scale.textboxKeyTyped(par1, par2);

        if (this.transform != null)
        {
            try
            {
                this.transform.setOffsetX(Double.parseDouble(this.fieldX.getText()));
                this.transform.setOffsetY(Double.parseDouble(this.fieldY.getText()));
                this.transform.setOffsetZ(Double.parseDouble(this.fieldZ.getText()));
                this.transform.setRotateX(Double.parseDouble(this.rotateX.getText()));
                this.transform.setRotateY(Double.parseDouble(this.rotateY.getText()));
                this.transform.setRotateZ(Double.parseDouble(this.rotateZ.getText()));
                this.transform.setScale(Double.parseDouble(this.scale.getText()));
            }
            catch (NumberFormatException var4)
            {
                ;
            }
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);
        this.fieldX.mouseClicked(par1, par2, par3);
        this.fieldY.mouseClicked(par1, par2, par3);
        this.fieldZ.mouseClicked(par1, par2, par3);
        this.rotateX.mouseClicked(par1, par2, par3);
        this.rotateY.mouseClicked(par1, par2, par3);
        this.rotateZ.mouseClicked(par1, par2, par3);
        this.scale.mouseClicked(par1, par2, par3);
        int i = 0;
        SkinType[] var5 = SkinType.values();
        int var6 = var5.length;

        for (int var7 = 0; var7 < var6; ++var7)
        {
            SkinType value = var5[var7];
            AbstractSkin[] var9 = value.getSkins();
            int var10 = var9.length;

            for (int var11 = 0; var11 < var10; ++var11)
            {
                AbstractSkin skin = var9[var11];

                if (!(skin instanceof BadgeSkin) && !(skin instanceof CapeSkin))
                {
                    int x = i % 30 * 27;
                    int y = 25 + i / 30 * 27;

                    if (par1 >= x && par1 <= x + 25 && par2 >= y && par2 <= y + 25 && par2 < this.height - 25)
                    {
                        this.transform = skin.getTransform("gui");
                        this.fieldX.setText(Double.toString(this.transform.getOffsetX()));
                        this.fieldY.setText(Double.toString(this.transform.getOffsetY()));
                        this.fieldZ.setText(Double.toString(this.transform.getOffsetZ()));
                        this.rotateX.setText(Double.toString(this.transform.getRotateX()));
                        this.rotateY.setText(Double.toString(this.transform.getRotateY()));
                        this.rotateZ.setText(Double.toString(this.transform.getRotateZ()));
                        this.scale.setText(Double.toString(this.transform.getScale()));
                        this.selectedSkin = skin.getId();
                    }

                    ++i;
                }
            }
        }
    }
}
