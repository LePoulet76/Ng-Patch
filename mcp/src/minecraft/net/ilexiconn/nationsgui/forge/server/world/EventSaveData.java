/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.util.ChunkCoordinates
 *  net.minecraft.world.World
 *  net.minecraft.world.WorldSavedData
 *  net.minecraft.world.storage.MapStorage
 */
package net.ilexiconn.nationsgui.forge.server.world;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;

public class EventSaveData
extends WorldSavedData {
    private static final String DATA_NAME = "Events";
    private final HashMap<String, Event> events = new HashMap();

    public EventSaveData() {
        super(DATA_NAME);
    }

    public EventSaveData(String p_i2141_1_) {
        super(p_i2141_1_);
    }

    public static EventSaveData get(World world) {
        MapStorage storage = world.field_72988_C;
        EventSaveData eventSaveData = (EventSaveData)storage.func_75742_a(EventSaveData.class, DATA_NAME);
        if (eventSaveData == null) {
            eventSaveData = new EventSaveData();
            storage.func_75745_a(DATA_NAME, (WorldSavedData)eventSaveData);
        }
        return eventSaveData;
    }

    public Event getEvent(String id) {
        return this.events.get(id);
    }

    public void putEvent(String id, Event event) {
        this.events.put(id, event);
        this.func_76186_a(true);
    }

    public Collection<Event> getEvents() {
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
        if (nbtTagCompound.func_74764_b("ActiveEvents")) {
            NBTTagList list = nbtTagCompound.func_74761_m("ActiveEvents");
            for (int i = 0; i < list.func_74745_c(); ++i) {
                Event event = new Event();
                event.readFromNBT((NBTTagCompound)list.func_74743_b(i));
                this.events.put(event.getName(), event);
            }
        }
    }

    public void func_76187_b(NBTTagCompound nbtTagCompound) {
        NBTTagList list = new NBTTagList();
        for (Map.Entry<String, Event> entry : this.events.entrySet()) {
            NBTTagCompound tag = new NBTTagCompound();
            entry.getValue().writeToNBT(tag);
            list.func_74742_a((NBTBase)tag);
        }
        nbtTagCompound.func_74782_a("ActiveEvents", (NBTBase)list);
    }

    public static class Event {
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
}

