package net.ilexiconn.nationsgui.forge.server.block.entity;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;

public class RadioBlockEntity extends BlockEntity {

   public SoundStreamer streamer = null;
   public String source = "https://radio.nationsglory.fr/listen/ngradio/ngradio";
   public int volume = 10;
   public boolean looping = false;
   public boolean needsRedstone = false;
   public boolean playing = false;
   public boolean canOpen = true;
   public List<ChunkCoordinates> speakers = new ArrayList();


   public void saveNBTData(NBTTagCompound compound) {
      compound.func_74778_a("Source", this.source);
      compound.func_74768_a("Volume", this.volume);
      compound.func_74757_a("IsLooping", this.looping);
      compound.func_74757_a("NeedsRedstone", this.needsRedstone);
      compound.func_74757_a("IsPlaying", this.playing);
      compound.func_74757_a("CanOpen", this.canOpen);
      NBTTagList list = new NBTTagList("Speakers");
      Iterator var3 = this.speakers.iterator();

      while(var3.hasNext()) {
         ChunkCoordinates coordinates = (ChunkCoordinates)var3.next();
         NBTTagCompound tag = new NBTTagCompound();
         tag.func_74768_a("X", coordinates.field_71574_a);
         tag.func_74768_a("Y", coordinates.field_71572_b);
         tag.func_74768_a("Z", coordinates.field_71573_c);
         list.func_74742_a(tag);
      }

      compound.func_74782_a("Speakers", list);
   }

   public void loadNBTData(NBTTagCompound compound) {
      this.source = compound.func_74779_i("Source");
      if(this.source.contains("radionationsgloryserveur")) {
         this.source = "https://radio.nationsglory.fr/listen/ngradio/ngradio";
      } else if(this.source.contains("https://streaming.nationsglory.fr/NGRadio")) {
         this.source = "https://radio.nationsglory.fr/listen/ngradio/ngradio";
      } else if(!this.source.contains("static.nationsglory.fr") && !this.source.contains("https://radio.nationsglory.fr")) {
         this.source = "https://radio.nationsglory.fr/listen/ngradio/ngradio";
      }

      this.volume = compound.func_74762_e("Volume");
      this.looping = compound.func_74767_n("IsLooping");
      this.needsRedstone = compound.func_74767_n("NeedsRedstone");
      this.playing = compound.func_74767_n("IsPlaying");
      this.canOpen = compound.func_74767_n("CanOpen");
      this.speakers.clear();
      NBTTagList list = compound.func_74761_m("Speakers");

      for(int i = 0; i < list.func_74745_c(); ++i) {
         NBTTagCompound tag = (NBTTagCompound)list.func_74743_b(i);
         int x = tag.func_74762_e("X");
         int y = tag.func_74762_e("Y");
         int z = tag.func_74762_e("Z");
         this.speakers.add(new ChunkCoordinates(x, y, z));
      }

   }

   public String getSource() {
      String source = this.source;
      if(!this.source.contains("static.nationsglory.fr") && !this.source.contains("https://radio.nationsglory.fr")) {
         this.source = "https://radio.nationsglory.fr/listen/ngradio/ngradio";
      }

      return source;
   }

   public void onUpdate() {
      if(this.field_70331_k.field_72995_K) {
         if(!this.playing && this.streamer != null && this.streamer.isPlaying()) {
            this.streamer.forceClose();
            this.streamer = null;
         } else if(this.playing && this.streamer == null) {
            (new Thread(this.streamer = new SoundStreamer(this.getSource()))).start();
            this.streamer.setVolume((float)this.volume / 10.0F);
         } else if(this.streamer != null && !this.streamer.getSource().equals(this.getSource())) {
            this.streamer.forceClose();
            (new Thread(this.streamer = new SoundStreamer(this.getSource()))).start();
            this.streamer.setVolume((float)this.volume / 10.0F);
         }

         if(this.streamer != null && this.streamer.audio == null) {
            this.playing = false;
            this.streamer = null;
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new SetPlayingPacket(this)));
         }

         if(this.streamer != null) {
            float remove = (float)this.volume / 10.0F;
            if(this.needsRedstone) {
               remove = (float)this.field_70331_k.func_94572_D(this.field_70329_l, this.field_70330_m, this.field_70327_n) / 15.0F;
            }

            float distance = this.getDistance();
            if(distance > 10000.0F * remove) {
               remove = 0.0F;
            } else {
               remove = Math.min(1.0F, 10000.0F / distance / 100.0F) * remove * remove;
            }

            if(NBTConfig.CONFIG.getCompound().func_74764_b("RadioVolume")) {
               this.streamer.setVolume(remove / 100.0F * NBTConfig.CONFIG.getCompound().func_74760_g("RadioVolume"));
            } else {
               this.streamer.setVolume(remove / 100.0F * 1.0F);
            }

            this.streamer.setLooping(this.looping);
         }
      } else {
         ArrayList remove1 = new ArrayList();
         Iterator distance1 = this.speakers.iterator();

         while(distance1.hasNext()) {
            ChunkCoordinates coordinates = (ChunkCoordinates)distance1.next();
            if(this.field_70331_k.func_72798_a(coordinates.field_71574_a, coordinates.field_71572_b, coordinates.field_71573_c) != NationsGUI.SPEAKER.field_71990_ca) {
               remove1.add(coordinates);
            }
         }

         this.speakers.removeAll(remove1);
      }

   }

   @SideOnly(Side.CLIENT)
   public float getDistance() {
      EntityClientPlayerMP player = Minecraft.func_71410_x().field_71439_g;
      float distance = (float)this.func_70318_a(player.field_70165_t, player.field_70163_u, player.field_70161_v);

      ChunkCoordinates coordinates;
      for(Iterator var3 = this.speakers.iterator(); var3.hasNext(); distance = (float)Math.min((double)distance, Math.pow(player.func_70011_f((double)coordinates.field_71574_a, (double)coordinates.field_71572_b, (double)coordinates.field_71573_c), 2.0D))) {
         coordinates = (ChunkCoordinates)var3.next();
      }

      return distance;
   }

   public void func_70313_j() {
      if(this.field_70331_k.field_72995_K) {
         if(this.streamer != null) {
            this.streamer.forceClose();
            this.streamer = null;
         }
      } else {
         Iterator var1 = this.speakers.iterator();

         while(var1.hasNext()) {
            ChunkCoordinates coordinates = (ChunkCoordinates)var1.next();
            this.field_70331_k.func_94578_a(coordinates.field_71574_a, coordinates.field_71572_b, coordinates.field_71573_c, true);
         }
      }

      super.func_70313_j();
   }

   @SideOnly(Side.CLIENT)
   public AxisAlignedBB getRenderBoundingBox() {
      return RenderManager.field_85095_o?TileEntity.INFINITE_EXTENT_AABB:super.getRenderBoundingBox();
   }
}
