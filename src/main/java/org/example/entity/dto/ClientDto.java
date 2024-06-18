package org.example.entity.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.utils.enums.ClientsType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    @NotNull
    @NotEmpty
    @Size(min = 2, message = "size should be greater than 1!!!!")
    private String name;
    @NotNull
    private ClientsType type;
}
