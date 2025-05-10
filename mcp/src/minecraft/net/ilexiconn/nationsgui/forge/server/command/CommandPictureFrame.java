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

public class CommandPictureFrame extends CommandBase
{
    public static final int[] offsetX = new int[] {1, 0, -1, 0};
    public static final int[] offsetZ = new int[] {0, -1, 0, 1};

    public String getCommandName()
    {
        return "pictureframe";
    }

    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "/pictureframe {width} {height} {refresh} {url}";
    }

    public void processCommand(ICommandSender icommandsender, String[] astring)
    {
        EntityPlayerMP player = getCommandSenderAsPlayer(icommandsender);

        if (astring.length != 4)
        {
            throw new CommandException("Invalid Command", new Object[0]);
        }
        else
        {
            int width;
            int height;
            boolean refresh;
            String url;

            try
            {
                width = Integer.parseInt(astring[0]);
                height = Integer.parseInt(astring[1]);
                refresh = Boolean.parseBoolean(astring[2]);
                URI world = new URI(astring[3]);

                if (!world.getHost().endsWith("nationsglory.fr") && !player.username.equalsIgnoreCase("tymothi") && !player.username.equalsIgnoreCase("ibalix") && !player.username.equalsIgnoreCase("wascar"))
                {
                    throw new CommandException("Unauthorized URL", new Object[0]);
                }

                url = world.toString();
            }
            catch (URISyntaxException var14)
            {
                System.out.println(var14.getMessage());
                throw new CommandException("Invalid Command", new Object[0]);
            }

            World world1 = player.getEntityWorld();
            MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(world1, player, false);

            if (mop != null && mop.typeOfHit == EnumMovingObjectType.TILE)
            {
                if (mop.sideHit != 0 && mop.sideHit != 1)
                {
                    int i1 = Direction.facingToDirection[mop.sideHit];
                    int offsetX = offsetX[i1];
                    int offsetZ = offsetZ[i1];
                    EntityPictureFrame entityhanging = new EntityPictureFrame(world1, mop.blockX + (width - 1) / 2 * offsetX, mop.blockY + -height / 2, mop.blockZ - (width - 1) / 2 * offsetZ, i1, width * 16, height * 16, refresh, url);

                    if (world1.isRemote || !player.canPlayerEdit(mop.blockX, mop.blockY, mop.blockZ, mop.sideHit, (ItemStack)null) || !entityhanging.onValidSurface())
                    {
                        throw new CommandException("Unable to pose the picture.", new Object[0]);
                    }

                    world1.spawnEntityInWorld(entityhanging);
                }
            }
            else
            {
                throw new CommandException("No block pointed", new Object[0]);
            }
        }
    }

    public int compareTo(Object o)
    {
        return 0;
    }

    protected MovingObjectPosition getMovingObjectPositionFromPlayer(World par1World, EntityPlayer par2EntityPlayer, boolean par3)
    {
        float f = 1.0F;
        float f1 = par2EntityPlayer.prevRotationPitch + (par2EntityPlayer.rotationPitch - par2EntityPlayer.prevRotationPitch) * f;
        float f2 = par2EntityPlayer.prevRotationYaw + (par2EntityPlayer.rotationYaw - par2EntityPlayer.prevRotationYaw) * f;
        double d0 = par2EntityPlayer.prevPosX + (par2EntityPlayer.posX - par2EntityPlayer.prevPosX) * (double)f;
        double d1 = par2EntityPlayer.prevPosY + (par2EntityPlayer.posY - par2EntityPlayer.prevPosY) * (double)f + (double)(par1World.isRemote ? par2EntityPlayer.getEyeHeight() - par2EntityPlayer.getDefaultEyeHeight() : par2EntityPlayer.getEyeHeight());
        double d2 = par2EntityPlayer.prevPosZ + (par2EntityPlayer.posZ - par2EntityPlayer.prevPosZ) * (double)f;
        Vec3 vec3 = par1World.getWorldVec3Pool().getVecFromPool(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = 5.0D;

        if (par2EntityPlayer instanceof EntityPlayerMP)
        {
            d3 = ((EntityPlayerMP)par2EntityPlayer).theItemInWorldManager.getBlockReachDistance();
        }

        Vec3 vec31 = vec3.addVector((double)f7 * d3, (double)f6 * d3, (double)f8 * d3);
        return par1World.rayTraceBlocks_do_do(vec3, vec31, par3, !par3);
    }
}
