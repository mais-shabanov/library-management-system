package com.example.librarymanagementsystem.model;

public enum Privilege {

    USER_READ("user:read"),
    USER_CREATE("user:create"),
    USER_UPDATE("user:update"),
    USER_DELETE("user:delete"),
    LIBRARIAN_READ("librarian:read"),
    LIBRARIAN_CREATE("librarian:create"),
    LIBRARIAN_UPDATE("librarian:update"),
    LIBRARIAN_DELETE("librarian:delete")

    ;

    private final String privilege;

    Privilege(String privilege) {
        this.privilege = privilege;
    }

    public String getPrivilege() {
        return privilege;
    }
}
