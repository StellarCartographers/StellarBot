package space.tscg.database;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import space.tscg.common.database.ManagedObject;
import space.tscg.crypto.EncryptedKey;

@Data
@SuperBuilder(builderMethodName = "Builder", toBuilder = true)
@Jacksonized
public class Member implements ManagedObject
{
    public static final String TABLE_NAME = "users";
    
    @Data
    @lombok.Builder(builderMethodName = "Builder")
    @Jacksonized
    public static class FrontierAuth {
        private EncryptedKey refreshToken;
        private long expiresIn;
    }
    
    private DiscordInfo discord;
    private FrontierAuth auth;

    @Override
    public String getId()
    {
        return discord.id;
    }

    @Override
    public String getTableName()
    {
        return TABLE_NAME;
    }
}
