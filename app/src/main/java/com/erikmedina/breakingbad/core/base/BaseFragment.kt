package com.erikmedina.breakingbad.core.base

import androidx.annotation.UiThread
import androidx.fragment.app.Fragment
import com.erikmedina.breakingbad.MyApp
import com.erikmedina.breakingbad.core.di.presentation.PresentationComponent
import com.erikmedina.breakingbad.core.di.presentation.PresentationModule

open class BaseFragment : Fragment() {

    private var isInjectorUsed: Boolean = false

    @UiThread
    protected fun getPresentationComponent(): PresentationComponent {
        if (isInjectorUsed) {
            throw RuntimeException("there is no need to use injector more than once")
        }
        isInjectorUsed = true
        return getApplicationComponent().newPresentationComponent(PresentationModule(activity!!))

    }

    private fun getApplicationComponent() = (activity?.application as MyApp).applicationComponent
}
