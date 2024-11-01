// package com.example.demo.Service;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import com.example.demo.model.UserProfile;
// import com.example.demo.repository.UserProfileRepository;
// @Service
// public class UserProfileService {
//     @Autowired
//     private UserProfileRepository userProfileRepository;
//     public UserProfile updateUserProfile(String username, UserProfile updatedProfile) {
//         UserProfile existingProfile = userProfileRepository.findByUsername(username);
//         if (existingProfile != null) {
//             existingProfile.setEmail(updatedProfile.getEmail());
//             existingProfile.setPhonenumber(updatedProfile.getPhonenumber());
//             existingProfile.setGender(updatedProfile.getGender());
//             existingProfile.setBirthdate(updatedProfile.getBirthdate());
//             return userProfileRepository.save(existingProfile);
//         } else {
//             throw new RuntimeException("User not found");
//         }
//     }
// }
package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.UserProfile;
import com.example.demo.repository.UserProfileRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    public UserProfile updateUserProfile(UserProfile updatedProfile) {
        UserProfile existingProfile = userProfileRepository.findByUsername(updatedProfile.getUsername());
        if (existingProfile != null) {
            existingProfile.setUsername(updatedProfile.getUsername());
            existingProfile.setEmail(updatedProfile.getEmail());
            existingProfile.setPhonenumber(updatedProfile.getPhonenumber());
            existingProfile.setGender(updatedProfile.getGender());
            existingProfile.setBirthdate(updatedProfile.getBirthdate());

            // Kiểm tra nếu có yêu cầu đổi mật khẩu
            if (updatedProfile.getNewPassword() != null && !updatedProfile.getNewPassword().isEmpty()) {
                // Logic kiểm tra mật khẩu hiện tại trước khi thay đổi
                if (updatedProfile.getCurrentPassword().equals(existingProfile.getPassword())) {
                    existingProfile.setPassword(updatedProfile.getNewPassword());
                } else {
                    throw new RuntimeException("Mật khẩu hiện tại không đúng");
                }
            }

            return userProfileRepository.save(existingProfile);
        } else {
            throw new RuntimeException("User not found");
        }
    }
    // Phương thức để lấy số điện thoại theo username

    public String getPhoneNumberByUsername(String username) {
        return userProfileRepository.findPhonenumberByUsername(username);
    }

    // ngày sinh null


}
