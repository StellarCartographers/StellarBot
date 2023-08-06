package space.tscg.capi;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.nimbusds.oauth2.sdk.AuthorizationCode;
import com.nimbusds.oauth2.sdk.AuthorizationCodeGrant;
import com.nimbusds.oauth2.sdk.AuthorizationRequest;
import com.nimbusds.oauth2.sdk.ResponseType;
import com.nimbusds.oauth2.sdk.TokenRequest;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.id.State;
import com.nimbusds.oauth2.sdk.pkce.CodeChallengeMethod;
import com.nimbusds.oauth2.sdk.pkce.CodeVerifier;

public class AuthorizationFlow
{
    private static Map<State, CodeVerifier> stateHolder = new HashMap<>(4);

    public static String getAuthorizationLogin()
    {
        CodeVerifier codeVerifier = new CodeVerifier();
        State        sessionId    = new State();
        AuthorizationFlow.stateHolder.put(sessionId, codeVerifier);
        //!f
        AuthorizationRequest request = new AuthorizationRequest.Builder(
                ResponseType.CODE, 
                new ClientID(Constants.CLIENT_ID))
            .customParameter("audience", "frontier,steam,epic")
        .scope(Constants.SCOPE)
        .state(sessionId)
        .codeChallenge(codeVerifier, CodeChallengeMethod.S256)
        .endpointURI(Constants.AUTH_URI)
        .redirectionURI(Constants.CALLBACK_URI)
        .build();
        //@f

        return request.toURI().toString();
    }

    static void parseCallback(AuthorizationCode code, State state)
    {
        AuthorizationCodeGrant grant   = new AuthorizationCodeGrant(code, Constants.CALLBACK_URI, stateHolder.get(state));
        TokenRequest           request = new TokenRequest(Constants.TOKEN_URI, new ClientID(Constants.CLIENT_ID), grant);
        try
        {
            HTTPResponse response = request.toHTTPRequest().send();
            System.out.println(response.getContent());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
