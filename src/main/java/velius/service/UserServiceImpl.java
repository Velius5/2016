/*
 * Author: Patryk Dobrzyński
 * Author URL: http://patrykdobrzynski.eu
 * Author Email: kontakt@patrykdobrzynski.eu
 */
package velius.service;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import velius.model.Friend;
import velius.model.User;
import velius.repository.UserRepository;

@Service
@Validated
public class UserServiceImpl implements UserService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }
    
    

    @Transactional
    public User save(@NotNull @Valid final User user) {
        LOGGER.debug("Creating {}", user);
        return repository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> getList() {
        LOGGER.debug("Retrieving the list of all users");
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public User getUser(long id) {
        LOGGER.debug("Get user by ID");
        return repository.findById(id);
    }

    @Override
    public User getUserByEmailAndPassword(String email, String password) {
        LOGGER.debug("Login user with email " + email);
        return repository.findByEmailAndPassword(email, password);
    }

    @Override
    public List<User> getUserFriends(long id) {
        List<Friend> friends = repository.findById(id).getFriends();
        List<User> users = new ArrayList<>();
        for(Friend friend : friends) {
            User user = repository.findById(friend.getFriendId());
            users.add(user);
        }
        return users;
    }

    
    @Override
    public Boolean exists(long id) {
        return repository.exists(id);
    }

    @Override
    public User getUserByEmail(String email) {
        User user = repository.findByEmail(email);
        return user;
    }

}
