package com.licenta.controller.responses;

import com.licenta.model.Role;
import com.licenta.model.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Data
public class UserDTO {
    private long id;

    private Date createdDate;

    private Date lastUpdateDate;

    private String name;

    private String surname;

    private String email;

    private String mobile;

    private List<Role> roles = new ArrayList<>();

    static public UserDTO fromEntity(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setCreatedDate(user.getCreatedDate());
        dto.setLastUpdateDate(user.getLastUpdateDate());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setSurname(user.getSurname());
        dto.setMobile(user.getMobile());
        if (dto.getRoles() != null)
            dto.setRoles(user.rawRoles());

        return dto;
    }
}
