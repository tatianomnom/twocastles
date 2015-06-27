package com.leveluptor.twocastles


class CardMechanics {

    Player activePlayer
    Player enemy
    Object effect

    CardMechanics(Object effect, Player activePlayer, Player enemy) {
        this.effect = effect
        this.activePlayer = activePlayer
        this.enemy = enemy
    }

    def evaluate() {
        Map params = new HashMap(effect)
        params.remove('type')
        this."$effect.type"(params)
    }

    def changeOwnFence(def params) {
        activePlayer.fence += params.amount
    }

    def reinforceOwnCastle(def params) {
        activePlayer.castle += params.amount
    }

    def addGenerator(def params) {
        if (params.generatorType == 'all') {
            activePlayer.generators.each { generator ->
                generator.value += params.amount
            }
        } else {
            def generatorType = params.generatorType as String
            activePlayer.generators.put(generatorType,
                    activePlayer.generators[generatorType] + params.amount as int)
        }
    }

    def removeEnemyGenerator(def params) {
        if (params.generatorType == 'all') {
            enemy.generators.each { generator ->
                generator.value += params.amount
            }
        } else {
            def generatorType = params.generatorType as String
            enemy.generators.put(generatorType,
                    enemy.generators[generatorType] - params.amount as int)
        }

        enemy.generators.each { if (it.value < 0) it.value = 0 }
    }

    def damageEnemyCastle(def params) {
        enemy.castle -= params.amount
//        if (enemy.castle < 0) enemy.castle = 0
    }

    def damageEnemy(def params) {
        if (params.amount <= enemy.fence) {
            enemy.fence -= params.amount
        } else {
            enemy.castle -= params.amount - enemy.fence
            enemy.fence = 0

            /*if (enemy.castle < 0) {
                enemy.castle = 0
            }*/
        }
    }

    def destroyEnemyResource(def params) {
        if (params.resourceType == 'all') {
            enemy.resources.each { r ->
                if (r.value < params.amount) {
                    r.value = 0
                } else {
                    r.value -= params.amount
                }
            }
        } else {
            if (enemy.resources[params.resourceType] < params.amount) {
                enemy.resources[params.resourceType] = 0
            } else {
                enemy.resources[params.resourceType] -= params.amount
            }
        }
    }

    def stealEnemyResources(def params) {
        enemy.resources.each { r ->
            if (r.value < params.amount) {
                activePlayer.resources[r.key] += r.value
                r.value = 0
            } else {
                r.value -= params.amount
                activePlayer.resources[r.key] += r.value
            }
        }

    }

    def createResource(def params) {
        activePlayer.resources[params.resourceType] += params.amount
    }


}
