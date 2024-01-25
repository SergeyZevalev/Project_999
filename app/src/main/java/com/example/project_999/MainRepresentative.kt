package com.example.project_999

interface MainRepresentative {

    fun startGettingUpdates(callback: UiObserver<Int>)
    fun stopGettingUpdates()
    fun startAsync()
    fun saveState()

    class Base(
        private val observable: UiObservable<Int>
    ) : MainRepresentative {

        private val thread = Thread {
            Thread.sleep(5000)
            observable.update(R.string.hello)

        }

        override fun startGettingUpdates(callback: UiObserver<Int>) =
            observable.updateObserver(callback)

        override fun stopGettingUpdates() = observable.updateObserver()

        override fun startAsync() = thread.start()

        override fun saveState() {
            //TODO: save and restore
        }

    }
}