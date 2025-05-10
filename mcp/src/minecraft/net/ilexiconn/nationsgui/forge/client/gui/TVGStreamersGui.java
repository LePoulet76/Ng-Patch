package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TVGStreamersDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TVGStreamersSetPacket;
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
public class TVGStreamersGui extends GuiScreen
{
    protected int xSize = 463;
    protected int ySize = 232;
    private int guiLeft;
    private int guiTop;
    public static boolean loaded = true;
    private RenderItem itemRenderer = new RenderItem();
    public static Integer playerPosition = Integer.valueOf(0);
    public static Long playerTime = Long.valueOf(0L);
    public static Integer totalCagnotte = Integer.valueOf(0);
    public static String hoveredStreamer = "";
    public static String selectedStreamer = "";
    public static String playerStreamer = "";
    public static HashMap<String, String> streamerData = new HashMap();
    public static boolean hoverPageSwitch = false;
    public static final HashMap<String, ResourceLocation> streamersLogo = new HashMap();
    public static final List<String> streamersName = Arrays.asList(new String[] {"2old4Stream", "17Blazx", "Akytio", "Alvaena", "Anthox", "Areliann", "Bichard", "Billiechou", "Chipsette", "DiscoverID", "Emmashtream", "French Hardware", "Guillaume", "Hanny017tv", "Hiuuugs", "iMeelk", "JulietteArz", "Karoviper", "KawaVanille", "Kinstaar", "Kitty_R6", "Krolay", "Krysthal", "KyriaTV", "Lemed", "Lilith", "Lriaa", "Lucyd", "LyeGaia", "Nensha", "Packam", "Pandaman", "Phyziik", "Pollynette", "PoppieLala", "Prophecy", "Raww", "Remx", "Seroths", "Sheppard", "Shynouh", "Skartiz", "Sludeina", "SoDlire", "SpaceKaeru", "Thomacky", "Teuf", "Toonz", "test"});
    public Integer page = Integer.valueOf(1);
    public static ResourceLocation unknowLogo = new ResourceLocation("nationsgui", "textures/tvg_streamers/inconnu.png");

