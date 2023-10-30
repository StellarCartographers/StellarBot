package space.tscg.internal.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder(builderMethodName = "Builder")
@Jacksonized
public class OwnerChannel
{
    private final String id;
    private Map<String, String> messages;
    
    public void setMessageId(OwnerNames name, String id)
    {
        this.messages.put(name.getName(), id);
    }
    
    public String getMessageId(OwnerNames name)
    {
        return this.messages.get(name.getName());
    }
    
    public static OwnerChannel create(String id)
    {
        return new OwnerChannel(id, populateMessages());
    }
    
    public static HashMap<String, String> populateMessages()
    {
        var map = new HashMap<String, String>();
        for (var name : OwnerNames.strings())
            map.put(name.toString().toLowerCase(), "unset");
        return map;
    }
    
    public enum OwnerNames implements Name
    {
        INFO,
        REFRESH,
        OPTIONS,
        ;
        
        @Override
        public String toString()
        {
            return super.toString().toLowerCase();
        }
        
        @Override
        public String getName()
        {
            return toString();
        }

        public static String[] strings()
        {
            var list = new ArrayList<String>();
            for (OwnerNames name : OwnerNames.values()) {
                list.add(name.getName());
            }
            return list.toArray(new String[0]);
        }
    }
}
