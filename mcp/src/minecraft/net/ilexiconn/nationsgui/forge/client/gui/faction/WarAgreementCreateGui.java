package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Arrays;
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
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionEnemyAgreementCreatePacket;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class WarAgreementCreateGui extends GuiScreen
{
    protected int xSize = 226;
    protected int ySize = 248;
    private int guiLeft;
    private int guiTop;
    private RenderItem itemRenderer = new RenderItem();
    private GuiScrollBarFaction scrollBarConditions;
    boolean agreementTypeExpanded = false;
    boolean sidesExpanded = false;
    boolean conditionTypeExpanded = false;
    private String selectedAgreementType = "";
    private String hoveredAgreementType = "";
    private String selectedSide = "";
    private String hoveredSide = "";
    private String selectedConditionType = "";
    private String hoveredConditionType = "";
    private String hoveredCondition = "";
    private String hoveredAction = "";
    private GuiTextField durationInput;
    private GuiTextField conditionValueInput;
    private EntityPlayer player;
    public int warId;
    private String faction1;
    private String faction2;
    private final List<String> availableAgreementTypes = Arrays.asList(new String[] {"peace", "no_missile", "no_assault"});
    private final List<String> availableConditionTypes = Arrays.asList(new String[] {"dollars", "power", "claims"});
    private HashMap<String, String> conditions = new HashMap();

    public WarAgreementCreateGui(EntityPlayer player, int warId, String faction1, String faction2)
    {
        this.player = player;
        this.warId = warId;
        this.faction1 = faction1;
        this.faction2 = faction2;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.scrollBarConditions = new GuiScrollBarFaction((float)(this.guiLeft + 211), (float)(this.guiTop + 77), 59);
        this.selectedAgreementType = (String)this.availableAgreementTypes.get(0);
        this.selectedConditionType = (String)this.availableConditionTypes.get(0);
        this.selectedSide = this.faction1;
        this.durationInput = new GuiTextField(this.fontRenderer, this.guiLeft + 123, this.guiTop + 48, 90, 10);
        this.durationInput.setEnableBackgroundDrawing(false);
        this.durationInput.setMaxStringLength(3);
        this.durationInput.setText("0");
        this.conditionValueInput = new GuiTextField(this.fontRenderer, this.guiLeft + 123, this.guiTop + 205, 68, 10);
        this.conditionValueInput.setEnableBackgroundDrawing(false);
        this.conditionValueInput.setMaxStringLength(7);
        this.conditionValueInput.setText("0");
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.durationInput.updateCursorCounter();
        this.conditionValueInput.updateCursorCounter();
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        this.hoveredAgreementType = "";
        this.hoveredSide = "";
        this.hoveredConditionType = "";
        this.hoveredCondition = "";
        this.hoveredAction = "";
        this.drawDefaultBackground();
        List tooltipToDraw = null;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        ClientEventHandler.STYLE.bindTexture("faction_war_agreement_create");
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.guiLeft + 12), (float)(this.guiTop + 180), 0.0F);
        GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef((float)(-(this.guiLeft + 12)), (float)(-(this.guiTop + 180)), 0.0F);
        this.drawScaledString(I18n.getString("faction.enemy.agreement.create.title"), this.guiLeft + 12, this.guiTop + 180, 16777215, 1.5F, false, false);
        GL11.glPopMatrix();
        ClientEventHandler.STYLE.bindTexture("faction_war_agreement_create");

        if (mouseX >= this.guiLeft + 214 && mouseX <= this.guiLeft + 214 + 9 && mouseY >= this.guiTop - 8 && mouseY <= this.guiTop - 8 + 10)
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 214), (float)(this.guiTop - 8), 46, 248, 9, 10, 512.0F, 512.0F, false);
        }
        else
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 214), (float)(this.guiTop - 8), 46, 258, 9, 10, 512.0F, 512.0F, false);
        }

        if (!this.selectedAgreementType.equals(""))
        {
            this.drawScaledString(I18n.getString("faction.enemy.agreement.type." + this.selectedAgreementType), this.guiLeft + 50, this.guiTop + 23, 16777215, 1.0F, false, false);
        }

        this.drawScaledString(I18n.getString("faction.enemy.agreement.duration"), this.guiLeft + 65, this.guiTop + 48, 16777215, 1.0F, false, false);
        this.durationInput.drawTextBox();
        this.drawScaledString(I18n.getString("faction.enemy.agreement.conditions"), this.guiLeft + 46, this.guiTop + 64, 0, 1.0F, false, false);
        ClientEventHandler.STYLE.bindTexture("faction_war_agreement_create");
        int infoX = this.guiLeft + 46 + this.fontRenderer.getStringWidth(I18n.getString("faction.enemy.agreement.conditions")) + 3;
        ModernGui.drawModalRectWithCustomSizedTexture((float)infoX, (float)(this.guiTop + 62), 71, 251, 10, 11, 512.0F, 512.0F, false);

        if (!this.agreementTypeExpanded && mouseX >= infoX && mouseX <= infoX + 10 && mouseY >= this.guiTop + 62 && mouseY <= this.guiTop + 62 + 11)
        {
            tooltipToDraw = Arrays.asList(I18n.getString("faction.enemy.agreement.conditions.tooltip").split("##"));
        }

        GUIUtils.startGLScissor(this.guiLeft + 47, this.guiTop + 75, 164, 63);
        int yOffset = 0;

        for (Iterator it = this.conditions.entrySet().iterator(); it.hasNext(); yOffset += 21)
        {
            Entry index = (Entry)it.next();
            String country = ((String)index.getValue()).split("#")[1];
            int agreementType = this.guiLeft + 47;
            Float offsetY = Float.valueOf((float)(this.guiTop + 75 + yOffset) + this.getSlideConditions());
            ClientEventHandler.STYLE.bindTexture("faction_war_agreement_create");
            ModernGui.drawModalRectWithCustomSizedTexture((float)agreementType, offsetY.floatValue(), 47, 75, 164, 21, 512.0F, 512.0F, false);
            ClientProxy.loadCountryFlag(country);

            if (ClientProxy.flagsTexture.containsKey(country))
            {
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((DynamicTexture)ClientProxy.flagsTexture.get(country)).getGlTextureId());
                ModernGui.drawScaledCustomSizeModalRect((float)(agreementType + 4), (float)(offsetY.intValue() + 4), 0.0F, 0.0F, 156, 78, 18, 10, 156.0F, 78.0F, false);
            }

            this.drawScaledString(((String)index.getValue()).split("#")[0] + " " + I18n.getString("faction.enemy.agreement.conditions." + index.getKey()), agreementType + 25, offsetY.intValue() + 6, 16777215, 1.0F, false, false);
            ClientEventHandler.STYLE.bindTexture("faction_war_agreement_create");

            if (!this.agreementTypeExpanded && mouseX >= agreementType + 141 && mouseX <= agreementType + 141 + 20 && (float)mouseY >= offsetY.floatValue() + 2.0F && (float)mouseY <= offsetY.floatValue() + 2.0F + 16.0F)
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(agreementType + 141), offsetY.floatValue() + 2.0F, 298, 323, 20, 16, 512.0F, 512.0F, false);
                this.hoveredCondition = (String)index.getKey();
            }
            else
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(agreementType + 141), offsetY.floatValue() + 2.0F, 298, 307, 20, 16, 512.0F, 512.0F, false);
            }
        }

        GUIUtils.endGLScissor();
        this.scrollBarConditions.draw(mouseX, mouseY);
        this.drawScaledString(I18n.getString("faction.enemy.agreement.new_condition"), this.guiLeft + 46, this.guiTop + 144, 0, 1.0F, false, false);

        if (!this.selectedSide.isEmpty())
        {
            ClientProxy.loadCountryFlag(this.selectedSide);

            if (ClientProxy.flagsTexture.containsKey(this.selectedSide))
            {
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((DynamicTexture)ClientProxy.flagsTexture.get(this.selectedSide)).getGlTextureId());
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 51), (float)(this.guiTop + 159), 0.0F, 0.0F, 156, 78, 18, 10, 156.0F, 78.0F, false);
                this.drawScaledString(this.selectedSide + " " + I18n.getString("faction.enemy.agreement.gives"), this.guiLeft + 73, this.guiTop + 161, 16777215, 1.0F, false, false);
            }
        }

        if (!this.selectedConditionType.isEmpty())
        {
            this.drawScaledString(I18n.getString("faction.enemy.agreement.conditions." + this.selectedConditionType), this.guiLeft + 51, this.guiTop + 182, 16777215, 1.0F, false, false);
        }

        this.drawScaledString(I18n.getString("faction.enemy.agreement.value"), this.guiLeft + 65, this.guiTop + 205, 16777215, 1.0F, false, false);
        this.conditionValueInput.drawTextBox();

        if (!this.conditionTypeExpanded && mouseX >= this.guiLeft + 196 && mouseX <= this.guiLeft + 196 + 20 && mouseY >= this.guiTop + 198 && mouseY <= this.guiTop + 198 + 20)
        {
            ClientEventHandler.STYLE.bindTexture("faction_war_agreement_create");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 196), (float)(this.guiTop + 198), 277, 319, 20, 20, 512.0F, 512.0F, false);
            this.hoveredAction = "add_condition";
        }

        if (!this.conditionTypeExpanded && mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 170 && mouseY >= this.guiTop + 220 && mouseY <= this.guiTop + 220 + 15)
        {
            ClientEventHandler.STYLE.bindTexture("faction_war_agreement_create");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 46), (float)(this.guiTop + 220), 0, 442, 170, 15, 512.0F, 512.0F, false);
            this.hoveredAction = "submit";
        }

        this.drawScaledString(I18n.getString("faction.enemy.agreement.submit"), this.guiLeft + 131, this.guiTop + 224, 16777215, 1.0F, true, false);

        if (this.sidesExpanded)
        {
            ClientEventHandler.STYLE.bindTexture("faction_war_agreement_create");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 46), (float)(this.guiTop + 174), 0, 312, 170, 19, 512.0F, 512.0F, false);
            ClientProxy.loadCountryFlag(this.faction1);

            if (ClientProxy.flagsTexture.containsKey(this.faction1))
            {
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((DynamicTexture)ClientProxy.flagsTexture.get(this.faction1)).getGlTextureId());
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 51), (float)(this.guiTop + 178), 0.0F, 0.0F, 156, 78, 18, 10, 156.0F, 78.0F, false);
                this.drawScaledString(this.faction1 + " " + I18n.getString("faction.enemy.agreement.gives"), this.guiLeft + 73, this.guiTop + 180, 16777215, 1.0F, false, false);

                if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 170 && mouseY >= this.guiTop + 174 && mouseY <= this.guiTop + 174 + 19)
                {
                    this.hoveredSide = this.faction1;
                }
            }

            ClientEventHandler.STYLE.bindTexture("faction_war_agreement_create");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 46), (float)(this.guiTop + 193), 0, 312, 170, 19, 512.0F, 512.0F, false);
            ClientProxy.loadCountryFlag(this.faction2);

            if (ClientProxy.flagsTexture.containsKey(this.faction2))
            {
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((DynamicTexture)ClientProxy.flagsTexture.get(this.faction2)).getGlTextureId());
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 51), (float)(this.guiTop + 197), 0.0F, 0.0F, 156, 78, 18, 10, 156.0F, 78.0F, false);
                this.drawScaledString(this.faction2 + " " + I18n.getString("faction.enemy.agreement.gives"), this.guiLeft + 73, this.guiTop + 199, 16777215, 1.0F, false, false);

                if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 170 && mouseY >= this.guiTop + 193 && mouseY <= this.guiTop + 193 + 19)
                {
                    this.hoveredSide = this.faction2;
                }
            }
        }

        int var12;
        Iterator var13;
        String var14;

        if (this.conditionTypeExpanded)
        {
            var12 = 0;

            for (var13 = this.availableConditionTypes.iterator(); var13.hasNext(); ++var12)
            {
                var14 = (String)var13.next();
                ClientEventHandler.STYLE.bindTexture("faction_war_agreement_create");
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 46), (float)(this.guiTop + 196 + var12 * 19), 0, 312, 170, 19, 512.0F, 512.0F, false);
                this.drawScaledString(I18n.getString("faction.enemy.agreement.conditions." + var14), this.guiLeft + 49, this.guiTop + 201 + var12 * 19, 16777215, 1.0F, false, false);

                if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 170 && mouseY >= this.guiTop + 196 + var12 * 19 && mouseY <= this.guiTop + 196 + var12 * 19 + 19)
                {
                    this.hoveredConditionType = var14;
                }
            }
        }

        if (this.agreementTypeExpanded)
        {
            var12 = 0;

            for (var13 = this.availableAgreementTypes.iterator(); var13.hasNext(); ++var12)
            {
                var14 = (String)var13.next();
                ClientEventHandler.STYLE.bindTexture("faction_war_agreement_create");
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 46), (float)(this.guiTop + 37 + var12 * 19), 0, 312, 170, 19, 512.0F, 512.0F, false);
                this.drawScaledString(I18n.getString("faction.enemy.agreement.type." + var14), this.guiLeft + 50, this.guiTop + 42 + var12 * 19, 16777215, 1.0F, false, false);
                ClientEventHandler.STYLE.bindTexture("faction_war_agreement_create");
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 46 + this.fontRenderer.getStringWidth(I18n.getString("faction.enemy.agreement.type." + var14)) + 6), (float)(this.guiTop + 40 + var12 * 19), 71, 251, 10, 11, 512.0F, 512.0F, false);

                if (mouseX >= this.guiLeft + 46 + this.fontRenderer.getStringWidth(I18n.getString("faction.enemy.agreement.type." + var14)) + 6 && mouseX <= this.guiLeft + 46 + this.fontRenderer.getStringWidth(I18n.getString("faction.enemy.agreement.type." + var14)) + 6 + 10 && mouseY >= this.guiTop + 40 + var12 * 19 && mouseY <= this.guiTop + 40 + var12 * 19 + 11)
                {
                    tooltipToDraw = Arrays.asList(I18n.getString("faction.enemy.agreement.type.desc." + var14).split("##"));
                }

                if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 170 && mouseY >= this.guiTop + 37 + var12 * 19 && mouseY <= this.guiTop + 37 + var12 * 19 + 19)
                {
                    this.hoveredAgreementType = var14;
                }
            }
        }

        if (tooltipToDraw != null && !tooltipToDraw.isEmpty())
        {
            this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.fontRenderer);
        }

        GL11.glEnable(GL11.GL_LIGHTING);
        RenderHelper.enableStandardItemLighting();
    }

    private float getSlideConditions()
    {
        return this.conditions.size() > 3 ? (float)(-(this.conditions.size() - 3) * 21) * this.scrollBarConditions.getSliderValue() : 0.0F;
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char typedChar, int keyCode)
    {
        if (this.durationInput.textboxKeyTyped(typedChar, keyCode))
        {
            this.durationInput.setText(this.durationInput.getText().replaceAll("^0", ""));
        }

        if (this.conditionValueInput.textboxKeyTyped(typedChar, keyCode))
        {
            this.conditionValueInput.setText(this.conditionValueInput.getText().replaceAll("^0", ""));
        }

        super.keyTyped(typedChar, keyCode);
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 170 && mouseY >= this.guiTop + 17 && mouseY <= this.guiTop + 17 + 20)
            {
                this.agreementTypeExpanded = !this.agreementTypeExpanded;
            }

            if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 170 && mouseY >= this.guiTop + 154 && mouseY <= this.guiTop + 154 + 20)
            {
                this.sidesExpanded = !this.sidesExpanded;
                this.conditionTypeExpanded = false;
            }

            if (!this.sidesExpanded && mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 170 && mouseY >= this.guiTop + 176 && mouseY <= this.guiTop + 176 + 20)
            {
                this.conditionTypeExpanded = !this.conditionTypeExpanded;
                this.sidesExpanded = false;
            }

            if (!this.hoveredSide.isEmpty())
            {
                this.selectedSide = this.hoveredSide;
                this.hoveredSide = "";
                this.sidesExpanded = false;
            }

            if (!this.hoveredAgreementType.isEmpty())
            {
                this.selectedAgreementType = this.hoveredAgreementType;
                this.hoveredAgreementType = "";
                this.agreementTypeExpanded = false;
            }

            if (!this.hoveredConditionType.isEmpty())
            {
                this.selectedConditionType = this.hoveredConditionType;
                this.hoveredConditionType = "";
                this.conditionTypeExpanded = false;
            }

            if (mouseX > this.guiLeft + 214 && mouseX < this.guiLeft + 214 + 9 && mouseY > this.guiTop - 8 && mouseY < this.guiTop - 8 + 10)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                this.mc.displayGuiScreen(new WarAgreementListGui(this.player, this.warId, this.faction1, this.faction2));
            }

            if (!this.sidesExpanded && !this.conditionTypeExpanded && this.hoveredAction.equals("add_condition") && !this.selectedSide.isEmpty() && !this.selectedConditionType.isEmpty() && this.isNumeric(this.conditionValueInput.getText()))
            {
                this.conditions.put(this.selectedConditionType, this.conditionValueInput.getText() + "#" + this.selectedSide);
                this.conditionValueInput.setText("0");
                this.selectedSide = this.faction1;
                this.selectedConditionType = (String)this.availableConditionTypes.get(0);
            }

            if (!this.agreementTypeExpanded && !this.hoveredCondition.isEmpty())
            {
                this.conditions.remove(this.hoveredCondition);
            }

            if (!this.sidesExpanded && !this.conditionTypeExpanded && this.hoveredAction.equals("submit") && !this.selectedAgreementType.isEmpty() && this.isNumeric(this.durationInput.getText()))
            {
                String conditionsString = "";
                Entry pair;

                for (Iterator it = this.conditions.entrySet().iterator(); it.hasNext(); conditionsString = conditionsString + pair.getKey() + "#" + pair.getValue() + ",")
                {
                    pair = (Entry)it.next();
                }

                conditionsString = conditionsString.replaceAll(",$", "");
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionEnemyAgreementCreatePacket(Integer.valueOf(this.warId), this.selectedAgreementType, Integer.parseInt(this.durationInput.getText()), conditionsString)));
                this.mc.displayGuiScreen(new WarAgreementListGui(this.player, this.warId, this.faction1, this.faction2));
            }

            this.durationInput.mouseClicked(mouseX, mouseY, mouseButton);
            this.conditionValueInput.mouseClicked(mouseX, mouseY, mouseButton);
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
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
