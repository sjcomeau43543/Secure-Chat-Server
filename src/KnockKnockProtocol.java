/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

/*
 * Copied from https://docs.oracle.com/javase/tutorial/networking/sockets/examples/KnockKnockProtocol.java
 * by Samantha Jo Comeau, WPI, sjcomeau@wpi.edu - 05MAR2017
 * by Tim Schmoyer, MITRE, schmoyert@mitre.org - 06MAR2017
 */

import java.util.*;

public class KnockKnockProtocol {
    private static final int WAITING = 0;
    private static final int SENTKNOCKKNOCK = 1;
    private static final int SENTCLUE = 2;
    private static final int ANOTHER = 3;
 
    private static final int NUMJOKES = 5;
    
    private static final int SENTWHOSTHERE = 6;
    private static final int SENTWHO = 7;
 
    private int stateServer = WAITING;
    private int stateClient = WAITING;

    // Modified to randomly select joke
    Random rand = new Random();
    private int currentJoke = rand.nextInt(NUMJOKES);
 
    private String[] clues = { "Turnip", "Little Old Lady", "Atch", "Who", "Who" };
    private String[] answers = { "Turnip the heat, it's cold in here!",
                                 "I didn't know you could yodel!",
                                 "Bless you!",
                                 "Is there an owl in here?",
                                 "Is there an echo in here?" };
    
    
 
    public String processInputServer(String theInput) {
        String theOutput = null;
 
        if (stateServer == WAITING) {
            theOutput = "Knock! Knock!";
            stateServer = SENTKNOCKKNOCK;
        } else if (stateServer == SENTKNOCKKNOCK) {
            if (theInput.equalsIgnoreCase("Who's there?")) {
                theOutput = clues[currentJoke];
                stateServer = SENTCLUE;
            } else {
                theOutput = "You're supposed to say \"Who's there?\"! " +
                "Try again. Knock! Knock!";
            }
        } else if (stateServer == SENTCLUE) {
            if (theInput.equalsIgnoreCase("Who?")) {
                theOutput = answers[currentJoke] + " Want another? (y/n)";
                stateServer = ANOTHER;
            } else {
                theOutput = "You're supposed to say \""+ 
                "Who?\"" + 
                "! Try again.";
                stateServer = SENTKNOCKKNOCK;
            }
        } else if (stateServer == ANOTHER) {
            if (theInput.equalsIgnoreCase("y")) {
                theOutput = "Knock! Knock!";
                if (currentJoke == (NUMJOKES - 1))
                    currentJoke = 0;
                else
                    currentJoke++;
                stateServer = SENTKNOCKKNOCK;
            } else {
                theOutput = "Bye.";
                stateServer = WAITING;
            }
        }
        return theOutput;
    }
    
    public String processInputClient(String theInput) {
        String theOutput = null;
 
        if (stateClient == WAITING) {
            theOutput = "Who's there?";
            stateClient = SENTWHOSTHERE;
        } else if (stateClient == SENTWHOSTHERE) {
            theOutput = "Who?";
            stateClient = SENTWHO;
        // Modified to only run one joke at a time
        } else {
            theOutput = "n";
        }
        return theOutput;
    }
}
