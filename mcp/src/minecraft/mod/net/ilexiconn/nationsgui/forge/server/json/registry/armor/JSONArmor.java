package net.ilexiconn.nationsgui.forge.server.json.registry.armor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.ilexiconn.nationsgui.forge.server.item.ICustomTooltip;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class JSONArmor extends ItemArmor implements ICustomTooltip
{
    public String iconHash;
    public String textureHash;
    public String textureOverlayHash;
    public int repairItemID;
    public List<JSONEffect> effects;
    public int tooltipColor = 0;
    public int tooltipBackgroundColor = 0;

    public JSONArmor(int id, EnumArmorMaterial material, int type)
    {
        super(id, material, 0, type);
        this.setTextureName("coal");
    }

    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite setIcon(TextureAtlasSprite texture)
    {
        return (TextureAtlasSprite)((TextureAtlasSprite)(this.itemIcon = texture));
    }

    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
        return type == null ? (this.armorType == 2 ? this.textureOverlayHash : this.textureHash) : null;
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    public boolean getIsRepairable(ItemStack input, ItemStack output)
    {
        return this.repairItemID == output.itemID;
    }

    public int getTooltipColor(ItemStack itemStack)
    {
        return this.tooltipColor;
    }

    public int getTooltipBackgroundColor(ItemStack itemStack)
    {
        return this.tooltipBackgroundColor;
    }
}
