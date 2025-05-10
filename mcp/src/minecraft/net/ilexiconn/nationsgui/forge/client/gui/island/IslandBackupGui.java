package net.ilexiconn.nationsgui.forge.client.gui.island;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandDeleteBackupPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandRestoreBackupPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandSaveBackupPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import sun.misc.BASE64Decoder;

@SideOnly(Side.CLIENT)
public class IslandBackupGui extends GuiScreen
{
    protected int xSize = 289;
    protected int ySize = 248;
    private int guiLeft;
    private int guiTop;
    private RenderItem itemRenderer = new RenderItem();
    private GuiScrollBarFaction scrollBar;
    private boolean saveStarted = false;
    private boolean restoreStarted = false;
    private String restoreStartedId = "";
    private long actionTime = 0L;
    private int actionWaitingSec = 5;
    private GuiTextField nameTextField;
    private String hoveredDelete = "";
    private String hoveredRestore = "";

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.nameTextField = new GuiTextField(this.fontRenderer, this.guiLeft + 59, this.guiTop + 36, 92, 10);
        this.nameTextField.setEnableBackgroundDrawing(false);
        this.nameTextField.setMaxStringLength(25);
        this.scrollBar = new GuiScrollBarFaction((float)(this.guiLeft + 270), (float)(this.guiTop + 116), 108);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.nameTextField.updateCursorCounter();
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        this.drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        ClientEventHandler.STYLE.bindTexture("island_backup");
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
        Object tooltipToDraw = new ArrayList();
        ClientEventHandler.STYLE.bindTexture("island_main");

