/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.island;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandCreateGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandChangeFlySpeedPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandChangeGamemodePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandClearInventoryPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandMainDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandVotePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

@SideOnly(value=Side.CLIENT)
public class IslandMainGui
extends GuiScreen {
    public static final List<GuiScreenTab> TABS = new ArrayList<GuiScreenTab>();
    protected int xSize = 289;
    protected int ySize = 248;
    private int guiLeft;
    private int guiTop;
    private RenderItem itemRenderer = new RenderItem();
    public static boolean loaded = false;
    public boolean changedIsGamemode = false;
    public boolean newIsGamemode = false;
    public boolean changedFlySpeed = false;
    public Double newFlySpeed = 0.0;
    private GuiScrollBarFaction scrollBarMembers;
    private GuiScrollBarFaction scrollBarVisitors;
    public static HashMap<String, Object> islandInfos;
    private DynamicTexture imageTexture;
    public static boolean isPremium;
    public static boolean isOp;
    public static String serverNumber;
    List<String> biomes = new ArrayList<String>();
    public static HashMap<String, Boolean> membersPerms;
    public static HashMap<String, Boolean> visitorsPerms;
    public static HashMap<String, Boolean> islandFlags;

    public IslandMainGui() {
        loaded = false;
        this.biomes.addAll(Arrays.asList("plaine", "marais", "desert", "neige", "jungle"));
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IslandMainDataPacket()));
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        this.scrollBarMembers = new GuiScrollBarFaction(this.guiLeft + 156, this.guiTop + 109, 84);
        this.scrollBarVisitors = new GuiScrollBarFaction(this.guiLeft + 271, this.guiTop + 109, 84);
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        this.func_73873_v_();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("island_main");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        List<Object> tooltipToDraw = new ArrayList();
        if (loaded && islandInfos.size() > 0) {
            ClientEventHandler.STYLE.bindTexture("island_main");
            for (int i = 0; i < TABS.size(); ++i) {
                GuiScreenTab type = TABS.get(i);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                int x = IslandMainGui.getTabIndex(TABS.get(i));
                if (((Object)((Object)this)).getClass() == type.getClassReferent()) {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23, this.guiTop + 47 + i * 31, 23, 249, 29, 30, 512.0f, 512.0f, false);
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23 + 4, this.guiTop + 47 + i * 31 + 5, x * 20, 331, 20, 20, 512.0f, 512.0f, false);
                    continue;
                }
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23, this.guiTop + 47 + i * 31, 0, 249, 23, 30, 512.0f, 512.0f, false);
                GL11.glBlendFunc((int)770, (int)771);
                GL11.glEnable((int)3042);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.75f);
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23 + 4, this.guiTop + 47 + i * 31 + 5, x * 20, 331, 20, 20, 512.0f, 512.0f, false);
                GL11.glDisable((int)3042);
                if (mouseX < this.guiLeft - 23 || mouseX > this.guiLeft - 23 + 29 || mouseY < this.guiTop + 47 + i * 31 || mouseY > this.guiTop + 47 + 30 + i * 31) continue;
                tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)("island.tab." + x)));
            }
        } else if (loaded && islandInfos.size() == 0) {
            Minecraft.func_71410_x().func_71373_a(null);
        }
        ClientEventHandler.STYLE.bindTexture("island_main");
        if (mouseX >= this.guiLeft + 278 && mouseX <= this.guiLeft + 278 + 9 && mouseY >= this.guiTop - 8 && mouseY <= this.guiTop - 8 + 10) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 278, this.guiTop - 8, 139, 259, 9, 10, 512.0f, 512.0f, false);
        } else {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 278, this.guiTop - 8, 139, 249, 9, 10, 512.0f, 512.0f, false);
        }
        if (loaded && islandInfos.size() > 0) {
            if (this.imageTexture == null && !((String)islandInfos.get("image")).isEmpty()) {
                BufferedImage image = IslandMainGui.decodeToImage((String)islandInfos.get("image"));
                this.imageTexture = new DynamicTexture(image);
            }
            GL11.glPushMatrix();
            Double titleOffsetY = (double)(this.guiTop + 45) + (double)this.field_73886_k.func_78256_a((String)islandInfos.get("name")) * 1.5;
            GL11.glTranslatef((float)(this.guiLeft + 14), (float)titleOffsetY.intValue(), (float)0.0f);
            GL11.glRotatef((float)-90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glTranslatef((float)(-(this.guiLeft + 14)), (float)(-titleOffsetY.intValue()), (float)0.0f);
            this.drawScaledString((String)islandInfos.get("name"), this.guiLeft + 14, titleOffsetY.intValue(), 0xFFFFFF, 1.5f, false, false);
            GL11.glPopMatrix();
            ClientEventHandler.STYLE.bindTexture("island_main");
            if (loaded) {
                Float offsetY;
                int offsetX;
                int l;
                if (this.imageTexture != null) {
                    GL11.glBindTexture((int)3553, (int)this.imageTexture.func_110552_b());
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 58, this.guiTop + 27, 0.0f, 0.0f, 102, 102, 30, 30, 102.0f, 102.0f, false);
                    ClientEventHandler.STYLE.bindTexture("island_main");
                    GL11.glEnable((int)3042);
                    GL11.glDisable((int)2929);
                    GL11.glDepthMask((boolean)false);
                    GL11.glBlendFunc((int)770, (int)771);
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    GL11.glDisable((int)3008);
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 57, this.guiTop + 26, 292, 41, 32, 32, 512.0f, 512.0f, false);
                    GL11.glDisable((int)3042);
                    GL11.glEnable((int)2929);
                    GL11.glDepthMask((boolean)true);
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    GL11.glEnable((int)3008);
                }
                ClientEventHandler.STYLE.bindTexture("island_main");
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 83, this.guiTop + 52, 292, 75, 10, 11, 512.0f, 512.0f, false);
                if (mouseX > this.guiLeft + 83 && mouseX < this.guiLeft + 83 + 10 && mouseY > this.guiTop + 52 && mouseY < this.guiTop + 52 + 11) {
                    tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"island.list.tooltip.creation") + " " + (String)islandInfos.get("creationDate"));
                }
                this.drawScaledString((String)islandInfos.get("name"), this.guiLeft + 95, this.guiTop + 27, 0xB4B4B4, 1.0f, false, false);
                String[] descriptionWords = ((String)islandInfos.get("description")).replaceAll("\u00a7[0-9a-z]{1}", "").split(" ");
                String line = "";
                int lineNumber = 0;
                for (String descWord : descriptionWords) {
                    if (this.field_73886_k.func_78256_a(line + descWord) <= 128) {
                        if (!line.equals("")) {
                            line = line + " ";
                        }
                        line = line + descWord;
                        continue;
                    }
                    if (lineNumber == 0) {
                        line = "\u00a7o\"" + line;
                    }
                    this.drawScaledString(line, this.guiLeft + 158, this.guiTop + 44 + lineNumber * 10, 0xFFFFFF, 1.0f, true, true);
                    ++lineNumber;
                    line = descWord;
                }
                if (lineNumber == 0) {
                    line = "\u00a7o\"" + line;
                }
                this.drawScaledString(line + "\"", this.guiLeft + 158, this.guiTop + 44 + lineNumber * 10, 0xFFFFFF, 1.0f, true, true);
                int biomeNumber = this.biomes.indexOf((String)islandInfos.get("biome"));
                ClientEventHandler.STYLE.bindTexture("island_main");
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 236, this.guiTop + 24, 0 + biomeNumber * 29, 284, 28, 38, 512.0f, 512.0f, false);
                this.drawScaledString(((String)islandInfos.get("visit")).equals("0") || ((String)islandInfos.get("visit")).equals("1") ? (String)islandInfos.get("visit") + " " + I18n.func_135053_a((String)"island.main.label.visit") : islandInfos.get("visit") + " " + I18n.func_135053_a((String)"island.main.label.visits"), this.guiLeft + 104, this.guiTop + 75, 0xFFFFFF, 1.0f, true, false);
                if (((Boolean)islandInfos.get("currentPlayerVotedUp")).booleanValue() || mouseX >= this.guiLeft + 163 && mouseX <= this.guiLeft + 163 + 55 && mouseY >= this.guiTop + 70 && mouseY <= this.guiTop + 70 + 18) {
                    ClientEventHandler.STYLE.bindTexture("island_main");
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 163, this.guiTop + 70, 291, 4, 55, 18, 512.0f, 512.0f, false);
                }
                this.drawScaledString((String)islandInfos.get("voteUp"), this.guiLeft + 191, this.guiTop + 75, 0xFFFFFF, 1.0f, false, false);
                if (((Boolean)islandInfos.get("currentPlayerVotedDown")).booleanValue() || mouseX >= this.guiLeft + 221 && mouseX <= this.guiLeft + 221 + 55 && mouseY >= this.guiTop + 70 && mouseY <= this.guiTop + 70 + 18) {
                    ClientEventHandler.STYLE.bindTexture("island_main");
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 221, this.guiTop + 70, 291, 22, 55, 18, 512.0f, 512.0f, false);
                }
                this.drawScaledString((String)islandInfos.get("voteDown"), this.guiLeft + 244, this.guiTop + 75, 0xFFFFFF, 1.0f, false, false);
                this.drawScaledString(I18n.func_135053_a((String)"island.main.members"), this.guiLeft + 48, this.guiTop + 97, 0, 1.0f, false, false);
                GUIUtils.startGLScissor(this.guiLeft + 49, this.guiTop + 107, 107, 88);
                for (l = 0; l < ((ArrayList)islandInfos.get("members")).size(); ++l) {
                    offsetX = this.guiLeft + 49;
                    offsetY = Float.valueOf((float)(this.guiTop + 107 + l * 21) + this.getSlideMembers());
                    ClientEventHandler.STYLE.bindTexture("island_main");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 49, 107, 107, 21, 512.0f, 512.0f, false);
                    this.drawScaledString((String)((ArrayList)islandInfos.get("members")).get(l), offsetX + 4, offsetY.intValue() + 6, 0xFFFFFF, 1.0f, false, false);
                }
                GUIUtils.endGLScissor();
                if (mouseX > this.guiLeft + 48 && mouseX < this.guiLeft + 48 + 113 && mouseY > this.guiTop + 106 && mouseY < this.guiTop + 106 + 90) {
                    this.scrollBarMembers.draw(mouseX, mouseY);
                }
                this.drawScaledString(I18n.func_135053_a((String)"island.main.visitors"), this.guiLeft + 164, this.guiTop + 97, 0, 1.0f, false, false);
                GUIUtils.startGLScissor(this.guiLeft + 164, this.guiTop + 107, 107, 88);
                for (l = 0; l < ((ArrayList)islandInfos.get("visitors")).size(); ++l) {
                    offsetX = this.guiLeft + 164;
                    offsetY = Float.valueOf((float)(this.guiTop + 107 + l * 21) + this.getSlideVisitors());
                    ClientEventHandler.STYLE.bindTexture("island_main");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 49, 107, 107, 21, 512.0f, 512.0f, false);
                    this.drawScaledString((String)((ArrayList)islandInfos.get("visitors")).get(l), offsetX + 4, offsetY.intValue() + 6, 0xFFFFFF, 1.0f, false, false);
                }
                GUIUtils.endGLScissor();
                if (mouseX > this.guiLeft + 163 && mouseX < this.guiLeft + 163 + 113 && mouseY > this.guiTop + 106 && mouseY < this.guiTop + 106 + 90) {
                    this.scrollBarVisitors.draw(mouseX, mouseY);
                }
                ClientEventHandler.STYLE.bindTexture("island_list");
                if (!this.canPlayerCreateIsland() || mouseX >= this.guiLeft + 170 && mouseX <= this.guiLeft + 170 + 103 && mouseY >= this.guiTop + 203 && mouseY <= this.guiTop + 203 + 27) {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 170, this.guiTop + 203, 153, 319, 103, 27, 512.0f, 512.0f, false);
                }
                if (((Boolean)islandInfos.get("playerIsCreator")).booleanValue()) {
                    this.drawScaledString(I18n.func_135053_a((String)"island.main.create.button.own"), this.guiLeft + 228, this.guiTop + 213, 0xFFFFFF, 1.0f, true, false);
                } else {
                    this.drawScaledString(I18n.func_135053_a((String)"island.main.create.button.other"), this.guiLeft + 228, this.guiTop + 213, 0xFFFFFF, 1.0f, true, false);
                }
                if (((Boolean)islandInfos.get("playerIsCreator")).booleanValue()) {
                    this.drawScaledString(I18n.func_135053_a((String)"island.main.create.text.own"), this.guiLeft + 54, this.guiTop + 207, 0xFFFFFF, 1.0f, false, false);
                    this.drawScaledString(I18n.func_135053_a((String)"island.main.create.text.own_2"), this.guiLeft + 54, this.guiTop + 217, 0xFFFFFF, 1.0f, false, false);
                } else {
                    this.drawScaledString(I18n.func_135053_a((String)"island.main.create.text.other_1"), this.guiLeft + 54, this.guiTop + 207, 0xFFFFFF, 1.0f, false, false);
                    this.drawScaledString(I18n.func_135053_a((String)"island.main.create.text.other_2"), this.guiLeft + 54, this.guiTop + 217, 0xFFFFFF, 1.0f, false, false);
                }
                if (!this.canPlayerCreateIsland() && mouseX >= this.guiLeft + 170 && mouseX <= this.guiLeft + 170 + 103 && mouseY >= this.guiTop + 203 && mouseY <= this.guiTop + 203 + 27) {
                    if (isPremium) {
                        this.drawHoveringText(Arrays.asList(I18n.func_135053_a((String)"island.create.disable.premium")), mouseX, mouseY, this.field_73886_k);
                    } else {
                        this.drawHoveringText(Arrays.asList(I18n.func_135053_a((String)"island.create.disable.non_premium_1"), I18n.func_135053_a((String)"island.create.disable.non_premium_2"), I18n.func_135053_a((String)"island.create.disable.non_premium_3")), mouseX, mouseY, this.field_73886_k);
                    }
                }
                ClientEventHandler.STYLE.bindTexture("island_main");
                if (!((ArrayList)islandInfos.get("playerPermissions")).contains("gamemode") || mouseX >= this.guiLeft + 291 && mouseX <= this.guiLeft + 291 + 60 && mouseY >= this.guiTop + 4 && mouseY <= this.guiTop + 4 + 15) {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 291, this.guiTop + 4, 352, 132, 60, 15, 512.0f, 512.0f, false);
                    if (!((ArrayList)islandInfos.get("playerPermissions")).contains("gamemode") && mouseX >= this.guiLeft + 291 && mouseX <= this.guiLeft + 291 + 60 && mouseY >= this.guiTop + 4 && mouseY <= this.guiTop + 4 + 15) {
                        tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"island.global.permission_required"));
                    }
                } else {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 291, this.guiTop + 4, 352, 117, 60, 15, 512.0f, 512.0f, false);
                }
                if (((Boolean)islandInfos.get("isCreative")).booleanValue() && !this.changedIsGamemode || this.changedIsGamemode && this.newIsGamemode) {
                    this.drawScaledString(I18n.func_135053_a((String)"island.main.survival"), this.guiLeft + 321, this.guiTop + 8, 0, 1.0f, true, false);
                } else {
                    this.drawScaledString(I18n.func_135053_a((String)"island.main.creative"), this.guiLeft + 321, this.guiTop + 8, 0, 1.0f, true, false);
                }
                ClientEventHandler.STYLE.bindTexture("island_main");
                if ((Boolean)islandInfos.get("isCreative") == false && !this.changedIsGamemode || this.changedIsGamemode && !this.newIsGamemode || mouseX >= this.guiLeft + 291 && mouseX <= this.guiLeft + 291 + 60 && mouseY >= this.guiTop + 23 && mouseY <= this.guiTop + 23 + 15) {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 291, this.guiTop + 23, 352, 132, 60, 15, 512.0f, 512.0f, false);
                    if ((!((Boolean)islandInfos.get("isCreative")).booleanValue() && !this.changedIsGamemode || this.changedIsGamemode && !this.newIsGamemode) && mouseX >= this.guiLeft + 291 && mouseX <= this.guiLeft + 291 + 60 && mouseY >= this.guiTop + 23 && mouseY <= this.guiTop + 23 + 15) {
                        tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"island.main.flyspeed.blocked"));
                    }
                } else {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 291, this.guiTop + 23, 352, 117, 60, 15, 512.0f, 512.0f, false);
                }
                Double speed = this.changedFlySpeed ? this.newFlySpeed : (Double)islandInfos.get("flySpeed");
                speed = speed * 10.0;
                this.drawScaledString(I18n.func_135053_a((String)"island.main.flyspeed") + " " + speed.intValue(), this.guiLeft + 321, this.guiTop + 27, 0, 1.0f, true, false);
                ClientEventHandler.STYLE.bindTexture("island_main");
                if (mouseX >= this.guiLeft + 291 && mouseX <= this.guiLeft + 291 + 60 && mouseY >= this.guiTop + 42 && mouseY <= this.guiTop + 42 + 15) {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 291, this.guiTop + 42, 352, 132, 60, 15, 512.0f, 512.0f, false);
                } else {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 291, this.guiTop + 42, 352, 117, 60, 15, 512.0f, 512.0f, false);
                }
                this.drawScaledString(I18n.func_135053_a((String)"island.main.clearinventory"), this.guiLeft + 321, this.guiTop + 46, 0, 1.0f, true, false);
                if (!tooltipToDraw.isEmpty()) {
                    this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
                }
            }
        }
        super.func_73863_a(mouseX, mouseY, par3);
        GL11.glEnable((int)2896);
        RenderHelper.func_74519_b();
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

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (loaded && islandInfos.size() > 0) {
                for (int i = 0; i < TABS.size(); ++i) {
                    GuiScreenTab type = TABS.get(i);
                    if (mouseX < this.guiLeft - 20 || mouseX > this.guiLeft + 3 || mouseY < this.guiTop + 47 + i * 31 || mouseY > this.guiTop + 47 + 30 + i * 31) continue;
                    this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                    if (((Object)((Object)this)).getClass() == type.getClassReferent()) continue;
                    try {
                        type.call();
                        continue;
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (mouseX > this.guiLeft + 278 && mouseX < this.guiLeft + 278 + 9 && mouseY > this.guiTop - 8 && mouseY < this.guiTop - 8 + 10) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a(null);
            } else if (loaded && islandInfos.size() > 0 && mouseX >= this.guiLeft + 163 && mouseX <= this.guiLeft + 163 + 55 && mouseY >= this.guiTop + 70 && mouseY <= this.guiTop + 70 + 18) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IslandVotePacket((String)islandInfos.get("id"), "up")));
            } else if (loaded && islandInfos.size() > 0 && mouseX >= this.guiLeft + 221 && mouseX <= this.guiLeft + 221 + 55 && mouseY >= this.guiTop + 70 && mouseY <= this.guiTop + 70 + 18) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IslandVotePacket((String)islandInfos.get("id"), "down")));
            } else if (loaded && islandInfos.size() > 0 && this.canPlayerCreateIsland() && mouseX >= this.guiLeft + 170 && mouseX <= this.guiLeft + 170 + 103 && mouseY >= this.guiTop + 203 && mouseY <= this.guiTop + 203 + 27) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new IslandCreateGui(isPremium, serverNumber));
            } else if (loaded && islandInfos.size() > 0 && ((ArrayList)islandInfos.get("playerPermissions")).contains("gamemode") && mouseX >= this.guiLeft + 291 && mouseX <= this.guiLeft + 291 + 60 && mouseY >= this.guiTop + 4 && mouseY <= this.guiTop + 4 + 15) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IslandChangeGamemodePacket()));
                this.newIsGamemode = !this.changedIsGamemode ? (Boolean)islandInfos.get("isCreative") == false : !this.newIsGamemode;
                this.changedIsGamemode = true;
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
            } else if (loaded && islandInfos.size() > 0 && (((Boolean)islandInfos.get("isCreative")).booleanValue() && !this.changedIsGamemode || this.changedIsGamemode && this.newIsGamemode) && mouseX >= this.guiLeft + 291 && mouseX <= this.guiLeft + 291 + 60 && mouseY >= this.guiTop + 4 && mouseY <= this.guiTop + 23 + 15) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IslandChangeFlySpeedPacket()));
                this.newFlySpeed = !this.changedFlySpeed ? Double.valueOf((Double)islandInfos.get("flySpeed") + 0.1 <= 0.5 ? (Double)islandInfos.get("flySpeed") + 0.1 : 0.1) : Double.valueOf(this.newFlySpeed + 0.1 <= 0.5 ? this.newFlySpeed + 0.1 : 0.1);
                this.changedFlySpeed = true;
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
            } else if (loaded && islandInfos.size() > 0 && mouseX >= this.guiLeft + 291 && mouseX <= this.guiLeft + 291 + 60 && mouseY >= this.guiTop + 4 && mouseY <= this.guiTop + 42 + 15) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IslandClearInventoryPacket()));
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    private float getSlideMembers() {
        return ((ArrayList)islandInfos.get("members")).size() > 4 ? (float)(-(((ArrayList)islandInfos.get("members")).size() - 4) * 20) * this.scrollBarMembers.getSliderValue() : 0.0f;
    }

    private float getSlideVisitors() {
        return ((ArrayList)islandInfos.get("visitors")).size() > 4 ? (float)(-(((ArrayList)islandInfos.get("visitors")).size() - 4) * 20) * this.scrollBarVisitors.getSliderValue() : 0.0f;
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

    public static int getTabIndex(GuiScreenTab inventoryTab) {
        String classReferent = inventoryTab.getClassReferent().toString();
        if (classReferent.contains("MainGui")) {
            return 0;
        }
        if (classReferent.contains("MembersGui")) {
            return 1;
        }
        if (classReferent.contains("PermsGui")) {
            return 2;
        }
        if (classReferent.contains("SettingsGui")) {
            return 3;
        }
        if (classReferent.contains("PropertiesGui")) {
            return 4;
        }
        if (classReferent.contains("BackupGui")) {
            return 5;
        }
        return 0;
    }

    public static BufferedImage decodeToImage(String imageString) {
        BufferedImage image = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    public boolean canPlayerCreateIsland() {
        return !(!isPremium && (Double)islandInfos.get("currentPlayerIslandCount") >= 1.0) && (!isPremium || !((Double)islandInfos.get("currentPlayerIslandCount") >= 5.0));
    }

    static {
        isPremium = false;
        isOp = false;
        serverNumber = "0";
        membersPerms = new HashMap();
        visitorsPerms = new HashMap();
        islandFlags = new HashMap();
    }
}

