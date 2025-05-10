package net.ilexiconn.nationsgui.forge.client.gui.island;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandSaveSettingsPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import sun.misc.BASE64Decoder;

@SideOnly(Side.CLIENT)
public class IslandSettingsGui extends GuiScreen
{
    protected int xSize = 291;
    protected int ySize = 247;
    private int guiLeft;
    private int guiTop;
    private boolean isPrivate = false;
    private GuiTextField nameTextField;
    private GuiTextField descriptionTextField;
    private GuiTextField passwordTextField;
    private GuiTextField welcomeTextField;
    private RenderItem itemRenderer = new RenderItem();
    private DynamicTexture imageTexture;
    private String cachedImage = "";
    public boolean canSave = true;
    public boolean expandOpenType = false;
    public ArrayList<String> openTypeList = new ArrayList();
    public String hoveredOpenType = "";
    public String selectedOpenType = "";

    public IslandSettingsGui()
    {
        this.isPrivate = Boolean.parseBoolean((String)IslandMainGui.islandInfos.get("isPrivate"));

        if (IslandMainGui.islandInfos.get("password").equals("true"))
        {
            this.selectedOpenType = "password";
        }
        else if (IslandMainGui.islandInfos.get("askBeforeJoin").equals("true"))
        {
            this.selectedOpenType = "ask";
        }
        else
        {
            this.selectedOpenType = "open";
        }

        this.openTypeList.addAll(Arrays.asList(new String[] {"open", "password"}));
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.nameTextField = new GuiTextField(this.fontRenderer, this.guiLeft + 53, this.guiTop + 30, 218, 10);
        this.nameTextField.setEnableBackgroundDrawing(false);
        this.nameTextField.setMaxStringLength(25);
        this.nameTextField.setText((String)IslandMainGui.islandInfos.get("name"));
        this.nameTextField.setCursorPositionZero();
        this.descriptionTextField = new GuiTextField(this.fontRenderer, this.guiLeft + 53, this.guiTop + 57, 218, 10);
        this.descriptionTextField.setEnableBackgroundDrawing(false);
        this.descriptionTextField.setMaxStringLength(50);
        this.descriptionTextField.setText((String)IslandMainGui.islandInfos.get("description"));
        this.descriptionTextField.setCursorPositionZero();
        this.passwordTextField = new GuiTextField(this.fontRenderer, this.guiLeft + 168, this.guiTop + 139, 103, 10);
        this.passwordTextField.setEnableBackgroundDrawing(false);
        this.passwordTextField.setMaxStringLength(20);
        this.passwordTextField.setText("********");
        this.welcomeTextField = new GuiTextField(this.fontRenderer, this.guiLeft + 53, this.guiTop + 166, 218, 10);
        this.welcomeTextField.setEnableBackgroundDrawing(false);
        this.welcomeTextField.setMaxStringLength(100);
        this.welcomeTextField.setText((String)IslandMainGui.islandInfos.get("welcome"));
        this.welcomeTextField.setCursorPositionZero();
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.nameTextField.updateCursorCounter();
        this.descriptionTextField.updateCursorCounter();
        this.passwordTextField.updateCursorCounter();
        this.welcomeTextField.updateCursorCounter();
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        Object tooltipToDraw = new ArrayList();
        this.drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        ClientEventHandler.STYLE.bindTexture("island_settings");
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
        ClientEventHandler.STYLE.bindTexture("island_main");
        int i;

        for (int titleOffsetY = 0; titleOffsetY < IslandMainGui.TABS.size(); ++titleOffsetY)
        {
            GuiScreenTab openTypeTextureY = (GuiScreenTab)IslandMainGui.TABS.get(titleOffsetY);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            i = IslandMainGui.getTabIndex((GuiScreenTab)IslandMainGui.TABS.get(titleOffsetY));

            if (this.getClass() == openTypeTextureY.getClassReferent())
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23), (float)(this.guiTop + 47 + titleOffsetY * 31), 23, 249, 29, 30, 512.0F, 512.0F, false);
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23 + 4), (float)(this.guiTop + 47 + titleOffsetY * 31 + 5), i * 20, 331, 20, 20, 512.0F, 512.0F, false);
            }
            else
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23), (float)(this.guiTop + 47 + titleOffsetY * 31), 0, 249, 23, 30, 512.0F, 512.0F, false);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23 + 4), (float)(this.guiTop + 47 + titleOffsetY * 31 + 5), i * 20, 331, 20, 20, 512.0F, 512.0F, false);
                GL11.glDisable(GL11.GL_BLEND);

                if (mouseX >= this.guiLeft - 23 && mouseX <= this.guiLeft - 23 + 29 && mouseY >= this.guiTop + 47 + titleOffsetY * 31 && mouseY <= this.guiTop + 47 + 30 + titleOffsetY * 31)
                {
                    tooltipToDraw = Arrays.asList(new String[] {I18n.getString("island.tab." + i)});
                }
            }
        }

        ClientEventHandler.STYLE.bindTexture("island_settings");

        if (mouseX >= this.guiLeft + 278 && mouseX <= this.guiLeft + 278 + 9 && mouseY >= this.guiTop - 8 && mouseY <= this.guiTop - 8 + 10)
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 278), (float)(this.guiTop - 8), 61, 305, 9, 10, 512.0F, 512.0F, false);
        }
        else
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 278), (float)(this.guiTop - 8), 61, 295, 9, 10, 512.0F, 512.0F, false);
        }

        GL11.glPushMatrix();
        Double var8 = Double.valueOf((double)(this.guiTop + 45) + (double)this.fontRenderer.getStringWidth((String)IslandMainGui.islandInfos.get("name")) * 1.5D);
        GL11.glTranslatef((float)(this.guiLeft + 14), (float)var8.intValue(), 0.0F);
        GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef((float)(-(this.guiLeft + 14)), (float)(-var8.intValue()), 0.0F);
        this.drawScaledString((String)IslandMainGui.islandInfos.get("name"), this.guiLeft + 14, var8.intValue(), 16777215, 1.5F, false, false);
        GL11.glPopMatrix();
        this.drawScaledString(I18n.getString("island.settings.name"), this.guiLeft + 48, this.guiTop + 16, 0, 1.0F, false, false);
        this.nameTextField.drawTextBox();
        this.drawScaledString(I18n.getString("island.settings.description"), this.guiLeft + 48, this.guiTop + 44, 0, 1.0F, false, false);
        this.descriptionTextField.drawTextBox();
        this.drawScaledString(I18n.getString("island.settings.welcome"), this.guiLeft + 48, this.guiTop + 153, 0, 1.0F, false, false);
        this.welcomeTextField.drawTextBox();

        if ((this.imageTexture == null || !((String)IslandMainGui.islandInfos.get("image")).equals(this.cachedImage)) && !((String)IslandMainGui.islandInfos.get("image")).isEmpty())
        {
            BufferedImage var9 = decodeToImage((String)IslandMainGui.islandInfos.get("image"));
            this.imageTexture = new DynamicTexture(var9);
            this.cachedImage = (String)IslandMainGui.islandInfos.get("image");
        }

        if (this.imageTexture != null)
        {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.imageTexture.getGlTextureId());
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 64), (float)(this.guiTop + 84), 0.0F, 0.0F, 102, 102, 30, 30, 102.0F, 102.0F, false);
            ClientEventHandler.STYLE.bindTexture("island_settings");
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(false);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 63), (float)(this.guiTop + 83), 292, 41, 32, 32, 512.0F, 512.0F, false);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(true);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
        }

        this.drawScaledString(I18n.getString("island.settings.edit_flag"), this.guiLeft + 186, this.guiTop + 95, 16777215, 1.0F, true, false);
        ClientEventHandler.STYLE.bindTexture("island_settings");

        if (this.isPrivate)
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 52), (float)(this.guiTop + 137), 50, 295, 10, 10, 512.0F, 512.0F, false);
        }

        this.drawScaledString(I18n.getString("island.settings.private"), this.guiLeft + 64, this.guiTop + 138, 16777215, 1.0F, false, false);
        ClientEventHandler.STYLE.bindTexture("island_settings");

        if (!IslandMainGui.isPremium)
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 101), (float)(this.guiTop + 131), 292, 90, 10, 12, 512.0F, 512.0F, false);

            if (mouseX >= this.guiLeft + 101 && mouseX <= this.guiLeft + 101 + 10 && mouseY >= this.guiTop + 131 && mouseY <= this.guiTop + 131 + 12)
            {
                tooltipToDraw = Arrays.asList(new String[] {I18n.getString("island.global.premium_required")});
            }
        }

        short var10 = 294;

        if (this.selectedOpenType.equals("ask"))
        {
            var10 = 306;
        }
        else if (this.selectedOpenType.equals("password"))
        {
            var10 = 320;
        }

        ClientEventHandler.STYLE.bindTexture("island_settings");
        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 115), (float)(this.guiTop + 136), 73, var10, 20, 11, 512.0F, 512.0F, false);

        if (this.expandOpenType)
        {
            for (i = 0; i < this.openTypeList.size(); ++i)
            {
                var10 = 294;

                if (((String)this.openTypeList.get(i)).equals("ask"))
                {
                    var10 = 306;
                }
                else if (((String)this.openTypeList.get(i)).equals("password"))
                {
                    var10 = 320;
                }

                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 112), (float)(this.guiTop + 149 + 15 * i), 0, 295, 49, 16, 512.0F, 512.0F, false);
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 115), (float)(this.guiTop + 151 + 15 * i), 73, var10, 20, 11, 512.0F, 512.0F, false);

                if (mouseX >= this.guiLeft + 112 && mouseX <= this.guiLeft + 112 + 49 && mouseY >= this.guiTop + 149 + 15 * i && mouseY <= this.guiTop + 149 + 15 * i + 11)
                {
                    this.hoveredOpenType = (String)this.openTypeList.get(i);
                }
            }
        }

        ClientEventHandler.STYLE.bindTexture("island_settings");

        if (this.selectedOpenType.equals("password"))
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 163), (float)(this.guiTop + 134), 0, 275, 113, 16, 512.0F, 512.0F, false);
            this.passwordTextField.drawTextBox();
        }

        ClientEventHandler.STYLE.bindTexture("island_settings");

        if (IslandMainGui.islandInfos.get("isCreator").equals("false") || mouseX >= this.guiLeft + 48 && mouseX <= this.guiLeft + 48 + 113 && mouseY >= this.guiTop + 214 && mouseY <= this.guiTop + 214 + 18)
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 48), (float)(this.guiTop + 214), 0, 256, 113, 18, 512.0F, 512.0F, false);
        }

        this.drawScaledString(I18n.getString("island.settings.delete"), this.guiLeft + 104, this.guiTop + 219, 16777215, 1.0F, true, false);
        ClientEventHandler.STYLE.bindTexture("island_settings");

        if (!this.canSave || this.nameTextField.getText().isEmpty() || this.descriptionTextField.getText().isEmpty() || this.selectedOpenType.equals("password") && this.passwordTextField.getText().isEmpty() || mouseX >= this.guiLeft + 163 && mouseX <= this.guiLeft + 163 + 113 && mouseY >= this.guiTop + 214 && mouseY <= this.guiTop + 214 + 18)
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 163), (float)(this.guiTop + 214), 113, 256, 113, 18, 512.0F, 512.0F, false);
        }

        this.drawScaledString(I18n.getString("island.global.save"), this.guiLeft + 219, this.guiTop + 219, 16777215, 1.0F, true, false);

        if (!((List)tooltipToDraw).isEmpty())
        {
            this.drawHoveringText((List)tooltipToDraw, mouseX, mouseY, this.fontRenderer);
        }

        super.drawScreen(mouseX, mouseY, par3);
        GL11.glEnable(GL11.GL_LIGHTING);
        RenderHelper.enableStandardItemLighting();
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char typedChar, int keyCode)
    {
        this.nameTextField.textboxKeyTyped(typedChar, keyCode);
        this.descriptionTextField.textboxKeyTyped(typedChar, keyCode);
        this.passwordTextField.textboxKeyTyped(typedChar, keyCode);
        this.welcomeTextField.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
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

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            for (int settings = 0; settings < IslandMainGui.TABS.size(); ++settings)
            {
                GuiScreenTab type = (GuiScreenTab)IslandMainGui.TABS.get(settings);

                if (mouseX >= this.guiLeft - 20 && mouseX <= this.guiLeft + 3 && mouseY >= this.guiTop + 47 + settings * 31 && mouseY <= this.guiTop + 47 + 30 + settings * 31)
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

            if (mouseX > this.guiLeft + 278 && mouseX < this.guiLeft + 278 + 9 && mouseY > this.guiTop - 8 && mouseY < this.guiTop - 8 + 10)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
            }
            else if (IslandMainGui.isPremium && mouseX >= this.guiLeft + 52 && mouseX <= this.guiLeft + 52 + 10 && mouseY >= this.guiTop + 137 && mouseY <= this.guiTop + 137 + 10)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                this.isPrivate = !this.isPrivate;
                this.canSave = true;
            }
            else if (mouseX >= this.guiLeft + 112 && mouseX <= this.guiLeft + 112 + 49 && mouseY >= this.guiTop + 134 && mouseY <= this.guiTop + 134 + 15)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                this.expandOpenType = !this.expandOpenType;
                this.canSave = true;
            }
            else if (mouseX >= this.guiLeft + 112 && mouseX <= this.guiLeft + 112 + 49 && mouseY >= this.guiTop + 149 && mouseY <= this.guiTop + 149 + 46 && !this.hoveredOpenType.isEmpty())
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                this.selectedOpenType = this.hoveredOpenType;
                this.expandOpenType = false;
                this.canSave = true;

                if (this.selectedOpenType.equals("password"))
                {
                    this.passwordTextField.setText("");
                }
            }
            else if (mouseX >= this.guiLeft + 163 && mouseX <= this.guiLeft + 163 + 113 && mouseY >= this.guiTop + 134 && mouseY <= this.guiTop + 134 + 16 && this.passwordTextField.getText().matches("\\*+"))
            {
                this.passwordTextField.setText("");
                this.canSave = true;
            }
            else if (mouseX >= this.guiLeft + 47 && mouseX <= this.guiLeft + 47 + 228 && mouseY >= this.guiTop + 25 && mouseY <= this.guiTop + 25 + 44)
            {
                this.canSave = true;
            }
            else if (mouseX >= this.guiLeft + 163 && mouseX <= this.guiLeft + 163 + 113 && mouseY >= this.guiTop + 214 && mouseY <= this.guiTop + 214 + 18)
            {
                if (this.canSave && !this.nameTextField.getText().isEmpty() && !this.descriptionTextField.getText().isEmpty() && (!this.selectedOpenType.equals("password") || !this.passwordTextField.getText().isEmpty()))
                {
                    this.canSave = false;
                    HashMap var8 = new HashMap();
                    var8.put("name", this.nameTextField.getText());
                    var8.put("description", this.descriptionTextField.getText());
                    var8.put("welcome", this.welcomeTextField.getText());
                    var8.put("isPrivate", IslandMainGui.isPremium ? this.isPrivate + "" : "false");
                    var8.put("openType", this.selectedOpenType);
                    var8.put("password", this.passwordTextField.getText());
                    PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandSaveSettingsPacket((String)IslandMainGui.islandInfos.get("id"), var8)));
                    this.mc.sndManager.playSoundFX("random.successful_hit", 1.0F, 1.0F);
                }
            }
            else if (mouseX >= this.guiLeft + 112 && mouseX <= this.guiLeft + 112 + 150 && mouseY >= this.guiTop + 90 && mouseY <= this.guiTop + 90 + 18)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen(new IslandFlagGui());
            }
            else if (IslandMainGui.islandInfos.get("isCreator").equals("true") && mouseX >= this.guiLeft + 48 && mouseX <= this.guiLeft + 48 + 113 && mouseY >= this.guiTop + 214 && mouseY <= this.guiTop + 214 + 18)
            {
                Minecraft.getMinecraft().displayGuiScreen(new ConfirmDeleteGui(this));
            }
        }

        this.nameTextField.mouseClicked(mouseX, mouseY, mouseButton);
        this.descriptionTextField.mouseClicked(mouseX, mouseY, mouseButton);
        this.passwordTextField.mouseClicked(mouseX, mouseY, mouseButton);
        this.welcomeTextField.mouseClicked(mouseX, mouseY, mouseButton);
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

    public static BufferedImage decodeToImage(String imageString)
    {
        BufferedImage image = null;

        try
        {
            BASE64Decoder e = new BASE64Decoder();
            byte[] imageByte = e.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        }
        catch (Exception var5)
        {
            var5.printStackTrace();
        }

        return image;
    }
}
