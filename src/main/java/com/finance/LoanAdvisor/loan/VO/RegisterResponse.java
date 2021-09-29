package com.finance.LoanAdvisor.loan.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {

    private Integer tenure;
    private Double emi;
}
