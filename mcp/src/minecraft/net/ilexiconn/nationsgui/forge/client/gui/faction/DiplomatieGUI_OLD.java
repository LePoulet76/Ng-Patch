/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.TabbedFactionGUI_OLD;
import net.ilexiconn.nationsgui.forge.client.gui.TexturedCenteredButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.DiplomatieWishGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGui_OLD;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ProfilGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionDiplomatieDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

public class DiplomatieGUI_OLD
extends TabbedFactionGUI_OLD {
    public static boolean loaded = false;
    public static ArrayList<HashMap<String, Object>> factionDiplomatieInfos;
    private HashMap<String, Object> selectedDiplomatieInfos;
    public HashMap<String, String> availableActions = new HashMap();
    public HashMap<String, Object> hoveredRelation = new HashMap();
    public String hoveredPlayer = "";
    private String loadedFlag2 = "";
    boolean expanded = false;
    private GuiScrollBarFaction scrollBar;
    private GuiScrollBarFaction scrollBarPlayers;
    private DynamicTexture flagTexture1;
    private DynamicTexture flagTexture2;
    private GuiButton wishButton;
    private EntityPlayer entityPlayer;

    public DiplomatieGUI_OLD(EntityPlayer player) {
        super(player);
        this.entityPlayer = player;
        if (FactionGui_OLD.factionInfos != null && FactionGui_OLD.factionInfos.get("flagImage") != null && !((String)FactionGui_OLD.factionInfos.get("flagImage")).isEmpty()) {
            BufferedImage image = DiplomatieGUI_OLD.decodeToImage((String)FactionGui_OLD.factionInfos.get("flagImage"));
            this.flagTexture1 = new DynamicTexture(image);
        }
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        loaded = false;
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionDiplomatieDataPacket((String)FactionGui_OLD.factionInfos.get("name"))));
        this.scrollBar = new GuiScrollBarFaction(this.guiLeft + 376, this.guiTop + 54, 150);
        this.scrollBarPlayers = new GuiScrollBarFaction(this.guiLeft + 248, this.guiTop + 145, 80);
        this.wishButton = new TexturedCenteredButtonGUI(0, this.guiLeft + 10, this.guiTop + 165, 100, 30, "faction_btn", 0, 68, "");
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        if (loaded) {
            this.drawScaledString(I18n.func_135053_a((String)"faction.diplomatie.title"), this.guiLeft + 131, this.guiTop + 16, 0x191919, 1.4f, false, false);
            if (factionDiplomatieInfos != null && factionDiplomatieInfos.size() > 0) {
                Float offsetY;
                int offsetX;
                int i;
                if (this.selectedDiplomatieInfos == null) {
                    this.selectedDiplomatieInfos = factionDiplomatieInfos.get(0);
                }
                if (this.loadedFlag2.isEmpty() || !this.loadedFlag2.equalsIgnoreCase((String)this.selectedDiplomatieInfos.get("factionName"))) {
                    if (this.selectedDiplomatieInfos.get("flag") != null && !((String)this.selectedDiplomatieInfos.get("flag")).isEmpty()) {
                        BufferedImage image = DiplomatieGUI_OLD.decodeToImage((String)this.selectedDiplomatieInfos.get("flag"));
                        this.flagTexture2 = new DynamicTexture(image);
                    } else {
                        this.flagTexture2 = null;
                    }
                }
                this.drawScaledString("\u00a7f[" + I18n.func_135053_a((String)("faction.common." + this.selectedDiplomatieInfos.get("relationType"))) + "\u00a7f] " + this.selectedDiplomatieInfos.get("factionName"), this.guiLeft + 137, this.guiTop + 37, 0xFFFFFF, 1.0f, false, false);
                this.drawScaledString(I18n.func_135053_a((String)("faction.common." + this.selectedDiplomatieInfos.get("relationType"))), this.guiLeft + 255, this.guiTop + 80, 0xFFFFFF, 1.7f, true, false);
                if (!((String)this.selectedDiplomatieInfos.get("relationTime")).isEmpty() && !((String)this.selectedDiplomatieInfos.get("relationTime")).equals("null")) {
                    this.drawScaledString(this.convertDate((String)this.selectedDiplomatieInfos.get("relationTime")), this.guiLeft + 253, this.guiTop + 95, 0xFFFFFF, 0.8f, true, false);
                }
                if (this.flagTexture1 != null) {
                    GL11.glBindTexture((int)3553, (int)this.flagTexture1.func_110552_b());
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 163, this.guiTop + 77, 0.0f, 0.0f, 156, 78, 35, 20, 156.0f, 78.0f, false);
                }
                this.drawScaledString((String)FactionGui_OLD.factionInfos.get("name"), this.guiLeft + 180, this.guiTop + 103, 0xFFFFFF, 1.0f, true, false);
                if (this.flagTexture2 != null) {
                    GL11.glBindTexture((int)3553, (int)this.flagTexture2.func_110552_b());
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 313, this.guiTop + 77, 0.0f, 0.0f, 156, 78, 35, 20, 156.0f, 78.0f, false);
                }
                this.drawScaledString((String)this.selectedDiplomatieInfos.get("factionName"), this.guiLeft + 330, this.guiTop + 103, 0xFFFFFF, 1.0f, true, false);
                this.hoveredPlayer = "";
                ArrayList onlinePlayers = (ArrayList)this.selectedDiplomatieInfos.get("onlinePlayers");
                this.drawScaledString(I18n.func_135053_a((String)"faction.diplomatie.online_players") + " (" + onlinePlayers.size() + ")", this.guiLeft + 131, this.guiTop + 133, 0x191919, 0.8f, false, false);
                GUIUtils.startGLScissor(this.guiLeft + 131, this.guiTop + 142, 117, 88);
                for (i = 0; i < onlinePlayers.size(); ++i) {
                    offsetX = this.guiLeft + 131;
                    offsetY = Float.valueOf((float)(this.guiTop + 142 + i * 20) + this.getSlidePlayers());
                    ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 131, 142, 117, 20, 512.0f, 512.0f, false);
                    this.drawScaledString((String)onlinePlayers.get(i), offsetX + 6, offsetY.intValue() + 6, 0xFFFFFF, 1.0f, false, false);
                    if (mouseX < offsetX || mouseX > offsetX + 117 || !((float)mouseY >= offsetY.floatValue()) || !((float)mouseY <= offsetY.floatValue() + 20.0f)) continue;
                    this.hoveredPlayer = (String)onlinePlayers.get(i);
                }
                GUIUtils.endGLScissor();
                if (!this.expanded && mouseX >= this.guiLeft + 130 && mouseX <= this.guiLeft + 130 + 125 && mouseY >= this.guiTop + 140 && mouseY <= this.guiTop + 140 + 90) {
                    this.scrollBarPlayers.draw(mouseX, mouseY);
                }
                this.drawScaledString(I18n.func_135053_a((String)"faction.diplomatie.buttons.page_pays"), this.guiLeft + 321, this.guiTop + 146, 0xFFFFFF, 1.0f, true, false);
                if (this.expanded) {
                    ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 130, this.guiTop + 49, 258, 352, 254, 160, 512.0f, 512.0f, false);
                    this.hoveredRelation = new HashMap();
                    GUIUtils.startGLScissor(this.guiLeft + 131, this.guiTop + 50, 245, 158);
                    for (i = 0; i < factionDiplomatieInfos.size(); ++i) {
                        offsetX = this.guiLeft + 131;
                        offsetY = Float.valueOf((float)(this.guiTop + 50 + i * 20) + this.getSlide());
                        ClientEventHandler.STYLE.bindTexture("faction_diplomatie");
                        ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 259, 353, 245, 20, 512.0f, 512.0f, false);
                        this.drawScaledString("\u00a7f[" + I18n.func_135053_a((String)("faction.common." + factionDiplomatieInfos.get(i).get("relationType"))) + "\u00a7f] " + factionDiplomatieInfos.get(i).get("factionName"), offsetX + 6, offsetY.intValue() + 6, 0xFFFFFF, 1.0f, false, false);
                        if (mouseX < offsetX || mouseX > offsetX + 245 || !((float)mouseY >= offsetY.floatValue()) || !((float)mouseY <= offsetY.floatValue() + 20.0f)) continue;
                        this.hoveredRelation = factionDiplomatieInfos.get(i);
                    }
                    GUIUtils.endGLScissor();
                    this.scrollBar.draw(mouseX, mouseY);
                }
            }
            this.wishButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
            this.drawScaledString(I18n.func_135053_a((String)"faction.diplomatie.buttons.wish_1"), this.guiLeft + 10 + 50, this.guiTop + 165 + 5, 0xFFFFFF, 1.0f, true, true);
            this.drawScaledString(I18n.func_135053_a((String)"faction.diplomatie.buttons.wish_2"), this.guiLeft + 10 + 50, this.guiTop + 165 + 15, 0xFFFFFF, 1.0f, true, true);
        }
    }

    private String convertDate(String time) {
        String date = "";
        long diff = System.currentTimeMillis() - Long.parseLong(time);
        long days = diff / 86400000L;
        long hours = 0L;
        long minutes = 0L;
        long seconds = 0L;
        if (days == 0L) {
            hours = diff / 3600000L;
            if (hours == 0L) {
                minutes = diff / 60000L;
                if (minutes == 0L) {
                    seconds = diff / 1000L;
                    date = date + " " + seconds + " " + I18n.func_135053_a((String)"faction.common.seconds");
                } else {
                    date = date + " " + minutes + " " + I18n.func_135053_a((String)"faction.common.minutes");
                }
            } else {
                date = date + " " + hours + " " + I18n.func_135053_a((String)"faction.common.hours");
            }
        } else {
            date = date + " " + days + " " + I18n.func_135053_a((String)"faction.common.days");
        }
        return date;
    }

    private float getSlide() {
        return factionDiplomatieInfos.size() > 8 ? (float)(-(factionDiplomatieInfos.size() - 8) * 20) * this.scrollBar.getSliderValue() : 0.0f;
    }

    private float getSlidePlayers() {
        return ((ArrayList)this.selectedDiplomatieInfos.get("onlinePlayers")).size() > 4 ? (float)(-(((ArrayList)this.selectedDiplomatieInfos.get("onlinePlayers")).size() - 4) * 20) * this.scrollBarPlayers.getSliderValue() : 0.0f;
    }

    public void drawTooltip(String text, int mouseX, int mouseY) {
        int mouseXGui = mouseX - this.guiLeft;
        int mouseYGui = mouseY - this.guiTop;
        this.drawHoveringText(Arrays.asList(text.substring(0, 1).toUpperCase() + text.substring(1)), mouseX, mouseY, this.field_73886_k);
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        super.func_73864_a(mouseX, mouseY, mouseButton);
        if (mouseButton == 0) {
            if (mouseX >= this.guiLeft + 364 && mouseX <= this.guiLeft + 364 + 20 && mouseY >= this.guiTop + 30 && mouseY <= this.guiTop + 30 + 20) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                boolean bl = this.expanded = !this.expanded;
            }
            if (mouseX >= this.guiLeft + 130 && mouseX <= this.guiLeft + 130 + 254 && mouseY >= this.guiTop + 49 && mouseY <= this.guiTop + 49 + 160 && this.hoveredRelation != null && this.hoveredRelation.size() > 0) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                this.expanded = false;
                this.selectedDiplomatieInfos = this.hoveredRelation;
                this.hoveredRelation = new HashMap();
            }
            if (!this.expanded && mouseX >= this.guiLeft + 130 && mouseX <= this.guiLeft + 130 + 125 && mouseY >= this.guiTop + 140 && mouseY <= this.guiTop + 140 + 90 && !this.hoveredPlayer.isEmpty()) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new ProfilGui(this.hoveredPlayer.split(" ")[0], ""));
            }
            if (!this.expanded && mouseX >= this.guiLeft + 258 && mouseX <= this.guiLeft + 258 + 126 && mouseY >= this.guiTop + 140 && mouseY <= this.guiTop + 140 + 20 && this.selectedDiplomatieInfos != null) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new FactionGui_OLD((String)this.selectedDiplomatieInfos.get("factionName")));
            }
            if (!this.expanded && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 165 && mouseY <= this.guiTop + 165 + 30 && this.selectedDiplomatieInfos != null) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new DiplomatieWishGUI(this.player));
            }
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
}

