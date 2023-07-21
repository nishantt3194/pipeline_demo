package com.futurealgos.admin.models.primary;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.futurealgos.admin.dto.response.users.UserInfo;
import com.futurealgos.admin.dto.response.users.UserMinimal;
import com.futurealgos.admin.dto.response.users.UserSearch;
import com.futurealgos.admin.utils.enums.Role;
import com.futurealgos.admin.utils.mappings.MachineMap;
import com.futurealgos.admin.utils.mappings.UserMap;
import com.futurealgos.data.models.BaseEntity;
import com.futurealgos.data.utils.mappings.BaseMap;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = UserMap.TABLE)
@AttributeOverrides({
        @AttributeOverride(name = BaseMap.ID, column = @Column(name = BaseMap.ID, columnDefinition = "BINARY(16) DEFAULT ((UUID_TO_BIN(UUID())))"))
})
public class User extends BaseEntity implements Serializable, UserDetails {

    @JsonProperty(UserMap.FIRST_NAME)
    @Column(name = UserMap.FIRST_NAME)
    private String firstName;

    @JsonProperty(UserMap.LAST_NAME)
    @Column(name = UserMap.LAST_NAME)
    private String lastName;

    @JsonProperty(UserMap.EMAIL)
    @Column(name = UserMap.EMAIL)
    private String email;

    @Builder.Default
    @JsonProperty(UserMap.ASSIGNED)
    @Column(name = UserMap.ASSIGNED, nullable = false)
    private boolean assigned = false;

    @JsonProperty(UserMap.GID)
    @Column(name = UserMap.GID)
    private String gid;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "operators", joinColumns = {
            @JoinColumn(name = UserMap.TABLE, referencedColumnName = BaseMap.ID, columnDefinition = "BINARY(16)")
    },
            inverseJoinColumns = {
                    @JoinColumn(name = MachineMap.TABLE, referencedColumnName = BaseMap.ID, columnDefinition = "BINARY(16)")
            }
    )
    private Set<Machine> machines;

    @JsonProperty(UserMap.ROLE)
    @Column(name = UserMap.ROLE)
    @Enumerated(value = EnumType.STRING)
    private Role role;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public UserInfo info() {
        return UserInfo.builder()
                .id(this.getId().toString())
                .fullName(getFullName())
                .email(this.email)
                .role(role != null ? role.getName():"")
                .status(status)
                .machine(machines!=null ? machines.stream().map(Machine::minimal).toList():null)
                .assigned(assigned)
                .build();
    }

    @Override
    public UserMinimal minimal() {
        return UserMinimal.builder()
                .id(this.id.toString())
                .email(email)
                .role(role !=null ? role.getName():"")
                .firstName(firstName)
                .lastName(lastName)
                .status(status)
                .build();

    }

    @Override
    public UserSearch search() {
        return UserSearch.builder()
                .identifier(id.toString())
                .label(getFullName())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !status;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !status;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !status;
    }

    @Override
    public boolean isEnabled() {
        return status;
    }
}
