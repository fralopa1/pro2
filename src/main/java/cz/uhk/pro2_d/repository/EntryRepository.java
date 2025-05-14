package cz.uhk.pro2_d.repository;

import cz.uhk.pro2_d.model.Entry;
import cz.uhk.pro2_d.model.User;
import cz.uhk.pro2_d.model.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {

    List<Entry> findByUser(User user);

    List<Entry> findByChallenge(Challenge challenge);

    List<Entry> findByUserAndChallenge(User user, Challenge challenge);
}
