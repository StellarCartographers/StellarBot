package space.tscg.capi;

public enum HostPath
{
    AUTH,
    TOKEN,
    FLEETCARRIER,
    PROFILE,
    JOURNAL;
    
    @Override
    public String toString()
    {
        return super.toString().toLowerCase();
    }
}
