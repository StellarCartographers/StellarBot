package space.tscg.database;

import static com.rethinkdb.RethinkDB.r;

import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Result;

import space.tscg.common.database.Database;
import space.tscg.common.database.DatabaseCredentials;
import space.tscg.common.dotenv.Dotenv;

public class TSCGDatabase extends Database
{
    static DatabaseCredentials credentials = DatabaseCredentials.builder()
        .hostname(Dotenv.get("db_host"))
        .databaseName("tscg")
        .username(Dotenv.get("db_user"))
        .password(Dotenv.get("db_user")).build();
    
    private static final TSCGDatabase _instance = new TSCGDatabase();

    private TSCGDatabase()
    {
        super(credentials);
    }
    
    public static TSCGDatabase get()
    {
        return _instance;
    }

    static Connection getConn()
    {
        return get().connection();
    }

    public <T> Result<T> getAll(String tableName, Class<T> target)
    {
        return r.table(tableName).run(connection(), target);
    }
}
