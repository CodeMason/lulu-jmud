/**
 * This file is part of Lulu's JMud.
 *
 *  Lulu's JMud is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Lulu's JMud is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Lulu's JMud.  If not, see <http://www.gnu.org/licenses/>.
 */
package jmud.engine.job.definitions;

import jmud.engine.account.Account;
import jmud.engine.character.PlayerCharacter;
import jmud.engine.character.PlayerCharacterManager;
import jmud.engine.netIOx.JMudClient;

/**
 * Just a template. Can be deleted once the Job Repository has sufficient
 * samples to draw from.
 * 
 * @author David Loman
 * @version 0.1
 */

public class LoadCharacterJob extends AbstractClientJob {
	
	private String pcName;
	
	public LoadCharacterJob(JMudClient c, String pcName) {
		super(c);
		this.pcName = pcName;
	}

	@Override
	public final boolean doJob() {
		synchronized (this.c) {
			Account a = this.c.getAccount();
			
			PlayerCharacter pc = PlayerCharacterManager.getInstance().loadPlayerCharacter(this.pcName, a.getAccountID());
			
			if (pc == null) {
				return false;
			} else {
				this.c.setPc(pc);
				return true;
			}
		}
	}
}
