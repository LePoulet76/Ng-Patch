/*
 * Decompiled with CFR 0.152.
 */
package net.ilexiconn.nationsgui.forge.server.permission;

public enum PermissionType {
    CATEGORY{

        @Override
        public String getPermission(String ... extra) {
            return String.format("nationsgui.category.%s", extra);
        }
    }
    ,
    MAIN_BADGE{

        @Override
        public String getPermission(String ... extra) {
            return String.format("nationsgui.mainbadge.%s", extra);
        }
    }
    ,
    SHOP{

        @Override
        public String getPermission(String ... extra) {
            return "nationsgui.shop.open";
        }
    }
    ,
    PREMIUM{

        @Override
        public String getPermission(String ... extra) {
            return "nationsgui.premium";
        }
    }
    ,
    ASSISTANCE_MODERATION{

        @Override
        public String getPermission(String ... extra) {
            return "nationsgui.assistance.moderator";
        }
    }
    ,
    RADIO_MODERATION{

        @Override
        public String getPermission(String ... extra) {
            return "nationsgui.radio.moderator";
        }
    }
    ,
    URL_ADMIN{

        @Override
        public String getPermission(String ... extra) {
            return "nationsgui.url.admin";
        }
    }
    ,
    GECKO_DIALOG_ADMIN{

        @Override
        public String getPermission(String ... extra) {
            return "nationsgui.gecko_dialog.admin";
        }
    };


    public abstract String getPermission(String ... var1);
}

