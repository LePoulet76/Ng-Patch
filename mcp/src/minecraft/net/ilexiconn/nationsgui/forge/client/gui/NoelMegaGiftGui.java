/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
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
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class NoelMegaGiftGui
extends GuiScreen {
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
    ArrayList<String> servers = new ArrayList<String>(Arrays.asList("red", "orange", "coral", "yellow", "lime", "green", "blue", "cyan", "pink", "purple", "black", "white", "mocha", "epsilon", "alpha", "omega", "delta", "sigma"));
    private int guiLeft;
    private int guiTop;
    private GuiScrollBarGeneric scrollBar;
    private RenderItem itemRenderer = new RenderItem();

    public void func_73866_w_() {
        super.func_73866_w_();
        history.clear();
        megaGiftAround = false;
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new NoelMegaGiftDataPacket()));
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new GUIGetHelpPacket(((Object)((Object)this)).getClass().getSimpleName())));
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        this.scrollBar = new GuiScrollBarGeneric(this.guiLeft + 440, this.guiTop + 45, 198, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_noel_mega_gift.png"), 2, 28);
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        boolean isHovered;
        if (System.currentTimeMillis() - this.lastMusicCheck > 1000L) {
            this.lastMusicCheck = System.currentTimeMillis();
            if (ClientProxy.commandPlayer == null || !ClientProxy.commandPlayer.isPlaying()) {
                ClientProxy.commandPlayer = new SoundStreamer("https://static.nationsglory.fr/N4__G564G3.mp3");
                ClientProxy.commandPlayer.setVolume(Minecraft.func_71410_x().field_71474_y.field_74340_b * 0.15f);
                new Thread(ClientProxy.commandPlayer).start();
            }
        }
        ModernGui.drawDefaultBackground(this, this.field_73880_f, this.field_73881_g, mouseX, mouseY);
        this.hoveredAction = "";
        ArrayList<String> tooltipToDraw = new ArrayList<String>();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("noel_mega_gift");
        if (this.stars.size() < 40) {
            Random random = new Random();
            String star = random.nextInt(this.field_73880_f) + "#" + random.nextInt(this.field_73881_g) + "#" + System.currentTimeMillis() + "#" + (System.currentTimeMillis() + (long)(random.nextInt(2000) + 1000)) + "#" + (random.nextInt(3) + 1) + "#" + (random.nextInt(80) + 20);
            this.stars.add(star);
        }
        ArrayList<String> newStars = new ArrayList<String>();
        for (String star : this.stars) {
            GL11.glPushMatrix();
            String[] startData = star.split("#");
            float percent = (float)(System.currentTimeMillis() - Long.parseLong(startData[2])) * 1.0f / (float)(Long.parseLong(startData[3]) - Long.parseLong(startData[2]));
            GL11.glScalef((float)(percent *= (float)Integer.parseInt(startData[5]) / 100.0f), (float)percent, (float)percent);
            GL11.glTranslatef((float)((float)Integer.parseInt(startData[0]) * (1.0f / percent) - 7.0f * percent), (float)((float)Integer.parseInt(startData[1]) * (1.0f / percent) - 7.0f * percent), (float)0.0f);
            ClientEventHandler.STYLE.bindTexture("noel_mega_gift");
            ModernGui.drawScaledCustomSizeModalRect(0.0f, 0.0f, 468 * GUI_SCALE, 100 * GUI_SCALE, 7 * GUI_SCALE, 7 * GUI_SCALE, 7, 7, textureWidth, textureHeight, false);
            GL11.glPopMatrix();
            if (System.currentTimeMillis() >= Long.parseLong(startData[3])) continue;
            newStars.add(star);
        }
        this.stars = newStars;
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft, this.guiTop, 0.0f, 0.0f, this.xSize * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize, this.ySize, textureWidth, textureHeight, false);
        ClientEventHandler.STYLE.bindTexture("noel_mega_gift");
        int firstKeyId = 3738;
        boolean playerHasMegaKeyInInventory = false;
        for (String string : Minecraft.func_71410_x().field_71439_g.field_71071_by.field_70462_a) {
            if (string == null || string.func_77973_b().field_77779_bT != 3747) continue;
            playerHasMegaKeyInInventory = true;
            break;
        }
        for (int i = 0; i < 10; ++i) {
            boolean bl;
            int x = this.guiLeft + 24 + i % 2 * 91;
            int y = this.guiTop + 45 + i / 2 * 34;
            boolean bl2 = false;
            for (ItemStack itemStack : Minecraft.func_71410_x().field_71439_g.field_71071_by.field_70462_a) {
                if (itemStack == null || itemStack.func_77973_b().field_77779_bT != firstKeyId + i) continue;
                bl = true;
                break;
            }
            int textureX = (bl && (!playerHasMegaKeyInInventory || i == 9) ? 24 : 206) + i % 2 * 91;
            int textureY = 283 + i / 2 * 34;
            ModernGui.drawScaledCustomSizeModalRect(x, y, textureX * GUI_SCALE, textureY * GUI_SCALE, 83 * GUI_SCALE, 30 * GUI_SCALE, 83, 30, textureWidth, textureHeight, false);
        }
        ClientEventHandler.STYLE.bindTexture("noel_mega_gift");
        boolean timeIsValidToOpen = false;
        Calendar now = Calendar.getInstance();
        int currentHour = now.get(11);
        int n = now.get(12);
        int currentSecond = now.get(13);
        boolean bl = timeIsValidToOpen = currentHour >= 16 && currentHour < 21;
        if (Minecraft.func_71410_x().field_71439_g.field_71092_bJ.equalsIgnoreCase("iBalix") || Minecraft.func_71410_x().field_71439_g.field_71092_bJ.equalsIgnoreCase("mineiban34")) {
            timeIsValidToOpen = true;
        }
        boolean bl3 = isHovered = mouseX >= this.guiLeft + 24 && mouseX < this.guiLeft + 24 + 174 && mouseY >= this.guiTop + 226 && mouseY < this.guiTop + 226 + 16;
        if (!megaGiftAround) {
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 24, this.guiTop + 226, 468 * GUI_SCALE, (isHovered ? 32 : 12) * GUI_SCALE, 174 * GUI_SCALE, 16 * GUI_SCALE, 174, 16, textureWidth, textureHeight, false);
            ModernGui.drawScaledStringCustomFont("SE T\u00c9L\u00c9PORTER AU MEGA CADEAU", this.guiLeft + 24 + 87, this.guiTop + 226 + 5, 11487488, 0.5f, "center", false, "georamaBold", 25);
            if (isHovered) {
                this.hoveredAction = "teleport";
            }
        } else if (playerHasMegaKeyInInventory && timeIsValidToOpen) {
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 24, this.guiTop + 226, 468 * GUI_SCALE, (isHovered ? 32 : 12) * GUI_SCALE, 174 * GUI_SCALE, 16 * GUI_SCALE, 174, 16, textureWidth, textureHeight, false);
            ModernGui.drawScaledStringCustomFont("OUVRIR LE MEGA CADEAU", this.guiLeft + 24 + 87, this.guiTop + 226 + 5, 11487488, 0.5f, "center", false, "georamaBold", 25);
            if (isHovered) {
                this.hoveredAction = "open";
            }
        } else {
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 24, this.guiTop + 226, 468 * GUI_SCALE, 52 * GUI_SCALE, 174 * GUI_SCALE, 16 * GUI_SCALE, 174, 16, textureWidth, textureHeight, false);
            if (!playerHasMegaKeyInInventory) {
                ModernGui.drawScaledStringCustomFont("OUVRIR LE MEGA CADEAU", this.guiLeft + 24 + 87, this.guiTop + 226 + 5, 10703923, 0.5f, "center", false, "georamaBold", 25);
                if (isHovered) {
                    tooltipToDraw.add("\u00a7cVous devez poss\u00e9der la cl\u00e9 du m\u00e9ga cadeau");
                }
            } else {
                Calendar nextSession = Calendar.getInstance();
                if (currentHour < 16) {
                    nextSession.set(11, 16);
                    nextSession.set(12, 0);
                    nextSession.set(13, 0);
                } else if (currentHour >= 21) {
                    nextSession.add(5, 1);
                    nextSession.set(11, 16);
                    nextSession.set(12, 0);
                    nextSession.set(13, 0);
                }
                long delayInSeconds = (nextSession.getTimeInMillis() - now.getTimeInMillis()) / 1000L;
                long hours = delayInSeconds / 3600L;
                long minutes = delayInSeconds % 3600L / 60L;
                long seconds = delayInSeconds % 60L;
                String formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                ModernGui.drawScaledStringCustomFont("PROCHAINE SESSION DANS " + formattedTime, this.guiLeft + 24 + 87, this.guiTop + 226 + 5, 10703923, 0.5f, "center", false, "georamaBold", 25);
            }
        }
        if (!history.isEmpty()) {
            ClientEventHandler.STYLE.bindTexture("noel_mega_gift");
            GUIUtils.startGLScissor(this.guiLeft + 262, this.guiTop + 45, 178, 205);
            for (int index = 0; index < history.size(); ++index) {
                boolean isToday;
                String line = history.get(index);
                String[] parts = line.split("#");
                String playerName = parts[0];
                String serverName = parts[1].toLowerCase();
                String lootName = parts[2];
                long date = Long.parseLong(parts[3]);
                int x = this.guiLeft + 262;
                int y = this.guiTop + 45 + index * 15 + (int)this.getSlideHistory();
                ClientEventHandler.STYLE.bindTexture("noel_mega_gift");
                if (this.servers.contains(serverName)) {
                    ModernGui.drawScaledCustomSizeModalRect(x, y, (468 + this.servers.indexOf(serverName) * 10) * GUI_SCALE, 78 * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, textureWidth, textureHeight, false);
                    if (mouseX >= x && mouseX <= x + 8 && mouseY >= y && mouseY <= y + 8) {
                        tooltipToDraw.add("Serveur " + serverName.substring(0, 1).toUpperCase() + serverName.substring(1));
                    }
                }
                if (!ClientProxy.cacheHeadPlayer.containsKey(playerName)) {
                    try {
                        ResourceLocation resourceLocation = AbstractClientPlayer.field_110314_b;
                        resourceLocation = AbstractClientPlayer.func_110311_f((String)playerName);
                        AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)playerName);
                        ClientProxy.cacheHeadPlayer.put(playerName, resourceLocation);
                    }
                    catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    Minecraft.func_71410_x().field_71446_o.func_110577_a(ClientProxy.cacheHeadPlayer.get(playerName));
                    this.field_73882_e.func_110434_K().func_110577_a(ClientProxy.cacheHeadPlayer.get(playerName));
                    GUIUtils.drawScaledCustomSizeModalRect(x + 19, y + 8, 8.0f, 16.0f, 8, -8, -8, -8, 64.0f, 64.0f);
                }
                playerName = playerName.length() > 12 ? playerName.substring(0, 12) + ".." : playerName;
                ModernGui.drawScaledStringCustomFont(playerName, x + 22, (float)y + 1.5f, 0xFFFFFF, 0.5f, "left", false, "georamaBold", 26);
                ModernGui.drawScaledStringCustomFont("x" + lootName.split(",").length + " items", x + 63, (float)y + 1.5f, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 20);
                if (mouseX >= x + 60 && mouseX <= x + 60 + 25 && (float)mouseY >= (float)y + 1.0f && (float)mouseY <= (float)y + 1.0f + 5.0f) {
                    tooltipToDraw.addAll(Arrays.asList(lootName.split(",")));
                }
                String dateString = "";
                Calendar targetDate = Calendar.getInstance();
                targetDate.setTimeInMillis(date);
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                boolean bl4 = isToday = now.get(1) == targetDate.get(1) && now.get(6) == targetDate.get(6);
                if (isToday) {
                    dateString = "Aujourd'hui " + timeFormat.format(targetDate.getTime());
                } else {
                    SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM HH:mm");
                    dateString = dateTimeFormat.format(targetDate.getTime());
                }
                ModernGui.drawScaledStringCustomFont("\u00a7o" + dateString, x + 175, (float)y + 2.5f, 6169387, 0.5f, "right", false, "georamaSemiBold", 16);
            }
            GUIUtils.endGLScissor();
            this.scrollBar.draw(mouseX, mouseY);
        }
        if (!tooltipToDraw.isEmpty()) {
            this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
        }
        super.func_73863_a(mouseX, mouseY, par3);
        GL11.glEnable((int)2896);
        RenderHelper.func_74519_b();
    }

    private float getSlideHistory() {
        return history.size() > 14 ? (float)(-(history.size() - 14) * 15) * this.scrollBar.getSliderValue() : 0.0f;
    }

    protected void drawHoveringText(List par1List, int par2, int par3, FontRenderer font) {
        if (!par1List.isEmpty()) {
            GL11.glDisable((int)32826);
            RenderHelper.func_74518_a();
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            int k = 0;
            for (String s : par1List) {
                int l = font.func_78256_a(s);
                if (l <= k) continue;
                k = l;
            }
            int i1 = par2 + 12;
            int j1 = par3 - 12;
            int k1 = 8;
            if (par1List.size() > 1) {
                k1 += 2 + (par1List.size() - 1) * 10;
            }
            if (i1 + k > this.field_73880_f) {
                i1 -= 28 + k;
            }
            if (j1 + k1 + 6 > this.field_73881_g) {
                j1 = this.field_73881_g - k1 - 6;
            }
            this.field_73735_i = 300.0f;
            this.itemRenderer.field_77023_b = 300.0f;
            int l1 = -267386864;
            this.func_73733_a(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
            this.func_73733_a(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
            this.func_73733_a(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
            this.func_73733_a(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
            this.func_73733_a(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
            int i2 = 0x505000FF;
            int j2 = (i2 & 0xFEFEFE) >> 1 | i2 & 0xFF000000;
            this.func_73733_a(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
            this.func_73733_a(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
            this.func_73733_a(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
            this.func_73733_a(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);
            for (int k2 = 0; k2 < par1List.size(); ++k2) {
                String s1 = (String)par1List.get(k2);
                font.func_78261_a(s1, i1, j1, -1);
                if (k2 == 0) {
                    j1 += 2;
                }
                j1 += 10;
            }
            this.field_73735_i = 0.0f;
            this.itemRenderer.field_77023_b = 0.0f;
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            GL11.glEnable((int)32826);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
    }

    public void func_73874_b() {
        if (ClientProxy.commandPlayer != null && ClientProxy.commandPlayer.isPlaying()) {
            ClientProxy.commandPlayer.softClose();
        }
        super.func_73874_b();
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        ModernGui.mouseClickedCommon(this, mouseX, mouseY, mouseButton);
        if (mouseButton == 0) {
            if (this.hoveredAction.equals("open")) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new NoelMegaGiftOpenPacket()));
                this.field_73882_e.func_71373_a(null);
            } else if (this.hoveredAction.equals("teleport")) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new PlayerExecCmdPacket("warp mega_gift", 0)));
                this.field_73882_e.func_71373_a(null);
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    public boolean func_73868_f() {
        return false;
    }
}

