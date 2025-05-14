package cz.uhk.pro2_d.service;

import cz.uhk.pro2_d.model.Challenge;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChallengeService {
    List<Challenge> getAllChallenges();
    void saveChallenge(Challenge challenge);
    Challenge getChallenge(long id);
    void deleteChallenge(long id);
}
