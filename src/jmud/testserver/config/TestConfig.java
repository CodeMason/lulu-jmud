package jmud.testserver.config;

/**
 * 25NOV08: TestConfig is used to configure items that are specific to the TestMud
 * 
 * Created on 25NOV08
 */

import jmud.engine.core.Namespace;
import jmud.engine.stats.SimpleStatDef;
import jmud.testserver.stats.definitions.HitPoint_StatDef;

public class TestConfig {

	public static void doSpecificConfig() {

		/*
		 * Right now, use the basic StatDef template BUT we can define custom
		 * Definitions later
		 */

		
		// Basic Attributes
		SimpleStatDef myStat = null;
		myStat = new SimpleStatDef("Strength", Namespace.Character);
		myStat.selfRegister();

		myStat = new SimpleStatDef("Dexterity", Namespace.Character);
		myStat.selfRegister();

		myStat = new SimpleStatDef("Wisdom", Namespace.Character);
		myStat.selfRegister();

		myStat = new SimpleStatDef("Intelligence", Namespace.Character);
		myStat.selfRegister();

		myStat = new SimpleStatDef("Constitution", Namespace.Character);
		myStat.selfRegister();

		myStat = new SimpleStatDef("Charisma", Namespace.Character);
		myStat.selfRegister();

		// Note the use of a custom StatDef!
		HitPoint_StatDef hpStat = new HitPoint_StatDef("HitPoints", Namespace.Character);
		hpStat.add(Namespace.Item);
		hpStat.selfRegister();
		
		myStat = new SimpleStatDef("ManaPoints", Namespace.Character);
		myStat.selfRegister();

		myStat = new SimpleStatDef("StaminaPoints", Namespace.Character);
		myStat.selfRegister();

		//
		myStat = new SimpleStatDef("ExperiencePoints", Namespace.Character);
		myStat.selfRegister();

		myStat = new SimpleStatDef("ReputationPoints", Namespace.Character);
		myStat.selfRegister();

		
		
	//
		myStat = new SimpleStatDef("Weight", Namespace.Item);
		myStat.selfRegister();

		myStat = new SimpleStatDef("Max_BULK", Namespace.ItemContainer);
		myStat.selfRegister();
		
		myStat = new SimpleStatDef("Current_BULK", Namespace.Item);
		myStat.selfRegister();

		myStat = new SimpleStatDef("Min_BULK", Namespace.ItemContainer);
		myStat.selfRegister();

		myStat = new SimpleStatDef("Wearable", Namespace.ItemContainer);
		myStat.selfRegister();
		
		myStat = new SimpleStatDef("Removeable", Namespace.Item);
		myStat.selfRegister();

		myStat = new SimpleStatDef("IsMagic", Namespace.Item);
		myStat.selfRegister();

		myStat = new SimpleStatDef("Condition", Namespace.Item);
		myStat.selfRegister();

		

		return;
	}

}
