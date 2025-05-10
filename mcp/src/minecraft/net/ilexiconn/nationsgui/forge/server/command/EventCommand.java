package net.ilexiconn.nationsgui.forge.server.command;

import fr.nationsglory.ngbridge.Bukkit2Forge;
import java.util.Iterator;
import micdoodle8.mods.galacticraft.core.entities.player.GCCorePlayerMP;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.entity.data.NGPlayerData;
import net.ilexiconn.nationsgui.forge.server.world.EventSaveData;
import net.ilexiconn.nationsgui.forge.server.world.EventSaveData$Event;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ChunkCoordinates;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

public class EventCommand extends CommandBase
{
    public String getCommandName()
    {
        return "goevent";
    }

    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "";
    }

    public void processCommand(ICommandSender iCommandSender, String[] strings)
    {
        MinecraftServer server = MinecraftServer.getServer();
        boolean isOp = !(iCommandSender instanceof EntityPlayer) || server.getConfigurationManager().isPlayerOpped(((EntityPlayer)iCommandSender).username);
        EventSaveData eventSaveData = EventSaveData.get(MinecraftServer.getServer().getEntityWorld());
        GCCorePlayerMP player = null;

        if (strings.length >= 3)
        {
            player = (GCCorePlayerMP)MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(strings[2]);
        }
        else if (iCommandSender instanceof GCCorePlayerMP)
        {
            player = (GCCorePlayerMP)iCommandSender;
        }

        EventSaveData$Event event = strings.length >= 2 ? eventSaveData.getEvent(strings[1]) : null;

        if (strings.length >= 1)
        {
            String var8 = strings[0];
            byte var9 = -1;

            switch (var8.hashCode())
            {
                case -690213213:
                    if (var8.equals("register"))
                    {
                        var9 = 2;
                    }

                    break;

                case 3304:
                    if (var8.equals("go"))
                    {
                        var9 = 0;
                    }

                    break;

                case 100571:
                    if (var8.equals("end"))
                    {
                        var9 = 1;
                    }

                    break;

                case 3322014:
                    if (var8.equals("list"))
                    {
                        var9 = 4;
                    }

                    break;

                case 836015164:
                    if (var8.equals("unregister"))
                    {
                        var9 = 3;
                    }
            }

            switch (var9)
            {
                case 0:
                    if (event == null)
                    {
                        throw new CommandException("event.notRegistered", new Object[0]);
                    }

                    if (player == null)
                    {
                        throw new CommandException("event.playerNotFound", new Object[0]);
                    }

                    NGPlayerData ngPlayerData1 = NGPlayerData.get(player);

                    if (ngPlayerData1.eventSave != null)
                    {
                        throw new CommandException("event.alreadyInEvent", new Object[0]);
                    }

                    this.savePlayerData(player);
                    this.clearPlayerData(player);
                    this.applyWarp(player, event);
                    break;

                case 1:
                    if (player == null)
                    {
                        throw new CommandException("event.playerNotFound", new Object[0]);
                    }

                    this.restorePlayerData(player);
                    break;

                case 2:
                    if (!isOp || player == null)
                    {
                        throw new CommandException("event.permissionDenied", new Object[0]);
                    }

                    if (event != null)
                    {
                        throw new CommandException("event.alreadyRegistered", new Object[0]);
                    }

                    if (strings.length < 2)
                    {
                        throw new CommandException("event.eventNameMissing", new Object[0]);
                    }

                    this.saveWarp(player, eventSaveData, strings[1]);
                    iCommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("event.registered"));
                    break;

                case 3:
                    if (!isOp)
                    {
                        throw new CommandException("event.permissionDenied", new Object[0]);
                    }

                    if (event == null)
                    {
                        throw new CommandException("event.notRegistered", new Object[0]);
                    }

                    eventSaveData.removeEvent(event.getName());
                    iCommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("event.unregistered"));
                    break;

                case 4:
                    iCommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("event.listHeader"));
                    Iterator ngPlayerData = eventSaveData.getEvents().iterator();

                    while (ngPlayerData.hasNext())
                    {
                        EventSaveData$Event eventSaved = (EventSaveData$Event)ngPlayerData.next();
                        iCommandSender.sendChatToPlayer(ChatMessageComponent.createFromText(eventSaved.getName()));
                    }
            }
        }
    }

    private boolean canBeTeleported(EntityPlayerMP player, int dimension, ChunkCoordinates coordinates)
    {
        Player bukkitPlayer = Bukkit.getServer().getPlayer(player.username);
        Iterator var5 = Bukkit.getServer().getWorlds().iterator();
        World world;
        net.minecraft.world.World world1;

        do
        {
            if (!var5.hasNext())
            {
                return false;
            }

            world = (World)var5.next();
            world1 = Bukkit2Forge.convertWorld(world);
        }
        while (world1.provider.dimensionId != dimension);

        PlayerTeleportEvent playerTeleportEvent = new PlayerTeleportEvent(bukkitPlayer, bukkitPlayer.getLocation(), new Location(world, (double)coordinates.posX, (double)coordinates.posY, (double)coordinates.posZ));
        Bukkit.getServer().getPluginManager().callEvent(playerTeleportEvent);
        return !playerTeleportEvent.isCancelled();
    }

    private void applyWarp(GCCorePlayerMP player, EventSaveData$Event event)
    {
        NationsGUI.teleportPlayerToLocation(player.username, event.getDimensionId() + "", event.getEventCoordinates().posX, event.getEventCoordinates().posY, event.getEventCoordinates().posZ, event.getRotationYaw(), event.getRotationPitch());
    }

    private void saveWarp(EntityPlayer player, EventSaveData worldData, String name)
    {
        EventSaveData$Event event = new EventSaveData$Event();
        event.setName(name);
        event.setDimensionId(player.dimension);
        event.setEventCoordinates(new ChunkCoordinates((int)player.posX, (int)player.posY, (int)player.posZ));
        event.setRotationYaw(player.rotationYaw);
        event.setRotationPitch(player.rotationPitch);
        worldData.putEvent(name, event);
    }

    private void savePlayerData(GCCorePlayerMP player)
    {
        NBTTagCompound saveStates = new NBTTagCompound();
        saveStates.setInteger("dimension", player.dimension);
        saveStates.setDouble("posX", player.posX);
        saveStates.setDouble("posY", player.posY);
        saveStates.setDouble("posZ", player.posZ);
        saveStates.setFloat("rotationYaw", player.rotationYaw);
        saveStates.setFloat("rotationPitch", player.rotationPitch);
        saveStates.setInteger("experienceLevel", player.experienceLevel);
        saveStates.setFloat("experience", player.experience);
        NBTTagList inventory = new NBTTagList();
        player.inventory.writeToNBT(inventory);
        saveStates.setTag("inventory", inventory);
        NBTTagList inventoryExtended = new NBTTagList();
        player.getExtendedInventory().writeToNBT(inventoryExtended);
        saveStates.setTag("inventoryExtended", inventoryExtended);
        NGPlayerData ngPlayerData = NGPlayerData.get(player);
        ngPlayerData.eventSave = saveStates;
    }

    private void clearPlayerData(GCCorePlayerMP player)
    {
        player.clearActivePotions();
        player.experienceLevel = 0;
        player.experience = 0.0F;
        player.inventory.clearInventory(-1, -1);
        ItemStack[] inventoryStacks = player.getExtendedInventory().inventoryStacks;
        int i = 0;

        for (int inventoryStacksLength = inventoryStacks.length; i < inventoryStacksLength; ++i)
        {
            inventoryStacks[i] = null;
        }
    }

    private void transferToDimension(EntityPlayerMP entityPlayer, int id)
    {
        entityPlayer.worldObj.removeEntity(entityPlayer);
        entityPlayer.mcServer.getConfigurationManager().transferPlayerToDimension(entityPlayer, id);
    }

    protected void restorePlayerData(GCCorePlayerMP player)
    {
        NGPlayerData ngPlayerData = NGPlayerData.get(player);
        NBTTagCompound eventSave = ngPlayerData.eventSave;

        if (eventSave == null)
        {
            throw new CommandException("event.noInEvent", new Object[0]);
        }
        else if (this.canBeTeleported(player, eventSave.getInteger("dimension"), new ChunkCoordinates((int)eventSave.getDouble("posX"), (int)eventSave.getDouble("posY"), (int)eventSave.getDouble("posZ"))))
        {
            this.clearPlayerData(player);
            NationsGUI.teleportPlayerToLocation(player.username, eventSave.getInteger("dimension") + "", (int)eventSave.getDouble("posX"), (int)eventSave.getDouble("posY"), (int)eventSave.getDouble("posZ"), eventSave.getFloat("rotationYaw"), eventSave.getFloat("rotationPitch"));
            player.inventory.readFromNBT(eventSave.getTagList("inventory"));
            player.getExtendedInventory().readFromNBT(eventSave.getTagList("inventoryExtended"));
            player.experienceLevel = eventSave.getInteger("experienceLevel");
            player.experience = eventSave.getFloat("experience");
            ngPlayerData.eventSave = null;
        }
    }

    public int compareTo(Object o)
    {
        return 0;
    }
}
