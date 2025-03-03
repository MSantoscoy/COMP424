package com.example.demo.user;

import jakarta.persistence.*;

@Entity
@Table
public class User {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "suer sequence",
            allocationSize= 1
    )

    @GeneratedValue
            (
                    strategy = GenerationType.SEQUENCE,
                    generator= "user_sequence"
            )

    private Long ID;
    private String userName;
    private String userPass;

}
