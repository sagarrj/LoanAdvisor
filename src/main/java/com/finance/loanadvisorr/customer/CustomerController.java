package com.finance.loanadvisorr.customer;

import com.finance.loanadvisorr.Sanction.dto.SanctionDTO;
import com.finance.loanadvisorr.customer.dto.CustomerDTO;
import com.finance.loanadvisorr.entities.Customer;
import com.finance.loanadvisorr.entities.Sanction;
import com.finance.loanadvisorr.exception.DataNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author priypawa
 *
 */
@Validated
@RestController
@RequestMapping("/customer")
public class CustomerController {

	private static final String CUSTOMER_IS_ALREADY_CREATED = "Customer is already created";

	private static final String CUSTOMER_NOT_FOUND = "Customer not found";

	private static final String LIST_IS_EMPTY = "List is empty";

	@Autowired
	CustomerService customerService;

	Logger logger = LoggerFactory.getLogger(CustomerController.class);

	/**
	 * This method returns list of available customers
	 * 
	 * @return {@link List} of {@link CustomerDTO}
	 *
	 * @return {@link List} of {@link CustomerDTO}
	 *
	 */
	@GetMapping("/list")
	public ResponseEntity<List<CustomerDTO>> getAllCustomers() throws DataNotFoundException {
		logger.info("List of customers from controller");
		List<CustomerDTO> customerDTOList = customerService.getAllCustomers();
		if (customerDTOList.isEmpty())
			throw new DataNotFoundException(LIST_IS_EMPTY);
		return new ResponseEntity<List<CustomerDTO>>(customerDTOList, HttpStatus.OK);

	}

	/**
	 * This method accepts customer Id and returns customer details based on Id.
	 *
	 * @param customerId : {@link Integer}
	 * @return {@link ResponseEntity} of {@link CustomerDTO}
	 */
	@GetMapping("/view/{id}")
	public ResponseEntity<CustomerDTO> getCustomer(
			@PathVariable("id") @NotNull(message = "Customer Id should not be empty")  @Min(value = 1, message = "Customer Id must be greater than or equal to 1")
            @Max(value = 1000, message = "Customer Id must be lower than or equal to 1000") Integer customerId)
			throws DataNotFoundException {
		logger.info("Customer returned from controller");
		CustomerDTO customerInfo = customerService.getCustomer(customerId);
		if (customerInfo == null) {
			throw new DataNotFoundException(CUSTOMER_NOT_FOUND);
		}
		return new ResponseEntity<CustomerDTO>(customerInfo, HttpStatus.OK);
	}

	/**
	 * This method accepts and saves customer details and return an object of
	 * {@link Customer} containing all arguments which has been saved.
	 * 
	 * @param customer : {@link Customer} Object.
	 * @return {@link ResponseEntity} of {@link CustomerDTO}
	 */
	@PostMapping("/add")
	public ResponseEntity<CustomerDTO> addCustomer(@Valid @RequestBody Customer customer) throws DataNotFoundException {
		logger.info("Customer returned from controller");
		CustomerDTO customerInfo = customerService.addCustomer(customer);
		if (customerInfo == null) {
			throw new DataNotFoundException(CUSTOMER_IS_ALREADY_CREATED);
		}
		return new ResponseEntity<CustomerDTO>(customerInfo, HttpStatus.CREATED);
	}

	/**
	 * This gets data from table and return an object of {@link SanctionDTO}
	 * containing all arguments which has been saved {@link Sanction} Object.
	 *
	 * @return {@link ResponseEntity} of {@link SanctionDTO}
	 */
	@GetMapping("/sanction/{customerId}/{loanId}")
	public ResponseEntity<SanctionDTO> loanEligibility(@PathVariable("customerId") Integer customerId,
			@PathVariable("loanId") Integer loanId) {
		SanctionDTO sanctionInfo = customerService.customerLoanEligibility(customerId, loanId);
		return new ResponseEntity<SanctionDTO>(sanctionInfo, HttpStatus.OK);
	}

}
