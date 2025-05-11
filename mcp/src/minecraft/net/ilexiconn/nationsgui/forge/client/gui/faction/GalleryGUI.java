/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.ChatMessageComponent
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionGalleryDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionGalleryDeleteImagePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionGalleryEditImagePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ChatMessageComponent;

public class GalleryGUI
extends TabbedFactionGUI {
    public static boolean loaded = false;
    public static ArrayList<HashMap<String, Object>> galleryImages = null;
    public static int selectedImageIndex = 0;
    public static int firstImageCarouselIndex = 0;
    public int hoveredImageIndex = -1;
    public int editModeOverrideImagePosition = -1;
    public boolean editMode = false;
    private GuiTextField titleInput;
    private GuiTextField descriptionInput;

    public GalleryGUI() {
        loaded = false;
        selectedImageIndex = 0;
        firstImageCarouselIndex = 0;
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionGalleryDataPacket((String)FactionGUI.factionInfos.get("name"))));
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        this.titleInput = new GuiTextField(this.field_73886_k, this.guiLeft + 248, this.guiTop + 53, 200, 10);
        this.titleInput.func_73786_a(false);
        this.titleInput.func_73804_f(60);
        if (loaded && galleryImages.size() > 0) {
            this.titleInput.func_73782_a((String)galleryImages.get(selectedImageIndex).get("title"));
        }
        this.descriptionInput = new GuiTextField(this.field_73886_k, this.guiLeft + 248, this.guiTop + 80, 200, 10);
        this.descriptionInput.func_73786_a(false);
        this.descriptionInput.func_73804_f(150);
        if (loaded && galleryImages.size() > 0) {
            this.descriptionInput.func_73782_a((String)galleryImages.get(selectedImageIndex).get("description"));
        }
    }

    @Override
    public void func_73863_a(int mouseX, int mouseY, float partialTick) {
        this.func_73873_v_();
        tooltipToDraw.clear();
        this.hoveredAction = "";
        this.hoveredImageIndex = -1;
        ClientEventHandler.STYLE.bindTexture("faction_global_2");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 30, this.guiTop + 0, 0 * GUI_SCALE, 0 * GUI_SCALE, (this.xSize - 30) * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize - 30, this.ySize, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
        if (loaded) {
            ModernGui.drawScaledStringCustomFont((String)FactionGUI.factionInfos.get("name"), this.guiLeft + 43, this.guiTop + 6, 10395075, 0.5f, "left", false, "georamaMedium", 32);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.gallery.title"), this.guiLeft + 43, this.guiTop + 16, 0xFFFFFF, 0.75f, "left", false, "georamaSemiBold", 32);
            if (galleryImages.size() == 0) {
                ClientEventHandler.STYLE.bindTexture("faction_gallery");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 42, this.guiTop + 34, 0 * GUI_SCALE, 45 * GUI_SCALE, 191 * GUI_SCALE, 107 * GUI_SCALE, 191, 107, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            } else {
                ModernGui.bindRemoteTexture((String)galleryImages.get(selectedImageIndex).get("image"));
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 42, this.guiTop + 34, 0 * GUI_SCALE, 0 * GUI_SCALE, 191 * GUI_SCALE, 107 * GUI_SCALE, 191, 107, 191 * GUI_SCALE, 107 * GUI_SCALE, false);
                if (this.editMode && FactionGUI.hasPermissions("gallery")) {
                    ClientEventHandler.STYLE.bindTexture("faction_gallery");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 239, this.guiTop + 34, 0 * GUI_SCALE, 169 * GUI_SCALE, 214 * GUI_SCALE, 189 * GUI_SCALE, 214, 189, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.gallery.edit.title"), this.guiLeft + 245, this.guiTop + 41, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 32);
                    ClientEventHandler.STYLE.bindTexture("faction_gallery");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 245, this.guiTop + 51, 140 * GUI_SCALE, 0 * GUI_SCALE, 203 * GUI_SCALE, 13 * GUI_SCALE, 203, 13, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    this.titleInput.func_73795_f();
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.gallery.edit.description"), this.guiLeft + 246, this.guiTop + 68, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 32);
                    ClientEventHandler.STYLE.bindTexture("faction_gallery");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 245, this.guiTop + 78, 140 * GUI_SCALE, 0 * GUI_SCALE, 203 * GUI_SCALE, 13 * GUI_SCALE, 203, 13, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    this.descriptionInput.func_73795_f();
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.gallery.edit.position"), this.guiLeft + 246, this.guiTop + 105, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 32);
                    ClientEventHandler.STYLE.bindTexture("faction_gallery");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 245, this.guiTop + 115, 97 * GUI_SCALE, 0 * GUI_SCALE, 40 * GUI_SCALE, 11 * GUI_SCALE, 40, 11, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    ModernGui.drawScaledStringCustomFont((this.editModeOverrideImagePosition != -1 ? this.editModeOverrideImagePosition : selectedImageIndex + 1) + "", this.guiLeft + 245 + 20, this.guiTop + 117, 0xFFFFFF, 0.5f, "center", false, "georamaSemiBold", 28);
                    if (mouseX >= this.guiLeft + 245 && mouseX <= this.guiLeft + 245 + 8 && mouseY >= this.guiTop + 115 && mouseY <= this.guiTop + 115 + 11) {
                        this.hoveredAction = "input_position_down";
                    } else if (mouseX >= this.guiLeft + 277 && mouseX <= this.guiLeft + 277 + 8 && mouseY >= this.guiTop + 115 && mouseY <= this.guiTop + 115 + 11) {
                        this.hoveredAction = "input_position_up";
                    }
                    ClientEventHandler.STYLE.bindTexture("faction_gallery");
                    if (mouseX >= this.guiLeft + 335 && mouseX <= this.guiLeft + 335 + 51 && mouseY >= this.guiTop + 205 && mouseY <= this.guiTop + 205 + 13) {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 335, this.guiTop + 205, 116 * GUI_SCALE, 154 * GUI_SCALE, 51 * GUI_SCALE, 13 * GUI_SCALE, 51, 13, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                        this.hoveredAction = "valid_edit_image";
                    } else {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 335, this.guiTop + 205, 0 * GUI_SCALE, 154 * GUI_SCALE, 51 * GUI_SCALE, 13 * GUI_SCALE, 51, 13, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    }
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.gallery.label.valid"), this.guiLeft + 335 + 26, this.guiTop + 207, 2234425, 0.5f, "center", false, "georamaSemiBold", 32);
                    ClientEventHandler.STYLE.bindTexture("faction_gallery");
                    if (mouseX >= this.guiLeft + 390 && mouseX <= this.guiLeft + 390 + 51 && mouseY >= this.guiTop + 205 && mouseY <= this.guiTop + 205 + 13) {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 390, this.guiTop + 205, 116 * GUI_SCALE, 154 * GUI_SCALE, 51 * GUI_SCALE, 13 * GUI_SCALE, 51, 13, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                        this.hoveredAction = "cancel_edit_image";
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.gallery.label.cancel"), this.guiLeft + 390 + 26, this.guiTop + 207, 2234425, 0.5f, "center", false, "georamaSemiBold", 32);
                    } else {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 390, this.guiTop + 205, 55 * GUI_SCALE, 154 * GUI_SCALE, 51 * GUI_SCALE, 13 * GUI_SCALE, 51, 13, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.gallery.label.cancel"), this.guiLeft + 390 + 26, this.guiTop + 207, 0xFFFFFF, 0.5f, "center", false, "georamaSemiBold", 32);
                    }
                } else {
                    String title = (String)galleryImages.get(selectedImageIndex).get("title");
                    title = title.isEmpty() ? "\u00a7o" + I18n.func_135053_a((String)"faction.gallery.no_title") : title;
                    String description = (String)galleryImages.get(selectedImageIndex).get("description");
                    description = description.isEmpty() ? "\u00a7o" + I18n.func_135053_a((String)"faction.gallery.no_description") : description;
                    ModernGui.drawScaledStringCustomFont(title, this.guiLeft + 246, this.guiTop + 34, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 28);
                    ModernGui.drawScaledStringCustomFont("\u00a7o" + (String)galleryImages.get(selectedImageIndex).get("date"), this.guiLeft + 246, this.guiTop + 43, 0x6E76EE, 0.5f, "left", false, "georamaMedium", 22);
                    ModernGui.drawSectionStringCustomFont(description, this.guiLeft + 246, this.guiTop + 53, 10395075, 0.5f, "left", false, "georamaMedium", 25, 8, 350);
                }
                if (FactionGUI.hasPermissions("gallery")) {
                    ClientEventHandler.STYLE.bindTexture("faction_gallery");
                    if (mouseX >= this.guiLeft + 213 && mouseX <= this.guiLeft + 213 + 6 && mouseY >= this.guiTop + 37 && mouseY <= this.guiTop + 37 + 5) {
                        this.hoveredAction = "edit_image";
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 213, this.guiTop + 37, 80 * GUI_SCALE, 7 * GUI_SCALE, 6 * GUI_SCALE, 5 * GUI_SCALE, 6, 5, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    } else {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 213, this.guiTop + 37, 80 * GUI_SCALE, 0 * GUI_SCALE, 6 * GUI_SCALE, 5 * GUI_SCALE, 6, 5, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    }
                    if (mouseX >= this.guiLeft + 223 && mouseX <= this.guiLeft + 223 + 6 && mouseY >= this.guiTop + 37 && mouseY <= this.guiTop + 37 + 6) {
                        this.hoveredAction = "delete_image";
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 223, this.guiTop + 37, 89 * GUI_SCALE, 7 * GUI_SCALE, 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    } else {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 223, this.guiTop + 37, 89 * GUI_SCALE, 0 * GUI_SCALE, 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    }
                }
            }
            if (!this.editMode) {
                ClientEventHandler.STYLE.bindTexture("faction_global");
                if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 10 && mouseY >= this.guiTop + 184 && mouseY <= this.guiTop + 184 + 18) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 46, this.guiTop + 184, 2 * GUI_SCALE, 172 * GUI_SCALE, 10 * GUI_SCALE, 18 * GUI_SCALE, 10, 18, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    this.hoveredAction = "carousel_previous";
                } else {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 46, this.guiTop + 184, 2 * GUI_SCALE, 191 * GUI_SCALE, 10 * GUI_SCALE, 18 * GUI_SCALE, 10, 18, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                }
                if (mouseX >= this.guiLeft + 430 && mouseX <= this.guiLeft + 430 + 10 && mouseY >= this.guiTop + 184 && mouseY <= this.guiTop + 184 + 18) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 430, this.guiTop + 184, 14 * GUI_SCALE, 172 * GUI_SCALE, 10 * GUI_SCALE, 18 * GUI_SCALE, 10, 18, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    this.hoveredAction = "carousel_next";
                } else {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 430, this.guiTop + 184, 14 * GUI_SCALE, 191 * GUI_SCALE, 10 * GUI_SCALE, 18 * GUI_SCALE, 10, 18, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                }
                int index = 0;
                for (int i = firstImageCarouselIndex; i < galleryImages.size(); ++i) {
                    if (index >= 4) continue;
                    ModernGui.bindRemoteTexture((String)galleryImages.get(i).get("image"));
                    if (mouseX >= this.guiLeft + 64 + index * 92 && mouseX <= this.guiLeft + 64 + index * 92 + 79 && mouseY >= this.guiTop + 170 && mouseY <= this.guiTop + 170 + 43) {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 62 + index * 92, this.guiTop + 168, 0 * GUI_SCALE, 0 * GUI_SCALE, 191 * GUI_SCALE, 107 * GUI_SCALE, 83, 46, 191 * GUI_SCALE, 107 * GUI_SCALE, false);
                        this.hoveredAction = "select_image";
                        this.hoveredImageIndex = i;
                    } else {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 64 + index * 92, this.guiTop + 170, 0 * GUI_SCALE, 0 * GUI_SCALE, 191 * GUI_SCALE, 107 * GUI_SCALE, 79, 43, 191 * GUI_SCALE, 107 * GUI_SCALE, false);
                    }
                    ++index;
                }
                if (FactionGUI.hasPermissions("gallery") && index < 4) {
                    ClientEventHandler.STYLE.bindTexture("faction_gallery");
                    if (mouseX >= this.guiLeft + 64 + index * 92 && mouseX <= this.guiLeft + 64 + index * 92 + 79 && mouseY >= this.guiTop + 170 && mouseY <= this.guiTop + 170 + 43) {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 62 + index * 92, this.guiTop + 168, 0 * GUI_SCALE, 0 * GUI_SCALE, 79 * GUI_SCALE, 43 * GUI_SCALE, 83, 46, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                        this.hoveredAction = "add_image";
                    } else {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 64 + index * 92, this.guiTop + 170, 0 * GUI_SCALE, 0 * GUI_SCALE, 79 * GUI_SCALE, 43 * GUI_SCALE, 79, 43, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    }
                }
            }
        }
        super.func_73863_a(mouseX, mouseY, partialTick);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
    }

    public void func_73876_c() {
        this.titleInput.func_73780_a();
        this.descriptionInput.func_73780_a();
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        this.titleInput.func_73802_a(typedChar, keyCode);
        this.descriptionInput.func_73802_a(typedChar, keyCode);
        super.func_73869_a(typedChar, keyCode);
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && !this.hoveredAction.isEmpty()) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
            if (this.hoveredAction.equals("carousel_previous")) {
                firstImageCarouselIndex = Math.max(0, firstImageCarouselIndex - 1);
            } else if (this.hoveredAction.equals("carousel_next")) {
                if (galleryImages.size() >= firstImageCarouselIndex + (FactionGUI.hasPermissions("gallery") ? 4 : 3)) {
                    ++firstImageCarouselIndex;
                }
            } else if (this.hoveredAction.equals("select_image")) {
                selectedImageIndex = this.hoveredImageIndex;
                this.editMode = false;
                this.titleInput.func_73782_a((String)galleryImages.get(selectedImageIndex).get("title"));
                this.descriptionInput.func_73782_a((String)galleryImages.get(selectedImageIndex).get("description"));
            } else if (this.hoveredAction.equals("delete_image")) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionGalleryDeleteImagePacket((String)FactionGUI.factionInfos.get("name"), selectedImageIndex, ((Double)galleryImages.get(selectedImageIndex).get("id")).intValue())));
            } else if (this.hoveredAction.equals("edit_image")) {
                this.editMode = !this.editMode;
                this.titleInput.func_73782_a((String)galleryImages.get(selectedImageIndex).get("title"));
                this.descriptionInput.func_73782_a((String)galleryImages.get(selectedImageIndex).get("description"));
                this.editModeOverrideImagePosition = selectedImageIndex + 1;
            } else if (this.hoveredAction.equals("cancel_edit_image")) {
                this.editModeOverrideImagePosition = -1;
                this.editMode = false;
                this.titleInput.func_73782_a((String)galleryImages.get(selectedImageIndex).get("title"));
                this.descriptionInput.func_73782_a((String)galleryImages.get(selectedImageIndex).get("description"));
            } else if (this.hoveredAction.equals("valid_edit_image")) {
                this.editMode = false;
                galleryImages.get(selectedImageIndex).put("title", this.titleInput.func_73781_b());
                galleryImages.get(selectedImageIndex).put("description", this.descriptionInput.func_73781_b());
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionGalleryEditImagePacket((String)FactionGUI.factionInfos.get("name"), ((Double)galleryImages.get(selectedImageIndex).get("id")).intValue(), selectedImageIndex, this.editModeOverrideImagePosition - 1, this.titleInput.func_73781_b(), this.descriptionInput.func_73781_b())));
                this.editModeOverrideImagePosition = -1;
            } else if (this.hoveredAction.equals("input_position_down")) {
                this.editModeOverrideImagePosition = Math.max(1, this.editModeOverrideImagePosition - 1);
            } else if (this.hoveredAction.equals("input_position_up")) {
                this.editModeOverrideImagePosition = Math.min(galleryImages.size(), this.editModeOverrideImagePosition + 1);
            } else if (this.hoveredAction.equals("add_image")) {
                ClientData.lastCaptureScreenshot.put("gallery", System.currentTimeMillis());
                Minecraft.func_71410_x().func_71373_a(null);
                Minecraft.func_71410_x().field_71439_g.func_70006_a(ChatMessageComponent.func_111066_d((String)I18n.func_135053_a((String)"faction.take_picture_gallery")));
            }
            this.hoveredAction = "";
        }
        this.titleInput.func_73793_a(mouseX, mouseY, mouseButton);
        this.descriptionInput.func_73793_a(mouseX, mouseY, mouseButton);
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }
}

