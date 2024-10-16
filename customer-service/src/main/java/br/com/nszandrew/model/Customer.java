package br.com.nszandrew.model;

import br.com.nszandrew.model.dto.CustomerRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_customer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
public class Customer implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String email;
    private LocalDate dateOfBirth;
    private String cpf;
    private String phone;
    private String address;
    @CreationTimestamp
    private LocalDateTime createAt;
    @UpdateTimestamp
    private LocalDateTime updateAt;

    public void updateCustomer(CustomerRequestDTO data){
        if(this.fullName != null){this.fullName = data.fullName();}
        if(this.email != null){this.email = data.email();}
        if(this.cpf != null){this.cpf = data.cpf();}
        if(this.phone != null){this.phone = data.phone();}
        if(this.address != null){this.address = data.address();}
        this.updateAt = LocalDateTime.now();
    }
}
