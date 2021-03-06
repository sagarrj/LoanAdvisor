package com.finance.loanadvisorr.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


/**
 * @author pkhedkar
 * This entity class includes declaration of parameters of 
 *         Customer class, no arguments constructors and parameterized constructors, getter
 *         and setter of parameters.
 *
 */
@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Integer loanId;
    @ManyToOne
	@JoinColumn(name = "LOAN_TYPE_ID")
    private LoanType loanType;

    private String loanDesc;
    private Double ROI;
    private char status = 'A';
	@Temporal(TemporalType.TIMESTAMP)
    private Date createDttm;
    private Integer createdBy;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDttm;
    private Integer updatedBy;
    

    
   /* @OneToMany(mappedBy = "loan")
 
=======

   

    
    
   /* @OneToMany(mappedBy = "loan")

	
    
  

>>>>>>> cf208cca6162a7c3cfe518188ac9e34eca4d641e
    /*@OneToMany(mappedBy = "loan")

    private Set<Sanction> sanctionSet = new HashSet<>();*/
}
