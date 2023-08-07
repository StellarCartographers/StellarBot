package space.tscg.capi;

import java.net.URI;

public class T
{

    public static void main(String[] args)
    {
        URI uri = URI.create(Constants.CALLBACK_URL_STRING + "/393847930039173131");
        System.out.println(uri.toString());
    }

}
