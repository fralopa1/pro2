package cz.uhk.pro2_d.controller;

import cz.uhk.pro2_d.model.Entry;
import cz.uhk.pro2_d.security.MyUserDetails;
import cz.uhk.pro2_d.service.EntryService;
import cz.uhk.pro2_d.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/entries")
public class EntryController {

    private final EntryService entryService;
    private final ChallengeService challengeService;

    @Autowired
    public EntryController(EntryService entryService, ChallengeService challengeService) {
        this.entryService = entryService;
        this.challengeService = challengeService;
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("entries", entryService.getAllEntries());
        return "entries_list";
    }

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable long id) {
        Entry entry = entryService.getEntry(id);
        model.addAttribute("entry", entry);
        return "entries_detail";
    }

    @GetMapping("/add")
    public String add(@RequestParam("challengeId") long challengeId, Model model) {
        Entry entry = new Entry();
        entry.setDate(LocalDate.now());
        entry.setChallenge(challengeService.getChallenge(challengeId));
        return prepareForm(entry, model);
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable long id, Model model,
                       @AuthenticationPrincipal MyUserDetails springUser) {
        Entry entry = entryService.getEntry(id);
        if (!entry.getUser().getUsername().equals(springUser.getUsername()) &&
                !springUser.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/entries/";
        }
        return prepareForm(entry, model);
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Entry entry,
                       @RequestParam("challengeId") long challengeId,
                       @AuthenticationPrincipal MyUserDetails springUser) {
        entry.setUser(springUser.getUser());
        entry.setChallenge(challengeService.getChallenge(challengeId));
        entryService.saveEntry(entry);
        return "redirect:/entries/";
    }

    @GetMapping("/{id}/delete")
    public String deleteConfirm(@PathVariable long id, Model model,
                                @AuthenticationPrincipal MyUserDetails springUser) {
        Entry entry = entryService.getEntry(id);
        if (!entry.getUser().getUsername().equals(springUser.getUsername()) &&
                !springUser.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/entries/";
        }
        model.addAttribute("entry", entry);
        return "entries_delete";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable long id,
                         @AuthenticationPrincipal MyUserDetails springUser) {
        Entry entry = entryService.getEntry(id);
        if (!entry.getUser().getUsername().equals(springUser.getUsername()) &&
                !springUser.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/entries/";
        }
        entryService.deleteEntry(id);
        return "redirect:/entries/";
    }

    private String prepareForm(Entry entry, Model model) {
        model.addAttribute("entry", entry);
        model.addAttribute("challenge", entry.getChallenge());
        return "entries_add";
    }
}
