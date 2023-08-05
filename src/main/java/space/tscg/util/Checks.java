package space.tscg.util;

import com.google.common.base.Verify;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Checks
{

    public <T> T notNullOrEmpty(T obj)
    {
        Verify.verifyNotNull(obj, "cannot be null");
        if(obj instanceof String && ((String) obj).isBlank())
            throw new NullPointerException("String cannot be empty");
        return obj;
    }
}
