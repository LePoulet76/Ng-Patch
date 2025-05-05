package net.ilexiconn.nationsgui.forge.server.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateDeserializer implements JsonDeserializer<Date> {

   public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
      String date = json.getAsString();
      SimpleDateFormat formatter = new SimpleDateFormat("d/M/yy");
      formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

      try {
         return formatter.parse(date);
      } catch (ParseException var7) {
         var7.printStackTrace();
         return null;
      }
   }

   // $FF: synthetic method
   // $FF: bridge method
   public Object deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
      return this.deserialize(var1, var2, var3);
   }
}
