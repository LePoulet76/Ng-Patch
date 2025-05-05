package fr.nationsglory.remoteitem.common.data;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemData
{
    private int id = 0;
    private String type = "item";
    private String texture = "";
    private Map<String, String> lang = new HashMap();
    private boolean full3D = false;
    private int repairItemID = 0;
    private int durability = 0;
    private int harvestLevel = 0;
    private float miningSpeed = 0.0F;
    private float damageVsEntities = 0.0F;
    private int enchantability = 0;
    private int potionCusumedID = 0;
    private List<PotionEffectData> effects = new ArrayList();
    private boolean gfxEffet = false;
    private Map<String, String> description = new HashMap();
    private int stackSize = 64;
    private List<EnchantData> enchants = new ArrayList();
    private int feedAmount = 0;
    private float saturationAmplifier = 0.0F;
    private boolean wolfsFavoriteMeat = false;
    private String tab = "";
    private String tooltipColor = "";
    private String tooltipBackgroundColor = "";

    public int getId()
    {
        return this.id - 256;
    }

    public String getTexture()
    {
        return this.texture;
    }

    public Map<String, String> getLocaleMap()
    {
        return this.lang;
    }

    public boolean isFull3D()
    {
        return this.full3D;
    }

    public int getRepairItemID()
    {
        return this.repairItemID;
    }

    public int getDurability()
    {
        return this.durability;
    }

    public String getType()
    {
        return this.type;
    }

    public Map<String, String> getLang()
    {
        return this.lang;
    }

    public int getHarvestLevel()
    {
        return this.harvestLevel;
    }

    public float getMiningSpeed()
    {
        return this.miningSpeed;
    }

    public float getDamageVsEntities()
    {
        return this.damageVsEntities;
    }

    public int getEnchantability()
    {
        return this.enchantability;
    }

    public int getPotionCusumedID()
    {
        return this.potionCusumedID;
    }

    public List<PotionEffectData> getEffects()
    {
        return this.effects;
    }

    public boolean isGfxEffet()
    {
        return this.gfxEffet;
    }

    public Map<String, String> getDescription()
    {
        return this.description;
    }

    public int getStackSize()
    {
        return this.stackSize;
    }

    public List<EnchantData> getEnchants()
    {
        return this.enchants;
    }

    public int getFeedAmount()
    {
        return this.feedAmount;
    }

    public float getSaturationAmplifier()
    {
        return this.saturationAmplifier;
    }

    public boolean isWolfsFavoriteMeat()
    {
        return this.wolfsFavoriteMeat;
    }

    public String getCreativeTab()
    {
        return this.tab;
    }

    public int getTooltipColor()
    {
        return (new BigInteger("FF" + this.tooltipColor, 16)).intValue();
    }

    public int getTooltipBackgroundColor()
    {
        return (new BigInteger("FF" + this.tooltipBackgroundColor, 16)).intValue();
    }
}
