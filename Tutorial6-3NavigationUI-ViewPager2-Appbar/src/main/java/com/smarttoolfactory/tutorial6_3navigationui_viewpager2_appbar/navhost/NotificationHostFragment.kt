package com.smarttoolfactory.tutorial6_3navigationui_viewpager2_appbar.navhost

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.smarttoolfactory.tutorial6_3navigationui_viewpager2_appbar.R
import com.smarttoolfactory.tutorial6_3navigationui_viewpager2_appbar.blankfragment.BaseDataBindingFragment
import com.smarttoolfactory.tutorial6_3navigationui_viewpager2_appbar.databinding.FragmentNavhostNotificationBinding


class NotificationHostFragment : BaseDataBindingFragment<FragmentNavhostNotificationBinding>() {
    override fun getLayoutRes(): Int = R.layout.fragment_navhost_notification

    val notificationNavController = MutableLiveData<NavController>()
    private var navController: NavController? = null

    private val nestedNavHostFragmentId = R.id.nested_nav_host_fragment_notification


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nestedNavHostFragment =
            childFragmentManager.findFragmentById(nestedNavHostFragmentId) as? NavHostFragment
        navController = nestedNavHostFragment?.navController
        notificationNavController.value = navController

        // Listen on back press
        listenOnBackPressed()

    }


    private fun listenOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }


    override fun onResume() {
        super.onResume()
        callback.isEnabled = true
    }

    override fun onPause() {
        super.onPause()
        callback.isEnabled = false
    }

    val callback = object : OnBackPressedCallback(true) {

        override fun handleOnBackPressed() {

            // Check if it's the root of nested fragments in this navhost
            if (navController?.currentDestination?.id == navController?.graph?.startDestination) {
                Toast.makeText(requireContext(), "⏰ AT START DESTINATION ", Toast.LENGTH_SHORT)
                    .show()
                remove()
                requireActivity().onBackPressed()
            } else if (isVisible) {
                navController?.navigateUp()
            }

        }
    }

}