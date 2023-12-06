package com.sparta_a5.ootd.user.entity;

public enum UserRoleEnum {
    USER(Authority.USER),  // 사용자 권한
    ADMIN(Authority.ADMIN),
    INF(Authority.INF),
    BUSINESS(Authority.BUSINESS);  // 관리자 권한

    private final String authority;

    UserRoleEnum(String authority) {
        if(authority == null) {
            authority = Authority.USER;
        }
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String USER = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String INF = "ROLE_INF";
        public static final String BUSINESS = "ROLE_BUSINESS";
    }
}
