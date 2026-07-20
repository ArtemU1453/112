package by.mchs.e112.telephony.repository;

import by.mchs.e112.telephony.domain.Call;
import by.mchs.e112.telephony.domain.CallStatus;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallRepository extends JpaRepository<Call, UUID> {

    List<Call> findByStatus(CallStatus status);

    List<Call> findByCallerPhoneOrderByStartedAtDesc(String callerPhone);

    List<Call> findByOperatorOrderByStartedAtDesc(String operator);
}
