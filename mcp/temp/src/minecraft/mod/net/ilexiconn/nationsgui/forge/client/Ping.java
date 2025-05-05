package net.ilexiconn.nationsgui.forge.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ping {

   private String cmdLine;
   private String osName = System.getProperty("os.name");


   public Ping(String host) {
      if(this.osName.contains("indows")) {
         this.cmdLine = "ping -n 3 " + host;
      } else {
         this.cmdLine = "ping " + host + " -c 3";
      }

   }

   public static String cmdExec(String cmdLine) {
      String output = "";

      try {
         Process p = Runtime.getRuntime().exec(cmdLine);

         String line;
         BufferedReader input;
         for(input = new BufferedReader(new InputStreamReader(p.getInputStream())); (line = input.readLine()) != null; output = output + line + '\n') {
            ;
         }

         input.close();
      } catch (Exception var5) {
         ;
      }

      return output;
   }

   public double run() {
      try {
         String ex = cmdExec(this.cmdLine);
         Pattern pattern;
         if(this.osName.contains("indows")) {
            pattern = Pattern.compile("([0-9]*)ms$");
         } else {
            pattern = Pattern.compile(" = (.*?)/(.*?)/");
         }

         Matcher matcher = pattern.matcher(ex);
         matcher.find();
         double avg;
         if(this.osName.contains("indows")) {
            avg = Double.parseDouble(matcher.group(1));
         } else {
            avg = Double.parseDouble(matcher.group(2));
         }

         return avg;
      } catch (Exception var6) {
         return 0.0D;
      }
   }
}
