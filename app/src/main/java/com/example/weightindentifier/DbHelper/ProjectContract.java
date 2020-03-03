package com.example.weightindentifier.DbHelper;
import android.provider.BaseColumns;

public class ProjectContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private ProjectContract() {}

    /* Inner class that defines the table contents */
    public static class Project implements BaseColumns
    {
        public static final String TBL_USER = "user";
        public static final String ID = "ID";
        public static final String USERNAME = "userName";
        public static final String PASSWORD = "password";
        public static final String MOBILENUMBER = "mobile";
        public static final String V_NUMBER = "vNumber";







    }
}
