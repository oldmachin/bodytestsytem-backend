package org.example.controller;

import org.example.model.DTO.ScoreStandardRequest;
import org.example.model.score.ScoreStandard;
import org.example.service.ScoreStandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/score-standards")
public class ScoreStandardController {
    @Autowired
    private ScoreStandardService scoreStandardService;

    @PostMapping
    public ResponseEntity<Long> createScoreStandard(@RequestBody ScoreStandardRequest request) {
        ScoreStandard scoreStandard = scoreStandardService.createScoreStandard(
                request.testItemId(),
                request.gender(),
                request.grade(),
                request.scoreMappings()
        );
        return new ResponseEntity<>(scoreStandard.getId(), HttpStatus.CREATED);
    }

    @PutMapping("/{standardId}")
    public ResponseEntity<ScoreStandard> updateScoreStandard(
            @PathVariable Long standardId,
            @RequestBody ScoreStandardRequest request
    ) {
        ScoreStandard scoreStandard = scoreStandardService.updateScoreStandard(
                standardId,
                request.gender(),
                request.grade(),
                request.scoreMappings()
        );
        return new ResponseEntity<>(scoreStandard, HttpStatus.OK);
    }

    @DeleteMapping("/{scoreStandardId}")
    public ResponseEntity<Void> deleteScoreStandard(@PathVariable Long scoreStandardId) {
        scoreStandardService.deleteScoreStandard(scoreStandardId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{scoreStandardId}")
    public ResponseEntity<ScoreStandard> getScoreStandardById(@PathVariable Long scoreStandardId) {
        ScoreStandard scoreStandard = scoreStandardService.getScoreStandardById(scoreStandardId);
        return ResponseEntity.ok(scoreStandard);
    }
}
