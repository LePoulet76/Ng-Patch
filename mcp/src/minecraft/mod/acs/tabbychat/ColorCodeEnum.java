package acs.tabbychat;

public enum ColorCodeEnum
{
    DEFAULT("Default", ""),
    DARKBLUE("Dark Blue", "\u00a71"),
    DARKGREEN("Dark Green", "\u00a72"),
    DARKAQUA("Dark Aqua", "\u00a73"),
    DARKRED("Dark Red", "\u00a74"),
    PURPLE("Purple", "\u00a75"),
    GOLD("Gold", "\u00a76"),
    GRAY("Gray", "\u00a77"),
    DARKGRAY("Dark Gray", "\u00a78"),
    INDIGO("Indigo", "\u00a79"),
    BRIGHTGREEN("Bright Green", "\u00a7a"),
    AQUA("Aqua", "\u00a7b"),
    RED("Red", "\u00a7c"),
    PINK("Pink", "\u00a7d"),
    YELLOW("Yellow", "\u00a7e"),
    WHITE("White", "\u00a7f");
    private String title;
    private String code;

    private ColorCodeEnum(String _name, String _code)
    {
        this.title = _name;
        this.code = _code;
    }

    public String toString()
    {
        return this.code + this.title + "\u00a7r";
    }

    public String toCode()
    {
        return this.code;
    }

    public String color()
    {
        return this.title;
    }
}
