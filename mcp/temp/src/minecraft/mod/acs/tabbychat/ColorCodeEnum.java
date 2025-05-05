package acs.tabbychat;


public enum ColorCodeEnum {

   DEFAULT("DEFAULT", 0, "Default", ""),
   DARKBLUE("DARKBLUE", 1, "Dark Blue", "\u00a71"),
   DARKGREEN("DARKGREEN", 2, "Dark Green", "\u00a72"),
   DARKAQUA("DARKAQUA", 3, "Dark Aqua", "\u00a73"),
   DARKRED("DARKRED", 4, "Dark Red", "\u00a74"),
   PURPLE("PURPLE", 5, "Purple", "\u00a75"),
   GOLD("GOLD", 6, "Gold", "\u00a76"),
   GRAY("GRAY", 7, "Gray", "\u00a77"),
   DARKGRAY("DARKGRAY", 8, "Dark Gray", "\u00a78"),
   INDIGO("INDIGO", 9, "Indigo", "\u00a79"),
   BRIGHTGREEN("BRIGHTGREEN", 10, "Bright Green", "\u00a7a"),
   AQUA("AQUA", 11, "Aqua", "\u00a7b"),
   RED("RED", 12, "Red", "\u00a7c"),
   PINK("PINK", 13, "Pink", "\u00a7d"),
   YELLOW("YELLOW", 14, "Yellow", "\u00a7e"),
   WHITE("WHITE", 15, "White", "\u00a7f");
   private String title;
   private String code;
   // $FF: synthetic field
   private static final ColorCodeEnum[] $VALUES = new ColorCodeEnum[]{DEFAULT, DARKBLUE, DARKGREEN, DARKAQUA, DARKRED, PURPLE, GOLD, GRAY, DARKGRAY, INDIGO, BRIGHTGREEN, AQUA, RED, PINK, YELLOW, WHITE};


   private ColorCodeEnum(String var1, int var2, String _name, String _code) {
      this.title = _name;
      this.code = _code;
   }

   public String toString() {
      return this.code + this.title + "\u00a7r";
   }

   public String toCode() {
      return this.code;
   }

   public String color() {
      return this.title;
   }

}
