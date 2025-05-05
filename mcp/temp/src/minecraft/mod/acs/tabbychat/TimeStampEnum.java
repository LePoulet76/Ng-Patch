package acs.tabbychat;


public enum TimeStampEnum {

   MILITARY("MILITARY", 0, "[HHmm]", "[2359]", "\\[[0-9]{4}\\]"),
   MILITARYWITHCOLON("MILITARYWITHCOLON", 1, "[HH:mm]", "[23:59]", "\\[[0-9]{2}:[0-9]{2}\\["),
   STANDARD("STANDARD", 2, "[hh:mm]", "[12:00]", "\\[[0-9]{2}:[0-9]{2}\\]"),
   STANDARDWITHMARKER("STANDARDWITHMARKER", 3, "[hh:mma]", "[12:00PM]", "\\[[0-9]{2}:[0-9]{2}(AM|PM)\\]"),
   MILITARYSECONDS("MILITARYSECONDS", 4, "[HH:mm:ss]", "[23:59:01]", "\\[[0-9]{2}:[0-9]{2}:[0-9]{2}\\]"),
   STANDARDSECONDS("STANDARDSECONDS", 5, "[hh:mm:ss]", "[12:00:01]", "\\[[0-9]{2}:[0-9]{2}:[0-9]{2}\\]"),
   STANDARDSECONDSMARKER("STANDARDSECONDSMARKER", 6, "[hh:mm:ssa]", "[12:00:01PM]", "\\[[0-9]{2}:[0-9]{2}:[0-9]{2}(AM|PM)\\]");
   private String code;
   public String maxTime;
   protected String regEx;
   // $FF: synthetic field
   private static final TimeStampEnum[] $VALUES = new TimeStampEnum[]{MILITARY, MILITARYWITHCOLON, STANDARD, STANDARDWITHMARKER, MILITARYSECONDS, STANDARDSECONDS, STANDARDSECONDSMARKER};


   private TimeStampEnum(String var1, int var2, String _code, String _maxTime, String _regex) {
      this.code = _code;
      this.maxTime = _maxTime;
      this.regEx = _regex;
   }

   public String toString() {
      return this.maxTime;
   }

   public String toCode() {
      return this.code;
   }

}
