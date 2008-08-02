/*****************************************************************************
      SQLJEP - Java SQL Expression Parser 0.2
      November 1 2006
         (c) Copyright 2006, Alexey Gaidukov
      SQLJEP Author: Alexey Gaidukov

      SQLJEP is based on JEP 2.24 (http://www.singularsys.com/jep/)
           (c) Copyright 2002, Nathan Funk
 
      See LICENSE.txt for license information.
*****************************************************************************/

package com.meidusa.amoeba.sqljep.function;

import com.meidusa.amoeba.sqljep.function.Comparative;
import com.meidusa.amoeba.sqljep.function.ComparativeComparator;
import com.meidusa.amoeba.sqljep.function.PostfixCommand;
import com.meidusa.amoeba.sqljep.ASTFunNode;
import com.meidusa.amoeba.sqljep.JepRuntime;
import com.meidusa.amoeba.sqljep.ParseException;

public final class ComparativeLT extends PostfixCommand {
	final public int getNumberOfParameters() {
		return 2;
	}
	
	public void evaluate(ASTFunNode node, JepRuntime runtime) throws ParseException {
		node.childrenAccept(runtime.ev, null);
		Comparable<?>  param2 = runtime.stack.pop();
		Comparable<?>  param1 = runtime.stack.pop();
		if (param1 == null || param2 == null) {
			runtime.stack.push(Boolean.FALSE);
		} else {
			if(param1 instanceof Comparative){
				Comparative other = (Comparative) param1;
				boolean result = other.intersect(Comparative.LessThan, param2, ComparativeComparator.comparator);
				runtime.stack.push(result);
			}else{
				runtime.stack.push(ComparativeComparator.compareTo(param1, param2) < 0);
			}
		}
	}
}

