package main;

import java.math.BigInteger;

public class Vars {
	static int m=40;
	static boolean isInRange(boolean leftInclude,boolean rightInclude,BigInteger left,BigInteger right,BigInteger id){
		
		if(left.compareTo(right)>=0){
			return isInRange(leftInclude,true,left,new BigInteger("2").pow(Vars.m).subtract(new BigInteger("1")),id) || 
					isInRange(true,rightInclude,(new BigInteger("0")),right,id);
		}
		
		
		if(leftInclude && rightInclude){
			if(id.compareTo(left)>=0 && id.compareTo(right)<=0){
				return true;
			}
		}
		else if(leftInclude){
			if(id.compareTo(left)>=0 && id.compareTo(right)<0){
				return true;
			}
		}
		else if(rightInclude){
			if(id.compareTo(left)>0 && id.compareTo(right)<=0){
				return true;
			}
		}
		else if(id.compareTo(left)>0 && id.compareTo(right)<0){
				return true;
		}
		
		return false;
	}
	
	
	
	
	
	/*
	public static void main(String[] args){
		BigInteger x1=new BigInteger("2");
		BigInteger x2=new BigInteger("56");
		BigInteger id=new BigInteger("1");
		System.out.println(isInRange(true,false,x2,x1,id));
	} */
}
