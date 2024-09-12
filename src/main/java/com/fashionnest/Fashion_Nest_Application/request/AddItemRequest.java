package com.fashionnest.Fashion_Nest_Application.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddItemRequest {

    private long productId;
private String size;
private int quantity;
private Integer price;
}
