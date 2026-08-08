package com.filmyflix.controller;

import com.filmyflix.model.Profile;
import com.filmyflix.model.User;
import com.filmyflix.repository.ProfileRepository;
import com.filmyflix.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProfileController(ProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Profile> myProfiles(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        return profileRepository.findByUserId(user.getId());
    }

    @PostMapping
    public Profile createProfile(@RequestBody Profile profile, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        profile.setUser(user);
        return profileRepository.save(profile);
    }

    @DeleteMapping("/{id}")
    public void deleteProfile(@PathVariable Long id) {
        profileRepository.deleteById(id);
    }
}
