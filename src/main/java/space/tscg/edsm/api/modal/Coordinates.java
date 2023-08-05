package space.tscg.edsm.api.modal;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

@Getter
public class Coordinates
{
    @SerializedName("x")
    private double x;
    @SerializedName("y")
    private double y;
    @SerializedName("z")
    private double z;
    
    public double[] getAsArray()
    {
        return new double[]{x, y, z};
    }
    
    @Override
    public String toString()
    {
        return "%s, %s, %s".formatted(this.x, this.y, this.z);
    }
}
