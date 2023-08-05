
package space.tscg.edsm.api.commander.modal;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

@Getter
public class RanksVerbose
{
    @SerializedName("Combat")
    private String combat;

    @SerializedName("Trade")
    private String trade;

    @SerializedName("Explore")
    private String explore;

    @SerializedName("Soldier")
    private String soldier;

    @SerializedName("Exobiologist")
    private String exobiologist;

    @SerializedName("CQC")
    private String cqc;

    @SerializedName("Federation")
    private String federation;

    @SerializedName("Empire")
    private String empire;
}
