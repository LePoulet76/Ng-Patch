package acs.tabbychat;


public enum ChatColorEnum {

   DEFAULTCOLOR("DEFAULTCOLOR", 0, "Any", 0, ""),
   BLACK("BLACK", 1, "Black", 0, "\u00a70"),
   DARKBLUE("DARKBLUE", 2, "Dark Blue", 0, "\u00a71"),
   DARKGREEN("DARKGREEN", 3, "Dark Green", 0, "\u00a72"),
   DARKAQUA("DARKAQUA", 4, "Dark Aqua", 0, "\u00a73"),
   DARKRED("DARKRED", 5, "Dark Red", 0, "\u00a74"),
   PURPLE("PURPLE", 6, "Purple", 0, "\u00a75"),
   GOLD("GOLD", 7, "Gold", 0, "\u00a76"),
   GRAY("GRAY", 8, "Gray", 0, "\u00a77"),
   DARKGRAY("DARKGRAY", 9, "Dark Gray", 0, "\u00a78"),
   INDIGO("INDIGO", 10, "Indigo", 0, "\u00a79"),
   BRIGHTGREEN("BRIGHTGREEN", 11, "Bright Green", 0, "\u00a7a"),
   AQUA("AQUA", 12, "Aqua", 0, "\u00a7b"),
   RED("RED", 13, "Red", 0, "\u00a7c"),
   PINK("PINK", 14, "Pink", 0, "\u00a7d"),
   YELLOW("YELLOW", 15, "Yellow", 0, "\u00a7e"),
   WHITE("WHITE", 16, "White", 0, "\u00a7f"),
   DEFAULTFORMAT("DEFAULTFORMAT", 17, "Any", 1, ""),
   BOLD("BOLD", 18, "Bold", 1, "\u00a7l"),
   STRIKED("STRIKED", 19, "Striked", 1, "\u00a7m"),
   UNDERLINE("UNDERLINE", 20, "Underlined", 1, "\u00a7n"),
   ITALIC("ITALIC", 21, "Italic", 1, "\u00a7o"),
   RESET("RESET", 22, "Reset", 2, "\u00a7r");
   private String title;
   private int type;
   private String code;
   private static final int color = 0;
   private static final int format = 1;
   private static final int normal = 2;
   // $FF: synthetic field
   private static final ChatColorEnum[] $VALUES = new ChatColorEnum[]{DEFAULTCOLOR, BLACK, DARKBLUE, DARKGREEN, DARKAQUA, DARKRED, PURPLE, GOLD, GRAY, DARKGRAY, INDIGO, BRIGHTGREEN, AQUA, RED, PINK, YELLOW, WHITE, DEFAULTFORMAT, BOLD, STRIKED, UNDERLINE, ITALIC, RESET};


   private ChatColorEnum(String var1, int var2, String _name, int _type, String _code) {
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
      if(this.type != 0) {
         return DEFAULTCOLOR;
      } else {
         ChatColorEnum[] theList = values();
         int i = this.ordinal();
         ++i;
         return i < theList.length && theList[i].type == 0?theList[i]:RESET.nextColor();
      }
   }

   public ChatColorEnum prevColor() {
      if(this.type != 0) {
         return WHITE;
      } else {
         ChatColorEnum[] theList = values();
         int i = this.ordinal();
         --i;
         return i >= 0 && theList[i].type == 0?theList[i]:RESET.prevColor();
      }
   }

   public ChatColorEnum nextFormat() {
      if(this.type != 1) {
         return DEFAULTFORMAT;
      } else {
         ChatColorEnum[] theList = values();
         int i = this.ordinal();
         ++i;
         return i < theList.length && theList[i].type == 1?theList[i]:RESET.nextFormat();
      }
   }

   public ChatColorEnum prevFormat() {
      if(this.type != 1) {
         return ITALIC;
      } else {
         ChatColorEnum[] theList = values();
         int i = this.ordinal();
         --i;
         return i >= 0 && theList[i].type == 1?theList[i]:RESET.prevFormat();
      }
   }

}
