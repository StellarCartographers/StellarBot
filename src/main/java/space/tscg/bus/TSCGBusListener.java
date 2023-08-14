package space.tscg.bus;

import com.google.common.eventbus.Subscribe;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import elite.dangerous.EliteAPI;
import elite.dangerous.capi.FleetCarrierData;
import space.tscg.BotLog;
import space.tscg.bus.event.CallEndpointsEvent;
import space.tscg.capi.Constants;
import space.tscg.capi.modal.EliteInfo;
import space.tscg.capi.modal.FrontierAuth;
import space.tscg.database.Member;
import space.tscg.edsm.Http;

public class TSCGBusListener
{
    @Subscribe
    public void onCallEndpointEvent(CallEndpointsEvent event)
    {
        Member member = event.getMember();
        FrontierAuth auth = member.getAuth();
        
        if(!auth.isAccessTokenExpired())
        {
            EliteInfo.Builder builder = EliteInfo.Builder();
            
            var profile = auth.addAccessTokenHeader(Http.GET).call(Constants.CAPI_PROFILE);
            
            BotLog.info("CAPI_PROFILE: " + profile.getCode());
            
            JsonObject obj = JsonParser.parseString(profile.getBody()).getAsJsonObject();
            var commander = obj.get("commander").getAsJsonObject();
            var lastSystem = obj.get("lastSystem").getAsJsonObject();
            
            builder.cmdrName(commander.get("name").getAsString());
            builder.id(commander.get("id").getAsString());
            builder.systemAddress(lastSystem.get("name").getAsString());

            member.setElite(builder.build());
            member.update();
            
            var fleetCarrier = auth.addAccessTokenHeader(Http.GET).call(Constants.CAPI_FLEETCARRIER);
            BotLog.info("CAPI_FLEETCARRIER: " + fleetCarrier.getCode());
            FleetCarrierData data = EliteAPI.fromJson(fleetCarrier.getBody(), FleetCarrierData.class);
            member.setFleetCarrier(data);
            member.update();
        }
    }
}
