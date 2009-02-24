package jmud.engine.object;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import jmud.engine.event.AffectRange;

/**
 * Static class that provides functions to manipulate JMudObjects
 * 
 * @author david.h.loman
 * 
 */
public class JMudObjectUtils {

	/**
	 * Attempts to remove the parent JMudObject reference
	 * 
	 * @param jmo
	 * @return whether or not the orphan action was successful.
	 */
	public static void orphan(JMudObject jmo) {

		// Check to see if it CAN be orphaned
		if (jmo.getJmoRelMap().hasParentObject() == false) {
			return;
		}

		// Get the parent object:
		JMudObject parent = jmo.getJmoRelMap().getParentObject();

		// Remove jmo from it's parent's ChildMap
		parent.getJmoRelMap().remChild(jmo);

		// Now remove the parent from jmo's Parent reference.
		jmo.getJmoRelMap().setParentObject(null);
		return;
	}

	public static void changeParent(JMudObject parent, JMudObject child) {

		// First, orphan the child.
		JMudObjectUtils.orphan(child);

		// Add a child to the parent
		parent.getJmoRelMap().addChild(child);

		// Add a parent to the child
		child.getJmoRelMap().setParentObject(parent);

		return;
	}

	public static Set<JMudObject> getJmosAffected(JMudObject jmo, AffectRange pr) {
		Set<JMudObject> allJmos = new HashSet<JMudObject>();
		getJmosAffected(allJmos, jmo, pr);
		return allJmos;
	}

	public static void getJmosAffected(Set<JMudObject> allJmos, JMudObject jmo, AffectRange pr) {
		// Set the provided jmo as the starting point
		JMudObject top = jmo;

		// Dig our way to the 'top'
		for (int i = 0; i < pr.getParentHeight(); ++i) {
			JMudObject tParent = top.getJmoRelMap().getParentObject();
			if (tParent == null) {
				break; //prevents running past the top of the tree.
			}
			top = tParent;
		}

		// Recurse and grab all the objects
		// Note that ParentHeight is made negative since:
		// parent < jmo (which is level 0) < children
		JMudObjectUtils.addAllChildren(allJmos, top, pr.getChildDepth(), (pr.getParentHeight() * -1));
		return;
	}

	/**
	 * This will drill down and get ALL children below any given point in the
	 * tree.
	 * 
	 * @param masterSet
	 * @param jmo
	 */
	public static Set<JMudObject> getAllChildren(JMudObject jmo) {
		Set<JMudObject> out = Collections.synchronizedSet(new HashSet<JMudObject>());
		JMudObjectUtils.addAllChildren(out, jmo, 0, Integer.MAX_VALUE);
		return out;
	}

	/**
	 * This will drill down and get ALL children below any given point in the
	 * tree to the provided depth
	 * 
	 * @param masterSet
	 * @param jmo
	 */
	public static Set<JMudObject> getAllChildren(JMudObject jmo, int maxDepth) {
		Set<JMudObject> out = Collections.synchronizedSet(new HashSet<JMudObject>());
		JMudObjectUtils.addAllChildren(out, jmo, maxDepth, 0);
		return out;
	}

	private static void addAllChildren(Set<JMudObject> masterSet, JMudObject jmo, int maxDepth, int curDepth) {
		// Get children
		Collection<JMudObject> children = jmo.getJmoRelMap().getAllChildren();

		// Check to see if there ARE any children
		if (children == null || children.size() <= 0) {
			return;
		}

		// Add all the children to the set
		masterSet.addAll(children);

		// Increment the current depth
		++curDepth;

		// If we are at maxDepth, do NOT recurse
		if (curDepth >= maxDepth) {
			return;
		}

		// Recurse on the children set
		for (JMudObject j : children) {
			addAllChildren(masterSet, j, maxDepth, curDepth);
		}
	}

}
