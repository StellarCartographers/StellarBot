package space.tscg.database;

import lombok.Builder.ObtainVia;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import space.tscg.common.database.ManagedObject;
import space.tscg.crypto.EncryptedKey;
import space.tscg.util.UUIDv5;

@Data
@SuperBuilder(builderMethodName = "Builder", toBuilder = true)
@Jacksonized
public class Member implements ManagedObject
{
    public static final String TABLE_NAME = "users";
    
    @Data
    @lombok.Builder(builderMethodName = "Builder")
    @Jacksonized
    public static class DiscordInfo {
        private String username;
        private String discordId;
        private long epochTimeSet;
    }
    
    @Data
    @lombok.Builder(builderMethodName = "Builder")
    @Jacksonized
    public static class FrontierAuth {
        private EncryptedKey authToken;
        private EncryptedKey refreshToken;
        private long expiresIn;
    }
    
    @ObtainVia(method = "createUuid")
    private final String id;
    private DiscordInfo discord;
    private FrontierAuth auth;
    
    private String createUuid()
    {
        return UUIDv5.create(discord.discordId).toString();
    }
    
    @Override
    public String getId()
    {
        return id;
    }

    @Override
    public String getTableName()
    {
        return TABLE_NAME;
    }
}
