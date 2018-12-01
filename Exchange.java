import java.util.*;

public class Exchange {
	
	int id;
	Exchange parent;

	ExchangeList childs = new ExchangeList();
	MobilePhoneSet mobiles = new MobilePhoneSet();

	Exchange(int number) {
		id = number;
	}
	Exchange(){
		id = 0;
	}
	
	public Exchange parent() {
		return parent;
	}

	public int numChildren() {			
		return childs.listNumChildren();
	}
	
	public boolean isRoot() {
		if (parent() == null) return true;
		return false;
	}
	
	public Boolean isChild(Exchange a){
		return childs.listIsMember(a); 
	}

	public RoutingMapTree subtree(int i){
		if(this.numChildren() < i)
			return null;
		RoutingMapTree res = new RoutingMapTree(childList().listNthChild(i));
		//res.root = childList().listNthChild(i);
		return res;
	}

	public ExchangeList childList(){
		return childs;
	}

	public MobilePhoneSet residentSet() {
		return mobiles;
	}

	public String printAllMobiles(int n){
		return this.residentSet().printAll(n);
	}
	public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Exchange that = (Exchange) y;
        int n=this.id;
        if(n==that.id)
          return(true);
        else
          return false;
  }
	//makes a the child
	/*public void makeChild(int a)
	{	
		if(findExchange(a) != null )
		Exchange newchild = new Exchange(a);
		this.childs.listAddMember(newchild);
		newchild.parent = this;
	}*/
	//Find exchange "a" in all its children
	/*public Exchange findExchange(int a){
		if(id == a) {	
			return this;
		}

		else {		
			for (int i = 1; i <= this.childList().listNumChildren(); i++) {
				Exchange A = this.childList().listNthChild(i).findExchange(a);
				if(A != null)	
					return A;		
			}
			return null;
		}
	}*/
}
