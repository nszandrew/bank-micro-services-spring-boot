package br.com.nszandrew.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "account", url = "http://localhost:8081")
public interface AccountClient {

    @RequestMapping(method = RequestMethod.POST, value = "/api/account")
    void created(@RequestBody AccountRequestDTO accountType);

    @RequestMapping(method = RequestMethod.DELETE, value = "/api/account")
    void delete(@RequestBody Long idCustomer);
}
