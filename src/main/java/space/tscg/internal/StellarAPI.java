package space.tscg.internal;

import elite.dangerous.Elite4J.CAPI;
import elite.dangerous.capi.FleetCarrierData;
import elite.dangerous.capi.Profile;
import net.dv8tion.jda.api.entities.UserSnowflake;
import okhttp3.HttpUrl;
import panda.std.Blank;
import panda.std.Result;
import space.tscg.api.carrier.IFleetCarrier;
import space.tscg.database.defined.TSCGDatabase;
import space.tscg.database.entity.TSCGMember;
import space.tscg.misc.json.StellarMapper;
import space.tscg.web.HttpError;
import space.tscg.web.Web;
import space.tscg.web.domain.Endpoint;

public class StellarAPI
{
    public static Result<Profile, HttpError> getProfile(String id)
    {
        var url = Endpoint.CAPI_PROFILE.toHttpUrl(id);
        var responseBody = Web.GET.call(url).getBody();
        var profile = StellarMapper.get().asOptional(responseBody, Profile.class);
        if(profile.isEmpty())
        {
            var err = StellarMapper.get().asOptional(responseBody, HttpError.class);
            if(err.isPresent())
            {
                return Result.error(err.get());
            }
            
            return HttpError.internalServerError();
        }
        
        return Result.ok(profile.get());
    }
    
    public static Result<FleetCarrierData, HttpError> getFleetCarrier(String id, boolean testing)
    {
        HttpUrl url = Endpoint.CAPI_CARRIER.toHttpUrl(id);
        if(testing) {
            url = HttpUrl.parse(url.toString()).newBuilder().addQueryParameter("testing", null).build();
        }
        
        var responseBody = Web.GET.call(url).getBody();
        try {
            var carrier = CAPI.parse(responseBody, FleetCarrierData.class).orElseThrow();
            return Result.ok(carrier);
        } catch (Exception e) {
            var err = StellarMapper.get().asOptional(responseBody, HttpError.class);
            if(err.isPresent())
            {
                return Result.error(err.get());
            }
            
            return HttpError.internalServerError();
        }
    }
    
    public static Result<String, Blank> createFleetCarrier(FleetCarrierData fcd)
    {
        IFleetCarrier fleetCarrier = IFleetCarrier.fromFleetCarrierData(fcd);
        TSCGDatabase.instance().create(fleetCarrier);
        return Result.ok(fcd.getCarrierId());
    }
    
    public static TSCGMember getMember(UserSnowflake user)
    {
        return TSCGMember.fromUserSnowflake(user);
    }
}
