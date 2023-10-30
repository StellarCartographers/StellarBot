package space.tscg.internal.db;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import space.tscg.api.database.DbEntity;
import space.tscg.database.DefinedTable;
import space.tscg.database.defined.TSCGDatabase;

@Getter
@Builder(builderMethodName = "Builder")
@Jacksonized
public class CarrierManager implements DbEntity
{
    private final String id;

    private OwnerChannel ownerChannel;

    private PublicChannel publicChannel;

    @JsonIgnore
    public static CarrierManager getFromId(String id)
    {
        return TSCGDatabase.instance().get(DefinedTable.CHANNELS, id, CarrierManager.class);
    }
    
    @JsonIgnore
    public static CarrierManager create(String managerId, String ownerId, String publicId)
    {
        var ownerChnl = OwnerChannel.create(ownerId);
        var publicChnl = PublicChannel.create(publicId);
        return new CarrierManager(managerId, ownerChnl, publicChnl);
    }
    
    @Override
    public String getId()
    {
        return id;
    }

    @Override
    public DefinedTable getTable()
    {
        return DefinedTable.CHANNELS;
    }
}
