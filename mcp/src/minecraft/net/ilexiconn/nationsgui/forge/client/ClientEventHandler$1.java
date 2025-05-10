package net.ilexiconn.nationsgui.forge.client;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

final class ClientEventHandler$1 extends HashMap<String, List<String>>
{
    ClientEventHandler$1()
    {
        this.put("chicken", Arrays.asList(new String[] {"wheat", "barley", "oats", "rye", "corn", "sunflower"}));
        this.put("pig", Arrays.asList(new String[] {"barley", "oats", "rye"}));
        this.put("cow", Arrays.asList(new String[] {"barley", "soybean", "corn"}));
    }
}
