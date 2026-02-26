package com.binayak.ecomm_backend.payload;

import com.binayak.ecomm_backend.entity.Category;
import com.binayak.ecomm_backend.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProductDto {

    private Integer productId;

    private String productName;

    private String productDescription;

    private Double productPrice;

    private Integer productQuantity;

    private String imageName;

    private Category category;

    private User user;
}
