/*
 * Decompiled with CFR 0.152.
 */
package acs.tabbychat;

public enum ChatColorEnum {
    DEFAULTCOLOR("Any", 0, ""),
    BLACK("Black", 0, "\u00a70"),
    DARKBLUE("Dark Blue", 0, "\u00a71"),
    DARKGREEN("Dark Green", 0, "\u00a72"),
    DARKAQUA("Dark Aqua", 0, "\u00a73"),
    DARKRED("Dark Red", 0, "\u00a74"),
    PURPLE("Purple", 0, "\u00a75"),
    GOLD("Gold", 0, "\u00a76"),
    GRAY("Gray", 0, "\u00a77"),
    DARKGRAY("Dark Gray", 0, "\u00a78"),
    INDIGO("Indigo", 0, "\u00a79"),
    BRIGHTGREEN("Bright Green", 0, "\u00a7a"),
    AQUA("Aqua", 0, "\u00a7b"),
    RED("Red", 0, "\u00a7c"),
    PINK("Pink", 0, "\u00a7d"),
    YELLOW("Yellow", 0, "\u00a7e"),
    WHITE("White", 0, "\u00a7f"),
    DEFAULTFORMAT("Any", 1, ""),
    BOLD("Bold", 1, "\u00a7l"),
    STRIKED("Striked", 1, "\u00a7m"),
    UNDERLINE("Underlined", 1, "\u00a7n"),
    ITALIC("Italic", 1, "\u00a7o"),
    RESET("Reset", 2, "\u00a7r");

    private String title;
    private int type;
    private String code;
    private static final int color = 0;
    private static final int format = 1;
    private static final int normal = 2;

    private ChatColorEnum(String _name, int _type, String _code) {
        this.title = _name;
        this.type = _type;
        this.code = _code;
    }

    public String toString() {
        return this.title;
    }

    public String getCode() {
        return this.code;
    }

    public ChatColorEnum nextColor() {
        if (this.type != 0) {
            return DEFAULTCOLOR;
        }
        ChatColorEnum[] theList = ChatColorEnum.values();
        int i = this.ordinal();
        if (++i < theList.length && theList[i].type == 0) {
            return theList[i];
        }
        return RESET.nextColor();
    }

    public ChatColorEnum prevColor() {
        if (this.type != 0) {
            return WHITE;
        }
        ChatColorEnum[] theList = ChatColorEnum.values();
        int i = this.ordinal();
        if (--i >= 0 && theList[i].type == 0) {
            return theList[i];
        }
        return RESET.prevColor();
    }

    public ChatColorEnum nextFormat() {
        if (this.type != 1) {
            return DEFAULTFORMAT;
        }
        ChatColorEnum[] theList = ChatColorEnum.values();
        int i = this.ordinal();
        if (++i < theList.length && theList[i].type == 1) {
            return theList[i];
        }
        return RESET.nextFormat();
    }

    public ChatColorEnum prevFormat() {
        if (this.type != 1) {
            return ITALIC;
        }
        ChatColorEnum[] theList = ChatColorEnum.values();
        int i = this.ordinal();
        if (--i >= 0 && theList[i].type == 1) {
            return theList[i];
        }
        return RESET.prevFormat();
    }
}

