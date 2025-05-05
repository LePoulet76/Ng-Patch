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

public class EntityPictureFrame extends Entity implements IEntityAdditionalSpawnData {

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


   public EntityPictureFrame(World world) {
      super(world);
      this.field_70129_M = 0.0F;
      this.func_70105_a(0.5F, 0.5F);
      if(world.field_72995_K) {
         this.allowRender = ClientProxy.clientConfig.displayPictureFrame;
      }

   }

   protected void func_70088_a() {}

   public boolean func_70112_a(double d) {
      return d < 16384.0D;
   }

   public EntityPictureFrame(World world, int posX, int posY, int posZ, int direction, int width, int height, boolean refresh, String url) {
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

   private void initLastUpdate() {
      if(!lastUpdate.containsKey(this.url)) {
         lastUpdate.put(this.url, Long.valueOf(System.currentTimeMillis()));
      }

   }

   @SideOnly(Side.CLIENT)
   public void setupSkin() {
      this.downloadImageSkin = this.getDownloadImage(this.getLocationSkin(), this.url, (ResourceLocation)null, new ImageBufferDownload());
   }

   @SideOnly(Side.CLIENT)
   public ResourceLocation getLocationSkin() {
      return new ResourceLocation("nationsgui", "pictureframe/" + this.url);
   }

   @SideOnly(Side.CLIENT)
   public DownloadableTexture getDownloadImageSkin() {
      return this.downloadImageSkin;
   }

   @SideOnly(Side.CLIENT)
   private DownloadableTexture getDownloadImage(ResourceLocation par0ResourceLocation, String par1Str, ResourceLocation par2ResourceLocation, IImageBuffer par3IImageBuffer) {
      TextureManager texturemanager = Minecraft.func_71410_x().func_110434_K();
      Object object = texturemanager.func_110581_b(par0ResourceLocation);
      if(object == null) {
         object = new DownloadableTexture(par1Str.replaceAll("PLAYER", Minecraft.func_71410_x().field_71439_g.field_71092_bJ), par2ResourceLocation, par3IImageBuffer);
         texturemanager.func_110579_a(par0ResourceLocation, (TextureObject)object);
      }

      return (DownloadableTexture)object;
   }

   public void func_70014_b(NBTTagCompound par1NBTTagCompound) {
      par1NBTTagCompound.func_74768_a("width", this.pictureWidth);
      par1NBTTagCompound.func_74768_a("height", this.pictureHeigth);
      par1NBTTagCompound.func_74778_a("url", this.url);
      par1NBTTagCompound.func_74774_a("Direction", (byte)this.hangingDirection);
      par1NBTTagCompound.func_74768_a("TileX", this.xPosition);
      par1NBTTagCompound.func_74768_a("TileY", this.yPosition);
      par1NBTTagCompound.func_74768_a("TileZ", this.zPosition);
      par1NBTTagCompound.func_74757_a("refresh", this.refresh);
      switch(this.hangingDirection) {
      case 0:
         par1NBTTagCompound.func_74774_a("Dir", (byte)2);
         break;
      case 1:
         par1NBTTagCompound.func_74774_a("Dir", (byte)1);
         break;
      case 2:
         par1NBTTagCompound.func_74774_a("Dir", (byte)0);
         break;
      case 3:
         par1NBTTagCompound.func_74774_a("Dir", (byte)3);
      }

   }

   public void func_70037_a(NBTTagCompound par1NBTTagCompound) {
      this.pictureWidth = par1NBTTagCompound.func_74762_e("width");
      this.pictureHeigth = par1NBTTagCompound.func_74762_e("height");
      this.url = par1NBTTagCompound.func_74779_i("url");
      if(par1NBTTagCompound.func_74764_b("Direction")) {
         this.hangingDirection = par1NBTTagCompound.func_74771_c("Direction");
      } else {
         switch(par1NBTTagCompound.func_74771_c("Dir")) {
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

      this.xPosition = par1NBTTagCompound.func_74762_e("TileX");
      this.yPosition = par1NBTTagCompound.func_74762_e("TileY");
      this.zPosition = par1NBTTagCompound.func_74762_e("TileZ");
      this.refresh = par1NBTTagCompound.func_74767_n("refresh");
      this.setDirection(this.hangingDirection);
   }

   public int getWidthPixels() {
      return this.pictureWidth;
   }

   public int getHeightPixels() {
      return this.pictureHeigth;
   }

   public void onBroken(Entity entity) {}

   public String getUrl() {
      return this.url;
   }

   public void func_70071_h_() {
      this.field_70169_q = this.field_70165_t;
      this.field_70167_r = this.field_70163_u;
      this.field_70166_s = this.field_70161_v;
      if(this.tickCounter1++ == 100 && !this.field_70170_p.field_72995_K) {
         this.tickCounter1 = 0;
         if(!this.field_70128_L && !this.onValidSurface()) {
            this.func_70106_y();
            this.onBroken((Entity)null);
         }
      }

      if(this.field_70170_p.field_72995_K && (ClientProxy.clientConfig.displayPictureFrame && !this.allowRender || ClientProxy.clientConfig.displayPictureFrame && this.refresh && this.canUpdateTexture())) {
         if(this.downloadImageSkin == null) {
            this.setupSkin();
         } else {
            this.downloadImageSkin.reloadTexture();
         }

         lastUpdate.remove(this.url);
         lastUpdate.put(this.url, Long.valueOf(System.currentTimeMillis()));
         this.allowRender = true;
      }

   }

   private boolean canUpdateTexture() {
      long currentTime = System.currentTimeMillis();
      return currentTime - ((Long)lastUpdate.get(this.url)).longValue() > 60000L;
   }

   public void writeSpawnData(ByteArrayDataOutput data) {
      data.writeInt(this.pictureWidth);
      data.writeInt(this.pictureHeigth);
      data.writeUTF(this.url);
      data.writeBoolean(this.refresh);
   }

   public void readSpawnData(ByteArrayDataInput data) {
      this.pictureWidth = data.readInt();
      this.pictureHeigth = data.readInt();
      this.url = data.readUTF();
      this.refresh = data.readBoolean();
      NationsGUI.PROXY.setupSkinPictureFrame(this);
      this.initLastUpdate();
   }

   public void setDirection(int par1) {
      this.hangingDirection = par1;
      this.field_70126_B = this.field_70177_z = (float)(par1 * 90);
      float f = (float)this.getWidthPixels();
      float f1 = (float)this.getHeightPixels();
      float f2 = (float)this.getWidthPixels();
      if(par1 != 2 && par1 != 0) {
         f = 0.5F;
      } else {
         f2 = 0.5F;
         this.field_70177_z = this.field_70126_B = (float)(Direction.field_71580_e[par1] * 90);
      }

      f /= 32.0F;
      f1 /= 32.0F;
      f2 /= 32.0F;
      float f3 = (float)this.xPosition + 0.5F;
      float f4 = (float)this.yPosition + 0.5F;
      float f5 = (float)this.zPosition + 0.5F;
      float f6 = 0.5625F;
      if(par1 == 2) {
         f5 -= f6;
      }

      if(par1 == 1) {
         f3 -= f6;
      }

      if(par1 == 0) {
         f5 += f6;
      }

      if(par1 == 3) {
         f3 += f6;
      }

      if(par1 == 2) {
         f3 -= this.func_70517_b(this.getWidthPixels());
      }

      if(par1 == 1) {
         f5 += this.func_70517_b(this.getWidthPixels());
      }

      if(par1 == 0) {
         f3 += this.func_70517_b(this.getWidthPixels());
      }

      if(par1 == 3) {
         f5 -= this.func_70517_b(this.getWidthPixels());
      }

      f4 += this.func_70517_b(this.getHeightPixels());
      this.func_70107_b((double)f3, (double)f4, (double)f5);
      float f7 = -0.03125F;
      this.field_70121_D.func_72324_b((double)(f3 - f - f7), (double)(f4 - f1 - f7), (double)(f5 - f2 - f7), (double)(f3 + f + f7), (double)(f4 + f1 + f7), (double)(f5 + f2 + f7));
   }

   private float func_70517_b(int par1) {
      return par1 == 32?0.5F:(par1 == 64?0.5F:0.0F);
   }

   protected boolean func_142008_O() {
      return false;
   }

   public boolean func_70067_L() {
      return true;
   }

   public boolean func_85031_j(Entity par1Entity) {
      return par1Entity instanceof EntityPlayer?this.func_70097_a(DamageSource.func_76365_a((EntityPlayer)par1Entity), 0.0F):false;
   }

   public boolean func_70097_a(DamageSource par1DamageSource, float par2) {
      if(this.func_85032_ar()) {
         return false;
      } else {
         if(par1DamageSource.func_76364_f() != null && par1DamageSource.func_76364_f() instanceof EntityPlayer) {
            EntityPlayer entityPlayer = (EntityPlayer)par1DamageSource.func_76364_f();
            if(!this.field_70128_L && !this.field_70170_p.field_72995_K && MinecraftServer.func_71276_C().func_71203_ab().func_72353_e(entityPlayer.getDisplayName())) {
               this.func_70106_y();
               this.func_70018_K();
               this.onBroken(par1DamageSource.func_76364_f());
               return true;
            }
         }

         return false;
      }
   }

   public boolean func_130002_c(EntityPlayer par1EntityPlayer) {
      if(!par1EntityPlayer.field_70170_p.field_72995_K && MinecraftServer.func_71276_C().func_71203_ab().func_72353_e(par1EntityPlayer.getDisplayName())) {
         par1EntityPlayer.func_71035_c("\u00a76Picture URL: \u00a7e" + this.url);
      }

      return super.func_130002_c(par1EntityPlayer);
   }

   public void func_70091_d(double par1, double par3, double par5) {
      if(!this.field_70170_p.field_72995_K && !this.field_70128_L && par1 * par1 + par3 * par3 + par5 * par5 > 0.0D) {
         this.func_70106_y();
         this.onBroken((Entity)null);
      }

   }

   public void func_70024_g(double par1, double par3, double par5) {
      if(!this.field_70170_p.field_72995_K && !this.field_70128_L && par1 * par1 + par3 * par3 + par5 * par5 > 0.0D) {
         this.func_70106_y();
         this.onBroken((Entity)null);
      }

   }

   public boolean onValidSurface() {
      if(!this.field_70170_p.func_72945_a(this, this.field_70121_D).isEmpty()) {
         return false;
      } else {
         int i = Math.max(1, this.getWidthPixels() / 16);
         int j = Math.max(1, this.getHeightPixels() / 16);
         int k = this.xPosition;
         int i1 = this.zPosition;
         if(this.hangingDirection == 2) {
            k = MathHelper.func_76128_c(this.field_70165_t - (double)((float)this.getWidthPixels() / 32.0F));
         }

         if(this.hangingDirection == 1) {
            i1 = MathHelper.func_76128_c(this.field_70161_v - (double)((float)this.getWidthPixels() / 32.0F));
         }

         if(this.hangingDirection == 0) {
            k = MathHelper.func_76128_c(this.field_70165_t - (double)((float)this.getWidthPixels() / 32.0F));
         }

         if(this.hangingDirection == 3) {
            i1 = MathHelper.func_76128_c(this.field_70161_v - (double)((float)this.getWidthPixels() / 32.0F));
         }

         int l = MathHelper.func_76128_c(this.field_70163_u - (double)((float)this.getHeightPixels() / 32.0F));

         for(int list = 0; list < i; ++list) {
            for(int iterator = 0; iterator < j; ++iterator) {
               Material entity;
               if(this.hangingDirection != 2 && this.hangingDirection != 0) {
                  entity = this.field_70170_p.func_72803_f(this.xPosition, l + iterator, i1 + list);
               } else {
                  entity = this.field_70170_p.func_72803_f(k + list, l + iterator, this.zPosition);
               }

               if(!entity.func_76220_a()) {
                  return false;
               }
            }
         }

         List var9 = this.field_70170_p.func_72839_b(this, this.field_70121_D);
         Iterator var10 = var9.iterator();

         while(var10.hasNext()) {
            Entity var11 = (Entity)var10.next();
            if(var11 instanceof EntityHanging) {
               return false;
            }
         }

         return true;
      }
   }

}
