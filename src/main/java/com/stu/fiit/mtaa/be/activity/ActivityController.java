package com.stu.fiit.mtaa.be.activity;

import com.stu.fiit.mtaa.be.appuser.AppUser;
import com.stu.fiit.mtaa.be.avatars.AvatarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;
    private final AvatarService avatarService;

    // Map Activity to UserActivitiesResponse
    private UserActivitiesResponse mapToUserActivitiesResponse(Activity activity) {
        UserActivitiesResponse response = new UserActivitiesResponse();
        response.setId(activity.getId());
        response.setDistance(activity.getDistance());
        response.setDuration(activity.getDuration());
        response.setAvgSpeed(activity.getAvgSpeed());
        response.setCalories(activity.getCalories());
        response.setMapRoute(activity.getMapRoute());
        response.setActivityType(activity.getActivityType());
        response.setDate(activity.getDate().toString());
        return response;
    }

    // Return activities for user order by date
    @GetMapping()
    public ResponseEntity<List<UserActivitiesResponse>> getUserActivities(Authentication auth){
        AppUser user = (AppUser) auth.getPrincipal();
        List<Activity> activities = activityService.getUserActivities(user);
        List<UserActivitiesResponse> responses = activities.stream()
                .map(this::mapToUserActivitiesResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(responses);
    }

    // Return activities for user friends
    @GetMapping("/friends")
    public ResponseEntity<List<FriendsActivitiesResponse>> getUserFriendsSharedActivities(Authentication auth){
        AppUser user = (AppUser) auth.getPrincipal();
        List<FriendsActivitiesResponse> responses = activityService.getUserFriendsSharedActivities(user).stream()
                .map(activity -> {
                    FriendsActivitiesResponse response = new FriendsActivitiesResponse();
                    response.setId(activity.getId());
                    response.setOwner_username(activity.getUser().getUsername());
                    response.setDistance(activity.getDistance());
                    response.setDuration(activity.getDuration());
                    response.setAvgSpeed(activity.getAvgSpeed());
                    response.setCalories(activity.getCalories());
                    response.setMapRoute(activity.getMapRoute());
                    response.setActivityType(activity.getActivityType());

//                    if (activity.getUser().getAvatar_id() == null) {
//                        response.setOwner_avatar(null);
//                    } else {
//                        response.setOwner_avatar(avatarService.getAvatarById(activity.getUser().getAvatar_id()));
//                    }


                    return response;
                }).collect(Collectors.toList());

        return ResponseEntity.ok().body(responses);
    }

    // Return specific activity
    @GetMapping(path = "/{activityId}")
    public ResponseEntity<?> getActivityById(@PathVariable("activityId") Long activityId){
        try {
            return ResponseEntity.ok(activityService.getActivityById(activityId));
        }catch(IllegalStateException e){
            return ResponseEntity.badRequest().build();
        }
    }

    //Add activity
    @PostMapping
    public ResponseEntity<UserActivitiesResponse> addNewActivity(@RequestBody AddActivityRequest request, Authentication auth){
        AppUser user = (AppUser) auth.getPrincipal();
        return ResponseEntity.ok(mapToUserActivitiesResponse(activityService.addNewActivity(request, user)));
    }

    //Share Activity
    @PutMapping(path = "/share/{activityId}")
    public ResponseEntity<?> updateActivity(@PathVariable("activityId") Long activityId){
        try {
            activityService.updateActivity(activityId);
            return ResponseEntity.ok("Activity shared");
        }catch(IllegalStateException e){
            return ResponseEntity.badRequest().build();
        }
    }

    //Delete activity
    @DeleteMapping(path = "/{activityId}")
    public ResponseEntity<HttpStatus> deleteActivity(@PathVariable("activityId") Long activityId){
        try{
            activityService.deleteActivity(activityId);
            return ResponseEntity.ok().build();
        }catch(IllegalStateException e){
            return ResponseEntity.badRequest().build();
        }

    }
}