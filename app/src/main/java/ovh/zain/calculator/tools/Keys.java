package ovh.zain.calculator.tools;

public enum Keys {
    LAST_CALCULATION("last_expression");

    private final String key;

    Keys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
