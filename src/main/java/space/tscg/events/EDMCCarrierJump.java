package space.tscg.events;

import elite.dangerous.events.fleetcarriers.CarrierJumpRequest;
import lombok.Getter;

@Getter
public class EDMCCarrierJump
{
    private String cmdr;
    private String system;
    private String station;
    private CarrierJumpRequest data;
}
