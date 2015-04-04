package com.leveluptor.twocastles

import spock.lang.Specification


class DamageSpec extends Specification {

    def game = new Game()
    def player = game.redPlayer

    def 'fence receives damage first'() {
        given:
        player.castle = castle
        player.fence = fence

        when:
        player.receiveDamage(damage)

        then:
        player.castle == castleNew
        player.fence == fenceNew

        where:
        castle | fence | damage | castleNew | fenceNew
        30     | 10    | 3      | 30        | 7
        30     | 10    | 15     | 25        | 0
        10     | 0     | 9      | 1         | 0
        10     | 30    | 39     | 1         | 0
        10     | 30    | 40     | 0         | 0
        10     | 30    | 41     | 0         | 0
    }

    def 'castle receives direct castle damage despite the fence'() {
        given:
        player.castle = castle
        player.fence = fence

        when:
        player.receiveCastleDamage(damage)

        then:
        player.castle == castleNew
        player.fence == fenceNew

        where:
        castle | fence | damage | castleNew | fenceNew
        30     | 10    | 3      | 27        | 10
        30     | 10    | 15     | 15        | 10
        10     | 0     | 9      | 1         | 0
        10     | 90    | 90     | 0         | 90
    }
}