package space.tscg.capi.modal;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder(builderClassName = "Builder", builderMethodName = "Builder")
@Jacksonized
public class EliteInfo
{
    private String cmdrName;
    private String id;
    private String systemAddress;
}
