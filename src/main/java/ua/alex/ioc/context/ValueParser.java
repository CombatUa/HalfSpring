package ua.alex.ioc.context;

public enum ValueParser {
    STRING {
        @Override
        public Object getValue(String toParse) {
            return toParse;
        }
    },
    INTEGER {
        @Override
        public Object getValue(String toParse) {
            return Integer.parseInt(toParse);
        }
    },
    INT {
        @Override
        public Object getValue(String toParse) {
            return INTEGER.getValue(toParse);
        }
    },
    CHAR {
        @Override
        public Object getValue(String toParse) {
            return toParse.charAt(0);
        }
    },
    SHORT {
        @Override
        public Object getValue(String toParse) {
            return Short.parseShort(toParse);
        }
    },
    LONG {
        @Override
        public Object getValue(String toParse) {
            return Long.parseLong(toParse);
        }
    },
    BYTE {
        @Override
        public Object getValue(String toParse) {
            return Byte.parseByte(toParse);
        }
    },
    FLOAT {
        @Override
        public Object getValue(String toParse) {
            return Float.parseFloat(toParse);
        }
    },
    DOUBLE {
        @Override
        public Object getValue(String toParse) {
            return Double.parseDouble(toParse);
        }
    };

    public abstract Object getValue(String toParse);

    public static boolean isValidEnum( String enumName) {
        if (enumName == null) {
            return false;
        } else {
            try {
                ValueParser.valueOf(enumName.toUpperCase());
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
    }
}
