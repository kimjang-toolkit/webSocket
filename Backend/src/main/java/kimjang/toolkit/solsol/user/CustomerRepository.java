package kimjang.toolkit.solsol.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<User, Long> {
    List<User> findByIdIn(List<Long> ids);

}
