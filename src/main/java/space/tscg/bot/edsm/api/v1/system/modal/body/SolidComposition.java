
package space.tscg.bot.edsm.api.v1.system.modal.body;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

@Getter
public class SolidComposition {

    @SerializedName("metal")
    private int metal;
    @SerializedName("ice")
    private int ice;
    @SerializedName("rock")
    private int rock;

}
