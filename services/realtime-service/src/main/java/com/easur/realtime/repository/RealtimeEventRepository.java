package com.easur.realtime.repository;

import com.easur.realtime.model.RealtimeEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RealtimeEventRepository extends JpaRepository<RealtimeEventEntity, Long> {
}
