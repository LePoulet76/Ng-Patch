/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.faction.WarAgreementListGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionEnemyAgreementCreatePacket;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class WarAgreementCreateGui
extends GuiScreen {
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
    private final List<String> availableAgreementTypes = Arrays.asList("peace", "no_missile", "no_assault");
    private final List<String> availableConditionTypes = Arrays.asList("dollars", "power", "claims");
    private HashMap<String, String> conditions = new HashMap();

    public WarAgreementCreateGui(EntityPlayer player, int warId, String faction1, String faction2) {
        this.player = player;
        this.warId = warId;
        this.faction1 = faction1;
        this.faction2 = faction2;
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        this.scrollBarConditions = new GuiScrollBarFaction(this.guiLeft + 211, this.guiTop + 77, 59);
        this.selectedAgreementType = this.availableAgreementTypes.get(0);
        this.selectedConditionType = this.availableConditionTypes.get(0);
        this.selectedSide = this.faction1;
        this.durationInput = new GuiTextField(this.field_73886_k, this.guiLeft + 123, this.guiTop + 48, 90, 10);
        this.durationInput.func_73786_a(false);
        this.durationInput.func_73804_f(3);
        this.durationInput.func_73782_a("0");
        this.conditionValueInput = new GuiTextField(this.field_73886_k, this.guiLeft + 123, this.guiTop + 205, 68, 10);
        this.conditionValueInput.func_73786_a(false);
        this.conditionValueInput.func_73804_f(7);
        this.conditionValueInput.func_73782_a("0");
    }

    public void func_73876_c() {
        this.durationInput.func_73780_a();
        this.conditionValueInput.func_73780_a();
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        this.hoveredAgreementType = "";
        this.hoveredSide = "";
        this.hoveredConditionType = "";
        this.hoveredCondition = "";
        this.hoveredAction = "";
        this.func_73873_v_();
        List<String> tooltipToDraw = null;
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("faction_war_agreement_create");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.guiLeft + 12), (float)(this.guiTop + 180), (float)0.0f);
        GL11.glRotatef((float)-90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glTranslatef((float)(-(this.guiLeft + 12)), (float)(-(this.guiTop + 180)), (float)0.0f);
        this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.agreement.create.title"), this.guiLeft + 12, this.guiTop + 180, 0xFFFFFF, 1.5f, false, false);
        GL11.glPopMatrix();
        ClientEventHandler.STYLE.bindTexture("faction_war_agreement_create");
        if (mouseX >= this.guiLeft + 214 && mouseX <= this.guiLeft + 214 + 9 && mouseY >= this.guiTop - 8 && mouseY <= this.guiTop - 8 + 10) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 214, this.guiTop - 8, 46, 248, 9, 10, 512.0f, 512.0f, false);
        } else {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 214, this.guiTop - 8, 46, 258, 9, 10, 512.0f, 512.0f, false);
        }
        if (!this.selectedAgreementType.equals("")) {
            this.drawScaledString(I18n.func_135053_a((String)("faction.enemy.agreement.type." + this.selectedAgreementType)), this.guiLeft + 50, this.guiTop + 23, 0xFFFFFF, 1.0f, false, false);
        }
        this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.agreement.duration"), this.guiLeft + 65, this.guiTop + 48, 0xFFFFFF, 1.0f, false, false);
        this.durationInput.func_73795_f();
        this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.agreement.conditions"), this.guiLeft + 46, this.guiTop + 64, 0, 1.0f, false, false);
        ClientEventHandler.STYLE.bindTexture("faction_war_agreement_create");
        int infoX = this.guiLeft + 46 + this.field_73886_k.func_78256_a(I18n.func_135053_a((String)"faction.enemy.agreement.conditions")) + 3;
        ModernGui.drawModalRectWithCustomSizedTexture(infoX, this.guiTop + 62, 71, 251, 10, 11, 512.0f, 512.0f, false);
        if (!this.agreementTypeExpanded && mouseX >= infoX && mouseX <= infoX + 10 && mouseY >= this.guiTop + 62 && mouseY <= this.guiTop + 62 + 11) {
            tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"faction.enemy.agreement.conditions.tooltip").split("##"));
        }
        GUIUtils.startGLScissor(this.guiLeft + 47, this.guiTop + 75, 164, 63);
        int yOffset = 0;
        for (Map.Entry<String, String> pair : this.conditions.entrySet()) {
            String country = pair.getValue().split("#")[1];
            int offsetX = this.guiLeft + 47;
            Float offsetY = Float.valueOf((float)(this.guiTop + 75 + yOffset) + this.getSlideConditions());
            ClientEventHandler.STYLE.bindTexture("faction_war_agreement_create");
            ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.floatValue(), 47, 75, 164, 21, 512.0f, 512.0f, false);
            ClientProxy.loadCountryFlag(country);
            if (ClientProxy.flagsTexture.containsKey(country)) {
                GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get(country).func_110552_b());
                ModernGui.drawScaledCustomSizeModalRect(offsetX + 4, offsetY.intValue() + 4, 0.0f, 0.0f, 156, 78, 18, 10, 156.0f, 78.0f, false);
            }
            this.drawScaledString(pair.getValue().split("#")[0] + " " + I18n.func_135053_a((String)("faction.enemy.agreement.conditions." + pair.getKey())), offsetX + 25, offsetY.intValue() + 6, 0xFFFFFF, 1.0f, false, false);
            ClientEventHandler.STYLE.bindTexture("faction_war_agreement_create");
            if (!this.agreementTypeExpanded && mouseX >= offsetX + 141 && mouseX <= offsetX + 141 + 20 && (float)mouseY >= offsetY.floatValue() + 2.0f && (float)mouseY <= offsetY.floatValue() + 2.0f + 16.0f) {
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 141, offsetY.floatValue() + 2.0f, 298, 323, 20, 16, 512.0f, 512.0f, false);
                this.hoveredCondition = pair.getKey();
            } else {
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 141, offsetY.floatValue() + 2.0f, 298, 307, 20, 16, 512.0f, 512.0f, false);
            }
            yOffset += 21;
        }
        GUIUtils.endGLScissor();
        this.scrollBarConditions.draw(mouseX, mouseY);
        this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.agreement.new_condition"), this.guiLeft + 46, this.guiTop + 144, 0, 1.0f, false, false);
        if (!this.selectedSide.isEmpty()) {
            ClientProxy.loadCountryFlag(this.selectedSide);
            if (ClientProxy.flagsTexture.containsKey(this.selectedSide)) {
                GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get(this.selectedSide).func_110552_b());
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 51, this.guiTop + 159, 0.0f, 0.0f, 156, 78, 18, 10, 156.0f, 78.0f, false);
                this.drawScaledString(this.selectedSide + " " + I18n.func_135053_a((String)"faction.enemy.agreement.gives"), this.guiLeft + 73, this.guiTop + 161, 0xFFFFFF, 1.0f, false, false);
            }
        }
        if (!this.selectedConditionType.isEmpty()) {
            this.drawScaledString(I18n.func_135053_a((String)("faction.enemy.agreement.conditions." + this.selectedConditionType)), this.guiLeft + 51, this.guiTop + 182, 0xFFFFFF, 1.0f, false, false);
        }
        this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.agreement.value"), this.guiLeft + 65, this.guiTop + 205, 0xFFFFFF, 1.0f, false, false);
        this.conditionValueInput.func_73795_f();
        if (!this.conditionTypeExpanded && mouseX >= this.guiLeft + 196 && mouseX <= this.guiLeft + 196 + 20 && mouseY >= this.guiTop + 198 && mouseY <= this.guiTop + 198 + 20) {
            ClientEventHandler.STYLE.bindTexture("faction_war_agreement_create");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 196, this.guiTop + 198, 277, 319, 20, 20, 512.0f, 512.0f, false);
            this.hoveredAction = "add_condition";
        }
        if (!this.conditionTypeExpanded && mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 170 && mouseY >= this.guiTop + 220 && mouseY <= this.guiTop + 220 + 15) {
            ClientEventHandler.STYLE.bindTexture("faction_war_agreement_create");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 46, this.guiTop + 220, 0, 442, 170, 15, 512.0f, 512.0f, false);
            this.hoveredAction = "submit";
        }
        this.drawScaledString(I18n.func_135053_a((String)"faction.enemy.agreement.submit"), this.guiLeft + 131, this.guiTop + 224, 0xFFFFFF, 1.0f, true, false);
        if (this.sidesExpanded) {
            ClientEventHandler.STYLE.bindTexture("faction_war_agreement_create");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 46, this.guiTop + 174, 0, 312, 170, 19, 512.0f, 512.0f, false);
            ClientProxy.loadCountryFlag(this.faction1);
            if (ClientProxy.flagsTexture.containsKey(this.faction1)) {
                GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get(this.faction1).func_110552_b());
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 51, this.guiTop + 178, 0.0f, 0.0f, 156, 78, 18, 10, 156.0f, 78.0f, false);
                this.drawScaledString(this.faction1 + " " + I18n.func_135053_a((String)"faction.enemy.agreement.gives"), this.guiLeft + 73, this.guiTop + 180, 0xFFFFFF, 1.0f, false, false);
                if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 170 && mouseY >= this.guiTop + 174 && mouseY <= this.guiTop + 174 + 19) {
                    this.hoveredSide = this.faction1;
                }
            }
            ClientEventHandler.STYLE.bindTexture("faction_war_agreement_create");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 46, this.guiTop + 193, 0, 312, 170, 19, 512.0f, 512.0f, false);
            ClientProxy.loadCountryFlag(this.faction2);
            if (ClientProxy.flagsTexture.containsKey(this.faction2)) {
                GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get(this.faction2).func_110552_b());
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 51, this.guiTop + 197, 0.0f, 0.0f, 156, 78, 18, 10, 156.0f, 78.0f, false);
                this.drawScaledString(this.faction2 + " " + I18n.func_135053_a((String)"faction.enemy.agreement.gives"), this.guiLeft + 73, this.guiTop + 199, 0xFFFFFF, 1.0f, false, false);
                if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 170 && mouseY >= this.guiTop + 193 && mouseY <= this.guiTop + 193 + 19) {
                    this.hoveredSide = this.faction2;
                }
            }
        }
        if (this.conditionTypeExpanded) {
            int index = 0;
            for (String conditionType : this.availableConditionTypes) {
                ClientEventHandler.STYLE.bindTexture("faction_war_agreement_create");
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 46, this.guiTop + 196 + index * 19, 0, 312, 170, 19, 512.0f, 512.0f, false);
                this.drawScaledString(I18n.func_135053_a((String)("faction.enemy.agreement.conditions." + conditionType)), this.guiLeft + 49, this.guiTop + 201 + index * 19, 0xFFFFFF, 1.0f, false, false);
                if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 170 && mouseY >= this.guiTop + 196 + index * 19 && mouseY <= this.guiTop + 196 + index * 19 + 19) {
                    this.hoveredConditionType = conditionType;
                }
                ++index;
            }
        }
        if (this.agreementTypeExpanded) {
            int index = 0;
            for (String agreementType : this.availableAgreementTypes) {
                ClientEventHandler.STYLE.bindTexture("faction_war_agreement_create");
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 46, this.guiTop + 37 + index * 19, 0, 312, 170, 19, 512.0f, 512.0f, false);
                this.drawScaledString(I18n.func_135053_a((String)("faction.enemy.agreement.type." + agreementType)), this.guiLeft + 50, this.guiTop + 42 + index * 19, 0xFFFFFF, 1.0f, false, false);
                ClientEventHandler.STYLE.bindTexture("faction_war_agreement_create");
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 46 + this.field_73886_k.func_78256_a(I18n.func_135053_a((String)("faction.enemy.agreement.type." + agreementType))) + 6, this.guiTop + 40 + index * 19, 71, 251, 10, 11, 512.0f, 512.0f, false);
                if (mouseX >= this.guiLeft + 46 + this.field_73886_k.func_78256_a(I18n.func_135053_a((String)("faction.enemy.agreement.type." + agreementType))) + 6 && mouseX <= this.guiLeft + 46 + this.field_73886_k.func_78256_a(I18n.func_135053_a((String)("faction.enemy.agreement.type." + agreementType))) + 6 + 10 && mouseY >= this.guiTop + 40 + index * 19 && mouseY <= this.guiTop + 40 + index * 19 + 11) {
                    tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)("faction.enemy.agreement.type.desc." + agreementType)).split("##"));
                }
                if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 170 && mouseY >= this.guiTop + 37 + index * 19 && mouseY <= this.guiTop + 37 + index * 19 + 19) {
                    this.hoveredAgreementType = agreementType;
                }
                ++index;
            }
        }
        if (tooltipToDraw != null && !tooltipToDraw.isEmpty()) {
            this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
        }
        GL11.glEnable((int)2896);
        RenderHelper.func_74519_b();
    }

    private float getSlideConditions() {
        return this.conditions.size() > 3 ? (float)(-(this.conditions.size() - 3) * 21) * this.scrollBarConditions.getSliderValue() : 0.0f;
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        if (this.durationInput.func_73802_a(typedChar, keyCode)) {
            this.durationInput.func_73782_a(this.durationInput.func_73781_b().replaceAll("^0", ""));
        }
        if (this.conditionValueInput.func_73802_a(typedChar, keyCode)) {
            this.conditionValueInput.func_73782_a(this.conditionValueInput.func_73781_b().replaceAll("^0", ""));
        }
        super.func_73869_a(typedChar, keyCode);
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 170 && mouseY >= this.guiTop + 17 && mouseY <= this.guiTop + 17 + 20) {
                boolean bl = this.agreementTypeExpanded = !this.agreementTypeExpanded;
            }
            if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 170 && mouseY >= this.guiTop + 154 && mouseY <= this.guiTop + 154 + 20) {
                this.sidesExpanded = !this.sidesExpanded;
                this.conditionTypeExpanded = false;
            }
            if (!this.sidesExpanded && mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 170 && mouseY >= this.guiTop + 176 && mouseY <= this.guiTop + 176 + 20) {
                this.conditionTypeExpanded = !this.conditionTypeExpanded;
                this.sidesExpanded = false;
            }
            if (!this.hoveredSide.isEmpty()) {
                this.selectedSide = this.hoveredSide;
                this.hoveredSide = "";
                this.sidesExpanded = false;
            }
            if (!this.hoveredAgreementType.isEmpty()) {
                this.selectedAgreementType = this.hoveredAgreementType;
                this.hoveredAgreementType = "";
                this.agreementTypeExpanded = false;
            }
            if (!this.hoveredConditionType.isEmpty()) {
                this.selectedConditionType = this.hoveredConditionType;
                this.hoveredConditionType = "";
                this.conditionTypeExpanded = false;
            }
            if (mouseX > this.guiLeft + 214 && mouseX < this.guiLeft + 214 + 9 && mouseY > this.guiTop - 8 && mouseY < this.guiTop - 8 + 10) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                this.field_73882_e.func_71373_a((GuiScreen)new WarAgreementListGui(this.player, this.warId, this.faction1, this.faction2));
            }
            if (!this.sidesExpanded && !this.conditionTypeExpanded && this.hoveredAction.equals("add_condition") && !this.selectedSide.isEmpty() && !this.selectedConditionType.isEmpty() && this.isNumeric(this.conditionValueInput.func_73781_b())) {
                this.conditions.put(this.selectedConditionType, this.conditionValueInput.func_73781_b() + "#" + this.selectedSide);
                this.conditionValueInput.func_73782_a("0");
                this.selectedSide = this.faction1;
                this.selectedConditionType = this.availableConditionTypes.get(0);
            }
            if (!this.agreementTypeExpanded && !this.hoveredCondition.isEmpty()) {
                this.conditions.remove(this.hoveredCondition);
            }
            if (!this.sidesExpanded && !this.conditionTypeExpanded && this.hoveredAction.equals("submit") && !this.selectedAgreementType.isEmpty() && this.isNumeric(this.durationInput.func_73781_b())) {
                String conditionsString = "";
                for (Map.Entry<String, String> pair : this.conditions.entrySet()) {
                    conditionsString = conditionsString + pair.getKey() + "#" + pair.getValue() + ",";
                }
                conditionsString = conditionsString.replaceAll(",$", "");
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionEnemyAgreementCreatePacket(this.warId, this.selectedAgreementType, Integer.parseInt(this.durationInput.func_73781_b()), conditionsString)));
                this.field_73882_e.func_71373_a((GuiScreen)new WarAgreementListGui(this.player, this.warId, this.faction1, this.faction2));
            }
            this.durationInput.func_73793_a(mouseX, mouseY, mouseButton);
            this.conditionValueInput.func_73793_a(mouseX, mouseY, mouseButton);
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    public void drawScaledString(String text, int x, int y, int color, float scale, boolean centered, boolean shadow) {
        GL11.glPushMatrix();
        GL11.glScalef((float)scale, (float)scale, (float)scale);
        float newX = x;
        if (centered) {
            newX = (float)x - (float)this.field_73886_k.func_78256_a(text) * scale / 2.0f;
        }
        if (shadow) {
            this.field_73886_k.func_85187_a(text, (int)(newX / scale), (int)((float)(y + 1) / scale), (color & 0xFCFCFC) >> 2 | color & 0xFF000000, false);
        }
        this.field_73886_k.func_85187_a(text, (int)(newX / scale), (int)((float)y / scale), color, false);
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public boolean func_73868_f() {
        return false;
    }

    protected void drawHoveringText(List par1List, int par2, int par3, FontRenderer font) {
        if (!par1List.isEmpty()) {
            GL11.glDisable((int)32826);
            RenderHelper.func_74518_a();
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            int k = 0;
            for (String s : par1List) {
                int l = font.func_78256_a(s);
                if (l <= k) continue;
                k = l;
            }
            int i1 = par2 + 12;
            int j1 = par3 - 12;
            int k1 = 8;
            if (par1List.size() > 1) {
                k1 += 2 + (par1List.size() - 1) * 10;
            }
            if (i1 + k > this.field_73880_f) {
                i1 -= 28 + k;
            }
            if (j1 + k1 + 6 > this.field_73881_g) {
                j1 = this.field_73881_g - k1 - 6;
            }
            this.field_73735_i = 300.0f;
            this.itemRenderer.field_77023_b = 300.0f;
            int l1 = -267386864;
            this.func_73733_a(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
            this.func_73733_a(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
            this.func_73733_a(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
            this.func_73733_a(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
            this.func_73733_a(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
            int i2 = 0x505000FF;
            int j2 = (i2 & 0xFEFEFE) >> 1 | i2 & 0xFF000000;
            this.func_73733_a(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
            this.func_73733_a(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
            this.func_73733_a(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
            this.func_73733_a(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);
            for (int k2 = 0; k2 < par1List.size(); ++k2) {
                String s1 = (String)par1List.get(k2);
                font.func_78261_a(s1, i1, j1, -1);
                if (k2 == 0) {
                    j1 += 2;
                }
                j1 += 10;
            }
            this.field_73735_i = 0.0f;
            this.itemRenderer.field_77023_b = 0.0f;
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            GL11.glEnable((int)32826);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
    }

    public boolean isNumeric(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) continue;
            return false;
        }
        return Integer.parseInt(str) > 0;
    }
}

