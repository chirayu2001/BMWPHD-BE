package net.bmw.bmwphd.dao;

import net.bmw.bmwphd.domain.ChangeRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChangeRequestDao extends JpaRepository<ChangeRequest, Integer> {
    List<ChangeRequest> findAllByStatus(String status);

    List<ChangeRequest> findAllByOwnerId(Integer id);
}
