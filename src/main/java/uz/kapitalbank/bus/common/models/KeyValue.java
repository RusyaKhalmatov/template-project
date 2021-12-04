package uz.kapitalbank.bus.common.models;

public class KeyValue {
    String key;
    String value;

    public String getKey() {
        return key;
    }

    public KeyValue setKey(String key) {
        this.key = key;
        return this;
    }

    public String getValue() {
        return value;
    }

    public KeyValue setValue(String value) {
        this.value = value;
        return this;
    }
}
