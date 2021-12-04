package uz.kapitalbank.bus.common.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Rustam Khalmatov
 */

public enum Lang {
    UZ("UZ", "O'zbekcha"),
    RU("RU", "Русский"),
    EN("EN", "English"),
    FR("FR", ""),
    DE("DE", ""),
    TR("TR", "");

    private String name;
    private String translate;

    Lang(String name) {
        this.name = name;
    }

    Lang(String name, String translate) {
        this.name = name;
        this.translate = translate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTranslate() {
        return translate;
    }

    public Lang setTranslate(String translate) {
        this.translate = translate;
        return this;
    }

    public static Lang getByName(final String name) {
        for (Lang lang : Lang.values()) {
            if (lang.getName().equals(name)) {
                return lang;
            }
        }
        return EN;
    }

    public static final List<Lang> languages = new ArrayList<>() {
        {
            add(UZ);
            add(RU);
            add(EN);
        }
    };

    public static List<KeyValue> getKeyValue() {
        return getKeyValue(languages);
    }

    public static List<KeyValue> getKeyValue(List<Lang> list) {
        return list.stream()
                .map(platform -> new KeyValue().setKey(platform.name()).setValue(platform.getTranslate()))
                .collect(Collectors.toList());
    }
}
