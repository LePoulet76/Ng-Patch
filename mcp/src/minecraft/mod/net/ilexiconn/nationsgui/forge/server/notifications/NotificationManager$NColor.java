package net.ilexiconn.nationsgui.forge.server.notifications;

public enum NotificationManager$NColor
{
    BLUE(-9537810),
    CYAN(-9518866),
    DARK_BLUE(-12891710),
    PURPLE(-5345554),
    PINK(-1090136),
    CORAL(-238491),
    RED(-776630),
    RUBY(-3668955),
    ORANGE(-28116),
    YELLOW(-606204),
    LIME(-8730273),
    GREEN(-11556787),
    WHITE(-1314054),
    BLACK(-14606034),
    GREY(-8224108);
    private final int colorCode;

    private NotificationManager$NColor(int colorCode)
    {
        this.colorCode = colorCode;
    }

    public int getColorCode()
    {
        return this.colorCode;
    }
}
