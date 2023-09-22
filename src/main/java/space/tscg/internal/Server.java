package space.tscg.internal;

import static io.javalin.apibuilder.ApiBuilder.*;

import io.javalin.Javalin;
import space.tscg.common.dotenv.Dotenv;

public class Server
{
    private Javalin javalinInstance;
    int SERVER_PORT =  Dotenv.getInt("javalin_port" ,9055);
    
    public Server()
    {
        this.javalinInstance = this.createJavalin();
        this.addEndpoints();
    }
    
    private void addEndpoints()
    {
        this.javalinInstance.routes(() ->
        {
            path("bot", () ->
            {
                post("/{event}", EventsEndpoint::handle);
            });
        }).start(SERVER_PORT);
    }
    
    private Javalin createJavalin()
    {
        return Javalin.create(config ->
        {
            config.plugins.enableDevLogging();
        });
    }
}
