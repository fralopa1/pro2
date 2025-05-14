package cz.uhk.pro2_d.service;

import cz.uhk.pro2_d.model.Entry;
import cz.uhk.pro2_d.model.User;
import cz.uhk.pro2_d.model.Challenge;
import cz.uhk.pro2_d.repository.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntryServiceImpl implements EntryService {

    private final EntryRepository entryRepository;

    @Autowired
    public EntryServiceImpl(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    @Override
    public List<Entry> getAllEntries() {
        return entryRepository.findAll();
    }

    @Override
    public void saveEntry(Entry entry) {
        entryRepository.save(entry);
    }

    @Override
    public Entry getEntry(long id) {
        return entryRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteEntry(long id) {
        entryRepository.deleteById(id);
    }

    @Override
    public List<Entry> getEntriesByUser(User user) {
        return entryRepository.findByUser(user);
    }

    @Override
    public List<Entry> getEntriesByChallenge(Challenge challenge) {
        return entryRepository.findByChallenge(challenge);
    }

    @Override
    public List<Entry> getEntriesByUserAndChallenge(User user, Challenge challenge) {
        return entryRepository.findByUserAndChallenge(user, challenge);
    }
}
