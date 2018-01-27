//
// GoodHeuristic
//
// This class extends the Heuristic class, providing a reasonable
// implementation of the heuristic function method.  The provided "good"
// heuristic function is admissible.
//
// YOUR NAME: Jacky Z. Chen -- TODAY'S DATE: Oct 14th, 2017
// Heuristic function referenced from : "http://theory.stanford.edu/~amitp/GameProgramming/Heuristics.html#S7"

public class GoodHeuristic extends Heuristic {

	//initialization
	Map map;
	Location location;
	double thisMaxiumSpeed = 0.0;

	public GoodHeuristic(Location location, Map map) {
		super(location);
		//objects initialization
		this.map = map;
		this.location = location;

		//variables initializations
		double Road_Latitude = 0.0;
		double Road_Longtitude = 0.0;
		double maximumSpeed = 0.0;
		double totalDistance = 0.0;
		double currentSpeed = 0.0;

		//for every locations in the list of collections of locations in map.
		for (Location locations : map.locations){
			//for every roads inside location roads
			for (Road roads : locations.roads){
				//computes the double distance equation; calculates distance between the differences in road latitude and longtitude
				//equation to compute distance is theoretically Pythagorean Theorem: c = sqrt(a^2+b^2)
				Road_Latitude = Math.pow((roads.fromLocation.latitude-roads.toLocation.latitude), 2);
				Road_Longtitude = Math.pow((roads.fromLocation.longitude-roads.toLocation.longitude), 2);
				totalDistance = Math.sqrt((Road_Latitude + Road_Longtitude));
				//speed is distance over time
				currentSpeed = (totalDistance/roads.cost);
				//if current speed exceeds maximum possible speed, then the current speed will become the new maximum speed.
				if(currentSpeed > maximumSpeed) {
					maximumSpeed = currentSpeed;
				}
			}
		}
		//store maximum speed into that variable for calculation in heuristic function
		thisMaxiumSpeed = maximumSpeed;
	}
	// YOU CAN ADD ANYTHING YOU LIKE TO THIS CLASS ... WHATEVER WOULD
	// ASSIST IN THE CALCULATION OF YOUR GOOD HEURISTIC VALUE.

	// heuristicFunction -- Return the appropriate heuristic values for the
	// given search tree node.  Note that the given Waypoint should not be
	// modified within the body of this function.
	public double heuristicFunction(Waypoint wp) {

		//object to get node destination
		Location node_destination = getDestination();

		//variables initialization
		double hVal = 0.0;
		double totalDistance = 0.0;
		double speed = 0.0;
		double dx1 = wp.loc.latitude;
		double dy1 = wp.loc.longitude;
		double dx2 = node_destination.latitude;
		double dy2 = node_destination.longitude;
		double dx = Math.pow(dx2-dx1, 2);
		double dy = Math.pow(dy2-dy1, 2);

		//compute double distance equation
		totalDistance = Math.sqrt(dx + dy);
		speed = thisMaxiumSpeed;
		//heuristic value is calculated based on the computed distance over speed
		hVal = (totalDistance/speed);

		// COMPUTE A REASONABLE HEURISTIC VALUE HERE
		return (hVal);
	}
}
