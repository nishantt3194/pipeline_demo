package com.futurealgos.admin.repos.primary;

import com.futurealgos.admin.models.primary.Product;
import com.futurealgos.data.repos.BaseRepository;
import com.futurealgos.data.repos.ReaderRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends BaseRepository<Product, UUID>, ReaderRepository<Product> {
}
