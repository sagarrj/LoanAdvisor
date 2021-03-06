package com.finance.loanadvisor.entities.repository;

import com.finance.loanadvisorr.entities.Loan;
import com.finance.loanadvisorr.entities.LoanType;
import com.finance.loanadvisorr.entities.repository.LoanRepository;
import com.finance.loanadvisorr.loan.dto.LoanDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;




@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
public class LoanRepositoryTest {

	
	@Autowired
	LoanRepository loanRepository;
	private Loan loan;
	private LoanDTO loanVO;
	
	@BeforeEach
	void initEmployeeObject() {
		loan = new Loan();
		loan.setLoanId(1);
		loan.setLoanDesc("Home loan");
		loan.setROI(7.0);
		loan.setStatus('A');
		loan.setCreateDttm(new Date(2021 - 9 - 27));
		loan.setCreatedBy(null);
		loan.setUpdateDttm(null);
		loan.setUpdatedBy(null);
		LoanType loanType=new LoanType();
		loanType.setLoanDescription("HomeLoanDes");
		loan.setLoanType(loanType);
	}

	@BeforeEach
	void initEmployeeObject1() {
		loanVO = new LoanDTO();
		loanVO.setLoanId(1);
		loanVO.setLoanDesc("HOMELOAN");
		loanVO.setLoanType("HomeLoanDes");
		loanVO.setROI(7.0);

	}
	@Test
	public void testAllLoan() {
		 List<LoanDTO> listLoanVO= new ArrayList<>();
		 listLoanVO.add(new LoanDTO(1, "HOMELOAN",7.0,null ));
		List<LoanDTO> listLoan = Arrays.asList(loanVO);
		List<Loan> allCustomer =loanRepository.findAllByStatus('A');
		assertThat(allCustomer.size()).isGreaterThanOrEqualTo(1);
		
		}
	
	@Test
	public void testFindByIdUser() {
		 List<LoanDTO> listLoanVO= new ArrayList<>();
		 listLoanVO.add(new LoanDTO(1, "HOMELOAN",7.0,null ));
			List<LoanDTO> listLoan = Arrays.asList(loanVO);
			loanRepository.findById(1);
		Assertions.assertEquals(1, loan.getLoanId());

	}
	
	}

