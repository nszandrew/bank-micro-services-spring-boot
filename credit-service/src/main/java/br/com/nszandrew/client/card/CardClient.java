package br.com.nszandrew.client.card;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "card", url = "http://localhost:8085")
public interface CardClient {

    @RequestMapping(method = RequestMethod.POST, value = "/api/card")
    void addCard(@RequestBody CardRequestDTO card);
}
