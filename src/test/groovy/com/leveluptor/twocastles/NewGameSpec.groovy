package com.leveluptor.twocastles

import spock.lang.Specification

class NewGameSpec extends Specification {

    def handSize = 8
    def defaultFence = 10
    def defaultCastle = 30

    def game = new Game()

    def 'game is not over'() {
        expect:
        !game.isOver()
    }

    def 'players have correct castle and fence height'() {
        expect:
        game.bluePlayer.castle == defaultCastle
        game.redPlayer.castle == defaultCastle
        game.bluePlayer.fence == defaultFence
        game.redPlayer.fence == defaultFence
    }

    def 'players have correct number of cards'() {
        expect:
        game.bluePlayer.cards.size() == handSize
        game.redPlayer.cards.size() == handSize
    }

    def 'players have correct amount of resources'() {
        expect:
        game.bluePlayer.generators.every {it.value == 2}
        game.redPlayer.generators.every {it.value == 2}
        game.bluePlayer.resources.every {it.value == 5}
        game.redPlayer.resources.every {it.value == 5}
    }
}