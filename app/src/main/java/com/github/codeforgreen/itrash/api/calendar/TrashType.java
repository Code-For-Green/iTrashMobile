package com.github.codeforgreen.itrash.api.calendar;

import android.content.Context;

import com.github.codeforgreen.itrash.R;

public enum TrashType {

    ZMIESZANE("Zmieszane", R.string.trash_type_zmieszane),
    TWORZYWA("Tworzywa", R.string.trash_type_tworzywa),
    PAPIER("Papier", R.string.trash_type_papier),
    SZKLO("Szkło", R.string.trash_type_szklo),
    BIO("Bio", R.string.trash_type_bio),
    MYCIE_BIO("Mycie poj. bio", R.string.trash_type_mycie_bio),
    MYCIE_KOM("Mycie poj. kom", R.string.trash_type_mycie_kom),
    ;

    public final String name;
    public final int niceName;

    TrashType(String name, int niceName) {
        this.name = name;
        this.niceName = niceName;
    }

    public String getNiceName(Context context) {
        return context.getString(this.niceName);
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
