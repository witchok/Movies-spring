package com.bootmovies.movies.data.user;

import com.bootmovies.movies.config.EmbeddedMongoConfig;
import com.bootmovies.movies.domain.enums.UserRole;
import com.bootmovies.movies.domain.user.User;
import com.bootmovies.movies.domain.user.UserDTO;
import com.bootmovies.movies.exceptions.UserAlreadyExistsException;
import com.bootmovies.movies.exceptions.UserWithSuchEmailAlreadyExistsException;
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


@ActiveProfiles("integrating-test")
@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest()
@ContextConfiguration(classes =   {EmbeddedMongoConfig.class})
public class UserServiceTest {
    private static MongoDatabase mongoDatabase;
    private static MongoCollection userCollection;

    @Autowired
    private UserService userService;

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
    public void testSaveUserDTO(){
        UserDTO userDTOToSave = createSimpleUserDTO("User1","em@gmail.com");
        User savedUser = userService.registerNewAccount(userDTOToSave, UserRole.USER);
        assertThat(userRepository.findAll().size()).isEqualTo(1);
        assertThat(savedUser.getId()).isNotNull();
        assertThat(userRepository.findAll().get(0).equals(savedUser));
        assertThat(savedUser.getUsername()).isEqualTo(userDTOToSave.getUsername());
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void testSaveUserWithSameUsername(){
        UserDTO userDTOToSave = createSimpleUserDTO("User1","em@gmail.com");
        User savedUser = userService.registerNewAccount(userDTOToSave, UserRole.USER);

        UserDTO anotherUser = createSimpleUserDTO("User1","em2@gmail.com");
        userService.registerNewAccount(anotherUser, UserRole.USER);
    }

    @Test(expected = UserWithSuchEmailAlreadyExistsException.class)
    public void testSaveUserWithSameEmail(){
        UserDTO userDTOToSave = createSimpleUserDTO("User1","em@gmail.com");
        User savedUser = userService.registerNewAccount(userDTOToSave, UserRole.USER);

        UserDTO anotherUser = createSimpleUserDTO("User2","em@gmail.com");
        userService.registerNewAccount(anotherUser, UserRole.USER);
    }
}
