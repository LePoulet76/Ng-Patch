package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.io.Serializable;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionEnemyRequestGenerateConditionsPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionEnemyRequestPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionEnemyRequestUpdateConditionsPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionEnemyRequestUpdateForumPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionEnemyRequestUpdateStatusPacket;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class WarRequestGUI extends GuiScreen
{
    protected int xSize = 319;
    protected int ySize = 248;
    private int guiLeft;
    private int guiTop;
    private RenderItem itemRenderer = new RenderItem();
    public static HashMap<String, Object> warInfos = new HashMap();
    public static boolean loaded = false;
    private GuiTextField linkForumInput;
    private GuiTextField conditionInput;
    private GuiTextField rewardInput;
    private GuiScrollBarFaction scrollBarAvailableConditions;
    private GuiScrollBarFaction scrollBarAvailableRewards;
    private GuiScrollBarFaction scrollBarConditions;
    private GuiScrollBarFaction scrollBarRewards;
    private GuiScrollBarFaction scrollBarFinishConditions;
    private GuiScrollBarFaction scrollBarFinishRewards;
    public static int warRequestId;
    private GuiScreen guiFrom;
    public String hoveredAction = "";
    public HashMap<String, Integer> conditions = new HashMap();
    public HashMap<String, Integer> rewards = new HashMap();
    public boolean conditionsTypeOpened = false;
    public boolean conditionsOpened = false;
    public boolean rewardsOpened = false;
    public String hoveredConditionsType = "";
    public String hoveredAvailableCondition = "";
    public String hoveredCondition = "";
    public String hoveredAvailableReward = "";
    public String hoveredReward = "";
    public String selectedConditionsType = "and";
    public String selectedCondition = "";
    public List<String> availableConditions = Arrays.asList(new String[] {"kill", "victory", "missile_point", "assault_point", "anti", "red"});
    public String selectedReward = "";
    public ArrayList<String> availableRewards = new ArrayList(Arrays.asList(new String[] {"dollars", "power", "claims", "peace"}));
    public HashMap<String, Integer> data_ATT = null;
    public HashMap<String, Integer> data_DEF = null;
    public String winner = null;

    public WarRequestGUI(int warRequestId, GuiScreen guiFrom)
    {
        warRequestId = warRequestId;
        this.guiFrom = guiFrom;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        loaded = false;
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionEnemyRequestPacket(warRequestId)));
        this.scrollBarAvailableConditions = new GuiScrollBarFaction((float)(this.guiLeft + 280), (float)(this.guiTop + 100), 71);
        this.scrollBarAvailableRewards = new GuiScrollBarFaction((float)(this.guiLeft + 280), (float)(this.guiTop + 172), 71);
        this.scrollBarConditions = new GuiScrollBarFaction((float)(this.guiLeft + 302), (float)(this.guiTop + 104), 41);
        this.scrollBarRewards = new GuiScrollBarFaction((float)(this.guiLeft + 302), (float)(this.guiTop + 175), 41);
        this.scrollBarFinishConditions = new GuiScrollBarFaction((float)(this.guiLeft + 302), (float)(this.guiTop + 99), 55);
        this.scrollBarFinishRewards = new GuiScrollBarFaction((float)(this.guiLeft + 302), (float)(this.guiTop + 174), 55);
        this.linkForumInput = new GuiTextField(this.fontRenderer, this.guiLeft + 48, this.guiTop + 126, 237, 10);
        this.linkForumInput.setEnableBackgroundDrawing(false);
        this.linkForumInput.setMaxStringLength(200);
        this.conditionInput = new GuiTextField(this.fontRenderer, this.guiLeft + 153, this.guiTop + 85, 47, 10);
        this.conditionInput.setEnableBackgroundDrawing(false);
        this.conditionInput.setMaxStringLength(3);
        this.conditionInput.setText("0");
        this.rewardInput = new GuiTextField(this.fontRenderer, this.guiLeft + 150, this.guiTop + 157, 50, 10);
        this.rewardInput.setEnableBackgroundDrawing(false);
        this.rewardInput.setMaxStringLength(7);
        this.rewardInput.setText("0");
        this.selectedCondition = (String)this.availableConditions.get(0);
        this.selectedReward = (String)this.availableRewards.get(0);
        this.availableRewards = new ArrayList(Arrays.asList(new String[] {"dollars", "power", "claims", "peace"}));
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.linkForumInput.updateCursorCounter();
        this.conditionInput.updateCursorCounter();
        this.rewardInput.updateCursorCounter();
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        this.drawDefaultBackground();
        this.hoveredAction = "";
        this.hoveredConditionsType = "";
        this.hoveredAvailableCondition = "";
        this.hoveredCondition = "";
        this.hoveredReward = "";
        this.hoveredAvailableReward = "";
        String tooltipToDraw = "";

        if (loaded && warInfos.size() > 0)
        {
            if (warInfos.get("reason").equals("colony_refusal") && warInfos.get("status").equals("waiting_conditions_att") && !this.availableRewards.contains("colonisation"))
            {
                this.availableRewards.add("colonisation");
            }

            String MAIN_TEXTURE = this.bindTextureDependingOnStatus();
            ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
            ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);

            if (mouseX >= this.guiLeft + 305 && mouseX <= this.guiLeft + 305 + 9 && mouseY >= this.guiTop - 6 && mouseY <= this.guiTop - 6 + 10)
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 305), (float)(this.guiTop - 6), 46, 258, 9, 10, 512.0F, 512.0F, false);
            }
            else
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 305), (float)(this.guiTop - 6), 46, 248, 9, 10, 512.0F, 512.0F, false);
            }

            GL11.glPushMatrix();
            GL11.glTranslatef((float)(this.guiLeft + 14), (float)(this.guiTop + 210), 0.0F);
            GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef((float)(-(this.guiLeft + 16)), (float)(-(this.guiTop + 210)), 0.0F);
            this.drawScaledString(I18n.getString("faction.enemy.title"), this.guiLeft + 14, this.guiTop + 210, 16777215, 1.5F, false, false);
            GL11.glPopMatrix();
            ClientProxy.loadCountryFlag((String)warInfos.get("factionATT"));
            ClientProxy.loadCountryFlag((String)warInfos.get("factionDEF"));

            if (ClientProxy.flagsTexture.containsKey((String)warInfos.get("factionATT")))
            {
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((DynamicTexture)ClientProxy.flagsTexture.get((String)warInfos.get("factionATT"))).getGlTextureId());
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 57), (float)(this.guiTop + 27), 0.0F, 0.0F, 156, 78, 27, 15, 156.0F, 78.0F, false);
            }

            if (ClientProxy.flagsTexture.containsKey((String)warInfos.get("factionDEF")))
            {
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((DynamicTexture)ClientProxy.flagsTexture.get((String)warInfos.get("factionDEF"))).getGlTextureId());
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 193), (float)(this.guiTop + 27), 0.0F, 0.0F, 156, 78, 27, 15, 156.0F, 78.0F, false);
            }

            if (((String)warInfos.get("factionATT")).contains("Empire"))
            {
                this.drawScaledString("Empire", this.guiLeft + 90, this.guiTop + 27, 16777215, 1.0F, false, false);
                this.drawScaledString(((String)warInfos.get("factionATT")).replace("Empire", ""), this.guiLeft + 90, this.guiTop + 37, 16777215, 1.0F, false, false);
            }
            else
            {
                this.drawScaledString((String)warInfos.get("factionATT"), this.guiLeft + 90, this.guiTop + 32, 16777215, 1.0F, false, false);
            }

            if (((String)warInfos.get("factionDEF")).contains("Empire"))
            {
                this.drawScaledString("Empire", this.guiLeft + 226, this.guiTop + 27, 16777215, 1.0F, false, false);
                this.drawScaledString(((String)warInfos.get("factionDEF")).replace("Empire", ""), this.guiLeft + 226, this.guiTop + 37, 16777215, 1.0F, false, false);
            }
            else
            {
                this.drawScaledString((String)warInfos.get("factionDEF"), this.guiLeft + 226, this.guiTop + 32, 16777215, 1.0F, false, false);
            }

            String side;

            if (!warInfos.get("status").equals("in_progress") && !warInfos.get("status").equals("finished"))
            {
                side = I18n.getString("faction.enemy.status." + warInfos.get("status") + ".att");
                this.drawScaledString(side, this.guiLeft + 108 + 6, this.guiTop + 58, 16777215, 1.0F, true, false);
                ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);

                if (side.startsWith("\u00a72"))
                {
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 108 - this.fontRenderer.getStringWidth(side) / 2 - 8), (float)(this.guiTop + 56), 110, 251, 10, 11, 512.0F, 512.0F, false);
                }
                else if (side.startsWith("\u00a74"))
                {
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 108 - this.fontRenderer.getStringWidth(side) / 2 - 8), (float)(this.guiTop + 56), 120, 251, 10, 11, 512.0F, 512.0F, false);
                }
                else
                {
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 108 - this.fontRenderer.getStringWidth(side) / 2 - 8), (float)(this.guiTop + 56), 130, 251, 10, 11, 512.0F, 512.0F, false);
                }

                side = I18n.getString("faction.enemy.status." + warInfos.get("status") + ".def");
                this.drawScaledString(side, this.guiLeft + 244 + 6, this.guiTop + 58, 16777215, 1.0F, true, false);
                ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);

                if (side.startsWith("\u00a72"))
                {
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 244 - this.fontRenderer.getStringWidth(side) / 2 - 8), (float)(this.guiTop + 56), 110, 251, 10, 11, 512.0F, 512.0F, false);
                }
                else if (side.startsWith("\u00a74"))
                {
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 244 - this.fontRenderer.getStringWidth(side) / 2 - 8), (float)(this.guiTop + 56), 120, 251, 10, 11, 512.0F, 512.0F, false);
                }
                else
                {
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 244 - this.fontRenderer.getStringWidth(side) / 2 - 8), (float)(this.guiTop + 56), 130, 251, 10, 11, 512.0F, 512.0F, false);
                }
            }
            else if (this.winner == null)
            {
                side = I18n.getString("faction.enemy.inactivity") + ": " + (warInfos.get("inactivity_ATT") != null ? warInfos.get("inactivity_ATT") : Integer.valueOf(0)) + "%";
                this.drawScaledString(side, this.guiLeft + 108 - 6, this.guiTop + 58, 16777215, 1.0F, true, false);
                ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 108 - 6 + this.fontRenderer.getStringWidth(side) / 2 + 2), (float)(this.guiTop + 56), 71, 251, 10, 11, 512.0F, 512.0F, false);

                if (mouseX >= this.guiLeft + 108 - 6 + this.fontRenderer.getStringWidth(side) / 2 + 2 && mouseX <= this.guiLeft + 108 - 6 + this.fontRenderer.getStringWidth(side) / 2 + 2 + 10 && mouseY >= this.guiTop + 56 && mouseY <= this.guiTop + 56 + 11)
                {
                    tooltipToDraw = I18n.getString("faction.enemy.tooltip.inactivity");
                }

                side = I18n.getString("faction.enemy.inactivity") + ": " + (warInfos.get("inactivity_DEF") != null ? warInfos.get("inactivity_DEF") : Integer.valueOf(0)) + "%";
                this.drawScaledString(side, this.guiLeft + 244 - 6, this.guiTop + 58, 16777215, 1.0F, true, false);
                ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 244 - 6 + this.fontRenderer.getStringWidth(side) / 2 + 2), (float)(this.guiTop + 56), 71, 251, 10, 11, 512.0F, 512.0F, false);

                if (mouseX >= this.guiLeft + 244 - 6 + this.fontRenderer.getStringWidth(side) / 2 + 2 && mouseX <= this.guiLeft + 244 - 6 + this.fontRenderer.getStringWidth(side) / 2 + 2 + 10 && mouseY >= this.guiTop + 56 && mouseY <= this.guiTop + 56 + 11)
                {
                    tooltipToDraw = I18n.getString("faction.enemy.tooltip.inactivity");
                }
            }
            else if (this.winner.equals(warInfos.get("factionATT")))
            {
                this.drawScaledString(I18n.getString("faction.enemy.winner"), this.guiLeft + 108 - 6, this.guiTop + 58, 16777215, 1.0F, true, false);
                ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 108 + this.fontRenderer.getStringWidth(I18n.getString("faction.enemy.winner")) / 2 - 6 + 2), (float)(this.guiTop + 56), 330, 22, 11, 11, 512.0F, 512.0F, false);
                this.drawScaledString(I18n.getString("faction.enemy.looser"), this.guiLeft + 244 - 9, this.guiTop + 58, 16777215, 1.0F, true, false);
                ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 244 + this.fontRenderer.getStringWidth(I18n.getString("faction.enemy.looser")) / 2 - 9 + 2), (float)(this.guiTop + 56), 329, 34, 16, 11, 512.0F, 512.0F, false);
            }
            else
            {
                this.drawScaledString(I18n.getString("faction.enemy.looser"), this.guiLeft + 108 - 6, this.guiTop + 58, 16777215, 1.0F, true, false);
                ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 108 + this.fontRenderer.getStringWidth(I18n.getString("faction.enemy.looser")) / 2 - 6 + 2), (float)(this.guiTop + 56), 329, 34, 16, 11, 512.0F, 512.0F, false);
                this.drawScaledString(I18n.getString("faction.enemy.winner"), this.guiLeft + 244 - 9, this.guiTop + 58, 16777215, 1.0F, true, false);
                ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 244 + this.fontRenderer.getStringWidth(I18n.getString("faction.enemy.winner")) / 2 - 9 + 2), (float)(this.guiTop + 56), 330, 22, 11, 11, 512.0F, 512.0F, false);
            }

            String progressDEF;
            int offset;
            int simpleDateFormat;
            int dateString;
            String totalKills;
            String finishReward;
            String rewardName;
            String rewardGoal;
            int var31;
            String[] var35;
            int var41;

            if (!warInfos.get("status").equals("waiting_validation") && !warInfos.get("status").equals("refused") && !warInfos.get("status").equals("cancelled") && (!warInfos.get("status").equals("waiting_conditions_att") || warInfos.get("factionATT").equals(warInfos.get("playerFaction")) && ((Boolean)warInfos.get("hasWarPermInOwnCountry")).booleanValue()) && (!warInfos.get("status").equals("waiting_conditions_def") || warInfos.get("factionDEF").equals(warInfos.get("playerFaction")) && ((Boolean)warInfos.get("hasWarPermInOwnCountry")).booleanValue()) && !warInfos.get("status").equals("waiting_conditions_att_second"))
            {
                Float var29;
                int var30;
                String[] var33;
                String var42;

                if ((!warInfos.get("status").equals("waiting_conditions_att") || !warInfos.get("factionATT").equals(warInfos.get("playerFaction"))) && (!warInfos.get("status").equals("waiting_conditions_def") || !warInfos.get("factionDEF").equals(warInfos.get("playerFaction"))))
                {
                    if (warInfos.get("status").equals("in_progress") || warInfos.get("status").equals("finished"))
                    {
                        int var24;
                        String[] var27;

                        if (this.data_ATT == null)
                        {
                            this.data_ATT = new HashMap();

                            if (warInfos.get("data_ATT") != null && !((String)warInfos.get("data_ATT")).isEmpty())
                            {
                                var27 = ((String)warInfos.get("data_ATT")).split(",");
                                var24 = var27.length;

                                for (var30 = 0; var30 < var24; ++var30)
                                {
                                    progressDEF = var27[var30];
                                    this.data_ATT.put(progressDEF.split("#")[0], Integer.valueOf(Integer.parseInt(progressDEF.split("#")[1])));
                                }
                            }
                        }

                        if (this.data_DEF == null)
                        {
                            this.data_DEF = new HashMap();

                            if (warInfos.get("data_DEF") != null && !((String)warInfos.get("data_DEF")).isEmpty())
                            {
                                var27 = ((String)warInfos.get("data_DEF")).split(",");
                                var24 = var27.length;

                                for (var30 = 0; var30 < var24; ++var30)
                                {
                                    progressDEF = var27[var30];
                                    this.data_DEF.put(progressDEF.split("#")[0], Integer.valueOf(Integer.parseInt(progressDEF.split("#")[1])));
                                }
                            }
                        }

                        side = "att";
                        float var25 = 0.0F;
                        float var39 = 0.0F;
                        float var38 = 0.0F;

                        if (warInfos.get("conditionsType").equals("or"))
                        {
                            var33 = ((String)warInfos.get("conditions")).split(",");
                            var31 = var33.length;

                            for (simpleDateFormat = 0; simpleDateFormat < var31; ++simpleDateFormat)
                            {
                                var42 = var33[simpleDateFormat];

                                if (this.data_ATT.containsKey(var42.split("#")[0]))
                                {
                                    var39 = Math.max(var39, (float)((Integer)this.data_ATT.get(var42.split("#")[0])).intValue() * 1.0F / (float)Integer.parseInt(var42.split("#")[1]));
                                }

                                if (this.data_DEF.containsKey(var42.split("#")[0]))
                                {
                                    var38 = Math.max(var38, (float)((Integer)this.data_DEF.get(var42.split("#")[0])).intValue() * 1.0F / (float)Integer.parseInt(var42.split("#")[1]));
                                }
                            }
                        }
                        else
                        {
                            var33 = ((String)warInfos.get("conditions")).split(",");
                            var31 = var33.length;

                            for (simpleDateFormat = 0; simpleDateFormat < var31; ++simpleDateFormat)
                            {
                                var42 = var33[simpleDateFormat];

                                if (this.data_ATT.containsKey(var42.split("#")[0]))
                                {
                                    var39 += (float)((Integer)this.data_ATT.get(var42.split("#")[0])).intValue() * 1.0F / (float)Integer.parseInt(var42.split("#")[1]);
                                }

                                if (this.data_DEF.containsKey(var42.split("#")[0]))
                                {
                                    var38 += (float)((Integer)this.data_DEF.get(var42.split("#")[0])).intValue() * 1.0F / (float)Integer.parseInt(var42.split("#")[1]);
                                }
                            }

                            var39 /= (float)((String)warInfos.get("conditions")).split(",").length;
                            var38 /= (float)((String)warInfos.get("conditions")).split(",").length;
                        }

                        if (var39 != 1.0F && (warInfos.get("winner") == null || !warInfos.get("winner").equals(warInfos.get("factionATTId"))))
                        {
                            if (var38 != 1.0F && (warInfos.get("winner") == null || !warInfos.get("winner").equals(warInfos.get("factionDEFId"))))
                            {
                                if (var39 >= var38)
                                {
                                    var25 = var39 - var38;
                                    side = "att";
                                }
                                else
                                {
                                    var25 = var38 - var39;
                                    side = "def";
                                }
                            }
                            else
                            {
                                this.winner = (String)warInfos.get("factionDEF");
                                var25 = -1.0F;
                            }
                        }
                        else
                        {
                            this.winner = (String)warInfos.get("factionATT");
                            var25 = 1.0F;
                        }

                        ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                        var29 = Float.valueOf(130.0F * (side.equals("att") ? -var25 : var25));
                        Gui.drawRect(this.guiLeft + 47, this.guiTop + 78, this.guiLeft + 47 + 130 + var29.intValue(), this.guiTop + 78 + 3, -15352550);
                        Gui.drawRect(this.guiLeft + 177 + var29.intValue(), this.guiTop + 78, this.guiLeft + 306, this.guiTop + 78 + 3, -3924707);
                        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 176 + var29.intValue() - 5), (float)(this.guiTop + 73), 330, 22, 11, 10, 512.0F, 512.0F, false);
                        this.drawScaledString(I18n.getString("faction.enemy.contitions") + " \u00a74(" + I18n.getString("faction.enemy.conditionsType." + warInfos.get("conditionsType")) + ")", this.guiLeft + 46, this.guiTop + 86, 0, 1.0F, false, false);
                        ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 48 + this.fontRenderer.getStringWidth(I18n.getString("faction.enemy.contitions") + " \u00a74(" + I18n.getString("faction.enemy.conditionsType." + warInfos.get("conditionsType")) + ")")), (float)(this.guiTop + 84), 71, 251, 10, 11, 512.0F, 512.0F, false);

                        if (mouseX >= this.guiLeft + 48 + this.fontRenderer.getStringWidth(I18n.getString("faction.enemy.contitions") + " \u00a74(" + I18n.getString("faction.enemy.conditionsType." + warInfos.get("conditionsType")) + ")") && mouseX <= this.guiLeft + 48 + this.fontRenderer.getStringWidth(I18n.getString("faction.enemy.contitions") + " \u00a74(" + I18n.getString("faction.enemy.conditionsType." + warInfos.get("conditionsType")) + ")") + 10 && mouseY >= this.guiTop + 84 && mouseY <= this.guiTop + 84 + 11)
                        {
                            tooltipToDraw = I18n.getString("faction.enemy.tooltip.conditions");
                        }

                        int offsetX;
                        Float offsetY;
                        String valueType;

                        if (warInfos.get("conditions") != null)
                        {
                            GUIUtils.startGLScissor(this.guiLeft + 47, this.guiTop + 96, 255, 60);
                            var31 = 0;
                            var35 = ((String)warInfos.get("conditions")).split(",");
                            dateString = var35.length;

                            for (var41 = 0; var41 < dateString; ++var41)
                            {
                                finishReward = var35[var41];

                                if (!finishReward.isEmpty() && finishReward.split("#").length == 2)
                                {
                                    rewardName = finishReward.split("#")[0];
                                    rewardGoal = finishReward.split("#")[1];
                                    offsetX = this.guiLeft + 47;
                                    offsetY = Float.valueOf((float)(this.guiTop + 96 + var31) + this.getSlideFinishConditions());
                                    ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                                    ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, offsetY.floatValue(), 47, 96, 255, 60, 512.0F, 512.0F, false);
                                    valueType = (this.data_ATT.containsKey(rewardName) ? (Serializable)this.data_ATT.get(rewardName) : "0") + "/" + rewardGoal + " " + I18n.getString("faction.enemy.conditions." + rewardName);

                                    if (this.data_ATT.containsKey(rewardName) && ((Integer)this.data_ATT.get(rewardName)).intValue() >= Integer.parseInt(rewardGoal))
                                    {
                                        valueType = "\u00a7a" + valueType;
                                    }

                                    this.drawScaledString(valueType, offsetX + 4, offsetY.intValue() + 5, 16777215, 1.0F, false, false);
                                    valueType = (this.data_DEF.containsKey(rewardName) ? (Serializable)this.data_DEF.get(rewardName) : "0") + "/" + rewardGoal + " " + I18n.getString("faction.enemy.conditions." + rewardName);

                                    if (this.data_DEF.containsKey(rewardName) && ((Integer)this.data_DEF.get(rewardName)).intValue() >= Integer.parseInt(rewardGoal))
                                    {
                                        valueType = "\u00a7a" + valueType;
                                    }

                                    this.drawScaledString(valueType, offsetX + 135, offsetY.intValue() + 5, 16777215, 1.0F, false, false);
                                    var31 += 20;
                                }
                            }

                            GUIUtils.endGLScissor();

                            if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 261 && mouseY >= this.guiTop + 95 && mouseY <= this.guiTop + 95 + 62)
                            {
                                this.scrollBarFinishConditions.draw(mouseX, mouseY);
                            }
                        }

                        this.drawScaledString(I18n.getString("faction.enemy.rewards"), this.guiLeft + 182, this.guiTop + 160, 0, 1.0F, false, false);
                        ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 184 + this.fontRenderer.getStringWidth(I18n.getString("faction.enemy.rewards"))), (float)(this.guiTop + 158), 71, 251, 10, 11, 512.0F, 512.0F, false);

                        if (mouseX >= this.guiLeft + 184 + this.fontRenderer.getStringWidth(I18n.getString("faction.enemy.rewards")) && mouseX <= this.guiLeft + 184 + this.fontRenderer.getStringWidth(I18n.getString("faction.enemy.rewards")) + 10 && mouseY >= this.guiTop + 158 && mouseY <= this.guiTop + 158 + 11)
                        {
                            tooltipToDraw = I18n.getString("faction.enemy.tooltip.rewards");
                        }

                        if (warInfos.get("rewards") != null)
                        {
                            GUIUtils.startGLScissor(this.guiLeft + 183, this.guiTop + 170, 119, 61);
                            var31 = 0;

                            if (!((String)warInfos.get("rewards")).isEmpty())
                            {
                                var35 = ((String)warInfos.get("rewards")).split(",");
                                dateString = var35.length;

                                for (var41 = 0; var41 < dateString; ++var41)
                                {
                                    finishReward = var35[var41];

                                    if (!finishReward.isEmpty() && finishReward.split("#").length == 2)
                                    {
                                        rewardName = finishReward.split("#")[0];
                                        rewardGoal = finishReward.split("#")[1];
                                        offsetX = this.guiLeft + 183;
                                        offsetY = Float.valueOf((float)(this.guiTop + 170 + var31) + this.getSlideFinishRewards());
                                        ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                                        ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, offsetY.floatValue(), 183, 170, 119, 20, 512.0F, 512.0F, false);

                                        if (Integer.parseInt(rewardGoal) > 0)
                                        {
                                            valueType = "";

                                            if (rewardName.equals("peace"))
                                            {
                                                valueType = I18n.getString("faction.enemy.rewards.valueType.day");
                                            }

                                            this.drawScaledString(rewardGoal + valueType + " " + I18n.getString("faction.enemy.rewards." + rewardName), offsetX + 4, offsetY.intValue() + 6, 16777215, 1.0F, false, false);
                                        }
                                        else
                                        {
                                            this.drawScaledString(I18n.getString("faction.enemy.rewards." + rewardName), offsetX + 4, offsetY.intValue() + 6, 16777215, 1.0F, false, false);
                                        }

                                        var31 += 20;
                                    }
                                }
                            }

                            GUIUtils.endGLScissor();

                            if (mouseX >= this.guiLeft + 182 && mouseX <= this.guiLeft + 182 + 125 && mouseY >= this.guiTop + 169 && mouseY <= this.guiTop + 169 + 63)
                            {
                                this.scrollBarFinishRewards.draw(mouseX, mouseY);
                            }
                        }

                        Date var46 = new Date(((Double)warInfos.get("enemyTime")).longValue());
                        SimpleDateFormat var45 = new SimpleDateFormat("dd/MM/yyy");
                        var42 = var45.format(var46);
                        this.drawScaledString(var42, this.guiLeft + 168 - this.fontRenderer.getStringWidth(var42), this.guiTop + 173, 16777215, 1.0F, false, false);

                        if (warInfos.get("playerTarget") != null && !((String)warInfos.get("playerTarget")).isEmpty())
                        {
                            ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 159), (float)(this.guiTop + 187), 71, 251, 10, 11, 512.0F, 512.0F, false);

                            if (mouseX >= this.guiLeft + 159 && mouseX <= this.guiLeft + 159 + 10 && mouseY >= this.guiTop + 187 && mouseY <= this.guiTop + 187 + 11)
                            {
                                tooltipToDraw = I18n.getString("faction.enemy.tooltip.reason").replace("#target#", (String)warInfos.get("playerTarget"));
                            }

                            this.drawScaledString(I18n.getString("faction.enemy.reason." + warInfos.get("reason")), this.guiLeft + 168 - this.fontRenderer.getStringWidth(I18n.getString("faction.enemy.reason." + warInfos.get("reason"))) - 14, this.guiTop + 189, 16777215, 1.0F, false, false);
                        }
                        else
                        {
                            this.drawScaledString(I18n.getString("faction.enemy.reason." + warInfos.get("reason")), this.guiLeft + 168 - this.fontRenderer.getStringWidth(I18n.getString("faction.enemy.reason." + warInfos.get("reason"))), this.guiTop + 189, 16777215, 1.0F, false, false);
                        }

                        var41 = 0;

                        if (this.data_ATT.containsKey("kill"))
                        {
                            var41 += ((Integer)this.data_ATT.get("kill")).intValue();
                        }

                        if (this.data_DEF.containsKey("kill"))
                        {
                            var41 += ((Integer)this.data_DEF.get("kill")).intValue();
                        }

                        this.drawScaledString(var41 + " kills", this.guiLeft + 168 - this.fontRenderer.getStringWidth(var41 + " kills"), this.guiTop + 205, 16777215, 1.0F, false, false);

                        if (((String)warInfos.get("linkForum")).isEmpty() || mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 125 && mouseY >= this.guiTop + 217 && mouseY <= this.guiTop + 217 + 15)
                        {
                            ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 46), (float)(this.guiTop + 217), 0, 302, 125, 15, 512.0F, 512.0F, false);
                            this.hoveredAction = "open_link_forum";
                        }

                        this.drawScaledString(I18n.getString("faction.enemy.linkForum"), this.guiLeft + 109, this.guiTop + 221, 0, 1.0F, true, false);

                        if (warInfos.get("status").equals("in_progress") && ((Boolean)warInfos.get("hasWarPermInOwnCountry")).booleanValue() && (warInfos.get("playerFaction").equals(warInfos.get("factionATT")) || warInfos.get("playerFaction").equals(warInfos.get("factionDEF"))))
                        {
                            ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 319), (float)(this.guiTop + 166), 319, 166, 23, 64, 512.0F, 512.0F, false);
                            GL11.glPushMatrix();
                            GL11.glTranslatef((float)(this.guiLeft + 325), (float)(this.guiTop + 212), 0.0F);
                            GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
                            GL11.glTranslatef((float)(-(this.guiLeft + 325)), (float)(-(this.guiTop + 212)), 0.0F);
                            this.drawScaledString(I18n.getString("faction.enemy.surrender"), this.guiLeft + 325, this.guiTop + 212, 0, 1.0F, false, false);
                            GL11.glPopMatrix();

                            if (mouseX >= this.guiLeft + 319 && mouseX <= this.guiLeft + 319 + 23 && mouseY >= this.guiTop + 166 && mouseY <= this.guiTop + 166 + 64)
                            {
                                if (warInfos.containsKey("enemyTime") && System.currentTimeMillis() - ((Double)warInfos.get("enemyTime")).longValue() < 604800000L)
                                {
                                    tooltipToDraw = I18n.getString("faction.enemy.tooltip.surrender_delay");
                                }
                                else
                                {
                                    this.hoveredAction = "surrender";
                                }
                            }
                        }
                    }
                }
                else
                {
                    this.drawScaledString(I18n.getString("faction.enemy.contitions"), this.guiLeft + 46, this.guiTop + 84, 0, 1.0F, false, false);
                    this.drawScaledString(I18n.getString("faction.enemy.conditionsType." + this.selectedConditionsType), this.guiLeft + 104, this.guiTop + 85, 16777215, 1.0F, false, false);
                    this.conditionInput.drawTextBox();
                    this.drawScaledString(I18n.getString("faction.enemy.conditions." + this.selectedCondition), this.guiLeft + 209, this.guiTop + 85, 16777215, 1.0F, false, false);

                    if (mouseX >= this.guiLeft + 287 && mouseX <= this.guiLeft + 287 + 20 && mouseY >= this.guiTop + 78 && mouseY <= this.guiTop + 78 + 20)
                    {
                        ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 287), (float)(this.guiTop + 78), 269, 328, 20, 20, 512.0F, 512.0F, false);
                        this.hoveredAction = "add_condition";

                        if (this.selectedCondition.equalsIgnoreCase("kill"))
                        {
                            tooltipToDraw = "\u00a7cMin 10";
                        }
                        else if (this.selectedCondition.equalsIgnoreCase("victory"))
                        {
                            tooltipToDraw = "\u00a7cMin 5";
                        }
                        else if (this.selectedCondition.equalsIgnoreCase("assault_point"))
                        {
                            tooltipToDraw = "\u00a7cMin 500";
                        }
                        else if (this.selectedCondition.equalsIgnoreCase("missile_point"))
                        {
                            tooltipToDraw = "\u00a7cMin 20";
                        }
                    }

                    GUIUtils.startGLScissor(this.guiLeft + 47, this.guiTop + 101, 255, 45);
                    int var23 = 0;
                    Iterator var22;
                    Entry var26;
                    int var28;

                    for (var22 = this.conditions.entrySet().iterator(); var22.hasNext(); var23 += 23)
                    {
                        var26 = (Entry)var22.next();
                        var28 = this.guiLeft + 47;
                        var29 = Float.valueOf((float)(this.guiTop + 101 + var23) + this.getSlideConditions());
                        ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                        ModernGui.drawModalRectWithCustomSizedTexture((float)var28, var29.floatValue(), 47, 101, 255, 23, 512.0F, 512.0F, false);
                        this.drawScaledString(var26.getValue() + " " + I18n.getString("faction.enemy.conditions." + var26.getKey()), var28 + 2, var29.intValue() + 7, 16777215, 1.0F, false, false);
                        ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);

                        if (!this.conditionsOpened && mouseX >= var28 + 233 && mouseX <= var28 + 233 + 20 && (float)mouseY >= var29.floatValue() + 3.0F && (float)mouseY <= var29.floatValue() + 3.0F + 16.0F)
                        {
                            ModernGui.drawModalRectWithCustomSizedTexture((float)(var28 + 233), var29.floatValue() + 3.0F, 298, 323, 20, 16, 512.0F, 512.0F, false);
                            this.hoveredCondition = (String)var26.getKey();
                        }
                        else
                        {
                            ModernGui.drawModalRectWithCustomSizedTexture((float)(var28 + 233), var29.floatValue() + 3.0F, 298, 307, 20, 16, 512.0F, 512.0F, false);
                        }
                    }

                    GUIUtils.endGLScissor();

                    if (!this.conditionsOpened && mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 261 && mouseY >= this.guiTop + 100 && mouseY <= this.guiTop + 100 + 47)
                    {
                        this.scrollBarConditions.draw(mouseX, mouseY);
                    }

                    if (this.conditionsTypeOpened)
                    {
                        ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 100), (float)(this.guiTop + 97), 464, 83, 48, 20, 512.0F, 512.0F, false);
                        this.drawScaledString(I18n.getString("faction.enemy.conditionsType.and"), this.guiLeft + 104, this.guiTop + 104, 16777215, 1.0F, false, false);

                        if (mouseX >= this.guiLeft + 100 && mouseX <= this.guiLeft + 100 + 48 && mouseY >= this.guiTop + 98 && mouseY <= this.guiTop + 98 + 19)
                        {
                            this.hoveredConditionsType = "and";
                        }

                        ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 100), (float)(this.guiTop + 116), 464, 83, 48, 20, 512.0F, 512.0F, false);
                        this.drawScaledString(I18n.getString("faction.enemy.conditionsType.or"), this.guiLeft + 104, this.guiTop + 123, 16777215, 1.0F, false, false);

                        if (mouseX >= this.guiLeft + 100 && mouseX <= this.guiLeft + 100 + 48 && mouseY >= this.guiTop + 117 && mouseY <= this.guiTop + 117 + 19)
                        {
                            this.hoveredConditionsType = "or";
                        }
                    }

                    this.drawScaledString(I18n.getString("faction.enemy.rewards"), this.guiLeft + 46, this.guiTop + 156, 0, 1.0F, false, false);
                    this.rewardInput.drawTextBox();
                    this.drawScaledString(I18n.getString("faction.enemy.rewards." + this.selectedReward), this.guiLeft + 209, this.guiTop + 157, 16777215, 1.0F, false, false);

                    if (mouseX >= this.guiLeft + 287 && mouseX <= this.guiLeft + 287 + 20 && mouseY >= this.guiTop + 150 && mouseY <= this.guiTop + 150 + 20)
                    {
                        ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 287), (float)(this.guiTop + 150), 269, 328, 20, 20, 512.0F, 512.0F, false);
                        this.hoveredAction = "add_reward";
                    }

                    GUIUtils.startGLScissor(this.guiLeft + 47, this.guiTop + 173, 255, 45);
                    var23 = 0;

                    for (var22 = this.rewards.entrySet().iterator(); var22.hasNext(); var23 += 23)
                    {
                        var26 = (Entry)var22.next();
                        var28 = this.guiLeft + 47;
                        var29 = Float.valueOf((float)(this.guiTop + 173 + var23) + this.getSlideRewards());
                        ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                        ModernGui.drawModalRectWithCustomSizedTexture((float)var28, var29.floatValue(), 47, 101, 255, 23, 512.0F, 512.0F, false);

                        if (((Integer)var26.getValue()).intValue() > 0)
                        {
                            String var36 = "";

                            if (var26.getKey().equals("peace"))
                            {
                                var36 = I18n.getString("faction.enemy.rewards.valueType.day");
                            }

                            this.drawScaledString(var26.getValue() + var36 + " " + I18n.getString("faction.enemy.rewards." + var26.getKey()), var28 + 2, var29.intValue() + 7, 16777215, 1.0F, false, false);
                        }
                        else
                        {
                            this.drawScaledString(I18n.getString("faction.enemy.rewards." + var26.getKey()), var28 + 2, var29.intValue() + 7, 16777215, 1.0F, false, false);
                        }

                        ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);

                        if (!this.rewardsOpened && mouseX >= var28 + 233 && mouseX <= var28 + 233 + 20 && (float)mouseY >= var29.floatValue() + 3.0F && (float)mouseY <= var29.floatValue() + 3.0F + 16.0F)
                        {
                            ModernGui.drawModalRectWithCustomSizedTexture((float)(var28 + 233), var29.floatValue() + 3.0F, 298, 323, 20, 16, 512.0F, 512.0F, false);
                            this.hoveredReward = (String)var26.getKey();
                        }
                        else
                        {
                            ModernGui.drawModalRectWithCustomSizedTexture((float)(var28 + 233), var29.floatValue() + 3.0F, 298, 307, 20, 16, 512.0F, 512.0F, false);
                        }
                    }

                    GUIUtils.endGLScissor();

                    if (!this.rewardsOpened && mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 261 && mouseY >= this.guiTop + 172 && mouseY <= this.guiTop + 172 + 47)
                    {
                        this.scrollBarRewards.draw(mouseX, mouseY);
                    }

                    ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);

                    if (warInfos.get("status").equals("waiting_conditions_att"))
                    {
                        if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 262 && mouseY >= this.guiTop + 221 && mouseY <= this.guiTop + 219 + 15)
                        {
                            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 46), (float)(this.guiTop + 221), 0, 323, 262, 15, 512.0F, 512.0F, false);
                            this.hoveredAction = "send_conditions_att";
                        }

                        this.drawScaledString(I18n.getString("faction.enemy.send_conditions_att"), this.guiLeft + 177, this.guiTop + 225, 16777215, 1.0F, true, false);
                    }
                    else
                    {
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 46), (float)(this.guiTop + 221), 0, 399, 262, 15, 512.0F, 512.0F, false);

                        if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 262 && mouseY >= this.guiTop + 221 && mouseY <= this.guiTop + 219 + 15)
                        {
                            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 46), (float)(this.guiTop + 221), 0, 414, 262, 15, 512.0F, 512.0F, false);
                            this.hoveredAction = "send_conditions_def";
                        }

                        this.drawScaledString(I18n.getString("faction.enemy.send_conditions_def"), this.guiLeft + 177, this.guiTop + 225, 16777215, 1.0F, true, false);
                    }

                    if (warInfos.get("status").equals("waiting_conditions_def"))
                    {
                        ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 319), (float)(this.guiTop + 12), 319, 12, 102, 222, 512.0F, 512.0F, false);
                        this.drawScaledString("\u00a77\u00a7l" + I18n.getString("faction.enemy.contitions") + " \u00a74\u00a7l" + I18n.getString("faction.enemy.conditionsType." + warInfos.get("conditionsType_ATT")), this.guiLeft + 326, this.guiTop + 42, 16777215, 1.0F, false, false);
                        var30 = 1;

                        if (warInfos.get("conditions_ATT") != null)
                        {
                            String[] var32 = ((String)warInfos.get("conditions_ATT")).split(",");
                            offset = var32.length;

                            for (var31 = 0; var31 < offset; ++var31)
                            {
                                String var37 = var32[var31];
                                this.drawScaledString("\u00a7f\u25cf " + var37.split("#")[1] + " " + I18n.getString("faction.enemy.conditions." + var37.split("#")[0]), this.guiLeft + 326, this.guiTop + 42 + 10 * var30, 16777215, 1.0F, false, false);
                                ++var30;
                            }
                        }
                        else
                        {
                            this.drawScaledString("\u00a7c\u2716 " + I18n.getString("faction.enemy.none"), this.guiLeft + 326, this.guiTop + 42 + 10, 16777215, 1.0F, false, false);
                        }

                        var28 = var30 * 10;
                        var30 = 1;
                        this.drawScaledString("\u00a77\u00a7l" + I18n.getString("faction.enemy.rewards"), this.guiLeft + 326, this.guiTop + 62 + var28, 16777215, 1.0F, false, false);

                        if (warInfos.get("rewards_ATT") != null && !((String)warInfos.get("rewards_ATT")).isEmpty())
                        {
                            var33 = ((String)warInfos.get("rewards_ATT")).split(",");
                            var31 = var33.length;

                            for (simpleDateFormat = 0; simpleDateFormat < var31; ++simpleDateFormat)
                            {
                                var42 = var33[simpleDateFormat];

                                if (Integer.parseInt(var42.split("#")[1]) > 0)
                                {
                                    totalKills = "";

                                    if (var42.split("#")[0].equals("peace"))
                                    {
                                        totalKills = I18n.getString("faction.enemy.rewards.valueType.day");
                                    }

                                    this.drawScaledString("\u00a7f\u25cf " + var42.split("#")[1] + totalKills + " " + I18n.getString("faction.enemy.rewards." + var42.split("#")[0]), this.guiLeft + 326, this.guiTop + 62 + var28 + 10 * var30, 16777215, 1.0F, false, false);
                                }
                                else
                                {
                                    this.drawScaledString("\u00a7f\u25cf " + I18n.getString("faction.enemy.rewards." + var42.split("#")[0]), this.guiLeft + 326, this.guiTop + 62 + var28 + 10 * var30, 16777215, 1.0F, false, false);
                                }

                                ++var30;
                            }
                        }
                        else
                        {
                            this.drawScaledString("\u00a7c\u2716 " + I18n.getString("faction.enemy.none"), this.guiLeft + 326, this.guiTop + 62 + var28 + 10 * var30, 16777215, 1.0F, false, false);
                        }

                        if (warInfos.get("conditions_ATT") == null || warInfos.get("rewards_ATT") == null || mouseX >= this.guiLeft + 323 && mouseX <= this.guiLeft + 323 + 90 && mouseY >= this.guiTop + 209 && mouseY <= this.guiTop + 209 + 15)
                        {
                            ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 323), (float)(this.guiTop + 209), 0, 369, 90, 15, 512.0F, 512.0F, false);

                            if (warInfos.get("conditions_ATT") != null && warInfos.get("rewards_ATT") != null)
                            {
                                this.hoveredAction = "accept_conditions_def";
                            }
                        }

                        this.drawScaledString(I18n.getString("faction.enemy.accept_conditions_def"), this.guiLeft + 368, this.guiTop + 213, 16777215, 1.0F, true, false);
                    }

                    Iterator var34;
                    Float var43;

                    if (this.rewardsOpened)
                    {
                        ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 205), (float)(this.guiTop + 169), 432, 0, 80, 77, 512.0F, 512.0F, false);
                        GUIUtils.startGLScissor(this.guiLeft + 206, this.guiTop + 170, 74, 75);
                        var23 = 0;

                        for (var34 = this.availableRewards.iterator(); var34.hasNext(); var23 += 19)
                        {
                            progressDEF = (String)var34.next();
                            offset = this.guiLeft + 206;
                            var43 = Float.valueOf((float)(this.guiTop + 170 + var23) + this.getSlideAvailableRewards());
                            ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                            ModernGui.drawModalRectWithCustomSizedTexture((float)offset, var43.floatValue(), 433, 1, 74, 19, 512.0F, 512.0F, false);
                            this.drawScaledString(I18n.getString("faction.enemy.rewards." + progressDEF), offset + 2, var43.intValue() + 5, 16777215, 1.0F, false, false);

                            if (mouseX >= offset && mouseX <= offset + 74 && (float)mouseY >= var43.floatValue() && (float)mouseY <= var43.floatValue() + 19.0F)
                            {
                                this.hoveredAvailableReward = progressDEF;
                            }
                        }

                        GUIUtils.endGLScissor();
                        this.scrollBarAvailableRewards.draw(mouseX, mouseY);
                    }

                    if (this.conditionsOpened)
                    {
                        ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 205), (float)(this.guiTop + 97), 432, 0, 80, 77, 512.0F, 512.0F, false);
                        GUIUtils.startGLScissor(this.guiLeft + 206, this.guiTop + 98, 74, 75);
                        var23 = 0;

                        for (var34 = this.availableConditions.iterator(); var34.hasNext(); var23 += 19)
                        {
                            progressDEF = (String)var34.next();
                            offset = this.guiLeft + 206;
                            var43 = Float.valueOf((float)(this.guiTop + 98 + var23) + this.getSlideAvailableConditions());
                            ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                            ModernGui.drawModalRectWithCustomSizedTexture((float)offset, var43.floatValue(), 433, 1, 74, 19, 512.0F, 512.0F, false);
                            this.drawScaledString(I18n.getString("faction.enemy.conditions." + progressDEF), offset + 2, var43.intValue() + 5, 16777215, 1.0F, false, false);

                            if (mouseX >= offset && mouseX <= offset + 74 && (float)mouseY >= var43.floatValue() && (float)mouseY <= var43.floatValue() + 19.0F)
                            {
                                this.hoveredAvailableCondition = progressDEF;
                            }
                        }

                        GUIUtils.endGLScissor();
                        this.scrollBarAvailableConditions.draw(mouseX, mouseY);
                    }
                }
            }
            else
            {
                this.drawScaledString(I18n.getString("faction.enemy.reason"), this.guiLeft + 46, this.guiTop + 78, 0, 1.0F, false, false);
                this.drawScaledString(I18n.getString("faction.enemy.reason." + warInfos.get("reason")), this.guiLeft + 50, this.guiTop + 94, 16777215, 1.0F, false, false);

                if (warInfos.get("playerTarget") != null && !((String)warInfos.get("playerTarget")).isEmpty())
                {
                    ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 159), (float)(this.guiTop + 92), 71, 251, 10, 11, 512.0F, 512.0F, false);

                    if (mouseX >= this.guiLeft + 159 && mouseX <= this.guiLeft + 159 + 10 && mouseY >= this.guiTop + 92 && mouseY <= this.guiTop + 92 + 11)
                    {
                        tooltipToDraw = I18n.getString("faction.enemy.tooltip.reason").replace("#target#", (String)warInfos.get("playerTarget"));
                    }
                }

                this.drawScaledString(I18n.getString("faction.enemy.creationDate"), this.guiLeft + 182, this.guiTop + 78, 0, 1.0F, false, false);
                Date var21 = new Date(((Double)warInfos.get("creationTime")).longValue());
                SimpleDateFormat progress = new SimpleDateFormat("dd/MM/yyy HH:mm");
                this.drawScaledString(progress.format(var21), this.guiLeft + 186, this.guiTop + 94, 16777215, 1.0F, false, false);
                this.drawScaledString(I18n.getString("faction.enemy.linkForum"), this.guiLeft + 46, this.guiTop + 110, 0, 1.0F, false, false);

                if (!warInfos.get("reason").equals("under_power"))
                {
                    this.linkForumInput.drawTextBox();

                    if (this.linkForumInput.getText().isEmpty() && !((String)warInfos.get("linkForum")).isEmpty())
                    {
                        this.linkForumInput.setText((String)warInfos.get("linkForum"));
                    }

                    ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

                    if (!this.linkForumInput.getText().isEmpty())
                    {
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 286), (float)(this.guiTop + 121), 148, 250, 20, 16, 512.0F, 512.0F, false);
                    }
                    else
                    {
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 286), (float)(this.guiTop + 121), 169, 250, 20, 16, 512.0F, 512.0F, false);
                    }
                }
                else
                {
                    this.drawScaledString(I18n.getString("faction.enemy.noLinkForum"), this.guiLeft + 50, this.guiTop + 126, 16777215, 1.0F, false, false);
                }

                String[] progressATT = null;

                if (warInfos.get("reason").equals("under_power"))
                {
                    progressATT = I18n.getString("faction.enemy.status.details.special.under_power").split(" ");
                }
                else if (!warInfos.get("status").equals("waiting_conditions_att_second"))
                {
                    progressATT = I18n.getString("faction.enemy.status.details." + warInfos.get("status")).split(" ");
                }
                else if (warInfos.get("factionATT").equals(warInfos.get("playerFaction")))
                {
                    progressATT = I18n.getString("faction.enemy.status.details." + warInfos.get("status") + ".att").split(" ");
                }
                else
                {
                    progressATT = I18n.getString("faction.enemy.status.details." + warInfos.get("status") + ".def").split(" ");
                }

                progressDEF = "";
                offset = 0;
                String[] date = progressATT;
                simpleDateFormat = progressATT.length;

                for (dateString = 0; dateString < simpleDateFormat; ++dateString)
                {
                    totalKills = date[dateString];

                    if ((double)this.fontRenderer.getStringWidth(progressDEF + totalKills) * 0.9D <= 190.0D)
                    {
                        if (!progressDEF.equals(""))
                        {
                            progressDEF = progressDEF + " ";
                        }

                        progressDEF = progressDEF + totalKills;
                    }
                    else
                    {
                        this.drawScaledString(progressDEF, this.guiLeft + 50, this.guiTop + 150 + offset * 10, 16777215, 0.9F, false, false);
                        ++offset;
                        progressDEF = totalKills;
                    }
                }

                this.drawScaledString(progressDEF, this.guiLeft + 50, this.guiTop + 150 + offset * 10, 16777215, 0.9F, false, false);

                if (warInfos.get("status").equals("waiting_conditions_att_second") && warInfos.get("factionATT").equals(warInfos.get("playerFaction")))
                {
                    ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 319), (float)(this.guiTop + 12), 319, 12, 102, 222, 512.0F, 512.0F, false);
                    this.drawScaledString("\u00a77\u00a7l" + I18n.getString("faction.enemy.contitions") + " \u00a74\u00a7l" + I18n.getString("faction.enemy.conditionsType." + warInfos.get("conditionsType_DEF")), this.guiLeft + 326, this.guiTop + 42, 16777215, 1.0F, false, false);
                    var31 = 1;

                    if (warInfos.get("conditions_DEF") != null)
                    {
                        var35 = ((String)warInfos.get("conditions_DEF")).split(",");
                        dateString = var35.length;

                        for (var41 = 0; var41 < dateString; ++var41)
                        {
                            finishReward = var35[var41];
                            this.drawScaledString("\u00a7f\u25cf " + finishReward.split("#")[1] + " " + I18n.getString("faction.enemy.conditions." + finishReward.split("#")[0]), this.guiLeft + 326, this.guiTop + 42 + 10 * var31, 16777215, 1.0F, false, false);
                            ++var31;
                        }
                    }
                    else
                    {
                        this.drawScaledString("\u00a7c\u2716 " + I18n.getString("faction.enemy.none"), this.guiLeft + 326, this.guiTop + 42 + 10, 16777215, 1.0F, false, false);
                    }

                    simpleDateFormat = var31 * 10;
                    var31 = 1;
                    this.drawScaledString("\u00a77\u00a7l" + I18n.getString("faction.enemy.rewards"), this.guiLeft + 326, this.guiTop + 62 + simpleDateFormat, 16777215, 1.0F, false, false);

                    if (warInfos.get("rewards_DEF") != null && !((String)warInfos.get("rewards_DEF")).isEmpty())
                    {
                        String[] var40 = ((String)warInfos.get("rewards_DEF")).split(",");
                        var41 = var40.length;

                        for (int var44 = 0; var44 < var41; ++var44)
                        {
                            rewardName = var40[var44];

                            if (Integer.parseInt(rewardName.split("#")[1]) > 0)
                            {
                                rewardGoal = "";

                                if (rewardName.split("#")[0].equals("peace"))
                                {
                                    rewardGoal = I18n.getString("faction.enemy.rewards.valueType.day");
                                }

                                this.drawScaledString("\u00a7f\u25cf " + rewardName.split("#")[1] + rewardGoal + " " + I18n.getString("faction.enemy.rewards." + rewardName.split("#")[0]), this.guiLeft + 326, this.guiTop + 62 + simpleDateFormat + 10 * var31, 16777215, 1.0F, false, false);
                            }
                            else
                            {
                                this.drawScaledString("\u00a7f\u25cf " + I18n.getString("faction.enemy.rewards." + rewardName.split("#")[0]), this.guiLeft + 326, this.guiTop + 62 + simpleDateFormat + 10 * var31, 16777215, 1.0F, false, false);
                            }

                            ++var31;
                        }
                    }
                    else
                    {
                        this.drawScaledString("\u00a7c\u2716 " + I18n.getString("faction.enemy.none"), this.guiLeft + 326, this.guiTop + 62 + simpleDateFormat + 10 * var31, 16777215, 1.0F, false, false);
                    }

                    if (warInfos.get("factionATT").equals(warInfos.get("playerFaction")) && ((Boolean)warInfos.get("hasWarPermInOwnCountry")).booleanValue())
                    {
                        ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 323), (float)(this.guiTop + 209), 0, 466, 90, 15, 512.0F, 512.0F, false);

                        if (warInfos.get("conditions_DEF") == null || warInfos.get("rewards_DEF") == null || mouseX >= this.guiLeft + 323 && mouseX <= this.guiLeft + 323 + 90 && mouseY >= this.guiTop + 209 && mouseY <= this.guiTop + 209 + 15)
                        {
                            ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 323), (float)(this.guiTop + 209), 0, 481, 90, 15, 512.0F, 512.0F, false);

                            if (warInfos.get("conditions_DEF") != null && warInfos.get("rewards_DEF") != null)
                            {
                                this.hoveredAction = "accept_conditions_att_second";
                            }
                        }

                        this.drawScaledString(I18n.getString("faction.enemy.accept_conditions_def"), this.guiLeft + 368, this.guiTop + 213, 16777215, 1.0F, true, false);
                    }
                }

                if (warInfos.get("status").equals("waiting_validation") && warInfos.get("factionATT").equals(warInfos.get("playerFaction")) && ((Boolean)warInfos.get("hasWarPermInOwnCountry")).booleanValue())
                {
                    ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 46), (float)(this.guiTop + 219), 0, 432, 127, 15, 512.0F, 512.0F, false);

                    if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 127 && mouseY >= this.guiTop + 219 && mouseY <= this.guiTop + 219 + 15)
                    {
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 46), (float)(this.guiTop + 219), 0, 447, 127, 15, 512.0F, 512.0F, false);
                        this.hoveredAction = "cancel";
                    }

                    this.drawScaledString(I18n.getString("faction.enemy.cancel_request"), this.guiLeft + 110, this.guiTop + 223, 16777215, 1.0F, true, false);
                    ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 181), (float)(this.guiTop + 219), 0, 402, 127, 15, 512.0F, 512.0F, false);

                    if (mouseX >= this.guiLeft + 181 && mouseX <= this.guiLeft + 181 + 127 && mouseY >= this.guiTop + 219 && mouseY <= this.guiTop + 219 + 15)
                    {
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 181), (float)(this.guiTop + 219), 0, 417, 127, 15, 512.0F, 512.0F, false);
                        this.hoveredAction = "save_forum";
                    }

                    this.drawScaledString(I18n.getString("faction.enemy.save_request"), this.guiLeft + 245, this.guiTop + 223, 16777215, 1.0F, true, false);
                }
                else if (warInfos.get("status").equals("waiting_validation") && ((Boolean)warInfos.get("hasStaffPerm")).booleanValue())
                {
                    ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 46), (float)(this.guiTop + 219), 0, 432, 127, 15, 512.0F, 512.0F, false);

                    if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 127 && mouseY >= this.guiTop + 219 && mouseY <= this.guiTop + 219 + 15)
                    {
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 46), (float)(this.guiTop + 219), 0, 447, 127, 15, 512.0F, 512.0F, false);
                        this.hoveredAction = "staff_refuse";
                    }

                    this.drawScaledString(I18n.getString("faction.enemy.refuse_request"), this.guiLeft + 110, this.guiTop + 223, 16777215, 1.0F, true, false);
                    ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 181), (float)(this.guiTop + 219), 0, 402, 127, 15, 512.0F, 512.0F, false);

                    if (mouseX >= this.guiLeft + 181 && mouseX <= this.guiLeft + 181 + 127 && mouseY >= this.guiTop + 219 && mouseY <= this.guiTop + 219 + 15)
                    {
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 181), (float)(this.guiTop + 219), 0, 417, 127, 15, 512.0F, 512.0F, false);
                        this.hoveredAction = "staff_accept";
                    }

                    this.drawScaledString(I18n.getString("faction.enemy.accept_request"), this.guiLeft + 245, this.guiTop + 223, 16777215, 1.0F, true, false);
                }
                else if (warInfos.get("status").equals("waiting_conditions_att_second") && warInfos.get("factionATT").equals(warInfos.get("playerFaction")) && ((Boolean)warInfos.get("hasWarPermInOwnCountry")).booleanValue())
                {
                    ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 46), (float)(this.guiTop + 219), 0, 353, 262, 15, 512.0F, 512.0F, false);

                    if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 262 && mouseY >= this.guiTop + 219 && mouseY <= this.guiTop + 219 + 15)
                    {
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 46), (float)(this.guiTop + 219), 0, 368, 262, 15, 512.0F, 512.0F, false);
                        this.hoveredAction = "refuse_conditions_att_second";
                    }

                    this.drawScaledString(I18n.getString("faction.enemy.refuse_conditions_att_second"), this.guiLeft + 177, this.guiTop + 223, 16777215, 1.0F, true, false);
                }
            }
        }

        if (tooltipToDraw != "")
        {
            this.drawHoveringText(Arrays.asList(tooltipToDraw.split("##")), mouseX, mouseY, this.fontRenderer);
        }
    }

    private float getSlideAvailableConditions()
    {
        return this.availableConditions.size() > 4 ? (float)(-(this.availableConditions.size() - 4) * 19) * this.scrollBarAvailableConditions.getSliderValue() : 0.0F;
    }

    private float getSlideAvailableRewards()
    {
        return this.availableRewards.size() > 4 ? (float)(-(this.availableRewards.size() - 4) * 19) * this.scrollBarAvailableRewards.getSliderValue() : 0.0F;
    }

    private float getSlideConditions()
    {
        return this.conditions.size() > 2 ? (float)(-(this.conditions.size() - 2) * 23) * this.scrollBarConditions.getSliderValue() : 0.0F;
    }

    private float getSlideRewards()
    {
        return this.rewards.size() > 2 ? (float)(-(this.rewards.size() - 2) * 23) * this.scrollBarRewards.getSliderValue() : 0.0F;
    }

    private float getSlideFinishConditions()
    {
        return ((String)warInfos.get("conditions")).split(",").length > 3 ? (float)(-(((String)warInfos.get("conditions")).split(",").length - 3) * 20) * this.scrollBarFinishConditions.getSliderValue() : 0.0F;
    }

    private float getSlideFinishRewards()
    {
        return ((String)warInfos.get("rewards")).split(",").length > 3 ? (float)(-(((String)warInfos.get("rewards")).split(",").length - 3) * 20) * this.scrollBarFinishRewards.getSliderValue() : 0.0F;
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
            if (mouseX > this.guiLeft + 305 && mouseX < this.guiLeft + 305 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                this.mc.displayGuiScreen(this.guiFrom);
            }

            Class conditionsString;
            Object rewardsString;

            if (!this.hoveredAction.isEmpty())
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);

                if (this.hoveredAction.equals("cancel"))
                {
                    PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionEnemyRequestUpdateStatusPacket(Integer.valueOf(warRequestId), "cancelled", (String)warInfos.get("status"), (String)warInfos.get("factionATT"), (String)warInfos.get("factionDEF"))));
                    this.mc.displayGuiScreen((GuiScreen)null);
                }
                else if (this.hoveredAction.equals("staff_refuse"))
                {
                    PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionEnemyRequestUpdateStatusPacket(Integer.valueOf(warRequestId), "refused", (String)warInfos.get("status"), (String)warInfos.get("factionATT"), (String)warInfos.get("factionDEF"))));
                    this.mc.displayGuiScreen(this.guiFrom);
                }
                else if (!this.hoveredAction.equals("staff_accept") && !this.hoveredAction.equals("accept"))
                {
                    if (this.hoveredAction.equals("accept_conditions_def"))
                    {
                        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionEnemyRequestUpdateStatusPacket(Integer.valueOf(warRequestId), "in_progress", (String)warInfos.get("status"), (String)warInfos.get("factionATT"), (String)warInfos.get("factionDEF"))));
                        this.mc.displayGuiScreen(this.guiFrom);
                    }
                    else if (this.hoveredAction.equals("accept_conditions_att_second"))
                    {
                        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionEnemyRequestUpdateStatusPacket(Integer.valueOf(warRequestId), "in_progress", (String)warInfos.get("status"), (String)warInfos.get("factionATT"), (String)warInfos.get("factionDEF"))));
                        this.mc.displayGuiScreen(this.guiFrom);
                    }
                    else if (this.hoveredAction.equals("refuse_conditions_att_second"))
                    {
                        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionEnemyRequestGenerateConditionsPacket(Integer.valueOf(warRequestId))));
                        this.mc.displayGuiScreen(this.guiFrom);
                    }
                    else if (this.hoveredAction.equals("save_forum"))
                    {
                        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionEnemyRequestUpdateForumPacket(Integer.valueOf(warRequestId), this.linkForumInput.getText())));
                        this.mc.displayGuiScreen(this.guiFrom);
                    }
                    else if (this.hoveredAction.equals("open_link_forum"))
                    {
                        if (warInfos != null && !((String)warInfos.get("linkForum")).isEmpty())
                        {
                            try
                            {
                                conditionsString = Class.forName("java.awt.Desktop");
                                rewardsString = conditionsString.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
                                conditionsString.getMethod("browse", new Class[] {URI.class}).invoke(rewardsString, new Object[] {URI.create((String)warInfos.get("linkForum"))});
                            }
                            catch (Throwable var9)
                            {
                                var9.printStackTrace();
                            }
                        }
                    }
                    else if (this.hoveredAction.equals("surrender"))
                    {
                        ;
                    }
                }
                else
                {
                    PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionEnemyRequestUpdateStatusPacket(Integer.valueOf(warRequestId), "waiting_conditions_att", (String)warInfos.get("status"), (String)warInfos.get("factionATT"), (String)warInfos.get("factionDEF"))));
                    this.mc.displayGuiScreen(this.guiFrom);
                }
            }

            if (!this.linkForumInput.getText().isEmpty() && mouseX >= this.guiLeft + 286 && mouseX <= this.guiLeft + 286 + 20 && mouseY >= this.guiTop + 121 && mouseY <= this.guiTop + 121 + 16 && (this.linkForumInput.getText().contains("https://nationsglory.fr/forums") || this.linkForumInput.getText().contains("forum.nationsglory.fr")))
            {
                try
                {
                    conditionsString = Class.forName("java.awt.Desktop");
                    rewardsString = conditionsString.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
                    conditionsString.getMethod("browse", new Class[] {URI.class}).invoke(rewardsString, new Object[] {URI.create(this.linkForumInput.getText())});
                }
                catch (Throwable var8)
                {
                    var8.printStackTrace();
                }
            }

            if (mouseX >= this.guiLeft + 100 && mouseX <= this.guiLeft + 100 + 48 && mouseY >= this.guiTop + 78 && mouseY <= this.guiTop + 78 + 20)
            {
                this.conditionsTypeOpened = !this.conditionsTypeOpened;
            }

            if (!this.hoveredConditionsType.isEmpty())
            {
                this.selectedConditionsType = this.hoveredConditionsType;
                this.hoveredConditionsType = "";
                this.conditionsTypeOpened = false;
            }

            if (mouseX >= this.guiLeft + 205 && mouseX <= this.guiLeft + 205 + 80 && mouseY >= this.guiTop + 78 && mouseY <= this.guiTop + 78 + 20)
            {
                this.conditionsOpened = !this.conditionsOpened;
                this.scrollBarAvailableConditions.reset();
            }

            if (!this.hoveredAvailableCondition.isEmpty())
            {
                this.selectedCondition = this.hoveredAvailableCondition;
                this.hoveredAvailableCondition = "";
                this.conditionsOpened = false;
            }

            int conditionsString1;

            if (this.hoveredAction.equals("add_condition") && !this.conditionInput.getText().isEmpty() && this.isNumeric(this.conditionInput.getText()))
            {
                conditionsString1 = Integer.parseInt(this.conditionInput.getText());

                if (this.selectedCondition.equalsIgnoreCase("kill") && conditionsString1 < 10)
                {
                    return;
                }

                if (this.selectedCondition.equalsIgnoreCase("victory") && conditionsString1 < 5)
                {
                    return;
                }

                if (this.selectedCondition.equalsIgnoreCase("assault_point") && conditionsString1 < 500)
                {
                    return;
                }

                if (this.selectedCondition.equalsIgnoreCase("missile_point") && conditionsString1 < 20)
                {
                    return;
                }

                this.conditions.put(this.selectedCondition, Integer.valueOf(Integer.parseInt(this.conditionInput.getText())));
                this.conditionInput.setText("0");
                this.selectedCondition = (String)this.availableConditions.get(0);
            }

            if (!this.hoveredCondition.isEmpty())
            {
                this.conditions.remove(this.hoveredCondition);
                this.hoveredCondition = "";
                this.scrollBarConditions.reset();
            }

            if (!this.conditionsOpened && mouseX >= this.guiLeft + 205 && mouseX <= this.guiLeft + 205 + 80 && mouseY >= this.guiTop + 150 && mouseY <= this.guiTop + 150 + 20)
            {
                this.rewardsOpened = !this.rewardsOpened;
                this.scrollBarAvailableRewards.reset();
            }

            if (!this.hoveredAvailableReward.isEmpty())
            {
                this.selectedReward = this.hoveredAvailableReward;
                this.hoveredAvailableReward = "";
                this.rewardsOpened = false;
            }

            if (this.hoveredAction.equals("add_reward") && (!this.rewardInput.getText().isEmpty() && this.isNumeric(this.rewardInput.getText()) || this.selectedReward.equals("colonisation")))
            {
                if (this.isNumeric(this.rewardInput.getText()))
                {
                    conditionsString1 = Integer.parseInt(this.rewardInput.getText());

                    if (this.selectedReward.equalsIgnoreCase("claims") && conditionsString1 > 150)
                    {
                        return;
                    }

                    if (this.selectedReward.equalsIgnoreCase("power") && conditionsString1 > 300)
                    {
                        return;
                    }
                }

                this.rewards.put(this.selectedReward, Integer.valueOf(!this.selectedReward.equals("colonisation") ? Integer.parseInt(this.rewardInput.getText()) : 0));
                this.rewardInput.setText("0");
                this.selectedReward = (String)this.availableRewards.get(0);
            }

            if (!this.hoveredReward.isEmpty())
            {
                this.rewards.remove(this.hoveredReward);
                this.hoveredReward = "";
                this.scrollBarRewards.reset();
            }

            if ((this.hoveredAction.equals("send_conditions_att") || this.hoveredAction.equals("send_conditions_def")) && !this.conditions.isEmpty())
            {
                String conditionsString2 = "";
                String rewardsString1 = "";
                Iterator it;
                Entry pair;

                for (it = this.conditions.entrySet().iterator(); it.hasNext(); conditionsString2 = conditionsString2 + pair.getKey() + "#" + pair.getValue() + ",")
                {
                    pair = (Entry)it.next();
                }

                for (it = this.rewards.entrySet().iterator(); it.hasNext(); rewardsString1 = rewardsString1 + pair.getKey() + "#" + pair.getValue() + ",")
                {
                    pair = (Entry)it.next();
                }

                conditionsString2 = conditionsString2.replaceAll(",$", "");
                rewardsString1 = rewardsString1.replaceAll(",$", "");
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionEnemyRequestUpdateConditionsPacket(Integer.valueOf(warRequestId), this.hoveredAction.replace("send_conditions_", ""), this.selectedConditionsType, conditionsString2, rewardsString1)));
                this.mc.displayGuiScreen(this.guiFrom);
            }
        }

        this.linkForumInput.mouseClicked(mouseX, mouseY, mouseButton);
        this.conditionInput.mouseClicked(mouseX, mouseY, mouseButton);
        this.rewardInput.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char typedChar, int keyCode)
    {
        this.linkForumInput.textboxKeyTyped(typedChar, keyCode);

        if (this.conditionInput.textboxKeyTyped(typedChar, keyCode))
        {
            this.conditionInput.setText(this.conditionInput.getText().replaceAll("^0", ""));
        }

        if (this.rewardInput.textboxKeyTyped(typedChar, keyCode))
        {
            this.rewardInput.setText(this.rewardInput.getText().replaceAll("^0", ""));
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

    public void drawScaledString(String text, int x, int y, int color, float scale, boolean centered, boolean shadow)
    {
        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, scale);
        float newX = (float)x;

        if (centered)
        {
            newX = (float)x - (float)this.fontRenderer.getStringWidth(text) * scale / 2.0F;
        }

        if (shadow)
        {
            this.fontRenderer.drawString(text, (int)(newX / scale), (int)((float)(y + 1) / scale), (color & 16579836) >> 2 | color & -16777216, false);
        }

        this.fontRenderer.drawString(text, (int)(newX / scale), (int)((float)y / scale), color, false);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    protected void drawHoveringText(List par1List, int par2, int par3, FontRenderer font)
    {
        if (!par1List.isEmpty())
        {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            int k = 0;
            Iterator iterator = par1List.iterator();
            int j1;

            while (iterator.hasNext())
            {
                String i1 = (String)iterator.next();
                j1 = font.getStringWidth(i1);

                if (j1 > k)
                {
                    k = j1;
                }
            }

            int var15 = par2 + 12;
            j1 = par3 - 12;
            int k1 = 8;

            if (par1List.size() > 1)
            {
                k1 += 2 + (par1List.size() - 1) * 10;
            }

            if (var15 + k > this.width)
            {
                var15 -= 28 + k;
            }

            if (j1 + k1 + 6 > this.height)
            {
                j1 = this.height - k1 - 6;
            }

            this.zLevel = 300.0F;
            this.itemRenderer.zLevel = 300.0F;
            int l1 = -267386864;
            this.drawGradientRect(var15 - 3, j1 - 4, var15 + k + 3, j1 - 3, l1, l1);
            this.drawGradientRect(var15 - 3, j1 + k1 + 3, var15 + k + 3, j1 + k1 + 4, l1, l1);
            this.drawGradientRect(var15 - 3, j1 - 3, var15 + k + 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(var15 - 4, j1 - 3, var15 - 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(var15 + k + 3, j1 - 3, var15 + k + 4, j1 + k1 + 3, l1, l1);
            int i2 = 1347420415;
            int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
            this.drawGradientRect(var15 - 3, j1 - 3 + 1, var15 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
            this.drawGradientRect(var15 + k + 2, j1 - 3 + 1, var15 + k + 3, j1 + k1 + 3 - 1, i2, j2);
            this.drawGradientRect(var15 - 3, j1 - 3, var15 + k + 3, j1 - 3 + 1, i2, i2);
            this.drawGradientRect(var15 - 3, j1 + k1 + 2, var15 + k + 3, j1 + k1 + 3, j2, j2);

            for (int k2 = 0; k2 < par1List.size(); ++k2)
            {
                String s1 = (String)par1List.get(k2);
                font.drawStringWithShadow(s1, var15, j1, -1);

                if (k2 == 0)
                {
                    j1 += 2;
                }

                j1 += 10;
            }

            this.zLevel = 0.0F;
            this.itemRenderer.zLevel = 0.0F;
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    public String bindTextureDependingOnStatus()
    {
        String textureToBind = "faction_war_request_1";

        if (loaded && warInfos.containsKey("status"))
        {
            String var2 = (String)warInfos.get("status");
            byte var3 = -1;

            switch (var2.hashCode())
            {
                case -1552874069:
                    if (var2.equals("waiting_validation"))
                    {
                        var3 = 0;
                    }

                    break;

                case -848652084:
                    if (var2.equals("waiting_conditions_att"))
                    {
                        var3 = 4;
                    }

                    break;

                case -848649680:
                    if (var2.equals("waiting_conditions_def"))
                    {
                        var3 = 5;
                    }

                    break;

                case -753541113:
                    if (var2.equals("in_progress"))
                    {
                        var3 = 6;
                    }

                    break;

                case -673660814:
                    if (var2.equals("finished"))
                    {
                        var3 = 7;
                    }

                    break;

                case 215313223:
                    if (var2.equals("waiting_conditions_att_second"))
                    {
                        var3 = 1;
                    }

                    break;

                case 476588369:
                    if (var2.equals("cancelled"))
                    {
                        var3 = 3;
                    }

                    break;

                case 1085547216:
                    if (var2.equals("refused"))
                    {
                        var3 = 2;
                    }
            }

            switch (var3)
            {
                case 0:
                case 1:
                case 2:
                case 3:
                    textureToBind = "faction_war_request_1";
                    break;

                case 4:
                    if (warInfos.get("factionATT").equals(warInfos.get("playerFaction")) && ((Boolean)warInfos.get("hasWarPermInOwnCountry")).booleanValue())
                    {
                        textureToBind = "faction_war_request_2";
                    }
                    else
                    {
                        textureToBind = "faction_war_request_1";
                    }

                    break;

                case 5:
                    if (warInfos.get("factionDEF").equals(warInfos.get("playerFaction")) && ((Boolean)warInfos.get("hasWarPermInOwnCountry")).booleanValue())
                    {
                        textureToBind = "faction_war_request_2";
                    }
                    else
                    {
                        textureToBind = "faction_war_request_1";
                    }

                    break;

                case 6:
                    textureToBind = "faction_war_request_3";
                    break;

                case 7:
                    textureToBind = "faction_war_request_3";
            }
        }

        return textureToBind;
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
