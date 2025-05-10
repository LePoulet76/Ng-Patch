package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionEnemyListPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionGetImagePacket;
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

public class WarRequestListGUI extends GuiScreen
{
    protected int xSize = 319;
    protected int ySize = 248;
    private int guiLeft;
    private int guiTop;
    private RenderItem itemRenderer = new RenderItem();
    public static ArrayList<HashMap<String, Object>> warsInfos = new ArrayList();
    public int hoveredRequestId = -1;
    public String hoveredStatus = "";
    public String selectedStatus = "";
    public boolean expandStatus = false;
    private GuiScrollBarFaction scrollBarRequests;
    public static boolean loaded = false;
    private GuiTextField countrySearch;
    public static List<String> availableStatus = Arrays.asList(new String[] {"all", "waiting_validation", "refused", "waiting_conditions_att", "waiting_conditions_def", "in_progress", "finished", "cancelled"});

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        loaded = false;
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionEnemyListPacket((String)FactionGUI.factionInfos.get("id"))));
        this.scrollBarRequests = new GuiScrollBarFaction((float)(this.guiLeft + 303), (float)(this.guiTop + 55), 172);
        this.countrySearch = new GuiTextField(this.fontRenderer, this.guiLeft + 209, this.guiTop + 23, 97, 10);
        this.countrySearch.setEnableBackgroundDrawing(false);
        this.countrySearch.setMaxStringLength(25);
        this.selectedStatus = (String)availableStatus.get(0);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.countrySearch.updateCursorCounter();
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        this.drawDefaultBackground();
        this.hoveredRequestId = -1;
        this.hoveredStatus = "";
        ClientEventHandler.STYLE.bindTexture("faction_war_requests");
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);

        if (mouseX >= this.guiLeft + 305 && mouseX <= this.guiLeft + 305 + 9 && mouseY >= this.guiTop - 6 && mouseY <= this.guiTop - 6 + 10)
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 305), (float)(this.guiTop - 6), 46, 258, 9, 10, 512.0F, 512.0F, false);
        }
        else
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 305), (float)(this.guiTop - 6), 46, 248, 9, 10, 512.0F, 512.0F, false);
        }

        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.guiLeft + 14), (float)(this.guiTop + 210), 0.0F);
        GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef((float)(-(this.guiLeft + 16)), (float)(-(this.guiTop + 210)), 0.0F);
        this.drawScaledString(I18n.getString("faction.enemy.requests.title"), this.guiLeft + 14, this.guiTop + 210, 16777215, 1.5F, false, false);
        GL11.glPopMatrix();
        this.countrySearch.drawTextBox();

        if (warsInfos.size() > 0)
        {
            String tooltipToDraw = "";
            GUIUtils.startGLScissor(this.guiLeft + 47, this.guiTop + 53, 256, 176);
            int yOffset = 0;
            Iterator i = warsInfos.iterator();

            while (i.hasNext())
            {
                HashMap warInfos = (HashMap)i.next();

                if ((this.countrySearch.getText().isEmpty() || ((String)warInfos.get("factionName")).toLowerCase().contains(this.countrySearch.getText().toLowerCase())) && (this.selectedStatus.equals("all") || ((String)warInfos.get("status")).startsWith(this.selectedStatus)))
                {
                    int offsetX = this.guiLeft + 47;
                    Float offsetY = Float.valueOf((float)(this.guiTop + 53 + yOffset) + this.getSlideWars());
                    ClientEventHandler.STYLE.bindTexture("faction_war_requests");
                    ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, offsetY.floatValue(), 47, 53, 256, 48, 512.0F, 512.0F, false);

                    if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 262 && mouseY >= this.guiTop + 53 && mouseY <= this.guiTop + 53 + 178 && mouseX >= offsetX && mouseX <= offsetX + 256 && (float)mouseY >= offsetY.floatValue() && (float)mouseY <= offsetY.floatValue() + 48.0F)
                    {
                        this.hoveredRequestId = ((Double)warInfos.get("id")).intValue();
                    }

                    ClientEventHandler.STYLE.bindTexture("faction_war_requests");

                    if (!ClientProxy.flagsTexture.containsKey((String)warInfos.get("factionName")))
                    {
                        if (!ClientProxy.base64FlagsByFactionName.containsKey((String)warInfos.get("factionName")))
                        {
                            ClientProxy.base64FlagsByFactionName.put((String)warInfos.get("factionName"), "");
                            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionGetImagePacket((String)warInfos.get("factionName"))));
                        }
                        else if (ClientProxy.base64FlagsByFactionName.containsKey((String)warInfos.get("factionName")) && !((String)ClientProxy.base64FlagsByFactionName.get((String)warInfos.get("factionName"))).isEmpty())
                        {
                            BufferedImage image = ClientProxy.decodeToImage((String)ClientProxy.base64FlagsByFactionName.get((String)warInfos.get("factionName")));
                            ClientProxy.flagsTexture.put((String)warInfos.get("factionName"), new DynamicTexture(image));
                        }
                    }

                    if (ClientProxy.flagsTexture.containsKey((String)warInfos.get("factionName")))
                    {
                        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((DynamicTexture)ClientProxy.flagsTexture.get((String)warInfos.get("factionName"))).getGlTextureId());
                        ModernGui.drawScaledCustomSizeModalRect((float)(offsetX + 7), (float)(offsetY.intValue() + 3), 0.0F, 0.0F, 156, 78, 27, 15, 156.0F, 78.0F, false);
                    }

                    this.drawScaledString((String)warInfos.get("factionName"), offsetX + 37, offsetY.intValue() + 7, 16777215, 1.0F, false, false);
                    this.drawScaledString(I18n.getString("faction.enemy.status." + warInfos.get("status")), offsetX + 253 - this.fontRenderer.getStringWidth(I18n.getString("faction.enemy.status." + warInfos.get("status"))), offsetY.intValue() + 7, 16777215, 1.0F, false, false);
                    this.drawScaledString(I18n.getString("faction.enemy.reason." + warInfos.get("reason")), offsetX + 7, offsetY.intValue() + 33, 16777215, 1.0F, false, false);
                    ClientEventHandler.STYLE.bindTexture("faction_war_requests");

                    if (mouseX >= offsetX + 202 && mouseX <= offsetX + 202 + 51 && (float)mouseY >= offsetY.floatValue() + 28.0F && (float)mouseY <= offsetY.floatValue() + 28.0F + 15.0F)
                    {
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 202), (float)(offsetY.intValue() + 28), 0, 287, 51, 15, 512.0F, 512.0F, false);
                    }
                    else
                    {
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 202), (float)(offsetY.intValue() + 28), 0, 272, 51, 15, 512.0F, 512.0F, false);
                    }

                    this.drawScaledString(I18n.getString("faction.enemy.requests.see"), offsetX + 227, offsetY.intValue() + 32, 0, 1.0F, true, false);
                    yOffset += 48;
                }
            }

            GUIUtils.endGLScissor();

            if (!this.expandStatus)
            {
                this.scrollBarRequests.draw(mouseX, mouseY);
            }

            this.drawScaledString(I18n.getString("faction.enemy.status." + this.selectedStatus), this.guiLeft + 50, this.guiTop + 23, 16777215, 1.0F, false, false);

            if (this.expandStatus)
            {
                ClientEventHandler.STYLE.bindTexture("faction_war_requests");

                for (int var11 = 0; var11 < availableStatus.size(); ++var11)
                {
                    ClientEventHandler.STYLE.bindTexture("faction_war_requests");
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 46), (float)(this.guiTop + 36 + 19 * var11), 0, 311, 120, 20, 512.0F, 512.0F, false);
                    this.drawScaledString(I18n.getString("faction.enemy.status." + (String)availableStatus.get(var11)), this.guiLeft + 50, this.guiTop + 36 + 19 * var11 + 6, 16777215, 1.0F, false, false);

                    if (mouseX > this.guiLeft + 46 && mouseX < this.guiLeft + 46 + 120 && mouseY > this.guiTop + 36 + 19 * var11 && mouseY < this.guiTop + 36 + 19 * var11 + 20)
                    {
                        this.hoveredStatus = (String)availableStatus.get(var11);
                    }
                }
            }
        }
    }

    private float getSlideWars()
    {
        return warsInfos.size() > 3 ? (float)(-(warsInfos.size() - 3) * 48) * this.scrollBarRequests.getSliderValue() : 0.0F;
    }

    public void drawTooltip(String text, int mouseX, int mouseY)
    {
        int var10000 = mouseX - this.guiLeft;
        var10000 = mouseY - this.guiTop;
        this.drawHoveringText(Arrays.asList(new String[] {text}), mouseX, mouseY, this.fontRenderer);
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (mouseButton == 0)
        {
            if (mouseX > this.guiLeft + 305 && mouseX < this.guiLeft + 305 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                this.mc.displayGuiScreen(new WarGUI());
            }

            if (!this.expandStatus && this.hoveredRequestId != -1)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen(new WarRequestGUI(this.hoveredRequestId, this));
            }

            if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 120 && mouseY >= this.guiTop + 17 && mouseY <= this.guiTop + 17 + 20)
            {
                this.expandStatus = !this.expandStatus;
            }

            if (this.hoveredStatus != null && !this.hoveredStatus.isEmpty())
            {
                this.selectedStatus = this.hoveredStatus;
                this.hoveredStatus = "";
                this.expandStatus = false;
            }
        }

        this.countrySearch.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char typedChar, int keyCode)
    {
        if (this.countrySearch.textboxKeyTyped(typedChar, keyCode))
        {
            this.scrollBarRequests.reset();
        }

        super.keyTyped(typedChar, keyCode);
    }

    private String formatDiff(long diff)
    {
        String date = "";
        long days = diff / 86400000L;
        long hours = 0L;
        long minutes = 0L;
        long seconds = 0L;

        if (days == 0L)
        {
            hours = diff / 3600000L;

            if (hours == 0L)
            {
                minutes = diff / 60000L;

                if (minutes == 0L)
                {
                    seconds = diff / 1000L;
                    date = date + " " + seconds + " " + I18n.getString("faction.common.seconds") + " " + I18n.getString("faction.bank.date_2");
                }
                else
                {
                    date = date + " " + minutes + " " + I18n.getString("faction.common.minutes") + " " + I18n.getString("faction.bank.date_2");
                }
            }
            else
            {
                date = date + " " + hours + " " + I18n.getString("faction.common.hours") + " " + I18n.getString("faction.bank.date_2");
            }
        }
        else
        {
            date = date + " " + days + " " + I18n.getString("faction.common.days") + " " + I18n.getString("faction.bank.date_2");
        }

        return date;
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
}
