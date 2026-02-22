package com.binayak.ecomm_backend.response;

import com.binayak.ecomm_backend.payload.UserDto;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@JsonPropertyOrder({
        "content",
        "pageNumber",
        "pageSize",
        "totalElements",
        "totalPages",
        "islastPage"
})
@NoArgsConstructor
@Getter
@Setter
public class UserResponse {
    private List<UserDto> content;
    private Integer pageNumber;
    private Integer pageSize;
    private long totalElements;
    private long totalPages;
    private boolean isLastPage;
    private String sortBy;
    private String sortDir;
}
