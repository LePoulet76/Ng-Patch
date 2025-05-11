/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.Icon
 *  net.minecraft.util.MathHelper
 *  net.minecraft.world.World
 */
package net.ilexiconn.nationsgui.forge.server.json.registry.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.ilexiconn.nationsgui.forge.server.json.registry.block.JSONDrop;
import net.ilexiconn.nationsgui.forge.server.json.registry.block.JSONMaterial;
import net.ilexiconn.nationsgui.forge.server.json.registry.block.JSONParticle;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class JSONBlock
extends Block {
    @SideOnly(value=Side.CLIENT)
    public Icon textureFront;
    @SideOnly(value=Side.CLIENT)
    public Icon textureBack;
    @SideOnly(value=Side.CLIENT)
    public Icon textureTop;
    @SideOnly(value=Side.CLIENT)
    public Icon textureBottom;
    @SideOnly(value=Side.CLIENT)
    public Icon textureSide;
    public String textureFrontString;
    public String textureBackString;
    public String textureTopString;
    public String textureBottomString;
    public String textureSideString;
    public boolean isTranslucent;
    public List<JSONDrop> drops;
    public Map<String, List<JSONParticle>> particles = new HashMap<String, List<JSONParticle>>();

    public JSONBlock(int id, JSONMaterial material) {
        super(id, material.getMaterial());
        this.func_111022_d("dirt");
        this.func_71849_a(CreativeTabs.field_78030_b);
        this.func_71884_a(material.getStepSound());
    }

    public void func_71860_a(World world, int x, int y, int z, EntityLivingBase entityLivingBase, ItemStack itemStack) {
        int metadata = MathHelper.func_76128_c((double)((double)(entityLivingBase.field_70177_z * 4.0f / 360.0f) + 0.5)) & 3;
        if (metadata == 0) {
            world.func_72921_c(x, y, z, 2, 2);
        } else if (metadata == 1) {
            world.func_72921_c(x, y, z, 5, 2);
        } else if (metadata == 2) {
            world.func_72921_c(x, y, z, 3, 2);
        } else if (metadata == 3) {
            world.func_72921_c(x, y, z, 4, 2);
        }
    }

    public boolean func_71926_d() {
        return false;
    }

    public boolean func_71886_c() {
        return this.isTranslucent;
    }

    @SideOnly(value=Side.CLIENT)
    public Icon func_71858_a(int side, int meta) {
        if (side == 0 && this.textureBottom != null) {
            return this.textureBottom;
        }
        if (side == 1 && this.textureTop != null) {
            return this.textureTop;
        }
        if (side == meta && this.textureFront != null) {
            return this.textureFront;
        }
        if ((side % 2 == 0 && side + 1 == meta || side % 2 == 1 && side - 1 == meta) && this.textureBack != null) {
            return this.textureBack;
        }
        return this.textureSide;
    }

    public void func_71893_a(World world, EntityPlayer player, int x, int y, int z, int meta) {
        if (this.drops != null) {
            Collections.shuffle(this.drops);
            for (JSONDrop drop : this.drops) {
                if (!drop.canDrop()) continue;
                drop.dropItem(player, world, x, y, z, this.field_71990_ca, this.drops.indexOf(drop));
                return;
            }
        } else {
            super.func_71893_a(world, player, x, y, z, meta);
        }
    }

    @SideOnly(value=Side.CLIENT)
    public void func_71862_a(World world, int x, int y, int z, Random random) {
        List<JSONParticle> particles = this.particles.get("tick");
        if (particles != null) {
            for (JSONParticle particle : particles) {
                particle.spawnParticle(world, x, y, z);
            }
        }
    }

    public void func_71898_d(World world, int x, int y, int z, int meta) {
        List<JSONParticle> particles;
        if (world.field_72995_K && (particles = this.particles.get("break")) != null) {
            for (JSONParticle particle : particles) {
                particle.spawnParticle(world, x, y, z);
            }
        }
    }
}

