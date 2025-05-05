package net.ilexiconn.nationsgui.forge.server.world;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;

public class EventSaveData$Event {

   private String name;
   private int dimensionId;
   private ChunkCoordinates eventCoordinates = null;
   private float rotationPitch;
   private float rotationYaw;


   public ChunkCoordinates getEventCoordinates() {
      return this.eventCoordinates;
   }

   public void setEventCoordinates(ChunkCoordinates eventCoordinates) {
      this.eventCoordinates = eventCoordinates;
   }

   public float getRotationYaw() {
      return this.rotationYaw;
   }

   public void setRotationYaw(float rotationYaw) {
      this.rotationYaw = rotationYaw;
   }

   public float getRotationPitch() {
      return this.rotationPitch;
   }

   public void setRotationPitch(float rotationPitch) {
      this.rotationPitch = rotationPitch;
   }

   public int getDimensionId() {
      return this.dimensionId;
   }

   public void setDimensionId(int dimensionId) {
      this.dimensionId = dimensionId;
   }

   public void readFromNBT(NBTTagCompound nbtTagCompound) {
      this.dimensionId = nbtTagCompound.func_74762_e("dimensionId");
      this.eventCoordinates = new ChunkCoordinates(nbtTagCompound.func_74762_e("posX"), nbtTagCompound.func_74762_e("posY"), nbtTagCompound.func_74762_e("posZ"));
      this.rotationYaw = nbtTagCompound.func_74760_g("rotationYaw");
      this.rotationPitch = nbtTagCompound.func_74760_g("rotationPitch");
      this.name = nbtTagCompound.func_74779_i("name");
   }

   public void writeToNBT(NBTTagCompound nbtTagCompound) {
      nbtTagCompound.func_74768_a("dimensionId", this.dimensionId);
      nbtTagCompound.func_74768_a("posX", this.eventCoordinates.field_71574_a);
      nbtTagCompound.func_74768_a("posY", this.eventCoordinates.field_71572_b);
      nbtTagCompound.func_74768_a("posZ", this.eventCoordinates.field_71573_c);
      nbtTagCompound.func_74776_a("rotationYaw", this.rotationYaw);
      nbtTagCompound.func_74776_a("rotationPitch", this.rotationPitch);
      nbtTagCompound.func_74778_a("name", this.name);
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }
}
