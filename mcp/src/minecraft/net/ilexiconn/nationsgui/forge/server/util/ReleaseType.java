/*
 * Decompiled with CFR 0.152.
 */
package net.ilexiconn.nationsgui.forge.server.util;

public enum ReleaseType {
    DEVELOP{

        @Override
        public String getBranding() {
            return "DEV";
        }
    }
    ,
    RELEASE_CANDIDATE{

        @Override
        public String getBranding() {
            return "RC";
        }
    }
    ,
    RELEASE{

        @Override
        public String getBranding() {
            return "";
        }
    };


    public abstract String getBranding();

    public static ReleaseType parseVersion(String version) {
        if (version.contains("-dev")) {
            return DEVELOP;
        }
        if (version.contains("-rc")) {
            return RELEASE_CANDIDATE;
        }
        return RELEASE;
    }
}

