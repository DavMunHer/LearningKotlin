package org.example.RA6

import kotlin.reflect.KProperty

/*
Steps (what to do):
1. Design an Observer<T> interface that receives notifications of changes to a value T.
2. Create a Subject<T> class that maintains a list of Observer<T> and allows
subscribing/unsubscribing and notifying.
3. Implement an ObservableDelegate<T> property delegate that:
• Stores the value internally.
• When the value changes (in setValue), calls the Subject to notify the
observers.
4. Use that delegate in a UserSettings class with several properties (e.g. theme,
volume, language) and register observers that log or print the changes.
5. Add a variant that validates values (e.g. volume must be between 0 and 100) and handles
invalid assignments (reject or normalise them).
 */

interface Observer<T> {
    fun onChange(oldValue: T, newValue: T)

}

class Subject<T> {
    private val observers = mutableListOf<Observer<T>>()

    fun subscribe(observer: Observer<T>) {
        observers += observer
    }

    fun unsubscribe(observer: Observer<T>) {
        observers -= observer
    }

    fun notifyChange(oldValue: T, newValue: T) {
        observers.forEach { it.onChange(oldValue, newValue) }
    }
}

class ObservableDelegate<T>(
    private var value: T,
    private val subject: Subject<T>,
    private val validator: ((T) -> T)? = null
) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = value

    operator fun setValue(thisRef: Any?, property: KProperty<*>, newValue: T) {
        val validatedValue = validator?.invoke(newValue) ?: newValue
        if (validatedValue != value) {
            val oldValue = value
            value = validatedValue
            subject.notifyChange(oldValue, validatedValue)
        }
    }
}

class UserSettings {
    private val themeSubject = Subject<String>()
    private val volumeSubject = Subject<Int>()
    private val languageSubject = Subject<String>()

    var theme: String by ObservableDelegate("Light", themeSubject)
    var volume: Int by ObservableDelegate(50, volumeSubject) { newValue ->
        newValue.coerceIn(0, 100)
    }
    var language: String by ObservableDelegate("English", languageSubject)

    fun onThemeChange(observer: Observer<String>) = themeSubject.subscribe(observer)
    fun onVolumeChange(observer: Observer<Int>) = volumeSubject.subscribe(observer)
    fun onLanguageChange(observer: Observer<String>) = languageSubject.subscribe(observer)
}


fun main() {
    val settings = UserSettings()

    val themeLogger = object : Observer<String> {
        override fun onChange(oldValue: String, newValue: String) {
            println("Theme changed from '$oldValue' to '$newValue'")
        }
    }

    val volumeLogger = object : Observer<Int> {
        override fun onChange(oldValue: Int, newValue: Int) {
            println("Volume changed from $oldValue to $newValue")
        }
    }

    settings.onThemeChange(themeLogger)
    settings.onVolumeChange(volumeLogger)

    settings.theme = "Dark"
    settings.volume = 80
    settings.volume = 150
}
