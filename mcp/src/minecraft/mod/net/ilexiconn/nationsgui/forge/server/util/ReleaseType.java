package net.ilexiconn.nationsgui.forge.server.util;

import net.ilexiconn.nationsgui.forge.server.util.ReleaseType$1;

public enum ReleaseType
{
    DEVELOP,
    RELEASE_CANDIDATE,
    RELEASE;

    public abstract String getBranding();

    public static ReleaseType parseVersion(String version)
    {
        return version.contains("-dev") ? DEVELOP : (version.contains("-rc") ? RELEASE_CANDIDATE : RELEASE);
    }

    ReleaseType(ReleaseType$1 x2)
    {
        this(x0, x1);
    }
}
