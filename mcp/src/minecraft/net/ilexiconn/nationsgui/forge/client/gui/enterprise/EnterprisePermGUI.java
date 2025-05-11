/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 */
package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.TabbedEnterpriseGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseSavePermDataPacket;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;

public class EnterprisePermGUI
extends TabbedEnterpriseGUI {
    public boolean saved = false;
    public static ArrayList<String> enterprisePermInfos = new ArrayList();
    public String hoveredAction = "";
    public static List<String> roles = Arrays.asList("leader", "cadre", "employee");
    private GuiScrollBarFaction scrollBar;

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        this.scrollBar = new GuiScrollBarFaction(this.guiLeft + 377, this.guiTop + 49, 161);
        enterprisePermInfos = (ArrayList)EnterpriseGui.enterpriseInfos.get("permissions");
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        this.hoveredAction = "";
        ClientEventHandler.STYLE.bindTexture("enterprise_perm");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        this.drawScaledString(I18n.func_135053_a((String)"faction.perm.title"), this.guiLeft + 131, this.guiTop + 15, 0x191919, 1.4f, false, false);
        this.drawScaledString(I18n.func_135053_a((String)"enterprise.role.leader"), this.guiLeft + 255, this.guiTop + 35, 0, 0.8f, true, false);
        this.drawScaledString(I18n.func_135053_a((String)"enterprise.role.cadre"), this.guiLeft + 302, this.guiTop + 35, 0, 0.8f, true, false);
        this.drawScaledString(I18n.func_135053_a((String)"enterprise.role.employee"), this.guiLeft + 349, this.guiTop + 35, 0, 0.8f, true, false);
        if (enterprisePermInfos.size() > 0) {
            String tooltipToDraw = "";
            GUIUtils.startGLScissor(this.guiLeft + 132, this.guiTop + 45, 245, 161);
            int index = 0;
            for (String permissionNode : enterprisePermInfos) {
                int offsetX = this.guiLeft + 132;
                Float offsetY = Float.valueOf((float)(this.guiTop + 45 + index * 22) + this.getSlide());
                String permName = permissionNode.split("#")[0];
                String authorizedRoles = permissionNode.split("#")[1];
                ClientEventHandler.STYLE.bindTexture("enterprise_perm");
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 132, 45, 245, 22, 512.0f, 512.0f, false);
                this.drawScaledString(I18n.func_135053_a((String)("enterprise.perm.label." + permName)), offsetX + 4, offsetY.intValue() + 6, 0xFFFFFF, 1.0f, false, false);
                ClientEventHandler.STYLE.bindTexture("enterprise_perm");
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 83, offsetY.intValue() + 5, 148, 250, 10, 11, 512.0f, 512.0f, false);
                if (mouseX > offsetX + 83 && mouseX < offsetX + 83 + 10 && (float)mouseY > offsetY.floatValue() + 5.0f && (float)mouseY < offsetY.floatValue() + 5.0f + 11.0f) {
                    tooltipToDraw = I18n.func_135053_a((String)("enterprise.perm.tooltip." + permName));
                }
                ClientEventHandler.STYLE.bindTexture("enterprise_perm");
                int indexRole = 0;
                for (String role : roles) {
                    if (authorizedRoles.contains(role)) {
                        ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 116 + indexRole * 47, offsetY.intValue() + 3, 179, 250, 14, 15, 512.0f, 512.0f, false);
                        if (mouseX >= offsetX + 116 + indexRole * 47 && mouseX <= offsetX + 116 + indexRole * 47 + 14 && mouseY >= offsetY.intValue() + 3 && mouseY <= offsetY.intValue() + 3 + 15 && !role.equals("leader")) {
                            this.hoveredAction = permName + "#" + role + "#no";
                            this.saved = false;
                        }
                    } else {
                        ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 116 + indexRole * 47, offsetY.intValue() + 3, 164, 250, 14, 15, 512.0f, 512.0f, false);
                        if (mouseX >= offsetX + 116 + indexRole * 47 && mouseX <= offsetX + 116 + indexRole * 47 + 14 && mouseY >= offsetY.intValue() + 3 && mouseY <= offsetY.intValue() + 3 + 15 && !role.equals("leader")) {
                            this.hoveredAction = permName + "#" + role + "#yes";
                            this.saved = false;
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
            if (EnterpriseGui.hasPermission("perms")) {
                ClientEventHandler.STYLE.bindTexture("enterprise_perm");
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 284, this.guiTop + 216, 300, 248, 100, 15, 512.0f, 512.0f, false);
                this.drawScaledString(I18n.func_135053_a((String)"faction.perm.save"), this.guiLeft + 334, this.guiTop + 220, 0xFFFFFF, 1.1f, true, false);
            }
        }
    }

    private float getSlide() {
        return enterprisePermInfos.size() > 7 ? (float)(-(enterprisePermInfos.size() - 7) * 22) * this.scrollBar.getSliderValue() : 0.0f;
    }

    public void drawTooltip(String text, int mouseX, int mouseY) {
        int mouseXGui = mouseX - this.guiLeft;
        int mouseYGui = mouseY - this.guiTop;
        this.drawHoveringText(Arrays.asList(text), mouseX, mouseY, this.field_73886_k);
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        super.func_73864_a(mouseX, mouseY, mouseButton);
        if (mouseButton == 0) {
            if (!this.saved && !this.hoveredAction.isEmpty() && EnterpriseGui.hasPermission("perms") && mouseY > this.guiTop + 45 && mouseY < this.guiTop + 45 + 161) {
                String permName = this.hoveredAction.split("#")[0];
                String roleName = this.hoveredAction.split("#")[1];
                String state = this.hoveredAction.split("#")[2];
                ArrayList<String> editedPermInfos = new ArrayList<String>();
                for (String permissionNode : enterprisePermInfos) {
                    if (permissionNode.split("#")[0].equalsIgnoreCase(permName)) {
                        String currentAuthorizedRoles = permissionNode.split("#")[1];
                        if (state.equalsIgnoreCase("yes") && !currentAuthorizedRoles.contains(roleName)) {
                            currentAuthorizedRoles = currentAuthorizedRoles + "," + roleName;
                        } else if (state.equalsIgnoreCase("no") && currentAuthorizedRoles.contains(roleName)) {
                            currentAuthorizedRoles = currentAuthorizedRoles.replace(roleName, "").replaceAll("^,", "").replaceAll(",$", "").replaceAll(",,", "");
                        }
                        editedPermInfos.add(permissionNode.split("#")[0] + "#" + currentAuthorizedRoles);
                        continue;
                    }
                    editedPermInfos.add(permissionNode);
                }
                enterprisePermInfos = editedPermInfos;
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
            }
            if (!this.saved && mouseX > this.guiLeft + 284 && mouseX < this.guiLeft + 284 + 100 && mouseY > this.guiTop + 216 && mouseY < this.guiTop + 216 + 15 && EnterpriseGui.hasPermission("perms")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.successful_hit", 1.0f, 1.0f);
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new EnterpriseSavePermDataPacket((String)EnterpriseGui.enterpriseInfos.get("name"), enterprisePermInfos)));
                this.saved = true;
            }
        }
    }
}

