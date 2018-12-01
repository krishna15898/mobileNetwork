import java.util.*;
import java.io.*;
public class RoutingMapTree {
	
	Exchange root;
	Myset switchedOffPhones = new Myset();
	public RoutingMapTree(){
		root = new Exchange(0);
		Myset switchedOffPhones = new Myset();
	}	
	public RoutingMapTree (Exchange e){
		root = e;
		Myset switchedOffPhones = new Myset();
	}

	// add Exchange with int a as a child of int b
	public void addExchange(int a,int b){
		if(findExchange(b) == null) 
			throw new NoSuchElementException();
		if(findExchange(a) != null)
			throw new IllegalArgumentException();
		Exchange B = findExchange(b);
		Exchange A = new Exchange(a);
		//B.makeChild(a);//making 'a' b's child and b a's parent
		A.parent = B;
		B.childList().listAddMember(A);
	}

	public Exchange findExchange(int a){
		if(root == null)
			return null;
		if(root.id == a)
			return root;
		for (int i = 0; i < root.numChildren(); i++) {
			RoutingMapTree t = root.subtree(i);
			Exchange e=t.findExchange(a);
			if(e!=null)
				return e;
		}
		return null;
	}

	//switches On the mobilePhone numbered a
	public void switchOn(MobilePhone a,Exchange b){
		
		if(b == null)
			throw new NoSuchElementException();

		//location of a to b
		a.location = b;
		a.status = true; 

		//putting 'a' in mobilephoneset of every ancestor
		while(b != null ){
			b.residentSet().MobileInsert(a);
			b = b.parent();
		}
		if(switchedOffPhones.IsMember(a.mobnum) == true)
			switchedOffPhones.Delete(a.mobnum);
	}                                                                                  

	public void switchOff(MobilePhone a){	

		switchedOffPhones.Insert(a.mobnum);

		Exchange b = a.location();
		b.residentSet().MobileDelete(a);
		while(b.parent() != null){
			b = b.parent();
			b.residentSet().MobileDelete(a);
		}
		
	}

	public void movePhone( MobilePhone a, Exchange b){
		switchOff(a);
		switchOn(a,b);
	}

	public Exchange findPhone(MobilePhone m){

		return m.location;
	}

	//Checking if tree contains node 'a'
	public boolean isNodePresent(Exchange a){
		if(a == null || root == null)
			return false;
		
		if (root.equals(a)) {
			return true;
		}
		boolean temp = false;
		if(root.numChildren() == 0)
			return false;
		for (int i = 0; i < root.numChildren(); i++) {
			RoutingMapTree t = root.subtree(i);
			temp = t.isNodePresent(a);
			if (temp == true) 
				return true;
		}
		return false;
	}

	//returns the mobilePhone which has number = a
	public MobilePhone findMobile(int a){
		return root.residentSet().findMobileInSet(a);
	} 
	
	public Exchange lowestRouter(Exchange a, Exchange b){
	    ExchangeList aAncestors = new ExchangeList();
		ExchangeList bAncestors = new ExchangeList();
		Exchange temp = a;//a is an ancestor of itself
		while(temp.parent() != null){
			aAncestors.listAddMember(temp);
			temp = temp.parent();
		}
		aAncestors.listAddMember(root);
		temp = b;
		while(temp.parent() != null){
			bAncestors.listAddMember(temp);
			temp = temp.parent();
		}
		bAncestors.listAddMember(root);
		return aAncestors.firstCommonExchange(bAncestors);
	}
	
	//list of call path
	public ExchangeList routeCall(MobilePhone a, MobilePhone b){
	    Exchange A = a.location;
	    Exchange B = b.location;

	    ExchangeList callPath = new ExchangeList();
		Exchange temp = A;//a is an sncestor of itself
		while(temp != lowestRouter(A,B) ){
			callPath.listAddMember(temp);
			temp = temp.parent;
		}

		callPath.listAddMember(lowestRouter(A,B));
		temp = lowestRouter(A,B);
		for (int i = 0; i < temp.numChildren(); i++) {
			if(temp.subtree(i).isNodePresent(B)){
				callPath.listAddMember(temp.subtree(i).root);
				temp = temp.subtree(i).root;
			}
		}
		callPath.listAddMember(B);

		return callPath;

	}

