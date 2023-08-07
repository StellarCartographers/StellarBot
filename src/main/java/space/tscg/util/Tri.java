package space.tscg.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import space.tscg.util.Tri.Bi;

public class Tri<A, B, C> extends HashMap<A, Bi<B, C>>
{
    private static final long serialVersionUID = 1L;
    private Map<B, A>         bToAReference    = new HashMap<>();

    public Tri()
    {
        super();
    }

    public void put(A a, B b, C c)
    {
        this.bToAReference.put(b, a);
        super.put(a, new Bi<>(b, c));
    }
    
    public Optional<C> getCReference(B b)
    {
        for(Bi<B,C> ref : this.values())
        {
            if(ref.containsKey(b))
            {
                return Optional.of(ref.get(b));
            }
        }
        
        return Optional.empty();
    }

    public boolean deleteByReference(B b)
    {
        A        a       = bToAReference.get(b);
        Bi<B, C> current = super.get(a);
        return super.remove(a, current);
    }

    public static class Bi<B, C> extends HashMap<B, C>
    {
        private static final long serialVersionUID = 1L;

        private Bi(B b, C c)
        {
            super();
            put(b, c);
        }
    }
}
