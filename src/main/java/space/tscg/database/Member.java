package space.tscg.database;

import static com.rethinkdb.RethinkDB.r;

import java.util.Optional;

import io.github.readonly.command.DiscordInfo;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import space.tscg.common.database.ManagedObject;
import space.tscg.common.database.Operation;
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
        return discord.getId();
    }

    @Override
    public String getTableName()
    {
        return TABLE_NAME;
    }
    
    public static Optional<Member> get(DiscordInfo info)
    {
        return get(info.getId());
    }
    
    public static Optional<Member> get(String id)
    {
        return Optional.ofNullable(r.table(TABLE_NAME).get(id).runAtom(TSCGDatabase.getConn(), Member.class));
    }
    
    public static Operation delete(String uuid)
    {
        return r.table(TABLE_NAME).get(uuid).delete().run(TSCGDatabase.getConn(), Operation.class).single();
    }
}
