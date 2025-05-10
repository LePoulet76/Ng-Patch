package net.ilexiconn.nationsgui.forge.client.gui.ghost;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUIClientHooks;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.GhostBuyPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GhostGUI extends GuiScreen
{
    public static final ResourceLocation TEXTURE = new ResourceLocation("nationsgui", "textures/gui/ghost.png");
    private static final int WIDTH = 289;
    private static final int HEIGHT = 172;
    private ItemStack[] itemStacks;
    private Map<String, Integer> playerScores;
    private int score;
    private boolean displayRank = false;
    private List<String> rulesLines = new ArrayList();
    private ItemStack currentItem = null;
    private final RenderItem renderItem = new RenderItem();
    private int guiLeft;
    private int guiTop;
    private int itemsX;
    private int itemsY;
    private GuiScrollGhost scroller;

    public GhostGUI(ItemStack[] itemStacks, Map<String, Integer> playerScores, int score)
    {
        this.itemStacks = itemStacks;
        this.playerScores = playerScores;
        this.score = score;
        this.buildTextRules();
    }

    private void buildTextRules()
    {
        StringBuilder sub = new StringBuilder();
        int i = 0;
        String line = I18n.getStringParams("ghost.rules", new Object[] {Integer.toString(this.score)});
        String[] words = line.split(" ");
        String[] var5 = words;
        int var6 = words.length;

        for (int var7 = 0; var7 < var6; ++var7)
        {
            String word = var5[var7];
            String temp = (!Objects.equals(words[0], word) ? " " : "") + word;

            if (Minecraft.getMinecraft().fontRenderer.getStringWidth(sub.toString()) + Minecraft.getMinecraft().fontRenderer.getStringWidth(temp) <= 111)
            {
                sub.append(temp);
            }
            else
            {
                this.rulesLines.add(sub.toString());
                sub = new StringBuilder(word);
                ++i;
            }
        }

        if (!sub.toString().equals(""))
        {
            this.rulesLines.add(sub.toString());
        }
    }

    public void setItemStacks(ItemStack[] itemStacks)
    {
        this.itemStacks = itemStacks;
        this.currentItem = null;
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        switch (par1GuiButton.id)
        {
            case 0:
                this.displayRank = !this.displayRank;
                par1GuiButton.displayString = I18n.getString(this.displayRank ? "ghost.rulesbutton" : "ghost.ranks");
                break;

            case 1:
                if (this.currentItem != null && this.score >= this.currentItem.getTagCompound().getInteger("ghostPoints"))
                {
                    List arrayList = Arrays.asList(this.itemStacks);
                    PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new GhostBuyPacket(arrayList.indexOf(this.currentItem))));
                    this.mc.displayGuiScreen((GuiScreen)null);
                }
        }
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.buttonList.clear();
        this.guiLeft = this.width / 2 - 144;
        this.guiTop = this.height / 2 - 86;
        this.itemsX = this.guiLeft + 57;
        this.itemsY = this.guiTop + 39;
        GhostButton rulesButton = new GhostButton(0, this.guiLeft + 168, this.guiTop + 135, I18n.getString(this.displayRank ? "ghost.rulesbutton" : "ghost.ranks"), false);
        GhostButton buyButton = new GhostButton(1, this.guiLeft + 56, this.guiTop + 135, I18n.getString("ghost.buy"), true);
        this.buttonList.add(rulesButton);

        if (this.currentItem != null)
        {
            buyButton.enabled = this.score >= this.currentItem.getTagCompound().getInteger("ghostPoints");
            this.buttonList.add(buyButton);
        }

        this.scroller = new GuiScrollGhost((float)(this.guiLeft + 265), (float)(this.guiTop + 25), 92);
    }

    public void setPlayerScores(Map<String, Integer> playerScores)
    {
        this.playerScores = playerScores;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.mc.getTextureManager().bindTexture(TEXTURE);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 289, 172);
        this.drawString(this.fontRenderer, I18n.getString("ghost.subtitle"), this.guiLeft + 67, this.guiTop + 25, 16777215);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.guiLeft + 14), (float)(this.guiTop + 43), 0.0F);
        GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef((float)(-this.fontRenderer.getStringWidth(I18n.getString("ghost.title"))) * 1.5F - 32.0F, 0.0F, 0.0F);
        GL11.glDisable(GL11.GL_LIGHTING);
        this.drawScaledString(I18n.getString("ghost.title"), 0, 0, 16777215, 1.5F, false, false);
        GL11.glPopMatrix();
        int i;
        int itemStack;
        int var12;

        if (this.displayRank)
        {
            this.mc.getTextureManager().bindTexture(TEXTURE);
            this.drawTexturedModalRect(this.guiLeft + 168, this.guiTop + 22, 293, 8, 102, 98);
            i = this.guiLeft + 168;
            GUIUtils.startGLScissor(this.guiLeft + 168, this.guiTop + 22, 102, 98);
            GL11.glPushMatrix();

            if (this.scroller != null && this.playerScores.size() > 6)
            {
                GL11.glTranslatef(0.0F, -((float)((this.playerScores.size() - 6) * 20) * this.scroller.sliderValue), 0.0F);
            }

            itemStack = 0;

            for (Iterator itemX = this.playerScores.entrySet().iterator(); itemX.hasNext(); ++itemStack)
            {
                Entry itemY = (Entry)itemX.next();
                int y = this.guiTop + itemStack * 18 + 26;
                ResourceLocation skin = AbstractClientPlayer.getLocationSkull((String)itemY.getKey());
                AbstractClientPlayer.getDownloadImageSkin(skin, (String)itemY.getKey());
                this.mc.getTextureManager().bindTexture(skin);
                GUIUtils.drawScaledCustomSizeModalRect(i + 11, y + 8, 8.0F, 16.0F, 8, -8, -8, -8, 64.0F, 64.0F);
                GUIUtils.drawScaledCustomSizeModalRect(i + 11, y + 8, 40.0F, 16.0F, 8, -8, -8, -8, 64.0F, 64.0F);
                GL11.glPushMatrix();
                GL11.glTranslatef((float)(i + 14), (float)(y + 2), 0.0F);
                GL11.glScalef(0.75F, 0.75F, 0.75F);
                this.drawString(this.fontRenderer, (String)itemY.getKey(), 0, 0, 16777215);
                GL11.glPopMatrix();
                this.drawString(this.fontRenderer, Integer.toString(((Integer)itemY.getValue()).intValue()), i + 85 - this.fontRenderer.getStringWidth(Integer.toString(((Integer)itemY.getValue()).intValue())), y, 16777215);
                GL11.glDisable(GL11.GL_LIGHTING);
                this.mc.getTextureManager().bindTexture(TEXTURE);
                this.drawTexturedModalRect(i + 87, y, 0, 182, 9, 9);
                this.drawTexturedModalRect(i + 3, y + 12, 171, 22, 92, 1);
            }

            GL11.glPopMatrix();
            GUIUtils.endGLScissor();

            if (this.scroller != null)
            {
                this.scroller.draw(par1, par2);
            }
        }
        else
        {
            i = this.guiLeft + 169;
            itemStack = this.guiTop + 24;
            GL11.glPushMatrix();
            GL11.glTranslatef((float)i, (float)itemStack, 0.0F);
            GL11.glScalef(0.9F, 0.9F, 0.9F);

            for (var12 = 0; var12 < this.rulesLines.size(); ++var12)
            {
                String var14 = (String)this.rulesLines.get(var12);
                this.drawString(this.fontRenderer, var14, 0, var12 * 12, 16777215);
            }

            GL11.glPopMatrix();
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderHelper.enableGUIStandardItemLighting();
        ItemStack var11;
        int var16;

        for (i = 0; i < this.itemStacks.length; ++i)
        {
            var11 = this.itemStacks[i];

            if (var11 != null)
            {
                var12 = this.itemsX + i % 5 * 19;
                var16 = this.itemsY + i / 5 * 19;

                if (var11 == this.currentItem)
                {
                    this.mc.getTextureManager().bindTexture(TEXTURE);
                    this.drawTexturedModalRect(var12 - 1, var16 - 1, 100, 214, 18, 18);
                }

                this.renderItem.zLevel = 5.0F;
                this.renderItem.renderItemAndEffectIntoGUI(this.fontRenderer, this.mc.getTextureManager(), var11, var12, var16);
                this.renderItem.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.getTextureManager(), var11, var12, var16);
            }
        }

        RenderHelper.disableStandardItemLighting();
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glDisable(GL11.GL_BLEND);

        if (this.currentItem != null)
        {
            this.mc.getTextureManager().bindTexture(TEXTURE);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            RenderHelper.enableGUIStandardItemLighting();
            this.renderItem.renderItemAndEffectIntoGUI(this.fontRenderer, this.mc.getTextureManager(), this.currentItem, this.guiLeft + 62, this.guiTop + 111);
            RenderHelper.disableStandardItemLighting();
            GL11.glColor3f(1.0F, 1.0F, 1.0F);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glPushMatrix();
            float var10 = Math.min(0.75F, 66.0F / (float)this.fontRenderer.getStringWidth(this.currentItem.getDisplayName()));
            GL11.glTranslatef((float)(this.guiLeft + 81 + 33) - (float)this.fontRenderer.getStringWidth(this.currentItem.getDisplayName()) / 2.0F * var10, (float)(this.guiTop + 110), 0.0F);
            GL11.glScaled((double)var10, (double)var10, (double)var10);
            this.drawString(this.fontRenderer, this.currentItem.getDisplayName(), 0, 0, 16777215);
            GL11.glPopMatrix();
            NBTTagCompound var13 = this.currentItem.getTagCompound();
            String var15 = Integer.toString(var13.getInteger("ghostPoints"));
            var16 = this.guiLeft + 81 + (66 - this.fontRenderer.getStringWidth(var15) - 10) / 2;
            this.drawString(this.fontRenderer, var15, var16, this.guiTop + 120, 16777215);
            this.mc.getTextureManager().bindTexture(TEXTURE);
            this.drawTexturedModalRect(var16 + this.fontRenderer.getStringWidth(var15) + 1, this.guiTop + 119, 0, 182, 9, 9);
        }

        GL11.glDisable(GL11.GL_LIGHTING);
        super.drawScreen(par1, par2, par3);

        for (i = 0; i < this.itemStacks.length; ++i)
        {
            var11 = this.itemStacks[i];

            if (var11 != null)
            {
                var12 = this.itemsX + i % 5 * 19;
                var16 = this.itemsY + i / 5 * 19;

                if (par1 >= var12 && par1 <= var12 + 16 && par2 >= var16 && par2 <= var16 + 16)
                {
                    NationsGUIClientHooks.drawItemStackTooltip(var11, par1, par2);
                }
            }
        }

        GL11.glEnable(GL11.GL_LIGHTING);
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
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        for (int i = 0; i < this.itemStacks.length; ++i)
        {
            ItemStack itemStack = this.itemStacks[i];

            if (itemStack != null)
            {
                int itemX = this.itemsX + i % 5 * 19;
                int itemY = this.itemsY + i / 5 * 19;

                if (par1 >= itemX && par1 <= itemX + 16 && par2 >= itemY && par2 <= itemY + 16)
                {
                    this.currentItem = itemStack;
                    this.initGui();
                    break;
                }
            }
        }

        super.mouseClicked(par1, par2, par3);
    }

    /**
     * Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
     */
    public void drawTexturedModalRect(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        float f = 0.001953125F;
        float f1 = 0.001953125F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(par1 + 0), (double)(par2 + par6), (double)this.zLevel, (double)((float)(par3 + 0) * f), (double)((float)(par4 + par6) * f1));
        tessellator.addVertexWithUV((double)(par1 + par5), (double)(par2 + par6), (double)this.zLevel, (double)((float)(par3 + par5) * f), (double)((float)(par4 + par6) * f1));
        tessellator.addVertexWithUV((double)(par1 + par5), (double)(par2 + 0), (double)this.zLevel, (double)((float)(par3 + par5) * f), (double)((float)(par4 + 0) * f1));
        tessellator.addVertexWithUV((double)(par1 + 0), (double)(par2 + 0), (double)this.zLevel, (double)((float)(par3 + 0) * f), (double)((float)(par4 + 0) * f1));
        tessellator.draw();
    }
}
