package fr.nationsglory.remoteitem.common.data;


public class PotionEffectData {

   private int id = 0;
   private int amplifier = 0;
   private int duration = 0;


   public int getId() {
      return this.id;
   }

   public int getAmplifier() {
      return this.amplifier - 1;
   }

   public int getDuration() {
      return this.duration * 20;
   }
}
