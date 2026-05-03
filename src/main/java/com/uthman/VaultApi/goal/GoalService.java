package com.uthman.VaultApi.goal;

import com.uthman.VaultApi.user.User;
import com.uthman.VaultApi.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalService {

    private final GoalRepository goalRepository;
    private final MilestoneRepository milestoneRepository;
    private final UserRepository userRepository;

    public GoalService(GoalRepository goalRepository,
                       MilestoneRepository milestoneRepository,
                       UserRepository userRepository) {
        this.goalRepository = goalRepository;
        this.milestoneRepository = milestoneRepository;
        this.userRepository = userRepository;
    }

    // Get logged in user
    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Get all goals with pagination
    public Page<Goal> getMyGoals(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return goalRepository.findByUserId(getCurrentUser().getId(), pageable);
    }

    // Get single goal
    public Goal getGoal(Long id) {
        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Goal not found"));
        if (!goal.getUser().getId().equals(getCurrentUser().getId())) {
            throw new RuntimeException("Unauthorized");
        }
        return goal;
    }

    // Create goal
    public Goal createGoal(Goal goal) {
        goal.setUser(getCurrentUser());
        return goalRepository.save(goal);
    }

    // Update goal
    public Goal updateGoal(Long id, Goal updated) {
        Goal goal = getGoal(id);
        goal.setTitle(updated.getTitle());
        goal.setDescription(updated.getDescription());
        goal.setCategory(updated.getCategory());
        goal.setStatus(updated.getStatus());
        goal.setTargetDate(updated.getTargetDate());
        return goalRepository.save(goal);
    }

    // Delete goal
    public void deleteGoal(Long id) {
        goalRepository.delete(getGoal(id));
    }

    // Add milestone to goal
    public Milestone addMilestone(Long goalId, Milestone milestone) {
        milestone.setGoal(getGoal(goalId));
        return milestoneRepository.save(milestone);
    }

    // Update milestone
    public Milestone updateMilestone(Long milestoneId, Milestone updated) {
        Milestone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new RuntimeException("Milestone not found"));
        milestone.setTitle(updated.getTitle());
        milestone.setCompleted(updated.isCompleted());
        return milestoneRepository.save(milestone);
    }

    // Delete milestone
    public void deleteMilestone(Long milestoneId) {
        milestoneRepository.deleteById(milestoneId);
    }
}