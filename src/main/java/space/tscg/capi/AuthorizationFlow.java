package space.tscg.capi;

import java.io.IOException;
import java.net.URI;

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

import net.dv8tion.jda.api.entities.User;
import space.tscg.util.Tri;

public class AuthorizationFlow
{
    private static Tri<String, State, CodeVerifier> triState = new Tri<>();
    
    public static String getAuthorizationLogin(User user)
    {
        CodeVerifier codeVerifier = new CodeVerifier();
        State        sessionId    = new State();
        AuthorizationFlow.triState.put(user.getId() ,sessionId, codeVerifier);
        //!f
        AuthorizationRequest request = new AuthorizationRequest.Builder(
                ResponseType.CODE, 
                new ClientID(Constants.CLIENT_ID))
            .customParameter("audience", "frontier,steam,epic")
        .scope(Constants.SCOPE)
        .state(sessionId)
        .codeChallenge(codeVerifier, CodeChallengeMethod.S256)
        .endpointURI(Constants.AUTH_URI)
        .redirectionURI(URI.create(Constants.CALLBACK_URL_STRING + "/" + user.getId()))
        .build();
        //@f

        return request.toURI().toString();
    }

    static void parseCallback(AuthorizationCode code, State state)
    {
        AuthorizationCodeGrant grant   = new AuthorizationCodeGrant(code, Constants.CALLBACK_URI, triState.getCReference(state).orElseThrow());
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
