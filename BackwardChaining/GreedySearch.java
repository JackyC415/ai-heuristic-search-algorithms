import java.util.*;
//GreedySearch was implemented based on the UCS algorithm pseudocode with slight modification : expand heuristic function
//referenced from the textbook, "Artificial Intelligence A Modern Approach Third Edition by Stuart J. Russell and Peter Norvig", located in pages.84.
//Referenced Professor David Noelle's PA0sol.zip for guidances on some aspect of the program.
//Author: Jacky Z. Chen :: Date: Oct 14th, 2017
//Course: Introduction to Artificial Intelligence, CSE 175
public class GreedySearch {

	//Objects and variables initialization
	public int expansionCount = 0;
	int depth_limit = 1000;
	String initial_Location = "";
	String destination_Location = "";  	
	Map graph;

	//Default constructor
	public GreedySearch() {

	}

	//Constructor which takes four arguments specified by instructions
	public GreedySearch(Map graph, String initial_Location, String destination_Location, int depth_limit) {
		this.graph = graph;
		this.initial_Location= initial_Location;
		this.destination_Location = destination_Location;
		this.depth_limit = depth_limit;
	}

	//search method which takes a single boolean argument and returns Waypoint object
	public Waypoint search(boolean argument) {

		//a node from Waypoint object which takes in the initial state of the problem, path_cost is zero at this point
		Waypoint node = new Waypoint(graph.findLocation(initial_Location));
		expansionCount = 0;

		//if problem (initial state) already satisfies the goal state; same location, then return the solution of node.
		if(node.isFinalDestination(destination_Location)) {
			return node;
		}

		//Object for good heuristic function and passes in destination location in graph
		GoodHeuristic GHeuristic = new GoodHeuristic(graph.findLocation(destination_Location),graph);
		//Object for sorted frontier, sortby.h
		SortedFrontier SFrontier = new SortedFrontier(SortBy.h);
		//add node into sorted frontier
		SFrontier.addSorted(node);

		//Repeated state checking UCS
		if (argument == true) {
			//create a hashset for visited nodes; necessary for repeated state checking - to avoid duplicates
			HashSet<String> visitedNodes = new HashSet<>();

			//while SFrontier isn't empty and node hasn't reached its depth limit, remove top from SFrontier.
			while(!SFrontier.isEmpty() && node.depth < depth_limit) {
				node = SFrontier.removeTop();

				//if node has reached goal state, then return node solution
				if(node.isFinalDestination(destination_Location)) {
					return node;
				} 
				//add node state to visited nodes
				visitedNodes.add(node.loc.name);
				//calculate path cost and fill in children nodes
				node.expand(GHeuristic);
				//counts number of node expansions
				expansionCount++;

				//for each children from the parent node in the list collection
				for (Waypoint child : node.options) {
					//if that children is not in both visited nodes and frontier
					if (!visitedNodes.contains(child.loc.name) && !SFrontier.contains(child)) {
						SFrontier.addSorted(child);
					}
				}
			} 
			//if all the above fails, no solution can be determined
			return null;
		}
		//Non repeated state checking UCS; argument == false
		else {
			//if node already satisfies the goal state, return node solution
			if(node.isFinalDestination(destination_Location)) {
				return node;
			}
			//while SFrontier isn't empty and node depth hasn't reached its limit, pops top node from SFrontier
			while(!SFrontier.isEmpty() && node.depth < depth_limit) {
				node = SFrontier.removeTop();
				//if node has reached goal state, then return node solution
				if(node.isFinalDestination(destination_Location)) {
					return node;
				}
			//else...
				//expand node based on good heuristic function
				node.expand(GHeuristic);
				//count node expansions
				expansionCount++;
				//add child node to sorted frontier
				SFrontier.addSorted(node.options);
			}
			//if all the above fails, no solution can be determine
			return null;
		}
	}
}