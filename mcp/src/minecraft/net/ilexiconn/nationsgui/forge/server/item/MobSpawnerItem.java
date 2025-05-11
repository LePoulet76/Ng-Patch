/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.entity.EntityList
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.monster.IMob
 *  net.minecraft.entity.passive.EntityPig
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntityMobSpawner
 *  net.minecraft.util.Icon
 *  net.minecraft.util.StatCollector
 *  net.minecraft.world.World
 */
package net.ilexiconn.nationsgui.forge.server.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.ilexiconn.nationsgui.forge.server.ServerEventHandler;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUITransformer;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class MobSpawnerItem
extends ItemBlock {
    private int lastX;
    private int lastY;
    private int lastZ;
    private static Map<Integer, EntityLiving> entityHashMap = new HashMap<Integer, EntityLiving>();

    public MobSpawnerItem() {
        super(3335);
        try {
            Field field = ItemBlock.class.getDeclaredField(NationsGUITransformer.inDevelopment ? "blockID" : "field_77885_a");
            field.setAccessible(true);
            field.set((Object)this, Block.field_72065_as.field_71990_ca);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.func_77655_b("mob_spawner");
        this.func_111206_d("coal");
        this.func_77627_a(true);
        this.func_77625_d(64);
        this.func_77637_a(null);
    }

    public Icon func_77617_a(int par1) {
        return Block.field_72065_as.func_71851_a(0);
    }

    public boolean func_77648_a(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int meta, float hitX, float hitY, float hitZ) {
        if (super.func_77648_a(stack, player, world, x, y, z, meta, hitX, hitY, hitZ) && !world.field_72995_K) {
            TileEntityMobSpawner tileEntity = (TileEntityMobSpawner)world.func_72796_p(this.lastX, this.lastY, this.lastZ);
            if (tileEntity != null) {
                this.setDefaultTag(stack);
                String name = ServerEventHandler.INSTANCE.idToName.get(stack.func_77960_j());
                if (name != null) {
                    tileEntity.func_98049_a().func_98272_a(name);
                }
            }
            return true;
        }
        return false;
    }

    @SideOnly(value=Side.CLIENT)
    public void func_77624_a(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
        EntityLiving entity;
        ServerEventHandler.INSTANCE.loadEntityIDs(player.field_70170_p);
        this.setDefaultTag(stack);
        int meta = stack.func_77960_j();
        if (meta == 0) {
            meta = MobSpawnerItem.getIDFromClass(EntityPig.class);
        }
        if ((entity = MobSpawnerItem.getEntity(meta, player.field_70170_p)) == null) {
            return;
        }
        list.add("\u00a7" + (entity instanceof IMob ? "4" : "3") + ServerEventHandler.INSTANCE.idToName.get(meta));
    }

    public static EntityLiving getEntity(int id, World world) {
        EntityLiving entityliving = entityHashMap.get(id);
        if (entityliving == null) {
            try {
                Class class1 = (Class)EntityList.field_75623_d.get(id);
                if (class1 != null && EntityLiving.class.isAssignableFrom(class1)) {
                    entityliving = (EntityLiving)class1.getConstructor(World.class).newInstance(world);
                }
            }
            catch (Throwable t) {
                t.printStackTrace();
            }
            entityHashMap.put(id, entityliving);
        }
        return entityliving;
    }

    private void setDefaultTag(ItemStack stack) {
        if (!ServerEventHandler.INSTANCE.idToName.containsKey(stack.func_77960_j())) {
            stack.func_77964_b(90);
        }
    }

    public String func_77635_s() {
        return StatCollector.func_74838_a((String)"tile.mobSpawner.name");
    }

    public String func_77653_i(ItemStack stack) {
        return StatCollector.func_74838_a((String)"tile.mobSpawner.name");
    }

    public String func_77628_j(ItemStack stack) {
        return StatCollector.func_74838_a((String)"tile.mobSpawner.name");
    }

    public static Integer getIDFromClass(Class<EntityPig> value) {
        for (Map.Entry<Integer, EntityLiving> entry : entityHashMap.entrySet()) {
            if (!Objects.equals(value, entry.getValue())) continue;
            return entry.getKey();
        }
        return null;
    }

    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        if (!world.func_72832_d(x, y, z, Block.field_72065_as.field_71990_ca, metadata, 3)) {
            return false;
        }
        if (world.func_72798_a(x, y, z) == Block.field_72065_as.field_71990_ca) {
            Block.field_71973_m[Block.field_72065_as.field_71990_ca].func_71860_a(world, x, y, z, (EntityLivingBase)player, stack);
            Block.field_71973_m[Block.field_72065_as.field_71990_ca].func_85105_g(world, x, y, z, metadata);
            this.lastX = x;
            this.lastY = y;
            this.lastZ = z;
        }
        return true;
    }
}

