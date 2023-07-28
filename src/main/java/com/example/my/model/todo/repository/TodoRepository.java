package com.example.my.model.todo.repository;

import com.example.my.model.todo.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    // 삭제 안된것만 가져오겠다
    List<TodoEntity> findByDeleteDateIsNull();
    
    // select *
    // from todo
    // where user_idx = ?
    // and delete_date is null;
    List<TodoEntity> findByUserEntity_IdxAndDeleteDateIsNull(Long userIdx);
    List<TodoEntity> findByUserEntity_IdxAndDeleteDateIsNullOrderByIdxDesc(Long userIdx);

    Optional<TodoEntity> findByIdx(Long idx);
    Optional<TodoEntity> findByIdxAndDeleteDateIsNull(Long idx);

    List<TodoEntity> findByUserEntity_IdAndDeleteDateIsNull(String userId);

   

}
