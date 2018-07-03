# BFS and DFS Algorithm

BFS and DFS were implemented based on the uniformed cost search algorithm; referenced pseudocode from the textbook,"Artificial Intelligence A Modern Approach Third Edition by Stuart J. Russell and Peter Norvig", located in pages.82.
Author of implmentations: Jacky Z. Chen

# Backward Chaining Algorithm

 This class implements a backward chaining inference procedure.  The
 implementation is very skeletal, and the resulting reasoning process is
 not particularly efficient.  Knowledge is restricted to the form of
 definite clauses, grouped into a list of positive literals (facts) and
 a list of Horn clause implications (rules).  The inference procedure
 maintains a list of goals.  On each step, a proof is sought for the
 first goal in this list, starting by an attempt to unify the goal with
 any known fact in the knowledge base.  If this fails, the rules are
 examined in the order in which they appear in the knowledge base, searching
 for a consequent that unifies with the goal.  Upon successful unification,
 a proof is sought for the conjunction of the rule antecedents.  If this
 fails, further rules are considered.  Note that this is a strictly
 depth-first approach, so it is incomplete.  Note, also, that there is
 no backtracking with regard to matches to facts -- the first match is
 always taken and other potential matches are ignored.  This can make
 the algorithm incomplete in another way.  In short, the order in which
 facts and rules appear in the knowledge base can have a large influence
 on the behavior of this inference procedure.
 
 In order to use this inference engine, the knowledge base must be
 initialized by a call to "initKB".  Queries are then submitted using the
 "ask" method.  The "ask" function returns a binding list which includes
 bindings for intermediate variables.
  
 David Noelle -- Tue Apr 10 17:08:45 PDT 2007
 Author of implmentations: Jacky Z. Chen

# Eval

 This class implements tools for evaluating game states for the Zombie
 Dice game.  These tools include a static function for calculating
 the expected utility value of a game state using look-ahead to a 
 fixed depth and a static heuristic evaluation function for estimating
 game payoff values for non-terminal states.  Since these tools are
 all static functions, no objects of this class need to be allocated in
 order to use them.  In general, both heuristic evaluation function
 values and expected utility values should be between plus and minus
 "State.win_payoff".

 Zombie Dice is a trademark of Steve Jackson Games.  For more information
 about this game, see "zombiedice.sjgames.com".
 
 David Noelle -- Sat Nov  3 16:37:10 PDT 2012
 Author of implmentations: Jacky Z. Chen
 
 # Layer
 
 This class implements a layer of processing units.
 Methods were implemented based on the BACK-PROP-LEARNING neural network algorithm referenced from the textbook,
 "Artificial Intelligence A Modern Approach Third Edition by Stuart J. Russell and Peter Norvig", located on pages.734.
 
 David Noelle -- Tue Apr 24 15:51:19 PDT 2007
 Author of implmentations: Jacky Z. Chen
