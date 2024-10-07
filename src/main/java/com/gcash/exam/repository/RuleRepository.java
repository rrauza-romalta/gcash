package com.gcash.exam.repository;

import com.gcash.exam.entity.RuleEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepository extends JpaRepository<RuleEntity, Integer> {
    // Additional query methods if needed
    List<RuleEntity> findByEnabledTrue();
}
