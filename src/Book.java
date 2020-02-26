import java.util.ArrayList;

public class Book {
	Integer score;
	int idx;
	ArrayList<Library>libsContainBook ;
	Book(int s,int index) {
		score = s;
		idx = index;
		libsContainBook = new ArrayList<Library>();
	}
	Book(int index) {
		idx = index;
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	public void addLib(Library lib) {
		libsContainBook.add(lib);
	}
	@Override
	public String toString() {
		return "Book["+idx+", "+score+", #libs: "+libsContainBook.size();
	}

	
}