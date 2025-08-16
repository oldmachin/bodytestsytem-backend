package org.example.repository;

import org.example.model.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findAddressByAddressId(Long addressId);
    Optional<Address> findAddressByDescription(String addressDescription);
}
