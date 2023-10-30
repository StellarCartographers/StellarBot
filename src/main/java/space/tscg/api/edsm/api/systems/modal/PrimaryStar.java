package space.tscg.api.edsm.api.systems.modal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class PrimaryStar {
	private String type = "None";
	private String name = "None";
	@JsonProperty("isScoopable")
	private boolean scoopable;
}
