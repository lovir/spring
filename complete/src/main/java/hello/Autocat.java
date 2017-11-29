package hello;

public class Autocat {

	private long rank;
	private String categoryCode;
	private String categoryName;
	private String recommendScore;

	public Autocat(long rank, String categoryCode, String categoryName, String recommendScore) {
		// TODO Auto-generated constructor stub
		 this.rank = rank;
		 this.categoryCode = categoryCode;
	     this.categoryName = categoryName;
	     this.recommendScore = recommendScore;
	}
	
	 public long getRank() {
	        return rank;
	    }
	 
	 public String getCategoryCode() {
	        return categoryCode;
	    } 

    public String getCategoryName() {
        return categoryName;
    }
    
    public String getRecommendScore() {
    	return recommendScore;
    }
	
	public Autocat() {}
}
