package space.tscg.api.edsm.api.system.modal;

import java.util.function.Supplier;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import space.tscg.api.edsm.api.system.modal.adapter.Deserialization;
import space.tscg.api.edsm.api.system.modal.body.Discovery;
import space.tscg.api.edsm.api.system.modal.body.Parents;

@Getter
public abstract class Body<T extends Body<T>> implements Supplier<T>
{
    @SerializedName("id")
    private int id;
    @SerializedName("id64")
    private long id64;
    @SerializedName("bodyId")
    private int bodyId;
    @SerializedName("name")
    private String name;
    @SerializedName("type")
    private String type;
    @SerializedName("subType")
    private String subType;
    @JsonAdapter(Deserialization.ParentAdapter.class)
    @SerializedName("parents")
    private Parents parents;
    @SerializedName("discovery")
    private Discovery discovery;
    @SerializedName("distanceToArrival")
    private int distanceToArrival;
    @SerializedName("surfaceTemperature")
    private int surfaceTemperature;
    @SerializedName("orbitalPeriod")
    private double orbitalPeriod;
    @SerializedName("semiMajorAxis")
    private double semiMajorAxis;
    @SerializedName("orbitalEccentricity")
    private double orbitalEccentricity;
    @SerializedName("orbitalInclination")
    private double orbitalInclination;
    @SerializedName("argOfPeriapsis")
    private double argOfPeriapsis;
    @SerializedName("rotationalPeriod")
    private double rotationalPeriod;
    @SerializedName("rotationalPeriodTidallyLocked")
    private boolean tidallyLocked;
    @SerializedName("axialTilt")
    private double axialTilt;
}
