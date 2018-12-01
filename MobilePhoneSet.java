import java.util.*;

public class MobilePhoneSet {
	
	Myset set1 = new Myset();
	
	public boolean MobileIsEmpty() {
		return set1.IsEmpty();
	}
	
	public boolean MobileIsMember(MobilePhone m) {
		return set1.IsMember(m);
	}
	
	public void MobileInsert(MobilePhone m) {
		set1.Insert(m);
	}
	
	public void MobileDelete(MobilePhone m) {
		set1.Delete(m);
	}
	
	public MobilePhoneSet MobileUnion(MobilePhoneSet a) {
		MobilePhoneSet s = new MobilePhoneSet();
		s.set1 = set1.Union(a.set1);
		return s;
	}
	
	public MobilePhoneSet MobileIntersection(MobilePhoneSet a) {
		MobilePhoneSet s = new MobilePhoneSet();
		s.set1 = set1.Intersection(a.set1);
		return s;
	}

 	public int numberOfMobiles(){
 		return set1.numberOfObjects();
 	}

 	public MobilePhone nthMobile(int n){
 		return (MobilePhone)(set1.nthObject(n));
 	}

 	public MobilePhone findMobileInSet(int n){
 		for (int i = 0; i < numberOfMobiles(); i++) {
 			if(nthMobile(i).mobileNumber() == n)
 				return nthMobile(i);
 		}
 		return null;
 	}

 	public String printAll(int n){
 		String res;

 		res = "queryMobilePhoneSet "+ n +": ";
 		for (int i = 0; i < numberOfMobiles(); i++) {
 			res = res + nthMobile(i).mobileNumber();
 			if(i < numberOfMobiles() - 1)
				res = res + ", ";
 		}
 		return res;
 	}
}
