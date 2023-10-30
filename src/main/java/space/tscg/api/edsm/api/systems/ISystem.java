package space.tscg.api.edsm.api.systems;

public interface ISystem
{
    String getName();
    
    boolean isBasic();
    
    default boolean hasCoordinates() {
        return false;
    }
    
    default boolean hasExtraSystemInfo() {
        return false;
    }
}
