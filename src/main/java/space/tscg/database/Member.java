package space.tscg.database;

import static com.rethinkdb.RethinkDB.r;

import java.util.Optional;

import elite.dangerous.capi.FleetCarrierData;
import io.github.readonly.command.DiscordInfo;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import space.tscg.capi.modal.EliteInfo;
import space.tscg.capi.modal.FrontierAuth;
import space.tscg.common.database.ManagedObject;
import space.tscg.common.database.Operation;

@Data
@SuperBuilder(builderMethodName = "Builder", toBuilder = true)
@Jacksonized
public class Member implements ManagedObject
{
    public static final String TABLE_NAME = "users";
    
    private DiscordInfo discord;
    private EliteInfo elite;
    private FleetCarrierData fleetCarrier;
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
    
    public Operation update()
    {
        return TSCGDatabase.get().update(this);
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
