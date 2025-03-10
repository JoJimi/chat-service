package org.example.chatservice.type;

import java.util.Arrays;

public enum RoleType {
    USER("ROLE_USER"), CONSULTANT("ROLE_CONSULTANT");

    String code;

    RoleType(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }

    public static RoleType fromCode(String code){
        return Arrays.stream(RoleType.values())
                .filter(role -> role.getCode().equals(code))
                .findFirst()
                .orElseThrow();
    }
}
