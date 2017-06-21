import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class App1b {
	public static String listToArray(ArrayList<String> str) {
		int n = str.size();
		String res = "";
		Collections.sort(str);
		for(int i = 0; i < n; i++) {
			res += str.get(i);
			if(i != n-1) {
				res += " , ";
			}
		}
		return res;
	}
	
	public static void main(String[] args) {
		Movie movie = new Movie();
		ArrayList<Movie> data = movie.parseMovie("topmovies.csv");
		int size = data.size();
		
		TreeMap<String, ArrayList<String>> map = new TreeMap<>();
		
		for(int i = 0; i < size; i++) {
			if(!map.containsKey(data.get(i).getStudio()))
				map.put(data.get(i).getStudio(), new ArrayList<String>());
			map.get(data.get(i).getStudio()).add(data.get(i).getName());
		}
		
		for (Map.Entry<String, ArrayList<String>> entry : map.entrySet())
		{
		    System.out.println(entry.getKey() + ": \t" + listToArray(entry.getValue()));
		}
		
	}

}
