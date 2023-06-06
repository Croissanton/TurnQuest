package com.gdx.turnquest.entities;

import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a clan in the game.
 * It contains the name of the clan, the members of the clan and the leader of the clan.
 * It also contains the getters and setters for the attributes.
 * It is used in the ClanScreen class.
 *
 * @author Pablo
 * @author Cristian
 */
public class Clan {

    private String name;
    private List<String> members;
    private String leader;

    public Clan () {
        name = "";
        members = new LinkedList<>();
        leader = "";
    }
    public Clan (String clanName, String clanLeader) {
        name = clanName;
        members = new LinkedList<>();
        members.add(clanLeader);
        leader = clanLeader;
    }

    public String getName () {
        return name;
    }

    public void setName (String clanName) {
        name = clanName;
    }

    public List<String> getMembers () {
        return members;
    }

    public void addMember (String playerName) {
        members.add(playerName);
    }

    public void removeMember (String playerName) {
        members.remove(playerName);
    }

    public String getLeader () {
        return leader;
    }
}
