package space.tscg.capi;

import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import net.dv8tion.jda.internal.interactions.component.ButtonImpl;

public enum CAPIButton
{
    CONSENT("consent", "Agree", ButtonStyle.SUCCESS, false),
    AUTHORIZE("authorize", "Authorize With Frontier", ButtonStyle.PRIMARY, true);

    private final String id;
    private final String label;
    private final ButtonStyle style;
    private boolean isLink;
    
    
    CAPIButton(String id, String label, ButtonStyle style, boolean isLink)
    {
        this.id = id;
        this.label = label;
        this.style = style;
        this.isLink = isLink;
    }
    
    public Button get()
    {
        return new ButtonImpl(id, label, style, false, null);
    }
    
    public Button getWithUrl(String url)
    {
        if(isLink)
        {
            return new ButtonImpl(id, label, style, url, false, null);
        }
        
        return get();
    }
}
