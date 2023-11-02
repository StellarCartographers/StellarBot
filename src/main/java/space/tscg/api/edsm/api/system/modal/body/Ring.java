package space.tscg.api.edsm.api.system.modal.body;

import lombok.Getter;

@Getter
public class Ring
{
    private String name;
    private String type;
    private long mass;
    private int innerRadius;
    private int outerRadius;
}
