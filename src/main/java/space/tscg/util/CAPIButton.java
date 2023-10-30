package space.tscg.util;

import lombok.Getter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import net.dv8tion.jda.internal.interactions.component.ButtonImpl;

public enum CAPIButton
{
    CONSENT("consent", "Agree", ButtonStyle.SUCCESS),
    AUTHORIZE(null, "Authorize With Frontier", ButtonStyle.LINK);

    @Getter
    private final String id;
    private final String label;
    private final ButtonStyle style;
    
    
    CAPIButton(String id, String label, ButtonStyle style)
    {
        this.id = id;
        this.label = label;
        this.style = style;
    }
    
    public Button get()
    {
        return new ButtonImpl(id, label, style, false, null);
    }
    
    public Button getWithUrl(String url)
    {
        if(!this.equals(CONSENT))
        {
            return new ButtonImpl(null, label, style, url, false, null);
        }
        
        return get();
    }
}
