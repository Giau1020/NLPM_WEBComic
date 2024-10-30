// package com.example.demo.controller;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
// import com.example.demo.Service.UserProfileService;
// import com.example.demo.model.UserProfile;
// @RestController
// @RequestMapping("/api/profile")
// public class UserProfileController {
//     @Autowired
//     private UserProfileService userProfileService;
//     @PutMapping("/{username}")
//     public UserProfile updateProfile(@PathVariable String username, @RequestBody UserProfile updatedProfile) {
//         return userProfileService.updateUserProfile(username, updatedProfile);
//     }
// }
package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.UserProfileService;
import com.example.demo.model.UserProfile;
// import com.example.demo.repository.UserProfileRepository;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;
    // private UserProfileRepository userProfileRepository;

    @PutMapping("/update")
    public UserProfile updateProfile(@RequestBody UserProfile updatedProfile) {
        return userProfileService.updateUserProfile(updatedProfile);
    }

    @GetMapping("/info/phonenumber/{username}")
    public ResponseEntity<String> getPhonenumber(@PathVariable String username) {
        String phonenumber = userProfileService.getPhoneNumberByUsername(username);

        if (phonenumber != null) {
            return ResponseEntity.ok(phonenumber); // Trả về số điện thoại
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Người dùng không tìm thấy
        }
    }
}
