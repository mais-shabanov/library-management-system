package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.dto.request.CreateDetailRequest;
import com.example.librarymanagementsystem.dto.request.UpdateDetailRequest;
import com.example.librarymanagementsystem.dto.response.DetailDto;
import com.example.librarymanagementsystem.service.DetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/title")
public class DetailController {

    private final DetailService detailService;

    public DetailController(DetailService detailService) {
        this.detailService = detailService;
    }

    @GetMapping
    public ResponseEntity<Set<DetailDto>> getAllDetails() {
        return ResponseEntity.ok(detailService.getAllDetails());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailDto> getDetailById(@PathVariable Long id) {
        return ResponseEntity.ok(detailService.getDetailById(id));
    }

    @PostMapping
    public ResponseEntity<Void> createDetail(@RequestBody CreateDetailRequest createDetailRequest) {
        detailService.createTitle(createDetailRequest);
        return new ResponseEntity<>(CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> updateDetail(@RequestBody UpdateDetailRequest updateDetailRequest) {
        detailService.updateDetail(updateDetailRequest);
        return new ResponseEntity<>(OK);
    }
}
