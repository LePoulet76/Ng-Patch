package net.ilexiconn.nationsgui.forge.server.notifications;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.util.HashMap;
import net.ilexiconn.nationsgui.bukkit.util.Translation;
import net.ilexiconn.nationsgui.forge.server.notifications.NotificationManager$NColor;
import net.ilexiconn.nationsgui.forge.server.notifications.NotificationManager$NIcon;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.NotificationPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class NotificationManager
{
    public static int T_SHORT = 3000;
    public static int T_MEDIUM = 5000;
    public static int T_LONG = 10000;
    private static HashMap<String, INotificationActionHandler> registry = new HashMap();

    public static void registerAction(String id, INotificationActionHandler notificationActionHandler)
    {
        registry.put(id, notificationActionHandler);
    }

    public static void executeAction(EntityPlayer player, String id, NBTTagCompound data)
    {
        if (registry.containsKey(id))
        {
            ((INotificationActionHandler)registry.get(id)).handleAction(player, data);
        }
    }

    public static void sendNotificationToPlayer(Player player, String title, String content, NotificationManager$NColor color, NotificationManager$NIcon icon, long lifeTime)
    {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setString("title", title);
        nbtTagCompound.setString("color", color.name());
        nbtTagCompound.setString("icon", icon.name());
        nbtTagCompound.setString("content", content);
        nbtTagCompound.setLong("lifetime", lifeTime);
        PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new NotificationPacket(nbtTagCompound)), player);
    }

    public static void sendNotificationToPlayer(Player player, String title, String content, NotificationManager$NColor color, NotificationManager$NIcon icon, long lifeTime, String actionAllowID, String actionAllowTitle, String actionDenyID, String actionDenyTitle)
    {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setString("title", title);
        nbtTagCompound.setString("color", color.name());
        nbtTagCompound.setString("icon", icon.name());
        nbtTagCompound.setString("content", content);
        nbtTagCompound.setLong("lifetime", lifeTime);
        NBTTagCompound actions = new NBTTagCompound();
        NBTTagCompound allow = new NBTTagCompound();
        NBTTagCompound deny = new NBTTagCompound();
        allow.setString("translatedTitle", actionAllowTitle);
        allow.setString("id", actionAllowID);
        deny.setString("translatedTitle", actionDenyTitle);
        deny.setString("id", actionDenyID);
        actions.setTag("allow", allow);
        actions.setTag("deny", deny);
        nbtTagCompound.setCompoundTag("actions", actions);
        PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new NotificationPacket(nbtTagCompound)), player);
    }

    public static void sendNotificationToPlayer(Player player, String title, String content, NotificationManager$NColor color, NotificationManager$NIcon icon, long lifeTime, String actionAllowID, String actionAllowTitle, String actionDenyID, String actionDenyTitle, String actionDenyArgs, String actionAllowArgs)
    {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setString("title", title);
        nbtTagCompound.setString("color", color.name());
        nbtTagCompound.setString("icon", icon.name());
        System.out.println(content);
        nbtTagCompound.setString("content", content);
        nbtTagCompound.setLong("lifetime", lifeTime);
        NBTTagCompound actions = new NBTTagCompound();
        NBTTagCompound allow = new NBTTagCompound();
        NBTTagCompound deny = new NBTTagCompound();
        boolean par1 = false;
        boolean par2 = false;

        if (!actionAllowTitle.equals("null"))
        {
            if (actionAllowTitle.equals("default"))
            {
                allow.setString("translatedTitle", Translation.get("Accepter"));
            }
            else
            {
                allow.setString("translatedTitle", actionAllowTitle);
            }

            allow.setString("id", actionAllowID);
            allow.setString("args", actionAllowArgs);
            actions.setTag("allow", allow);
            par1 = true;
        }

        if (!actionDenyTitle.equals("null"))
        {
            if (actionDenyTitle.equals("default"))
            {
                deny.setString("translatedTitle", Translation.get("Refuser"));
            }
            else
            {
                deny.setString("translatedTitle", actionDenyTitle);
            }

            deny.setString("id", actionDenyID);
            deny.setString("args", actionDenyArgs);
            actions.setTag("deny", deny);
            par2 = true;
        }

        if (par1 || par2)
        {
            nbtTagCompound.setCompoundTag("actions", actions);
        }

        PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new NotificationPacket(nbtTagCompound)), player);
    }
}
