package space.tscg.crypto;

import java.beans.ConstructorProperties;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public final class EncryptedKey
{
    public enum KeyType {
        TOKEN,
        REFRESH;
    }
    
    private final String encryptedKey;
    private final KeyType keyType;
    
    @JsonCreator
    @ConstructorProperties({"key", "type"})
    public EncryptedKey(@JsonProperty("key") String key, @JsonProperty("type") KeyType type)
    {
        this.encryptedKey = EncryptDecrypt.encode(key);
        this.keyType = type;
    }
}
