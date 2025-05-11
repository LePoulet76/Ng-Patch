/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.BlockContainer
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.particle.EntityFX
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  org.lwjgl.input.Keyboard
 */
package net.ilexiconn.nationsgui.forge.server.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Random;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.particle.RadioParticle;
import net.ilexiconn.nationsgui.forge.server.block.entity.RadioBlockEntity;
import net.ilexiconn.nationsgui.forge.server.block.entity.SpeakerBlockEntity;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

public class SpeakerBlock
extends BlockContainer {
    public SpeakerBlock() {
        super(647, Material.field_76253_m);
        this.func_71864_b("speaker");
        this.func_71849_a(CreativeTabs.field_78030_b);
        this.func_71848_c(2.0f);
        this.func_71894_b(2.0f);
        this.func_111022_d("wool_colored_black");
    }

    public int func_71857_b() {
        return -1;
    }

    public boolean func_71926_d() {
        return false;
    }

    public boolean func_71886_c() {
        return false;
    }

    public AxisAlignedBB func_71872_e(World world, int x, int y, int z) {
        this.func_71902_a((IBlockAccess)world, x, y, z);
        return super.func_71872_e(world, x, y, z);
    }

    public void func_71902_a(IBlockAccess world, int x, int y, int z) {
        switch (world.func_72805_g(x, y, z) & 7) {
            case 2: {
                this.func_71905_a(0.12f, 0.12f, 0.65f, 0.88f, 0.88f, 1.0f);
                break;
            }
            case 3: {
                this.func_71905_a(0.12f, 0.12f, 0.0f, 0.88f, 0.88f, 0.35f);
                break;
            }
            case 4: {
                this.func_71905_a(0.65f, 0.12f, 0.12f, 1.0f, 0.88f, 0.88f);
                break;
            }
            case 5: {
                this.func_71905_a(0.0f, 0.12f, 0.12f, 0.35f, 0.88f, 0.88f);
            }
        }
    }

    public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune) {
        SpeakerBlockEntity blockEntity = (SpeakerBlockEntity)world.func_72796_p(x, y, z);
        ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        ItemStack stack = new ItemStack(NationsGUI.SPEAKER);
        NBTTagCompound compound = new NBTTagCompound();
        compound.func_74768_a("RadioX", blockEntity.radioX);
        compound.func_74768_a("RadioY", blockEntity.radioY);
        compound.func_74768_a("RadioZ", blockEntity.radioZ);
        stack.func_77982_d(compound);
        list.add(stack);
        return list;
    }

    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        if (Keyboard.isKeyDown((int)29)) {
            SpeakerBlockEntity blockEntity = (SpeakerBlockEntity)world.func_72796_p(x, y, z);
            ItemStack stack = new ItemStack(NationsGUI.SPEAKER);
            NBTTagCompound compound = new NBTTagCompound();
            compound.func_74768_a("RadioX", blockEntity.radioX);
            compound.func_74768_a("RadioY", blockEntity.radioY);
            compound.func_74768_a("RadioZ", blockEntity.radioZ);
            stack.func_77982_d(compound);
            return stack;
        }
        return super.getPickBlock(target, world, x, y, z);
    }

    @SideOnly(value=Side.CLIENT)
    public void func_71862_a(World world, int x, int y, int z, Random random) {
        SpeakerBlockEntity speaker = (SpeakerBlockEntity)world.func_72796_p(x, y, z);
        RadioBlockEntity blockEntity = (RadioBlockEntity)world.func_72796_p(speaker.radioX, speaker.radioY, speaker.radioZ);
        if (blockEntity != null && (float)blockEntity.volume > 0.0f && blockEntity.streamer != null && blockEntity.streamer.isPlaying() && (!blockEntity.needsRedstone || world.func_94572_D(x, y, z) > 0)) {
            Minecraft.func_71410_x().field_71452_i.func_78873_a((EntityFX)new RadioParticle(world, (float)x + ((float)random.nextInt(75) + 1.0f) / 100.0f, (float)y + ((float)random.nextInt(100) + 1.0f) / 100.0f, (float)z + ((float)random.nextInt(100) + 1.0f) / 100.0f, (float)blockEntity.volume / 100.0f, random));
        }
    }

    public TileEntity func_72274_a(World world) {
        return new SpeakerBlockEntity();
    }
}

