package com.cgi.lauri.movieRecommender.logic;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ScreenLogic {

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
        if (noOfTickets > occupiedSeats.stream().filter(seat -> seat).count())
            return null;
        TreeMap<Integer, List<int[]>> scoremap = populateHeatMap(occupiedSeats,rows,seatsInRow);
        List<Integer> recommendedSeats=new ArrayList<>();

        for (Integer score : scoremap.keySet()) {
            List<int[]> allScoreIndexes = scoremap.get(score);
            for (int i = 0; i < allScoreIndexes.size(); i++) {
                int[] seat = allScoreIndexes.get(i);
                //If the seat is available
                int seatIndex = translateSeatIndexes(seat,seatsInRow);
                if (occupiedSeats.get(seatIndex)){
                    //System.out.println(seatIndex +" " + occupiedSeats.get(seatIndex) + " row: " + getRow(occupiedSeats,seatIndex,seatsInRow) + " seatIndex: " + seatIndex);
                    recommendedSeats= findSeatsInRow(getRow(occupiedSeats,seatIndex,seatsInRow),noOfTickets,seat[1]);
                    if (!recommendedSeats.isEmpty()) {
                        return recommendedSeats.stream().map(mapper -> seatsInRow * seat[0]+mapper)
                                .collect(Collectors.toList());
                    }
                }
            }
        }

        return null;
    }

    private List<Boolean> getRow(List<Boolean> originalOccupiedSeats, int seatIndex, int seatsInRow) {
        int startIndex = seatIndex-(seatIndex%seatsInRow);
        int endIndex = startIndex+seatsInRow-1;
        return originalOccupiedSeats.subList(startIndex, endIndex);
    }

    private List<Integer> findSeatsInRow(List<Boolean> occupiedSeatsInRow, Integer noOfTickets, int index) {
        System.out.println(noOfTickets + " " + index);
        List<Integer> seats = new ArrayList<>();
        if (index < 0 || index >= occupiedSeatsInRow.size()) {
            return seats;
        }
        int count=0;
        for (int i = index; i < occupiedSeatsInRow.size(); i++) {
            if (occupiedSeatsInRow.get(i)) {
                seats.add(i);
                count++;
                if (count == noOfTickets) {
                    return seats;
                }
            } else {
                count=0;
                seats.clear();
                break;
            }
        }

        for (int i = index; i >= 0; i--) {
            if (occupiedSeatsInRow.get(i)) {
                seats.add(0,i);
                count++;
                if (count == noOfTickets) {
                    return seats;
                }
            } else break;

        }
        return new ArrayList<>();
    }

    private Integer translateSeatIndexes(int[] indexes, int seatsInRows) {
        return indexes[0]*seatsInRows+indexes[1];
    }

    //[row,place]
    private int[] translateSeatIndex(int seatIndex,int seatsInRow){
        int row=seatIndex/seatsInRow;
        int place=seatIndex%seatsInRow;
        return new int[]{row,place};
    }

    private TreeMap<Integer, List<int[]>> populateHeatMap(List<Boolean> occupiedSeats,int rows,int seatsInRow){
        TreeMap<Integer, List<int[]>> scoreWithIndex = new TreeMap<>(Collections.reverseOrder());
        //int[][] arr=new int[rows][seatsInRow];
        int maxScore= occupiedSeats.size();
        int rowCenter=rows/2;
        int colCenter=seatsInRow/2;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seatsInRow; j++) {
                int score = maxScore-Math.abs(rowCenter-i)*10-Math.abs(colCenter-j)*10;
                //arr[i][j] = score;
                int[] indexes = new int[]{i, j};
                List<int[]> indexList = scoreWithIndex.getOrDefault(score, new ArrayList<>());
                indexList.add(indexes);
                scoreWithIndex.put(score, indexList);

            }
        }
        System.out.println(scoreWithIndex);
        return scoreWithIndex;
    }
}
