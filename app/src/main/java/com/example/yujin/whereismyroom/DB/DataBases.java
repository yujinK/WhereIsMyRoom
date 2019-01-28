package com.example.yujin.whereismyroom.DB;

import android.provider.BaseColumns;

public final class DataBases {
    public static final class CreateDB implements BaseColumns {
        public static final String TABLE_NAME = "room";

        public static final String DEPOSIT = "deposit";
        public static final String RENT_MONTH = "rent_month";
        public static final String UTILITIES = "utilities";
        public static final String INCLUDED_UTILITIES = "included_utilities";
        public static final String BUILD_FLOOR = "build_floor";
        public static final String MY_FLOOR = "my_floor";
        public static final String DIRECTION = "direction";
        public static final String ROOM_TYPE = "room_type";
        public static final String ROOM_SIZE_M = "room_size_m";
        public static final String ROOM_SIZE_P = "room_size_p";
        public static final String OPTION = "option";
        public static final String ANIMAL = "animal";
        public static final String ELEVATOR = "elevator";
        public static final String PARKING = "parking";
        public static final String DETAIL = "detail";

        public static final String _CREATE = "create table if not exists " + TABLE_NAME + "("
                + _ID + " integer primary key autoincrement, "
                + DEPOSIT + " integer not null, "
                + RENT_MONTH + " integer not null, "
                + UTILITIES + " integer not null, "
                + INCLUDED_UTILITIES + " text, "
                + BUILD_FLOOR + " integer, "
                + MY_FLOOR + " integer, "
                + DIRECTION + " text, "
                + ROOM_TYPE + " text, "
                + ROOM_SIZE_M + " real, "
                + ROOM_SIZE_P + " real, "
                + OPTION + " text, "
                + ANIMAL + " integer, "
                + ELEVATOR + " integer, "
                + PARKING + " integer, "
                + DETAIL + " text);";
    }
}
