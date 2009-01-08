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
package jmud.test.statics;

import jmud.engine.core.JMudStatics;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class SplashScreenTest{

    private static final String TEST_SPLASH_SCREEN_FILE = "testSplashScreen.txt";
    private static final String NON_EXISTANT_SPLASH_SCREEN_FILE = UUID.randomUUID().toString() + ".txt";
    private static final String TEST_SPLASH_SCREEN = "Test\nsplash\nscreen\n_";

    @Test
    public void testDefaultSplashScreenExists(){
        reloadSplashScreenFromFile(NON_EXISTANT_SPLASH_SCREEN_FILE);
        Assert.assertNotNull("No default splash screen found.", JMudStatics.getSplashScreen());
    }

    @Test
    public void testSplashScreenFromFile(){
        String splashScreen;
        try{
            writeSplashScreenFile();
        }catch(Exception e){
            Assert.fail();
        }
        reloadSplashScreenFromFile(TEST_SPLASH_SCREEN_FILE);
        splashScreen = JMudStatics.getSplashScreen();

        Assert.assertNotNull("Splash screen was null; expected " + TEST_SPLASH_SCREEN, splashScreen);
        Assert.assertEquals("Expected splash screen not found; expected " + TEST_SPLASH_SCREEN + ", found " + splashScreen, TEST_SPLASH_SCREEN, splashScreen);
    }

    private void reloadSplashScreenFromFile(String fileName) {
        JMudStatics.reloadSplashScreenFromFile(fileName);
    }

    private void writeSplashScreenFile() throws IOException {
        BufferedWriter bufferedWriter = null;

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(TEST_SPLASH_SCREEN_FILE));
            bufferedWriter.write(TEST_SPLASH_SCREEN);
        } finally {
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        }
    }
}
