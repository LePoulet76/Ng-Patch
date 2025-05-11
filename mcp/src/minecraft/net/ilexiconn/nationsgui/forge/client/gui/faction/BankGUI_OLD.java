/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.packet.Packet
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.TabbedFactionGUI_OLD;
import net.ilexiconn.nationsgui.forge.client.gui.faction.BankActionGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGui_OLD;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ProfilGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionBankDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.RemoteOpenFactionChestPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;

public class BankGUI_OLD
extends TabbedFactionGUI_OLD {
    public static boolean loaded = false;
    public static HashMap<String, Object> factionBankInfos;
    private ArrayList<HashMap<String, String>> cachedLogs = new ArrayList();
    private ArrayList<String> cachedMembers = new ArrayList();
    private GuiScrollBarFaction scrollBarLogs;
    private GuiScrollBarFaction scrollBarMembers;
    private GuiButton chestButton;
    private GuiButton actionButton;
    private String hoveredPlayer;
    private boolean doRefresh;

    public BankGUI_OLD(EntityPlayer player, boolean doRefresh) {
        super(player);
        this.doRefresh = doRefresh;
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        loaded = false;
        if (this.doRefresh) {
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionBankDataPacket((String)FactionGui_OLD.factionInfos.get("id"))));
        }
        this.scrollBarMembers = new GuiScrollBarFaction(this.guiLeft + 248, this.guiTop + 145, 80);
        this.scrollBarLogs = new GuiScrollBarFaction(this.guiLeft + 377, this.guiTop + 145, 80);
        this.cachedLogs = new ArrayList();
        this.cachedMembers = new ArrayList();
        this.chestButton = new GuiButton(0, this.guiLeft + 10, this.guiTop + 165, 100, 20, I18n.func_135053_a((String)"faction.bank.chest_button"));
        this.actionButton = new GuiButton(1, this.guiLeft + 10, this.guiTop + 188, 100, 20, I18n.func_135053_a((String)"faction.bank.action_button"));
        if (!(FactionGui_OLD.hasPermissions("chest_access") && ((Boolean)FactionGui_OLD.factionInfos.get("canOpenChest")).booleanValue() || FactionGui_OLD.hasPermissions("admin"))) {
            this.chestButton.field_73742_g = false;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        ClientEventHandler.STYLE.bindTexture("faction_bank");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        if (loaded) {
            this.drawScaledString(I18n.func_135053_a((String)"faction.bank.title") + " " + FactionGui_OLD.factionInfos.get("name"), this.guiLeft + 259, this.guiTop + 25, 3818599, 1.7f, true, false);
            this.drawScaledString(factionBankInfos.get("bank") + "$", this.guiLeft + 257, this.guiTop + 107, 0xFFFFFF, 1.5f, true, false);
            this.drawScaledString(I18n.func_135053_a((String)"faction.bank.members"), this.guiLeft + 130, this.guiTop + 132, 3818599, 0.8f, false, false);
            this.drawScaledString(I18n.func_135053_a((String)"faction.bank.transactions"), this.guiLeft + 259, this.guiTop + 132, 3818599, 0.8f, false, false);
            String tooltipToDraw = "";
            if (factionBankInfos.get("logs") != null && ((ArrayList)factionBankInfos.get("logs")).size() > 0) {
                Float offsetY;
                if (this.cachedLogs.size() == 0) {
                    for (int i = 0; i < ((ArrayList)factionBankInfos.get("logs")).size(); ++i) {
                        HashMap<String, String> cachedLog = new HashMap<String, String>();
                        String line = (String)((ArrayList)factionBankInfos.get("logs")).get(i);
                        if (line.split("#").length != 3) continue;
                        String amount = line.split("#")[0];
                        amount = amount.contains("-") ? amount.replace("-", "\u00a74-\u00a77") : "\u00a7a+\u00a77" + amount;
                        amount = amount + "$";
                        Long time = Long.parseLong(line.split("#")[1]);
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
                        String playerName = line.split("#")[2];
                        cachedLog.put("amount", amount);
                        cachedLog.put("date", date);
                        cachedLog.put("playerName", playerName);
                        this.cachedLogs.add(cachedLog);
                    }
                    for (int k = 0; k < ((ArrayList)factionBankInfos.get("members")).size(); ++k) {
                        String name = ((String)((ArrayList)factionBankInfos.get("members")).get(k)).split("#")[1];
                        String role = ((String)((ArrayList)factionBankInfos.get("members")).get(k)).split("#")[0];
                        this.cachedMembers.add("[" + role.substring(0, 1).toUpperCase() + role.substring(1) + "] " + name);
                    }
                }
                this.hoveredPlayer = "";
                GUIUtils.startGLScissor(this.guiLeft + 132, this.guiTop + 142, 116, 86);
                for (int l = 0; l < this.cachedMembers.size(); ++l) {
                    int offsetX = this.guiLeft + 132;
                    offsetY = Float.valueOf((float)(this.guiTop + 142 + l * 20) + this.getSlideMembers());
                    if (mouseX > offsetX && mouseX < offsetX + 116 && (float)mouseY > offsetY.floatValue() + 142.0f && (float)mouseY < offsetY.floatValue() + 20.0f) {
                        this.hoveredPlayer = this.cachedMembers.get(l);
                    }
                    ClientEventHandler.STYLE.bindTexture("faction_bank");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 261, 142, 116, 20, 512.0f, 512.0f, false);
                    this.drawScaledString(this.cachedMembers.get(l), offsetX + 3, offsetY.intValue() + 6, 0xB4B4B4, 0.8f, false, true);
                }
                GUIUtils.endGLScissor();
                if (mouseX > this.guiLeft + 130 && mouseX < this.guiLeft + 130 + 125 && mouseY > this.guiTop + 139 && mouseY < this.guiTop + 229) {
                    this.scrollBarMembers.draw(mouseX, mouseY);
                }
                if (FactionGui_OLD.hasPermissions("bank_log")) {
                    GUIUtils.startGLScissor(this.guiLeft + 261, this.guiTop + 142, 116, 86);
                    for (int j = 0; j < this.cachedLogs.size(); ++j) {
                        int offsetX = this.guiLeft + 261;
                        offsetY = Float.valueOf((float)(this.guiTop + 142 + j * 20) + this.getSlideLogs());
                        ClientEventHandler.STYLE.bindTexture("faction_bank");
                        ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 261, 142, 116, 20, 512.0f, 512.0f, false);
                        this.drawScaledString(this.cachedLogs.get(j).get("amount"), offsetX + 3, offsetY.intValue() + 3, 0xB4B4B4, 0.85f, false, false);
                        this.drawScaledString(this.cachedLogs.get(j).get("date"), offsetX + 3, offsetY.intValue() + 12, 0x666666, 0.65f, false, false);
                        ClientEventHandler.STYLE.bindTexture("faction_bank");
                        ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 104, offsetY.intValue() + 5, 148, 250, 10, 11, 512.0f, 512.0f, false);
                        if (mouseX <= offsetX + 104 || mouseX >= offsetX + 104 + 10 || !((float)mouseY > offsetY.floatValue() + 5.0f) || !((float)mouseY < offsetY.floatValue() + 5.0f + 11.0f)) continue;
                        tooltipToDraw = this.cachedLogs.get(j).get("playerName");
                    }
                    GUIUtils.endGLScissor();
                }
                if (mouseX > this.guiLeft + 259 && mouseX < this.guiLeft + 384 && mouseY > this.guiTop + 139 && mouseY < this.guiTop + 229) {
                    this.scrollBarLogs.draw(mouseX, mouseY);
                }
                if (!tooltipToDraw.isEmpty()) {
                    this.drawTooltip(tooltipToDraw, mouseX, mouseY);
                }
            }
            this.chestButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
            ClientEventHandler.STYLE.bindTexture("faction_bank");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 26, this.guiTop + 170, 159, 250, 10, 10, 512.0f, 512.0f, false);
            this.actionButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
            if (!this.chestButton.field_73742_g && mouseX > this.guiLeft + 10 && mouseX < this.guiLeft + 10 + 100 && mouseY > this.guiTop + 165 && mouseY < this.guiTop + 165 + 20) {
                this.drawHoveringText(Arrays.asList(I18n.func_135053_a((String)"faction.bank.chest_disabled_1"), I18n.func_135053_a((String)"faction.bank.chest_disabled_2"), I18n.func_135053_a((String)"faction.bank.chest_disabled_3"), I18n.func_135053_a((String)"faction.bank.chest_disabled_4"), I18n.func_135053_a((String)"faction.bank.chest_disabled_5")), mouseX, mouseY + 20, this.field_73886_k);
            }
        }
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && FactionGui_OLD.factionInfos != null) {
            if ((((Boolean)FactionGui_OLD.factionInfos.get("canOpenChest")).booleanValue() && FactionGui_OLD.hasPermissions("chest_access") || FactionGui_OLD.hasPermissions("admin")) && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 165 && mouseY <= this.guiTop + 165 + 20) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new RemoteOpenFactionChestPacket((String)FactionGui_OLD.factionInfos.get("id"), FactionGui_OLD.hasPermissions("chest_access"), FactionGui_OLD.hasPermissions("chest_access"), Integer.parseInt((String)FactionGui_OLD.factionInfos.get("chestLevel")))));
            }
            if (mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 188 && mouseY <= this.guiTop + 188 + 20) {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new BankActionGui(this.player, this));
            }
            if (this.hoveredPlayer != null && !this.hoveredPlayer.isEmpty() && mouseX > this.guiLeft + 130 && mouseX < this.guiLeft + 130 + 125 && mouseY > this.guiTop + 140 && mouseY < this.guiTop + 140 + 90) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new ProfilGui(this.hoveredPlayer.split(" ")[1], ""));
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    private float getSlideLogs() {
        return ((ArrayList)factionBankInfos.get("logs")).size() > 4 ? (float)(-(((ArrayList)factionBankInfos.get("logs")).size() - 4) * 20) * this.scrollBarLogs.getSliderValue() : 0.0f;
    }

    private float getSlideMembers() {
        return ((ArrayList)factionBankInfos.get("members")).size() > 4 ? (float)(-(((ArrayList)factionBankInfos.get("members")).size() - 4) * 20) * this.scrollBarMembers.getSliderValue() : 0.0f;
    }

    public void drawTooltip(String playerName, int mouseX, int mouseY) {
        int mouseXGui = mouseX - this.guiLeft;
        int mouseYGui = mouseY - this.guiTop;
        if (!playerName.contains("Action de")) {
            this.drawHoveringText(Arrays.asList(playerName), mouseX, mouseY, this.field_73886_k);
        } else {
            this.drawHoveringText(Arrays.asList(playerName), mouseX, mouseY, this.field_73886_k);
        }
    }
}

