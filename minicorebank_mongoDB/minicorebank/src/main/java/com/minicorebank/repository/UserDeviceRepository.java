package com.minicorebank.repository;

import com.minicorebank.model.User;
import com.minicorebank.model.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserDeviceRepository extends MongoRepository<UserDevice, String> {
    List<UserDevice> findByUserId(String userId);
    Optional<UserDevice> findByDeviceId(String deviceId);
}
