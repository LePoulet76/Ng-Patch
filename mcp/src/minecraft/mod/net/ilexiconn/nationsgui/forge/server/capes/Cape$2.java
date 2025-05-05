package net.ilexiconn.nationsgui.forge.server.capes;

import java.util.HashMap;
import org.json.simple.JSONObject;

final class Cape$2 extends HashMap<String, String>
{
    final JSONObject val$obj;

    Cape$2(JSONObject var1)
    {
        this.val$obj = var1;
        this.put("fr", (String)((JSONObject)this.val$obj.get("descriptions")).get("fr"));
        this.put("en", (String)((JSONObject)this.val$obj.get("descriptions")).get("en"));
    }
}
