package net.ilexiconn.nationsgui.forge.server.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import java.util.Random;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientKeyHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.server.block.entity.PortalBlockEntity;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IconFlipped;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

public class PortalBlock extends BlockDoor implements ITileEntityProvider
{
    public static HashMap<String, Long> lastTP = new HashMap();
    @SideOnly(Side.CLIENT)
    private Icon[] field_111044_a;
    @SideOnly(Side.CLIENT)
    private Icon[] field_111043_b;
    @SideOnly(Side.CLIENT)
    private Icon[] textureOff_a;
    private Icon[] textureOff_b;

    public PortalBlock()
    {
        super(835, Material.portal);
        this.setUnlocalizedName("island_portal");
        this.setLightValue(0.0F);
        this.setHardness(2.0F);
        this.setResistance(10.0F);
        this.setTextureName("nationsgui:island_portal");
        this.setBlockBounds(0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
     */
    public int getRenderBlockPass()
    {
        return 1;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        PortalBlockEntity tile = (PortalBlockEntity)((PortalBlockEntity)par1World.getBlockTileEntity(par2, par3, par4));

        if (tile != null && par5Entity.ridingEntity == null && par5Entity.riddenByEntity == null && par1World.isRemote && System.currentTimeMillis() - ClientEventHandler.joinTime > 10000L)
        {
            ClientProxy.lastCollidedWithPortalTime = Long.valueOf(System.currentTimeMillis());
            ClientProxy.lastCollidedWithPortalX = par2;
            ClientProxy.lastCollidedWithPortalY = par3;
            ClientProxy.lastCollidedWithPortalZ = par4;

            if (lastTP.containsKey(Minecraft.getMinecraft().thePlayer.username) && System.currentTimeMillis() - ((Long)lastTP.get(Minecraft.getMinecraft().thePlayer.username)).longValue() < 4000L)
            {
                ClientProxy.sendClientHotBarMessage(I18n.getString("nationsgui.portal.teleporting"), 3000L);
            }
            else
            {
                ClientProxy.sendClientHotBarMessage(I18n.getString("nationsgui.portal.teleport").replaceAll("<key>", Keyboard.getKeyName(ClientKeyHandler.KEY_PORTAL_TP.keyCode)), 300L);
            }
        }
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        PortalBlockEntity tile = (PortalBlockEntity)((PortalBlockEntity)par1World.getBlockTileEntity(par2, par3, par4));

        if (tile.owner != null && par5EntityPlayer.getDisplayName().equalsIgnoreCase(tile.owner))
        {
            par5EntityPlayer.addChatMessage("\u00a76Code : \u00a7e" + tile.code);
        }

        return false;
    }

    /**
     * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
     */
    public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        PortalBlockEntity tile = (PortalBlockEntity)((PortalBlockEntity)par1IBlockAccess.getBlockTileEntity(par2, par3, par4));

        if (tile != null && par5 != 1 && par5 != 0)
        {
            int i1 = this.getFullMetadata(par1IBlockAccess, par2, par3, par4);
            int j1 = i1 & 3;
            boolean flag = (i1 & 4) != 0;
            boolean flag1 = false;
            boolean flag2 = (i1 & 8) != 0;

            if (flag)
            {
                if (j1 == 0 && par5 == 2)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 1 && par5 == 5)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 2 && par5 == 3)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 3 && par5 == 4)
                {
                    flag1 = !flag1;
                }
            }
            else
            {
                if (j1 == 0 && par5 == 5)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 1 && par5 == 3)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 2 && par5 == 4)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 3 && par5 == 2)
                {
                    flag1 = !flag1;
                }

                if ((i1 & 16) != 0)
                {
                    flag1 = !flag1;
                }
            }

            return flag2 ? (tile.active ? this.field_111044_a[flag1 ? 1 : 0] : this.textureOff_a[flag1 ? 1 : 0]) : (tile.active ? this.field_111043_b[flag1 ? 1 : 0] : this.textureOff_b[flag1 ? 1 : 0]);
        }
        else
        {
            return tile != null ? (tile.active ? this.field_111043_b[0] : this.textureOff_b[0]) : this.field_111043_b[0];
        }
    }

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.field_111044_a = new Icon[2];
        this.field_111043_b = new Icon[2];
        this.textureOff_a = new Icon[2];
        this.textureOff_b = new Icon[2];
        this.field_111044_a[0] = par1IconRegister.registerIcon("nationsgui:island_portal");
        this.field_111043_b[0] = par1IconRegister.registerIcon("nationsgui:island_portal");
        this.textureOff_a[0] = par1IconRegister.registerIcon("nationsgui:island_portal_off");
        this.textureOff_b[0] = par1IconRegister.registerIcon("nationsgui:island_portal_off");
        this.field_111044_a[1] = new IconFlipped(this.field_111044_a[0], true, false);
        this.field_111043_b[1] = new IconFlipped(this.field_111043_b[0], true, false);
        this.textureOff_a[1] = new IconFlipped(this.textureOff_a[0], true, false);
        this.textureOff_b[1] = new IconFlipped(this.textureOff_b[0], true, false);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int par1, int par2)
    {
        return this.field_111043_b[0];
    }

    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    public int idPicked(World par1World, int par2, int par3, int par4)
    {
        return 4755;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return 0;
    }

    @SideOnly(Side.CLIENT)

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        PortalBlockEntity tile = (PortalBlockEntity)((PortalBlockEntity)par1World.getBlockTileEntity(par2, par3, par4));

        if (tile != null && tile.active)
        {
            if (par5Random.nextInt(100) == 0)
            {
                par1World.playSound((double)par2 + 0.5D, (double)par3 + 0.5D, (double)par4 + 0.5D, "portal.portal", 0.5F, par5Random.nextFloat() * 0.4F + 0.8F, false);
            }

            for (int l = 0; l < 4; ++l)
            {
                double d0 = (double)((float)par2 + par5Random.nextFloat());
                double d1 = (double)((float)par3 + par5Random.nextFloat());
                double d2 = (double)((float)par4 + par5Random.nextFloat());
                double d3 = 0.0D;
                double d4 = 0.0D;
                double d5 = 0.0D;
                int i1 = par5Random.nextInt(2) * 2 - 1;
                d3 = ((double)par5Random.nextFloat() - 0.5D) * 0.5D;
                d4 = ((double)par5Random.nextFloat() - 0.5D) * 0.5D;
                d5 = ((double)par5Random.nextFloat() - 0.5D) * 0.5D;

                if (par1World.getBlockId(par2 - 1, par3, par4) != this.blockID && par1World.getBlockId(par2 + 1, par3, par4) != this.blockID)
                {
                    d0 = (double)par2 + 0.5D + 0.25D * (double)i1;
                    d3 = (double)(par5Random.nextFloat() * 2.0F * (float)i1);
                }
                else
                {
                    d2 = (double)par4 + 0.5D + 0.25D * (double)i1;
                    d5 = (double)(par5Random.nextFloat() * 2.0F * (float)i1);
                }

                par1World.spawnParticle("portal", d0, d1, d2, d3, d4, d5);
            }
        }
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World world)
    {
        return new PortalBlockEntity();
    }

    /**
     * Called on server worlds only when the block has been replaced by a different block ID, or the same block with a
     * different metadata value, but before the new metadata value is set. Args: World, x, y, z, old block ID, old
     * metadata
     */
    public void breakBlock(World var1, int var2, int var3, int var4, int var5, int var6)
    {
        super.breakBlock(var1, var2, var3, var4, var5, var6);
        var1.removeBlockTileEntity(var2, var3, var4);
    }
}
