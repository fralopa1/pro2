package cz.uhk.pro2_d.service;

import cz.uhk.pro2_d.model.Challenge;
import cz.uhk.pro2_d.repository.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChallengeServiceImpl implements ChallengeService {

    private final ChallengeRepository challengeRepository;

    @Autowired
    public ChallengeServiceImpl(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    @Override
    public List<Challenge> getAllChallenges() {
        return challengeRepository.findAll();
    }

    @Override
    public void saveChallenge(Challenge challenge) {
        challengeRepository.save(challenge);
    }

    @Override
    public Challenge getChallenge(long id) {
        return challengeRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteChallenge(long id) {
        challengeRepository.deleteById(id);
    }
}
