package br.com.nszandrew.client.account;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(value = "account", url = "http://localhost:8081")
public interface AccountClient {

    @RequestMapping(method = RequestMethod.PUT, value = "/api/account/card")
    void withdraw(@RequestParam(value = "balance") BigDecimal balance, @RequestParam(value = "accountNumber") String accountNumber);

    @RequestMapping(method = RequestMethod.GET, value = "/api/account/accountnumber")
    AccountResponseDTO getAccount(@RequestParam(value = "accountNumber") String accountNumber);
}
