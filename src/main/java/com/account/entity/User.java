package com.account.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "role_id")
	Role role;
	@ManyToOne
	@JoinColumn(name = "company_id")
	Company company;
	@Column(unique = true)
	private String username;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	private String firstname;
	private String lastname;
	private String phone;
	private boolean enabled;
}
