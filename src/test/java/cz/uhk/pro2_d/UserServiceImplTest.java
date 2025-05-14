package cz.uhk.pro2_d;

import cz.uhk.pro2_d.model.User;
import cz.uhk.pro2_d.repository.UserRepository;
import cz.uhk.pro2_d.security.MyUserDetails;
import cz.uhk.pro2_d.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setUsername("test");
        user.setPassword("plain");

        when(passwordEncoder.encode(any())).thenReturn("encoded-password");
    }

    @Test
    public void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        List<User> result = userService.getAllUsers();
        assertEquals(1, result.size());
        assertEquals("Test User", result.get(0).getName());
    }

    @Test
    public void testSaveUser() {
        userService.saveUser(user);
        verify(passwordEncoder, times(1)).encode("plain");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User result = userService.getUser(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testGetUserByIdNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        User result = userService.getUser(99L);
        assertNull(result);
    }

    @Test
    public void testDeleteUser() {
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testLoadUserByUsernameFound() {
        when(userRepository.findByUsername("test")).thenReturn(user);
        UserDetails details = userService.loadUserByUsername("test");

        assertTrue(details instanceof MyUserDetails);
        assertEquals("test", details.getUsername());
    }

    @Test
    public void testLoadUserByUsernameNotFound() {
        when(userRepository.findByUsername("nonexistent")).thenReturn(null);
        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("nonexistent");
        });
    }
}
