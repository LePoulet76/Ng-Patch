package net.ilexiconn.nationsgui.forge.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import net.ilexiconn.nationsgui.forge.client.ClientSocket$1;
import net.ilexiconn.nationsgui.forge.client.ClientSocket$2;
import net.ilexiconn.nationsgui.forge.client.gui.CustomConnectingGui;
import net.ilexiconn.nationsgui.forge.client.gui.ServerSwitchExpressGui;
import net.ilexiconn.nationsgui.forge.client.gui.WaitingModalGui;
import net.ilexiconn.nationsgui.forge.client.gui.WaitingSocketGui;
import net.ilexiconn.nationsgui.forge.client.gui.main.MainGUI;
import net.ilexiconn.nationsgui.forge.server.util.SoundStreamer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.stats.StatList;
import org.json.utils.Utils;

public class ClientSocket
{
    public static final String serverAddress = "socket.nationsglory.fr";
    public static Scanner in;
    public static PrintWriter out;
    public static Socket socket;
    public static String clientName;
    private NetClientHandler netClientHandler = null;
    public static boolean tryToReconnect = true;
    public static Thread heartbeatThread = null;
    public static long heartbeatDelayMillis = 5000L;
    private static Thread wakeUpThread = null;
    private static Thread pingThread = null;
    public static long lastSocketPing = 0L;
    private static ClientSocket client;

    public ClientSocket(String name)
    {
        clientName = name;
        client = this;
        wakeUpThread = new Thread(new ClientSocket$1(this));
        wakeUpThread.start();
        lastSocketPing = System.currentTimeMillis();
        pingThread = new Thread(new ClientSocket$2(this));
        pingThread.start();
    }

    public static void connectPlayerToServer(String ipAndPort)
    {
        if (ipAndPort == null)
        {
            System.out.println("Server not found in server list");
        }
        else
        {
            ClientData.lastPlayerWantDisconnect = Long.valueOf(System.currentTimeMillis());
            String ip = ipAndPort.split(":")[0];
            int port = Integer.parseInt(ipAndPort.split(":")[1]);
            String targetServer = ClientProxy.getServerNameByIpAndPort(ipAndPort);

            if (targetServer == null || !targetServer.equalsIgnoreCase(ClientProxy.currentServerName))
            {
                ClientData.waitingServerName = null;
                ClientData.waitingPosition = 0;
                ClientData.waitingJoinTime = Long.valueOf(0L);

                if (WaitingSocketGui.player != null && WaitingSocketGui.player.isPlaying())
                {
                    WaitingSocketGui.player.forceClose();
                }

                try
                {
                    Iterator e = (new ArrayList(ClientProxy.STREAMER_LIST)).iterator();

                    while (e.hasNext())
                    {
                        SoundStreamer e21 = (SoundStreamer)e.next();
                        e21.forceClose();
                    }

                    ClientProxy.STREAMER_LIST.clear();
                    Minecraft.getMinecraft().displayGuiScreen(new ServerSwitchExpressGui(ip, port, targetServer));
                }
                catch (Exception var8)
                {
                    System.out.println(var8.getMessage());

                    try
                    {
                        if (Minecraft.getMinecraft().theWorld != null)
                        {
                            Minecraft.getMinecraft().statFileWriter.readStat(StatList.leaveGameStat, 1);
                            Minecraft.getMinecraft().theWorld.sendQuittingDisconnectingPacket();
                            Minecraft.getMinecraft().loadWorld((WorldClient)null);
                            Iterator e2 = (new ArrayList(ClientProxy.STREAMER_LIST)).iterator();

                            while (e2.hasNext())
                            {
                                SoundStreamer player = (SoundStreamer)e2.next();
                                player.forceClose();
                            }

                            ClientProxy.STREAMER_LIST.clear();
                        }

                        Minecraft.getMinecraft().displayGuiScreen(new CustomConnectingGui(new MainGUI(), ip, port, targetServer));
                    }
                    catch (Exception var7)
                    {
                        System.out.println(var7.getMessage());
                    }
                }
            }
        }
    }

