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

public class EventSaveData extends WorldSavedData
{
    private static final String DATA_NAME = "Events";
    private final HashMap<String, EventSaveData$Event> events = new HashMap();

    public EventSaveData()
    {
        super("Events");
    }

    public EventSaveData(String par1Str)
    {
        super(par1Str);
    }

    public static EventSaveData get(World world)
    {
        MapStorage storage = world.mapStorage;
        EventSaveData eventSaveData = (EventSaveData)storage.loadData(EventSaveData.class, "Events");

        if (eventSaveData == null)
        {
            eventSaveData = new EventSaveData();
            storage.setData("Events", eventSaveData);
        }

        return eventSaveData;
    }

    public EventSaveData$Event getEvent(String id)
    {
        return (EventSaveData$Event)this.events.get(id);
    }

    public void putEvent(String id, EventSaveData$Event event)
    {
        this.events.put(id, event);
        this.setDirty(true);
    }

    public Collection<EventSaveData$Event> getEvents()
    {
        return this.events.values();
    }

    public boolean hasEvent(String id)
    {
        return this.events.containsKey(id);
    }

    public void removeEvent(String id)
    {
        this.events.remove(id);
        this.setDirty(true);
    }

    /**
     * reads in data from the NBTTagCompound into this MapDataBase
     */
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        if (nbtTagCompound.hasKey("ActiveEvents"))
        {
            NBTTagList list = nbtTagCompound.getTagList("ActiveEvents");

            for (int i = 0; i < list.tagCount(); ++i)
            {
                EventSaveData$Event event = new EventSaveData$Event();
                event.readFromNBT((NBTTagCompound)list.tagAt(i));
                this.events.put(event.getName(), event);
            }
        }
    }

    /**
     * write data to NBTTagCompound from this MapDataBase, similar to Entities and TileEntities
     */
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        NBTTagList list = new NBTTagList();
        Iterator var3 = this.events.entrySet().iterator();

        while (var3.hasNext())
        {
            Entry entry = (Entry)var3.next();
            NBTTagCompound tag = new NBTTagCompound();
            ((EventSaveData$Event)entry.getValue()).writeToNBT(tag);
            list.appendTag(tag);
        }

        nbtTagCompound.setTag("ActiveEvents", list);
    }
}
