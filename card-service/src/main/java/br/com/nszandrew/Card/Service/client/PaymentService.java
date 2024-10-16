package br.com.nszandrew.Card.Service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "payment", url = "http://localhost:8084")
public interface PaymentService {

    @RequestMapping(method = RequestMethod.POST, value = "/api/payment/credit")
    void creditPurchase(@RequestBody PaymentCreditRequestDTO data);

    @RequestMapping(method = RequestMethod.POST, value = "/api/payment/debit")
    void debitPurchase(@RequestBody PaymentDebitRequestDTO data);
}
