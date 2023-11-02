package space.tscg.api.edsm.api.system.modal;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import space.tscg.api.edsm.api.system.modal.body.Discovery;
import space.tscg.api.edsm.api.system.modal.body.Ring;

@Getter
public class Planet extends Body
{
    private int       id;
    private long      id64;
    private int       bodyId;
    private String    name;
    private String    type;
    private String    subType;
    private Discovery discovery;
    private int       distanceToArrival;
    private int       surfaceTemperature;
    private double    orbitalPeriod;
    private double    semiMajorAxis;
    private double    orbitalEccentricity;
    private double    orbitalInclination;
    private double    argOfPeriapsis;
    private double    rotationalPeriod;
    private boolean   tidallyLocked;
    private double    axialTilt;
    
    private boolean landable;
    private double gravity;
    private double earthMasses;
    private double radius;
    private String volcanismType;
    private String atmosphereType;
    private Map<String, Double> atmosphereComposition;
    private Map<String, Double> solidComposition;
    private String terraformingState;
    private Map<String, Double> materials;
    private String updateTime;
    private List<Ring> rings;
}
