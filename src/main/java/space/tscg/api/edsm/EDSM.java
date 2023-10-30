package space.tscg.api.edsm;


import java.util.Optional;

import org.tinylog.Logger;
import org.tinylog.TaggedLogger;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;

import okhttp3.HttpUrl;
import space.tscg.api.edsm.api.LastKnownPosition;
import space.tscg.api.edsm.api.Location;
import space.tscg.api.edsm.api.commander.GetRanks;
import space.tscg.api.edsm.api.system.GetSystemBodies;
import space.tscg.api.edsm.api.systems.SystemInformation;
import space.tscg.web.Web;

public class EDSM
{
    private static TaggedLogger logger = Logger.tag("EDSM-API");
    
    private static final ObjectMapper MAPPER = new JsonMapper()
                    .disable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)
                    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
    
    private static final HttpUrl LOGS_URL_BUILDER = HttpUrl.parse("https://www.edsm.net/api-logs-v1");
    private static final HttpUrl CMDR_URL_BUILDER = HttpUrl.parse("https://www.edsm.net/api-commander-v1");
    private static final HttpUrl SYSTEM_URL_BUILDER = HttpUrl.parse("https://www.edsm.net/api-system-v1");
    private static final HttpUrl SYSTEMS_URL_BUILDER = HttpUrl.parse("https://www.edsm.net/api-v1");
    
    public static Optional<Location> getCoordinates(String name)
    {
    	Location location = null;
        
        LastKnownPosition pos = getPosition(name, false, true).orElseThrow();
        if(pos.getMsgNum() == 100) {
        	location = new Location(name + " (" + pos.getSystem() + ")", pos.getCoordinates());
        } else {
            SystemInformation sysPos = getSystemInformation(name).orElseThrow();
            if(sysPos != null) {
            	location = new Location(sysPos.getName(), sysPos.getCoordinates());
            }
        }
        return Optional.ofNullable(location);
    }
    
    public static Optional<SystemInformation> getSystemInformation(String systemName)
    {
        logger.info("SystemInformation Requested");
        HttpUrl url = EDSM.SYSTEMS_URL_BUILDER.newBuilder()
           .addPathSegment("system")
           .addEncodedQueryParameter("systemName", systemName)
           .addEncodedQueryParameter("showCoordinates", "1")
           .addEncodedQueryParameter("showId", "1")
           .addEncodedQueryParameter("showPermit", "1")
           .addEncodedQueryParameter("showInformation", "1")
           .addEncodedQueryParameter("showPrimaryStar", "1")
           .build();
        var response = Web.GET.call(url);
        
        return fromJson(response.getBody(), SystemInformation.class);
    }
    
    public static Optional<GetSystemBodies> getSystemBodies(String systemName)
    {
        HttpUrl url = EDSM.SYSTEM_URL_BUILDER.newBuilder()
           .addPathSegment("bodies")
           .addEncodedQueryParameter("systemName", systemName)
           .build();
        var response = Web.GET.call(url);
        return fromJson(response.getBody(), GetSystemBodies.class);
    }
    
    public static Optional<GetRanks> getRanks(String cmdrName)
    {
        HttpUrl url = EDSM.CMDR_URL_BUILDER.newBuilder()
            .addPathSegment("get-ranks")
            .addEncodedQueryParameter("commanderName", cmdrName)
            .build();
        var response = Web.GET.call(url);
        System.out.println(response.getBody());
        return fromJson(response.getBody(), GetRanks.class);
    }
    
    public static Optional<LastKnownPosition> getPosition(String cmdrName, boolean showId, boolean showCoordinates)
    {
        HttpUrl.Builder urlBuilder = EDSM.LOGS_URL_BUILDER.newBuilder()
            .addPathSegment("get-position")
            .addEncodedQueryParameter("commanderName", cmdrName);
            
        if(showId)
            urlBuilder.addEncodedQueryParameter("showId", "1");
            
        if(showCoordinates)
            urlBuilder.addEncodedQueryParameter("showCoordinates", "1");
            
        var url = urlBuilder.build();
        var response = Web.GET.call(url);
        return fromJson(response.getBody(), LastKnownPosition.class);
    }
    
    private static <T> Optional<T> fromJson(String json, Class<T> type)
    {
        try {
            return Optional.of(MAPPER.readValue(json, type));
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }
}
