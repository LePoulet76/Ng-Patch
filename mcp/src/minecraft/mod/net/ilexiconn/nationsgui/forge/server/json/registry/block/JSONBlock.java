package net.ilexiconn.nationsgui.forge.server.json.registry.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class JSONBlock extends Block
{
    @SideOnly(Side.CLIENT)
    public Icon textureFront;
    @SideOnly(Side.CLIENT)
    public Icon textureBack;
    @SideOnly(Side.CLIENT)
    public Icon textureTop;
    @SideOnly(Side.CLIENT)
    public Icon textureBottom;
    @SideOnly(Side.CLIENT)
    public Icon textureSide;
    public String textureFrontString;
    public String textureBackString;
    public String textureTopString;
    public String textureBottomString;
    public String textureSideString;
    public boolean isTranslucent;
    public List<JSONDrop> drops;
    public Map<String, List<JSONParticle>> particles = new HashMap();

    public JSONBlock(int id, JSONMaterial material)
    {
        super(id, material.getMaterial());
        this.setTextureName("dirt");
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setStepSound(material.getStepSound());
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLivingBase, ItemStack itemStack)
    {
        int metadata = MathHelper.floor_double((double)(entityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (metadata == 0)
        {
            world.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }
        else if (metadata == 1)
        {
            world.setBlockMetadataWithNotify(x, y, z, 5, 2);
        }
        else if (metadata == 2)
        {
            world.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }
        else if (metadata == 3)
        {
            world.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return this.isTranslucent;
    }

    @SideOnly(Side.CLIENT)

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int side, int meta)
    {
        return side == 0 && this.textureBottom != null ? this.textureBottom : (side == 1 && this.textureTop != null ? this.textureTop : (side == meta && this.textureFront != null ? this.textureFront : ((side % 2 == 0 && side + 1 == meta || side % 2 == 1 && side - 1 == meta) && this.textureBack != null ? this.textureBack : this.textureSide)));
    }

    /**
     * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
     * block and l is the block's subtype/damage.
     */
    public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta)
    {
        if (this.drops != null)
        {
            Collections.shuffle(this.drops);
            Iterator var7 = this.drops.iterator();

            while (var7.hasNext())
            {
                JSONDrop drop = (JSONDrop)var7.next();

                if (drop.canDrop())
                {
                    drop.dropItem(player, world, x, y, z, this.blockID, this.drops.indexOf(drop));
                    return;
                }
            }
        }
        else
        {
            super.harvestBlock(world, player, x, y, z, meta);
        }
    }

    @SideOnly(Side.CLIENT)

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
        List particles = (List)this.particles.get("tick");

        if (particles != null)
        {
            Iterator var7 = particles.iterator();

            while (var7.hasNext())
            {
                JSONParticle particle = (JSONParticle)var7.next();
                particle.spawnParticle(world, x, y, z);
            }
        }
    }

    /**
     * Called right before the block is destroyed by a player.  Args: world, x, y, z, metaData
     */
    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta)
    {
        if (world.isRemote)
        {
            List particles = (List)this.particles.get("break");

            if (particles != null)
            {
                Iterator var7 = particles.iterator();

                while (var7.hasNext())
                {
                    JSONParticle particle = (JSONParticle)var7.next();
                    particle.spawnParticle(world, x, y, z);
                }
            }
        }
    }
}
