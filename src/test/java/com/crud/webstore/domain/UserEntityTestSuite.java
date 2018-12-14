package com.crud.webstore.domain;

import com.crud.webstore.repository.UserRepository;
import com.crud.webstore.service.impl.UtilsImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@Transactional
@Rollback(false)
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserEntityTestSuite {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testUserEntitySave() {
        //Given
        UserEntity user1 = new UserEntity("test1", "test1", "test1", "test1", "test1", "test");
        //When
        userRepository.save(user1);
        //Then
        long id = user1.getId();
        UserEntity userEntity1 = userRepository.findOne(id);
        Assert.assertEquals(id, userEntity1.getId());
        //CleanUp
        userRepository.delete(id);
    }

    @Test
    public void testUserEntitySaveWithAddresses() {
        //Given
        UserEntity user1 = new UserEntity("test1", "test1", "test1", "test1", "test1", "test");
        UserEntity user2 = new UserEntity("test2", "test2", "test2", "test2", "test2", "test");

        AddressEntity address1 = new AddressEntity("test1", "test1", "test1", "test1", "test1");
        AddressEntity address2 = new AddressEntity("test2", "test2", "test2", "test2", "test2");
        user1.getAddressEntityList().add(address1);
        user2.getAddressEntityList().add(address2);
        //When
        userRepository.save(user1);
        userRepository.save(user2);
        long id1 = user1.getId();
        long id2 = user2.getId();
        //Then
        Assert.assertNotEquals(0, id1);
        Assert.assertNotEquals(0, id2);
        //Clean up
        userRepository.delete(id1);
        userRepository.delete(id2);
    }
}
