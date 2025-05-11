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
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

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
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.faction.DiplomatieGUI_OLD;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGui_OLD;
import net.ilexiconn.nationsgui.forge.client.gui.faction.RefuseColonyConfirmGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionDiplomatieWishActionPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionDiplomatieWishDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

@SideOnly(value=Side.CLIENT)
public class DiplomatieWishGUI
extends GuiScreen {
    protected int xSize = 333;
    protected int ySize = 168;
    private int guiLeft;
    private int guiTop;
    public static boolean loaded = false;
    private RenderItem itemRenderer = new RenderItem();
    public static HashMap<String, ArrayList<HashMap<String, String>>> diplomatieWishInfos = new HashMap();
    private GuiScrollBarFaction scrollBarReceived;
    private GuiScrollBarFaction scrollBarSent;
    private EntityPlayer entityPlayer;
    private HashMap<String, DynamicTexture> cachedFlags = new HashMap();
    private String hoveredCountryFlag = "";
    private String hoveredAction = "";
    private String hoveredCountry = "";
    private String hoveredRelationType = "";
    private String clickedCountry = "";

    public DiplomatieWishGUI(EntityPlayer entityPlayer) {
        this.entityPlayer = entityPlayer;
        loaded = false;
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        this.scrollBarReceived = new GuiScrollBarFaction(this.guiLeft + 175, this.guiTop + 57, 80);
        this.scrollBarSent = new GuiScrollBarFaction(this.guiLeft + 306, this.guiTop + 57, 80);
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionDiplomatieWishDataPacket((String)FactionGui_OLD.factionInfos.get("name"))));
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        this.func_73873_v_();
        ClientEventHandler.STYLE.bindTexture("faction_diplomatie_wish");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        if (mouseX >= this.guiLeft + 320 && mouseX <= this.guiLeft + 320 + 9 && mouseY >= this.guiTop - 6 && mouseY <= this.guiTop - 6 + 10) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 320, this.guiTop - 6, 0, 179, 9, 10, 512.0f, 512.0f, false);
        } else {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 320, this.guiTop - 6, 0, 169, 9, 10, 512.0f, 512.0f, false);
        }
        this.drawScaledString(I18n.func_135053_a((String)"faction.diplomatie_wish.title"), this.guiLeft + 51, this.guiTop + 17, 0x191919, 1.2f, false, false);
        this.hoveredCountryFlag = "";
        this.hoveredCountry = "";
        this.hoveredAction = "";
        this.hoveredRelationType = "";
        this.drawScaledString(I18n.func_135053_a((String)"faction.diplomatie_wish.received"), this.guiLeft + 57, this.guiTop + 44, 0x191919, 0.9f, false, false);
        ArrayList<HashMap<String, String>> received = diplomatieWishInfos.get("received");
        if (received != null && received.size() > 0) {
            GUIUtils.startGLScissor(this.guiLeft + 57, this.guiTop + 53, 118, 88);
            for (int l = 0; l < received.size(); ++l) {
                HashMap<String, String> paysInfos = received.get(l);
                int offsetX = this.guiLeft + 57;
                Float offsetY = Float.valueOf((float)(this.guiTop + 53 + l * 22) + this.getSlideReceived());
                ClientEventHandler.STYLE.bindTexture("faction_diplomatie_wish");
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 57, 53, 118, 22, 512.0f, 512.0f, false);
                if (!this.cachedFlags.containsKey(paysInfos.get("factionName")) && paysInfos.get("flag") != null && !paysInfos.get("flag").isEmpty() || this.cachedFlags.containsKey(paysInfos.get("factionName")) && !this.cachedFlags.get(paysInfos.get("factionName")).equals(paysInfos.get("flag"))) {
                    BufferedImage image = DiplomatieWishGUI.decodeToImage(paysInfos.get("flag"));
                    this.cachedFlags.put(paysInfos.get("factionName"), new DynamicTexture(image));
                }
                if (this.cachedFlags.containsKey(paysInfos.get("factionName")) && this.cachedFlags.get(paysInfos.get("factionName")) != null) {
                    GL11.glBindTexture((int)3553, (int)this.cachedFlags.get(paysInfos.get("factionName")).func_110552_b());
                    ModernGui.drawScaledCustomSizeModalRect(offsetX + 5, offsetY.intValue() + 5, 0.0f, 0.0f, 156, 78, 18, 10, 156.0f, 78.0f, false);
                } else {
                    ClientEventHandler.STYLE.bindTexture("faction_diplomatie_wish");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 5, offsetY.intValue() + 5, 38, 169, 18, 10, 512.0f, 512.0f, false);
                }
                if (mouseX >= offsetX + 5 && mouseX <= offsetX + 5 + 18 && mouseY >= offsetY.intValue() + 5 && mouseY <= offsetY.intValue() + 5 + 10) {
                    this.hoveredCountryFlag = paysInfos.get("factionName");
                }
                this.drawScaledString(I18n.func_135053_a((String)("faction.common." + paysInfos.get("relationType").toLowerCase())), offsetX + 28, offsetY.intValue() + 6, 0xFFFFFF, 1.0f, false, false);
                if (!((Boolean)FactionGui_OLD.factionInfos.get("isInCountry")).booleanValue() || !((Boolean)FactionGui_OLD.factionInfos.get("isAtLeastOfficer")).booleanValue()) continue;
                ClientEventHandler.STYLE.bindTexture("faction_diplomatie_wish");
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 93, offsetY.intValue() + 5, 15, 169, 10, 10, 512.0f, 512.0f, false);
                if (mouseX >= offsetX + 93 && mouseX <= offsetX + 93 + 10 && mouseY >= offsetY.intValue() + 5 && mouseY <= offsetY.intValue() + 5 + 10) {
                    this.hoveredCountry = paysInfos.get("factionName");
                    this.hoveredAction = "yes";
                    this.hoveredRelationType = paysInfos.get("relationType");
                }
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 105, offsetY.intValue() + 5, 25, 169, 10, 10, 512.0f, 512.0f, false);
                if (mouseX < offsetX + 105 || mouseX > offsetX + 105 + 10 || mouseY < offsetY.intValue() + 5 || mouseY > offsetY.intValue() + 5 + 10) continue;
                this.hoveredCountry = paysInfos.get("factionName");
                this.hoveredAction = "no";
                this.hoveredRelationType = paysInfos.get("relationType");
            }
            GUIUtils.endGLScissor();
            if (mouseX >= this.guiLeft + 57 && mouseX <= this.guiLeft + 57 + 118 && mouseY >= this.guiTop + 53 && mouseY <= this.guiTop + 53 + 88) {
                this.scrollBarReceived.draw(mouseX, mouseY);
            }
        }
        this.drawScaledString(I18n.func_135053_a((String)"faction.diplomatie_wish.sent"), this.guiLeft + 188, this.guiTop + 44, 0x191919, 0.9f, false, false);
        ArrayList<HashMap<String, String>> sent = diplomatieWishInfos.get("sent");
        if (sent != null && sent.size() > 0) {
            GUIUtils.startGLScissor(this.guiLeft + 188, this.guiTop + 53, 118, 88);
            for (int l = 0; l < sent.size(); ++l) {
                HashMap<String, String> paysInfos = sent.get(l);
                int offsetX = this.guiLeft + 188;
                Float offsetY = Float.valueOf((float)(this.guiTop + 53 + l * 22) + this.getSlideSent());
                ClientEventHandler.STYLE.bindTexture("faction_diplomatie_wish");
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 188, 53, 118, 22, 512.0f, 512.0f, false);
                if (!this.cachedFlags.containsKey(paysInfos.get("factionName")) && paysInfos.get("flag") != null && !paysInfos.get("flag").isEmpty() || this.cachedFlags.containsKey(paysInfos.get("factionName")) && !this.cachedFlags.get(paysInfos.get("factionName")).equals(paysInfos.get("flag"))) {
                    BufferedImage image = DiplomatieWishGUI.decodeToImage(paysInfos.get("flag"));
                    this.cachedFlags.put(paysInfos.get("factionName"), new DynamicTexture(image));
                }
                if (this.cachedFlags.containsKey(paysInfos.get("factionName")) && this.cachedFlags.get(paysInfos.get("factionName")) != null) {
                    GL11.glBindTexture((int)3553, (int)this.cachedFlags.get(paysInfos.get("factionName")).func_110552_b());
                    ModernGui.drawScaledCustomSizeModalRect(offsetX + 5, offsetY.intValue() + 5, 0.0f, 0.0f, 156, 78, 18, 10, 156.0f, 78.0f, false);
                } else {
                    ClientEventHandler.STYLE.bindTexture("faction_diplomatie_wish");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 5, offsetY.intValue() + 5, 38, 169, 18, 10, 512.0f, 512.0f, false);
                }
                if (mouseX >= offsetX + 5 && mouseX <= offsetX + 5 + 18 && mouseY >= offsetY.intValue() + 5 && mouseY <= offsetY.intValue() + 5 + 10) {
                    this.hoveredCountryFlag = paysInfos.get("factionName");
                }
                this.drawScaledString(I18n.func_135053_a((String)("faction.common." + paysInfos.get("relationType").toLowerCase())), offsetX + 28, offsetY.intValue() + 6, 0xFFFFFF, 1.0f, false, false);
                if (!((Boolean)FactionGui_OLD.factionInfos.get("isInCountry")).booleanValue() || !((Boolean)FactionGui_OLD.factionInfos.get("isAtLeastOfficer")).booleanValue()) continue;
                ClientEventHandler.STYLE.bindTexture("faction_diplomatie_wish");
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 105, offsetY.intValue() + 5, 25, 169, 10, 10, 512.0f, 512.0f, false);
                if (mouseX < offsetX + 105 || mouseX > offsetX + 105 + 10 || mouseY < offsetY.intValue() + 5 || mouseY > offsetY.intValue() + 5 + 10) continue;
                this.hoveredCountry = paysInfos.get("factionName");
                this.hoveredAction = "cancel";
            }
            GUIUtils.endGLScissor();
            if (mouseX >= this.guiLeft + 188 && mouseX <= this.guiLeft + 188 + 118 && mouseY >= this.guiTop + 53 && mouseY <= this.guiTop + 53 + 88) {
                this.scrollBarSent.draw(mouseX, mouseY);
            }
        }
        if (!this.hoveredCountryFlag.isEmpty()) {
            this.drawHoveringText(Arrays.asList(this.hoveredCountryFlag), mouseX, mouseY, this.field_73886_k);
        }
    }

    private float getSlideReceived() {
        return diplomatieWishInfos.get("received").size() > 4 ? (float)(-(diplomatieWishInfos.get("received").size() - 4) * 22) * this.scrollBarReceived.getSliderValue() : 0.0f;
    }

    private float getSlideSent() {
        return diplomatieWishInfos.get("sent").size() > 4 ? (float)(-(diplomatieWishInfos.get("sent").size() - 4) * 22) * this.scrollBarSent.getSliderValue() : 0.0f;
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (mouseX > this.guiLeft + 320 && mouseX < this.guiLeft + 320 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new DiplomatieGUI_OLD(this.entityPlayer));
            }
            if (!(this.hoveredAction.isEmpty() || this.hoveredCountry.isEmpty() || this.clickedCountry.equals(this.hoveredCountry))) {
                this.clickedCountry = this.hoveredCountry;
                if (this.hoveredAction.equals("no") && this.hoveredRelationType.equalsIgnoreCase("colony")) {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new RefuseColonyConfirmGui(this, this.hoveredCountry, this.hoveredAction, this.hoveredRelationType));
                } else {
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionDiplomatieWishActionPacket((String)FactionGui_OLD.factionInfos.get("name"), this.hoveredCountry, this.hoveredAction, this.hoveredRelationType)));
                }
            }
        }
    }

    public boolean func_73868_f() {
        return false;
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

