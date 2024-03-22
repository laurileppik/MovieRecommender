package com.cgi.lauri.movieRecommender.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "screens")
public class Screen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_of_seats")
    private int noOfSeats;

    @Transient
    private List<Boolean> occupiedSeats;

    @JsonIgnore
    @OneToMany(mappedBy="screen")
    private List<Showtime> showtimes;

    @Column(name = "number_of_rows")
    private int rows;
    @Column(name = "seats_in_row")
    private int seatsInRow;

    @Transient
    private List<Integer> recommendedSeats;

    public Screen(Long id, int noOfSeats, List<Boolean> occupiedSeats, List<Showtime> showtimes) {
        this.id = id;
        this.noOfSeats = noOfSeats;
        this.occupiedSeats = occupiedSeats;
        this.showtimes = showtimes;
    }

}
