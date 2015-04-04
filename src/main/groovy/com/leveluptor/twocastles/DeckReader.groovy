package com.leveluptor.twocastles

import groovy.json.JsonSlurper

class DeckReader {
    List<Card> readDeck() {
        List<Card> cards = new ArrayList<>()
        def slurper = new JsonSlurper()
        def result = slurper.parse(this.class.getResourceAsStream('/cards.json') as InputStream)
        result.cards.each() {
            cards.add(it as Card)
        }

        println cards.size()
        return cards
    }

    List<Card> buildDeck(List<Card> sourceCards = readDeck()) {

        Properties cardQuantities = new Properties()
        InputStream is = this.class.getResourceAsStream('/cardQuantities.properties')
        cardQuantities.load(is)

        List<Card> finalDeck = new LinkedList<>()
        sourceCards.each {
            def card = it
            int quantity = cardQuantities[card.name] as int
            quantity.times {
                finalDeck.add(card)
            }
        }

        println finalDeck.size()

        return finalDeck

    }

}
