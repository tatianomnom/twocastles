package com.leveluptor.twocastles

class InfiniteDeck {

    private final List<Card> initialList
    Stack<Card> workingList

    InfiniteDeck(List<Card> initialList) {
        this.initialList = Collections.unmodifiableList(initialList)
        workingList = new Stack<>()
        workingList.addAll(initialList)
        Collections.shuffle(workingList)
    }

    Card drawCard() {
        if (workingList.isEmpty()) {
            workingList.addAll(initialList)
            Collections.shuffle(workingList)
        }
        return workingList.pop()

    }

}
