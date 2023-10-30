package space.tscg.internal.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder(builderMethodName = "Builder")
@Jacksonized
public class PublicChannel
{
    public final String id;
    public Map<String, String> messages;
    
    public void setMessageId(Name name, String id)
    {
        this.messages.put(name.getName(), id);
    }
    
    public String getMessageId(Name name)
    {
        if(this.isHidden(name))
            return this.messages.get(name.getName().split("::")[0]);
        else
            return this.messages.get(name.getName());
    }
    
    public static PublicChannel create(String id)
    {
        return new PublicChannel(id, populateMessages());
    }
    
    public static HashMap<String, String> populateMessages()
    {
        var map = new HashMap<String, String>();
        for (var name : PublicNames.strings())
            map.put(name.toString().toLowerCase(), "unset");
        return map;
    }
    
    public void setHidden(Name key)
    {
        String currentVal = (String) messages.get(key.getName());
        if(!currentVal.endsWith("::hidden"))
        {
            currentVal = currentVal + "::hidden";
            messages.put(key.getName(), currentVal);
        }
    }
    
    public void setVisable(Name key)
    {
        String currentVal = messages.get(key.getName());
        if(currentVal.endsWith("::hidden"))
        {
            currentVal = currentVal.replace("::hidden", "");
            messages.put(key.getName(), currentVal);
        }
    }
    
    public boolean isHidden(Name key)
    {
        return messages.get(key.getName()).endsWith("::hidden");
    }
    
    public enum PublicNames implements Name
    {
        LOCATION,
        SERVICES,
        ORDERS,
        SHIPYARD,
        OUTFITTING,
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
            for (PublicNames name : PublicNames.values()) {
                list.add(name.getName());
            }
            return list.toArray(new String[0]);
        }
    }
}
