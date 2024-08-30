package tutorialSecurity.Baocao.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tutorialSecurity.Baocao.Entity.User;
import tutorialSecurity.Baocao.Repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    public String addUser(User user){
        User userOld = userRepository.findByUsername(user.getUsername());
        if(userOld != null)return "Tai khoan da ton tai";
        User userData = userRepository.save(user);
        if(userData != null)return "Add User Access";
        else return "Add User Error";
    }

    public User updateUser(User user){
        User userData = userRepository.findById(user.getId()).get();
        userData.setPhone(user.getPhone());
        userData.setFullName(user.getFullName());
        User userNew  = userRepository.save(userData);
        return userNew;
    }
    public User getUser(Long id){
        User user = userRepository.findById(id).get();
        return user;
    }

    public String deleteUser(Long id){
        User user = userRepository.findById(id).get();
        userRepository.delete(user);
        return "Delete access user" + user.getUsername();
    }

    public Page<User> getUsers(Pageable pageable){
        Page<User> users = userRepository.getUsers(pageable);
        return users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = (UserDetails) userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }
}
