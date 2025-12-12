package com.example.uberreviewservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
//@JsonIgnoreProperties({"hibernateLazyInitializer","handler","booking"})
public class Review extends BaseModel {


    @OneToOne(cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Booking booking;

    @Column(nullable = false)
    protected String content;

    @Column(nullable = false)
    protected Double rating;

}
