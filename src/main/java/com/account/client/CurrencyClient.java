package com.account.client;

import com.account.dto.CurrencyApiResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "CURRENCY-CLIENT", url = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/usd.json")
public interface CurrencyClient {

	@GetMapping()
	CurrencyApiResponseDto getData();

}
