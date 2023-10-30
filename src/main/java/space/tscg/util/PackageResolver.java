package space.tscg.util;

import java.io.IOException;
import java.net.URL;

public interface PackageResolver
{
    /**
     * Returns an URL for a package
     * 
     * @param pkg
     *            the package (e.g. de.fhg.igd.CityServer3D)
     * 
     * @return the URL to the package
     * 
     * @throws IOException
     *             if the URL could not be retrieved
     */
    URL resolve(String pkg) throws IOException;
}
