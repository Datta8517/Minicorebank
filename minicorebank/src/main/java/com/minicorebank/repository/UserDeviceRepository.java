package com.minicorebank.repository;

import com.minicorebank.model.User;
import com.minicorebank.model.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {
    List<UserDevice> findByUser(User user);
}
