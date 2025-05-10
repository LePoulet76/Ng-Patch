package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseBankDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;

public class EnterpriseBankGUI extends TabbedEnterpriseGUI
{
    public static boolean loaded = false;
    public static HashMap<String, Object> enterpriseBankInfos;
    private ArrayList<HashMap<String, String>> cachedLogs = new ArrayList();
    private GuiScrollBarFaction scrollBarLogs;
    private GuiButton salaryButton;
    private GuiButton bonusButton;
    private GuiButton capitalButton;

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        loaded = false;
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterpriseBankDataPacket((String)EnterpriseGui.enterpriseInfos.get("name"))));
        this.scrollBarLogs = new GuiScrollBarFaction((float)(this.guiLeft + 377), (float)(this.guiTop + 145), 80);
        this.cachedLogs = new ArrayList();
        this.salaryButton = new GuiButton(0, this.guiLeft + 10, this.guiTop + 165, 100, 20, I18n.getString("enterprise.bank.btn.salary"));
        this.bonusButton = new GuiButton(1, this.guiLeft + 10, this.guiTop + 188, 100, 20, I18n.getString("enterprise.bank.btn.bonus"));
        this.capitalButton = new GuiButton(2, this.guiLeft + 10, this.guiTop + 211, 100, 20, I18n.getString("enterprise.bank.btn.capital"));

        if (!EnterpriseGui.hasPermission("salary"))
        {
            this.salaryButton.enabled = false;
            this.bonusButton.enabled = false;
        }

        if (!EnterpriseGui.hasPermission("capital"))
        {
            this.capitalButton.enabled = false;
        }
    }

    public void drawScreen(int mouseX, int mouseY)
    {
        String tooltipToDraw = "";
        ClientEventHandler.STYLE.bindTexture("enterprise_bank");
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
        this.salaryButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
        this.bonusButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
        this.capitalButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);

        if (!this.salaryButton.enabled)
        {
            if (mouseX > this.guiLeft + 10 && mouseX < this.guiLeft + 10 + 100 && mouseY > this.guiTop + 165 && mouseY < this.guiTop + 165 + 20)
            {
                tooltipToDraw = I18n.getString("enterprise.bank.btn_disabled");
            }
            else if (mouseX > this.guiLeft + 10 && mouseX < this.guiLeft + 10 + 100 && mouseY > this.guiTop + 188 && mouseY < this.guiTop + 188 + 20)
            {
                tooltipToDraw = I18n.getString("enterprise.bank.btn_disabled");
            }
        }

        if (!this.capitalButton.enabled && mouseX > this.guiLeft + 10 && mouseX < this.guiLeft + 10 + 100 && mouseY > this.guiTop + 211 && mouseY < this.guiTop + 211 + 20)
        {
            tooltipToDraw = I18n.getString("enterprise.bank.btn_disabled");
        }

        if (loaded)
        {
            this.drawScaledString(I18n.getString("enterprise.bank.title") + " " + EnterpriseGui.enterpriseInfos.get("name"), this.guiLeft + 259, this.guiTop + 24, 3818599, 1.7F, true, false);
            this.drawScaledString(enterpriseBankInfos.get("bank") + "$", this.guiLeft + 257, this.guiTop + 107, 16777215, 1.5F, true, false);
            this.drawScaledString(I18n.getString("faction.bank.transactions"), this.guiLeft + 259, this.guiTop + 132, 3818599, 0.8F, false, false);

            if (mouseX >= this.guiLeft + 134 && mouseX <= this.guiLeft + 134 + 58 && mouseY >= this.guiTop + 140 && mouseY <= this.guiTop + 140 + 43)
            {
                tooltipToDraw = I18n.getString("enterprise.bank.flux");
            }
            else if (mouseX >= this.guiLeft + 196 && mouseX <= this.guiLeft + 196 + 58 && mouseY >= this.guiTop + 140 && mouseY <= this.guiTop + 140 + 43)
            {
                tooltipToDraw = I18n.getString("enterprise.bank.waiting_money");
            }
            else if (mouseX >= this.guiLeft + 134 && mouseX <= this.guiLeft + 134 + 58 && mouseY >= this.guiTop + 187 && mouseY <= this.guiTop + 187 + 43)
            {
                tooltipToDraw = I18n.getString("enterprise.bank.salaries");
            }
            else if (mouseX >= this.guiLeft + 196 && mouseX <= this.guiLeft + 196 + 58 && mouseY >= this.guiTop + 187 && mouseY <= this.guiTop + 187 + 43)
            {
                tooltipToDraw = I18n.getString("enterprise.bank.taxes");
            }

            String flux = (String)enterpriseBankInfos.get("flux");
            flux = flux.contains("-") ? "\u00a7c" + flux : "\u00a72+" + flux;
            String waiting_money = (String)enterpriseBankInfos.get("waiting_money");
            waiting_money = "\u00a72" + waiting_money;
            String salaries = (String)enterpriseBankInfos.get("salaries");
            salaries = salaries.contains("-") ? "\u00a7c" + salaries : "\u00a72+" + salaries;
            String taxes = (String)enterpriseBankInfos.get("taxes");
            taxes = taxes.contains("-") ? "\u00a7c" + taxes : "\u00a72+" + taxes;
            this.drawScaledString(flux + "$", this.guiLeft + 163, this.guiTop + 168, 16777215, 1.0F, true, false);
            this.drawScaledString(waiting_money + "$", this.guiLeft + 225, this.guiTop + 168, 16777215, 1.0F, true, false);
            this.drawScaledString(salaries + "$", this.guiLeft + 163, this.guiTop + 215, 16777215, 1.0F, true, false);
            this.drawScaledString(taxes + "$", this.guiLeft + 225, this.guiTop + 215, 16777215, 1.0F, true, false);
            ClientEventHandler.STYLE.bindTexture("enterprise_bank");

            if (enterpriseBankInfos.get("logs") != null && ((ArrayList)enterpriseBankInfos.get("logs")).size() > 0)
            {
                int j;
                String logType;

                if (this.cachedLogs.size() == 0)
                {
                    for (j = 0; j < ((ArrayList)enterpriseBankInfos.get("logs")).size(); ++j)
                    {
                        HashMap offsetX = new HashMap();
                        String offsetY = (String)((ArrayList)enterpriseBankInfos.get("logs")).get(j);

                        if (offsetY.split("##").length == 3)
                        {
                            logType = offsetY.split("##")[1];

                            if (logType.contains("-"))
                            {
                                logType = logType.replace("-", "\u00a74-\u00a77");
                            }
                            else
                            {
                                logType = "\u00a7a+\u00a77" + logType;
                            }

                            logType = logType + "$";
                            Long time = Long.valueOf(Long.parseLong(offsetY.split("##")[2]));
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

                            String type = offsetY.split("##")[0];
                            offsetX.put("amount", logType);
                            offsetX.put("date", date);
                            offsetX.put("type", type);
                            this.cachedLogs.add(offsetX);
                        }
                    }
                }

                if (EnterpriseGui.hasPermission("bank_log"))
                {
                    GUIUtils.startGLScissor(this.guiLeft + 261, this.guiTop + 142, 116, 86);

                    for (j = 0; j < this.cachedLogs.size(); ++j)
                    {
                        int var27 = this.guiLeft + 261;
                        Float var28 = Float.valueOf((float)(this.guiTop + 142 + j * 20) + this.getSlideLogs());
                        ClientEventHandler.STYLE.bindTexture("enterprise_bank");
                        ModernGui.drawModalRectWithCustomSizedTexture((float)var27, (float)var28.intValue(), 261, 142, 116, 20, 512.0F, 512.0F, false);
                        this.drawScaledString((String)((HashMap)this.cachedLogs.get(j)).get("amount"), var27 + 3, var28.intValue() + 3, 11842740, 0.85F, false, false);
                        this.drawScaledString((String)((HashMap)this.cachedLogs.get(j)).get("date"), var27 + 3, var28.intValue() + 12, 6710886, 0.65F, false, false);
                        ClientEventHandler.STYLE.bindTexture("enterprise_bank");
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(var27 + 104), (float)(var28.intValue() + 5), 148, 250, 10, 11, 512.0F, 512.0F, false);

                        if (mouseX > var27 + 104 && mouseX < var27 + 104 + 10 && (float)mouseY > var28.floatValue() + 5.0F && (float)mouseY < var28.floatValue() + 5.0F + 11.0F)
                        {
                            logType = (String)((HashMap)this.cachedLogs.get(j)).get("type");

                            if (logType.contains("#"))
                            {
                                tooltipToDraw = I18n.getString("enterprise.bank.transaction.type." + logType.split("#")[0]).replace("<player>", logType.split("#")[1]);
                            }
                            else
                            {
                                tooltipToDraw = I18n.getString("enterprise.bank.transaction.type." + logType);
                            }
                        }
                    }

                    GUIUtils.endGLScissor();
                }

                if (mouseX > this.guiLeft + 259 && mouseX < this.guiLeft + 384 && mouseY > this.guiTop + 139 && mouseY < this.guiTop + 229)
                {
                    this.scrollBarLogs.draw(mouseX, mouseY);
                }
            }

            if (!tooltipToDraw.isEmpty())
            {
                this.drawTooltip(tooltipToDraw, mouseX, mouseY);
            }
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0 && EnterpriseGui.enterpriseInfos != null)
        {
            if (this.salaryButton.enabled && EnterpriseGui.hasPermission("salary") && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 165 && mouseY <= this.guiTop + 165 + 20)
            {
                Minecraft.getMinecraft().displayGuiScreen(new EnterpriseSalaryGui(this));
            }

            if (this.bonusButton.enabled && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 188 && mouseY <= this.guiTop + 188 + 20)
            {
                Minecraft.getMinecraft().displayGuiScreen(new EnterpriseBonusGui(this));
            }

            if (this.capitalButton.enabled && mouseX >= this.guiLeft + 10 && mouseX <= this.guiLeft + 10 + 100 && mouseY >= this.guiTop + 211 && mouseY <= this.guiTop + 211 + 20)
            {
                Minecraft.getMinecraft().displayGuiScreen(new EnterpriseCapitalGui(this));
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private float getSlideLogs()
    {
        return ((ArrayList)enterpriseBankInfos.get("logs")).size() > 4 ? (float)(-(((ArrayList)enterpriseBankInfos.get("logs")).size() - 4) * 20) * this.scrollBarLogs.getSliderValue() : 0.0F;
    }

    public void drawTooltip(String text, int mouseX, int mouseY)
    {
        int var10000 = mouseX - this.guiLeft;
        var10000 = mouseY - this.guiTop;
        this.drawHoveringText(Arrays.asList(new String[] {text}), mouseX, mouseY, this.fontRenderer);
    }
}
