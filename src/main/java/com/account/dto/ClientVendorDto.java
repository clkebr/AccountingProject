package com.account.dto;

import com.account.enums.ClientVendorType;
import com.account.entity.ClientVendor;
import com.account.validation.Unique;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientVendorDto {

	private Long id;

	@Unique(fieldName = "clientVendorName", entityClass = ClientVendor.class)
	@NotBlank(message = "Company Name is required field")
	@Size(max = 50, min = 2, message = "Company Name should have 2-50 characters long.")
	private String clientVendorName;


	@Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$" // +111 (202) 555-0125  +1 (202) 555-0125
			+ "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"                                  // +111 123 456 789
			+ "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$", message = "Phone is required field and may be in any valid phone number format.")
	private String phone;

	@Pattern(regexp = "^http(s{0,1})://[a-zA-Z0-9/\\-\\.]+\\.([A-Za-z/]{2,5})[a-zA-Z0-9/\\&\\?\\=\\-\\.\\~\\%]*", message = "Website should have a valid format.")
	private String website;

	@NotNull(message = "Please select a Type")
	private ClientVendorType clientVendorType;

	@NotNull
	@Valid
	private AddressDto address;

	private CompanyDto company;
}