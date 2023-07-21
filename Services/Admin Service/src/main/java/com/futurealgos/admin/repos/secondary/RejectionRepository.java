package com.futurealgos.admin.repos.secondary;


import com.futurealgos.admin.models.secondary.entry.Rejection;
import com.futurealgos.data.repos.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RejectionRepository extends BaseRepository<Rejection, Long> {
}
