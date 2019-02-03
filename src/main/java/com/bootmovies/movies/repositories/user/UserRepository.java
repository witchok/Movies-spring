package com.bootmovies.movies.repositories.user;

import com.bootmovies.movies.domain.user.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@Cacheable(value = "userCache")
public interface UserRepository extends MongoRepository<User, String>, CustomUserRepository {
    User save(User user);
    User getUserById(String id);
    User getUserByEmail(String email);
    User getUserByUsername(String username);

}
