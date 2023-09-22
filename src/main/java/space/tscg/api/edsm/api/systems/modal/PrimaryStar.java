package space.tscg.api.edsm.api.systems.modal;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

@Getter
public class PrimaryStar {
    @SerializedName("type")
	private String type = "None";
    @SerializedName("name")
	private String name = "None";
	@SerializedName("isScoopable")
	private boolean scoopable;
}
