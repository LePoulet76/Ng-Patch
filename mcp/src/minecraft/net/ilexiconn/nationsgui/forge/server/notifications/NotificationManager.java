/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  net.ilexiconn.nationsgui.bukkit.util.Translation
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.network.packet.Packet
 */
package net.ilexiconn.nationsgui.forge.server.notifications;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.util.HashMap;
import net.ilexiconn.nationsgui.bukkit.util.Translation;
import net.ilexiconn.nationsgui.forge.server.notifications.INotificationActionHandler;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.NotificationPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;

public class NotificationManager {
    public static int T_SHORT = 3000;
    public static int T_MEDIUM = 5000;
    public static int T_LONG = 10000;
    private static HashMap<String, INotificationActionHandler> registry = new HashMap();

    public static void registerAction(String id, INotificationActionHandler notificationActionHandler) {
        registry.put(id, notificationActionHandler);
    }

    public static void executeAction(EntityPlayer player, String id, NBTTagCompound data) {
        if (registry.containsKey(id)) {
            registry.get(id).handleAction(player, data);
        }
    }

    public static void sendNotificationToPlayer(Player player, String title, String content, NColor color, NIcon icon, long lifeTime) {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.func_74778_a("title", title);
        nbtTagCompound.func_74778_a("color", color.name());
        nbtTagCompound.func_74778_a("icon", icon.name());
        nbtTagCompound.func_74778_a("content", content);
        nbtTagCompound.func_74772_a("lifetime", lifeTime);
        PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new NotificationPacket(nbtTagCompound)), (Player)player);
    }

    public static void sendNotificationToPlayer(Player player, String title, String content, NColor color, NIcon icon, long lifeTime, String actionAllowID, String actionAllowTitle, String actionDenyID, String actionDenyTitle) {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.func_74778_a("title", title);
        nbtTagCompound.func_74778_a("color", color.name());
        nbtTagCompound.func_74778_a("icon", icon.name());
        nbtTagCompound.func_74778_a("content", content);
        nbtTagCompound.func_74772_a("lifetime", lifeTime);
        NBTTagCompound actions = new NBTTagCompound();
        NBTTagCompound allow = new NBTTagCompound();
        NBTTagCompound deny = new NBTTagCompound();
        allow.func_74778_a("translatedTitle", actionAllowTitle);
        allow.func_74778_a("id", actionAllowID);
        deny.func_74778_a("translatedTitle", actionDenyTitle);
        deny.func_74778_a("id", actionDenyID);
        actions.func_74782_a("allow", (NBTBase)allow);
        actions.func_74782_a("deny", (NBTBase)deny);
        nbtTagCompound.func_74766_a("actions", actions);
        PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new NotificationPacket(nbtTagCompound)), (Player)player);
    }

    public static void sendNotificationToPlayer(Player player, String title, String content, NColor color, NIcon icon, long lifeTime, String actionAllowID, String actionAllowTitle, String actionDenyID, String actionDenyTitle, String actionDenyArgs, String actionAllowArgs) {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.func_74778_a("title", title);
        nbtTagCompound.func_74778_a("color", color.name());
        nbtTagCompound.func_74778_a("icon", icon.name());
        System.out.println(content);
        nbtTagCompound.func_74778_a("content", content);
        nbtTagCompound.func_74772_a("lifetime", lifeTime);
        NBTTagCompound actions = new NBTTagCompound();
        NBTTagCompound allow = new NBTTagCompound();
        NBTTagCompound deny = new NBTTagCompound();
        boolean par1 = false;
        boolean par2 = false;
        if (!actionAllowTitle.equals("null")) {
            if (actionAllowTitle.equals("default")) {
                allow.func_74778_a("translatedTitle", Translation.get((String)"Accepter"));
            } else {
                allow.func_74778_a("translatedTitle", actionAllowTitle);
            }
            allow.func_74778_a("id", actionAllowID);
            allow.func_74778_a("args", actionAllowArgs);
            actions.func_74782_a("allow", (NBTBase)allow);
            par1 = true;
        }
        if (!actionDenyTitle.equals("null")) {
            if (actionDenyTitle.equals("default")) {
                deny.func_74778_a("translatedTitle", Translation.get((String)"Refuser"));
            } else {
                deny.func_74778_a("translatedTitle", actionDenyTitle);
            }
            deny.func_74778_a("id", actionDenyID);
            deny.func_74778_a("args", actionDenyArgs);
            actions.func_74782_a("deny", (NBTBase)deny);
            par2 = true;
        }
        if (par1 || par2) {
            nbtTagCompound.func_74766_a("actions", actions);
        }
        PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new NotificationPacket(nbtTagCompound)), (Player)player);
    }

    public static enum NIcon {
        CALENDAR_ADD,
        PEOPLE_BLACK,
        HAND_PLUS,
        TARGET_BLACK,
        LOCK_GREY,
        FLAG,
        ISLAND,
        ENTER,
        TICKET,
        HAND_PLUS_GREY,
        CHAT_GREY,
        HAND_SHAKE,
        CLOUD_GREY,
        SKULL,
        CALENDAR_BLUE,
        MEDAL_STAR_BLACK,
        MEDAL_CHECK_BLACK,
        STAR_BLACK,
        QUESTION,
        EXCLAMATION,
        VOTE,
        CHECK,
        THUMBS_UP,
        ALARM,
        LEAVE;

    }

    public static enum NColor {
        BLUE(-9537810),
        CYAN(-9518866),
        DARK_BLUE(-12891710),
        PURPLE(-5345554),
        PINK(-1090136),
        CORAL(-238491),
        RED(-776630),
        RUBY(-3668955),
        ORANGE(-28116),
        YELLOW(-606204),
        LIME(-8730273),
        GREEN(-11556787),
        WHITE(-1314054),
        BLACK(-14606034),
        GREY(-8224108);

        private final int colorCode;

        private NColor(int colorCode) {
            this.colorCode = colorCode;
        }

        public int getColorCode() {
            return this.colorCode;
        }
    }
}

