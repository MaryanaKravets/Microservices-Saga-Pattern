package com.example.restoranservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "rates")
@Data
@NoArgsConstructor
public class Rate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "dish_id")
    private long dishId;
    @Column(name = "common_voted_qty")
    private int commonVotedQty;
    @Column(name = "like_voted_qty")
    private int likeVotedQty;

    public Rate(long dishId, int commonVotedQty, int likeVotedQty) {
        this.dishId = dishId;
        this.commonVotedQty = commonVotedQty;
        this.likeVotedQty = likeVotedQty;
    }
}
