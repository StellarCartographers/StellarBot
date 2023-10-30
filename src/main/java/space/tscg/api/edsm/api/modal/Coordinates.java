package space.tscg.api.edsm.api.modal;

import lombok.Getter;

@Getter
public class Coordinates
{
    private double x;
    private double y;
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
