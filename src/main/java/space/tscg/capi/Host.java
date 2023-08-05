package space.tscg.capi;

import okhttp3.HttpUrl;

public enum Host
{
    LIVE_SERVER("companion.orerve.net"),
    LEGACY_SERVER("legacy-companion.orerve.net"),
    AUTH_SERVER("auth.frontierstore.net");

    private final HttpUrl.Builder builder;
    
    Host(String host)
    {
        this.builder = new HttpUrl.Builder().scheme("https").host(host);
    }
    
    public HttpUrl get()
    {
        return builder.build();
    }

    public HttpUrl buildUrl(HostPath path, Queries queries)
    {
        queries.addToUrl(builder);
        builder.addPathSegment(path.toString());
        return builder.build();
    }
}
