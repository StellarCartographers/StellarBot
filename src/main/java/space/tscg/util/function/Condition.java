package space.tscg.util.function;

import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;

@SuppressWarnings({"rawtypes", "unchecked"})
public enum Condition {
    GREATER_THAN_0((Predicate<Integer>) i -> i > 0),
    NOT_EMPTY_LIST((Predicate<Collection<?>>) c -> !(c != null) || !c.isEmpty()),
    NOT_EMPTY_STRING((Predicate<String>) c -> !(c != null) || !c.isBlank() || !c.isEmpty()),
    NOT_EMPTY_MAP((Predicate<Map<?, ?>>) c -> !(c != null) || !c.isEmpty());

    private Predicate<Object> predicate;

    Condition(Predicate predicate)
    {
        this.predicate = predicate;
    }
    
    public boolean check(Object o)
    {
        try {
            return predicate.test(o);
        } catch (ClassCastException e) {
            return true;
        }
    }
}