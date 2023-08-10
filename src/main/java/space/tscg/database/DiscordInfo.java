package space.tscg.database;

import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@lombok.Builder(builderMethodName = "Builder")
@Jacksonized
public class DiscordInfo {
    private String username;
    String id;
}