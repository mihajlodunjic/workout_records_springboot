package org.fon.workout_records.security;


import org.fon.workout_records.entity.Trainer;
import org.fon.workout_records.repository.TrainerRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {
    private final TrainerRepository repo;
    public AppUserDetailsService(TrainerRepository repo) { this.repo = repo; }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Trainer t = repo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("not found"));
        return User.withUsername(t.getUsername()).password(t.getPasswordHash()).authorities("ROLE_TRAINER").build();
    }
}
