package jmud.engine.event;

public class AffectRange {
	private int parentHeight;
	private int childDepth;

	public AffectRange() {
		this(1, 1);
	}

	public AffectRange(int parentHeight, int childDepth) {
		super();
		this.childDepth = childDepth;
		this.parentHeight = parentHeight;
	}

	public int getParentHeight() {
		return parentHeight;
	}

	public int getChildDepth() {
		return childDepth;
	}

	
	/*
	 * Helpers
	 */
	
	public static AffectRange makeSingleTargetChildrenAffectedPR(){
		return new AffectRange(0,Integer.MAX_VALUE);
	}
	public static AffectRange makeSingleTargetChildrenNotAffectedPR(){
		return new AffectRange(0,0);
	}
	public static AffectRange makeAOEChildrenAffectedPR(){
		return new AffectRange(1,Integer.MAX_VALUE);
	}
	public static AffectRange makeAOEChildrenNotAffectedPR(){
		return new AffectRange(1,0);
	}
}
