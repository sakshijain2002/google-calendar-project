package com.auth.model;

import lombok.Data;

@Data
public class UpdateRoleRequest {
    private String email;
    private String role;
}