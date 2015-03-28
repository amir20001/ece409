package com.security;

import java.math.BigInteger;

public class Generation {

	BigInteger p;
	BigInteger q;
	BigInteger g;
	BigInteger publicKey;
	BigInteger privateKey;

	public Generation() {
		p = new BigInteger(
				"16819938870120985392012908511330240702317396271716022919731854548482310101838"
						+ "67243519643163012786421435674358104484724658871432229345451549430057142651244452442479"
						+ "88777471773193847131514083030740407543233616696550197643519458134465700691569680905568"
						+ "000063025830089599260400096259430726498683087138415465107499");
		q = new BigInteger("959452661475451209325433595634941112150003865821");
		g = new BigInteger(
				"94389192776327398589845326980349814526433869093412782345430946059206568804005"
						+ "18160085582590614296727187254837587773894987581254043322344496846135078946138504377502"
						+ "99639006381231834351335372621529733554984329953645051389125697558596236498663751353531"
						+ "79362670798771770711847430626954864269888988371113567502852");
		privateKey = new BigInteger("432398415306986194693973996870836079581453988813");
		publicKey = new BigInteger("49336018324808093534733548840411752485726058527829630668967480568854756416567"
				+ "4962162949190519101486861866227068697023216644650947032473686465068210152903024809904501302806"
				+ "1692922691724625514706329230172429768068340125863618218559912413117007754845075429408372888507"
				+ "5516985144944984920010138492897272069257160");
		System.out.println(verifyValues());

	}

	public boolean verifyValues() {
		if (p.bitLength() != 1024) {
			return false;
		} else if (q.bitLength() != 160) {
			return false;
		} else if (!g.modPow(q, p).equals(new BigInteger("1"))) {
			return false;
		}else if (!g.modPow(privateKey, p).equals(publicKey)){
			return false;
		}
		return true;
	}

	public BigInteger getP() {
		return p;
	}

	public void setP(BigInteger p) {
		this.p = p;
	}

	public BigInteger getQ() {
		return q;
	}

	public void setQ(BigInteger q) {
		this.q = q;
	}

	public BigInteger getG() {
		return g;
	}

	public void setG(BigInteger g) {
		this.g = g;
	}

	public BigInteger getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(BigInteger publicKey) {
		this.publicKey = publicKey;
	}

	public BigInteger getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(BigInteger privateKey) {
		this.privateKey = privateKey;
	}

}
