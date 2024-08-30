package tutorialSecurity.Baocao.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tutorialSecurity.Baocao.Entity.Role;
import tutorialSecurity.Baocao.Repository.RoleRepository;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public Role getRole(Long id){
        Role role = roleRepository.findById(id).get();
        return role;
    }
}
