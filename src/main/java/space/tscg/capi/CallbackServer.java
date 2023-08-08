package space.tscg.capi;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.port;

import com.google.gson.JsonObject;
import com.nimbusds.oauth2.sdk.AuthorizationCode;
import com.nimbusds.oauth2.sdk.id.State;

import spark.Spark;

public class CallbackServer
{
    public CallbackServer()
    {
        port(9050);
        path("/oauth", () ->
        {
            before("/*", (req, resp) -> System.out.println("Received api call"));
            get("/callback/:id", (req, resp) ->
            {
                System.out.println(req.ip());
                System.out.println(req.params("id"));
                AuthorizationCode code = new AuthorizationCode(req.queryParams("code"));
                State state = new State(req.queryParams("state"));
                AuthorizationFlow.parseCallback(code, state);
                resp.status(302);
                resp.redirect("https://tscg.network/complete/");
                JsonObject obj = new JsonObject();
                obj.addProperty("status", "ok");
                return obj;
            });
        });
    }
    
    public synchronized void shutdown()
    {
        Spark.stop();
    }
}
