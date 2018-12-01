import java.util.*;

public class Myset{
		
	node head;
		
	class node{
		Object data;
		node next;
	}
	
	public Boolean IsEmpty() {
		if(head == null) return true;
		return false;
	}
	
	public int numberOfObjects(){
		node temp = new node();
		temp = head;
		int count = 0;
		while(temp != null){
			count++;
			temp = temp.next;
		}
		return count;
	}
	//REQD TO RETURN ALL OBJECTS (IN ANY ORDER)
	public Object nthObject(int n){
		if(n >= numberOfObjects())
			return null;
		node temp = head;
		for (int i = 0; i < n; i++) {
			temp = temp.next;
		}
		return temp.data;
	}

	public Boolean IsMember(Object o) {
		
		node temp = head;
		while( temp != null ) {
			if(temp.data.equals(o)) return true;
			temp = temp.next;
		}
		return false;
	}
	
	public void Insert(Object o) {
		if(IsMember(o)) return;

		node newnode = new node();
		newnode.data = o;
		newnode.next = null;

		if(head == null){
			head = newnode;
			return;
		}


		node temp = head;
		while(temp.next != null)
			temp = temp.next;

		temp.next = newnode;
		return;
	}
	
	public void Delete(Object o) {
		
		if( IsEmpty() == true)
			throw new NoSuchElementException();
			
		node temp = head;
		if(head.data.equals(o))
			head = head.next;

		while( temp != null ) {
			
			if(temp.data.equals(o)) return;
			
			if(temp.next.data.equals(o)) {
				temp.next = temp.next.next;
				return;
			}
			temp = temp.next;
		}
		
		throw new NoSuchElementException();
	}
	
	public Myset Union(Myset a) {
		
		//if( IsEmpty() && a.IsEmpty() ) return null;//both empty
		
		if ( a.IsEmpty() ) return this;//a empty
		if ( this.IsEmpty() ) return a;//our set empty
		
		Myset res = this;
		
		node temp2 = new node();
		temp2 = a.head;
		
		node temp = new node();
		temp = res.head;
		
		while (temp.next != null) {
			temp = temp.next;
		}
		
		while (temp2 != null) {
			if(!(res.IsMember(temp2.data))) {
				temp.next = temp2;
				temp = temp.next;
				
			}
			temp2 = temp2.next;
		}
		node p = new node();
		p = res.head;
		while (p != null) {
			//System.out.println(p.data);
			p = p.next;
		}
		
		return res;
	}
	
	public Myset Intersection(Myset a) {
		
		Myset res = new Myset();
		node temp1 = new node();
		
		temp1 = head;
		
		while(temp1 != null) {			
			if (a.IsMember(temp1.data)) {
				res.Insert(temp1.data);
			}
			temp1 = temp1.next;
		}
		
		return res;
	}
}

