package space.tscg.capi;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.port;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.checkerframework.checker.units.qual.K;

import com.nimbusds.oauth2.sdk.AuthorizationCode;
import com.nimbusds.oauth2.sdk.AuthorizationCodeGrant;
import com.nimbusds.oauth2.sdk.AuthorizationGrant;
import com.nimbusds.oauth2.sdk.AuthorizationRequest;
import com.nimbusds.oauth2.sdk.AuthorizationResponse;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.ResponseType;
import com.nimbusds.oauth2.sdk.Scope;
import com.nimbusds.oauth2.sdk.TokenRequest;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.id.State;
import com.nimbusds.oauth2.sdk.pkce.CodeChallengeMethod;
import com.nimbusds.oauth2.sdk.pkce.CodeVerifier;
import com.nimbusds.openid.connect.sdk.AuthenticationErrorResponse;
import com.nimbusds.openid.connect.sdk.AuthenticationSuccessResponse;

import space.tscg.common.dotenv.Dotenv;

public class Authorization
{
    private static String CLIENT_ID = Dotenv.get("client_id");
    
    private static String CALLBACK_URL = "https://auth.tscg.network/oauth/callback";

    private static String LIVE_SERVER   = "https://companion.orerve.net";
    private static String LEGACY_SERVER = "https://legacy-companion.orerve.net/";
    private static String BETA_SERVER   = "https://pts-companion.orerve.net";
    private static String AUTH_SERVER   = "https://auth.frontierstore.net";

    private static Scope  SCOPE     = new Scope("capi", "auth");
    private static String AUTH_URL  = "/auth";
    private static String TOKEN_URL = "/token";

    private static String PROFILE_URL      = "/profile";
    private static String JOURNAL_URL      = "/journal";
    private static String FLEETCARRIER_URL = "/fleetcarrier";

    private static final Map<String, CodeVerifier> verifyMap = new HashMap<>();
    
    public static void spark()
    {
        port(9050);
        path("/oauth", () ->
        {
            before("/*", (req, resp) -> System.out.println("Received api call"));
            get("/callback", (req, resp) ->
            {
                System.out.println(CALLBACK_URL + "?" + req.queryString());
                Authorization.parseCallback(req.queryParams("code"), req.queryParams("state"));
                return null;
            });
        });
    }

    public static String askForLogin()
    {
        URI redirectURI = URI.create(CALLBACK_URL);

        CodeVerifier codeVerifier = new CodeVerifier();
        State sessionId = new State();
        verifyMap.put(sessionId.getValue(), codeVerifier);

        System.out.println(sessionId.getValue());
        //!f
        AuthorizationRequest request = new AuthorizationRequest.Builder(
                ResponseType.CODE, 
                new ClientID(CLIENT_ID))
            .customParameter("audience", "frontier,steam,epic")
        .scope(SCOPE)
        .state(sessionId)
        .codeChallenge(codeVerifier, CodeChallengeMethod.S256)
        .endpointURI(URI.create("%s%s".formatted(AUTH_SERVER, AUTH_URL)))
        .redirectionURI(redirectURI)
        .build();
        //@f
        
        return request.toURI().toString();
    }
    
    

    public static void parseCallback(String code, String state)
    {
        System.out.println("SessionState: " + state);
        System.out.println("Code: " + code);
        AuthorizationCode authCode = new AuthorizationCode(code);
        AuthorizationCodeGrant grant = new AuthorizationCodeGrant(authCode, URI.create(CALLBACK_URL), verifyMap.get(state));
        TokenRequest request = new TokenRequest(
            URI.create("%s%s".formatted(AUTH_SERVER, TOKEN_URL)),
            new ClientID(CLIENT_ID),
            grant);
        
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
