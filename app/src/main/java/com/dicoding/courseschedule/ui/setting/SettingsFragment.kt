package com.dicoding.courseschedule.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder
import com.dicoding.courseschedule.util.NightMode

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //TODO 10 : Update theme based on value in ListPreference
        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference
        setNotification()
        setTheme()
    }

    private fun setTheme(){
        val preferenceTheme = findPreference<ListPreference>(requireContext().getString(R.string.pref_key_dark))
        preferenceTheme?.setOnPreferenceChangeListener { _, value ->
            when(value){
                "on"  -> updateTheme(NightMode.ON.value)
                "auto"    -> updateTheme(NightMode.AUTO.value)
                "off"   -> updateTheme(NightMode.OFF.value)
            }
            return@setOnPreferenceChangeListener true
        }
    }

    private fun setNotification(){
        val dailyReminder = DailyReminder()
        val preferenceNotification = findPreference<SwitchPreference>(requireContext().getString(R.string.pref_key_notify))
        preferenceNotification?.setOnPreferenceChangeListener { _, newValue ->
            if (newValue as Boolean){
                dailyReminder.setDailyReminder(requireContext())
            }else{
                dailyReminder.cancelAlarm(requireContext())
            }
            true
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}