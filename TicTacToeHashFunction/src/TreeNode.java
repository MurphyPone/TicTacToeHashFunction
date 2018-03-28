public class TreeNode {
	private Object value;	
	private TreeNode left;
	private TreeNode right;
	
	public TreeNode(Object v) 
		{ value = v; left = null; right = null;}
	
	public TreeNode(Object v, TreeNode l, TreeNode r) 
		{ value = v; left = l; right = r; }
	
	public Object getValue() { return value; }
	public TreeNode getLeft() { return left; }
	public TreeNode getRight() { return right; }
	
	public void setValue(Object v) { value = v; }
	public void setLeft(TreeNode l) { left = l; }
	public void setRight(TreeNode r) { right = r; }
	
	// ADDED METHODS //	
	
	public boolean hasChildren() { return (getRight() != null || getLeft() != null); }
	
	public TreeNode add(TreeNode node, Object value) {
		if(node == null) //if the Bucket is empty (null)
			node = new TreeNode(value); //Create a new root holding the Board conf
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