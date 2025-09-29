package com.vedalvi.CashFlow.service;

import com.vedalvi.CashFlow.dto.request.TagRequest;
import com.vedalvi.CashFlow.dto.response.TagResponse;
import com.vedalvi.CashFlow.model.Tag;
import java.util.List;

public interface TagService {

    Tag createTag(TagRequest tag, String useName);

    List<TagResponse> getAllTags(String userName);

}
