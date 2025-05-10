package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.TabbedFactionGUI_OLD;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionBankDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.RemoteOpenFactionChestPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;

public class BankGUI_OLD extends TabbedFactionGUI_OLD
{
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

    public BankGUI_OLD(EntityPlayer player, boolean doRefresh)
    {
        super(player);
        this.doRefresh = doRefresh;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        loaded = false;

        if (this.doRefresh)
        {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionBankDataPacket((String)FactionGui_OLD.factionInfos.get("id"))));
        }

        this.scrollBarMembers = new GuiScrollBarFaction((float)(this.guiLeft + 248), (float)(this.guiTop + 145), 80);
        this.scrollBarLogs = new GuiScrollBarFaction((float)(this.guiLeft + 377), (float)(this.guiTop + 145), 80);
        this.cachedLogs = new ArrayList();
        this.cachedMembers = new ArrayList();
        this.chestButton = new GuiButton(0, this.guiLeft + 10, this.guiTop + 165, 100, 20, I18n.getString("faction.bank.chest_button"));
        this.actionButton = new GuiButton(1, this.guiLeft + 10, this.guiTop + 188, 100, 20, I18n.getString("faction.bank.action_button"));

        if ((!FactionGui_OLD.hasPermissions("chest_access") || !((Boolean)FactionGui_OLD.factionInfos.get("canOpenChest")).booleanValue()) && !FactionGui_OLD.hasPermissions("admin"))
        {
            this.chestButton.enabled = false;
        }
    }

    public void drawScreen(int mouseX, int mouseY)
    {
        ClientEventHandler.STYLE.bindTexture("faction_bank");
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);

        if (loaded)
        {
            this.drawScaledString(I18n.getString("faction.bank.title") + " " + FactionGui_OLD.factionInfos.get("name"), this.guiLeft + 259, this.guiTop + 25, 3818599, 1.7F, true, false);
            this.drawScaledString(factionBankInfos.get("bank") + "$", this.guiLeft + 257, this.guiTop + 107, 16777215, 1.5F, true, false);
            this.drawScaledString(I18n.getString("faction.bank.members"), this.guiLeft + 130, this.guiTop + 132, 3818599, 0.8F, false, false);
            this.drawScaledString(I18n.getString("faction.bank.transactions"), this.guiLeft + 259, this.guiTop + 132, 3818599, 0.8F, false, false);
            String tooltipToDraw = "";

            if (factionBankInfos.get("logs") != null && ((ArrayList)factionBankInfos.get("logs")).size() > 0)
            {
                int j;

                if (this.cachedLogs.size() == 0)
                {
                    String offsetY;

                    for (j = 0; j < ((ArrayList)factionBankInfos.get("logs")).size(); ++j)
                    {
                        HashMap offsetX = new HashMap();
                        offsetY = (String)((ArrayList)factionBankInfos.get("logs")).get(j);

                        if (offsetY.split("#").length == 3)
                        {
                            String amount = offsetY.split("#")[0];

                            if (amount.contains("-"))
                            {
                                amount = amount.replace("-", "\u00a74-\u00a77");
                            }
                            else
                            {
                                amount = "\u00a7a+\u00a77" + amount;
                            }

                            amount = amount + "$";
                            Long time = Long.valueOf(Long.parseLong(offsetY.split("#")[1]));
                            long now = System.currentTimeMillis();
                            long diff = now - time.longValue();
                            String date = "\u00a78" + I18n.getString("faction.bank.date_1");
                            long days = diff / 86400000L;
                            long hours = 0L;
                            long minutes = 0L;
                            long seconds = 0L;

                            if (days == 0L)
                            {
                                hours = diff / 3600000L;

                                if (hours == 0L)
                                {
                                    minutes = diff / 60000L;

                                    if (minutes == 0L)
                                    {
                                        seconds = diff / 1000L;
                                        date = date + " " + seconds + " " + I18n.getString("faction.common.seconds") + " " + I18n.getString("faction.bank.date_2");
                                    }
                                    else
                                    {
                                        date = date + " " + minutes + " " + I18n.getString("faction.common.minutes") + " " + I18n.getString("faction.bank.date_2");
                                    }
                                }
                                else
                                {
                                    date = date + " " + hours + " " + I18n.getString("faction.common.hours") + " " + I18n.getString("faction.bank.date_2");
                                }
                            }
                            else
                            {
                                date = date + " " + days + " " + I18n.getString("faction.common.days") + " " + I18n.getString("faction.bank.date_2");
                            }

                            String playerName = offsetY.split("#")[2];
                            offsetX.put("amount", amount);
                            offsetX.put("date", date);
                            offsetX.put("playerName", playerName);
                            this.cachedLogs.add(offsetX);
                        }
                    }

                    for (j = 0; j < ((ArrayList)factionBankInfos.get("members")).size(); ++j)
                    {
                        String var23 = ((String)((ArrayList)factionBankInfos.get("members")).get(j)).split("#")[1];
                        offsetY = ((String)((ArrayList)factionBankInfos.get("members")).get(j)).split("#")[0];
                        this.cachedMembers.add("[" + offsetY.substring(0, 1).toUpperCase() + offsetY.substring(1) + "] " + var23);
                    }
                }

                this.hoveredPlayer = "";
                GUIUtils.startGLScissor(this.guiLeft + 132, this.guiTop + 142, 116, 86);
                int var24;
                Float var25;

                for (j = 0; j < this.cachedMembers.size(); ++j)
                {
                    var24 = this.guiLeft + 132;
                    var25 = Float.valueOf((float)(this.guiTop + 142 + j * 20) + this.getSlideMembers());

                    if (mouseX > var24 && mouseX < var24 + 116 && (float)mouseY > var25.floatValue() + 142.0F && (float)mouseY < var25.floatValue() + 20.0F)
                    {
                        this.hoveredPlayer = (String)this.cachedMembers.get(j);
                    }

                    ClientEventHandler.STYLE.bindTexture("faction_bank");
                    ModernGui.drawModalRectWithCustomSizedTexture((float)var24, (float)var25.intValue(), 261, 142, 116, 20, 512.0F, 512.0F, false);
                    this.drawScaledString((String)this.cachedMembers.get(j), var24 + 3, var25.intValue() + 6, 11842740, 0.8F, false, true);
                }

                GUIUtils.endGLScissor();

                if (mouseX > this.guiLeft + 130 && mouseX < this.guiLeft + 130 + 125 && mouseY > this.guiTop + 139 && mouseY < this.guiTop + 229)
                {
                    this.scrollBarMembers.draw(mouseX, mouseY);
                }

                if (FactionGui_OLD.hasPermissions("bank_log"))
                {
                    GUIUtils.startGLScissor(this.guiLeft + 261, this.guiTop + 142, 116, 86);

                    for (j = 0; j < this.cachedLogs.size(); ++j)
                    {
                        var24 = this.guiLeft + 261;
                        var25 = Float.valueOf((float)(this.guiTop + 142 + j * 20) + this.getSlideLogs());
                        ClientEventHandler.STYLE.bindTexture("faction_bank");
                        ModernGui.drawModalRectWithCustomSizedTexture((float)var24, (float)var25.intValue(), 261, 142, 116, 20, 512.0F, 512.0F, false);
                        this.drawScaledString((String)((HashMap)this.cachedLogs.get(j)).get("amount"), var24 + 3, var25.intValue() + 3, 11842740, 0.85F, false, false);
                        this.drawScaledString((String)((HashMap)this.cachedLogs.get(j)).get("date"), var24 + 3, var25.intValue() + 12, 6710886, 0.65F, false, false);
                        ClientEventHandler.STYLE.bindTexture("faction_bank");
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(var24 + 104), (float)(var25.intValue() + 5), 148, 250, 10, 11, 512.0F, 512.0F, false);

                        if (mouseX > var24 + 104 && mouseX < var24 + 104 + 10 && (float)mouseY > var25.floatValue() + 5.0F && (float)mouseY < var25.floatValue() + 5.0F + 11.0F)
                        {
                            tooltipToDraw = (String)((HashMap)this.cachedLogs.get(j)).get("playerName");
                        }
                    }

                    GUIUtils.endGLScissor();
                }

                if (mouseX > this.guiLeft + 259 && mouseX < this.guiLeft + 384 && mouseY > this.guiTop + 139 && mouseY < this.guiTop + 229)
                {
                    this.scrollBarLogs.draw(mouseX, mouseY);
                }

                if (!tooltipToDraw.isEmpty())
                {
                    this.drawTooltip(tooltipToDraw, mouseX, mouseY);
                }
            }

            this.chestButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
            ClientEventHandler.STYLE.bindTexture("faction_bank");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 26), (float)(this.guiTop + 170), 159, 250, 10, 10, 512.0F, 512.0F, false);
            this.actionButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);

            if (!this.chestButton.enabled && mouseX > this.guiLeft + 10 && mouseX < this.guiLeft + 10 + 100 && mouseY > this.guiTop + 165 && mouseY < this.guiTop + 165 + 20)
            {
                this.drawHoveringText(Arrays.asList(new String[] {I18n.getString("faction.bank.chest_disabled_1"), I18n.getString("faction.bank.chest_disabled_2"), I18n.getString("faction.bank.chest_disabled_3"), I18n.getString("faction.bank.chest_disabled_4"), I18n.getString("faction.bank.chest_disabled_5")}), mouseX, mouseY + 20, this.fontRenderer);
            }
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0 && FactionGui_OLD.factionInfos != null)
        {
            if ((((Boolean)FactionGui_OLD.factionInfos.get("canOpenChest")).booleanValue() && FactionGui_OLD.hasPermissions("chest_access") || FactionGui_OLD.hasPermissions("admin")) && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 165 && mouseY <= this.guiTop + 165 + 20)
            {
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new RemoteOpenFactionChestPacket((String)FactionGui_OLD.factionInfos.get("id"), FactionGui_OLD.hasPermissions("chest_access"), FactionGui_OLD.hasPermissions("chest_access"), Integer.parseInt((String)FactionGui_OLD.factionInfos.get("chestLevel")))));
            }

            if (mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 188 && mouseY <= this.guiTop + 188 + 20)
            {
                Minecraft.getMinecraft().displayGuiScreen(new BankActionGui(this.player, this));
            }

            if (this.hoveredPlayer != null && !this.hoveredPlayer.isEmpty() && mouseX > this.guiLeft + 130 && mouseX < this.guiLeft + 130 + 125 && mouseY > this.guiTop + 140 && mouseY < this.guiTop + 140 + 90)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen(new ProfilGui(this.hoveredPlayer.split(" ")[1], ""));
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private float getSlideLogs()
    {
        return ((ArrayList)factionBankInfos.get("logs")).size() > 4 ? (float)(-(((ArrayList)factionBankInfos.get("logs")).size() - 4) * 20) * this.scrollBarLogs.getSliderValue() : 0.0F;
    }

    private float getSlideMembers()
    {
        return ((ArrayList)factionBankInfos.get("members")).size() > 4 ? (float)(-(((ArrayList)factionBankInfos.get("members")).size() - 4) * 20) * this.scrollBarMembers.getSliderValue() : 0.0F;
    }

    public void drawTooltip(String playerName, int mouseX, int mouseY)
    {
        int var10000 = mouseX - this.guiLeft;
        var10000 = mouseY - this.guiTop;

        if (!playerName.contains("Action de"))
        {
            this.drawHoveringText(Arrays.asList(new String[] {playerName}), mouseX, mouseY, this.fontRenderer);
        }
        else
        {
            this.drawHoveringText(Arrays.asList(new String[] {playerName}), mouseX, mouseY, this.fontRenderer);
        }
    }
}
