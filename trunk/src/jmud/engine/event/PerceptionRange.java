package jmud.engine.event;

public class PerceptionRange {
	private int siblingWidth;
	private int parentHeight;
	private int childDepth;

	public PerceptionRange() {
		this(1, 1, 1);
	}

	public PerceptionRange(int childDepth, int parentHeight, int siblingWidth) {
		super();
		this.childDepth = childDepth;
		this.parentHeight = parentHeight;
		this.siblingWidth = siblingWidth;
	}

	public int getSiblingWidth() {
		return siblingWidth;
	}

	public int getParentHeight() {
		return parentHeight;
	}

	public int getChildDepth() {
		return childDepth;
	}

}
