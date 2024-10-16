package br.com.nszandrew.client.account;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "account", url = "http://localhost:8081")
public interface AccountClient {

    @RequestMapping(method = RequestMethod.GET, value = "/api/account")
    AccountResponseDTO getAccount(@RequestParam Long idCustomer);
}
