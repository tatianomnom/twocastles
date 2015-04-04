package com.leveluptor.twocastles

import spock.lang.Specification


class DeckSpec extends Specification {
    def 'available cards are read from config'() {
        given:
        def deckReader = new DeckReader()

        when:
        def availableCards = deckReader.readDeck()

        then:
        availableCards.size() == 30
    }

    def 'deck is constructed with correct quantities of each card'() {
        given:
        def deckReader = new DeckReader()

        when:
        def availableCards = deckReader.readDeck()
        def deck = deckReader.buildDeck(availableCards)

        then:
        deck.size() == 78
    }

    def 'the deck is infinite'() {
        given:
        def deckReader = new DeckReader()

        when:
        def deck = deckReader.buildDeck(deckReader.readDeck())
        def playingDeck = new InfiniteDeck(deck)

        then:
        300.times {
            playingDeck.drawCard()
        }

    }
}