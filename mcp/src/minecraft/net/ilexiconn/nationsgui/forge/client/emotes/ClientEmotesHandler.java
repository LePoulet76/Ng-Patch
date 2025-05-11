/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.ITickHandler
 *  cpw.mods.fml.common.TickType
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.registry.TickRegistry
 *  cpw.mods.fml.relauncher.Side
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.model.ModelBiped
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.packet.Packet
 *  net.minecraftforge.common.MinecraftForge
 */
package net.ilexiconn.nationsgui.forge.client.emotes;

import aurelienribon.tweenengine.Tween;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import java.util.EnumSet;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.EmoteAirGuitar;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.EmoteBalance;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.EmoteCheer;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.EmoteClap;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.EmoteDab;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.EmoteExorcist;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.EmoteFacepalm;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.EmoteGangnamStyle;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.EmoteGedDan;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.EmoteHeadbang;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.EmoteLevitate;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.EmoteNo;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.EmotePoint;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.EmoteRun;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.EmoteSalute;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.EmoteShaftHeadTilt;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.EmoteShrug;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.EmoteSpinHead;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.EmoteStand;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.EmoteStillArms;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.EmoteTDance;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.EmoteThink;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.EmoteWave;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.EmoteYes;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.EmoteZombie;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.base.EmoteHandler;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.base.ModelAccessor;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.jojo.EmoteAwakenPose;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.jojo.EmoteJosephPose;
import net.ilexiconn.nationsgui.forge.client.emotes.emote.jojo.EmoteWryPose;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.StartAnimationPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraftforge.common.MinecraftForge;

public class ClientEmotesHandler
implements ITickHandler {
    public static float time = 0.0f;
    public static float delta = 0.0f;

    public ClientEmotesHandler() {
        this.init();
    }

    public void init() {
        Tween.registerAccessor(ModelBiped.class, new ModelAccessor());
        MinecraftForge.EVENT_BUS.register((Object)this);
        TickRegistry.registerTickHandler((ITickHandler)this, (Side)Side.CLIENT);
        this.initEmotes();
    }

    public void handlePacket(String emote, String username) {
        if (!ClientProxy.clientConfig.renderEmotes) {
            return;
        }
        WorldClient world = Minecraft.func_71410_x().field_71441_e;
        EntityPlayer player = world.func_72924_a(username);
        if (player != null) {
            EmoteHandler.putEmote(player, emote);
        }
    }

    private void initEmotes() {
        System.out.println("Init Emotes...");
        EmoteHandler.emoteMap.put("airguitar", EmoteAirGuitar.class);
        EmoteHandler.emoteMap.put("cheer", EmoteCheer.class);
        EmoteHandler.emoteMap.put("clap", EmoteClap.class);
        EmoteHandler.emoteMap.put("exorcist", EmoteExorcist.class);
        EmoteHandler.emoteMap.put("facepalm", EmoteFacepalm.class);
        EmoteHandler.emoteMap.put("headbang", EmoteHeadbang.class);
        EmoteHandler.emoteMap.put("no", EmoteNo.class);
        EmoteHandler.emoteMap.put("point", EmotePoint.class);
        EmoteHandler.emoteMap.put("run", EmoteRun.class);
        EmoteHandler.emoteMap.put("salute", EmoteSalute.class);
        EmoteHandler.emoteMap.put("shaftheadtilt", EmoteShaftHeadTilt.class);
        EmoteHandler.emoteMap.put("shrug", EmoteShrug.class);
        EmoteHandler.emoteMap.put("stand", EmoteStand.class);
        EmoteHandler.emoteMap.put("think", EmoteThink.class);
        EmoteHandler.emoteMap.put("wave", EmoteWave.class);
        EmoteHandler.emoteMap.put("yes", EmoteYes.class);
        EmoteHandler.emoteMap.put("zombie", EmoteZombie.class);
        EmoteHandler.emoteMap.put("levitate", EmoteLevitate.class);
        EmoteHandler.emoteMap.put("gangnamstyle", EmoteGangnamStyle.class);
        EmoteHandler.emoteMap.put("balance", EmoteBalance.class);
        EmoteHandler.emoteMap.put("spinhead", EmoteSpinHead.class);
        EmoteHandler.emoteMap.put("dab", EmoteDab.class);
        EmoteHandler.emoteMap.put("stillarms", EmoteStillArms.class);
        EmoteHandler.emoteMap.put("geddan", EmoteGedDan.class);
        EmoteHandler.emoteMap.put("tdance", EmoteTDance.class);
        EmoteHandler.emoteMap.put("awaken", EmoteAwakenPose.class);
        EmoteHandler.emoteMap.put("wry", EmoteWryPose.class);
        EmoteHandler.emoteMap.put("joseph", EmoteJosephPose.class);
    }

    public void tickStart(EnumSet<TickType> type, Object ... tickData) {
        if (type.equals(EnumSet.of(TickType.CLIENT))) {
            time = (float)Math.ceil(time);
        } else if (type.equals(EnumSet.of(TickType.RENDER))) {
            float ctime = (float)Math.floor(time) + ((Float)tickData[0]).floatValue();
            delta = (ctime - time) * 50.0f;
            time = ctime;
            EmoteHandler.clearPlayerList();
        }
    }

    public void tickEnd(EnumSet<TickType> type, Object ... tickData) {
    }

    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.CLIENT, TickType.RENDER);
    }

    public String getLabel() {
        return null;
    }

    public static void playEmote(String emote, boolean playerOnly) {
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new StartAnimationPacket(emote, false)));
    }
}

