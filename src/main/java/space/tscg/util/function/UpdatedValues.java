package space.tscg.util.function;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class UpdatedValues extends HashMap<String, Object>
{
    public UpdatedValues(Map<String, Object> map)
    {
        super(map);
    }

    public static UpdatedValues.Builder Builder()
    {
        return new UpdatedValues.Builder();
    }

    public static class Builder
    {
        private Map<String, Object> objMap;

        private Builder()
        {
            this.objMap = new HashMap<>();
        }

        public final UpdatedValues buildUpdate()
        {
            return new UpdatedValues(this.objMap);
        }

        public Builder append(String fieldName, String v1, String v2)
        {
            if (!v1.equalsIgnoreCase(v2))
                this.objMap.put(fieldName, v1);
            return this;
        }

        public Builder append(String fieldName, boolean v1, boolean v2)
        {
            if (v1 != v2)
                this.objMap.put(fieldName, v1);
            return this;
        }

        public Builder append(String fieldName, int v1, int v2)
        {
            if (v1 != v2)
                this.objMap.put(fieldName, v1);
            return this;
        }

        public Builder append(String fieldName, long v1, long v2)
        {
            if (v1 != v2)
                this.objMap.put(fieldName, v1);
            return this;
        }

        public Builder appendDiff(String fieldName, UpdatedValues v1)
        {
            if(!v1.isEmpty())
                this.objMap.put(fieldName, v1);
            return this;
        }
    }
}
