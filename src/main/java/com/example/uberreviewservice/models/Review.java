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
@Inheritance(strategy = InheritanceType.JOINED)
public class Review extends BaseModel {

    @Column(nullable = false)
    protected String content;

    @Column(nullable = false)
    protected Double rating;

}
