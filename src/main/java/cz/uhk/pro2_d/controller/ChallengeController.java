package cz.uhk.pro2_d.controller;

import cz.uhk.pro2_d.model.Challenge;
import cz.uhk.pro2_d.model.User;
import cz.uhk.pro2_d.security.MyUserDetails;
import cz.uhk.pro2_d.service.CategoryService;
import cz.uhk.pro2_d.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/challenges")
public class ChallengeController {

    private final ChallengeService challengeService;
    private final CategoryService categoryService;

    @Autowired
    public ChallengeController(ChallengeService challengeService, CategoryService categoryService) {
        this.challengeService = challengeService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String list(Model model, @RequestParam(required = false) Long categoryId) {
        List<Challenge> challenges = challengeService.getAllChallenges();

        if (categoryId != null) {
            challenges = challenges.stream()
                    .filter(c -> c.getCategory() != null && c.getCategory().getId() == categoryId)
                    .collect(Collectors.toList());
        }

        model.addAttribute("challenges", challenges);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "challenges_list";
    }

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable long id) {
        model.addAttribute("challenge", challengeService.getChallenge(id));
        return "challenges_detail";
    }

    @GetMapping("/add")
    public String add(Model model) {
        return prepareForm(new Challenge(), model);
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable long id,
                       @AuthenticationPrincipal MyUserDetails springUser) {
        Challenge challenge = challengeService.getChallenge(id);
        if (!challenge.getCreator().getUsername().equals(springUser.getUsername()) &&
                !springUser.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/challenges/";
        }
        return prepareForm(challenge, model);
    }

    @PostMapping("/save")
    public String saveChallenge(@ModelAttribute Challenge challenge,
                                @AuthenticationPrincipal MyUserDetails springUser) {
        if (springUser == null) {
            return "redirect:/login";
        }
        challenge.setCreator(springUser.getUser());
        challengeService.saveChallenge(challenge);
        return "redirect:/challenges/";
    }

    @GetMapping("/{id}/delete")
    public String deleteConfirm(Model model, @PathVariable long id,
                                @AuthenticationPrincipal MyUserDetails springUser) {
        Challenge challenge = challengeService.getChallenge(id);
        if (!challenge.getCreator().getUsername().equals(springUser.getUsername()) &&
                !springUser.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/challenges/";
        }
        model.addAttribute("challenge", challenge);
        return "challenges_delete";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable long id,
                         @AuthenticationPrincipal MyUserDetails springUser) {
        Challenge challenge = challengeService.getChallenge(id);
        if (!challenge.getCreator().getUsername().equals(springUser.getUsername()) &&
                !springUser.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/challenges/";
        }
        challengeService.deleteChallenge(id);
        return "redirect:/challenges/";
    }


    private String prepareForm(Challenge challenge, Model model) {
        model.addAttribute("challenge", challenge);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "challenges_add";
    }
}
