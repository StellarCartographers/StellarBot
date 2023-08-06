package space.tscg.events;

import com.nimbusds.oauth2.sdk.AuthorizationCode;
import com.nimbusds.oauth2.sdk.id.State;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CallbackEvent
{
    private AuthorizationCode authorizationCode;
    private State state;
}
