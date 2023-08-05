package space.tscg.bot.edsm;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import space.tscg.bot.edsm.OkCli.BetterResponse;

public enum Http
{

    GET,
    POST,
    DELETE,
    PATCH;

    Request.Builder reqBuilder = new Request.Builder();

    public Http header(String key, String value)
    {
        reqBuilder.addHeader(key, value);
        return this;
    }

    public Http headers(Headers headers)
    {
        reqBuilder.headers(headers);
        return this;
    }

    public BetterResponse call(String url, RequestBody body)
    {
        return call(HttpUrl.parse(url), body);
    }

    public BetterResponse call(HttpUrl url, RequestBody body)
    {
        switch (this)
        {
            case POST -> reqBuilder.post(body);
            case PATCH -> reqBuilder.patch(body);
            case DELETE -> reqBuilder.delete(body);
            case GET -> call(url);
        }
        Request r = reqBuilder.url(url).build();
        try (Response response = OkCli.CLIENT.newCall(r).execute())
        {
            return BetterResponse.fromResponse(response);
        } catch (IOException e)
        {
            return BetterResponse.fromResponse(new Response.Builder().message(e.getMessage()).code(500).build());
        }
    }

    public BetterResponse call(String url)
    {
        return call(HttpUrl.parse(url));
    }

    public BetterResponse call(HttpUrl url)
    {
        switch (this)
        {
            case POST -> throw new IllegalArgumentException(this.name() + " Requires a RequestBody!");
            case PATCH -> throw new IllegalArgumentException(this.name() + " Requires a RequestBody!");
            case DELETE -> reqBuilder.delete();
            default -> reqBuilder.get();
        }
        Request r = reqBuilder.url(url).build();
        try (Response response = OkCli.CLIENT.newCall(r).execute())
        {
            return BetterResponse.fromResponse(response);
        } catch (IOException e)
        {
            return BetterResponse.fromResponse(new Response.Builder().message(e.getMessage()).code(500).build());
        }
    }
}
