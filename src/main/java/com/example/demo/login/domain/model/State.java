package com.example.demo.login.domain.model;

public enum State {
	STOCK, APPLYING, LENDING, RETURN, RESERVE, PENDING;

/*
	STOCK(0), APPLYING(1), LENDING(2), RETURNING(3), RESERVE(4), PENDING(5);
	private int stateNum;

	State() { this.stateNum = 0; }
	State(int stateNum) {
		if(stateNum > 5) {
			this.stateNum = 0;
		}
		this.stateNum = stateNum;
	}
	public int getSteteNum() { return this.stateNum; }
	public int SetStete() { return this.stateNum; }
*/
}
