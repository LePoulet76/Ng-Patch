/*
 * Decompiled with CFR 0.152.
 */
package net.ilexiconn.nationsgui.forge.server.json;

public class RemoteButtonJSON {
    public String text;
    public String color;
    public int hexColor = -1;
    public boolean display;
    public String ip;
    public int port;

    public int getColor() {
        if (this.hexColor == -1 && this.color.startsWith("0x")) {
            String hex = this.color.split("0x")[1];
            while (hex.length() < 8) {
                hex = "F" + hex;
            }
            return (int)Long.parseLong(hex, 16);
        }
        return this.hexColor;
    }
}

