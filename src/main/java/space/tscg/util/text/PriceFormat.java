package space.tscg.util.text;

import java.text.DecimalFormat;

public class PriceFormat extends DecimalFormat
{
    private static final long serialVersionUID = -8484935705137040711L;

    private volatile static PriceFormat instance;

    private static PriceFormat get()
    {
        if (instance == null) {
            synchronized (PriceFormat.class) {
                if (instance == null) {
                    instance = new PriceFormat();
                }
            }
        }
        return instance;
    }
    
    public static String of(int number)
    {
        return get().format((long) number);
    }
    
    private PriceFormat()
    {
        super("#");
        this.setGroupingUsed(true);
        this.setGroupingSize(3);
    }
}
