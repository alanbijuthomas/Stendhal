/***************************************************************************
 *                   (C) Copyright 2003-2013 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.maps.ados.snake_pit;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.action.EquipItemAction;
import games.stendhal.server.entity.npc.action.IncreaseKarmaAction;
import games.stendhal.server.entity.npc.action.MultipleActions;
import games.stendhal.server.entity.npc.condition.NotCondition;
import games.stendhal.server.entity.npc.condition.PlayerHasItemWithHimCondition;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PurpleCrystalNPC implements ZoneConfigurator {
	/**
	 * Configure a zone.
	 *
	 * @param	zone		The zone to be configured.
	 * @param	attributes	Configuration attributes.
	 * 
	 * @author AntumDeluge
	 */
	public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
		buildNPC(zone);
	}

	private void buildNPC(final StendhalRPZone zone) {
		
		// Name
		final String RIDDLE_NAME = "purple_crystal";
		
		// Answers to the riddle
		final List<String> answers = Arrays.asList("fear", "fearful", "fearfullness",
				"fright", "frightened", "afraid", "scared");
		
		// Message to show when player begins conversation
		final String riddle = "I dare not come out and avoid the consequence. They try and convince my but I shall not. Trembling is my favorite activity. What am I?";
		
		// Message when player leaves conversation
		final String goodbyeMessage = "Farewell, return to me when you have found the answer to my riddle.";
		
		// Item given as reward for answering the riddle
		final String rewardItem = "crystal of fear";
		
		// Reward for getting riddle right
		final List<ChatAction> rewardAction = new LinkedList<ChatAction>();
		rewardAction.add(new EquipItemAction(rewardItem, 1, true));
		rewardAction.add(new IncreaseKarmaAction(5.0));
		
		// Amount of time, in minutes, player must wait before retrying the riddle
		final int WAIT_TIME = 24 * 60;
		
		// Create the NPC
		final SpeakerNPC crystal = new SpeakerNPC("Purple Crystal") {

			@Override
			protected void createPath() {
				// NPC doesn't move
				setPath(null);
			}

			@Override
			protected void createDialog() {
				addGreeting(riddle);
				addGoodbye(goodbyeMessage);     
			}
		};

		crystal.setEntityClass("transparentnpc");
		crystal.setAlternativeImage("crystalpurplenpc");
		crystal.setPosition(47, 64);
		crystal.initHP(100);
		crystal.setDescription("You see a purple colored crystal. There is something eerie about it.");
		crystal.setResistance(0);
		
		// Offering a riddle
		crystal.add(ConversationStates.IDLE,
				ConversationPhrases.GREETING_MESSAGES,
				new NotCondition(new PlayerHasItemWithHimCondition(rewardItem)),
				ConversationStates.ATTENDING,
				riddle,
				null);
		
		// Player already has crystal reward
		crystal.add(ConversationStates.IDLE,
				ConversationPhrases.GREETING_MESSAGES,
				new PlayerHasItemWithHimCondition(rewardItem),
				ConversationStates.IDLE,
				"I have nothing left to offer you.",
				null);
		
		// Player says "bye"
		crystal.add(ConversationStates.ATTENDING,
				ConversationPhrases.GOODBYE_MESSAGES,
				null,
				ConversationStates.IDLE,
				goodbyeMessage,
				null);
		
		// Player gets the riddle right
		crystal.add(ConversationStates.ATTENDING,
				answers,
				null,
				ConversationStates.IDLE,
				"That is correct. Take this crystal as a reward",
				new MultipleActions(rewardAction));
		
		// Player gets the riddle wrong
		crystal.add(ConversationStates.ATTENDING,
				"",
				null,
				ConversationStates.IDLE,
				"I'm sorry, that is incorrect.",
				null);
		
		
		zone.add(crystal);
	}
}