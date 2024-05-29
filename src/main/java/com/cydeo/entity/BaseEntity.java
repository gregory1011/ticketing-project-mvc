package com.cydeo.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(BaseEntityListener.class)
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false) // when we update something in DB it auto set null value for this variable for this reason we use notNull = false in order to keep same value as before
    protected LocalDateTime insertDateTime;

    @Column(nullable = false, updatable = false)
    protected Long insertUserId;

    @Column(nullable = false)
    protected LocalDateTime lastUpdateDateTime;

    @Column(nullable = false)
    protected Long lastUpdateUserId;

    // this variable will be responsible for deleted users in DB = false or not Deleted = true
    protected Boolean isDeleted= false;  // always false


    // to preserve changes in DB
    // implement a method to initialize static variables

//    @PrePersist // this annotation means to save data in DB
//    public void onPersist(){
//        this.insertDateTime= LocalDateTime.now();
//        this.lastUpdateDateTime= LocalDateTime.now();
//        this.insertUserId= 1L; // -> '1L' is temporary
//        this.lastUpdateUserId= 1L; // -> '1L' is temporary
//    }
//
//    @PreUpdate // this annotation will update data in DB
//    public void onPreUpdate(){
//        this.lastUpdateDateTime= LocalDateTime.now();
//        this.lastUpdateUserId= 1L;
//    }

}
