package com.futurealgos.admin.repos.primary;

import com.futurealgos.admin.models.primary.User;
import com.futurealgos.data.repos.BaseRepository;
import com.futurealgos.data.repos.ReaderRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends BaseRepository<User, UUID>, ReaderRepository<User> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
