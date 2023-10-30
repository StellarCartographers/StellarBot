package space.tscg.api.edsm.api.systems.modal;

import java.text.DecimalFormat;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

@Getter
public class Information {
    @SerializedName("allegiance")
	private String allegiance = "None";
    @SerializedName("government")
	private String government = "None";
    @SerializedName("faction")
	private String faction = "None";
	@SerializedName("factionState")
	private String factionState = "None";
	@SerializedName("population")
	private long population;
	@SerializedName("security")
	private String security = "Anarchy";
	@SerializedName("economy")
	private String economy = "None";
	
	public String getSmallFormatPopulation()
	{
        return shortFormat(population);
	}
	
    public static String shortFormat(long number)
    {
        String str = String.valueOf(number);
        double db;
        String unit = "";
        if (str.length() >= 10) {
            db = number / 10e8;
            unit = " Billion";
        } else if (str.length() >= 7) {
            db = number / 10e5;
            unit = " Million";
        } else if (str.length() >= 4) {
            db = number / 10e2;
            unit = " Thousand";
        } else {
            db = number;
            unit = " Hundred";
        }
        
        return new DecimalFormat("0.##").format(db) + unit;
    }
	
	public String getFormattedPopulation() {
		String unit = "";
		String number = String.valueOf(population);
		if (number.length() >= 10) {
			unit = "Billion";
		} else if (number.length() >= 7) {
			unit = "Million";
		} else if (number.length() >= 4) {
			unit = "Thousand";
		}
		DecimalFormat f = new DecimalFormat("#,###");
		
		
		return "%s %s".formatted(f.format(population), unit);
	}
}