        for (int titleOffsetY = 0; titleOffsetY < IslandMainGui.TABS.size(); ++titleOffsetY)
        {
            GuiScreenTab l = (GuiScreenTab)IslandMainGui.TABS.get(titleOffsetY);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            int backupId = IslandMainGui.getTabIndex((GuiScreenTab)IslandMainGui.TABS.get(titleOffsetY));

            if (this.getClass() == l.getClassReferent())
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23), (float)(this.guiTop + 47 + titleOffsetY * 31), 23, 249, 29, 30, 512.0F, 512.0F, false);
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23 + 4), (float)(this.guiTop + 47 + titleOffsetY * 31 + 5), backupId * 20, 331, 20, 20, 512.0F, 512.0F, false);
            }
            else
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23), (float)(this.guiTop + 47 + titleOffsetY * 31), 0, 249, 23, 30, 512.0F, 512.0F, false);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23 + 4), (float)(this.guiTop + 47 + titleOffsetY * 31 + 5), backupId * 20, 331, 20, 20, 512.0F, 512.0F, false);
                GL11.glDisable(GL11.GL_BLEND);

                if (mouseX >= this.guiLeft - 23 && mouseX <= this.guiLeft - 23 + 29 && mouseY >= this.guiTop + 47 + titleOffsetY * 31 && mouseY <= this.guiTop + 47 + 30 + titleOffsetY * 31)
                {
                    tooltipToDraw = Arrays.asList(new String[] {I18n.getString("island.tab." + backupId)});
                }
            }
        }

        ClientEventHandler.STYLE.bindTexture("island_backup");

        if (mouseX >= this.guiLeft + 276 && mouseX <= this.guiLeft + 276 + 9 && mouseY >= this.guiTop - 8 && mouseY <= this.guiTop - 8 + 10)
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 276), (float)(this.guiTop - 8), 84, 254, 9, 10, 512.0F, 512.0F, false);
        }
        else
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 276), (float)(this.guiTop - 8), 84, 264, 9, 10, 512.0F, 512.0F, false);
        }

        GL11.glPushMatrix();
        Double var17 = Double.valueOf((double)(this.guiTop + 45) + (double)this.fontRenderer.getStringWidth((String)IslandMainGui.islandInfos.get("name")) * 1.5D);
        GL11.glTranslatef((float)(this.guiLeft + 14), (float)var17.intValue(), 0.0F);
        GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef((float)(-(this.guiLeft + 14)), (float)(-var17.intValue()), 0.0F);
        this.drawScaledString((String)IslandMainGui.islandInfos.get("name"), this.guiLeft + 14, var17.intValue(), 16777215, 1.5F, false, false);
        GL11.glPopMatrix();
        this.drawScaledString(I18n.getString("island.backup.save_name"), this.guiLeft + 57, this.guiTop + 22, 16777215, 1.0F, false, false);
        this.nameTextField.drawTextBox();
        ClientEventHandler.STYLE.bindTexture("island_backup");

        if (!this.saveStarted)
        {
            if (System.currentTimeMillis() - Long.parseLong((String)IslandMainGui.islandInfos.get("regenTime")) < 300000L || mouseX >= this.guiLeft + 56 && mouseX <= this.guiLeft + 56 + 98 && mouseY >= this.guiTop + 54 && mouseY <= this.guiTop + 54 + 18)
            {
                ClientEventHandler.STYLE.bindTexture("island_backup");
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 56), (float)(this.guiTop + 54), 0, 314, 98, 18, 512.0F, 512.0F, false);

                if (System.currentTimeMillis() - Long.parseLong((String)IslandMainGui.islandInfos.get("regenTime")) < 300000L && mouseX >= this.guiLeft + 56 && mouseX <= this.guiLeft + 56 + 98 && mouseY >= this.guiTop + 54 && mouseY <= this.guiTop + 54 + 18)
                {
                    tooltipToDraw = Arrays.asList(new String[] {I18n.getString("island.global.cooldown_1"), I18n.getString("island.global.cooldown_2")});
                }
                else if (((ArrayList)IslandMainGui.islandInfos.get("backupsInfos")).size() >= 6 && mouseX >= this.guiLeft + 56 && mouseX <= this.guiLeft + 56 + 98 && mouseY >= this.guiTop + 54 && mouseY <= this.guiTop + 54 + 18)
                {
                    tooltipToDraw = Arrays.asList(new String[] {I18n.getString("island.backup.limit_1"), I18n.getString("island.backup.limit_2"), I18n.getString("island.backup.limit_3")});
                }
            }

            this.drawScaledString(I18n.getString("island.global.save"), this.guiLeft + 105, this.guiTop + 59, 16777215, 1.0F, true, false);
        }
        else
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 57), (float)(this.guiTop + 66), 0, 284, 96, 5, 512.0F, 512.0F, false);
            long var18 = System.currentTimeMillis() - this.actionTime;

            for (int backupName = 0; (long)backupName <= var18 / (long)(this.actionWaitingSec * 1000 / 48); backupName += 2)
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 57 + backupName * 2), (float)(this.guiTop + 67), 97, 284, 2, 4, 512.0F, 512.0F, false);
            }

            if (var18 >= (long)(this.actionWaitingSec * 1000))
            {
                this.saveStarted = false;
            }

            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 60), (float)(this.guiTop + 56), 118, 253, 14, 14, 512.0F, 512.0F, false);
        }

        this.drawScaledString(I18n.getString("island.backup.last_save"), this.guiLeft + 54, this.guiTop + 84, 16777215, 1.0F, false, false);
        String var23;

        if (!IslandMainGui.islandInfos.get("lastBackup").equals("0"))
        {
            Date var19 = new Date(Long.parseLong((String)IslandMainGui.islandInfos.get("lastBackup")));
            SimpleDateFormat var21 = new SimpleDateFormat("dd/MM/yyy HH:mm");
            var23 = var21.format(var19);
            this.drawScaledString(var23, this.guiLeft + 181, this.guiTop + 84, 16777215, 1.0F, false, false);
        }

        this.drawScaledString(I18n.getString("island.backup.my_saves"), this.guiLeft + 49, this.guiTop + 102, 0, 1.0F, false, false);
        this.hoveredDelete = "";
        this.hoveredRestore = "";
        ClientEventHandler.STYLE.bindTexture("island_backup");
        GUIUtils.startGLScissor(this.guiLeft + 50, this.guiTop + 112, 220, 116);

        for (int var20 = 0; var20 < ((ArrayList)IslandMainGui.islandInfos.get("backupsInfos")).size(); ++var20)
        {
            String var22 = ((String)((ArrayList)IslandMainGui.islandInfos.get("backupsInfos")).get(var20)).split("###")[0];
            var23 = ((String)((ArrayList)IslandMainGui.islandInfos.get("backupsInfos")).get(var20)).split("###")[1];
            String backupTime = ((String)((ArrayList)IslandMainGui.islandInfos.get("backupsInfos")).get(var20)).split("###")[2];
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyy HH:mm");
            String backupDate = simpleDateFormat.format(new Date(Long.parseLong(backupTime)));
            int offsetX = this.guiLeft + 50;
            Float offsetY = Float.valueOf((float)(this.guiTop + 112 + var20 * 21) + this.getSlide());
            ClientEventHandler.STYLE.bindTexture("island_backup");
            ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)offsetY.intValue(), 50, 112, 220, 21, 512.0F, 512.0F, false);
            this.drawScaledString("\u00a77" + var23, offsetX + 4, offsetY.intValue() + 6, 16777215, 1.0F, false, false);
            ClientEventHandler.STYLE.bindTexture("island_backup");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 136), (float)(offsetY.intValue() + 4), 68, 255, 10, 11, 512.0F, 512.0F, false);

            if (mouseX >= offsetX + 136 && mouseX <= offsetX + 136 + 10 && mouseY >= offsetY.intValue() + 4 && mouseY <= offsetY.intValue() + 4 + 11)
            {
                tooltipToDraw = Arrays.asList(new String[] {backupDate});
            }

            if (!this.restoreStarted && !this.saveStarted && (mouseX < offsetX + 152 || mouseX > offsetX + 152 + 32 || mouseY < offsetY.intValue() + 2 || mouseY > offsetY.intValue() + 2 + 15))
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 152), (float)(offsetY.intValue() + 2), 0, 253, 32, 15, 512.0F, 512.0F, false);
            }
            else
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 152), (float)(offsetY.intValue() + 2), 0, 267, 32, 15, 512.0F, 512.0F, false);

                if (!this.restoreStarted && !this.saveStarted)
                {
                    this.hoveredDelete = var22;
                }
            }

            ClientEventHandler.STYLE.bindTexture("island_backup");

            if (!this.restoreStarted && (System.currentTimeMillis() - Long.parseLong((String)IslandMainGui.islandInfos.get("regenTime")) < 300000L || this.saveStarted || mouseX >= offsetX + 186 && mouseX <= offsetX + 186 + 32 && mouseY >= offsetY.intValue() + 2 && mouseY <= offsetY.intValue() + 2 + 15))
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 186), (float)(offsetY.intValue() + 2), 32, 267, 32, 15, 512.0F, 512.0F, false);

                if (!this.restoreStarted && !this.saveStarted && mouseX >= offsetX + 186 && mouseX <= offsetX + 186 + 32 && mouseY >= offsetY.intValue() + 2 && mouseY <= offsetY.intValue() + 2 + 15)
                {
                    this.hoveredRestore = var22;
                }

                if (System.currentTimeMillis() - Long.parseLong((String)IslandMainGui.islandInfos.get("regenTime")) < 300000L && mouseX >= offsetX + 186 && mouseX <= offsetX + 186 + 32 && mouseY >= offsetY.intValue() + 2 && mouseY <= offsetY.intValue() + 2 + 15)
                {
                    tooltipToDraw = Arrays.asList(new String[] {I18n.getString("island.global.cooldown_1"), I18n.getString("island.global.cooldown_2")});
                }
            }
            else
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 186), (float)(offsetY.intValue() + 2), 32, 253, 32, 15, 512.0F, 512.0F, false);

                if (this.restoreStarted && var22.equals(this.restoreStartedId))
                {
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 187), (float)(offsetY.intValue() + 12), 0, 290, 30, 4, 512.0F, 512.0F, false);
                    long timeSinceActionStarted = System.currentTimeMillis() - this.actionTime;

                    for (int i = 0; (long)i <= timeSinceActionStarted / (long)(this.actionWaitingSec * 1000 / 15); ++i)
                    {
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 188 + i * 2), (float)(offsetY.intValue() + 13), 31, 290, 1, 3, 512.0F, 512.0F, false);
                    }

                    if (timeSinceActionStarted >= (long)(this.actionWaitingSec * 1000))
                    {
                        this.restoreStarted = false;
                    }
                }
            }
        }

        GUIUtils.endGLScissor();

        if (mouseX > this.guiLeft + 49 && mouseX < this.guiLeft + 49 + 226 && mouseY > this.guiTop + 111 && mouseY < this.guiTop + 111 + 118)
        {
            this.scrollBar.draw(mouseX, mouseY);
        }

        if (!IslandMainGui.isPremium)
        {
            drawRect(this.guiLeft + 40, this.guiTop + 6, this.guiLeft + 40 + 247, this.guiTop + 6 + 236, -1157627904);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            ClientEventHandler.STYLE.bindTexture("island_properties");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 155), (float)(this.guiTop + 111), 293, 57, 16, 26, 512.0F, 512.0F, false);
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 155 + 9), (float)(this.guiTop + 111 + 19), 75, 331, 10, 12, 512.0F, 512.0F, false);

            if (mouseX >= this.guiLeft + 155 && mouseX <= this.guiLeft + 155 + 16 && mouseY >= this.guiTop + 111 && mouseY <= this.guiTop + 111 + 26)
            {
                tooltipToDraw = Arrays.asList(new String[] {I18n.getString("island.global.premium_required")});
            }
        }

        if (!((List)tooltipToDraw).isEmpty())
        {
            this.drawHoveringText((List)tooltipToDraw, mouseX, mouseY, this.fontRenderer);
        }

        super.drawScreen(mouseX, mouseY, par3);
        GL11.glEnable(GL11.GL_LIGHTING);
        RenderHelper.enableStandardItemLighting();
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
            for (int i = 0; i < IslandMainGui.TABS.size(); ++i)
            {
                GuiScreenTab type = (GuiScreenTab)IslandMainGui.TABS.get(i);

                if (mouseX >= this.guiLeft - 20 && mouseX <= this.guiLeft + 3 && mouseY >= this.guiTop + 47 + i * 31 && mouseY <= this.guiTop + 47 + 30 + i * 31)
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

            if (mouseX > this.guiLeft + 276 && mouseX < this.guiLeft + 276 + 9 && mouseY > this.guiTop - 8 && mouseY < this.guiTop - 8 + 10)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
            }
            else if (IslandMainGui.isPremium && ((ArrayList)IslandMainGui.islandInfos.get("backupsInfos")).size() < 6 && System.currentTimeMillis() - Long.parseLong((String)IslandMainGui.islandInfos.get("regenTime")) > 300000L && !this.nameTextField.getText().isEmpty() && !this.saveStarted && !this.restoreStarted && mouseX >= this.guiLeft + 56 && mouseX <= this.guiLeft + 56 + 98 && mouseY >= this.guiTop + 54 && mouseY <= this.guiTop + 54 + 18)
            {
                this.saveStarted = true;
                this.actionTime = System.currentTimeMillis();
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandSaveBackupPacket((String)IslandMainGui.islandInfos.get("id"), this.nameTextField.getText())));
            }
            else if (IslandMainGui.isPremium && !this.saveStarted && !this.restoreStarted && !this.hoveredDelete.isEmpty())
            {
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandDeleteBackupPacket((String)IslandMainGui.islandInfos.get("id"), this.hoveredDelete)));
            }
            else if (IslandMainGui.isPremium && System.currentTimeMillis() - Long.parseLong((String)IslandMainGui.islandInfos.get("regenTime")) > 300000L && !this.saveStarted && !this.restoreStarted && !this.hoveredRestore.isEmpty())
            {
                this.restoreStartedId = this.hoveredRestore;
                this.restoreStarted = true;
                this.actionTime = System.currentTimeMillis();
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandRestoreBackupPacket((String)IslandMainGui.islandInfos.get("id"), this.hoveredRestore)));
            }
        }

        if (IslandMainGui.isPremium)
        {
            this.nameTextField.mouseClicked(mouseX, mouseY, mouseButton);
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private float getSlide()
    {
        return ((ArrayList)IslandMainGui.islandInfos.get("backupsInfos")).size() > 5 ? (float)(-(((ArrayList)IslandMainGui.islandInfos.get("backupsInfos")).size() - 5) * 21) * this.scrollBar.getSliderValue() : 0.0F;
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char typedChar, int keyCode)
    {
        this.nameTextField.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
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
