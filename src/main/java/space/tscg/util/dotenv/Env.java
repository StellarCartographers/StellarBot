package space.tscg.util.dotenv;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class Env
{
    static final Map<String, Secret> MAP = new HashMap<>();
    
    public Env()
    {
        Dotenv.configure().ignoreIfMalformed().ignoreIfMissing().directory(".").load().entries().forEach(e -> {
            MAP.put(e.getKey(), new Secret("Dotenv", e.getValue()));
        });
        
        for(Entry<String, Secret> s : MAP.entrySet())
        {
            log.info("Loaded [{}] from [{}]", s.getKey(), s.getValue().source());
        }
    }
    
    public Env(Map<String, String> javaArgs)
    {
        Dotenv.configure().ignoreIfMalformed().ignoreIfMissing().directory(".").load().entries().forEach(e -> {
            MAP.put(e.getKey(), new Secret("Dotenv", e.getValue()));
        });
        javaArgs.entrySet().forEach(e -> {
            MAP.put(e.getKey(), new Secret("JavaArgs", e.getValue()));
        });
        
        for(Entry<String, Secret> s : MAP.entrySet())
        {
            log.info("Loaded [{}] from [{}]", s.getKey(), s.getValue().source());
        }
    }
}
