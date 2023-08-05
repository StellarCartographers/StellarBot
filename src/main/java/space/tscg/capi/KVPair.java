package space.tscg.capi;

import lombok.Getter;
import lombok.Setter;

@Getter
class KVPair
{
    private final String k;
    @Setter
    private String v;
    
    public static KVPair of(String name)
    {
        return of(name, null);
    }
    
    public static KVPair of(String name, String value)
    {
        return new KVPair(name, value);
    }
    
    private KVPair(String name)
    {
        this(name, null);
    }
    
    private KVPair(String name, String value)
    {
        this.k = name;
        this.v = value;
    }
}
