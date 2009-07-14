/***************************************************************************
 *                    (C) Copyright 2003-2009 - Stendhal                   *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.core.engine.db;

import games.stendhal.server.entity.player.Player;

import java.io.IOException;
import java.sql.SQLException;

import marauroa.common.game.RPObject;
import marauroa.server.db.DBTransaction;
import marauroa.server.game.db.CharacterDAO;
import marauroa.server.game.db.DAORegister;

import org.apache.log4j.Logger;

/**
 * Stendhal epecific extensions to the normal CharacterDAO which will update
 * the redundant tables for the web application.
 */
public class StendhalCharacterDAO extends CharacterDAO {
	private static Logger logger = Logger.getLogger(StendhalCharacterDAO.class);

	@Override
	public void addCharacter(final DBTransaction transaction, final String username,
			final String character, final RPObject player) throws SQLException, IOException {

		super.addCharacter(transaction, username, character, player);

		// Here goes the stendhal specific code.
		try {
			final Player instance = (Player) player;
			DAORegister.get().get(StendhalWebsiteDAO.class).insertIntoCharStats(transaction, instance);
		} catch (final SQLException sqle) {
			logger.warn("error storing character", sqle);
			throw sqle;
		}
	}

	
	@Override
	public void storeCharacter(final DBTransaction transaction, final String username,
			final String character, final RPObject player) throws SQLException, IOException {

		super.storeCharacter(transaction, username, character, player);

		// Here goes the stendhal specific code.
		try {
			Player instance = (Player) player;
			final int count = DAORegister.get().get(StendhalWebsiteDAO.class).updateCharStats(transaction, instance);
			if (count == 0) {
				instance = (Player) player;
				DAORegister.get().get(StendhalWebsiteDAO.class).insertIntoCharStats(transaction, instance);
			}
		} catch (final SQLException sqle) {
			logger.warn("error storing character", sqle);
			throw sqle;
		}
	}
}
