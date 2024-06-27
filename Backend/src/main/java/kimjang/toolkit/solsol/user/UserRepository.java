package kimjang.toolkit.solsol.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByIdIn(List<Long> ids);

    @Query(value = "select u from User u join FETCH u.authorities a WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);
}
