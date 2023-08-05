package space.tscg.edsm.api.system.modal.body;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

@Getter
public class Discovery {
    
    @SerializedName("commander")
    private String commander;
    @SerializedName("date")
    private String date;
}
