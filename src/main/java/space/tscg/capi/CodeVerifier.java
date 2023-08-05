package space.tscg.capi;

import java.security.SecureRandom;
import java.util.Base64;

public class CodeVerifier
{
    private final String _verifier;
    
    public CodeVerifier()
    {
        SecureRandom sr = new SecureRandom();
        byte[] code = new byte[32];
        sr.nextBytes(code);
        this._verifier = Base64.getUrlEncoder().withoutPadding().encodeToString(code);
    }
    
    public String get()
    {
        return _verifier;
    }
}
