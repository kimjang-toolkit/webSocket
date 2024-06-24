package kimjang.toolkit.solsol.user;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Table(name = "authorities")
public class Authority {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}