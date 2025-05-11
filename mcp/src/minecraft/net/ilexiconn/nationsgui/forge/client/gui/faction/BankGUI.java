/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ActionsListGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.BuyActionConfirmGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ProfilGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionActionsDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionBankActionPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionBankDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionLockActionPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.RemoteOpenFactionChestPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class BankGUI
extends TabbedFactionGUI {
    public static boolean loaded = false;
    public static int lastBalance = -1;
    public static long lastBalanceAnimation = -1L;
    public static HashMap<String, Object> factionBankInfos;
    public static HashMap<String, Object> factionActionsInfos;
    public static ArrayList<HashMap<String, String>> cachedLogs;
    private ArrayList<HashMap<String, String>> cachedDividendesLogs = new ArrayList();
    private ArrayList<String> cachedMembers = new ArrayList();
    private GuiScrollBarGeneric scrollBarLogs;
    private GuiScrollBarGeneric scrollBarMembers;
    private GuiScrollBarGeneric scrollBarTotalDividendes;
    private GuiScrollBarGeneric scrollBarLogsDividendes;
    private GuiTextField amountInput;
    public static List<String> flagCoords;
    public static List<String> lockCoords;
    private int hoveredIndex;
    private String hoveredOwnerFactionId;
    private String hoveredStatus;
    private boolean hoveringStatus;
    private boolean hoveringFlag;
    private String hoveredPlayer;
    public static HashMap<String, Integer> blockSolde;
    public static HashMap<String, Integer> blockActions;

    public BankGUI() {
        loaded = false;
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionBankDataPacket((String)FactionGUI.factionInfos.get("id"))));
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionActionsDataPacket((String)FactionGUI.factionInfos.get("name"))));
    }

    public void func_73876_c() {
        this.amountInput.func_73780_a();
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        cachedLogs = new ArrayList();
        this.cachedDividendesLogs = new ArrayList();
        this.cachedMembers = new ArrayList();
        this.scrollBarMembers = new GuiScrollBarGeneric(this.guiLeft + 128, this.guiTop + 120, 97, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        this.scrollBarLogs = new GuiScrollBarGeneric(this.guiLeft + 226, this.guiTop + 120, 97, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        this.scrollBarLogs.setScrollIncrement(0.005f);
        this.scrollBarTotalDividendes = new GuiScrollBarGeneric(this.guiLeft + 343, this.guiTop + 163, 54, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        this.scrollBarLogsDividendes = new GuiScrollBarGeneric(this.guiLeft + 446, this.guiTop + 163, 54, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        this.amountInput = new GuiTextField(this.field_73886_k, this.guiLeft + 143, this.guiTop + 38, 79, 10);
        this.amountInput.func_73786_a(false);
        this.amountInput.func_73804_f(8);
        this.amountInput.func_73782_a("0");
    }

    @Override
    public void func_73863_a(int mouseX, int mouseY, float partialTick) {
        this.func_73873_v_();
        tooltipToDraw.clear();
        this.hoveredIndex = -1;
        this.hoveredOwnerFactionId = null;
        this.hoveredStatus = null;
        this.hoveringStatus = false;
        this.hoveringFlag = false;
        this.hoveredAction = "";
        this.hoveredPlayer = "";
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("faction_global_2");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 30, this.guiTop + 0, 0 * GUI_SCALE, 0 * GUI_SCALE, (this.xSize - 30) * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize - 30, this.ySize, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 242, this.guiTop + 0, 0 * GUI_SCALE, 240 * GUI_SCALE, (this.xSize - 242) * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize - 242, this.ySize, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
        if (loaded && factionBankInfos != null && factionActionsInfos != null) {
            Float offsetY;
            int l;
            ModernGui.drawScaledStringCustomFont((String)FactionGUI.factionInfos.get("name"), this.guiLeft + 43, this.guiTop + 6, 10395075, 0.5f, "left", false, "georamaMedium", 32);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.bank.title.bank"), this.guiLeft + 43, this.guiTop + 16, 0xFFFFFF, 0.75f, "left", false, "georamaSemiBold", 32);
            ClientEventHandler.STYLE.bindTexture("faction_bank");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 42, this.guiTop + 32, 100 * GUI_SCALE, 25 * GUI_SCALE, 189 * GUI_SCALE, 32 * GUI_SCALE, 189, 32, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 42, this.guiTop + 32, 100 * GUI_SCALE, blockSolde.get((String)FactionGUI.factionInfos.get("actualRelation")) * GUI_SCALE, 91 * GUI_SCALE, 32 * GUI_SCALE, 91, 32, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 51, this.guiTop + 40, 100 * GUI_SCALE, 0 * GUI_SCALE, 18 * GUI_SCALE, 17 * GUI_SCALE, 18, 17, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.bank.balance"), this.guiLeft + 77, this.guiTop + 40, 0xFFFFFF, 0.5f, "left", false, "georamaRegular", 25);
            int balance = ((Double)factionBankInfos.get("balance")).intValue();
            if (lastBalance != -1 && System.currentTimeMillis() - lastBalanceAnimation < 1000L) {
                double gap = (Double)factionBankInfos.get("balance") - (double)lastBalance;
                Double progress = (double)(System.currentTimeMillis() - lastBalanceAnimation) / 1000.0 * gap;
                balance = lastBalance + progress.intValue();
            } else {
                lastBalance = balance;
            }
            ModernGui.drawScaledStringCustomFont(balance + "$", this.guiLeft + 77, this.guiTop + 47, 0xFFFFFF, 0.75f, "left", false, "georamaSemiBold", 25);
            ClientEventHandler.STYLE.bindTexture("faction_bank");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 140, this.guiTop + 36, 200 * GUI_SCALE, 193 * GUI_SCALE, 85 * GUI_SCALE, 11 * GUI_SCALE, 85, 11, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            this.amountInput.func_73795_f();
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            ClientEventHandler.STYLE.bindTexture("faction_bank");
            if (mouseX >= this.guiLeft + 140 && mouseX <= this.guiLeft + 140 + 41 && mouseY >= this.guiTop + 51 && mouseY <= this.guiTop + 51 + 9) {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 140, this.guiTop + 51, 200 * GUI_SCALE, 219 * GUI_SCALE, 41 * GUI_SCALE, 9 * GUI_SCALE, 41, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.bank.action.deposit"), this.guiLeft + 140 + 20, this.guiTop + 53, 2826561, 0.5f, "center", false, "georamaSemiBold", 24);
                this.hoveredAction = "deposit";
            } else {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 140, this.guiTop + 51, 200 * GUI_SCALE, 208 * GUI_SCALE, 41 * GUI_SCALE, 9 * GUI_SCALE, 41, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.bank.action.deposit"), this.guiLeft + 140 + 20, this.guiTop + 53, 2826561, 0.5f, "center", false, "georamaSemiBold", 24);
            }
            ClientEventHandler.STYLE.bindTexture("faction_bank");
            if (!((Boolean)factionBankInfos.get("playerIsMember")).booleanValue()) {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 184, this.guiTop + 51, 244 * GUI_SCALE, 219 * GUI_SCALE, 41 * GUI_SCALE, 9 * GUI_SCALE, 41, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.bank.action.withdraw"), this.guiLeft + 184 + 20, this.guiTop + 53, 3682124, 0.5f, "center", false, "georamaSemiBold", 24);
            } else if (mouseX >= this.guiLeft + 184 && mouseX <= this.guiLeft + 184 + 41 && mouseY >= this.guiTop + 51 && mouseY <= this.guiTop + 51 + 9) {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 184, this.guiTop + 51, 200 * GUI_SCALE, 219 * GUI_SCALE, 41 * GUI_SCALE, 9 * GUI_SCALE, 41, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.bank.action.withdraw"), this.guiLeft + 184 + 20, this.guiTop + 53, 2826561, 0.5f, "center", false, "georamaSemiBold", 24);
                this.hoveredAction = "withdraw";
            } else {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 184, this.guiTop + 51, 244 * GUI_SCALE, 208 * GUI_SCALE, 41 * GUI_SCALE, 9 * GUI_SCALE, 41, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.bank.action.withdraw"), this.guiLeft + 184 + 20, this.guiTop + 53, 15463162, 0.5f, "center", false, "georamaSemiBold", 24);
            }
            ClientEventHandler.STYLE.bindTexture("faction_bank");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 42, this.guiTop + 66, 100 * GUI_SCALE, 25 * GUI_SCALE, 189 * GUI_SCALE, 32 * GUI_SCALE, 189, 32, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 50, this.guiTop + 76, 125 * GUI_SCALE, 3 * GUI_SCALE, 21 * GUI_SCALE, 15 * GUI_SCALE, 21, 15, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.bank.chest"), this.guiLeft + 77, this.guiTop + 74, 0xFFFFFF, 0.5f, "left", false, "georamaRegular", 25);
            ModernGui.drawScaledStringCustomFont((String)FactionGUI.factionInfos.get("chestLevel") + " " + I18n.func_135053_a((String)"faction.bank.chest.line"), this.guiLeft + 77, this.guiTop + 81, 0xFFFFFF, 0.75f, "left", false, "georamaSemiBold", 25);
            ClientEventHandler.STYLE.bindTexture("faction_bank");
            if (!((Boolean)FactionGUI.factionInfos.get("canOpenChest")).booleanValue()) {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 140, this.guiTop + 79, 200 * GUI_SCALE, 154 * GUI_SCALE, 85 * GUI_SCALE, 9 * GUI_SCALE, 85, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.bank.action.open_chest"), this.guiLeft + 140 + 42, this.guiTop + 81, 3682124, 0.5f, "center", false, "georamaSemiBold", 24);
            } else if (mouseX >= this.guiLeft + 140 && mouseX <= this.guiLeft + 140 + 85 && mouseY >= this.guiTop + 79 && mouseY <= this.guiTop + 79 + 9) {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 140, this.guiTop + 79, 200 * GUI_SCALE, 167 * GUI_SCALE, 85 * GUI_SCALE, 9 * GUI_SCALE, 85, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.bank.action.open_chest"), this.guiLeft + 140 + 42, this.guiTop + 81, 2826561, 0.5f, "center", false, "georamaSemiBold", 24);
                this.hoveredAction = "open_chest";
            } else {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 140, this.guiTop + 79, 200 * GUI_SCALE, 180 * GUI_SCALE, 85 * GUI_SCALE, 9 * GUI_SCALE, 85, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.bank.action.open_chest"), this.guiLeft + 140 + 42, this.guiTop + 81, 15463162, 0.5f, "center", false, "georamaSemiBold", 24);
            }
            if (this.cachedMembers.isEmpty() && ((ArrayList)factionBankInfos.get("members")).size() > 0) {
                for (int k = 0; k < ((ArrayList)factionBankInfos.get("members")).size(); ++k) {
                    String name = ((String)((ArrayList)factionBankInfos.get("members")).get(k)).split("#")[1];
                    String role = ((String)((ArrayList)factionBankInfos.get("members")).get(k)).split("#")[0];
                    this.cachedMembers.add("[" + role.substring(0, 1).toUpperCase() + "] " + name);
                }
            }
            ClientEventHandler.STYLE.bindTexture("faction_bank");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 42, this.guiTop + 105, 200 * GUI_SCALE, 300 * GUI_SCALE, 91 * GUI_SCALE, 116 * GUI_SCALE, 91, 116, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.bank.members"), this.guiLeft + 49, this.guiTop + 111, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 30);
            ClientEventHandler.STYLE.bindTexture("faction_bank");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 128, this.guiTop + 120, 316 * GUI_SCALE, 115 * GUI_SCALE, 2 * GUI_SCALE, 97 * GUI_SCALE, 2, 97, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            GUIUtils.startGLScissor(this.guiLeft + 49, this.guiTop + 126, 80, 87);
            for (l = 0; l < this.cachedMembers.size(); ++l) {
                int offsetX = this.guiLeft + 49;
                offsetY = Float.valueOf((float)(this.guiTop + 126 + l * 13) + this.getSlideMembers());
                String playerName = this.cachedMembers.get(l).split(" ")[1];
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
                    GUIUtils.drawScaledCustomSizeModalRect(offsetX + 10, offsetY.intValue() + 10, 8.0f, 16.0f, 8, -8, -10, -10, 64.0f, 64.0f);
                }
                ModernGui.drawScaledStringCustomFont(this.cachedMembers.get(l), offsetX + 13, offsetY.intValue() + 2, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 25);
                if (mouseX <= offsetX || mouseX >= offsetX + 80 || !((float)mouseY > offsetY.floatValue()) || !((float)mouseY < offsetY.floatValue() + 13.0f)) continue;
                this.hoveredPlayer = this.cachedMembers.get(l);
            }
            GUIUtils.endGLScissor();
            if (mouseX > this.guiLeft + 42 && mouseX < this.guiLeft + 42 + 91 && mouseY >= this.guiTop + 105 && mouseY <= this.guiTop + 105 + 116) {
                this.scrollBarMembers.draw(mouseX, mouseY);
            }
            ClientEventHandler.STYLE.bindTexture("faction_bank");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 140, this.guiTop + 105, 200 * GUI_SCALE, 300 * GUI_SCALE, 91 * GUI_SCALE, 116 * GUI_SCALE, 91, 116, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.bank.transactions"), this.guiLeft + 146, this.guiTop + 111, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 30);
            if (FactionGUI.hasPermissions("bank_log")) {
                ClientEventHandler.STYLE.bindTexture("faction_bank");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 226, this.guiTop + 120, 316 * GUI_SCALE, 115 * GUI_SCALE, 2 * GUI_SCALE, 97 * GUI_SCALE, 2, 97, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                if (factionBankInfos.get("logs") != null && ((ArrayList)factionBankInfos.get("logs")).size() > 0 && cachedLogs.size() == 0) {
                    for (int i = 0; i < ((ArrayList)factionBankInfos.get("logs")).size(); ++i) {
                        HashMap<String, String> cachedLog = new HashMap<String, String>();
                        String line = (String)((ArrayList)factionBankInfos.get("logs")).get(i);
                        if (line.split("#").length != 3) continue;
                        String amount = line.split("#")[0];
                        amount = amount.contains("-") ? amount.replace("-", "\u00a74-\u00a7f ") : "\u00a7a+\u00a7f " + amount;
                        amount = amount + "$";
                        Long time = Long.parseLong(line.split("#")[1]);
                        String date = ModernGui.formatDelayTime(time);
                        String playerName = line.split("#")[2];
                        cachedLog.put("amount", amount);
                        cachedLog.put("date", date);
                        cachedLog.put("playerName", playerName);
                        cachedLogs.add(cachedLog);
                    }
                }
                GUIUtils.startGLScissor(this.guiLeft + 146, this.guiTop + 126, 80, 87);
                l = 0;
                while (true) {
                    if (l >= cachedLogs.size()) break;
                    int offsetX = this.guiLeft + 146;
                    offsetY = Float.valueOf((float)(this.guiTop + 126 + l * 18) + this.getSlideLogs());
                    ModernGui.drawScaledStringCustomFont(cachedLogs.get(l).get("playerName"), offsetX, offsetY.intValue(), 10395075, 0.5f, "left", false, "georamaMedium", 20);
                    ModernGui.drawScaledStringCustomFont(cachedLogs.get(l).get("amount"), offsetX, offsetY.intValue() + 6, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 28);
                    ModernGui.drawScaledStringCustomFont(cachedLogs.get(l).get("date"), offsetX + 77, offsetY.intValue() + 7, 10395075, 0.5f, "right", false, "georamaMedium", 20);
                    ++l;
                }
                GUIUtils.endGLScissor();
                if (mouseX > this.guiLeft + 140 && mouseX < this.guiLeft + 140 + 91 && mouseY >= this.guiTop + 105 && mouseY <= this.guiTop + 105 + 116) {
                    this.scrollBarLogs.draw(mouseX, mouseY);
                }
            }
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.bank.title.actions"), this.guiLeft + 253, this.guiTop + 14, 0xFFFFFF, 0.75f, "left", false, "georamaSemiBold", 32);
            ClientEventHandler.STYLE.bindTexture("faction_bank");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 253, this.guiTop + 32, 295 * GUI_SCALE, 0 * GUI_SCALE, 102 * GUI_SCALE, 107 * GUI_SCALE, 102, 107, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 259, this.guiTop + 41, 403 * GUI_SCALE, 85 * GUI_SCALE, 89 * GUI_SCALE, 89 * GUI_SCALE, 89, 89, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            ArrayList owners = (ArrayList)factionActionsInfos.get("owners");
            ArrayList status = (ArrayList)factionActionsInfos.get("status");
            for (int index = 0; index < owners.size(); ++index) {
                String flagImageString;
                String ownerFactionId = (String)owners.get(index);
                int flagOffsetX = Integer.parseInt(flagCoords.get(index).split(",")[0]);
                int flagOffsetY = Integer.parseInt(flagCoords.get(index).split(",")[1]);
                int lockOffsetX = flagOffsetX + 5;
                int lockOffsetY = flagOffsetY + 1;
                if (!FactionGUI.isNumeric(ownerFactionId, true) && !ownerFactionId.equals(FactionGUI.factionInfos.get("id"))) {
                    if (!ClientProxy.flagsTexture.containsKey(ownerFactionId) && !(flagImageString = (String)((ArrayList)factionActionsInfos.get("flags")).get(index)).equals("null")) {
                        BufferedImage image = ModernGui.decodeToImage(flagImageString);
                        ClientProxy.flagsTexture.put(ownerFactionId, new DynamicTexture(image));
                    }
                    if (ClientProxy.flagsTexture.containsKey(ownerFactionId)) {
                        GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get(ownerFactionId).func_110552_b());
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + flagOffsetX, this.guiTop + flagOffsetY, 0.0f, 0.0f, 156, 78, 17, 10, 156.0f, 78.0f, false);
                    }
                } else if (!((String)status.get(index)).equals("unlocked")) {
                    if (!ClientProxy.flagsTexture.containsKey((String)FactionGUI.factionInfos.get("id"))) {
                        flagImageString = (String)FactionGUI.factionInfos.get("flagImage");
                        BufferedImage image = ModernGui.decodeToImage(flagImageString);
                        ClientProxy.flagsTexture.put((String)FactionGUI.factionInfos.get("id"), new DynamicTexture(image));
                    }
                    if (ClientProxy.flagsTexture.containsKey((String)FactionGUI.factionInfos.get("id"))) {
                        GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get((String)FactionGUI.factionInfos.get("id")).func_110552_b());
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + flagOffsetX, this.guiTop + flagOffsetY, 0.0f, 0.0f, 156, 78, 17, 10, 156.0f, 78.0f, false);
                    }
                }
                ClientEventHandler.STYLE.bindTexture("faction_bank");
                if (((String)status.get(index)).equals("unlocked")) {
                    if (mouseX >= this.guiLeft + flagOffsetX && mouseX <= this.guiLeft + flagOffsetX + 17 && mouseY >= this.guiTop + flagOffsetY && mouseY <= this.guiTop + flagOffsetY + 10) {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + lockOffsetX, this.guiTop + lockOffsetY, 183 * GUI_SCALE, 6 * GUI_SCALE, 7 * GUI_SCALE, 8 * GUI_SCALE, 7, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    } else {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + lockOffsetX, this.guiTop + lockOffsetY, 172 * GUI_SCALE, 6 * GUI_SCALE, 7 * GUI_SCALE, 8 * GUI_SCALE, 7, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    }
                } else {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + lockOffsetX, this.guiTop + lockOffsetY, 161 * GUI_SCALE, 6 * GUI_SCALE, 7 * GUI_SCALE, 8 * GUI_SCALE, 7, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                }
                if (mouseX < this.guiLeft + flagOffsetX || mouseX > this.guiLeft + flagOffsetX + 17 || mouseY < this.guiTop + flagOffsetY || mouseY > this.guiTop + flagOffsetY + 10) continue;
                if (FactionGUI.hasPermissions("actions") && (((String)FactionGUI.factionInfos.get("playerWhoSeeFactionId")).equals(ownerFactionId) || FactionGUI.isNumeric(ownerFactionId, true) && ((Boolean)FactionGUI.factionInfos.get("isInCountry")).booleanValue())) {
                    tooltipToDraw.add(I18n.func_135053_a((String)("faction.actions.label.change." + (String)status.get(index))));
                    this.hoveredIndex = index;
                    this.hoveredOwnerFactionId = ownerFactionId;
                    this.hoveredStatus = (String)status.get(index);
                    this.hoveringStatus = true;
                    continue;
                }
                String factionName = !FactionGUI.isNumeric(ownerFactionId, true) ? (String)((ArrayList)factionActionsInfos.get("factionsName")).get(index) : (String)FactionGUI.factionInfos.get("nameColored");
                tooltipToDraw.add(factionName);
                tooltipToDraw.add(I18n.func_135053_a((String)"faction.actions.label.status") + ": " + I18n.func_135053_a((String)("faction.actions.status." + (String)status.get(index))));
                tooltipToDraw.add(I18n.func_135053_a((String)"faction.actions.label.price") + ": \u00a78" + factionActionsInfos.get("price") + "$");
                if (((String)status.get(index)).equals("locked") || ((String)FactionGUI.factionInfos.get("playerWhoSeeFactionId")).equals(this.hoveredOwnerFactionId) || FactionGUI.isNumeric(ownerFactionId, true) && ((Boolean)FactionGUI.factionInfos.get("isInCountry")).booleanValue()) continue;
                this.hoveredIndex = index;
                this.hoveredOwnerFactionId = ownerFactionId;
                this.hoveringFlag = true;
                ClientEventHandler.STYLE.bindTexture("faction_bank");
                if (!FactionGUI.hasPermissions("actions")) continue;
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
            }
            ClientEventHandler.STYLE.bindTexture("faction_bank");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 355, this.guiTop + 32, 0 * GUI_SCALE, blockActions.get((String)FactionGUI.factionInfos.get("actualRelation")) * GUI_SCALE, 96 * GUI_SCALE, 107 * GUI_SCALE, 96, 107, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.actions.total_dividendes"), this.guiLeft + 359, this.guiTop + 40, 0xFFFFFF, 0.5f, "left", false, "georamaRegular", 25);
            ModernGui.drawScaledStringCustomFont(factionActionsInfos.get("totalDividendes") + "$", this.guiLeft + 359, this.guiTop + 47, 0xFFFFFF, 0.75f, "left", false, "georamaSemiBold", 25);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.actions.available"), this.guiLeft + 359, this.guiTop + 65, 0xFFFFFF, 0.5f, "left", false, "georamaRegular", 25);
            ModernGui.drawScaledStringCustomFont(factionActionsInfos.get("availableActions") + " action(s)", this.guiLeft + 359, this.guiTop + 72, 0xFFFFFF, 0.75f, "left", false, "georamaSemiBold", 25);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.actions.price"), this.guiLeft + 359, this.guiTop + 90, 0xFFFFFF, 0.5f, "left", false, "georamaRegular", 25);
            ModernGui.drawScaledStringCustomFont(factionActionsInfos.get("price") + "$", this.guiLeft + 359, this.guiTop + 97, 0xFFFFFF, 0.75f, "left", false, "georamaSemiBold", 25);
            ClientEventHandler.STYLE.bindTexture("faction_bank");
            if (mouseX >= this.guiLeft + 360 && mouseX <= this.guiLeft + 360 + 85 && mouseY >= this.guiTop + 115 && mouseY <= this.guiTop + 115 + 9) {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 360, this.guiTop + 115, 200 * GUI_SCALE, 167 * GUI_SCALE, 85 * GUI_SCALE, 9 * GUI_SCALE, 85, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.bank.owned_actions"), this.guiLeft + 360 + 43, this.guiTop + 117, 2234425, 0.5f, "center", false, "georamaMedium", 25);
                this.hoveredAction = "see_owned_actions";
            } else {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 360, this.guiTop + 115, 200 * GUI_SCALE, 180 * GUI_SCALE, 85 * GUI_SCALE, 9 * GUI_SCALE, 85, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.bank.owned_actions"), this.guiLeft + 360 + 43, this.guiTop + 117, 0xFFFFFF, 0.5f, "center", false, "georamaMedium", 25);
            }
            ModernGui.drawScaledStringCustomFont("\u00a7o" + I18n.func_135053_a((String)"faction.actions.how_to"), this.guiLeft + 359, this.guiTop + 130, 0xFFFFFF, 0.5f, "left", false, "georamaRegular", 25);
            if (mouseX > this.guiLeft + 359 && mouseX < this.guiLeft + 359 + 60 && mouseY > this.guiTop + 130 && mouseY < this.guiTop + 130 + 5) {
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
            ClientEventHandler.STYLE.bindTexture("faction_bank");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 253, this.guiTop + 146, 401 * GUI_SCALE, 0 * GUI_SCALE, 95 * GUI_SCALE, 75 * GUI_SCALE, 95, 75, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.actions.title.top"), this.guiLeft + 260, this.guiTop + 151, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 30);
            ClientEventHandler.STYLE.bindTexture("faction_bank");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 343, this.guiTop + 163, 309 * GUI_SCALE, 115 * GUI_SCALE, 2 * GUI_SCALE, 54 * GUI_SCALE, 2, 54, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            ArrayList actionsTotals = (ArrayList)factionActionsInfos.get("totals");
            GUIUtils.startGLScissor(this.guiLeft + 259, this.guiTop + 162, 81, 57);
            for (int l2 = 0; l2 < actionsTotals.size(); ++l2) {
                int offsetX = this.guiLeft + 259;
                Float offsetY2 = Float.valueOf((float)(this.guiTop + 162 + l2 * 10) + this.getSlideTotalDividendes());
                ModernGui.drawScaledStringCustomFont(((String)actionsTotals.get(l2)).split("##")[0], offsetX, offsetY2.intValue(), 10395075, 0.5f, "left", false, "georamaMedium", 22);
                ModernGui.drawScaledStringCustomFont(((String)actionsTotals.get(l2)).split("##")[1] + "$", offsetX + 81, offsetY2.intValue(), 0xFFFFFF, 0.5f, "right", false, "georamaMedium", 22);
            }
            GUIUtils.endGLScissor();
            if (mouseX > this.guiLeft + 253 && mouseX < this.guiLeft + 253 + 95 && mouseY > this.guiTop + 146 && mouseY < this.guiTop + 146 + 75) {
                this.scrollBarTotalDividendes.draw(mouseX, mouseY);
            }
            ClientEventHandler.STYLE.bindTexture("faction_bank");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 356, this.guiTop + 146, 401 * GUI_SCALE, 0 * GUI_SCALE, 95 * GUI_SCALE, 75 * GUI_SCALE, 95, 75, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.actions.title.logs"), this.guiLeft + 363, this.guiTop + 151, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 30);
            ClientEventHandler.STYLE.bindTexture("faction_bank");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 446, this.guiTop + 163, 309 * GUI_SCALE, 115 * GUI_SCALE, 2 * GUI_SCALE, 54 * GUI_SCALE, 2, 54, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            if (this.cachedDividendesLogs.size() == 0) {
                ArrayList actionsLogs = (ArrayList)factionActionsInfos.get("logs");
                for (int i = 0; i < actionsLogs.size(); ++i) {
                    HashMap<String, String> cachedLog = new HashMap<String, String>();
                    String line = (String)actionsLogs.get(i);
                    String amount = line.split("##")[1];
                    amount = "\u00a7a+\u00a7f" + amount;
                    amount = amount + "$";
                    Long time = Long.parseLong(line.split("##")[2]);
                    long now = System.currentTimeMillis();
                    long diff = now - time;
                    String date = "";
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
                                date = date + " " + seconds + " " + I18n.func_135053_a((String)"faction.common.seconds.short");
                            } else {
                                date = date + " " + minutes + " " + I18n.func_135053_a((String)"faction.common.minutes.short");
                            }
                        } else {
                            date = date + " " + hours + " " + I18n.func_135053_a((String)"faction.common.hours.short");
                        }
                    } else {
                        date = date + " " + days + " " + I18n.func_135053_a((String)"faction.common.days.short");
                    }
                    cachedLog.put("amount", amount);
                    cachedLog.put("date", date);
                    cachedLog.put("factionName", line.split("##")[0]);
                    this.cachedDividendesLogs.add(cachedLog);
                }
            }
            GUIUtils.startGLScissor(this.guiLeft + 362, this.guiTop + 162, 81, 57);
            for (int l3 = 0; l3 < this.cachedDividendesLogs.size(); ++l3) {
                int offsetX = this.guiLeft + 362;
                Float offsetY3 = Float.valueOf((float)(this.guiTop + 162 + l3 * 19) + this.getSlideLogsDividendes());
                ModernGui.drawScaledStringCustomFont(this.cachedDividendesLogs.get(l3).get("factionName"), offsetX, offsetY3.intValue(), 10395075, 0.5f, "left", false, "georamaMedium", 20);
                ModernGui.drawScaledStringCustomFont(this.cachedDividendesLogs.get(l3).get("amount"), offsetX, offsetY3.intValue() + 6, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 28);
                ModernGui.drawScaledStringCustomFont(this.cachedDividendesLogs.get(l3).get("date"), offsetX + 81, offsetY3.intValue() + 7, 10395075, 0.5f, "right", false, "georamaMedium", 20);
            }
            GUIUtils.endGLScissor();
            if (mouseX > this.guiLeft + 356 && mouseX < this.guiLeft + 356 + 95 && mouseY > this.guiTop + 146 && mouseY < this.guiTop + 146 + 75) {
                this.scrollBarLogsDividendes.draw(mouseX, mouseY);
            }
        }
        if (tooltipToDraw != null && !tooltipToDraw.isEmpty()) {
            this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
        }
        super.func_73863_a(mouseX, mouseY, partialTick);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        this.amountInput.func_73802_a(typedChar, keyCode);
        super.func_73869_a(typedChar, keyCode);
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && FactionGUI.factionInfos != null) {
            if (this.hoveredIndex != -1 && this.hoveredOwnerFactionId != null && this.hoveringFlag) {
                if (FactionGUI.hasPermissions("actions") && !((String)FactionGUI.factionInfos.get("playerWhoSeeFactionId")).equals(this.hoveredOwnerFactionId) && (System.currentTimeMillis() - Long.parseLong((String)factionActionsInfos.get("lastActionFromBuyer")) >= 18000000L || ((Boolean)factionActionsInfos.get("isOp")).booleanValue())) {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new BuyActionConfirmGui(this, this.hoveredIndex, this.hoveredOwnerFactionId, (String)factionActionsInfos.get("price")));
                }
            } else if (this.hoveredIndex != -1 && this.hoveredOwnerFactionId != null && this.hoveringStatus && this.hoveredStatus != null) {
                if (FactionGUI.hasPermissions("actions") && (((String)FactionGUI.factionInfos.get("playerWhoSeeFactionId")).equals(this.hoveredOwnerFactionId) || FactionGUI.isNumeric(this.hoveredOwnerFactionId, true) && ((Boolean)FactionGUI.factionInfos.get("isInCountry")).booleanValue())) {
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionLockActionPacket((String)FactionGUI.factionInfos.get("id"), this.hoveredIndex)));
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionActionsDataPacket((String)FactionGUI.factionInfos.get("name"))));
                }
            } else if (this.hoveredAction.equals("open_chest")) {
                if (((Boolean)FactionGUI.factionInfos.get("canOpenChest")).booleanValue() && FactionGUI.hasPermissions("chest_access") || FactionGUI.hasPermissions("admin")) {
                    if (Integer.parseInt((String)FactionGUI.factionInfos.get("chestLevel")) == 0) {
                        Minecraft.func_71410_x().field_71439_g.func_71035_c(I18n.func_135053_a((String)(FactionGUI.hasPermissions("admin") ? "faction.bank.chest_no" : "faction.bank.chest_no_level")));
                        return;
                    }
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new RemoteOpenFactionChestPacket((String)FactionGUI.factionInfos.get("id"), FactionGUI.hasPermissions("chest_access"), FactionGUI.hasPermissions("chest_access"), Integer.parseInt((String)FactionGUI.factionInfos.get("chestLevel")))));
                }
            } else if (this.hoveredAction.equals("deposit")) {
                if (!this.amountInput.func_73781_b().isEmpty() && FactionGUI.isNumeric(this.amountInput.func_73781_b(), true)) {
                    this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionBankActionPacket((String)FactionGUI.factionInfos.get("name"), this.amountInput.func_73781_b().replaceAll("^0+", ""), "deposit")));
                    this.amountInput.func_73782_a("0");
                }
            } else if (this.hoveredAction.equals("withdraw")) {
                if (!this.amountInput.func_73781_b().isEmpty() && FactionGUI.isNumeric(this.amountInput.func_73781_b(), true)) {
                    this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionBankActionPacket((String)FactionGUI.factionInfos.get("name"), this.amountInput.func_73781_b().replaceAll("^0+", ""), "take")));
                    this.amountInput.func_73782_a("0");
                }
            } else if (this.hoveredAction.equals("see_owned_actions")) {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new ActionsListGui());
            } else if (!this.hoveredPlayer.isEmpty()) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new ProfilGui(this.hoveredPlayer.split(" ")[1], ""));
            }
        }
        this.amountInput.func_73793_a(mouseX, mouseY, mouseButton);
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    private float getSlideLogs() {
        return ((ArrayList)factionBankInfos.get("logs")).size() > 5 ? (float)(-(((ArrayList)factionBankInfos.get("logs")).size() - 5) * 18) * this.scrollBarLogs.getSliderValue() : 0.0f;
    }

    private float getSlideMembers() {
        return ((ArrayList)factionBankInfos.get("members")).size() > 7 ? (float)(-(((ArrayList)factionBankInfos.get("members")).size() - 7) * 13) * this.scrollBarMembers.getSliderValue() : 0.0f;
    }

    private float getSlideTotalDividendes() {
        return ((ArrayList)factionActionsInfos.get("totals")).size() > 6 ? (float)(-(((ArrayList)factionActionsInfos.get("totals")).size() - 6) * 10) * this.scrollBarTotalDividendes.getSliderValue() : 0.0f;
    }

    private float getSlideLogsDividendes() {
        return ((ArrayList)factionActionsInfos.get("logs")).size() > 3 ? (float)(-(((ArrayList)factionActionsInfos.get("logs")).size() - 3) * 19) * this.scrollBarLogsDividendes.getSliderValue() : 0.0f;
    }

    static {
        cachedLogs = new ArrayList();
        flagCoords = Arrays.asList("306,51", "324,70", "324,90", "306,109", "283,109", "266,90", "266,70", "283,51");
        lockCoords = Arrays.asList("311,52", "328,70", "328,93", "311,109", "289,109", "272,94", "272,71", "289,52");
        blockSolde = new HashMap<String, Integer>(){
            {
                this.put("neutral", 109);
                this.put("enemy", 143);
                this.put("ally", 177);
                this.put("colony", 212);
            }
        };
        blockActions = new HashMap<String, Integer>(){
            {
                this.put("neutral", 0);
                this.put("enemy", 109);
                this.put("ally", 218);
                this.put("colony", 327);
            }
        };
    }
}

