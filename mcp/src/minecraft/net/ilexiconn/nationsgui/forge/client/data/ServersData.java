/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package net.ilexiconn.nationsgui.forge.client.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;

public class ServersData {
    private Map<String, ServerGroup> permanentServers = new HashMap<String, ServerGroup>();
    private Map<String, Server> temporaryServers = new HashMap<String, Server>();
    private Map<String, Map<String, Map<String, String>>> lang = new HashMap<String, Map<String, Map<String, String>>>();

    public Map<String, ServerGroup> getPermanentServers() {
        return this.permanentServers;
    }

    public Map<String, Server> getTemporaryServers() {
        return this.temporaryServers;
    }

    public Map<String, Map<String, Map<String, String>>> getLang() {
        return this.lang;
    }

    public class Server {
        private boolean isOnline = true;
        private String ip = "";
        private String backgroundIconColor = "";
        private String iconTexture = "";
        private List<String> zone = null;
        private int slots = -1;
        private boolean invertTextColor = false;

        public boolean isOnline() {
            return this.isOnline;
        }

        public String getIp() {
            return this.ip;
        }

        public String getBackgroundIconColor() {
            return this.backgroundIconColor;
        }

        public List<String> getZones() {
            return this.zone;
        }

        public String getIconTexture() {
            return this.iconTexture;
        }

        public boolean isVisible(Minecraft mc) {
            return mc.func_110432_I().func_111286_b() != null && !mc.func_110432_I().func_111286_b().equals("took") || !this.isOnline();
        }

        public int getSlots() {
            return this.slots;
        }

        public void setSlots(int slots) {
            this.slots = slots;
        }

        public boolean isInvertTextColor() {
            return this.invertTextColor;
        }
    }

    public class ServerGroup {
        private String iconTexture = "";
        private Map<String, Server> serverList = new HashMap<String, Server>();

        public String getIconTexture() {
            return this.iconTexture;
        }

        public Map<String, Server> getServerMap() {
            return this.serverList;
        }
    }
}

