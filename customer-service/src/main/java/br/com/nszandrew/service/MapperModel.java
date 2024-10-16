package br.com.nszandrew.service;

import br.com.nszandrew.model.Customer;
import br.com.nszandrew.model.dto.GetCustomerDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
public class MapperModel implements Serializable {

    private static ModelMapper mapper = new ModelMapper();

    public static <O, D> D parseObject(O origin, Class<D> destination) {
        return mapper.map(origin, destination);
    }

    public static List<GetCustomerDTO> parseListObjects(List<Customer> all, Class<GetCustomerDTO> destination) {
        List<GetCustomerDTO> destinationObjects = new ArrayList<GetCustomerDTO>();

        for (Customer customer : all) {
            destinationObjects.add(mapper.map(customer, destination));
        }
        return destinationObjects;
    }
}
