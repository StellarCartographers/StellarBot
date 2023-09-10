package space.tscg.edsm;


import com.google.gson.JsonSyntaxException;

import elite.dangerous.EliteAPI;
import okhttp3.HttpUrl;
import space.tscg.common.http.Http;
import space.tscg.edsm.api.LastKnownPosition;
import space.tscg.edsm.api.Location;
import space.tscg.edsm.api.commander.GetRanks;
import space.tscg.edsm.api.system.GetSystemBodies;
import space.tscg.edsm.api.systems.SystemInformation;

public class EDSM
{
    private static final HttpUrl LOGS_URL_BUILDER = HttpUrl.parse("https://www.edsm.net/api-logs-v1");
    private static final HttpUrl CMDR_URL_BUILDER = HttpUrl.parse("https://www.edsm.net/api-commander-v1");
    private static final HttpUrl SYSTEM_URL_BUILDER = HttpUrl.parse("https://www.edsm.net/api-system-v1");
    private static final HttpUrl SYSTEMS_URL_BUILDER = HttpUrl.parse("https://www.edsm.net/api-v1");
    
    public static Location getCoordinates(String name)
    {
    	Location location = null;
        
        LastKnownPosition pos = getPosition(name, false, true);
        if(pos.getMessageNumber() == 100) {
        	location = new Location(name + " (" + pos.getSystem() + ")", pos.getCoordinates());
        } else {
            SystemInformation sysPos = getSystemInformation(name);
            if(sysPos != null) {
            	location = new Location(sysPos.getName(), sysPos.getCoordinates());
            }
        }
        return location;
    }
    
    public static SystemInformation getSystemInformation(String systemName)
    {
        HttpUrl url = EDSM.SYSTEMS_URL_BUILDER.newBuilder()
           .addPathSegment("system")
           .addEncodedQueryParameter("systemName", systemName)
           .addEncodedQueryParameter("showCoordinates", "1")
           .addEncodedQueryParameter("showId", "1")
           .addEncodedQueryParameter("showPermit", "1")
           .addEncodedQueryParameter("showInformation", "1")
           .addEncodedQueryParameter("showPrimaryStar", "1")
           .build();
        var response = Http.GET.call(url);

        try {
            return EliteAPI.fromJson(response.getBody(), SystemInformation.class);
        } catch (JsonSyntaxException e) {
        	return null;
		}
    }
    
    public static GetSystemBodies getSystemBodies(String systemName)
    {
        HttpUrl url = EDSM.SYSTEM_URL_BUILDER.newBuilder()
           .addPathSegment("bodies")
           .addEncodedQueryParameter("systemName", systemName)
           .build();
        var response = Http.GET.call(url);
        return EliteAPI.fromJson(response.getBody(), GetSystemBodies.class);
    }
    
    public static GetRanks getRanks(String cmdrName)
    {
        HttpUrl url = EDSM.CMDR_URL_BUILDER.newBuilder()
            .addPathSegment("get-ranks")
            .addEncodedQueryParameter("commanderName", cmdrName)
            .build();
        var response = Http.GET.call(url);
        System.out.println(response.getBody());
        return EliteAPI.fromJson(response.getBody(), GetRanks.class);
    }
    
    public static LastKnownPosition getPosition(String cmdrName, boolean showId, boolean showCoordinates)
    {
        HttpUrl.Builder urlBuilder = EDSM.LOGS_URL_BUILDER.newBuilder()
            .addPathSegment("get-position")
            .addEncodedQueryParameter("commanderName", cmdrName);
            
        if(showId)
            urlBuilder.addEncodedQueryParameter("showId", "1");
            
        if(showCoordinates)
            urlBuilder.addEncodedQueryParameter("showCoordinates", "1");
            
        var url = urlBuilder.build();
        var response = Http.GET.call(url);
        return EliteAPI.fromJson(response.getBody(), LastKnownPosition.class);
    }
}
