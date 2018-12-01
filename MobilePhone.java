import java.util.*;

public class MobilePhone {
		
	int mobnum;
	boolean status;
	Exchange location;

	MobilePhone(int number){
		mobnum = number;
	}
	
	public int mobileNumber() {
		return mobnum;
	}
	
	public Boolean status() {
		return status;
	}
	
	public void switchOn() {
		status = true;
	}
	
	public void switchOff() {
		status = false;
	}
	
	public Exchange location() {
		if(status == false) 
			throw new IllegalArgumentException();

		if (status == true);
			return location;
	}
	public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        MobilePhone that = (MobilePhone) y;
        int n=this.mobnum;
        if(n!=that.mobnum)
          return(false);
        else
          return true;
    }
}
