package campuslib.models;

import campuslib.enums.UserStatus;

public class User extends Person {
    private final int enrollmentID;
    private String email;
    private UserStatus status;
    private int maxLoans;
}