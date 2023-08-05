package space.tscg.bot.edsm.api.system;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import elite.dangerous.EliteAPI;
import lombok.Getter;
import space.tscg.bot.edsm.api.system.modal.Body;

@Getter
public class GetSystemBodies
{
    @SerializedName("id")
    private int id;
    @SerializedName("id64")
    private long id64;
    @SerializedName("url")
    private String url;
    @SerializedName("bodyCount")
    private int bodyCount;
    @SerializedName("bodies")
    private List<Body<?>> bodies;
    
    public void print()
    {
        System.out.println(EliteAPI.toJson(this));
    }
}
