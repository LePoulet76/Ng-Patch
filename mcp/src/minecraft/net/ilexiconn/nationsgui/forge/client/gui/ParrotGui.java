package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.ParrotDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PlayerExecCmdPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class ParrotGui extends GuiScreen
{
    public static int GUI_SCALE = 3;
    public static CFontRenderer georamaBold27 = ModernGui.getCustomFont("georamaBold", Integer.valueOf(27));
    public static HashMap<String, Object> data = new HashMap();
    public static boolean hasNGPrime = false;
    public static boolean loaded = false;
    public static List<String> TABS = Arrays.asList(new String[] {"world", "warps", "homes", "warps_spawn"});
    public String hoveredAction = "";
    protected int xSize = 505;
    protected int ySize = 235;
    private RenderItem itemRenderer = new RenderItem();
    private GuiScrollBarGeneric scrollBar;
    private String selectedTab;
    private int guiLeft;
    private int guiTop;

    public ParrotGui(String tabToOpen)
    {
        loaded = false;
        this.selectedTab = tabToOpen;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ParrotDataPacket()));
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.scrollBar = new GuiScrollBarGeneric((float)(this.guiLeft + 344), (float)(this.guiTop + 48), 175, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_parrot.png"), 5, 28);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        this.drawDefaultBackground();
        this.hoveredAction = "";
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        ClientEventHandler.STYLE.bindTexture("parrot_main");
        ModernGui.drawScaledCustomSizeModalRect((float)this.guiLeft, (float)this.guiTop, (float)(9 * GUI_SCALE), (float)(0 * GUI_SCALE), 499 * GUI_SCALE, 235 * GUI_SCALE, 499, 235, (float)(1204 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);

        if (mouseX >= this.guiLeft + 505 && mouseX <= this.guiLeft + 515 && mouseY >= this.guiTop && mouseY <= this.guiTop + 10)
        {
            this.hoveredAction = "close";
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 505), (float)this.guiTop, (float)(560 * GUI_SCALE), (float)(43 * GUI_SCALE), 10 * GUI_SCALE, 10 * GUI_SCALE, 10, 10, (float)(1204 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
        }
        else
        {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 505), (float)this.guiTop, (float)(517 * GUI_SCALE), (float)(0 * GUI_SCALE), 10 * GUI_SCALE, 10 * GUI_SCALE, 10, 10, (float)(1204 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
        }

        Iterator elements = TABS.iterator();

        while (elements.hasNext())
        {
            String index = (String)elements.next();

            if (this.selectedTab.equals(index))
            {
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft - 6), (float)(this.guiTop + TABS.indexOf(this.selectedTab) * 40), (float)((this.selectedTab.equals("warps_spawn") ? 603 : 560) * GUI_SCALE), (float)(0 * GUI_SCALE), 41 * GUI_SCALE, 35 * GUI_SCALE, 41, 35, (float)(1204 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft - 4), (float)(this.guiTop + TABS.indexOf(this.selectedTab) * 40), (float)(9 * GUI_SCALE), (float)((252 + TABS.indexOf(this.selectedTab) * 35) * GUI_SCALE), 35 * GUI_SCALE, 35 * GUI_SCALE, 35, 35, (float)(1204 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            }
            else if (mouseX > this.guiLeft && mouseX < this.guiLeft + 35 && mouseY > this.guiTop + TABS.indexOf(index) * 40 && mouseY < this.guiTop + TABS.indexOf(index) * 40 + 35)
            {
                ModernGui.drawScaledCustomSizeModalRect((float)this.guiLeft, (float)(this.guiTop + TABS.indexOf(index) * 40), (float)(9 * GUI_SCALE), (float)((427 + TABS.indexOf(index) * 35) * GUI_SCALE), 35 * GUI_SCALE, 35 * GUI_SCALE, 35, 35, (float)(1204 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                this.hoveredAction = "tab#" + index;
            }
            else
            {
                ModernGui.drawScaledCustomSizeModalRect((float)this.guiLeft, (float)(this.guiTop + TABS.indexOf(index) * 40), (float)(9 * GUI_SCALE), (float)((602 + TABS.indexOf(index) * 35) * GUI_SCALE), 35 * GUI_SCALE, 35 * GUI_SCALE, 35, 35, (float)(1204 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            }
        }

        ClientEventHandler.STYLE.bindTexture("parrot_main");
        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + (this.selectedTab.equals("warps_spawn") ? 15 : 50)), (float)(this.guiTop + 10), (float)(237 * GUI_SCALE), (float)((this.selectedTab.equals("warps_spawn") ? 706 : 665) * GUI_SCALE), 176 * GUI_SCALE, 30 * GUI_SCALE, 176, 30, (float)(1204 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
        ModernGui.drawScaledStringCustomFont(I18n.getString("gui.parrot." + this.selectedTab), (float)(this.guiLeft + (this.selectedTab.equals("warps_spawn") ? 50 : 83)), (float)(this.guiTop + 32), this.selectedTab.equals("warps_spawn") ? 16171012 : 7258350, 0.5F, "left", true, "georamaSemiBold", 25);

        if (this.selectedTab.equals("world"))
        {
            ClientEventHandler.STYLE.bindTexture("parrot_main");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 49), (float)(this.guiTop + 47), (float)(72 * GUI_SCALE), (float)(243 * GUI_SCALE), 436 * GUI_SCALE, 173 * GUI_SCALE, 436, 173, (float)(1204 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 350), (float)(this.guiTop + 15), (float)(74 * GUI_SCALE), (float)(647 * GUI_SCALE), 145 * GUI_SCALE, 134 * GUI_SCALE, 145, 134, (float)(1204 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 57), (float)(this.guiTop + 60), (float)(622 * GUI_SCALE), (float)(766 * GUI_SCALE), 285 * GUI_SCALE, 152 * GUI_SCALE, 285, 152, (float)(1204 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);

            if (mouseX < this.guiLeft + 148 || mouseX > this.guiLeft + 172 || mouseY < this.guiTop + 110 || mouseY > this.guiTop + 135)
            {
                if (mouseX >= this.guiLeft + 57 && mouseX <= this.guiLeft + 178 && mouseY >= this.guiTop + 60 && mouseY <= this.guiTop + 145)
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 57), (float)(this.guiTop + 60), (float)(919 * GUI_SCALE), (float)(766 * GUI_SCALE), 285 * GUI_SCALE, 152 * GUI_SCALE, 285, 152, (float)(1204 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                    this.hoveredAction = "world#america_north";
                }
                else if (mouseX >= this.guiLeft + 57 && mouseX <= this.guiLeft + 178 && mouseY >= this.guiTop + 145 && mouseY <= this.guiTop + 200)
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 57), (float)(this.guiTop + 60), (float)(919 * GUI_SCALE), (float)(307 * GUI_SCALE), 285 * GUI_SCALE, 152 * GUI_SCALE, 285, 152, (float)(1204 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                    this.hoveredAction = "world#america_south";
                }
                else if (mouseX >= this.guiLeft + 178 && mouseX <= this.guiLeft + 239 && mouseY >= this.guiTop + 60 && mouseY <= this.guiTop + 129)
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 57), (float)(this.guiTop + 60), (float)(919 * GUI_SCALE), (float)(613 * GUI_SCALE), 285 * GUI_SCALE, 152 * GUI_SCALE, 285, 152, (float)(1204 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                    this.hoveredAction = "world#europe";
                }
                else if (mouseX >= this.guiLeft + 178 && mouseX <= this.guiLeft + 239 && mouseY >= this.guiTop + 129 && mouseY <= this.guiTop + 200)
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 57), (float)(this.guiTop + 60), (float)(919 * GUI_SCALE), (float)(154 * GUI_SCALE), 285 * GUI_SCALE, 152 * GUI_SCALE, 285, 152, (float)(1204 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                    this.hoveredAction = "world#africa";
                }
                else if (mouseX >= this.guiLeft + 239 && mouseX <= this.guiLeft + 342 && mouseY >= this.guiTop + 60 && mouseY <= this.guiTop + 152)
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 57), (float)(this.guiTop + 60), (float)(919 * GUI_SCALE), (float)(460 * GUI_SCALE), 285 * GUI_SCALE, 152 * GUI_SCALE, 285, 152, (float)(1204 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                    this.hoveredAction = "world#asia";
                }
                else if (mouseX >= this.guiLeft + 264 && mouseX <= this.guiLeft + 342 && mouseY >= this.guiTop + 152 && mouseY <= this.guiTop + 200)
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 57), (float)(this.guiTop + 60), (float)(919 * GUI_SCALE), (float)(1 * GUI_SCALE), 285 * GUI_SCALE, 152 * GUI_SCALE, 285, 152, (float)(1204 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                    this.hoveredAction = "world#oceania";
                }
                else if (mouseX >= this.guiLeft + 120 && mouseX <= this.guiLeft + 208 && mouseY >= this.guiTop + 200 && mouseY <= this.guiTop + 215)
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 57), (float)(this.guiTop + 60), (float)(622 * GUI_SCALE), (float)(460 * GUI_SCALE), 285 * GUI_SCALE, 152 * GUI_SCALE, 285, 152, (float)(1204 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                    this.hoveredAction = "world#antarctica_west";
                }
                else if (mouseX >= this.guiLeft + 208 && mouseX <= this.guiLeft + 295 && mouseY >= this.guiTop + 200 && mouseY <= this.guiTop + 215)
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 57), (float)(this.guiTop + 60), (float)(622 * GUI_SCALE), (float)(613 * GUI_SCALE), 285 * GUI_SCALE, 152 * GUI_SCALE, 285, 152, (float)(1204 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                    this.hoveredAction = "world#antarctica_east";
                }
            }

            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 57), (float)(this.guiTop + 60), (float)(325 * GUI_SCALE), (float)(766 * GUI_SCALE), 285 * GUI_SCALE, 152 * GUI_SCALE, 285, 152, (float)(1204 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
        }
        else if ((this.selectedTab.equals("warps") || this.selectedTab.equals("homes") || this.selectedTab.equals("warps_spawn")) && loaded)
        {
            ClientEventHandler.STYLE.bindTexture("parrot_main");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 362), (float)(this.guiTop + 47), (float)(385 * GUI_SCALE), (float)(243 * GUI_SCALE), 123 * GUI_SCALE, 173 * GUI_SCALE, 123, 173, (float)(1204 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 350), (float)(this.guiTop + 15), (float)(74 * GUI_SCALE), (float)((this.selectedTab.equals("warps_spawn") ? 781 : 647) * GUI_SCALE), 145 * GUI_SCALE, 134 * GUI_SCALE, 145, 134, (float)(1204 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 344), (float)(this.guiTop + 48), (float)(560 * GUI_SCALE), (float)(316 * GUI_SCALE), 5 * GUI_SCALE, 175 * GUI_SCALE, 5, 175, (float)(1204 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            ArrayList var11 = new ArrayList();

            if (this.selectedTab.equals("warps"))
            {
                var11 = (ArrayList)data.get("warps");
            }
            else if (this.selectedTab.equals("warps_spawn"))
            {
                var11 = (ArrayList)data.get("warps_spawn");
            }
            else if (this.selectedTab.equals("homes"))
            {
                var11 = (ArrayList)data.get("homes");
            }

            GUIUtils.startGLScissor(this.guiLeft + 49, this.guiTop + 47, 285, 176);
            int var12 = 0;

            for (Iterator var6 = var11.iterator(); var6.hasNext(); ++var12)
            {
                String element = (String)var6.next();
                int offsetX = this.guiLeft + 49 + var12 % 3 * 96;
                Float offsetY = Float.valueOf((float)(this.guiTop + 47 + var12 / 3 * 37) + this.getSlide());
                boolean isHovered = mouseX >= offsetX && mouseX <= offsetX + 92 && (float)mouseY >= offsetY.floatValue() && (float)mouseY <= offsetY.floatValue() + 28.0F;
                ClientEventHandler.STYLE.bindTexture("parrot_main");
                ModernGui.drawScaledCustomSizeModalRect((float)offsetX, offsetY.floatValue(), (float)((this.selectedTab.equals("warps_spawn") && isHovered ? 662 : 560) * GUI_SCALE), (float)((this.selectedTab.equals("warps_spawn") && isHovered ? 205 : (isHovered ? 243 : 280)) * GUI_SCALE), 92 * GUI_SCALE, 28 * GUI_SCALE, 92, 28, (float)(1204 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                ModernGui.drawScaledStringCustomFont(element.split("#")[0].replaceAll("capitale_", "").toUpperCase(), (float)(offsetX + 46), offsetY.floatValue() + (float)(this.selectedTab.contains("warps") ? 6 : 9), isHovered ? 1908021 : 12237530, 0.55F, "center", false, "minecraftDungeons", 23);

                if (this.selectedTab.contains("warps"))
                {
                    ModernGui.drawScaledStringCustomFont(element.split("#")[1].equals("0") ? "\u00a7o" + I18n.getString("gui.parrot.warps.free") : "\u00a7o" + element.split("#")[1] + "$", (float)(offsetX + 46), offsetY.floatValue() + 17.0F, isHovered ? 1908021 : 12237530, 0.5F, "center", false, "georamaSemiBold", 23);
                }

                if (isHovered && this.selectedTab.contains("warps"))
                {
                    this.hoveredAction = "warp#" + element;
                }
                else if (isHovered && this.selectedTab.equals("homes"))
                {
                    this.hoveredAction = "home#" + element.split("#")[0];
                }
            }

            GUIUtils.endGLScissor();
            this.scrollBar.draw(mouseX, mouseY);
        }

        if (!this.selectedTab.equals("map"))
        {
            ModernGui.drawScaledStringCustomFont(I18n.getString("gui.parrot.tickets.title").toUpperCase(), (float)(this.guiLeft + 374), (float)(this.guiTop + 160), 12237530, 0.5F, "left", true, "minecraftDungeons", 23);
            ClientEventHandler.STYLE.bindTexture("parrot_main");

            if (loaded)
            {
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 374), (float)(this.guiTop + 172), (float)(560 * GUI_SCALE), (float)((hasNGPrime ? 99 : 145) * GUI_SCALE), 98 * GUI_SCALE, 18 * GUI_SCALE, 98, 18, (float)(1204 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                ModernGui.drawScaledStringCustomFont(I18n.getString("gui.parrot.tickets.1er"), (float)(this.guiLeft + 400), (float)(this.guiTop + 178), hasNGPrime ? 1908021 : 11431662, 0.5F, "left", false, "georamaBold", 27);
                ModernGui.drawScaledStringCustomFont("\u00a7o" + I18n.getString("gui.parrot.tickets.free"), (float)(this.guiLeft + 400) + georamaBold27.getStringWidth(I18n.getString("gui.parrot.tickets.1er")) / 2.0F + 5.0F, (float)this.guiTop + 177.5F, hasNGPrime ? 1908021 : 11431662, 0.5F, "left", false, "georamaMedium", 25);
                ClientEventHandler.STYLE.bindTexture("parrot_main");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 374), (float)(this.guiTop + 194), (float)(560 * GUI_SCALE), (float)((hasNGPrime ? 122 : (this.selectedTab.equals("warps_spawn") ? 168 : 76)) * GUI_SCALE), 98 * GUI_SCALE, 18 * GUI_SCALE, 98, 18, (float)(1204 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                ModernGui.drawScaledStringCustomFont(I18n.getString("gui.parrot.tickets.2nd"), (float)(this.guiLeft + 400), (float)(this.guiTop + 200), hasNGPrime ? 4868744 : 1908021, 0.5F, "left", false, "georamaBold", 27);

                if (!hasNGPrime)
                {
                    ModernGui.drawScaledStringCustomFont(this.selectedTab.equals("world") ? String.format("%.0f", new Object[] {(Double)data.get("price")}) + "$": I18n.getString("gui.parrot.tickets.variable"), (float)(this.guiLeft + 400) + georamaBold27.getStringWidth(I18n.getString("gui.parrot.tickets.2nd")) / 2.0F + 5.0F, (float)this.guiTop + 199.5F, 1908021, 0.5F, "left", false, "georamaSemiBold", 25);
                }
            }
        }

        super.drawScreen(mouseX, mouseY, par3);
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    private float getSlide()
    {
        ArrayList elements = new ArrayList();

        if (this.selectedTab.equals("warps"))
        {
            elements = (ArrayList)data.get("warps");
        }
        else if (this.selectedTab.equals("warps_spawn"))
        {
            elements = (ArrayList)data.get("warps_spawn");
        }
        else if (this.selectedTab.equals("homes"))
        {
            elements = (ArrayList)data.get("homes");
        }

        return elements.size() > 15 ? (float)(-(elements.size() - 15) * 14) * this.scrollBar.getSliderValue() : 0.0F;
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            if (this.hoveredAction.equals("close"))
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
            }
            else if (this.hoveredAction.contains("tab#"))
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                this.selectedTab = this.hoveredAction.split("#")[1];
            }
            else if (this.hoveredAction.contains("world#"))
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new PlayerExecCmdPacket("warp " + this.hoveredAction.split("#")[1], hasNGPrime ? 0 : ((Double)data.get("price")).intValue())));
            }
            else if (this.hoveredAction.contains("warp#"))
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new PlayerExecCmdPacket("warp " + this.hoveredAction.split("#")[1], hasNGPrime ? 0 : Integer.parseInt(this.hoveredAction.split("#")[2]))));
            }
            else if (this.hoveredAction.contains("home#"))
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new PlayerExecCmdPacket("home " + this.hoveredAction.split("#")[1], 0)));
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
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
