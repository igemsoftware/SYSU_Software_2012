/*
 Copyright (c) 2009 The Regents of the University of California.
 All rights reserved.
 Permission is hereby granted, without written agreement and without
 license or royalty fees, to use, copy, modify, and distribute this
 software and its documentation for any purpose, provided that the above
 copyright notice and the following two paragraphs appear in all copies
 of this software.

 IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY
 FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
 ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
 SUCH DAMAGE.

 THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
 INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
 PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
 CALIFORNIA HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
 ENHANCEMENTS, OR MODIFICATIONS..
 */

/**
  *@author Lesia Bilitchenko, Adam Liu
  *@see <a href="http://www.clothocad.org">http://www.clothocad.org</a>
  */

package eugene;

import java.util.ArrayList;

public class Rule {

    public String name;
	public String operator;
	public String operand1;
	public String operand2;

	Rule() {
		name = "";
		operator = "";
		operand1 = "";
		operand2 = "";
	}

	public String evaluate(ArrayList<String> componentList, ArrayList<String> unopenedList) {
		if (operator.equals("BEFORE")) {
			if (componentList != null) {
				return evaluateBefore(componentList, operand1, operand2);
            } else {
				return evaluateBefore(unopenedList, operand1, operand2);
		
            }
        }
		if (operator.equals("AFTER")) {
			if (componentList != null) {
				return evaluateBefore(componentList, operand2, operand1);
            } else {
				return evaluateBefore(unopenedList, operand2, operand1);
            }
        }
		if (operator.equals("WITH")) {
			if (componentList != null) {
				return evaluateWith(componentList);
            } else {
				return evaluateWith(unopenedList);
            }
        }
		if (operator.equals("NOTWITH")) {
			if (componentList != null) {
				String result = evaluateWith(componentList);
				if (result.equals("0")) {
					return "1";
                } else {
					return "0";
                }
			} else {
				String result = evaluateWith(unopenedList);
				if(result.equals("0")) {
					return "1";
                } else {
					return "0";
                }
			}
		}
		if (operator.equals("NEXTTO")) {
			if (componentList != null) {
				return evaluateNextTo(componentList);
            } else {
				return evaluateNextTo(unopenedList);
            }
        }
		if (operator.equals("NOTCONTAINS")) {
			if (componentList != null) {
				return evaluateNotContains(componentList);
			} else {
				return evaluateNotContains(unopenedList);
			}
		}
        
        if (operator.equals("CONTAINS")) {
            if (componentList != null) {
                return evaluateContains(componentList);
            } else {
                return evaluateContains(unopenedList);
            }
        }

		if (operator.equals("NOTMORETHAN")) {
			if (componentList != null) {
				return evaluateNotMoreThan(componentList);
			} else {
				return evaluateNotMoreThan(unopenedList);
			}
		}
		if (operator.equals(">=")) {
			return evaluateGEQUAL();
		}
		if (operator.equals("<=")) {
			return evaluateLEQUAL();
		}
		if (operator.equals(">")) {
			return evaluateGTHAN();
		}
		if (operator.equals("<")) {
			return evaluateLTHAN();
		}
		if (operator.equals("!=")) {
			if(!operand1.equals(operand2)) {
				return "1";
            } else {
				return "0";
            }
		}
		if (operator.equals("==")) {
			if(operand1.equals(operand2)) {
				return "1";
			} else {
				return "0";
			}
		}
		return "null";
	}

	//check for all occurences of operator1 before all occurences of operator2
	public String evaluateBefore(ArrayList<String> compList, String op1, String op2) {
		String result = "";
		int pos1 = -1, pos2 = -1;
		for (int i = 0 ; i < compList.size(); i++ ) {
			if (compList.get(i).equals(op1) && ((pos1 == -1) || (pos1 == -2))) {
				pos1 = i;
            }
			if (compList.get(i).equals(op2) && (pos2 == -1)) {
				pos2 = i;
            }
			if (pos2 != -1 && pos1 != -2 && pos1 != -1) {
				if (pos1 < pos2) {
					pos1 = -2;
				} else {
					result = "0";
					break;
				}
			}
		}
		//means have found that no other occurence of operand1 comes after operand2
		if (pos1 == -2) {
			return "1";
        }
		//means have found a violation
		if (!result.equals("")) {
			return result;
        }
		//haven't found neither occurence of operand1 and operand2
		if(pos1 == -1 && pos2 == -1)
			return "1";
		//otherwise only one operand has been found and not the other
		return "1";
	}

