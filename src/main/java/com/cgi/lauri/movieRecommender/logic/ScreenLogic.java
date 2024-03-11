package com.cgi.lauri.movieRecommender.logic;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
@Component
public class ScreenLogic {
    private final List<Integer> heatmap=new ArrayList<>();

    public List<Boolean> generateRandomSeats(int noOfSeats) {
        List<Boolean> seats = new ArrayList<>();
        for (int i = 0; i < noOfSeats; i++) {
            Random rn = new Random();
            int tester = rn.nextInt(10) + 1;
            if (tester % 2 == 0)
                seats.add(true);
            else
                seats.add(false);
        }
        return seats;
    }

    public List<Integer> generateRecommendSeats(List<Boolean> occupiedSeats, Integer noOfTickets, int rows, int seatsInRow) {
        populateHeatMap(occupiedSeats,rows,seatsInRow);
        return null;
    }

    private void populateHeatMap(List<Boolean> occupiedSeats,int rows,int seatsInRow){
        int[][] arr=new int[rows][seatsInRow];
        int maxScore= occupiedSeats.size();
        int rowCenter=rows/2;
        int colCenter=seatsInRow/2;


        System.out.println(Arrays.deepToString(arr));
    }
}
