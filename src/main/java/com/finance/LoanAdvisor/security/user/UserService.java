package com.finance.LoanAdvisor.security.user;

import com.finance.LoanAdvisor.entities.User;
import com.finance.LoanAdvisor.entities.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
//@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public User createUser(User user) {
        user.setCreateDttm(new Date());
        user.setCreatedBy(0);
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        User u = userRepository.save(user);

        return u;
    }


    public User fetchUser(String username){
        return userRepository.findByUsername(username);
    }


    public void fetchUserRole(String username) {
    }
}
