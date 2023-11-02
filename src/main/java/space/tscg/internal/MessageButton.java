package space.tscg.internal;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;

@Getter
public enum MessageButton
{
    REGISTER_CARRIER    ("register"   , "New Carrier"        , ButtonStyle.PRIMARY),
    PURCHASE_LIST       ("buy-list"   , "Get Purchase Orders", ButtonStyle.PRIMARY),
    SELL_LIST           ("sell-list"  , "Get Sell Orders"    , ButtonStyle.PRIMARY),
    MODULE_LIST         ("module-list", "Get Module List"    , ButtonStyle.PRIMARY),
    SHIPYARD_LIST       ("ship-list"  , "Get Shipyard List"  , ButtonStyle.PRIMARY),
    ORDERS_HIDE         ("order-h"    , "Hide Orders"        , ButtonStyle.DANGER),
    ORDERS_DISPLAY      ("order-d"    , "Display Orders"     , ButtonStyle.SUCCESS),
    OUTFITTING_HIDE     ("outfit-h"   , "Hide Outfitting"    , ButtonStyle.DANGER),
    OUTFITTING_DISPLAY  ("outfit-d"   , "Display Outfitting" , ButtonStyle.SUCCESS),
    SHIPYARD_HIDE       ("shipyard-h" , "Hide Shipyard"      , ButtonStyle.DANGER),
    SHIPYARD_DISPLAY    ("shipyard-d" , "Display Shipyard"   , ButtonStyle.SUCCESS),
    REFRESH             ("refresh"    , "Refresh Carrier"    , ButtonStyle.PRIMARY),
    CONSENT             ("consent"    , "Agree"              , ButtonStyle.SUCCESS),
    DENY                ("deny"       , "Decline"            , ButtonStyle.DANGER),
    MOCK_FRONTIER       ("mock-auth"  , "Mock Frontier Login", ButtonStyle.SECONDARY),
    ;
    
    private String id;
    final Button button;
    private static Map<String, MessageButton> idMap = new HashMap<>();
    
    static {
       for(var button : MessageButton.values()) {
           idMap.put(button.getId(), button);
       }
    }
    
    private MessageButton(String id, String label, ButtonStyle style)
    {
        this.id = id;
        this.button = Button.of(style, id, label);
    }
    
    public boolean equals(Button button)
    {
        return button.getId().equals(id);
    }
    
    public static boolean equalsAny(Button button)
    {
        var id = button.getId();
        return  id.equals(PURCHASE_LIST.getId()) ||
                id.equals(SELL_LIST.getId()) ||
                id.equals(MODULE_LIST.getId()) ||
                id.equals(SHIPYARD_LIST.getId()) ||
                id.equals(ORDERS_HIDE.getId()) ||
                id.equals(ORDERS_DISPLAY.getId()) ||
                id.equals(OUTFITTING_HIDE.getId()) ||
                id.equals(OUTFITTING_DISPLAY.getId()) ||
                id.equals(SHIPYARD_HIDE.getId()) ||
                id.equals(SHIPYARD_DISPLAY.getId()) ||
                id.equals(REFRESH.getId());
    }
    
    public static MessageButton get(ButtonInteractionEvent event)
    {
        return idMap.get(event.getButton().getId());
    }
}