    public TVGStreamersGui()
    {
        selectedStreamer = "";
        loaded = false;
        streamerData = new HashMap();
        Iterator var1 = streamersName.iterator();

        while (var1.hasNext())
        {
            String streamerName = (String)var1.next();
            streamersLogo.put(streamerName, new ResourceLocation("nationsgui", "textures/tvg_streamers/" + streamerName.replaceAll(" ", "_").toLowerCase() + ".png"));
        }
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new TVGStreamersDataPacket("")));
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        hoverPageSwitch = false;
        hoveredStreamer = "";
        Object tooltipToDraw = new ArrayList();
        this.drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        ClientEventHandler.STYLE.bindTexture("tvg_streamers");
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);

        if (mouseX >= this.guiLeft + 435 && mouseX <= this.guiLeft + 435 + 18 && mouseY >= this.guiTop + 13 && mouseY <= this.guiTop + 13 + 18)
        {
            ClientEventHandler.STYLE.bindTexture("tvg_streamers");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 435), (float)(this.guiTop + 13), 353, 245, 18, 18, 512.0F, 512.0F, false);
        }

        ModernGui.drawScaledStringCustomFont(I18n.getString("tvg.title"), (float)(this.guiLeft + 14), (float)(this.guiTop + 12), 16777215, 1.0F, "left", false, "georamaExtraBold", 22);
        ModernGui.drawScaledStringCustomFont(I18n.getString("tvg.subtitle"), (float)(this.guiLeft + 14), (float)(this.guiTop + 24), 13513553, 1.0F, "left", false, "georamaMedium", 18);
        ClientEventHandler.STYLE.bindTexture("tvg_streamers");
        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 337), (float)(this.guiTop + 17), 453, 245, 11, 11, 512.0F, 512.0F, false);
        ModernGui.drawScaledStringCustomFont(totalCagnotte + " eur", (float)(this.guiLeft + 425), (float)(this.guiTop + 18), 16777215, 1.0F, "right", false, "georamaMedium", 22);
        ClientEventHandler.STYLE.bindTexture("tvg_streamers");
        List streamers = streamersName.subList((this.page.intValue() - 1) * 28, Math.min((this.page.intValue() - 1) * 28 + 28, streamersName.size() - 1));

        for (int bestTime = 0; bestTime < streamers.size(); ++bestTime)
        {
            int position = bestTime % 7;
            int cagnotte = bestTime / 7;
            int min = this.guiLeft + 24 + position * 42;
            int offsetY = this.guiTop + 50 + cagnotte * 40;
            this.mc.getTextureManager().bindTexture((ResourceLocation)streamersLogo.get(streamers.get(bestTime)));
            ModernGui.drawScaledCustomSizeModalRect((float)min, (float)offsetY, 0.0F, 0.0F, 192, 192, 33, 33, 192.0F, 192.0F, false);
            ClientEventHandler.STYLE.bindTexture("tvg_streamers");

            if ((mouseX < min || mouseX > min + 33 || mouseY < offsetY || mouseY > offsetY + 33) && !((String)streamers.get(bestTime)).equals(selectedStreamer))
            {
                ModernGui.drawModalRectWithCustomSizedTextureWithTransparency((float)(min - 1), (float)(offsetY - 1), 39, 353, 35, 35, 512.0F, 512.0F, false);
            }
            else
            {
                ModernGui.drawModalRectWithCustomSizedTextureWithTransparency((float)(min - 1), (float)(offsetY - 1), 3, 353, 35, 35, 512.0F, 512.0F, false);

                if (mouseX >= min && mouseX <= min + 33 && mouseY >= offsetY && mouseY <= offsetY + 33)
                {
                    hoveredStreamer = (String)streamers.get(bestTime);
                    tooltipToDraw = Arrays.asList(new String[] {(String)streamers.get(bestTime)});
                }
            }

            if (selectedStreamer.equalsIgnoreCase((String)streamers.get(bestTime)))
            {
                ModernGui.drawModalRectWithCustomSizedTextureWithTransparency((float)min, (float)offsetY, 431, 313, 33, 33, 512.0F, 512.0F, false);
            }
        }

        if (mouseX >= this.guiLeft + 7 && mouseX <= this.guiLeft + 18 && mouseY >= this.guiTop + 117 && mouseY <= this.guiTop + 135)
        {
            hoverPageSwitch = true;
            ClientEventHandler.STYLE.bindTexture("tvg_streamers");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 7), (float)(this.guiTop + 117), 389, 243, 11, 18, 512.0F, 512.0F, false);
        }
        else if (mouseX >= this.guiLeft + 317 && mouseX <= this.guiLeft + 328 && mouseY >= this.guiTop + 117 && mouseY <= this.guiTop + 135)
        {
            hoverPageSwitch = true;
            ClientEventHandler.STYLE.bindTexture("tvg_streamers");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 317), (float)(this.guiTop + 117), 404, 243, 11, 18, 512.0F, 512.0F, false);
        }

        if (!selectedStreamer.isEmpty() && !selectedStreamer.equals(playerStreamer))
        {
            ClientEventHandler.STYLE.bindTexture("tvg_streamers");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 24), (float)(this.guiTop + 208), 176, 276, 287, 18, 512.0F, 512.0F, false);
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 170 - 7 + 2) + ModernGui.getCustomFont("georamaSemiBold", Integer.valueOf(20)).getStringWidth(I18n.getString("tvg.valid")) / 2.0F, (float)(this.guiTop + 212), 428, 245, 14, 11, 512.0F, 512.0F, false);
            ModernGui.drawScaledStringCustomFont(I18n.getString("tvg.valid"), (float)(this.guiLeft + 170 - 7), (float)(this.guiTop + 213), 16777215, 1.0F, "center", false, "georamaSemiBold", 20);
        }
        else
        {
            ModernGui.drawScaledStringCustomFont(I18n.getString("tvg.select"), (float)(this.guiLeft + 170), (float)(this.guiTop + 213), 16777215, 1.0F, "center", false, "georamaSemiBold", 20);
        }

        ModernGui.drawScaledStringCustomFont(I18n.getString("tvg.stats"), (float)(this.guiLeft + 339), (float)(this.guiTop + 58), 16777215, 1.0F, "left", false, "georamaSemiBold", 20);
        ClientEventHandler.STYLE.bindTexture("tvg_streamers");
        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 339), (float)(this.guiTop + 75), 3, 323, 108, 28, 512.0F, 512.0F, false);
        ModernGui.drawScaledStringCustomFont(I18n.getString("tvg.bestTime"), (float)(this.guiLeft + 344), (float)(this.guiTop + 86), 13513553, 1.0F, "left", false, "georamaMedium", 15);
        String var15 = "00:00:00";
        long sec;
        long var19;

        if (playerTime.longValue() != 0L)
        {
            long var16 = playerTime.longValue() / 1000L / 60L;
            var19 = playerTime.longValue() / 1000L % 60L;
            sec = playerTime.longValue() - var16 * 60000L - var19 * 1000L;

            if (sec > 100L)
            {
                sec /= 10L;
            }

            var15 = (var16 < 10L ? "0" + var16 : Long.valueOf(var16)) + ":" + (var19 < 10L ? "0" + var19 : Long.valueOf(var19)) + ":" + (sec < 10L ? "0" + sec : Long.valueOf(sec));
        }

        ModernGui.drawScaledStringCustomFont(var15, (float)(this.guiLeft + 410), (float)(this.guiTop + 86), 16777215, 1.0F, "left", false, "georamaMedium", 15);
        ClientEventHandler.STYLE.bindTexture("tvg_streamers");
        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 339), (float)(this.guiTop + 108), 3, 323, 108, 28, 512.0F, 512.0F, false);
        ModernGui.drawScaledStringCustomFont(I18n.getString("tvg.position"), (float)(this.guiLeft + 344), (float)(this.guiTop + 119), 13513553, 1.0F, "left", false, "georamaMedium", 15);
        String var17 = playerPosition.intValue() != 0 ? "#" + playerPosition : "NC";
        ModernGui.drawScaledStringCustomFont(var17, (float)(this.guiLeft + 410), (float)(this.guiTop + 119), 16777215, 1.0F, "left", false, "georamaMedium", 15);

        if (loaded)
        {
            this.mc.getTextureManager().bindTexture(!selectedStreamer.isEmpty() ? (ResourceLocation)streamersLogo.get(selectedStreamer) : unknowLogo);
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 368), (float)(this.guiTop + 141), 0.0F, 0.0F, 192, 192, 79, 79, 192.0F, 192.0F, false);
            ClientEventHandler.STYLE.bindTexture("tvg_streamers");
            ModernGui.drawModalRectWithCustomSizedTextureWithTransparency((float)(this.guiLeft + 339), (float)(this.guiTop + 141), 3, 238, 108, 79, 512.0F, 512.0F, false);

            if (!selectedStreamer.isEmpty())
            {
                ModernGui.drawScaledStringCustomFont(selectedStreamer, (float)(this.guiLeft + 344), (float)(this.guiTop + 148), 16777215, 1.0F, "left", false, "georamaMedium", 15);
            }
            else
            {
                ModernGui.drawScaledStringCustomFont(I18n.getString("tvg.unknow1"), (float)(this.guiLeft + 344), (float)(this.guiTop + 148), 16777215, 1.0F, "left", false, "georamaMedium", 15);
                ModernGui.drawScaledStringCustomFont(I18n.getString("tvg.unknow2"), (float)(this.guiLeft + 344), (float)(this.guiTop + 155), 16777215, 1.0F, "left", false, "georamaMedium", 15);
            }

            ModernGui.drawScaledStringCustomFont(streamerData.containsKey("position") ? "#" + (String)streamerData.get("position") : "NC", (float)(this.guiLeft + 344), (float)(this.guiTop + 190), 16777215, 1.0F, "left", false, "georamaRegular", 15);
            String var18 = streamerData.containsKey("cagnotte") ? (String)streamerData.get("cagnotte") : "0";
            ModernGui.drawScaledStringCustomFont(var18 + " eur", (float)(this.guiLeft + 344), (float)(this.guiTop + 200), 16777215, 1.0F, "left", false, "georamaRegular", 15);
            var15 = "00:00:00";

            if (streamerData.containsKey("time"))
            {
                var19 = Long.parseLong((String)streamerData.get("time")) / 1000L / 60L;
                sec = Long.parseLong((String)streamerData.get("time")) / 1000L % 60L;
                long millis = Long.parseLong((String)streamerData.get("time")) - var19 * 60000L - sec * 1000L;

                if (millis > 100L)
                {
                    millis /= 10L;
                }

                var15 = (var19 < 10L ? "0" + var19 : Long.valueOf(var19)) + ":" + (sec < 10L ? "0" + sec : Long.valueOf(sec)) + ":" + (millis < 10L ? "0" + millis : Long.valueOf(millis));
            }

            ModernGui.drawScaledStringCustomFont(var15, (float)(this.guiLeft + 344), (float)(this.guiTop + 210), 16777215, 1.0F, "left", false, "georamaRegular", 15);
        }

        if (!((List)tooltipToDraw).isEmpty())
        {
            this.drawHoveringText((List)tooltipToDraw, mouseX, mouseY, this.fontRenderer);
        }

        super.drawScreen(mouseX, mouseY, par3);
        GL11.glEnable(GL11.GL_LIGHTING);
        RenderHelper.enableStandardItemLighting();
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            if (mouseX > this.guiLeft + 435 && mouseX < this.guiLeft + 435 + 18 && mouseY > this.guiTop + 13 && mouseY < this.guiTop + 13 + 18)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
            }
            else if (mouseX > this.guiLeft + 24 && mouseX < this.guiLeft + 24 + 287 && mouseY > this.guiTop + 208 && mouseY < this.guiTop + 208 + 18)
            {
                if (!selectedStreamer.isEmpty())
                {
                    this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                    PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new TVGStreamersSetPacket(selectedStreamer)));
                    playerStreamer = selectedStreamer;
                }
            }
            else if (!hoveredStreamer.isEmpty())
            {
                selectedStreamer = hoveredStreamer;
                hoveredStreamer = "";
                streamerData = new HashMap();
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new TVGStreamersDataPacket(selectedStreamer)));
            }
            else if (hoverPageSwitch)
            {
                hoverPageSwitch = false;
                this.page = Integer.valueOf(this.page.intValue() == 1 ? 2 : 1);
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
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
