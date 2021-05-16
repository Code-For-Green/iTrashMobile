package com.github.codeforgreen.itrash.api.calendar;

public enum TrashType {

    ZMIESZANE("Zmieszane"),
    TWORZYWA("Tworzywa"),
    PAPIER("Papier"),
    SZKLO("Szkło"),
    BIO("Bio"),
    MYCIE_BIO("Mycie poj. bio"),
    MYCIE_KOM("Mycie poj. kom"),
    ;

    private final String name;

    TrashType(String name) {
        this.name = name;
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
