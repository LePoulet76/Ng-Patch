/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.multiplayer.NetClientHandler
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.stats.StatList
 *  org.json.utils.Utils
 */
package net.ilexiconn.nationsgui.forge.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.CustomConnectingGui;
import net.ilexiconn.nationsgui.forge.client.gui.ServerSwitchExpressGui;
import net.ilexiconn.nationsgui.forge.client.gui.WaitingModalGui;
import net.ilexiconn.nationsgui.forge.client.gui.WaitingSocketGui;
import net.ilexiconn.nationsgui.forge.client.gui.main.MainGUI;
import net.ilexiconn.nationsgui.forge.server.util.SoundStreamer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.stats.StatList;
import org.json.utils.Utils;

public class ClientSocket {
    public static final String serverAddress = "socket.nationsglory.fr";
    public static Scanner in;
    public static PrintWriter out;
    public static Socket socket;
    public static String clientName;
    private NetClientHandler netClientHandler = null;
    public static boolean tryToReconnect;
    public static Thread heartbeatThread;
    public static long heartbeatDelayMillis;
    private static Thread wakeUpThread;
    private static Thread pingThread;
    public static long lastSocketPing;
    private static ClientSocket client;

    public ClientSocket(String name) {
        clientName = name;
        client = this;
        wakeUpThread = new Thread(new Runnable(){

            @Override
            public void run() {
                client.run();
                while (tryToReconnect) {
                    try {
                        if (System.currentTimeMillis() - lastSocketPing > 30000L) {
                            System.out.println("[Socket] No ping received from server, trying to reconnect...");
                            throw new Exception("No ping received from server");
                        }
                        socket.getOutputStream().write(666);
                        Thread.sleep(5000L);
                    }
                    catch (Exception e) {
                        System.out.println("[Socket] Erreur d\u00e9tect\u00e9e, tentative de reconnexion...");
                        try {
                            if (socket != null && !socket.isClosed()) {
                                socket.close();
                                System.out.println("[Socket] Socket ferm\u00e9 proprement.");
                            }
                        }
                        catch (IOException closeException) {
                            System.out.println("[Socket] Erreur lors de la fermeture du socket : " + closeException.getMessage());
                        }
                        try {
                            Thread.sleep(5000L);
                        }
                        catch (InterruptedException ie) {
                            System.out.println("[Socket] Thread interrompu : " + ie.getMessage());
                        }
                        client.run();
                    }
                }
            }
        });
        wakeUpThread.start();
        lastSocketPing = System.currentTimeMillis();
        pingThread = new Thread(new Runnable(){

            @Override
            public void run() {
                while (true) {
                    System.out.println("[Socket] Last ping : " + (System.currentTimeMillis() - lastSocketPing) + "ms");
                    if (System.currentTimeMillis() - lastSocketPing > 30000L) {
                        try {
                            if (socket != null && !socket.isClosed()) {
                                socket.close();
                                System.out.println("[Socket] Socket ferm\u00e9 proprement car aucune activit\u00e9 durant les 30 derni\u00e8res secondes");
                            }
                        }
                        catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    try {
                        Thread.sleep(5000L);
                    }
                    catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        pingThread.start();
    }

    public static void connectPlayerToServer(String ipAndPort) {
        if (ipAndPort == null) {
            System.out.println("Server not found in server list");
            return;
        }
        ClientData.lastPlayerWantDisconnect = System.currentTimeMillis();
        String ip = ipAndPort.split(":")[0];
        int port = Integer.parseInt(ipAndPort.split(":")[1]);
        String targetServer = ClientProxy.getServerNameByIpAndPort(ipAndPort);
        if (targetServer != null && targetServer.equalsIgnoreCase(ClientProxy.currentServerName)) {
            return;
        }
        ClientData.waitingServerName = null;
        ClientData.waitingPosition = 0;
        ClientData.waitingJoinTime = 0L;
        if (WaitingSocketGui.player != null && WaitingSocketGui.player.isPlaying()) {
            WaitingSocketGui.player.forceClose();
        }
        try {
            for (SoundStreamer player : new ArrayList<SoundStreamer>(ClientProxy.STREAMER_LIST)) {
                player.forceClose();
            }
            ClientProxy.STREAMER_LIST.clear();
            Minecraft.func_71410_x().func_71373_a((GuiScreen)new ServerSwitchExpressGui(ip, port, targetServer));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            try {
                if (Minecraft.func_71410_x().field_71441_e != null) {
                    Minecraft.func_71410_x().field_71413_E.func_77450_a(StatList.field_75947_j, 1);
                    Minecraft.func_71410_x().field_71441_e.func_72882_A();
                    Minecraft.func_71410_x().func_71403_a(null);
                    for (SoundStreamer player : new ArrayList<SoundStreamer>(ClientProxy.STREAMER_LIST)) {
                        player.forceClose();
                    }
                    ClientProxy.STREAMER_LIST.clear();
                }
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new CustomConnectingGui((GuiScreen)new MainGUI(), ip, port, targetServer));
            }
            catch (Exception e2) {
                System.out.println(e2.getMessage());
            }
        }
    }

    public void run() {
        try {
            lastSocketPing = System.currentTimeMillis();
            socket = new Socket(serverAddress, 59001);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
            while (in.hasNextLine()) {
                String serverName;
                String line = in.nextLine();
                System.out.println("[Socket] DEBUG Receive message : " + line);
                if (line.startsWith("SUBMITNAME")) {
                    String authString = clientName + "##" + Minecraft.func_71410_x().func_110432_I().func_111286_b() + "##" + Utils.toTweaker((byte[])MessageDigest.getInstance("SHA-256").digest(Utils.getTweaker().getBytes(StandardCharsets.UTF_8)));
                    System.out.println("DEBUG CLIENT SEND TO SOCKET: " + authString);
                    out.println(authString);
                    continue;
                }
                if (line.startsWith("NAMEACCEPTED")) {
                    System.out.println("Connection accepted by server");
                    if (ClientData.waitingServerName == null || ClientData.waitingServerName.equalsIgnoreCase("null")) continue;
                    out.println("MESSAGE socket ADD_WAITINGLIST " + ClientData.waitingServerName);
                    ClientData.waitingJoinTime = System.currentTimeMillis();
                    continue;
                }
                if (line.contains("NAMENOTACCEPTED")) {
                    System.out.println("DEBUG DUPLICATE CONNECTION");
                    return;
                }
                if (line.startsWith("MESSAGE")) continue;
                if (line.startsWith("PING_AND_DATA")) {
                    lastSocketPing = System.currentTimeMillis();
                    serverName = line.split(" ")[1];
                    String waitingListServerName = line.split(" ")[2];
                    int totalPlayersInWaitingList = Integer.parseInt(line.split(" ")[3]);
                    int playerPositionInWaitingList = Integer.parseInt(line.split(" ")[4]);
                    boolean isPlayerInWaitingList = Boolean.parseBoolean(line.split(" ")[5]);
                    if (waitingListServerName.equals(ClientProxy.currentServerName) && !ClientProxy.currentServerName.equals("dev")) {
                        ClientData.waitingServerName = null;
                        ClientData.waitingPosition = 0;
                        ClientData.waitingJoinTime = 0L;
                        out.println("MESSAGE socket REMOVE_WAITINGLIST");
                    } else {
                        if (ClientData.waitingServerName == null || ClientData.waitingServerName.equalsIgnoreCase("null")) {
                            ClientData.waitingServerName = waitingListServerName;
                        }
                        ClientData.waitingPosition = playerPositionInWaitingList;
                        ClientData.waitingTotal = totalPlayersInWaitingList;
                        ClientData.waitingPriority = isPlayerInWaitingList;
                        if (!(ClientData.waitingServerName.equalsIgnoreCase("null") || ClientData.waitingJoinTime != null && ClientData.waitingJoinTime != 0L)) {
                            ClientData.waitingJoinTime = System.currentTimeMillis();
                        }
                    }
                    out.println("MESSAGE socket PONG_CLIENT " + (!ClientProxy.currentServerName.equals("") ? ClientProxy.currentServerName : (ClientProxy.isPlayerOnServer() ? "other" : "null")));
                    continue;
                }
                if (line.startsWith("NEED_CONFIRMATION_JOIN_WAITING")) {
                    serverName = line.split(" ")[1];
                    if (ClientProxy.currentServerName.equalsIgnoreCase(serverName)) {
                        return;
                    }
                    ClientData.waitingServerNeedConfirmation = serverName;
                    Thread.sleep(1000L);
                    if (!ClientProxy.isPlayerOnServer()) {
                        WaitingSocketGui.askForConfirmation = true;
                        continue;
                    }
                    if (Minecraft.func_71410_x().field_71462_r instanceof WaitingModalGui) continue;
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new WaitingModalGui("switch"));
                    continue;
                }
                if (line.startsWith("CONNECT_TO")) {
                    Thread.sleep(1000L);
                    ClientData.waitingServerName = serverName = line.split(" ")[1];
                    String ipAndPort = line.split(" ")[2];
                    if (ClientProxy.currentServerName.equalsIgnoreCase(serverName)) {
                        ClientData.waitingServerName = null;
                    }
                    if (ClientProxy.isPlayerOnServer() && !ClientProxy.currentServerName.equalsIgnoreCase("hub") && !ClientProxy.currentServerName.equalsIgnoreCase("accueil")) {
                        if (ClientData.isCombatTagged) {
                            Minecraft.func_71410_x().field_71439_g.func_71035_c(I18n.func_135053_a((String)"waiting.combattagged").replaceAll("<server>", serverName));
                            out.println("MESSAGE socket STEPBACK_WAITINGLIST");
                            continue;
                        }
                        ClientData.waitingServerIpPort = ipAndPort;
                        if (Minecraft.func_71410_x().field_71462_r instanceof WaitingModalGui) continue;
                        Minecraft.func_71410_x().func_71373_a((GuiScreen)new WaitingModalGui("join"));
                        continue;
                    }
                    if (ipAndPort == null) {
                        System.out.println("Server not found in server list");
                        return;
                    }
                    ClientSocket.connectPlayerToServer(ipAndPort);
                    continue;
                }
                if (!line.startsWith("ADDED_WAITLIST_FORCE")) continue;
                ClientData.waitingServerName = serverName = line.split(" ")[1];
                ClientData.waitingJoinTime = System.currentTimeMillis();
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        tryToReconnect = true;
        heartbeatThread = null;
        heartbeatDelayMillis = 5000L;
        wakeUpThread = null;
        pingThread = null;
        lastSocketPing = 0L;
    }
}

