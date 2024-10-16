package br.com.nszandrew.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(value = "account", url = "http://localhost:8081")
public interface AccountClient {

    @RequestMapping(method = RequestMethod.PUT, value = "/api/account/ted")
    void transactionByTED(@RequestBody TransferTEDMoneyDTO transferTEDMoneyDTO);

    @RequestMapping(method = RequestMethod.PUT, value = "/api/account/deposit")
    void depositBalanceAccount(@RequestParam BigDecimal balance, @RequestParam String idCustomer);

    @RequestMapping(method = RequestMethod.PUT, value = "/api/account/withdraw")
    void withdrawBalanceAccount(@RequestParam BigDecimal balance, @RequestParam String idCustomer);
}
