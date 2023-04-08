package nus.task2.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import nus.task2.Models.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByName(String username);

}
