package com.vedalvi.CashFlow.service.impl;

import com.vedalvi.CashFlow.dto.request.TagRequest;
import com.vedalvi.CashFlow.dto.response.TagResponse;
import com.vedalvi.CashFlow.exception.DuplicateEntryException;
import com.vedalvi.CashFlow.exception.NotFoundException;
import com.vedalvi.CashFlow.exception.ResourceNotFoundException;
import com.vedalvi.CashFlow.model.Tag;
import com.vedalvi.CashFlow.model.User;
import com.vedalvi.CashFlow.repository.TagRepository;
import com.vedalvi.CashFlow.repository.UserRepository;
import com.vedalvi.CashFlow.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Tag createTag(TagRequest tag, String userName) {
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + userName));

        if (tagRepository.existsByNameAndUser(tag.getName(), user))
            throw new DuplicateEntryException("Tag already exists for this user");

        Tag tag1 = Tag.builder()
                .name(tag.getName())
                .createdAt(new Date().toInstant())
                .user(user)
                .build();

        return tagRepository.save(tag1);
    }

    @Override
    public List<TagResponse> getAllTags(String userName) {


        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + userName));



        List<Tag> tags = tagRepository.findByUserId(user.getId());
        return tags.stream()
                .map(tag -> TagResponse.builder()
                        .id(tag.getId())
                        .name(tag.getName())
                        .build())
                .toList();


    }
}
