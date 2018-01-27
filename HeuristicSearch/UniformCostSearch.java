import java.util.*;
//Uniform Cost Search was implemented based on the UCS algorithm pseudocode referenced from the textbook,
//"Artificial Intelligence A Modern Approach Third Edition by Stuart J. Russell and Peter Norvig", located in pages.84.
//Referenced Professor David Noelle's PA0sol.zip for guidances on some aspect of the program.
//Author: Jacky Z. Chen :: Date: Oct 14th, 2017
//Course: Introduction to Artificial Intelligence, CSE 175
public class UniformCostSearch {

	//Objects and variables initialization
	public int expansionCount = 0;
	int depth_limit = 1000;
	String initial_Location = "";
	String destination_Location = "";  	
	Map graph;

	//Default constructor
	public UniformCostSearch() {

	}

	//Constructor which takes four arguments specified by instructions
	public UniformCostSearch(Map graph, String initial_Location, String destination_Location, int depth_limit) {
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
		//create an object for sorted frontier named SFrontier
		SortedFrontier SFrontier = new SortedFrontier();
		//add node to sorted frontier
		SFrontier.addSorted(node);

		//if problem (initial state) already satisfies the goal state; same location, then return the solution of node.
		if(node.isFinalDestination(destination_Location)) {
			return node;
		}

		//Repeated state checking for UCS
		if (argument == true) {
			//create a hashset for visited nodes; necessary for repeated state checking - to avoid duplicates
			HashSet<String> visitedNodes = new HashSet<>();

			//while SFrontier isn't empty and node hasn't reached its depth limit, pop from sorted frontier
			while(!SFrontier.isEmpty() && node.depth < depth_limit) {
				node = SFrontier.removeTop();

				//if node has reached goal state, then solution is found
				if(node.isFinalDestination(destination_Location)) {
					return node;
				} 
				
				//else...
				//expand node
				node.expand();
				//count node expansions
				expansionCount++;
				//add node state to visited nodes
				visitedNodes.add(node.loc.name);

				//for every child in list collection of parent nodes
				for (Waypoint child : node.options) {
					//if that child is not in visited nodes and sorted frontier
					if (!visitedNodes.contains(child.loc.name) && !SFrontier.contains(child.loc.name)) {
						//add that child to sorted frontier
						SFrontier.addSorted(child);
					}
					//else if child is in sorted frontier and the there exists a child's path cost which is greater than its current path cost
					else if (SFrontier.contains(child.loc.name) && (SFrontier.find(child).partialPathCost >= child.partialPathCost)) {
						//remove that existing child path cost and add the current path cost to sorted frontier (shortest path possible)
						SFrontier.remove(SFrontier.find(child.loc.name));
						SFrontier.addSorted(child);
					}	
				}
			} 
			//if all the above fails, no solution can be determined
			return null;
		}
		//Non repeated state checking UCS; argument == false
		else {
			//if node already satisfies the goal state, then return solution
			if(node.isFinalDestination(destination_Location)) {
				return node;
			}
			//while SFrontier1 isn't empty and node depth hasn't reached its limit, pops top node from SFrontier1
			while(!SFrontier.isEmpty() && node.depth < depth_limit) {
				node = SFrontier.removeTop();
				//if node has reached goal state, then return node solution
				if(node.isFinalDestination(destination_Location)) {
					return node;
				}
			//else...
				//expand node
				node.expand();
				//count node expansions
				expansionCount = expansionCount+1;
				//add child node to sorted frontier
				SFrontier.addSorted(node.options);
			}
			//if all the above fails, no solution can be determine
			return null;
		}
	}
}