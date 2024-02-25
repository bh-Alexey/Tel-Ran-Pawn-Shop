package us.telran.pawnshop.security;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import us.telran.pawnshop.repository.ManagerRepository;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class ManagerDetailsService implements UserDetailsService {

    private final ManagerRepository managerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

            return managerRepository.findManagerByEmail(email)
                    .map(manager -> new ManagerDetails(
                            manager.getManagerId(),
                            manager.getEmail(),
                            manager.getPassword(),
                            Collections.singletonList(new SimpleGrantedAuthority(manager.getManagerStatus().getRole()))
                    ))
                    .orElseThrow(() -> new UsernameNotFoundException("Manager not found with email: " + email));
    }
}

