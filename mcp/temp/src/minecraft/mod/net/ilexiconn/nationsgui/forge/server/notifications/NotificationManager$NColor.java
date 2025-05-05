package net.ilexiconn.nationsgui.forge.server.notifications;


public enum NotificationManager$NColor {

   BLUE("BLUE", 0, -9537810),
   CYAN("CYAN", 1, -9518866),
   DARK_BLUE("DARK_BLUE", 2, -12891710),
   PURPLE("PURPLE", 3, -5345554),
   PINK("PINK", 4, -1090136),
   CORAL("CORAL", 5, -238491),
   RED("RED", 6, -776630),
   RUBY("RUBY", 7, -3668955),
   ORANGE("ORANGE", 8, -28116),
   YELLOW("YELLOW", 9, -606204),
   LIME("LIME", 10, -8730273),
   GREEN("GREEN", 11, -11556787),
   WHITE("WHITE", 12, -1314054),
   BLACK("BLACK", 13, -14606034),
   GREY("GREY", 14, -8224108);
   private final int colorCode;
   // $FF: synthetic field
   private static final NotificationManager$NColor[] $VALUES = new NotificationManager$NColor[]{BLUE, CYAN, DARK_BLUE, PURPLE, PINK, CORAL, RED, RUBY, ORANGE, YELLOW, LIME, GREEN, WHITE, BLACK, GREY};


   private NotificationManager$NColor(String var1, int var2, int colorCode) {
      this.colorCode = colorCode;
   }

   public int getColorCode() {
      return this.colorCode;
   }

}
