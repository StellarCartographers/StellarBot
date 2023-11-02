package space.tscg.internal.server;

import static io.javalin.apibuilder.ApiBuilder.*;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.DeserializationFeature;

import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import space.tscg.internal.server.bus.ServerEventManager;
import space.tscg.properties.dot.Dotenv;

public class Server
{
    private Javalin javalinInstance;
    int SERVER_PORT =  Dotenv.getInt("javalin_port" ,9055);
    
    public Server()
    {
        this.javalinInstance = this.createJavalin();
        this.addEndpoints();
        ServerEventManager.registerListener(new CarrierRegisterSubsciber());
        ServerEventManager.registerListener(new ButtonSubsciber());
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
            config.showJavalinBanner = false;
            config.jsonMapper(new JavalinJackson().updateMapper(mapper -> {
                mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
                mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            }));
            config.plugins.enableDevLogging();
        });
    }
}
