package net.ilexiconn.nationsgui.forge.server.entity;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.render.texture.DownloadableTexture;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureObject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityPictureFrame extends Entity implements IEntityAdditionalSpawnData
{
    private String url;
    private int pictureWidth;
    private int pictureHeigth;
    private static HashMap<String, Long> lastUpdate = new HashMap();
    private int xPosition;
    private int yPosition;
    private int zPosition;
    private int hangingDirection;
    private boolean refresh;
    private int tickCounter1;
    private boolean allowRender;
    private DownloadableTexture downloadImageSkin;

    public EntityPictureFrame(World world)
    {
        super(world);
        this.yOffset = 0.0F;
        this.setSize(0.5F, 0.5F);

        if (world.isRemote)
        {
            this.allowRender = ClientProxy.clientConfig.displayPictureFrame;
        }
    }

    protected void entityInit() {}

    /**
     * Checks if the entity is in range to render by using the past in distance and comparing it to its average edge
     * length * 64 * renderDistanceWeight Args: distance
     */
    public boolean isInRangeToRenderDist(double d)
    {
        return d < 16384.0D;
    }

    public EntityPictureFrame(World world, int posX, int posY, int posZ, int direction, int width, int height, boolean refresh, String url)
    {
        this(world);
        this.xPosition = posX;
        this.yPosition = posY;
        this.zPosition = posZ;
        this.url = url;
        this.refresh = refresh;
        this.pictureWidth = width;
        this.pictureHeigth = height;
        this.setDirection(direction);
        NationsGUI.PROXY.setupSkinPictureFrame(this);
        this.initLastUpdate();
    }

    private void initLastUpdate()
    {
        if (!lastUpdate.containsKey(this.url))
        {
            lastUpdate.put(this.url, Long.valueOf(System.currentTimeMillis()));
        }
    }

    @SideOnly(Side.CLIENT)
    public void setupSkin()
    {
        this.downloadImageSkin = this.getDownloadImage(this.getLocationSkin(), this.url, (ResourceLocation)null, new ImageBufferDownload());
    }

    @SideOnly(Side.CLIENT)
    public ResourceLocation getLocationSkin()
    {
        return new ResourceLocation("nationsgui", "pictureframe/" + this.url);
    }

    @SideOnly(Side.CLIENT)
    public DownloadableTexture getDownloadImageSkin()
    {
        return this.downloadImageSkin;
    }

    @SideOnly(Side.CLIENT)
    private DownloadableTexture getDownloadImage(ResourceLocation par0ResourceLocation, String par1Str, ResourceLocation par2ResourceLocation, IImageBuffer par3IImageBuffer)
    {
        TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
        Object object = texturemanager.getTexture(par0ResourceLocation);

        if (object == null)
        {
            object = new DownloadableTexture(par1Str.replaceAll("PLAYER", Minecraft.getMinecraft().thePlayer.username), par2ResourceLocation, par3IImageBuffer);
            texturemanager.loadTexture(par0ResourceLocation, (TextureObject)object);
        }

        return (DownloadableTexture)object;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setInteger("width", this.pictureWidth);
        par1NBTTagCompound.setInteger("height", this.pictureHeigth);
        par1NBTTagCompound.setString("url", this.url);
        par1NBTTagCompound.setByte("Direction", (byte)this.hangingDirection);
        par1NBTTagCompound.setInteger("TileX", this.xPosition);
        par1NBTTagCompound.setInteger("TileY", this.yPosition);
        par1NBTTagCompound.setInteger("TileZ", this.zPosition);
        par1NBTTagCompound.setBoolean("refresh", this.refresh);

        switch (this.hangingDirection)
        {
            case 0:
                par1NBTTagCompound.setByte("Dir", (byte)2);
                break;

            case 1:
                par1NBTTagCompound.setByte("Dir", (byte)1);
                break;

            case 2:
                par1NBTTagCompound.setByte("Dir", (byte)0);
                break;

            case 3:
                par1NBTTagCompound.setByte("Dir", (byte)3);
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        this.pictureWidth = par1NBTTagCompound.getInteger("width");
        this.pictureHeigth = par1NBTTagCompound.getInteger("height");
        this.url = par1NBTTagCompound.getString("url");

        if (par1NBTTagCompound.hasKey("Direction"))
        {
            this.hangingDirection = par1NBTTagCompound.getByte("Direction");
        }
        else
        {
            switch (par1NBTTagCompound.getByte("Dir"))
            {
                case 0:
                    this.hangingDirection = 2;
                    break;

                case 1:
                    this.hangingDirection = 1;
                    break;

                case 2:
                    this.hangingDirection = 0;
                    break;

                case 3:
                    this.hangingDirection = 3;
            }
        }

        this.xPosition = par1NBTTagCompound.getInteger("TileX");
        this.yPosition = par1NBTTagCompound.getInteger("TileY");
        this.zPosition = par1NBTTagCompound.getInteger("TileZ");
        this.refresh = par1NBTTagCompound.getBoolean("refresh");
        this.setDirection(this.hangingDirection);
    }

    public int getWidthPixels()
    {
        return this.pictureWidth;
    }

    public int getHeightPixels()
    {
        return this.pictureHeigth;
    }

    public void onBroken(Entity entity) {}

    public String getUrl()
    {
        return this.url;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.tickCounter1++ == 100 && !this.worldObj.isRemote)
        {
            this.tickCounter1 = 0;

            if (!this.isDead && !this.onValidSurface())
            {
                this.setDead();
                this.onBroken((Entity)null);
            }
        }

        if (this.worldObj.isRemote && (ClientProxy.clientConfig.displayPictureFrame && !this.allowRender || ClientProxy.clientConfig.displayPictureFrame && this.refresh && this.canUpdateTexture()))
        {
            if (this.downloadImageSkin == null)
            {
                this.setupSkin();
            }
            else
            {
                this.downloadImageSkin.reloadTexture();
            }

            lastUpdate.remove(this.url);
            lastUpdate.put(this.url, Long.valueOf(System.currentTimeMillis()));
            this.allowRender = true;
        }
    }

    private boolean canUpdateTexture()
    {
        long currentTime = System.currentTimeMillis();
        return currentTime - ((Long)lastUpdate.get(this.url)).longValue() > 60000L;
    }

    public void writeSpawnData(ByteArrayDataOutput data)
    {
        data.writeInt(this.pictureWidth);
        data.writeInt(this.pictureHeigth);
        data.writeUTF(this.url);
        data.writeBoolean(this.refresh);
    }

    public void readSpawnData(ByteArrayDataInput data)
    {
        this.pictureWidth = data.readInt();
        this.pictureHeigth = data.readInt();
        this.url = data.readUTF();
        this.refresh = data.readBoolean();
        NationsGUI.PROXY.setupSkinPictureFrame(this);
        this.initLastUpdate();
    }

    public void setDirection(int par1)
    {
        this.hangingDirection = par1;
        this.prevRotationYaw = this.rotationYaw = (float)(par1 * 90);
        float f = (float)this.getWidthPixels();
        float f1 = (float)this.getHeightPixels();
        float f2 = (float)this.getWidthPixels();

        if (par1 != 2 && par1 != 0)
        {
            f = 0.5F;
        }
        else
        {
            f2 = 0.5F;
            this.rotationYaw = this.prevRotationYaw = (float)(Direction.rotateOpposite[par1] * 90);
        }

        f /= 32.0F;
        f1 /= 32.0F;
        f2 /= 32.0F;
        float f3 = (float)this.xPosition + 0.5F;
        float f4 = (float)this.yPosition + 0.5F;
        float f5 = (float)this.zPosition + 0.5F;
        float f6 = 0.5625F;

        if (par1 == 2)
        {
            f5 -= f6;
        }

        if (par1 == 1)
        {
            f3 -= f6;
        }

        if (par1 == 0)
        {
            f5 += f6;
        }

        if (par1 == 3)
        {
            f3 += f6;
        }

        if (par1 == 2)
        {
            f3 -= this.func_70517_b(this.getWidthPixels());
        }

        if (par1 == 1)
        {
            f5 += this.func_70517_b(this.getWidthPixels());
        }

        if (par1 == 0)
        {
            f3 += this.func_70517_b(this.getWidthPixels());
        }

        if (par1 == 3)
        {
            f5 -= this.func_70517_b(this.getWidthPixels());
        }

        f4 += this.func_70517_b(this.getHeightPixels());
        this.setPosition((double)f3, (double)f4, (double)f5);
        float f7 = -0.03125F;
        this.boundingBox.setBounds((double)(f3 - f - f7), (double)(f4 - f1 - f7), (double)(f5 - f2 - f7), (double)(f3 + f + f7), (double)(f4 + f1 + f7), (double)(f5 + f2 + f7));
    }

    private float func_70517_b(int par1)
    {
        return par1 == 32 ? 0.5F : (par1 == 64 ? 0.5F : 0.0F);
    }

    protected boolean shouldSetPosAfterLoading()
    {
        return false;
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith()
    {
        return true;
    }

    /**
     * Called when a player attacks an entity. If this returns true the attack will not happen.
     */
    public boolean hitByEntity(Entity par1Entity)
    {
        return par1Entity instanceof EntityPlayer ? this.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)par1Entity), 0.0F) : false;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
    {
        if (this.isEntityInvulnerable())
        {
            return false;
        }
        else
        {
            if (par1DamageSource.getSourceOfDamage() != null && par1DamageSource.getSourceOfDamage() instanceof EntityPlayer)
            {
                EntityPlayer entityPlayer = (EntityPlayer)par1DamageSource.getSourceOfDamage();

                if (!this.isDead && !this.worldObj.isRemote && MinecraftServer.getServer().getConfigurationManager().isPlayerOpped(entityPlayer.getDisplayName()))
                {
                    this.setDead();
                    this.setBeenAttacked();
                    this.onBroken(par1DamageSource.getSourceOfDamage());
                    return true;
                }
            }

            return false;
        }
    }

    /**
     * First layer of player interaction
     */
    public boolean interactFirst(EntityPlayer par1EntityPlayer)
    {
        if (!par1EntityPlayer.worldObj.isRemote && MinecraftServer.getServer().getConfigurationManager().isPlayerOpped(par1EntityPlayer.getDisplayName()))
        {
            par1EntityPlayer.addChatMessage("\u00a76Picture URL: \u00a7e" + this.url);
        }

        return super.interactFirst(par1EntityPlayer);
    }

    /**
     * Tries to moves the entity by the passed in displacement. Args: x, y, z
     */
    public void moveEntity(double par1, double par3, double par5)
    {
        if (!this.worldObj.isRemote && !this.isDead && par1 * par1 + par3 * par3 + par5 * par5 > 0.0D)
        {
            this.setDead();
            this.onBroken((Entity)null);
        }
    }

    /**
     * Adds to the current velocity of the entity. Args: x, y, z
     */
    public void addVelocity(double par1, double par3, double par5)
    {
        if (!this.worldObj.isRemote && !this.isDead && par1 * par1 + par3 * par3 + par5 * par5 > 0.0D)
        {
            this.setDead();
            this.onBroken((Entity)null);
        }
    }

    public boolean onValidSurface()
    {
        if (!this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty())
        {
            return false;
        }
        else
        {
            int i = Math.max(1, this.getWidthPixels() / 16);
            int j = Math.max(1, this.getHeightPixels() / 16);
            int k = this.xPosition;
            int i1 = this.zPosition;

            if (this.hangingDirection == 2)
            {
                k = MathHelper.floor_double(this.posX - (double)((float)this.getWidthPixels() / 32.0F));
            }

            if (this.hangingDirection == 1)
            {
                i1 = MathHelper.floor_double(this.posZ - (double)((float)this.getWidthPixels() / 32.0F));
            }

            if (this.hangingDirection == 0)
            {
                k = MathHelper.floor_double(this.posX - (double)((float)this.getWidthPixels() / 32.0F));
            }

            if (this.hangingDirection == 3)
            {
                i1 = MathHelper.floor_double(this.posZ - (double)((float)this.getWidthPixels() / 32.0F));
            }

            int l = MathHelper.floor_double(this.posY - (double)((float)this.getHeightPixels() / 32.0F));

            for (int list = 0; list < i; ++list)
            {
                for (int iterator = 0; iterator < j; ++iterator)
                {
                    Material entity;

                    if (this.hangingDirection != 2 && this.hangingDirection != 0)
                    {
                        entity = this.worldObj.getBlockMaterial(this.xPosition, l + iterator, i1 + list);
                    }
                    else
                    {
                        entity = this.worldObj.getBlockMaterial(k + list, l + iterator, this.zPosition);
                    }

                    if (!entity.isSolid())
                    {
                        return false;
                    }
                }
            }

            List var9 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox);
            Iterator var10 = var9.iterator();

            while (var10.hasNext())
            {
                Entity var11 = (Entity)var10.next();

                if (var11 instanceof EntityHanging)
                {
                    return false;
                }
            }

            return true;
        }
    }
}
