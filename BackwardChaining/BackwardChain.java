//
// BackwardChain
//
// This class implements a backward chaining inference procedure.  The
// implementation is very skeletal, and the resulting reasoning process is
// not particularly efficient.  Knowledge is restricted to the form of
// definite clauses, grouped into a list of positive literals (facts) and
// a list of Horn clause implications (rules).  The inference procedure
// maintains a list of goals.  On each step, a proof is sought for the
// first goal in this list, starting by an attempt to unify the goal with
// any known fact in the knowledge base.  If this fails, the rules are
// examined in the order in which they appear in the knowledge base, searching
// for a consequent that unifies with the goal.  Upon successful unification,
// a proof is sought for the conjunction of the rule antecedents.  If this
// fails, further rules are considered.  Note that this is a strictly
// depth-first approach, so it is incomplete.  Note, also, that there is
// no backtracking with regard to matches to facts -- the first match is
// always taken and other potential matches are ignored.  This can make
// the algorithm incomplete in another way.  In short, the order in which
// facts and rules appear in the knowledge base can have a large influence
// on the behavior of this inference procedure.
//
// In order to use this inference engine, the knowledge base must be
// initialized by a call to "initKB".  Queries are then submitted using the
// "ask" method.  The "ask" function returns a binding list which includes
// bindings for intermediate variables.
//
// David Noelle -- Tue Apr 10 17:08:45 PDT 2007
//

//Assignment - To Implement the following functions:
//1) public BindingList unify(Literal lit1, Literal lit2, BindingList bl) { }
//2) public BindingList unify(Term t1, Term t2, BindingList bl) { }
//3) public BindingList unify(Function f1, Function f2, BindingList bl) { }
//Author: Jacky Z. Chen -- Mon Nov 6 4:54:19 PDT 2017

import java.util.*;


public class BackwardChain {

	public KnowledgeBase kb;

	// Default constructor ...
	public BackwardChain() {
		this.kb = new KnowledgeBase();
	}

	// initKB -- Initialize the knowledge base by interactively requesting
	// file names and reading those files.  Return false on error.
	public boolean initKB() {
		return (kb.readKB());
	}

	// unify -- Return the most general unifier for the two provided literals,
	// or null if no unification is possible.  The returned binding list
	// should be freshly allocated.
	public BindingList unify(Literal lit1, Literal lit2, BindingList bl) {

		// PROVIDE YOUR CODE HERE!
		if (lit1.pred.equals(lit2.pred)) {
			return unify(lit1.args, lit2.args, bl);
		}
		return (null);
	}

	// unify -- Return the most general unifier for the two provided terms,
	// or null if no unification is possible.  The returned binding list
	// should be freshly allocated.
	public BindingList unify(Term t1, Term t2, BindingList bl) {

		// PROVIDE YOUR CODE HERE!

		//initialize a new binding list object 
		BindingList newBindingList = new BindingList(bl);

		//initialize boolean checks for terms constants, variables and functions
		//for readability and efficiency purposes
		boolean T1C = (t1.c == null);
		boolean T2C = (t2.c == null);
		boolean T1V = (t1.v == null);
		boolean T2V = (t2.v == null);
		boolean T1F = (t1.f == null);
		boolean T2F = (t2.f == null);

		//check both terms constants t1 and t2 and if aren't equivalent
		//then return new BL
		if(!T1C && !T2C && (t1.equals(t2))) {
			return new BindingList(newBindingList);
		}

		//check terms constant t1 and variable t2
		//then unify t2 and t1
		if (!T1C && !T2V) {
			return unify (t2, t1, newBindingList);
		}

		//check terms variable t1 and function t2
		//then unify t2 and t1
		if (!T1V && !T2F) {
			return unify(t2,t1,newBindingList);
		}

		//check terms function t1 and t2
		//then unify t1 and t2
		if (!T1F && !T2F) {
			return unify(t1.f, t2.f, newBindingList);
		}

		//combination one variable and constant
		//check terms variable t1 and constant t2
		if (!T1V && !T2C) {
			//check if t1 and t2 are bound
			if (newBindingList.boundValue(t1.v) == null) {
				//t1 and t2 bounded
				//return new BL
				newBindingList.addBinding(t1.v, t2);
				return (newBindingList);
			}
			else {
				//else check whether t1 and t2 are equal
				//return new BL
				//else return null
				if (newBindingList.boundValue(t1.v).equals(t2)) {
					return (newBindingList);
				}
				return (null);
			}
		} 

		//combination two variable and variable
		//check terms variables t1 and t2
		if (!T1V && !T2V) {
			//check if variable t1 is bound in BL
			if (newBindingList.boundValue(t1.v) == null) {
				//variable t1 and t2 are bounded
				//then return new BL
				newBindingList.addBinding(t1.v, t2);
				return (newBindingList);
			}
			//else if, check whether variable t2 is bound in BL
			else if (newBindingList.boundValue(t2.v) == null) {
				//variables t2 and t1 are bounded
				//then return new BL
				newBindingList.addBinding(t2.v, t1);
				return (newBindingList);
			}
			//lastly,check whether both variables t1 and t2 are equivalent
			//then return new BL
			else {
				if (t1.v.equals(t2.v)) {
					return (newBindingList);
				}		
				//check fails
				return (null);
			}
		}
		
		//combination three variable and function
		//check terms variable t1 and function t2
		if(!T1V && !T2F) {
			//check whether variable t1 is bound in BL
			if(newBindingList.boundValue(t1.v) == null) {
				//bound t1 and t2
				//then return new BL
				newBindingList.addBinding(t1.v, t2);
				return (newBindingList);
			}
			else {
				//check whether variable t1 exists in function t2
				//if that's the case, return null
				if (t2.f.allVariables().contains(t1.v)) {
					return null;
				}
				//next check whether t1 and t2 are equivalent
				//then return new BL
				if (newBindingList.boundValue(t1.v).equals(t2)) {
					return (newBindingList);
				}
			}
		}
		return (null);
	}

