package com.tsquaredapplications.liquid.ext

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController

fun Fragment.navigate(to: NavDirections) = findNavController().navigate(to)
fun Fragment.navigate(to: NavDirections, navExtras: FragmentNavigator.Extras) = findNavController().navigate(to, navExtras)