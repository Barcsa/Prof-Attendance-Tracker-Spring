package com.licenta.controller.responses;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IdDTO {
    private Long id;

    public static IdDTO fromId(long id) {
        return new IdDTO(id);
    }
}
