package space.tscg.util;

import java.net.URL;

public class DefaultPackageResolver implements PackageResolver
{
    /**
     * @see PackageResolver#resolve(java.lang.String)
     */
    @Override
    public URL resolve(String pkg)
    {
        String package_path = pkg.replaceAll("\\.", "/"); //$NON-NLS-1$ //$NON-NLS-2$
        URL u = DefaultPackageResolver.class.getClassLoader().getResource(package_path);
        return u;
    }
}
