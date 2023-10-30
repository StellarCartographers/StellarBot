
package space.tscg.api.edsm.api.commander;

import lombok.Getter;
import space.tscg.api.edsm.api.commander.modal.Progress;
import space.tscg.api.edsm.api.commander.modal.Ranks;
import space.tscg.api.edsm.api.commander.modal.RanksVerbose;

@Getter
public class GetRanks
{
    private Ranks        ranks;
    private Progress     progress;
    private RanksVerbose ranksVerbose;
}
