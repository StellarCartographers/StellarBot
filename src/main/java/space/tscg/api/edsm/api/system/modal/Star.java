package space.tscg.api.edsm.api.system.modal;

import lombok.Getter;
import space.tscg.api.edsm.api.system.modal.body.Discovery;

@Getter
public class Star extends Body
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
    
    private boolean isMainStar;
    private boolean isScoopable;
    private int age;
    private String luminosity;
    private double absoluteMagnitude;
    private double solarMasses;
    private double solarRadius;
}
