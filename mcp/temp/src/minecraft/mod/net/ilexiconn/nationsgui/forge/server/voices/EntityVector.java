package net.ilexiconn.nationsgui.forge.server.voices;


public class EntityVector {

   public static final EntityVector NULL = new EntityVector(0, "", 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
   public double x;
   public double y;
   public double z;
   public double motX;
   public double motY;
   public double motZ;
   public String entityName;


   public EntityVector(int entityID, String name, double x, double y, double z, double motX, double motY, double motZ) {
      this.entityName = name;
      this.x = x;
      this.y = y;
      this.z = z;
      this.motX = motX;
      this.motY = motY;
      this.motZ = motZ;
   }

   public void setPosition(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public void setVelocity(double motX, double motY, double motZ) {
      this.motX = motX;
      this.motY = motY;
      this.motZ = motZ;
   }

   public String toString() {
      return "EntityVector[" + this.x + ", " + this.y + "," + this.z + ", " + this.motX + ", " + this.motY + ", " + this.motZ + "]";
   }

}
