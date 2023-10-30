package space.tscg.api.edsm.api.system;

import java.util.List;

import lombok.Getter;
import space.tscg.api.edsm.api.system.modal.Body;

@Getter
public class GetSystemBodies
{
    private int           id;
    private long          id64;
    private String        url;
    private int           bodyCount;
    private List<Body<?>> bodies;
}
