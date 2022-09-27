package com.adamsero.votingapp.controllers;

import com.adamsero.votingapp.database.dto.CandidateDTO;
import com.adamsero.votingapp.database.dto.VoterDTO;
import com.adamsero.votingapp.services.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
public class VoteController {
  private final VoteService voteService;

  @PostMapping("/create-candidate/{name}")
  public ResponseEntity<String> createCandidate(@PathVariable @NotBlank String name) {
    voteService.createCandidate(name);
    return ResponseEntity.ok("Candidate created");
  }

  @GetMapping("/get-candidates")
  public List<CandidateDTO> getAllCandidates() {
    return voteService.getAllCandidates();
  }

  @GetMapping("/get-candidate-names")
  public List<String> getAllCandidateNames() {
    return voteService.getAllCandidateNames();
  }

  @PostMapping("/create-voter/{name}")
  public ResponseEntity<String> createVoter(@PathVariable @NotBlank String name) {
    voteService.createVoter(name);
    return ResponseEntity.ok("Voter created");
  }

  @GetMapping("/get-voters")
  public List<VoterDTO> getAllVoters() {
    return voteService.getAllVoters();
  }

  @GetMapping("/get-available-voter-names")
  public List<String> getAvailableVoterNames() {
    return voteService.getAvailableVoterNames();
  }

  @PatchMapping("/vote")
  public ResponseEntity<String> vote(
      @RequestParam @NotBlank String voterName, @RequestParam @NotBlank String candidateName) {
    voteService.vote(voterName, candidateName);
    return ResponseEntity.ok("Vote cast");
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<String> handleEntityNotFoundException(ResponseStatusException exception) {
    return ResponseEntity.status(exception.getStatus()).body(exception.getReason());
  }
}
