package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseSavePermDataPacket;
import net.minecraft.client.resources.I18n;

public class EnterprisePermGUI extends TabbedEnterpriseGUI
{
    public boolean saved = false;
    public static ArrayList<String> enterprisePermInfos = new ArrayList();
    public String hoveredAction = "";
    public static List<String> roles = Arrays.asList(new String[] {"leader", "cadre", "employee"});
    private GuiScrollBarFaction scrollBar;

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.scrollBar = new GuiScrollBarFaction((float)(this.guiLeft + 377), (float)(this.guiTop + 49), 161);
        enterprisePermInfos = (ArrayList)EnterpriseGui.enterpriseInfos.get("permissions");
    }

    public void drawScreen(int mouseX, int mouseY)
    {
        this.hoveredAction = "";
        ClientEventHandler.STYLE.bindTexture("enterprise_perm");
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
        this.drawScaledString(I18n.getString("faction.perm.title"), this.guiLeft + 131, this.guiTop + 15, 1644825, 1.4F, false, false);
        this.drawScaledString(I18n.getString("enterprise.role.leader"), this.guiLeft + 255, this.guiTop + 35, 0, 0.8F, true, false);
        this.drawScaledString(I18n.getString("enterprise.role.cadre"), this.guiLeft + 302, this.guiTop + 35, 0, 0.8F, true, false);
        this.drawScaledString(I18n.getString("enterprise.role.employee"), this.guiLeft + 349, this.guiTop + 35, 0, 0.8F, true, false);

        if (enterprisePermInfos.size() > 0)
        {
            String tooltipToDraw = "";
            GUIUtils.startGLScissor(this.guiLeft + 132, this.guiTop + 45, 245, 161);
            int index = 0;

            for (Iterator var5 = enterprisePermInfos.iterator(); var5.hasNext(); ++index)
            {
                String permissionNode = (String)var5.next();
                int offsetX = this.guiLeft + 132;
                Float offsetY = Float.valueOf((float)(this.guiTop + 45 + index * 22) + this.getSlide());
                String permName = permissionNode.split("#")[0];
                String authorizedRoles = permissionNode.split("#")[1];
                ClientEventHandler.STYLE.bindTexture("enterprise_perm");
                ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)offsetY.intValue(), 132, 45, 245, 22, 512.0F, 512.0F, false);
                this.drawScaledString(I18n.getString("enterprise.perm.label." + permName), offsetX + 4, offsetY.intValue() + 6, 16777215, 1.0F, false, false);
                ClientEventHandler.STYLE.bindTexture("enterprise_perm");
                ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 83), (float)(offsetY.intValue() + 5), 148, 250, 10, 11, 512.0F, 512.0F, false);

                if (mouseX > offsetX + 83 && mouseX < offsetX + 83 + 10 && (float)mouseY > offsetY.floatValue() + 5.0F && (float)mouseY < offsetY.floatValue() + 5.0F + 11.0F)
                {
                    tooltipToDraw = I18n.getString("enterprise.perm.tooltip." + permName);
                }

                ClientEventHandler.STYLE.bindTexture("enterprise_perm");
                int indexRole = 0;

                for (Iterator var12 = roles.iterator(); var12.hasNext(); ++indexRole)
                {
                    String role = (String)var12.next();

                    if (authorizedRoles.contains(role))
                    {
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 116 + indexRole * 47), (float)(offsetY.intValue() + 3), 179, 250, 14, 15, 512.0F, 512.0F, false);

                        if (mouseX >= offsetX + 116 + indexRole * 47 && mouseX <= offsetX + 116 + indexRole * 47 + 14 && mouseY >= offsetY.intValue() + 3 && mouseY <= offsetY.intValue() + 3 + 15 && !role.equals("leader"))
                        {
                            this.hoveredAction = permName + "#" + role + "#no";
                            this.saved = false;
                        }
                    }
                    else
                    {
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 116 + indexRole * 47), (float)(offsetY.intValue() + 3), 164, 250, 14, 15, 512.0F, 512.0F, false);

                        if (mouseX >= offsetX + 116 + indexRole * 47 && mouseX <= offsetX + 116 + indexRole * 47 + 14 && mouseY >= offsetY.intValue() + 3 && mouseY <= offsetY.intValue() + 3 + 15 && !role.equals("leader"))
                        {
                            this.hoveredAction = permName + "#" + role + "#yes";
                            this.saved = false;
                        }
                    }
                }
            }

            GUIUtils.endGLScissor();
            this.scrollBar.draw(mouseX, mouseY);

            if (!tooltipToDraw.isEmpty())
            {
                this.drawTooltip(tooltipToDraw, mouseX, mouseY);
            }

            if (EnterpriseGui.hasPermission("perms"))
            {
                ClientEventHandler.STYLE.bindTexture("enterprise_perm");
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 284), (float)(this.guiTop + 216), 300, 248, 100, 15, 512.0F, 512.0F, false);
                this.drawScaledString(I18n.getString("faction.perm.save"), this.guiLeft + 334, this.guiTop + 220, 16777215, 1.1F, true, false);
            }
        }
    }

    private float getSlide()
    {
        return enterprisePermInfos.size() > 7 ? (float)(-(enterprisePermInfos.size() - 7) * 22) * this.scrollBar.getSliderValue() : 0.0F;
    }

    public void drawTooltip(String text, int mouseX, int mouseY)
    {
        int var10000 = mouseX - this.guiLeft;
        var10000 = mouseY - this.guiTop;
        this.drawHoveringText(Arrays.asList(new String[] {text}), mouseX, mouseY, this.fontRenderer);
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (mouseButton == 0)
        {
            if (!this.saved && !this.hoveredAction.isEmpty() && EnterpriseGui.hasPermission("perms") && mouseY > this.guiTop + 45 && mouseY < this.guiTop + 45 + 161)
            {
                String permName = this.hoveredAction.split("#")[0];
                String roleName = this.hoveredAction.split("#")[1];
                String state = this.hoveredAction.split("#")[2];
                ArrayList editedPermInfos = new ArrayList();
                Iterator var8 = enterprisePermInfos.iterator();

                while (var8.hasNext())
                {
                    String permissionNode = (String)var8.next();

                    if (!permissionNode.split("#")[0].equalsIgnoreCase(permName))
                    {
                        editedPermInfos.add(permissionNode);
                    }
                    else
                    {
                        String currentAuthorizedRoles = permissionNode.split("#")[1];

                        if (state.equalsIgnoreCase("yes") && !currentAuthorizedRoles.contains(roleName))
                        {
                            currentAuthorizedRoles = currentAuthorizedRoles + "," + roleName;
                        }
                        else if (state.equalsIgnoreCase("no") && currentAuthorizedRoles.contains(roleName))
                        {
                            currentAuthorizedRoles = currentAuthorizedRoles.replace(roleName, "").replaceAll("^,", "").replaceAll(",$", "").replaceAll(",,", "");
                        }

                        editedPermInfos.add(permissionNode.split("#")[0] + "#" + currentAuthorizedRoles);
                    }
                }

                enterprisePermInfos = editedPermInfos;
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
            }

            if (!this.saved && mouseX > this.guiLeft + 284 && mouseX < this.guiLeft + 284 + 100 && mouseY > this.guiTop + 216 && mouseY < this.guiTop + 216 + 15 && EnterpriseGui.hasPermission("perms"))
            {
                this.mc.sndManager.playSoundFX("random.successful_hit", 1.0F, 1.0F);
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterpriseSavePermDataPacket((String)EnterpriseGui.enterpriseInfos.get("name"), enterprisePermInfos)));
                this.saved = true;
            }
        }
    }
}
