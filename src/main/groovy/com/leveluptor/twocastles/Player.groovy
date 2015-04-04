package com.leveluptor.twocastles

import groovy.beans.Bindable

class Player {

    String name

    def generators = [builders: 2, soldiers: 2, magic: 2]
    def resources = [bricks: 5, weapons: 5, crystals: 5]

    def generators2Resources = [builders: 'bricks', soldiers: 'weapons', magic: 'crystals']

    @Bindable def fence = 10
    @Bindable def castle = 30

    List<Card> cards = new ArrayList<Card>()

    List<Card> currentAvailableCards = new ArrayList<Card>()

    Player(String name) {
        this.name = name
        propertyChange = {
            if (it.propertyName == 'castle' || it.propertyName == 'fence') {

                if (it.newValue < 0) {
                    it.newValue = 0
                }
                println "$name $it.propertyName was $it.oldValue, now $it.newValue"
            }
        }
    }

    def updateResources() {
        generators.each { generator ->
            def correspondingResource = generators2Resources[generator.key]
            resources[correspondingResource] += generator.value
        }
    }

    boolean hasAvailableCard() {
        currentAvailableCards.clear()

        cards.each { card ->
            if (resources[card.price.resource as String] >= card.price.amount) {
                if (card?.precondition?.fenceHeight != null) {
                    if (card.precondition.fenceHeight <= fence) {
                        currentAvailableCards.add(card)
                    } else {
                        println 'not enough fence height'
                    }
                } else {
                    currentAvailableCards.add(card)
                }
            } else {
                println "can't afford card $card.name"
            }

        }

        return !currentAvailableCards.isEmpty()
    }

    Card chooseCard() {
        def randomIndex = new Random().nextInt(currentAvailableCards.size())
        Card card = currentAvailableCards.get(randomIndex)
        currentAvailableCards.remove(randomIndex)
        return card
    }

}
