package acs.tabbychat;


public enum NotificationSoundEnum {

   ORB("ORB", 0, "Orb", "random.orb"),
   ANVIL("ANVIL", 1, "Anvil", "random.anvil_land"),
   BOWHIT("BOWHIT", 2, "Bow Hit", "random.bowhit"),
   BREAK("BREAK", 3, "Break", "random.break"),
   CLICK("CLICK", 4, "Click", "random.click"),
   GLASS("GLASS", 5, "Glass", "random.glass"),
   BASS("BASS", 6, "Bass", "note.bassattack"),
   HARP("HARP", 7, "Harp", "note.harp"),
   PLING("PLING", 8, "Pling", "note.pling"),
   CAT("CAT", 9, "Cat", "mob.cat.meow");
   private String title;
   private String file;
   // $FF: synthetic field
   private static final NotificationSoundEnum[] $VALUES = new NotificationSoundEnum[]{ORB, ANVIL, BOWHIT, BREAK, CLICK, GLASS, BASS, HARP, PLING, CAT};


   private NotificationSoundEnum(String var1, int var2, String _title, String _file) {
      this.title = _title;
      this.file = _file;
   }

   public String toString() {
      return this.title;
   }

   public String file() {
      return this.file;
   }

}
