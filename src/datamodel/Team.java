package datamodel;

import java.util.ArrayList;

public class Team {
	public static final int gameNum = 82;
	public int index;
	public String city;
	public String name;
	public String shortName;
	
	ArrayList<Integer> gameIndex = new ArrayList<Integer>();
	
	public int[] overall;
	int[] home;
	int[] road;
	int[] eastOppo;
	int[] westOppo;
	int[] preAllStar;
	int[] postAllStar;
	int[] marginLess3;
	int[] marginMore10;
	
	int[] oct;
	int[] nov;
	int[] dec;
	int[] jan;
	int[] feb;
	int[] mar;
	int[] apr;
	
	public float fg, fga, fgp;
	public float _3p, _3pa, _3pp;
	public float _2p, _2pa, _2pp;
	public float ft, fta, ftp;
	public float orb, drb, trb;
	public float ast, stl, blk, tov, pf;
	public float pts;
	
	public float opfg, opfga, opfgp;
	public float op_3p, op_3pa, op_3pp;
	public float op_2p, op_2pa, op_2pp;
	public float opft, opfta, opftp;
	public float oporb, opdrb, optrb;
	public float opast, opstl, opblk, optov, oppf;
	public float oppts;
	
	
	
	public Team () {
		overall = new int[2];
		home = new int[2];
		road = new int[2];
		eastOppo = new int[2];
		westOppo = new int[2];
		preAllStar = new int[2];
		postAllStar = new int[2];
		marginLess3 = new int[2];
		marginMore10 = new int[2];
		
		oct = new int[2];
		nov = new int[2];
		dec = new int[2];
		jan = new int[2];
		feb = new int[2];
		mar = new int[2];
		apr = new int[2];
	}
	
	public Team (String city, String name, String shortName) {
		this();
		this.city = city;
		this.name = name;
		this.shortName = shortName;
	}
	
	public void setOverall (int[] overall) {
		this.overall[0] = overall[0];
		this.overall[1] = overall[1];
	}
	public int[] getOverall () {
		return overall;
	}
	
	public void setHome (int[] home) {
		this.home[0] = home[0];
		this.home[1] = home[1];
	}
	public int[] getHome () {
		return home;
	}
	
	public void setRoad (int[] road) {
		this.road[0] = road[0];
		this.road[1] = road[1];
	}
	public int[] getRoad () {
		return road;
	}
	
	public void setEastOppo (int[] eastOppo) {
		this.eastOppo[0] = eastOppo[0];
		this.eastOppo[1] = eastOppo[1];
	}
	public int[] getEastOppo () {
		return eastOppo;
	}
	
	public void setWestOppo (int[] westOppo) {
		this.westOppo[0] = westOppo[0];
		this.westOppo[1] = westOppo[1];
	}
	public int[] getWestOppo () {
		return westOppo;
	}
	
	public void setPreAllStar (int[] preAllStar) {
		this.preAllStar[0] = preAllStar[0];
		this.preAllStar[1] = preAllStar[1];
	}
	public int[] getPreAllStart () {
		return preAllStar;
	}
	
	public void setPostAllStar (int[] postAllStar) {
		this.postAllStar[0] = postAllStar[0];
		this.postAllStar[1] = postAllStar[1];
	}
	public int[] getPostAllStart () {
		return postAllStar;
	}
	
	public void setMarginLess3 (int[] marginLess3) {
		this.marginLess3[0] = marginLess3[0];
		this.marginLess3[1] = marginLess3[1];
	}
	public int[] getMarginLess3 () {
		return marginLess3;
	}
	
	public void setMarginMore10 (int[] marginMore10) {
		this.marginMore10[0] = marginMore10[0];
		this.marginMore10[1] = marginMore10[1];
	}
	public int[] getMarginMore10 () {
		return marginMore10;
	}
	
	public void setOct (int[] oct) {
		this.oct[0] = oct[0];
		this.oct[1] = oct[1];
	}
	public int[] getOct () {
		return oct;
	}
	
	public void setNov (int[] nov) {
		this.nov[0] = nov[0];
		this.nov[1] = nov[1];
	}
	public int[] getNov () {
		return nov;
	}
	
	public void setDec (int[] dec) {
		this.dec[0] = dec[0];
		this.dec[1] = dec[1];
	}
	public int[] getDec () {
		return dec;
	}
	
	public void setJan (int[] jan) {
		this.jan[0] = jan[0];
		this.jan[1] = jan[1];
	}
	public int[] getJan () {
		return jan;
	}
	
	public void setFeb (int[] feb) {
		this.feb[0] = feb[0];
		this.feb[1] = feb[1];
	}
	public int[] getFeb () {
		return feb;
	}
	
	public void setMar (int[] mar) {
		this.mar[0] = mar[0];
		this.mar[1] = mar[1];
	}
	public int[] getMar () {
		return mar;
	}
	
	public void setApr (int[] apr) {
		this.apr[0] = apr[0];
		this.apr[1] = apr[1];
	}
	public int[] getApr () {
		return apr;
	}
	
	public ArrayList<Integer> getGameIndex () {
		return gameIndex;
	}
	
	public float[] getAttrValueByType (String type) {
		float[] result = new float[2];
		
		if (type.equals("Points")) {
			result[0] = pts;
			result[1] = oppts;
		} else if (type.equals("Field Goal")) {
			result[0] = fg;
			result[1] = opfg;
		} else if (type.equals("FGA")) {
			result[0] = fga;
			result[1] = opfga;
		} else if (type.equals("FGP%")) {
			result[0] = fgp;
			result[1] = opfgp;
		} else if (type.equals("2-Points")) {
			result[0] = _2p;
			result[1] = op_2p;
		} else if (type.equals("2PA")) {
			result[0] = _2pa;
			result[1] = op_2pa;
		} else if (type.equals("2PP%")) {
			result[0] = _2pp;
			result[1] = op_2pp;
		} else if (type.equals("3-Points")) {
			result[0] = _3p;
			result[1] = op_3p;
		} else if (type.equals("3PA")) {
			result[0] = _3pa;
			result[1] = op_3pa;
		} else if (type.equals("3PP%")) {
			result[0] = _3pp;
			result[1] = op_3pp;
		} else if (type.equals("Free Throw")) {
			result[0] = ft;
			result[1] = opft;
		} else if (type.equals("FTA")) {
			result[0] = fta;
			result[1] = opfta;
		} else if (type.equals("FTP%")) {
			result[0] = ftp;
			result[1] = opftp;
		} else if (type.equals("Rebound")) {
			result[0] = trb;
			result[1] = optrb;
		} else if (type.equals("Offense RB")) {
			result[0] = orb;
			result[1] = oporb;
		} else if (type.equals("Defense RB")) {
			result[0] = drb;
			result[1] = opdrb;
		} else if (type.equals("Assist")) {
			result[0] = ast;
			result[1] = opast;
		} else if (type.equals("Steal")) {
			result[0] = stl;
			result[1] = opstl;
		} else if (type.equals("Block")) {
			result[0] = blk;
			result[1] = opblk;
		} else if (type.equals("Turnover")) {
			result[0] = tov;
			result[1] = optov;
		} else if (type.equals("Personal Foul")) {
			result[0] = pf;
			result[1] = oppf;
		} 
		return result;
	}
	
}

























