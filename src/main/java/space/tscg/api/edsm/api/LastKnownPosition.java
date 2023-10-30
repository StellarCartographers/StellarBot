package space.tscg.api.edsm.api;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import space.tscg.api.edsm.api.modal.Coordinates;

@Getter
public class LastKnownPosition
{
    @JsonIgnore
    private final String urlTemplate = "https://www.edsm.net/en/system/id/%d/name/%s";

    private int          msgNum;
    private String       system;
    private int          systemId;
    private boolean      firstDiscover;
    private String       date;
    private Coordinates  coordinates;
    private String       url;

    public String getSystemEdsmUrl()
    {
        return urlTemplate.formatted(systemId, system.replace(" ", "+"));
    }
}
