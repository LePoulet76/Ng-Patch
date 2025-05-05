package net.ilexiconn.nationsgui.forge.server.world;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.server.world.EventSaveData$Event;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;

public class EventSaveData extends WorldSavedData {

   private static final String DATA_NAME = "Events";
   private final HashMap<String, EventSaveData$Event> events = new HashMap();


   public EventSaveData() {
      super("Events");
   }

   public EventSaveData(String p_i2141_1_) {
      super(p_i2141_1_);
   }

   public static EventSaveData get(World world) {
      MapStorage storage = world.field_72988_C;
      EventSaveData eventSaveData = (EventSaveData)storage.func_75742_a(EventSaveData.class, "Events");
      if(eventSaveData == null) {
         eventSaveData = new EventSaveData();
         storage.func_75745_a("Events", eventSaveData);
      }

      return eventSaveData;
   }

   public EventSaveData$Event getEvent(String id) {
      return (EventSaveData$Event)this.events.get(id);
   }

   public void putEvent(String id, EventSaveData$Event event) {
      this.events.put(id, event);
      this.func_76186_a(true);
   }

   public Collection<EventSaveData$Event> getEvents() {
      return this.events.values();
   }

   public boolean hasEvent(String id) {
      return this.events.containsKey(id);
   }

   public void removeEvent(String id) {
      this.events.remove(id);
      this.func_76186_a(true);
   }

   public void func_76184_a(NBTTagCompound nbtTagCompound) {
      if(nbtTagCompound.func_74764_b("ActiveEvents")) {
         NBTTagList list = nbtTagCompound.func_74761_m("ActiveEvents");

         for(int i = 0; i < list.func_74745_c(); ++i) {
            EventSaveData$Event event = new EventSaveData$Event();
            event.readFromNBT((NBTTagCompound)list.func_74743_b(i));
            this.events.put(event.getName(), event);
         }
      }

   }

   public void func_76187_b(NBTTagCompound nbtTagCompound) {
      NBTTagList list = new NBTTagList();
      Iterator var3 = this.events.entrySet().iterator();

      while(var3.hasNext()) {
         Entry entry = (Entry)var3.next();
         NBTTagCompound tag = new NBTTagCompound();
         ((EventSaveData$Event)entry.getValue()).writeToNBT(tag);
         list.func_74742_a(tag);
      }

      nbtTagCompound.func_74782_a("ActiveEvents", list);
   }
}
