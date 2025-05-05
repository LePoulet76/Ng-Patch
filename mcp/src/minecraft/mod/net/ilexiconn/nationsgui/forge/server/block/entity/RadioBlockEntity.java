package net.ilexiconn.nationsgui.forge.server.block.entity;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.config.NBTConfig;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SetPlayingPacket;
import net.ilexiconn.nationsgui.forge.server.util.SoundStreamer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;

public class RadioBlockEntity extends BlockEntity
{
    public SoundStreamer streamer = null;
    public String source = "https://radio.nationsglory.fr/listen/ngradio/ngradio";
    public int volume = 10;
    public boolean looping = false;
    public boolean needsRedstone = false;
    public boolean playing = false;
    public boolean canOpen = true;
    public List<ChunkCoordinates> speakers = new ArrayList();

    public void saveNBTData(NBTTagCompound compound)
    {
        compound.setString("Source", this.source);
        compound.setInteger("Volume", this.volume);
        compound.setBoolean("IsLooping", this.looping);
        compound.setBoolean("NeedsRedstone", this.needsRedstone);
        compound.setBoolean("IsPlaying", this.playing);
        compound.setBoolean("CanOpen", this.canOpen);
        NBTTagList list = new NBTTagList("Speakers");
        Iterator var3 = this.speakers.iterator();

        while (var3.hasNext())
        {
            ChunkCoordinates coordinates = (ChunkCoordinates)var3.next();
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("X", coordinates.posX);
            tag.setInteger("Y", coordinates.posY);
            tag.setInteger("Z", coordinates.posZ);
            list.appendTag(tag);
        }

        compound.setTag("Speakers", list);
    }

    public void loadNBTData(NBTTagCompound compound)
    {
        this.source = compound.getString("Source");

        if (this.source.contains("radionationsgloryserveur"))
        {
            this.source = "https://radio.nationsglory.fr/listen/ngradio/ngradio";
        }
        else if (this.source.contains("https://streaming.nationsglory.fr/NGRadio"))
        {
            this.source = "https://radio.nationsglory.fr/listen/ngradio/ngradio";
        }
        else if (!this.source.contains("static.nationsglory.fr") && !this.source.contains("https://radio.nationsglory.fr"))
        {
            this.source = "https://radio.nationsglory.fr/listen/ngradio/ngradio";
        }

        this.volume = compound.getInteger("Volume");
        this.looping = compound.getBoolean("IsLooping");
        this.needsRedstone = compound.getBoolean("NeedsRedstone");
        this.playing = compound.getBoolean("IsPlaying");
        this.canOpen = compound.getBoolean("CanOpen");
        this.speakers.clear();
        NBTTagList list = compound.getTagList("Speakers");

        for (int i = 0; i < list.tagCount(); ++i)
        {
            NBTTagCompound tag = (NBTTagCompound)list.tagAt(i);
            int x = tag.getInteger("X");
            int y = tag.getInteger("Y");
            int z = tag.getInteger("Z");
            this.speakers.add(new ChunkCoordinates(x, y, z));
        }
    }

    public String getSource()
    {
        String source = this.source;

        if (!this.source.contains("static.nationsglory.fr") && !this.source.contains("https://radio.nationsglory.fr"))
        {
            this.source = "https://radio.nationsglory.fr/listen/ngradio/ngradio";
        }

        return source;
    }

    public void onUpdate()
    {
        if (this.worldObj.isRemote)
        {
            if (!this.playing && this.streamer != null && this.streamer.isPlaying())
            {
                this.streamer.forceClose();
                this.streamer = null;
            }
            else if (this.playing && this.streamer == null)
            {
                (new Thread(this.streamer = new SoundStreamer(this.getSource()))).start();
                this.streamer.setVolume((float)this.volume / 10.0F);
            }
            else if (this.streamer != null && !this.streamer.getSource().equals(this.getSource()))
            {
                this.streamer.forceClose();
                (new Thread(this.streamer = new SoundStreamer(this.getSource()))).start();
                this.streamer.setVolume((float)this.volume / 10.0F);
            }

            if (this.streamer != null && this.streamer.audio == null)
            {
                this.playing = false;
                this.streamer = null;
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new SetPlayingPacket(this)));
            }

            if (this.streamer != null)
            {
                float remove = (float)this.volume / 10.0F;

                if (this.needsRedstone)
                {
                    remove = (float)this.worldObj.getStrongestIndirectPower(this.xCoord, this.yCoord, this.zCoord) / 15.0F;
                }

                float distance = this.getDistance();

                if (distance > 10000.0F * remove)
                {
                    remove = 0.0F;
                }
                else
                {
                    remove = Math.min(1.0F, 10000.0F / distance / 100.0F) * remove * remove;
                }

                if (NBTConfig.CONFIG.getCompound().hasKey("RadioVolume"))
                {
                    this.streamer.setVolume(remove / 100.0F * NBTConfig.CONFIG.getCompound().getFloat("RadioVolume"));
                }
                else
                {
                    this.streamer.setVolume(remove / 100.0F * 1.0F);
                }

                this.streamer.setLooping(this.looping);
            }
        }
        else
        {
            ArrayList remove1 = new ArrayList();
            Iterator distance1 = this.speakers.iterator();

            while (distance1.hasNext())
            {
                ChunkCoordinates coordinates = (ChunkCoordinates)distance1.next();

                if (this.worldObj.getBlockId(coordinates.posX, coordinates.posY, coordinates.posZ) != NationsGUI.SPEAKER.blockID)
                {
                    remove1.add(coordinates);
                }
            }

            this.speakers.removeAll(remove1);
        }
    }

    @SideOnly(Side.CLIENT)
    public float getDistance()
    {
        EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
        float distance = (float)this.getDistanceFrom(player.posX, player.posY, player.posZ);
        ChunkCoordinates coordinates;

        for (Iterator var3 = this.speakers.iterator(); var3.hasNext(); distance = (float)Math.min((double)distance, Math.pow(player.getDistance((double)coordinates.posX, (double)coordinates.posY, (double)coordinates.posZ), 2.0D)))
        {
            coordinates = (ChunkCoordinates)var3.next();
        }

        return distance;
    }

    /**
     * invalidates a tile entity
     */
    public void invalidate()
    {
        if (this.worldObj.isRemote)
        {
            if (this.streamer != null)
            {
                this.streamer.forceClose();
                this.streamer = null;
            }
        }
        else
        {
            Iterator var1 = this.speakers.iterator();

            while (var1.hasNext())
            {
                ChunkCoordinates coordinates = (ChunkCoordinates)var1.next();
                this.worldObj.destroyBlock(coordinates.posX, coordinates.posY, coordinates.posZ, true);
            }
        }

        super.invalidate();
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
        return RenderManager.field_85095_o ? TileEntity.INFINITE_EXTENT_AABB : super.getRenderBoundingBox();
    }
}
