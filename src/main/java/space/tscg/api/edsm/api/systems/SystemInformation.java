package space.tscg.api.edsm.api.systems;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import okhttp3.HttpUrl;
import space.tscg.api.edsm.api.modal.Coordinates;
import space.tscg.api.edsm.api.systems.modal.Information;
import space.tscg.api.edsm.api.systems.modal.PrimaryStar;

@Getter
public class SystemInformation implements ISystem
{
    @JsonIgnore
    private final String urlTemplate = "https://www.edsm.net/en/system/id/%d/name/%s";
    @Setter
    private String       name;
    private int          id;
    private long         id64;
    private Coordinates  coordinates;
    private boolean      coordsLocked;
    private boolean      permitLocked;
    private String       permitName;
    private Information  information;
    private PrimaryStar  primaryStar;

    public String getSystemEdsmUrl()
    {
        return urlTemplate.formatted(id, name.replace(" ", "+"));
    }
    
    public HttpUrl getSystemHttpUrl()
    {
        return HttpUrl.parse(getSystemEdsmUrl());
    }

    @Override
    public boolean hasCoordinates()
    {
        return coordinates != null;
    }

    @Override
    public boolean hasExtraSystemInfo()
    {
        return information != null && information.getPopulation() > 0;
    }

    @Override
    public boolean isBasic()
    {
        return false;
    }
}
