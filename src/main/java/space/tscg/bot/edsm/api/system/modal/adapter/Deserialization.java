package space.tscg.bot.edsm.api.system.modal.adapter;

import java.lang.reflect.Type;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import space.tscg.bot.edsm.api.system.modal.Body;
import space.tscg.bot.edsm.api.system.modal.Planet;
import space.tscg.bot.edsm.api.system.modal.Star;
import space.tscg.bot.edsm.api.system.modal.body.AtmosphereComposition;
import space.tscg.bot.edsm.api.system.modal.body.Materials;
import space.tscg.bot.edsm.api.system.modal.body.Parents;

public class Deserialization
{
    public static class BodyAdapter implements JsonDeserializer<Body<?>>
    {
        @Override
        public Body<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            Body<?> body;
            JsonObject bodyJson = json.getAsJsonObject();
            if(bodyJson.get("type").getAsString().equals("Star"))
            {
                body = context.deserialize(json, Star.class);
            } else {
                body = context.deserialize(json, Planet.class);
            }
            
            return body;
        }
    }
    
    public static class ParentAdapter implements JsonDeserializer<Parents>
    {
        @Override
        public Parents deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            JsonArray obj = json.getAsJsonArray();
            Parents parents = new Parents();
            for (int i = 0; i < obj.size(); i++)
            {
                JsonObject o = obj.get(i).getAsJsonObject();
                o.asMap().forEach((s,e) -> {
                    parents.getParents().add(new Parents.Parent(s, e));
                });
            }
            return parents;
        }
    }
    
    public static class MaterialsAdapter implements JsonDeserializer<Materials>
    {
        @Override
        public Materials deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            JsonObject obj = json.getAsJsonObject();
            Materials materials = new Materials();
            if(obj.has("materials"))
            {
                JsonObject m = obj.getAsJsonObject("materials");
                
                for(Entry<String, JsonElement> entry : m.entrySet())
                {
                    materials.getMaterials().add(new Materials.Material(entry.getKey(), entry.getValue()));
                }
            }
            
            return materials;
        }
        
    }
    
    public static class AtmosphereCompositionAdapter implements JsonDeserializer<AtmosphereComposition>
    {
        @Override
        public AtmosphereComposition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            JsonObject obj = json.getAsJsonObject();
            AtmosphereComposition atmosphere = new AtmosphereComposition();
            if(obj.has("atmosphereComposition"))
            {
                JsonObject ac = obj.getAsJsonObject("atmosphereComposition");
                
                for(Entry<String, JsonElement> entry : ac.entrySet())
                {
                    atmosphere.getElements().add(new AtmosphereComposition.Element(entry.getKey(), entry.getValue()));
                }
            }
            
            return atmosphere;
        }
    }
}
