package space.tscg.capi;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.port;

import java.net.URI;

import com.nimbusds.oauth2.sdk.AuthorizationCode;
import com.nimbusds.oauth2.sdk.AuthorizationRequest;
import com.nimbusds.oauth2.sdk.AuthorizationResponse;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.ResponseType;
import com.nimbusds.oauth2.sdk.Scope;
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

    private static String _verifier;
    private static String _sessionId;

    public static void spark()
    {
        port(9050);
        path("/oauth", () ->
        {
            before("/*", (req, resp) -> System.out.println("Received api call"));
            get("/callback", (req, resp) ->
            {
                System.out.println(req.queryString());
                System.out.println(req.url());
                System.out.println(req.uri());
                Authorization.parseCallback(URI.create(req.url()));
                resp.status(302);
                return null;
            });
        });
    }

    public static String askForLogin()
    {
        URI redirectURI = URI.create("https://auth.tscg.network/oauth/callback");

        CodeVerifier codeVerifier = new CodeVerifier();
        Authorization._verifier = codeVerifier.getValue();

        State sessionId = new State();
        Authorization._sessionId = sessionId.getValue();

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

    public static void parseCallback(URI response)
    {
        AuthorizationResponse auth;
        try
        {
            auth = AuthorizationResponse.parse(response);
            if (auth instanceof AuthenticationErrorResponse)
            {
                // process error
            }

            AuthenticationSuccessResponse successResponse = (AuthenticationSuccessResponse) auth;

            // Retrieve the authorisation code
            AuthorizationCode code = successResponse.getAuthorizationCode();
            State state = successResponse.getSessionState();
            System.out.println("SessionState: " + state.toString());
            System.out.println("Code: " + code.toString());
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
    }
}
