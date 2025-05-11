/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.packet.Packet
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.TabbedFactionGUI_OLD;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGui_OLD;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionSavePermDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionSettingsDataPacket;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;

public class PermGUI
extends TabbedFactionGUI_OLD {
    public static boolean loaded = false;
    public boolean saved = false;
    public static HashMap<String, HashMap<String, Object>> factionPermInfos;
    public HashMap<String, String> availableActions = new HashMap();
    public String hoveredAction = "";
    public static List<String> rolesIds;
    public static String rolesNames;
    private GuiScrollBarFaction scrollBar;

    public PermGUI(EntityPlayer player) {
        super(player);
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        loaded = false;
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionSettingsDataPacket((String)FactionGui_OLD.factionInfos.get("name"))));
        this.scrollBar = new GuiScrollBarFaction(this.guiLeft + 377, this.guiTop + 49, 161);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        this.hoveredAction = "";
        ClientEventHandler.STYLE.bindTexture("faction_perm");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        if (loaded) {
            this.drawScaledString(I18n.func_135053_a((String)"faction.perm.title"), this.guiLeft + 131, this.guiTop + 16, 0x191919, 1.4f, false, false);
            this.drawScaledString(rolesNames, this.guiLeft + 218, this.guiTop + 35, 0xFFFFFF, 0.8f, false, false);
            if (factionPermInfos.size() > 0) {
                String tooltipToDraw = "";
                GUIUtils.startGLScissor(this.guiLeft + 132, this.guiTop + 45, 245, 161);
                int index = 0;
                Iterator<Map.Entry<String, HashMap<String, Object>>> it = factionPermInfos.entrySet().iterator();
                while (it.hasNext()) {
                    int offsetX = this.guiLeft + 132;
                    Float offsetY = Float.valueOf((float)(this.guiTop + 45 + index * 22) + this.getSlide());
                    Map.Entry<String, HashMap<String, Object>> pair = it.next();
                    String permName = pair.getKey();
                    HashMap<String, Object> infos = pair.getValue();
                    ArrayList authorizedRoleIds = (ArrayList)infos.get("permissions");
                    ClientEventHandler.STYLE.bindTexture("faction_perm");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 132, 45, 245, 22, 512.0f, 512.0f, false);
                    this.drawScaledString(permName.substring(0, 1).toUpperCase() + permName.substring(1), offsetX + 4, offsetY.intValue() + 6, 0xFFFFFF, 1.0f, false, false);
                    ClientEventHandler.STYLE.bindTexture("faction_perm");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 65, offsetY.intValue() + 5, 148, 250, 10, 11, 512.0f, 512.0f, false);
                    if (mouseX > offsetX + 65 && mouseX < offsetX + 65 + 10 && (float)mouseY > offsetY.floatValue() + 5.0f && (float)mouseY < offsetY.floatValue() + 5.0f + 11.0f) {
                        tooltipToDraw = (String)infos.get("description");
                    }
                    ClientEventHandler.STYLE.bindTexture("faction_perm");
                    int indexRole = 0;
                    for (String roleId : rolesIds) {
                        if (authorizedRoleIds.contains(roleId)) {
                            ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 83 + indexRole * 18, offsetY.intValue() + 3, 179, 250, 14, 15, 512.0f, 512.0f, false);
                            if (mouseX >= offsetX + 83 + indexRole * 18 && mouseX <= offsetX + 83 + indexRole * 18 + 14 && mouseY >= offsetY.intValue() + 3 && mouseY <= offsetY.intValue() + 3 + 15 && (!roleId.equals("70") || ((Boolean)FactionGui_OLD.factionInfos.get("isLeader")).booleanValue())) {
                                this.hoveredAction = permName + "#" + roleId + "#no";
                                this.saved = false;
                            }
                        } else {
                            boolean forbidden = false;
                            if (Integer.parseInt(roleId) <= 45) {
                                if (Arrays.asList("assault", "join assault", "wars", "relations", "actions", "territory", "locations", "perms", "taxes", "access", "setwarp", "sethome", "settings", "kick").contains(permName)) {
                                    forbidden = true;
                                }
                            } else if (Integer.parseInt(roleId) <= 50 && Arrays.asList("wars", "actions", "relations", "locations", "perms", "setwarp", "sethome", "settings", "kick").contains(permName)) {
                                forbidden = true;
                            }
                            if (!forbidden) {
                                ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 83 + indexRole * 18, offsetY.intValue() + 3, 164, 250, 14, 15, 512.0f, 512.0f, false);
                                if (mouseX >= offsetX + 83 + indexRole * 18 && mouseX <= offsetX + 83 + indexRole * 18 + 14 && mouseY >= offsetY.intValue() + 3 && mouseY <= offsetY.intValue() + 3 + 15 && (!roleId.equals("70") || ((Boolean)FactionGui_OLD.factionInfos.get("isLeader")).booleanValue())) {
                                    this.hoveredAction = permName + "#" + roleId + "#yes";
                                    this.saved = false;
                                }
                            }
                        }
                        ++indexRole;
                    }
                    ++index;
                }
                GUIUtils.endGLScissor();
                this.scrollBar.draw(mouseX, mouseY);
                if (!tooltipToDraw.isEmpty()) {
                    this.drawTooltip(tooltipToDraw, mouseX, mouseY);
                }
                if (FactionGui_OLD.hasPermissions("perms")) {
                    ClientEventHandler.STYLE.bindTexture("faction_perm");
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 284, this.guiTop + 216, 300, 248, 100, 15, 512.0f, 512.0f, false);
                    this.drawScaledString(I18n.func_135053_a((String)"faction.perm.save"), this.guiLeft + 334, this.guiTop + 220, 0xFFFFFF, 1.1f, true, false);
                }
            }
        }
    }

    private float getSlide() {
        return factionPermInfos.size() > 7 ? (float)(-(factionPermInfos.size() - 7) * 22) * this.scrollBar.getSliderValue() : 0.0f;
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
            if (!this.saved && !this.hoveredAction.isEmpty() && FactionGui_OLD.hasPermissions("perms") && mouseY > this.guiTop + 45 && mouseY < this.guiTop + 45 + 161) {
                String permName = this.hoveredAction.split("#")[0];
                String roleId = this.hoveredAction.split("#")[1];
                String state = this.hoveredAction.split("#")[2];
                HashMap<String, Object> permInfos = factionPermInfos.get(permName);
                List permissions = (List)permInfos.get("permissions");
                if (state.equals("yes")) {
                    if (!permissions.contains(roleId)) {
                        permissions.add(roleId);
                    }
                } else if (permissions.contains(roleId)) {
                    permissions.remove(roleId);
                }
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                factionPermInfos.get(permName).put("permissions", permissions);
            }
            if (!this.saved && mouseX > this.guiLeft + 284 && mouseX < this.guiLeft + 284 + 100 && mouseY > this.guiTop + 216 && mouseY < this.guiTop + 216 + 15 && FactionGui_OLD.hasPermissions("perms")) {
                HashMap<String, Object> hashMapForPacket = new HashMap<String, Object>();
                for (Map.Entry<String, HashMap<String, Object>> pair : factionPermInfos.entrySet()) {
                    hashMapForPacket.put(pair.getKey(), (ArrayList)pair.getValue().get("permissions"));
                }
                hashMapForPacket.put("factionName", FactionGui_OLD.factionInfos.get("name"));
                this.field_73882_e.field_71416_A.func_77366_a("random.successful_hit", 1.0f, 1.0f);
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionSavePermDataPacket(hashMapForPacket)));
                this.saved = true;
            }
        }
    }

    static {
        rolesIds = Arrays.asList("70", "60", "50", "45", "40", "35", "30", "20", "10");
        rolesNames = "\u00a72LEA \u00a72OFF \u00a72MEM \u00a72REC \u00a73COL \u00a75ALL \u00a7dTRU \u00a70NEU \u00a7cENE";
    }
}

