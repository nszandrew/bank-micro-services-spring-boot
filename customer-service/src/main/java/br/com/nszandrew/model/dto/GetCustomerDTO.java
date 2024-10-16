package br.com.nszandrew.model.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class GetCustomerDTO implements Serializable {

    private final static long serialVersionUID = 1L;

    private Long id;
    private String fullName;
    private String email;
    private LocalDate dateOfBirth;
    private String cpf;
    private String phone;
    private String address;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public GetCustomerDTO() {
    }

    public GetCustomerDTO(Long id, String fullName, String email, LocalDate dateOfBirth, String cpf, String phone, String address, LocalDateTime createAt, LocalDateTime updateAt) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.cpf = cpf;
        this.phone = phone;
        this.address = address;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetCustomerDTO that = (GetCustomerDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(fullName, that.fullName) && Objects.equals(email, that.email) && Objects.equals(dateOfBirth, that.dateOfBirth) && Objects.equals(cpf, that.cpf) && Objects.equals(phone, that.phone) && Objects.equals(address, that.address) && Objects.equals(createAt, that.createAt) && Objects.equals(updateAt, that.updateAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, email, dateOfBirth, cpf, phone, address, createAt, updateAt);
    }
}
