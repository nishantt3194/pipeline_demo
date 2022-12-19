package com.futurealgos.micro.auth.models.base;

import com.futurealgos.micro.auth.models.main.AuthDirectory;
import com.futurealgos.micro.auth.models.root.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User extends BaseEntity implements Serializable, UserDetails {

    private static final long serialVersionUID = -4422508847654421636L;
    @Field("first_name")
    private String firstName;

    @Field("last_name")
    private String lastName;

    private String username;

    private String password;

    @Field("first_time_login")
    private boolean firstTimeLogin;

    @DocumentReference(collection = "auth_directory")
    private AuthDirectory directory;

    @Builder.Default
    @DocumentReference(lazy = true, collection = "roles")
    private Set<Role> role = new HashSet<>();

    @Builder.Default
    @DocumentReference(lazy = true, collection = "groups")
    private Set<Group> group = new HashSet<>();


    @DocumentReference(lazy = true, collection = "groups")
    @Field("primary_group")
    private Group primaryGroup;

    @Builder.Default
    private boolean status = true;

    @Field("partner")
    @DocumentReference(collection = "partners")
    private Partner partner;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.concat(group.stream()
                        .flatMap(g -> g.getRoles().stream()), this.getRole().stream())
                .map(Role::getTag)
                .distinct()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toUnmodifiableSet());
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return status;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return status;
    }

    @Override
    public boolean isEnabled() {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("firstTineLogin", firstTimeLogin)
                .append("username", username)
                .append("password", password)
                .append("directory", directory)
                .append("role", role)
                .append("group", group)
                .append("status", status)
                .toString();
    }
}
