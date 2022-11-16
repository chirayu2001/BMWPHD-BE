package net.bmw.bmwphd.dao;

import net.bmw.bmwphd.domain.Horse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface HorseDao extends JpaRepository<Horse, String>, JpaSpecificationExecutor<Horse> {
}
