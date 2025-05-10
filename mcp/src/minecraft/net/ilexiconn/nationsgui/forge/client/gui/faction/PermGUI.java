package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.TabbedFactionGUI_OLD;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionSavePermDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionSettingsDataPacket;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;

public class PermGUI extends TabbedFactionGUI_OLD
{
    public static boolean loaded = false;
    public boolean saved = false;
    public static HashMap<String, HashMap<String, Object>> factionPermInfos;
    public HashMap<String, String> availableActions = new HashMap();
    public String hoveredAction = "";
    public static List<String> rolesIds = Arrays.asList(new String[] {"70", "60", "50", "45", "40", "35", "30", "20", "10"});
    public static String rolesNames = "\u00a72LEA \u00a72OFF \u00a72MEM \u00a72REC \u00a73COL \u00a75ALL \u00a7dTRU \u00a70NEU \u00a7cENE";
    private GuiScrollBarFaction scrollBar;

    public PermGUI(EntityPlayer player)
    {
        super(player);
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        loaded = false;
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionSettingsDataPacket((String)FactionGui_OLD.factionInfos.get("name"))));
        this.scrollBar = new GuiScrollBarFaction((float)(this.guiLeft + 377), (float)(this.guiTop + 49), 161);
    }

    public void drawScreen(int mouseX, int mouseY)
    {
        this.hoveredAction = "";
        ClientEventHandler.STYLE.bindTexture("faction_perm");
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);

        if (loaded)
        {
            this.drawScaledString(I18n.getString("faction.perm.title"), this.guiLeft + 131, this.guiTop + 16, 1644825, 1.4F, false, false);
            this.drawScaledString(rolesNames, this.guiLeft + 218, this.guiTop + 35, 16777215, 0.8F, false, false);

            if (factionPermInfos.size() > 0)
            {
                String tooltipToDraw = "";
                GUIUtils.startGLScissor(this.guiLeft + 132, this.guiTop + 45, 245, 161);
                int index = 0;

                for (Iterator it = factionPermInfos.entrySet().iterator(); it.hasNext(); ++index)
                {
                    int offsetX = this.guiLeft + 132;
                    Float offsetY = Float.valueOf((float)(this.guiTop + 45 + index * 22) + this.getSlide());
                    Entry pair = (Entry)it.next();
                    String permName = (String)pair.getKey();
                    HashMap infos = (HashMap)pair.getValue();
                    ArrayList authorizedRoleIds = (ArrayList)infos.get("permissions");
                    ClientEventHandler.STYLE.bindTexture("faction_perm");
                    ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)offsetY.intValue(), 132, 45, 245, 22, 512.0F, 512.0F, false);
                    this.drawScaledString(permName.substring(0, 1).toUpperCase() + permName.substring(1), offsetX + 4, offsetY.intValue() + 6, 16777215, 1.0F, false, false);
                    ClientEventHandler.STYLE.bindTexture("faction_perm");
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 65), (float)(offsetY.intValue() + 5), 148, 250, 10, 11, 512.0F, 512.0F, false);

                    if (mouseX > offsetX + 65 && mouseX < offsetX + 65 + 10 && (float)mouseY > offsetY.floatValue() + 5.0F && (float)mouseY < offsetY.floatValue() + 5.0F + 11.0F)
                    {
                        tooltipToDraw = (String)infos.get("description");
                    }

                    ClientEventHandler.STYLE.bindTexture("faction_perm");
                    int indexRole = 0;

                    for (Iterator var13 = rolesIds.iterator(); var13.hasNext(); ++indexRole)
                    {
                        String roleId = (String)var13.next();

                        if (authorizedRoleIds.contains(roleId))
                        {
                            ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 83 + indexRole * 18), (float)(offsetY.intValue() + 3), 179, 250, 14, 15, 512.0F, 512.0F, false);

                            if (mouseX >= offsetX + 83 + indexRole * 18 && mouseX <= offsetX + 83 + indexRole * 18 + 14 && mouseY >= offsetY.intValue() + 3 && mouseY <= offsetY.intValue() + 3 + 15 && (!roleId.equals("70") || ((Boolean)FactionGui_OLD.factionInfos.get("isLeader")).booleanValue()))
                            {
                                this.hoveredAction = permName + "#" + roleId + "#no";
                                this.saved = false;
                            }
                        }
                        else
                        {
                            boolean forbidden = false;

                            if (Integer.parseInt(roleId) <= 45)
                            {
                                if (Arrays.asList(new String[] {"assault", "join assault", "wars", "relations", "actions", "territory", "locations", "perms", "taxes", "access", "setwarp", "sethome", "settings", "kick"}).contains(permName))
                                {
                                    forbidden = true;
                                }
                            }
                            else if (Integer.parseInt(roleId) <= 50 && Arrays.asList(new String[] {"wars", "actions", "relations", "locations", "perms", "setwarp", "sethome", "settings", "kick"}).contains(permName))
                            {
                                forbidden = true;
                            }

                            if (!forbidden)
                            {
                                ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 83 + indexRole * 18), (float)(offsetY.intValue() + 3), 164, 250, 14, 15, 512.0F, 512.0F, false);

                                if (mouseX >= offsetX + 83 + indexRole * 18 && mouseX <= offsetX + 83 + indexRole * 18 + 14 && mouseY >= offsetY.intValue() + 3 && mouseY <= offsetY.intValue() + 3 + 15 && (!roleId.equals("70") || ((Boolean)FactionGui_OLD.factionInfos.get("isLeader")).booleanValue()))
                                {
                                    this.hoveredAction = permName + "#" + roleId + "#yes";
                                    this.saved = false;
                                }
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

                if (FactionGui_OLD.hasPermissions("perms"))
                {
                    ClientEventHandler.STYLE.bindTexture("faction_perm");
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 284), (float)(this.guiTop + 216), 300, 248, 100, 15, 512.0F, 512.0F, false);
                    this.drawScaledString(I18n.getString("faction.perm.save"), this.guiLeft + 334, this.guiTop + 220, 16777215, 1.1F, true, false);
                }
            }
        }
    }

    private float getSlide()
    {
        return factionPermInfos.size() > 7 ? (float)(-(factionPermInfos.size() - 7) * 22) * this.scrollBar.getSliderValue() : 0.0F;
    }

    public void drawTooltip(String text, int mouseX, int mouseY)
    {
        int var10000 = mouseX - this.guiLeft;
        var10000 = mouseY - this.guiTop;
        this.drawHoveringText(Arrays.asList(new String[] {text.substring(0, 1).toUpperCase() + text.substring(1)}), mouseX, mouseY, this.fontRenderer);
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (mouseButton == 0)
        {
            if (!this.saved && !this.hoveredAction.isEmpty() && FactionGui_OLD.hasPermissions("perms") && mouseY > this.guiTop + 45 && mouseY < this.guiTop + 45 + 161)
            {
                String hashMapForPacket = this.hoveredAction.split("#")[0];
                String it = this.hoveredAction.split("#")[1];
                String pair = this.hoveredAction.split("#")[2];
                HashMap permInfos = (HashMap)factionPermInfos.get(hashMapForPacket);
                List permissions = (List)permInfos.get("permissions");

                if (pair.equals("yes"))
                {
                    if (!permissions.contains(it))
                    {
                        permissions.add(it);
                    }
                }
                else if (permissions.contains(it))
                {
                    permissions.remove(it);
                }

                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                ((HashMap)factionPermInfos.get(hashMapForPacket)).put("permissions", permissions);
            }

            if (!this.saved && mouseX > this.guiLeft + 284 && mouseX < this.guiLeft + 284 + 100 && mouseY > this.guiTop + 216 && mouseY < this.guiTop + 216 + 15 && FactionGui_OLD.hasPermissions("perms"))
            {
                HashMap hashMapForPacket1 = new HashMap();
                Iterator it1 = factionPermInfos.entrySet().iterator();

                while (it1.hasNext())
                {
                    Entry pair1 = (Entry)it1.next();
                    hashMapForPacket1.put((String)pair1.getKey(), (ArrayList)((ArrayList)((HashMap)pair1.getValue()).get("permissions")));
                }

                hashMapForPacket1.put("factionName", FactionGui_OLD.factionInfos.get("name"));
                this.mc.sndManager.playSoundFX("random.successful_hit", 1.0F, 1.0F);
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionSavePermDataPacket(hashMapForPacket1)));
                this.saved = true;
            }
        }
    }
}
