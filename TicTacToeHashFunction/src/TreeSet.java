//Might not need this
public class TreeSet {
	private TreeNode root;
	
	public TreeSet() { root = null; }
	
	public boolean contains(Object value) { return contains(root, value); }
	public boolean add(Object value) {
		if(contains(value) )
			return false;
		root = add(root, value);
		return true;
	}
	
	//UNUSED
	/* public boolean remove(Object value) {
		if( !contains(value) )
			return false;
		root = remove(root, value);
		return true;
	} */
	
	public String toString() {
		String result = toString(root);
		if(result.endsWith(", ") )
			result = result.substring(0, result.length() -2);
		return "[" + result + "]";
	}
	
	// HELPERS //
	private boolean contains(TreeNode node, Object value) {
		if(node == null)
			return false;
		else {
			int diff = ((String) value).compareTo((String) node.getValue() );
			if(diff == 0)
				return true;
			else if(diff < 0 )
				return contains(node.getLeft(), value);
			else
				return contains(node.getRight(), value);
		}
	}
	
	private TreeNode add(TreeNode node, Object value) {
		if(node == null) 
			node = new TreeNode(value);
		else {
			int diff = ((String) value).compareTo((String) node.getValue() );
			if(diff < 0)
				node.setLeft(node.getLeft());
			else
				node.setRight(node.getRight());
		}
		return node;
	}
	
	/* UNUSED
	private TreeNode remove(TreeNode node, Object value) {
		int diff = ((Comparable<Object>) value).compareTo(node.getValue() );
		if(diff == 0 )
			node = null; //removeRoot(node);
		else if(diff < 0 )
			node.setLeft(remove( node.getLeft(), value ) );
		else 
			node.setRight(remove( node.getRight(), value ) );
		return node;
	} */
	
	private String toString(TreeNode node) {
		if (node == null) 
			return "";
		else 
			return toString(node.getLeft()) + node.getValue() + ", " + toString(node.getRight());
	}
	

}
