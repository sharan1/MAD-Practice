import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class App1a {
	
	public static void main(String[] args) {
		Movie movie = new Movie();
		
		ArrayList<Movie> data = movie.parseMovie("topmovies.csv");
		
		Collections.sort(data, new Comparator<Movie>() {

			@Override
			public int compare(Movie o1, Movie o2) {
				if(o1.getRevenue() < o2.getRevenue())
					return 1;
				else
					return -1;
			}
			
		});
		
		for (Movie movie2 : data) {
			System.out.println(movie2.toString());
		}
		return;
	}

}
