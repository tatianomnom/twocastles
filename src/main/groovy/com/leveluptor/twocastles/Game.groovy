package com.leveluptor.twocastles

import groovy.transform.TypeChecked

//@TypeChecked
class Game {

    private InfiniteDeck deck

    Player redPlayer
    Player bluePlayer

    Game() {
        deck = new InfiniteDeck(new DeckReader().buildDeck())

        redPlayer = new Player('red')
        bluePlayer = new Player('blue')
        for (i in 1..8) {
            bluePlayer.cards.add(deck.drawCard())
            redPlayer.cards.add(deck.drawCard())
        }
    }

    def start() {

        def activePlayer = redPlayer
        def enemyPlayer = bluePlayer

        while (!isOver()) {
            activePlayer.updateResources()
            if (activePlayer.hasAvailableCard()) {
                Card card = activePlayer.chooseCard()
                evaluateMechanics(card, activePlayer, enemyPlayer)
            }

            (activePlayer, enemyPlayer) = [enemyPlayer, activePlayer]
        }

        println 'Game over'
    }

    def evaluateMechanics(Card card, Player activePlayer, Player enemyPlayer) {
        card.effects.each { effect ->
            new CardMechanics(effect, activePlayer, enemyPlayer).evaluate()
        }
    }

    def isOver() {
        return bluePlayer.castle >= 100 ||
                bluePlayer.castle <= 0 ||
                redPlayer.castle >= 100 ||
                redPlayer.castle <= 0
    }


}

