/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.renderer.ThreadDownloadImageData
 *  net.minecraft.util.ResourceLocation
 */
package net.ilexiconn.nationsgui.forge.client.gui.shop;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Locale;
import net.ilexiconn.nationsgui.forge.client.gui.shop.CategoryItem;
import net.ilexiconn.nationsgui.forge.client.gui.shop.ShopGUI;
import net.ilexiconn.nationsgui.forge.client.gui.shop.type.CategoryTypes;
import net.ilexiconn.nationsgui.forge.client.gui.shop.type.ICategoryType;
import net.ilexiconn.nationsgui.forge.server.json.CategoryJSON;
import net.ilexiconn.nationsgui.forge.server.permission.IPermissionCallback;
import net.ilexiconn.nationsgui.forge.server.permission.PermissionCache;
import net.ilexiconn.nationsgui.forge.server.permission.PermissionType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.util.ResourceLocation;

@SideOnly(value=Side.CLIENT)
public class Category {
    private CategoryJSON container;
    private ICategoryType type;
    private Boolean enabled;
    private ResourceLocation resourceLocation;
    private ThreadDownloadImageData imageData;
    private ResourceLocation resourceLocationImage;
    private ThreadDownloadImageData imageDataImage;
    private ResourceLocation resourceLocationImageHover;
    private ThreadDownloadImageData imageDataImageHover;
    private CategoryItem[] items;

    public Category(ShopGUI gui, CategoryJSON container) {
        this.container = container;
        this.type = CategoryTypes.valueOf(container.type.toUpperCase(Locale.ENGLISH)).getType();
        this.resourceLocation = new ResourceLocation("category_icon/" + container.name);
        this.imageData = gui.getDownloadImage(this.resourceLocation, container.icon);
        if (container.image != null) {
            this.resourceLocationImage = new ResourceLocation("category_image/" + container.name);
            this.imageDataImage = gui.getDownloadImage(this.resourceLocationImage, container.image);
            if (container.imageHover != null) {
                this.resourceLocationImageHover = new ResourceLocation("category_image_hover/" + container.name);
                this.imageDataImageHover = gui.getDownloadImage(this.resourceLocationImageHover, container.imageHover);
            }
        }
        this.enabled = !container.permission ? Boolean.valueOf(true) : PermissionCache.INSTANCE.checkPermission(PermissionType.CATEGORY, new IPermissionCallback(){

            @Override
            public void call(String permission, boolean has) {
                Category.this.enabled = has;
            }
        }, container.name);
        this.items = new CategoryItem[container.items.length];
        for (int i = 0; i < container.items.length; ++i) {
            this.items[i] = new CategoryItem(gui, this, container.items[i], i);
        }
    }

    public boolean isEnabled() {
        return this.enabled == null ? false : this.enabled;
    }

    public void initCategory(ShopGUI gui, List<GuiButton> buttonList) {
        this.type.init(this.getX(gui), this.getY(gui), gui, this, buttonList);
    }

    public void renderCategory(ShopGUI gui, int mouseX, int mouseY) {
        this.type.render(this.getX(gui), this.getY(gui), mouseX, mouseY, gui, this, Minecraft.func_71410_x().field_71466_p);
    }

    public void renderCategoryPost(ShopGUI gui, int mouseX, int mouseY) {
        this.type.renderPost(this.getX(gui), this.getY(gui), mouseX, mouseY, gui, this, Minecraft.func_71410_x().field_71466_p);
    }

    public void mouseClicked(ShopGUI gui, int mouseX, int mouseY, int button) {
        this.type.mouseClicked(this.getX(gui), this.getY(gui), mouseX, mouseY, button, gui, this);
    }

    public void actionPerformed(ShopGUI gui, GuiButton button) {
        this.type.actionPerformed(button, gui, this);
    }

    private int getX(ShopGUI gui) {
        return gui.field_73880_f / 2 - 147;
    }

    private int getY(ShopGUI gui) {
        return gui.field_73881_g / 2 - 119;
    }

    public String getName() {
        return this.container.name;
    }

    public boolean isIconLoaded() {
        return this.imageData.func_110557_a();
    }

    public ResourceLocation getIcon() {
        return this.resourceLocation;
    }

    public boolean isImageLoaded() {
        return this.imageDataImage.func_110557_a();
    }

    public ResourceLocation getImage() {
        return this.resourceLocationImage;
    }

    public boolean isImageHoverLoaded() {
        return this.imageDataImageHover.func_110557_a();
    }

    public ResourceLocation getImageHover() {
        return this.resourceLocationImageHover;
    }

    public CategoryItem[] getItems() {
        return this.items;
    }

    public String getURL() {
        return this.container.url;
    }
}

