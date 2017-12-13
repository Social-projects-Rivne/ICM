package ua.softserve.rv_028.issuecitymonitor.entity.enums;

public enum UserRole {
    ADMIN,
    MODERATOR,
    USER;

    UserRole(){

    }

    @Override
    public String getAuthority() {
        return this.name();
    }

    public static Collection<GrantedAuthority> collectionForRole(UserRole userRole){
        if (userRole == ADMIN)
            return Arrays.asList(USER, MODERATOR, ADMIN);
        else if (userRole == MODERATOR)
            return Arrays.asList(USER, MODERATOR);
        else
            return Collections.singletonList(USER);
    }
}