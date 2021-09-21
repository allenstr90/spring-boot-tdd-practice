package aem.example.tdd.ecasastorage.config;

import aem.example.tdd.ecasastorage.entity.User;
import aem.example.tdd.ecasastorage.repository.UserRepository;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EcasaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public EcasaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(this::buildUser)
                .orElseThrow(() -> new UsernameNotFoundException("The user do not exist"));
    }

    private UserDetails buildUser(User user) {
        if (!user.isActive())
            throw new UserNotActivatedException("The user " + user.getUsername() + " was no activated");
        List<GrantedAuthority> grantedAuthorities =
                user
                        .getAuthorities()
                        .stream().map(authority -> new SimpleGrantedAuthority(authority.getName()))
                        .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }

    public class UserNotActivatedException extends AuthenticationException {
        private static final long serialVersionUID = 1L;

        public UserNotActivatedException(String message) {
            super(message);
        }

        public UserNotActivatedException(String message, Throwable t) {
            super(message, t);
        }
    }
}
