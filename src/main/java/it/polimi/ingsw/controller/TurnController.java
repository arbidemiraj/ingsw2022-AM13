package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.message.servermsg.AskCard;
import it.polimi.ingsw.network.message.servermsg.AskCloud;
import it.polimi.ingsw.network.server.GameHandler;

import java.io.Serial;
import java.io.Serializable;

public class TurnController implements Serializable {
    @Serial
    private static final long serialVersionUID = 8578930385438818920L;
    private Game game;
    private GameController gameController;
    private GameHandler gameHandler;


    public TurnController(GameController gameController, GameHandler gameHandler) {
        this.gameController = gameController;
        this.gameHandler = gameHandler;
    }

    public void planningPhase() {
    }

    public void actionPhase() {
    }
}
