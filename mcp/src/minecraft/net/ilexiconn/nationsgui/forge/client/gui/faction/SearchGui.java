package net.ilexiconn.nationsgui.forge.client.gui.faction;

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
import net.ilexiconn.nationsgui.forge.client.gui.faction.SearchGui$1;
import net.ilexiconn.nationsgui.forge.client.gui.faction.SearchGui$2;
import net.ilexiconn.nationsgui.forge.client.gui.faction.SearchGui$3;
import net.ilexiconn.nationsgui.forge.client.gui.faction.SearchGui$4;
import net.ilexiconn.nationsgui.forge.client.gui.faction.SearchGui$5;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionListDataPacket;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class SearchGui extends GuiScreen
{
    public static final List<GuiScreenTab> TABS = new ArrayList();
    protected int xSize = 319;
    protected int ySize = 250;
    private int guiLeft;
    private int guiTop;
    public static boolean loaded = false;
    private RenderItem itemRenderer = new RenderItem();
    private GuiScrollBarFaction scrollBar;
    public static ArrayList<HashMap<String, String>> countries = new ArrayList();
    private HashMap<String, String> hoveredCountry = new HashMap();
    private boolean open_filter = false;
    private String searchText = "";
    private GuiTextField countrySearch;

    public SearchGui()
    {
        loaded = false;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionListDataPacket()));
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.scrollBar = new GuiScrollBarFaction((float)(this.guiLeft + 296), (float)(this.guiTop + 64), 150);
        this.countrySearch = new GuiTextField(this.fontRenderer, this.guiLeft + 208, this.guiTop + 28, 93, 10);
        this.countrySearch.setEnableBackgroundDrawing(false);
        this.countrySearch.setMaxStringLength(40);
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
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        ClientEventHandler.STYLE.bindTexture("faction_search");
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
        this.countrySearch.drawTextBox();
        ClientEventHandler.STYLE.bindTexture("faction_search");
        int i;
        int offsetX;

        for (i = 0; i < TABS.size(); ++i)
        {
            GuiScreenTab i1 = (GuiScreenTab)TABS.get(i);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            offsetX = i % 5;
            int offsetY = i / 5;

            if (this.getClass() == i1.getClassReferent())
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23), (float)(this.guiTop + 20 + i * 31), 23, 251, 29, 30, 512.0F, 512.0F, false);
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23 + 4), (float)(this.guiTop + 20 + i * 31 + 5), offsetX * 20, 298 + offsetY * 20, 20, 20, 512.0F, 512.0F, false);
            }
            else
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23), (float)(this.guiTop + 20 + i * 31), 0, 251, 23, 30, 512.0F, 512.0F, false);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23 + 4), (float)(this.guiTop + 20 + i * 31 + 5), offsetX * 20, 298 + offsetY * 20, 20, 20, 512.0F, 512.0F, false);
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

        this.drawScaledString(I18n.getString("faction.search.title"), this.guiLeft + 50, this.guiTop + 20, 1644825, 1.0F, false, false);
        this.drawScaledString(I18n.getString("faction.search.country"), this.guiLeft + 51 + 5, this.guiTop + 50, 1644825, 0.9F, false, false);
        this.drawScaledString(I18n.getString("faction.search.age"), this.guiLeft + 51 + 100, this.guiTop + 50, 1644825, 0.9F, false, false);
        this.drawScaledString(I18n.getString("faction.search.level"), this.guiLeft + 51 + 155, this.guiTop + 50, 1644825, 0.9F, false, false);
        this.drawScaledString(I18n.getString("faction.search.online_players"), this.guiLeft + 51 + 210, this.guiTop + 50, 1644825, 0.9F, false, false);
        this.drawScaledString(I18n.getString("faction.search.open_filter"), this.guiLeft + 64, this.guiTop + 227, 1644825, 0.9F, false, false);
        ClientEventHandler.STYLE.bindTexture("faction_search");

        if (this.open_filter)
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 50), (float)(this.guiTop + 225), 159, 250, 10, 10, 512.0F, 512.0F, false);
        }
        else
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 50), (float)(this.guiTop + 225), 169, 250, 10, 10, 512.0F, 512.0F, false);
        }

        if (loaded && countries.size() > 0)
        {
            this.hoveredCountry = new HashMap();
            i = 0;
            GUIUtils.startGLScissor(this.guiLeft + 51, this.guiTop + 60, 245, 158);

            for (int var8 = 0; var8 < countries.size(); ++var8)
            {
                if ((!this.open_filter || this.open_filter && ((String)((HashMap)countries.get(var8)).get("open")).equals("true")) && (this.searchText.isEmpty() || !this.searchText.isEmpty() && ((String)((HashMap)countries.get(var8)).get("name")).toLowerCase().contains(this.searchText.toLowerCase())))
                {
                    offsetX = this.guiLeft + 51;
                    Float var9 = Float.valueOf((float)(this.guiTop + 60 + (var8 - i) * 20) + this.getSlide());
                    ClientEventHandler.STYLE.bindTexture("faction_search");
                    ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)var9.intValue(), 51, 60, 245, 20, 512.0F, 512.0F, false);
                    this.drawScaledString((String)((HashMap)countries.get(var8)).get("name"), offsetX + 5, var9.intValue() + 5, 16777215, 1.0F, false, false);
                    this.drawScaledString((String)((HashMap)countries.get(var8)).get("age") + I18n.getString("faction.common.days.short"), offsetX + 100, var9.intValue() + 5, 16777215, 1.0F, false, false);
                    this.drawScaledString((String)((HashMap)countries.get(var8)).get("level"), offsetX + 155, var9.intValue() + 5, 16777215, 1.0F, false, false);
                    this.drawScaledString((String)((HashMap)countries.get(var8)).get("online_players") + "/" + (String)((HashMap)countries.get(var8)).get("max_players"), offsetX + 210, var9.intValue() + 5, 16777215, 1.0F, false, false);

                    if (mouseX > this.guiLeft + 51 && mouseX < this.guiLeft + 51 + 245 && mouseY > this.guiTop + 60 && mouseY < this.guiTop + 60 + 158 && mouseX > offsetX && mouseX < offsetX + 245 && (float)mouseY > var9.floatValue() && (float)mouseY < var9.floatValue() + 20.0F)
                    {
                        this.hoveredCountry = (HashMap)countries.get(var8);
                    }
                }
                else
                {
                    ++i;
                }
            }

            GUIUtils.endGLScissor();
            this.scrollBar.draw(mouseX, mouseY);
        }

        for (i = 0; i < TABS.size(); ++i)
        {
            if (mouseX >= this.guiLeft - 23 && mouseX <= this.guiLeft - 23 + 29 && mouseY >= this.guiTop + 20 + i * 31 && mouseY <= this.guiTop + 20 + 30 + i * 31)
            {
                this.drawHoveringText(Arrays.asList(new String[] {I18n.getString("faction.tab.search." + i)}), mouseX, mouseY, this.fontRenderer);
            }
        }

        super.drawScreen(mouseX, mouseY, par3);
        GL11.glEnable(GL11.GL_LIGHTING);
        RenderHelper.enableStandardItemLighting();
    }

    private float getSlide()
    {
        int counter = 0;

        for (int i = 0; i < countries.size(); ++i)
        {
            if ((!this.open_filter || this.open_filter && ((String)((HashMap)countries.get(i)).get("open")).equals("true")) && (this.searchText.isEmpty() || !this.searchText.isEmpty() && ((String)((HashMap)countries.get(i)).get("name")).toLowerCase().contains(this.searchText.toLowerCase())))
            {
                ++counter;
            }
        }

        return counter > 8 ? (float)(-(counter - 8) * 20) * this.scrollBar.getSliderValue() : 0.0F;
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

            if (mouseX > this.guiLeft + 304 && mouseX < this.guiLeft + 304 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (this.hoveredCountry != null && !this.hoveredCountry.isEmpty())
            {
                this.mc.displayGuiScreen(new FactionGui_OLD((String)this.hoveredCountry.get("name")));
            }

            if (mouseX >= this.guiLeft + 50 && mouseX <= this.guiLeft + 50 + 10 && mouseY >= this.guiTop + 225 && mouseY <= this.guiTop + 225 + 10)
            {
                this.open_filter = !this.open_filter;
            }
        }

        this.countrySearch.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char typedChar, int keyCode)
    {
        if (this.countrySearch.textboxKeyTyped(typedChar, keyCode))
        {
            this.searchText = this.countrySearch.getText();
        }

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
        TABS.add(new SearchGui$1());
        TABS.add(new SearchGui$2());
        TABS.add(new SearchGui$3());
        TABS.add(new SearchGui$4());
        TABS.add(new SearchGui$5());
    }
}
