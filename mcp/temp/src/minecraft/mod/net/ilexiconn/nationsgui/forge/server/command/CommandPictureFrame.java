package net.ilexiconn.nationsgui.forge.server.command;

import java.net.URI;
import java.net.URISyntaxException;
import net.ilexiconn.nationsgui.forge.server.entity.EntityPictureFrame;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class CommandPictureFrame extends CommandBase {

   public static final int[] offsetX = new int[]{1, 0, -1, 0};
   public static final int[] offsetZ = new int[]{0, -1, 0, 1};


   public String func_71517_b() {
      return "pictureframe";
   }

   public String func_71518_a(ICommandSender icommandsender) {
      return "/pictureframe {width} {height} {refresh} {url}";
   }

   public void func_71515_b(ICommandSender icommandsender, String[] astring) {
      EntityPlayerMP player = func_71521_c(icommandsender);
      if(astring.length != 4) {
         throw new CommandException("Invalid Command", new Object[0]);
      } else {
         int width;
         int height;
         boolean refresh;
         String url;
         try {
            width = Integer.parseInt(astring[0]);
            height = Integer.parseInt(astring[1]);
            refresh = Boolean.parseBoolean(astring[2]);
            URI world = new URI(astring[3]);
            if(!world.getHost().endsWith("nationsglory.fr") && !player.field_71092_bJ.equalsIgnoreCase("tymothi") && !player.field_71092_bJ.equalsIgnoreCase("ibalix") && !player.field_71092_bJ.equalsIgnoreCase("wascar")) {
               throw new CommandException("Unauthorized URL", new Object[0]);
            }

            url = world.toString();
         } catch (URISyntaxException var14) {
            System.out.println(var14.getMessage());
            throw new CommandException("Invalid Command", new Object[0]);
         }

         World world1 = player.func_130014_f_();
         MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(world1, player, false);
         if(mop != null && mop.field_72313_a == EnumMovingObjectType.TILE) {
            if(mop.field_72310_e != 0 && mop.field_72310_e != 1) {
               int i1 = Direction.field_71579_d[mop.field_72310_e];
               int offsetX = offsetX[i1];
               int offsetZ = offsetZ[i1];
               EntityPictureFrame entityhanging = new EntityPictureFrame(world1, mop.field_72311_b + (width - 1) / 2 * offsetX, mop.field_72312_c + -height / 2, mop.field_72309_d - (width - 1) / 2 * offsetZ, i1, width * 16, height * 16, refresh, url);
               if(world1.field_72995_K || !player.func_82247_a(mop.field_72311_b, mop.field_72312_c, mop.field_72309_d, mop.field_72310_e, (ItemStack)null) || !entityhanging.onValidSurface()) {
                  throw new CommandException("Unable to pose the picture.", new Object[0]);
               }

               world1.func_72838_d(entityhanging);
            }

         } else {
            throw new CommandException("No block pointed", new Object[0]);
         }
      }
   }

   public int compareTo(Object o) {
      return 0;
   }

   protected MovingObjectPosition getMovingObjectPositionFromPlayer(World par1World, EntityPlayer par2EntityPlayer, boolean par3) {
      float f = 1.0F;
      float f1 = par2EntityPlayer.field_70127_C + (par2EntityPlayer.field_70125_A - par2EntityPlayer.field_70127_C) * f;
      float f2 = par2EntityPlayer.field_70126_B + (par2EntityPlayer.field_70177_z - par2EntityPlayer.field_70126_B) * f;
      double d0 = par2EntityPlayer.field_70169_q + (par2EntityPlayer.field_70165_t - par2EntityPlayer.field_70169_q) * (double)f;
      double d1 = par2EntityPlayer.field_70167_r + (par2EntityPlayer.field_70163_u - par2EntityPlayer.field_70167_r) * (double)f + (double)(par1World.field_72995_K?par2EntityPlayer.func_70047_e() - par2EntityPlayer.getDefaultEyeHeight():par2EntityPlayer.func_70047_e());
      double d2 = par2EntityPlayer.field_70166_s + (par2EntityPlayer.field_70161_v - par2EntityPlayer.field_70166_s) * (double)f;
      Vec3 vec3 = par1World.func_82732_R().func_72345_a(d0, d1, d2);
      float f3 = MathHelper.func_76134_b(-f2 * 0.017453292F - 3.1415927F);
      float f4 = MathHelper.func_76126_a(-f2 * 0.017453292F - 3.1415927F);
      float f5 = -MathHelper.func_76134_b(-f1 * 0.017453292F);
      float f6 = MathHelper.func_76126_a(-f1 * 0.017453292F);
      float f7 = f4 * f5;
      float f8 = f3 * f5;
      double d3 = 5.0D;
      if(par2EntityPlayer instanceof EntityPlayerMP) {
         d3 = ((EntityPlayerMP)par2EntityPlayer).field_71134_c.getBlockReachDistance();
      }

      Vec3 vec31 = vec3.func_72441_c((double)f7 * d3, (double)f6 * d3, (double)f8 * d3);
      return par1World.func_72831_a(vec3, vec31, par3, !par3);
   }

}
