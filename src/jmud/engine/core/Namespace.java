package jmud.engine.core;

/**
 * Provides a means in which to categorize <code>Objects</code>.
 * <code>StatDef</code> objects are a good example as they are stored in
 * <code>Namespace</code> mappings in <code>StatDefRegistrar</code>.
 * @author David Loman
 */

public enum Namespace {

   Character, Item, ItemContainer;
}
