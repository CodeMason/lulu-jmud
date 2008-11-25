package jmud.testserver.config;

/**
 * 25NOV08: TestConfig is used to configure items that are specific to the TestMud
 * 
 * Created on 25NOV08
 */

import jmud.engine.character.Character;
import jmud.engine.item.Item;
import jmud.engine.item.ItemContainer;
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
		myStat = new SimpleStatDef("Strength");
		myStat.selfRegister();
		Character.statsDefs.put(myStat.getName(), myStat);

		myStat = new SimpleStatDef("Dexterity");
		myStat.selfRegister();
		Character.statsDefs.put(myStat.getName(), myStat);

		myStat = new SimpleStatDef("Wisdom");
		myStat.selfRegister();
		Character.statsDefs.put(myStat.getName(), myStat);

		myStat = new SimpleStatDef("Intelligence");
		myStat.selfRegister();
		Character.statsDefs.put(myStat.getName(), myStat);

		myStat = new SimpleStatDef("Constitution");
		myStat.selfRegister();
		Character.statsDefs.put(myStat.getName(), myStat);

		myStat = new SimpleStatDef("Charisma");
		myStat.selfRegister();
		Character.statsDefs.put(myStat.getName(), myStat);

		// Note the use of a custom StatDef!
		HitPoint_StatDef hpStat = new HitPoint_StatDef("HitPoints");
		hpStat.selfRegister();
		Character.statsDefs.put(hpStat.getName(), hpStat);
		Item.statsDefs.put(hpStat.getName(), hpStat);
		
		myStat = new SimpleStatDef("ManaPoints");
		myStat.selfRegister();
		Character.statsDefs.put(myStat.getName(), myStat);

		myStat = new SimpleStatDef("StaminaPoints");
		myStat.selfRegister();
		Character.statsDefs.put(myStat.getName(), myStat);

		//
		myStat = new SimpleStatDef("ExperiencePoints");
		myStat.selfRegister();
		Character.statsDefs.put(myStat.getName(), myStat);

		myStat = new SimpleStatDef("ReputationPoints");
		myStat.selfRegister();
		Character.statsDefs.put(myStat.getName(), myStat);

		
		
	//
		myStat = new SimpleStatDef("Weight");
		myStat.selfRegister();
		Item.statsDefs.put(myStat.getName(), myStat);

		myStat = new SimpleStatDef("Max_BULK");
		myStat.selfRegister();
		ItemContainer.statsDefs.put(myStat.getName(), myStat);
		
		myStat = new SimpleStatDef("Current_BULK");
		myStat.selfRegister();
		Item.statsDefs.put(myStat.getName(), myStat);

		myStat = new SimpleStatDef("Min_BULK");
		myStat.selfRegister();
		ItemContainer.statsDefs.put(myStat.getName(), myStat);

		myStat = new SimpleStatDef("Wearable");
		myStat.selfRegister();
		Item.statsDefs.put(myStat.getName(), myStat);

		myStat = new SimpleStatDef("Removeable");
		myStat.selfRegister();
		Item.statsDefs.put(myStat.getName(), myStat);

		myStat = new SimpleStatDef("IsMagic");
		myStat.selfRegister();
		Item.statsDefs.put(myStat.getName(), myStat);

		myStat = new SimpleStatDef("Condition");
		myStat.selfRegister();
		Item.statsDefs.put(myStat.getName(), myStat);

		

		return;
	}

}
