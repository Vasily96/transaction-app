package org.example.entity.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankDto {
    @NotNull
    @NotEmpty
    @Size(min = 3)
    private String name;
    @Min(0)
    @Max(50)
    private Double individualCommission;
    @Min(0)
    @Max(50)
    private Double legalCommission;

}
