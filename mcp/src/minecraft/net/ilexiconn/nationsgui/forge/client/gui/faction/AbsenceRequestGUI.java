package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PlayerAbsenceRequestUpdateStatusPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class AbsenceRequestGUI extends GuiScreen
{
    public static HashMap<String, String> absenceInfos = new HashMap();
    public static boolean loaded = false;
    public static String playerName;
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
    public List<String> availableConditions = Arrays.asList(new String[] {"exams", "vacation", "other"});
    public String selectedReward = "";
    public ArrayList<String> availableRewards = new ArrayList(Arrays.asList(new String[] {"dollars", "power", "claims", "peace"}));
    public HashMap<String, Integer> data_ATT = null;
    public HashMap<String, Integer> data_DEF = null;
    public String winner = null;
    protected int xSize = 319;
    protected int ySize = 248;
    private int guiLeft;
    private int guiTop;
    private RenderItem itemRenderer = new RenderItem();
    private GuiTextField linkForumInput;
    private GuiTextField conditionInput;
    private GuiTextField rewardInput;
    private GuiScrollBarFaction scrollBarAvailableConditions;
    private GuiScrollBarFaction scrollBarAvailableRewards;
    private GuiScrollBarFaction scrollBarConditions;
    private GuiScrollBarFaction scrollBarRewards;
    private GuiScrollBarFaction scrollBarFinishConditions;
    private GuiScrollBarFaction scrollBarFinishRewards;
    private GuiScreen guiFrom;

    public AbsenceRequestGUI(String playerName, HashMap<String, String> absenceInfos, GuiScreen guiFrom)
    {
        playerName = playerName;
        absenceInfos = absenceInfos;
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
        String absenceStartTime = (String)absenceInfos.get("startTime");
        String absenceEndTime = (String)absenceInfos.get("endTime");
        String tooltipToDraw = "";

        if (absenceInfos.size() > 0)
        {
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
            this.drawScaledString(I18n.getString("faction.absence.title"), this.guiLeft + 14, this.guiTop + 210, 16777215, 1.5F, false, false);
            GL11.glPopMatrix();
            ClientProxy.loadCountryFlag((String)absenceInfos.get("name"));

            if (ClientProxy.flagsTexture.containsKey(absenceInfos.get("name")))
            {
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((DynamicTexture)ClientProxy.flagsTexture.get(absenceInfos.get("name"))).getGlTextureId());
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 57), (float)(this.guiTop + 27), 0.0F, 0.0F, 156, 78, 27, 15, 156.0F, 78.0F, false);
            }

            if (((String)absenceInfos.get("name")).contains("Empire"))
            {
                this.drawScaledString("Empire", this.guiLeft + 90, this.guiTop + 27, 16777215, 1.0F, false, false);
                this.drawScaledString(((String)absenceInfos.get("name")).replace("Empire", ""), this.guiLeft + 90, this.guiTop + 37, 16777215, 1.0F, false, false);
            }
            else
            {
                this.drawScaledString((String)absenceInfos.get("name"), this.guiLeft + 90, this.guiTop + 32, 16777215, 1.0F, false, false);
            }

            if (!ClientProxy.cacheHeadPlayer.containsKey(absenceInfos.get("playerName")))
            {
                try
                {
                    ResourceLocation startDate = AbstractClientPlayer.locationStevePng;
                    startDate = AbstractClientPlayer.getLocationSkin((String)absenceInfos.get("playerName"));
                    AbstractClientPlayer.getDownloadImageSkin(startDate, (String)absenceInfos.get("playerName"));
                    ClientProxy.cacheHeadPlayer.put(absenceInfos.get("playerName"), startDate);
                }
                catch (Exception var22)
                {
                    System.out.println(var22.getMessage());
                }
            }
            else
            {
                Minecraft.getMinecraft().renderEngine.bindTexture((ResourceLocation)ClientProxy.cacheHeadPlayer.get(absenceInfos.get("playerName")));
                this.mc.getTextureManager().bindTexture((ResourceLocation)ClientProxy.cacheHeadPlayer.get(absenceInfos.get("playerName")));
                GUIUtils.drawScaledCustomSizeModalRect(this.guiLeft + 205 + 13, this.guiTop + 37 + 10, 8.0F, 16.0F, 8, -8, -27, -25, 64.0F, 64.0F);
            }

            this.drawScaledString((String)absenceInfos.get("playerName"), this.guiLeft + 225, this.guiTop + 32, 16777215, 1.0F, false, false);
            this.drawScaledString(((String)absenceInfos.get("playerRole")).toLowerCase(), this.guiLeft + 245, this.guiTop + 58, 16777215, 1.0F, true, false);
            Date var23 = new Date(Double.valueOf(absenceStartTime).longValue());
            SimpleDateFormat startDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date endDate = new Date(Double.valueOf(absenceEndTime).longValue());
            SimpleDateFormat endDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            this.drawScaledString("\u00a7b" + startDateFormat.format(var23), this.guiLeft + 110 - this.fontRenderer.getStringWidth(startDateFormat.format(var23)), this.guiTop + 125, 16777215, 1.0F, false, false);
            this.drawScaledString("\u00a7b" + endDateFormat.format(endDate), this.guiLeft + 245 - this.fontRenderer.getStringWidth(endDateFormat.format(endDate)) + this.fontRenderer.getStringWidth(startDateFormat.format(var23)), this.guiTop + 125, 16777215, 1.0F, false, false);
            String status = I18n.getString("faction.absence.status." + (String)absenceInfos.get("status"));
            this.drawScaledString(status, this.guiLeft + 108 + 6, this.guiTop + 58, 16777215, 1.0F, true, false);
            ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);

            if (status.startsWith("\u00a72"))
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 108 - this.fontRenderer.getStringWidth(status) / 2 - 8), (float)(this.guiTop + 56), 110, 251, 10, 11, 512.0F, 512.0F, false);
            }
            else if (status.startsWith("\u00a74"))
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 108 - this.fontRenderer.getStringWidth(status) / 2 - 8), (float)(this.guiTop + 56), 120, 251, 10, 11, 512.0F, 512.0F, false);
            }
            else
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 108 - this.fontRenderer.getStringWidth(status) / 2 - 8), (float)(this.guiTop + 56), 130, 251, 10, 11, 512.0F, 512.0F, false);
            }

            this.drawScaledString(I18n.getString("faction.absence.reason"), this.guiLeft + 46, this.guiTop + 78, 0, 1.0F, false, false);
            this.drawScaledString(I18n.getString("faction.absence.reason." + (String)absenceInfos.get("reason")), this.guiLeft + 50, this.guiTop + 94, 16777215, 1.0F, false, false);
            this.drawScaledString(I18n.getString("faction.enemy.creationDate"), this.guiLeft + 182, this.guiTop + 78, 0, 1.0F, false, false);
            Date date = new Date(Double.valueOf((String)absenceInfos.get("creationTime")).longValue());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyy HH:mm");
            this.drawScaledString(simpleDateFormat.format(date), this.guiLeft + 186, this.guiTop + 94, 16777215, 1.0F, false, false);
            String[] descriptionWords = null;
            descriptionWords = I18n.getString("faction.absence.status.details." + (String)absenceInfos.get("status")).split(" ");
            String line = "";
            int lineNumber = 0;
            String[] var18 = descriptionWords;
            int var19 = descriptionWords.length;

            for (int var20 = 0; var20 < var19; ++var20)
            {
                String descWord = var18[var20];

                if ((double)this.fontRenderer.getStringWidth(line + descWord) * 0.9D <= 190.0D)
                {
                    if (!line.equals(""))
                    {
                        line = line + " ";
                    }

                    line = line + descWord;
                }
                else
                {
                    this.drawScaledString(line, this.guiLeft + 50, this.guiTop + 150 + lineNumber * 10, 16777215, 0.9F, false, false);
                    ++lineNumber;
                    line = descWord;
                }
            }

            this.drawScaledString(line, this.guiLeft + 50, this.guiTop + 150 + lineNumber * 10, 16777215, 0.9F, false, false);

            if (((String)absenceInfos.get("status")).equals("waiting_validation"))
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
            else if (((String)absenceInfos.get("status")).equals("accepted"))
            {
                ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 46), (float)(this.guiTop + 219), 0, 432, 127, 15, 512.0F, 512.0F, false);

                if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 127 && mouseY >= this.guiTop + 219 && mouseY <= this.guiTop + 219 + 15)
                {
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 46), (float)(this.guiTop + 219), 0, 447, 127, 15, 512.0F, 512.0F, false);
                    this.hoveredAction = "staff_cancel";
                }

                this.drawScaledString(I18n.getString("faction.enemy.cancel_request"), this.guiLeft + 110, this.guiTop + 223, 16777215, 1.0F, true, false);
            }

            if (((String)absenceInfos.get("status")).equals("in_progress") || ((String)absenceInfos.get("status")).equals("validated"))
            {
                ClientEventHandler.STYLE.bindTexture(MAIN_TEXTURE);
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 46), (float)(this.guiTop + 219), 0, 432, 127, 15, 512.0F, 512.0F, false);

                if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 127 && mouseY >= this.guiTop + 219 && mouseY <= this.guiTop + 219 + 15)
                {
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 46), (float)(this.guiTop + 219), 0, 447, 127, 15, 512.0F, 512.0F, false);
                    this.hoveredAction = "cancel";
                }

                this.drawScaledString(I18n.getString("faction.enemy.cancel_request"), this.guiLeft + 110, this.guiTop + 223, 16777215, 1.0F, true, false);
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
        return ((String)absenceInfos.get("conditions")).split(",").length > 3 ? (float)(-(((String)absenceInfos.get("conditions")).split(",").length - 3) * 20) * this.scrollBarFinishConditions.getSliderValue() : 0.0F;
    }

    private float getSlideFinishRewards()
    {
        return ((String)absenceInfos.get("rewards")).split(",").length > 3 ? (float)(-(((String)absenceInfos.get("rewards")).split(",").length - 3) * 20) * this.scrollBarFinishRewards.getSliderValue() : 0.0F;
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

            if (!this.hoveredAction.isEmpty())
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);

                if (this.hoveredAction.equals("cancel"))
                {
                    PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new PlayerAbsenceRequestUpdateStatusPacket((String)absenceInfos.get("playerName"), "cancelled")));
                    this.mc.displayGuiScreen((GuiScreen)null);
                }
                else if (this.hoveredAction.equals("staff_refuse"))
                {
                    PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new PlayerAbsenceRequestUpdateStatusPacket((String)absenceInfos.get("playerName"), "refused")));
                    this.mc.displayGuiScreen(this.guiFrom);
                }
                else if (!this.hoveredAction.equals("staff_accept") && !this.hoveredAction.equals("accept"))
                {
                    if (this.hoveredAction.equals("staff_cancel"))
                    {
                        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new PlayerAbsenceRequestUpdateStatusPacket((String)absenceInfos.get("playerName"), "cancelled")));
                        this.mc.displayGuiScreen(this.guiFrom);
                    }
                }
                else
                {
                    PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new PlayerAbsenceRequestUpdateStatusPacket((String)absenceInfos.get("playerName"), "accepted")));
                    this.mc.displayGuiScreen(this.guiFrom);
                }
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
        return "faction_war_request_1";
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
