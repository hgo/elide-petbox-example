package domain;

import com.yahoo.elide.annotation.Exclude;
import com.yahoo.elide.annotation.Include;
import com.yahoo.elide.annotation.ReadPermission;
import com.yahoo.elide.annotation.SharePermission;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Include(rootLevel = true)
@SharePermission(expression = "all users")
public class User {

    @Id
    @GeneratedValue
    public Long id;


    public String password;
    @Column(nullable = false)
    public String email;
    @Column(nullable = false)
    public String name;

    @Exclude
    @ReadPermission(expression = "user is accessing self")
    public String getPassword() {
        return password;
    }


}
