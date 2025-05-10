package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseParcelleActionPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseParcelleDataPacket;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;

public class EnterpriseParcelleGUI extends TabbedEnterpriseGUI
{
    public static ArrayList<HashMap<String, Object>> parcellesInfos = new ArrayList();
    public static boolean canSeeCoords = false;
    public String hoveredParcelleName = "";
    public String openedParcelleName = "";
    public String hoveredAction = "";
    public String hoveredStatus = "";
    public String selectedStatus = "";
    public boolean expandStatus = false;
    private GuiScrollBarFaction scrollBarStatus;
    private GuiScrollBarFaction scrollBarParcelle;
    public static boolean loaded = false;
    private GuiTextField parcelleSearch;
    private Long lastAction = Long.valueOf(0L);
    public static List<String> availableStatus = Arrays.asList(new String[] {"all", "to_rent", "to_sell", "rent", "free"});

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        loaded = false;
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterpriseParcelleDataPacket((String)EnterpriseGui.enterpriseInfos.get("name"))));
        this.scrollBarStatus = new GuiScrollBarFaction((float)(this.guiLeft + 379), (float)(this.guiTop + 54), 90);
        this.scrollBarParcelle = new GuiScrollBarFaction((float)(this.guiLeft + 389), (float)(this.guiTop + 59), 174);
        this.parcelleSearch = new GuiTextField(this.fontRenderer, this.guiLeft + 152, this.guiTop + 36, 97, 10);
        this.parcelleSearch.setEnableBackgroundDrawing(false);
        this.parcelleSearch.setMaxStringLength(20);
        this.selectedStatus = (String)availableStatus.get(0);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.parcelleSearch.updateCursorCounter();
    }

    public void drawScreen(int mouseX, int mouseY)
    {
        this.hoveredAction = "";
        this.hoveredParcelleName = "";
        this.hoveredStatus = "";
        ClientEventHandler.STYLE.bindTexture("enterprise_parcelle");
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
        this.drawScaledString(I18n.getString("enterprise.parcelle.title"), this.guiLeft + 131, this.guiTop + 15, 1644825, 1.4F, false, false);
        this.parcelleSearch.drawTextBox();

        if (parcellesInfos.size() > 0)
        {
            String tooltipToDraw = "";
            GUIUtils.startGLScissor(this.guiLeft + 131, this.guiTop + 59, 253, 174);
            int yOffset = 0;
            Iterator i = parcellesInfos.iterator();

            while (i.hasNext())
            {
                HashMap offsetX = (HashMap)i.next();

                if ((this.parcelleSearch.getText().isEmpty() || ((String)offsetX.get("clientName")).toLowerCase().contains(this.parcelleSearch.getText().toLowerCase()) || ((String)offsetX.get("name")).toLowerCase().contains(this.parcelleSearch.getText().toLowerCase())) && (this.selectedStatus.equals("all") || ((String)offsetX.get("status")).startsWith(this.selectedStatus)))
                {
                    int offsetY = this.guiLeft + 131;
                    Float offsetY1 = Float.valueOf((float)(this.guiTop + 59 + yOffset) + this.getSlideContracts());

                    if (this.openedParcelleName != "" && this.openedParcelleName == offsetX.get("name"))
                    {
                        ClientEventHandler.STYLE.bindTexture("enterprise_parcelle");
                        ModernGui.drawModalRectWithCustomSizedTexture((float)offsetY, offsetY1.floatValue(), 0, 385, 253, 104, 512.0F, 512.0F, false);
                        yOffset += 57;

                        if (mouseX >= offsetY && mouseX <= offsetY + 253 && (float)mouseY >= offsetY1.floatValue() && (float)mouseY <= offsetY1.floatValue() + 104.0F)
                        {
                            this.hoveredParcelleName = (String)offsetX.get("name");
                        }
                    }
                    else
                    {
                        ClientEventHandler.STYLE.bindTexture("enterprise_parcelle");
                        ModernGui.drawModalRectWithCustomSizedTexture((float)offsetY, offsetY1.floatValue(), 0, 350, 253, 34, 512.0F, 512.0F, false);
                        yOffset += 35;

                        if (EnterpriseGui.hasPermission("locations") && (!offsetX.get("status").equals("free") || !((Boolean)offsetX.get("isStaff")).booleanValue()) && mouseX >= offsetY && mouseX <= offsetY + 253 && (float)mouseY >= offsetY1.floatValue() && (float)mouseY <= offsetY1.floatValue() + 34.0F)
                        {
                            this.hoveredParcelleName = (String)offsetX.get("name");
                        }
                    }

                    ClientEventHandler.STYLE.bindTexture("enterprise_parcelle");
                    this.drawScaledString((String)offsetX.get("clientName"), offsetY + 4, offsetY1.intValue() + 5, 16777215, 1.0F, false, false);
                    this.drawScaledString("\u00a72" + offsetX.get("price"), offsetY + 126, offsetY1.intValue() + 5, 16777215, 1.0F, true, false);
                    this.drawScaledString((String)offsetX.get("name"), offsetY + 249 - this.fontRenderer.getStringWidth((String)offsetX.get("name")), offsetY1.intValue() + 5, 16777215, 1.0F, false, false);

                    if (((String)offsetX.get("status")).contains("rent#"))
                    {
                        this.drawScaledString(I18n.getString("enterprise.parcelle.status.rent") + " (" + ((String)offsetX.get("status")).split("#")[1] + "j)", offsetY + 4, offsetY1.intValue() + 24, 16777215, 1.0F, false, false);
                    }
                    else
                    {
                        this.drawScaledString(I18n.getString("enterprise.parcelle.status." + offsetX.get("status")), offsetY + 4, offsetY1.intValue() + 24, 16777215, 1.0F, false, false);
                    }

                    String signCoords = "???";

                    if (canSeeCoords)
                    {
                        signCoords = (String)offsetX.get("sign");
                    }

                    this.drawScaledString("\u00a78" + signCoords, offsetY + 249 - this.fontRenderer.getStringWidth(signCoords), offsetY1.intValue() + 24, 16777215, 1.0F, false, false);

                    if (this.openedParcelleName != "" && this.openedParcelleName == offsetX.get("name"))
                    {
                        ClientEventHandler.STYLE.bindTexture("enterprise_parcelle");
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetY + 196), (float)(offsetY1.intValue() + 36), 121, 251, 55, 18, 512.0F, 512.0F, false);
                        String action = "cancel";

                        if (!offsetX.get("status").equals("free"))
                        {
                            this.drawScaledString(I18n.getString("enterprise.parcelle.btn_label.cancel"), offsetY + 224, offsetY1.intValue() + 41, 16777215, 1.0F, true, false);
                        }
                        else
                        {
                            this.drawScaledString(I18n.getString("enterprise.parcelle.btn_label.remove"), offsetY + 224, offsetY1.intValue() + 41, 16777215, 1.0F, true, false);
                            action = "remove";
                        }

                        if (mouseX >= offsetY + 196 && mouseX <= offsetY + 196 + 55 && mouseY >= offsetY1.intValue() + 36 && mouseY <= offsetY1.intValue() + 36 + 18)
                        {
                            this.hoveredAction = action;
                        }
                    }
                }
            }

            GUIUtils.endGLScissor();

            if (!this.expandStatus)
            {
                this.scrollBarParcelle.draw(mouseX, mouseY);
            }

            this.drawScaledString(I18n.getString("enterprise.parcelle.status." + this.selectedStatus), this.guiLeft + 278, this.guiTop + 36, 16777215, 1.0F, false, false);

            if (this.expandStatus)
            {
                ClientEventHandler.STYLE.bindTexture("enterprise_parcelle");
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 274), (float)(this.guiTop + 49), 10, 251, 110, 99, 512.0F, 512.0F, false);
                GUIUtils.startGLScissor(this.guiLeft + 275, this.guiTop + 50, 104, 97);

                for (int var11 = 0; var11 < availableStatus.size(); ++var11)
                {
                    int var12 = this.guiLeft + 275;
                    Float var13 = Float.valueOf((float)(this.guiTop + 50 + var11 * 20) + this.getSlideStatus());
                    ClientEventHandler.STYLE.bindTexture("enterprise_parcelle");
                    ModernGui.drawModalRectWithCustomSizedTexture((float)var12, (float)var13.intValue(), 11, 252, 104, 20, 512.0F, 512.0F, false);
                    this.drawScaledString(I18n.getString("enterprise.parcelle.status." + (String)availableStatus.get(var11)), var12 + 3, var13.intValue() + 5, 16777215, 1.0F, false, false);

                    if (mouseX > var12 && mouseX < var12 + 104 && (float)mouseY > var13.floatValue() && (float)mouseY < var13.floatValue() + 20.0F)
                    {
                        this.hoveredStatus = (String)availableStatus.get(var11);
                    }
                }

                GUIUtils.endGLScissor();
                this.scrollBarStatus.draw(mouseX, mouseY);
            }
        }
    }

    private float getSlideStatus()
    {
        return availableStatus.size() > 5 ? (float)(-(availableStatus.size() - 5) * 20) * this.scrollBarStatus.getSliderValue() : 0.0F;
    }

    private float getSlideContracts()
    {
        int offsetOpened = this.openedParcelleName != "" ? 22 : 0;
        return (this.openedParcelleName == "" || parcellesInfos.size() <= 2) && parcellesInfos.size() <= 5 ? 0.0F : (float)(-((parcellesInfos.size() - 5) * 35 + offsetOpened)) * this.scrollBarParcelle.getSliderValue();
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
            if (!this.hoveredAction.isEmpty() && this.openedParcelleName != "" && EnterpriseGui.hasPermission("locations") && System.currentTimeMillis() - this.lastAction.longValue() > 800L)
            {
                this.lastAction = Long.valueOf(System.currentTimeMillis());
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterpriseParcelleActionPacket((String)EnterpriseGui.enterpriseInfos.get("name"), this.openedParcelleName, this.hoveredAction)));
                this.hoveredAction = "";
                this.openedParcelleName = "";
            }

            if (!this.expandStatus && this.hoveredParcelleName != "")
            {
                if (this.openedParcelleName != this.hoveredParcelleName)
                {
                    this.openedParcelleName = this.hoveredParcelleName;
                }
                else
                {
                    this.openedParcelleName = "";
                }

                this.hoveredParcelleName = "";
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
            }

            if (mouseX >= this.guiLeft + 365 && mouseX <= this.guiLeft + 365 + 19 && mouseY >= this.guiTop + 30 && mouseY <= this.guiTop + 30 + 20)
            {
                this.expandStatus = !this.expandStatus;
            }

            if (this.hoveredStatus != null && !this.hoveredStatus.isEmpty())
            {
                this.selectedStatus = this.hoveredStatus;
                this.hoveredStatus = "";
                this.expandStatus = false;
            }
        }

        this.parcelleSearch.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char typedChar, int keyCode)
    {
        if (this.parcelleSearch.textboxKeyTyped(typedChar, keyCode))
        {
            this.openedParcelleName = "";
        }

        super.keyTyped(typedChar, keyCode);
    }

    private String formatDiff(long diff)
    {
        String date = "";
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

        return date;
    }
}
