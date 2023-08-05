
package space.tscg.bot.edsm.api.v1.system.modal.body;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;

import lombok.Getter;

@Getter
public class Materials {
    
    private List<Material> materials = new ArrayList<>();
    
    @Getter
    public static class Material {
        private String name;
        private double percentage;
        
        public Material(String name, JsonElement percentage) {
            this.name = name;
            this.percentage = percentage.getAsDouble();
        }
    }
}
