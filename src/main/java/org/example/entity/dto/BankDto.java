package org.example.entity.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    private Double individualCommission;
    @Min(0)
    private Double legalCommission;

}
