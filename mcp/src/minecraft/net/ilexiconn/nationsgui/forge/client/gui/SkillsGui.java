/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.inventory.GuiInventory
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.world.World
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IncrementObjectivePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SkillDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SkillLeaderBoardDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class SkillsGui
extends GuiScreen {
    protected int xSize = 463;
    protected int ySize = 232;
    private int guiLeft;
    private int guiTop;
    public String playerTarget;
    public static boolean loaded = false;
    public static HashMap<String, HashMap<String, Object>> skillsData = null;
    public static HashMap<String, List<String>> skillsLeaderBoard = null;
    public static HashMap<String, String> skillsTopInterServ = null;
    private RenderItem itemRenderer = new RenderItem();
    public String hoveredAction = "";
    public String hoveredSkill = "";
    public static String selectedSkill = "";
    public int hoveredLevel = -1;
    private GuiScrollBarGeneric scrollBar;
    private EntityOtherPlayerMP topPlayerEntity = null;
    public static HashMap<String, Integer> skillIconPositionY = new HashMap();
    public static HashMap<String, ResourceLocation> cacheHeadPlayer = new HashMap();
    public static String displayMode = "personal";
    public static boolean achievementDone = false;

    public SkillsGui(String playerTarget) {
        this.playerTarget = playerTarget;
        loaded = false;
        displayMode = "personal";
        if (skillIconPositionY.isEmpty()) {
            skillIconPositionY.put("farmer", 240);
            skillIconPositionY.put("lumberjack", 271);
            skillIconPositionY.put("builder", 303);
            skillIconPositionY.put("hunter", 333);
            skillIconPositionY.put("engineer", 365);
            skillIconPositionY.put("miner", 396);
        }
        selectedSkill = "";
        if (!achievementDone) {
            achievementDone = true;
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IncrementObjectivePacket("player_open_skills", 1)));
        }
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new SkillDataPacket(this.playerTarget)));
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        this.scrollBar = new GuiScrollBarGeneric(this.guiLeft + 437, this.guiTop + 56, 154, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_grey.png"), 3, 26);
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        List<Object> tooltipToDraw = new ArrayList();
        this.hoveredLevel = -1;
        this.hoveredSkill = "";
        this.hoveredAction = "";
        this.func_73873_v_();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("skills");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        if (mouseX >= this.guiLeft + 433 && mouseX <= this.guiLeft + 433 + 18 && mouseY >= this.guiTop + 8 && mouseY <= this.guiTop + 8 + 18) {
            ClientEventHandler.STYLE.bindTexture("skills");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 433, this.guiTop + 8, 129, 280, 18, 18, 512.0f, 512.0f, false);
        }
        ModernGui.drawScaledStringCustomFont(Minecraft.func_71410_x().field_71439_g.field_71092_bJ.equalsIgnoreCase(this.playerTarget) ? I18n.func_135053_a((String)"skills.title") : I18n.func_135053_a((String)"skills.title_of").replace("#player#", this.playerTarget.toUpperCase()), this.guiLeft + 12, this.guiTop + 11, 0xC4C4C4, 1.0f, "left", false, "georamaExtraBold", 28);
        if (loaded && skillsData.size() > 0) {
            int skillOffsetY = 0;
            for (Map.Entry<String, HashMap<String, Object>> pair : skillsData.entrySet()) {
                String skillName = pair.getKey();
                ClientEventHandler.STYLE.bindTexture("skills");
                if (selectedSkill.equals(skillName)) {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 12, this.guiTop + 41 + 31 * skillOffsetY, 214, 485, 104, 27, 512.0f, 512.0f, false);
                } else if (mouseX >= this.guiLeft + 12 && mouseX <= this.guiLeft + 12 + 104 && mouseY >= this.guiTop + 41 + 31 * skillOffsetY && mouseY <= this.guiTop + 41 + 31 * skillOffsetY + 27) {
                    this.hoveredSkill = skillName;
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 12, this.guiTop + 41 + 31 * skillOffsetY, 107, 485, 104, 27, 512.0f, 512.0f, false);
                } else {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 12, this.guiTop + 41 + 31 * skillOffsetY, 0, 485, 104, 27, 512.0f, 512.0f, false);
                }
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 17, this.guiTop + 48 + 31 * skillOffsetY, skillName.equals(selectedSkill) ? 423 : 443, skillIconPositionY.get(skillName), 15, 15, 512.0f, 512.0f, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("skills.skill." + skillName)), this.guiLeft + 38, this.guiTop + 50 + 31 * skillOffsetY, skillName.equals(selectedSkill) ? 0xFFFFFF : 0xC4C4C4, 1.0f, "left", false, "georamaSemiBold", 20);
                ModernGui.drawScaledStringCustomFont(String.format("%.0f", (Double)pair.getValue().get("actualLevelNumber")), this.guiLeft + 20 + 104 - 15, this.guiTop + 51 + 31 * skillOffsetY, skillName.equals(selectedSkill) ? 0xFFFFFF : 0x6E76EE, 1.0f, "right", false, "georamaSemiBold", 17);
                ++skillOffsetY;
            }
            if (displayMode.equals("personal")) {
                if (!selectedSkill.isEmpty()) {
                    HashMap<String, Object> skillData = skillsData.get(selectedSkill);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("skills.skill." + selectedSkill)).toUpperCase(), this.guiLeft + 134, this.guiTop + 51, 0x6E76EE, 1.0f, "left", false, "georamaBold", 28);
                    for (int i = 0; i <= 4; ++i) {
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("skills.skill." + selectedSkill + ".desc." + (i + 1))), this.guiLeft + 134, this.guiTop + 78 + i * 12, 0xFFFFFF, 1.0f, "left", false, "georamaRegular", 20);
                    }
                    ClientEventHandler.STYLE.bindTexture("skills");
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 134, this.guiTop + 145, 443, skillIconPositionY.get(selectedSkill), 15, 15, 512.0f, 512.0f, false);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"skills.how_to"), this.guiLeft + 153, this.guiTop + 148, 0xC4C4C4, 1.0f, "left", false, "georamaRegular", 18);
                    if (mouseX >= this.guiLeft + 134 && mouseX <= this.guiLeft + 134 + 15 + this.field_73886_k.func_78256_a(I18n.func_135053_a((String)"skills.how_to")) && mouseY >= this.guiTop + 145 && mouseY <= this.guiTop + 145 + 15) {
                        tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)("skills.skill." + selectedSkill + ".tooltip.how_to")).split("##"));
                    }
                    ClientEventHandler.STYLE.bindTexture("skills");
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 340, this.guiTop + 50, 104, 320, 101, 111, 512.0f, 512.0f, false);
                    ClientEventHandler.STYLE.bindTexture("skill_" + selectedSkill);
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 336, this.guiTop - 7, 0.0f, 0.0f, 331, 400, 109, 132, 331.0f, 400.0f, false);
                    Double playerPosition = (Double)skillData.get("position");
                    ModernGui.drawScaledStringCustomFont(playerPosition != 0.0 ? String.format("%.0f", playerPosition) + "e" : "NC", this.guiLeft + 390, this.guiTop + 129, 0xFFFFFF, 1.0f, "center", false, "georamaSemiBold", 23);
                    ClientEventHandler.STYLE.bindTexture("skills");
                    if (mouseX >= this.guiLeft + 355 && mouseX <= this.guiLeft + 355 + 71 && mouseY >= this.guiTop + 142 && mouseY <= this.guiTop + 142 + 15) {
                        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 355, this.guiTop + 142, 149, 297, 71, 15, 512.0f, 512.0f, false);
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"skills.leaderboard"), this.guiLeft + 391, this.guiTop + 146, 0x6E76EE, 1.0f, "center", false, "georamaSemiBold", 18);
                        this.hoveredAction = "switch_leaderboard";
                    } else {
                        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 355, this.guiTop + 142, 149, 280, 71, 15, 512.0f, 512.0f, false);
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"skills.leaderboard"), this.guiLeft + 391, this.guiTop + 146, 0xFFFFFF, 1.0f, "center", false, "georamaSemiBold", 18);
                    }
                    ClientEventHandler.STYLE.bindTexture("skills");
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 134, this.guiTop + 170, 0, 433, 307, 46, 512.0f, 512.0f, false);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"skills.level").toUpperCase(), this.guiLeft + 141, this.guiTop + 175, 0xFFFFFF, 1.0f, "left", false, "georamaSemiBold", 20);
                    Double playerScore = (Double)skillData.get("score");
                    Double playerActualLevelNumber = (Double)skillData.get("actualLevelNumber");
                    Double playerActualLevelScore = (Double)skillData.get("actualLevel");
                    Double playerNextLevelScore = (Double)skillData.get("nextLevel");
                    ModernGui.drawScaledStringCustomFont(String.format("%.0f", playerScore) + "/" + String.format("%.0f", playerNextLevelScore), this.guiLeft + 436, this.guiTop + 175, 0xFFFFFF, 1.0f, "right", false, "georamaRegular", 18);
                    List levels = (List)skillData.get("levels");
                    int levelDotInterval = 276 / levels.size();
                    Double scoreProgress = Math.min(1.0, (playerScore - playerActualLevelScore) / (playerNextLevelScore - playerActualLevelScore));
                    Double progressWidth = Math.min(276.0, playerActualLevelNumber * (double)levelDotInterval + (double)levelDotInterval * scoreProgress);
                    ClientEventHandler.STYLE.bindTexture("skills");
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 149, this.guiTop + 200, 166, 234, 276, 4, 512.0f, 512.0f, false);
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 149, this.guiTop + 200, 215, 420, progressWidth.intValue(), 4, 512.0f, 512.0f, false);
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 149 - 7, this.guiTop + 194, 129, 252, 15, 15, 512.0f, 512.0f, false);
                    ModernGui.drawScaledStringCustomFont("0", this.guiLeft + 150, this.guiTop + 196, 0xFFFFFF, 1.0f, "center", false, "georamaBold", 22);
                    for (int i = 1; i <= levels.size(); ++i) {
                        ClientEventHandler.STYLE.bindTexture("skills");
                        if (playerScore >= (Double)levels.get(i - 1)) {
                            if (mouseX >= this.guiLeft + 149 - 7 + levelDotInterval * i && mouseX <= this.guiLeft + 149 - 7 + levelDotInterval * i + 15 && mouseY >= this.guiTop + 194 && mouseY <= this.guiTop + 194 + 15) {
                                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 149 - 7 + levelDotInterval * i, this.guiTop + 194, 149, 252, 15, 15, 512.0f, 512.0f, false);
                                ModernGui.drawScaledStringCustomFont(i + "", this.guiLeft + 150 + levelDotInterval * i, this.guiTop + 196, 0x6E76EE, 1.0f, "center", false, "georamaBold", 22);
                                tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)("skills.skill." + selectedSkill + ".tooltip.level." + i)).split("##"));
                                continue;
                            }
                            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 149 - 7 + levelDotInterval * i, this.guiTop + 194, 129, 252, 15, 15, 512.0f, 512.0f, false);
                            ModernGui.drawScaledStringCustomFont(i + "", this.guiLeft + 150 + levelDotInterval * i, this.guiTop + 196, 0xFFFFFF, 1.0f, "center", false, "georamaBold", 22);
                            continue;
                        }
                        if (mouseX >= this.guiLeft + 149 - 7 + levelDotInterval * i && mouseX <= this.guiLeft + 149 - 7 + levelDotInterval * i + 15 && mouseY >= this.guiTop + 194 && mouseY <= this.guiTop + 194 + 15) {
                            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 149 - 7 + levelDotInterval * i, this.guiTop + 194, 149, 234, 15, 15, 512.0f, 512.0f, false);
                            tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)("skills.skill." + selectedSkill + ".tooltip.level." + i)).split("##"));
                            continue;
                        }
                        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 149 - 7 + levelDotInterval * i, this.guiTop + 194, 129, 234, 15, 15, 512.0f, 512.0f, false);
                    }
                }
            } else {
                ResourceLocation resourceLocation;
                int i;
                ClientEventHandler.STYLE.bindTexture("skills");
                if (mouseX >= this.guiLeft + 375 && mouseX <= this.guiLeft + 375 + 52 && mouseY >= this.guiTop + 9 && mouseY <= this.guiTop + 9 + 16) {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 375, this.guiTop + 9, 168, 262, 52, 16, 512.0f, 512.0f, false);
                    this.hoveredAction = "switch_personal";
                } else {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 375, this.guiTop + 9, 168, 245, 52, 16, 512.0f, 512.0f, false);
                }
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"skills.return"), this.guiLeft + 401, this.guiTop + 13, 0x6E76EE, 1.0f, "center", false, "georamaRegular", 18);
                ClientEventHandler.STYLE.bindTexture("skills");
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 130, this.guiTop + 50, 0, 234, 127, 35, 512.0f, 512.0f, false);
                if (!skillsTopInterServ.get(selectedSkill).isEmpty()) {
                    if (this.topPlayerEntity == null || !skillsTopInterServ.get(selectedSkill).split("#")[0].equalsIgnoreCase(this.topPlayerEntity.field_71092_bJ)) {
                        try {
                            this.topPlayerEntity = new EntityOtherPlayerMP((World)this.field_73882_e.field_71441_e, skillsTopInterServ.get(selectedSkill).split("#")[0]);
                        }
                        catch (Exception e) {
                            this.topPlayerEntity = null;
                        }
                    }
                    if (this.topPlayerEntity != null) {
                        GUIUtils.startGLScissor(this.guiLeft + 130, this.guiTop + 35, 50, 50);
                        GuiInventory.func_110423_a((int)(this.guiLeft + 155), (int)(this.guiTop + 117), (int)42, (float)0.0f, (float)0.0f, (EntityLivingBase)this.topPlayerEntity);
                        GUIUtils.endGLScissor();
                    }
                }
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"skills.best") + " " + I18n.func_135053_a((String)("skills.skill." + selectedSkill)).toUpperCase() + " NG", this.guiLeft + 180, this.guiTop + 54, 0, 1.0f, "left", false, "georamaRegular", 13);
                ModernGui.drawScaledStringCustomFont(!skillsTopInterServ.get(selectedSkill).isEmpty() ? this.topPlayerEntity.field_71092_bJ : "???", this.guiLeft + 180, this.guiTop + 61, 0xFFFFFF, 1.0f, "left", false, "georamaSemiBold", 22);
                ModernGui.drawScaledStringCustomFont(!skillsTopInterServ.get(selectedSkill).isEmpty() ? skillsTopInterServ.get(selectedSkill).split("#")[1] : "0", this.guiLeft + 180, this.guiTop + 74, 0xFFFFFF, 1.0f, "left", false, "georamaRegular", 18);
                ClientEventHandler.STYLE.bindTexture("skills");
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 130, this.guiTop + 91, 0, 271, 127, 35, 512.0f, 512.0f, false);
                if (!cacheHeadPlayer.containsKey(this.playerTarget)) {
                    try {
                        ResourceLocation resourceLocation2 = AbstractClientPlayer.field_110314_b;
                        resourceLocation2 = AbstractClientPlayer.func_110311_f((String)this.playerTarget);
                        AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation2, (String)this.playerTarget);
                        cacheHeadPlayer.put(this.playerTarget, resourceLocation2);
                    }
                    catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    Minecraft.func_71410_x().field_71446_o.func_110577_a(cacheHeadPlayer.get(this.playerTarget));
                    this.field_73882_e.func_110434_K().func_110577_a(cacheHeadPlayer.get(this.playerTarget));
                    GUIUtils.drawScaledCustomSizeModalRect(this.guiLeft + 166, this.guiTop + 122, 8.0f, 16.0f, 8, -8, -26, -26, 64.0f, 64.0f);
                }
                ModernGui.drawScaledStringCustomFont(this.playerTarget.toUpperCase(), this.guiLeft + 180, this.guiTop + 95, 0, 1.0f, "left", false, "georamaRegular", 13);
                ModernGui.drawScaledStringCustomFont(String.format("%.0f", (Double)skillsData.get(selectedSkill).get("score")), this.guiLeft + 180, this.guiTop + 102, 0, 1.0f, "left", false, "georamaSemiBold", 22);
                Double playerPosition = (Double)skillsData.get(selectedSkill).get("position");
                ModernGui.drawScaledStringCustomFont(playerPosition != 0.0 ? String.format("%.0f", playerPosition) + "e" : "NC", this.guiLeft + 180, this.guiTop + 115, 0, 1.0f, "left", false, "georamaRegular", 18);
                ClientEventHandler.STYLE.bindTexture("skills");
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 130, this.guiTop + 135, 384, 430, 127, 82, 512.0f, 512.0f, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"skills.top_3"), this.guiLeft + 138, this.guiTop + 141, 0xC4C4C4, 1.0f, "left", false, "georamaRegular", 13);
                for (i = 0; i < 3; ++i) {
                    ClientEventHandler.STYLE.bindTexture("skills");
                    if (mouseX >= this.guiLeft + 137 && mouseX <= this.guiLeft + 137 + 15 && mouseY >= this.guiTop + 153 + i * 21 && mouseY <= this.guiTop + 153 + i * 21 + 15) {
                        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 137, this.guiTop + 153 + i * 21, 149, 252, 15, 15, 512.0f, 512.0f, false);
                        ModernGui.drawScaledStringCustomFont(i + 1 + "", this.guiLeft + 145, this.guiTop + 155 + i * 21, 0x6E76EE, 1.0f, "center", false, "georamaBold", 22);
                        tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)("skills.rewards.server." + (i + 1))).split("##"));
                    } else {
                        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 137, this.guiTop + 153 + i * 21, 129, 252, 15, 15, 512.0f, 512.0f, false);
                        ModernGui.drawScaledStringCustomFont(i + 1 + "", this.guiLeft + 145, this.guiTop + 155 + i * 21, 0xFFFFFF, 1.0f, "center", false, "georamaBold", 22);
                    }
                    String playerName = skillsLeaderBoard.get(selectedSkill).size() > i ? skillsLeaderBoard.get(selectedSkill).get(i).split("#")[0] : "-";
                    String playerScore = skillsLeaderBoard.get(selectedSkill).size() > i ? skillsLeaderBoard.get(selectedSkill).get(i).split("#")[1] : "0";
                    Double score = Double.parseDouble(playerScore) / 1000.0;
                    if (!cacheHeadPlayer.containsKey(playerName)) {
                        try {
                            resourceLocation = AbstractClientPlayer.field_110314_b;
                            resourceLocation = AbstractClientPlayer.func_110311_f((String)playerName);
                            AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)playerName);
                            cacheHeadPlayer.put(playerName, resourceLocation);
                        }
                        catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        Minecraft.func_71410_x().field_71446_o.func_110577_a(cacheHeadPlayer.get(playerName));
                        this.field_73882_e.func_110434_K().func_110577_a(cacheHeadPlayer.get(playerName));
                        GUIUtils.drawScaledCustomSizeModalRect(this.guiLeft + 170, this.guiTop + 166 + i * 21, 8.0f, 16.0f, 8, -8, -12, -12, 64.0f, 64.0f);
                    }
                    ModernGui.drawScaledStringCustomFont(playerName, this.guiLeft + 175, this.guiTop + 157 + i * 21, 0xC4C4C4, 1.0f, "left", false, "georamaSemiBold", 16);
                    ModernGui.drawScaledStringCustomFont(String.format("%.0f", score) + "k", this.guiLeft + 250, this.guiTop + 157 + i * 21, 0xC4C4C4, 1.0f, "right", false, "georamaRegular", 16);
                }
                ClientEventHandler.STYLE.bindTexture("skills");
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 265, this.guiTop + 48, 224, 245, 181, 169, 512.0f, 512.0f, false);
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 437, this.guiTop + 56, 407, 245, 3, 154, 512.0f, 512.0f, false);
                this.scrollBar.draw(mouseX, mouseY);
                if (skillsLeaderBoard.get(selectedSkill).size() > 3) {
                    GUIUtils.startGLScissor(this.guiLeft + 265, this.guiTop + 48, 172, 169);
                    for (i = 3; i < skillsLeaderBoard.get(selectedSkill).size(); ++i) {
                        Float offsetY = Float.valueOf((float)(this.guiTop + 53 + (i - 3) * 18) + this.getSlide());
                        ModernGui.drawScaledStringCustomFont(i + 1 + "", this.guiLeft + 276, offsetY.intValue() + 4, 0xFFFFFF, 1.0f, "center", false, "georamaSemiBold", 18);
                        ClientEventHandler.STYLE.bindTexture("skills");
                        String playerName = skillsLeaderBoard.get(selectedSkill).get(i).split("#")[0];
                        String playerScore = skillsLeaderBoard.get(selectedSkill).get(i).split("#")[1];
                        if (!cacheHeadPlayer.containsKey(playerName)) {
                            try {
                                resourceLocation = AbstractClientPlayer.field_110314_b;
                                resourceLocation = AbstractClientPlayer.func_110311_f((String)playerName);
                                AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)playerName);
                                cacheHeadPlayer.put(playerName, resourceLocation);
                            }
                            catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        } else {
                            Minecraft.func_71410_x().field_71446_o.func_110577_a(cacheHeadPlayer.get(playerName));
                            this.field_73882_e.func_110434_K().func_110577_a(cacheHeadPlayer.get(playerName));
                            GUIUtils.drawScaledCustomSizeModalRect(this.guiLeft + 298, offsetY.intValue() + 14, 8.0f, 16.0f, 8, -8, -12, -12, 64.0f, 64.0f);
                        }
                        ModernGui.drawScaledStringCustomFont(playerName, this.guiLeft + 305, offsetY.intValue() + 5, 0xC4C4C4, 1.0f, "left", false, "georamaSemiBold", 16);
                        ModernGui.drawScaledStringCustomFont(playerScore, this.guiLeft + 422, offsetY.intValue() + 5, 0xC4C4C4, 1.0f, "right", false, "georamaRegular", 16);
                    }
                    GUIUtils.endGLScissor();
                }
            }
        }
        if (!tooltipToDraw.isEmpty()) {
            this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
        }
        super.func_73863_a(mouseX, mouseY, par3);
        GL11.glEnable((int)2896);
        RenderHelper.func_74519_b();
    }

    private float getSlide() {
        return skillsLeaderBoard.get(selectedSkill).size() - 3 > 9 ? (float)(-(skillsLeaderBoard.get(selectedSkill).size() - 3 - 9) * 18) * this.scrollBar.getSliderValue() : 0.0f;
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (mouseX > this.guiLeft + 433 && mouseX < this.guiLeft + 433 + 18 && mouseY > this.guiTop + 8 && mouseY < this.guiTop + 8 + 18) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a(null);
            }
            if (!this.hoveredSkill.isEmpty()) {
                selectedSkill = this.hoveredSkill;
                this.hoveredSkill = "";
            }
            if (this.hoveredAction.equals("switch_leaderboard")) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new SkillLeaderBoardDataPacket()));
            }
            if (this.hoveredAction.equals("switch_personal")) {
                displayMode = "personal";
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
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
}

