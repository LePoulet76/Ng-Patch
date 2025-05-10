package net.ilexiconn.nationsgui.forge.client.gui.shop;

import com.google.gson.Gson;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.gui.CloseButtonGUI;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.json.CategoryJSON;
import net.ilexiconn.nationsgui.forge.server.packet.PacketCallbacks;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.CheckForbiddenShopCategoriesPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IncrementObjectivePacket;
import net.ilexiconn.nationsgui.forge.server.permission.PermissionCache;
import net.ilexiconn.nationsgui.forge.server.util.ReleaseType;
import net.ilexiconn.nationsgui.forge.server.util.Tuple;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureObject;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class ShopGUI extends GuiScreen
{
    public static final ResourceLocation TEXTURE = new ResourceLocation("nationsgui", "textures/gui/shop.png");
    public static final String API_URL = "https://apiv2.nationsglory.fr/mods/shop_api";
    public static final String API_DEV_URL = "https://apiv2.nationsglory.fr/json/shop_api.json";
    public static final Gson GSON = new Gson();
    public static boolean CAN_BUY = true;
    public static double CURRENT_MONEY = -1.0D;
    public static long lastBuy = 0L;
    public Category selectedCategory;
    public CategoryItem selectedItem;
    public Category hoverCategory;
    public Category[] categories;
    public static List<String> forbiddenCategories = new ArrayList();
    public Map<Category, Tuple<Float, Float>> categoryPositions = new HashMap();
    public RenderItem itemRenderer = new RenderItem();
    public float currentScroll;
    public float currentOffset;
    public boolean isScrolling;
    public boolean wasClicking;
    public int guiLeft;
    public int guiTop;
    public static CategoryJSON[] containers;
    public static boolean achievementDone = false;
    public List<String> tooltipToDraw = new ArrayList();

    public ShopGUI()
    {
        if (!achievementDone)
        {
            achievementDone = true;
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IncrementObjectivePacket("player_open_catalog", 1)));
        }

        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new CheckForbiddenShopCategoriesPacket()));
        CAN_BUY = true;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        PacketCallbacks.MONEY.send(new String[0]);
        PermissionCache.INSTANCE.clearCache();

        if (containers == null)
        {
            try
            {
                containers = (CategoryJSON[])GSON.fromJson(new BufferedReader(new InputStreamReader((new URL(NationsGUI.RELEASE_TYPE == ReleaseType.DEVELOP ? "https://apiv2.nationsglory.fr/json/shop_api.json" : "https://apiv2.nationsglory.fr/mods/shop_api?lang=" + System.getProperty("java.lang"))).openStream(), "UTF-8")), CategoryJSON[].class);
            }
            catch (Exception var2)
            {
                var2.printStackTrace();
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
            }
        }

        this.categories = new Category[containers.length + 1];

        for (int i = 0; i < containers.length; ++i)
        {
            this.categories[i] = new Category(this, containers[i]);
        }

        this.guiLeft = this.width / 2 - 147;
        this.guiTop = this.height / 2 - 119;
        this.buttonList.add(new CloseButtonGUI(0, this.guiLeft + 274, this.guiTop + 9));
        this.selectCategory(this.categories[0]);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.tooltipToDraw = new ArrayList();
        this.mc.getTextureManager().bindTexture(TEXTURE);
        int x = this.width / 2 - 147;
        int y = this.height / 2 - 119;
        this.drawTexturedModalRect(x + 113, y, 0, 196, 181, 29);
        int money;

        for (money = 0; money < 10; ++money)
        {
            this.drawTexturedModalRect(x + 113, y + 29 + money * 19, 0, 225, 181, 19);
        }

        this.drawTexturedModalRect(x + 113, y + 29 + 190, 0, 225, 181, 12);
        this.drawTexturedModalRect(x + 113, y + 231, 0, 249, 181, 7);
        this.drawTexturedModalRect(x, y + 26, 0, 0, 113, 7);
        this.drawTexturedModalRect(x, y + 215, 0, 34, 113, 7);

        for (money = 0; money < 7; ++money)
        {
            this.drawTexturedModalRect(x, y + 33 + 26 * money, 0, 7, 113, 26);
        }

        this.drawTexturedModalRect(x + 6, y + 3, 149, 173, 107, 23);

        if (this.mc.gameSettings.language.startsWith("fr_"))
        {
            this.drawTexturedModalRect(x + 148, y + 7, 0, 58, 113, 17);
        }
        else
        {
            this.drawTexturedModalRect(x + 148, y + 7, 0, 41, 113, 17);
        }

        if (!this.hasScrollbar())
        {
            GL11.glColor4f(0.75F, 0.75F, 0.75F, 1.0F);
        }

        this.drawTexturedModalRect(x + 101, y + 33 + (int)(167.0F * this.currentOffset), 204, 0, 9, 15);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        String var18 = (int)CURRENT_MONEY + "$";
        this.fontRenderer.drawString(var18, x + 107 - this.fontRenderer.getStringWidth(var18), y + 14, -1);
        float scrollOffset = -(this.currentOffset * ((float)this.categories.length / 182.0F) * 19.0F) * 182.0F;
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, scrollOffset, 0.0F);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        int categoryOffset = 0;
        GUIUtils.startGLScissor(0, y + 33, x + 98, 182);
        boolean flag = false;
        Category[] var10 = this.categories;
        int var11 = var10.length;

        for (int var12 = 0; var12 < var11; ++var12)
        {
            Category category = var10[var12];

            if (category != null && category.isEnabled())
            {
                this.mc.getTextureManager().bindTexture(TEXTURE);
                int categoryX = x + 7;
                int categoryY = y + 33 + categoryOffset * 19;
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.drawTexturedModalRect(categoryX + 75, categoryY, 188, this.selectedCategory == category ? 0 : 19, 16, 19);
                GL11.glPushMatrix();

                if (!this.categoryPositions.containsKey(category))
                {
                    this.categoryPositions.put(category, new Tuple(Float.valueOf(0.0F), Float.valueOf(0.0F)));
                }

                GL11.glTranslatef(((Float)((Tuple)this.categoryPositions.get(category)).a).floatValue(), 0.0F, 0.0F);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

                if (!this.isScrolling && mouseX > x + 7 && mouseX < x + 98 && mouseY > y + 33 && mouseY < y + 215)
                {
                    if (mouseX > categoryX && mouseX < categoryX + 91 && (float)mouseY - scrollOffset > (float)categoryY && (float)mouseY - scrollOffset < (float)(categoryY + 19))
                    {
                        this.hoverCategory = category;
                        flag = true;
                    }
                }
                else
                {
                    this.hoverCategory = null;
                }

                Tuple position = (Tuple)this.categoryPositions.get(category);

                if (this.hoverCategory == category)
                {
                    position.b = Float.valueOf(-12.0F);
                }
                else
                {
                    position.b = Float.valueOf(0.0F);
                }

                position.a = Float.valueOf(GUIUtils.interpolate(((Float)position.a).floatValue(), ((Float)position.b).floatValue(), 0.15F));
                this.categoryPositions.put(category, position);
                this.drawTexturedModalRect(categoryX, categoryY, 113, this.selectedCategory == category ? 0 : 19, 19, 19);
                this.drawStripTexturedModalRect(categoryX + 19, categoryY, 128, this.selectedCategory == category ? 0 : 19, 60 - (int)((Float)position.a).floatValue(), 19);
                this.fontRenderer.drawString(I18n.getString("shop.category." + category.getName().toLowerCase().replace(" ", "_")), categoryX + 23, categoryY + 5, this.selectedCategory != category && this.hoverCategory != category ? 7631988 : 4013373);

                if (category.isIconLoaded())
                {
                    this.mc.renderEngine.bindTexture(category.getIcon());
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    Tessellator tessellator = Tessellator.instance;
                    tessellator.startDrawingQuads();
                    tessellator.addVertexWithUV((double)(categoryX + 1), (double)(categoryY + 17), 0.0D, 0.0D, 1.0D);
                    tessellator.addVertexWithUV((double)(categoryX + 17), (double)(categoryY + 17), 0.0D, 1.0D, 1.0D);
                    tessellator.addVertexWithUV((double)(categoryX + 17), (double)(categoryY + 1), 0.0D, 1.0D, 0.0D);
                    tessellator.addVertexWithUV((double)(categoryX + 1), (double)(categoryY + 1), 0.0D, 0.0D, 0.0D);
                    tessellator.draw();
                }

                ++categoryOffset;
                GL11.glPopMatrix();
            }
        }

        if (!flag)
        {
            this.hoverCategory = null;
        }

        GUIUtils.endGLScissor();
        GL11.glPopMatrix();
        this.selectedCategory.renderCategory(this, mouseX, mouseY);

        if (this.hasScrollbar() && !this.wasClicking && Mouse.isButtonDown(0) && mouseX >= x + 101 && mouseY >= y + 33 && mouseX < x + 101 + 9 && mouseY < y + 33 + 182)
        {
            this.isScrolling = true;
        }
        else if (!Mouse.isButtonDown(0))
        {
            this.isScrolling = false;
        }

        this.wasClicking = Mouse.isButtonDown(0);

        if (this.isScrolling)
        {
            this.currentScroll = ((float)(mouseY - y) - 33.0F - 9.0F) / 156.0F;

            if (this.currentScroll < 0.0F)
            {
                this.currentScroll = 0.0F;
            }
            else if (this.currentScroll > 1.0F)
            {
                this.currentScroll = 1.0F;
            }
        }

        this.currentOffset = GUIUtils.interpolate(this.currentOffset, this.currentScroll, 0.15F);
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.selectedCategory.renderCategoryPost(this, mouseX, mouseY);

        if (!this.selectedCategory.getName().equalsIgnoreCase("accueil") && mouseX >= this.guiLeft + 208 && mouseX <= this.guiLeft + 208 + 21 && mouseY >= this.guiTop + 109 && mouseY <= this.guiTop + 109 + 10)
        {
            this.drawHoveringText(Arrays.asList(new String[] {I18n.getString("nationsgui.shop.stack_tooltip")}), mouseX, mouseY, this.fontRenderer);
        }

        if (this.tooltipToDraw != null && !this.tooltipToDraw.isEmpty())
        {
            this.drawHoveringText(this.tooltipToDraw, mouseX, mouseY, this.fontRenderer);
        }
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput()
    {
        super.handleMouseInput();
        int scroll = Mouse.getEventDWheel();

        if (this.hasScrollbar() && scroll != 0)
        {
            this.currentScroll += (float)scroll / ((float)this.categories.length * 19.0F - 182.0F) / 19.0F;

            if (this.currentScroll < 0.0F)
            {
                this.currentScroll = 0.0F;
            }
            else if (this.currentScroll > 1.0F)
            {
                this.currentScroll = 1.0F;
            }
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int button)
    {
        if (this.hoverCategory != null)
        {
            this.selectCategory(this.hoverCategory);
            this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
        }
        else
        {
            this.selectedCategory.mouseClicked(this, mouseX, mouseY, button);
            super.mouseClicked(mouseX, mouseY, button);
        }
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 0)
        {
            this.mc.displayGuiScreen((GuiScreen)null);
        }

        this.selectedCategory.actionPerformed(this, button);
    }

    public void selectCategory(Category category)
    {
        ArrayList remove = new ArrayList();
        Iterator var3 = this.buttonList.iterator();

        while (var3.hasNext())
        {
            GuiButton button = (GuiButton)var3.next();

            if (button.id != 0)
            {
                remove.add(button);
            }
        }

        this.buttonList.removeAll(remove);
        this.selectedCategory = category;
        category.initCategory(this, this.buttonList);
    }

    public ThreadDownloadImageData getDownloadImage(ResourceLocation location, String url)
    {
        if (url.equals("https://static.nationsglory.fr/N23665_G5_.png") && !Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode().equalsIgnoreCase("fr_fr"))
        {
            url = "https://static.nationsglory.fr/N362_6G_22.png";
        }
        else if (url.equals("https://static.nationsglory.fr/N23665_2Ny.png") && !Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode().equalsIgnoreCase("fr_fr"))
        {
            url = "https://static.nationsglory.fr/n236652nyen.png";
        }

        TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
        Object texture = textureManager.getTexture(location);

        if (texture == null)
        {
            texture = new ThreadDownloadImageData(url, (ResourceLocation)null, (IImageBuffer)null);
            textureManager.loadTexture(location, (TextureObject)texture);
        }

        return (ThreadDownloadImageData)texture;
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

    public void drawStripTexturedModalRect(int x, int y, int u, int v, int width, int height)
    {
        float size = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)x, (double)(y + height), (double)this.zLevel, (double)((float)u * size), (double)((float)(v + height) * size));
        tessellator.addVertexWithUV((double)(x + width), (double)(y + height), (double)this.zLevel, (double)((float)(u + 1) * size), (double)((float)(v + height) * size));
        tessellator.addVertexWithUV((double)(x + width), (double)y, (double)this.zLevel, (double)((float)(u + 1) * size), (double)((float)v * size));
        tessellator.addVertexWithUV((double)x, (double)y, (double)this.zLevel, (double)((float)u * size), (double)((float)v * size));
        tessellator.draw();
    }

    public boolean hasScrollbar()
    {
        return this.categories.length > 8;
    }

    public void setZLevel(float zLevel)
    {
        this.zLevel = zLevel;
    }
}
