package ru.skysoftlab.smarthome.heating.cdi;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;

import io.silverspoon.bulldog.core.platform.Board;
import io.silverspoon.bulldog.core.platform.Platform;

public class BoardProducer {

	private Board board = Platform.createBoard();

	public void close(@Disposes Board board) {
		board.shutdown();
		this.board = null;
	}

	@Produces
	@Default
	public Board create() {
		return board;
	}
}
