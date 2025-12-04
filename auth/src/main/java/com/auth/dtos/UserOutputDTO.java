package com.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.internal.build.AllowNonPortable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserOutputDTO {
    private String message;
    private String token;
}