	public String performAction(String actionMessage){
		
		String res = "";
		String[] w = actionMessage.split(" ");
		//addExchange make b the child of a
		if (w[0].equals("addExchange")) {
			int a,b;
			
			a = Integer.parseInt(w[1]);
			b = Integer.parseInt(w[2]);

			try{
				addExchange(b,a);
			} 
			catch(NoSuchElementException e){
				res = res + "addExchange: Error - No exchange with identifier "+a+" found in the network";
			}
			catch(IllegalArgumentException ea){
				res = res + "addExchange: Error - No exchange with identifier "+b+" found in the network";
			}
		}
		
		//switchOnMobile
		else if (w[0].equals("switchOnMobile")) {
			int a = Integer.parseInt(w[1]);
			int b = Integer.parseInt(w[2]);

			Exchange B = findExchange(b);
			
			if(findMobile(a) != null){
				res = "switchOnMobile: Error - There is already a mobile with number "+a+" in the network.";
			}
			else if(switchedOffPhones.IsMember(a))
				switchedOffPhones.Delete(a);
			else if(findMobile(a) == null){
				MobilePhone A = new MobilePhone(a);
				A.switchOn();
				try{
				switchOn(A,B);
				}
				catch(NoSuchElementException e){
					res = "switchOnMobile: Error - No exchange with identifier "+b+" found in the network";
				}

			}
							
		}

		//switchOffMobile
		else if (w[0].equals("switchOffMobile")) {
			int a = Integer.parseInt(w[1]);

			if(switchedOffPhones.IsMember(a))
				res = "This phone is already switched off.";
			else if(findMobile(a) == null)
				res = "switchOffMobile: Error - No mobile Phone with identifier "+a+" found in the network";
			
			else switchOff(findMobile(a));
		}
		
		//queryNthChild bth child of a
		else if (w[0].equals("queryNthChild")) {
			int a = Integer.parseInt(w[1]);
			int b = Integer.parseInt(w[2]);

			try{
				res = "queryNthChild "+a+" "+b+": "+ findExchange(a).childList().listNthChild(b).id ;
			}
			catch(NoSuchElementException e){
				res = "queryNthChild: Error - No exchange with identifier "+a+" found in the network";
			}
			catch(NullPointerException ea){
				res = "queryNthChild: Error - "+b+"th child of Exchange "+a+" does not exist";
			}
		}
	
		//queryMobilePhoneSet
		else if(w[0].equals("queryMobilePhoneSet")) {
			int a = Integer.parseInt(w[1]);

			if(findExchange(a) == null)
				res = "queryMobilePhoneSet: Error - No exchange with identifier "+a+" found in the network";				
			else{
				try{
					res = findExchange(a).printAllMobiles( a );
				}
				catch(NoSuchElementException e){
					res = "queryMobilePhoneSet: Error - No exchange with identifier "+a+" found in the network";
				}
			}
		}

		//queryfindPhone with number a
		else if(w[0].equals("findPhone")){
			int a = Integer.parseInt(w[1]);
			if(switchedOffPhones.IsMember(a))
				res = "queryfindPhone "+a+": Error - Mobile phone with identifier "+a+" is currently switched off";
			
			else try{
				res = "queryFindPhone "+a+": "+findPhone(findMobile(a)).id;
			}
			catch(NoSuchElementException e){
				res = "queryFindPhone "+a+": Error - No mobile phone with identifier "+a+" found in the network";
			}
			catch(NullPointerException ae){
				res = "queryFindPhone "+a+": Error - No mobile phone with identifier "+a+" found in the network";
			}
		}

		//movePhone of a to b
		else if(w[0].equals("movePhone")){
			int a = Integer.parseInt(w[1]);
			int b = Integer.parseInt(w[2]);
			if(findExchange(b) == null)
				res = "movePhone: Error - No exchange with identifier "+b+" found in the network";
			if(findMobile(a) == null)
				res = "movePhone: Error - No mobile phone with identifier "+a+" found in the network";
			if(switchedOffPhones.IsMember(a))
				res = "movePhone: Error - mobile phone with identifier "+a+" is currently switched off";
			else movePhone(findMobile(a),findExchange(b));

		}

	    //querylowestrouter
	   	else if (w[0].equals("lowestRouter")) {
			int a = Integer.parseInt(w[1]);
			int b = Integer.parseInt(w[2]);
			
			if(findExchange(a) == null)
				res = "queryLowestRouter: Error - No exchange with identifier "+a+" found in the network";
			if(findExchange(b) == null)
				res = "queryLowestRouter: Error - No exchange with identifier "+b+" found in the network";
			
			else res = "queryLowestRouter "+a+" "+b+": "+lowestRouter(findExchange(a),findExchange(b)).id;
	   		
	   	}

	   	//queryFindCallPath
	   	else if (w[0].equals("findCallPath")) {
			int a = Integer.parseInt(w[1]);
			int b = Integer.parseInt(w[2]);
			if (switchedOffPhones.IsMember(a))
				res = "queryFindCallPath "+a+" "+b+": Error - Mobile phone with identifier "+a+" is currently switched off";		
			if (switchedOffPhones.IsMember(b))
				res = "queryFindCallPath "+a+" "+b+": Error - Mobile phone with identifier "+b+" is currently switched off";
			
			else if(findMobile(a) == null)
				res = "queryFindCallPath "+a+" "+b+": Error - No mobile phone with identifier "+a+" found in the network";
			else if(findMobile(b) == null)
				res = "queryFindCallPath "+a+" "+b+": Error - No mobile phone with identifier "+b+" found in the network";
		
			else res = res + routeCall(findMobile(a),findMobile(b)).printAllExchanges(a,b);
	   	}
	   	System.out.println(res);
		return res;
	}
}
