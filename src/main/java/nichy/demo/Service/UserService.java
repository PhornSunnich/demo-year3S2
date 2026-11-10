package nichy.demo.Service;
import nichy.demo.Entity.User;
import nichy.demo.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
    public class UserService implements UserDetailsService {
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
            this.userRepository = userRepository;
            this.passwordEncoder = passwordEncoder;
        }
        public boolean registerUser(User user) {
            if (userRepository.existsByEmail(user.getEmail())) {
                return false;
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return true;
        }
        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("រកមិនឃើញគណនី: " + email));
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail())
                    .password(user.getPassword())
                    .roles(user.getRole().replace("ROLE_", ""))
                    .build();
        }

        // 1. Read All
        public List<User> getAllUsers() {
            return userRepository.findAll();
        }

        // 2. Read by ID
        public User getUserById(Long id) {
            return userRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("រកមិនឃើញ User ID: " + id));
        }

        // 3. Create User ថ្មី
        public boolean createUser(User user) {
            if (userRepository.existsByEmail(user.getEmail())) {
                return false;
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return true;
        }

    // 4. Update User
    public void updateUser(Long id, User updatedUser) {
        User existingUser = getUserById(id);
        existingUser.setFullName(updatedUser.getFullName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setRole(updatedUser.getRole());
        // បើមានការបញ្ចូល password ថ្មី ទើប hash រួច update
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        userRepository.save(existingUser);
    }

    // 5. Delete User
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


}