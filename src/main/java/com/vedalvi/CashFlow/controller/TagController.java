package com.vedalvi.CashFlow.controller;

import com.vedalvi.CashFlow.dto.request.TagRequest;
import com.vedalvi.CashFlow.dto.response.TagResponse;
import com.vedalvi.CashFlow.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import com.vedalvi.CashFlow.model.Tag;
import com.vedalvi.CashFlow.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestBody TagRequest tag,
                                         @AuthenticationPrincipal CustomUserDetails currentUser) {
        Tag newTag1 = tagService.createTag(tag, currentUser.getUsername());
        return new ResponseEntity<>(newTag1, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TagResponse>> getAllTags(@AuthenticationPrincipal CustomUserDetails currentUser) {
        List<TagResponse> tags = tagService.getAllTags(currentUser.getUsername());
        return ResponseEntity.ok(tags);
    }

}
