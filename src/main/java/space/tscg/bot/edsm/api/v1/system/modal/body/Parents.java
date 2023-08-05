package space.tscg.bot.edsm.api.v1.system.modal.body;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;

import lombok.Getter;

@Getter
public class Parents {

    private List<Parent> parents = new ArrayList<>();

    @Getter
    public static class Parent {
        
        private String type;
        private int count;
        
        public Parent(String type, JsonElement count)
        {
            this.type = type;
            this.count = count.getAsInt();
        }
    }
}
