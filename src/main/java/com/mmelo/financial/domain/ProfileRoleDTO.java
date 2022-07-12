package com.mmelo.financial.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileRoleDTO {
    private Long id;
    private ProfileDTO profile;
    private RoleDTO role;
}