package com.finance.loanadvisorr.loan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest
{
    private Integer customerId;
    private Integer sanctionId;
    private Integer preferredTenure;


}
