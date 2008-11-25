package jmud.testserver.config;


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

		myStat = new SimpleStatDef("Dexterity");
		myStat.selfRegister();

		myStat = new SimpleStatDef("Wisdom");
		myStat.selfRegister();

		myStat = new SimpleStatDef("Intelligence");
		myStat.selfRegister();

		myStat = new SimpleStatDef("Constitution");
		myStat.selfRegister();

		myStat = new SimpleStatDef("Charisma");
		myStat.selfRegister();

		// Note the use of a custom StatDef!
		HitPoint_StatDef hpStat = new HitPoint_StatDef("HitPoints");
		hpStat.selfRegister();

		myStat = new SimpleStatDef("ManaPoints");
		myStat.selfRegister();

		myStat = new SimpleStatDef("StaminaPoints");
		myStat.selfRegister();

		//
		myStat = new SimpleStatDef("ExperiencePoints");
		myStat.selfRegister();

		myStat = new SimpleStatDef("ReputationPoints");
		myStat.selfRegister();

		myStat = new SimpleStatDef("GoldCoins");
		myStat.selfRegister();

		return;
	}

}
