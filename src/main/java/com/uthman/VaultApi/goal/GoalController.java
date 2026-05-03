package com.uthman.VaultApi.goal;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/goals")
public class GoalController {

    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    // Get all my goals with pagination
    @GetMapping
    public ResponseEntity<Page<Goal>> getMyGoals(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(goalService.getMyGoals(page, size));
    }

    // Get single goal
    @GetMapping("/{id}")
    public ResponseEntity<Goal> getGoal(@PathVariable Long id) {
        return ResponseEntity.ok(goalService.getGoal(id));
    }

    // Create goal
    @PostMapping
    public ResponseEntity<Goal> createGoal(@Valid @RequestBody Goal goal) {
        return ResponseEntity.ok(goalService.createGoal(goal));
    }

    // Update goal
    @PutMapping("/{id}")
    public ResponseEntity<Goal> updateGoal(@PathVariable Long id,
                                           @Valid @RequestBody Goal goal) {
        return ResponseEntity.ok(goalService.updateGoal(id, goal));
    }

    // Delete goal
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGoal(@PathVariable Long id) {
        goalService.deleteGoal(id);
        return ResponseEntity.ok("Goal deleted successfully");
    }

    // Add milestone
    @PostMapping("/{id}/milestones")
    public ResponseEntity<Milestone> addMilestone(@PathVariable Long id,
                                                  @Valid @RequestBody Milestone milestone) {
        return ResponseEntity.ok(goalService.addMilestone(id, milestone));
    }

    // Update milestone
    @PutMapping("/{id}/milestones/{mId}")
    public ResponseEntity<Milestone> updateMilestone(@PathVariable Long id,
                                                     @PathVariable Long mId,
                                                     @Valid @RequestBody Milestone milestone) {
        return ResponseEntity.ok(goalService.updateMilestone(mId, milestone));
    }

    // Delete milestone
    @DeleteMapping("/{id}/milestones/{mId}")
    public ResponseEntity<String> deleteMilestone(@PathVariable Long id,
                                                  @PathVariable Long mId) {
        goalService.deleteMilestone(mId);
        return ResponseEntity.ok("Milestone deleted successfully");
    }
}