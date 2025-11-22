package com.example.uberreviewservice.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;

@MappedSuperclass
public abstract class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdAt;

    @Column(nullable = false)
    @UpdateTimestamp // This annotation automatically sets the value on update
    @Temporal(TemporalType.TIMESTAMP) // Specifies the type
    // Matches 'updated_at datetime(6) not null'
    protected Date updatedAt;
}

