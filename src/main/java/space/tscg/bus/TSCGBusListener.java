package space.tscg.bus;

import com.google.common.eventbus.Subscribe;

import space.tscg.bus.event.CallEndpointsEvent;
import space.tscg.capi.modal.EliteInfo;
import space.tscg.capi.modal.FrontierAuth;
import space.tscg.database.Member;

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
        }
    }
}
