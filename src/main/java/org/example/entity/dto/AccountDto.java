package org.example.entity.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.utils.enums.Currency;

@Data
public class AccountDto {
    @NotNull
    @NotEmpty
    private String accountNumber;
    @NotNull
    @Min(0)
    private Double value;
    @NotNull
    private Currency currency;

}
