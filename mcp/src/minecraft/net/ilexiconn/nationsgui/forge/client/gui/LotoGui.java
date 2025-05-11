/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.internal.LinkedTreeMap
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.gui.inventory.GuiInventory
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.world.World
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import com.google.gson.internal.LinkedTreeMap;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.main.component.CustomInputFieldGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.LotoBuyPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.LotoDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.LotoGetItemsPacket;
import net.ilexiconn.nationsgui.forge.server.util.SoundStreamer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class LotoGui
extends GuiScreen {
    public static int GUI_SCALE = 3;
    public static HashMap<String, Object> data = new HashMap();
    public static boolean loaded = false;
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
    public static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    public static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
    public static LinkedHashMap<String, Integer> colors = new LinkedHashMap<String, Integer>(){
        {
            this.put("yellow", -407550);
            this.put("red", -1760196);
            this.put("orange", -2323131);
            this.put("green", -8730273);
            this.put("blue", -9537810);
            this.put("cyan", -9518866);
            this.put("purple", -5345554);
            this.put("pink", -1867845);
            this.put("white", -1314054);
        }
    };
    public static LinkedHashMap<String, Integer> colorIndex = new LinkedHashMap<String, Integer>(){
        {
            this.put("yellow", 0);
            this.put("red", 1);
            this.put("orange", 2);
            this.put("green", 3);
            this.put("blue", 4);
            this.put("cyan", 5);
            this.put("purple", 6);
            this.put("pink", 7);
            this.put("white", 8);
        }
    };
    public static LinkedHashMap<String, Integer> lightsY = new LinkedHashMap<String, Integer>(){
        {
            this.put("yellow", 22);
            this.put("red", 45);
            this.put("orange", 68);
            this.put("green", 91);
            this.put("blue", 114);
            this.put("cyan", 137);
            this.put("purple", 160);
            this.put("pink", 183);
            this.put("white", 206);
        }
    };
    public static LinkedHashMap<String, Integer> iconsX = new LinkedHashMap<String, Integer>(){
        {
            this.put("trophee", 779);
            this.put("calendar", 790);
            this.put("ticket", 801);
            this.put("people", 812);
            this.put("money", 823);
        }
    };
    public String hoveredAction = "";
    public static long lastLightsSwitch = 0L;
    protected int xSize = 380;
    protected int ySize = 218;
    private int guiLeft;
    private int guiTop;
    private RenderItem itemRenderer = new RenderItem();
    public static boolean currentLightsStatus = false;
    public static long lastFlareFullOpacity = 0L;
    private int carouselLotteryIndex = 0;
    private LinkedTreeMap<String, Object> selectedLottery = null;
    public String displayMode = "in_progress";
    public long lastMusicCheck = 0L;
    private GuiTextField ticketsInput;
    private GuiTextField donationInput;
    private GuiScrollBarGeneric scrollBar;
    private GuiScrollBarGeneric scrollBarWinner;
    private int hoveredLotteryPastId = -1;
    private EntityOtherPlayerMP cachedPlayerEntity = null;

    public LotoGui() {
        loaded = false;
        this.carouselLotteryIndex = 0;
    }

    public static int getElementYByColor(int defaultY, String color) {
        return defaultY + 70 * colorIndex.get(color);
    }

    public static List<ItemStack> stringToItemstacks(String itemsSerialized) {
        ArrayList<ItemStack> itemStacks = new ArrayList<ItemStack>();
        for (String itemStackStr : itemsSerialized.split(",")) {
            itemStacks.add(new ItemStack(Integer.parseInt(itemStackStr.split(":")[0]), 1, Integer.parseInt(itemStackStr.split(":")[1])));
        }
        return itemStacks;
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new LotoDataPacket(false)));
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        this.ticketsInput = new CustomInputFieldGUI(this.guiLeft + 226, this.guiTop + 135, 29, 12, "georamaMedium", 25);
        this.ticketsInput.func_73804_f(5);
        this.donationInput = new CustomInputFieldGUI(this.guiLeft + 226, this.guiTop + 147, 29, 12, "georamaMedium", 25);
        this.donationInput.func_73804_f(7);
        this.scrollBar = new GuiScrollBarGeneric(this.guiLeft + 348, this.guiTop + 42, 152, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        this.scrollBarWinner = new GuiScrollBarGeneric(this.guiLeft + 338, this.guiTop + 130, 49, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        this.cachedPlayerEntity = null;
    }

    public void func_73874_b() {
        if (ClientProxy.commandPlayer != null && ClientProxy.commandPlayer.isPlaying()) {
            ClientProxy.commandPlayer.softClose();
        }
        super.func_73874_b();
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        if (System.currentTimeMillis() - this.lastMusicCheck > 1000L) {
            this.lastMusicCheck = System.currentTimeMillis();
            if (ClientProxy.commandPlayer == null || !ClientProxy.commandPlayer.isPlaying()) {
                ClientProxy.commandPlayer = new SoundStreamer("https://static.nationsglory.fr/N332GNyG2N.mp3");
                ClientProxy.commandPlayer.setVolume(Minecraft.func_71410_x().field_71474_y.field_74340_b * 0.15f);
                new Thread(ClientProxy.commandPlayer).start();
            }
        }
        ArrayList tooltipToDraw = new ArrayList();
        this.hoveredAction = "";
        this.hoveredLotteryPastId = -1;
        this.func_73873_v_();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("loto");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 7, this.guiTop + 7, 7 * GUI_SCALE, 7 * GUI_SCALE, 366 * GUI_SCALE, 204 * GUI_SCALE, 366, 204, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
        ClientEventHandler.STYLE.bindTexture("loto");
        if (mouseX >= this.guiLeft + 347 && mouseX <= this.guiLeft + 347 + 9 && mouseY >= this.guiTop + 20 && mouseY <= this.guiTop + 20 + 9) {
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 347, this.guiTop + 20, 442 * GUI_SCALE, 53 * GUI_SCALE, 9 * GUI_SCALE, 9 * GUI_SCALE, 9, 9, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
            this.hoveredAction = "close";
        } else {
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 347, this.guiTop + 20, 431 * GUI_SCALE, 53 * GUI_SCALE, 9 * GUI_SCALE, 9 * GUI_SCALE, 9, 9, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
        }
        if (this.selectedLottery != null || this.displayMode.equals("past")) {
            ClientEventHandler.STYLE.bindTexture("loto");
            if (mouseX >= this.guiLeft + 305 && mouseX <= this.guiLeft + 305 + 30 && mouseY >= this.guiTop + 20 && mouseY <= this.guiTop + 20 + 9) {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 305, this.guiTop + 20, 439 * GUI_SCALE, 67 * GUI_SCALE, 6 * GUI_SCALE, 9 * GUI_SCALE, 6, 9, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.label.return"), this.guiLeft + 315, this.guiTop + 21, 14803951, 0.5f, "left", false, "georamaSemiBold", 30);
                this.hoveredAction = "return";
            } else {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 305, this.guiTop + 20, 431 * GUI_SCALE, 67 * GUI_SCALE, 6 * GUI_SCALE, 9 * GUI_SCALE, 6, 9, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.label.return"), this.guiLeft + 315, this.guiTop + 21, 10395075, 0.5f, "left", false, "georamaSemiBold", 30);
            }
        }
        if (loaded) {
            ArrayList lotteries_inprogress = (ArrayList)data.get("lotteries_inprogress");
            String color = "yellow";
            LinkedTreeMap<String, Object> currentCarouselLottery = null;
            if (lotteries_inprogress.size() > 0) {
                currentCarouselLottery = (LinkedTreeMap<String, Object>)lotteries_inprogress.get(this.carouselLotteryIndex);
            }
            if (this.selectedLottery != null) {
                currentCarouselLottery = this.selectedLottery;
            }
            if (currentCarouselLottery != null) {
                color = (String)currentCarouselLottery.get((Object)"designColor");
            }
            if (this.displayMode.equals("past") && this.selectedLottery == null) {
                color = "yellow";
            }
            ModernGui.glColorHex(colors.get(color), 1.0f);
            ModernGui.drawRectangle(this.guiLeft, this.guiTop, this.field_73735_i, this.xSize, 4.0f);
            ModernGui.glColorHex(colors.get(color), 0.5f);
            ModernGui.drawRectangle((float)this.guiLeft + 4.0f, (float)this.guiTop + 4.0f, this.field_73735_i, this.xSize - 8, 3.0f);
            ModernGui.glColorHex(colors.get(color), 1.0f);
            ModernGui.drawRectangle(this.guiLeft, this.guiTop + this.ySize - 4, this.field_73735_i, this.xSize, 4.0f);
            ModernGui.glColorHex(colors.get(color), 0.5f);
            ModernGui.drawRectangle((float)this.guiLeft + 4.0f, (float)this.guiTop + (float)this.ySize - 7.0f, this.field_73735_i, this.xSize - 8, 3.0f);
            ModernGui.glColorHex(colors.get(color), 1.0f);
            ModernGui.drawRectangle(this.guiLeft, this.guiTop, this.field_73735_i, 4.0f, this.ySize);
            ModernGui.glColorHex(colors.get(color), 0.5f);
            ModernGui.drawRectangle((float)this.guiLeft + 4.0f, (float)this.guiTop + 4.0f, this.field_73735_i, 3.0f, this.ySize - 8);
            ModernGui.glColorHex(colors.get(color), 1.0f);
            ModernGui.drawRectangle((float)this.guiLeft + (float)this.xSize - 4.0f, this.guiTop, this.field_73735_i, 4.0f, this.ySize);
            ModernGui.glColorHex(colors.get(color), 0.5f);
            ModernGui.drawRectangle((float)this.guiLeft + (float)this.xSize - 7.0f, (float)this.guiTop + 4.0f, this.field_73735_i, 3.0f, this.ySize - 8);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            if (System.currentTimeMillis() - lastLightsSwitch > 250L) {
                lastLightsSwitch = System.currentTimeMillis();
                currentLightsStatus = !currentLightsStatus;
            }
            ClientEventHandler.STYLE.bindTexture("loto");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 307, this.guiTop - 8, 397 * GUI_SCALE, (currentLightsStatus ? lightsY.get(color) : 0) * GUI_SCALE, 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 368, this.guiTop - 8, 397 * GUI_SCALE, (!currentLightsStatus ? lightsY.get(color) : 0) * GUI_SCALE, 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 368, this.guiTop + 46, 397 * GUI_SCALE, (currentLightsStatus ? lightsY.get(color) : 0) * GUI_SCALE, 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 368, this.guiTop + 100, 397 * GUI_SCALE, (!currentLightsStatus ? lightsY.get(color) : 0) * GUI_SCALE, 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 368, this.guiTop + 154, 397 * GUI_SCALE, (currentLightsStatus ? lightsY.get(color) : 0) * GUI_SCALE, 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 368, this.guiTop + 206, 397 * GUI_SCALE, (!currentLightsStatus ? lightsY.get(color) : 0) * GUI_SCALE, 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 307, this.guiTop + 206, 397 * GUI_SCALE, (currentLightsStatus ? lightsY.get(color) : 0) * GUI_SCALE, 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 244, this.guiTop + 206, 397 * GUI_SCALE, (!currentLightsStatus ? lightsY.get(color) : 0) * GUI_SCALE, 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 181, this.guiTop + 206, 397 * GUI_SCALE, (currentLightsStatus ? lightsY.get(color) : 0) * GUI_SCALE, 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 118, this.guiTop + 206, 397 * GUI_SCALE, (!currentLightsStatus ? lightsY.get(color) : 0) * GUI_SCALE, 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 55, this.guiTop + 206, 397 * GUI_SCALE, (currentLightsStatus ? lightsY.get(color) : 0) * GUI_SCALE, 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft - 8, this.guiTop + 206, 397 * GUI_SCALE, (!currentLightsStatus ? lightsY.get(color) : 0) * GUI_SCALE, 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft - 8, this.guiTop + 154, 397 * GUI_SCALE, (currentLightsStatus ? lightsY.get(color) : 0) * GUI_SCALE, 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft - 8, this.guiTop + 100, 397 * GUI_SCALE, (!currentLightsStatus ? lightsY.get(color) : 0) * GUI_SCALE, 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft - 8, this.guiTop + 46, 397 * GUI_SCALE, (currentLightsStatus ? lightsY.get(color) : 0) * GUI_SCALE, 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft - 8, this.guiTop - 8, 397 * GUI_SCALE, (!currentLightsStatus ? lightsY.get(color) : 0) * GUI_SCALE, 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 55, this.guiTop - 8, 397 * GUI_SCALE, (currentLightsStatus ? lightsY.get(color) : 0) * GUI_SCALE, 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
            ClientEventHandler.STYLE.bindTexture("loto");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 100, this.guiTop - 35, 248 * GUI_SCALE, 404 * GUI_SCALE, 188 * GUI_SCALE, 95 * GUI_SCALE, 188, 95, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 133, this.guiTop - 20, 471 * GUI_SCALE, 411 * GUI_SCALE, 120 * GUI_SCALE, 50 * GUI_SCALE, 120, 50, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)(this.selectedLottery != null ? "lottery.player.informations" : (this.displayMode.equals("past") ? "lottery.player.past_lotteries" : "lottery.player.informations"))), this.guiLeft + 25, this.guiTop + 30, 10395075, 0.5f, "left", false, "georamaSemiBold", 26);
            if (this.displayMode.equals("past") && this.selectedLottery == null) {
                ClientEventHandler.STYLE.bindTexture("loto");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 24, this.guiTop + 38, 0 * GUI_SCALE, 512 * GUI_SCALE, 332 * GUI_SCALE, 160 * GUI_SCALE, 332, 160, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 348, this.guiTop + 43, 513 * GUI_SCALE, 8 * GUI_SCALE, 2 * GUI_SCALE, 152 * GUI_SCALE, 2, 152, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                int index = 0;
                for (LinkedTreeMap lottery : (ArrayList)data.get("lotteries_past")) {
                    GUIUtils.startGLScissor(this.guiLeft + 29, this.guiTop + 46, 315, 152);
                    int offsetX = this.guiLeft + 29;
                    Float offsetY = Float.valueOf((float)(this.guiTop + 46 + index * 39) + this.getSlideLotteries());
                    ClientEventHandler.STYLE.bindTexture("loto_staff");
                    ModernGui.drawScaledCustomSizeModalRect(offsetX, offsetY.intValue(), 0 * GUI_SCALE, 219 * GUI_SCALE, 245 * GUI_SCALE, 35 * GUI_SCALE, 245, 35, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    ModernGui.glColorHex(colors.get(lottery.get((Object)"designColor")), 1.0f);
                    ModernGui.drawRoundedRectangle((float)offsetX + 234.0f, offsetY.floatValue(), this.field_73735_i, 81.0f, 35.0f);
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    ClientEventHandler.STYLE.bindTexture("loto");
                    ModernGui.drawScaledCustomSizeModalRect(offsetX + 2, offsetY.intValue() + 1, iconsX.get("calendar") * GUI_SCALE, LotoGui.getElementYByColor(16, (String)lottery.get((Object)"designColor")) * GUI_SCALE, 11 * GUI_SCALE, 11 * GUI_SCALE, 11, 11, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    String date = dateFormat.format(new Date(((Double)lottery.get((Object)"endTime")).longValue()));
                    ModernGui.drawScaledStringCustomFont(date, offsetX + 15, offsetY.intValue() + 3, 16514302, 0.5f, "left", false, "georamaMedium", 26);
                    ClientEventHandler.STYLE.bindTexture("loto");
                    ModernGui.drawScaledCustomSizeModalRect(offsetX + 2, offsetY.intValue() + 12, iconsX.get("people") * GUI_SCALE, LotoGui.getElementYByColor(16, (String)lottery.get((Object)"designColor")) * GUI_SCALE, 11 * GUI_SCALE, 11 * GUI_SCALE, 11, 11, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    ModernGui.drawScaledStringCustomFont((((String)lottery.get((Object)"players")).isEmpty() ? "0" : Integer.valueOf(((String)lottery.get((Object)"players")).split(",").length)) + " " + I18n.func_135053_a((String)"lottery.label.players"), offsetX + 15, offsetY.intValue() + 14, 16514302, 0.5f, "left", false, "georamaMedium", 26);
                    ClientEventHandler.STYLE.bindTexture("loto");
                    ModernGui.drawScaledCustomSizeModalRect(offsetX + 2, offsetY.intValue() + 23, iconsX.get("trophee") * GUI_SCALE, LotoGui.getElementYByColor(16, (String)lottery.get((Object)"designColor")) * GUI_SCALE, 11 * GUI_SCALE, 11 * GUI_SCALE, 11, 11, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    ModernGui.drawScaledStringCustomFont(String.format("%.0f", (Double)lottery.get((Object)"winnersCount")) + " " + I18n.func_135053_a((String)"lottery.label.winners"), offsetX + 15, offsetY.intValue() + 25, 16514302, 0.5f, "left", false, "georamaMedium", 26);
                    ModernGui.drawScaledStringCustomFont(String.format("%.0f", (Double)lottery.get((Object)"cashPrice")) + "$", offsetX + 125, offsetY.intValue() + 8, 15463162, 0.75f, "left", false, "georamaSemiBold", 28);
                    ModernGui.drawScaledStringCustomFont((((String)lottery.get((Object)"itemsPrice")).isEmpty() ? "0" : Integer.valueOf(((String)lottery.get((Object)"itemsPrice")).split(",").length)) + " item(s)", offsetX + 125, offsetY.intValue() + 20, 15463162, 0.5f, "left", false, "georamaSemiBold", 28);
                    if (((String)lottery.get((Object)"designLogo")).isEmpty() || !((String)lottery.get((Object)"designLogo")).matches("https://static.nationsglory.fr/.*\\.png")) {
                        ClientEventHandler.STYLE.bindTexture("loto");
                        ModernGui.drawScaledCustomSizeModalRect(offsetX + 245, offsetY.intValue() + 5, 471 * GUI_SCALE, 411 * GUI_SCALE, 120 * GUI_SCALE, 50 * GUI_SCALE, 60, 25, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    } else {
                        ModernGui.bindRemoteTexture((String)lottery.get((Object)"designLogo"));
                        ModernGui.drawScaledCustomSizeModalRect(offsetX + 245, offsetY.intValue() + 5, 0 * GUI_SCALE, 0 * GUI_SCALE, 600, 250, 60, 25, 600.0f, 250.0f, false);
                    }
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    if (mouseX >= this.guiLeft + 29 && mouseX <= this.guiLeft + 29 + 315 && mouseY >= this.guiTop + 46 && mouseY <= this.guiTop + 46 + 152 && mouseX <= offsetX + 315 && mouseY >= offsetY.intValue() && mouseY <= offsetY.intValue() + 35) {
                        this.hoveredAction = "open_lottery_past";
                        this.hoveredLotteryPastId = index;
                    }
                    GUIUtils.endGLScissor();
                    ++index;
                }
                this.scrollBar.draw(mouseX, mouseY);
            } else if (currentCarouselLottery != null) {
                ModernGui.glColorHex(colors.get(color), 1.0f);
                ModernGui.drawRoundedRectangle((float)this.guiLeft + 24.0f + 10.0f, (float)this.guiTop + 38.0f, this.field_73735_i, 322.0f, 76.0f);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                ClientEventHandler.STYLE.bindTexture("loto");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 24, this.guiTop + 38, 172 * GUI_SCALE, 229 * GUI_SCALE, 332 * GUI_SCALE, 76 * GUI_SCALE, 332, 76, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 294, this.guiTop + 31, 432 * GUI_SCALE, 8 * GUI_SCALE, 62 * GUI_SCALE, 12 * GUI_SCALE, 62, 12, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(((Double)currentCarouselLottery.get((Object)"ticketsPlayer")).intValue() + " " + I18n.func_135053_a((String)"lottery.label.tickets.short") + " | " + String.format("%.3f", (Double)currentCarouselLottery.get((Object)"ticketsPlayer") / Math.max(1.0, (Double)currentCarouselLottery.get((Object)"ticketsSold")) * 100.0) + "%", this.guiLeft + 325, this.guiTop + 34, 10395075, 0.5f, "center", false, "georamaMedium", 24);
                if (((String)currentCarouselLottery.get((Object)"designLogo")).isEmpty() || !((String)currentCarouselLottery.get((Object)"designLogo")).matches("https://static.nationsglory.fr/.*\\.png")) {
                    ClientEventHandler.STYLE.bindTexture("loto");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 247, this.guiTop + 57, 471 * GUI_SCALE, 411 * GUI_SCALE, 120 * GUI_SCALE, 50 * GUI_SCALE, 96, 40, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                } else {
                    ModernGui.bindRemoteTexture((String)currentCarouselLottery.get((Object)"designLogo"));
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 247, this.guiTop + 57, 0 * GUI_SCALE, 0 * GUI_SCALE, 600, 250, 96, 40, 600.0f, 250.0f, false);
                }
                int offsetY = 0;
                if ((Double)currentCarouselLottery.get((Object)"cashPrice") > 0.0 || currentCarouselLottery.containsKey((Object)"poolMode") && ((String)currentCarouselLottery.get((Object)"poolMode")).equals("true")) {
                    ModernGui.drawScaledString(ModernGui.formatIntToDevise(((Double)currentCarouselLottery.get((Object)"cashPrice")).intValue()) + "$", this.guiLeft + 35, this.guiTop + 55, 16514302, 2.5f, false, true);
                    offsetY += 22;
                } else if (!((String)currentCarouselLottery.get((Object)"itemsPrice")).isEmpty()) {
                    ModernGui.drawScaledString(I18n.func_135053_a((String)"lottery.player.items_lot"), this.guiLeft + 35, this.guiTop + 55, 16514302, 2.5f, false, true);
                    offsetY += 22;
                }
                if (!((String)currentCarouselLottery.get((Object)"itemsPrice")).isEmpty()) {
                    String[] itemStacks = LotoGui.stringToItemstacks((String)currentCarouselLottery.get((Object)"itemsPrice"));
                    for (int i = 0; i < itemStacks.size(); ++i) {
                        ClientEventHandler.STYLE.bindTexture("loto");
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 33 + 20 * i, this.guiTop + 55 + offsetY, 461 * GUI_SCALE, 52 * GUI_SCALE, 16 * GUI_SCALE, 16 * GUI_SCALE, 16, 16, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                        ItemStack itemStack = itemStacks.get(i);
                        GL11.glPushMatrix();
                        this.itemRenderer.func_82406_b(this.field_73886_k, Minecraft.func_71410_x().func_110434_K(), itemStack, this.guiLeft + 33 + 20 * i, this.guiTop + 55 + offsetY);
                        GL11.glPopMatrix();
                        GL11.glDisable((int)2896);
                        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    }
                }
            } else {
                ClientEventHandler.STYLE.bindTexture("loto");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 24, this.guiTop + 38 - 31, 0 * GUI_SCALE, 917 * GUI_SCALE, 332 * GUI_SCALE, 107 * GUI_SCALE, 332, 107, 1024 * GUI_SCALE, 1024 * GUI_SCALE, true);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.player.no_lottery_inprogress"), this.guiLeft + 35, this.guiTop + 50, 16514302, 0.5f, "left", false, "georamaSemiBold", 30);
            }
            if (this.selectedLottery != null) {
                ClientEventHandler.STYLE.bindTexture("loto");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 24, this.guiTop + 110, 0 * GUI_SCALE, 308 * GUI_SCALE, 332 * GUI_SCALE, 89 * GUI_SCALE, 332, 89, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 37, this.guiTop + 122, iconsX.get("calendar") * GUI_SCALE, LotoGui.getElementYByColor(16, (String)this.selectedLottery.get((Object)"designColor")) * GUI_SCALE, 11 * GUI_SCALE, 11 * GUI_SCALE, 11, 11, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.player.draw_on") + " " + dateTimeFormat.format(new Date(((Double)this.selectedLottery.get((Object)"endTime")).longValue())), this.guiLeft + 51, this.guiTop + 124, 16514302, 0.5f, "left", false, "georamaSemiBold", 26);
                ClientEventHandler.STYLE.bindTexture("loto");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 37, this.guiTop + 137, iconsX.get("people") * GUI_SCALE, LotoGui.getElementYByColor(16, (String)this.selectedLottery.get((Object)"designColor")) * GUI_SCALE, 11 * GUI_SCALE, 11 * GUI_SCALE, 11, 11, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont((((String)this.selectedLottery.get((Object)"players")).isEmpty() ? "0" : Integer.valueOf(((String)this.selectedLottery.get((Object)"players")).split(",").length)) + " " + I18n.func_135053_a((String)"lottery.label.players"), this.guiLeft + 51, this.guiTop + 139, 16514302, 0.5f, "left", false, "georamaSemiBold", 26);
                ModernGui.glColorHex(colors.get(this.selectedLottery.get((Object)"designColor")), 1.0f);
                ModernGui.drawRoundedRectangle((float)this.guiLeft + 34.0f, (float)this.guiTop + 155.0f, this.field_73735_i, 150.0f, 35.0f);
                ClientEventHandler.STYLE.bindTexture("loto");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 37, this.guiTop + 159, 443 * GUI_SCALE, 84 * GUI_SCALE, 11 * GUI_SCALE, 11 * GUI_SCALE, 11, 11, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(((Double)this.selectedLottery.get((Object)"ticketsPlayer")).intValue() + " " + I18n.func_135053_a((String)((Double)this.selectedLottery.get((Object)"ticketsPlayer") > 0.0 ? "lottery.player.bought_tickets" : "lottery.player.bought_ticket")) + " (" + Double.valueOf((Double)this.selectedLottery.get((Object)"ticketsPlayer") * (Double)this.selectedLottery.get((Object)"ticketPrice")).intValue() + "$)", this.guiLeft + 51, this.guiTop + 162, 2826561, 0.5f, "left", false, "georamaSemiBold", 26);
                ClientEventHandler.STYLE.bindTexture("loto");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 37, this.guiTop + 174, 465 * GUI_SCALE, 84 * GUI_SCALE, 11 * GUI_SCALE, 11 * GUI_SCALE, 11, 11, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(String.format("%.3f", (Double)this.selectedLottery.get((Object)"ticketsPlayer") / Math.max(1.0, (Double)this.selectedLottery.get((Object)"ticketsSold")) * 100.0) + "% " + I18n.func_135053_a((String)"lottery.player.win_chance"), this.guiLeft + 51, this.guiTop + 177, 2826561, 0.5f, "left", false, "georamaSemiBold", 26);
                if (this.displayMode.equals("past")) {
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.player.winners"), this.guiLeft + 196, this.guiTop + 118, 16514302, 0.5f, "left", false, "georamaSemiBold", 28);
                    if (!((String)this.selectedLottery.get((Object)"winners")).isEmpty() && ((String)this.selectedLottery.get((Object)"winners")).split(",").length == 1) {
                        String winnerName = ((String)this.selectedLottery.get((Object)"winners")).split("#")[0];
                        ModernGui.drawScaledStringCustomFont(winnerName, this.guiLeft + 219, this.guiTop + 149, 16514302, 0.5f, "left", false, "georamaBold", 28);
                        for (String player : ((String)this.selectedLottery.get((Object)"players")).split(",")) {
                            if (!player.split("#")[0].equals(winnerName)) continue;
                            ModernGui.drawScaledStringCustomFont(player.split("#")[1] + " " + I18n.func_135053_a((String)"lottery.label.tickets"), this.guiLeft + 219, this.guiTop + 158, 10395075, 0.5f, "left", false, "georamaSemiBold", 28);
                        }
                        if (this.cachedPlayerEntity == null || !this.cachedPlayerEntity.field_71092_bJ.equals(winnerName)) {
                            try {
                                this.cachedPlayerEntity = new EntityOtherPlayerMP((World)this.field_73882_e.field_71441_e, winnerName);
                            }
                            catch (Exception e) {
                                this.cachedPlayerEntity = null;
                            }
                        } else {
                            GUIUtils.startGLScissor(this.guiLeft + 285, this.guiTop + 111, 51, 75);
                            GuiInventory.func_110423_a((int)(this.guiLeft + 310), (int)(this.guiTop + 212), (int)50, (float)0.0f, (float)0.0f, (EntityLivingBase)this.cachedPlayerEntity);
                            GUIUtils.endGLScissor();
                        }
                    } else if (!((String)this.selectedLottery.get((Object)"winners")).isEmpty()) {
                        ClientEventHandler.STYLE.bindTexture("loto");
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 338, this.guiTop + 130, 513 * GUI_SCALE, 8 * GUI_SCALE, 2 * GUI_SCALE, 49 * GUI_SCALE, 2, 49, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                        GUIUtils.startGLScissor(this.guiLeft + 201, this.guiTop + 131, 140, 55);
                        int i = 0;
                        for (String winner : ((String)this.selectedLottery.get((Object)"winners")).split(",")) {
                            String winnerName = winner.split("#")[0];
                            int offsetX = this.guiLeft + 201;
                            Float offsetY = Float.valueOf((float)(this.guiTop + 131 + i * 13) + this.getSlideWinners());
                            if (!ClientProxy.cacheHeadPlayer.containsKey(winnerName)) {
                                try {
                                    ResourceLocation resourceLocation = AbstractClientPlayer.field_110314_b;
                                    resourceLocation = AbstractClientPlayer.func_110311_f((String)winnerName);
                                    AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)winnerName);
                                    ClientProxy.cacheHeadPlayer.put(winnerName, resourceLocation);
                                }
                                catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            } else {
                                Minecraft.func_71410_x().field_71446_o.func_110577_a(ClientProxy.cacheHeadPlayer.get(winnerName));
                                this.field_73882_e.func_110434_K().func_110577_a(ClientProxy.cacheHeadPlayer.get(winnerName));
                                GUIUtils.drawScaledCustomSizeModalRect(offsetX + 12, offsetY.intValue() + 0 + 10, 8.0f, 16.0f, 8, -8, -10, -10, 64.0f, 64.0f);
                            }
                            ModernGui.drawScaledStringCustomFont(winnerName, offsetX + 17, offsetY.intValue() + 2, 16514302, 0.5f, "left", false, "georamaSemiBold", 26);
                            for (String player : ((String)this.selectedLottery.get((Object)"players")).split(",")) {
                                if (!player.split("#")[0].equals(winnerName)) continue;
                                ModernGui.drawScaledStringCustomFont(player.split("#")[1] + " " + I18n.func_135053_a((String)"lottery.label.tickets"), offsetX + 132, offsetY.intValue() + 1, 10395075, 0.5f, "right", false, "georamaMedium", 26);
                            }
                            ++i;
                        }
                        GUIUtils.endGLScissor();
                        this.scrollBarWinner.draw(mouseX, mouseY);
                    }
                    if (((String)this.selectedLottery.get((Object)"players_itemsPrice")).matches("(,.*|^)" + Minecraft.func_71410_x().field_71439_g.field_71092_bJ + "(,.*|$)")) {
                        ClientEventHandler.STYLE.bindTexture("loto");
                        if (mouseX >= this.guiLeft + 244 && mouseX <= this.guiLeft + 244 + 53 && mouseY >= this.guiTop + 180 && mouseY <= this.guiTop + 180 + 11) {
                            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 244, this.guiTop + 180, 519 * GUI_SCALE, 8 * GUI_SCALE, 53 * GUI_SCALE, 11 * GUI_SCALE, 53, 11, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                            this.hoveredAction = "get_items";
                        } else {
                            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 244, this.guiTop + 180, 779 * GUI_SCALE, LotoGui.getElementYByColor(3, (String)this.selectedLottery.get((Object)"designColor")) * GUI_SCALE, 53 * GUI_SCALE, 11 * GUI_SCALE, 53, 11, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                        }
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.label.get_items"), this.guiLeft + 244 + 27, this.guiTop + 183, 2234425, 0.5f, "center", false, "georamaSemiBold", 24);
                    }
                } else {
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.player.participate"), this.guiLeft + 196, this.guiTop + 116, 16514302, 0.5f, "left", false, "georamaSemiBold", 28);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.player.tickets"), this.guiLeft + 200, this.guiTop + 138, 15463162, 0.5f, "left", false, "georamaSemiBold", 26);
                    ClientEventHandler.STYLE.bindTexture("loto");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 227, this.guiTop + 137, 838 * GUI_SCALE, LotoGui.getElementYByColor(3, (String)this.selectedLottery.get((Object)"designColor")) * GUI_SCALE, 30 * GUI_SCALE, 9 * GUI_SCALE, 30, 9, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    this.ticketsInput.func_73795_f();
                    if (((String)this.selectedLottery.get((Object)"allowDonation")).equals("true")) {
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.player.donations"), this.guiLeft + 200, this.guiTop + 150, 15463162, 0.5f, "left", false, "georamaSemiBold", 26);
                        ClientEventHandler.STYLE.bindTexture("loto");
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 227, this.guiTop + 149, 472 * GUI_SCALE, 185 * GUI_SCALE, 30 * GUI_SCALE, 9 * GUI_SCALE, 30, 9, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                        this.donationInput.func_73795_f();
                    }
                    ModernGui.drawScaledStringCustomFont("TOTAL " + (FactionGUI.isNumeric(this.ticketsInput.func_73781_b(), true) && FactionGUI.isNumeric(this.donationInput.func_73781_b(), true) ? Integer.parseInt(this.ticketsInput.func_73781_b()) * ((Double)this.selectedLottery.get((Object)"ticketPrice")).intValue() + Integer.parseInt(this.donationInput.func_73781_b()) : 0) + "$", this.guiLeft + 271, this.guiTop + 170, 16514302, 0.5f, "center", false, "georamaSemiBold", 32);
                    ClientEventHandler.STYLE.bindTexture("loto");
                    if (mouseX >= this.guiLeft + 244 && mouseX <= this.guiLeft + 244 + 53 && mouseY >= this.guiTop + 180 && mouseY <= this.guiTop + 180 + 11) {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 244, this.guiTop + 180, 519 * GUI_SCALE, 8 * GUI_SCALE, 53 * GUI_SCALE, 11 * GUI_SCALE, 53, 11, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                        this.hoveredAction = "buy";
                    } else {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 244, this.guiTop + 180, 779 * GUI_SCALE, LotoGui.getElementYByColor(3, (String)this.selectedLottery.get((Object)"designColor")) * GUI_SCALE, 53 * GUI_SCALE, 11 * GUI_SCALE, 53, 11, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    }
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.label.buy"), this.guiLeft + 244 + 27, this.guiTop + 183, 2234425, 0.5f, "center", false, "georamaSemiBold", 24);
                    ClientEventHandler.STYLE.bindTexture("loto");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 276, this.guiTop + 137, 437 * GUI_SCALE, 197 * GUI_SCALE, 65 * GUI_SCALE, 21 * GUI_SCALE, 65, 21, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.player.ticket_price") + " : " + ((Double)this.selectedLottery.get((Object)"ticketPrice")).intValue() + "$", this.guiLeft + 280, this.guiTop + 140, 10395075, 0.5f, "left", false, "georamaSemiBold", 24);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.player.remaining_tickets") + " : " + ((Double)this.selectedLottery.get((Object)"maxTicketsGlobal") != 0.0 ? String.format("%.0f", Math.max(0.0, (Double)this.selectedLottery.get((Object)"maxTicketsGlobal") - (Double)this.selectedLottery.get((Object)"ticketsSold"))) : "-"), this.guiLeft + 280, this.guiTop + 149, 10395075, 0.5f, "left", false, "georamaSemiBold", 24);
                }
            } else if (!this.displayMode.equals("past")) {
                if (currentCarouselLottery != null) {
                    ClientEventHandler.STYLE.bindTexture("loto");
                    if (mouseX >= this.guiLeft + 33 && mouseX <= this.guiLeft + 33 + 53 && mouseY >= this.guiTop + 97 && mouseY <= this.guiTop + 97 + 11) {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 33, this.guiTop + 97, 519 * GUI_SCALE, 8 * GUI_SCALE, 53 * GUI_SCALE, 11 * GUI_SCALE, 53, 11, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                        this.hoveredAction = "participate";
                    } else {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 33, this.guiTop + 97, 779 * GUI_SCALE, LotoGui.getElementYByColor(3, (String)currentCarouselLottery.get((Object)"designColor")) * GUI_SCALE, 53 * GUI_SCALE, 11 * GUI_SCALE, 53, 11, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    }
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.label.participate"), this.guiLeft + 33 + 27, this.guiTop + 100, 2234425, 0.5f, "center", false, "georamaSemiBold", 24);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.player.draw_on") + " " + dateTimeFormat.format(new Date(((Double)currentCarouselLottery.get((Object)"endTime")).longValue())), this.guiLeft + 352, this.guiTop + 107, 2234425, 0.5f, "right", false, "georamaSemiBold", 22);
                    ClientEventHandler.STYLE.bindTexture("loto");
                    if (mouseX >= this.guiLeft + 12 && mouseX <= this.guiLeft + 12 + 10 && mouseY >= this.guiTop + 67 && mouseY <= this.guiTop + 67 + 18) {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 12, this.guiTop + 67, 454 * GUI_SCALE, 29 * GUI_SCALE, 10 * GUI_SCALE, 18 * GUI_SCALE, 10, 18, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                        this.hoveredAction = "carousel_previous";
                    } else {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 12, this.guiTop + 67, 466 * GUI_SCALE, 29 * GUI_SCALE, 10 * GUI_SCALE, 18 * GUI_SCALE, 10, 18, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    }
                    ClientEventHandler.STYLE.bindTexture("loto");
                    if (mouseX >= this.guiLeft + 359 && mouseX <= this.guiLeft + 359 + 10 && mouseY >= this.guiTop + 67 && mouseY <= this.guiTop + 67 + 18) {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 359, this.guiTop + 67, 442 * GUI_SCALE, 29 * GUI_SCALE, 10 * GUI_SCALE, 18 * GUI_SCALE, 10, 18, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                        this.hoveredAction = "carousel_next";
                    } else {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 359, this.guiTop + 67, 430 * GUI_SCALE, 29 * GUI_SCALE, 10 * GUI_SCALE, 18 * GUI_SCALE, 10, 18, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    }
                }
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.player.old_draws"), this.guiLeft + 25, this.guiTop + 122, 10395075, 0.5f, "left", false, "georamaSemiBold", 26);
                ClientEventHandler.STYLE.bindTexture("loto");
                if (mouseX >= this.guiLeft + 24 && mouseX <= this.guiLeft + 24 + 160 && mouseY >= this.guiTop + 130 && mouseY <= this.guiTop + 130 + 61) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 24, this.guiTop + 130, 613 * GUI_SCALE, LotoGui.getElementYByColor(3, !lotteries_inprogress.isEmpty() ? (String)((LinkedTreeMap)lotteries_inprogress.get(this.carouselLotteryIndex)).get((Object)"designColor") : "yellow") * GUI_SCALE, 160 * GUI_SCALE, 61 * GUI_SCALE, 160, 61, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 159, this.guiTop + 153, 539 * GUI_SCALE, 24 * GUI_SCALE, 16 * GUI_SCALE, 16 * GUI_SCALE, 16, 16, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    this.hoveredAction = "old_draw";
                } else {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 24, this.guiTop + 130, 0 * GUI_SCALE, 225 * GUI_SCALE, 160 * GUI_SCALE, 61 * GUI_SCALE, 160, 61, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 159, this.guiTop + 153, 519 * GUI_SCALE, 24 * GUI_SCALE, 16 * GUI_SCALE, 16 * GUI_SCALE, 16, 16, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                }
                ClientEventHandler.STYLE.bindTexture("loto");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 45, this.guiTop + 143, 471 * GUI_SCALE, 411 * GUI_SCALE, 120 * GUI_SCALE, 50 * GUI_SCALE, 80, 35, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.player.my_profil"), this.guiLeft + 196, this.guiTop + 122, 10395075, 0.5f, "left", false, "georamaSemiBold", 26);
                ClientEventHandler.STYLE.bindTexture("loto");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 195, this.guiTop + 130, 0 * GUI_SCALE, 225 * GUI_SCALE, 160 * GUI_SCALE, 61 * GUI_SCALE, 160, 61, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                if (!ClientProxy.cacheHeadPlayer.containsKey(Minecraft.func_71410_x().field_71439_g.field_71092_bJ)) {
                    try {
                        ResourceLocation resourceLocation = AbstractClientPlayer.field_110314_b;
                        resourceLocation = AbstractClientPlayer.func_110311_f((String)Minecraft.func_71410_x().field_71439_g.field_71092_bJ);
                        AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)Minecraft.func_71410_x().field_71439_g.field_71092_bJ);
                        ClientProxy.cacheHeadPlayer.put(Minecraft.func_71410_x().field_71439_g.field_71092_bJ, resourceLocation);
                    }
                    catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    Minecraft.func_71410_x().field_71446_o.func_110577_a(ClientProxy.cacheHeadPlayer.get(Minecraft.func_71410_x().field_71439_g.field_71092_bJ));
                    this.field_73882_e.func_110434_K().func_110577_a(ClientProxy.cacheHeadPlayer.get(Minecraft.func_71410_x().field_71439_g.field_71092_bJ));
                    GUIUtils.drawScaledCustomSizeModalRect(this.guiLeft + 202 + 18, this.guiTop + 134 + 18, 8.0f, 16.0f, 8, -8, -18, -18, 64.0f, 64.0f);
                }
                ModernGui.drawScaledStringCustomFont(Minecraft.func_71410_x().field_71439_g.field_71092_bJ, this.guiLeft + 230, this.guiTop + 135, 15463162, 0.5f, "left", false, "georamaBold", 32);
                ClientEventHandler.STYLE.bindTexture("loto");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 202, this.guiTop + 155, iconsX.get("trophee") * GUI_SCALE, LotoGui.getElementYByColor(16, color) * GUI_SCALE, 11 * GUI_SCALE, 11 * GUI_SCALE, 11, 11, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.player.wins"), this.guiLeft + 216, this.guiTop + 156, 0xDADAED, 0.5f, "left", false, "georamaSemiBold", 26);
                ModernGui.drawScaledStringCustomFont(((Double)data.get("player_wins")).intValue() + "", this.guiLeft + 297, this.guiTop + 156, 15463162, 0.5f, "left", false, "georamaSemiBold", 26);
                ClientEventHandler.STYLE.bindTexture("loto");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 202, this.guiTop + 165, iconsX.get("ticket") * GUI_SCALE, LotoGui.getElementYByColor(16, color) * GUI_SCALE, 11 * GUI_SCALE, 11 * GUI_SCALE, 11, 11, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.player.participations"), this.guiLeft + 216, this.guiTop + 167, 0xDADAED, 0.5f, "left", false, "georamaSemiBold", 26);
                ModernGui.drawScaledStringCustomFont(((Double)data.get("player_participations")).intValue() + "", this.guiLeft + 297, this.guiTop + 167, 15463162, 0.5f, "left", false, "georamaSemiBold", 26);
                ClientEventHandler.STYLE.bindTexture("loto");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 202, this.guiTop + 177, iconsX.get("money") * GUI_SCALE, LotoGui.getElementYByColor(16, color) * GUI_SCALE, 11 * GUI_SCALE, 11 * GUI_SCALE, 11, 11, 1024 * GUI_SCALE, 1024 * GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"lottery.player.money_earn"), this.guiLeft + 216, this.guiTop + 178, 0xDADAED, 0.5f, "left", false, "georamaSemiBold", 26);
                ModernGui.drawScaledStringCustomFont(((Double)data.get("player_total_money")).intValue() + "$", this.guiLeft + 297, this.guiTop + 178, 15463162, 0.5f, "left", false, "georamaSemiBold", 26);
            }
        }
        if (!tooltipToDraw.isEmpty()) {
            this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
        }
        super.func_73863_a(mouseX, mouseY, par3);
        GL11.glEnable((int)2896);
        RenderHelper.func_74519_b();
    }

    private float getSlideLotteries() {
        return ((ArrayList)data.get("lotteries_past")).size() > 4 ? (float)(-(((ArrayList)data.get("lotteries_past")).size() - 4) * 39) * this.scrollBar.getSliderValue() : 0.0f;
    }

    private float getSlideWinners() {
        return ((String)this.selectedLottery.get((Object)"winners")).split(",").length > 4 ? (float)(-(((String)this.selectedLottery.get((Object)"winners")).split(",").length - 4) * 13) * this.scrollBarWinner.getSliderValue() : 0.0f;
    }

    public void func_73876_c() {
        this.ticketsInput.func_73780_a();
        this.donationInput.func_73780_a();
        super.func_73876_c();
    }

    protected void func_73869_a(char par1, int par2) {
        this.ticketsInput.func_73802_a(par1, par2);
        this.donationInput.func_73802_a(par1, par2);
        super.func_73869_a(par1, par2);
    }

    public boolean func_73868_f() {
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            block24: {
                if (this.hoveredAction.equals("close")) {
                    this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                    Minecraft.func_71410_x().func_71373_a(null);
                } else if (this.hoveredAction.equals("return")) {
                    if (this.selectedLottery == null && this.displayMode.equals("past")) {
                        this.displayMode = "in_progress";
                    }
                    this.selectedLottery = null;
                } else if (this.hoveredAction.equals("open_lottery_past")) {
                    this.selectedLottery = (LinkedTreeMap)((ArrayList)data.get("lotteries_past")).get(this.hoveredLotteryPastId);
                } else if (this.hoveredAction.equals("carousel_next")) {
                    this.carouselLotteryIndex = ((ArrayList)data.get("lotteries_inprogress")).size() <= this.carouselLotteryIndex + 1 ? 0 : this.carouselLotteryIndex + 1;
                } else if (this.hoveredAction.equals("carousel_previous")) {
                    this.carouselLotteryIndex = this.carouselLotteryIndex - 1 < 0 ? ((ArrayList)data.get("lotteries_inprogress")).size() - 1 : this.carouselLotteryIndex - 1;
                } else if (this.hoveredAction.equals("participate")) {
                    this.ticketsInput.func_73782_a("0");
                    this.donationInput.func_73782_a("0");
                    this.selectedLottery = (LinkedTreeMap)((ArrayList)data.get("lotteries_inprogress")).get(this.carouselLotteryIndex);
                } else if (this.hoveredAction.equals("old_draw")) {
                    this.displayMode = "past";
                } else {
                    if (this.hoveredAction.equals("buy")) {
                        if (this.ticketsInput.func_73781_b().isEmpty()) {
                            this.ticketsInput.func_73782_a("0");
                        }
                        if (this.donationInput.func_73781_b().isEmpty()) {
                            this.donationInput.func_73782_a("0");
                        }
                        if (FactionGUI.isNumeric(this.ticketsInput.func_73781_b(), true) && FactionGUI.isNumeric(this.donationInput.func_73781_b(), true) && (Integer.parseInt(this.ticketsInput.func_73781_b()) != 0 || Integer.parseInt(this.donationInput.func_73781_b()) != 0)) {
                            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new LotoBuyPacket(((Double)this.selectedLottery.get((Object)"id")).intValue(), Integer.parseInt(this.ticketsInput.func_73781_b()), Integer.parseInt(this.donationInput.func_73781_b()))));
                            this.selectedLottery = null;
                            this.ticketsInput.func_73782_a("0");
                            this.donationInput.func_73782_a("0");
                            break block24;
                        } else {
                            Minecraft.func_71410_x().field_71439_g.func_71035_c(I18n.func_135053_a((String)"lotery.error.wrong_data"));
                            return;
                        }
                    }
                    if (this.hoveredAction.equals("get_items")) {
                        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new LotoGetItemsPacket(((Double)this.selectedLottery.get((Object)"id")).intValue())));
                        Minecraft.func_71410_x().func_71373_a(null);
                    }
                }
            }
            if (this.selectedLottery != null && !this.displayMode.equals("past")) {
                this.ticketsInput.func_73793_a(mouseX, mouseY, mouseButton);
                if (((String)this.selectedLottery.get((Object)"allowDonation")).equals("true")) {
                    this.donationInput.func_73793_a(mouseX, mouseY, mouseButton);
                }
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
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
}

