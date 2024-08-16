package com.yo.model;



import com.yo.security.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "USER_DATA")
public class UserData {
	/**
	 * 
	 */
	@Id
	  @GeneratedValue
	  private Integer id;
	  private String name;
	  private String username;
	  private String password;

	  @Enumerated(EnumType.STRING)
	  private Role role;

	

}