    public void run()
    {
        try
        {
            lastSocketPing = System.currentTimeMillis();
            socket = new Socket("socket.nationsglory.fr", 59001);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);

            while (in.hasNextLine())
            {
                String e = in.nextLine();
                System.out.println("[Socket] DEBUG Receive message : " + e);
                String serverName;

                if (e.startsWith("SUBMITNAME"))
                {
                    serverName = clientName + "##" + Minecraft.getMinecraft().getSession().getSessionID() + "##" + Utils.toTweaker(MessageDigest.getInstance("SHA-256").digest(Utils.getTweaker().getBytes(StandardCharsets.UTF_8)));
                    System.out.println("DEBUG CLIENT SEND TO SOCKET: " + serverName);
                    out.println(serverName);
                }
                else if (e.startsWith("NAMEACCEPTED"))
                {
                    System.out.println("Connection accepted by server");

                    if (ClientData.waitingServerName != null && !ClientData.waitingServerName.equalsIgnoreCase("null"))
                    {
                        out.println("MESSAGE socket ADD_WAITINGLIST " + ClientData.waitingServerName);
                        ClientData.waitingJoinTime = Long.valueOf(System.currentTimeMillis());
                    }
                }
                else
                {
                    if (e.contains("NAMENOTACCEPTED"))
                    {
                        System.out.println("DEBUG DUPLICATE CONNECTION");
                        return;
                    }

                    if (!e.startsWith("MESSAGE"))
                    {
                        String ipAndPort;

                        if (!e.startsWith("PING_AND_DATA"))
                        {
                            if (e.startsWith("NEED_CONFIRMATION_JOIN_WAITING"))
                            {
                                serverName = e.split(" ")[1];

                                if (ClientProxy.currentServerName.equalsIgnoreCase(serverName))
                                {
                                    return;
                                }

                                ClientData.waitingServerNeedConfirmation = serverName;
                                Thread.sleep(1000L);

                                if (!ClientProxy.isPlayerOnServer())
                                {
                                    WaitingSocketGui.askForConfirmation = true;
                                }
                                else if (!(Minecraft.getMinecraft().currentScreen instanceof WaitingModalGui))
                                {
                                    Minecraft.getMinecraft().displayGuiScreen(new WaitingModalGui("switch"));
                                }
                            }
                            else if (e.startsWith("CONNECT_TO"))
                            {
                                Thread.sleep(1000L);
                                serverName = e.split(" ")[1];
                                ClientData.waitingServerName = serverName;
                                ipAndPort = e.split(" ")[2];

                                if (ClientProxy.currentServerName.equalsIgnoreCase(serverName))
                                {
                                    ClientData.waitingServerName = null;
                                }

                                if (ClientProxy.isPlayerOnServer() && !ClientProxy.currentServerName.equalsIgnoreCase("hub") && !ClientProxy.currentServerName.equalsIgnoreCase("accueil"))
                                {
                                    if (ClientData.isCombatTagged)
                                    {
                                        Minecraft.getMinecraft().thePlayer.addChatMessage(I18n.getString("waiting.combattagged").replaceAll("<server>", serverName));
                                        out.println("MESSAGE socket STEPBACK_WAITINGLIST");
                                    }
                                    else
                                    {
                                        ClientData.waitingServerIpPort = ipAndPort;

                                        if (!(Minecraft.getMinecraft().currentScreen instanceof WaitingModalGui))
                                        {
                                            Minecraft.getMinecraft().displayGuiScreen(new WaitingModalGui("join"));
                                        }
                                    }
                                }
                                else
                                {
                                    if (ipAndPort == null)
                                    {
                                        System.out.println("Server not found in server list");
                                        return;
                                    }

                                    connectPlayerToServer(ipAndPort);
                                }
                            }
                            else if (e.startsWith("ADDED_WAITLIST_FORCE"))
                            {
                                serverName = e.split(" ")[1];
                                ClientData.waitingServerName = serverName;
                                ClientData.waitingJoinTime = Long.valueOf(System.currentTimeMillis());
                            }
                        }
                        else
                        {
                            lastSocketPing = System.currentTimeMillis();
                            serverName = e.split(" ")[1];
                            ipAndPort = e.split(" ")[2];
                            int totalPlayersInWaitingList = Integer.parseInt(e.split(" ")[3]);
                            int playerPositionInWaitingList = Integer.parseInt(e.split(" ")[4]);
                            boolean isPlayerInWaitingList = Boolean.parseBoolean(e.split(" ")[5]);

                            if (ipAndPort.equals(ClientProxy.currentServerName) && !ClientProxy.currentServerName.equals("dev"))
                            {
                                ClientData.waitingServerName = null;
                                ClientData.waitingPosition = 0;
                                ClientData.waitingJoinTime = Long.valueOf(0L);
                                out.println("MESSAGE socket REMOVE_WAITINGLIST");
                            }
                            else
                            {
                                if (ClientData.waitingServerName == null || ClientData.waitingServerName.equalsIgnoreCase("null"))
                                {
                                    ClientData.waitingServerName = ipAndPort;
                                }

                                ClientData.waitingPosition = playerPositionInWaitingList;
                                ClientData.waitingTotal = totalPlayersInWaitingList;
                                ClientData.waitingPriority = isPlayerInWaitingList;

                                if (!ClientData.waitingServerName.equalsIgnoreCase("null") && (ClientData.waitingJoinTime == null || ClientData.waitingJoinTime.longValue() == 0L))
                                {
                                    ClientData.waitingJoinTime = Long.valueOf(System.currentTimeMillis());
                                }
                            }

                            out.println("MESSAGE socket PONG_CLIENT " + (!ClientProxy.currentServerName.equals("") ? ClientProxy.currentServerName : (ClientProxy.isPlayerOnServer() ? "other" : "null")));
                        }
                    }
                }
            }
        }
        catch (IOException var7)
        {
            System.out.println(var7.getMessage());
        }
        catch (InterruptedException var8)
        {
            var8.printStackTrace();
        }
        catch (NoSuchAlgorithmException var9)
        {
            throw new RuntimeException(var9);
        }
    }

    static ClientSocket access$000()
    {
        return client;
    }
}
