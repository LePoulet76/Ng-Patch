/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.ChunkCoordinates
 */
package net.ilexiconn.nationsgui.forge.server.block.entity;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.block.entity.BlockEntity;
import net.ilexiconn.nationsgui.forge.server.config.NBTConfig;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SetPlayingPacket;
import net.ilexiconn.nationsgui.forge.server.util.SoundStreamer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;

public class RadioBlockEntity
extends BlockEntity {
    public SoundStreamer streamer = null;
    public String source = "https://radio.nationsglory.fr/listen/ngradio/ngradio";
    public int volume = 10;
    public boolean looping = false;
    public boolean needsRedstone = false;
    public boolean playing = false;
    public boolean canOpen = true;
    public List<ChunkCoordinates> speakers = new ArrayList<ChunkCoordinates>();

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        compound.func_74778_a("Source", this.source);
        compound.func_74768_a("Volume", this.volume);
        compound.func_74757_a("IsLooping", this.looping);
        compound.func_74757_a("NeedsRedstone", this.needsRedstone);
        compound.func_74757_a("IsPlaying", this.playing);
        compound.func_74757_a("CanOpen", this.canOpen);
        NBTTagList list = new NBTTagList("Speakers");
        for (ChunkCoordinates coordinates : this.speakers) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.func_74768_a("X", coordinates.field_71574_a);
            tag.func_74768_a("Y", coordinates.field_71572_b);
            tag.func_74768_a("Z", coordinates.field_71573_c);
            list.func_74742_a((NBTBase)tag);
        }
        compound.func_74782_a("Speakers", (NBTBase)list);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        this.source = compound.func_74779_i("Source");
        if (this.source.contains("radionationsgloryserveur")) {
            this.source = "https://radio.nationsglory.fr/listen/ngradio/ngradio";
        } else if (this.source.contains("https://streaming.nationsglory.fr/NGRadio")) {
            this.source = "https://radio.nationsglory.fr/listen/ngradio/ngradio";
        } else if (!this.source.contains("static.nationsglory.fr") && !this.source.contains("https://radio.nationsglory.fr")) {
            this.source = "https://radio.nationsglory.fr/listen/ngradio/ngradio";
        }
        this.volume = compound.func_74762_e("Volume");
        this.looping = compound.func_74767_n("IsLooping");
        this.needsRedstone = compound.func_74767_n("NeedsRedstone");
        this.playing = compound.func_74767_n("IsPlaying");
        this.canOpen = compound.func_74767_n("CanOpen");
        this.speakers.clear();
        NBTTagList list = compound.func_74761_m("Speakers");
        for (int i = 0; i < list.func_74745_c(); ++i) {
            NBTTagCompound tag = (NBTTagCompound)list.func_74743_b(i);
            int x = tag.func_74762_e("X");
            int y = tag.func_74762_e("Y");
            int z = tag.func_74762_e("Z");
            this.speakers.add(new ChunkCoordinates(x, y, z));
        }
    }

    public String getSource() {
        String source = this.source;
        if (!this.source.contains("static.nationsglory.fr") && !this.source.contains("https://radio.nationsglory.fr")) {
            this.source = "https://radio.nationsglory.fr/listen/ngradio/ngradio";
        }
        return source;
    }

    @Override
    public void onUpdate() {
        if (this.field_70331_k.field_72995_K) {
            if (!this.playing && this.streamer != null && this.streamer.isPlaying()) {
                this.streamer.forceClose();
                this.streamer = null;
            } else if (this.playing && this.streamer == null) {
                this.streamer = new SoundStreamer(this.getSource());
                new Thread(this.streamer).start();
                this.streamer.setVolume((float)this.volume / 10.0f);
            } else if (this.streamer != null && !this.streamer.getSource().equals(this.getSource())) {
                this.streamer.forceClose();
                this.streamer = new SoundStreamer(this.getSource());
                new Thread(this.streamer).start();
                this.streamer.setVolume((float)this.volume / 10.0f);
            }
            if (this.streamer != null && this.streamer.audio == null) {
                this.playing = false;
                this.streamer = null;
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new SetPlayingPacket(this)));
            }
            if (this.streamer != null) {
                float distance;
                float volume = (float)this.volume / 10.0f;
                if (this.needsRedstone) {
                    volume = (float)this.field_70331_k.func_94572_D(this.field_70329_l, this.field_70330_m, this.field_70327_n) / 15.0f;
                }
                volume = (distance = this.getDistance()) > 10000.0f * volume ? 0.0f : Math.min(1.0f, 10000.0f / distance / 100.0f) * volume * volume;
                if (NBTConfig.CONFIG.getCompound().func_74764_b("RadioVolume")) {
                    this.streamer.setVolume(volume / 100.0f * NBTConfig.CONFIG.getCompound().func_74760_g("RadioVolume"));
                } else {
                    this.streamer.setVolume(volume / 100.0f * 1.0f);
                }
                this.streamer.setLooping(this.looping);
            }
        } else {
            ArrayList<ChunkCoordinates> remove = new ArrayList<ChunkCoordinates>();
            for (ChunkCoordinates coordinates : this.speakers) {
                if (this.field_70331_k.func_72798_a(coordinates.field_71574_a, coordinates.field_71572_b, coordinates.field_71573_c) == NationsGUI.SPEAKER.field_71990_ca) continue;
                remove.add(coordinates);
            }
            this.speakers.removeAll(remove);
        }
    }

    @SideOnly(value=Side.CLIENT)
    public float getDistance() {
        EntityClientPlayerMP player = Minecraft.func_71410_x().field_71439_g;
        float distance = (float)this.func_70318_a(player.field_70165_t, player.field_70163_u, player.field_70161_v);
        for (ChunkCoordinates coordinates : this.speakers) {
            distance = (float)Math.min((double)distance, Math.pow(player.func_70011_f((double)coordinates.field_71574_a, (double)coordinates.field_71572_b, (double)coordinates.field_71573_c), 2.0));
        }
        return distance;
    }

    public void func_70313_j() {
        if (this.field_70331_k.field_72995_K) {
            if (this.streamer != null) {
                this.streamer.forceClose();
                this.streamer = null;
            }
        } else {
            for (ChunkCoordinates coordinates : this.speakers) {
                this.field_70331_k.func_94578_a(coordinates.field_71574_a, coordinates.field_71572_b, coordinates.field_71573_c, true);
            }
        }
        super.func_70313_j();
    }

    @SideOnly(value=Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return RenderManager.field_85095_o ? TileEntity.INFINITE_EXTENT_AABB : super.getRenderBoundingBox();
    }
}

