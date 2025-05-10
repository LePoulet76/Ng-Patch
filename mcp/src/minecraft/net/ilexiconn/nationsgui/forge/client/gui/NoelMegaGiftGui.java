package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.GUIGetHelpPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.NoelMegaGiftDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.NoelMegaGiftOpenPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PlayerExecCmdPacket;
import net.ilexiconn.nationsgui.forge.server.util.SoundStreamer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class NoelMegaGiftGui extends GuiScreen
{
    public static int GUI_SCALE = 3;
    public static int textureWidth = 843 * GUI_SCALE;
    public static int textureHeight = 474 * GUI_SCALE;
    public static ArrayList<String> history = new ArrayList();
    public static boolean megaGiftAround = false;
    public long lastMusicCheck = 0L;
    public String hoveredAction = "";
    public ArrayList<String> stars = new ArrayList();
    protected int xSize = 460;
    protected int ySize = 261;
    ArrayList<String> servers = new ArrayList(Arrays.asList(new String[] {"red", "orange", "coral", "yellow", "lime", "green", "blue", "cyan", "pink", "purple", "black", "white", "mocha", "epsilon", "alpha", "omega", "delta", "sigma"}));
    private int guiLeft;
    private int guiTop;
    private GuiScrollBarGeneric scrollBar;
    private RenderItem itemRenderer = new RenderItem();

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        history.clear();
        megaGiftAround = false;
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new NoelMegaGiftDataPacket()));
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new GUIGetHelpPacket(this.getClass().getSimpleName())));
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.scrollBar = new GuiScrollBarGeneric((float)(this.guiLeft + 440), (float)(this.guiTop + 45), 198, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_noel_mega_gift.png"), 2, 28);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        if (System.currentTimeMillis() - this.lastMusicCheck > 1000L)
        {
            this.lastMusicCheck = System.currentTimeMillis();

            if (ClientProxy.commandPlayer == null || !ClientProxy.commandPlayer.isPlaying())
            {
                ClientProxy.commandPlayer = new SoundStreamer("https://static.nationsglory.fr/N4__G564G3.mp3");
                ClientProxy.commandPlayer.setVolume(Minecraft.getMinecraft().gameSettings.soundVolume * 0.15F);
                (new Thread(ClientProxy.commandPlayer)).start();
            }
        }

        ModernGui.drawDefaultBackground(this, this.width, this.height, mouseX, mouseY);
        this.hoveredAction = "";
        ArrayList tooltipToDraw = new ArrayList();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        ClientEventHandler.STYLE.bindTexture("noel_mega_gift");

        if (this.stars.size() < 40)
        {
            Random newStars = new Random();
            String firstKeyId = newStars.nextInt(this.width) + "#" + newStars.nextInt(this.height) + "#" + System.currentTimeMillis() + "#" + (System.currentTimeMillis() + (long)(newStars.nextInt(2000) + 1000)) + "#" + (newStars.nextInt(3) + 1) + "#" + (newStars.nextInt(80) + 20);
            this.stars.add(firstKeyId);
        }

        ArrayList var30 = new ArrayList();
        Iterator var31 = this.stars.iterator();

        while (var31.hasNext())
        {
            String playerHasMegaKeyInInventory = (String)var31.next();
            GL11.glPushMatrix();
            String[] timeIsValidToOpen = playerHasMegaKeyInInventory.split("#");
            float now = (float)(System.currentTimeMillis() - Long.parseLong(timeIsValidToOpen[2])) * 1.0F / (float)(Long.parseLong(timeIsValidToOpen[3]) - Long.parseLong(timeIsValidToOpen[2]));
            now *= (float)Integer.parseInt(timeIsValidToOpen[5]) / 100.0F;
            GL11.glScalef(now, now, now);
            GL11.glTranslatef((float)Integer.parseInt(timeIsValidToOpen[0]) * (1.0F / now) - 7.0F * now, (float)Integer.parseInt(timeIsValidToOpen[1]) * (1.0F / now) - 7.0F * now, 0.0F);
            ClientEventHandler.STYLE.bindTexture("noel_mega_gift");
            ModernGui.drawScaledCustomSizeModalRect(0.0F, 0.0F, (float)(468 * GUI_SCALE), (float)(100 * GUI_SCALE), 7 * GUI_SCALE, 7 * GUI_SCALE, 7, 7, (float)textureWidth, (float)textureHeight, false);
            GL11.glPopMatrix();

            if (System.currentTimeMillis() < Long.parseLong(timeIsValidToOpen[3]))
            {
                var30.add(playerHasMegaKeyInInventory);
            }
        }

        this.stars = var30;
        ModernGui.drawScaledCustomSizeModalRect((float)this.guiLeft, (float)this.guiTop, 0.0F, 0.0F, this.xSize * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize, this.ySize, (float)textureWidth, (float)textureHeight, false);
        ClientEventHandler.STYLE.bindTexture("noel_mega_gift");
        short var32 = 3738;
        boolean var33 = false;
        ItemStack[] var34 = Minecraft.getMinecraft().thePlayer.inventory.mainInventory;
        int var37 = var34.length;
        int currentHour;

        for (currentHour = 0; currentHour < var37; ++currentHour)
        {
            ItemStack currentMinute = var34[currentHour];

            if (currentMinute != null && currentMinute.getItem().itemID == 3747)
            {
                var33 = true;
                break;
            }
        }

        int var35 = 0;
        int index;
        int var41;

        while (var35 < 10)
        {
            var37 = this.guiLeft + 24 + var35 % 2 * 91;
            currentHour = this.guiTop + 45 + var35 / 2 * 34;
            boolean var39 = false;
            ItemStack[] currentSecond = Minecraft.getMinecraft().thePlayer.inventory.mainInventory;
            int isHovered = currentSecond.length;
            index = 0;

            while (true)
            {
                if (index < isHovered)
                {
                    ItemStack line = currentSecond[index];

                    if (line == null || line.getItem().itemID != var32 + var35)
                    {
                        ++index;
                        continue;
                    }

                    var39 = true;
                }

                var41 = (var39 && (!var33 || var35 == 9) ? 24 : 206) + var35 % 2 * 91;
                isHovered = 283 + var35 / 2 * 34;
                ModernGui.drawScaledCustomSizeModalRect((float)var37, (float)currentHour, (float)(var41 * GUI_SCALE), (float)(isHovered * GUI_SCALE), 83 * GUI_SCALE, 30 * GUI_SCALE, 83, 30, (float)textureWidth, (float)textureHeight, false);
                ++var35;
                break;
            }
        }

        ClientEventHandler.STYLE.bindTexture("noel_mega_gift");
        boolean var36 = false;
        Calendar var38 = Calendar.getInstance();
        currentHour = var38.get(11);
        int var40 = var38.get(12);
        var41 = var38.get(13);
        var36 = currentHour >= 16 && currentHour < 21;

        if (Minecraft.getMinecraft().thePlayer.username.equalsIgnoreCase("iBalix") || Minecraft.getMinecraft().thePlayer.username.equalsIgnoreCase("mineiban34"))
        {
            var36 = true;
        }

        boolean var42 = mouseX >= this.guiLeft + 24 && mouseX < this.guiLeft + 24 + 174 && mouseY >= this.guiTop + 226 && mouseY < this.guiTop + 226 + 16;

        if (!megaGiftAround)
        {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 24), (float)(this.guiTop + 226), (float)(468 * GUI_SCALE), (float)((var42 ? 32 : 12) * GUI_SCALE), 174 * GUI_SCALE, 16 * GUI_SCALE, 174, 16, (float)textureWidth, (float)textureHeight, false);
            ModernGui.drawScaledStringCustomFont("SE T\u00c9L\u00c9PORTER AU MEGA CADEAU", (float)(this.guiLeft + 24 + 87), (float)(this.guiTop + 226 + 5), 11487488, 0.5F, "center", false, "georamaBold", 25);

            if (var42)
            {
                this.hoveredAction = "teleport";
            }
        }
        else if (var33 && var36)
        {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 24), (float)(this.guiTop + 226), (float)(468 * GUI_SCALE), (float)((var42 ? 32 : 12) * GUI_SCALE), 174 * GUI_SCALE, 16 * GUI_SCALE, 174, 16, (float)textureWidth, (float)textureHeight, false);
            ModernGui.drawScaledStringCustomFont("OUVRIR LE MEGA CADEAU", (float)(this.guiLeft + 24 + 87), (float)(this.guiTop + 226 + 5), 11487488, 0.5F, "center", false, "georamaBold", 25);

            if (var42)
            {
                this.hoveredAction = "open";
            }
        }
        else
        {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 24), (float)(this.guiTop + 226), (float)(468 * GUI_SCALE), (float)(52 * GUI_SCALE), 174 * GUI_SCALE, 16 * GUI_SCALE, 174, 16, (float)textureWidth, (float)textureHeight, false);

            if (!var33)
            {
                ModernGui.drawScaledStringCustomFont("OUVRIR LE MEGA CADEAU", (float)(this.guiLeft + 24 + 87), (float)(this.guiTop + 226 + 5), 10703923, 0.5F, "center", false, "georamaBold", 25);

                if (var42)
                {
                    tooltipToDraw.add("\u00a7cVous devez poss\u00e9der la cl\u00e9 du m\u00e9ga cadeau");
                }
            }
            else
            {
                Calendar var43 = Calendar.getInstance();

                if (currentHour < 16)
                {
                    var43.set(11, 16);
                    var43.set(12, 0);
                    var43.set(13, 0);
                }
                else if (currentHour >= 21)
                {
                    var43.add(5, 1);
                    var43.set(11, 16);
                    var43.set(12, 0);
                    var43.set(13, 0);
                }

                long var44 = (var43.getTimeInMillis() - var38.getTimeInMillis()) / 1000L;
                long playerName = var44 / 3600L;
                long lootName = var44 % 3600L / 60L;
                long seconds = var44 % 60L;
                String y = String.format("%02d:%02d:%02d", new Object[] {Long.valueOf(playerName), Long.valueOf(lootName), Long.valueOf(seconds)});
                ModernGui.drawScaledStringCustomFont("PROCHAINE SESSION DANS " + y, (float)(this.guiLeft + 24 + 87), (float)(this.guiTop + 226 + 5), 10703923, 0.5F, "center", false, "georamaBold", 25);
            }
        }

        if (!history.isEmpty())
        {
            ClientEventHandler.STYLE.bindTexture("noel_mega_gift");
            GUIUtils.startGLScissor(this.guiLeft + 262, this.guiTop + 45, 178, 205);

            for (index = 0; index < history.size(); ++index)
            {
                String var45 = (String)history.get(index);
                String[] parts = var45.split("#");
                String var46 = parts[0];
                String serverName = parts[1].toLowerCase();
                String var47 = parts[2];
                long date = Long.parseLong(parts[3]);
                int x = this.guiLeft + 262;
                int var48 = this.guiTop + 45 + index * 15 + (int)this.getSlideHistory();
                ClientEventHandler.STYLE.bindTexture("noel_mega_gift");

                if (this.servers.contains(serverName))
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)x, (float)var48, (float)((468 + this.servers.indexOf(serverName) * 10) * GUI_SCALE), (float)(78 * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)textureWidth, (float)textureHeight, false);

                    if (mouseX >= x && mouseX <= x + 8 && mouseY >= var48 && mouseY <= var48 + 8)
                    {
                        tooltipToDraw.add("Serveur " + serverName.substring(0, 1).toUpperCase() + serverName.substring(1));
                    }
                }

                if (!ClientProxy.cacheHeadPlayer.containsKey(var46))
                {
                    try
                    {
                        ResourceLocation dateString = AbstractClientPlayer.locationStevePng;
                        dateString = AbstractClientPlayer.getLocationSkin(var46);
                        AbstractClientPlayer.getDownloadImageSkin(dateString, var46);
                        ClientProxy.cacheHeadPlayer.put(var46, dateString);
                    }
                    catch (Exception var29)
                    {
                        System.out.println(var29.getMessage());
                    }
                }
                else
                {
                    Minecraft.getMinecraft().renderEngine.bindTexture((ResourceLocation)ClientProxy.cacheHeadPlayer.get(var46));
                    this.mc.getTextureManager().bindTexture((ResourceLocation)ClientProxy.cacheHeadPlayer.get(var46));
                    GUIUtils.drawScaledCustomSizeModalRect(x + 19, var48 + 8, 8.0F, 16.0F, 8, -8, -8, -8, 64.0F, 64.0F);
                }

                var46 = var46.length() > 12 ? var46.substring(0, 12) + ".." : var46;
                ModernGui.drawScaledStringCustomFont(var46, (float)(x + 22), (float)var48 + 1.5F, 16777215, 0.5F, "left", false, "georamaBold", 26);
                ModernGui.drawScaledStringCustomFont("x" + var47.split(",").length + " items", (float)(x + 63), (float)var48 + 1.5F, 16777215, 0.5F, "left", false, "georamaSemiBold", 20);

                if (mouseX >= x + 60 && mouseX <= x + 60 + 25 && (float)mouseY >= (float)var48 + 1.0F && (float)mouseY <= (float)var48 + 1.0F + 5.0F)
                {
                    tooltipToDraw.addAll(Arrays.asList(var47.split(",")));
                }

                String var49 = "";
                Calendar targetDate = Calendar.getInstance();
                targetDate.setTimeInMillis(date);
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                boolean isToday = var38.get(1) == targetDate.get(1) && var38.get(6) == targetDate.get(6);

                if (isToday)
                {
                    var49 = "Aujourd\'hui " + timeFormat.format(targetDate.getTime());
                }
                else
                {
                    SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM HH:mm");
                    var49 = dateTimeFormat.format(targetDate.getTime());
                }

                ModernGui.drawScaledStringCustomFont("\u00a7o" + var49, (float)(x + 175), (float)var48 + 2.5F, 6169387, 0.5F, "right", false, "georamaSemiBold", 16);
            }

            GUIUtils.endGLScissor();
            this.scrollBar.draw(mouseX, mouseY);
        }

        if (!tooltipToDraw.isEmpty())
        {
            this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.fontRenderer);
        }

        super.drawScreen(mouseX, mouseY, par3);
        GL11.glEnable(GL11.GL_LIGHTING);
        RenderHelper.enableStandardItemLighting();
    }

    private float getSlideHistory()
    {
        return history.size() > 14 ? (float)(-(history.size() - 14) * 15) * this.scrollBar.getSliderValue() : 0.0F;
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

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        if (ClientProxy.commandPlayer != null && ClientProxy.commandPlayer.isPlaying())
        {
            ClientProxy.commandPlayer.softClose();
        }

        super.onGuiClosed();
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        ModernGui.mouseClickedCommon(this, mouseX, mouseY, mouseButton);

        if (mouseButton == 0)
        {
            if (this.hoveredAction.equals("open"))
            {
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new NoelMegaGiftOpenPacket()));
                this.mc.displayGuiScreen((GuiScreen)null);
            }
            else if (this.hoveredAction.equals("teleport"))
            {
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new PlayerExecCmdPacket("warp mega_gift", 0)));
                this.mc.displayGuiScreen((GuiScreen)null);
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
}
