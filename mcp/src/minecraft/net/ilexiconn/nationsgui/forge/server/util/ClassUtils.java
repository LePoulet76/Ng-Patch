/*
 * Decompiled with CFR 0.152.
 */
package net.ilexiconn.nationsgui.forge.server.util;

public class ClassUtils {
    public static boolean classExist(String cla$$) {
        try {
            Class.forName(cla$$);
            return true;
        }
        catch (ClassNotFoundException e) {
            return false;
        }
    }
}

