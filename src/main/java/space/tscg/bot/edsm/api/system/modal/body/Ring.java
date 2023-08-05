package space.tscg.bot.edsm.api.system.modal.body;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

@Getter
public class Ring
{
    @SerializedName("name")
    private String name;
    @SerializedName("type")
    private String type;
    @SerializedName("mass")
    private long mass;
    @SerializedName("innerRadius")
    private int innerRadius;
    @SerializedName("outerRadius")
    private int outerRadius;
}
