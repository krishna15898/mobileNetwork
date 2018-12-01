import java.util.*;
import java.io.*;

public class ExchangeList {	
	Node head;
	public class Node{
		Exchange e;
		Node next;
	}

	public int listNumChildren() {
		Node temp = new Node();
		temp = head;
		int count = 0;
		while(temp != null){
			count++;
			temp = temp.next;
		}
		return count;
	}

	public boolean listIsMember(Exchange a){
		
		Node temp = head;
		while(temp != null){
			if((temp.e).equals(a)) return true;
			temp = temp.next;
		}
		return false;
	}

	public void listAddMember(Exchange a){
		if(listIsMember(a) == false){
		    Node new_node = new Node();
		 	new_node.e = a;
		 	new_node.next = null;
		    
		    if (head == null)
		    {
		        head = new_node;
		        return;
		    }
		 
		    Node last = head; 
		    while (last.next != null)
		        last = last.next;
		 
		    last.next = new_node;
		    return;
		}	
	}

	public Exchange listNthChild(int n){
		if(listNumChildren() < n)
			throw new IllegalArgumentException();
		
		else{
			Node temp = head;
			for (int j = 0; j < n; j++) {
				temp = temp.next;
			}
			return temp.e;
		}
	}

	public String printAllExchanges(int a, int b){
	    String res = "queryFindCallPath "+a+" "+b+": ";
	    for(int i = 0; i < listNumChildren(); i++){
	        res = res + listNthChild(i).id; 
	        if(i < listNumChildren() - 1)
				res = res + ", ";
	    }
	    return res;
	}

	public Exchange firstCommonExchange(ExchangeList a){
		Node temp = head;
		//here head is the lowest exchange going up.
		while(temp != null){
			if(a.listIsMember(temp.e))
				return temp.e;
			temp = temp.next;
		}//returns the first common exchange.
		return null;
	}
/*	
 	public String printAll(int n){//printAll is printing all mobiles
 		String res;

 		res = "queryMobilePhoneSet "+ n +": ";
 		for (int i = 0; i < numberOfMobiles(); i++) {
 			res = res + nthMobile(i).mobileNumber();
 			if(i < numberOfMobiles() - 1)
				res = res + ", ";
 		}
 		return res;
 	}
*/
/*
	public void listAddMember(Exchange a){
		Node temp = new Node();
		temp.e = a;
		temp.next = head;
		head = temp;
	}
*/
}
