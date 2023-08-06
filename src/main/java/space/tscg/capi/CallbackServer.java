package space.tscg.capi;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.port;

import spark.Spark;

public class CallbackServer
{
    public CallbackServer()
    {
        port(9050);
        path("/oauth", () ->
        {
            before("/*", (req, resp) -> System.out.println("Received api call"));
            get("/callback", (req, resp) ->
            {
                System.out.println("UserAgent: " + req.userAgent());
                System.out.println("IP: " + req.ip());
                System.out.println("Headers:");
                req.headers().forEach(h -> {
                    System.out.println(" -> " + h);
                });
                AuthorizationFlow.parseCallback(req.queryParams("code"), req.queryParams("state"));
                resp.redirect("https://tscg.network/complete/");
                return null;
            });
        });
    }
    
    public synchronized void shutdown()
    {
        Spark.stop();
    }
}