	//checks if operand1 and operand2 both exist in the list together
	public String evaluateWith(ArrayList<String> compList) {
        if (compList.indexOf(operand1) != -1 && compList.indexOf(operand2) != -1) {
            return "1";
        } else {
            return "0";
        }
	}

    public String evaluateNextTo(ArrayList<String> compList) {
		for (int i = 0; i < compList.size()-1; i++) {
			if ((compList.get(i).equals(operand1) && compList.get(i+1).equals(operand2)) ||
			    (compList.get(i).equals(operand2) && compList.get(i+1).equals(operand1))) {
				return "1";
            }
		}
		return "0";
	}

    public String evaluateContains(ArrayList<String> compList) {
        for(int i=0; i < compList.size(); i++){
            if(compList.get(i).equals(operand2))
                return "1";
        }

        return "0";
    }

	public String evaluateNotContains(ArrayList<String> compList) {
		for(int i=0; i < compList.size(); i++) {
			if(compList.get(i).equals(operand2))
				return "0";
		}
		return "1";
	}
	
	public String evaluateNotMoreThan(ArrayList<String> compList) {
		int count = 0;
		for(int i=0; i < compList.size(); i++) {
			if(compList.get(i).equals(operand1))
				count++;
		}
		if(count <= (int)Double.parseDouble(operand2))
			return "1";
		else
			return "0";
	}
	
	public String evaluateGEQUAL() {
        boolean checkDigit1 = checkIfContainsDigits(operand1);
        boolean checkDigit2 = checkIfContainsDigits(operand2);
        if (checkDigit1 && checkDigit2) {
            double val1 = Double.parseDouble(operand1);
            double val2 = Double.parseDouble(operand2);
            if (val1 >= val2) {
                return "1";
            } else {
                return "0";
            }
        } else if (!checkDigit1 && !checkDigit2) {
			if (operand1.compareTo(operand2) >= 0) {
				return "1";
            } else {
				return "0";
            }
		} else {
 			System.out.println("Cannot compare operands of different types.");
		}
		return "0";
	}

	public String evaluateLEQUAL() {
        boolean checkDigit1 = checkIfContainsDigits(operand1);
        boolean checkDigit2 = checkIfContainsDigits(operand2);
        if (checkDigit1 && checkDigit2) {
            double val1 = Double.parseDouble(operand1);
            double val2 = Double.parseDouble(operand2);
            if (val1 <= val2) {
                return "1";
            } else {
                return "0";
            }
        } else if (!checkDigit1 && !checkDigit2) {
			if (operand1.compareTo(operand2) <= 0) {
				return "1";
            } else {
				return "0";
            }
		} else {
 			System.out.println("Cannot compare operands of different types.");
		}
		return "0";
	}

    public String evaluateGTHAN() {
        boolean checkDigit1 = checkIfContainsDigits(operand1);
        boolean checkDigit2 = checkIfContainsDigits(operand2);
        if (checkDigit1 && checkDigit2) {
            double val1 = Double.parseDouble(operand1);
            double val2 = Double.parseDouble(operand2);
            if (val1 > val2) {
                return "1";
            } else {
                return "0";
            }
        } else if (!checkDigit1 && !checkDigit2) {
			if (operand1.compareTo(operand2) > 0) {
				return "1";
            } else {
				return "0";
            }
		} else {
 			System.out.println("Cannot compare operands of different types.");
		}
		return "0";
	}

	public String evaluateLTHAN() {
        boolean checkDigit1 = checkIfContainsDigits(operand1);
        boolean checkDigit2 = checkIfContainsDigits(operand2);
		if (checkDigit1 && checkDigit2) {
			double val1 = Double.parseDouble(operand1);
			double val2 = Double.parseDouble(operand2);
			if (val1 < val2) {
				return "1";
            } else {
				return "0";
            }
		} else if (!checkDigit1 && !checkDigit2) {
			if (operand1.compareTo(operand2) < 0) {
				return "1";
            } else {
				return "0";
            }
		} else {
 			System.out.println("Cannot compare operands of different types.");
		}
		return "0";
	}

    public boolean checkIfContainsDigits(String str) {
        char[] charOp = str.toCharArray();
		for (int i = 0 ; i < charOp.length; i++) {
			if (!Character.isDigit(charOp[i])) {
				return false;
			}
		}
        return true;
    }
    
}
