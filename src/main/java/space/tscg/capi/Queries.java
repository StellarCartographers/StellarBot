package space.tscg.capi;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import okhttp3.HttpUrl;

public final class Queries
{
    private final MultiValuedMap<String, String> queryMap = new ArrayListValuedHashMap<>();

    public Queries put(String key, String value)
    {
        this.queryMap.put(key, value);
        return this;
    }
    
    public void addToUrl(HttpUrl.Builder urlBuilder)
    {
        this.queryMap.asMap().forEach((k,v) -> {
            v.forEach(val -> {
               urlBuilder.addQueryParameter(k, val); 
            });
        });
    }
}
