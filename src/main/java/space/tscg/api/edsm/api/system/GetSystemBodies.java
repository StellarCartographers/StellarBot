package space.tscg.api.edsm.api.system;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import space.tscg.api.edsm.api.system.modal.Body;
import space.tscg.api.edsm.api.system.modal.Planet;
import space.tscg.api.edsm.api.system.modal.Star;

@Getter
public class GetSystemBodies
{
    private int        id;
    private long       id64;
    private String     url;
    private int        bodyCount;
    private List<Body> bodies;
    
    @JsonIgnore
    public List<Planet> getPlanetList()
    {
        return this.bodies.stream().filter(b -> b.getClass().getSimpleName().equals("Planet")).map(b -> (Planet) b).collect(Collectors.toList());
    }
    
    @JsonIgnore
    public List<Star> getStarList()
    {
        return this.bodies.stream().filter(b -> b.getClass().getSimpleName().equals("Star")).map(b -> (Star) b).collect(Collectors.toList());
    }
}
