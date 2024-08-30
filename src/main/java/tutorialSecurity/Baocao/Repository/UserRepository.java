package tutorialSecurity.Baocao.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tutorialSecurity.Baocao.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {


    @Query(value = "select * from user", nativeQuery = true)
    Page<User> getUsers(Pageable pageable);

    User findByUsername(String username);
}
