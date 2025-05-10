package net.ilexiconn.nationsgui.forge.client.gui.island;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandSaveFlagPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Encoder;

@SideOnly(Side.CLIENT)
public class IslandFlagGui extends GuiScreen
{
    protected int xSize = 319;
    protected int ySize = 179;
    private int guiLeft;
    private int guiTop;
    private static Map<String, DynamicTexture> images = Maps.newHashMap();
    private HashMap<Integer, Integer> pixels = new HashMap();
    private ArrayList<Integer> colorList = new ArrayList();
    private int pixelHoveredId = -1;
    private int pixelHoveredColorId = 0;
    private boolean mouseDrawing = true;
    private int colorHovered = 0;
    private int colorSelected = 0;
    private GuiButton saveButton;

    public IslandFlagGui()
    {
        int i;

        if (((ArrayList)IslandMainGui.islandInfos.get("imagePixels")).size() > 0)
        {
            for (i = 0; i < ((ArrayList)IslandMainGui.islandInfos.get("imagePixels")).size(); ++i)
            {
                this.pixels.put(Integer.valueOf(i), Integer.valueOf(((Double)((ArrayList)IslandMainGui.islandInfos.get("imagePixels")).get(i)).intValue()));
            }
        }
        else
        {
            for (i = 0; i < 289; ++i)
            {
                this.pixels.put(Integer.valueOf(i), Integer.valueOf(-1));
            }
        }

        this.colorList.addAll(Arrays.asList(new Integer[] {Integer.valueOf(-793344), Integer.valueOf(-145910), Integer.valueOf(-815329), Integer.valueOf(-1351392), Integer.valueOf(-1891550), Integer.valueOf(-3931010), Integer.valueOf(-9553525), Integer.valueOf(-12235111), Integer.valueOf(-13930319), Integer.valueOf(-16345413), Integer.valueOf(-16740772), Integer.valueOf(-7488731), Integer.valueOf(-1), Integer.valueOf(-16448251), Integer.valueOf(-10471149), Integer.valueOf(-5789785), Integer.valueOf(-6274803), Integer.valueOf(-3761043)}));
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.saveButton = new GuiButton(0, this.guiLeft + 244, this.guiTop + 142, 54, 20, I18n.getString("faction.flag.valid"));
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        this.drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        ClientEventHandler.STYLE.bindTexture("island_flag");
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
        super.drawScreen(mouseX, mouseY, par3);
        ClientEventHandler.STYLE.bindTexture("island_flag");

        if (mouseX >= this.guiLeft + 304 && mouseX <= this.guiLeft + 304 + 9 && mouseY >= this.guiTop - 6 && mouseY <= this.guiTop - 6 + 10)
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 304), (float)(this.guiTop - 6), 0, 192, 9, 10, 512.0F, 512.0F, false);
        }
        else
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 304), (float)(this.guiTop - 6), 0, 182, 9, 10, 512.0F, 512.0F, false);
        }

        GL11.glPushMatrix();
        Double titleOffsetY = Double.valueOf((double)(this.guiTop + 45) + (double)this.fontRenderer.getStringWidth((String)IslandMainGui.islandInfos.get("name")) * 1.5D);
        GL11.glTranslatef((float)(this.guiLeft + 14), (float)titleOffsetY.intValue(), 0.0F);
        GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef((float)(-(this.guiLeft + 14)), (float)(-titleOffsetY.intValue()), 0.0F);
        this.drawScaledString((String)IslandMainGui.islandInfos.get("name"), this.guiLeft + 14, titleOffsetY.intValue(), 16777215, 1.5F, false, false);
        GL11.glPopMatrix();
        int x = 0;
        int y = 0;
        int i;

        for (i = 0; i < this.colorList.size(); ++i)
        {
            int it = ((Integer)this.colorList.get(i)).intValue();

            if (mouseX > this.guiLeft + 244 + x * 18 && mouseX < this.guiLeft + 244 + x * 18 + 18 && mouseY > this.guiTop + 28 + y * 18 && mouseY < this.guiTop + 28 + y * 18 + 18)
            {
                this.colorHovered = it;
            }

            if (it == this.colorSelected)
            {
                ClientEventHandler.STYLE.bindTexture("island_flag");
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 244 + x * 18 - 3), (float)(this.guiTop + 28 + y * 18 - 3), 22, 182, 24, 24, 512.0F, 512.0F, false);
            }

            x = x < 2 ? x + 1 : 0;
            y = x == 0 ? y + 1 : y;
        }

        x = 0;
        y = 0;
        i = 0;

        if (this.pixels.size() > 0)
        {
            for (Iterator var11 = this.pixels.entrySet().iterator(); var11.hasNext(); ++i)
            {
                Entry pair = (Entry)var11.next();
                int colorId = ((Integer)pair.getValue()).intValue();

                if (mouseX >= this.guiLeft + 84 + x * 6 && mouseX <= this.guiLeft + 84 + x * 6 + 6 && mouseY >= this.guiTop + 32 + y * 6 && mouseY <= this.guiTop + 32 + y * 6 + 6)
                {
                    this.pixelHoveredId = i;
                }

                if (this.mouseDrawing && colorId != this.colorSelected && this.pixelHoveredId == i)
                {
                    this.pixels.put(Integer.valueOf(i), Integer.valueOf(this.colorSelected));
                    colorId = this.colorSelected;
                }

                Gui.drawRect(this.guiLeft + 84 + x * 6, this.guiTop + 32 + y * 6, this.guiLeft + 84 + x * 6 + 6, this.guiTop + 32 + y * 6 + 6, colorId);
                x = x < 16 ? x + 1 : 0;
                y = x == 0 ? y + 1 : y;
            }
        }

        this.drawScaledString(I18n.getString("faction.flag.reset"), this.guiLeft + 73, this.guiTop + 144, 1644825, 1.0F, false, false);
        this.saveButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
        GL11.glEnable(GL11.GL_LIGHTING);
        RenderHelper.enableStandardItemLighting();
    }

    /**
     * Called when the mouse is moved or a mouse button is released.  Signature: (mouseX, mouseY, which) which==-1 is
     * mouseMove, which==0 or which==1 is mouseUp
     */
    protected void mouseMovedOrUp(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            this.mouseDrawing = false;
        }

        super.mouseMovedOrUp(mouseX, mouseY, mouseButton);
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            if (mouseX > this.guiLeft + 304 && mouseX < this.guiLeft + 304 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen(new IslandSettingsGui());
            }

            if (this.colorHovered != 0 && mouseX > this.guiLeft + 244 && mouseX < this.guiLeft + 244 + 54 && mouseY > this.guiTop + 28 && mouseY < this.guiTop + 28 + 108)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                this.colorSelected = this.colorHovered;
                this.colorHovered = 0;
            }

            if (mouseX > this.guiLeft + 84 && mouseX < this.guiLeft + 84 + 102 && mouseY > this.guiTop + 32 && mouseY < this.guiTop + 32 + 102)
            {
                this.mouseDrawing = true;
            }

            if (mouseX > this.guiLeft + 54 && mouseX < this.guiLeft + 54 + 15 && mouseY > this.guiTop + 140 && mouseY < this.guiTop + 140 + 15)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);

                for (int imagePixels = 0; imagePixels < 289; ++imagePixels)
                {
                    this.pixels.put(Integer.valueOf(imagePixels), Integer.valueOf(-1));
                }
            }

            if (mouseX > this.guiLeft + 244 && mouseX < this.guiLeft + 244 + 54 && mouseY > this.guiTop + 142 && mouseY < this.guiTop + 142 + 18)
            {
                ArrayList var12 = new ArrayList();
                BufferedImage image = new BufferedImage(102, 102, 2);
                Graphics2D graphics2D = image.createGraphics();
                int x = 0;
                int y = 0;

                for (Iterator it = this.pixels.entrySet().iterator(); it.hasNext(); y = x == 0 ? y + 1 : y)
                {
                    Entry base64Img = (Entry)it.next();
                    var12.add(Integer.valueOf(((Integer)base64Img.getValue()).intValue()));
                    graphics2D.setPaint(new Color(((Integer)base64Img.getValue()).intValue()));
                    graphics2D.fillRect(x * 6, y * 6, 6, 6);
                    x = x < 16 ? x + 1 : 0;
                }

                graphics2D.dispose();
                String var13 = encodeToString(image, "png").replace("\r\n", "");
                this.mc.sndManager.playSoundFX("random.successful_hit", 1.0F, 1.0F);
                HashMap dataToPacket = new HashMap();
                dataToPacket.put("image", var13);
                dataToPacket.put("imagePixels", var12);
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandSaveFlagPacket((String)IslandMainGui.islandInfos.get("id"), dataToPacket)));
                Minecraft.getMinecraft().displayGuiScreen(new IslandSettingsGui());
            }
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

    public static String encodeToString(BufferedImage image, String type)
    {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try
        {
            ImageIO.write(image, type, bos);
            byte[] e = bos.toByteArray();
            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(e);
            bos.close();
        }
        catch (IOException var6)
        {
            var6.printStackTrace();
        }

        return imageString;
    }

    public static void bindTexture(String name)
    {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((DynamicTexture)images.get(name)).getGlTextureId());
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}
