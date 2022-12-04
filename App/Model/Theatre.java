package Model;
public class Theatre {
    private String theatreName;
    private String theatreLocation;
    private int theatreID;
	public static int theatreCount;
    public Theatre(String name, String location){
        this.theatreName = name;
		this.theatreLocation = location;
        theatreID = theatreCount++;
    }
    public String getTheatreName(){
        return this.theatreName;
    }
    public String getTheatreLocation(){
        return this.theatreLocation;
    }
    public int getTheatreID() {
        return this.theatreID;
    }
}
