package acs.tabbychat;


public enum FormatCodeEnum {

   DEFAULT("DEFAULT", 0, "Default", ""),
   BOLD("BOLD", 1, "Bold", "\u00a7l"),
   STRIKED("STRIKED", 2, "Striked", "\u00a7m"),
   UNDERLINE("UNDERLINE", 3, "Underlined", "\u00a7n"),
   ITALIC("ITALIC", 4, "Italic", "\u00a7o");
   private String title;
   private String code;
   // $FF: synthetic field
   private static final FormatCodeEnum[] $VALUES = new FormatCodeEnum[]{DEFAULT, BOLD, STRIKED, UNDERLINE, ITALIC};


   private FormatCodeEnum(String var1, int var2, String _name, String _code) {
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
