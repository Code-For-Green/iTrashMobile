package com.github.codeforgreen.itrash.api.calendar;

public enum TrashType {

    ZMIESZANE("Zmieszane", "Zmieszane"),
    TWORZYWA("Tworzywa", "Tworzywa"),
    PAPIER("Papier", "Papier"),
    SZKLO("Szkło", "Szkło"),
    BIO("Bio", "Bio"),
    MYCIE_BIO("Mycie poj. bio", "Mycie pojemników bio"),
    MYCIE_KOM("Mycie poj. kom", "Mycie pojemników komunalnych"),
    ;

    public final String name;
    public final String niceName;

    TrashType(String name, String niceName) {
        this.name = name;
        this.niceName = niceName;
    }

    public static TrashType of(String name) {
        for (TrashType type : TrashType.values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }
        return null;
    }
}
