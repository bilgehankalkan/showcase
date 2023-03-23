package com.trendyol.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.trendyol.medusalib.navigator.MultipleStackNavigator
import com.trendyol.medusalib.navigator.Navigator
import com.trendyol.medusalib.navigator.NavigatorConfiguration
import com.trendyol.medusalib.navigator.transaction.NavigatorTransaction

class MainActivity : AppCompatActivity(R.layout.activity_main), Navigator.NavigatorListener {

    private var onTabChangeListener: ((tabIndex: Int) -> Unit)? = null
    private lateinit var navigator: Navigator
    private val bottomNavigation: BottomNavigationView by lazy {
        findViewById(R.id.bottomNavigation)
    }
    private lateinit var navigationOperationHandler: NavigationOperationHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigator = createNavigator()
        initializeMainActivity(savedInstanceState)
    }

    private fun initializeMainActivity(savedInstanceState: Bundle?) {
        navigator.initialize(savedInstanceState)
        navigationOperationHandler = NavigationOperationHandler(navigator)
        initializeBottomBarListener()
    }

    private fun initializeBottomBarListener() {
        bottomNavigation.setOnItemSelectedListener {
            navigator.switchTab(it.order)
            true
        }
        bottomNavigation.setOnItemReselectedListener {
            val selectedTabIndex = it.order
            if (navigator.hasOnlyRoot(selectedTabIndex).not()) {
                navigator.reset(selectedTabIndex)
            }
        }
    }

    private fun createNavigator(): Navigator =
        MultipleStackNavigator(
            fragmentManager = supportFragmentManager,
            containerId = R.id.containerMain,
            rootFragmentProvider = getRootFragments().map { { it } },
            navigatorListener = this,
            navigatorConfiguration = NavigatorConfiguration(
                initialTabIndex = 0,
                alwaysExitFromInitial = true,
                defaultNavigatorTransaction = NavigatorTransaction.SHOW_HIDE
            )
        )

    private fun getRootFragments(): ArrayList<Fragment> = arrayListOf(
        SampleFragment.newInstance(isStatusBarVisible = true),
        SampleFragment.newInstance(isStatusBarVisible = false),
        SecondarySampleFragment(1),
        SecondarySampleFragment(2),
    )

    fun getNavigator(): Navigator {
        return navigator
    }

    override fun onTabChanged(tabIndex: Int) {
        val bottomNavigationView = bottomNavigation
        val selectedItemId = bottomNavigationView.menu.getItem(tabIndex).itemId
        onTabChangeListener?.invoke(tabIndex)

        if (selectedItemId != bottomNavigationView.selectedItemId) {
            bottomNavigationView.selectedItemId = selectedItemId
        }
    }
}