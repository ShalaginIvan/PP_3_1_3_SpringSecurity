package springcource.PP_3_1_3_SpringSecurity.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Setter
@Getter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "FirstName should not be blank")
    @NotEmpty(message = "FirstName should not be empty")
    @Size(min = 2, max = 30, message = "FirstName should be between 2 and 30 characters")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "LastName should not be blank")
    @NotEmpty(message = "LastName should not be empty")
    @Size(min = 2, max = 30, message = "LastName should be between 2 and 30 characters")
    private String lastName;

    @Column(name = "email")
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    @Column(name = "password")
    @Size(min=4, message = "Password must contain at least 4 characters")
    private String password;

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    @Transient
    private String passwordConfirm;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return firstName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return  true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return  true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return  true;
    }

    @Override
    public boolean isEnabled() {
        return  true;
    }
}

