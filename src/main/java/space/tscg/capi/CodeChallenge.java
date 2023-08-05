package space.tscg.capi;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;

public class CodeChallenge
{
    private final String _verifier;
    private final String _value;
    
    public CodeChallenge(String verifier)
    {
        this._verifier = verifier;
        byte[] bytes = StringUtils.getBytesUsAscii(_verifier);
        MessageDigest md = DigestUtils.getSha256Digest();
        md.update(bytes, 0, bytes.length);
        byte[] digest = md.digest();
        this._value = Base64.encodeBase64URLSafeString(digest);
    }
    
    public String getChallenge()
    {
        return _value;
    }
}
