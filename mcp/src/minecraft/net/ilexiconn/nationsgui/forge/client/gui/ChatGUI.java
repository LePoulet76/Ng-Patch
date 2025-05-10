package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.data.Objective;
import net.ilexiconn.nationsgui.forge.client.gui.ChatGUI$Button;
import net.ilexiconn.nationsgui.forge.client.gui.ChatGUI$ScrollBar;
import net.ilexiconn.nationsgui.forge.client.gui.ChatGUI$SimpleButton;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUIClientHooks;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.ObjectivePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class ChatGUI extends GuiChat
{
    private boolean displayText = false;
    private List<String> lines = new ArrayList();
    private ChatGUI$ScrollBar scrollBar;
    public static final int BOX_WIDTH = 400;
    public static final int BOX_HEIGHT = 250;
    private int heightModifier = 0;
    private RenderItem itemRenderer = new RenderItem();

    public ChatGUI() {}

    public ChatGUI(String par1Str)
    {
        super(par1Str);
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.buttonList.clear();
        byte y = 15;

        if (ClientProxy.clientConfig.displayObjectives && !Minecraft.getMinecraft().gameSettings.showDebugInfo)
        {
            if (!this.displayText)
            {
                this.buttonList.add(new ChatGUI$Button(this, 0, 118, y, 16, 16));
                this.buttonList.add(new ChatGUI$Button(this, 1, 134, y, 16, 16));

                if (!ClientData.objectives.isEmpty())
                {
                    this.buttonList.add(new ChatGUI$Button(this, 2, 134, y + 19, 16, 15));
                }

                this.scrollBar = null;
            }
            else
            {
                this.heightModifier = ((Objective)ClientData.objectives.get(ClientData.currentObjectiveIndex)).getItemStack() != null ? 10 : 0;
                this.scrollBar = new ChatGUI$ScrollBar(this, (float)(this.width / 2 + 200 - 15), (float)(this.height / 2 - 125 + 30), 185);

                if (((Objective)ClientData.objectives.get(ClientData.currentObjectiveIndex)).getItemStack() == null)
                {
                    this.buttonList.add(new ChatGUI$SimpleButton(this, 3, this.width / 2 + 200 - 56, this.height / 2 + 125 - 25, 50, 15, "Ok"));
                }
                else
                {
                    this.buttonList.add(new ChatGUI$SimpleButton(this, 4, this.width / 2 + 200 - 56, this.height / 2 + 125 - 25, 50, 15, "Ok"));
                    this.buttonList.add(new ChatGUI$SimpleButton(this, 3, this.width / 2 + 200 - 56 - 105, this.height / 2 + 125 - 25, 100, 15, I18n.getString("objectives.validate")));
                }
            }
        }
    }

    /**
     * Draws the background (i is always 0 as of 1.2.2)
     */
    public void drawBackground(int par1) {}

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        int posYHelperAttacker;
        int x;
        int y;
        int y1;

        if (this.displayText)
        {
            Objective posXHelperAttacker = (Objective)ClientData.objectives.get(ClientData.currentObjectiveIndex);
            drawRect(this.width / 2 - 200, this.height / 2 - 125 + 25, this.width / 2 + 200, this.height / 2 + 125, -301989888);
            drawRect(this.width / 2 - 200, this.height / 2 - 125 + 5, this.width / 2 + 200, this.height / 2 - 125 + 25, -1728053248);
            ClientEventHandler.STYLE.bindTexture("hud2");
            GL11.glColor3f(1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(this.width / 2 - 200 + 5, this.height / 2 - 125 + 8, 2, 20, 13, 13);
            this.drawString(this.fontRenderer, posXHelperAttacker.getTitle(), this.width / 2 - 200 + 20, this.height / 2 - 125 + 11, 16777215);
            GUIUtils.startGLScissor(this.width / 2 - 200, this.height / 2 - 125 + 5 + 30, 380, 185);
            GL11.glPushMatrix();

            if (this.lines.size() > 11)
            {
                GL11.glTranslatef(0.0F, -this.scrollBar.sliderValue * (float)((this.lines.size() - 11) * 12 + this.heightModifier), 0.0F);
            }

            posYHelperAttacker = 0;

            for (Iterator entityPlayer = this.lines.iterator(); entityPlayer.hasNext(); ++posYHelperAttacker)
            {
                String handItem = (String)entityPlayer.next();
                this.drawString(this.fontRenderer, handItem, this.width / 2 - 200 + 10, this.height / 2 - 125 + 35 + posYHelperAttacker * 12, 16777215);
            }

            ItemStack var14 = ((Objective)ClientData.objectives.get(ClientData.currentObjectiveIndex)).getItemStack();
            boolean var16 = false;

            if (var14 != null)
            {
                x = this.lines.size() * 12 + 2;
                y = this.fontRenderer.getStringWidth(I18n.getString("objectives.collect"));
                y1 = y + 16 + 4;
                this.fontRenderer.drawString(I18n.getString("objectives.collect"), this.width / 2 - 10 - y1 / 2, this.height / 2 - 125 + 35 + x + 4, 16777215);
                this.itemRenderer.renderItemAndEffectIntoGUI(this.fontRenderer, this.mc.getTextureManager(), var14, this.width / 2 - 10 - y1 / 2 + y + 4, this.height / 2 - 125 + 35 + x);
                this.itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.getTextureManager(), var14, this.width / 2 - 10 - y1 / 2 + y + 4, this.height / 2 - 125 + 35 + x);
                int pX = this.width / 2 - 10 - y1 / 2 + y + 4;
                int pY = this.height / 2 - 125 + 35 + x - (int)(this.scrollBar.sliderValue * (float)((this.lines.size() - 11) * 12 + this.heightModifier));
                var16 = par1 >= pX && par1 <= pX + 18 && par2 >= pY && par2 <= pY + 18;
                GL11.glDisable(GL11.GL_LIGHTING);
            }

            GL11.glPopMatrix();
            GUIUtils.endGLScissor();

            if (var16)
            {
                NationsGUIClientHooks.drawItemStackTooltip(var14, par1, par2);
                GL11.glDisable(GL11.GL_LIGHTING);
            }

            this.scrollBar.draw(par1, par2);
        }

        int var13;

        if (ClientProxy.clientConfig.specialEnabled && ClientProxy.clientConfig.displayArmorInInfo)
        {
            var13 = this.width - 146;
            posYHelperAttacker = this.height - 100;
            EntityClientPlayerMP var15 = Minecraft.getMinecraft().thePlayer;

            for (int var17 = 0; var17 < 4; ++var17)
            {
                ItemStack var18 = var15.getCurrentArmor(3 - var17);

                if (var18 != null)
                {
                    y = var13 + 75 + 17 * var17;
                    y1 = posYHelperAttacker + 5;

                    if (par1 >= y && par1 <= y + 16 && par2 >= y1 && par2 <= y1 + 16)
                    {
                        NationsGUIClientHooks.drawItemStackTooltip(var18, par1, par2);
                    }
                }
            }

            ItemStack var19 = Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem();

            if (var19 != null)
            {
                x = var13 + 100;
                y = posYHelperAttacker + 30;

                if (par1 >= x && par1 <= x + 16 && par2 >= y && par2 <= y + 16)
                {
                    NationsGUIClientHooks.drawItemStackTooltip(var19, par1, par2);
                }
            }
        }

        if (ClientData.currentAssault != null && !ClientData.currentAssault.isEmpty())
        {
            if (!((String)ClientData.currentAssault.get("attackerHelpersCount")).equals("0"))
            {
                var13 = this.width - 140 + 23 + this.fontRenderer.getStringWidth((String)ClientData.currentAssault.get("attackerFactionName")) - 1;
                posYHelperAttacker = (int)((double)this.height * 0.4D) + 26;

                if (par1 >= var13 && par1 <= var13 + 19 && par2 >= posYHelperAttacker && par2 <= posYHelperAttacker + 3)
                {
                    this.drawHoveringText(Arrays.asList(((String)ClientData.currentAssault.get("attackerHelpersName")).split(",")), par1, par2, this.fontRenderer);
                }
            }

            if (!((String)ClientData.currentAssault.get("defenderHelpersCount")).equals("0"))
            {
                var13 = this.width - 140 + 23 + this.fontRenderer.getStringWidth((String)ClientData.currentAssault.get("defenderFactionName")) - 1;
                posYHelperAttacker = (int)((double)this.height * 0.4D) + 26;

                if (par1 >= var13 && par1 <= var13 + 19 && par2 >= posYHelperAttacker && par2 <= posYHelperAttacker + 3)
                {
                    this.drawHoveringText(Arrays.asList(((String)ClientData.currentAssault.get("defenderHelpersName")).split(",")), par1, par2, this.fontRenderer);
                }
            }
        }

        super.drawScreen(par1, par2, par3);
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        super.actionPerformed(par1GuiButton);
        List objectives = ClientData.objectives;

        switch (par1GuiButton.id)
        {
            case 0:
                if (ClientData.currentObjectiveIndex - 1 >= 0)
                {
                    --ClientData.currentObjectiveIndex;
                }
                else
                {
                    ClientData.currentObjectiveIndex = ClientData.objectives.size() - 1;
                }

                break;

            case 1:
                if (ClientData.currentObjectiveIndex + 1 < objectives.size())
                {
                    ++ClientData.currentObjectiveIndex;
                }
                else
                {
                    ClientData.currentObjectiveIndex = 0;
                }

                break;

            case 2:
                this.displayText = true;
                this.generateTextLines();
                this.initGui();
                break;

            case 3:
                Objective current = (Objective)ClientData.objectives.get(ClientData.currentObjectiveIndex);

                if (current != null && current.getId().split("-").length == 3)
                {
                    PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ObjectivePacket()));
                }

                this.displayText = false;
                this.initGui();
                break;

            case 4:
                this.displayText = false;
                this.initGui();
        }
    }

    private void generateTextLines()
    {
        this.lines.clear();
        short lineWidth = 360;
        int spaceWidth = this.fontRenderer.getStringWidth(" ");
        Objective current = (Objective)ClientData.objectives.get(ClientData.currentObjectiveIndex);
        String[] l = current.getText().split("\n");
        String[] var5 = l;
        int var6 = l.length;

        for (int var7 = 0; var7 < var6; ++var7)
        {
            String line = var5[var7];
            int spaceLeft = lineWidth;
            String[] words = line.split(" ");
            StringBuilder stringBuilder = new StringBuilder();
            String[] var12 = words;
            int var13 = words.length;

            for (int var14 = 0; var14 < var13; ++var14)
            {
                String word = var12[var14];
                int wordWidth = this.fontRenderer.getStringWidth(word);

                if (wordWidth + spaceWidth > spaceLeft)
                {
                    this.lines.add(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                    spaceLeft = lineWidth - wordWidth;
                }
                else
                {
                    spaceLeft -= wordWidth + spaceWidth;
                }

                stringBuilder.append(word);
                stringBuilder.append(' ');
            }

            this.lines.add(stringBuilder.toString());
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);
    }

    public boolean isDisplayingText()
    {
        return this.displayText;
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

    static FontRenderer access$000(ChatGUI x0)
    {
        return x0.fontRenderer;
    }

    static FontRenderer access$100(ChatGUI x0)
    {
        return x0.fontRenderer;
    }
}
