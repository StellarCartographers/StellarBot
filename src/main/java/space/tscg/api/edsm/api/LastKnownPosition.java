package space.tscg.api.edsm.api;

import com.google.gson.annotations.SerializedName;

import elite.dangerous.EliteAPI;
import lombok.Getter;
import space.tscg.api.edsm.api.modal.Coordinates;

@Getter
public class LastKnownPosition
{
    @SerializedName("msgNum")
    private int messageNumber;
    @SerializedName("system")
    private String system;
    @SerializedName("systemId")
    private int systemId;
    @SerializedName("firstDiscover")
    private boolean firstDiscover;
    @SerializedName("date")
    private String date;
    @SerializedName("coordinates")
    private Coordinates coordinates;
    @SerializedName("url")
    private String url;
    
    public void print()
    {
        System.out.println(EliteAPI.toJson(this));
    }
}
