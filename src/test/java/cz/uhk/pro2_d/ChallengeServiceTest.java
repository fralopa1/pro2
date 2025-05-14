package cz.uhk.pro2_d;

import cz.uhk.pro2_d.model.Challenge;
import cz.uhk.pro2_d.repository.ChallengeRepository;
import cz.uhk.pro2_d.service.ChallengeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ChallengeServiceTest {

    @Mock
    private ChallengeRepository challengeRepository;

    @InjectMocks
    private ChallengeServiceImpl challengeService;

    private Challenge challenge;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        challenge = new Challenge();
        challenge.setId(1L);
        challenge.setName("30 dní bez cukru");
    }

    @Test
    public void testGetAllChallenges() {
        List<Challenge> mockList = Arrays.asList(challenge);
        when(challengeRepository.findAll()).thenReturn(mockList);

        List<Challenge> result = challengeService.getAllChallenges();

        assertEquals(1, result.size());
        assertEquals("30 dní bez cukru", result.get(0).getName());
    }

    @Test
    public void testGetChallengeById() {
        when(challengeRepository.findById(1L)).thenReturn(Optional.of(challenge));

        Challenge result = challengeService.getChallenge(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testChallengeNotFound() {
        when(challengeRepository.findById(99L)).thenReturn(Optional.empty());

        Challenge result = challengeService.getChallenge(99L);

        assertNull(result);
    }

    @Test
    public void testSaveChallenge() {
        challengeService.saveChallenge(challenge);
        verify(challengeRepository, times(1)).save(challenge);
    }

    @Test
    public void testDeleteChallenge() {
        challengeService.deleteChallenge(1L);
        verify(challengeRepository, times(1)).deleteById(1L);
    }
}
