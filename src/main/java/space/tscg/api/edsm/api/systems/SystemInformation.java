package space.tscg.api.edsm.api.systems;

import com.google.gson.annotations.SerializedName;

import elite.dangerous.EliteAPI;
import lombok.Getter;
import space.tscg.api.edsm.api.modal.Coordinates;
import space.tscg.api.edsm.api.systems.modal.Information;
import space.tscg.api.edsm.api.systems.modal.PrimaryStar;

@Getter
public class SystemInformation
{
    @SerializedName("name")
    private String name;
    @SerializedName("coords")
    private Coordinates coordinates;
    @SerializedName("coordsLocked")
    private boolean coordsLocked;
    @SerializedName("requirePermit")
    private boolean permitLocked;
    @SerializedName("permitName")
    private String permitName;
    @SerializedName("information")
    private Information information = new Information();
    @SerializedName("primaryStar")
    private PrimaryStar primaryStar = new PrimaryStar();
    
    public boolean hasCoordinates()
    {
    	return this.coordinates != null;
    }
    
    public boolean hasExtraInformation()
    {
    	return this.information.getPopulation() > 0;
    }
    
    public void print()
    {
        System.out.println(EliteAPI.toJson(this));
    }
}
