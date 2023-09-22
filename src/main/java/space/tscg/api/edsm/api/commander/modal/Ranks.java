
package space.tscg.api.edsm.api.commander.modal;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

@Getter
public class Ranks
{
    @SerializedName("Combat")
    private int combat;

    @SerializedName("Trade")
    private int trade;

    @SerializedName("Explore")
    private int explore;

    @SerializedName("Soldier")
    private int soldier;

    @SerializedName("Exobiologist")
    private int exobiologist;

    @SerializedName("CQC")
    private int cqc;

    @SerializedName("Federation")
    private int federation;

    @SerializedName("Empire")
    private int empire;
}
