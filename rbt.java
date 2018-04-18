import java.util.NoSuchElementException;

class rbt{
	private static final boolean RED = true;
    private static final boolean BLACK = false;

     Node root; //root of the BST
     public class Node {
         int JobId;            //key
         int exeTime;
         int totalTime;          //associated data
        Node left, right;   //links to left and right subtrees
        boolean color;      //color of parent link
		int size;           //subtree count

        public Node(int JobId, int exeTime, int totalTime, boolean color, int size) {
            this.JobId = JobId;
            this.exeTime = exeTime;
            this.totalTime = totalTime;
            this.color = color;
            this.size = size;
        }
    }

    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }

     public boolean isEmpty() {
        return root == null;
    }

     private int size(Node x) {
        if (x == null) return 0;
        return x.size;
    } 

    public boolean contains(int JobId) {
        return get(JobId) != null;
    }



    public  void put(int JobId, int exeTime, int totalTime) {
        // if (exeTime == null || totalTime == null) {
        //     delete(key);
        //     return;
        // }

        root = put(root, JobId, exeTime, totalTime);
        root.color = BLACK;
        // assert check();
    }

    // insert the key-value pair in the subtree rooted at h
    private Node put(Node h, int JobId, int exeTime, int totalTime) { 
        if (h == null) return new Node(JobId, exeTime, totalTime, RED,1);

        if      (JobId < h.JobId) h.left  = put(h.left,  JobId, exeTime, totalTime); 
        else if (JobId > h.JobId) h.right = put(h.right, JobId, exeTime, totalTime); 
        else {
        		 h.exeTime   = exeTime;
        		 h.totalTime   = totalTime;
        }            

        // fix-up any right-leaning links
        if (isRed(h.right) && !isRed(h.left))      h = rotateLeft(h);
        if (isRed(h.left)  &&  isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left)  &&  isRed(h.right))     flipColors(h);
        h.size = size(h.left) + size(h.right) + 1;

        return h;
    }


    public void delete(int JobId) { 
        if (!contains(JobId)) return;

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = delete(root, JobId);
        if (!isEmpty()) root.color = BLACK;
        // assert check();
    }


     private Node delete(Node h, int JobId) { 
        // assert get(h, key) != null;

        if (JobId < h.JobId)  {
            if (!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = delete(h.left, JobId);
        }
        else {
            if (isRed(h.left))
                h = rotateRight(h);
            if (JobId == h.JobId && (h.right == null))
                return null;
            if (!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            if (JobId == h.JobId) {
                Node x = min(h.right);
                h.JobId = x.JobId;
                h.exeTime = x.exeTime;
                h.totalTime = x.totalTime;
                // h.val = get(h.right, min(h.right).key);
                // h.key = min(h.right).key;
                h.right = deleteMin(h.right);
            }
            else h.right = delete(h.right, JobId);
        }
        return balance(h);
    }

    private Node rotateRight(Node h) {
        // assert (h != null) && isRed(h.left);
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = x.right.color;
        x.right.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }


     private Node rotateLeft(Node h) {
        // assert (h != null) && isRed(h.right);
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = x.left.color;
        x.left.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }


    private void flipColors(Node h) {
        // h must have opposite color of its two children
        // assert (h != null) && (h.left != null) && (h.right != null);
        // assert (!isRed(h) &&  isRed(h.left) &&  isRed(h.right))
        //    || (isRed(h)  && !isRed(h.left) && !isRed(h.right));
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

   private Node moveRedRight(Node h) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);
        flipColors(h);
        if (isRed(h.left.left)) { 
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    private Node deleteMin(Node h) { 
        if (h.left == null)
            return null;

        if (!isRed(h.left) && !isRed(h.left.left))
            h = moveRedLeft(h);

        h.left = deleteMin(h.left);
        return balance(h);
    }

    private Node moveRedLeft(Node h) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);

        flipColors(h);
        if (isRed(h.right.left)) { 
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

     private Node balance(Node h) {
        // assert (h != null);

        if (isRed(h.right))                      h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right))     flipColors(h);

        h.size = size(h.left) + size(h.right) + 1;
        return h;
    }

    public int min() {
        if (isEmpty()) throw new NoSuchElementException("calls min() with empty symbol table");
        return min(root).JobId;
    } 

    // the smallest key in subtree rooted at x; null if no such key
    private Node min(Node x) { 
        // assert x != null;
        if (x.left == null) return x; 
        else                return min(x.left); 
    } 

    public Node get(int JobId) {
        return get(root, JobId);
    }

    // value associated with the given key in subtree rooted at x; null if no such key
    private Node get(Node x, int JobId) {
        while (x != null) {
            if      (JobId < x.JobId) x = x.left;
            else if (JobId > x.JobId) x = x.right;
            else              return x;
        }
        return null;
    }
/*floor function returens predecessor*/
   public int floor(int JobId) {
        //if (key == null) throw new IllegalArgumentException("argument to floor() is null");
        //if (isEmpty()) throw new NoSuchElementException("calls floor() with empty symbol table");
        Node x = floor(root, JobId);
        if (x == null) return 0;
        else           return x.JobId;
    }    

    // the largest key in the subtree rooted at x less than or equal to the given key
    private Node floor(Node x, int JobId) {
        if (x == null) return null;
        //int cmp = key.compareTo(x.key);
        if (JobId == x.JobId) return x;
        if (JobId < x.JobId)  return floor(x.left, JobId);
        Node t = floor(x.right, JobId);
        if (t != null) return t; 
        else           return x;
    }


    public int ceiling(int JobId) {
        //if (key == null) throw new IllegalArgumentException("argument to ceiling() is null");
        //if (isEmpty()) throw new NoSuchElementException("calls ceiling() with empty symbol table");
        Node x = ceiling(root, JobId);
        if (x == null) return 0;
        else           return x.JobId;  
    }

    // the smallest key in the subtree rooted at x greater than or equal to the given key
    private Node ceiling(Node x, int JobId) {  
        if (x == null) return null;
        //int cmp = key.compareTo(x.key);
        if (JobId == x.JobId) return x;
        if (JobId > x.JobId)  return ceiling(x.right, JobId);
        Node t = ceiling(x.left, JobId);
        if (t != null) return t; 
        else           return x;
    }
}