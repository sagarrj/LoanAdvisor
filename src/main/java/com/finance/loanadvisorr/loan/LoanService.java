package com.finance.loanadvisorr.loan;

import com.finance.loanadvisorr.config.ApplicationConstants;
import com.finance.loanadvisorr.entities.Borrower;
import com.finance.loanadvisorr.entities.Customer;
import com.finance.loanadvisorr.entities.Loan;
import com.finance.loanadvisorr.entities.Sanction;
import com.finance.loanadvisorr.entities.repository.BorrowerRepository;
import com.finance.loanadvisorr.entities.repository.CustomerRepository;
import com.finance.loanadvisorr.entities.repository.LoanRepository;
import com.finance.loanadvisorr.entities.repository.SanctionRepository;
import com.finance.loanadvisorr.exception.ApplicationException;
import com.finance.loanadvisorr.loan.dto.LoanDTO;
import com.finance.loanadvisorr.loan.dto.RegisterRequest;
import com.finance.loanadvisorr.loan.dto.RegisterResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author pkhedkar
 *
 */
/**
 * @author pkhedkar
 *
 */
/**
 * @author pkhedkar
 *
 */
/**
 * @author pkhedkar
 *
 */
/**
 * @author pkhedkar
 *
 */
@Service
@RequiredArgsConstructor
public class LoanService {

	@Autowired
	ModelMapper modelMapper;

	Logger logger = LoggerFactory.getLogger(LoanController.class);

	private final LoanRepository loanRepository;
	private final SanctionRepository sanctionRepository;
	private final CustomerRepository customerRepository;
	private final BorrowerRepository borrowerRepository;

	public RegisterResponse registerCustomerForLoan(RegisterRequest registerRequest) {

		if(registerRequest.getCustomerId() < 1 || registerRequest.getSanctionId() <1 ){
			throw new ApplicationException(ApplicationConstants.INVALID_INPUT);
		}

		List<Borrower> borrowers = borrowerRepository.findByCustomer_customerIdAndSanction_sanctionId(registerRequest.getCustomerId(),
				registerRequest.getSanctionId());
		if(!borrowers.isEmpty()){
			throw new ApplicationException(ApplicationConstants.CUSTOMER_ALREADY_REGISTERED_FOR_THIS_LOAN);
		}

		Optional<Customer> optionalCustomer = customerRepository.findById(registerRequest.getCustomerId());
		Optional<Sanction> optionalSanction = sanctionRepository.findById(registerRequest.getSanctionId());
		if (optionalCustomer.isPresent() && optionalSanction.isPresent()) {
			Customer customer = optionalCustomer.get();
			Sanction sanction = optionalSanction.get();

			Integer maxTenure = ApplicationConstants.MAX_AGE - customer.getAge();
			if (maxTenure < 1) {
				throw new ApplicationException(ApplicationConstants.CANNOT_PROVIDE_LOAN_AFTER + ApplicationConstants.MAX_AGE);
			}else if(maxTenure <= 1){
				throw new ApplicationException(ApplicationConstants.CANNOT_PROVIDE_LOAN_FOR_SUCH_SMALL_DURATION + maxTenure);
			}

			Integer tenure = registerRequest.getPreferredTenure() < maxTenure ? registerRequest.getPreferredTenure()
					: maxTenure;
			Double emi = getEMI(sanction.getROI(), tenure, sanction.getLoanAmount());

			Borrower borrower = new Borrower();
			borrower.setCustomer(customer);
			borrower.setSanction(sanction);
			borrower.setTenure(tenure);
			borrower.setEmi(emi);
			borrower.setStatus('A');
			borrower.setCreateDttm(new Date());
			Borrower save = borrowerRepository.save(borrower);

			RegisterResponse registerResponse = new RegisterResponse();
			registerResponse.setEmi(emi);
			registerResponse.setTenure(tenure);
			return registerResponse;

		} else {
			throw new ApplicationException(ApplicationConstants.CUSTOMER_SANCTION_NOT_FOUND);
		}

	}

	/**
	 * This method accepts loan Id and returns loan details based on Id
	 *
	 * @param id:{@link Integer}
	 * @return {@link LoanDTO}
	 * @throws ApplicationException
	 */
	public LoanDTO getLoan(int id) throws ApplicationException {

		Optional<Loan> optionalLoan = loanRepository.findById(id);
//Optional.ofNullable(optionalLoan)!=null

		if (!optionalLoan.isPresent() || optionalLoan == null) {

			logger.warn("Loan not found");
			throw new ApplicationException(ApplicationConstants.LOAN_NOT_FOUND);
		}

		Loan loan = optionalLoan.get();
		logger.info("Loan returned from service");
		LoanDTO loanDTO = convertToLoanDTO(loan);
		return loanDTO;

	}

	/**
	 * This method returns list of all available Loan
	 * 
	 * @return {@link LoanDTO}
	 * @throws ApplicationException
	 */
	public List<LoanDTO> getAllLoan() throws ApplicationException {

		List<Loan> loanList = loanRepository.findAllByStatus(ApplicationConstants.ACTIVE);
		if (loanList.isEmpty()) {
			logger.warn("List is empty");
			throw new ApplicationException(ApplicationConstants.LIST_IS_EMPTY);
		}
		logger.info("List of Loans from service");
		List<LoanDTO> loanDTOList = convertToLoanDTOList(loanList);
		return loanDTOList;

	}

	/**
	 *
	 * This method converts List {@link Loan} object into {@link LoanDTO} object
	 *
	 * @param loans:{@link Loan}
	 * @return {@link LoanDTO}
	 *
	 */
	private List<LoanDTO> convertToLoanDTOList(List<Loan> loans) {
		List<LoanDTO> loanDTOList = new ArrayList<>();

		for (Loan loan : loans) {

			loanDTOList.add(convertToLoanDTO(loan));
		}

		return loanDTOList;
	}

	/**
	 * This method contain {@link Loan} of boject into {@link LoanDTO} This method
	 * contain model mapper
	 *
	 * @param loan
	 * @return
	 */
	private LoanDTO convertToLoanDTO(Loan loan) {
		LoanDTO loanDTO = modelMapper.map(loan, LoanDTO.class);
		loanDTO.setLoanType(loan.getLoanType().getLoanDescription());
		return loanDTO;

	}

	public Double getEMI(Double rate, Integer tenure, Double principal) {
		Double emi;

		rate = rate / (12 * 100); // one month interest
		tenure = tenure * 12; // one month period
		emi = (principal * rate * (float) Math.pow(1 + rate, tenure)) / (float) (Math.pow(1 + rate, tenure) - 1);

		return (double) Math.round(emi);
	}

}
