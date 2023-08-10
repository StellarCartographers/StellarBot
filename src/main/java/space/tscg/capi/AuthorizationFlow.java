package space.tscg.capi;

import java.io.IOException;
import java.util.Optional;

import com.nimbusds.oauth2.sdk.AuthorizationCode;
import com.nimbusds.oauth2.sdk.AuthorizationCodeGrant;
import com.nimbusds.oauth2.sdk.AuthorizationRequest;
import com.nimbusds.oauth2.sdk.ResponseType;
import com.nimbusds.oauth2.sdk.TokenRequest;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.id.State;
import com.nimbusds.oauth2.sdk.pkce.CodeChallengeMethod;
import com.nimbusds.oauth2.sdk.pkce.CodeVerifier;

import net.dv8tion.jda.api.entities.User;
import space.tscg.database.DiscordInfo;
import space.tscg.util.Tri;

public class AuthorizationFlow
{
    private static Tri<DiscordInfo, State, CodeVerifier> triState = new Tri<>();
    
    public static String getAuthorizationLogin(User user)
    {
        CodeVerifier codeVerifier = new CodeVerifier();
        State        sessionId    = new State();
        DiscordInfo info = DiscordInfo.Builder().id(user.getId()).username(user.getName()).build();
        
        AuthorizationFlow.triState.put(info ,sessionId, codeVerifier);
        //!f
        AuthorizationRequest request = new AuthorizationRequest.Builder(
                ResponseType.CODE, 
                Constants.CLIENT_ID)
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
        CodeVerifier verifier = triState.get_C_From_B(state).orElseThrow();
        AuthorizationCodeGrant grant   = new AuthorizationCodeGrant(code, Constants.CALLBACK_URI, verifier);
        TokenRequest           request = new TokenRequest(Constants.TOKEN_URI, Constants.CLIENT_ID, grant);
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
