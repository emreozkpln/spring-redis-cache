package dev.buddly.redis_cache_demo.repository;

import dev.buddly.redis_cache_demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
