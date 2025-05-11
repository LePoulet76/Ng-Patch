/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.StepSound
 *  net.minecraft.block.material.Material
 */
package net.ilexiconn.nationsgui.forge.server.json.registry.block;

import net.minecraft.block.Block;
import net.minecraft.block.StepSound;
import net.minecraft.block.material.Material;

public enum JSONMaterial {
    GRASS(Material.field_76247_b, Block.field_71965_g, "grass"),
    DIRT(Material.field_76248_c, Block.field_71964_f, "dirt", "ground", "gravel"),
    METAL(Material.field_76243_f, Block.field_71977_i, "iron", "metal"),
    WOOD(Material.field_76245_d, Block.field_71967_e, "wood", "planks"),
    SAND(Material.field_76251_o, Block.field_71972_l, "sand"),
    STONE(Material.field_76246_e, Block.field_71976_h, "stone", "rock"),
    GLASS(Material.field_76264_q, Block.field_71974_j, "glass");

    private static final JSONMaterial[] VALUES;
    private String[] names;
    private Material material;
    private StepSound stepSound;

    private JSONMaterial(Material material, StepSound stepSound, String ... names) {
        this.material = material;
        this.stepSound = stepSound;
        this.names = names;
    }

    public Material getMaterial() {
        return this.material;
    }

    public StepSound getStepSound() {
        return this.stepSound;
    }

    public static JSONMaterial parseMaterial(String material) {
        for (JSONMaterial value : VALUES) {
            for (String name : value.names) {
                if (!name.equals(material)) continue;
                return value;
            }
        }
        return STONE;
    }

    static {
        VALUES = JSONMaterial.values();
    }
}

