import java.util.*;

//BFS and DFS were implemented based on the uniformed cost search algorithm referenced from the textbook,
//"Artificial Intelligence A Modern Approach Third Edition by Stuart J. Russell and Peter Norvig", located in pages.82.
//Author: Jacky Z. Chen
//Course: Introduction to Artificial Intelligence, CSE 175

public class DFSearch {
	
	//Objects and variables initialization
		public int expansionCount = 0;
		int depth_limit = 0;
		String initial_Location = "";
		String destination_Location = "";  	
		Map graph;
		
		//Default constructor
		public DFSearch() {
			
		}
		
		//Constructor which takes four arguments specified by instructions
		public DFSearch(Map graph, String initial_Location, String destination_Location, int depth_limit) {
			this.graph = graph;
			this.initial_Location= initial_Location;
			this.destination_Location = destination_Location;
			this.depth_limit = depth_limit;
		}
		
		//search method which takes a single boolean argument and returns Waypoint object
		public Waypoint search(boolean argument) {
			
			//A node from Waypoint object which takes in the initial state of the problem, path_cost is zero at this point
			Waypoint node = new Waypoint(graph.findLocation(initial_Location));
			expansionCount = 0;
			
			//if problem (initial state) already satisfies the goal state; same location, then return the solution of node
			if(node.loc.name == destination_Location) {
				return node;
			}
			//FIFO node with frontier object
			Frontier frontier = new Frontier();
			frontier.addToBottom(node);

			//Non-repeated state checking DFS
			if (argument == true) {
			//create a hashset for visited nodes; necessary for repeated state checking
			HashSet<String> visitedNodes = new HashSet<>();

				//while frontier isn't empty and node depth hasn't reached its limit, LIFO (Stack)
				while(!frontier.isEmpty() && node.depth < depth_limit-1) {
					node = frontier.removeTop();
					
					//if node has reached its goal state, return node solution
					if(node.isFinalDestination(destination_Location)) {
						return node;
					}
					
					//add node state to visited nodes
					visitedNodes.add(node.loc.name);
					//calculate path cost and fill in children nodes
					node.expand();
					//counts number of node expansions
					expansionCount = expansionCount+1;
						
					//for each options referenced from the parent node in the list collection
					for(Waypoint actions: node.options) {
						//if visited nodes and frontier do not contain that option, push it to frontier
						if((!visitedNodes.contains(actions.loc.name)) && (!frontier.contains(actions))) {
								frontier.addToTop(actions);
					}
				}
			}		
	//if all the above fails, no solution can be determined.
	return null;
	}
			//Repeated state checking DFS
			else {
				//add parent node to frontier
				Frontier frontier1 = new Frontier();
				frontier1.addToBottom(node);
					//while frontier isn't empty and node depth hasn't reached its limit, lIFO (Stack)
					while(!frontier1.isEmpty() && node.depth < depth_limit-1) {
				
						node = frontier1.removeTop();
						//if node has reached goal state
						if(node.loc.name == destination_Location) {
							//return solution of node
							return node;
						}
						//calculate path_cost and fill in children nodes
						node.expand();
						//counts number of expansions
						expansionCount = expansionCount+1;
						//push children nodes to frontier
						frontier1.addToTop(node.options);
					}
			//if all the above fails, no solution can be determined
			return null;
			}
		}		
	}