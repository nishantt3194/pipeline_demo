package com.futurealgos.micro.users.dto.response;

import com.futurealgos.micro.users.models.base.User;
import com.futurealgos.micro.users.utils.specs.dto.SearchResponse;

public class PartnerSearch extends SearchResponse {
    public PartnerSearch(User user) {
        super(user.getId().toHexString(),user.getUsername());
    }
}
