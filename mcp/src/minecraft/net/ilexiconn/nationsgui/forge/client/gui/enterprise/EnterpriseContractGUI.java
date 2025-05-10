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
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseContractActionPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseContractDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class EnterpriseContractGUI extends TabbedEnterpriseGUI
{
    public static ArrayList<HashMap<String, Object>> contractsInfos = new ArrayList();
    public Integer hoveredContractId = Integer.valueOf(-1);
    public Integer openedContractId = Integer.valueOf(-1);
    public String hoveredAction = "";
    public String hoveredStatus = "";
    public String selectedStatus = "";
    public boolean expandStatus = false;
    public int lastInputX = 0;
    public int lastInputY = 0;
    private GuiTextField refundInput;
    private RenderItem itemRenderer = new RenderItem();
    private GuiScrollBarFaction scrollBarStatus;
    private GuiScrollBarFaction scrollBarContract;
    public static boolean loaded = false;
    private GuiTextField contractSearch;
    private Long lastAction = Long.valueOf(0L);
    public static List<String> availableStatus = Arrays.asList(new String[] {"all", "waiting_enterprise", "in_progress", "done", "dispute_open_enterprise", "dispute_open_client", "dispute_close_enterprise", "dispute_close_client", "refused", "cancelled", "waiting_garant"});

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        loaded = false;
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterpriseContractDataPacket((String)EnterpriseGui.enterpriseInfos.get("name"))));
        this.scrollBarStatus = new GuiScrollBarFaction((float)(this.guiLeft + 379), (float)(this.guiTop + 54), 90);
        this.scrollBarContract = new GuiScrollBarFaction((float)(this.guiLeft + 389), (float)(this.guiTop + 59), 174);
        this.contractSearch = new GuiTextField(this.fontRenderer, this.guiLeft + 152, this.guiTop + 36, 97, 10);
        this.contractSearch.setEnableBackgroundDrawing(false);
        this.contractSearch.setMaxStringLength(20);
        this.selectedStatus = (String)availableStatus.get(0);
        this.refundInput = new GuiTextField(this.fontRenderer, this.guiLeft, this.guiTop, 51, 10);
        this.refundInput.setEnableBackgroundDrawing(false);
        this.refundInput.setMaxStringLength(7);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.contractSearch.updateCursorCounter();
        this.refundInput.updateCursorCounter();
    }

    public void drawScreen(int mouseX, int mouseY)
    {
        this.hoveredAction = "";
        this.hoveredContractId = Integer.valueOf(-1);
        this.hoveredStatus = "";
        ClientEventHandler.STYLE.bindTexture("enterprise_contract");
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
        this.drawScaledString(I18n.getString("enterprise.contract.title"), this.guiLeft + 131, this.guiTop + 15, 1644825, 1.4F, false, false);
        this.contractSearch.drawTextBox();

        if (contractsInfos.size() > 0)
        {
            String tooltipToDraw = "";
            GUIUtils.startGLScissor(this.guiLeft + 131, this.guiTop + 59, 253, 174);
            int yOffset = 0;
            Iterator i = contractsInfos.iterator();

            while (i.hasNext())
            {
                HashMap offsetX = (HashMap)i.next();

                if ((this.contractSearch.getText().isEmpty() || ((String)offsetX.get("clientName")).toLowerCase().contains(this.contractSearch.getText().toLowerCase())) && (this.selectedStatus.equals("all") || ((String)offsetX.get("status")).equals(this.selectedStatus)))
                {
                    int offsetY = this.guiLeft + 131;
                    Float offsetY1 = Float.valueOf((float)(this.guiTop + 59 + yOffset) + this.getSlideContracts());

                    if (this.openedContractId.intValue() != -1 && this.openedContractId.intValue() == ((Double)offsetX.get("id")).intValue())
                    {
                        ClientEventHandler.STYLE.bindTexture("enterprise_contract");
                        ModernGui.drawModalRectWithCustomSizedTexture((float)offsetY, offsetY1.floatValue(), 0, 385, 253, 104, 512.0F, 512.0F, false);
                        yOffset += 105;

                        if (mouseX >= offsetY && mouseX <= offsetY + 253 && (float)mouseY >= offsetY1.floatValue() && (float)mouseY <= offsetY1.floatValue() + 104.0F)
                        {
                            this.hoveredContractId = Integer.valueOf(((Double)offsetX.get("id")).intValue());
                        }
                    }
                    else
                    {
                        ClientEventHandler.STYLE.bindTexture("enterprise_contract");
                        ModernGui.drawModalRectWithCustomSizedTexture((float)offsetY, offsetY1.floatValue(), 0, 350, 253, 34, 512.0F, 512.0F, false);
                        yOffset += 35;

                        if (mouseX >= offsetY && mouseX <= offsetY + 253 && (float)mouseY >= offsetY1.floatValue() && (float)mouseY <= offsetY1.floatValue() + 34.0F)
                        {
                            this.hoveredContractId = Integer.valueOf(((Double)offsetX.get("id")).intValue());
                        }
                    }

                    try
                    {
                        ResourceLocation diffText = AbstractClientPlayer.locationStevePng;
                        diffText = AbstractClientPlayer.getLocationSkin((String)offsetX.get("clientName"));
                        AbstractClientPlayer.getDownloadImageSkin(diffText, (String)offsetX.get("clientName"));
                        Minecraft.getMinecraft().renderEngine.bindTexture(diffText);
                        this.mc.getTextureManager().bindTexture(diffText);
                        GUIUtils.drawScaledCustomSizeModalRect(offsetY + 10 + 3, offsetY1.intValue() + 10 + 3, 8.0F, 16.0F, 8, -8, -10, -10, 64.0F, 64.0F);
                        GUIUtils.drawScaledCustomSizeModalRect(offsetY + 10 + 3, offsetY1.intValue() + 10 + 3, 40.0F, 16.0F, 8, -8, -10, -10, 64.0F, 64.0F);
                    }
                    catch (Exception var20)
                    {
                        ;
                    }

                    ClientEventHandler.STYLE.bindTexture("enterprise_contract");
                    this.drawScaledString((String)offsetX.get("clientName"), offsetY + 15, offsetY1.intValue() + 5, 16777215, 1.0F, false, false);
                    this.drawScaledString("\u00a72" + ((Double)offsetX.get("price")).intValue() + "$", offsetY + 126, offsetY1.intValue() + 5, 16777215, 1.0F, true, false);
                    this.drawScaledString((String)offsetX.get("creationDate"), offsetY + 249 - this.fontRenderer.getStringWidth((String)offsetX.get("creationDate")), offsetY1.intValue() + 5, 16777215, 1.0F, false, false);
                    this.drawScaledString(I18n.getString("enterprise.contract.status." + offsetX.get("status")), offsetY + 4, offsetY1.intValue() + 24, 16777215, 1.0F, false, false);
                    String var24 = "";
                    boolean expired = false;

                    if (((Double)offsetX.get("deadlineTime")).doubleValue() > 0.0D)
                    {
                        long description = ((Double)offsetX.get("deadlineTime")).longValue() - System.currentTimeMillis();

                        if (description > 0L)
                        {
                            var24 = this.formatDiff(description);
                            var24 = I18n.getString("enterprise.contract.ends_in") + var24;
                        }
                        else
                        {
                            var24 = I18n.getString("enterprise.contract.expired");
                            expired = true;
                        }

                        var24 = var24.trim();
                    }
                    else if (((Double)offsetX.get("deadlineTime")).doubleValue() == 0.0D)
                    {
                        var24 = I18n.getString("enterprise.contract.at_end_assault");
                    }
                    else if (((Double)offsetX.get("deadlineTime")).doubleValue() == -10.0D)
                    {
                        var24 = "-";
                    }

                    this.drawScaledString("\u00a78" + var24, offsetY + 249 - this.fontRenderer.getStringWidth(var24), offsetY1.intValue() + 24, 16777215, 1.0F, false, false);

                    if (this.openedContractId.intValue() != -1 && this.openedContractId.intValue() == ((Double)offsetX.get("id")).intValue())
                    {
                        String var25 = (String)offsetX.get("description");
                        String descriptionWords;

                        if (var25.matches("PVP##.*"))
                        {
                            descriptionWords = I18n.getString("enterprise.create.text_description.pvp");
                            descriptionWords = descriptionWords.replace("<playerFaction>", var25.split("##")[1]);
                            descriptionWords = descriptionWords.replace("<factionHelpers>", var25.split("##")[2]);
                            descriptionWords = descriptionWords.replace("<opponentFaction>", var25.split("##")[3]);
                            descriptionWords = descriptionWords.replace("<opponentHelpers>", var25.split("##")[4]);
                            descriptionWords = descriptionWords.replace("<price>", var25.split("##")[5]);
                            descriptionWords = descriptionWords.replace("<condition>", I18n.getString("enterprise.contract.condition." + var25.split("##")[6]));
                            var25 = descriptionWords;
                        }
                        else if (var25.matches("REPAIR##.*"))
                        {
                            descriptionWords = I18n.getString("enterprise.create.text_description.repair");
                            descriptionWords = descriptionWords.replace("<factionName>", var25.split("##")[1]);
                            descriptionWords = descriptionWords.replace("<price>", var25.split("##")[2]);
                            descriptionWords = descriptionWords.replace("<amount>", var25.split("##")[3]);
                            var25 = descriptionWords;
                        }
                        else if (var25.contains("LOAN##"))
                        {
                            descriptionWords = I18n.getString("enterprise.create.text_description.loan");
                            descriptionWords = descriptionWords.replace("<amount>", var25.split("##")[1]);
                            descriptionWords = descriptionWords.replace("<duration>", var25.split("##")[3]);
                            descriptionWords = descriptionWords.replace("<rate>", var25.split("##")[4]);
                            descriptionWords = descriptionWords.replace("<totalDue>", var25.split("##")[2]);
                            descriptionWords = descriptionWords.replace("<waranty>", I18n.getString("enterprise.contract.loan.type." + var25.split("##")[5]));
                            descriptionWords = descriptionWords.replace("<warant>", var25.split("##")[5].equalsIgnoreCase("garant") ? "(" + var25.split("##")[6] + ")" : "");
                            var25 = descriptionWords;
                        }

                        String[] var31 = var25.replaceAll("\u00a7[0-9a-z]{1}", "").split(" ");
                        String line = "";
                        int lineNumber = 0;
                        String[] status = var31;
                        int clientName = var31.length;

                        for (int validButton = 0; validButton < clientName; ++validButton)
                        {
                            String invalidButton = status[validButton];

                            if ((double)this.fontRenderer.getStringWidth(line + invalidButton) * 0.9D <= 245.0D)
                            {
                                if (!line.equals(""))
                                {
                                    line = line + " ";
                                }

                                line = line + invalidButton;
                            }
                            else
                            {
                                this.drawScaledString(line, offsetY + 4, offsetY1.intValue() + 38 + lineNumber * 10, 16777215, 0.9F, false, false);
                                ++lineNumber;
                                line = invalidButton;
                            }
                        }

                        this.drawScaledString(line, offsetY + 4, offsetY1.intValue() + 38 + lineNumber * 10, 16777215, 0.9F, false, false);

                        if (offsetX.containsKey("items"))
                        {
                            int var26 = 0;

                            for (Iterator var28 = ((ArrayList)offsetX.get("items")).iterator(); var28.hasNext(); ++var26)
                            {
                                String var30 = (String)var28.next();
                                String[] var33 = var30.split("#");
                                ItemStack stack = new ItemStack(Integer.parseInt(var33[0]), Integer.parseInt(var33[2]), Integer.parseInt(var33[1]));
                                GL11.glEnable(GL11.GL_DEPTH_TEST);
                                RenderHelper.enableGUIStandardItemLighting();
                                this.itemRenderer.renderItemAndEffectIntoGUI(this.fontRenderer, Minecraft.getMinecraft().getTextureManager(), stack, offsetY + 4 + 18 * var26, offsetY1.intValue() + 38 + lineNumber * 10 + 10);
                                this.itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, Minecraft.getMinecraft().getTextureManager(), stack, offsetY + 4 + 18 * var26, offsetY1.intValue() + 38 + lineNumber * 10 + 10, stack.stackSize + "");
                                RenderHelper.disableStandardItemLighting();
                                GL11.glDisable(GL11.GL_LIGHTING);

                                if (mouseX >= offsetY + 4 + 18 * var26 && mouseX <= offsetY + 4 + 18 * var26 + 18 && mouseY >= offsetY1.intValue() + 38 + lineNumber * 10 + 10 - 18 && mouseY >= offsetY1.intValue() + 38 + lineNumber * 10 + 10)
                                {
                                    tooltipToDraw = stack.getDisplayName();
                                }
                            }
                        }

                        String var27 = (String)offsetX.get("status");
                        String var29 = (String)offsetX.get("clientName");
                        boolean var32 = false;
                        boolean var34 = false;
                        ClientEventHandler.STYLE.bindTexture("enterprise_contract");

                        if (var27.contains("dispute_open") && ((Boolean)EnterpriseGui.enterpriseInfos.get("canManageLitige")).booleanValue())
                        {
                            ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetY + 140), (float)(offsetY1.intValue() + 82), 121, 270, 55, 18, 512.0F, 512.0F, false);
                            this.drawScaledString(I18n.getString("enterprise.contract.btn_label.dispute_for_client"), offsetY + 168, offsetY1.intValue() + 87, 16777215, 1.0F, true, false);
                            var32 = true;
                        }
                        else if (var27.equals("waiting_enterprise") && EnterpriseGui.hasPermission("contracts") && ((Boolean)EnterpriseGui.enterpriseInfos.get("isInEnterprise")).booleanValue())
                        {
                            ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetY + 140), (float)(offsetY1.intValue() + 82), 121, 270, 55, 18, 512.0F, 512.0F, false);
                            this.drawScaledString(I18n.getString("enterprise.contract.btn_label.accept"), offsetY + 168, offsetY1.intValue() + 87, 16777215, 1.0F, true, false);
                            var32 = true;
                        }
                        else if (var27.equals("in_progress") && Minecraft.getMinecraft().thePlayer.username.equals(var29))
                        {
                            if (!EnterpriseGui.enterpriseInfos.get("type").equals("trader") && !EnterpriseGui.enterpriseInfos.get("type").equals("repair") && !EnterpriseGui.enterpriseInfos.get("type").equals("loan"))
                            {
                                ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetY + 140), (float)(offsetY1.intValue() + 82), 121, 270, 55, 18, 512.0F, 512.0F, false);
                                this.drawScaledString(I18n.getString("enterprise.contract.btn_label.validate"), offsetY + 168, offsetY1.intValue() + 87, 16777215, 1.0F, true, false);
                                var32 = true;
                            }
                            else if (EnterpriseGui.enterpriseInfos.get("type").equals("loan"))
                            {
                                ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetY + 140), (float)(offsetY1.intValue() + 82), 121, 270, 55, 18, 512.0F, 512.0F, false);
                                this.drawScaledString(I18n.getString("enterprise.contract.btn_label.refund"), offsetY + 168, offsetY1.intValue() + 87, 16777215, 1.0F, true, false);
                                var32 = true;
                                ModernGui.drawNGBlackSquare(offsetY + 196, offsetY1.intValue() + 82, 55, 18);

                                if (this.lastInputX != offsetY + 196 + 3 || this.lastInputY != offsetY1.intValue() + 82 + 6)
                                {
                                    this.lastInputX = offsetY + 196 + 3;
                                    this.lastInputY = offsetY1.intValue() + 82 + 6;
                                    this.refundInput = new GuiTextField(this.fontRenderer, offsetY + 196 + 3, offsetY1.intValue() + 82 + 6, 54, 10);
                                    this.refundInput.setEnableBackgroundDrawing(false);
                                    this.refundInput.setMaxStringLength(7);
                                    this.refundInput.setText("0");
                                }

                                this.refundInput.drawTextBox();

                                if (mouseX >= offsetY + 196 && mouseX <= offsetY + 196 + 55 && mouseY >= offsetY1.intValue() + 82 && mouseY <= offsetY1.intValue() + 82 + 18)
                                {
                                    this.hoveredContractId = Integer.valueOf(-1);
                                }
                            }
                        }
                        else if (var27.equals("waiting_garant") && Minecraft.getMinecraft().thePlayer.username.equals(offsetX.get("garant")))
                        {
                            ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetY + 140), (float)(offsetY1.intValue() + 82), 121, 270, 55, 18, 512.0F, 512.0F, false);
                            this.drawScaledString(I18n.getString("enterprise.contract.btn_label.sign"), offsetY + 168, offsetY1.intValue() + 87, 16777215, 1.0F, true, false);
                            var32 = true;
                            this.drawScaledString("\u00a7c" + I18n.getString("enterprise.contract.label.garant_warning") + " \u00a74" + String.format("%.0f", new Object[] {Double.valueOf(((Double)offsetX.get("price")).doubleValue() / 2.0D)}) + "$", offsetY + 4, offsetY1.intValue() + 87, 16777215, 1.0F, false, false);
                        }

                        ClientEventHandler.STYLE.bindTexture("enterprise_contract");

                        if (var27.contains("dispute_open") && ((Boolean)EnterpriseGui.enterpriseInfos.get("canManageLitige")).booleanValue())
                        {
                            ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetY + 196), (float)(offsetY1.intValue() + 82), 121, 251, 55, 18, 512.0F, 512.0F, false);
                            this.drawScaledString(I18n.getString("enterprise.contract.btn_label.dispute_for_enterprise"), offsetY + 224, offsetY1.intValue() + 87, 16777215, 1.0F, true, false);
                            var34 = true;
                        }
                        else if (var27.equals("waiting_enterprise") && EnterpriseGui.hasPermission("contracts") && ((Boolean)EnterpriseGui.enterpriseInfos.get("isInEnterprise")).booleanValue())
                        {
                            ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetY + 196), (float)(offsetY1.intValue() + 82), 121, 251, 55, 18, 512.0F, 512.0F, false);
                            this.drawScaledString(I18n.getString("enterprise.contract.btn_label.decline"), offsetY + 224, offsetY1.intValue() + 87, 16777215, 1.0F, true, false);
                            var34 = true;
                        }
                        else if ((var27.equals("waiting_enterprise") || var27.equals("waiting_garant")) && Minecraft.getMinecraft().thePlayer.username.equals(var29))
                        {
                            ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetY + 196), (float)(offsetY1.intValue() + 82), 121, 251, 55, 18, 512.0F, 512.0F, false);
                            this.drawScaledString(I18n.getString("enterprise.contract.btn_label.cancel"), offsetY + 224, offsetY1.intValue() + 87, 16777215, 1.0F, true, false);
                            var34 = true;
                        }
                        else if (var27.equals("in_progress") && !EnterpriseGui.enterpriseInfos.get("type").equals("trader") && !EnterpriseGui.enterpriseInfos.get("type").equals("repair") && (!EnterpriseGui.enterpriseInfos.get("type").equals("loan") || expired && ((Boolean)EnterpriseGui.enterpriseInfos.get("isInEnterprise")).booleanValue() && EnterpriseGui.hasPermission("contracts")) && (Minecraft.getMinecraft().thePlayer.username.equals(var29) || EnterpriseGui.hasPermission("contracts")))
                        {
                            ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetY + 196), (float)(offsetY1.intValue() + 82), 121, 251, 55, 18, 512.0F, 512.0F, false);
                            this.drawScaledString(I18n.getString("enterprise.contract.btn_label.dispute"), offsetY + 224, offsetY1.intValue() + 87, 16777215, 1.0F, true, false);
                            var34 = true;
                        }
                        else if (var27.equals("dispute_open_enterprise") && EnterpriseGui.hasPermission("contracts"))
                        {
                            ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetY + 196), (float)(offsetY1.intValue() + 82), 121, 251, 55, 18, 512.0F, 512.0F, false);
                            this.drawScaledString(I18n.getString("enterprise.contract.btn_label.cancel"), offsetY + 224, offsetY1.intValue() + 87, 16777215, 1.0F, true, false);
                            var34 = true;
                        }
                        else if (var27.equals("dispute_open_client") && Minecraft.getMinecraft().thePlayer.username.equals(var29))
                        {
                            ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetY + 196), (float)(offsetY1.intValue() + 82), 121, 251, 55, 18, 512.0F, 512.0F, false);
                            this.drawScaledString(I18n.getString("enterprise.contract.btn_label.cancel"), offsetY + 224, offsetY1.intValue() + 87, 16777215, 1.0F, true, false);
                            var34 = true;
                        }
                        else if (var27.equals("in_progress") && EnterpriseGui.enterpriseInfos.get("type").equals("trader") && Minecraft.getMinecraft().thePlayer.username.equals(var29))
                        {
                            ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetY + 196), (float)(offsetY1.intValue() + 82), 121, 251, 55, 18, 512.0F, 512.0F, false);
                            this.drawScaledString(I18n.getString("enterprise.contract.btn_label.retire"), offsetY + 224, offsetY1.intValue() + 87, 16777215, 1.0F, true, false);
                            var34 = true;
                        }
                        else if (var27.equals("in_progress") && EnterpriseGui.enterpriseInfos.get("type").equals("repair") && Minecraft.getMinecraft().thePlayer.username.equals(var29))
                        {
                            ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetY + 196), (float)(offsetY1.intValue() + 82), 121, 251, 55, 18, 512.0F, 512.0F, false);
                            this.drawScaledString(I18n.getString("enterprise.contract.btn_label.cancel"), offsetY + 224, offsetY1.intValue() + 87, 16777215, 1.0F, true, false);
                            var34 = true;
                        }
                        else if (var27.equals("in_progress") && EnterpriseGui.enterpriseInfos.get("type").equals("trader") && EnterpriseGui.hasPermission("contracts"))
                        {
                            ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetY + 196), (float)(offsetY1.intValue() + 82), 121, 251, 55, 18, 512.0F, 512.0F, false);
                            this.drawScaledString(I18n.getString("enterprise.contract.btn_label.cancel"), offsetY + 224, offsetY1.intValue() + 87, 16777215, 1.0F, true, false);
                            var34 = true;
                        }
                        else if (var27.equals("in_progress") && EnterpriseGui.enterpriseInfos.get("type").equals("repair") && EnterpriseGui.hasPermission("contracts"))
                        {
                            ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetY + 196), (float)(offsetY1.intValue() + 82), 121, 251, 55, 18, 512.0F, 512.0F, false);
                            this.drawScaledString(I18n.getString("enterprise.contract.btn_label.cancel"), offsetY + 224, offsetY1.intValue() + 87, 16777215, 1.0F, true, false);
                            var34 = true;
                        }
                        else if (var27.equals("waiting_garant") && Minecraft.getMinecraft().thePlayer.username.equals(offsetX.get("garant")))
                        {
                            ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetY + 196), (float)(offsetY1.intValue() + 82), 121, 251, 55, 18, 512.0F, 512.0F, false);
                            this.drawScaledString(I18n.getString("enterprise.contract.btn_label.decline"), offsetY + 224, offsetY1.intValue() + 87, 16777215, 1.0F, true, false);
                            var34 = true;
                        }
                        else if ((var27.equals("refused") || var27.equals("done")) && Minecraft.getMinecraft().thePlayer.username.equals(var29) && offsetX.containsKey("items") && ((ArrayList)offsetX.get("items")).size() > 0)
                        {
                            ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetY + 196), (float)(offsetY1.intValue() + 82), 121, 251, 55, 18, 512.0F, 512.0F, false);
                            this.drawScaledString(I18n.getString("enterprise.contract.btn_label.get_items"), offsetY + 224, offsetY1.intValue() + 87, 16777215, 1.0F, true, false);
                            var34 = true;
                        }

                        if (var32 && mouseX >= offsetY + 140 && mouseX <= offsetY + 140 + 55 && mouseY >= offsetY1.intValue() + 82 && mouseY <= offsetY1.intValue() + 82 + 18)
                        {
                            this.hoveredAction = "valid";
                        }
                        else if (var34 && mouseX >= offsetY + 196 && mouseX <= offsetY + 196 + 55 && mouseY >= offsetY1.intValue() + 82 && mouseY <= offsetY1.intValue() + 82 + 18)
                        {
                            this.hoveredAction = "invalid";
                        }
                    }
                }
            }

            GUIUtils.endGLScissor();

            if (!this.expandStatus)
            {
                this.scrollBarContract.draw(mouseX, mouseY);
            }

            this.drawScaledString(I18n.getString("enterprise.contract.status." + this.selectedStatus), this.guiLeft + 278, this.guiTop + 36, 16777215, 1.0F, false, false);

            if (this.expandStatus)
            {
                ClientEventHandler.STYLE.bindTexture("enterprise_contract");
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 274), (float)(this.guiTop + 49), 10, 251, 110, 99, 512.0F, 512.0F, false);
                GUIUtils.startGLScissor(this.guiLeft + 275, this.guiTop + 50, 104, 97);

                for (int var21 = 0; var21 < availableStatus.size(); ++var21)
                {
                    int var22 = this.guiLeft + 275;
                    Float var23 = Float.valueOf((float)(this.guiTop + 50 + var21 * 20) + this.getSlideStatus());
                    ClientEventHandler.STYLE.bindTexture("enterprise_contract");
                    ModernGui.drawModalRectWithCustomSizedTexture((float)var22, (float)var23.intValue(), 11, 252, 104, 20, 512.0F, 512.0F, false);
                    this.drawScaledString(I18n.getString("enterprise.contract.status." + (String)availableStatus.get(var21)), var22 + 3, var23.intValue() + 5, 16777215, 1.0F, false, false);

                    if (mouseX > var22 && mouseX < var22 + 104 && (float)mouseY > var23.floatValue() && (float)mouseY < var23.floatValue() + 20.0F)
                    {
                        this.hoveredStatus = (String)availableStatus.get(var21);
                    }
                }

                GUIUtils.endGLScissor();
                this.scrollBarStatus.draw(mouseX, mouseY);
            }

            if (tooltipToDraw != "")
            {
                this.drawHoveringText(Arrays.asList(new String[] {tooltipToDraw}), mouseX, mouseY, this.fontRenderer);
            }
        }
    }

    private float getSlideStatus()
    {
        return availableStatus.size() > 5 ? (float)(-(availableStatus.size() - 5) * 20) * this.scrollBarStatus.getSliderValue() : 0.0F;
    }

    private float getSlideContracts()
    {
        int offsetOpened = this.openedContractId.intValue() != -1 ? 70 : 0;
        return (this.openedContractId.intValue() == -1 || contractsInfos.size() <= 2) && contractsInfos.size() <= 5 ? 0.0F : (float)(-((contractsInfos.size() - 5) * 35 + offsetOpened)) * this.scrollBarContract.getSliderValue();
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
            if (!this.hoveredAction.isEmpty() && this.openedContractId.intValue() != -1 && System.currentTimeMillis() - this.lastAction.longValue() > 800L)
            {
                this.lastAction = Long.valueOf(System.currentTimeMillis());
                int refundAmount = 0;

                if (!this.refundInput.getText().isEmpty())
                {
                    if (!this.isNumeric(this.refundInput.getText()))
                    {
                        return;
                    }

                    refundAmount = Integer.parseInt(this.refundInput.getText());
                }

                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterpriseContractActionPacket((String)EnterpriseGui.enterpriseInfos.get("name"), this.openedContractId, this.hoveredAction, Integer.valueOf(refundAmount))));
            }

            if (!this.expandStatus && this.hoveredContractId.intValue() != -1)
            {
                if (this.openedContractId != this.hoveredContractId)
                {
                    this.openedContractId = this.hoveredContractId;
                }
                else
                {
                    this.openedContractId = Integer.valueOf(-1);
                }

                this.hoveredContractId = Integer.valueOf(-1);
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
                this.scrollBarContract.reset();
            }
        }

        this.contractSearch.mouseClicked(mouseX, mouseY, mouseButton);
        this.refundInput.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char typedChar, int keyCode)
    {
        if (this.contractSearch.textboxKeyTyped(typedChar, keyCode))
        {
            this.openedContractId = Integer.valueOf(-1);
        }

        this.refundInput.textboxKeyTyped(typedChar, keyCode);
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

    public boolean isNumeric(String str)
    {
        if (str != null && str.length() != 0)
        {
            char[] var2 = str.toCharArray();
            int var3 = var2.length;

            for (int var4 = 0; var4 < var3; ++var4)
            {
                char c = var2[var4];

                if (!Character.isDigit(c))
                {
                    return false;
                }
            }

            if (Integer.parseInt(str) <= 0)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }
    }
}
