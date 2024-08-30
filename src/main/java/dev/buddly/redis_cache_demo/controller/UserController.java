package dev.buddly.redis_cache_demo.controller;

import dev.buddly.redis_cache_demo.dto.CreateUserDto;
import dev.buddly.redis_cache_demo.model.User;
import dev.buddly.redis_cache_demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto dto){
        return ResponseEntity.ok(userService.createUser(dto));
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<User> updateUser(@RequestBody CreateUserDto dto,@PathVariable Integer id) {
        return new ResponseEntity<>(userService.updateUser(dto,id), HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteUser(@RequestParam Integer id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @GetMapping()
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/id")
    public User getUserById(@RequestParam Integer id) {
        return userService.getUserById(id);
    }
}
