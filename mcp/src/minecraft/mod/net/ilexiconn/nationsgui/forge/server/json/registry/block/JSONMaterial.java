package net.ilexiconn.nationsgui.forge.server.json.registry.block;

import net.minecraft.block.Block;
import net.minecraft.block.StepSound;
import net.minecraft.block.material.Material;

public enum JSONMaterial
{
    GRASS(Material.grass, Block.soundGrassFootstep, new String[]{"grass"}),
    DIRT(Material.ground, Block.soundGravelFootstep, new String[]{"dirt", "ground", "gravel"}),
    METAL(Material.iron, Block.soundMetalFootstep, new String[]{"iron", "metal"}),
    WOOD(Material.wood, Block.soundWoodFootstep, new String[]{"wood", "planks"}),
    SAND(Material.sand, Block.soundSandFootstep, new String[]{"sand"}),
    STONE(Material.rock, Block.soundStoneFootstep, new String[]{"stone", "rock"}),
    GLASS(Material.glass, Block.soundGlassFootstep, new String[]{"glass"});
    private static final JSONMaterial[] VALUES = values();
    private String[] names;
    private Material material;
    private StepSound stepSound;

    private JSONMaterial(Material material, StepSound stepSound, String ... names)
    {
        this.material = material;
        this.stepSound = stepSound;
        this.names = names;
    }

    public Material getMaterial()
    {
        return this.material;
    }

    public StepSound getStepSound()
    {
        return this.stepSound;
    }

    public static JSONMaterial parseMaterial(String material)
    {
        JSONMaterial[] var1 = VALUES;
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3)
        {
            JSONMaterial value = var1[var3];
            String[] var5 = value.names;
            int var6 = var5.length;

            for (int var7 = 0; var7 < var6; ++var7)
            {
                String name = var5[var7];

                if (name.equals(material))
                {
                    return value;
                }
            }
        }

        return STONE;
    }
}
