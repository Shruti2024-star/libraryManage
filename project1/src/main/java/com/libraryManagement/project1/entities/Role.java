package com.libraryManagement.project1.entities;


import java.util.Set;


public enum Role {

    // ADMIN HAS ALL PERMISSIONS
    ADMIN(
            Set.of(

                    // BOOK
                    Permission.BOOK_CREATE,
                    Permission.BOOK_UPDATE,
                    Permission.BOOK_DELETE,
                    Permission.BOOK_VIEW,
                    Permission.BOOK_VIEWALL,
                    Permission.BOOK_SEARCH,

                    // CATEGORY
                    Permission.CATEGORY_CREATE,
                    Permission.CATEGORY_UPDATE,
                    Permission.CATEGORY_DELETE,
                    Permission.CATEGORY_VIEW,
                    Permission.CATEGORY_VIEWALL,

                    // ISSUE
                    Permission.BOOK_ISSUE,
                    Permission.BOOK_RETURN,
                    Permission.ISSUE_VIEW_ACTIVE,
                    Permission.ISSUE_VIEW_HISTORY,
                    Permission.ISSUE_VIEWALL,

                    // RESERVATION
                    Permission.RESERVATION_CREATE,
                    Permission.RESERVATION_CANCEL,
                    Permission.RESERVATION_VIEW,
                    Permission.RESERVATION_VIEWALL,

                    // USER
                    Permission.USER_ALLUSERS
                  
            )
    ),

    // LIBRARIAN MANAGES BOOKS, ISSUES AND RESERVATIONS
    LIBRARIAN(
            Set.of(

                    // BOOK
                    Permission.BOOK_CREATE,
                    Permission.BOOK_UPDATE,
                    Permission.BOOK_VIEW,
                    Permission.BOOK_VIEWALL,
                    Permission.BOOK_SEARCH,

                    // CATEGORY
                    Permission.CATEGORY_CREATE,
                    Permission.CATEGORY_UPDATE,
                    Permission.CATEGORY_VIEW,
                    Permission.CATEGORY_VIEWALL,

                    // ISSUE
                    Permission.BOOK_ISSUE,
                    Permission.BOOK_RETURN,
                    Permission.ISSUE_VIEWALL,
                    

                    // RESERVATION
                    Permission.RESERVATION_VIEW,
                    
                    //user
                    Permission.USER_ALLUSERS
            )
    ),

    // NORMAL USER
    USER(
            Set.of(

                    // BOOK
                    Permission.BOOK_VIEW,
                    Permission.BOOK_SEARCH,
                    Permission.BOOK_VIEWALL,

                    // ISSUE
                    Permission.BOOK_ISSUE,
                    Permission.BOOK_RETURN,
                    Permission.ISSUE_VIEW_HISTORY,
                    Permission.ISSUE_VIEW_ACTIVE,

                    // RESERVATION
                    Permission.RESERVATION_CREATE,
                    Permission.RESERVATION_CANCEL,
                    Permission.RESERVATION_VIEW
            )
    );

    private final Set<Permission> permissions;


    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }
}