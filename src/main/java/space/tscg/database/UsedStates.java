package space.tscg.database;

import java.beans.ConstructorProperties;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import space.tscg.common.database.ManagedObject;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UsedStates implements ManagedObject
{
    private List<String> used;
    
    @JsonCreator
    @ConstructorProperties({"used"})
    public UsedStates(List<String> used)
    {
        this.used = used;
    }
    
    public void addState(String stateString)
    {
        this.used.add(stateString);
        TSCGDatabase.get().update(this);
    }
    
    public boolean containsState(String stateString)
    {
        return this.used.contains(stateString);
    }
    
    @Override
    public String getId()
    {
        return "used-states";
    }

    @Override
    public String getTableName()
    {
        return "states";
    }
}
