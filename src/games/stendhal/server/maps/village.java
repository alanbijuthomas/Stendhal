package games.stendhal.server.maps;

import marauroa.common.game.*;
import games.stendhal.server.*;
import games.stendhal.server.entity.*;
import java.util.*;
import games.stendhal.server.entity.npc.*;
import games.stendhal.server.entity.creature.*;

public class village 
  {
  public village(StendhalRPZone zone)
    {
    Portal portal=new Portal();
    zone.assignRPObjectID(portal);
    portal.setx(16);
    portal.sety(34);
    portal.setNumber(0);
    portal.setDestination("tavern",0);
    zone.add(portal);
    zone.addPortal(portal);

    Sign sign=new Sign();
    zone.assignRPObjectID(sign);
    sign.setx(23);
    sign.sety(61);
    sign.setText("You are about to leave this area and move to the plains.|You may fatten up your sheep there on the wild berries.|Be careful though, wolves roam these plains.");
    zone.add(sign);

    sign=new Sign();
    zone.assignRPObjectID(sign);
    sign.setx(26);
    sign.sety(41);
    sign.setText("Talk to Nishiya to buy a sheep!.|He has the best prices for miles.");
    zone.add(sign);

    sign=new Sign();
    zone.assignRPObjectID(sign);
    sign.setx(60);
    sign.sety(47);
    sign.setText("You are about to leave this area to move to the city.|You can sell your sheep there.");    
    zone.add(sign);
    
    
    SellerNPC npc=new SellerNPC()
      {
      protected void createPath()
        {
        List<Path.Node> nodes=new LinkedList<Path.Node>();
        nodes.add(new Path.Node(33,44));
        nodes.add(new Path.Node(33,42));
        nodes.add(new Path.Node(23,42));
        nodes.add(new Path.Node(23,44));
        setPath(nodes,true);
        }
      };
      
    zone.assignRPObjectID(npc);
    npc.setName("Nishiya");
    npc.setx(33);
    npc.sety(44);
    npc.setbaseHP(100);
    zone.add(npc);    
    zone.addNPC(npc);
    }
  }
