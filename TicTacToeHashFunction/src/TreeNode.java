/**
 * Class Description: The standard TreeNode implementation for a Binary Search Tree copied from my memory --> and the textbook
 * @author MurphyP1
 * @date 3/26/18
 **/
public class TreeNode {
	private String board;
	private boolean isWin;
	private TreeNode left;
	private TreeNode right;
	
	public TreeNode(String v) 
		{ board = v; isWin = false; left = null; right = null;}
	public TreeNode(String v, boolean winner) 
		{ board = v; isWin = winner; left = null; right = null;}

	public TreeNode(String v, boolean winner, TreeNode l, TreeNode r) 
		{ board = v; isWin = winner; left = l; right = r; }
	
	public String getValue() { return board; }
	public boolean getWin() { return isWin; }
	public TreeNode getLeft() { return left; }
	public TreeNode getRight() { return right; }
	
	public void setBoard(String v) { board = v; }
	public void setWinner(boolean w) { isWin = w; }
	public void setLeft(TreeNode l) { left = l; }
	public void setRight(TreeNode r) { right = r; }
	
	// ADDED METHODS //	
	
	public boolean hasChildren() { return (getRight() != null || getLeft() != null); }
	
	public TreeNode add(TreeNode node, Object value) {
		if(node == null) //if the Bucket is empty (null)
			node = new TreeNode((String) value); //Create a new root holding the Board conf
		else {	//else there is already a Board conf there
			int diff = ((String) value).compareTo((String) node.getValue() );	//compare which one is bigger
			if(diff < 0)
				node.setLeft(add(node.getLeft(), value));
			else
				node.setRight(add(node.getRight(), value));
		}
		return node;
	}
}