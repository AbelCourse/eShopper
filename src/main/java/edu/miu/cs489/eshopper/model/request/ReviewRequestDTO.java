package edu.miu.cs489.eshopper.model.request;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class ReviewRequestDTO {
    private String comment;
    private double rating;

    public ReviewRequestDTO() {
    }

    public ReviewRequestDTO(String comment, double rating) {
        this.comment = comment;
        this.rating = rating;
    }


}
