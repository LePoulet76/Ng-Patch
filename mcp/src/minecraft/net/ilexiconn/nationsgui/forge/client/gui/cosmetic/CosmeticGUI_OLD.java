package net.ilexiconn.nationsgui.forge.client.gui.cosmetic;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.CloseButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBar;
import net.ilexiconn.nationsgui.forge.client.gui.InventoryGUI;
import net.ilexiconn.nationsgui.forge.client.gui.TexturedButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.CosmeticGUI_OLD$1;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.CosmeticGUI_OLD$2;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.CosmeticGUI_OLD$3;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.impl.GetGroupAndPrimePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class CosmeticGUI_OLD extends GuiScreen
{
    public static final List<GuiScreenTab> TABS = new ArrayList();
    public static final ResourceLocation TEXTURE = new ResourceLocation("nationsgui", "textures/gui/cosmetic.png");
    public static NBTTagCompound CLIENT_BADGES = null;
    public static String CLICKED_BADGE = null;
    public static String MAIN_BADGE = null;
    public static boolean loaded = false;
    private String[] availableBadges;
    private String[] activeBadges;
    private RenderItem itemRenderer = new RenderItem();
    private int guiLeft = 0;
    private int guiTop = 0;
    private int xSize = 257;
    private int ySize = 209;
    private GuiButton addButton;
    private GuiButton removeButton;
    private GuiScrollBar scrollBar;

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        loaded = false;
        this.guiLeft = this.width / 2 - this.xSize / 2;
        this.guiTop = this.height / 2 - this.ySize / 2;
        this.buttonList.clear();
        CLIENT_BADGES = null;
        CLICKED_BADGE = null;
        MAIN_BADGE = null;
        this.activeBadges = null;
        this.availableBadges = null;
        this.addButton = new TexturedButtonGUI(0, this.guiLeft + 165, this.guiTop + 175, 74, 19, "cosmetic_btns", 0, 0, I18n.getString("gui.cosmetic.add"));
        this.removeButton = new TexturedButtonGUI(1, this.guiLeft + 165, this.guiTop + 175, 74, 19, "cosmetic_btns", 74, 0, I18n.getString("gui.cosmetic.remove"));
        this.buttonList.add(new CloseButtonGUI(2, this.guiLeft + 236, this.guiTop + 17));
        this.scrollBar = new GuiScrollBar((float)(this.guiLeft + 177), (float)(this.guiTop + 53), 106);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.guiLeft = this.width / 2 - 128;
        this.guiTop = this.height / 2 - 104;
        this.drawDefaultBackground();

        if (MAIN_BADGE == null && GetGroupAndPrimePacket.GRP_BADGES_PLAYERS.containsKey(Minecraft.getMinecraft().thePlayer.username) && NationsGUI.BADGES_RESOURCES.containsKey(GetGroupAndPrimePacket.GRP_BADGES_PLAYERS.get(Minecraft.getMinecraft().thePlayer.username)))
        {
            MAIN_BADGE = (String)GetGroupAndPrimePacket.GRP_BADGES_PLAYERS.get(Minecraft.getMinecraft().thePlayer.username);
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        ClientEventHandler.STYLE.bindTexture("cosmetic");
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        ClientEventHandler.STYLE.bindTexture("cosmetic");
        int i;
        GuiScreenTab j;
        int k;
        int tooltipLines;

        for (i = 0; i <= 3; ++i)
        {
            j = (GuiScreenTab)TABS.get(i);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            k = i % 4;
            tooltipLines = i / 4;

            if (this.getClass() == j.getClassReferent())
            {
                this.drawTexturedModalRect(this.guiLeft - 23, this.guiTop + 50 + i * 31, 125, 210, 29, 30);
                this.drawTexturedModalRect(this.guiLeft - 23 + 3, this.guiTop + 50 + i * 31 + 5, 154 + k * 20, 210 + tooltipLines * 20, 20, 20);
            }
            else
            {
                this.drawTexturedModalRect(this.guiLeft - 20, this.guiTop + 50 + i * 31, 102, 210, 23, 30);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
                this.drawTexturedModalRect(this.guiLeft - 20 + 3, this.guiTop + 50 + i * 31 + 5, 154 + k * 20, 210 + tooltipLines * 20, 20, 20);
                GL11.glDisable(GL11.GL_BLEND);
            }
        }

        for (i = 4; i < TABS.size(); ++i)
        {
            j = (GuiScreenTab)TABS.get(i);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            k = i % 4;
            tooltipLines = i / 4;

            if (this.getClass() == j.getClassReferent())
            {
                this.drawTexturedModalRect(this.guiLeft + 250, this.guiTop + 50 + (i - 4) * 31, 51, 210, 29, 30);
                this.drawTexturedModalRect(this.guiLeft + 250 + 3, this.guiTop + 50 + (i - 4) * 31 + 5, 154 + k * 20, 210 + tooltipLines * 20, 20, 20);
            }
            else
            {
                this.drawTexturedModalRect(this.guiLeft + 252, this.guiTop + 50 + (i - 4) * 31, 80, 210, 23, 30);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
                this.drawTexturedModalRect(this.guiLeft + 250 + 3, this.guiTop + 50 + (i - 4) * 31 + 5, 154 + k * 20, 210 + tooltipLines * 20, 20, 20);
                GL11.glDisable(GL11.GL_BLEND);
            }
        }

        this.drawScaledString(I18n.getString("gui.cosmetic.title"), this.guiLeft + 20, this.guiTop + 15, 16777215, 2.0F, false);

        if (loaded)
        {
            if (this.activeBadges == null && CLIENT_BADGES.hasKey("Active_Badge") && CLIENT_BADGES.hasKey("Active_Badge") && !CLIENT_BADGES.getString("Active_Badge").equals("null"))
            {
                this.activeBadges = CLIENT_BADGES.getString("Active_Badge").split(",");
            }

            this.availableBadges = CLIENT_BADGES.getString("AvailableBadges").split(",");
            this.availableBadges = this.removeAvailableBadgesIfActive(this.availableBadges);

            if (CLICKED_BADGE != null && NationsGUI.BADGES_RESOURCES.containsKey(CLICKED_BADGE))
            {
                Minecraft.getMinecraft().getTextureManager().bindTexture((ResourceLocation)NationsGUI.BADGES_RESOURCES.get(CLICKED_BADGE));
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 23), (float)(this.guiTop + 175), 0, 0, 18, 18, 18.0F, 18.0F, false);

                if (NationsGUI.BADGES_NAMES.containsKey(CLICKED_BADGE))
                {
                    this.drawScaledString((String)NationsGUI.BADGES_NAMES.get(CLICKED_BADGE), this.guiLeft + 23 + 30, this.guiTop + 180, 16777215, 1.2F, false);
                }
                else
                {
                    this.drawScaledString(CLICKED_BADGE, this.guiLeft + 23 + 30, this.guiTop + 180, 16777215, 1.2F, false);
                }
            }

            ClientEventHandler.STYLE.bindTexture("cosmetic");
            Double var16;

            if (MAIN_BADGE != null)
            {
                GL11.glScaled(0.75D, 0.75D, 0.75D);
                Minecraft.getMinecraft().getTextureManager().bindTexture((ResourceLocation)NationsGUI.BADGES_RESOURCES.get(MAIN_BADGE));
                Double var15 = Double.valueOf(((double)this.guiLeft + 193.5D) * 1.0D / 0.75D);
                var16 = Double.valueOf((double)((this.guiTop + 54) * 1) / 0.75D);
                ModernGui.drawModalRectWithCustomSizedTexture((float)var15.intValue(), (float)var16.intValue(), 0, 0, 18, 18, 18.0F, 18.0F, false);
                GL11.glScaled(1.3333333333333333D, 1.3333333333333333D, 1.3333333333333333D);
            }
            else
            {
                this.drawTexturedModalRect(this.guiLeft + 191, this.guiTop + 52, 33, 210, 18, 18);

                if (mouseX > this.guiLeft + 191 && mouseX < this.guiLeft + 191 + 18 && mouseY > this.guiTop + 52 && mouseY < this.guiTop + 52 + 18)
                {
                    this.drawHoveringText(Collections.singletonList("\u00a7cEmplacement r\u00e9serv\u00e9"), mouseX, mouseY, this.fontRenderer);
                }
            }

            if (this.activeBadges != null)
            {
                GL11.glPushMatrix();
                GL11.glScaled(0.75D, 0.75D, 0.75D);

                for (i = 0; i < this.activeBadges.length; ++i)
                {
                    int var17 = i % 9;
                    k = i / 9;
                    Double availableBadge;
                    Double var20;

                    if (NationsGUI.BADGES_RESOURCES.containsKey(this.activeBadges[i]))
                    {
                        Minecraft.getMinecraft().getTextureManager().bindTexture((ResourceLocation)NationsGUI.BADGES_RESOURCES.get(this.activeBadges[i]));
                        var20 = Double.valueOf(((double)this.guiLeft + 211.5D + (double)(var17 * 18)) * 1.0D / 0.75D);
                        availableBadge = Double.valueOf(((double)this.guiTop + 54.5D + (double)(k * 18)) * 1.0D / 0.75D);
                        ModernGui.drawModalRectWithCustomSizedTexture((float)var20.intValue(), (float)availableBadge.intValue(), 0, 0, 18, 18, 18.0F, 18.0F, false);
                    }

                    if (CLICKED_BADGE != null && CLICKED_BADGE.equals(this.activeBadges[i]))
                    {
                        ClientEventHandler.STYLE.bindTexture("cosmetic");
                        GL11.glScaled(1.3333333333333333D, 1.3333333333333333D, 1.3333333333333333D);
                        GL11.glEnable(GL11.GL_BLEND);
                        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                        this.zLevel = 300.0F;
                        var20 = Double.valueOf((double)this.guiLeft + 206.5D + (double)(var17 * 18));
                        availableBadge = Double.valueOf((double)this.guiTop + 49.5D + (double)(k * 18));
                        this.drawTexturedModalRect(var20.intValue(), availableBadge.intValue(), 9, 210, 24, 24);
                        this.zLevel = 0.0F;
                        GL11.glDisable(GL11.GL_BLEND);
                        GL11.glScaled(0.75D, 0.75D, 0.75D);
                    }
                }

                GL11.glScaled(1.3333333333333333D, 1.3333333333333333D, 1.3333333333333333D);

                for (i = 0; i < this.activeBadges.length; ++i)
                {
                    var16 = Double.valueOf((double)(this.guiLeft + i % 9 * 18) + 211.0D);
                    Double var19 = Double.valueOf((double)(this.guiTop + i / 9 * 18) + 52.0D);

                    if ((double)mouseX > var16.doubleValue() && (double)mouseX < var16.doubleValue() + 18.0D && (double)mouseY > var19.doubleValue() && (double)mouseY < var19.doubleValue() + 18.0D)
                    {
                        if (NationsGUI.BADGES_TOOLTIPS.containsKey(this.activeBadges[i]))
                        {
                            List var21 = (List)NationsGUI.BADGES_TOOLTIPS.get(this.activeBadges[i]);
                            this.drawHoveringText(var21, mouseX, mouseY, this.fontRenderer);
                        }

                        if (Mouse.isButtonDown(0) && (CLICKED_BADGE == null || CLICKED_BADGE != null && !CLICKED_BADGE.equals(this.activeBadges[i])))
                        {
                            Minecraft.getMinecraft().sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                            CLICKED_BADGE = this.activeBadges[i];
                            this.clickOnBadge(this.activeBadges[i]);
                        }
                    }
                }

                if (MAIN_BADGE != null)
                {
                    if (NationsGUI.BADGES_TOOLTIPS.containsKey(MAIN_BADGE) && mouseX > this.guiLeft + 192 && mouseX < this.guiLeft + 192 + 18 && mouseY > this.guiTop + 53 && mouseY < this.guiTop + 53 + 18)
                    {
                        this.drawHoveringText((List)NationsGUI.BADGES_TOOLTIPS.get(MAIN_BADGE), mouseX, mouseY, this.fontRenderer);
                    }
                }
                else if (mouseX > this.guiLeft + 191 && mouseX < this.guiLeft + 191 + 18 && mouseY > this.guiTop + 52 && mouseY < this.guiTop + 52 + 18)
                {
                    this.drawHoveringText(Collections.singletonList("\u00a7cEmplacement r\u00e9serv\u00e9"), mouseX, mouseY, this.fontRenderer);
                }

                GL11.glPopMatrix();
                ClientEventHandler.STYLE.bindTexture("cosmetic");
            }

            if (CLIENT_BADGES != null && CLIENT_BADGES.hasKey("AvailableBadges"))
            {
                GL11.glPushMatrix();
                GUIUtils.startGLScissor(this.guiLeft + 9, this.guiTop + 51, 164, 110);
                GL11.glScaled(0.75D, 0.75D, 0.75D);
                i = 0;
                String[] var18 = this.availableBadges;
                k = var18.length;
                String var22;

                for (tooltipLines = 0; tooltipLines < k; ++tooltipLines)
                {
                    var22 = var18[tooltipLines];
                    int j1 = i % 9;
                    int k1 = i / 9;

                    if (NationsGUI.BADGES_RESOURCES.containsKey(var22))
                    {
                        Minecraft.getMinecraft().getTextureManager().bindTexture((ResourceLocation)NationsGUI.BADGES_RESOURCES.get(var22));
                        Double tooltipLines1 = Double.valueOf(((double)this.guiLeft + 12.5D + (double)(j1 * 18)) * 1.0D / 0.75D);
                        Double offsetY = Double.valueOf(((double)this.guiTop + 54.5D + (double)(k1 * 18) + (double)this.getSlide()) * 1.0D / 0.75D);
                        ModernGui.drawModalRectWithCustomSizedTexture((float)tooltipLines1.intValue(), (float)offsetY.intValue(), 0, 0, 18, 18, 18.0F, 18.0F, false);

                        if (CLICKED_BADGE != null && CLICKED_BADGE.equals(var22))
                        {
                            ClientEventHandler.STYLE.bindTexture("cosmetic");
                            GL11.glScaled(1.3333333333333333D, 1.3333333333333333D, 1.3333333333333333D);
                            GL11.glEnable(GL11.GL_BLEND);
                            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                            this.zLevel = 300.0F;
                            Double offsetXSelect = Double.valueOf((double)this.guiLeft + 7.5D + (double)(j1 * 18));
                            Double offsetYSelect = Double.valueOf((double)this.guiTop + 49.0D + (double)(k1 * 18));
                            this.drawTexturedModalRect(offsetXSelect.intValue(), offsetYSelect.intValue(), 9, 210, 24, 24);
                            this.zLevel = 0.0F;
                            GL11.glDisable(GL11.GL_BLEND);
                            GL11.glScaled(0.75D, 0.75D, 0.75D);
                        }

                        ++i;
                    }
                }

                GL11.glScaled(1.3333333333333333D, 1.3333333333333333D, 1.3333333333333333D);
                GUIUtils.endGLScissor();
                this.scrollBar.draw(mouseX, mouseY);
                i = 0;
                var18 = this.availableBadges;
                k = var18.length;

                for (tooltipLines = 0; tooltipLines < k; ++tooltipLines)
                {
                    var22 = var18[tooltipLines];

                    if (NationsGUI.BADGES_RESOURCES.containsKey(var22))
                    {
                        Double var23 = Double.valueOf((double)(this.guiLeft + i % 9 * 18) + 9.375D);
                        Double var24 = Double.valueOf((double)(this.guiTop + i / 9 * 18) + 52.0D);

                        if ((double)mouseX > var23.doubleValue() && (double)mouseX < var23.doubleValue() + 18.0D && (double)mouseY > var24.doubleValue() && (double)mouseY < var24.doubleValue() + 18.0D)
                        {
                            if (NationsGUI.BADGES_TOOLTIPS.containsKey(var22))
                            {
                                List var25 = (List)NationsGUI.BADGES_TOOLTIPS.get(var22);
                                this.drawHoveringText(var25, mouseX, mouseY, this.fontRenderer);
                            }

                            if (Mouse.isButtonDown(0) && (CLICKED_BADGE == null || CLICKED_BADGE != null && !CLICKED_BADGE.equals(var22)))
                            {
                                Minecraft.getMinecraft().sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                                CLICKED_BADGE = var22;
                                this.clickOnBadge(var22);
                            }
                        }

                        ++i;
                    }
                }

                GL11.glPopMatrix();
                ClientEventHandler.STYLE.bindTexture("cosmetic");
            }
        }

        for (i = 0; i <= 3; ++i)
        {
            if (mouseX >= this.guiLeft - (this.getClass() == ((GuiScreenTab)InventoryGUI.TABS.get(i)).getClassReferent() ? 23 : 20) && mouseX <= this.guiLeft + 3 && mouseY >= this.guiTop + 50 + i * 31 && mouseY <= this.guiTop + 85 + i * 31)
            {
                this.drawHoveringText(Collections.singletonList(I18n.getString("gui.cosmetic.tab." + i)), mouseX, mouseY, this.fontRenderer);
            }
        }

        for (i = 4; i < TABS.size(); ++i)
        {
            if (mouseX >= this.guiLeft + 252 && mouseX <= this.guiLeft + 278 && mouseY >= this.guiTop + 50 + (i - 4) * 31 && mouseY <= this.guiTop + 85 + (i - 4) * 31)
            {
                this.drawHoveringText(Collections.singletonList(I18n.getString("gui.cosmetic.tab." + i)), mouseX, mouseY, this.fontRenderer);
            }
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private float getSlide()
    {
        return this.availableBadges.length > 54 ? (float)(-(this.availableBadges.length - 54) * 18) * this.scrollBar.getSliderValue() : 0.0F;
    }

    public void drawScaledString(String text, int x, int y, int color, float scale, boolean centered)
    {
        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, scale);
        float newX = (float)x;

        if (centered)
        {
            newX = (float)x - (float)this.fontRenderer.getStringWidth(text) * scale / 2.0F;
        }

        this.fontRenderer.drawString(text, (int)(newX / scale), (int)((float)(y + 1) / scale), (color & 16579836) >> 2 | color & -16777216, false);
        this.fontRenderer.drawString(text, (int)(newX / scale), (int)((float)y / scale), color, false);
        GL11.glPopMatrix();
    }

    public String[] removeAvailableBadgesIfActive(String[] availableBadges)
    {
        ArrayList availableBadgesWhichAreNotActive = new ArrayList();
        String[] var3 = availableBadges;
        int var4 = availableBadges.length;

        for (int var5 = 0; var5 < var4; ++var5)
        {
            String availableBadge = var3[var5];
            boolean add = true;

            if (this.activeBadges != null)
            {
                String[] var8 = this.activeBadges;
                int var9 = var8.length;

                for (int var10 = 0; var10 < var9; ++var10)
                {
                    String activeBadge = var8[var10];

                    if (activeBadge.equals(availableBadge))
                    {
                        add = false;
                    }
                }
            }

            if (MAIN_BADGE != null && MAIN_BADGE.equals(availableBadge))
            {
                add = false;
            }

            if (add)
            {
                availableBadgesWhichAreNotActive.add(availableBadge);
            }
        }

        return (String[])availableBadgesWhichAreNotActive.toArray(new String[0]);
    }

    private void clickOnBadge(String badge)
    {
        ArrayList activeBadgesTab = null;

        if (this.activeBadges != null)
        {
            activeBadgesTab = new ArrayList(Arrays.asList(this.activeBadges));
        }

        if (activeBadgesTab != null && activeBadgesTab.contains(badge))
        {
            this.buttonList.add(this.removeButton);
        }
        else
        {
            this.buttonList.add(this.addButton);
        }
    }

    public void drawHoveringText(List<String> text, int mouseX, int mouseY, FontRenderer fontRenderer)
    {
        if (!text.isEmpty())
        {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            int width = 0;
            int offsetY;

            for (Iterator posX = text.iterator(); posX.hasNext(); width = Math.max(width, offsetY))
            {
                String posY = (String)posX.next();
                offsetY = fontRenderer.getStringWidth(posY);
            }

            int var14 = mouseX + 12;
            int var15 = mouseY - 12;
            offsetY = 8;

            if (text.size() > 1)
            {
                offsetY += 2 + (text.size() - 1) * 10;
            }

            if (var14 + width > this.width)
            {
                var14 -= 28 + width;
            }

            if (var15 + offsetY + 6 > this.height)
            {
                var15 = this.height - offsetY - 6;
            }

            this.zLevel = 300.0F;
            this.itemRenderer.zLevel = 300.0F;
            int color1 = -267386864;
            this.drawGradientRect(var14 - 3, var15 - 4, var14 + width + 3, var15 - 3, color1, color1);
            this.drawGradientRect(var14 - 3, var15 + offsetY + 3, var14 + width + 3, var15 + offsetY + 4, color1, color1);
            this.drawGradientRect(var14 - 3, var15 - 3, var14 + width + 3, var15 + offsetY + 3, color1, color1);
            this.drawGradientRect(var14 - 4, var15 - 3, var14 - 3, var15 + offsetY + 3, color1, color1);
            this.drawGradientRect(var14 + width + 3, var15 - 3, var14 + width + 4, var15 + offsetY + 3, color1, color1);
            int color2 = 1347420415;
            int color3 = (color2 & 16711422) >> 1 | color2 & -16777216;
            this.drawGradientRect(var14 - 3, var15 - 3 + 1, var14 - 3 + 1, var15 + offsetY + 3 - 1, color2, color3);
            this.drawGradientRect(var14 + width + 2, var15 - 3 + 1, var14 + width + 3, var15 + offsetY + 3 - 1, color2, color3);
            this.drawGradientRect(var14 - 3, var15 - 3, var14 + width + 3, var15 - 3 + 1, color2, color2);
            this.drawGradientRect(var14 - 3, var15 + offsetY + 2, var14 + width + 3, var15 + offsetY + 3, color3, color3);

            for (int i = 0; i < text.size(); ++i)
            {
                String line = (String)text.get(i);

                if (i == 0)
                {
                    fontRenderer.drawStringWithShadow(line, var14, var15, -1);
                    var15 += 2;
                }
                else
                {
                    fontRenderer.drawStringWithShadow(EnumChatFormatting.GOLD + line, var14 + width - fontRenderer.getStringWidth(line), var15, 16777215);
                }

                var15 += 10;
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
            int i;
            GuiScreenTab type;

            for (i = 0; i <= 3; ++i)
            {
                type = (GuiScreenTab)TABS.get(i);

                if (mouseX >= this.guiLeft - 20 && mouseX <= this.guiLeft + 3 && mouseY >= this.guiTop + 50 + i * 31 && mouseY <= this.guiTop + 85 + i * 31)
                {
                    this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);

                    if (this.getClass() != type.getClassReferent())
                    {
                        try
                        {
                            type.call();
                        }
                        catch (Exception var8)
                        {
                            var8.printStackTrace();
                        }
                    }
                }
            }

            for (i = 4; i < TABS.size(); ++i)
            {
                type = (GuiScreenTab)TABS.get(i);

                if (mouseX >= this.guiLeft + 252 && mouseX <= this.guiLeft + 278 && mouseY >= this.guiTop + 50 + (i - 4) * 31 && mouseY <= this.guiTop + 85 + (i - 4) * 31)
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
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 2)
        {
            this.mc.displayGuiScreen((GuiScreen)null);
            this.mc.setIngameFocus();
            this.mc.sndManager.resumeAllSounds();
        }

        if (this.availableBadges != null)
        {
            ArrayList newActiveBadges;

            if (button.id == 0)
            {
                if (this.activeBadges == null || this.activeBadges.length < 2)
                {
                    newActiveBadges = new ArrayList();

                    if (this.activeBadges != null)
                    {
                        newActiveBadges = new ArrayList(Arrays.asList(this.activeBadges));
                    }

                    newActiveBadges.add(CLICKED_BADGE);
                    this.activeBadges = (String[])newActiveBadges.toArray(new String[0]);
                    CLICKED_BADGE = null;
                    this.buttonList.clear();
                }
            }
            else if (button.id == 1 && this.activeBadges.length > 0)
            {
                newActiveBadges = new ArrayList(Arrays.asList(this.activeBadges));
                newActiveBadges.remove(CLICKED_BADGE);
                this.activeBadges = (String[])newActiveBadges.toArray(new String[0]);
                CLICKED_BADGE = null;
                this.buttonList.clear();
            }
        }
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    static
    {
        TABS.add(new CosmeticGUI_OLD$1());
        TABS.add(new CosmeticGUI_OLD$2());
        TABS.add(new CosmeticGUI_OLD$3());
    }
}
