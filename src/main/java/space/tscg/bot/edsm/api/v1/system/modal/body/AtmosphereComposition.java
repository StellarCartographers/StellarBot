package space.tscg.bot.edsm.api.v1.system.modal.body;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;

import lombok.Getter;

@Getter
public class AtmosphereComposition
{
    private List<Element> elements = new ArrayList<>();
    
    @Getter
    public static class Element {
        private String name;
        private double percentage;
        
        public Element(String name, JsonElement percentage) {
            this.name = name;
            this.percentage = percentage.getAsDouble();
        }
    }
}
