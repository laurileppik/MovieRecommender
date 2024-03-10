package com.cgi.lauri.movieRecommender.model;

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

    @ManyToMany(mappedBy = "screens")
    private List<Movie> movies;

    @Transient
    private List<Boolean> occupiedSeats= new ArrayList<>();

    @OneToMany(mappedBy="screen")
    private List<Showtime> showtimes;

    public Screen(Long id, int noOfSeats) {
        this.id=id;
        this.noOfSeats=noOfSeats;
        this.occupiedSeats=generateSeats(noOfSeats);
        System.out.println(occupiedSeats);
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
