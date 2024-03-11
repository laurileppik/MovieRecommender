package com.cgi.lauri.movieRecommender.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    /**@ManyToMany(mappedBy = "screens")
    private List<Movie> movies;**/

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

    public Screen(Long id, int noOfSeats) {
        this.id=id;
        this.noOfSeats=noOfSeats;
        this.occupiedSeats=generateSeats(this.noOfSeats);
        System.out.println(occupiedSeats);
    }

    public Screen(Long id, int noOfSeats, List<Boolean> occupiedSeats, List<Showtime> showtimes) {
        this.id = id;
        this.noOfSeats = noOfSeats;
        this.occupiedSeats = generateSeats(this.noOfSeats);
        this.showtimes = showtimes;
    }

    private List<Boolean> generateSeats(int noOfSeats) {
        List<Boolean> seats=new ArrayList<>();
        for (int i = 0; i < noOfSeats; i++) {
            Random rn = new Random();
            int tester = rn.nextInt(10) + 1;
            if (tester%2==0)
                seats.add(true);
            else seats.add(false);
        }
        return seats;
    }


}
