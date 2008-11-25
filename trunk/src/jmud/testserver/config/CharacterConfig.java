package jmud.testserver.config;

import jmud.engine.config.AbstractConfig;
import jmud.engine.stats.SimpleStatDef;
import jmud.testserver.stats.definitions.HitPoint_StatDef;

public class CharacterConfig extends AbstractConfig {

	@Override
	public boolean doConfig() {

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

		//
		HitPoint_StatDef hpStat = new HitPoint_StatDef("HitPoints");  //Note the use of a custom StatDef!
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


		return true;
	}

}
