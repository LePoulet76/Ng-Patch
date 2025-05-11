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
import java.util.List;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.TabbedFactionGUI_OLD;
import net.ilexiconn.nationsgui.forge.client.gui.TexturedCenteredButtonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ActionsListGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.BuyActionConfirmGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGui_OLD;
import net.ilexiconn.nationsgui.forge.client.gui.faction.LockActionConfirmGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionActionsDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

public class ActionsGUI
extends TabbedFactionGUI_OLD {
    public static boolean loaded = false;
    public boolean packetSent = false;
    public static HashMap<String, Object> factionActionsInfos;
    private List<String> flagCoords = Arrays.asList("261,41", "290,69", "290,94", "261,121", "234,121", "205,94", "205,69", "234,41");
    private List<String> lockCoords = Arrays.asList("264,52", "277,66", "277,91", "264,104", "239,104", "227,91", "227,66", "239,52");
    private HashMap<String, DynamicTexture> flagTextures = new HashMap();
    private ArrayList<HashMap<String, String>> cachedLogs = new ArrayList();
    private ArrayList<String> cachedTotals = new ArrayList();
    private GuiScrollBarFaction scrollBarTotal;
    private GuiScrollBarFaction scrollBarLogs;
    private int hoveredIndex;
    private String hoveredOwnerFactionId;
    private String hoveredStatus;
    private boolean hoveringStatus;
    private boolean hoveringFlag;
    private GuiButton allActionsButton;

    public ActionsGUI(EntityPlayer player) {
        super(player);
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        loaded = false;
        this.packetSent = false;
        this.scrollBarTotal = new GuiScrollBarFaction(this.guiLeft + 248, this.guiTop + 173, 50);
        this.scrollBarLogs = new GuiScrollBarFaction(this.guiLeft + 378, this.guiTop + 173, 50);
        this.allActionsButton = new TexturedCenteredButtonGUI(0, this.guiLeft + 10, this.guiTop + 165, 100, 30, "faction_btn", 0, 68, "");
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        if (!this.packetSent && FactionGui_OLD.loaded) {
            this.packetSent = true;
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionActionsDataPacket((String)FactionGui_OLD.factionInfos.get("name"))));
        }
        ClientEventHandler.STYLE.bindTexture("faction_actions");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        if (FactionGui_OLD.factionInfos != null && loaded) {
            int offsetX;
            this.hoveredIndex = -1;
            this.hoveredOwnerFactionId = null;
            this.hoveredStatus = null;
            this.hoveringStatus = false;
            this.hoveringFlag = false;
            this.drawScaledString(I18n.func_135053_a((String)"faction.actions.title"), this.guiLeft + 131, this.guiTop + 16, 0x191919, 1.4f, false, false);
            ArrayList owners = (ArrayList)factionActionsInfos.get("owners");
            ArrayList status = (ArrayList)factionActionsInfos.get("status");
            ArrayList<String> tooltipToDraw = new ArrayList<String>();
            for (int index = 0; index < owners.size(); ++index) {
                String flagImageString;
                String ownerFactionId = (String)owners.get(index);
                int flagOffsetX = Integer.parseInt(this.flagCoords.get(index).split(",")[0]);
                int flagOffsetY = Integer.parseInt(this.flagCoords.get(index).split(",")[1]);
                int lockOffsetX = Integer.parseInt(this.lockCoords.get(index).split(",")[0]);
                int lockOffsetY = Integer.parseInt(this.lockCoords.get(index).split(",")[1]);
                if (!this.isNumeric(ownerFactionId)) {
                    if (!this.flagTextures.containsKey(ownerFactionId) && !(flagImageString = (String)((ArrayList)factionActionsInfos.get("flags")).get(index)).equals("null")) {
                        BufferedImage image = ActionsGUI.decodeToImage(flagImageString);
                        this.flagTextures.put(ownerFactionId, new DynamicTexture(image));
                    }
                    if (this.flagTextures.containsKey(ownerFactionId)) {
                        GL11.glBindTexture((int)3553, (int)this.flagTextures.get(ownerFactionId).func_110552_b());
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + flagOffsetX, this.guiTop + flagOffsetY, 0.0f, 0.0f, 156, 78, 17, 10, 156.0f, 78.0f, false);
                    }
                } else {
                    if (!this.flagTextures.containsKey((String)FactionGui_OLD.factionInfos.get("id"))) {
                        flagImageString = (String)FactionGui_OLD.factionInfos.get("flagImage");
                        BufferedImage image = ActionsGUI.decodeToImage(flagImageString);
                        this.flagTextures.put((String)FactionGui_OLD.factionInfos.get("id"), new DynamicTexture(image));
                    }
                    if (this.flagTextures.containsKey((String)FactionGui_OLD.factionInfos.get("id"))) {
                        GL11.glBindTexture((int)3553, (int)this.flagTextures.get((String)FactionGui_OLD.factionInfos.get("id")).func_110552_b());
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + flagOffsetX, this.guiTop + flagOffsetY, 0.0f, 0.0f, 156, 78, 17, 10, 156.0f, 78.0f, false);
                    }
                }
                ClientEventHandler.STYLE.bindTexture("faction_actions");
                if (((String)status.get(index)).equals("locked")) {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + lockOffsetX, this.guiTop + lockOffsetY, 33, 254, 8, 14, 512.0f, 512.0f, false);
                } else {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + lockOffsetX, this.guiTop + lockOffsetY, 44, 254, 8, 14, 512.0f, 512.0f, false);
                }
                if (mouseX >= this.guiLeft + flagOffsetX && mouseX <= this.guiLeft + flagOffsetX + 17 && mouseY >= this.guiTop + flagOffsetY && mouseY <= this.guiTop + flagOffsetY + 10) {
                    String factionName = !this.isNumeric(ownerFactionId) ? (String)((ArrayList)factionActionsInfos.get("factionsName")).get(index) : (String)FactionGui_OLD.factionInfos.get("nameColored");
                    tooltipToDraw.add(factionName);
                    tooltipToDraw.add(I18n.func_135053_a((String)"faction.actions.label.status") + ": " + I18n.func_135053_a((String)("faction.actions.status." + (String)status.get(index))));
                    tooltipToDraw.add(I18n.func_135053_a((String)"faction.actions.label.price") + ": \u00a78" + factionActionsInfos.get("price") + "$");
                    if (((String)status.get(index)).equals("locked") || ((String)FactionGui_OLD.factionInfos.get("playerWhoSeeFactionId")).equals(this.hoveredOwnerFactionId) || this.isNumeric(ownerFactionId) && ((Boolean)FactionGui_OLD.factionInfos.get("isInCountry")).booleanValue()) continue;
                    this.hoveredIndex = index;
                    this.hoveredOwnerFactionId = ownerFactionId;
                    this.hoveringFlag = true;
                    ClientEventHandler.STYLE.bindTexture("faction_actions");
                    ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(this.guiLeft + flagOffsetX - 1, this.guiTop + flagOffsetY - 1, 54, 256, 19, 12, 512.0f, 512.0f, false);
                    if (!FactionGui_OLD.hasPermissions("actions")) continue;
                    long diff = System.currentTimeMillis() - Long.parseLong((String)factionActionsInfos.get("lastActionFromBuyer"));
                    long cooldown = 18000000L - diff;
                    if (cooldown > 0L) {
                        long minutes = cooldown / 60000L;
                        tooltipToDraw.add("");
                        tooltipToDraw.add(I18n.func_135053_a((String)"faction.actions.label.cooldown") + " \u00a74" + minutes + " minutes");
                        continue;
                    }
                    tooltipToDraw.add("");
                    tooltipToDraw.add(I18n.func_135053_a((String)"faction.actions.cta.buy"));
                    continue;
                }
                if (mouseX < this.guiLeft + lockOffsetX || mouseX > this.guiLeft + lockOffsetX + 8 || mouseY < this.guiTop + lockOffsetY || mouseY > this.guiTop + lockOffsetY + 14 || !FactionGui_OLD.hasPermissions("actions") || !((String)FactionGui_OLD.factionInfos.get("playerWhoSeeFactionId")).equals(ownerFactionId) && (!this.isNumeric(ownerFactionId) || !((Boolean)FactionGui_OLD.factionInfos.get("isInCountry")).booleanValue())) continue;
                tooltipToDraw.add(I18n.func_135053_a((String)("faction.actions.label.change." + (String)status.get(index))));
                this.hoveredIndex = index;
                this.hoveredOwnerFactionId = ownerFactionId;
                this.hoveredStatus = (String)status.get(index);
                this.hoveringStatus = true;
            }
            this.drawScaledString(I18n.func_135053_a((String)"faction.actions.title.top"), this.guiLeft + 131, this.guiTop + 160, 3818599, 0.9f, false, false);
            ArrayList actionsTotals = (ArrayList)factionActionsInfos.get("totals");
            GUIUtils.startGLScissor(this.guiLeft + 131, this.guiTop + 168, 117, 60);
            for (int l = 0; l < actionsTotals.size(); ++l) {
                offsetX = this.guiLeft + 131;
                Float offsetY = Float.valueOf((float)(this.guiTop + 168 + l * 21) + this.getSlideTotals());
                ClientEventHandler.STYLE.bindTexture("faction_actions");
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 131, 168, 117, 21, 512.0f, 512.0f, false);
                this.drawScaledString(((String)actionsTotals.get(l)).replace("##", "  ") + "$", offsetX + 4, offsetY.intValue() + 7, 0xB4B4B4, 0.8f, false, true);
            }
            GUIUtils.endGLScissor();
            if (mouseX > this.guiLeft + 130 && mouseX < this.guiLeft + 130 + 125 && mouseY > this.guiTop + 167 && mouseY < this.guiTop + 167 + 62) {
                this.scrollBarTotal.draw(mouseX, mouseY);
            }
            if (this.cachedLogs.size() == 0) {
                ArrayList actionsLogs = (ArrayList)factionActionsInfos.get("logs");
                for (int i = 0; i < actionsLogs.size(); ++i) {
                    HashMap<String, String> cachedLog = new HashMap<String, String>();
                    String line = (String)actionsLogs.get(i);
                    String amount = line.split("##")[1];
                    amount = "\u00a7a+\u00a77" + amount;
                    amount = amount + "$";
                    Long time = Long.parseLong(line.split("##")[2]);
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
                    cachedLog.put("amount", amount);
                    cachedLog.put("date", date);
                    cachedLog.put("factionName", line.split("##")[0]);
                    this.cachedLogs.add(cachedLog);
                }
            }
            this.drawScaledString(I18n.func_135053_a((String)"faction.actions.title.logs"), this.guiLeft + 260, this.guiTop + 160, 3818599, 0.9f, false, false);
            GUIUtils.startGLScissor(this.guiLeft + 260, this.guiTop + 168, 118, 60);
            for (int l = 0; l < this.cachedLogs.size(); ++l) {
                offsetX = this.guiLeft + 260;
                Float offsetY = Float.valueOf((float)(this.guiTop + 168 + l * 21) + this.getSlideLogs());
                ClientEventHandler.STYLE.bindTexture("faction_actions");
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 131, 168, 117, 21, 512.0f, 512.0f, false);
                this.drawScaledString(this.cachedLogs.get(l).get("amount"), offsetX + 3, offsetY.intValue() + 3, 0xB4B4B4, 0.8f, false, false);
                this.drawScaledString(this.cachedLogs.get(l).get("date"), offsetX + 3, offsetY.intValue() + 12, 0x666666, 0.65f, false, false);
                ClientEventHandler.STYLE.bindTexture("faction_actions");
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 104, offsetY.intValue() + 5, 19, 256, 10, 11, 512.0f, 512.0f, false);
                if (mouseX <= offsetX + 104 || mouseX >= offsetX + 104 + 10 || !((float)mouseY > offsetY.floatValue() + 5.0f) || !((float)mouseY < offsetY.floatValue() + 5.0f + 11.0f)) continue;
                tooltipToDraw.add(this.cachedLogs.get(l).get("factionName"));
            }
            GUIUtils.endGLScissor();
            if (mouseX > this.guiLeft + 260 && mouseX < this.guiLeft + 260 + 125 && mouseY > this.guiTop + 167 && mouseY < this.guiTop + 167 + 62) {
                this.scrollBarLogs.draw(mouseX, mouseY);
            }
            if (mouseX > this.guiLeft + 345 && mouseX < this.guiLeft + 345 + 13 && mouseY > this.guiTop + 79 && mouseY < this.guiTop + 79 + 14) {
                tooltipToDraw.add(I18n.func_135053_a((String)"faction.actions.help0"));
                tooltipToDraw.add("");
                tooltipToDraw.add(I18n.func_135053_a((String)"faction.actions.help1"));
                tooltipToDraw.add(I18n.func_135053_a((String)"faction.actions.help2"));
                tooltipToDraw.add(I18n.func_135053_a((String)"faction.actions.help3"));
                tooltipToDraw.add("");
                tooltipToDraw.add(I18n.func_135053_a((String)"faction.actions.help4"));
                tooltipToDraw.add(I18n.func_135053_a((String)"faction.actions.help5"));
                tooltipToDraw.add(I18n.func_135053_a((String)"faction.actions.help6"));
                tooltipToDraw.add("");
                tooltipToDraw.add(I18n.func_135053_a((String)"faction.actions.help7"));
                tooltipToDraw.add(I18n.func_135053_a((String)"faction.actions.help8"));
                tooltipToDraw.add(I18n.func_135053_a((String)"faction.actions.help9"));
                tooltipToDraw.add("");
                tooltipToDraw.add(I18n.func_135053_a((String)"faction.actions.help10"));
                tooltipToDraw.add(I18n.func_135053_a((String)"faction.actions.help11"));
                tooltipToDraw.add(I18n.func_135053_a((String)"faction.actions.help12"));
            }
            if (tooltipToDraw != null) {
                this.drawTooltip(tooltipToDraw, mouseX, mouseY);
            }
            this.allActionsButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
            this.drawScaledString(I18n.func_135053_a((String)"faction.actions.button.all_1"), this.guiLeft + 10 + 50, this.guiTop + 165 + 5, 0xFFFFFF, 1.0f, true, true);
            this.drawScaledString(I18n.func_135053_a((String)"faction.actions.button.all_2"), this.guiLeft + 10 + 50, this.guiTop + 165 + 15, 0xFFFFFF, 1.0f, true, true);
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

    private float getSlideTotals() {
        return ((ArrayList)factionActionsInfos.get("totals")).size() > 3 ? (float)(-(((ArrayList)factionActionsInfos.get("totals")).size() - 3) * 21) * this.scrollBarTotal.getSliderValue() : 0.0f;
    }

    private float getSlideLogs() {
        return ((ArrayList)factionActionsInfos.get("logs")).size() > 3 ? (float)(-(((ArrayList)factionActionsInfos.get("logs")).size() - 3) * 21) * this.scrollBarLogs.getSliderValue() : 0.0f;
    }

    public void drawTooltip(List<String> texts, int mouseX, int mouseY) {
        int mouseXGui = mouseX - this.guiLeft;
        int mouseYGui = mouseY - this.guiTop;
        this.drawHoveringText(texts, mouseX, mouseY, this.field_73886_k);
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        super.func_73864_a(mouseX, mouseY, mouseButton);
        if (loaded && mouseButton == 0) {
            if (this.hoveredIndex != -1 && this.hoveredOwnerFactionId != null && this.hoveringFlag) {
                if (FactionGui_OLD.hasPermissions("actions") && !((String)FactionGui_OLD.factionInfos.get("playerWhoSeeFactionId")).equals(this.hoveredOwnerFactionId) && (System.currentTimeMillis() - Long.parseLong((String)factionActionsInfos.get("lastActionFromBuyer")) >= 18000000L || ((Boolean)factionActionsInfos.get("isOp")).booleanValue())) {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new BuyActionConfirmGui(this, this.hoveredIndex, this.hoveredOwnerFactionId, (String)factionActionsInfos.get("price")));
                }
            } else if (this.hoveredIndex != -1 && this.hoveredOwnerFactionId != null && this.hoveringStatus && this.hoveredStatus != null && FactionGui_OLD.hasPermissions("actions") && (((String)FactionGui_OLD.factionInfos.get("playerWhoSeeFactionId")).equals(this.hoveredOwnerFactionId) || this.isNumeric(this.hoveredOwnerFactionId) && ((Boolean)FactionGui_OLD.factionInfos.get("isInCountry")).booleanValue())) {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new LockActionConfirmGui(this, (String)FactionGui_OLD.factionInfos.get("id"), this.hoveredIndex, this.hoveredStatus));
            }
            if (mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 165 && mouseY <= this.guiTop + 165 + 30 && FactionGui_OLD.factionInfos != null) {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new ActionsListGui());
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

    public boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
}

