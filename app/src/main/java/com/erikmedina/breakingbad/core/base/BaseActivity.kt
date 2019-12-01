package com.erikmedina.breakingbad.core.base

import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import com.erikmedina.breakingbad.MyApp
import com.erikmedina.breakingbad.core.di.presentation.PresentationComponent
import com.erikmedina.breakingbad.core.di.presentation.PresentationModule

/**
 * Activity declared as open so other activities can inherit from it
 *
 * It gives the PresentationComponent to its children so they can inject dependencies.
 */
open class BaseActivity : AppCompatActivity() {

    private var isInjectorUsed: Boolean = false

    @UiThread
    protected fun getPresentationComponent(): PresentationComponent {
        if (isInjectorUsed) {
            throw RuntimeException("there is no need to use injector more than once")
        }
        isInjectorUsed = true
        return getApplicationComponent().newPresentationComponent(PresentationModule(this))
    }

    private fun getApplicationComponent() = (application as MyApp).applicationComponent
}
