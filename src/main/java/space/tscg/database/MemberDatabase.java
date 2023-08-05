package space.tscg.database;

import static com.rethinkdb.RethinkDB.r;

import java.util.Optional;

import com.rethinkdb.net.Result;

import space.tscg.common.database.Database;
import space.tscg.common.database.DatabaseCredentials;
import space.tscg.common.database.Operation;
import space.tscg.common.dotenv.Dotenv;

public class MemberDatabase extends Database
{
    static DatabaseCredentials credentials = DatabaseCredentials.builder()
        .hostname(Dotenv.get("db_host"))
        .databaseName("fdev")
        .username(Dotenv.get("db_user"))
        .password(Dotenv.get("db_user")).build();
    
    private static final MemberDatabase _instance = new MemberDatabase();

    private MemberDatabase()
    {
        super(credentials);
    }
    
    public static MemberDatabase get()
    {
        return _instance;
    }

    public Optional<Member> getMember(String uuid)
    {
        var obj = r.table(Member.TABLE_NAME).get(uuid).runAtom(connection(), Member.class);
        return (obj == null) ? Optional.empty() : Optional.of(obj);
    }

    public Operation delete(String uuid)
    {
        return r.table(Member.TABLE_NAME).get(uuid).delete().run(connection(), Operation.class).single();
    }

    public <T> Result<T> getAll(String tableName, Class<T> target)
    {
        return r.table(tableName).run(connection(), target);
    }
}
