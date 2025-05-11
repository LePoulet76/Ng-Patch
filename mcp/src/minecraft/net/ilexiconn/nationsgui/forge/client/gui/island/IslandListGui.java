/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.island;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandCreateGui;
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandPasswordGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandGetImagePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandListDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandTPPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

@SideOnly(value=Side.CLIENT)
public class IslandListGui
extends GuiScreen {
    protected int xSize = 292;
    protected int ySize = 248;
    private int guiLeft;
    private int guiTop;
    private RenderItem itemRenderer = new RenderItem();
    public static boolean loaded = false;
    private GuiScrollBarFaction scrollBar;
    public static ArrayList<HashMap<String, String>> islands = new ArrayList();
    private HashMap<String, String> hoveredIsland = new HashMap();
    private HashMap<String, String> hoveredInfoIsland = new HashMap();
    private String hoveredFilter = "";
    private String selectedFilter = "";
    boolean filterExpanded = false;
    public static HashMap<String, Long> clickedRingTime = new HashMap();
    private String searchText = "";
    private int countIslandListed = 0;
    public static boolean onlyMyIslands = false;
    public static boolean resetList = true;
    private GuiTextField islandSearch;
    public EntityPlayer player;
    private HashMap<String, DynamicTexture> imagesTexture = new HashMap();
    public boolean helpOpened = false;
    public int helpSectionOffsetX = 0;
    List<String> filters = new ArrayList<String>();
    public static boolean isPremium = false;
    public static boolean isOp = false;
    public static String serverNumber = "0";
    public static HashMap<String, String> base64ImagesByIslandId = new HashMap();
    public ArrayList<String> islandIdsImageAlreadyPreload = new ArrayList();
    DynamicTexture defaultImage = null;
    int currentPlayerIslandCount = 0;

    public IslandListGui(EntityPlayer player) {
        this.player = player;
        loaded = false;
        islands = null;
        this.filters.add("visit");
        this.filters.add("connected");
        this.filters.add("vote");
        this.selectedFilter = "connected";
        onlyMyIslands = false;
        BufferedImage image = IslandListGui.decodeToImage("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");
        this.defaultImage = new DynamicTexture(image);
    }

    public void func_73876_c() {
        this.islandSearch.func_73780_a();
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IslandListDataPacket(this.selectedFilter, onlyMyIslands)));
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        this.scrollBar = new GuiScrollBarFaction(this.guiLeft + 272, this.guiTop + 47, 150);
        this.islandSearch = new GuiTextField(this.field_73886_k, this.guiLeft + 180, this.guiTop + 25, 95, 10);
        this.islandSearch.func_73786_a(false);
        this.islandSearch.func_73804_f(40);
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        this.func_73873_v_();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("island_list");
        this.helpSectionOffsetX = !this.helpOpened ? Math.max(this.helpSectionOffsetX - 2, 0) : Math.min(this.helpSectionOffsetX + 2, 107);
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 292 + this.helpSectionOffsetX, this.guiTop + 157, 93, 250, 23, 45, 512.0f, 512.0f, false);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.guiLeft + 300 + this.helpSectionOffsetX), (float)(this.guiTop + 195), (float)0.0f);
        GL11.glRotatef((float)-90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glTranslatef((float)(-(this.guiLeft + 300 + this.helpSectionOffsetX)), (float)(-(this.guiTop + 195)), (float)0.0f);
        this.drawScaledString(I18n.func_135053_a((String)"island.list.help.title"), this.guiLeft + 300 + this.helpSectionOffsetX, this.guiTop + 195, 0, 1.0f, false, false);
        GL11.glPopMatrix();
        ClientEventHandler.STYLE.bindTexture("island_list");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 292 - 107 + this.helpSectionOffsetX, this.guiTop + 8, 405, 0, 107, 232, 512.0f, 512.0f, false);
        this.drawScaledString("\u00a74" + I18n.func_135053_a((String)"island.list.help.title"), this.guiLeft + 344 - 107 + this.helpSectionOffsetX, this.guiTop + 20, 0, 1.3f, true, false);
        for (int i = 1; i <= 16; ++i) {
            this.drawScaledString(I18n.func_135053_a((String)("island.list.help.text_" + i)), this.guiLeft + 344 - 107 + this.helpSectionOffsetX, this.guiTop + 45 + (i - 1) * 10, 0, 0.9f, true, false);
        }
        ClientEventHandler.STYLE.bindTexture("island_list");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        List<Object> tooltipToDraw = new ArrayList();
        this.islandSearch.func_73795_f();
        ClientEventHandler.STYLE.bindTexture("island_list");
        if (mouseX >= this.guiLeft + 278 && mouseX <= this.guiLeft + 278 + 9 && mouseY >= this.guiTop - 8 && mouseY <= this.guiTop - 8 + 10) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 278, this.guiTop - 8, 0, 261, 9, 10, 512.0f, 512.0f, false);
        } else {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 278, this.guiTop - 8, 0, 251, 9, 10, 512.0f, 512.0f, false);
        }
        if (loaded) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)(this.guiLeft + 14), (float)(this.guiTop + 128), (float)0.0f);
            GL11.glRotatef((float)-90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glTranslatef((float)(-(this.guiLeft + 14)), (float)(-(this.guiTop + 128)), (float)0.0f);
            this.drawScaledString(I18n.func_135053_a((String)"island.list.title"), this.guiLeft + 14, this.guiTop + 128, 0xFFFFFF, 1.5f, false, false);
            GL11.glPopMatrix();
            ClientEventHandler.STYLE.bindTexture("island_list");
            if (loaded && islands.size() > 0) {
                int filterOffsetY = 0;
                this.hoveredIsland = new HashMap();
                boolean hoveredButton = false;
                this.hoveredInfoIsland = new HashMap();
                this.hoveredFilter = "";
                this.currentPlayerIslandCount = 0;
                GUIUtils.startGLScissor(this.guiLeft + 50, this.guiTop + 43, 222, 158);
                int index = 0;
                for (HashMap<String, String> islandData : islands) {
                    boolean isAtLeastMember;
                    if (islandData.get("playersOnline").equals("0") && this.searchText.length() < 3 && !onlyMyIslands && this.selectedFilter.equals("connected")) continue;
                    boolean bl = isAtLeastMember = islandData.get("creator").equals(Minecraft.func_71410_x().field_71439_g.getDisplayName()) || islandData.get("members").contains("#" + Minecraft.func_71410_x().field_71439_g.getDisplayName() + "#");
                    if ((!islandData.get("isPrivate").equals("true") || isOp || isAtLeastMember || islandData.get("name").toLowerCase().equalsIgnoreCase(this.searchText.toLowerCase()) || islandData.get("id").toLowerCase().equalsIgnoreCase(this.searchText.toLowerCase())) && (this.searchText.length() >= 3 && islandData.get("name").toLowerCase().contains(this.searchText.toLowerCase()) || this.searchText.length() >= 3 && islandData.get("creator").toLowerCase().contains(this.searchText.toLowerCase()) || this.searchText.length() >= 3 && islandData.get("id").toLowerCase().contains(this.searchText.toLowerCase()) || onlyMyIslands && isAtLeastMember || !islandData.get("playersOnline").equals("0") && this.searchText.length() == 0 || !this.selectedFilter.equals("connected") && index < 50)) {
                        boolean haveToWait;
                        int offsetX = this.guiLeft + 50;
                        Float offsetY = Float.valueOf((float)(this.guiTop + 43 + index * 38) + this.getSlide());
                        if (offsetY.floatValue() >= (float)(this.guiTop + 43) && offsetY.floatValue() <= (float)(this.guiTop + 201)) {
                            if (!this.imagesTexture.containsKey(islandData.get("id") + "#" + islandData.get("serverNumber")) && !this.islandIdsImageAlreadyPreload.contains(islandData.get("id") + "#" + islandData.get("serverNumber"))) {
                                this.islandIdsImageAlreadyPreload.add(islandData.get("id") + "#" + islandData.get("serverNumber"));
                                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IslandGetImagePacket(islandData.get("id"), islandData.get("serverNumber"))));
                            }
                            if (base64ImagesByIslandId.containsKey(islandData.get("id") + "#" + islandData.get("serverNumber")) && !this.imagesTexture.containsKey(islandData.get("id") + "#" + islandData.get("serverNumber"))) {
                                if (!base64ImagesByIslandId.get(islandData.get("id") + "#" + islandData.get("serverNumber")).isEmpty()) {
                                    BufferedImage image = IslandListGui.decodeToImage(base64ImagesByIslandId.get(islandData.get("id") + "#" + islandData.get("serverNumber")));
                                    this.imagesTexture.put(islandData.get("id") + "#" + islandData.get("serverNumber"), new DynamicTexture(image));
                                } else {
                                    this.imagesTexture.put(islandData.get("id") + "#" + islandData.get("serverNumber"), this.defaultImage);
                                }
                            }
                        }
                        hoveredButton = false;
                        ClientEventHandler.STYLE.bindTexture("island_list");
                        ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 50, 43, 222, 38, 512.0f, 512.0f, false);
                        if (this.imagesTexture.containsKey(islandData.get("id") + "#" + islandData.get("serverNumber")) || islandData.get("image").isEmpty()) {
                            GL11.glBindTexture((int)3553, (int)(this.imagesTexture.containsKey(islandData.get("id") + "#" + islandData.get("serverNumber")) ? this.imagesTexture.get(islandData.get("id") + "#" + islandData.get("serverNumber")).func_110552_b() : this.defaultImage.func_110552_b()));
                            ModernGui.drawScaledCustomSizeModalRect(offsetX + 7, offsetY.intValue() + 3, 0.0f, 0.0f, 156, 156, 30, 30, 156.0f, 156.0f, false);
                            ClientEventHandler.STYLE.bindTexture("island_list");
                            GL11.glEnable((int)3042);
                            GL11.glDisable((int)2929);
                            GL11.glDepthMask((boolean)false);
                            GL11.glBlendFunc((int)770, (int)771);
                            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                            GL11.glDisable((int)3008);
                            ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 6, offsetY.intValue() + 2, 119, 250, 32, 32, 512.0f, 512.0f, false);
                            GL11.glDisable((int)3042);
                            GL11.glEnable((int)2929);
                            GL11.glDepthMask((boolean)true);
                            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                            GL11.glEnable((int)3008);
                        }
                        int playerOnline = Integer.parseInt(islandData.get("playersOnline"));
                        int voteDiff = Integer.parseInt(islandData.get("voteDiff"));
                        int visits = Integer.parseInt(islandData.get("visit"));
                        if (this.selectedFilter.equals("connected") && playerOnline <= 3 || this.selectedFilter.equals("vote") && voteDiff <= 10 || this.selectedFilter.equals("visit") && visits <= 100) {
                            ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 7, offsetY.intValue() + 25, 121, 283, 30, 8, 512.0f, 512.0f, false);
                        } else if (this.selectedFilter.equals("connected") && playerOnline <= 6 || this.selectedFilter.equals("vote") && voteDiff <= 50 || this.selectedFilter.equals("visit") && visits <= 500) {
                            ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 7, offsetY.intValue() + 25, 121, 292, 30, 8, 512.0f, 512.0f, false);
                        } else {
                            ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 7, offsetY.intValue() + 25, 121, 301, 30, 8, 512.0f, 512.0f, false);
                        }
                        if (this.selectedFilter.equals("connected")) {
                            this.drawScaledString(islandData.get("playersOnline"), offsetX + 22, offsetY.intValue() + 27, 0xFFFFFF, 0.7f, true, false);
                        } else if (this.selectedFilter.equals("vote")) {
                            this.drawScaledString(islandData.get("voteDiff"), offsetX + 22, offsetY.intValue() + 27, 0xFFFFFF, 0.7f, true, false);
                        } else if (this.selectedFilter.equals("visit")) {
                            this.drawScaledString(islandData.get("visit"), offsetX + 22, offsetY.intValue() + 27, 0xFFFFFF, 0.7f, true, false);
                        }
                        this.drawScaledString(islandData.get("name"), offsetX + 43, offsetY.intValue() + 8, 0xFFFFFF, 1.0f, false, false);
                        this.drawScaledString(I18n.func_135053_a((String)"island.list.created_by") + " " + islandData.get("creator"), offsetX + 43, offsetY.intValue() + 20, 0xB4B4B4, 0.9f, false, false);
                        if (islandData.get("creator").equals(this.player.getDisplayName())) {
                            ++this.currentPlayerIslandCount;
                        }
                        ClientEventHandler.STYLE.bindTexture("island_list");
                        int delay = 10000;
                        boolean bl2 = System.currentTimeMillis() - Long.parseLong(islandData.get("creationTime")) < (long)delay || System.currentTimeMillis() - Long.parseLong(islandData.get("regenTime") != null ? islandData.get("regenTime") : "0") < (long)delay ? true : (haveToWait = false);
                        if (mouseX > this.guiLeft + 50 && mouseX < this.guiLeft + 50 + 222 && mouseY > this.guiTop + 43 && mouseY < this.guiTop + 43 + 158 && mouseX > offsetX + 186 && mouseX < offsetX + 186 + 32 && mouseY > offsetY.intValue() + 11 && mouseY < offsetY.intValue() + 11 + 15) {
                            if (!haveToWait) {
                                this.hoveredIsland = islandData;
                            }
                            hoveredButton = true;
                            if (haveToWait) {
                                tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"island.list.tooltip.waiting"));
                            }
                        }
                        if (islandData.get("password").equals("true") && islandData.get("directJoin").equals("false")) {
                            ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 186, offsetY.intValue() + 11, 59, !hoveredButton && !haveToWait ? 250 : 298, 32, 15, 512.0f, 512.0f, false);
                        } else {
                            ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 186, offsetY.intValue() + 11, 59, !hoveredButton && !haveToWait ? 266 : 314, 32, 15, 512.0f, 512.0f, false);
                        }
                        ClientEventHandler.STYLE.bindTexture("island_list");
                        ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 170, offsetY.intValue() + 13, 47, 251, 10, 11, 512.0f, 512.0f, false);
                        if (mouseX > this.guiLeft + 50 && mouseX < this.guiLeft + 50 + 222 && mouseY > this.guiTop + 43 && mouseY < this.guiTop + 43 + 158 && mouseX > offsetX + 170 && mouseX < offsetX + 170 + 10 && mouseY > offsetY.intValue() + 13 && mouseY < offsetY.intValue() + 13 + 11) {
                            this.hoveredInfoIsland = islandData;
                        }
                        ++index;
                        continue;
                    }
                    ++filterOffsetY;
                }
                this.countIslandListed = index + 1;
                GUIUtils.endGLScissor();
                ClientEventHandler.STYLE.bindTexture("island_list");
                this.drawScaledString(I18n.func_135053_a((String)("island.list.filter." + this.selectedFilter)), this.guiLeft + 49 + 6, this.guiTop + 26, 0xFFFFFF, 1.0f, false, false);
                ClientEventHandler.STYLE.bindTexture("island_list");
                if (this.filterExpanded) {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 49, this.guiTop + 37, 153, 250, 104, 65, 512.0f, 512.0f, false);
                    if (this.filters.size() > 0) {
                        for (int i = 0; i < this.filters.size(); ++i) {
                            int offsetX = this.guiLeft + 49;
                            int offsetY = this.guiTop + 37 + i * 16;
                            ClientEventHandler.STYLE.bindTexture("island_list");
                            if (mouseX >= offsetX && mouseX <= offsetX + 104 && mouseY >= offsetY && mouseY <= offsetY + 16) {
                                this.hoveredFilter = this.filters.get(i);
                            }
                            this.drawScaledString(I18n.func_135053_a((String)("island.list.filter." + this.filters.get(i))), offsetX + 6, offsetY + 5, 0xFFFFFF, 1.0f, false, false);
                        }
                    }
                }
                if (this.hoveredInfoIsland != null && !this.hoveredInfoIsland.isEmpty()) {
                    tooltipToDraw = Arrays.asList("\u00a77" + this.hoveredInfoIsland.get("description"), "", "\u00a79" + I18n.func_135053_a((String)"island.list.tooltip.creation") + " \u00a77" + this.hoveredInfoIsland.get("creationDate"), "\u00a79" + I18n.func_135053_a((String)"island.list.tooltip.visits") + " \u00a77" + this.hoveredInfoIsland.get("visit"), "\u00a79" + I18n.func_135053_a((String)"island.list.tooltip.votes") + " \u00a7a" + this.hoveredInfoIsland.get("voteUp") + "\u00a77/\u00a7c" + this.hoveredInfoIsland.get("voteDown"), "\u00a79" + I18n.func_135053_a((String)"island.list.tooltip.server") + " \u00a77" + this.hoveredInfoIsland.get("serverNumber"));
                }
                this.scrollBar.draw(mouseX, mouseY);
            }
            ClientEventHandler.STYLE.bindTexture("island_list");
            if (mouseX >= this.guiLeft + 49 && mouseX <= this.guiLeft + 49 + 73 && mouseY >= this.guiTop + 212 && mouseY <= this.guiTop + 212 + 15) {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 49, this.guiTop + 212, 0, 364, 73, 15, 512.0f, 512.0f, false);
            } else {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 49, this.guiTop + 212, 0, 349, 73, 15, 512.0f, 512.0f, false);
            }
            if (!onlyMyIslands) {
                this.drawScaledString(I18n.func_135053_a((String)"island.list.my_islands"), this.guiLeft + 85, this.guiTop + 216, 0xFFFFFF, 1.0f, true, false);
            } else {
                this.drawScaledString(I18n.func_135053_a((String)"island.list.all_islands"), this.guiLeft + 85, this.guiTop + 216, 0xFFFFFF, 1.0f, true, false);
            }
            if (!serverNumber.equals("0")) {
                ClientEventHandler.STYLE.bindTexture("island_list");
                if (!this.canPlayerCreateIsland() || mouseX >= this.guiLeft + 175 && mouseX <= this.guiLeft + 175 + 103 && mouseY >= this.guiTop + 206 && mouseY <= this.guiTop + 206 + 27) {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 175, this.guiTop + 206, 153, 319, 103, 27, 512.0f, 512.0f, false);
                } else {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 175, this.guiTop + 206, 153, 346, 103, 27, 512.0f, 512.0f, false);
                }
                this.drawScaledString(I18n.func_135053_a((String)"island.list.create_island"), this.guiLeft + 232, this.guiTop + 215, 0xFFFFFF, 1.0f, true, false);
                if (!this.canPlayerCreateIsland() && mouseX >= this.guiLeft + 175 && mouseX <= this.guiLeft + 175 + 103 && mouseY >= this.guiTop + 206 && mouseY <= this.guiTop + 206 + 27) {
                    if (isPremium) {
                        this.drawHoveringText(Arrays.asList(I18n.func_135053_a((String)"island.create.disable.premium")), mouseX, mouseY, this.field_73886_k);
                    } else {
                        this.drawHoveringText(Arrays.asList(I18n.func_135053_a((String)"island.create.disable.non_premium_1"), I18n.func_135053_a((String)"island.create.disable.non_premium_2"), I18n.func_135053_a((String)"island.create.disable.non_premium_3")), mouseX, mouseY, this.field_73886_k);
                    }
                }
            }
        }
        if (!tooltipToDraw.isEmpty()) {
            this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
        }
        super.func_73863_a(mouseX, mouseY, par3);
        GL11.glEnable((int)2896);
        RenderHelper.func_74519_b();
    }

    public boolean canPlayerCreateIsland() {
        return (isPremium || this.currentPlayerIslandCount < 1) && (!isPremium || this.currentPlayerIslandCount < 5);
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

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (mouseX > this.guiLeft + 278 && mouseX < this.guiLeft + 278 + 9 && mouseY > this.guiTop - 8 && mouseY < this.guiTop - 8 + 10) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a(null);
            } else if (this.hoveredIsland != null && !this.hoveredIsland.isEmpty() && mouseX > this.guiLeft + 50 && mouseX < this.guiLeft + 50 + 222 && mouseY > this.guiTop + 43 && mouseY < this.guiTop + 43 + 158) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                if (this.hoveredIsland.get("password").equals("true") && !this.hoveredIsland.get("creator").equalsIgnoreCase(Minecraft.func_71410_x().field_71439_g.field_71092_bJ) && !this.hoveredIsland.get("members").contains("#" + Minecraft.func_71410_x().field_71439_g.getDisplayName() + "#")) {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new IslandPasswordGui(this.player, this, this.hoveredIsland.get("id"), this.hoveredIsland.get("passwordValue"), this.hoveredIsland.get("serverNumber")));
                } else {
                    Minecraft.func_71410_x().func_71373_a(null);
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IslandTPPacket(this.hoveredIsland.get("id"), this.hoveredIsland.get("serverNumber"))));
                }
            } else if (!this.helpOpened && mouseX > this.guiLeft + 292 && mouseX < this.guiLeft + 292 + 23 && mouseY > this.guiTop + 157 && mouseY < this.guiTop + 157 + 45) {
                this.helpOpened = true;
            } else if (this.helpOpened && mouseX > this.guiLeft + 292 + 107 && mouseX < this.guiLeft + 292 + 107 + 23 && mouseY > this.guiTop + 157 && mouseY < this.guiTop + 157 + 45) {
                this.helpOpened = false;
            } else if (mouseX > this.guiLeft + 136 && mouseX < this.guiLeft + 136 + 16 && mouseY > this.guiTop + 22 && mouseY < this.guiTop + 22 + 15) {
                this.filterExpanded = !this.filterExpanded;
            } else if (!this.hoveredFilter.isEmpty() && mouseX > this.guiLeft + 49 && mouseX < this.guiLeft + 49 + 104 && mouseY > this.guiTop + 37 && mouseY < this.guiTop + 37 + 65) {
                this.selectedFilter = this.hoveredFilter;
                this.filterExpanded = false;
                resetList = true;
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IslandListDataPacket(this.selectedFilter, onlyMyIslands)));
                this.scrollBar.reset();
            } else if (!serverNumber.equals("0") && this.canPlayerCreateIsland() && mouseX >= this.guiLeft + 175 && mouseX <= this.guiLeft + 175 + 103 && mouseY >= this.guiTop + 206 && mouseY <= this.guiTop + 206 + 27) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new IslandCreateGui(isPremium, serverNumber));
            } else if (mouseX >= this.guiLeft + 49 && mouseX <= this.guiLeft + 49 + 73 && mouseY >= this.guiTop + 212 && mouseY <= this.guiTop + 212 + 15) {
                onlyMyIslands = !onlyMyIslands;
                resetList = true;
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IslandListDataPacket(this.selectedFilter, onlyMyIslands)));
                this.scrollBar.reset();
            }
        }
        this.islandSearch.func_73793_a(mouseX, mouseY, mouseButton);
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    private float getSlide() {
        return this.countIslandListed > 4 ? (float)(-(this.countIslandListed - 4) * 38) * this.scrollBar.getSliderValue() : 0.0f;
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        if (this.islandSearch.func_73802_a(typedChar, keyCode)) {
            this.searchText = this.islandSearch.func_73781_b();
            this.scrollBar.reset();
        }
        super.func_73869_a(typedChar, keyCode);
    }

    public void drawScaledString(String text, int x, int y, int color, float scale, boolean centered, boolean shadow) {
        GL11.glPushMatrix();
        GL11.glScalef((float)scale, (float)scale, (float)scale);
        float newX = x;
        if (centered) {
            newX = (float)x - (float)this.field_73886_k.func_78256_a(text) * scale / 2.0f;
        }
        if (shadow) {
            this.field_73886_k.func_85187_a(text, (int)(newX / scale), (int)((float)(y + 1) / scale), (color & 0xFCFCFC) >> 2 | color & 0xFF000000, false);
        }
        this.field_73886_k.func_85187_a(text, (int)(newX / scale), (int)((float)y / scale), color, false);
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public boolean func_73868_f() {
        return false;
    }

    public static BufferedImage decodeToImage(String imageString) {
        BufferedImage image = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
}

