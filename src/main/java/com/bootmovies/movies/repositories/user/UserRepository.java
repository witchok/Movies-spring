package com.bootmovies.movies.repositories.user;

import com.bootmovies.movies.domain.user.User;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
//@Cacheable(value = "userCache")
public interface UserRepository extends MongoRepository<User, String> {
//    @CachePut( key="#result.id")
    User save(User user);
    User findUserById(String id);
    User findUserByEmail(String email);
    User findUserByUsername(String username);

}
