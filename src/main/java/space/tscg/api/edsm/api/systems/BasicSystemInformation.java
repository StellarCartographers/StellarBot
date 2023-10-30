package space.tscg.api.edsm.api.systems;

import lombok.Getter;

@Getter
public class BasicSystemInformation implements ISystem
{
    private final String name;

    public BasicSystemInformation(String name)
    {
        this.name = name;
    }
    
    @Override
    public boolean isBasic()
    {
        return true;
    }
}
