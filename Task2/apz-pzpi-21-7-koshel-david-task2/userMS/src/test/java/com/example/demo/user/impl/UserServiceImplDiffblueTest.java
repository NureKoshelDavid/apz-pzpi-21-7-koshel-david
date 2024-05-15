package com.example.demo.user.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.user.UserCredential;
import com.example.demo.user.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UserServiceImplDiffblueTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * Method under test: {@link UserServiceImpl#findAll()}
     */
    @Test
    void testFindAll() {
        // Arrange
        ArrayList<UserCredential> userCredentialList = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(userCredentialList);

        // Act
        List<UserCredential> actualFindAllResult = userServiceImpl.findAll();

        // Assert
        verify(userRepository).findAll();
        assertTrue(actualFindAllResult.isEmpty());
        assertSame(userCredentialList, actualFindAllResult);
    }

    /**
     * Method under test: {@link UserServiceImpl#findAllBySaloonId(Long)}
     */
    @Test
    void testFindAllBySaloonId() {
        // Arrange
        ArrayList<UserCredential> userCredentialList = new ArrayList<>();
        when(userRepository.findAllBySaloonId(Mockito.<Long>any())).thenReturn(userCredentialList);

        // Act
        List<UserCredential> actualFindAllBySaloonIdResult = userServiceImpl.findAllBySaloonId(1L);

        // Assert
        verify(userRepository).findAllBySaloonId(eq(1L));
        assertTrue(actualFindAllBySaloonIdResult.isEmpty());
        assertSame(userCredentialList, actualFindAllBySaloonIdResult);
    }

    /**
     * Method under test: {@link UserServiceImpl#getUserById(Long)}
     */
    @Test
    void testGetUserById() {
        // Arrange
        UserCredential userCredential = new UserCredential();
        userCredential.setAllowance(1);
        userCredential.setApproved(true);
        userCredential.setEmail("jane.doe@example.org");
        userCredential.setId(1L);
        userCredential.setName("Name");
        userCredential.setPassword("iloveyou");
        userCredential.setPatronymic("Patronymic");
        userCredential.setPhone("6625550144");
        userCredential.setRoles("Roles");
        userCredential.setSalary(1);
        userCredential.setSaloonId(1L);
        userCredential.setSchedule("Schedule");
        userCredential.setSurname("Doe");
        Optional<UserCredential> ofResult = Optional.of(userCredential);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        UserCredential actualUserById = userServiceImpl.getUserById(1L);

        // Assert
        verify(userRepository).findById(eq(1L));
        assertSame(userCredential, actualUserById);
    }

    /**
     * Method under test: {@link UserServiceImpl#getUserByEmail(String)}
     */
    @Test
    void testGetUserByEmail() {
        // Arrange
        UserCredential userCredential = new UserCredential();
        userCredential.setAllowance(1);
        userCredential.setApproved(true);
        userCredential.setEmail("jane.doe@example.org");
        userCredential.setId(1L);
        userCredential.setName("Name");
        userCredential.setPassword("iloveyou");
        userCredential.setPatronymic("Patronymic");
        userCredential.setPhone("6625550144");
        userCredential.setRoles("Roles");
        userCredential.setSalary(1);
        userCredential.setSaloonId(1L);
        userCredential.setSchedule("Schedule");
        userCredential.setSurname("Doe");
        when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(userCredential);

        // Act
        UserCredential actualUserByEmail = userServiceImpl.getUserByEmail("jane.doe@example.org");

        // Assert
        verify(userRepository).findByEmail(eq("jane.doe@example.org"));
        assertSame(userCredential, actualUserByEmail);
    }

    /**
     * Method under test: {@link UserServiceImpl#createStaff(UserCredential)}
     */
    @Test
    void testCreateStaff() {
        // Arrange
        UserCredential userCredential = new UserCredential();
        userCredential.setAllowance(1);
        userCredential.setApproved(true);
        userCredential.setEmail("jane.doe@example.org");
        userCredential.setId(1L);
        userCredential.setName("Name");
        userCredential.setPassword("iloveyou");
        userCredential.setPatronymic("Patronymic");
        userCredential.setPhone("6625550144");
        userCredential.setRoles("Roles");
        userCredential.setSalary(1);
        userCredential.setSaloonId(1L);
        userCredential.setSchedule("Schedule");
        userCredential.setSurname("Doe");
        when(userRepository.save(Mockito.<UserCredential>any())).thenReturn(userCredential);

        UserCredential userCredential2 = new UserCredential();
        userCredential2.setAllowance(1);
        userCredential2.setApproved(true);
        userCredential2.setEmail("jane.doe@example.org");
        userCredential2.setId(1L);
        userCredential2.setName("Name");
        userCredential2.setPassword("iloveyou");
        userCredential2.setPatronymic("Patronymic");
        userCredential2.setPhone("6625550144");
        userCredential2.setRoles("Roles");
        userCredential2.setSalary(1);
        userCredential2.setSaloonId(1L);
        userCredential2.setSchedule("Schedule");
        userCredential2.setSurname("Doe");

        // Act
        userServiceImpl.createStaff(userCredential2);

        // Assert that nothing has changed
        verify(userRepository).save(isA(UserCredential.class));
        assertEquals("6625550144", userCredential2.getPhone());
        assertEquals("Doe", userCredential2.getSurname());
        assertEquals("Name", userCredential2.getName());
        assertEquals("Patronymic", userCredential2.getPatronymic());
        assertEquals("Roles", userCredential2.getRoles());
        assertEquals("Schedule", userCredential2.getSchedule());
        assertEquals("iloveyou", userCredential2.getPassword());
        assertEquals("jane.doe@example.org", userCredential2.getEmail());
        assertEquals(1, userCredential2.getAllowance());
        assertEquals(1, userCredential2.getSalary());
        assertEquals(1L, userCredential2.getId().longValue());
        assertEquals(1L, userCredential2.getSaloonId().longValue());
        assertTrue(userCredential2.isApproved());
        assertTrue(userServiceImpl.findAll().isEmpty());
    }

    /**
     * Method under test: {@link UserServiceImpl#deleteUserById(Long)}
     */
    @Test
    void testDeleteUserById() {
        // Arrange
        doNothing().when(userRepository).deleteById(Mockito.<Long>any());

        // Act
        boolean actualDeleteUserByIdResult = userServiceImpl.deleteUserById(1L);

        // Assert
        verify(userRepository).deleteById(eq(1L));
        assertTrue(actualDeleteUserByIdResult);
    }

    /**
     * Method under test:
     * {@link UserServiceImpl#updateUserById(Long, UserCredential)}
     */
    @Test
    void testUpdateUserById() {
        // Arrange
        UserCredential userCredential = new UserCredential();
        userCredential.setAllowance(1);
        userCredential.setApproved(true);
        userCredential.setEmail("jane.doe@example.org");
        userCredential.setId(1L);
        userCredential.setName("Name");
        userCredential.setPassword("iloveyou");
        userCredential.setPatronymic("Patronymic");
        userCredential.setPhone("6625550144");
        userCredential.setRoles("Roles");
        userCredential.setSalary(1);
        userCredential.setSaloonId(1L);
        userCredential.setSchedule("Schedule");
        userCredential.setSurname("Doe");
        Optional<UserCredential> ofResult = Optional.of(userCredential);

        UserCredential userCredential2 = new UserCredential();
        userCredential2.setAllowance(1);
        userCredential2.setApproved(true);
        userCredential2.setEmail("jane.doe@example.org");
        userCredential2.setId(1L);
        userCredential2.setName("Name");
        userCredential2.setPassword("iloveyou");
        userCredential2.setPatronymic("Patronymic");
        userCredential2.setPhone("6625550144");
        userCredential2.setRoles("Roles");
        userCredential2.setSalary(1);
        userCredential2.setSaloonId(1L);
        userCredential2.setSchedule("Schedule");
        userCredential2.setSurname("Doe");
        when(userRepository.save(Mockito.<UserCredential>any())).thenReturn(userCredential2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        UserCredential updatedUserCredential = new UserCredential();
        updatedUserCredential.setAllowance(1);
        updatedUserCredential.setApproved(true);
        updatedUserCredential.setEmail("jane.doe@example.org");
        updatedUserCredential.setId(1L);
        updatedUserCredential.setName("Name");
        updatedUserCredential.setPassword("iloveyou");
        updatedUserCredential.setPatronymic("Patronymic");
        updatedUserCredential.setPhone("6625550144");
        updatedUserCredential.setRoles("Roles");
        updatedUserCredential.setSalary(1);
        updatedUserCredential.setSaloonId(1L);
        updatedUserCredential.setSchedule("Schedule");
        updatedUserCredential.setSurname("Doe");

        // Act
        boolean actualUpdateUserByIdResult = userServiceImpl.updateUserById(1L, updatedUserCredential);

        // Assert
        verify(userRepository).findById(eq(1L));
        verify(userRepository).save(isA(UserCredential.class));
        assertTrue(actualUpdateUserByIdResult);
    }

    /**
     * Method under test:
     * {@link UserServiceImpl#updateUserById(Long, UserCredential)}
     */
    @Test
    void testUpdateUserById2() {
        // Arrange
        Optional<UserCredential> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        UserCredential updatedUserCredential = new UserCredential();
        updatedUserCredential.setAllowance(1);
        updatedUserCredential.setApproved(true);
        updatedUserCredential.setEmail("jane.doe@example.org");
        updatedUserCredential.setId(1L);
        updatedUserCredential.setName("Name");
        updatedUserCredential.setPassword("iloveyou");
        updatedUserCredential.setPatronymic("Patronymic");
        updatedUserCredential.setPhone("6625550144");
        updatedUserCredential.setRoles("Roles");
        updatedUserCredential.setSalary(1);
        updatedUserCredential.setSaloonId(1L);
        updatedUserCredential.setSchedule("Schedule");
        updatedUserCredential.setSurname("Doe");

        // Act
        boolean actualUpdateUserByIdResult = userServiceImpl.updateUserById(1L, updatedUserCredential);

        // Assert
        verify(userRepository).findById(eq(1L));
        assertFalse(actualUpdateUserByIdResult);
    }
}
