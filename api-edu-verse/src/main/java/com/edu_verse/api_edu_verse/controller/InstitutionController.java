package com.edu_verse.api_edu_verse.controller;


import com.edu_verse.api_edu_verse.model.Institution;
import com.edu_verse.api_edu_verse.repository.InstituitionRepository;
import com.edu_verse.api_edu_verse.service.cookieService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/instituitions")
public class InstitutionController {

    @Autowired
    private InstituitionRepository instituitionRepository;
    @Autowired
    private cookieService cookieService;

    @PostMapping("/login")
    public ResponseEntity<String> login(HttpServletResponse response) {
        response.addCookie(cookieService.genCookie());
        return ResponseEntity.ok("Login feito! Cookie gerado.");
    }

    @GetMapping("/get-instituition")
    public List<Institution> getInstituition() { return instituitionRepository.findAll(); }

    @PostMapping("/new-instituition")
    public ResponseEntity<Institution> newInstituition(@RequestBody Institution instituition) {
        Institution updateInstituition = instituitionRepository.save(instituition);
        return ResponseEntity.status(HttpStatus.CREATED).body(updateInstituition);
    }

    @PutMapping("/new-intituition/")
    public ResponseEntity<Institution> updateinstituition(
            @RequestBody Institution instituition,
            @CookieValue(value= "auth_token", defaultValue = "") String Token
    ){
        if (!cookieService.isCookieValid(Token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (instituition.getId() == null || !instituitionRepository.existsById(instituition.getId())) {
            return ResponseEntity.notFound().build();
        }

        Institution updated = instituitionRepository.save(instituition);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete-instituition/{id}")
    public ResponseEntity<Void> deleteInstituition(
            @PathVariable long id,
            @CookieValue(value = "auth_token", defaultValue = "") String token) {
        if(!cookieService.isCookieValid(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if(!instituitionRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }

        instituitionRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}