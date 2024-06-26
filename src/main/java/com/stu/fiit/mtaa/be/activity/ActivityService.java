package com.stu.fiit.mtaa.be.activity;

import com.stu.fiit.mtaa.be.appuser.AppUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;


    //Get User activities order by date
    public List<Activity> getUserActivities(AppUser user) {
        return activityRepository.findActivitiesByUserOrderByDate(user);
    }

    public List<Activity> getUserFriendsSharedActivities(AppUser user) {
        return activityRepository.findAllSharedActivitiesByFriends(user.getId());
    }

    // Delete User activity
    public void deleteActivity(Long activityId) {
        if (!activityRepository.existsById(activityId)) {
            throw new IllegalStateException("Activity with id " + activityId + " does not exist");
        }
        activityRepository.deleteActivityById(activityId);
    }

    //Add new Activity
    public Activity addNewActivity(AddActivityRequest request, AppUser user) {

        var activity = Activity.builder()
                .distance(request.getDistance())
                .duration(request.getDuration())
                .calories(request.getCalories())
                .avgSpeed(request.getAvgSpeed())
                .mapRoute(request.getMapRoute())
                .activityType(request.getActivityType())
                .user(user)
                .shared(true)
                .date(request.getDate())
                .build();

        return activityRepository.save(activity);
    }

    //Search and get specific activity
    public Activity getActivityById(Long activityId) {
        return activityRepository.findActivityById(activityId)
                .orElseThrow(() -> new IllegalStateException("Activity with id " + activityId + " does not exist"));
    }

    //Share activity
    @Transactional
    public void updateActivity(Long activityId) {
        Activity activity = activityRepository.findById(activityId)
                        .orElseThrow(() -> new IllegalStateException("Activity not found"));
        activity.setShared(true);

        activityRepository.save(activity);
    }
}