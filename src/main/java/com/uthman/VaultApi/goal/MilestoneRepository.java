package com.uthman.VaultApi.goal;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
    List<Milestone> findByGoalId(Long goalId);
}