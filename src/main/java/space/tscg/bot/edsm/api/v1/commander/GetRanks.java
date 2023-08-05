
package space.tscg.bot.edsm.api.v1.commander;

import com.google.gson.annotations.SerializedName;

import elite.dangerous.EliteAPI;
import lombok.Getter;
import space.tscg.bot.edsm.api.v1.commander.modal.Progress;
import space.tscg.bot.edsm.api.v1.commander.modal.Ranks;
import space.tscg.bot.edsm.api.v1.commander.modal.RanksVerbose;

@Getter
public class GetRanks
{
    @SerializedName("ranks")
    private Ranks        ranks;
    @SerializedName("progress")
    private Progress     progress;
    @SerializedName("ranksVerbose")
    private RanksVerbose ranksVerbose;
    
    public void print()
    {
        System.out.println(EliteAPI.toJson(this));
    }
}
