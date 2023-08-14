package space.tscg.capi.modal;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.nimbusds.oauth2.sdk.token.BearerAccessToken;
import com.nimbusds.oauth2.sdk.token.RefreshToken;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Value;
import space.tscg.edsm.Http;
import space.tscg.util.crypto.EncryptDecrypt;
import space.tscg.util.crypto.EncryptedKey;

@Value
@JsonDeserialize(builder = FrontierAuth.Builder.class)
public class FrontierAuth {
    private EncryptedKey<BearerAccessToken> accessToken;
    private EncryptedKey<RefreshToken> refreshToken;
    private long expireEpochSecond;
    
    FrontierAuth(final EncryptedKey<BearerAccessToken> accessToken, final EncryptedKey<RefreshToken> refreshToken, final long expiresIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expireEpochSecond = Instant.now().plusSeconds(expiresIn).getEpochSecond();;
    }
    
    @JsonIgnore
    public boolean isAccessTokenExpired()
    {
        return Instant.now().isAfter(Instant.ofEpochSecond(expireEpochSecond));
    }
    
    public void addAccessTokenHeader(Http request)
    {
        BearerAccessToken decodedToken = new BearerAccessToken(EncryptDecrypt.decode(accessToken));
        request.header("Authorization", decodedToken.toAuthorizationHeader());
    }
    
    public static FrontierAuth.Builder Builder() {
        return new FrontierAuth.Builder();
    }

    @ToString
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    @JsonPOJOBuilder(withPrefix = "", buildMethodName = "build")
    public static class Builder {
        private EncryptedKey<BearerAccessToken> accessToken;
        private EncryptedKey<RefreshToken> refreshToken;
        private long expiresIn;

        public FrontierAuth.Builder accessToken(final BearerAccessToken accessToken) {
            this.accessToken = EncryptedKey.of(accessToken);
            return this;
        }
        
        public FrontierAuth.Builder refreshToken(final RefreshToken refreshToken) {
            this.refreshToken = EncryptedKey.of(refreshToken);
            return this;
        }

        public FrontierAuth.Builder expiresIn(final long expiresIn) {
            this.expiresIn = expiresIn;
            return this;
        }

        public FrontierAuth build() {
            return new FrontierAuth(this.accessToken, this.refreshToken, this.expiresIn);
        }
    }
}