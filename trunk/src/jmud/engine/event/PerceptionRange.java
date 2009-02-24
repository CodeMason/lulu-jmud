package jmud.engine.event;

public class PerceptionRange {
	private int parentHeight;
	private int childDepth;

	public PerceptionRange() {
		this(1, 1);
	}

	public PerceptionRange(int parentHeight, int childDepth) {
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
	
	public static PerceptionRange makeSingleTargetChildrenAffectedPR(){
		return new PerceptionRange(0,Integer.MAX_VALUE);
	}
	public static PerceptionRange makeSingleTargetChildrenNotAffectedPR(){
		return new PerceptionRange(0,0);
	}
	public static PerceptionRange makeAOEChildrenAffectedPR(){
		return new PerceptionRange(1,Integer.MAX_VALUE);
	}
	public static PerceptionRange makeAOEChildrenNotAffectedPR(){
		return new PerceptionRange(1,0);
	}
}
