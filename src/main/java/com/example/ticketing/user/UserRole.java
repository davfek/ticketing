package com.example.ticketing.user;

public enum UserRole {
    ADMIN,EXTERNAL,INTERNAL;

    UserRole() {
    }
    public static UserRole defineRoleFromString(String s){
        String formattedString = s.toLowerCase().replaceAll("[^a-zA-Z]", "").trim();

        switch (formattedString) {
            case "internal":
                return UserRole.INTERNAL;
            case "admin":
                return UserRole.ADMIN;
            default:
                return UserRole.EXTERNAL;
        }
    }
}
