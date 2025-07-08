package com.qb.app.model;

public class HandleTheException {

    public static void takeCareOfIt(Exception e) {
        e.printStackTrace();
        getLogger.logger().warning(e.toString());
    }
}
