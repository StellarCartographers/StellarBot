package space.tscg.capi;

import java.io.IOException;

import com.nimbusds.oauth2.sdk.AccessTokenResponse;
import com.nimbusds.oauth2.sdk.AuthorizationCode;
import com.nimbusds.oauth2.sdk.AuthorizationCodeGrant;
import com.nimbusds.oauth2.sdk.AuthorizationRequest;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.ResponseType;
import com.nimbusds.oauth2.sdk.TokenRequest;
import com.nimbusds.oauth2.sdk.TokenResponse;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.id.State;
import com.nimbusds.oauth2.sdk.pkce.CodeChallengeMethod;
import com.nimbusds.oauth2.sdk.pkce.CodeVerifier;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import com.nimbusds.oauth2.sdk.token.Tokens;

import io.github.readonly.command.DiscordInfo;
import space.tscg.BotLog;
import space.tscg.bus.BusHandler;
import space.tscg.bus.event.CallEndpointsEvent;
import space.tscg.capi.modal.FrontierAuth;
import space.tscg.database.Member;
import space.tscg.database.TSCGDatabase;
import space.tscg.util.Tri;
import space.tscg.util.crypto.EncryptedKey;

public class AuthorizationFlow
{
    private static Tri<DiscordInfo, State, CodeVerifier> triState = new Tri<>();
    
    public static String getAuthorizationLogin(DiscordInfo userInfo)
    {
        if(Member.get(userInfo).isEmpty()) {
            Member member = Member.Builder().discord(userInfo).auth(null).build();
            TSCGDatabase.get().create(member);
        }
        
        CodeVerifier codeVerifier = new CodeVerifier();
        State        sessionId    = new State();
        
        AuthorizationFlow.triState.put(userInfo ,sessionId, codeVerifier);
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
    
    public static void refreshToken(EncryptedKey<RefreshToken> encryptedRefreshToken)
    {
        RefreshToken refreshToken = encryptedRefreshToken.decodeAsToken();
    }

    static void parseCallback(AuthorizationCode code, State state)
    {
        CodeVerifier verifier = triState.get_C_From_B(state).orElseThrow();
        AuthorizationCodeGrant grant   = new AuthorizationCodeGrant(code, Constants.CALLBACK_URI, verifier);
        TokenRequest           request = new TokenRequest(Constants.TOKEN_URI, Constants.CLIENT_ID, grant);
        
        var diOpt = triState.get_A_From_B(state);
        if(diOpt.isPresent())
        {
            var di = diOpt.get();
            Constants.deleteMessage(di.getId());
            
            try
            {
                HTTPResponse response = request.toHTTPRequest().send();
                try {
                    TokenResponse r = TokenResponse.parse(response);
                    if(r instanceof AccessTokenResponse)
                    {
                        AccessTokenResponse tokenResponse = r.toSuccessResponse();
                        Tokens tokens = tokenResponse.getTokens();
                        
                        FrontierAuth frontier = FrontierAuth.Builder()
                            .refreshToken(tokens.getRefreshToken())
                            .accessToken(tokens.getBearerAccessToken())
                            .expiresIn(tokens.getAccessToken().getLifetime())
                            .build();
                        
                        Member m = Member.get(di).orElseThrow();
                        m.setAuth(frontier);
                        m.update();
                        
                        BusHandler.post(new CallEndpointsEvent(m));
                    }
                } catch (ParseException e)
                {
                    BotLog.error(e, "Error while parsing HTTP Response");
                }

            } catch (IOException e)
            {
                BotLog.error(e, "Error while sending HTTP Request");
            }
        }
    }
}
