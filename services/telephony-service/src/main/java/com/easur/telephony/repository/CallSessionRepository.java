package com.easur.telephony.repository;

import com.easur.telephony.model.CallSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallSessionRepository extends JpaRepository<CallSessionEntity, Long> {}
