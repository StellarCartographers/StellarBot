package space.tscg.bot.edsm;

import java.io.IOException;
import java.time.Duration;

import lombok.Builder;
import lombok.Getter;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public final class OkCli
{
    private static final Duration TIME = Duration.ofMillis(10000);
    //!f
    public static final OkHttpClient CLIENT = new OkHttpClient.Builder()
        .followRedirects(true)
        .connectTimeout(TIME)
        .readTimeout(TIME)
        .writeTimeout(TIME)
        .build();
    //@f
    
    @Builder(builderClassName = "Builder")
    @Getter
    public static class BetterResponse {
        private int code;
        @lombok.Builder.Default
        private String body = "";
        @lombok.Builder.Default
        private String message = "";
        private Headers headers;
        private boolean isOk;
        
        public static BetterResponse fromResponse(Response response)
        {
            BetterResponse.Builder builder = BetterResponse.builder()
                .code(response.code())
                .isOk(response.isSuccessful())
                .headers(response.headers());
            
            if((response.message() != null) || !response.message().isBlank())
            {
                builder.message(response.message());
            }
            
            try
            {
                builder.body(response.body().string());
            } catch (IOException e)
            {
                builder.message(e.getMessage());
            }
            return builder.build();
        }
    }
}
