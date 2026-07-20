package com.easur.dispatch.repository;

import com.easur.dispatch.model.DispatchTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DispatchTaskRepository extends JpaRepository<DispatchTaskEntity, Long> {
}
