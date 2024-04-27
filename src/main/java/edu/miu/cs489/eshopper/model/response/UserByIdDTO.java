package edu.miu.cs489.eshopper.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserByIdDTO {

    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private List<ReviewDTO> reviewList;
    private CartDTO cart;
}
