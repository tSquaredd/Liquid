package com.tsquaredapplications.liquid.ext

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

fun Fragment.navigate(to: NavDirections) = findNavController().navigate(to)