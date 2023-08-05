package space.tscg.edsm.api.system.modal;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

@Getter
public class Star extends Body<Star>
{
    @SerializedName("isMainStar")
    private boolean mainStar;
    @SerializedName("isScoopable")
    private boolean scoopable;
    @SerializedName("age")
    private int age;
    @SerializedName("luminosity")
    private String luminosity;
    @SerializedName("absoluteMagnitude")
    private double absoluteMagnitude;
    @SerializedName("solarMasses")
    private double solarMasses;
    @SerializedName("solarRadius")
    private double solarRadius;
    
    @Override
    public Star get()
    {
        return this;
    }
}
