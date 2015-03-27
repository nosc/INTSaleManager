package sample.controllers;

/**
 * Created by dima on 25.03.15.
 */
public class DataSession {
    public static String url;
    public static String user;
    public static String password;
    public static String sqlpath = "jdbc:sqlite:"+System.getProperty("user.dir") +"/data.db";
}
