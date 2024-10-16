package br.com.nszandrew.client.credit;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "credit", url = "http://localhost:8083")
public interface CreditClient {

    @RequestMapping(method = RequestMethod.GET, value = "/api/credit")
    CreditResponseDTO getCredits(@RequestParam(value = "accountNumber") String accountNumber);
}
