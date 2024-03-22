package com.cgi.lauri.movieRecommender.logic;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ScreenLogic {
    //Aitab leida kohad, kus ükski sõber ei peaks üksinda istuma
    private static final List<Integer> splittingFriendsRecommendedSeats = new ArrayList<>();

    /**
     * Genereerib suvalised juba võetud istekohad
     *
     * @param noOfSeats Mitmele kohale on vaja istekohad genereerida
     * @return Suvalised juba võetud istekohad
     */
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

    /**
     * Leiab soovitatavad istekohad filmile piletide ostmisel
     *
     * @param occupiedSeats Juba võetud kohad
     * @param noOfTickets   Mitu piletit ostetakse
     * @param rows          Ridade arv
     * @param seatsInOneRow Mitu istet on ühes reas
     * @return Soovitatavad istekohad
     */
    public List<Integer> generateRecommendSeats(List<Boolean> occupiedSeats, Integer noOfTickets, int rows, int seatsInOneRow) {
        if (noOfTickets > occupiedSeats.stream().filter(seat -> seat).count())
            return null;
        TreeMap<Integer, List<int[]>> scoremap = populateHeatMap(occupiedSeats, rows, seatsInOneRow);
        List<Integer> recommendedSeats = new ArrayList<>();

        //Vajalik kui me ei leia n järjestikust vaba kohta ega piisavalt tükeldatud vabasid kohti
        int backupCounter = noOfTickets;
        List<Integer> backupSeats = new ArrayList<>();

        //Hakkame järjest vaatama istekohti populaarsuse alusel
        for (Integer score : scoremap.keySet()) {
            List<int[]> allScoreIndexes = scoremap.get(score);
            for (int i = 0; i < allScoreIndexes.size(); i++) {
                //iste = [rida,koht]
                int[] seat = allScoreIndexes.get(i);
                int seatIndex = translateSeatIndexes(seat, seatsInOneRow);
                //Ainult kui vaadatav koht on vaba
                if (occupiedSeats.get(seatIndex)) {
                    //ning vaatame, kas leiame järjestikuseid kohti
                    //Ühtlasi lisame selle meetodi sees tükeldatud kohti
                    recommendedSeats = findSeatsInRow(getRow(occupiedSeats, seatIndex, seatsInOneRow), noOfTickets, seat, seatsInOneRow);
                    //Kui leiame järjestikused kohad
                    if (!recommendedSeats.isEmpty()) {
                        return recommendedSeats.stream().map(mapper -> seatsInOneRow * seat[0] + mapper)
                                .collect(Collectors.toList());
                    }
                    //Kui ei siis lisame iga kord kõige parema skooriga koha tagavarasse
                    if (backupCounter > 0) {
                        backupSeats.add(seatIndex);
                        backupCounter--;
                    }
                }
            }
        }
        List<Integer> returnableList = new ArrayList<>(splittingFriendsRecommendedSeats);
        splittingFriendsRecommendedSeats.clear();
        //Kui oleme leidnud piisavalt tükeldatud kohti tagastame need, kui ei siis tagastame tagavara.
        if (returnableList.size() == noOfTickets)
            return returnableList;
        return backupSeats;
    }

    /** Leiab kõigist juba võetud kohtadest parajasti vaadatava rea võetud kohtadega
     *
     * @param originalOccupiedSeats Kõik juba võetud kohad
     * @param seatIndex Vaadatava istekoha indeks
     * @param seatsInRow Mitu kohta on reas
     * @return Rida koos juba võetud kohtadega
     */
    private List<Boolean> getRow(List<Boolean> originalOccupiedSeats, int seatIndex, int seatsInRow) {
        int startIndex = seatIndex - (seatIndex % seatsInRow);
        int endIndex = startIndex + seatsInRow - 1;
        return originalOccupiedSeats.subList(startIndex, endIndex);
    }

    /** Leia antud reast kohad, mida saab kasutajale soovitada.
     *
     * @param occupiedSeatsInRow Missugused kohad on antud reast juba võetud.
     * @param noOfTickets Mitu piletit meil vaja on.
     * @param seat Millisest istekohast me alustame.
     * @param seatsInOneRow Mitu istekohta on reas
     * @return Juhul, kui leidub noOfTickets järjestikust kohta, tagasta need kohad, juhul kui ei tagasta tühi list.
     */
    private List<Integer> findSeatsInRow(List<Boolean> occupiedSeatsInRow, Integer noOfTickets, int[] seat, int seatsInOneRow) {
        List<Integer> seats = new ArrayList<>();
        if (seat[1] < 0 || seat[1] >= occupiedSeatsInRow.size()) {
            return seats;
        }
        int count = 0;

        //Alustame paremale liikudes vabade kohtade võtmist
        for (int i = seat[1]; i < occupiedSeatsInRow.size(); i++) {
            if (occupiedSeatsInRow.get(i)) {
                seats.add(i);
                count++;
                if (count == noOfTickets) {
                    return seats;
                }
                //Kui neid enam ei leidu
            } else break;
        }

        //Siis otsime edasi antud koha vasakult
        for (int i = seat[1] - 1; i >= 0; i--) {
            if (occupiedSeatsInRow.get(i)) {
                seats.add(0, i);
                count++;
                if (count == noOfTickets) {
                    return seats;
                }
            } else break;

        }

        //Juhul kui meil ei leidu noOfTickets järjestikust kohta, proovime kohti tükeldada nii, et saaksime vähemalt paarides sõbrad istuma panna.
        findSplitSeats(noOfTickets, seat, seatsInOneRow, seats);
        return new ArrayList<>();
    }

    /** Tükeldame kohti nii, et meil ei jääks ükski sõber üksi istuma
     *
     * @param noOfTickets Mitu piletit meil vaja on.
     * @param seat Vaadatav istekoht.
     * @param seatsInOneRow Mitu istekohta on vaadatavas reas
     * @param seats Meetodist findSeatsInRow juba leitud vabad koha(d) reas.
     */
    private static void findSplitSeats(Integer noOfTickets, int[] seat, int seatsInOneRow, List<Integer> seats) {
        //Saame lisada kohad ainult juhul kui meil on üle kahe lisatava koha ning lisamisel ei teki kunagi
        //olukorda, kus meil jääb lisada vaja veel ainult 1 koht
        if (seats.size() >= 2 && noOfTickets - splittingFriendsRecommendedSeats.size() >= 2 &&
                (noOfTickets - seats.size() - splittingFriendsRecommendedSeats.size() >= 2 ||
                        noOfTickets - seats.size() - splittingFriendsRecommendedSeats.size() == 0)) {
            while (noOfTickets - splittingFriendsRecommendedSeats.size() > 0) {
                int addableSeat = seatsInOneRow * seat[0] + seats.remove(0);
                if (!splittingFriendsRecommendedSeats.contains(addableSeat))
                    splittingFriendsRecommendedSeats.add(addableSeat);
                if (seats.size() <= 0) break;
            }
        }
    }

    /** Leia päris istekoha indeks
     * @param indexes     Koht kujul [rida, istekoht]
     * @param seatsInRows Kohti reas
     * @return Istekoha indeks, kus rida 2 istekoht 3 = 12, kui meil on 4 kohta reas
     */
    private Integer translateSeatIndexes(int[] indexes, int seatsInRows) {
        return indexes[0] * seatsInRows + indexes[1];
    }

    /**
     * Annab igale kohale skoori, kus keskmisemad kohad on rohkem väärtustatud
     *
     * @param occupiedSeats Juba võetud kohad
     * @param rows          Ridade arv
     * @param seatsInRow    Kohti reas
     * @return Sorteeritud paisktabel, kus võti: koha skoor, väärtus: List kohtadest skooriga skoor, kus iga koht on kujul [rida, istekoht]
     */
    private TreeMap<Integer, List<int[]>> populateHeatMap(List<Boolean> occupiedSeats, int rows, int seatsInRow) {
        TreeMap<Integer, List<int[]>> scoreWithIndex = new TreeMap<>(Collections.reverseOrder());
        int maxScore = occupiedSeats.size();
        //Leiame keskmised kohad, mille järgi hakkame kõige populaarsemaid skoore jagama
        int rowCenter = rows / 2;
        int colCenter = seatsInRow / 2;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seatsInRow; j++) {
                //Vaatame kui kaugel on koht tsentrist ning lahutame vastavalt maksimaalsest skoorist
                int score = maxScore - Math.abs(rowCenter - i) * 10 - Math.abs(colCenter - j) * 10;
                int[] indexes = new int[]{i, j};
                List<int[]> indexList = scoreWithIndex.getOrDefault(score, new ArrayList<>());
                indexList.add(indexes);
                scoreWithIndex.put(score, indexList);
            }
        }
        return scoreWithIndex;
    }
}
