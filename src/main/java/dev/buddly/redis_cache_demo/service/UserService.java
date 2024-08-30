package dev.buddly.redis_cache_demo.service;

import dev.buddly.redis_cache_demo.dto.CreateUserDto;
import dev.buddly.redis_cache_demo.model.User;
import dev.buddly.redis_cache_demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserRepository userRepository;

    @CacheEvict(value = {"users","user_id"},allEntries = true)//bu method çalıştığında users ve user_id cachelerini temizle
    public User createUser(CreateUserDto dto) {
        var user = repository.save(dto.toEntity(dto));
        return user;
    }

    @Cacheable(value = "users",key = "#root.methodName",unless = "#result==null")//gelen data null ise cache yapma
    public List<User> getUsers() {
        return repository.findAll();
    }

    @Cacheable(cacheNames = "user_id",key = "#root.methodName + #id",unless = "#result==null")
    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElse(null);
    }

    @CachePut(cacheNames = "user_id",key = "'getUserById'+#id",unless = "#result==null")
    public User updateUser(CreateUserDto dto,Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User user1 = user.get();
            user1.setPassword(dto.getPassword());
            return userRepository.save(user1);
        } else {
            return null;
        }
    }

    @CacheEvict(value = {"users","user_id"},allEntries = true)
    public String deleteUser(Integer id) {
        userRepository.deleteById(id);
        return "User deleted";
    }
}
