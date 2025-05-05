package acs.tabbychat;

public enum ChannelDelimEnum
{
    ANGLES("<Angles>", "<", ">"),
    BRACES("{Braces}", "{", "}"),
    BRACKETS("[Brackets]", "[", "]"),
    PARENTHESIS("(Parenthesis)", "(", ")"),
    ANGLESPARENSCOMBO("<(Combo)Pl.>", "<\\(", ")( |\u00a7r)*[A-Za-z0-9_]{1,16}>"),
    ANGLESBRACKETSCOMBO("<[Combo]Pl.>", "<\\[", "]( |\u00a7r)*[A-Za-z0-9_]{1,16}>");
    private String title;
    private char open;
    private char close;

    private ChannelDelimEnum(String title, String open, String close)
    {
        this.title = title;
        this.open = open.charAt(0);
        this.close = close.charAt(0);
    }

    public String getTitle()
    {
        return this.title;
    }

    public String toString()
    {
        return this.title;
    }

    public void setValue(String _title)
    {
        ChannelDelimEnum[] var2 = values();
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4)
        {
            ChannelDelimEnum tmp = var2[var4];

            if (_title.equals(tmp.title))
            {
                this.title = tmp.title;
                this.open = tmp.open;
                this.close = tmp.close;
                break;
            }
        }
    }

    public String open()
    {
        return Character.toString(this.open);
    }

    public String close()
    {
        return Character.toString(this.close);
    }

    protected ChannelDelimEnum next()
    {
        return this.equals(ANGLES) ? BRACES : (this.equals(BRACES) ? BRACKETS : (this.equals(BRACKETS) ? PARENTHESIS : ANGLES));
    }
}