	// unify -- Return the most general unifier for the two provided lists of
	// terms, or null if no unification is possible.  The returned binding list
	// should be freshly allocated.
	public BindingList unify(Function f1, Function f2, BindingList bl) {

		// PROVIDE YOUR CODE HERE!
		if(f1.func.equals(f2.func)) {
			return unify(f1.args, f2.args, bl);
		}

		return (null);
	}

	// unify -- Return the most general unifier for the two provided lists of
	// terms, or null if no unification is possible.  The returned binding 
	// list should be freshly allocated.
	public BindingList unify(List<Term> ts1, List<Term> ts2, 
			BindingList bl) {
		if (bl == null)
			return (null);
		if ((ts1.size() == 0) && (ts2.size() == 0))
			// Empty lists match other empty lists ...
			return (new BindingList(bl));
		if ((ts1.size() == 0) || (ts2.size() == 0))
			// Ran out of arguments in one list before reaching the
			// end of the other list, which means the two argument lists
			// can't match ...
			return (null);
		List<Term> terms1 = new LinkedList<Term>();
		List<Term> terms2 = new LinkedList<Term>();
		terms1.addAll(ts1);
		terms2.addAll(ts2);
		Term t1 = terms1.get(0);
		Term t2 = terms2.get(0);
		terms1.remove(0);
		terms2.remove(0);
		return (unify(terms1, terms2, unify(t1, t2, bl)));
	}

	// askFacts -- Examine all of the facts in the knowledge base to
	// determine if any of them unify with the given literal, under the
	// given binding list.  If a unification is found, return the
	// corresponding most general unifier.  If none is found, return null
	// to indicate failure.
	BindingList askFacts(Literal lit, BindingList bl) {
		BindingList mgu = null;  // Most General Unifier
		for (Literal fact : kb.facts) {
			mgu = unify(lit, fact, bl);
			if (mgu != null)
				return (mgu);
		}
		return (null);
	}

	// askFacts -- Examine all of the facts in the knowledge base to
	// determine if any of them unify with the given literal.  If a
	// unification is found, return the corresponding most general unifier.
	// If none is found, return null to indicate failure.
	BindingList askFacts(Literal lit) {
		return (askFacts(lit, new BindingList()));
	}

	// ask -- Try to prove the given goal literal, under the constraints of
	// the given binding list, using both the list of known facts and the 
	// collection of known rules.  Terminate as soon as a proof is found,
	// returning the resulting binding list for that proof.  Return null if
	// no proof can be found.  The returned binding list should be freshly
	// allocated.
	BindingList ask(Literal goal, BindingList bl) {
		BindingList result = askFacts(goal, bl);
		if (result != null) {
			// The literal can be unified with a known fact ...
			return (result);
		}
		// Need to look at rules ...
		for (Rule candidateRule : kb.rules) {
			if (candidateRule.consequent.pred.equals(goal.pred)) {
				// The rule head uses the same predicate as the goal ...
				// Standardize apart ...
				Rule r = candidateRule.standardizeApart();
				// Check to see if the consequent unifies with the goal ...
				result = unify(goal, r.consequent, bl);
				if (result != null) {
					// This rule might be part of a proof, if we can prove
					// the rule's antecedents ...
					result = ask(r.antecedents, result);
					if (result != null) {
						// The antecedents have been proven, so the goal
						// is proven ...
						return (result);
					}
				}
			}
		}
		// No rule that matches has antecedents that can be proven.  Thus,
		// the search fails ...
		return (null);
	}

	// ask -- Try to prove the given goal literal using both the list of 
	// known facts and the collection of known rules.  Terminate as soon as 
	// a proof is found, returning the resulting binding list for that proof.
	// Return null if no proof can be found.  The returned binding list 
	// should be freshly allocated.
	BindingList ask(Literal goal) {
		return (ask(goal, new BindingList()));
	}

	// ask -- Try to prove the given list of goal literals, under the 
	// constraints of the given binding list, using both the list of known 
	// facts and the collection of known rules.  Terminate as soon as a proof
	// is found, returning the resulting binding list for that proof.  Return
	// null if no proof can be found.  The returned binding list should be
	// freshly allocated.
	BindingList ask(List<Literal> goals, BindingList bl) {
		if (goals.size() == 0) {
			// All goals have been satisfied ...
			return (bl);
		} else {
			List<Literal> newGoals = new LinkedList<Literal>();
			newGoals.addAll(goals);
			Literal goal = newGoals.get(0);
			newGoals.remove(0);
			BindingList firstBL = ask(goal, bl);
			if (firstBL == null) {
				// Failure to prove one of the goals ...
				return (null);
			} else {
				// Try to prove the remaining goals ...
				return (ask(newGoals, firstBL));
			}
		}
	}


}

