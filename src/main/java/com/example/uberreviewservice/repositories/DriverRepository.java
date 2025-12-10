package com.example.uberreviewservice.repositories;

import com.example.uberreviewservice.models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver,Long> {
    Optional<Driver> findByIdAndLicenceNumber(Long id, String licenceNumber);

    Optional<Driver> findByLicenceNumber(String licenceNumber);

    @Query(nativeQuery = true, value = "SELECT * FROM Driver WHERE  id=:id AND licence_number=:license")
    Optional<Driver> rawFindByIdAndLicenceNumber(Long id, String license);

    @Query("SELECT d FROM Driver d WHERE d.id = :id AND d.licenceNumber = :licenceNumber")
    Driver hqlFindByIdAndLicense(Long id, String licenceNumber);


    List<Driver> findAllByIdIn(List<Long> driverIds);

}



