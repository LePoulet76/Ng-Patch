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
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

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
import net.ilexiconn.nationsgui.forge.client.MinimapRenderer;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.TexturedCenteredButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseBankGUI;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseContractForm_Default_Gui;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseContractForm_Loan_Gui;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseContractForm_Pvp_Gui;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseContractForm_Repair_Gui;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseContractForm_Trader_Gui;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseLeaveConfirmGui;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterprisePermGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ProfilGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseMainDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterprisePlayerDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.MinimapRequestPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

@SideOnly(value=Side.CLIENT)
public class EnterpriseGui
extends GuiScreen {
    public static final List<GuiScreenTab> TABS = new ArrayList<GuiScreenTab>();
    public static HashMap<String, HashMap<String, Object>> playerTooltip;
    public static HashMap<String, Object> enterpriseInfos;
    public static boolean loaded;
    private String enterpriseName;
    private RenderItem itemRenderer = new RenderItem();
    protected int xSize = 400;
    protected int ySize = 250;
    protected String hoveredPlayer = "";
    private boolean showServices = false;
    private int guiLeft;
    private int guiTop;
    private GuiButton actionButton;
    private GuiButton contractButton;
    private GuiButton leaveButton;
    private GuiScrollBarFaction scrollBarOnline;
    private GuiScrollBarFaction scrollBarOffline;
    public MinimapRenderer minimapRenderer = new MinimapRenderer(6, 6);
    public static boolean mapLoaded;
    private DynamicTexture flagTexture;
    public static Long lastContractDemand;
    public static List<String> availableTypes;

    public EnterpriseGui(String enterpriseName) {
        EnterpriseGui.initTabs();
        this.enterpriseName = enterpriseName;
        mapLoaded = false;
        loaded = false;
        enterpriseInfos = null;
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        this.scrollBarOnline = new GuiScrollBarFaction(this.guiLeft + 248, this.guiTop + 145, 80);
        this.scrollBarOffline = new GuiScrollBarFaction(this.guiLeft + 377, this.guiTop + 145, 80);
        playerTooltip = new HashMap();
        if (enterpriseInfos == null || enterpriseInfos.size() == 0 || !enterpriseInfos.get("name").equals(this.enterpriseName)) {
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new EnterpriseMainDataPacket(this.enterpriseName)));
        } else {
            loaded = true;
            mapLoaded = false;
        }
        this.actionButton = new GuiButton(0, this.guiLeft + 10, this.guiTop + 165, 100, 20, I18n.func_135053_a((String)"enterprise.home.button.services"));
        this.contractButton = new GuiButton(1, this.guiLeft + 10, this.guiTop + 188, 100, 20, I18n.func_135053_a((String)"enterprise.home.button.contract"));
        this.leaveButton = new TexturedCenteredButtonGUI(2, this.guiLeft + 10, this.guiTop + 220, 100, 20, "faction_btn", 0, 0, I18n.func_135053_a((String)"enterprise.home.button.leave"));
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        if (loaded && enterpriseInfos != null && this.flagTexture == null && enterpriseInfos != null && enterpriseInfos.get("flagImage") != null && !((String)enterpriseInfos.get("flagImage")).isEmpty()) {
            BufferedImage image = EnterpriseGui.decodeToImage((String)enterpriseInfos.get("flagImage"));
            this.flagTexture = new DynamicTexture(image);
        }
        this.func_73873_v_();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("enterprise_main");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        List<String> tooltipToDraw = null;
        String playerTooltipToDraw = "";
        if (loaded && enterpriseInfos != null && enterpriseInfos.size() > 0) {
            int i;
            this.actionButton.field_73744_e = this.showServices ? I18n.func_135053_a((String)"enterprise.home.button.infos") : I18n.func_135053_a((String)"enterprise.home.button.services");
            this.actionButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
            if (!(enterpriseInfos.get("type").equals("farm") || enterpriseInfos.get("type").equals("electric") || enterpriseInfos.get("type").equals("petrol") || enterpriseInfos.get("type").equals("bet") || enterpriseInfos.get("type").equals("casino") || enterpriseInfos.get("type").equals("realestate") || ((Boolean)enterpriseInfos.get("isInEnterprise")).booleanValue() && !enterpriseInfos.get("type").equals("trader") && !Minecraft.func_71410_x().field_71439_g.field_71092_bJ.equalsIgnoreCase("iBalix") || !((Boolean)enterpriseInfos.get("canRequestContract")).booleanValue())) {
                this.contractButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
                if (!((Boolean)enterpriseInfos.get("isOpen")).booleanValue()) {
                    this.contractButton.field_73742_g = false;
                    if (mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 188 && mouseY <= this.guiTop + 188 + 20) {
                        tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"enterprise.home.button.contract.closed").split("##"));
                    }
                } else if (System.currentTimeMillis() - lastContractDemand < 300000L && !Minecraft.func_71410_x().field_71439_g.field_71092_bJ.equalsIgnoreCase("iBalix")) {
                    this.contractButton.field_73742_g = false;
                    if (mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 188 && mouseY <= this.guiTop + 188 + 20) {
                        tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"enterprise.home.button.contract.delay").split("##"));
                    }
                }
            } else {
                this.contractButton.field_73742_g = false;
            }
            if (!enterpriseInfos.get("playerRole").equals("leader") && ((Boolean)enterpriseInfos.get("isInEnterprise")).booleanValue()) {
                this.leaveButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
            }
            ClientEventHandler.STYLE.bindTexture("enterprise_main");
            if (!mapLoaded && !enterpriseInfos.get("position").equals("")) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new MinimapRequestPacket(Integer.parseInt(((String)enterpriseInfos.get("position")).split("##")[0]), Integer.parseInt(((String)enterpriseInfos.get("position")).split("##")[1]), 6, 6)));
                mapLoaded = true;
            }
            if (mouseX > this.guiLeft + 385 && mouseX < this.guiLeft + 385 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10) {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 385, this.guiTop - 6, 138, 261, 9, 10, 512.0f, 512.0f, false);
            } else {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 385, this.guiTop - 6, 138, 251, 9, 10, 512.0f, 512.0f, false);
            }
            if (!this.showServices) {
                this.drawScaledString(I18n.func_135053_a((String)"enterprise.tab.home"), this.guiLeft + 131, this.guiTop + 16, 0x191919, 1.4f, false, false);
            } else {
                this.drawScaledString(I18n.func_135053_a((String)"enterprise.tab.home_services"), this.guiLeft + 131, this.guiTop + 16, 0x191919, 1.4f, false, false);
            }
            if (((String)enterpriseInfos.get("name")).length() <= 9) {
                this.drawScaledString((String)enterpriseInfos.get("name"), this.guiLeft + 60, this.guiTop + 25, 0xFFFFFF, 1.8f, true, true);
            } else {
                this.drawScaledString((String)enterpriseInfos.get("name"), this.guiLeft + 60, this.guiTop + 25, 0xFFFFFF, 1.0f, true, true);
            }
            this.drawScaledString(I18n.func_135053_a((String)"faction.common.age_1") + " " + enterpriseInfos.get("age") + " " + I18n.func_135053_a((String)"faction.common.age_2"), this.guiLeft + 60, this.guiTop + 43, 0xB4B4B4, 0.65f, true, false);
            ClientEventHandler.STYLE.bindTexture("enterprise_main");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 12, this.guiTop + 61, EnterpriseGui.getTypeOffsetX((String)enterpriseInfos.get("type")), 442, 16, 16, 512.0f, 512.0f, false);
            this.drawScaledString(I18n.func_135053_a((String)("enterprise.type." + ((String)enterpriseInfos.get("type")).toLowerCase())), this.guiLeft + 32, this.guiTop + 66, 0xFFFFFF, 1.0f, false, false);
            if (!this.showServices) {
                ResourceLocation resourceLocation;
                Float offsetY;
                if (this.flagTexture != null) {
                    GL11.glBindTexture((int)3553, (int)this.flagTexture.func_110552_b());
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 337, this.guiTop + 47, 0.0f, 0.0f, 150, 150, 22, 22, 150.0f, 150.0f, false);
                }
                String[] descriptionWords = ((String)enterpriseInfos.get("description")).replaceAll("\u00a7[0-9a-z]{1}", "").split(" ");
                Object line = "";
                int lineNumber = 0;
                for (String descWord : descriptionWords) {
                    if (this.field_73886_k.func_78256_a((String)line + descWord) <= 160) {
                        if (!((String)line).equals("")) {
                            line = (String)line + " ";
                        }
                        line = (String)line + descWord;
                        continue;
                    }
                    if (lineNumber == 0) {
                        line = "\u00a7o\"" + (String)line;
                    }
                    this.drawScaledString((String)line, this.guiLeft + 220, this.guiTop + 40 + lineNumber * 10, 0xFFFFFF, 1.0f, true, true);
                    ++lineNumber;
                    line = descWord;
                }
                if (lineNumber == 0) {
                    line = "\u00a7o\"" + (String)line;
                }
                this.drawScaledString((String)line + "\"", this.guiLeft + 220, this.guiTop + 40 + lineNumber * 10, 0xFFFFFF, 1.0f, true, true);
                this.drawScaledString((String)enterpriseInfos.get("turnover"), this.guiLeft + 151, this.guiTop + 114, 0xB4B4B4, 0.9f, false, false);
                if (mouseX >= this.guiLeft + 130 && mouseX <= this.guiLeft + 150 && mouseY >= this.guiTop + 109 && mouseY <= this.guiTop + 123) {
                    tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"enterprise.infos.turnover"));
                }
                this.drawScaledString((String)enterpriseInfos.get("contractsDone"), this.guiLeft + 228, this.guiTop + 114, 0xB4B4B4, 0.9f, false, false);
                if (mouseX >= this.guiLeft + 208 && mouseX <= this.guiLeft + 228 && mouseY >= this.guiTop + 109 && mouseY <= this.guiTop + 123) {
                    tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"enterprise.infos.contractsDone"));
                }
                this.drawScaledString((String)enterpriseInfos.get("contractsSuccess"), this.guiLeft + 291, this.guiTop + 114, 0xB4B4B4, 0.9f, false, false);
                if (mouseX >= this.guiLeft + 271 && mouseX <= this.guiLeft + 291 && mouseY >= this.guiTop + 109 && mouseY <= this.guiTop + 123) {
                    tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"enterprise.infos.contractsSuccess"));
                }
                this.drawScaledString((String)enterpriseInfos.get("disputes"), this.guiLeft + 355, this.guiTop + 114, 0xB4B4B4, 0.9f, false, false);
                if (mouseX >= this.guiLeft + 335 && mouseX <= this.guiLeft + 355 && mouseY >= this.guiTop + 109 && mouseY <= this.guiTop + 123) {
                    tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"enterprise.infos.disputes"));
                }
                ClientEventHandler.STYLE.bindTexture("enterprise_main");
                this.hoveredPlayer = "";
                this.drawScaledString(I18n.func_135053_a((String)"enterprise.home.players_online") + " (" + ((ArrayList)enterpriseInfos.get("players_online")).size() + ")", this.guiLeft + 131, this.guiTop + 133, 0x191919, 0.85f, false, false);
                if (((ArrayList)enterpriseInfos.get("players_online")).size() > 0) {
                    GUIUtils.startGLScissor(this.guiLeft + 132, this.guiTop + 142, 116, 86);
                    for (int i2 = 0; i2 < ((ArrayList)enterpriseInfos.get("players_online")).size(); ++i2) {
                        String playerName = ((String)((ArrayList)enterpriseInfos.get("players_online")).get(i2)).split("#")[1];
                        String playerNamePrefix = "";
                        if (playerName.split(" ").length > 1) {
                            playerNamePrefix = playerName.split(" ")[0];
                            playerName = playerName.split(" ")[1];
                        }
                        int offsetX = this.guiLeft + 132;
                        offsetY = Float.valueOf((float)(this.guiTop + 142 + i2 * 20) + this.getSlideOnline());
                        if (mouseX > offsetX && mouseX < offsetX + 116 && (float)mouseY > offsetY.floatValue() && (float)mouseY < offsetY.floatValue() + 20.0f) {
                            this.hoveredPlayer = playerName;
                        }
                        ClientEventHandler.STYLE.bindTexture("enterprise_main");
                        ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 132, 142, 116, 20, 512.0f, 512.0f, false);
                        try {
                            resourceLocation = AbstractClientPlayer.field_110314_b;
                            resourceLocation = AbstractClientPlayer.func_110311_f((String)playerName);
                            AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)playerName);
                            Minecraft.func_71410_x().field_71446_o.func_110577_a(resourceLocation);
                            this.field_73882_e.func_110434_K().func_110577_a(resourceLocation);
                            GUIUtils.drawScaledCustomSizeModalRect(offsetX + 6 + 10, offsetY.intValue() + 4 + 10, 8.0f, 16.0f, 8, -8, -10, -10, 64.0f, 64.0f);
                            GUIUtils.drawScaledCustomSizeModalRect(offsetX + 6 + 10, offsetY.intValue() + 4 + 10, 40.0f, 16.0f, 8, -8, -10, -10, 64.0f, 64.0f);
                        }
                        catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        this.drawScaledString(playerNamePrefix + " " + playerName, offsetX + 20, offsetY.intValue() + 6, 0xB4B4B4, 0.8f, false, true);
                        ClientEventHandler.STYLE.bindTexture("enterprise_main");
                        ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 104, offsetY.intValue() + 5, 148, 250, 10, 11, 512.0f, 512.0f, false);
                        if (mouseX < offsetX + 104 || mouseX > offsetX + 104 + 10 || !((float)mouseY >= offsetY.floatValue() + 5.0f) || !((float)mouseY <= offsetY.floatValue() + 5.0f + 11.0f)) continue;
                        if (playerTooltip.containsKey(this.enterpriseName + "##" + playerName)) {
                            playerTooltipToDraw = playerName;
                            continue;
                        }
                        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new EnterprisePlayerDataPacket(this.enterpriseName, playerName)));
                    }
                    GUIUtils.endGLScissor();
                }
                this.drawScaledString(I18n.func_135053_a((String)"enterprise.home.players_offline") + " (" + ((ArrayList)enterpriseInfos.get("players_offline")).size() + ")", this.guiLeft + 260, this.guiTop + 133, 0x191919, 0.85f, false, false);
                if (((ArrayList)enterpriseInfos.get("players_offline")).size() > 0) {
                    GUIUtils.startGLScissor(this.guiLeft + 261, this.guiTop + 142, 116, 86);
                    for (int i3 = 0; i3 < ((ArrayList)enterpriseInfos.get("players_offline")).size(); ++i3) {
                        String playerName = ((String)((ArrayList)enterpriseInfos.get("players_offline")).get(i3)).split("#")[1];
                        String playerNamePrefix = "";
                        if (playerName.split(" ").length > 1) {
                            playerNamePrefix = playerName.split(" ")[0];
                            playerName = playerName.split(" ")[1];
                        }
                        int offsetX = this.guiLeft + 261;
                        offsetY = Float.valueOf((float)(this.guiTop + 142 + i3 * 20) + this.getSlideOffline());
                        if (mouseX > offsetX && mouseX < offsetX + 116 && (float)mouseY > offsetY.floatValue() && (float)mouseY < offsetY.floatValue() + 20.0f) {
                            this.hoveredPlayer = playerName;
                        }
                        ClientEventHandler.STYLE.bindTexture("enterprise_main");
                        ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 261, 142, 116, 20, 512.0f, 512.0f, false);
                        try {
                            resourceLocation = AbstractClientPlayer.field_110314_b;
                            resourceLocation = AbstractClientPlayer.func_110311_f((String)playerName);
                            AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)playerName);
                            Minecraft.func_71410_x().field_71446_o.func_110577_a(resourceLocation);
                            this.field_73882_e.func_110434_K().func_110577_a(resourceLocation);
                            GUIUtils.drawScaledCustomSizeModalRect(offsetX + 6 + 10, offsetY.intValue() + 4 + 10, 8.0f, 16.0f, 8, -8, -10, -10, 64.0f, 64.0f);
                            GUIUtils.drawScaledCustomSizeModalRect(offsetX + 6 + 10, offsetY.intValue() + 4 + 10, 40.0f, 16.0f, 8, -8, -10, -10, 64.0f, 64.0f);
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        this.drawScaledString(playerNamePrefix + " " + playerName, offsetX + 20, offsetY.intValue() + 6, 0xB4B4B4, 0.8f, false, true);
                        ClientEventHandler.STYLE.bindTexture("enterprise_main");
                        ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 104, offsetY.intValue() + 5, 148, 250, 10, 11, 512.0f, 512.0f, false);
                        if (mouseX < offsetX + 104 || mouseX > offsetX + 104 + 10 || !((float)mouseY >= offsetY.floatValue() + 5.0f) || !((float)mouseY <= offsetY.floatValue() + 5.0f + 11.0f)) continue;
                        if (playerTooltip.containsKey(this.enterpriseName + "##" + playerName)) {
                            playerTooltipToDraw = playerName;
                            continue;
                        }
                        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new EnterprisePlayerDataPacket(this.enterpriseName, playerName)));
                    }
                    GUIUtils.endGLScissor();
                }
                if (mouseX > this.guiLeft + 130 && mouseX < this.guiLeft + 255 && mouseY > this.guiTop + 139 && mouseY < this.guiTop + 229) {
                    this.scrollBarOnline.draw(mouseX, mouseY);
                }
                if (mouseX > this.guiLeft + 259 && mouseX < this.guiLeft + 384 && mouseY > this.guiTop + 139 && mouseY < this.guiTop + 229) {
                    this.scrollBarOffline.draw(mouseX, mouseY);
                }
            } else {
                ModernGui.drawNGBlackSquare(this.guiLeft + 130, this.guiTop + 30, 254, 200);
                int index = 0;
                for (String serviceLine : ((String)enterpriseInfos.get("services")).split("##")) {
                    this.drawScaledString(serviceLine.replace("&", "\u00a7"), this.guiLeft + 133, this.guiTop + 38 + index * 10, 0xFFFFFF, 1.0f, false, false);
                    ++index;
                }
            }
            if (tooltipToDraw != null) {
                this.drawTooltip(tooltipToDraw, mouseX, mouseY);
            }
            if (!playerTooltipToDraw.isEmpty()) {
                this.drawPlayerTooltip(playerTooltipToDraw, mouseX, mouseY);
            }
            ClientEventHandler.STYLE.bindTexture("enterprise_main");
            for (i = 0; i < TABS.size(); ++i) {
                GuiScreenTab type = TABS.get(i);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                int x = EnterpriseGui.getTabIndex(TABS.get(i));
                if (((Object)((Object)this)).getClass() == type.getClassReferent()) {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23, this.guiTop + 20 + i * 31, 23, 250, 29, 31, 512.0f, 512.0f, false);
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23 + 4, this.guiTop + 20 + i * 31 + 5, x * 20, 301, 20, 20, 512.0f, 512.0f, false);
                    continue;
                }
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23, this.guiTop + 20 + i * 31, 0, 250, 23, 31, 512.0f, 512.0f, false);
                GL11.glBlendFunc((int)770, (int)771);
                GL11.glEnable((int)3042);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.75f);
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23 + 4, this.guiTop + 20 + i * 31 + 5, x * 20, 301, 20, 20, 512.0f, 512.0f, false);
                GL11.glDisable((int)3042);
            }
            if (mapLoaded) {
                GUIUtils.startGLScissor(this.guiLeft + 11, this.guiTop + 83, 180, 78);
                GL11.glDisable((int)2929);
                this.minimapRenderer.renderMap(this.guiLeft + 12, this.guiTop + 74, mouseX, mouseY, true);
                GL11.glEnable((int)2929);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                ClientEventHandler.STYLE.bindTexture("enterprise_main");
                ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(this.guiLeft + 11, this.guiTop + 83, 0, 362, 98, 78, 512.0f, 512.0f, false);
                GUIUtils.endGLScissor();
            }
            for (i = 0; i < TABS.size(); ++i) {
                int x = EnterpriseGui.getTabIndex(TABS.get(i));
                if (mouseX < this.guiLeft - 23 || mouseX > this.guiLeft - 23 + 29 || mouseY < this.guiTop + 20 + i * 31 || mouseY > this.guiTop + 20 + 30 + i * 31) continue;
                this.drawHoveringText(Arrays.asList(I18n.func_135053_a((String)("enterprise.tab." + x))), mouseX, mouseY, this.field_73886_k);
            }
            super.func_73863_a(mouseX, mouseY, par3);
            GL11.glEnable((int)2896);
            RenderHelper.func_74519_b();
        } else if (loaded) {
            Minecraft.func_71410_x().func_71373_a(null);
        }
    }

    private void drawPlayerTooltip(String playerName, int mouseX, int mouseY) {
        ArrayList<String> tooltipLines = this.getPlayerTooltip(playerName);
        this.drawHoveringText(tooltipLines, mouseX, mouseY, this.field_73886_k);
    }

    private void drawTooltip(List<String> lines, int mouseX, int mouseY) {
        this.drawHoveringText(lines, mouseX, mouseY, this.field_73886_k);
    }

    public ArrayList<String> getPlayerTooltip(String playerName) {
        HashMap<String, Object> playerInfo = playerTooltip.get(this.enterpriseName + "##" + playerName);
        ArrayList<String> tooltipLines = new ArrayList<String>();
        tooltipLines.add("\u00a77" + I18n.func_135053_a((String)"enterprise.label.job") + " : " + I18n.func_135053_a((String)("enterprise.role." + playerInfo.get("role"))));
        tooltipLines.add("\u00a78----------------------");
        if (playerInfo.get("offline_days").equals("online")) {
            tooltipLines.add("\u00a7a" + I18n.func_135053_a((String)"enterprise.home.tooltip.online"));
        } else {
            tooltipLines.add("\u00a77" + I18n.func_135053_a((String)"enterprise.home.tooltip.offline_since_1") + " " + playerInfo.get("offline_days") + " " + I18n.func_135053_a((String)"enterprise.home.tooltip.offline_since_2"));
        }
        return tooltipLines;
    }

    private float getSlideOnline() {
        return ((ArrayList)enterpriseInfos.get("players_online")).size() > 4 ? (float)(-(((ArrayList)enterpriseInfos.get("players_online")).size() - 4) * 20) * this.scrollBarOnline.getSliderValue() : 0.0f;
    }

    private float getSlideOffline() {
        return ((ArrayList)enterpriseInfos.get("players_offline")).size() > 4 ? (float)(-(((ArrayList)enterpriseInfos.get("players_offline")).size() - 4) * 20) * this.scrollBarOffline.getSliderValue() : 0.0f;
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            for (int i = 0; i < TABS.size(); ++i) {
                GuiScreenTab type = TABS.get(i);
                if (mouseX < this.guiLeft - 20 || mouseX > this.guiLeft + 3 || mouseY < this.guiTop + 20 + i * 31 || mouseY > this.guiTop + 50 + i * 31) continue;
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                if (((Object)((Object)this)).getClass() == type.getClassReferent()) continue;
                try {
                    type.call();
                    continue;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (mouseX > this.guiLeft + 385 && mouseX < this.guiLeft + 385 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a(null);
            } else if (this.actionButton.field_73742_g && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 165 && mouseY <= this.guiTop + 165 + 20) {
                boolean bl = this.showServices = !this.showServices;
            }
            if (this.contractButton.field_73742_g && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 188 && mouseY <= this.guiTop + 188 + 20) {
                if (enterpriseInfos.get("type").equals("pvp")) {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseContractForm_Pvp_Gui());
                } else if (enterpriseInfos.get("type").equals("trader")) {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseContractForm_Trader_Gui());
                } else if (enterpriseInfos.get("type").equals("repair")) {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseContractForm_Repair_Gui());
                } else if (enterpriseInfos.get("type").equals("loan")) {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseContractForm_Loan_Gui());
                } else {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseContractForm_Default_Gui());
                }
            } else if (enterpriseInfos != null && ((Boolean)enterpriseInfos.get("isInEnterprise")).booleanValue() && !enterpriseInfos.get("playerRole").equals("leader") && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 220 && mouseY <= this.guiTop + 220 + 20) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseLeaveConfirmGui(this));
            } else if (!this.hoveredPlayer.isEmpty() && mouseX > this.guiLeft + 130 && mouseX < this.guiLeft + 130 + 254 && mouseY > this.guiTop + 140 && mouseY < this.guiTop + 140 + 90) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new ProfilGui(this.hoveredPlayer, this.enterpriseName));
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    public static boolean hasPermission(String permName) {
        if (enterpriseInfos != null) {
            for (String permissionNode : (ArrayList)enterpriseInfos.get("permissions")) {
                if ((!permissionNode.contains(permName) || !permissionNode.contains((String)enterpriseInfos.get("playerRole"))) && (!enterpriseInfos.containsKey("isPlayerOp") || !((Boolean)enterpriseInfos.get("isPlayerOp")).booleanValue())) continue;
                return true;
            }
        }
        return false;
    }

    public static void initTabs() {
        TABS.clear();
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return EnterpriseGui.class;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseGui((String)enterpriseInfos.get("name")));
            }
        });
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return EnterpriseBankGUI.class;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseBankGUI());
            }
        });
        TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return EnterprisePermGUI.class;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterprisePermGUI());
            }
        });
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

    public static int getTypeOffsetX(String type) {
        if (availableTypes.contains(type)) {
            if (availableTypes.contains("all")) {
                return (availableTypes.indexOf(type) - 1) * 16;
            }
            return availableTypes.indexOf(type) * 16;
        }
        return 0;
    }

    public static int getTabIndex(GuiScreenTab inventoryTab) {
        String classReferent = inventoryTab.getClassReferent().toString();
        if (classReferent.contains("EnterpriseGui")) {
            return 0;
        }
        if (classReferent.contains("EnterpriseBankGUI")) {
            return 1;
        }
        if (classReferent.contains("EnterprisePermGUI")) {
            return 2;
        }
        if (classReferent.contains("EnterpriseContractGUI")) {
            return 3;
        }
        if (classReferent.contains("EnterpriseSettingsGUI")) {
            return 4;
        }
        if (classReferent.contains("EnterpriseParcelleGUI")) {
            return 5;
        }
        if (classReferent.contains("EnterpriseTraderGUI")) {
            return 6;
        }
        if (classReferent.contains("EnterpriseCasinoGUI")) {
            return 7;
        }
        if (classReferent.contains("EnterpriseBetGUI")) {
            return 8;
        }
        if (classReferent.contains("EnterpriseElectricGUI")) {
            return 9;
        }
        if (classReferent.contains("EnterprisePetrolGUI")) {
            return 10;
        }
        if (classReferent.contains("EnterpriseFarmGUI")) {
            return 11;
        }
        return 0;
    }

    static {
        loaded = false;
        mapLoaded = false;
        lastContractDemand = 0L;
        availableTypes = new ArrayList<String>();
    }
}

