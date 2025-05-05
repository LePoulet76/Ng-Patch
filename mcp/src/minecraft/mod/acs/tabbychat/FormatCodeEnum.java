package acs.tabbychat;

public enum FormatCodeEnum
{
    DEFAULT("Default", ""),
    BOLD("Bold", "\u00a7l"),
    STRIKED("Striked", "\u00a7m"),
    UNDERLINE("Underlined", "\u00a7n"),
    ITALIC("Italic", "\u00a7o");
    private String title;
    private String code;

    private FormatCodeEnum(String _name, String _code)
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
