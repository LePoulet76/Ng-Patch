package net.ilexiconn.nationsgui.forge.server.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.server.ServerEventHandler;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUITransformer;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class MobSpawnerItem extends ItemBlock
{
    private int lastX;
    private int lastY;
    private int lastZ;
    private static Map<Integer, EntityLiving> entityHashMap = new HashMap();

    public MobSpawnerItem()
    {
        super(3335);

        try
        {
            Field e = ItemBlock.class.getDeclaredField(NationsGUITransformer.inDevelopment ? "blockID" : "blockID");
            e.setAccessible(true);
            e.set(this, Integer.valueOf(Block.mobSpawner.blockID));
        }
        catch (Exception var2)
        {
            throw new RuntimeException(var2);
        }

        this.setUnlocalizedName("mob_spawner");
        this.setTextureName("coal");
        this.setHasSubtypes(true);
        this.setMaxStackSize(64);
        this.setCreativeTab((CreativeTabs)null);
    }

    /**
     * Gets an icon index based on an item's damage value
     */
    public Icon getIconFromDamage(int par1)
    {
        return Block.mobSpawner.getBlockTextureFromSide(0);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int meta, float hitX, float hitY, float hitZ)
    {
        if (super.onItemUse(stack, player, world, x, y, z, meta, hitX, hitY, hitZ) && !world.isRemote)
        {
            TileEntityMobSpawner tileEntity = (TileEntityMobSpawner)world.getBlockTileEntity(this.lastX, this.lastY, this.lastZ);

            if (tileEntity != null)
            {
                this.setDefaultTag(stack);
                String name = (String)ServerEventHandler.INSTANCE.idToName.get(Integer.valueOf(stack.getItemDamage()));

                if (name != null)
                {
                    tileEntity.getSpawnerLogic().setMobID(name);
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    @SideOnly(Side.CLIENT)

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced)
    {
        ServerEventHandler.INSTANCE.loadEntityIDs(player.worldObj);
        this.setDefaultTag(stack);
        int meta = stack.getItemDamage();

        if (meta == 0)
        {
            meta = getIDFromClass(EntityPig.class).intValue();
        }

        EntityLiving entity = getEntity(meta, player.worldObj);

        if (entity != null)
        {
            list.add("\u00a7" + (entity instanceof IMob ? "4" : "3") + (String)ServerEventHandler.INSTANCE.idToName.get(Integer.valueOf(meta)));
        }
    }

    public static EntityLiving getEntity(int id, World world)
    {
        EntityLiving entityliving = (EntityLiving)entityHashMap.get(Integer.valueOf(id));

        if (entityliving == null)
        {
            try
            {
                Class t = (Class)EntityList.IDtoClassMapping.get(Integer.valueOf(id));

                if (t != null && EntityLiving.class.isAssignableFrom(t))
                {
                    entityliving = (EntityLiving)t.getConstructor(new Class[] {World.class}).newInstance(new Object[] {world});
                }
            }
            catch (Throwable var4)
            {
                var4.printStackTrace();
            }

            entityHashMap.put(Integer.valueOf(id), entityliving);
        }

        return entityliving;
    }

    private void setDefaultTag(ItemStack stack)
    {
        if (!ServerEventHandler.INSTANCE.idToName.containsKey(Integer.valueOf(stack.getItemDamage())))
        {
            stack.setItemDamage(90);
        }
    }

    public String getStatName()
    {
        return StatCollector.translateToLocal("tile.mobSpawner.name");
    }

    public String getItemStackDisplayName(ItemStack stack)
    {
        return StatCollector.translateToLocal("tile.mobSpawner.name");
    }

    public String getItemDisplayName(ItemStack stack)
    {
        return StatCollector.translateToLocal("tile.mobSpawner.name");
    }

    public static Integer getIDFromClass(Class<EntityPig> value)
    {
        Iterator var1 = entityHashMap.entrySet().iterator();
        Entry entry;

        do
        {
            if (!var1.hasNext())
            {
                return null;
            }

            entry = (Entry)var1.next();
        }
        while (!Objects.equals(value, entry.getValue()));

        return (Integer)entry.getKey();
    }

    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
    {
        if (!world.setBlock(x, y, z, Block.mobSpawner.blockID, metadata, 3))
        {
            return false;
        }
        else
        {
            if (world.getBlockId(x, y, z) == Block.mobSpawner.blockID)
            {
                Block.blocksList[Block.mobSpawner.blockID].onBlockPlacedBy(world, x, y, z, player, stack);
                Block.blocksList[Block.mobSpawner.blockID].onPostBlockPlaced(world, x, y, z, metadata);
                this.lastX = x;
                this.lastY = y;
                this.lastZ = z;
            }

            return true;
        }
    }
}
