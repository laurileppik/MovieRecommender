package com.cgi.lauri.movieRecommender.logic;

import com.cgi.lauri.movieRecommender.model.Customer;
import com.cgi.lauri.movieRecommender.model.Movie;
import com.cgi.lauri.movieRecommender.model.MovieRating;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Component
public class MovieRecommenderLogic {

    public List<Movie> recommendedMovies(List<MovieRating> ratings, List<Movie> allMovies, Customer customer) {
        //Kõigepealt filteerime välja kõik filmid, mis ei ole eesti keeles
        filterByAge(allMovies, customer.getBirthDate());
        //Seejärel filtreerime välja filmid mida kasutaja on juba näinud.
        filterByAlreadySeen(ratings, allMovies);
        //Loeme kõik vaadatud žanrid paisktabelisse, võti: žanr, väärtus: mitu korda žanrit vaadatud on
        HashMap<String, Integer> watchedGenresCounted = countWatchedGenres(ratings);
        //Anname kõikidele žanritele kasutaja hinnatud filmide keskmise skoori. (nt kui Action filme on kasutaja vaadanud 2 korda ja andud
        // neile skoorid 3 ja 4, siis keskmine watchedGenresScore = (3+4)/2=3.5)
        HashMap<String, Double> watchedGenresScore = assignGenresByQuantityScore(ratings, watchedGenresCounted);
        //Lisa 3le kõige rohkem vaadatule žanrile kordaja, 1.5x esimene, 1.25x teine, 1.1x kolmas
        addMultiplierToScores(watchedGenresCounted, watchedGenresScore);
        //Leiame palju igat filmi tagastada
        HashMap<String, Integer> howManyRecommGenres = getQuantityOfRecommendedGenres(watchedGenresScore);
        //Leia kõik tagastatavad filmid
        return getRecommendedMovies(howManyRecommGenres, allMovies);
    }

    /**
     * Leiab kõik soovitatavad filmid
     *
     * @param howManyRecommGenres Kui palju igat žanrit soovitame
     * @param allMovies           Kõik filmid
     * @return Soovitatavad filmid
     */
    private List<Movie> getRecommendedMovies(HashMap<String, Integer> howManyRecommGenres, List<Movie> allMovies) {
        List<Movie> recommendedMovies = new ArrayList<>();
        for (String genre : howManyRecommGenres.keySet()) {
            int howManyMovies = howManyRecommGenres.get(genre);
            List<Movie> correctGenreMovies = getcorrectGenreMovies(genre, howManyMovies, allMovies);
            while (!correctGenreMovies.isEmpty()) {
                recommendedMovies.add(correctGenreMovies.remove(0));
            }
        }
        //Vajalik, sest tahame filme näidata kõige suuremast IMDB ratingust kõige väiksemani.
        recommendedMovies.sort((m1, m2) -> Double.compare(m2.getImdbRating(), m1.getImdbRating()));
        return recommendedMovies;
    }

    /**
     * Andes ette žanri ja arvu mitu filmi vaja (n), leiab sellest žanrist n suurima IMDB ratinguga filmi.
     *
     * @param genre         Žanr, millest filme vaja
     * @param howManyMovies Mitu filmi vaja
     * @param allMovies     Kõik filmid
     * @return n suurima IMDB ratinguga filmi
     */
    private List<Movie> getcorrectGenreMovies(String genre, int howManyMovies, List<Movie> allMovies) {
        allMovies.sort((m1, m2) -> Double.compare(m2.getImdbRating(), m1.getImdbRating()));
        List<Movie> correctGenreMovies = new ArrayList<>();
        int count = 0;
        for (Movie movie : allMovies) {
            if (count == howManyMovies)
                return correctGenreMovies;
            if (movie.getGenre().equals(genre)) {
                correctGenreMovies.add(movie);
                count++;
            }
        }
        return correctGenreMovies;
    }

    /**
     * Leiab mitu žanrit peaksime ideaalis kasutajale soovitama
     *
     * @param watchedGenresScore Žanrite skoorid
     * @return Žanrite arv, millest tahame hiljem kasutajale filme soovitada
     */
    private HashMap<String, Integer> getQuantityOfRecommendedGenres(HashMap<String, Double> watchedGenresScore) {
        HashMap<String, Integer> recommendation = new HashMap<>();
        double totalScore = 0;
        //Esimesena leiame kõikide žanrite koguskoori
        for (double val : watchedGenresScore.values()) {
            totalScore += val;
        }

        //Seejärel vaatame iga žanrit eraldi
        for (String genre : watchedGenresScore.keySet()) {
            //Leiame iga žanri relatiivse osakaalu (kui meil on action filmid skooriga 2 ja comedy filmid skooriga 4
            //, siis nt action filmide oskaal on 2/6=0.33)
            double relativeFrequency = watchedGenresScore.get(genre) / totalScore;
            //Ümardame kuni järgmise kümnendini, sest juhul kui meil oleks nt 11 erinevat žanrit kõik osakaaluga
            //0.09 ja me ümardaks alla, ei soovitaks me ühtegi filmi
            double roundRelativeFreq = Math.ceil(relativeFrequency * 10.0) / 10.0;
            //*10ga, et saada soovitatavate filmide arv
            recommendation.put(genre, (int) (roundRelativeFreq * 10));
        }
        return recommendation;
    }

