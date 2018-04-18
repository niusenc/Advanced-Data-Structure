import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
class minheap{

	public ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
	public  void insert(int jobID, int exctTime,int totalTime){
		ArrayList<Integer> listion = new ArrayList<Integer>();
		listion.add(jobID);
		listion.add(exctTime);
        listion.add(totalTime);
		list.add(listion);
		fixUp(list.size()-1);
	}
 	private void fixUp(int i) {
        if (i <= 0 || i >= list.size())
            return;
        for (int j = parentIndex(i);
                j >= 0 && list.get(j).get(1) > list.get(i).get(1);
                i = j, j = parentIndex(j)) {
            swap(i, j);
        }
    }
     private int parentIndex(int index) {
        return (index - 1) >> 1;
    }
     private void swap(int a, int b) {
        ArrayList<Integer> tmp = list.get(a);
        list.set(a, list.get(b));
        list.set(b, tmp);
    }
    private int leftSon(int index) {
        return (index << 1) + 1;
    }
    private int rightSon(int index) {
        return (index << 1) + 2;
    }
 
     public ArrayList<Integer> pop() {
        if (list.isEmpty())
            return null;
        ArrayList<Integer> res = list.get(0);
        swap(0, list.size()-1);
        list.remove(list.size()-1);
        fixDown(0);
        return res;
    }
     private void fixDown(int i) {
        int son = leftSon(i);
        while (son < list.size()) {
            if (son + 1 < list.size() && list.get(son + 1).get(1) < (list.get(son).get(1)))
                son += 1;
            if (list.get(i).get(1) <= (list.get(son).get(1)))
                break;
            swap(i, son);
            i = son;
            son = leftSon(i);
        }
    }

    public int size() {
        return list.size();
    }
}
