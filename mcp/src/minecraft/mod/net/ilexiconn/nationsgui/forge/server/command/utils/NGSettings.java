package net.ilexiconn.nationsgui.forge.server.command.utils;

public class NGSettings
{
    public static NGSettings[] settings = new NGSettings[] {maxInPeriod = new NGSettings("maxInPeriod", 15, 0), repeaterPeriodDuration = new NGSettings("repeaterPeriodDuration", 10, 0), repeaterDelay = new NGSettings("repeaterDelay", 2, 0), maxPistonExtensionPerSecond = new NGSettings("maxPistonExtensionPerSecond", 4, 0), observerDelay = new NGSettings("observerDelay", 0, 0)};
    public static NGSettings maxInPeriod;
    public static NGSettings repeaterPeriodDuration;
    public static NGSettings repeaterDelay;
    public static NGSettings maxPistonExtensionPerSecond;
    public static NGSettings observerDelay;

    public NGSettings(String name, int defaultValue, int currentValue) {}
}
