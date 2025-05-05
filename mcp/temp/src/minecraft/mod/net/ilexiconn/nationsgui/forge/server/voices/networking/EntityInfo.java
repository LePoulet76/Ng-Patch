package net.ilexiconn.nationsgui.forge.server.voices.networking;


public class EntityInfo {

   public int entityID;
   public String address;


   public EntityInfo(String address, int entityID) {
      this.entityID = entityID;
      this.address = address;
   }
}
