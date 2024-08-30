package tutorialSecurity.Baocao.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tutorialSecurity.Baocao.Config.JwtTokenProvider;
import tutorialSecurity.Baocao.DTO.AuthenticationResponse;
import tutorialSecurity.Baocao.DTO.RoleDTO;
import tutorialSecurity.Baocao.DTO.UserDTO;
import tutorialSecurity.Baocao.Entity.Role;
import tutorialSecurity.Baocao.Entity.User;
import tutorialSecurity.Baocao.Service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public AuthenticationResponse loginUser(@RequestBody UserDTO userDTO){
        UsernamePasswordAuthenticationToken token =new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        User user = (User) authentication.getPrincipal();
        String jwt = jwtTokenProvider.generateToken(user);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAccess_token(jwt);
        authenticationResponse.setEmail(user.getUsername());
        authenticationResponse.setFullName(user.getFullName());
        return authenticationResponse;

    }

    @PostMapping("/add")
    public String addUser(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setFullName(userDTO.getFullName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setPhone(userDTO.getPhone());
        user.setUsername(userDTO.getUsername());
        List<Role> roles = new ArrayList<>();
        for (RoleDTO roleDTO : userDTO.getRoleDTOS()) {
            Role role = new Role();
            role.setId(roleDTO.getId());
            roles.add(role);
        }
        user.setRoles(roles);
        return userService.addUser(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PutMapping("/update")
    public User updateUser(@RequestBody UserDTO userDTO) {

        User user = new User();
        user.setId(userDTO.getId());
        user.setFullName(userDTO.getFullName());
        user.setPhone(userDTO.getPhone());
        return userService.updateUser(user);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @GetMapping("/get/users")
    public Page<User> getUsers(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userService.getUsers(pageable);
    }
}
