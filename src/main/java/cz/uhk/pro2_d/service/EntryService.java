package cz.uhk.pro2_d.service;

import cz.uhk.pro2_d.model.Entry;
import cz.uhk.pro2_d.model.User;
import cz.uhk.pro2_d.model.Challenge;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EntryService {
    List<Entry> getAllEntries();
    void saveEntry(Entry entry);
    Entry getEntry(long id);
    void deleteEntry(long id);

    List<Entry> getEntriesByUser(User user);
    List<Entry> getEntriesByChallenge(Challenge challenge);
    List<Entry> getEntriesByUserAndChallenge(User user, Challenge challenge);
}
