package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseCreateGui$1;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseCreateGui$2;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseCreateDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseCreatePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class EnterpriseCreateGui extends GuiScreen
{
    public static final List<GuiScreenTab> TABS = new ArrayList();
    protected int xSize = 319;
    protected int ySize = 250;
    private int guiLeft;
    private int guiTop;
    private RenderItem itemRenderer = new RenderItem();
    public static boolean loaded = false;
    private GuiScrollBarFaction scrollBar;
    boolean typesExpanded = false;
    private String selectedType = "";
    private String hoveredType = "";
    private GuiButton createButton;
    private GuiTextField enterpriseNameField;
    public static HashMap<String, Object> createInfos = new HashMap();
    public static String errorMessage;

    public EnterpriseCreateGui()
    {
        loaded = false;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterpriseCreateDataPacket()));
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.scrollBar = new GuiScrollBarFaction((float)(this.guiLeft + 296), (float)(this.guiTop + 128), 70);
        this.createButton = new GuiButton(0, this.guiLeft + 303 - 122, this.guiTop + 211, 122, 20, I18n.getString("enterprise.create.create"));
        this.enterpriseNameField = new GuiTextField(this.fontRenderer, this.guiLeft + 53, this.guiTop + 65, 247, 10);
        this.enterpriseNameField.setEnableBackgroundDrawing(false);
        this.enterpriseNameField.setMaxStringLength(15);
        this.selectedType = "build";
        errorMessage = "";
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.enterpriseNameField.updateCursorCounter();
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char typedChar, int keyCode)
    {
        this.enterpriseNameField.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        this.drawDefaultBackground();
        List tooltipToDraw = null;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        ClientEventHandler.STYLE.bindTexture("enterprise_create");
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
        this.createButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
        this.enterpriseNameField.drawTextBox();
        ClientEventHandler.STYLE.bindTexture("enterprise_create");
        int i;
        int offsetY;

        for (i = 0; i < TABS.size(); ++i)
        {
            GuiScreenTab offsetX = (GuiScreenTab)TABS.get(i);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            offsetY = i % 5;
            int y = i / 5;

            if (this.getClass() == offsetX.getClassReferent())
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23), (float)(this.guiTop + 20 + i * 31), 23, 251, 29, 30, 512.0F, 512.0F, false);
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23 + 4), (float)(this.guiTop + 20 + i * 31 + 5), offsetY * 20, 298 + y * 20, 20, 20, 512.0F, 512.0F, false);
            }
            else
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23), (float)(this.guiTop + 20 + i * 31), 0, 251, 23, 30, 512.0F, 512.0F, false);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23 + 4), (float)(this.guiTop + 20 + i * 31 + 5), offsetY * 20, 298 + y * 20, 20, 20, 512.0F, 512.0F, false);
                GL11.glDisable(GL11.GL_BLEND);
            }
        }

        if (mouseX >= this.guiLeft + 304 && mouseX <= this.guiLeft + 304 + 9 && mouseY >= this.guiTop - 6 && mouseY <= this.guiTop - 6 + 10)
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 304), (float)(this.guiTop - 6), 138, 261, 9, 10, 512.0F, 512.0F, false);
        }
        else
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 304), (float)(this.guiTop - 6), 138, 251, 9, 10, 512.0F, 512.0F, false);
        }

        if (loaded)
        {
            if (((Double)createInfos.get("count")).doubleValue() >= ((Double)createInfos.get("canCreate")).doubleValue())
            {
                this.createButton.enabled = false;

                if (mouseX >= this.guiLeft + 181 && mouseX <= this.guiLeft + 181 + 122 && mouseY >= this.guiTop + 211 && mouseY <= this.guiTop + 211 + 20)
                {
                    tooltipToDraw = Arrays.asList(I18n.getString("enterprise.create.disable.count").split("##"));
                }
            }
            else if (((Double)createInfos.get("canJoin")).doubleValue() <= 0.0D)
            {
                this.createButton.enabled = false;

                if (mouseX >= this.guiLeft + 181 && mouseX <= this.guiLeft + 181 + 122 && mouseY >= this.guiTop + 211 && mouseY <= this.guiTop + 211 + 20)
                {
                    tooltipToDraw = Arrays.asList(I18n.getString("enterprise.create.max_join.count").split("##"));
                }
            }
            else if (((List)createInfos.get("types")).contains(this.selectedType))
            {
                this.createButton.enabled = false;

                if (mouseX >= this.guiLeft + 181 && mouseX <= this.guiLeft + 181 + 122 && mouseY >= this.guiTop + 211 && mouseY <= this.guiTop + 211 + 20)
                {
                    tooltipToDraw = Arrays.asList(new String[] {I18n.getString("enterprise.create.disable.type")});
                }
            }
            else if (((List)createInfos.get("forbiddenTypes")).contains(this.selectedType))
            {
                this.createButton.enabled = false;

                if (mouseX >= this.guiLeft + 181 && mouseX <= this.guiLeft + 181 + 122 && mouseY >= this.guiTop + 211 && mouseY <= this.guiTop + 211 + 20)
                {
                    tooltipToDraw = Arrays.asList(new String[] {I18n.getString("enterprise.create.forbidden.type")});
                }
            }
            else if (((List)createInfos.get("blockedByResearchTypes")).contains(this.selectedType))
            {
                this.createButton.enabled = false;

                if (mouseX >= this.guiLeft + 181 && mouseX <= this.guiLeft + 181 + 122 && mouseY >= this.guiTop + 211 && mouseY <= this.guiTop + 211 + 20)
                {
                    tooltipToDraw = Arrays.asList(new String[] {I18n.getString("enterprise.create.blockedByResearchTypes.type")});
                }
            }
            else if (((List)createInfos.get("notAvailableTypes")).contains(this.selectedType))
            {
                this.createButton.enabled = false;

                if (mouseX >= this.guiLeft + 181 && mouseX <= this.guiLeft + 181 + 122 && mouseY >= this.guiTop + 211 && mouseY <= this.guiTop + 211 + 20)
                {
                    tooltipToDraw = Arrays.asList(new String[] {I18n.getString("enterprise.create.not_available.type")});
                }
            }
            else
            {
                this.createButton.enabled = true;
            }

            this.drawScaledString(I18n.getString("enterprise.create.title"), this.guiLeft + 50, this.guiTop + 20, 1644825, 1.4F, false, false);

            if (errorMessage != null && !errorMessage.isEmpty())
            {
                this.drawScaledString(I18n.getString("enterprise.create.error." + errorMessage), this.guiLeft + 50, this.guiTop + 36, 1644825, 1.0F, false, false);
            }

            this.drawScaledString(I18n.getString("enterprise.create.name"), this.guiLeft + 51, this.guiTop + 49, 1644825, 1.0F, false, false);
            this.drawScaledString(I18n.getString("enterprise.create.type"), this.guiLeft + 51, this.guiTop + 94, 1644825, 1.0F, false, false);
            ClientEventHandler.STYLE.bindTexture("enterprise_main");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 53), (float)(this.guiTop + 106), EnterpriseGui.getTypeOffsetX(this.selectedType), 442, 16, 16, 512.0F, 512.0F, false);
            this.drawScaledString(I18n.getString("enterprise.type." + this.selectedType), this.guiLeft + 53 + 16 + 2, this.guiTop + 110, 16777215, 1.0F, false, false);
            ClientEventHandler.STYLE.bindTexture("enterprise_create");

            if (this.selectedType != null && !this.selectedType.isEmpty() && EnterpriseGui.availableTypes.contains(this.selectedType))
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 66), (float)(this.guiTop + 148), EnterpriseGui.availableTypes.indexOf(this.selectedType) <= 12 ? EnterpriseGui.availableTypes.indexOf(this.selectedType) * 39 : (EnterpriseGui.availableTypes.indexOf(this.selectedType) - 13) * 39, EnterpriseGui.availableTypes.indexOf(this.selectedType) <= 12 ? 331 : 371, 39, 39, 512.0F, 512.0F, false);
                String[] var12 = I18n.getString("enterprise.create.type.desc." + this.selectedType).split(" ");
                String var13 = "";
                offsetY = 0;
                String[] var17 = var12;
                int var9 = var12.length;

                for (int var10 = 0; var10 < var9; ++var10)
                {
                    String descWord = var17[var10];

                    if ((double)this.fontRenderer.getStringWidth(var13 + descWord) * 0.9D <= 175.0D)
                    {
                        if (!var13.equals(""))
                        {
                            var13 = var13 + " ";
                        }

                        var13 = var13 + descWord;
                    }
                    else
                    {
                        this.drawScaledString(var13, this.guiLeft + 124, this.guiTop + 137 + offsetY * 10, 16777215, 0.9F, false, false);
                        ++offsetY;
                        var13 = descWord;
                    }
                }

                this.drawScaledString(var13, this.guiLeft + 124, this.guiTop + 137 + offsetY * 10, 16777215, 0.9F, false, false);
            }

            ClientEventHandler.STYLE.bindTexture("enterprise_create");

            if (this.typesExpanded)
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 50), (float)(this.guiTop + 123), 181, 250, 253, 80, 512.0F, 512.0F, false);
                GUIUtils.startGLScissor(this.guiLeft + 51, this.guiTop + 124, 245, 78);

                for (i = 0; i < EnterpriseGui.availableTypes.size(); ++i)
                {
                    int var15 = this.guiLeft + 51;
                    Float var16 = Float.valueOf((float)(this.guiTop + 124 + i * 20) + this.getSlideTypes());
                    ClientEventHandler.STYLE.bindTexture("enterprise_create");
                    ModernGui.drawModalRectWithCustomSizedTexture((float)var15, (float)var16.intValue(), 182, 251, 245, 20, 512.0F, 512.0F, false);
                    ClientEventHandler.STYLE.bindTexture("enterprise_main");
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(var15 + 2), (float)(var16.intValue() + 1), EnterpriseGui.getTypeOffsetX((String)EnterpriseGui.availableTypes.get(i)), 442, 16, 16, 512.0F, 512.0F, false);
                    this.drawScaledString(I18n.getString("enterprise.type." + (String)EnterpriseGui.availableTypes.get(i)), var15 + 2 + 16 + 3, var16.intValue() + 5, 16777215, 1.0F, false, false);
                    ClientEventHandler.STYLE.bindTexture("enterprise_create");

                    if (mouseX > var15 && mouseX < var15 + 245 && (float)mouseY > var16.floatValue() && (float)mouseY < var16.floatValue() + 20.0F)
                    {
                        this.hoveredType = (String)EnterpriseGui.availableTypes.get(i);
                    }
                }

                GUIUtils.endGLScissor();
                this.scrollBar.draw(mouseX, mouseY);
            }

            ClientEventHandler.STYLE.bindTexture("enterprise_create");
            String var14 = "\u00a77" + I18n.getString("enterprise.create.fee") + ": \u00a7f" + (int)((((Double)createInfos.get("count")).doubleValue() + 1.0D) * 15000.0D) + "$";
            this.drawScaledString(var14, this.guiLeft + 168 - this.fontRenderer.getStringWidth(var14), this.guiTop + 217, 16777215, 1.0F, false, false);
            this.drawScaledString("\u00a7a$", this.guiLeft + 60, this.guiTop + 217, 16777215, 1.3F, true, false);
        }

        for (i = 0; i < TABS.size(); ++i)
        {
            if (mouseX >= this.guiLeft - 23 && mouseX <= this.guiLeft - 23 + 29 && mouseY >= this.guiTop + 20 + i * 31 && mouseY <= this.guiTop + 20 + 30 + i * 31)
            {
                this.drawHoveringText(Arrays.asList(new String[] {I18n.getString("enterprise.tab.search." + i)}), mouseX, mouseY, this.fontRenderer);
            }
        }

        if (tooltipToDraw != null)
        {
            this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.fontRenderer);
        }

        GL11.glEnable(GL11.GL_LIGHTING);
        RenderHelper.enableStandardItemLighting();
    }

    private float getSlideTypes()
    {
        return EnterpriseGui.availableTypes.size() > 4 ? (float)(-(EnterpriseGui.availableTypes.size() - 4) * 20) * this.scrollBar.getSliderValue() : 0.0F;
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            for (int i = 0; i < TABS.size(); ++i)
            {
                GuiScreenTab type = (GuiScreenTab)TABS.get(i);

                if (mouseX >= this.guiLeft - 20 && mouseX <= this.guiLeft + 3 && mouseY >= this.guiTop + 20 + i * 31 && mouseY <= this.guiTop + 50 + i * 31)
                {
                    this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);

                    if (this.getClass() != type.getClassReferent())
                    {
                        try
                        {
                            type.call();
                        }
                        catch (Exception var7)
                        {
                            var7.printStackTrace();
                        }
                    }
                }
            }

            if (mouseX >= this.guiLeft + 50 && mouseX <= this.guiLeft + 50 + 253 && mouseY >= this.guiTop + 104 && mouseY <= this.guiTop + 104 + 20)
            {
                this.typesExpanded = !this.typesExpanded;
            }

            if (mouseX > this.guiLeft + 304 && mouseX < this.guiLeft + 304 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (this.hoveredType != null && !this.hoveredType.isEmpty())
            {
                this.selectedType = this.hoveredType;
                this.hoveredType = "";
                this.typesExpanded = false;
            }

            if (!this.enterpriseNameField.getText().isEmpty() && this.createButton.enabled && this.selectedType != null && !this.selectedType.isEmpty() && mouseX >= this.guiLeft + 181 && mouseX <= this.guiLeft + 181 + 122 && mouseY >= this.guiTop + 211 && mouseY <= this.guiTop + 211 + 20)
            {
                System.out.println("send create packet");
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterpriseCreatePacket(this.enterpriseNameField.getText(), this.selectedType, (int)((((Double)createInfos.get("count")).doubleValue() + 1.0D) * 15000.0D))));
            }

            this.enterpriseNameField.mouseClicked(mouseX, mouseY, mouseButton);
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void drawScaledString(String text, int x, int y, int color, float scale, boolean centered, boolean shadow)
    {
        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, scale);
        float newX = (float)x;

        if (centered)
        {
            newX = (float)x - (float)this.fontRenderer.getStringWidth(text) * scale / 2.0F;
        }

        if (shadow)
        {
            this.fontRenderer.drawString(text, (int)(newX / scale), (int)((float)(y + 1) / scale), (color & 16579836) >> 2 | color & -16777216, false);
        }

        this.fontRenderer.drawString(text, (int)(newX / scale), (int)((float)y / scale), color, false);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    protected void drawHoveringText(List par1List, int par2, int par3, FontRenderer font)
    {
        if (!par1List.isEmpty())
        {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            int k = 0;
            Iterator iterator = par1List.iterator();
            int j1;

            while (iterator.hasNext())
            {
                String i1 = (String)iterator.next();
                j1 = font.getStringWidth(i1);

                if (j1 > k)
                {
                    k = j1;
                }
            }

            int var15 = par2 + 12;
            j1 = par3 - 12;
            int k1 = 8;

            if (par1List.size() > 1)
            {
                k1 += 2 + (par1List.size() - 1) * 10;
            }

            if (var15 + k > this.width)
            {
                var15 -= 28 + k;
            }

            if (j1 + k1 + 6 > this.height)
            {
                j1 = this.height - k1 - 6;
            }

            this.zLevel = 300.0F;
            this.itemRenderer.zLevel = 300.0F;
            int l1 = -267386864;
            this.drawGradientRect(var15 - 3, j1 - 4, var15 + k + 3, j1 - 3, l1, l1);
            this.drawGradientRect(var15 - 3, j1 + k1 + 3, var15 + k + 3, j1 + k1 + 4, l1, l1);
            this.drawGradientRect(var15 - 3, j1 - 3, var15 + k + 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(var15 - 4, j1 - 3, var15 - 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(var15 + k + 3, j1 - 3, var15 + k + 4, j1 + k1 + 3, l1, l1);
            int i2 = 1347420415;
            int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
            this.drawGradientRect(var15 - 3, j1 - 3 + 1, var15 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
            this.drawGradientRect(var15 + k + 2, j1 - 3 + 1, var15 + k + 3, j1 + k1 + 3 - 1, i2, j2);
            this.drawGradientRect(var15 - 3, j1 - 3, var15 + k + 3, j1 - 3 + 1, i2, i2);
            this.drawGradientRect(var15 - 3, j1 + k1 + 2, var15 + k + 3, j1 + k1 + 3, j2, j2);

            for (int k2 = 0; k2 < par1List.size(); ++k2)
            {
                String s1 = (String)par1List.get(k2);
                font.drawStringWithShadow(s1, var15, j1, -1);

                if (k2 == 0)
                {
                    j1 += 2;
                }

                j1 += 10;
            }

            this.zLevel = 0.0F;
            this.itemRenderer.zLevel = 0.0F;
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    static
    {
        TABS.add(new EnterpriseCreateGui$1());
        TABS.add(new EnterpriseCreateGui$2());
    }
}
