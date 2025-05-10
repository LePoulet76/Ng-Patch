package net.ilexiconn.nationsgui.forge.client.gui.faction;

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
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionSaveFlagDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Encoder;

@SideOnly(Side.CLIENT)
public class FlagGui extends GuiScreen
{
    protected int xSize = 319;
    protected int ySize = 179;
    private int guiLeft;
    private int guiTop;
    private static Map<String, DynamicTexture> images = Maps.newHashMap();
    private String targetName;
    private HashMap<Integer, Integer> pixels = new HashMap();
    private ArrayList<Integer> colorList = new ArrayList();
    private int pixelHoveredId = -1;
    private int pixelHoveredColorId = 0;
    private boolean mouseDrawing = true;
    private int colorHovered = 0;
    private int colorSelected = 0;
    private GuiButton saveButton;
    private EntityPlayer player = null;

    public FlagGui(EntityPlayer player, String targetName)
    {
        this.targetName = targetName;
        this.player = player;
        int i;

        if (FactionGui_OLD.factionInfos.get("flagPixels") != null && ((ArrayList)FactionGui_OLD.factionInfos.get("flagPixels")).size() > 0)
        {
            for (i = 0; i < ((ArrayList)FactionGui_OLD.factionInfos.get("flagPixels")).size(); ++i)
            {
                this.pixels.put(Integer.valueOf(i), Integer.valueOf(((Double)((ArrayList)FactionGui_OLD.factionInfos.get("flagPixels")).get(i)).intValue()));
            }
        }
        else
        {
            for (i = 0; i < 338; ++i)
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
        ClientEventHandler.STYLE.bindTexture("faction_flag");
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
        super.drawScreen(mouseX, mouseY, par3);
        ClientEventHandler.STYLE.bindTexture("faction_flag");

        if (mouseX >= this.guiLeft + 304 && mouseX <= this.guiLeft + 304 + 9 && mouseY >= this.guiTop - 6 && mouseY <= this.guiTop - 6 + 10)
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 304), (float)(this.guiTop - 6), 0, 192, 9, 10, 512.0F, 512.0F, false);
        }
        else
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 304), (float)(this.guiTop - 6), 0, 182, 9, 10, 512.0F, 512.0F, false);
        }

        this.drawScaledString(I18n.getString("faction.flag.title") + " " + this.targetName, this.guiLeft + 51, this.guiTop + 17, 1644825, 1.2F, false, false);
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
                ClientEventHandler.STYLE.bindTexture("faction_flag");
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
            for (Iterator var10 = this.pixels.entrySet().iterator(); var10.hasNext(); ++i)
            {
                Entry pair = (Entry)var10.next();
                int colorId = ((Integer)pair.getValue()).intValue();

                if (FactionGui_OLD.hasPermissions("flag") && mouseX >= this.guiLeft + 55 + x * 6 && mouseX <= this.guiLeft + 55 + x * 6 + 6 && mouseY >= this.guiTop + 48 + y * 6 && mouseY <= this.guiTop + 48 + y * 6 + 6)
                {
                    this.pixelHoveredId = i;
                }

                if (FactionGui_OLD.hasPermissions("flag") && this.mouseDrawing && colorId != this.colorSelected && this.pixelHoveredId == i)
                {
                    this.pixels.put(Integer.valueOf(i), Integer.valueOf(this.colorSelected));
                    colorId = this.colorSelected;
                }

                Gui.drawRect(this.guiLeft + 55 + x * 6, this.guiTop + 48 + y * 6, this.guiLeft + 55 + x * 6 + 6, this.guiTop + 48 + y * 6 + 6, colorId);
                x = x < 25 ? x + 1 : 0;
                y = x == 0 ? y + 1 : y;
            }
        }

        this.drawScaledString(I18n.getString("faction.flag.reset"), this.guiLeft + 73, this.guiTop + 144, 1644825, 1.0F, false, false);

        if (FactionGui_OLD.hasPermissions("flag"))
        {
            this.saveButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
        }

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
                Minecraft.getMinecraft().displayGuiScreen(new SettingsGUI_OLD(this.player));
            }

            if (FactionGui_OLD.hasPermissions("flag") && this.colorHovered != 0 && mouseX > this.guiLeft + 244 && mouseX < this.guiLeft + 244 + 54 && mouseY > this.guiTop + 28 && mouseY < this.guiTop + 28 + 108)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                this.colorSelected = this.colorHovered;
                this.colorHovered = 0;
            }

            if (FactionGui_OLD.hasPermissions("flag") && mouseX > this.guiLeft + 55 && mouseX < this.guiLeft + 55 + 156 && mouseY > this.guiTop + 48 && mouseY < this.guiTop + 48 + 78)
            {
                this.mouseDrawing = true;
            }

            if (FactionGui_OLD.hasPermissions("flag") && mouseX > this.guiLeft + 54 && mouseX < this.guiLeft + 54 + 15 && mouseY > this.guiTop + 140 && mouseY < this.guiTop + 140 + 15)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);

                for (int imagePixels = 0; imagePixels < 338; ++imagePixels)
                {
                    this.pixels.put(Integer.valueOf(imagePixels), Integer.valueOf(-1));
                }
            }

            if (FactionGui_OLD.hasPermissions("flag") && mouseX > this.guiLeft + 244 && mouseX < this.guiLeft + 244 + 54 && mouseY > this.guiTop + 142 && mouseY < this.guiTop + 142 + 18)
            {
                ArrayList var12 = new ArrayList();
                BufferedImage image = new BufferedImage(156, 78, 2);
                Graphics2D graphics2D = image.createGraphics();
                int x = 0;
                int y = 0;

                for (Iterator it = this.pixels.entrySet().iterator(); it.hasNext(); y = x == 0 ? y + 1 : y)
                {
                    Entry base64Img = (Entry)it.next();
                    var12.add(Integer.valueOf(((Integer)base64Img.getValue()).intValue()));
                    graphics2D.setPaint(new Color(((Integer)base64Img.getValue()).intValue()));
                    graphics2D.fillRect(x * 6, y * 6, 6, 6);
                    x = x < 25 ? x + 1 : 0;
                }

                graphics2D.dispose();
                String var13 = encodeToString(image, "png").replace("\r\n", "");
                HashMap dataToPacket = new HashMap();
                dataToPacket.put("imageCode", var13);
                dataToPacket.put("imagePixels", var12);
                dataToPacket.put("factionName", this.targetName);
                dataToPacket.put("mainFlagColor", mostCommon(var12));
                this.mc.sndManager.playSoundFX("random.successful_hit", 1.0F, 1.0F);
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionSaveFlagDataPacket(dataToPacket)));
                Minecraft.getMinecraft().displayGuiScreen(new SettingsGUI_OLD(this.player));
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

    public static <T extends Object> T mostCommon(List<T> list)
    {
        HashMap map = new HashMap();
        Iterator max = list.iterator();

        while (max.hasNext())
        {
            Object t = max.next();
            Integer e = (Integer)map.get(t);
            map.put(t, Integer.valueOf(e == null ? 1 : e.intValue() + 1));
        }

        Entry max1 = null;
        Iterator t1 = map.entrySet().iterator();

        while (t1.hasNext())
        {
            Entry e1 = (Entry)t1.next();

            if (max1 == null || ((Integer)e1.getValue()).intValue() > ((Integer)max1.getValue()).intValue())
            {
                max1 = e1;
            }
        }

        return max1.getKey();
    }
}
