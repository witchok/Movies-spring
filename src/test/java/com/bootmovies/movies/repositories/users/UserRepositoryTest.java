package com.bootmovies.movies.repositories.users;


import com.bootmovies.movies.config.EmbeddedMongoConfig;
import com.bootmovies.movies.domain.enums.UserRole;
import com.bootmovies.movies.domain.user.User;
import com.bootmovies.movies.domain.user.UserDTO;
import com.bootmovies.movies.exceptions.UserAlreadyExistsException;
import com.bootmovies.movies.exceptions.UserWithSuchEmailAlreadyExistsException;
import com.bootmovies.movies.repositories.user.UserRepository;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.bootmovies.movies.UsersCreator.*;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.*;


@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =   {EmbeddedMongoConfig.class})
public class UserRepositoryTest {
    private static MongoDatabase mongoDatabase;
    private static MongoCollection userCollection;

    @Autowired
    private UserRepository userRepository;

    @BeforeClass
    public static void prepare(){
        MongoClient mongoClient = new MongoClient(new ServerAddress(EmbeddedMongoConfig.IP, EmbeddedMongoConfig.PORT));
        mongoDatabase = mongoClient.getDatabase("test");
        userCollection = mongoDatabase.getCollection("movieUsers");
    }

    @After
    public void clean(){
        userCollection.drop();
    }

    @Test
    public void testSaveUser(){
        User userToSave = createSimpleUser("User1","em@gmail.com");
        User savedUser = userRepository.save(userToSave);
        assertThat(userRepository.findAll().size()).isEqualTo(1);
        assertThat(savedUser.getId()).isNotNull();
        assertThat(userRepository.findAll().get(0).equals(savedUser));
        assertThat(savedUser.getUsername()).isEqualTo(userToSave.getUsername());
    }

    @Test
    public void testSaveUserDTO(){
        UserDTO userDTOToSave = createSimpleUserDTO("User1","em@gmail.com");
        User savedUser = userRepository.save(userDTOToSave, UserRole.USER);
        assertThat(userRepository.findAll().size()).isEqualTo(1);
        assertThat(savedUser.getId()).isNotNull();
        assertThat(userRepository.findAll().get(0).equals(savedUser));
        assertThat(savedUser.getUsername()).isEqualTo(userDTOToSave.getUsername());
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void testSaveUserWithSameUsername(){
        UserDTO userDTOToSave = createSimpleUserDTO("User1","em@gmail.com");
        User savedUser = userRepository.save(userDTOToSave, UserRole.USER);

        UserDTO anotherUser = createSimpleUserDTO("User1","em2@gmail.com");
        userRepository.save(anotherUser, UserRole.USER);
    }

    @Test(expected = UserWithSuchEmailAlreadyExistsException.class)
    public void testSaveUserWithSameEmail(){
        UserDTO userDTOToSave = createSimpleUserDTO("User1","em@gmail.com");
        User savedUser = userRepository.save(userDTOToSave, UserRole.USER);

        UserDTO anotherUser = createSimpleUserDTO("User2","em@gmail.com");
        userRepository.save(anotherUser, UserRole.USER);
    }

    @Test
    public void findUserById(){
        User user1 = createSimpleUser("51fcd","user1","em1@gmail.com");
        User user2 = createSimpleUser("52fcd","user2","em2@gmail.com");
        User user3 = createSimpleUser("53fcd","user3","em3@gmail.com");

        userRepository.save(user1);
        User user2Saved = userRepository.save(user2);
        userRepository.save(user3);

        assertEquals(user2, user2Saved);

        User user2ById = userRepository.getUserById(user2.getId());
        assertEquals(user2ById,user2);
        assertNull(userRepository.getUserById("55fcdcd"));
    }

    @Test
    public void findUserByUserName(){
        User user1 = createSimpleUser("user1","em1@gmail.com");
        User user2 = createSimpleUser("user2","em2@gmail.com");
        User user3 = createSimpleUser("user3","em3@gmail.com");

        userRepository.save(user1);
        User user2Saved = userRepository.save(user2);
        userRepository.save(user3);

        assertEquals(user2.getEmail(), user2Saved.getEmail());
        assertEquals(user2.getUsername(), user2Saved.getUsername());

        User user2ByUsername = userRepository.getUserByUsername(user2.getUsername());
        assertEquals(user2ByUsername,user2Saved);
        assertNull(userRepository.getUserByUsername("not existed"));

    }

    @Test
    public void findUserEmail(){
        User user1 = createSimpleUser("user1","em1@gmail.com");
        User user2 = createSimpleUser("user2","em2@gmail.com");
        User user3 = createSimpleUser("user3","em3@gmail.com");

        userRepository.save(user1);
        User user2Saved = userRepository.save(user2);
        userRepository.save(user3);

        assertEquals(user2.getEmail(), user2Saved.getEmail());
        assertEquals(user2.getUsername(), user2Saved.getUsername());

        User user2ByEmail = userRepository.getUserByEmail(user2.getEmail());
        assertEquals(user2ByEmail,user2Saved);

        assertNull(userRepository.getUserByEmail("not existed"));
    }
}
