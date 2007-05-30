/*
 * @(#) src/games/stendhal/server/entity/area/AreaEntity.java
 *
 * $Id$
 */

package games.stendhal.server.entity.area;

//
//

import java.awt.geom.Rectangle2D;

import marauroa.common.game.RPClass;
import games.stendhal.server.entity.PassiveEntity;

/**
 * A base area entity.
 */
public abstract class AreaEntity extends PassiveEntity {
	/**
	 * The RPClass.
	 */
	public final static RPClass	RPCLASS		= createRPClass();

	/**
	 * The height.
	 */
	protected int height;

	/**
	 * The width
	 */
	protected int width;


	/**
	 * Create an area entity.
	 *
	 * @param	width		Width of this area
	 * @param	height		Height of this area
	 */
	public AreaEntity(int width, int height) {
		put("type", "area");

		this.height = height;
		this.width = width;

		put("width", width);
		put("height", height);
	}


	//
	// AreaEntity
	//

	/**
	 * Define the RPClass.
	 *
	 * NOTE: Same thing [almost] as generateRPClass(), but incompatible
	 * method signature.
	 *
	 * @return	The configured RPClass.
	 */
	protected static RPClass createRPClass() {
		RPClass rpclass = new RPClass("area");

		// MAYBEDO: rpclass.isA(Entity.RPCLASS)
		rpclass.isA("entity");

		rpclass.add("height", RPClass.SHORT);
		rpclass.add("width", RPClass.SHORT);

		return rpclass;
	}


	//
	// Entity
	//

	/**
	 * Get the entity's area.
	 *
	 * @param	rect		The rectangle to fill in.
	 * @param	x		The X coordinate.
	 * @param	y		The Y coordinate.
	 */
	@Override
	public void getArea(Rectangle2D rect, double x, double y) {
		rect.setRect(x, y, width, height);
	}


	/**
	 * Handle object attribute change(s).
	 */
	@Override
	public void update() {
		super.update();

		if(has("height")) {
			height = getInt("height");
		}

		if(has("width")) {
			width = getInt("width");
		}
	}
}
