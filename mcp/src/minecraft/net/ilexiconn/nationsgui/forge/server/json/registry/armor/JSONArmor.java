/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.entity.Entity
 *  net.minecraft.item.EnumArmorMaterial
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemStack
 */
package net.ilexiconn.nationsgui.forge.server.json.registry.armor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.ilexiconn.nationsgui.forge.server.item.ICustomTooltip;
import net.ilexiconn.nationsgui.forge.server.json.registry.armor.JSONEffect;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class JSONArmor
extends ItemArmor
implements ICustomTooltip {
    public String iconHash;
    public String textureHash;
    public String textureOverlayHash;
    public int repairItemID;
    public List<JSONEffect> effects;
    public int tooltipColor = 0;
    public int tooltipBackgroundColor = 0;

    public JSONArmor(int id, EnumArmorMaterial material, int type) {
        super(id, material, 0, type);
        this.func_111206_d("coal");
    }

    @SideOnly(value=Side.CLIENT)
    public TextureAtlasSprite setIcon(TextureAtlasSprite texture) {
        this.field_77791_bV = texture;
        return this.field_77791_bV;
    }

    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        return type == null ? (this.field_77881_a == 2 ? this.textureOverlayHash : this.textureHash) : null;
    }

    public boolean func_82789_a(ItemStack input, ItemStack output) {
        return this.repairItemID == output.field_77993_c;
    }

    @Override
    public int getTooltipColor(ItemStack itemStack) {
        return this.tooltipColor;
    }

    @Override
    public int getTooltipBackgroundColor(ItemStack itemStack) {
        return this.tooltipBackgroundColor;
    }
}

