package com.flexink.common.code;

public class FxBootType {
	public static final String Y = "Y";
    public static final String N = "N";

    public static enum Used {
        Y, N
    }

    public static enum Deleted {
        Y, N;
        
        public static Deleted get(String delYn) {
            for (Deleted deleted : Deleted.values()) {
                if (deleted.name().equals(delYn))
                    return deleted;
            }
            return null;
        }
    }
    
    public static enum Secret {
		Y, N
	}
    

    public static enum DataStatus {
        CREATED,
        MODIFIED,
        DELETED,
        ORIGIN
    }
}
