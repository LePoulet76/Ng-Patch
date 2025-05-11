/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.packet.Packet
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.TabbedFactionGUI_OLD;
import net.ilexiconn.nationsgui.forge.client.gui.TexturedCenteredButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGui_OLD;
import net.ilexiconn.nationsgui.forge.client.gui.faction.WarAgreementListGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.WarRequestGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.WarRequestListGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionStartAssaultPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionWarDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;

public class WarGUI_OLD
extends TabbedFactionGUI_OLD {
    public static boolean loaded = false;
    public static ArrayList<HashMap<String, Object>> factionWars;
    private GuiScrollBarFaction scrollBarMissiles;
    private GuiScrollBarFaction scrollBarAssaults;
    private ArrayList<HashMap<String, Object>> cachedAssaults = new ArrayList();
    private RenderItem itemRenderer = new RenderItem();
    private GuiButton assaultButton;
    private GuiButton warRequestsButton;
    private int currentWarIndex = 0;
    private HashMap<String, Object> currentWar;
    private GuiScrollBarFaction scrollBar;

    public WarGUI_OLD(EntityPlayer player) {
        super(player);
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        loaded = false;
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionWarDataPacket((String)FactionGui_OLD.factionInfos.get("name"))));
        this.scrollBarMissiles = new GuiScrollBarFaction(this.guiLeft + 248, this.guiTop + 145, 80);
        this.scrollBarAssaults = new GuiScrollBarFaction(this.guiLeft + 377, this.guiTop + 145, 55);
        this.assaultButton = new TexturedCenteredButtonGUI(0, this.guiLeft + 259, this.guiTop + 210, 125, 20, "faction_btn", 103, 0, "   " + I18n.func_135053_a((String)"faction.war.assault_button"));
        this.warRequestsButton = new GuiButton(1, this.guiLeft + 10, this.guiTop + 165, 100, 20, I18n.func_135053_a((String)"faction.wars.button.requests"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        if (loaded && factionWars.size() > 0 && this.currentWar == null) {
            this.currentWar = factionWars.get(this.currentWarIndex);
        }
        ClientEventHandler.STYLE.bindTexture("faction_war");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        if (this.warRequestsButton != null) {
            this.warRequestsButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
        }
        this.drawScaledString(I18n.func_135053_a((String)"faction.war.title"), this.guiLeft + 131, this.guiTop + 16, 0x191919, 1.4f, false, false);
        if (loaded && this.currentWar != null) {
            HashMap<Object, Object> assault;
            int i;
            if (this.assaultButton.field_73742_g && !this.currentWar.get("canAssault").equals("true")) {
                this.assaultButton.field_73742_g = false;
            }
            ClientEventHandler.STYLE.bindTexture("faction_war");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 353, this.guiTop + 14, 180, 250, 31, 14, 512.0f, 512.0f, false);
            this.drawScaledString(I18n.func_135053_a((String)"faction.war.duration_1") + " " + this.currentWar.get("duration") + " " + I18n.func_135053_a((String)"faction.war.duration_2"), this.guiLeft + 255, this.guiTop + 35, 0xFFFFFF, 0.7f, true, false);
            if (((String)FactionGui_OLD.factionInfos.get("name")).contains("Empire")) {
                this.drawScaledString("Empire", this.guiLeft + 185, this.guiTop + 55, 0xFFFFFF, 1.2f, true, false);
                this.drawScaledString(((String)FactionGui_OLD.factionInfos.get("name")).replace("Empire", ""), this.guiLeft + 189, this.guiTop + 67, 0xFFFFFF, 1.2f, true, false);
            } else {
                this.drawScaledString((String)FactionGui_OLD.factionInfos.get("name"), this.guiLeft + 185, this.guiTop + 60, 0xFFFFFF, 1.4f, true, false);
            }
            if (((String)this.currentWar.get("name")).contains("Empire")) {
                this.drawScaledString("Empire", this.guiLeft + 330, this.guiTop + 55, 0xFFFFFF, 1.2f, true, false);
                this.drawScaledString("\u00a7c" + ((String)this.currentWar.get("name")).replace("Empire", ""), this.guiLeft + 330, this.guiTop + 67, 0xFFFFFF, 1.2f, true, false);
            } else {
                this.drawScaledString("\u00a7c" + this.currentWar.get("name"), this.guiLeft + 330, this.guiTop + 60, 0xFFFFFF, 1.4f, true, false);
            }
            if (this.currentWar.containsKey("remainingPoints")) {
                String line = I18n.func_135053_a((String)"faction.war.summary.sentence").replace("#remainingPoints#", (String)this.currentWar.get("remainingPoints"));
                line = line.replace("#days#", (String)this.currentWar.get("daysBeforeReset"));
                this.drawScaledString(line, this.guiLeft + 157, this.guiTop + 110, 0xB4B4B4, 0.8f, false, false);
            }
            if (this.currentWar.containsKey("warId")) {
                ClientEventHandler.STYLE.bindTexture("faction_war");
                if (mouseX > this.guiLeft + 327 && mouseX < this.guiLeft + 327 + 50 && mouseY > this.guiTop + 105 && mouseY < this.guiTop + 105 + 15) {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 327, this.guiTop + 105, 212, 265, 50, 15, 512.0f, 512.0f, false);
                } else {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 327, this.guiTop + 105, 212, 250, 50, 15, 512.0f, 512.0f, false);
                }
                this.drawScaledString(I18n.func_135053_a((String)"faction.war.warRequestBtn"), this.guiLeft + 352, this.guiTop + 109, 0xFFFFFF, 1.0f, true, false);
                ClientEventHandler.STYLE.bindTexture("faction_war");
                if (mouseX > this.guiLeft + 300 && mouseX < this.guiLeft + 300 + 50 && mouseY > this.guiTop + 14 && mouseY < this.guiTop + 14 + 14) {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 300, this.guiTop + 14, 263, 264, 50, 14, 512.0f, 512.0f, false);
                } else {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 300, this.guiTop + 14, 263, 250, 50, 14, 512.0f, 512.0f, false);
                }
                this.drawScaledString(I18n.func_135053_a((String)"faction.war.warAgreementBtn"), this.guiLeft + 325, this.guiTop + 17, 0xFFFFFF, 1.0f, true, false);
            }
            ArrayList<String> tooltipToDraw = new ArrayList<String>();
            this.drawScaledString(I18n.func_135053_a((String)"faction.war.missile_history"), this.guiLeft + 131, this.guiTop + 133, 0x191919, 0.85f, false, false);
            if (this.currentWar.containsKey("missiles") && ((ArrayList)this.currentWar.get("missiles")).size() > 0) {
                GUIUtils.startGLScissor(this.guiLeft + 131, this.guiTop + 141, 117, 88);
                for (i = 0; i < ((ArrayList)this.currentWar.get("missiles")).size(); ++i) {
                    String missileInfos = (String)((ArrayList)this.currentWar.get("missiles")).get(i);
                    long timeLaunched = Long.parseLong(missileInfos.split("#")[0]);
                    Date date = new Date(timeLaunched);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String dateFormated = simpleDateFormat.format(date);
                    String senderName = missileInfos.split("#")[1];
                    String[] missileName = missileInfos.split("#")[2];
                    String cost = missileInfos.split("#")[3];
                    int missileID = Integer.parseInt(missileInfos.split("#")[4]);
                    int offsetX = this.guiLeft + 131;
                    Float offsetY = Float.valueOf((float)(this.guiTop + 141 + i * 31) + this.getSlideMissiles());
                    ClientEventHandler.STYLE.bindTexture("faction_war");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 131, 141, 117, 31, 512.0f, 512.0f, false);
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 5, offsetY.intValue() + 5, 159, 250, 20, 19, 512.0f, 512.0f, false);
                    ItemStack missile = new ItemStack(19483, 1, missileID);
                    this.itemRenderer.func_82406_b(this.field_73886_k, this.field_73882_e.func_110434_K(), missile, offsetX + 7, (int)(offsetY.floatValue() + 7.0f));
                    this.itemRenderer.func_77021_b(this.field_73886_k, this.field_73882_e.func_110434_K(), missile, offsetX + 7, (int)(offsetY.floatValue() + 7.0f));
                    this.drawScaledString((String)missileName, offsetX + 32, offsetY.intValue() + 8, 0xFFFFFF, 0.9f, false, false);
                    this.drawScaledString("\u00a74-\u00a77" + cost + " points", offsetX + 32, offsetY.intValue() + 18, 0x666666, 0.7f, false, false);
                    ClientEventHandler.STYLE.bindTexture("faction_war");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 104, offsetY.intValue() + 10, 148, 250, 10, 11, 512.0f, 512.0f, false);
                    if (mouseX <= offsetX + 104 || mouseX >= offsetX + 104 + 10 || !((float)mouseY > offsetY.floatValue() + 10.0f) || !((float)mouseY < offsetY.floatValue() + 10.0f + 11.0f)) continue;
                    tooltipToDraw.add(I18n.func_135053_a((String)"faction.war.missile_firedby") + " " + senderName + " - " + dateFormated);
                }
                GUIUtils.endGLScissor();
            }
            if (this.cachedAssaults.size() == 0 && this.currentWar.containsKey("assaults") && ((ArrayList)this.currentWar.get("assaults")).size() > 0) {
                for (i = 0; i < ((ArrayList)this.currentWar.get("assaults")).size(); ++i) {
                    assault = new HashMap();
                    String assaultInfos = (String)((ArrayList)this.currentWar.get("assaults")).get(i);
                    long time = Long.parseLong(assaultInfos.split("#")[0]);
                    boolean win = assaultInfos.split("#")[1].contains("win");
                    String mmr = assaultInfos.split("#")[1].split(",").length > 1 ? assaultInfos.split("#")[1].split(",")[1] : "25";
                    ArrayList<String> winnerHelpers = new ArrayList<String>();
                    for (String helper : assaultInfos.split("#")[2].split(",")) {
                        if (helper.contains("Helper")) continue;
                        winnerHelpers.add(helper);
                    }
                    ArrayList<String> looserHelpers = new ArrayList<String>();
                    for (String helper : assaultInfos.split("#")[3].split(",")) {
                        if (helper.contains("Helper")) continue;
                        looserHelpers.add(helper);
                    }
                    long now = System.currentTimeMillis();
                    long diff = now - time;
                    String date = "\u00a78" + I18n.func_135053_a((String)"faction.bank.date_1");
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
                                date = date + " " + seconds + " " + I18n.func_135053_a((String)"faction.common.seconds") + " " + I18n.func_135053_a((String)"faction.bank.date_2");
                            } else {
                                date = date + " " + minutes + " " + I18n.func_135053_a((String)"faction.common.minutes") + " " + I18n.func_135053_a((String)"faction.bank.date_2");
                            }
                        } else {
                            date = date + " " + hours + " " + I18n.func_135053_a((String)"faction.common.hours") + " " + I18n.func_135053_a((String)"faction.bank.date_2");
                        }
                    } else {
                        date = date + " " + days + " " + I18n.func_135053_a((String)"faction.common.days") + " " + I18n.func_135053_a((String)"faction.bank.date_2");
                    }
                    assault.put("date", date);
                    assault.put("win", win);
                    assault.put("mmr", mmr);
                    assault.put("winnerHelpers", winnerHelpers);
                    assault.put("looserHelpers", looserHelpers);
                    this.cachedAssaults.add(assault);
                }
            }
            this.drawScaledString(I18n.func_135053_a((String)"faction.war.assault_history"), this.guiLeft + 260, this.guiTop + 133, 0x191919, 0.85f, false, false);
            if (this.cachedAssaults.size() > 0) {
                GUIUtils.startGLScissor(this.guiLeft + 260, this.guiTop + 141, 117, 63);
                for (i = 0; i < this.cachedAssaults.size(); ++i) {
                    assault = this.cachedAssaults.get(i);
                    int offsetX = this.guiLeft + 260;
                    Float offsetY = Float.valueOf((float)(this.guiTop + 141 + i * 20) + this.getSlideAssaults());
                    ClientEventHandler.STYLE.bindTexture("faction_war");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 260, 141, 117, 20, 512.0f, 512.0f, false);
                    this.drawScaledString((Boolean)assault.get("win") != false ? I18n.func_135053_a((String)"faction.common.victory").replace("#mmr#", (String)assault.get("mmr")) : I18n.func_135053_a((String)"faction.common.defeat").replace("#mmr#", (String)assault.get("mmr")), offsetX + 3, offsetY.intValue() + 3, 0xB4B4B4, 0.85f, false, false);
                    this.drawScaledString((String)assault.get("date"), offsetX + 3, offsetY.intValue() + 12, 0x666666, 0.65f, false, false);
                    ClientEventHandler.STYLE.bindTexture("faction_war");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 104, offsetY.intValue() + 5, 148, 250, 10, 11, 512.0f, 512.0f, false);
                    if (mouseX <= this.guiLeft + 260 || mouseX >= this.guiLeft + 260 + 117 || mouseY <= this.guiTop + 141 || mouseY >= this.guiTop + 141 + 63 || mouseX <= offsetX + 104 || mouseX >= offsetX + 104 + 10 || !((float)mouseY > offsetY.floatValue() + 5.0f) || !((float)mouseY < offsetY.floatValue() + 5.0f + 11.0f)) continue;
                    tooltipToDraw.add("\u00a72" + I18n.func_135053_a((String)"faction.war.assaults.helpers.ally") + ":");
                    for (String helper : (Boolean)assault.get("win") != false ? (List)assault.get("winnerHelpers") : (List)assault.get("looserHelpers")) {
                        tooltipToDraw.add("\u00a77- " + helper);
                    }
                    tooltipToDraw.add("");
                    tooltipToDraw.add("\u00a7c" + I18n.func_135053_a((String)"faction.war.assaults.helpers.ennemy") + ":");
                    for (String helper : (Boolean)assault.get("win") != false ? (List)assault.get("looserHelpers") : (List)assault.get("winnerHelpers")) {
                        tooltipToDraw.add("\u00a77- " + helper);
                    }
                }
                GUIUtils.endGLScissor();
            }
            if (mouseX >= this.guiLeft + 130 && mouseX <= this.guiLeft + 255 && mouseY >= this.guiTop + 141 && mouseY <= this.guiTop + 225) {
                this.scrollBarMissiles.draw(mouseX, mouseY);
            }
            if (mouseX >= this.guiLeft + 259 && mouseX <= this.guiLeft + 384 && mouseY >= this.guiTop + 141 && mouseY <= this.guiTop + 205) {
                this.scrollBarAssaults.draw(mouseX, mouseY);
            }
            if (!this.currentWar.get("canAssault").equals("false")) {
                this.assaultButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
                if (!this.assaultButton.field_73742_g && mouseX > this.guiLeft + 259 && mouseX < this.guiLeft + 259 + 125 && mouseY > this.guiTop + 210 && mouseY < this.guiTop + 210 + 20) {
                    if (((String)this.currentWar.get("canAssault")).contains("cooldown")) {
                        this.drawHoveringText(Arrays.asList(I18n.func_135053_a((String)"faction.war.assault.reason.cooldown").replace("#cooldown#", ((String)this.currentWar.get("canAssault")).replace("cooldown.", ""))), mouseX, mouseY + 20, this.field_73886_k);
                    } else if (((String)this.currentWar.get("canAssault")).contains("self.days.protection")) {
                        this.drawHoveringText(Arrays.asList(I18n.func_135053_a((String)"self.days.protection").replace("#protection#", ((String)this.currentWar.get("canAssault")).replace("self.days.protection.", ""))), mouseX, mouseY + 20, this.field_73886_k);
                    } else if (((String)this.currentWar.get("canAssault")).contains("other.days.protection")) {
                        this.drawHoveringText(Arrays.asList(I18n.func_135053_a((String)"other.days.protection").replace("#protection#", ((String)this.currentWar.get("canAssault")).replace("other.days.protection.", ""))), mouseX, mouseY + 20, this.field_73886_k);
                    } else {
                        this.drawHoveringText(Arrays.asList(I18n.func_135053_a((String)("faction.war.assault.reason." + this.currentWar.get("canAssault")))), mouseX, mouseY + 20, this.field_73886_k);
                    }
                }
            }
            if (!tooltipToDraw.isEmpty()) {
                this.drawTooltip(tooltipToDraw, mouseX, mouseY);
            }
        } else {
            Gui.func_73734_a((int)(this.guiLeft + 124), (int)(this.guiTop + 15), (int)(this.guiLeft + 124 + 268), (int)(this.guiTop + 15 + 224), (int)-1513240);
            this.drawScaledString(I18n.func_135053_a((String)"faction.war.no_result"), this.guiLeft + 258, this.guiTop + 100, 328965, 1.1f, true, false);
        }
    }

    public void drawTooltip(List<String> texts, int mouseX, int mouseY) {
        int mouseXGui = mouseX - this.guiLeft;
        int mouseYGui = mouseY - this.guiTop;
        this.drawHoveringText(texts, mouseX, mouseY, this.field_73886_k);
    }

    private float getSlideMissiles() {
        return ((ArrayList)this.currentWar.get("missiles")).size() > 2 ? (float)(-(((ArrayList)this.currentWar.get("missiles")).size() - 2) * 31) * this.scrollBarMissiles.getSliderValue() : 0.0f;
    }

    private float getSlideAssaults() {
        return ((ArrayList)this.currentWar.get("assaults")).size() > 3 ? (float)(-(((ArrayList)this.currentWar.get("assaults")).size() - 3) * 20) * this.scrollBarAssaults.getSliderValue() : 0.0f;
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        super.func_73864_a(mouseX, mouseY, mouseButton);
        if (mouseButton == 0) {
            if (this.currentWar != null && this.currentWar.get("canAssault").equals("true") && mouseX >= this.guiLeft + 259 && mouseX <= this.guiLeft + 259 + 125 && mouseY >= this.guiTop + 210 && mouseY <= this.guiTop + 210 + 20) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionStartAssaultPacket((String)FactionGui_OLD.factionInfos.get("id"), (String)this.currentWar.get("id"))));
                Minecraft.func_71410_x().func_71373_a(null);
            }
            if (mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 165 && mouseY <= this.guiTop + 165 + 20) {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new WarRequestListGUI());
            }
            if (this.currentWar != null && this.currentWar.containsKey("warId") && mouseX > this.guiLeft + 327 && mouseX < this.guiLeft + 327 + 50 && mouseY > this.guiTop + 105 && mouseY < this.guiTop + 105 + 15) {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new WarRequestGUI(((Double)this.currentWar.get("warId")).intValue(), null));
            }
            if (this.currentWar != null && this.currentWar.containsKey("warId") && mouseX > this.guiLeft + 300 && mouseX < this.guiLeft + 300 + 50 && mouseY > this.guiTop + 14 && mouseY < this.guiTop + 14 + 14) {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new WarAgreementListGui(this.player, ((Double)this.currentWar.get("warId")).intValue(), (String)FactionGui_OLD.factionInfos.get("name"), (String)this.currentWar.get("name")));
            }
            if (factionWars != null && factionWars.size() > 0 && mouseX >= this.guiLeft + 353 && mouseX <= this.guiLeft + 353 + 14 && mouseY >= this.guiTop + 14 && mouseY <= this.guiTop + 14 + 14) {
                this.cachedAssaults = new ArrayList();
                this.currentWarIndex = this.currentWarIndex - 1 >= 0 ? this.currentWarIndex - 1 : factionWars.size() - 1;
                this.currentWar = factionWars.get(this.currentWarIndex);
            }
            if (factionWars != null && factionWars.size() > 0 && mouseX >= this.guiLeft + 370 && mouseX <= this.guiLeft + 370 + 14 && mouseY >= this.guiTop + 14 && mouseY <= this.guiTop + 14 + 14) {
                this.cachedAssaults = new ArrayList();
                this.currentWarIndex = this.currentWarIndex + 1 < factionWars.size() ? this.currentWarIndex + 1 : 0;
                this.currentWar = factionWars.get(this.currentWarIndex);
            }
        }
    }
}

