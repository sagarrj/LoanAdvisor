package com.finance.loanadvisor.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * This entity class includes declaration of parameters of User class, no
 * arguments constructor, getter and setter of parameters.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	private String username;
	private String password;
	private String email;
	private char status = 'A';
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDttm;
	private Integer createdBy;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDttm;
	private Integer updatedBy;

}
