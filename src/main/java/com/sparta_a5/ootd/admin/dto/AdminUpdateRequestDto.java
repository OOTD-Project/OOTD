package com.sparta_a5.ootd.admin.dto;

import com.sparta_a5.ootd.user.entity.UserRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminUpdateRequestDto {
    private String userRole;

}
