package jmud.engine.behavior;


/**
 *
 */
public class BehaviorGenerator<T extends AbstractBehavior> {

	private String className;

	/*
	 * Constructors.
	 */
	public BehaviorGenerator(String className) {
		this.className = className;
	}

	@SuppressWarnings("unchecked")
	public T newInstance() {

		try {
			Class<?> c = Class.forName(this.className);
			return (T) c.newInstance();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
