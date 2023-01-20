package com.example.masir.utility.extentions

import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.*
import kotlin.reflect.KProperty

fun<T> LiveData<T>.alwaysActive() = this.apply {
    if(!hasActiveObservers()){
        observeForever{} //empty live observer to make it always active
    }
}

fun<T> LiveData<T>.changed() {
    (this as? MutableLiveData<T>)?.run {
        value = value
    }
}


//fun<T> LiveData<T>.withRefresh(refreshLiveData: LiveData<*>) =
//    combineLatestWith(refreshLiveData){ a, _ -> a}

fun<T> MutableLiveData<T>.emit(v:T?){
    if(Looper.getMainLooper() == Looper.myLooper()){
        value = v
    } else {
        postValue(v)
    }
}

fun <T> LiveData<T>.debounce(duration: Long, coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)) = MediatorLiveData<T>().also { mld ->
    val source = this
    var job: Job? = null

    mld.addSource(source) {
        job?.cancel()
        job = coroutineScope.launch(Dispatchers.Main) {
            delay(duration)
            if(isActive)
                mld.value = source.value
        }
    }
}

typealias ChangedAwareLiveDataListener<T> = (old: T?, new: T?) -> Unit
open class ChangedAwareLiveData<T> : MutableLiveData<T> {
    private val listener : ChangedAwareLiveDataListener<T>

    constructor(changedListener : ChangedAwareLiveDataListener<T>) : super(){
        listener = changedListener
    }
    constructor(initialVal: T?, changedListener : ChangedAwareLiveDataListener<T>) : super(initialVal){
        listener = changedListener
    }


    override fun setValue(value: T?) {
        val startVal = this.value
        val changed = startVal != value
        super.setValue(value)
        if(changed){
            listener(startVal, value)
        }
    }
}

interface GenericNullableDelegate<T> {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T?
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?)
}

val<T> MutableLiveData<T>.valDelegate: GenericNullableDelegate<T>
    get() = object : GenericNullableDelegate<T> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        return value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        emit(value)
    }

}


fun <T> LiveData<T>.toEvent(): LiveData<T> {
    val result = LiveEvent<T>()
    result.addSource(this) {
        result.value = it
    }
    return result
}