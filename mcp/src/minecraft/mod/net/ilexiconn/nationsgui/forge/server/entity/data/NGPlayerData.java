package net.ilexiconn.nationsgui.forge.server.entity.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.server.capes.Cape;
import net.ilexiconn.nationsgui.forge.server.capes.Capes;
import net.ilexiconn.nationsgui.forge.server.util.CommandUtils;
import net.ilexiconn.nationsgui.forge.server.util.Translation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class NGPlayerData implements IExtendedEntityProperties
{
    public static final String ID = "NGPlayerData";
    private static final Map<String, NBTTagCompound> playersData = new HashMap();
    private final EntityPlayer player;
    public List<String> emotes = new ArrayList();
    public List<String> currentEmotes = new ArrayList();
    public List<String> capes = new ArrayList();
    public String currentCape = "";
    public NBTTagCompound eventSave = null;

    public NGPlayerData()
    {
        this.player = null;
    }

    public NGPlayerData(EntityPlayer player)
    {
        this.player = player;
        this.currentCape = "";
    }

    public static NBTTagCompound listToNBT(List<String> list)
    {
        NBTTagCompound tag = new NBTTagCompound();
        NBTTagList nbtList = new NBTTagList();
        Iterator var3 = list.iterator();

        while (var3.hasNext())
        {
            String emote = (String)var3.next();
            nbtList.appendTag(new NBTTagString(emote, emote));
        }

        tag.setTag("list", nbtList);
        return tag;
    }

    public static List<String> nbtToList(NBTTagCompound props)
    {
        ArrayList emotes = new ArrayList();
        NBTTagList list = props.getTagList("list");

        for (int i = 0; i < list.tagCount(); ++i)
        {
            if (list.tagAt(i) instanceof NBTTagString)
            {
                emotes.add(((NBTTagString)list.tagAt(i)).data);
            }
        }

        return emotes;
    }

    public static final void register(EntityPlayer player)
    {
        player.registerExtendedProperties("NGPlayerData", new NGPlayerData(player));
    }

    public static final NGPlayerData get(EntityPlayer player)
    {
        return player != null && "NGPlayerData" != null ? (NGPlayerData)player.getExtendedProperties("NGPlayerData") : null;
    }

    public static void saveData(EntityPlayer player)
    {
        NGPlayerData playerData = get(player);
        NBTTagCompound savedData = new NBTTagCompound();
        playerData.saveNBTData(savedData);
        storeEntityData(getSaveKey(player), savedData);
    }

    public static void loadData(EntityPlayer player)
    {
        NGPlayerData playerData = get(player);
        NBTTagCompound savedData = getEntityData(getSaveKey(player));

        if (savedData != null)
        {
            playerData.loadNBTData(savedData);
        }

        playerData.synchronize();
    }

    static void storeEntityData(String name, NBTTagCompound compound)
    {
        playersData.put(name, compound);
    }

    static NBTTagCompound getEntityData(String name)
    {
        return (NBTTagCompound)playersData.remove(name);
    }

    private static String getSaveKey(EntityPlayer player)
    {
        return player.getDisplayName() + ":" + "NGPlayerData";
    }

    public static void refreshPremiumEmotes(String playerName, boolean has)
    {
        EntityPlayer entityPlayer = CommandUtils.getPlayer(playerName);

        if (entityPlayer != null)
        {
            NGPlayerData ngPlayerData = get(entityPlayer);

            if (has)
            {
                ngPlayerData.addEmote("facepalm");
                ngPlayerData.addEmote("shrug");
                ngPlayerData.addEmote("stand");
                ngPlayerData.addEmote("point");
                ngPlayerData.addEmote("salute");
                ngPlayerData.addEmote("shaftheadtilt");
                ngPlayerData.addEmote("levitate");
                ngPlayerData.addEmote("balance");
            }
            else
            {
                ngPlayerData.removeEmote("facepalm");
                ngPlayerData.removeEmote("shrug");
                ngPlayerData.removeEmote("stand");
                ngPlayerData.removeEmote("point");
                ngPlayerData.removeEmote("salute");
                ngPlayerData.removeEmote("shaftheadtilt");
                ngPlayerData.removeEmote("levitate");
                ngPlayerData.removeEmote("balance");
            }
        }
    }

    public final void synchronize() {}

    public void saveNBTData(NBTTagCompound compound)
    {
        NBTTagCompound props = new NBTTagCompound();
        props.setTag("Emotes", listToNBT(this.emotes));
        props.setTag("CurrentEmotes", listToNBT(this.currentEmotes));
        props.setTag("Capes", listToNBT(this.capes));
        props.setString("CurrentCape", this.currentCape == "" ? "none" : this.currentCape);

        if (this.eventSave != null)
        {
            props.setCompoundTag("EventSave", this.eventSave);
        }

        compound.setTag("NGPlayerData", props);
    }

    public void loadNBTData(NBTTagCompound compound)
    {
        try
        {
            NBTTagCompound props = (NBTTagCompound)compound.getTag("NGPlayerData");
            this.emotes = nbtToList(props.getCompoundTag("Emotes"));
            this.currentEmotes = nbtToList(props.getCompoundTag("CurrentEmotes"));
            this.capes = nbtToList(props.getCompoundTag("Capes"));
            this.currentCape = props.getString("CurrentCape").equalsIgnoreCase("none") ? "" : props.getString("CurrentCape");

            if (props.hasKey("EventSave"))
            {
                this.eventSave = props.getCompoundTag("EventSave");
            }
        }
        catch (Exception var3)
        {
            ;
        }
    }

    public void init(Entity entity, World world) {}

    public boolean addEmote(String emote)
    {
        if (!this.emotes.contains(emote))
        {
            this.emotes.add(emote);
            this.player.sendChatToPlayer(ChatMessageComponent.createFromText(Translation.get("\u00a74[\u00a7cEmotes\u00a74] \u00a77Vous avez re\u00e7u l\'emote \'<emote>\'").replace("<emote>", emote)));
            this.synchronize();
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean removeEmote(String emote)
    {
        if (this.emotes.contains(emote))
        {
            this.emotes.remove(emote);
            this.synchronize();
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean hasEmote(String emote)
    {
        return emote.isEmpty() || this.emotes.contains(emote);
    }

    public List<String> getEmotes()
    {
        return this.emotes;
    }

    public void setEmotes(List<String> emotes)
    {
        this.emotes = emotes;
        this.synchronize();
    }

    public boolean hasCurrentEmote(String emote)
    {
        return this.currentEmotes.contains(emote);
    }

    public List<String> getCurrentEmotes()
    {
        return this.currentEmotes;
    }

    public void setCurrentEmotes(List<String> emotes)
    {
        this.currentEmotes = emotes;
        this.synchronize();
    }

    public boolean addCape(Cape cape)
    {
        if (!this.capes.contains(cape.getIdentifier()))
        {
            this.capes.add(cape.getIdentifier());
            this.synchronize();
            this.player.sendChatToPlayer(ChatMessageComponent.createFromText(Translation.get("\u00a74[\u00a7cCapes\u00a74] \u00a77Vous avez re\u00e7u la cape \'<cape>\'").replaceAll("<cape>", cape.getDisplayName())));
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean removeCape(Cape cape)
    {
        if (this.capes.contains(cape.getIdentifier()))
        {
            this.capes.remove(cape.getIdentifier());
            this.synchronize();
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean hasCape(Cape cape)
    {
        return cape.getIdentifier().isEmpty() || this.capes.contains(cape.getIdentifier());
    }

    public boolean hasCape(String cape)
    {
        return this.capes.contains(cape);
    }

    public List<Cape> getCapes()
    {
        ArrayList capes = new ArrayList();
        Iterator var2 = this.capes.iterator();

        while (var2.hasNext())
        {
            String capeName = (String)var2.next();
            Cape cape = Capes.getCapeFromIdentifier(capeName);

            if (cape != null)
            {
                capes.add(cape);
            }
        }

        return capes;
    }

    public void setCapes(List<String> capes)
    {
        this.capes = capes;
        this.synchronize();
    }

    public boolean hasCurrentCape(Cape cape)
    {
        return this.currentCape.equals(cape.getIdentifier());
    }

    public boolean hasCurrentCape()
    {
        return this.currentCape != null;
    }

    public Cape getCurrentCape()
    {
        return Capes.getCapeFromIdentifier(this.currentCape);
    }

    public void setCurrentCape(Cape cape)
    {
        this.currentCape = cape.getIdentifier();
        this.synchronize();
    }

    public void resetCurrentCape()
    {
        this.currentCape = "";
        this.synchronize();
    }

    @ForgeSubscribe
    public void onEntityConstructing(EntityConstructing event)
    {
        if (event.entity instanceof EntityPlayer && get((EntityPlayer)event.entity) == null && ((EntityPlayer)event.entity).getExtendedProperties("NGPlayerData") == null)
        {
            register((EntityPlayer)event.entity);
        }
    }

    @ForgeSubscribe
    public void onLivingDeathEvent(LivingDeathEvent event)
    {
        if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer)
        {
            NBTTagCompound playerData = new NBTTagCompound();
            ((NGPlayerData)((NGPlayerData)event.entity.getExtendedProperties("NGPlayerData"))).saveNBTData(playerData);
            playersData.put(((EntityPlayer)event.entity).username, playerData);
            saveData((EntityPlayer)event.entity);
        }
    }

    @ForgeSubscribe
    public void onEntityJoinWorldEvent(EntityJoinWorldEvent event)
    {
        if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer && ((EntityPlayer)event.entity).username != null)
        {
            NBTTagCompound playerData = (NBTTagCompound)playersData.remove(((EntityPlayer)event.entity).username);

            if (playerData != null)
            {
                ((NGPlayerData)((NGPlayerData)event.entity.getExtendedProperties("NGPlayerData"))).loadNBTData(playerData);
                playersData.remove(((EntityPlayer)event.entity).username);
            }

            ((NGPlayerData)((NGPlayerData)event.entity.getExtendedProperties("NGPlayerData"))).synchronize();
        }
    }
}
