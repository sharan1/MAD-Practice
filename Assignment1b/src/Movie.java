import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Movie {
	private String name;
	private String studio;
	private double revenue;
	private Integer year;
	
	public Movie(String name, String studio, double revenue, Integer year) {
		this.name = name;
		this.studio = studio;
		this.revenue = revenue;
		this.year = year;
		return;
	}


	@Override
	public String toString() {
		return "Movie [name=" + name + ", studio=" + studio + ", revenue=" + revenue + ", year=" + year + "]";
	}


	public Movie() {
		// TODO Auto-generated constructor stub
	}


	public String getName() {
		return name;
	}


	public String getStudio() {
		return studio;
	}


	public double getRevenue() {
		return revenue;
	}


	public Integer getYear() {
		return year;
	}
	
	public ArrayList<Movie> parseMovie(String filename) {
		if(filename == null || filename.isEmpty()) {
			System.out.println("Invalid File Path");
			return null;
		}
		
		String filepath = System.getProperty("user.dir") + "/" + filename;
		BufferedReader inputStream = null;
		ArrayList<Movie> data = new ArrayList<>();
		try {
			try {
				inputStream = new BufferedReader(new FileReader(filepath));
				String lineContent = null;
				boolean check = false;
				while((lineContent = inputStream.readLine()) != null) {
					if(!check) {
						check = true;
						continue;
					}
					String[] resultingTokens = lineContent.split(",");
					Integer size = resultingTokens.length;
					Integer y = Integer.parseInt(resultingTokens[size-1]);
					double r = Double.parseDouble(resultingTokens[size-2]);
					String st = resultingTokens[size-3];
					String na = "";
					for(int i = 0; i < size-3;i++) {
						na += resultingTokens[i];
						if(i != size-4)
							na += ",";
					}
					Movie m = new Movie(na.trim(), st.trim(), r, y);
					data.add(m);					
				}
			}
			finally {
				if(inputStream != null) {
					inputStream.close();
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		return data;
	}
}
