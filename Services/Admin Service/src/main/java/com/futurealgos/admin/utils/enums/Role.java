package com.futurealgos.admin.utils.enums;

public enum Role {
    ADMINISTRATOR("ADMINISTRATOR", "Administrator Of Whole Platform"),
    SUPERVISOR("SUPERVISOR", "Managers of the Company. Supervise the operators"),
    OPERATOR("OPERATOR", "Handlers of machines"),

    GUEST("GUEST", "registered User");

    public static final Role DEFAULT_ROLE = OPERATOR;
    private final String name;
    private final String description;

    Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static Role fromString(String role) {
        Role[] roles = values();
        for (int i = 0; i < roles.length; ++i) {
            Role toReturn = roles[i];
            if (toReturn.getName().equals(role)) {
                return toReturn;
            }
        }
        throw new IllegalArgumentException("Cannot Create enum from" + role);
    }

    public static boolean isAdmin(String role) {
        Role converted = fromString(role);
        return converted.equals(Role.ADMINISTRATOR);
    }

    public static boolean isSupervisor(String role) {
        Role converted = fromString(role);
        return converted.equals(Role.SUPERVISOR);
    }

    public static boolean isOperator(String role) {
        Role converted = fromString(role);
        return converted.equals(Role.OPERATOR);
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }
}
