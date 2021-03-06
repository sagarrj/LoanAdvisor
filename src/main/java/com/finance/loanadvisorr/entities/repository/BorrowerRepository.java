package com.finance.loanadvisorr.entities.repository;

import com.finance.loanadvisorr.entities.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower,Integer> {

    List<Borrower> findByCustomer_customerIdAndSanction_sanctionId(Integer customerId, Integer sanctionId);
}
