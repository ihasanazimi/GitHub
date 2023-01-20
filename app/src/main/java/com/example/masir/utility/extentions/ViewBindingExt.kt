package com.example.masir.utility.extentions

import androidx.viewbinding.ViewBinding

fun ViewBinding.setView(view: Any){
    try{ javaClass.getMethod("setView", view.javaClass).invoke(this,view)
    }catch (e: Exception){ /**e.printStackTrace()*/ }
}
fun ViewBinding.setVm(vm: Any){
    try{ javaClass.getMethod("setVm", vm.javaClass).invoke(this,vm)
    }catch (e: Exception){ /**e.printStackTrace()*/ }
}