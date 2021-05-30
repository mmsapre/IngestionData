package com.test.ingestion.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisCacheRepository extends CrudRepository<String, String> {
}
