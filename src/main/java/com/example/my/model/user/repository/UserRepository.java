package com.example.my.model.user.repository;

import com.example.my.model.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findById(String id);
    // 아이디 있는지 없는지 체크
    Optional<UserEntity> findByIdAndDeleteDateIsNull(String userId);
    // 계정이 삭제되지 않은걸 찾는다 id로 찾음
    Optional<UserEntity> findByIdxAndDeleteDateIsNull(Long idx);
     
}
