package cz.uhk.pro2_d;

import cz.uhk.pro2_d.model.Entry;
import cz.uhk.pro2_d.repository.EntryRepository;
import cz.uhk.pro2_d.service.EntryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class EntryServiceTest {

    @Mock
    private EntryRepository entryRepository;

    @InjectMocks
    private EntryServiceImpl entryService;

    private Entry entry;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        entry = new Entry();
        entry.setId(1L);
        entry.setDate(LocalDate.now());
        entry.setNote("běh 5 km");
        entry.setSuccess(true);
    }

    @Test
    public void testGetAllEntries() {
        List<Entry> mockList = Arrays.asList(entry);
        when(entryRepository.findAll()).thenReturn(mockList);

        List<Entry> result = entryService.getAllEntries();

        assertEquals(1, result.size());
        assertEquals("běh 5 km", result.get(0).getNote());
    }

    @Test
    public void testGetEntryById() {
        when(entryRepository.findById(1L)).thenReturn(Optional.of(entry));

        Entry result = entryService.getEntry(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testEntryNotFound() {
        when(entryRepository.findById(99L)).thenReturn(Optional.empty());

        Entry result = entryService.getEntry(99L);

        assertNull(result);
    }

    @Test
    public void testSaveEntry() {
        entryService.saveEntry(entry);
        verify(entryRepository, times(1)).save(entry);
    }

    @Test
    public void testDeleteEntry() {
        entryService.deleteEntry(1L);
        verify(entryRepository, times(1)).deleteById(1L);
    }
}
