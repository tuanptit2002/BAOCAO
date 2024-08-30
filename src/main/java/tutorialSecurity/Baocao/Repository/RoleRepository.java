package tutorialSecurity.Baocao.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tutorialSecurity.Baocao.Entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
