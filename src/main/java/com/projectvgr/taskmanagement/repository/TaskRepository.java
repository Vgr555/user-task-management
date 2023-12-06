package com.projectvgr.taskmanagement.repository;

import com.projectvgr.taskmanagement.entities.TaskEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {

    @Query(value = "SELECT * FROM TASK WHERE USER_ID = ?1", nativeQuery = true)
    List<TaskEntity> findTasksByUserId(Integer userId);

    @Query(value = "SELECT * FROM TASK WHERE USER_ID=?1 AND ID=?2", nativeQuery = true)
    Optional<TaskEntity> findByIdAndUserId(Integer userId, Integer id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM TASK WHERE USER_ID=?1", nativeQuery = true)
    void deleteTasksByUserId(Integer id);
}
