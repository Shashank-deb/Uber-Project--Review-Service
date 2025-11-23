package com.example.uberreviewservice.models;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "booking_review")
public class Review extends BaseModel {

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Double rating;

}
