package acs.tabbychat;


public enum ChannelDelimEnum {

   ANGLES("ANGLES", 0, "<Angles>", "<", ">"),
   BRACES("BRACES", 1, "{Braces}", "{", "}"),
   BRACKETS("BRACKETS", 2, "[Brackets]", "[", "]"),
   PARENTHESIS("PARENTHESIS", 3, "(Parenthesis)", "(", ")"),
   ANGLESPARENSCOMBO("ANGLESPARENSCOMBO", 4, "<(Combo)Pl.>", "<\\(", ")( |\u00a7r)*[A-Za-z0-9_]{1,16}>"),
   ANGLESBRACKETSCOMBO("ANGLESBRACKETSCOMBO", 5, "<[Combo]Pl.>", "<\\[", "]( |\u00a7r)*[A-Za-z0-9_]{1,16}>");
   private String title;
   private char open;
   private char close;
   // $FF: synthetic field
   private static final ChannelDelimEnum[] $VALUES = new ChannelDelimEnum[]{ANGLES, BRACES, BRACKETS, PARENTHESIS, ANGLESPARENSCOMBO, ANGLESBRACKETSCOMBO};


   private ChannelDelimEnum(String var1, int var2, String title, String open, String close) {
      this.title = title;
      this.open = open.charAt(0);
      this.close = close.charAt(0);
   }

   public String getTitle() {
      return this.title;
   }

   public String toString() {
      return this.title;
   }

   public void setValue(String _title) {
      ChannelDelimEnum[] var2 = values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ChannelDelimEnum tmp = var2[var4];
         if(_title.equals(tmp.title)) {
            this.title = tmp.title;
            this.open = tmp.open;
            this.close = tmp.close;
            break;
         }
      }

   }

   public String open() {
      return Character.toString(this.open);
   }

   public String close() {
      return Character.toString(this.close);
   }

   protected ChannelDelimEnum next() {
      return this.equals(ANGLES)?BRACES:(this.equals(BRACES)?BRACKETS:(this.equals(BRACKETS)?PARENTHESIS:ANGLES));
   }

}
