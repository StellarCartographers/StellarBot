package space.tscg.internal.db;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import net.dv8tion.jda.api.entities.UserSnowflake;
import space.tscg.api.database.DbEntity;
import space.tscg.database.DefinedTable;
import space.tscg.database.defined.TSCGDatabase;
import space.tscg.database.entity.TSCGMember;
import space.tscg.internal.StellarAPI;

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
    
    public static CarrierManager fromMember(TSCGMember member)
    {
        return getFromId(member.getElite().getCarrierId());
    }
    
    public static CarrierManager fromMember(UserSnowflake snowflake)
    {
        return getFromId(StellarAPI.getMember(snowflake).getElite().getCarrierId());
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