    /**
     * Lisame 3le kõige vaadatumale žanrile kordaja, kus 1.5x esimene, 1.25x teine, 1.1x kolmas kõige vaadatum žanr
     *
     * @param watchedGenresCounted Kokku loetud žanrite arvud
     * @param watchedGenresScore   Žanrite skoorid
     */
    private void addMultiplierToScores(HashMap<String, Integer> watchedGenresCounted, HashMap<String, Double> watchedGenresScore) {
        //Võtame kõik vaadatud žanrid, sorteerime need kõige vaadatumate järgi
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(watchedGenresCounted.entrySet());
        entries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        for (Map.Entry<String, Integer> entry : entries) {
            String genre = entry.getKey();
            double multiplier;
            //Anname kordajad
            if (genre.equals(entries.get(0).getKey()))
                multiplier = 1.5;
            else if (genre.equals(entries.get(1).getKey()))
                multiplier = 1.25;
            else if (genre.equals(entries.get(2).getKey()))
                multiplier = 1.1;
            else break;
            //Ning lõpuks korrutame skooride paisktabelis kordajaga läbi
            watchedGenresScore.put(genre, watchedGenresScore.get(genre) * multiplier);
        }
    }

    /**
     * Annab kõikidele žanritele skoori vastavalt sellele kuidas kasutaja teisi selle žanri filme on hinnanud.
     *
     * @param ratings              Kõik kasutaja vaadatud (ja hinnatud) filmid
     * @param watchedGenresCounted Paisktabel, milles on žanrid ja nende arv kasutaja hinnangutes
     * @return Paisktabel kujul, võti: žanr, väärtus: summa žanri reitingutest/žanri reitingute koguarv
     */
    private HashMap<String, Double> assignGenresByQuantityScore(List<MovieRating> ratings, HashMap<String, Integer> watchedGenresCounted) {
        HashMap<String, Double> genreScores = new HashMap<>();
        for (MovieRating rating : ratings) {
            String genre = rating.getMovie().getGenre();
            if (!genreScores.containsKey(genre)) {
                genreScores.put(genre, rating.getRating() * 1.0);
            } else {
                genreScores.put(genre, genreScores.get(genre) + rating.getRating());
            }
        }
        //Jagame kõik lisatud žanriskoorid žanriskooride koguarvuga
        genreScores.replaceAll((k, v) -> genreScores.get(k) / watchedGenresCounted.get(k));
        return genreScores;
    }

    /**
     * Loeb kokku mitu korda kõiki žanreid vaadatud on
     *
     * @param ratings Kõik kasutaja vaadatud filmid
     * @return paisktabel, kus võti: žanr, väärtus: mitu korda žanrit vaadatud on
     */
    private HashMap<String, Integer> countWatchedGenres(List<MovieRating> ratings) {
        HashMap<String, Integer> watchedGenres = new HashMap<>();
        for (MovieRating rating : ratings) {
            String genre = rating.getMovie().getGenre();
            if (!watchedGenres.containsKey(genre)) {
                watchedGenres.put(genre, 1);
            } else {
                watchedGenres.put(genre, watchedGenres.get(genre) + 1);
            }
        }
        return watchedGenres;
    }

    /**
     * Filtreerib välja kõik filmid, mida kasutaja on juba näinud
     *
     * @param ratings   Kõik kasutaja vaadatud filmid
     * @param allMovies List kõikidest filmidest
     */
    private void filterByAlreadySeen(List<MovieRating> ratings, List<Movie> allMovies) {
        for (MovieRating movieRating : ratings) {
            allMovies.remove(movieRating.getMovie());
        }
    }

    /**
     * Filtreerib välja kõik filmid, millest kasutaja on noorem
     *
     * @param movies    List kõikidest filmidest
     * @param birthDate Kasutaja sünniaeg
     */
    public void filterByAge(List<Movie> movies, Date birthDate) {
        LocalDate currentDate = LocalDate.now();
        LocalDate localBirthDate = birthDate.toLocalDate();
        Period period = Period.between(localBirthDate, currentDate);
        int ageInYears = period.getYears();
        movies.removeIf(movie -> movie.getMinimumAge() > ageInYears);
    }
}
