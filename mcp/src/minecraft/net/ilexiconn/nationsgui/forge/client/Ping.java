/*
 * Decompiled with CFR 0.152.
 */
package net.ilexiconn.nationsgui.forge.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ping {
    private String cmdLine;
    private String osName = System.getProperty("os.name");

    public Ping(String host) {
        this.cmdLine = this.osName.contains("indows") ? "ping -n 3 " + host : "ping " + host + " -c 3";
    }

    public static String cmdExec(String cmdLine) {
        String output = "";
        try {
            String line;
            Process p = Runtime.getRuntime().exec(cmdLine);
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                output = output + line + '\n';
            }
            input.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
        return output;
    }

    public double run() {
        try {
            String cmdOutput = Ping.cmdExec(this.cmdLine);
            Pattern pattern = this.osName.contains("indows") ? Pattern.compile("([0-9]*)ms$") : Pattern.compile(" = (.*?)/(.*?)/");
            Matcher matcher = pattern.matcher(cmdOutput);
            matcher.find();
            double avg = this.osName.contains("indows") ? Double.parseDouble(matcher.group(1)) : Double.parseDouble(matcher.group(2));
            return avg;
        }
        catch (Exception ex) {
            return 0.0;
        }
    }
}

